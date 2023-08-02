package com.grupobios.bioslis.microbiologia.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DAODelegatedRDBMSFactory implements DAODelegatedFactory {

  Logger logger = LogManager.getLogger(DAODelegatedRDBMSFactory.class);

  private static MicrobiologiaDAORDBMS dao = new MicrobiologiaDAORDBMS();
  private static Map misDaos = new HashMap<Class, BiosLisDAO>();

  private static MicrobiologiaDAORDBMS defaultDao = new MicrobiologiaDAORDBMS();
  private static HashMap<String, Object> daoMap = new HashMap<String, Object>() {
    {
      put("MBOrder", new MBOrderDAORDBMS());
      put("Patient", new PatientDAORDBMS());
      put("Parameter", new ParameterDAORDBMS());
      put("MBExam", new MBExamDAORDBMS());
      put("MBTest", new MBTestDAORDBMS());
      put("Physician", new PhysicianDAORDBMS());
      put("MBAntibiogram", new MBAntibiogramDAORDBMS());
      put("MBConfiguration", new MBConfigurationDAORDBMS());
      put("MBTask", new MBTaskDAORDBMS());
    }
  };

  @Override
  public MicrobiologiaDAO getDAO() {
    return defaultDao;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T getDAO(String entityName) {
    return (T) daoMap.get(entityName);
  }

  @Override
  public BiosLisDAO getBiosLisDAO(Class clazz) throws BiosLISDaoException {

    BiosLisDAO _dao;
    try {
      if (misDaos.containsValue(clazz)) {
        return (BiosLisDAO) misDaos.get(clazz);
      }
      _dao = (BiosLisDAO) clazz.getConstructor().newInstance();
      return _dao;
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
        | NoSuchMethodException | SecurityException ex) {
      logger.error(ex.getMessage());
    }
    throw new BiosLISDaoException("No se pudo crear DAO de " + clazz.getCanonicalName());
  }

}
