/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Jan Perkov
 */
public class HibernateUtil {

	private static Logger logger = LogManager.getLogger(HibernateUtil.class);
	private static final SessionFactory sessionFactory;
	private static final Configuration config;

	static {
		try {
			// Create the SessionFactory from standard (hibernate.cfg.xml)
			// config file.
//            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();

			config = new Configuration();
			config.configure();
			StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
			registryBuilder.applySettings(config.getProperties());
			ServiceRegistry svc = registryBuilder.build();
			sessionFactory = config.buildSessionFactory(svc);

		} catch (Throwable ex) {
			// Log the exception.
			logger.error("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Configuration getConfiguration() {
		return config;
	}
}
