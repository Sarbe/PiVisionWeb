package com.pivision.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

public class ConfigPropertiesProvider {

	private ResourceBundle bundle = null;
	private static final Logger logger = Logger.getLogger(ConfigPropertiesProvider.class);
	
	private static ConfigPropertiesProvider configProvider;
	
	private ConfigPropertiesProvider()
	{
		
	}
	
	public static synchronized ConfigPropertiesProvider getInstance()
	{
		if(configProvider == null)
		{
			configProvider = new ConfigPropertiesProvider();
		}
		
		return configProvider;
	}

	public void initializeBundle() {

		if (bundle == null) {
			try {
				
				String bundlename = "PiVision";
				this.bundle = ResourceBundle.getBundle ( bundlename);
				logger.info(this.bundle.keySet());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Could not intialize StoreFront resource bundle:"
						+ e.getMessage());
			}
		}

		

	}

	public String getValue(String key) {
		initializeBundle();
		String result = null;
		try {
			result = this.bundle.getString(key);
		} catch (MissingResourceException e) {
			result = "???" + key + "??? not found";
			logger.error(result);
			logger.error(e.getMessage());

		}
		return result;
	}

}
