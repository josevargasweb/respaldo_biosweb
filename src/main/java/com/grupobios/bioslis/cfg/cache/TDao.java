package com.grupobios.bioslis.cfg.cache;

import java.util.List;

import com.grupobios.bioslis.dao.BiosLISDAOException;

public interface TDao<T> {

  public List<T> getAll() throws BiosLISDAOException;

}
