package com.pivision.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;



public class MessageProvider {
	
	  private ResourceBundle bundle=null;
	  private static final Logger logger = Logger.getLogger(MessageProvider.class);
	  
	    public void initializeBundle() {
	       
	            
	            if(bundle == null)
	            {
	            	try {
						FacesContext context = FacesContext.getCurrentInstance();
						this.bundle = context.getApplication().getResourceBundle(context, "messages");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error("Could not intialize resource bundle:"+e.getMessage());
					}
	            }
	            
	                
	        logger.info(this.bundle.keySet());
	        
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
