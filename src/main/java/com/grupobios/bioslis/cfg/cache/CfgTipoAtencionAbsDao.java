package com.grupobios.bioslis.cfg.cache;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgTipoAtencionDAO;
import com.grupobios.bioslis.entity.CfgTipoatencion;

public class CfgTipoAtencionAbsDao implements TDao<CfgTipoatencion> {

  private static CfgTipoAtencionDAO ref;
  private static CfgTipoAtencionAbsDao instance = null;
  private static List<CfgTipoatencion> lista;

  private static Map<Integer, CfgTipoatencion> cachedData;

  public static CfgTipoAtencionAbsDao init(CfgTipoAtencionDAO ref) throws BiosLISDAOException {
    if (instance == null) {
      instance = new CfgTipoAtencionAbsDao(ref);
      fill();

    }
    return instance;
  }

  public static CfgTipoAtencionAbsDao getInstance() throws BiosLISException {

    if (instance == null) {
      throw new BiosLISException("Singleton CfgTipoAtencionAbsDao no instanciado");
    }
    return instance;
  }

  private static void fill() throws BiosLISDAOException {
    lista = ref.getTiposAtencion();
    int n = 0;
    if (lista != null) {
      n = lista.size();
    }
    cachedData = new HashMap<Integer, CfgTipoatencion>(n);
    if (n == 0)
      return;
    for (CfgTipoatencion cfgTipoatencion : lista) {
      cachedData.put(cfgTipoatencion.getId(), cfgTipoatencion);
    }
  }

  private CfgTipoAtencionAbsDao(CfgTipoAtencionDAO refOr) {
    ref = refOr;
  }

  @Override
  public List<CfgTipoatencion> getAll() throws BiosLISDAOException {
    return lista;
  }

  public static CfgTipoatencion getById(Integer id) throws BiosLISDAOException, BiosLISException {
    return getInstance().cachedData.get(id);
  }

  public static String getDescripcionById(Integer id) throws BiosLISDAOException, BiosLISException {
    return getInstance().cachedData.get(id).getCtaDescripcion();
  }

  public static String getDescripcionById(BigDecimal id) throws BiosLISDAOException, BiosLISException {
    return getInstance().getDescripcionById(id.intValueExact());
  }

}
