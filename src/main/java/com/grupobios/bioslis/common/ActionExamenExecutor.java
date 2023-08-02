package com.grupobios.bioslis.common;

import java.io.IOException;
import java.util.List;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.ResultadoNumericoTestExamenOrdenDTO;

public interface ActionExamenExecutor extends ActionExecutor {

  public void exec(ResultadoNumericoTestExamenOrdenDTO dfetId, Long idUsuario) throws BiosLISDAOException,
      BiosLISException, BiosLISDAONotFoundException, BiosLisExamenSignerException, IOException;

  void exec(List<ExamenOrdenDTO> listaExamenes, Long idUsuario) throws BiosLISDAOException;

}
