/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.grupobios.bioslis.cfg.cache.CfgCentrosDeSaludAbsDao;
import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.ValidacionMantenedor;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.config.SpringContext;
import com.grupobios.bioslis.dto.CfgExamenesDTO;
import com.grupobios.bioslis.dto.ItemPanelExamenDTO;
import com.grupobios.bioslis.dto.PanelExamenDTO;
import com.grupobios.bioslis.entity.CfgExamenes;
import com.grupobios.bioslis.entity.CfgExamenesPaneles;
import com.grupobios.bioslis.entity.CfgLaboratorios;
import com.grupobios.bioslis.entity.CfgMuestras;
import com.grupobios.bioslis.entity.CfgPanelesExamenes;

import com.grupobios.bioslis.entity.CfgSecciones;

import com.grupobios.bioslis.entity.LogCfgtablas;


/**
 *
 * @author Marco Caracciolo
 */
public class CfgPanelesExamenesDAO {

  private static Logger logger = LogManager.getLogger(CfgPanelesExamenesDAO.class);
  private static ValidacionMantenedor validador = new ValidacionMantenedor();

  LogCfgTablasDAO logCfgTablasDAO;
  



public LogCfgTablasDAO getLogCfgTablasDAO() {
	return logCfgTablasDAO;
}

public void setLogCfgTablasDAO(LogCfgTablasDAO logCfgTablasDAO) {
	this.logCfgTablasDAO = logCfgTablasDAO;
}

public List<CfgPanelesExamenes> getListPanelesByName(String nombre) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      String sql = "from CfgPanelesExamenes cpe" + " where cpe.cpeNombrepanelexamen like :nombre"
          + " order by cpe.cpeNombrepanelexamen asc";
      Query query = sesion.createQuery(sql);
      query.setParameter("nombre", "%" + nombre + "%");
      List<CfgPanelesExamenes> lista = query.list();
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

  public List<CfgPanelesExamenes> getListPanelesFiltro(String nombre, int activo) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      String sql = "from CfgPanelesExamenes cpe" + " where cpe.cpeNombrepanelexamen like :nombre";
      if (activo == 1) {
        sql += " and cpe.cpeActivo = 'S'";
      }
      Query query = sesion.createQuery(sql);
      query.setParameter("nombre", "%" + nombre + "%");
      List<CfgPanelesExamenes> lista = query.list();
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

  public CfgPanelesExamenes getPanelById(Short idPanel) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      String sql = "from CfgPanelesExamenes cpe" + " where cpe.cpeIdpanelexamenes = :idPanel";
      Query query = sesion.createQuery(sql);
      query.setParameter("idPanel", idPanel);
      CfgPanelesExamenes panel = (CfgPanelesExamenes) query.uniqueResult();
      sesion.close();
      return panel;
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

  public String insertPanelExamen(CfgPanelesExamenes cpe, Long idUsuario) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String mensaje = "E";
    try {

    	if (validador.validarCodigo("CPE_NOMBREPANELEXAMEN", "CFG_PANELESEXAMENES", cpe.getCpeNombrepanelexamen())) {
	        sesion.beginTransaction();
	        sesion.save(cpe);
	        
	        //Se agregan antecedentes a log tablas **********************
            LogCfgtablas logTabla = new LogCfgtablas();
            LogCfgTablasDAO log = new LogCfgTablasDAO();
            logTabla.setCfgAccionesdatos(1);
            logTabla.setLctIdusuario(idUsuario.intValue());
            logTabla.setLctNombretabla("CFG_PANELESEXAMENES");                  
            logTabla.setLctIdtabla(cpe.getCpeIdpanelexamenes());
            
            log.insertLogTablas(logTabla, sesion);
	        //*************************************************************
	        sesion.getTransaction().commit();
	        mensaje = "C";
	        sesion.close();
	      
    	}
    } catch (HibernateException he) {
    	he.printStackTrace();
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
    return mensaje;
  }

  public String updatePanelExamen(CfgPanelesExamenes cpe, Long idUsuario) throws BiosLISDAOException {
	  
	  CfgPanelesExamenes cpeAntiguo = this.getPanelById(cpe.getCpeIdpanelexamenes());
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String mensaje = "E";
    try {

    	BigDecimal id = new BigDecimal(cpe.getCpeIdpanelexamenes());
    	if (validador.validarxIdSinAbreviado("CPE_NOMBREPANELEXAMEN", "CPE_IDPANELEXAMENES", "CFG_PANELESEXAMENES", cpe.getCpeNombrepanelexamen(), id)) {
            sesion.beginTransaction();
            sesion.update(cpe);
            sesion.getTransaction().commit();
            mensaje = "C";
            sesion.close();
            LogCfgTablasDAO log = new LogCfgTablasDAO();
            log.validarDatos(cpe.getCpeNombrepanelexamen(), cpeAntiguo.getCpeNombrepanelexamen(), idUsuario , "CPE_ NOMBREPANELEXAMEN", cpe.getCpeIdpanelexamenes(), "CFG_PANELESEXAMENES", cpe.getCpeNombrepanelexamen());	     
            log.validarDatos(cpe.getCpeActivo(), cpeAntiguo.getCpeActivo(), idUsuario , "CPE_ACTIVO", cpe.getCpeIdpanelexamenes(), "CFG_PANELESEXAMENES", cpe.getCpeNombrepanelexamen());	     
            
		}


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
    return mensaje;
  }

  public Map<String, ArrayList<HashMap<BigDecimal, ArrayList<BigDecimal>>>> getAll() throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      Query query = sesion.createSQLQuery(sqlTestxPanel);
      query.setResultTransformer(Transformers.aliasToBean(PanelExamenDTO.class));
      List<PanelExamenDTO> lstPanel = query.list();
      sesion.close();
      Map<String, ArrayList<HashMap<BigDecimal, ArrayList<BigDecimal>>>> hm = this.groupByPanel(lstPanel);

      for (String panelExamenName : hm.keySet()) {

        logger.debug("Panel {}", panelExamenName);
        ArrayList<HashMap<BigDecimal, ArrayList<BigDecimal>>> lstHMPanelExamen = hm.get(panelExamenName);

        for (HashMap<BigDecimal, ArrayList<BigDecimal>> hmExamenLstTest : lstHMPanelExamen) {

          for (BigDecimal examenId : hmExamenLstTest.keySet()) {

            logger.debug("Examen id {}", examenId);

            List<BigDecimal> lstTest = hmExamenLstTest.get(examenId);

            for (BigDecimal testID : lstTest) {

              logger.debug("Test id {}", testID);

            }

          }

        }
      }

      return hm;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error("No se pudo recuperar paneles. {}", he.getLocalizedMessage());
      throw new BiosLISDAOException("No se pudo recuperar paneles. ");
    }

    finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  private Map<String, ArrayList<HashMap<BigDecimal, ArrayList<BigDecimal>>>> groupByPanel(
      List<PanelExamenDTO> lstPanelExamenDTO) {

    Map<String, ArrayList<HashMap<BigDecimal, ArrayList<BigDecimal>>>> hmPanelesExamen = new HashMap<>();
    ArrayList<HashMap<BigDecimal, ArrayList<BigDecimal>>> listHMPanelExamen = new ArrayList<HashMap<BigDecimal, ArrayList<BigDecimal>>>();
    HashMap<BigDecimal, ArrayList<BigDecimal>> hmListExamenTest = new HashMap<BigDecimal, ArrayList<BigDecimal>>();
    ArrayList<BigDecimal> lstTest = new ArrayList<BigDecimal>();
    int n = lstPanelExamenDTO.size();

    if (n == 0)
      return null;

    PanelExamenDTO currentPanelExamenDTO = lstPanelExamenDTO.get(0);
    String lastPanel = currentPanelExamenDTO.getCPE_NOMBREPANELEXAMEN();
    BigDecimal lastExamen = currentPanelExamenDTO.getCE_IDEXAMEN();
    lstTest.add(currentPanelExamenDTO.getCT_IDTEST());
    lastExamen = currentPanelExamenDTO.getCE_IDEXAMEN();
    lastPanel = currentPanelExamenDTO.getCPE_NOMBREPANELEXAMEN();
    hmListExamenTest.put(lastExamen, lstTest);
    listHMPanelExamen.add(hmListExamenTest);
    hmPanelesExamen.put(lastPanel, listHMPanelExamen);

    for (int i = 1; i < n; i++) {
      currentPanelExamenDTO = lstPanelExamenDTO.get(i);
      logger.debug("{}", currentPanelExamenDTO);
      if (!lastPanel.equals(currentPanelExamenDTO.getCPE_NOMBREPANELEXAMEN())) {
        logger.debug("Nuevo panel", currentPanelExamenDTO.getCPE_NOMBREPANELEXAMEN());
        listHMPanelExamen = new ArrayList<HashMap<BigDecimal, ArrayList<BigDecimal>>>();
        hmListExamenTest = new HashMap<BigDecimal, ArrayList<BigDecimal>>();
        lstTest = new ArrayList<BigDecimal>();
        lastPanel = currentPanelExamenDTO.getCPE_NOMBREPANELEXAMEN();
        lastExamen = currentPanelExamenDTO.getCE_IDEXAMEN();
        lstTest.add(currentPanelExamenDTO.getCT_IDTEST());
        hmListExamenTest.put(lastExamen, lstTest);
        listHMPanelExamen.add(hmListExamenTest);
        hmPanelesExamen.put(lastPanel, listHMPanelExamen);
      } else if (!lastExamen.equals(currentPanelExamenDTO.getCE_IDEXAMEN())) {
        logger.debug("Nuevo examen", currentPanelExamenDTO.getCE_IDEXAMEN());
        hmListExamenTest = new HashMap<BigDecimal, ArrayList<BigDecimal>>();
        lstTest = new ArrayList<BigDecimal>();
        lastExamen = currentPanelExamenDTO.getCE_IDEXAMEN();
        lstTest.add(currentPanelExamenDTO.getCT_IDTEST());
        hmListExamenTest.put(lastExamen, lstTest);
        listHMPanelExamen.add(hmListExamenTest);
        hmPanelesExamen.put(lastPanel, listHMPanelExamen);
      } else {
        if (lstTest != null) {
          lstTest.add(currentPanelExamenDTO.getCT_IDTEST());
          logger.debug("Solo Nuevo test {}", currentPanelExamenDTO.getCT_IDTEST());
        }
      }

    }
    return hmPanelesExamen;

  }

  public List<ItemPanelExamenDTO> getExamenesxPanel() throws BiosLISDAOException, BiosLISException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      Query qry = sesion.createQuery(hqlExamenxPanel);
      List<Object[]> lst = qry.list();
      logger.debug("Cantidad itempanelexamen : {}", lst.size());
      Short currentId = -1;
      List<ItemPanelExamenDTO> lstPaneles = new ArrayList<ItemPanelExamenDTO>();
      int i = -1;
      CfgCentrosDeSaludAbsDao.init(SpringContext.getBean(CfgCentrosDeSaludDAO.class));

      for (Object[] objects : lst) {

        CfgExamenesPaneles cep = (CfgExamenesPaneles) objects[0];
        CfgExamenes ce = (CfgExamenes) objects[1];
        CfgSecciones csec = (CfgSecciones) objects[2];
        CfgMuestras cm = (CfgMuestras) objects[3];
        CfgLaboratorios clab = (CfgLaboratorios) objects[4];
        CfgPanelesExamenes cpe = (CfgPanelesExamenes) objects[5];
        CfgExamenesDTO ceDto = new CfgExamenesDTO(ce);
        ceDto.setCfgSecciones(csec);
        ceDto.setCfgMuestras(cm);
        ceDto.setCfgLaboratorio(clab);
        ceDto.setLdvIndicacionesexamenes(ce.getLdvIndicacionesexamenes());
        if (cep.getCfgExamenesPanelesId().getCepIdpanelexamenes() != currentId) {

          ItemPanelExamenDTO ipeDto = new ItemPanelExamenDTO();
          ipeDto.setItemPanel(cpe.getCpeIdpanelexamenes());
          ipeDto.setNamePanel(cpe.getCpeNombrepanelexamen());
          ipeDto.setLstExamenes(new ArrayList<>());
          ipeDto.getLstExamenes().add(ceDto);
          currentId = cep.getCfgExamenesPanelesId().getCepIdpanelexamenes();
          lstPaneles.add(ipeDto);
          i++;
        } else {
          lstPaneles.get(i).getLstExamenes().add(ceDto);
        }
      }
      return lstPaneles;
    } catch (HibernateException he) {
      logger.error(he.getLocalizedMessage());
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      throw new BiosLISDAOException(he.getMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }

  }

  // modificado por cristian 30-11 -->> faltaba una coma y algunas columnas mal
  // referenciadas
  private final static String sqlTestxPanel = ""
      + "SELECT cp.CPE_NOMBREPANELEXAMEN,ce.CE_IDEXAMEN , ce.CE_DESCRIPCION, ct.CT_IDTEST ,ct.CT_ABREVIADO,cp.CPE_IDPANELEXAMENES, "
      + " csec.CSEC_IDSECCION, csec.CSEC_DESCRIPCION,csec.CSEC_CODIGO, clab.CLAB_IDLABORATORIO, clab.CLAB_DESCRIPCION, ldvie.LDVIE_IDINDICACIONESEXAMEN, ldvie.LDVIE_DESCRIPCION  "
      + "FROM BIOSLIS.CFG_EXAMENES ce "
      + "INNER JOIN BIOSLIS.CFG_EXAMENESPANELES cep ON (ce.CE_IDEXAMEN = cep.CEP_IDEXAMEN) "
      + "INNER JOIN BIOSLIS.CFG_PANELESEXAMENES cp ON (cp.CPE_IDPANELEXAMENES = cep.CEP_IDPANELEXAMENES) "
      + "INNER JOIN BIOSLIS.CFG_EXAMENESTEST cet ON (ce.CE_IDEXAMEN = cet.CET_IDEXAMEN) "
      + "INNER JOIN BIOSLIS.CFG_TEST ct ON (ct.CT_IDTEST = cet.CET_IDTEST) "
      + "INNER JOIN BIOSLIS.CFG_SECCIONES csec ON (ce.CE_IDSECCION = csec.CSEC_IDSECCION)"
      + "INNER JOIN BIOSLIS.CFG_LABORATORIOS clab ON (clab.CLAB_IDLABORATORIO = csec.CSEC_IDSECCION)"
      + "RIGHT OUTER JOIN BIOSLIS.LDV_INDICACIONESEXAMENES  ldvie ON (ldvie.LDVIE_IDINDICACIONESEXAMEN = ce.CE_IDINDICACIONEXAMEN)"
      + "WHERE cp.CPE_ACTIVO ='S' AND ce.CE_ACTIVO ='S' AND csec.csec_ACTIVA='S' AND clab.CLAB_ACTIVO = 'S' ORDER BY 1,2,4";

  private final static String hqlExamenxPanel = "SELECT cep,ce,csec,cm,clab,cpe  "
      + "from CfgExamenesPaneles cep inner join cep.cfgExamenes ce " + "inner join ce.cfgSecciones csec "
      + "inner join  csec.cfgLaboratorios clab " + "inner join ce.cfgMuestras cm "
      + "inner join  cep.cfgPanelesExamenes cpe " + "where " + "cpe.cpeActivo like 'S' "
      + " and  clab.clabActivo like 'S' " + "and ce.ceActivo like 'S' "
      + " order by cep.cfgExamenesPanelesId.cepIdpanelexamenes";

}
