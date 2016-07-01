package com.test.amil.api.core;

import java.io.IOException;
import java.util.Properties;

/**
 * Classe core do app
 * @author tbdea
 *
 */
public final class AppCore {
	
	private static AppCore instance = null;
	private Properties props = null;
	
	private static final String PROPERTIES_FILE_NAME = "amil.properties";
	
	protected AppCore() {
		this.props = new Properties();
		try {
			props.load(getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static AppCore getInstance() {
		if(instance == null) {
			instance = new AppCore();
		}
		return instance;
	}

	public static void destroy() {
		instance = null;
	}
	
	public Properties getProperties() {
		return this.props;
	}
}
