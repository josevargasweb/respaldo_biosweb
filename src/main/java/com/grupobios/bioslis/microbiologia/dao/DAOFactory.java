package com.grupobios.bioslis.microbiologia.dao;

public class DAOFactory {

  private static DAODelegatedFactory delegatedFactory() {

    return new DAODelegatedRDBMSFactory();
  }

  public String getDelegatedClassName() {
    return DAOFactory.delegatedFactory().getClass().getName();
  }

  public MicrobiologiaDAO getDAO() {
    return DAOFactory.delegatedFactory().getDAO();
  }

  @SuppressWarnings("unchecked")
  public <T> T getDAO(String entityName) {
    return (T) DAOFactory.delegatedFactory().getDAO(entityName);
  }

  public static BiosLisDAO getBiosLisDao(Class clazz) throws BiosLISDaoException {
    return DAOFactory.delegatedFactory().getBiosLisDAO(clazz);
  }

}
