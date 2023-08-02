package com.grupobios.bioslis.cfg.cache;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgCentrosDeSaludDAO;
import com.grupobios.bioslis.entity.CfgCentrosdesalud;

public class CfgCentrosDeSaludAbsDao implements TDao<CfgCentrosdesalud> {

  private static CfgCentrosDeSaludDAO ref;
  private static CfgCentrosDeSaludAbsDao instance = null;
  private static List<CfgCentrosdesalud> lista;

  private static Map<Integer, CfgCentrosdesalud> cachedData;

  public static CfgCentrosDeSaludAbsDao init(CfgCentrosDeSaludDAO ref) throws BiosLISDAOException {
    if (instance == null) {
      instance = new CfgCentrosDeSaludAbsDao(ref);
      fill();

    }
    return instance;
  }

  public static CfgCentrosDeSaludAbsDao getInstance() throws BiosLISException {

    if (instance == null) {
      throw new BiosLISException("Singleton CfgTipoAtencionAbsDao no instanciado");
    }
    return instance;
  }

  private static void fill() throws BiosLISDAOException {
    lista = ref.getCentrosDeSalud();
    int n = 0;
    if (lista != null) {
      n = lista.size();
    }
    cachedData = new HashMap<Integer, CfgCentrosdesalud>(n);
    if (n == 0)
      return;
    for (CfgCentrosdesalud cfgCds : lista) {
      cachedData.put(Short.toUnsignedInt(cfgCds.getCcdsIdcentrodesalud()), cfgCds);
    }
  }

  private CfgCentrosDeSaludAbsDao(CfgCentrosDeSaludDAO refOr) {
    ref = refOr;
  }

  @Override
  public List<CfgCentrosdesalud> getAll() throws BiosLISDAOException {
    return lista;
  }

  public static CfgCentrosdesalud getById(Integer id) throws BiosLISDAOException, BiosLISException {
    return getInstance().cachedData.get(id);
  }

  public static String getDescripcionById(Integer id) throws BiosLISDAOException, BiosLISException {
    return getInstance().cachedData.get(id).getCcdsDescripcion();
  }

  public static String getDescripcionById(BigDecimal id) throws BiosLISDAOException, BiosLISException {
    return getInstance().getDescripcionById(id.intValueExact());
  }

}
