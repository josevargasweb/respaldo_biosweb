package com.grupobios.bioslis.cfg.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.grupobios.bioslis.config.SpringContext;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgSeccionesDAO;
import com.grupobios.bioslis.entity.CfgSecciones;

public class CfgCacheCfgSecciones {

  private CfgCacheCfgSecciones() {
  }

  private static CfgSeccionesDAO Dao;
  private static Boolean isDirty = true;
  private static Map<Integer, CfgSecciones> data;

  public static Boolean getIsDirty() {
    return isDirty;
  }

  public static void setIsDirty(Boolean isDirty) {
    CfgCacheCfgSecciones.isDirty = isDirty;
  }

  private static CfgCacheCfgSecciones instance = null;
  private static List<CfgSecciones> lista;

  public static CfgCacheCfgSecciones getInstance() throws BiosLISDAOException {
    if (instance == null) {
      instance = new CfgCacheCfgSecciones();
      Dao = SpringContext.getBean(CfgSeccionesDAO.class);
      if (Dao == null) {
        throw new RuntimeException();
      }
      lista = Dao.getSecciones();
      isDirty = false;
      getAll();
      fillData();
    }
    return instance;
  }

  public static List<CfgSecciones> getAll() throws BiosLISDAOException {

    if (lista != null && !isDirty) {
      return lista;
    } else {
      lista = Dao.getSecciones();
    }
    return lista;
  };

  public static CfgSecciones getById(Integer id) throws BiosLISDAOException {
    return data.get(id);
  }

  private static void fillData() throws BiosLISDAOException {

    List<CfgSecciones> lst = getAll();
    int n = 0;
    if (lista != null) {
      n = lista.size();
    }
    data = new HashMap<>(n);
    for (int i = 0; i < n; i++) {
      data.put(lst.get(i).getCsecIdseccion(), lst.get(i));
    }
  }
}
