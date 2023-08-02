package com.grupobios.bioslis.common;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.grupobios.bioslis.bs.ExamenService;
import com.grupobios.bioslis.bs.InformeResultadosService;
import com.grupobios.bioslis.bs.OrdenService;
import com.grupobios.bioslis.bs.PdfService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.DatosFichasExamenesTestDAO;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.ResultadoNumericoTestExamenOrdenDTO;
import com.grupobios.bioslis.dto.SendMailDTO;

public class SignExamenExecutor implements ActionExamenExecutor {
  private static Logger logger = LogManager.getLogger(ActionExecutor.class);

  @Autowired
  private BioslisMailSender bioslisMailS;

  public BioslisMailSender getBioslisMailS() {
    return bioslisMailS;
  }

  public void setBioslisMailS(BioslisMailSender bioslisMailS) {
    this.bioslisMailS = bioslisMailS;
  }

  @Autowired
  private ExamenService examenService;

  @Autowired
  private PdfService pdfHelper;

  @Autowired
  private OrdenService ordenService;

  public OrdenService getOrdenService() {
    return ordenService;
  }

  public void setOrdenService(OrdenService ordenService) {
    this.ordenService = ordenService;
  }

  @Autowired
  private DatosFichasExamenesTestDAO datosFichasExamenesTestDAO;

  private InformeResultadosService informeResultadosService;

  public InformeResultadosService getInformeResultadosService() {
    return informeResultadosService;
  }

  public void setInformeResultadosService(InformeResultadosService informeResultadosService) {
    this.informeResultadosService = informeResultadosService;
  }

  public ExamenService getExamenService() {
    return examenService;
  }

  public void setExamenService(ExamenService examenService) {
    this.examenService = examenService;
  }

  void sign(ResultadoNumericoTestExamenOrdenDTO dfetId, Long idUsuario) {

  }

  @Override
  public void exec(ResultadoNumericoTestExamenOrdenDTO dfetId, Long idUsuario) throws BiosLISDAOException,
      BiosLISException, BiosLISDAONotFoundException, BiosLisExamenSignerException, IOException {

    if (examenService.readyToSign(dfetId.getDFE_IDEXAMEN().longValue(), dfetId.getDF_NORDEN().longValue())) {
      examenService.sign(dfetId, idUsuario);
    } else {
      throw new BiosLisExamenSignerException("Examen no está listo para ser firmado.");
    }

    List<ExamenOrdenDTO> lst = ordenService.readyToSign(dfetId.getDF_NORDEN().longValue());

    if (lst == null || lst.isEmpty()) {

      throw new BiosLisExamenSignerException(
          "Orden " + dfetId.getDF_NORDEN().longValue() + " aun con firmas pendientes.");
    }

    String[] lstExamenes = new String[lst.size()];
    int i = 0;
    logger.debug("{} examenes listos para orden {}", lstExamenes.length, dfetId.getDF_NORDEN().longValue());
    for (ExamenOrdenDTO datosFichasexamenestest : lst) {

      logger.debug(" Examen {}", datosFichasexamenestest.getDFE_IDEXAMEN().toString());
      lstExamenes[i] = datosFichasexamenestest.getDFE_IDEXAMEN().toString();
      logger.debug(" Examen {}", lstExamenes[i]);
      i++;
    }

    String[] destinatarios = { "daniel.delaguila@grupobios.cl" };
    SendMailDTO sendMailDto = new SendMailDTO();
    sendMailDto.setDestinatarios(destinatarios);
    sendMailDto.setLstIdExamenes(lstExamenes);
    //this.informeResultadosService.sendExamenes(dfetId.getDF_NORDEN().longValueExact(), sendMailDto);
    logger.debug("exec");

  }

  public PdfService getPdfHelper() {
    return pdfHelper;
  }

  public void setPdfHelper(PdfService pdfHelper) {
    this.pdfHelper = pdfHelper;
  }

  public DatosFichasExamenesTestDAO getDatosFichasExamenesTestDAO() {
    return datosFichasExamenesTestDAO;
  }

  public void setDatosFichasExamenesTestDAO(DatosFichasExamenesTestDAO datosFichasExamenesTestDAO) {
    this.datosFichasExamenesTestDAO = datosFichasExamenesTestDAO;
  }

  @Override
  public void exec(List<ExamenOrdenDTO> listaExamenes, Long idUsuario) {

    this.examenService.sign(listaExamenes, idUsuario);
  }

}
