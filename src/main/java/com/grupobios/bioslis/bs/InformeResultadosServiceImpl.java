/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.BioslisMailSender;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgServiciosDAO;
import com.grupobios.bioslis.dao.CfgTipoAtencionDAO;
import com.grupobios.bioslis.dao.DatosFichasExamenesDAO;
import com.grupobios.bioslis.dao.DatosFichas_DAO;
import com.grupobios.bioslis.dao.LdvTiposdocumentosDAO;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.FichaFiltroDTO;
import com.grupobios.bioslis.dto.OrdenExamenCapturaResultadoDTO;
import com.grupobios.bioslis.dto.OrdenInformeResultadoDTO;
import com.grupobios.bioslis.dto.SendMailDTO;
import com.grupobios.bioslis.entity.CfgServicios;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.LdvTiposdocumentos;

/**
 *
 * @author eric.nicholls
 */

@Service // pasar a annotations más adelante.
public class InformeResultadosServiceImpl implements InformeResultadosService {

  private static Logger logger = LogManager.getLogger(InformeResultadosServiceImpl.class);
  private DatosFichas_DAO datosFichas_dao;
  private LdvTiposdocumentosDAO ldvTiposdocumentos_dao;
  private CfgTipoAtencionDAO cfgTipoAtencionDAO;

  public CfgTipoAtencionDAO getCfgTipoAtencionDAO() {
    return cfgTipoAtencionDAO;
  }

  public void setCfgTipoAtencionDAO(CfgTipoAtencionDAO cfgTipoAtencionDAO) {
    this.cfgTipoAtencionDAO = cfgTipoAtencionDAO;
  }

  private CfgServiciosDAO servicios_dao;
  private DatosFichasExamenesDAO datosFichasExamenesDAO;

  @Autowired
  private BioslisMailSender bioslisMailS;

  @Autowired
  private PdfService pdfHelper;

  public BioslisMailSender getBioslisMailS() {
    return bioslisMailS;
  }

  public void setBioslisMailS(BioslisMailSender bioslisMailS) {
    this.bioslisMailS = bioslisMailS;
  }

  public PdfService getPdfHelper() {
    return pdfHelper;
  }

  public void setPdfHelper(PdfService pdfHelper) {
    this.pdfHelper = pdfHelper;
  }

  public InformeResultadosServiceImpl() {
    logger.debug(InformeResultadosServiceImpl.class.getCanonicalName());
  }

  @Override
  public ModelAndView fill(ModelAndView mav) {
    try {
      mav.addObject("listaTiposDocumentos", ldvTiposdocumentos_dao.getTiposDocumentoRutPasaporte());
      mav.addObject("listaTiposAtencion", cfgTipoAtencionDAO.getTipoAtencion());
      mav.addObject("listaServicios", servicios_dao.getServicios());
      mav.addObject("listaServiciosConMail", this.getlistaServiciosConMail(servicios_dao.getServicios()));
      logger.debug("Fill lista resultadis");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(InformeResultadosServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return mav;
  }

  @Override
  public List<DatosFichas> getOrdenes() throws BiosLISDAOException {
    return datosFichas_dao.getOrdenes();
  }

  @Override
  public List<OrdenInformeResultadoDTO> getOrdenesInformeResultados(FichaFiltroDTO ffDto)
      throws BiosLISException, BiosLISDAOException {
    return datosFichas_dao.getOrdenesInformeResultados(ffDto);
  }

  @Override
  public List<LdvTiposdocumentos> getTiposDocumentos() {
    return ldvTiposdocumentos_dao.getTiposDocumentos();
  }

  @Override
  public List<ExamenOrdenDTO> getExamenesOrden() {
    return datosFichas_dao.getExamenesOrden();
  }

  public void setDatosFichas_dao(DatosFichas_DAO datosFichas_dao) {
    this.datosFichas_dao = datosFichas_dao;
  }

  public LdvTiposdocumentosDAO getLdvTiposdocumentos_dao() {
    return ldvTiposdocumentos_dao;
  }

  public void setLdvTiposdocumentos_dao(LdvTiposdocumentosDAO ldvTiposdocumentos_dao) {
    this.ldvTiposdocumentos_dao = ldvTiposdocumentos_dao;
  }

  public CfgServiciosDAO getServicios_dao() {
    return servicios_dao;
  }

  public void setServicios_dao(CfgServiciosDAO servicios_dao) {
    this.servicios_dao = servicios_dao;
  }

  public DatosFichasExamenesDAO getDatosFichasExamenesDAO() {
    return datosFichasExamenesDAO;
  }

  public void setDatosFichasExamenesDAO(DatosFichasExamenesDAO datosFichasExamenesDAO) {
    this.datosFichasExamenesDAO = datosFichasExamenesDAO;
  }

  @Override
  public List<ExamenOrdenDTO> getExamenesOrden(Long nroOrden) {

    return datosFichas_dao.getExamenesOrden(nroOrden);
  }

  private static final String asunto = "Test";
  private static final String cuerpo = "Test";

  @Override
  public List<ExamenOrdenDTO> sendExamenes(Long nroOrden, SendMailDTO sendMailDto)
      throws BiosLISException, BiosLISDAOException {
    try {
      int n = sendMailDto.getLstIdExamenes().length;
      List<Long> listaIdExamenes = new ArrayList<>(n);

      for (int i = 0; i < n; i++) {
        if (StringUtils.isNumeric(sendMailDto.getLstIdExamenes()[i])) {
          listaIdExamenes.add(Long.valueOf(sendMailDto.getLstIdExamenes()[i]));
        } else {
          logger.error("Identificador de {}-ésimo exámen inválido [{}]", i, sendMailDto.getLstIdExamenes()[i]);
          throw new BiosLISBSException("Lista de identificadores de examenes inválidos.");
        }
      }
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      // this.pdfHelper.fillByteArrayOutputStreamReport(nroOrden, listaIdExamenes,
      // output);
//      this.pdfHelper.fillByteArrayOutputStreamCrReport(nroOrden, listaIdExamenes, output);

      for (String destinatario : sendMailDto.getDestinatarios()) {
        this.bioslisMailS.enviarMail(destinatario, asunto, cuerpo, output);
      }
      this.datosFichasExamenesDAO.updateByMail(nroOrden, sendMailDto);
    } catch (Exception e) {
      logger.error(e.getLocalizedMessage());
      throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e);
    }
    return null;// datosFichas_dao.getExamenesOrden(nroOrden);
  }

  @Override
  public List<ExamenOrdenDTO> sendExamen(Long nroOrden, Long idExamen, SendMailDTO sendMailDto)
      throws BiosLISBSException, BiosLISJasperException, BiosLISException, BiosLISDAOException {

    int n = sendMailDto.getLstIdExamenes().length;
    List<Long> listaIdExamenes = new ArrayList<>(n);
    try {
      for (int i = 0; i < n; i++) {
        if (StringUtils.isNumeric(sendMailDto.getLstIdExamenes()[i])) {
          listaIdExamenes.add(Long.valueOf(sendMailDto.getLstIdExamenes()[i]));
        } else {
          logger.error("Identificador de {}-ésimo exámen inválido [{}]", i, sendMailDto.getLstIdExamenes()[i]);
          throw new BiosLISBSException("Lista de identificadores de examenes inválidos.");
        }
      }
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      this.pdfHelper.fillByteArrayOutputStreamReport(nroOrden, listaIdExamenes, output);

      for (String destinatario : sendMailDto.getDestinatarios()) {
        this.bioslisMailS.enviarMail(destinatario, asunto, cuerpo, output);
      }
      this.datosFichasExamenesDAO.updateByMail(nroOrden, sendMailDto);
    } catch (Exception e) {
      logger.error(e.getLocalizedMessage());
      throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e);
    }
    return null;// datosFichas_dao.getExamenesOrden(nroOrden);
  }

  private List<CfgServicios> getlistaServiciosConMail(List<CfgServicios> servicios) {
    List<CfgServicios> listaServiciosConMail = new ArrayList<>(servicios_dao.getServicios());
    servicios_dao.getServicios().removeIf(svc -> svc.getCsActivo().equals("N") || svc.getCsActivo() == null
        || svc.getCsActivo().isEmpty() || svc.getCsEmail() == null || svc.getCsEmail().trim().isEmpty());

    return listaServiciosConMail;
  }

  @Override
  public HashMap<String, Object> updateInformeResultado(OrdenExamenCapturaResultadoDTO mOD)
      throws BiosLISDAOException, BiosLISException, ParseException {
    // TODO Auto-generated method stub
    return DatosFichas_DAO.updateInformeResultado(mOD);
  }

  @Override
  public ExamenOrdenDTO getOrdenesxExamen(Long nroOrden, Long idExamen) throws BiosLISDAOException {
    // TODO Auto-generated method stub
    return DatosFichas_DAO.getOrdenesxExamen(nroOrden, idExamen);
  }

  @Override
  public List<ExamenOrdenDTO> imprimirExamenes(Long nroOrden, SendMailDTO sendMailDto)
      throws BiosLISBSException, BiosLISJasperException, BiosLISException, BiosLISDAOException {
    this.datosFichasExamenesDAO.updateByImprimir(nroOrden, sendMailDto);
    return null;
  }
}
