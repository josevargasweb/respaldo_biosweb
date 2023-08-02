package com.grupobios.bioslis.dto;

import java.util.HashMap;
import java.util.Map;

public class AppDtoMap {

	private AppDtoMap() {
	}

	private static final Map<Class<?>, DtoMap> appDtoMap = new HashMap<Class<?>, DtoMap>();

	private static AppDtoMap instance;

	public static final AppDtoMap getInstance() {
		synchronized (appDtoMap) {
			if (instance == null) {
				instance = new AppDtoMap();
			}
		}
		return instance;
	}

	public void init(Class<?> clazz) {

		DtoMap dm = new DtoMap(clazz);
		appDtoMap.put(clazz, dm);
	}

	public DtoMap getMap(Class<?> clazz) {

		DtoMap rpta = appDtoMap.get(clazz);

		if (rpta == null) {
			this.init(clazz);
			rpta = appDtoMap.get(clazz);
		}

		return rpta;
	}

}
