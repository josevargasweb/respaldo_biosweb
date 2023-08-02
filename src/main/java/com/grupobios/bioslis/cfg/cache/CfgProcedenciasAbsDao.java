package com.grupobios.bioslis.cfg.cache;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgProcedenciasDAO;
import com.grupobios.bioslis.entity.CfgProcedencias;

public class CfgProcedenciasAbsDao implements TDao<CfgProcedencias> {

  private static CfgProcedenciasDAO ref;
  private static CfgProcedenciasAbsDao instance = null;
  private static List<CfgProcedencias> lista;

  private static Map<Integer, CfgProcedencias> cachedData;

  public static CfgProcedenciasAbsDao init(CfgProcedenciasDAO ref) throws BiosLISDAOException {
    if (instance == null) {
      instance = new CfgProcedenciasAbsDao(ref);
      fill();

    }
    return instance;
  }

  public static CfgProcedenciasAbsDao getInstance() throws BiosLISException {

    if (instance == null) {
      throw new BiosLISException("Singleton CfgProcedenciasAbsDao no instanciado");
    }
    return instance;
  }

  private static void fill() throws BiosLISDAOException {
    lista = ref.getProcedenciasHistoricas();
    int n = 0;
    if (lista != null) {
      n = lista.size();
    }
    cachedData = new HashMap<Integer, CfgProcedencias>(n);
    if (n == 0)
      return;
    for (CfgProcedencias cfgProcedencias : lista) {
      cachedData.put(cfgProcedencias.getId(), cfgProcedencias);
    }
  }

  private CfgProcedenciasAbsDao(CfgProcedenciasDAO refOr) {
    ref = refOr;
  }

  @Override
  public List<CfgProcedencias> getAll() throws BiosLISDAOException {
    return lista;
  }

  public static CfgProcedencias getById(Integer id) throws BiosLISDAOException, BiosLISException {
    return getInstance().cachedData.get(id);
  }

  public static String getDescripcionById(Integer id) throws BiosLISDAOException, BiosLISException {
    return getInstance().cachedData.get(id).getCpDescripcion();
  }

  public static String getDescripcionById(BigDecimal id) throws BiosLISDAOException, BiosLISException {
    return getInstance().getDescripcionById(id.intValueExact());
  }

}
