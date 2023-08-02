/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;


import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.common.ValidacionMantenedor;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.LaboratorioCentroDTO;
import com.grupobios.bioslis.entity.CfgLaboratorios;
import com.grupobios.bioslis.entity.LogCfgtablas;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;



/**
 *
 * @author Nacho
 */
public class CfgLaboratoriosDAO {


    private static Logger logger = LogManager.getLogger(CfgLaboratoriosDAO.class);
    private static ValidacionMantenedor validador = new ValidacionMantenedor();
    
    public String insertLab(CfgLaboratorios clab, Long idUsuario) throws BiosLISDAOException {
        Session sesion = null;
        String mensaje = "E";
        try {
        	if (validador.validarCodigoyAbreviado("CLAB_CODIGO", "CLAB_DESCRIPCION", "CFG_LABORATORIOS", clab.getClabCodigo(), clab.getClabDescripcion())) {
                sesion = HibernateUtil.getSessionFactory().openSession();
                sesion.beginTransaction();
                //clab.setClabIdlaboratorio(this.getNewIdLab());
                sesion.save(clab);
                
                
                
    	        //Se agregan laboratorios a log tablas **********************
                LogCfgtablas logTabla = new LogCfgtablas();
                LogCfgTablasDAO log = new LogCfgTablasDAO();
                logTabla.setCfgAccionesdatos(1);
                logTabla.setLctIdusuario(idUsuario.intValue());
                logTabla.setLctNombretabla("CFG_LABORATORIOS");                  
                logTabla.setLctIdtabla(clab.getClabIdlaboratorio());
                logTabla.setLctDescripcion(clab.getClabDescripcion());
                
                log.insertLogTablas(logTabla, sesion);
    	        //*************************************************************
                
                
                sesion.getTransaction().commit();
                sesion.close();
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

    public String updateLab(CfgLaboratorios clab, Long idUsuario) throws BiosLISDAOException {
    	System.out.println(clab);
    	CfgLaboratorios clabAntiguo = this.getLaboratorioById(clab.getClabIdlaboratorio());
    	clab.setCfgSecciones(clabAntiguo.getCfgSecciones());
        Session sesion = null;
        String mensaje = "E";
        try {
        	BigDecimal id = new BigDecimal(clab.getClabIdlaboratorio());
        	if (validador.validarxId("CLAB_CODIGO", "CLAB_IDLABORATORIO", "CLAB_DESCRIPCION", "CFG_LABORATORIOS", clab.getClabCodigo(), clab.getClabDescripcion(), id)) {
                sesion = HibernateUtil.getSessionFactory().openSession();
                sesion.beginTransaction();
                sesion.update(clab);
                sesion.getTransaction().commit();
                sesion.close();
                mensaje = "C";
               
                LogCfgTablasDAO log = new LogCfgTablasDAO();
                log.compararTablasLaboratorio(clab, clabAntiguo, idUsuario,clab.getClabDescripcion());
              
                
                
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
    public int getNewIdLab() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
>>>>>>> v04_tomcat_ca
        sesion.beginTransaction();
        // clab.setClabIdlaboratorio(this.getNewIdLab());
        sesion.save(clab);
        sesion.getTransaction().commit();
        sesion.close();
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

  public String updateLab(CfgLaboratorios clab) throws BiosLISDAOException {
    Session sesion = null;
    String mensaje = "E";
    try {
      BigDecimal id = new BigDecimal(clab.getClabIdlaboratorio());
      if (validador.validarxId("CLAB_CODIGO", "CLAB_IDLABORATORIO", "CLAB_DESCRIPCION", "CFG_LABORATORIOS",
          clab.getClabCodigo(), clab.getClabDescripcion(), id)) {
        sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        CfgLaboratorios cBd = this.getLaboratorioById(clab.getClabIdlaboratorio());

        logger.debug("ANtes");
        cBd.copySinSecciones(clab);
        sesion.update(cBd);
        logger.debug("Despues");
        sesion.getTransaction().commit();
        sesion.close();
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
   * public int getNewIdLab() { Session sesion =
   * HibernateUtil.getSessionFactory().openSession(); sesion.beginTransaction();
   * Query query = sesion.
   * createQuery("select clab.clabIdlaboratorio from com.grupobios.bioslis.entity.CfgLaboratorios clab "
   * + "order by clab.clabIdlaboratorio desc").setMaxResults(1); int indice; try {
   * indice = ((int) query.list().get(0)) + 1; } catch (Exception e) { indice = 1;
   * } sesion.getTransaction().commit(); sesion.close(); return indice; }
   */
  public List<CfgLaboratorios> getLaboratorios() throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      sesion.beginTransaction();
      Query query = sesion.createQuery("select clab "
              + "from com.grupobios.bioslis.entity.CfgLaboratorios clab "
              + "where clab.clabIdlaboratorio > 0"
              //+ "order by clab.clabIdlaboratorio asc");
              + " order by clab.clabSort asc, clab.clabDescripcion asc");
      List<CfgLaboratorios> listaLaboratorios = query.list();
      sesion.getTransaction().commit();
      sesion.close();
      return listaLaboratorios;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public CfgLaboratorios getLaboratorioById(int idLaboratorio) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    CfgLaboratorios lab = null;
    try {
      Query query = sesion.createQuery("select clab " + "from com.grupobios.bioslis.entity.CfgLaboratorios clab "
          + "where clab.clabIdlaboratorio = :idLaboratorio");
      query.setParameter("idLaboratorio", idLaboratorio);
      lab = (CfgLaboratorios) query.uniqueResult();
      sesion.close();
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return lab;
  }

  public List<CfgLaboratorios> getLaboratoriosByCentro(int idCentro) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String condition = "";
      if (idCentro > -1)
        condition = "where clab.clabIdCentroSalud = :idCentro ";

      Query query = sesion.createQuery("select clab "
              + "from CfgLaboratorios clab "
              + condition
              + "order by clab.clabSort asc, clab.clabDescripcion asc");
      if (idCentro > -1)
          query.setParameter("idCentro", idCentro);

      List<CfgLaboratorios> listaLaboratorios = query.list();
      sesion.close();
      return listaLaboratorios;
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

  public List<LaboratorioCentroDTO> getLaboratoriosCentroSalud() throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sql = "select clab.clab_idlaboratorio, clab.clab_idcentrodesalud, clab.clab_descripcion, cds.ccds_descripcion " +
              "from cfg_laboratorios clab " +
              "join cfg_centrosdesalud cds on cds.ccds_idcentrodesalud = clab.clab_idcentrodesalud " +
              //"order by clab.clab_idlaboratorio";
              "order by clab.clab_sort asc, clab.clab_descripcion asc";
      Query query = sesion.createSQLQuery(sql)
          .setResultTransformer(Transformers.aliasToBean(LaboratorioCentroDTO.class));
      List<LaboratorioCentroDTO> list = query.list();
      sesion.close();
      return list;
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

  public List<CfgLaboratorios> getLaboratoriosFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String codigo = filtros.get("codigo").toUpperCase();
    String descripcion = filtros.get("descripcion").toUpperCase();
    String activo = filtros.get("activo");
    try {
      String sql = "select clab from CfgLaboratorios clab" + " where clab.clabIdlaboratorio > 0"
          + " and clab.clabDescripcion like :descripcion" + " and clab.clabCodigo like :codigo";
      if (activo.equals(Constante.ESTADO_ACTIVO)) {
        sql += " and clab.clabActivo = 'S'";
      }
      sql += " order by clab.clabSort asc, clab.clabDescripcion asc";
      Query query = sesion.createQuery(sql);
      query.setParameter("codigo", "%" + codigo + "%");
      query.setParameter("descripcion", "%" + descripcion + "%");
      List<CfgLaboratorios> listaLaboratorios = query.list();
      sesion.close();
      return listaLaboratorios;
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
}
