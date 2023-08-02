package com.grupobios.bioslis.dto;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.grupobios.bioslis.common.Constante;

public class DtoMap {

  private static Logger logger = LogManager.getLogger(DtoMap.class);

  private Class<?> dtoClazz;
  private Map<String, String> fieldColMap = new HashMap<String, String>();
  private Properties fieldColMapProperties;
  private String tableName;

  public DtoMap(Class<?> dtoClaz) {

    this.dtoClazz = dtoClaz;
    Field[] allFields = dtoClazz.getDeclaredFields();

    for (Field field : allFields) {
      fieldColMap.put(field.getName(), null);
    }

    Resource src = new ClassPathResource(dtoClazz.getSimpleName().concat(".properties"));
    fieldColMapProperties = new Properties();
    try (InputStream is = src.getInputStream()) {
      fieldColMapProperties.load(is);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
    this.tableName = fieldColMapProperties.getProperty(Constante.PROPERTY_TABLE_NAME);

    logger.debug("Nombre de tabla recuperado {}", this.tableName);

  }

  /**
   * @return the dtoClazz
   */
  public Class<?> getDtoClazz() {
    return dtoClazz;
  }

  /**
   * @param dtoClazz the dtoClazz to set
   */
  public void setDtoClazz(Class<?> dtoClazz) {
    this.dtoClazz = dtoClazz;
  }

  /**
   * @return the fieldColMap
   */
  public Map<String, String> getFieldColMap() {
    return fieldColMap;
  }

  /**
   * @param fieldColMap the fieldColMap to set
   */
  public void setFieldColMap(Map<String, String> fieldColMap) {
    this.fieldColMap = fieldColMap;
  }

  public void addFieldColNameMap(String fieldName, String colName) {
    this.fieldColMap.put(fieldName, colName);
  }

  public void addFieldColName(Map<String, String> map) {

    this.fieldColMap.putAll(map);
  }

//	public DtoAsociadoEntityEntry<?> createDtoEntityEntry(String fieldName) {
//
//		DtoAsociadoEntityEntry<String> daee = new DtoAsociadoEntityEntryImpl<>();
//
//		daee.setChanged(false);
//		daee.setFieldName(fieldName);
//		daee.setColName(fieldColMapProperties.getProperty(fieldName));
//		daee.setSvalue("Nulo");
//		daee.setValue(null);
//		return daee;
//
//	}
//
//	public DtoAsociadoEntityEntry<?> createDtoEntityEntry(String fieldName, String value, BigDecimal nOrden,
//			BigDecimal idExamen, BigDecimal idTest) {
//
//		DtoAsociadoEntityEntry<String> daee = new DtoAsociadoEntityEntryImpl<>(fieldName, value, nOrden, idExamen,
//				idTest);
//
//		daee.setChanged(false);
//		daee.setFieldName(fieldName);
//		daee.setColName(fieldColMapProperties.getProperty(fieldName));
//		daee.setSvalue(value);
//		daee.setValue(null);
//
//		return daee;
//
//	}

  /**
   * @return the tableName
   */
  public String getTableName() {
    return tableName;
  }

  public String getColName(String fieldName) {
    return fieldColMapProperties.getProperty(fieldName);
  }

}
