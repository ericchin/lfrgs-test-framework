package com.liferay.gs.test.functional.selenium.properties;

import com.liferay.gs.test.functional.selenium.constants.SeleniumPropertyKeys;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

/**
 * @author Andrew Betts
 */
public class SeleniumProperties {

	private static final Properties _PROPERTIES = new Properties();

	private static void init() throws IOException {

		// copy system properties

		Properties systemProperties = System.getProperties();

		for (String key : systemProperties.stringPropertyNames()) {
			_PROPERTIES.setProperty(key, systemProperties.getProperty(key));
		}

		// load default properties

		ClassLoader classLoader = SeleniumProperties.class.getClassLoader();

		InputStream inputStream =
			classLoader.getResourceAsStream("selenium.properties");

		_PROPERTIES.load(inputStream);

		// override defaults

		String propertiesPath = _PROPERTIES.getProperty(
			SeleniumPropertyKeys.SELENIUM_PROPERTIES_PATH);

		if ((propertiesPath != null) && !propertiesPath.isEmpty()) {
			_PROPERTIES.load(new FileInputStream(new File(propertiesPath)));
		}
	}

	public static String get(String key) {
		return _PROPERTIES.getProperty(key);
	}

	public static void set(String key, String value) {
		_PROPERTIES.setProperty(key, value);
	}

	static {
		try {
			init();
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

}