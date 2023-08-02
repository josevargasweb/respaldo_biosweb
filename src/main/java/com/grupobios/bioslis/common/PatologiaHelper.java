package com.grupobios.bioslis.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.grupobios.bioslis.entity.LdvPatologias;

public class PatologiaHelper {

  private static List<LdvPatologias> lstLdvPatologias = null;
  private static Map<Integer, LdvPatologias> mapCodPatologia = null;

  private static final Logger logger = LogManager.getLogger(PatologiaHelper.class);

  private PatologiaHelper() {
  }

  public static void init(List<LdvPatologias> lstPatologias) {
    lstLdvPatologias = lstPatologias;
    mapCodPatologia = new HashMap<Integer, LdvPatologias>();
    for (LdvPatologias ldvPatologias : lstPatologias) {
      mapCodPatologia.put(ldvPatologias.getLdvpatIdpatologia(), ldvPatologias);
    }
  }

  public static LdvPatologias getPatologiaByCod(Integer idPatologia) {

    if (lstLdvPatologias == null)
      return null;
    return mapCodPatologia.get(idPatologia);

  }

}
