package com.grupobios.bioslis.common;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.grupobios.bioslis.bs.ExamenService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.ResultadoNumericoTestExamenOrdenDTO;

public class ExamenNoExecutor implements ActionExamenExecutor {

  @Autowired
  ExamenService examenService;

  @Override
  public void exec(ResultadoNumericoTestExamenOrdenDTO dfetId, Long idUsuario) throws BiosLISDAOException,
      BiosLISException, BiosLISDAONotFoundException, BiosLisExamenSignerException, IOException {

  }

  @Override
  public void exec(List<ExamenOrdenDTO> listaExamenes, Long idUsuario) {

  }

  public ExamenService getExamenService() {
    return examenService;
  }

  public void setExamenService(ExamenService examenService) {
    this.examenService = examenService;
  }

}
