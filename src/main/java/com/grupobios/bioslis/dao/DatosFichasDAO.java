/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.math.BigDecimal;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.BiosLisLogger;
import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.BiosLisEtiquetaDTO;
import com.grupobios.bioslis.dto.CfgExamenesDTO;
import com.grupobios.bioslis.dto.ExamenPacienteDTO;
import com.grupobios.bioslis.dto.OrdenesTMDTO;
import com.grupobios.bioslis.dto.TMClickDTO;
import com.grupobios.bioslis.entity.CfgDerivadores;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;
import com.grupobios.bioslis.entity.DatosFichasmuestras;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.entity.LogEventosfichas;

/**
 *
 * @author Jan Perkov
 */
public class DatosFichasDAO {

  private static Logger logger = LogManager.getLogger(DatosFichasDAO.class);

  private CapturaResultadosDAO capturaResultadosDAO;
  private DatosFichasExamenesDAO datosFichasExamenesDAO;
  private DatosFichasExamenesTestDAO datosFichasExamenesTestDAO;

  public DatosFichas getOrdenxID(int nOrden) throws BiosLISDAOException {

    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery("select med from DatosFichas med " + "where med.dfNorden = :nOrden");
      query.setParameter("nOrden", nOrden);
      DatosFichas df = (DatosFichas) query.uniqueResult();
      sesion.close();
      return df;
    } catch (HibernateException he) {
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  // Este metodo es igual anterior. Hay que unificarlos.
  public DatosFichas getDatosOrden(Long nOrden) {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery("select med from DatosFichas med " + "where med.dfNorden = :nOrden");
      query.setLong("nOrden", nOrden);
      DatosFichas paciente = (DatosFichas) query.uniqueResult();
      sesion.close();
      return paciente;

    } catch (HibernateException e) {
      logger.error("Error al obtener datos de orden {}", e.getMessage());
      throw e;
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public void updateOrden(DatosFichas med, String[] examenes, String ipEquipo, Long idUsuario)
      throws BiosLISDAOException {
    LogCfgDatosFichasDAO log = new LogCfgDatosFichasDAO();
    DatosFichasExamenesDAO dfed = new DatosFichasExamenesDAO();
    DatosFichasExamenesTestDAO dfetd = new DatosFichasExamenesTestDAO();
    Session sesion = HibernateUtil.getSessionFactory().openSession();

    try {
      DatosFichas updateDf = this.getOrdenxID(med.getDfNorden());

      log.comparadorObjetos(med, updateDf, "Datos_Fichas", 2, 1, "Equipo", med.getDfNorden(), "1.1",
          med.getDfFechaorden(), examenes, med.getDatosPacientes());

      // comparador

      updateDf.setCfgConvenios(med.getCfgConvenios());
      updateDf.setCfgDiagnosticos(med.getCfgDiagnosticos());
      updateDf.setCfgInstitucionesdesalud(med.getCfgInstitucionesdesalud());
      updateDf.setCfgPrioridadatencion(med.getCfgPrioridadatencion());
      updateDf.setCfgProcedencias(med.getCfgProcedencias());
      updateDf.setCfgServicios(med.getCfgServicios());
      updateDf.setCfgTipoatencion(med.getCfgTipoatencion());
      updateDf.setDatosPacientes(med.getDatosPacientes());
      updateDf.setDfEnviarresultadosemail(med.getDfEnviarresultadosemail());
      updateDf.setDfIdmedico(med.getDfIdmedico());
      updateDf.setDfIdprevision(med.getDfIdprevision());
      updateDf.setDfObservacion(med.getDfObservacion());
      sesion.beginTransaction();
      sesion.update(updateDf);
      sesion.getTransaction().commit();
      sesion.close();
      dfed.updateDatosFichasExamenes(med.getDfNorden(), examenes, med.getDatosPacientes(), ipEquipo, idUsuario);
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }

    }

  }

  public void insertOrden(DatosFichas med, Integer[] examenes, String ipEquipo, Long idUsuario)
      throws NumberFormatException, BiosLISDAOException, BiosLISException, BiosLISDAONotFoundException {

    BiosLisLogger bl = new BiosLisLogger();
    logger.debug("Se inserta registro : {}", med.toString());

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      med.setDfFechaorden(BiosLisCalendarService.getInstance().getTS());
      med.setCfgInstitucionesdesalud(1);
      med.setLdvFichasestadostm(Constante.ESTADOFICHAESPERA);
      med.setDfTmclick("N");

      sesion.beginTransaction();
      sesion.save(med);
//      sesion.getTransaction().commit();
//      sesion.close();
    } catch (HibernateException e) {
      if (sesion.isOpen()) {
        sesion.close();
      }
      logger.error("Error en insertOrden {}", e.getMessage());
      throw new BiosLISDAOException("No se pudo insertar orden.");

    }

    int nOrden = med.getDfNorden();

    for (Integer examene : examenes) {
      try {
        datosFichasExamenesDAO.insertDatosFichasExamenes(nOrden, examene, sesion);
      } catch (NumberFormatException | BiosLISDAOException e) {
        logger.error(e.getMessage());
        throw new BiosLISException(e.getMessage());
      }

//

//      datosFichasExamenesTestDAO.insertDatosFichasExamenesTest(nOrden, examene, med.getDatosPacientes(), ipEquipo,
//          idUsuario);

    }
    sesion.getTransaction().commit();

    try {
      bl.logInsertDatosFichaTableRecord(DatosFichas.class, med, new BigDecimal(1),
          BiosLisCalendarService.getInstance().getTS(), new BigDecimal(med.getDfNorden()), med.getDfFechaorden(), null,
          null, new BigDecimal(med.getDatosPacientes()), null, "", Constante.CREACION_DATOS_FICHAS);

    } catch (IllegalArgumentException | IllegalAccessException e1) {
      logger.error("No se pudo insertar registro de log de tabla.\n{}", e1.getMessage());
    }

  }

  public boolean verificarMasDeUnaOrden(int id) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      // sumar un dia a fecha ingresada
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Calendar c = Calendar.getInstance();
      String today = sdf.format(c.getTime());
      c.add(Calendar.DAY_OF_MONTH, 1);
      String newDate = sdf.format(c.getTime());

      sesion.beginTransaction();
      Query query = sesion.createQuery("select med.datosPacientes from com.grupobios.bioslis.entity.DatosFichas med "
          + "where med.dfFechaorden  BETWEEN '" + today + "' and '" + newDate + "' AND " + "med.datosPacientes = :id")
          .setMaxResults(1);
      query.setParameter("id", id);
      List<DatosFichas> lista = query.list();
      sesion.getTransaction().commit();
      sesion.close();
      return lista.isEmpty();
    } catch (Exception e) {
      throw e;
    }

  }

  public boolean verificarExamenxPaciente(int idPaciente, long idExamen) {
    CfgExamenesDAO exadao = new CfgExamenesDAO();
    int diasValidar = exadao.diasValidacionExamen(idExamen);
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    Query query = sesion.createSQLQuery(
        "SELECT a.df_fechaorden from bioslis.datos_fichas a " + "INNER JOIN bioslis.datos_fichasexamenes b "
            + "ON a.df_norden = b.dfe_norden " + "where a.df_idpaciente = :idPaciente AND b.dfe_idexamen = :idExamen "
            + "AND a.df_fechaorden > (sysdate-:diasValidar)");
    query.setParameter("idPaciente", idPaciente);
    query.setParameter("idExamen", idExamen);
    query.setParameter("diasValidar", diasValidar);
    List<DatosFichas> lista = query.list();
    sesion.close();
    return lista.isEmpty();

  }

  public List<DatosFichas> OrdenxPaciente(int idPaciente) {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    Query query = sesion.createQuery("select list from com.grupobios.bioslis.entity.DatosFichas list "
        + "where list.datosPacientes = :idPaciente order by list.dfFechaorden asc");
    query.setParameter("idPaciente", idPaciente);
    List<DatosFichas> lista = query.list();
    sesion.getTransaction().commit();
    sesion.close();
    return lista;

  }

  public void updateOrdenxPaciente(int idPaciente1, int idPaciente2, String ordenes) {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    Query query = sesion.createSQLQuery("update datos_fichas " + "set df_idpaciente = :idPaciente2 "
        + "where df_idpaciente = :idPaciente1 " + "AND df_norden IN (" + ordenes + ")");
    query.setParameter("idPaciente1", idPaciente1);
    query.setParameter("idPaciente2", idPaciente2);
    query.executeUpdate();
    sesion.getTransaction().commit();
    sesion.close();

  }

  public List<Object[]> selectOrdenPorOrden(int nOrden) {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    Query query = sesion.createSQLQuery(
        "select datos_fichas.df_fechaorden,datos_fichas.df_norden,CONCAT(CONCAT(datos_pacientes.dp_nombres,' '),datos_pacientes.dp_primerapellido),ldv_tiposdocumentos.ldvtd_descripcion,datos_pacientes.dp_nrodocumento, cfg_tipoatencion.cta_descripcion,cfg_procedencias.cp_descripcion,cfg_servicios.cs_descripcion "
            + " ,datos_fichas.df_idmedico,cfg_localizaciones.cl_idservicio,cfg_localizaciones.cl_idsalaservicio,cfg_localizaciones.cl_idcamasala,datos_fichas.df_enviarresultadosemail"
            + " ,datos_fichas.df_iddiagnostico,datos_fichas.df_idconvenio,datos_fichas.df_idprioridadatencion,datos_fichas.df_observacion"
            + " from datos_fichas" + " INNER JOIN datos_pacientes ON"
            + "    datos_pacientes.dp_idpaciente = datos_fichas.df_idpaciente" + " INNER JOIN ldv_tiposdocumentos ON"
            + "    ldv_tiposdocumentos.ldvtd_idtipodocumento = datos_pacientes.dp_idtipodocumento"
            + " INNER JOIN cfg_tipoatencion ON"
            + "   cfg_tipoatencion.cta_idtipoatencion = datos_fichas.df_idtipoatencion"
            + " INNER JOIN cfg_procedencias ON" + "  cfg_procedencias.cp_idprocedencia = datos_fichas.df_idprocedencia"
            + " INNER JOIN cfg_localizaciones ON"
            + "  cfg_localizaciones.cl_idlocalizacion = datos_fichas.df_idlocalizacion" + " INNER JOIN cfg_servicios ON"
            + " cfg_servicios.cs_idservicio = cfg_localizaciones.cl_idservicio"
            + " where datos_fichas.df_norden= :nOrden" + " order by datos_fichas.df_fechaorden asc");
    query.setParameter("nOrden", nOrden);
    List<Object[]> lista = query.list();
    sesion.getTransaction().commit();
    sesion.close();
    return lista;

  }

  public List<Object[]> selectOrdenNombreApellido(String nombre, String apellido) {

    Session sesion = HibernateUtil.getSessionFactory().openSession();

    try {

      sesion.beginTransaction();
      Query query = sesion.createSQLQuery(
          "select datos_fichas.df_fechaorden,datos_fichas.df_norden,CONCAT(CONCAT(datos_pacientes.dp_nombres,' '),datos_pacientes.dp_primerapellido),ldv_tiposdocumentos.ldvtd_descripcion,datos_pacientes.dp_nrodocumento, cfg_tipoatencion.cta_descripcion,cfg_procedencias.cp_descripcion,cfg_servicios.cs_descripcion "
              + " ,datos_fichas.df_idmedico,cfg_localizaciones.cl_idservicio,cfg_localizaciones.cl_idsalaservicio,cfg_localizaciones.cl_idcamasala,datos_fichas.df_enviarresultadosemail"
              + " ,datos_fichas.df_iddiagnostico,datos_fichas.df_idconvenio,datos_fichas.df_idprioridadatencion,datos_fichas.df_observacion"
              + " from datos_fichas" + " INNER JOIN datos_pacientes ON"
              + "    datos_pacientes.dp_idpaciente = datos_fichas.df_idpaciente" + " INNER JOIN ldv_tiposdocumentos ON"
              + "    ldv_tiposdocumentos.ldvtd_idtipodocumento = datos_pacientes.dp_idtipodocumento"
              + " INNER JOIN cfg_tipoatencion ON"
              + "   cfg_tipoatencion.cta_idtipoatencion = datos_fichas.df_idtipoatencion"
              + " INNER JOIN cfg_procedencias ON"
              + "  cfg_procedencias.cp_idprocedencia = datos_fichas.df_idprocedencia"
              + " INNER JOIN cfg_localizaciones ON"
              + "  cfg_localizaciones.cl_idlocalizacion = datos_fichas.df_idlocalizacion"
              + " INNER JOIN cfg_servicios ON" + " cfg_servicios.cs_idservicio = cfg_localizaciones.cl_idservicio"
              + " where datos_pacientes.dp_nombres like '" + nombre.toUpperCase() + "%' "
              + " and datos_pacientes.dp_primerapellido like '" + apellido.toUpperCase() + "%'"
              + " order by datos_fichas.df_fechaorden asc");

      List<Object[]> lista = query.list();
      sesion.getTransaction().commit();
      sesion.close();
      return lista;
    } catch (Exception e) {
      throw e;
    }

  }

  public List<Object[]> selectOrdenNombreApellidoFechas(String nombre, String apellido, String fechaInicio,
      String fechaFin) {

    Session sesion = HibernateUtil.getSessionFactory().openSession();

    try {

      sesion.beginTransaction();
      Query query = sesion.createSQLQuery(
          "select datos_fichas.df_fechaorden,datos_fichas.df_norden,CONCAT(CONCAT(datos_pacientes.dp_nombres,' '),datos_pacientes.dp_primerapellido),ldv_tiposdocumentos.ldvtd_descripcion,datos_pacientes.dp_nrodocumento, cfg_tipoatencion.cta_descripcion,cfg_procedencias.cp_descripcion,cfg_servicios.cs_descripcion "
              + " ,datos_fichas.df_idmedico,cfg_localizaciones.cl_idservicio,cfg_localizaciones.cl_idsalaservicio,cfg_localizaciones.cl_idcamasala,datos_fichas.df_enviarresultadosemail"
              + " ,datos_fichas.df_iddiagnostico,datos_fichas.df_idconvenio,datos_fichas.df_idprioridadatencion,datos_fichas.df_observacion"
              + " from datos_fichas" + " INNER JOIN datos_pacientes ON"
              + "    datos_pacientes.dp_idpaciente = datos_fichas.df_idpaciente" + " INNER JOIN ldv_tiposdocumentos ON"
              + "    ldv_tiposdocumentos.ldvtd_idtipodocumento = datos_pacientes.dp_idtipodocumento"
              + " INNER JOIN cfg_tipoatencion ON"
              + "   cfg_tipoatencion.cta_idtipoatencion = datos_fichas.df_idtipoatencion"
              + " INNER JOIN cfg_procedencias ON"
              + "  cfg_procedencias.cp_idprocedencia = datos_fichas.df_idprocedencia"
              + " INNER JOIN cfg_localizaciones ON"
              + "  cfg_localizaciones.cl_idlocalizacion = datos_fichas.df_idlocalizacion"
              + " INNER JOIN cfg_servicios ON" + " cfg_servicios.cs_idservicio = cfg_localizaciones.cl_idservicio"
              + " where datos_pacientes.dp_nombres like '" + nombre.toUpperCase() + "%' "
              + " and datos_pacientes.dp_primerapellido like '" + apellido.toUpperCase() + "%' "
              + " and datos_fichas.df_fechaorden BETWEEN '" + fechaInicio + "' and to_date('" + fechaFin
              + " 23:59:59', 'dd/mm/yy HH24:mi:ss')" + " order by datos_fichas.df_fechaorden asc");

      List<Object[]> lista = query.list();
      sesion.getTransaction().commit();
      sesion.close();
      return lista;
    } catch (Exception e) {
      throw e;
    }

  }

  public List<Object[]> selectOrdenesxnDoc(String rut) {

    Session sesion = HibernateUtil.getSessionFactory().openSession();

    try {

      sesion.beginTransaction();
      Query query = sesion.createSQLQuery(
          "select datos_fichas.df_fechaorden,datos_fichas.df_norden,CONCAT(CONCAT(datos_pacientes.dp_nombres,' '),datos_pacientes.dp_primerapellido),ldv_tiposdocumentos.ldvtd_descripcion,datos_pacientes.dp_nrodocumento, cfg_tipoatencion.cta_descripcion,cfg_procedencias.cp_descripcion,cfg_servicios.cs_descripcion "
              + " ,datos_fichas.df_idmedico,cfg_localizaciones.cl_idservicio,cfg_localizaciones.cl_idsalaservicio,cfg_localizaciones.cl_idcamasala,datos_fichas.df_enviarresultadosemail"
              + " ,datos_fichas.df_iddiagnostico,datos_fichas.df_idconvenio,datos_fichas.df_idprioridadatencion,datos_fichas.df_observacion"
              + " from datos_fichas" + " INNER JOIN datos_pacientes ON"
              + "    datos_pacientes.dp_idpaciente = datos_fichas.df_idpaciente" + " INNER JOIN ldv_tiposdocumentos ON"
              + "    ldv_tiposdocumentos.ldvtd_idtipodocumento = datos_pacientes.dp_idtipodocumento"
              + " INNER JOIN cfg_tipoatencion ON"
              + "   cfg_tipoatencion.cta_idtipoatencion = datos_fichas.df_idtipoatencion"
              + " INNER JOIN cfg_procedencias ON"
              + "  cfg_procedencias.cp_idprocedencia = datos_fichas.df_idprocedencia"
              + " INNER JOIN cfg_localizaciones ON"
              + "  cfg_localizaciones.cl_idlocalizacion = datos_fichas.df_idlocalizacion"
              + " INNER JOIN cfg_servicios ON" + " cfg_servicios.cs_idservicio = cfg_localizaciones.cl_idservicio"
              + "  where datos_pacientes.dp_nrodocumento = :rut" + "  order by datos_fichas.df_fechaorden asc");
      query.setParameter("rut", rut);
      List<Object[]> lista = query.list();
      sesion.getTransaction().commit();
      sesion.close();
      return lista;
    } catch (Exception e) {
      throw e;
    }

  }

  public List<Object[]> selectOrdenRutYFechas(String rut, String fechaInicio, String fechaFin) {

    Session sesion = HibernateUtil.getSessionFactory().openSession();

    try {

      sesion.beginTransaction();
      Query query = sesion.createSQLQuery(
          "select datos_fichas.df_fechaorden,datos_fichas.df_norden,CONCAT(CONCAT(datos_pacientes.dp_nombres,' '),datos_pacientes.dp_primerapellido),ldv_tiposdocumentos.ldvtd_descripcion,datos_pacientes.dp_nrodocumento, cfg_tipoatencion.cta_descripcion,cfg_procedencias.cp_descripcion,cfg_servicios.cs_descripcion "
              + " ,datos_fichas.df_idmedico,cfg_localizaciones.cl_idservicio,cfg_localizaciones.cl_idsalaservicio,cfg_localizaciones.cl_idcamasala,datos_fichas.df_enviarresultadosemail"
              + " ,datos_fichas.df_iddiagnostico,datos_fichas.df_idconvenio,datos_fichas.df_idprioridadatencion,datos_fichas.df_observacion"
              + " from datos_fichas" + " INNER JOIN datos_pacientes ON"
              + "    datos_pacientes.dp_idpaciente = datos_fichas.df_idpaciente" + " INNER JOIN ldv_tiposdocumentos ON"
              + "    ldv_tiposdocumentos.ldvtd_idtipodocumento = datos_pacientes.dp_idtipodocumento"
              + " INNER JOIN cfg_tipoatencion ON"
              + "   cfg_tipoatencion.cta_idtipoatencion = datos_fichas.df_idtipoatencion"
              + " INNER JOIN cfg_procedencias ON"
              + "  cfg_procedencias.cp_idprocedencia = datos_fichas.df_idprocedencia"
              + " INNER JOIN cfg_localizaciones ON"
              + "  cfg_localizaciones.cl_idlocalizacion = datos_fichas.df_idlocalizacion"
              + " INNER JOIN cfg_servicios ON" + " cfg_servicios.cs_idservicio = cfg_localizaciones.cl_idservicio"
              + " where datos_pacientes.dp_nrodocumento = :rut" + " and datos_fichas.df_fechaorden BETWEEN '"
              + fechaInicio + "' and to_date('" + fechaFin + " 23:59:59', 'dd/mm/yy HH24:mi:ss')"
              + " order by datos_fichas.df_fechaorden asc");
      query.setParameter("rut", rut);
      List<Object[]> lista = query.list();
      sesion.getTransaction().commit();
      sesion.close();
      return lista;
    } catch (Exception e) {
      throw e;
    }

  }

  public List<Object[]> selectOrdenProcedenciaFechas(int procedencia, String fechaInicio, String fechaFin) {

    Session sesion = HibernateUtil.getSessionFactory().openSession();

    try {

      sesion.beginTransaction();
      Query query = sesion.createSQLQuery(
          "select datos_fichas.df_fechaorden,datos_fichas.df_norden,CONCAT(CONCAT(datos_pacientes.dp_nombres,' '),datos_pacientes.dp_primerapellido),ldv_tiposdocumentos.ldvtd_descripcion,datos_pacientes.dp_nrodocumento, cfg_tipoatencion.cta_descripcion,cfg_procedencias.cp_descripcion,cfg_servicios.cs_descripcion "
              + " ,datos_fichas.df_idmedico,cfg_localizaciones.cl_idservicio,cfg_localizaciones.cl_idsalaservicio,cfg_localizaciones.cl_idcamasala,datos_fichas.df_enviarresultadosemail"
              + " ,datos_fichas.df_iddiagnostico,datos_fichas.df_idconvenio,datos_fichas.df_idprioridadatencion,datos_fichas.df_observacion"
              + " from datos_fichas" + " INNER JOIN datos_pacientes ON"
              + "    datos_pacientes.dp_idpaciente = datos_fichas.df_idpaciente" + " INNER JOIN ldv_tiposdocumentos ON"
              + "    ldv_tiposdocumentos.ldvtd_idtipodocumento = datos_pacientes.dp_idtipodocumento"
              + " INNER JOIN cfg_tipoatencion ON"
              + "   cfg_tipoatencion.cta_idtipoatencion = datos_fichas.df_idtipoatencion"
              + " INNER JOIN cfg_procedencias ON"
              + "  cfg_procedencias.cp_idprocedencia = datos_fichas.df_idprocedencia"
              + " INNER JOIN cfg_localizaciones ON"
              + "  cfg_localizaciones.cl_idlocalizacion = datos_fichas.df_idlocalizacion"
              + " INNER JOIN cfg_servicios ON" + " cfg_servicios.cs_idservicio = cfg_localizaciones.cl_idservicio"
              + " where datos_fichas.df_idprocedencia = :procedencia" + " and datos_fichas.df_fechaorden BETWEEN '"
              + fechaInicio + "' and to_date('" + fechaFin + " 23:59:59', 'dd/mm/yy HH24:mi:ss')"
              + " order by datos_fichas.df_fechaorden asc");
      query.setParameter("procedencia", procedencia);
      List<Object[]> lista = query.list();
      sesion.getTransaction().commit();
      sesion.close();
      return lista;
    } catch (Exception e) {
      throw e;
    }

  }

  public List<Object[]> selectOrdenFechas(String fechaInicio, String fechaFin) {

    Session sesion = HibernateUtil.getSessionFactory().openSession();

    try {
      sesion.beginTransaction();
      Query query = sesion.createSQLQuery(
          "select datos_fichas.df_fechaorden,datos_fichas.df_norden,CONCAT(CONCAT(datos_pacientes.dp_nombres,' '),datos_pacientes.dp_primerapellido),ldv_tiposdocumentos.ldvtd_descripcion,datos_pacientes.dp_nrodocumento, cfg_tipoatencion.cta_descripcion,cfg_procedencias.cp_descripcion,cfg_servicios.cs_descripcion "
              + " ,datos_fichas.df_idmedico,cfg_localizaciones.cl_idservicio,cfg_localizaciones.cl_idsalaservicio,cfg_localizaciones.cl_idcamasala,datos_fichas.df_enviarresultadosemail"
              + " ,datos_fichas.df_iddiagnostico,datos_fichas.df_idconvenio,datos_fichas.df_idprioridadatencion,datos_fichas.df_observacion"
              + " from datos_fichas" + " INNER JOIN datos_pacientes ON"
              + "    datos_pacientes.dp_idpaciente = datos_fichas.df_idpaciente" + " INNER JOIN ldv_tiposdocumentos ON"
              + "    ldv_tiposdocumentos.ldvtd_idtipodocumento = datos_pacientes.dp_idtipodocumento"
              + " INNER JOIN cfg_tipoatencion ON"
              + "   cfg_tipoatencion.cta_idtipoatencion = datos_fichas.df_idtipoatencion"
              + " INNER JOIN cfg_procedencias ON"
              + "  cfg_procedencias.cp_idprocedencia = datos_fichas.df_idprocedencia"
              + " INNER JOIN cfg_localizaciones ON"
              + "  cfg_localizaciones.cl_idlocalizacion = datos_fichas.df_idlocalizacion"
              + " INNER JOIN cfg_servicios ON" + " cfg_servicios.cs_idservicio = cfg_localizaciones.cl_idservicio"
              + " where datos_fichas.df_fechaorden BETWEEN '" + fechaInicio + "' and to_date('" + fechaFin
              + " 23:59:59', 'dd/mm/yyyy HH24:mi:ss')" + " order by datos_fichas.df_fechaorden asc");
      List<Object[]> lista = query.list();
      sesion.getTransaction().commit();
      sesion.close();
      return lista;
    } catch (Exception e) {
      throw e;
    }

  }

  /* TOMA DE MUESTRAS */
  public List<Object[]> PrimeraTablaTomaMuestras(String fechaInicio, String fechaFin) throws Exception {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    // to_char(df_fechaorden, 'hh:mm DD/MM')
    try {
      sesion.beginTransaction();
      Query query = sesion.createSQLQuery("select df_norden, to_char(df_fechaorden, 'dd/mm/yyyy hh:mi'),"
          + " datos_pacientes.dp_nombres || ' ' || datos_pacientes.dp_primerapellido || ' ' || datos_pacientes.dp_segundoapellido,"
          + " to_char(datos_pacientes.dp_fnacimiento, 'DD/MM/YYYY'),"
          + " cfg_prioridadatencion.cpa_iconoprioridad,cfg_prioridadatencion.cpa_colorprioridad,cfg_procedencias.cp_descripcion,cfg_servicios.cs_descripcion,"
          + " ldv_fichasestadostm.ldvfet_descripcion,datos_fichas.df_observacion,cfg_prioridadatencion.cpa_descripcion,datos_pacientes.dp_idpaciente,"
          + " cfg_tipoatencion.cta_descripcion" + " from datos_fichas"
          + " left join datos_pacientes ON datos_pacientes.dp_idpaciente = datos_fichas.df_idpaciente"
          + " left join cfg_prioridadatencion ON cfg_prioridadatencion.cpa_idprioridadatencion = datos_fichas.df_idprioridadatencion"
          + " left JOIN cfg_localizaciones ON"
          + " cfg_localizaciones.cl_idlocalizacion = datos_fichas.df_idlocalizacion" + " left JOIN cfg_servicios ON"
          + " cfg_servicios.cs_idservicio = cfg_localizaciones.cl_idservicio" + " left JOIN cfg_procedencias ON"
          + " cfg_procedencias.cp_idprocedencia = datos_fichas.df_idprocedencia" + " left JOIN ldv_fichasestadostm ON"
          + " ldv_fichasestadostm.ldvfet_idfichaestadotm = datos_fichas.df_idfichaestadotm"
          + " left JOIN cfg_tipoatencion ON" + "   cfg_tipoatencion.cta_idtipoatencion = datos_fichas.df_idtipoatencion"
          // + " where df_fechaorden BETWEEN '25/01/21' and to_date('25/01/21 23:59:59',
          // 'dd/mm/yy HH24:mi:ss')"
          + " where df_fechaorden BETWEEN :fechaInicio and to_date( :fechaFin, 'dd/mm/yy HH24:mi:ss')"
          // + " AND ldv_fichasestadostm.ldvfet_idfichaestadotm = 1"
          + " order by  cfg_prioridadatencion.cpa_prioridad asc");
      query.setParameter("fechaInicio", fechaInicio);
      query.setParameter("fechaFin", fechaFin);
      // query.setParameter("fechaFin", "to_date('" + fechaFin + "', 'dd/mm/yy
      // HH24:mi:ss')");
      List<Object[]> lista = query.list();
      sesion.getTransaction().commit();
      sesion.close();

      for (Object[] listaa : lista) {

        if (listaa[4] != null) {
          Blob myblob = (Blob) listaa[4];
          byte[] bdata = myblob.getBytes(1, (int) myblob.length());
          byte[] encodeBase64 = Base64.getEncoder().encode(bdata);
          String base64Encoded = new String(encodeBase64, "UTF-8");
          listaa[4] = base64Encoded;
        }

      }

      return lista;
    } catch (Exception e) {
      throw e;
    }

  }

  // Tabla de órdenes Toma de muestras
  public List<OrdenesTMDTO> getOrdenesTomaMuestras(String fechaInicio, String fechaFin) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sqlQueryOrdenes = "select df_norden NORDEN, to_char(df_fechaorden, 'dd/mm/yyyy hh24:mi:ss') FECHAORDEN,"
          + " case when datos_pacientes.dp_esvip = 'S' and datos_pacientes.dp_nombreencriptado is not null"
          + " then datos_pacientes.dp_nombreencriptado" + " else"
          + " case when datos_pacientes.dp_nombresocial is not null" + " then datos_pacientes.dp_nombresocial || '*' "
          + " else datos_pacientes.dp_nombres || ' ' || datos_pacientes.dp_primerapellido || ' ' || datos_pacientes.dp_segundoapellido"
          + " end" + " end NOMBRES," + " to_char(datos_pacientes.dp_fnacimiento, 'DD/MM/YYYY') FECHANAC, "// + "
          // cfg_prioridadatencion.cpa_iconoprioridad,"
          + " cfg_prioridadatencion.cpa_colorprioridad COLORPRIO,"
          + " cfg_procedencias.cp_descripcion PROCEDENCIA, cfg_servicios.cs_descripcion SERVICIO, cfg_servicios.cs_idservicio IDSERVICIO,"
          /*
           * + " case when cfg_procedencias.cp_idprocedencia > 0" +
           * " then cfg_procedencias.cp_descripcion" + " else '' end procedencia," +
           * " case when cfg_servicios.cs_idservicio > 0" +
           * " then cfg_servicios.cs_descripcion" + " else '' end servicio,"
           */
          + " ldv_fichasestadostm.ldvfet_descripcion ESTADO, datos_fichas.df_observacion OBSERVACION, cfg_prioridadatencion.cpa_descripcion PRIORIDADATENCION,"
          + " cfg_prioridadatencion.cpa_prioridad PRIORIDAD, datos_pacientes.dp_idpaciente IDPACIENTE, cfg_tipoatencion.cta_descripcion TIPOATENCION,"
          + " datos_fichas.df_tmclick TMCLICK," + " (" + " SELECT"
          + " CAST(MAX(CASE WHEN ct.CT_TIENEANTECEDENTES = 'S' THEN 'S' ELSE 'N' END)AS VARCHAR(1) )"
          + " FROM datos_fichasexamenestest dfet" + " INNER JOIN CFG_TEST ct ON dfet.DFET_IDTEST = ct.CT_IDTEST"
          + " WHERE dfet.DFET_NORDEN = datos_fichas.df_norden" + " GROUP BY DFET_NORDEN) AS ESTADOANTECEDENTETM"
          + " from datos_fichas"
          + " left join datos_pacientes ON datos_pacientes.dp_idpaciente = datos_fichas.df_idpaciente"
          + " left join cfg_prioridadatencion ON cfg_prioridadatencion.cpa_idprioridadatencion = datos_fichas.df_idprioridadatencion"
          + " left JOIN cfg_localizaciones ON cfg_localizaciones.cl_idlocalizacion = datos_fichas.df_idlocalizacion"
          + " left JOIN cfg_servicios ON cfg_servicios.cs_idservicio = DF_IDSERVICIO"
          + " left JOIN cfg_procedencias ON cfg_procedencias.cp_idprocedencia = datos_fichas.df_idprocedencia"
          + " left JOIN ldv_fichasestadostm ON ldv_fichasestadostm.ldvfet_idfichaestadotm = datos_fichas.df_idfichaestadotm"
          + " left JOIN cfg_tipoatencion ON cfg_tipoatencion.cta_idtipoatencion = datos_fichas.df_idtipoatencion"
          + " where df_fechaorden >=  to_date(:fechaInicio, 'dd/mm/yyyy hh24:mi:ss') and df_fechaorden < to_date(:fechaFin, 'dd/mm/yyyy') +1"
          + " and exists (select dfe.dfe_idexamen from datos_fichasexamenes dfe"
          + " where dfe.dfe_examenanulado = 'N' and dfe.dfe_norden = df_norden)"
          //
          + " order by case when cfg_prioridadatencion.cpa_idprioridadatencion = 12" + " then 1 else 2" + " end, "
          + "df_fechaorden asc";
      Query query = sesion.createSQLQuery(sqlQueryOrdenes)
          .setResultTransformer(Transformers.aliasToBean(OrdenesTMDTO.class));
      query.setParameter("fechaInicio", fechaInicio);
      query.setParameter("fechaFin", fechaFin);
      List<OrdenesTMDTO> lista = (List<OrdenesTMDTO>) query.list();
      return lista;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public void updateTMClick(int nOrden) throws BiosLISDAOException {
    // public void updateTMClick(int nOrden, String ipEquipo) {
    Session sesion = null;
    try {
      Boolean validadorTM = this.validarTMClick(nOrden);
      String tmClick = null;
      if (validadorTM) {
        tmClick = "N";
        sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        Query query = sesion
            .createSQLQuery("update datos_fichas " + "set df_tmclick = :tmClick " + "where df_norden = :nOrden ");
        query.setParameter("nOrden", nOrden);
        query.setParameter("tmClick", tmClick);
        query.executeUpdate();
        sesion.getTransaction().commit();
        sesion.close();

      } else {
        tmClick = "S";
        sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        Query query = sesion
            .createSQLQuery("update datos_fichas " + "set df_tmclick = :tmClick " + "where df_norden = :nOrden ");
        query.setParameter("nOrden", nOrden);
        query.setParameter("tmClick", tmClick);
        query.executeUpdate();
        sesion.getTransaction().commit();
        sesion.close();

      }

      LogEventosfichasDAO lefDAO = new LogEventosfichasDAO();
      LogEventosfichas lef = new LogEventosfichas();
      Date date = new Date(System.currentTimeMillis());
      // SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
      // Date fecha = new Date();
      // lef.setLefIpestacion(ipEquipo);
      lef.setDatosFichas(nOrden);
      lef.setCfgEventos(2);
      lef.setLefFechaorden(date);
      lef.setLefNombretabla("DATOS_FICHAS");
      lef.setLefNombrecampo("DF_TMCLICK");
      lef.setLefFecharegistro(date);
      lef.setLefIdusuario(1);
      // lefDAO.insertLogEventosFichas(lef);

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }

  }

  // método creado por Marco Caracciolo 25/04/2022
  public void actualizarTmClick(int nOrden, long idUsuario) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      DatosUsuariosDAO duDAO = new DatosUsuariosDAO();
      DatosUsuarios du = duDAO.getUsuarioById(idUsuario);
      DatosFichas df = this.getOrdenxID(nOrden);
      String strIdUsr = Long.toString(du.getDuIdusuario());
      String nombreTC = strIdUsr + "/" + du.getDuNombres() + " " + du.getDuPrimerapellido();
      // esto es para evitar que el largo del tmClick sea mayor a 25
      if (nombreTC.length() > 25) {
        // si el usuario tiene 2 nombres o bien hay un espacio entre ellos se corta
        String[] partNombre = du.getDuNombres().split(" ");
        nombreTC = strIdUsr + "/" + partNombre[0].toString() + " " + du.getDuPrimerapellido();
        // si sigue siendo más largo que 25, simplemente se corta con substring de
        // manera que quede de largo 25
        if (nombreTC.length() > 25) {
          nombreTC = nombreTC.substring(0, 25);
        }
      }
      df.setDfTmclick(nombreTC);
      this.updateDatosFichas(df);
      sesion.getTransaction().commit();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public Boolean validarTMClick(int nOrden) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      logger.debug("nOrden: " + nOrden);

      Boolean validadorFinal;
      sesion.beginTransaction();
      Query query = sesion.createQuery("select med.dfTmclick from com.grupobios.bioslis.entity.DatosFichas med "
          + " where med.dfNorden = :nOrden" + " order by med.dfNorden desc");
      query.setParameter("nOrden", nOrden);

      String validador = (String) query.uniqueResult();

      sesion.getTransaction().commit();
      sesion.close();
      logger.debug("validador: " + validador);
      if ("S".equals(validador)) {
        validadorFinal = true;
      } else {
        validadorFinal = false;
      }

      return validadorFinal;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  // método creado por Marco Caracciolo 25/04/2022
  public TMClickDTO getTmClick(Integer nOrden) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sql = "select df.df_norden NORDEN, df.df_tmclick OCUPADO from datos_fichas df "
          + "where df.df_norden = :nOrden";
      Query query = sesion.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(TMClickDTO.class));
      // query.setParameter("nOrden", nOrden);
      query.setInteger("nOrden", nOrden);
      TMClickDTO tmClick = (TMClickDTO) query.uniqueResult();
      return tmClick;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  /*
   * Método creado por Marco Caracciolo 25/04/2022 Este busca las órdenes activas
   * que no sean la orden especificada en el parámetro Sirve para ver si hay otra
   * orden que el usuario tenga tomada anteriormente
   */
  public List<TMClickDTO> getTmClickNotOrden(long nOrden) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sql = "select df.df_norden NORDEN, df.df_tmclick OCUPADO from datos_fichas df "
          + "where df.df_norden != :nOrden and df.df_tmclick != 'N'";
      Query query = sesion.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(TMClickDTO.class));
      query.setParameter("nOrden", nOrden);
      List<TMClickDTO> listmclick = query.list();
      sesion.close();
      return listmclick;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<TMClickDTO> getTmClickOrdenesOcupadas() {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String sql = "select df.df_norden NORDEN, df.df_tmclick OCUPADO from datos_fichas df "
        + "where df.df_tmclick != 'N'";
    Query query = sesion.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(TMClickDTO.class));
    List<TMClickDTO> listmclick = query.list();
    sesion.close();
    return listmclick;
  }

  public void volverANormlidad() {

    try {
      Session sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      Query query = sesion.createSQLQuery("UPDATE DATOS_FICHAS SET DF_TMCLICK = 'N'");
      query.executeUpdate();
      sesion.getTransaction().commit();
      sesion.close();
    } catch (Exception e) {
      throw e;
    }

  }

  public void volverANormlidadOrden(int nOrden) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      Query query = sesion.createSQLQuery("UPDATE DATOS_FICHAS SET DF_TMCLICK = 'N'" + "where DF_NORDEN = :nOrden");
      query.setParameter("nOrden", nOrden);
      query.executeUpdate();
      sesion.getTransaction().commit();
      sesion.close();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }

  }

  public void volverANormlidadOrdenesUsuario(long idUsr) throws BiosLISDAOException {
    List<TMClickDTO> listTMClick = this.getTmClickOrdenesOcupadas();
    for (TMClickDTO tc : listTMClick) {
      String[] parts = tc.getOCUPADO().split("/");
      Long idUsrTc = Long.parseLong(parts[0]);
      if (Objects.equals(idUsrTc, idUsr)) {
        this.volverANormlidadOrden(tc.getNORDEN().intValue());
      }
    }
  }

  public void updateEstadoUrgente(int nOrden, Long idUsuario) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      Query query = sesion
          .createSQLQuery("update datos_fichas set df_idprioridadatencion = 12 where df_norden = :nOrden ");
      query.setParameter("nOrden", nOrden);
      query.executeUpdate();

      sesion.getTransaction().commit();
      sesion.close();
      DatosFichas df = this.getOrdenxID(nOrden);
      LogEventosfichas lef = new LogEventosfichas();
      lef.setDatosFichas(nOrden);
      lef.setLefFechaorden(df.getDfFechaorden());
      lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
      lef.setCfgEventos(2);
      lef.setLefValornuevo("URGENTE");
      lef.setLefNombrecampo("DF_IDPRIORIDADATENCION");
      lef.setLefNombretabla("DATOS_FICHAS");
      lef.setLefIdpaciente(df.getDatosPacientes());
      lef.setLefIdusuario(idUsuario.intValue());
      LogEventosfichasDAO lefDAO = new LogEventosfichasDAO();
      lefDAO.insertLogEventosFichas(lef);

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }

  }

  public List<Object[]> modalDatosOrden(int nOrden) {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      sesion.beginTransaction();
      Query query = sesion.createSQLQuery("select cfg_examenes.ce_descripcion, cfg_examenes.ce_codigoexamen,"
          + " cfg_centrosdesalud.ccds_descripcion, cfg_laboratorios.clab_descripcion, cfg_secciones.csec_descripcion,"
          + " cfg_muestras.cmue_descripcion, cfg_envases.cenv_descripcion, cfg_examenes.ce_compartemuestra,"
          + " datos_fichas.df_observacion, cfg_diagnosticos.cd_descripcion, cd.cderiv_descripcion, cd.cderiv_idderivador,"
          + " cfg_examenes.ce_idexamen, dfet.dfet_idmuestra, cfg_centrosdesalud.ccds_idcentrodesalud id_centro,"
          + " cfg_laboratorios.clab_idlaboratorio idlab, cfg_secciones.csec_idseccion idseccion"
          + " from datos_fichasexamenestest dfet "
          + " INNER JOIN datos_fichasexamenes ON datos_fichasexamenes.dfe_norden = dfet.dfet_norden"
          + " and datos_fichasexamenes.dfe_idexamen = dfet.dfet_idexamen"
          + " INNER JOIN cfg_examenes ON dfet.dfet_idexamen = cfg_examenes.ce_idexamen"
          + " INNER JOIN cfg_test ON dfet.dfet_idtest = cfg_test.CT_IDTEST"
          + " INNER JOIN cfg_secciones ON cfg_secciones.csec_idseccion = dfet.dfet_idseccion"
          + " INNER JOIN cfg_laboratorios ON cfg_secciones.csec_idlaboratorio = cfg_laboratorios.clab_idlaboratorio"
          + " INNER JOIN cfg_centrosdesalud ON cfg_centrosdesalud.ccds_idcentrodesalud = cfg_laboratorios.clab_idlaboratorio"
          + " INNER JOIN datos_fichas ON datos_fichas.df_norden =dfet.dfet_norden"
          + " INNER JOIN cfg_muestras ON cfg_muestras.cmue_idtipomuestra = cfg_test.ct_idtipomuestra"
          + " INNER JOIN cfg_envases ON cfg_envases.cenv_idenvase = dfet.dfet_idenvase"
          + " INNER JOIN cfg_diagnosticos ON cfg_diagnosticos.cd_iddiagnostico = datos_fichas.df_iddiagnostico"
          + " INNER JOIN cfg_derivadores cd ON cd.cderiv_idderivador = datos_fichasexamenes.dfe_idderivador"
          + " where dfet.dfet_norden = :nOrden");
      query.setParameter("nOrden", nOrden);
      List<Object[]> lista = query.list();
      sesion.getTransaction().commit();
      sesion.close();
      return lista;
    } catch (Exception e) {
      throw e;
    }

  }

  public List<DatosFichas> getOrdenes() {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    Query query = sesion.createQuery("select df " + "from com.grupobios.bioslis.entity.DatosFichas df");
    List<DatosFichas> listaOrdenes = query.list();
    sesion.getTransaction().commit();
    sesion.close();
    return listaOrdenes;
  }

  // new metodo Marco C. 20-08-21

  public void updateEstadoPaciente(long nOrden, int estado, Long idUsuario)
      throws IllegalArgumentException, IllegalAccessException, BiosLISDAOException {
    DatosFichas df = this.getDatosOrden(nOrden);

    DatosFichasmuestrasDAO datos = new DatosFichasmuestrasDAO();
    List<DatosFichasmuestras> muestrasOrden = datos.obtenerListaMuestrasPorOrden(nOrden);
    // **** eventos fichas ***************

    LogEventosfichas lef = new LogEventosfichas();
    logger.debug(df.getLdvFichasestadostm());

    // if (df.getLdvFichasestadostm() != 1) { COMENTADO YA QUE NO REGISTRABA CUANDO
    // PASABA DE ATENDIDO A OTRO ESTADO
    lef.setDatosFichas(df.getDfNorden());
    lef.setLefFechaorden(df.getDfFechaorden());
    lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
    lef.setCfgEventos(2);

    if (df.getLdvFichasestadostm() == 1) {
      lef.setLefValoranterior("ATENDIDO");
      logger.debug("entro a updateestado 1");
    } else if (df.getLdvFichasestadostm() == 2) {
      lef.setLefValoranterior("ESPERA");

    } else {
      lef.setLefValoranterior("BLOQUEADO");
    }

    if (estado == 1) {
      lef.setLefValornuevo("ATENDIDO");
    } else if (estado == 2) {
      lef.setLefValornuevo("ESPERA");
      logger.debug("entro a updateestado 2");
    } else {
      lef.setLefValornuevo("BLOQUEADO");
    }
    lef.setLefNombrecampo("DF_IDFICHAESTADOTM");
    lef.setLefNombretabla("DATOS_FICHAS");
    lef.setLefIdpaciente(df.getDatosPacientes());
    lef.setLefIdusuario(idUsuario.intValue());
    LogEventosfichasDAO lefDAO = new LogEventosfichasDAO();
    logger.debug(lef.getLefValoranterior());
    logger.debug(lef.getLefValornuevo());
    if (!lef.getLefValoranterior().equals(lef.getLefValornuevo())) {

      lefDAO.insertLogEventosFichas(lef);
    }
    // }
    // }
    /*
     * }else { // aqui se agrega datos a logeventosfichas ************ >>>>>>>
     * v04_tomcat_ca
     * 
     * if (muestrasOrden.size() > 0) { for (DatosFichasmuestras fila :
     * muestrasOrden) { // aqui se agrega datos a logeventosfichas ************
     * lef.setLefCodigobarra(fila.getDfmCodigobarra()); lef.setLefIdmuestra((int)
     * fila.getDfmIdmuestra()); lef.setDatosFichas(df.getDfNorden());
     * lef.setLefFechaorden(df.getDfFechaorden());
     * lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
     * lef.setCfgEventos(2); if (df.getLdvFichasestadostm() == 1) {
     * lef.setLefValoranterior("ATENDIDO"); } else if (df.getLdvFichasestadostm() ==
     * 2) { lef.setLefValoranterior("ESPERA"); } else {
     * lef.setLefValoranterior("BLOQUEADO"); }
     * 
     * if (estado == 1) { lef.setLefValornuevo("ATENDIDO"); } else if (estado == 2)
     * { lef.setLefValornuevo("ESPERA"); } else { lef.setLefValornuevo("BLOQUEADO");
     * } lef.setLefNombrecampo("DF_IDFICHAESTADOTM");
     * lef.setLefNombretabla("DATOS_FICHAS");
     * lef.setLefIdpaciente(df.getDatosPacientes());
     * lef.setLefIdusuario(idUsuario.intValue()); LogEventosfichasDAO lefDAO = new
     * LogEventosfichasDAO(); if
     * (!lef.getLefValoranterior().equals(lef.getLefValornuevo())) {
     * lefDAO.insertLogEventosFichas(lef); } }
     * 
     * } else { // aqui se agrega datos a logeventosfichas ************
     * 
     * lef.setDatosFichas(df.getDfNorden());
     * lef.setLefFechaorden(df.getDfFechaorden());
     * lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
     * lef.setCfgEventos(2); if (df.getLdvFichasestadostm() == 1) {
     * lef.setLefValoranterior("ATENDIDO"); } else if (df.getLdvFichasestadostm() ==
     * 2) { lef.setLefValoranterior("ESPERA"); } else {
     * lef.setLefValoranterior("BLOQUEADO"); }
     * 
     * if (estado == 1) { lef.setLefValornuevo("ATENDIDO"); } else if (estado == 2)
     * { lef.setLefValornuevo("ESPERA"); } else { lef.setLefValornuevo("BLOQUEADO");
     * } lef.setLefNombrecampo("DF_IDFICHAESTADOTM");
     * lef.setLefNombretabla("DATOS_FICHAS");
     * lef.setLefIdpaciente(df.getDatosPacientes());
     * lef.setLefIdusuario(idUsuario.intValue()); LogEventosfichasDAO lefDAO = new
     * LogEventosfichasDAO(); if
     * (!lef.getLefValoranterior().equals(lef.getLefValornuevo())) {
     * lefDAO.insertLogEventosFichas(lef); } }
     */
    // *********************************************************

    df.setLdvFichasestadostm(estado);
    this.updateDatosFichas(df);
//se elimina codigo anterior que enviaba a logebentos antigui

  }

  public void updateDatosFichas(DatosFichas df) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      sesion.update(df);
      sesion.getTransaction().commit();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public Object[] datosAnexosPaciente(int idPaciente, long nOrden) {
    try {
      Session sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createSQLQuery("SELECT df.df_observacion, ta.cta_descripcion, cp.cp_descripcion "
          + "FROM datos_fichas df " + "left join BIOSLIS.datos_pacientes dp on dp.dp_idpaciente = df.df_idpaciente "
          + "left JOIN cfg_procedencias cp ON cp.cp_idprocedencia = df.df_idprocedencia "
          + "left JOIN cfg_tipoatencion ta ON ta.cta_idtipoatencion = df.df_idtipoatencion "
          + "where dp.dp_idpaciente = :idPaciente and df.df_norden = :nOrden");
      query.setParameter("nOrden", nOrden);
      query.setParameter("idPaciente", idPaciente);
      Object[] daPac = (Object[]) query.setMaxResults(1).uniqueResult();
      sesion.close();
      return daPac;
    } catch (Exception e) {
      throw e;
    }
  }

  public OrdenesTMDTO getUltimoRegistro(long nOrden) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sql = "select df.df_norden norden, " + "est.ldvfet_descripcion estado, "
          + "case when dp.dp_nombresocial is not null " + "then dp.dp_nombresocial "
          + "else to_char(dp.dp_nombres || ' ' || dp.dp_primerapellido || ' ' || dp.dp_segundoapellido) "
          + "end nombres " + "from BIOSLIS.datos_fichas df "
          + "join BIOSLIS.datos_pacientes dp on dp.dp_idpaciente = df.df_idpaciente "
          + "join ldv_fichasestadostm est ON est.ldvfet_idfichaestadotm = df.df_idfichaestadotm "
          + "where df.df_norden = :nOrden";
      Query query = sesion.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(OrdenesTMDTO.class));
      query.setParameter("nOrden", nOrden);
      OrdenesTMDTO orden = (OrdenesTMDTO) query.uniqueResult();
      return orden;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  // new metodo MC 20/05/2022
  public ExamenPacienteDTO getExamenPacienteCurva(long nOrden, long idMuestra) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sql = "SELECT DISTINCT CE.CE_IDEXAMEN, ce.CE_ABREVIADO, dp.DP_IDPACIENTE, "
          + "CASE WHEN DP.dp_esvip = 'S' and DP.dp_nombreencriptado is not null " + "THEN DP.dp_nombreencriptado "
          + "ELSE " + "CASE when DP.dp_nombresocial is not null " + "THEN DP.dp_nombresocial "
          + "ELSE to_char(DP.dp_nombres || ' ' || DP.dp_primerapellido || ' ' || DP.dp_segundoapellido) " + "END "
          + "END NOMBRES " + "FROM DATOS_FICHAS df "
          + "JOIN DATOS_FICHASEXAMENES dfe ON (dfe.DFE_NORDEN = df.DF_NORDEN) "
          + "JOIN DATOS_FICHASEXAMENESTEST DFET ON dfet.DFET_NORDEN = dfe.DFE_NORDEN "
          + "JOIN CFG_EXAMENES ce ON (ce.CE_IDEXAMEN = dfe.DFE_IDEXAMEN) "
          + "JOIN DATOS_PACIENTES dp ON( df.DF_IDPACIENTE = dp.DP_IDPACIENTE) "
          + "WHERE DF_NORDEN = :nOrden and dfet.dfet_idmuestra = :idMuestra and ce.ce_escurvatolerancia = 'S'";
      Query query = sesion.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(ExamenPacienteDTO.class));
      query.setParameter("nOrden", nOrden);
      query.setParameter("idMuestra", idMuestra);
      ExamenPacienteDTO curvaDTO = (ExamenPacienteDTO) query.uniqueResult();
      return curvaDTO;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  /**
   * @return the capturaResultadosDAO
   */
  public CapturaResultadosDAO getCapturaResultadosDAO() {
    return capturaResultadosDAO;
  }

  /**
   * @param capturaResultadosDAO the capturaResultadosDAO to set
   */
  public void setCapturaResultadosDAO(CapturaResultadosDAO capturaResultadosDAO) {
    this.capturaResultadosDAO = capturaResultadosDAO;
  }

  /**
   * @return the datosFichasExamenesDAO
   */
  public DatosFichasExamenesDAO getDatosFichasExamenesDAO() {
    return datosFichasExamenesDAO;
  }

  /**
   * @param datosFichasExamenesDAO the datosFichasExamenesDAO to set
   */
  public void setDatosFichasExamenesDAO(DatosFichasExamenesDAO datosFichasExamenesDAO) {
    this.datosFichasExamenesDAO = datosFichasExamenesDAO;
  }

  /**
   * @return the datosFichasExamenesTestDAO
   */
  public DatosFichasExamenesTestDAO getDatosFichasExamenesTestDAO() {
    return datosFichasExamenesTestDAO;
  }

  /**
   * @param datosFichasExamenesTestDAO the datosFichasExamenesTestDAO to set
   */
  public void setDatosFichasExamenesTestDAO(DatosFichasExamenesTestDAO datosFichasExamenesTestDAO) {
    this.datosFichasExamenesTestDAO = datosFichasExamenesTestDAO;
  }

  private LogEventosfichas creaLogEventosfichas(String nombreTabla, String nombreCampo, String ipEquipo, int nOrden,
      Date date, String examene, int idPaciente) {

    LogEventosfichas lefexamenes = new LogEventosfichas();
    lefexamenes.setLefIpestacion(ipEquipo);
    lefexamenes.setDatosFichas(nOrden);
    lefexamenes.setCfgEventos(1);
    lefexamenes.setLefFechaorden(date);
    lefexamenes.setLefIdpaciente(idPaciente);
    lefexamenes.setLefNombretabla(nombreTabla);
    lefexamenes.setLefNombrecampo(nombreCampo);
//    if (examene != null && examene.) {
//      lefexamenes.setLefIdexamen(Integer.parseInt(examene));
//    }
    lefexamenes.setLefFecharegistro(date);
    lefexamenes.setLefIdusuario(1);
    return lefexamenes;
  }

  public List<DatosFichas> selectOrdenxRangoFechas(LocalDate fechaInicio, LocalDate fechaFin, Integer idPac)
      throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();

    try {
      Query query = sesion.createQuery("select datos_fichas from DatosFichas datos_fichas "
          + " where datos_fichas.dfFechaorden BETWEEN :fechaInicio and :fechaFin"
          + " and datos_fichas.datosPacientes= :idPac");

      query.setInteger("idPac", idPac);
      query.setDate("fechaInicio", BiosLisCalendarService.getInstance().toDate(fechaInicio));
      query.setDate("fechaFin", BiosLisCalendarService.getInstance().toDate(fechaFin));

      logger.debug("\n{} {} {} \n", idPac, fechaInicio, fechaFin);
      logger.debug("\n{} {} {} \n", idPac, BiosLisCalendarService.getInstance().toDate(fechaInicio),
          BiosLisCalendarService.getInstance().toDate(fechaFin));

      List<DatosFichas> lista = query.list();
      return lista;
    } catch (HibernateException he) {
      logger.error(he.getLocalizedMessage());
      throw new BiosLISDAOException("No se pudo buscar ordenes");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

  }

  // DatosFichas med, Integer[] examenes, String ipEquipo, Long idUsuario

  public void insertOrden(DatosFichas med, CfgExamenesDTO[] lstExamenesDto, String ipEquipo, Long idUsuario)
      throws BiosLISDAOException, BiosLISDAONotFoundException {
    BiosLisLogger bl = new BiosLisLogger();
    logger.debug("Se inserta registro : {}", med.toString());

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      med.setDfFechaorden(BiosLisCalendarService.getInstance().getTS(sesion));
      med.setCfgInstitucionesdesalud(1);
      med.setLdvFichasestadostm(Constante.ESTADOFICHAESPERA);
      med.setDfTmclick("N");
      sesion.beginTransaction();
      sesion.save(med);
      LogEventosfichasDAO lefDAO = new LogEventosfichasDAO();
      LogEventosfichas lef = new LogEventosfichas();

      int nOrden = med.getDfNorden();

      lef.setCfgEventos(1);
      lef.setDatosFichas(med.getDfNorden());
      lef.setLefFechaorden(med.getDfFechaorden());
      lef.setLefIdpaciente(med.getDatosPacientes());
      lef.setLefNombretabla("DATOS_FICHAS");
      lef.setLefFecharegistro(med.getDfFechaorden());
      lef.setLefIdusuario(idUsuario.intValue());

      lefDAO.insertLogEventosFichas(lef, sesion);

      for (CfgExamenesDTO examen : lstExamenesDto) {
        LogEventosfichas lef1 = new LogEventosfichas();

        datosFichasExamenesDAO.insertDatosFichasExamenes(nOrden, examen, idUsuario, sesion);
        lef1.setCfgEventos(1);
        lef1.setLefIdpaciente(med.getDatosPacientes());
        lef1.setLefNombretabla("DATOS_FICHASEXAMENES");
        lef1.setLefIdexamen((int) examen.getCeIdexamen());
        lef1.setDatosFichas(med.getDfNorden());
        lef1.setLefFechaorden(med.getDfFechaorden());
        lef1.setLefFecharegistro(med.getDfFechaorden());
        lef1.setLefIdusuario(idUsuario.intValue());
        lefDAO.insertLogEventosFichas(lef1, sesion);

        if (examen.getDerivador() != null && examen.getDerivador() != 0) {
          CfgDerivadoresDAO cfgDerivadoresDAO = new CfgDerivadoresDAO();
          CfgDerivadores derivador = cfgDerivadoresDAO.getDerivadorById(examen.getDerivador());
          LogEventosfichas lef2 = new LogEventosfichas();
          lef2.setCfgEventos(2);
          lef2.setLefIdpaciente(med.getDatosPacientes());
          lef2.setLefNombrecampo("DFE_IDDERIVADOR");
          lef2.setLefValornuevo(derivador.getCderivDescripcion());
          lef2.setLefIdexamen((int) examen.getCeIdexamen());
          lef2.setDatosFichas(med.getDfNorden());
          lef2.setLefFechaorden(med.getDfFechaorden());
          lef2.setLefFecharegistro(med.getDfFechaorden());
          lef2.setLefIdusuario(idUsuario.intValue());
          lefDAO.insertLogEventosFichas(lef2, sesion);
        }

        datosFichasExamenesTestDAO.insertDatosFichasExamenesTest(nOrden, examen, med.getDatosPacientes(), ipEquipo,
            idUsuario, sesion);
      }
      sesion.getTransaction().commit();
    } catch (HibernateException e) {
      logger.error(e.getLocalizedMessage());
      throw new BiosLISDAOException(e.getLocalizedMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

  }

  public void addTestOpcional2Examen(Integer ordenId, Integer examenId, Integer testId, Long idUsuario)
      throws BiosLISDAOException, BiosLISDAONotFoundException {
    // DatosFichas df = this.getDatosOrden(new Long(ordenId));
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    DatosFichas df = null;
    try {
      df = (DatosFichas) sesion.get(DatosFichas.class, ordenId);
      sesion.close();
    } catch (HibernateException e) {
      logger.error(e.getLocalizedMessage());
      throw new BiosLISDAOException(e.getLocalizedMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    datosFichasExamenesTestDAO.insertDatosFichasExamenesTestOpcional(ordenId, examenId, testId, df.getDatosPacientes(),
        idUsuario);
  }

  public void getDatosTestNotificacion(Long nroOrden, Long examenId, Long testId) {
  }

  private String hqlGetDatosTestNotificacion = "Select dfet from DatosFichasExamenesTest dfet";

  public List<CfgTest> getTestOpcionalesExamenesOrden(Long nOrden, Long idExamen) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      Query qry = sesion.createQuery(hqlTestOpcionalesExamenesOrden);
      qry.setLong("nOrden", nOrden);
      qry.setLong("idExamen", idExamen);
      return qry.list();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  private final static String hqlTestOpcionalesExamenesOrden = "SELECT ct  "
      + "from CfgTest ct inner join fetch ct.cfgExamenestests cet inner join fetch cet.id ce "
      + "where ct.ctActivo like 'S'  and ct.ctOpcional like 'S' and ce.cetIdexamen = :idExamen"
      + " and  ct.ctIdtest not in (select dfet.id.dfetIdtest from  DatosFichasexamenestest dfet where dfet.id.dfetNorden = :nOrden and dfet.id.dfetIdexamen = :idExamen) ";

  public List<OrdenesTMDTO> getOrdenesTomaMuestrasByOrden(int nOrden) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      String sqlQueryOrdenes = "select df_norden NORDEN, to_char(df_fechaorden, 'dd/mm/yyyy hh24:mi:ss') FECHAORDEN,"
          + " case when datos_pacientes.dp_esvip = 'S' and datos_pacientes.dp_nombreencriptado is not null"
          + " then datos_pacientes.dp_nombreencriptado" + " else"
          + " case when datos_pacientes.dp_nombresocial is not null" + " then datos_pacientes.dp_nombresocial || '*' "
          + " else datos_pacientes.dp_nombres || ' ' || datos_pacientes.dp_primerapellido || ' ' || datos_pacientes.dp_segundoapellido"
          + " end" + " end NOMBRES," + " to_char(datos_pacientes.dp_fnacimiento, 'DD/MM/YYYY') FECHANAC, "// + "
          // cfg_prioridadatencion.cpa_iconoprioridad,"
          + " cfg_prioridadatencion.cpa_colorprioridad COLORPRIO,"
          // + " cfg_procedencias.cp_descripcion PROCEDENCIA, cfg_servicios.cs_descripcion
          // SERVICIO,"
          + " case when cfg_procedencias.cp_idprocedencia > 0" + " then cfg_procedencias.cp_descripcion"
          + " else '' end procedencia," + " case when cfg_servicios.cs_idservicio > 0"
          + " then cfg_servicios.cs_descripcion" + " else '' end servicio,"
          + " ldv_fichasestadostm.ldvfet_descripcion ESTADO, datos_fichas.df_observacion OBSERVACION, cfg_prioridadatencion.cpa_descripcion PRIORIDADATENCION,"
          + " cfg_prioridadatencion.cpa_prioridad PRIORIDAD, datos_pacientes.dp_idpaciente IDPACIENTE, cfg_tipoatencion.cta_descripcion TIPOATENCION,"
          + " datos_fichas.df_tmclick TMCLICK," + " (" + " SELECT"
          + " CAST(MAX(CASE WHEN ct.CT_TIENEANTECEDENTES = 'S' THEN 'S' ELSE 'N' END)AS VARCHAR(1) )"
          + " FROM datos_fichasexamenestest dfet" + " INNER JOIN CFG_TEST ct ON dfet.DFET_IDTEST = ct.CT_IDTEST"
          + " WHERE dfet.DFET_NORDEN = datos_fichas.df_norden" + " GROUP BY DFET_NORDEN) AS ESTADOANTECEDENTETM"
          + " from datos_fichas"
          + " left join datos_pacientes ON datos_pacientes.dp_idpaciente = datos_fichas.df_idpaciente"
          + " left join cfg_prioridadatencion ON cfg_prioridadatencion.cpa_idprioridadatencion = datos_fichas.df_idprioridadatencion"
          + " left JOIN cfg_localizaciones ON cfg_localizaciones.cl_idlocalizacion = datos_fichas.df_idlocalizacion"
          + " left JOIN cfg_servicios ON cfg_servicios.cs_idservicio = cfg_localizaciones.cl_idservicio"
          + " left JOIN cfg_procedencias ON cfg_procedencias.cp_idprocedencia = datos_fichas.df_idprocedencia"
          + " left JOIN ldv_fichasestadostm ON ldv_fichasestadostm.ldvfet_idfichaestadotm = datos_fichas.df_idfichaestadotm"
          + " left JOIN cfg_tipoatencion ON cfg_tipoatencion.cta_idtipoatencion = datos_fichas.df_idtipoatencion "
          // + " where df_fechaorden BETWEEN :fechaInicio and to_date( :fechaFin,
          // 'dd/mm/yyyy HH24:mi:ss')"
          + " where df_norden = :nOrden"
          // verifica ordenes con examenes anulados
          + " and exists (select dfe.dfe_idexamen from datos_fichasexamenes dfe"
          + " where dfe.dfe_examenanulado = 'N' and dfe.dfe_norden = df_norden)"
          //
          + " order by case when cfg_prioridadatencion.cpa_idprioridadatencion = 12" + " then 1 else 2" + " end, "
          + "df_fechaorden asc";
      Query query = sesion.createSQLQuery(sqlQueryOrdenes)
          .setResultTransformer(Transformers.aliasToBean(OrdenesTMDTO.class));
      query.setParameter("nOrden", nOrden);
      List<OrdenesTMDTO> lista = (List<OrdenesTMDTO>) query.list();
      sesion.close();
      return lista;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

  }

  private static String sqlEtiquetasOrden = " select distinct dp.dp_primerapellido || ' ' ||   NVL(dp.dp_segundoapellido,'')"
      + "   || ' ' ||   dp.dp_nombres   ETIQUETANOMBREPACIENTE_, "
//      + "            df.df_fechaorden , "
      + "            ldvs.ldvs_descripcion ETIQUETASEXO_,"
//      + "            cta.cta_descripcion , "
      + "            dp.dp_nrodocumento ETIQUETARUT_, " + "  TO_CHAR(dp.dp_fnacimiento,'dd-MM-yyyy') ETIQUETAFECHA_,"
      + "            cmue.cmue_descripcionabreviada ETIQUETATIPOMUESTRA_,"
      + "            cenv.cenv_descripcion ETIQUETAENVASE_, " + "            cp.cp_descripcion ETIQUETAPROCEDENCIA_,  "
      + "            cs.cs_descripcion ETIQUETASERVICIO_," + "            dfm.dfm_codigobarra CODIGOBARRAS_, "
      + "            ce.ce_abreviado2 ETIQUETAEXAMENES_  "
//      + "            cmue_esmultimuestra " 
      + "          from DATOS_FICHAS df"
      + "       INNER JOIN DATOS_FICHASEXAMENES dfe ON(dfe.DFE_NORDEN = df.df_norden)"
      + "       INNER JOIN DATOS_FICHASEXAMENESTEST dfet ON(dfet.DFET_IDEXAMEN = dfe.dfe_idexamen and dfet.DFET_NORDEN  = dfe.DFE_NORDEN)"
      + "       INNER JOIN DATOS_FICHASMUESTRAS dfm  ON(dfm.DFM_NORDEN = dfet.DFET_NORDEN AND dfm.DFM_IDMUESTRA  = dfet.DFET_IDMUESTRA)"
      + "       INNER JOIN CFG_EXAMENESTEST cet ON(cet.CET_IDEXAMEN = DFET.DFET_idexamen and cet.CET_IDTEST = DFET.DFET_idtest)"
      + "       INNER JOIN cfg_examenes ce ON(cet.CET_IDEXAMEN  = ce.CE_IDEXAMEN)"
      + "       INNER JOIN cfg_test ct  ON(cet.cet_idtest = ct.Ct_IDtest)"
      + "       INNER JOIN CFG_ENVASES cenv ON(cenv.CENV_IDENVASE = cet.CET_IDENVASE)"
      + "       INNER JOIN cfg_muestras cmue  ON(cmue.cmue_idtipomuestra = ce.CE_IDTIPOMUESTRA)"
      + "       INNER JOIN datos_pacientes dp ON(dp.dp_idpaciente = df.df_idpaciente)"
      + "       INNER JOIN LDV_SEXO ldvs ON(dp.dp_idsexo = ldvs.ldvs_idsexo)"
      + "       INNER JOIN BIOSLIS.CFG_TIPOATENCION cta ON(df.df_idtipoatencion = cta.cta_idtipoatencion)"
      + "       left outer join cfg_servicios cs on(cs.cs_idservicio = df.df_idservicio)"
      + "       left outer join cfg_procedencias cp on(cp.cp_idprocedencia = df.df_idprocedencia)"
      + "       where df.df_norden = :nOrden " + "         and dfet.dfet_norden = :nOrden "
      + "         and (dfm.dfm_estadotm = 'P' or dfm.dfm_estadotm = 'T' ) ";

  public List<BiosLisEtiquetaDTO> getEtiquetasOrden(Long nOrden) throws BiosLISDAOException {
    Session sesion = null;

    try {
      sesion = HibernateUtil.getSessionFactory().openSession();

      Query qry = sesion.createSQLQuery(sqlEtiquetasOrden)
          .setResultTransformer(Transformers.aliasToBean(BiosLisEtiquetaDTO.class));
      qry.setLong("nOrden", nOrden);
      return qry.list();
//      List<BiosLisEtiquetaDTO> 
    } catch (HibernateException he) {
      logger.error("Error: " + he.getLocalizedMessage());
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }

    }

  }

  public List<BiosLisEtiquetaDTO> getEtiquetasOrdenYCodigosBarras(Long nOrden, String[] lstCodigos)
      throws BiosLISDAOException {
    Session sesion = null;

    try {
      sesion = HibernateUtil.getSessionFactory().openSession();

      Query qry = sesion.createSQLQuery(sqlEtiquetasOrden.concat("and dfm_codigobarra  IN (:codigos) order by 10"))
          .setResultTransformer(Transformers.aliasToBean(BiosLisEtiquetaDTO.class));
      qry.setLong("nOrden", nOrden);
      qry.setParameterList("codigos", lstCodigos);

      List<BiosLisEtiquetaDTO> lista = qry.list();
      sesion.close();
      return lista;
    } catch (HibernateException he) {
      logger.error("Error: " + he.getLocalizedMessage());
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }

    }

  }

  public List<DatosFichasexamenestest> getDatosTestCriticos(Long nroOrden) throws BiosLISDAOException {

    return this.datosFichasExamenesTestDAO.getDatosFichasExamenesTestCriticosByOrden(nroOrden);

  }

  public List<DatosFichasexamenestest> getDatosTestCriticos(Long nroOrden, Long examenId) throws BiosLISDAOException {
    return this.datosFichasExamenesTestDAO.getDatosFichasExamenesTestCriticosByOrdenExamen(nroOrden, examenId);
  }

  public String getCorreoxOrden(BigDecimal bigDecimal) throws BiosLISDAOException {
    Session sesion = null;
    String correo = "";
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();

      Query qry = sesion.createSQLQuery("SELECT DP_EMAIL FROM datos_fichas df "
          + "INNER JOIN datos_pacientes dp ON dp.DP_IDPACIENTE = df.DF_IDPACIENTE " + "WHERE df.DF_NORDEN =:nOrden");
      qry.setParameter("nOrden", bigDecimal);

      correo = (String) qry.uniqueResult();
    } catch (HibernateException he) {
      logger.error("Error: " + he.getLocalizedMessage());
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }

    }
    return correo;
  }

//  and dfm_codigobarra  IN ({0}) order by 13 "
}
