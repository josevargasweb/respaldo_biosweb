package com.grupobios.bioslis.cfg.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.grupobios.bioslis.config.SpringContext;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.LdvSexoDAO;
import com.grupobios.bioslis.entity.LdvSexo;

public class CfgCacheSexo<T> {

  public CfgCacheSexo(Class<?> daoClass) {
    this.daoClass = daoClass;
  }

  public CfgCacheSexo() {
  }

  private static Class<?> daoClass;
  private static Boolean isDirty = true;
  private T cfgDAO;
  private static Map<Integer, LdvSexo> data;
  private static int counter = 0;

  public static Boolean getIsDirty() {
    return isDirty;
  }

  public static void setIsDirty(Boolean isDirty) {
    CfgCacheSexo.isDirty = isDirty;
  }

  private static CfgCacheSexo instance = null;

  public CfgCacheSexo getInstance() {
    if (counter == 0) {
      counter = 1;
      cfgDAO = (T) SpringContext.getBean(daoClass);
      if (cfgDAO == null) {
        throw new RuntimeException();
      }
      isDirty = false;
      fillData();
      instance = this;
    }
    return this;
  }

  private static List<LdvSexo> lista;

  public List<LdvSexo> getAll() {

    LdvSexoDAO ldvsDao = (LdvSexoDAO) cfgDAO;
    getInstance();
    if (lista != null && !isDirty) {
      return lista;
    } else {
      lista = ldvsDao.getSexo();
    }
    return lista;
  };

  public LdvSexo getById(Integer id) throws BiosLISDAOException {
    getInstance();
    return data.get(id);
  }

  private void fillData() {
    List<LdvSexo> lst = getAll();
    int n = 0;
    if (lista != null) {
      n = lista.size();
    }
    data = new HashMap<>(n);
    for (int i = 0; i < n; i++) {
      data.put(lst.get(i).getLdvsIdsexo(), lst.get(i));
    }
  }

}
