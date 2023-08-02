package com.grupobios.bioslis.common;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.grupobios.bioslis.bs.ExamenService;
import com.grupobios.bioslis.bs.OrdenService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.DatosFichasexamenes;

public class SignOrdenExecutor implements ActionOrdenExecutor {
  private static Logger logger = LogManager.getLogger(ActionExecutor.class);

  @Autowired
  private ExamenService examenService;

  @Autowired
  private OrdenService ordenService;

  public OrdenService getOrdenService() {
    return ordenService;
  }

  public void setOrdenService(OrdenService ordenService) {
    this.ordenService = ordenService;
  }

  @Override
  public void exec(Long nroOrden, Long idUsuario) throws BiosLISDAOException, BiosLisExecutorException {

    List<DatosFichasexamenes> lstExamenes = ordenService.getExamenes(nroOrden);

    for (DatosFichasexamenes dfe : lstExamenes) {

      if (examenService.readyToSign(dfe.getIddatosFichasExamenes().getDfeNorden(),
          dfe.getIddatosFichasExamenes().getDfeIdexamen())) {
        examenService.sign(dfe.getIddatosFichasExamenes(), idUsuario);
      } else {
        throw new BiosLisExecutorException("Examen no está listo para ser firmado.");
      }
    }

    logger.debug("exec");

  }

  public ExamenService getExamenService() {
    return examenService;
  }

  public void setExamenService(ExamenService examenService) {
    this.examenService = examenService;
  }

}
