package com.grupobios.bioslis.common;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.grupobios.bioslis.bs.ExamenService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.ResultadoNumericoTestExamenOrdenDTO;

public class DesbloquearTestExecutor implements ActionTestExecutor {

  @Autowired
  ExamenService examenService;

  @Override
  public void exec(ResultadoNumericoTestExamenOrdenDTO dfetId, Long idUsuario)
      throws BiosLISDAOException, BiosLISException, BiosLISDAONotFoundException, BiosLisExecutorException, IOException {
    if (examenService.readyToSign(dfetId.getDFET_IDTEST().longValue(), dfetId.getDF_NORDEN().longValue())) {
      examenService.sign(dfetId, idUsuario);
    } else {
      throw new BiosLisExecutorException("Examen no está listo para ser firmado.");
    }
//
//    List<ExamenOrdenDTO> lst = ordenService.readyToSign(dfetId.getDF_NORDEN().longValue());
//
//    if (lst == null || lst.isEmpty()) {
//
//      throw new BiosLISException("Orden " + dfetId.getDF_NORDEN().longValue() + " aun con firmas pendientes.");
//    }
//
//    String[] lstExamenes = new String[lst.size()];
//    int i = 0;
//    logger.debug("{} examenes listos para orden {}", lstExamenes.length, dfetId.getDF_NORDEN().longValue());
//    for (ExamenOrdenDTO datosFichasexamenestest : lst) {
//
//      logger.debug(" Exámen {}", datosFichasexamenestest.getDFE_IDEXAMEN().toString());
//      lstExamenes[i] = datosFichasexamenestest.getDFE_IDEXAMEN().toString();
//      logger.debug(" Exámen {}", lstExamenes[i]);
//      i++;
//    }
//
//    String[] destinatarios = { "daniel.delaguila@grupobios.cl" };
//    SendMailDTO sendMailDto = new SendMailDTO();
//    sendMailDto.setDestinatarios(destinatarios);
//    sendMailDto.setLstIdExamenes(lstExamenes);
//    this.informeResultadosService.sendExamenes(dfetId.getDF_NORDEN().longValueExact(), sendMailDto);
//    logger.debug("exec");

  }

  @Override
  public void exec(List<ExamenOrdenDTO> listaExamenes) {
    // TODO Auto-generated method stub

  }

}
