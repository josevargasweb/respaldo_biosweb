package com.grupobios.bioslis.common;

import com.grupobios.bioslis.dao.BiosLISDAOException;

public interface ActionOrdenExecutor extends ActionExecutor {

  void exec(Long nroOrden, Long idUsuario) throws BiosLISDAOException, BiosLisExecutorException;

}
