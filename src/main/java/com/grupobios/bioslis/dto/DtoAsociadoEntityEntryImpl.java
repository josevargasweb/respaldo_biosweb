package com.grupobios.bioslis.dto;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.entity.LogEventosfichas;

public class DtoAsociadoEntityEntryImpl<T> implements DtoAsociadoEntityEntry<T>, utilIn<T> {

//	private String tableName;
  private String fieldName;
  private String colName;
  private T value;
  private String svalue;

  private Boolean changed;
  private LogEventosfichas lef;

  public DtoAsociadoEntityEntryImpl() {
    AppDtoMap appDtoMap = AppDtoMap.getInstance();
    DtoMap dm = appDtoMap.getMap(this.value.getClass());
//		this.tableName = dm.getTableName();
    this.lef = new LogEventosfichas();
    this.lef.setCfgEventos(Constante.AR.intValue());
    this.lef.setLefNombretabla(dm.getTableName());
  }

  public DtoAsociadoEntityEntryImpl(T dtoObject) {
    AppDtoMap appDtoMap = AppDtoMap.getInstance();
    DtoMap dm = appDtoMap.getMap(this.value.getClass());
//		this.tableName = dm.getTableName();
    this.value = dtoObject;

    this.lef = new LogEventosfichas();
    this.lef.setCfgEventos(Constante.AR.intValue());
    this.lef.setLefNombretabla(dm.getTableName());
    this.lef.setLefNombrecampo(null);
    this.lef.setLefValornuevo(null);
    this.lef.setDatosFichas(null);
    this.lef.setLefIdexamen(null);
    this.lef.setLefIdtest(null);
  }

  public DtoAsociadoEntityEntryImpl(String fieldName, Integer nOrden, Integer idExamen, Integer idTest) {

    AppDtoMap appDtoMap = AppDtoMap.getInstance();
    DtoMap dm = appDtoMap.getMap(this.value.getClass());
//		this.tableName = dm.getTableName();
    this.fieldName = fieldName;
    this.colName = ""; // obtener desde un singleton
    this.lef = new LogEventosfichas();
    this.lef.setCfgEventos(Constante.AR.intValue());
    this.lef.setLefNombrecampo(fieldName);
    this.lef.setLefNombretabla("");
    this.lef.setLefValornuevo(value.toString());
    this.lef.setDatosFichas(nOrden);
    this.lef.setLefIdexamen(idExamen);
    this.lef.setLefIdtest(idTest);

  }

  public DtoAsociadoEntityEntryImpl(String fieldName, String value, BigDecimal nOrden, BigDecimal idExamen,
      BigDecimal idTest) {
    this.fieldName = fieldName;
    this.svalue = value;
    this.changed = true;
    this.colName = ""; // obtener desde un singleton
    this.lef = new LogEventosfichas();
    this.lef.setCfgEventos(Constante.AR.intValue());
    this.lef.setLefNombrecampo(fieldName);
    this.lef.setLefNombretabla("");
    this.lef.setLefValornuevo(value);
    this.lef.setDatosFichas(nOrden.intValue());
    this.lef.setLefIdexamen(idExamen.intValue());
    this.lef.setLefIdtest(idTest.intValue());
  }

  public DtoAsociadoEntityEntryImpl(String tableName, String fieldName, String colname, String value,
      LogEventoFichaDtoId lefdid) {
    this.fieldName = fieldName;
    this.svalue = value;
    this.changed = true;
    this.colName = colname;
    this.lef = new LogEventosfichas();
    this.lef.setCfgEventos(Constante.AR.intValue());
    this.lef.setLefNombrecampo(fieldName);
    this.lef.setLefNombretabla(tableName);
    this.lef.setLefValornuevo(value);
    this.lef.setDatosFichas(lefdid.getnOrden().intValue());
    this.lef.setLefIdexamen(lefdid.getIdExamen().intValue());
    this.lef.setLefIdtest(lefdid.getIdTest().intValue());
    this.lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
  }

  /**
   * @return the fieldName
   */
  @Override
  public String getFieldName() {
    return fieldName;
  }

  /**
   * @param fieldName the fieldName to set
   */
  @Override
  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  /**
   * @return the colName
   */
  @Override
  public String getColName() {
    return colName;
  }

  /**
   * @param colName the colName to set
   */
  @Override
  public void setColName(String colName) {
    this.colName = colName;
  }

  /**
   * @return the value
   */
  @Override
  public T getValue() {
    return value;
  }

  /**
   * @return the changed
   */
  @Override
  public Boolean getChanged() {
    return changed;
  }

  /**
   * @param changed the changed to set
   */
  @Override
  public void setChanged(Boolean changed) {
    this.changed = changed;
  }

  /**
   * @return the svalue
   */
  @Override
  public String getSvalue() {
    return svalue;
  }

  /**
   * @param svalue the svalue to set
   */
  @Override
  public void setSvalue(String svalue) {
    this.svalue = svalue;
  }

  @Override
  public void setValue(T dtoObject) {
    this.value = dtoObject;

  }

  public void getDtoFields() throws IllegalArgumentException, IllegalAccessException {

    AppDtoMap appDtoMap = AppDtoMap.getInstance();
    DtoMap dm = appDtoMap.getMap(this.value.getClass());
    Field[] allFields = value.getClass().getDeclaredFields();
    String colName = null;
    for (Field field : allFields) {
      colName = dm.getColName(field.getName());
      Object value = null;
      value = field.get(value);

    }

  }

  /**
   * @return the lef
   */
  @Override
  public LogEventosfichas getLef() {
    return lef;
  }

}
