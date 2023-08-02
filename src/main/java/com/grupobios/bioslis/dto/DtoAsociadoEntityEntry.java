package com.grupobios.bioslis.dto;

import com.grupobios.bioslis.entity.LogEventosfichas;

public interface DtoAsociadoEntityEntry<T> {

	/**
	 * @return the fieldName
	 */
	String getFieldName();

	/**
	 * @param fieldName the fieldName to set
	 */
	void setFieldName(String fieldName);

	/**
	 * @return the colName
	 */
	String getColName();

	/**
	 * @param colName the colName to set
	 */
	void setColName(String colName);

	/**
	 * @return the value
	 */
	T getValue();

	/**
	 * @return the changed
	 */
	Boolean getChanged();

	/**
	 * @param changed the changed to set
	 */
	void setChanged(Boolean changed);

	/**
	 * @return the svalue
	 */
	String getSvalue();

	/**
	 * @param svalue the svalue to set
	 */
	void setSvalue(String svalue);

	void setValue(T object);

	LogEventosfichas getLef();

}
