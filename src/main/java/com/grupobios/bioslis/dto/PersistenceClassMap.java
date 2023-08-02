package com.grupobios.bioslis.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.KeyValue;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.PrimaryKey;
import org.hibernate.metadata.ClassMetadata;

import com.grupobios.bioslis.config.HibernateUtil;

public class PersistenceClassMap {

  private static Logger logger = LogManager.getLogger(DtoMap.class);

  private PersistentClass persistenceClazz;
  private Map<String, String> fieldColMap = new HashMap<String, String>();

  public Map<String, String> getFieldColMap() {
    return fieldColMap;
  }

  public List<Column> getLstPkCol() {
    return lstPkCol;
  }

  public String getTableName() {
    return tableName;
  }

  private List<Column> lstPkCol;
  private String tableName;

  public PersistenceClassMap(Class<?> clazz) {
    ClassMetadata cm = HibernateUtil.getSessionFactory().getClassMetadata(clazz);
    String[] propertyNames = cm.getPropertyNames();
    Configuration configuration = HibernateUtil.getConfiguration();
    this.persistenceClazz = configuration.getClassMapping(clazz.getName());
    KeyValue kv = this.persistenceClazz.getIdentifier();
    PrimaryKey pk = kv.getTable().getPrimaryKey();
    this.tableName = kv.getTable().getName();
    lstPkCol = pk.getColumns();

    for (String property : propertyNames) {
      logger.debug(property);
      try {
        Column col = (Column) this.persistenceClazz.getProperty(property).getColumnIterator().next();
        fieldColMap.put(property, col.getName());
        logger.debug(col.getName());
      } catch (java.util.NoSuchElementException e) {
        logger.error(e.getMessage());
      }
    }
  }

  public Boolean isInPk(String colName) {

//    lstPkCol.forEach(pkC -> {
//      logger.debug(pkC.getName());
//    });

    return lstPkCol.contains(colName);

  }

}
