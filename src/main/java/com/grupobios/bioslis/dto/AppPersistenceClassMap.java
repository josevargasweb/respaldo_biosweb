package com.grupobios.bioslis.dto;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.SingleTableEntityPersister;

import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.config.HibernateUtil;

public class AppPersistenceClassMap {

  private AppPersistenceClassMap() {
  }

  private static final Map<Class<?>, PersistenceClassMap> appDtoMap = new HashMap<Class<?>, PersistenceClassMap>();

  private static AppPersistenceClassMap instance;

  public static final AppPersistenceClassMap getInstance() {
    synchronized (appDtoMap) {
      if (instance == null) {
        instance = new AppPersistenceClassMap();
      }
    }
    return instance;
  }

  public void init(Class<?> clazz) {

    PersistenceClassMap dm = new PersistenceClassMap(clazz);
    appDtoMap.put(clazz, dm);
  }

  public PersistenceClassMap getMap(Class<?> clazz) {

    PersistenceClassMap rpta = appDtoMap.get(clazz);

    if (rpta == null) {
      this.init(clazz);
      rpta = appDtoMap.get(clazz);
    }

    return rpta;
  }

  public String getTableNameForEntityClass(Class<?> clazz) {
    ClassMetadata cm = HibernateUtil.getSessionFactory().getClassMetadata(clazz);
    if (cm instanceof SingleTableEntityPersister) {
      SingleTableEntityPersister sp = (SingleTableEntityPersister) cm;
      String tableName = sp.getTableName();
      int pos = tableName.indexOf(Constante.SCHEMA.concat("."));
      if (pos != -1) {
        tableName = tableName.substring(Constante.SCHEMA.concat(".").length());
      }
      return tableName;
    }
    return "";
  }

}
