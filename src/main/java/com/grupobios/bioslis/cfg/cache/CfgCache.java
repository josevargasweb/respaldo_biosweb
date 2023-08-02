package com.grupobios.bioslis.cfg.cache;

import java.util.List;
import java.util.Map;

import com.grupobios.bioslis.config.SpringContext;

public class CfgCache<T, U extends TDao> {

  public CfgCache(Class<?> daoClass) {
    this.daoClass = daoClass;
  }

  public CfgCache() {
  }

  private static Class<?> daoClass;
  private static Boolean isDirty = true;
  private T cfgDAO;
  private Map<Integer, U> data;
  private static int counter = 0;

  public static Boolean getIsDirty() {
    return isDirty;
  }

  public static void setIsDirty(Boolean isDirty) {
    CfgCache.isDirty = isDirty;
  }

  private static CfgCache instance = null;

  public CfgCache getInstance() {
    if (counter == 0) {
      counter = 1;
      cfgDAO = (T) SpringContext.getBean(daoClass);
      if (cfgDAO == null) {
        throw new RuntimeException();
      }
      isDirty = false;
//      fillData();
      instance = this;
    }
    return this;
  }

  private List<U> lista;

//  public List<U> getAll() throws BiosLISDAOException {
//
//    T ldvsDao = (T) cfgDAO;
//    TDao<U> tDao = CfgDaoContainerFactory.get(ldvsDao);
//    getInstance();
//    if (lista != null && !isDirty) {
//      return lista;
//    } else {
//      lista = tDao.getAll();
//    }
//    return lista;
//  };
//
//  public U getById(Integer id) throws BiosLISDAOException {
//    getInstance();
//    return data.get(id);
//  }
//
//  private void fillData() {
//    List<U> lst = getAll();
//    int n = 0;
//    if (lista != null) {
//      n = lista.size();
//    }
//    data = new HashMap<>(n);
//    for (int i = 0; i < n; i++) {
//      data.put(lst.get(i).getElementId(), lst.get(i));
//    }
//  }

}
