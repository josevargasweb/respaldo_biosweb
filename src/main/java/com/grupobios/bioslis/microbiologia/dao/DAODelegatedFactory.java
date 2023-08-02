package com.grupobios.bioslis.microbiologia.dao;

public interface DAODelegatedFactory {
    public MicrobiologiaDAO getDAO();
    public BiosLisDAO getBiosLisDAO(Class clazz)  throws BiosLISDaoException;
    public <T> T getDAO(String entityName);
}
