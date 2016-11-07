package com.pivision.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class CommonFunction {
	private static final Logger logger = Logger.getLogger(CommonFunction.class);

	public static Date stringToDateConversion(String dateInString) {
		SimpleDateFormat formatter = // new
										// SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
		new SimpleDateFormat("dd-MMM-yyyy");
		// String dateInString = "Friday, Jun 7, 2013 12:10:56 PM";
		// 04-Feb-2015 07:00 pm
		Date date = null;
		try {

			date = formatter.parse(dateInString);
			//System.out.println(date);
			//System.out.println(formatter.format(date));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;

	}

	public static Date stringToCustomDateConversion(String dateInString, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = null;
		try {

			date = formatter.parse(dateInString);
			//System.out.println(date);
			//System.out.println(formatter.format(date));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;

	}

	
	public static String dtToStr(Date indate, String format) {
		String dateString = null;
		SimpleDateFormat sdfr = new SimpleDateFormat(format);
		try {
			dateString = sdfr.format(indate);
		} catch (Exception ex) {
		}
		return dateString;
	}


	public static String imagteToByte(String path,String fileType)  {
		// byte[] res=baos.toByteArray();
		String encodedImage="";
		BufferedImage image;
		ByteArrayOutputStream baos;
		try {
			File img = new File(path);
			if(img.exists()){
				image = ImageIO.read(img);
				baos = new ByteArrayOutputStream();
				ImageIO.write(image, fileType, baos);
				encodedImage = new String(Base64.encodeBase64(baos.toByteArray()));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("error encoding image"+e.getMessage());
		}
		finally
		{
			image=null;
			baos=null;
		}
		return encodedImage;
	}
	
	public static void fileToByte(String file) {
		try {
			System.out.println("Path :: " + new File(file).toPath());
			byte[] fileCnt = Files.readAllBytes(new File(file).toPath());
			System.out.println(new String(fileCnt));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String externalURL(String urlStr ) throws Exception {
		URL url;
		String responeOutput = "";
		URLConnection urlCon = null;
		try {
			url = new URL(urlStr);
			urlCon = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlCon.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				responeOutput += inputLine;
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
		}
		return responeOutput;
	}

	public static String listToInClauseStr(List<BigInteger> l) {
		String r= "";
		for(int i =0;i<l.size();i++){
			if(i==0)
				r = l.get(i).toString();
			else
				r += ","+ l.get(i).toString();
		}
		return r;
	}
}
