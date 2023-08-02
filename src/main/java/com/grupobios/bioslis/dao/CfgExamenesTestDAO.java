package com.grupobios.bioslis.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.ExamenThinDTO;
import com.grupobios.bioslis.entity.CfgExamenestest;

public class CfgExamenesTestDAO {

  private static Logger logger = LogManager.getLogger(CfgExamenesTestDAO.class);

  public List<CfgExamenestest> getExamenTest(int idexamen) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery("select list " + "from CfgExamenestest list "
          + "left join fetch list.cfgTest ct " + "where list.id.cetIdexamen = :id");
      query.setParameter("id", idexamen);
      List<CfgExamenestest> lista = query.list();
      sesion.close();
      return lista;
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

  public List<CfgExamenestest> getTestByExamen(int idExamen, String nombre) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      Query query = sesion.createQuery("select list " + "from com.grupobios.bioslis.entity.CfgExamenestest list "
          + "left join fetch list.cfgTest ct " + "where list.id.cetIdexamen = :id "
          + "and ct.ctDescripcion like :nombre");
      query.setParameter("id", idExamen);
      query.setParameter("nombre", "%" + nombre + "%");
      List<CfgExamenestest> lista = query.list();
      sesion.getTransaction().commit();
      sesion.close();
      return lista;
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

  public List<CfgExamenestest> getTestByExamenId(int idExamen) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      String sqlTestExamenesFilter = "select list" + " from com.grupobios.bioslis.entity.CfgExamenestest list "
          + " left join fetch list.cfgTest ct" + " left join fetch ct.cfgMuestras cm"
          + " where list.id.cetIdexamen = :idExamen";

      sqlTestExamenesFilter += " order by ct.ctSort asc, ct.ctAbreviado asc";
      Query query = sesion.createQuery(sqlTestExamenesFilter);
      query.setParameter("idExamen", idExamen);
      List<CfgExamenestest> lista = query.list();
      sesion.close();
      return lista;
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

  public List<CfgExamenestest> getTestByExamenIdActivo(int idExamen, String activo) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sqlTestExamenesFilter = "select list" + " from com.grupobios.bioslis.entity.CfgExamenestest list "
          + " left join fetch list.cfgTest ct" + " left join fetch ct.cfgMuestras cm"
          + " where list.id.cetIdexamen = :idExamen";
      if (activo.equals(Constante.ESTADO_ACTIVO)) {
        sqlTestExamenesFilter = " and ct.ctActivo = 'S'";
      }
      sqlTestExamenesFilter += " order by ct.ctSort asc, ct.ctAbreviado asc";
      Query query = sesion.createQuery(sqlTestExamenesFilter);
      query.setParameter("idExamen", idExamen);
      List<CfgExamenestest> lista = query.list();
      sesion.close();
      return lista;
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

  // Creado a partir del método anterior, solo que esta consulta coincidencias por
  // ctDescripcion y ctAbreviado.
  public List<CfgExamenestest> getTestByExamenIdyNombre(int idExamen, String nombre, String activo)
      throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      String sqlTestExamenesFilter = "from com.grupobios.bioslis.entity.CfgExamenestest list "
          + " left join fetch list.cfgTest ct" + " where list.id.cetIdexamen = :idExamen"
          + " and ((translate(upper(ct.ctDescripcion), 'ÁÉÍÓÚ', 'AEIOU')) like :nombre"
          + " or (translate(upper(ct.ctAbreviado), 'ÁÉÍÓÚ', 'AEIOU')) like :nombre)";
      if (activo.equals(Constante.ESTADO_ACTIVO)) {
        sqlTestExamenesFilter = " and ct.ctActivo = 'S'";
      }
      sqlTestExamenesFilter += " order by ct.ctSort asc, ct.ctAbreviado asc";
      Query query = sesion.createQuery(sqlTestExamenesFilter);
      query.setParameter("idExamen", idExamen);
      query.setParameter("nombre", "%" + nombre + "%");
      List<CfgExamenestest> lista = query.list();
      sesion.close();
      return lista;
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

  public Object[] getTest(int idexamen) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      Query query = sesion.createQuery("select list.id.cetIdtest, ex.ceDescripcion "
          + "from com.grupobios.bioslis.entity.CfgExamenestest list,com.grupobios.bioslis.entity.CfgExamenes ex "
          + "where list.id.cetIdexamen = :id and " + "ex.ceIdexamen = list.id.cetIdexamen");
      query.setParameter("id", idexamen);
      sesion.getTransaction().commit();
      List<String[]> lista = query.list();
      sesion.close();
      Object[] b = null;
      for (int i = 0; i < lista.size(); i++) {
        b[i] = lista.get(i)[0].toString();
      }


      return b;
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

  public void insertExamenTest(CfgExamenestest cet) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      sesion.beginTransaction();
      sesion.save(cet);
      sesion.getTransaction().commit();
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

  public void updateExamenTest(CfgExamenestest cet) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      sesion.beginTransaction();
      sesion.update(cet);
      sesion.getTransaction().commit();
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

  public void deleteExamenTest(CfgExamenestest cet) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
    	
      sesion.beginTransaction();
      
      sesion.delete(cet);
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

  public boolean existeTest(int idexamen, int idtest) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgExamenestest> lista ;
    try {
      sesion.beginTransaction();
      Query query = sesion.createQuery("select list from CfgExamenestest list "
          + "where list.id.cetIdexamen = :idexamen and list.id.cetIdtest = :idtest");
      query.setParameter("idexamen", idexamen);
      query.setParameter("idtest", idtest);
      sesion.getTransaction().commit();
      lista = query.list();
      sesion.close();
      
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return !lista.isEmpty();
  }

  public List<ExamenThinDTO> getExamenesTest(int idtest) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<ExamenThinDTO> getExamenesTestList = null;
    try {
      sesion.beginTransaction();
      String sql = "select ce.CE_IDEXAMEN, ce.CE_ABREVIADO from cfg_examenestest cet"
          + " join cfg_examenes ce on ce.ce_idexamen = cet.cet_idexamen" + " where cet.cet_idtest = :idtest";
      Query query = sesion.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(ExamenThinDTO.class));
      query.setParameter("idtest", idtest);
    
      getExamenesTestList = query.list();
      sesion.getTransaction().commit();
      sesion.close();

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
    return getExamenesTestList;
  }

  public List<CfgExamenestest> getExamenTest(int idexamen, Session sesion) throws BiosLISDAOException {
    try {
    	logger.debug("ENTRO A EXAMENTEST/*/-*/-*/-*-*******************/////////-----------------------");
      Query query = sesion.createQuery("select list " + "from CfgExamenestest list "
          + "left join fetch list.cfgTest ct " + "where list.id.cetIdexamen = :id");
      query.setParameter("id", idexamen);
      List<CfgExamenestest> lista = query.list();
//      sesion.close();
      logger.debug(lista+"  /*/-*/-*/-*-*******************/////////-----------------------");
      return lista;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
//      if (sesion != null && sesion.isOpen()) {
//        sesion.close();
//      }
    }
  }

}
