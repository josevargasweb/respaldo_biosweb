/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.common.ValidacionMantenedor;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.CfgProcedenciasDTO;
import com.grupobios.bioslis.entity.CfgCentrosdesalud;
import com.grupobios.bioslis.entity.CfgConvenios;
import com.grupobios.bioslis.entity.CfgProcedencias;
import com.grupobios.bioslis.entity.LogCfgtablas;

public class CfgProcedenciasDAO {

  private static Logger logger = LogManager.getLogger(CfgProcedenciasDAO.class);
  private static ValidacionMantenedor validador = new ValidacionMantenedor();

  
 LogCfgTablasDAO logCfgTablasDAO;
 
 

  
  public LogCfgTablasDAO getLogCfgTablasDAO() {
	return logCfgTablasDAO;
}

public void setLogCfgTablasDAO(LogCfgTablasDAO logCfgTablasDAO) {
	this.logCfgTablasDAO = logCfgTablasDAO;
}

public List<CfgProcedencias> getProcedencias() throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgProcedencias> lista = null;
    try {

    	Query query = sesion.createQuery("select list from CfgProcedencias list where list.cpActivo = 'S'"
                + " order by case list.cpIdprocedencia when 0 then 0 else 1 end, list.cpSort asc, list.cpDescripcion asc");
      // Query query = sesion.createQuery("select list from CfgProcedencias list where
      // list.cpActivo ='S' ");
      lista = query.list();

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return lista;
  }

  public List<CfgProcedencias> getProcedenciasActivas() throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgProcedencias> lista = null;
    try {

      Query query = sesion.createQuery("select list from CfgProcedencias list where list.cpActivo ='S' ");
      lista = query.list();

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return lista;
  }

  public List<CfgProcedencias> getProcedenciasHistoricas() throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgProcedencias> lista = null;
    try {

      Query query = sesion.createQuery("select list from CfgProcedencias list");
      lista = query.list();

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return lista;
  }

  public List<CfgProcedencias> getProcedenciasList() throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgProcedencias> lista = null;
    try {
      Query query = sesion.createQuery("from CfgProcedencias cp " + "left join fetch cp.cfgCentroDeSalud "
          + "left join fetch cp.cfgConvenios " + "where cp.cpIdprocedencia <> 0");
      lista = query.list();

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return lista;
  }

  public CfgProcedencias getProcedenciasByNroOrden(long nOrden) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    CfgProcedencias procedencia = null;
    try {

      Query query = sesion
          .createSQLQuery("SELECT cp.* from cfg_procedencias cp "
              + "join datos_fichas df ON cp.cp_idprocedencia = df.df_idprocedencia " + "where df.df_norden = :nOrden")
          .addEntity(CfgProcedencias.class);
      query.setParameter("nOrden", nOrden);
      procedencia = (CfgProcedencias) query.setMaxResults(1).uniqueResult();

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return procedencia;
  }

  public CfgProcedencias getProcedenciaById(Integer idProcedencia) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    CfgProcedencias procedencia = null;
    try {
      Query query = sesion.createQuery("from CfgProcedencias cp " + "left join fetch cp.cfgCentroDeSalud "
          + "left join fetch cp.cfgConvenios " + "where cp.cpIdprocedencia = :idProcedencia");
      query.setParameter("idProcedencia", idProcedencia);
      procedencia = (CfgProcedencias) query.uniqueResult();

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return procedencia;
  }

  public String insertProcedencia(CfgProcedenciasDTO cp, Long idUsuario) throws BiosLISDAOException {
	 
	  
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String mensaje = "E";
    CfgProcedencias proce = new CfgProcedencias();
    if (cp.getCfgCentroDeSalud() > 0) {
      CfgCentrosdesalud centro = new CfgCentrosdesalud();
      centro.setCcdsIdcentrodesalud(cp.getCfgCentroDeSalud());
      proce.setCfgCentroDeSalud(centro);
    } else {
      proce.setCfgCentroDeSalud(null);
    }
    if (cp.getCfgConvenios() > 0) {
      CfgConvenios conv = new CfgConvenios();
      conv.setCcIdconvenio(cp.getCfgConvenios());
      proce.setCfgConvenios(conv);
    }
    proce.setCpCodigo(cp.getCpCodigo());
    proce.setCpDescripcion(cp.getCpDesc());
    proce.setCpSort(cp.getCpSort());
    proce.setCpEmail(cp.getCpEmail());
    proce.setCpTextoInforme(cp.getCpTextoInforme());
    proce.setCpCodigoGrupo(cp.getCpCodigoGrupo());
    proce.setCpProcedenciarestringida(cp.getCpProcedenciarestringida());
    proce.setCpTomamuestraautomatica(cp.getCpTomamuestraautomatica());//
    proce.setCpMembrete(cp.getCpMembrete());
    proce.setCpHost(cp.getCpHost());
    proce.setCpHost2(cp.getCpHost2());
    proce.setCpHost3(cp.getCpHost3());
    proce.setCpActivo(cp.getCpActivo());

    
    try{
    	if (validador.validarCodigoyAbreviado("CP_CODIGO", "CP_DESCRIPCION", "CFG_PROCEDENCIAS", cp.getCpCodigo(),cp.getCpDesc())) {
	        sesion.beginTransaction();
	        sesion.save(proce);
	        
	       
	       
            LogCfgtablas logTabla = new LogCfgtablas();
           
            logTabla.setCfgAccionesdatos(1);
            logTabla.setLctIdusuario(idUsuario.intValue());
            logTabla.setLctNombretabla("CFG_PROCEDENCIAS");                  
            logTabla.setLctIdtabla(proce.getCpIdprocedencia());
            logTabla.setLctDescripcion(proce.getCpDescripcion());
            
            logCfgTablasDAO.insertLogTablas(logTabla, sesion);
          
	        //*************************************************************
	      
	        sesion.getTransaction().commit();
	        mensaje = "C";
    	}

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return mensaje;
  }

  public String updateProcedencia(CfgProcedenciasDTO cp, Long idUsuario) throws BiosLISDAOException {
	  CfgProcedencias cpAntiguo = this.getProcedenciaById(cp.getCpidProcedencia().intValue());
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String mensaje = "E";

    try {

        CfgProcedencias proce = new CfgProcedencias();
        if(cp.getCfgCentroDeSalud() > 0){
            CfgCentrosdesalud centro = new CfgCentrosdesalud();
            centro.setCcdsIdcentrodesalud(cp.getCfgCentroDeSalud());
            proce.setCfgCentroDeSalud(centro);
        } else {
        	proce.setCfgCentroDeSalud(null);
        }
        if(cp.getCfgConvenios() > 0){
            CfgConvenios conv = new CfgConvenios();
            conv.setCcIdconvenio(cp.getCfgConvenios());
            proce.setCfgConvenios(conv);
        } else {
        	proce.setCfgConvenios(null);
        }
        proce.setCpIdprocedencia(cp.getCpidProcedencia().intValue());
        proce.setCpCodigo(cp.getCpCodigo());
        proce.setCpDescripcion(cp.getCpDesc());
        proce.setCpSort(cp.getCpSort());
        proce.setCpEmail(cp.getCpEmail());
        proce.setCpTextoInforme(cp.getCpTextoInforme());
        proce.setCpCodigoGrupo(cp.getCpCodigoGrupo());
        proce.setCpProcedenciarestringida(cp.getCpProcedenciarestringida());
        proce.setCpTomamuestraautomatica(cp.getCpTomamuestraautomatica());//
        proce.setCpMembrete(cp.getCpMembrete());
        proce.setCpHost(cp.getCpHost());
        proce.setCpHost2(cp.getCpHost2());
        proce.setCpHost3(cp.getCpHost3());
        proce.setCpActivo(cp.getCpActivo());
    	if (validador.validarxId("CP_CODIGO", "CP_IDPROCEDENCIA", "CP_DESCRIPCION", "CFG_PROCEDENCIAS", cp.getCpCodigo(), cp.getCpDesc(), cp.getCpidProcedencia())) {
            sesion.beginTransaction();
            sesion.update(proce);
            sesion.getTransaction().commit();
            mensaje = "C";
            sesion.close();
            logCfgTablasDAO.compararTablasProcedencia(proce, cpAntiguo, idUsuario, proce.getCpDescripcion());
		}

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return mensaje;
  }

  public List<CfgProcedencias> getProcedenciasFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
    String codigo = filtros.get("codigo").toUpperCase();
    String nombre = filtros.get("nombre").toUpperCase();
    String activo = filtros.get("activo");
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgProcedencias> lista = null;
    try {
      String sqlProcedencia = "from CfgProcedencias cp" + " left join fetch cp.cfgConvenios"
          + " where cp.cpCodigo like :codigo" + " and cp.cpDescripcion like :nombre" + " and cp.cpIdprocedencia <> 0";
      if (activo.equals(Constante.ESTADO_ACTIVO)) {
        sqlProcedencia += " and cp.cpActivo = 'S'";
      }
      sqlProcedencia += " order by cp.cpSort asc, cp.cpDescripcion asc";
      Query query = sesion.createQuery(sqlProcedencia);
      query.setParameter("codigo", "%" + codigo + "%");
      query.setParameter("nombre", "%" + nombre + "%");
      lista = query.list();

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return lista;
  }

  // hecho x jan

  public List<CfgProcedenciasDTO> getProcedenciasxCentro(long idCentro) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgProcedenciasDTO> procedencia = null;
    try {
      Query qery = sesion
          .createSQLQuery(
              "SELECT CP_IDPROCEDENCIA,CP_DESCRIPCION FROM CFG_PROCEDENCIAS  WHERE CP_IDCENTRODESALUD = :idCentro")
          .setResultTransformer(Transformers.aliasToBean(CfgProcedenciasDTO.class));

      qery.setParameter("idCentro", idCentro);

      procedencia = qery.list();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return procedencia;
  }

}
