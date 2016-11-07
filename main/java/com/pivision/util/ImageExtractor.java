package com.pivision.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ImageExtractor {
	
	
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    
    //create the fileDO
   
    
    //read in the image file to memory and store in the DO
   public byte[] getImageBytes(String filename)
   {
   
	byte[] image_data=null;
	File inputfile=null;
	InputStream is=null;
	   
	try
    {
		inputfile = new File("h:\\inputtest\\input image.jpg");
	    is = new FileInputStream(inputfile);
		
		
		byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = is.read(buffer)) != -1)
        {
            baos.write(buffer,0, bytesRead);
        }
        
        image_data =  baos.toByteArray();
        
    }
    catch(Exception e)
    {
    	e.printStackTrace();
    }
    finally
    {
        try
        {
        	is.close();
        	baos.close();
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
        
    
    }
      return image_data; 
   }
   
   


}
