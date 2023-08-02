/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.common.ValidacionMantenedor;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgGlosas;
import com.grupobios.bioslis.entity.LogCfgtablas;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 *
 * @author Nacho
 */
public class CfgGlosasDAO {


    private static Logger logger = LogManager.getLogger(CfgGlosasDAO.class);
    private static ValidacionMantenedor validador = new ValidacionMantenedor();
    LogCfgTablasDAO logCfgTablasDAO;

    
    public LogCfgTablasDAO getLogCfgTablasDAO() {
		return logCfgTablasDAO;
	}

	public void setLogCfgTablasDAO(LogCfgTablasDAO logCfgTablasDAO) {
		this.logCfgTablasDAO = logCfgTablasDAO;
	}

	public String insertGlosas(CfgGlosas cg, Long idUsuario) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String mensaje = "E";
        try {
        	cg.setCgCodigoglosa(cg.getCgCodigoglosa().toUpperCase());
        	cg.setCgDescripcion(cg.getCgDescripcion().toUpperCase());
        	if (validador.validarCodigo("CG_CODIGOGLOSA", "CFG_GLOSAS", cg.getCgCodigoglosa())) {
                sesion.beginTransaction();
                sesion.save(cg);
                
                //Se agregan glosas log tablas **********************
		            LogCfgtablas logTabla = new LogCfgtablas();
	                logTabla.setCfgAccionesdatos(1);
	                logTabla.setLctIdusuario(idUsuario.intValue());
	                logTabla.setLctNombretabla("CFG_GLOSAS");                  
	                logTabla.setLctIdtabla(cg.getCgIdglosa());
	                logTabla.setLctDescripcion(cg.getCgDescripcion());
                
                logCfgTablasDAO.insertLogTablas(logTabla, sesion);
                //*************************************************
                               
                sesion.getTransaction().commit();
                mensaje = "C";
			}

        } catch (HibernateException he) {
        	if (sesion != null && sesion.isOpen()) {
                sesion.close();
              }
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
        	if (sesion != null && sesion.isOpen()) {
                sesion.close();
              }
        }
        return mensaje;
    }

    public String updateGlosa(CfgGlosas cg, Long idUsuario) throws BiosLISDAOException {
    	CfgGlosas  cgAntiguo = this.getGlosaById(cg.getCgIdglosa());
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String mensaje = "E";
        try {
        	cg.setCgCodigoglosa(cg.getCgCodigoglosa().toUpperCase());
        	cg.setCgDescripcion(cg.getCgDescripcion().toUpperCase());
        	BigDecimal id = new BigDecimal(cg.getCgIdglosa());
        	if (validador.validarxIdSinAbreviado("CG_CODIGOGLOSA", "CG_IDGLOSA",  "CFG_GLOSAS", cg.getCgCodigoglosa(),  id)) {
                sesion.beginTransaction();
                sesion.update(cg);
                sesion.getTransaction().commit();
                sesion.close();
                
               //****   se agregan modificaciones log tablas 
                logCfgTablasDAO.comparadorObjetos(cgAntiguo, cg, 2, idUsuario.intValue(), cg.getCgIdglosa(), cg.getCgDescripcion(), "");
                
                mensaje = "C";
			}

        } catch (HibernateException he) {
        	if (sesion != null && sesion.isOpen()) {
                sesion.close();
              }
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
        	if (sesion != null && sesion.isOpen()) {
                sesion.close();
              }
        }
        return mensaje;
    }
/*
    public int getNewIdGlosas() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
>>>>>>> v04_tomcat_ca
        sesion.beginTransaction();
        sesion.save(cg);
        sesion.getTransaction().commit();
        mensaje = "C";
      }
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return mensaje;
  }

  public String updateGlosa(CfgGlosas cg) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String mensaje = "E";
    try {
      cg.setCgCodigoglosa(cg.getCgCodigoglosa().toUpperCase());
      cg.setCgDescripcion(cg.getCgDescripcion().toUpperCase());
      BigDecimal id = new BigDecimal(cg.getCgIdglosa());
      if (validador.validarxIdSinAbreviado("CG_CODIGOGLOSA", "CG_IDGLOSA", "CFG_GLOSAS", cg.getCgCodigoglosa(), id)) {
        sesion.beginTransaction();
        sesion.update(cg);
        sesion.getTransaction().commit();
        mensaje = "C";
      }

    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return mensaje;
  }

  /*
   * public int getNewIdGlosas() { Session sesion =
   * HibernateUtil.getSessionFactory().openSession(); sesion.beginTransaction();
   * Query query = sesion.
   * createQuery("select cg.cgIdglosa from com.grupobios.bioslis.entity.CfgGlosas cg "
   * + "order by cg.cgIdglosa desc").setMaxResults(1); int indice; try { indice =
   * ((int) query.list().get(0)) + 1; } catch (Exception e) { indice = 1; }
   * sesion.getTransaction().commit(); sesion.close(); return indice; }
   */
  public List<CfgGlosas> getGlosas() throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      Query query = sesion.createQuery("select cg from com.grupobios.bioslis.entity.CfgGlosas cg");
      List<CfgGlosas> listaGlosas = query.list();
      sesion.close();
      return listaGlosas;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<CfgGlosas> getGlosasActivas() throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery("select cg from CfgGlosas cg " + "where cg.cgActivo = 'S'");
      List<CfgGlosas> listaGlosas = query.list();
      sesion.close();
      return listaGlosas;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  /*
   * public List<CfgGlosas> getGlosasNombreLike(String nombre) { Session sesion =
   * HibernateUtil.getSessionFactory().openSession(); sesion.beginTransaction();
   * Query query = sesion.createQuery("select cg " +
   * "from com.grupobios.bioslis.entity.CfgGlosas cg " +
   * "where upper(cg.cgDescripcion) like :nombre"); query.setParameter("nombre",
   * "%" + nombre + "%"); List<CfgGlosas> listaGlosas = query.list();
   * sesion.getTransaction().commit(); sesion.close(); return listaGlosas; }
   * 
   * public List<CfgGlosas> getGlosasBySeccion(String nombre, int idSeccion) {
   * Session sesion = HibernateUtil.getSessionFactory().openSession();
   * sesion.beginTransaction(); Query query = sesion.createQuery("select cg " +
   * "from com.grupobios.bioslis.entity.CfgGlosas cg " +
   * "where upper(cg.cgDescripcion) like :nombre " +
   * "and cg.cgIdSeccion = :idSeccion"); query.setParameter("nombre", "%" + nombre
   * + "%"); query.setParameter("idSeccion", idSeccion); List<CfgGlosas>
   * listaGlosas = query.list(); sesion.getTransaction().commit(); sesion.close();
   * return listaGlosas; }
   */
  public CfgGlosas getGlosaById(int idGlosa) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery(
          "select cg " + "from com.grupobios.bioslis.entity.CfgGlosas cg " + "where cg.cgIdglosa = :idGlosa");
      query.setParameter("idGlosa", idGlosa);
      CfgGlosas glosa = (CfgGlosas) query.uniqueResult();
      sesion.close();
      return glosa;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<CfgGlosas> getGlosasByCodigoLike(String codigo) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    Query query = sesion.createQuery("select cg " + "from com.grupobios.bioslis.entity.CfgGlosas cg "
        + "where upper(cg.cgCodigoglosa) like :codigo");
    query.setParameter("codigo", "%" + codigo + "%");
    List<CfgGlosas> listaGlosas = query.list();
    sesion.getTransaction().commit();
    sesion.close();
    return listaGlosas;
  }

  public List<CfgGlosas> getGlosasByIdSeccion(int idSeccion) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery("select cg " + "from com.grupobios.bioslis.entity.CfgGlosas cg "
          + "where cg.cgIdSeccion = :idSeccion " + "and cg.cgActivo = 'S'");
      query.setParameter("idSeccion", idSeccion);
      List<CfgGlosas> listaGlosas = query.list();
      sesion.close();
      return listaGlosas;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<CfgGlosas> getGlosasFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
    String codigo = filtros.get("codigo").toUpperCase();
    String nombre = filtros.get("nombre").toUpperCase();
    String activo = filtros.get("activo");
    int idSeccion = 0;
    if(filtros.get("idSeccion") != null) {
    	 idSeccion = Integer.parseInt(filtros.get("idSeccion"));
    }
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      String sqlGlosasFiltro = "select cg" + " from com.grupobios.bioslis.entity.CfgGlosas cg"
          + " where upper(cg.cgCodigoglosa) like :codigo" + " and upper(cg.cgDescripcion) like :nombre";
      if (idSeccion > 0) {
        sqlGlosasFiltro += " and cg.cgIdSeccion = :idSeccion";
      }
      if (activo.equals(Constante.ESTADO_ACTIVO)) {
        sqlGlosasFiltro += " and cg.cgActivo = 'S'";
      }
      sqlGlosasFiltro += " order by cg.cgSort asc, cg.cgDescripcion";
      Query query = sesion.createQuery(sqlGlosasFiltro);
      query.setParameter("codigo", "%" + codigo + "%");
      query.setParameter("nombre", "%" + nombre + "%");
      if (idSeccion > 0) {
        query.setParameter("idSeccion", idSeccion);
      }
      List<CfgGlosas> listaGlosas = query.list();
      sesion.close();
      return listaGlosas;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public String getCriticoGT(int idtest, int idglosa) throws BiosLISDAOException {
    Session sesion = null;
    String critico = "N";
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();

      Query query = sesion.createSQLQuery(
          "SELECT CGT_ESGLOSACRITICA FROM cfg_glosastest WHERE cgt_idtest = :idtest AND cgt_idglosa = :idglosa");
      query.setParameter("idtest", idtest);
      query.setParameter("idglosa", idglosa);
      critico = (String) query.uniqueResult();
      sesion.close();
    } catch (HibernateException he) {
      he.printStackTrace();
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }


    return critico;
  }

}
