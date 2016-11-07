package com.pivision.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	public static boolean visibility = true;
	static Properties configProperties = new Properties();
	static String configFilePath = "PiVision.properties";

	private static void loadBundle() {
		try {
			// ./ is the root of the config.properties file
			InputStream input = Config.class.getClassLoader().getResourceAsStream(configFilePath);
			configProperties.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File not found");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Key access
	public static String getKey(String key) {
		if (configProperties.isEmpty())
			loadBundle();
		return configProperties.getProperty(key);
	}

}
