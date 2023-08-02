package com.grupobios.bioslis.cfg.cache;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.LdvFormatosDAO;
import com.grupobios.bioslis.entity.LdvFormatos;

public class LdvFormatosAbsDao implements TDao<LdvFormatos> {

  private static LdvFormatosDAO ref;
  private static LdvFormatosAbsDao instance = null;
  private static List<LdvFormatos> lista;

  private static Map<Integer, LdvFormatos> cachedData;

  public static LdvFormatosAbsDao init(LdvFormatosDAO ref) throws BiosLISDAOException {
    if (instance == null) {
      instance = new LdvFormatosAbsDao(ref);
      fill();

    }
    return instance;
  }

  public static LdvFormatosAbsDao getInstance() throws BiosLISException {

    if (instance == null) {
      throw new BiosLISException("Singleton LdvFormatosAbsDao no instanciado");
    }
    return instance;
  }

  private static void fill() throws BiosLISDAOException {
    lista = ref.getListFormatos();
    int n = 0;
    if (lista != null) {
      n = lista.size();
    }
    cachedData = new HashMap<Integer, LdvFormatos>(n);
    if (n == 0)
      return;
    for (LdvFormatos ldvs : lista) {
      cachedData.put(Byte.toUnsignedInt(ldvs.getLdvfIdformato()), ldvs);
    }
  }

  private LdvFormatosAbsDao(LdvFormatosDAO refOr) {
    ref = refOr;
  }

  @Override
  public List<LdvFormatos> getAll() throws BiosLISDAOException {
    return lista;
  }

  public static LdvFormatos getById(Integer id) throws BiosLISDAOException, BiosLISException {
    return getInstance().cachedData.get(id);
  }

  public static String getDescripcionById(Integer id) throws BiosLISDAOException, BiosLISException {
    return getInstance().cachedData.get(id).getLdvfDescripcion();
  }

  public static String getCodigoById(Integer id) throws BiosLISDAOException, BiosLISException {
    return getInstance().cachedData.get(id).getLdvfDescripcion();
  }

  public static String getDescripcionById(BigDecimal id) throws BiosLISDAOException, BiosLISException {
    return getInstance().getDescripcionById(id.intValueExact());
  }

}
