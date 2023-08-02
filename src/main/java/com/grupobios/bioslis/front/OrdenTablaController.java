package com.grupobios.bioslis.front;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.grupobios.bioslis.dao.CfgConvenioDAO;
import com.grupobios.bioslis.dao.CfgDiagnosticosDAO;
import com.grupobios.bioslis.dao.CfgExamenesDAO;
import com.grupobios.bioslis.dao.CfgInstitucionesDeSaludDAO;
import com.grupobios.bioslis.dao.CfgLocalizacionesDAO;
import com.grupobios.bioslis.dao.CfgMedicosDAO;
import com.grupobios.bioslis.dao.CfgPrioridadAtencionDAO;
import com.grupobios.bioslis.dao.CfgProcedenciasDAO;
import com.grupobios.bioslis.dao.CfgServiciosDAO;
import com.grupobios.bioslis.dao.CfgTipoAtencionDAO;
import com.grupobios.bioslis.dao.DatosFichasDAO;
import com.grupobios.bioslis.dao.LdvSexoDAO;
import com.grupobios.bioslis.dao.LdvTiposdocumentosDAO;

@Controller
public class OrdenTablaController {

  private static Logger logger = LogManager.getLogger(OrdenTablaController.class);

  @Autowired
  private DatosFichasDAO datosFichasDAO;
  private CfgLocalizacionesDAO cfgLocalizacionesDAO;

  ModelAndView mav = new ModelAndView();

  @RequestMapping("/OrdenTabla")
  public ModelAndView pageLoad() throws BiosLISDAOException {
    LdvTiposdocumentosDAO tiposDocumentosDAO = new LdvTiposdocumentosDAO();//
    LdvSexoDAO sexoDAO = new LdvSexoDAO();
    CfgPrioridadAtencionDAO prior = new CfgPrioridadAtencionDAO();
    CfgTipoAtencionDAO tipoAt = new CfgTipoAtencionDAO();
    CfgProcedenciasDAO getProce = new CfgProcedenciasDAO();
    CfgConvenioDAO getConvenio = new CfgConvenioDAO();
    CfgInstitucionesDeSaludDAO institSalud = new CfgInstitucionesDeSaludDAO();
    CfgExamenesDAO getExamn = new CfgExamenesDAO();
    CfgDiagnosticosDAO getDiag = new CfgDiagnosticosDAO();
    CfgServiciosDAO getServicios = new CfgServiciosDAO();
    CfgMedicosDAO med = new CfgMedicosDAO();

    mav.addObject("listaServicios", getServicios.getServicios());
    mav.addObject("listaInsitucionesSalud", institSalud.getInstitucionesDeSalud());
    mav.addObject("listaConvenio", getConvenio.getConvenios());
    mav.addObject("listaSexo", sexoDAO.getSexo());
    mav.addObject("listaExamen", getExamn.getExamenes());
    //mav.addObject("listaProce", getProce.getProcedencias());
    mav.addObject("listaTiposDocumentos", tiposDocumentosDAO.getTiposDocumentoRutPasaporte());
    mav.addObject("listaMedicos", med.getAllMedics());
//    mav.addObject("listaPrior", prior.getPrior());
    mav.addObject("listaTipoAt", tipoAt.getTipoAtencion());
    mav.addObject("listaDiag", getDiag.getDiagnosticos());
    mav.setViewName("OrdenTabla");
    return mav;
  }

  /**
   * @return the datosFichasDAO
   */
  public DatosFichasDAO getDatosFichasDAO() {
    return datosFichasDAO;
  }

  /**
   * @param datosFichasDAO the datosFichasDAO to set
   */
  public void setDatosFichasDAO(DatosFichasDAO datosFichasDAO) {
    this.datosFichasDAO = datosFichasDAO;
  }

  /**
   * @return the cfgLocalizacionesDAO
   */
  public CfgLocalizacionesDAO getCfgLocalizacionesDAO() {
    return cfgLocalizacionesDAO;
  }

  /**
   * @param cfgLocalizacionesDAO the cfgLocalizacionesDAO to set
   */
  public void setCfgLocalizacionesDAO(CfgLocalizacionesDAO cfgLocalizacionesDAO) {
    this.cfgLocalizacionesDAO = cfgLocalizacionesDAO;
  }

  /*
   * private DatosFichas registroOrdenBS(DatosFichasDTO medReq, String[] examenes,
   * String ipEquipo) throws NumberFormatException, BiosLISDAOException,
   * BiosLISException {
   * 
   * DatosFichas med = medReq.toDatosFicha(); if (med.getCfgDiagnosticos() == 0) {
   * med.setCfgDiagnosticos(1); }
   * 
   * if (med.getCfgProcedencias() == Constante.SIN_PROCEDENCIA) {
   * med.setDfCodigolocalizacion("");
   * med.setCfgLocalizaciones(Constante.SIN_LOCALIZAR); } else { Integer idSalas =
   * medReq.getSalas() == null ? 0 : (medReq.getSalas().equals("N") ? 0 :
   * Integer.valueOf(medReq.getSalas())); Integer idCamas = medReq.getCamas() ==
   * null ? 0 : (medReq.getCamas().equals("N") ? 0 :
   * Integer.valueOf(medReq.getCamas())); Integer idServicios =
   * medReq.getCfgServicios() == null ? 0 : (medReq.getCfgServicios().equals("N")
   * ? 0 : Integer.valueOf(medReq.getCfgServicios()));
   * 
   * CfgLocalizaciones cl = cfgLocalizacionesDAO.getLocalizacion(idServicios,
   * idSalas, idCamas);
   * 
   * if (cl != null) { med.setDfCodigolocalizacion(cl.getClCodigolocalizacion());
   * med.setCfgLocalizaciones(cl.getClIdlocalizacion()); } else {
   * med.setDfCodigolocalizacion("");
   * med.setCfgLocalizaciones(Constante.SIN_LOCALIZAR); } } //
   * datosFichasDAO.insertOrden(med, examenes, ipEquipo); return med; }
   */
}
