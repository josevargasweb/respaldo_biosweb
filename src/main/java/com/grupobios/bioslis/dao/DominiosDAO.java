package com.grupobios.bioslis.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.GlosaDTO;
import com.grupobios.bioslis.dto.GlosasExamenDTO;
import com.grupobios.bioslis.dto.ParamResultadoExamenDTO;
import com.grupobios.bioslis.dto.ParamResultadoExamenOrden;

public class DominiosDAO {

  private static Logger logger = LogManager.getLogger(DominiosDAO.class);

  public List<GlosaDTO> getGlosasResultados() {
    Session sesion = null;
    try {
      List<GlosaDTO> lst = null;
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query qry = sesion.createSQLQuery(sqlGlosasResultados)
          .setResultTransformer(Transformers.aliasToBean(GlosaDTO.class));
      lst = (List<GlosaDTO>) qry.list();
      sesion.close();
      return lst;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      // throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return null;
  }

  private final String sqlGlosasResultados = "SELECT cte.CT_IDTEST ,ctr.CTR_CODIGO ,cg.CG_IDGLOSA,cg.CG_DESCRIPCION,cg.CG_CODIGOGLOSA "
      + "FROM CFG_TEST cte " + "JOIN CFG_TIPOSDERESULTADO ctr ON (ctr.CTR_IDTIPORESULTADO = cte.CT_IDTIPORESULTADO) "
      + "JOIN CFG_GLOSASTEST cgt ON (cgt.CGT_IDTEST = cte.CT_IDTEST) "
      + "JOIN CFG_GLOSAS cg ON (cg.CG_IDGLOSA = cgt.CGT_IDGLOSA ) " + " WHERE ctr.CTR_CODIGO = 'G' "
      + "AND cg.CG_ACTIVO = 'S'";

  private final String sqlDominiosGlosasTests = "SELECT ct.CT_IDTEST ,ctr.CTR_CODIGO ,cg.CG_IDGLOSA,cg.CG_DESCRIPCION "
      + "FROM CFG_EXAMENESTEST cet " + "JOIN CFG_TEST ct on cet.CET_IDTEST = ct.CT_IDTEST "
      + "JOIN CFG_TIPOSDERESULTADO ctr ON (ctr.CTR_IDTIPORESULTADO = ct.CT_IDTIPORESULTADO) "
      + "JOIN CFG_GLOSASTEST cgt ON (cgt.CGT_IDTEST = ct.CT_IDTEST) "
      + "JOIN CFG_GLOSAS cg ON (cg.CG_IDGLOSA = cgt.CGT_IDGLOSA ) " + " WHERE ctr.CTR_CODIGO = 'G' "
      + "AND cg.CG_ACTIVO = 'S' " + "" + " AND cet.CET_IDEXAMEN = :idExamen " + "ORDER BY ct.CT_IDTEST";

  public List<GlosasExamenDTO> getDominiosResultadosExamenesOrden(Long nroOrden,
      ParamResultadoExamenOrden prmResultadoExamenOrden) throws BiosLISDAOException {

    List<ParamResultadoExamenDTO> list = prmResultadoExamenOrden.getExamenes();
    List<GlosasExamenDTO> lstGlosas = new ArrayList<GlosasExamenDTO>();
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      for (ParamResultadoExamenDTO pDto : list) {

        GlosasExamenDTO geDto = new GlosasExamenDTO();
        geDto.setCE_IDEXAMEN(pDto.getDFE_IDEXAMEN());
        List<GlosaDTO> lst = null;
        Query qry = sesion.createSQLQuery(sqlDominiosGlosasTests)
            .setResultTransformer(Transformers.aliasToBean(GlosaDTO.class));
        qry.setBigDecimal("idExamen", pDto.getDFE_IDEXAMEN());

        lst = (List<GlosaDTO>) qry.list();
        geDto.setLstglosas(lst);
        lstGlosas.add(geDto);
      }
      sesion.close();
    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("No se pudo recuperar dominio para test.");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return lstGlosas;

  }

  public List<GlosasExamenDTO> getDominiosResultadosAllExamenesOrden(Long nroOrden,
      ParamResultadoExamenOrden prmResultadoExamenOrden) throws BiosLISDAOException {

    List<ParamResultadoExamenDTO> list = prmResultadoExamenOrden.getExamenes();
    List<GlosasExamenDTO> lstGlosas = new ArrayList<GlosasExamenDTO>();
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      for (ParamResultadoExamenDTO pDto : list) {

        GlosasExamenDTO geDto = new GlosasExamenDTO();
        geDto.setCE_IDEXAMEN(pDto.getDFE_IDEXAMEN());
        List<GlosaDTO> lst = null;
        Query qry = sesion.createSQLQuery(sqlDominiosGlosasTests)
            .setResultTransformer(Transformers.aliasToBean(GlosaDTO.class));
        qry.setBigDecimal("idExamen", pDto.getDFE_IDEXAMEN());

        lst = (List<GlosaDTO>) qry.list();
        geDto.setLstglosas(lst);
        lstGlosas.add(geDto);
      }
      sesion.close();
    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("No se pudo recuperar dominio para test.");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return lstGlosas;

  }

  public List<GlosaDTO> getDominiosGlosaxTest() throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<GlosaDTO> lst = null;
    try {

      Query qry = sesion.createSQLQuery(sqlDominiosGlosasxTest)
          .setResultTransformer(Transformers.aliasToBean(GlosaDTO.class));

      lst = qry.list();

    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("No se pudo recuperar dominio para test.");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return lst;
  }

  // se modifica el 28-12 por cristian alveal
  private String sqlDominiosGlosasxTest = "SELECT ct.CT_IDTEST ,ctr.CTR_CODIGO ,cg.CG_IDGLOSA,cg.CG_DESCRIPCION,cgt.CGT_ESGLOSACRITICA  "
      + "FROM CFG_TEST ct  " + "    JOIN CFG_TIPOSDERESULTADO ctr ON (ctr.CTR_IDTIPORESULTADO = ct.CT_IDTIPORESULTADO) "
      + "    JOIN CFG_GLOSASTEST cgt ON (cgt.CGT_IDTEST = ct.CT_IDTEST) "
      + "    JOIN CFG_GLOSAS cg ON (cg.CG_IDGLOSA = cgt.CGT_IDGLOSA )    " + "WHERE " + "ctr.CTR_CODIGO = 'G' ";

}
