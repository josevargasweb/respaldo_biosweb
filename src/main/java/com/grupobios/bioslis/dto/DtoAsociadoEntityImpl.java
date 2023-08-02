package com.grupobios.bioslis.dto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DtoAsociadoEntityImpl<T> implements DtoAsociadoEntity<T> {

	private static Logger logger = LogManager.getLogger(DtoAsociadoEntityImpl.class);
	private String tableAsocName = "";
	private T dtoObject;
	private List<DtoAsociadoEntityEntry<T>> listEntr = new ArrayList<DtoAsociadoEntityEntry<T>>();

	public DtoAsociadoEntityImpl(T dtoObject, LogEventoFichaDtoId lefdid) {

		this.dtoObject = dtoObject;
		Field[] fields = this.dtoObject.getClass().getDeclaredFields();
		AppDtoMap appDtoMap = AppDtoMap.getInstance();
		DtoMap dm = appDtoMap.getMap(this.dtoObject.getClass());
		this.tableAsocName = dm.getTableName();
		logger.debug("this.tableAsocName {}", this.tableAsocName);

		for (Field field : fields) {
			Object value = null;
			try {
				field.setAccessible(true);
				value = field.get(dtoObject);
				if (value == null) {
					continue;
				}
				DtoAsociadoEntityEntry<T> daeei = new DtoAsociadoEntityEntryImpl<>(this.tableAsocName, field.getName(),
						dm.getColName(field.getName()), value.toString(), lefdid);
				listEntr.add(daeei);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				logger.error(e.getMessage());
			}

		}
	}

	/**
	 * @return the tableAsocName
	 */
	public String getTableAsocName() {
		return tableAsocName;
	}

	/**
	 * @param tableAsocName the tableAsocName to set
	 */
	public void setTableAsocName(String tableAsocName) {
		this.tableAsocName = tableAsocName;
	}

	/**
	 * @return the listEntr
	 */
	public List<DtoAsociadoEntityEntry<T>> getListEntr() {
		return listEntr;
	}

//	public DtoAsociadoEntityImpl(Map<String, String> mapeo, String tableName) {
//
//		for (Entry<String, String> entry : mapeo.entrySet()) {
//
//			DtoAsociadoEntityEntry<?> dtoEntry = new DtoAsociadoEntityEntryImpl<String>();
//			dtoEntry.setChanged(false);
//			dtoEntry.setColName(entry.getKey());
//			dtoEntry.setFieldName(entry.getValue());
//			dtoEntry.setValue(null);
//			entries.put(tableAsocName, dtoEntry);
//		}
//
//	}

//	public void updateTablaAsociada() {
//
//		for (Entry<String, DtoAsociadoEntityEntry> entry : this.entries.entrySet()) {
//
//			LogEventosfichas lef = new LogEventosfichas();
//
//			logEvF.setLefFecharegistro(fechaHoraMod);
//			logEvF.setLefFechaorden(fechaOrden);
//			logEvF.setLefIdestacion(null);
//			logEvF.setLefIdexamen(idExamen != null ? idExamen.intValue() : null);
//			logEvF.setLefIdmuestra(idMuestra != null ? idMuestra.intValue() : null);
//			logEvF.setLefIdpaciente(idPaciente != null ? idPaciente.intValue() : null);
//			logEvF.setLefIdtest(idTest != null ? idExamen.intValue() : null);
//			logEvF.setLefIdusuario(idUsuario.intValue());
//			logEvF.setLefIpestacion(ipEstacion);
//			logEvF.setLefValoranterior("");
//			logEvF.setLefNombrecampo(fieldColNameMap.get(field.getName()));
//			logEvF.setLefNombretabla(tableName);
//			logEvF.setCfgEventos(idEvento.intValue());
//			logEvF.setDatosFichas(idRegistro.intValue());
//			if (field.get(hibernateObject) != null) {
//				logEvF.setLefValornuevo(field.get(hibernateObject).toString());
//				logger.debug("Valor: {}", field.get(hibernateObject).toString());
//			} else {
//				logEvF.setLefValornuevo("Nulo");
//				logger.debug("Valor: Nulo");
//			}
//
//		}
//
//	}

}
