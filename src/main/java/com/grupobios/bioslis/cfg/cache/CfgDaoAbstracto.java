package com.grupobios.bioslis.cfg.cache;

import java.math.BigDecimal;
import java.util.List;

import com.grupobios.bioslis.dao.BiosLISDAOException;

public interface CfgDaoAbstracto {

  public List<CfgAbstracto> getAll() throws BiosLISDAOException;

  public CfgAbstracto getById(Integer id) throws BiosLISDAOException;

  public String getDescripcionById(Integer id) throws BiosLISDAOException;

  String getDescripcionById(BigDecimal id) throws BiosLISDAOException;

}
