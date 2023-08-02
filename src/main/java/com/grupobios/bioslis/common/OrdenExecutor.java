package com.grupobios.bioslis.common;

import java.io.IOException;

import com.grupobios.bioslis.bs.BiosLISJasperException;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dto.ResultadoNumericoTestExamenOrdenDTO;

public interface OrdenExecutor extends ActionExecutor {

  void exec(ResultadoNumericoTestExamenOrdenDTO dfetId) throws BiosLISJasperException, BiosLISDAOException,
      BiosLISException, BiosLISDAONotFoundException, BiosLisExecutorException, IOException;

}
