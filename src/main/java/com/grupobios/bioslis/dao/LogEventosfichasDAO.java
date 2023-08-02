/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.LogFichasExamenesTestDTO;
import com.grupobios.bioslis.dto.UltimoLogEventoCampoDatoFicha;
import com.grupobios.bioslis.entity.DatosFichasexamenestestId;
import com.grupobios.bioslis.entity.LogEventosfichas;

/**
 *
 * @author Nacho
 */
public class LogEventosfichasDAO {

  private static final Logger logger = LogManager.getLogger(LogEventosfichasDAO.class);

  public void insertLogEventosFichas(LogEventosfichas lef) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String[] ipLocal = new String[2];  
    try {
      InetAddress address = InetAddress.getLocalHost();

      // IP en formato String
      String paso = address.toString();
      ipLocal = paso.split("/");

    } catch (UnknownHostException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    lef.setLefIpestacion(ipLocal[1]);
    if (ipLocal[0].length() > 50) {
      lef.setLefNombreequipo(ipLocal[0].substring(0, 50));
    } else {
      lef.setLefNombreequipo(ipLocal[0]);
    }
    try {
      sesion.beginTransaction();
      logger.debug(lef);
      sesion.save(lef);
      sesion.getTransaction().commit();
      sesion.close();
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error("error en insertLogEventosFichas " + he.getMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    logger.debug("insertando eventos en insertLogEventosFichas");
  }

  @SuppressWarnings("unchecked")
  public List<LogFichasExamenesTestDTO> getLogEventosTest(DatosFichasexamenestestId dfetId)
      throws BiosLISDAONotFoundException, BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<LogFichasExamenesTestDTO> lstEventosTest = null;
    try {
      /*
       * Query qry = sesion.createQuery(
       * "select lef from LogEventosfichas lef where lef.datosFichas = :datosFicha and lef.lefIdexamen = :idExamen and lef.lefIdtest= :idTest"
       * );
       */

      Query qry = sesion.createSQLQuery(
          "select lef.lef_idlogeventoficha, lef.lef_idevento, lef.lef_norden , lef.lef_valoranterior, lef.lef_valornuevo, lef.lef_ipestacion,"
              + " lef.lef_nombreequipo , lef.lef_idmuestra,  lef.lef_codigobarra , lef.lef_nombrecampo , "
              + "lef.lef_fecharegistro,  du.du_nombres, du.du_primerapellido, du.du_segundoapellido , "
              + " ce.ce_abreviado , ct.ct_abreviado, ct.ct_idtest ,"
              + " (select   concat(concat(dp.dp_nombres , ' ' ), dp.dp_primerapellido ) as nombrepaciente from  datos_pacientes dp where dp.dp_idpaciente = lef.lef_idpaciente ) as nombrepaciente "
              + " from Log_Eventosfichas lef " + " INNER JOIN datos_usuarios du on du.du_idusuario = lef.lef_idusuario "
              + " left join cfg_examenes ce on ce.ce_idexamen = lef.lef_idexamen"
              + " left join cfg_test ct on ct.ct_idtest = lef.lef_idtest  "
              + "where lef.lef_norden= :datosFicha and lef.lef_Idexamen = :idExamen and lef.lef_Idtest= :idTest order by lef.lef_idlogeventoficha Desc")
          .setResultTransformer(Transformers.aliasToBean(LogFichasExamenesTestDTO.class));
      ;
      qry.setLong("datosFicha", dfetId.getDfetNorden());
      qry.setLong("idExamen", dfetId.getDfetIdexamen());
      qry.setLong("idTest", dfetId.getDfetIdtest());
      lstEventosTest = qry.list();
      sesion.close();

      if (lstEventosTest.isEmpty()) {
        if (sesion != null && sesion.isOpen()) {
          sesion.close();
        }
        throw new BiosLISDAONotFoundException("No hay eventos asociados al test");
      }

    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error("error en getExamenes " + he.getMessage());
      throw new BiosLISDAOException("No hay eventos asociados al test");
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return lstEventosTest;
  }

  @SuppressWarnings("unchecked")
  public Map<String, LogEventosfichas> getLogUltimosEventosOrden(Long orderId, String tableName, Long idExamen,
      Long idTest, Long idMuestra) throws BiosLISDAONotFoundException, BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    Map<String, LogEventosfichas> mapEventos = new HashMap<String, LogEventosfichas>();
    try {

      logger.debug("getLogUltimosEventosOrden, parametros:{} - {} - {} - {} - {} ", orderId, tableName, idExamen,
          idTest, idMuestra);

      Query qry = sesion.createSQLQuery(sqlUltimasModificaciones)
          .setResultTransformer(Transformers.aliasToBean(UltimoLogEventoCampoDatoFicha.class));
      qry.setLong("nOrden", orderId);
      qry.setString("tableName", tableName);

      List<UltimoLogEventoCampoDatoFicha> lst = (List<UltimoLogEventoCampoDatoFicha>) qry.list();
      sesion.close();

      if (lst.isEmpty()) {
        logger.debug("Ultimos eventos para Orden {}, en la tabla{}", orderId, tableName);
      }
      for (UltimoLogEventoCampoDatoFicha u : lst) {

        logger.debug("\nUltima modificación \n\t{}", u);
        LogEventosfichas lef = this.getLogUltimoEventoCampoOrden(orderId, tableName, u.getLEFNOMBRECAMPO(),
            u.getUFECHAREGISTRO(), idExamen, idTest, idMuestra);
        if (lef != null) {
          mapEventos.put(lef.getLefNombrecampo(), lef);
        }
      }

    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error("error en getLogUltimosEventosOrden " + he.getMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return mapEventos;
  }

  public LogEventosfichas getLogUltimoEventoCampoOrden(Long orderId, String tableName, String colName,
      Date fecharegistro, Long idExamen, Long idTest, Long idMuestra)
      throws BiosLISDAONotFoundException, BiosLISDAOException {
    logger.debug("buscando eventos en getLogUltimoEventoCampoOrden");
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String sQuery = hSqlUltimaModificacion;
    LogEventosfichas lef = null;
    try {

      if (tableName.equals("DATOS_FICHASEXAMENES") && idExamen != null) {
        sQuery = hSqlUltimaModificacion + " and lef.lefIdexamen=:idExamen";
      }
      if (tableName.equals("DATOS_FICHASEXAMENESTEST") && idExamen != null && idTest != null) {
        sQuery = hSqlUltimaModificacion + " and lef.lefIdexamen=:idExamen and lef.lefIdtest=:idTest ";
      }
      Query qry = sesion.createQuery(sQuery);
      qry.setLong("nOrden", orderId);
      qry.setString("tableName", tableName);
      qry.setString("colName", colName);
      qry.setTimestamp("fecharegistro", fecharegistro);
      if (tableName.equals("DATOS_FICHASEXAMENES") && idExamen != null) {
        qry.setLong("idExamen", idExamen);
      }
      if (tableName.equals("DATOS_FICHASEXAMENESTEST") && idExamen != null && idTest != null) {
        qry.setLong("idExamen", idExamen);
        qry.setLong("idTest", idTest);
      }
      lef = (LogEventosfichas) qry.uniqueResult();
      logger.debug("Lef : {}", lef);
      sesion.close();

    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error("error en getLogUltimoEventoCampoOrden " + he.getMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return lef;
  }

  private static final String sqlUltimasModificaciones = "SELECT lef.LEF_NORDEN datosfichas, lef.LEF_NOMBRETABLA lefNombretabla, lef.LEF_NOMBRECAMPO lefNombrecampo, max (lef.LEF_FECHAREGISTRO) UFECHAREGISTRO "
      + "FROM BIOSLIS.LOG_EVENTOSFICHAS lef "
      + "WHERE lef.LEF_NORDEN = :nOrden and lef.LEF_NOMBRETABLA like :tableName "
      + "AND lef.LEF_NOMBRECAMPO is not null " + "GROUP BY lef.LEF_NORDEN, lef.LEF_NOMBRETABLA , lef.LEF_NOMBRECAMPO";
  /*
   * se comenta por cristian - no se estaba usando -- 12-07-2022 private static
   * final String hSqlUltimasModificaciones =
   * "SELECT lef.datosFichas, lef.lefNombretabla , lef.lefNombrecampo,  max(lef.lefFecharegistro) uFecharegistro "
   * + "from  LogEventosfichas lef " +
   * "WHERE lef.datosFichas = :nOrden and lef.lefNombretabla like :tableName " +
   * "GROUP BY lef.datosFichas, lef.lefNombretabla , lef.lefNombrecampo ";
   */
  private static final String hSqlUltimaModificacion = "SELECT lef " + "from  LogEventosfichas lef "
      + "WHERE lef.datosFichas = :nOrden and lef.lefNombretabla like :tableName and lef.lefNombrecampo like :colName and lef.lefFecharegistro =:fecharegistro ";

  public List<LogFichasExamenesTestDTO> getEventosFichasByOrder(Long orderId, String filtro, String idTest)
      throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<LogFichasExamenesTestDTO> lstEventosOrder = null;

    try {
      String query = "select lef.lef_idlogeventoficha, lef.lef_idevento, lef.lef_norden , lef.lef_valoranterior, lef.lef_valornuevo, lef.lef_ipestacion,"
          + " lef.lef_nombreequipo , lef.lef_idmuestra,  lef.lef_codigobarra , lef.lef_nombrecampo , lef.lef_nombretabla , "
          + "lef.lef_fecharegistro,  du.du_nombres, du.du_primerapellido, du.du_segundoapellido , "
          + " ce.ce_abreviado , ct.ct_abreviado, ct.ct_idtest ,"
          + " (select   concat(concat(dp.dp_nombres , ' ' ), dp.dp_primerapellido ) as nombrepaciente from  datos_pacientes dp where dp.dp_idpaciente = lef.lef_idpaciente ) as nombrepaciente "
          + " from Log_Eventosfichas lef " + " INNER JOIN datos_usuarios du on du.du_idusuario = lef.lef_idusuario "
          + " left join cfg_examenes ce on ce.ce_idexamen = lef.lef_idexamen"
          + " left join cfg_test ct on ct.ct_idtest = lef.lef_idtest  " + "where lef.lef_norden = :datosFicha ";
      if (!idTest.equals("-1")) {
        query = query + " and lef.lef_idtest =:idTest  ";
      }

      if (!filtro.equals("-1")) {
        filtro = filtro.toUpperCase();

        query = query + " and (lef.lef_valoranterior like '%" + filtro + "%' or lef.lef_valornuevo like '%" + filtro
            + "%'" + " or lef.lef_codigobarra like '%" + filtro + "%' or lef.lef_nombrecampo like '%" + filtro + "%'"
            + " or du.du_nombres like '%" + filtro + "%'  or du.du_primerapellido like '%" + filtro + "%' "
            + " or ce.ce_abreviado like '%" + filtro + "%'  or  ct.ct_abreviado like '%" + filtro + "%'  )";
      }

      query = query + " order by lef.lef_idlogeventoficha Desc ";
      Query qry = sesion.createSQLQuery(query)
          .setResultTransformer(Transformers.aliasToBean(LogFichasExamenesTestDTO.class));
      ;
      qry.setLong("datosFicha", orderId);
      if (!idTest.equals("-1")) {
        qry.setParameter("idTest", idTest);
      }
      lstEventosOrder = qry.list();
      sesion.close();

    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error("error en getExamenes " + he.getMessage());
      throw new BiosLISDAOException("No hay eventos asociados al test");
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return lstEventosOrder;

  }

  public void insertLogEventosFichas(LogEventosfichas lef, Session sesion) {

    String[] ipLocal = new String[2];
    try {
      InetAddress address = InetAddress.getLocalHost();
      // IP en formato String
      String paso = address.toString();
      ipLocal = paso.split("/");
    } catch (UnknownHostException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
    }

    lef.setLefIpestacion(ipLocal[1]);
    if (ipLocal[0].length() > 50) {
      lef.setLefNombreequipo(ipLocal[0].substring(0, 50));
    } else {
      lef.setLefNombreequipo(ipLocal[0]);
    }
    try {
//      sesion.beginTransaction();
      sesion.save(lef);
//      sesion.getTransaction().commit();
//      sesion.close();
    } catch (HibernateException he) {
      //if (sesion != null && sesion.isOpen()) {
       // sesion.close();
     // }
    	he.printStackTrace();
      logger.error("error en insertLogEventosFichas " + he.getMessage());
    } finally {
//      if (sesion != null && sesion.isOpen()) {
//        sesion.close();
//      }
    }
    logger.debug("insertando eventos en insertLogEventosFichas");
  }






}


