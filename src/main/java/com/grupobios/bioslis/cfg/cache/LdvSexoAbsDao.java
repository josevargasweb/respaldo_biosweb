package com.grupobios.bioslis.cfg.cache;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.LdvSexoDAO;
import com.grupobios.bioslis.entity.LdvSexo;

public class LdvSexoAbsDao implements TDao<LdvSexo> {

  private static LdvSexoDAO ref;
  private static LdvSexoAbsDao instance = null;
  private static List<LdvSexo> lista;

  private static Map<Integer, LdvSexo> cachedData;

  public static LdvSexoAbsDao init(LdvSexoDAO ref) throws BiosLISDAOException {
    if (instance == null) {
      instance = new LdvSexoAbsDao(ref);
      fill();

    }
    return instance;
  }

  public static LdvSexoAbsDao getInstance() throws BiosLISException {

    if (instance == null) {
      throw new BiosLISException("Singleton LdvSexoAbsDao no instanciado");
    }
    return instance;
  }

  private static void fill() throws BiosLISDAOException {
    lista = ref.getSexo();
    int n = 0;
    if (lista != null) {
      n = lista.size();
    }
    cachedData = new HashMap<Integer, LdvSexo>(n);
    if (n == 0)
      return;
    for (LdvSexo ldvs : lista) {
      cachedData.put(ldvs.getId(), ldvs);
    }
  }

  private LdvSexoAbsDao(LdvSexoDAO refOr) {
    ref = refOr;
  }

  @Override
  public List<LdvSexo> getAll() throws BiosLISDAOException {
    return lista;
  }

  public static LdvSexo getById(Integer id) throws BiosLISDAOException, BiosLISException {
    return getInstance().cachedData.get(id);
  }

  public static String getDescripcionById(Integer id) throws BiosLISDAOException, BiosLISException {
    return getInstance().cachedData.get(id).getDescripcion();
  }

  public static String getCodigoById(Integer id) throws BiosLISDAOException, BiosLISException {
    return getInstance().cachedData.get(id).getLdvsCodigo();
  }

  public static String getDescripcionById(BigDecimal id) throws BiosLISDAOException, BiosLISException {
    return getInstance().getDescripcionById(id.intValueExact());
  }

}
