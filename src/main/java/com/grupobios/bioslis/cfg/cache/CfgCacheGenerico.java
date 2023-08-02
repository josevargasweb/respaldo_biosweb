package com.grupobios.bioslis.cfg.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.grupobios.bioslis.config.SpringContext;
import com.grupobios.bioslis.dao.BiosLISDAOException;

public class CfgCacheGenerico<T extends CfgAbstracto, U> implements TDao<T> {

  public CfgCacheGenerico(TDao<T> tDao) {

    instanceCounter = -1;
    dao = tDao;
  }

  private T t;
  private TDao<?> dao;
  private static Boolean isDirty = true;
  private Map<Integer, T> data;

  public static Boolean getIsDirty() {
    return isDirty;
  }

  public static void setIsDirty(Boolean isDirty) {
    CfgCacheGenerico.isDirty = isDirty;
  }

  private static int instanceCounter = 0;
  private CfgCacheGenerico<T, U> instance = null;
  private List<T> lista;

  public <T> CfgCacheGenerico getInstance() throws BiosLISDAOException {
    if (instance == null && instanceCounter == -1) {
//      instance = null;//new CfgCacheGenerico();
      dao = SpringContext.getBean(dao.getClass());
      if (dao == null) {
        throw new RuntimeException();
      }
      isDirty = false;
      instanceCounter = 0;
      fillData();
    }
    return instance;
  }

  public T getById(int id) throws BiosLISDAOException {
    return data.get(id);
  }

  private void fillData() throws BiosLISDAOException {

    List<T> lst = getAll();
    int n = 0;
    if (lista != null) {
      n = lista.size();
    }
    data = new HashMap<>(n);
    for (int i = 0; i < n; i++) {
      T element = lst.get(i);
      Integer idx = element.getId();
      data.put(idx, element);
    }
  }

  @Override
  public List<T> getAll() throws BiosLISDAOException {
    if (lista != null && !isDirty) {
      return lista;
    } else {
      lista = (List<T>) dao.getAll();
    }
    return lista;
  }

}
