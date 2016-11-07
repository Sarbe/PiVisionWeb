package com.pivision.client.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pivision.client.InfoPayLoad;
import com.pivision.client.PayLoad;
import com.pivision.client.service.PayLoadService;
import com.pivision.util.Constants;

@Controller
@RequestMapping("/client/Payload/")
public class PayLoadController {

	private static final Logger logger = Logger.getLogger(PayLoadController.class);
	
	@Autowired
	PayLoadService payLoadService; 
	
	// generate content
		@RequestMapping(value = "/getContent/{macID}", method = RequestMethod.GET)
		public @ResponseBody List<PayLoad> getContent(@PathVariable String macID,HttpServletRequest req) {
			macID = macID.toUpperCase();
			logger.debug("Fetching payload for:" + macID);
			List<PayLoad> payLoadList = null;
			try {
				String rootPath = Constants.imgPath;//req.getSession().getServletContext().getRealPath("/");
				payLoadList = payLoadService.getPayLoad(macID,rootPath);
				//payLoad.setJsonResponse(gson.toJson(payLoad));
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				logger.error("Error generating Payload for macid:"+macID);
			}
			//logger.info("JSON generated for macID:"+macID);
			//logger.info(payLoadList.size());
			return payLoadList;

		}
		
		// generate content
		@RequestMapping(value = "/getOldContent/{macID}", method = RequestMethod.GET)
		public @ResponseBody List<PayLoad> getOldContent(@PathVariable String macID,HttpServletRequest req) {
			macID = macID.toUpperCase();
			logger.debug("Trying to generate payload for:"+macID);
			
			List<PayLoad> payLoadList = null;
			try {
				String rootPath = Constants.imgPath;//req.getSession().getServletContext().getRealPath("/");
				payLoadList = payLoadService.getOldPayLoad(macID,rootPath);
				//payLoad.setJsonResponse(gson.toJson(payLoad));
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				logger.error("Error generating Payload for macid:"+macID);
			}
			//logger.info("JSON generated for macID:"+macID);
			return payLoadList;

		}
		
		//heart beat method
		@RequestMapping(value = "/getInfo/{macID}/{refreshSts}", method = RequestMethod.GET)
		public @ResponseBody InfoPayLoad getInfo(@PathVariable String macID, @PathVariable String refreshSts,HttpServletRequest req) {
			macID = macID.toUpperCase();
			logger.debug("Trying to get Info for:"+macID);
			InfoPayLoad infoPayLoad = null;
			try {
				String rootPath = Constants.imgPath;//req.getSession().getServletContext().getRealPath("/");
				infoPayLoad = payLoadService.generateInfoPayLoad(macID,rootPath);
				//payLoad.setJsonResponse(gson.toJson(payLoad));
				
				// Update the display status
				payLoadService.updateDisplayStaus(macID, refreshSts);
				
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				logger.error("Error generating status for macid:"+macID);
			}			
			logger.debug("status generated for macID:"+macID);
			return infoPayLoad;

		}
		 
		@RequestMapping(value="/downloadUpdate/{macID}",method = RequestMethod.GET)
		public void downloadFile(@PathVariable String macID, HttpServletRequest req,HttpServletResponse response )
				throws Exception {
			macID = macID.toUpperCase();
			logger.debug("Download updates");
		    try {
		    	// Check if any update is available for the MACID
		    	boolean isAvail = payLoadService.getSoftwareUpdDetails(macID);
		    	if(isAvail){
			    	File softwareDir = new File(Constants.softwareUpdateFolder);
			    	String filePathToBeServed = softwareDir.listFiles()[0].getAbsolutePath();
			        File fileToDownload = new File(filePathToBeServed);
			        InputStream inputStream = new FileInputStream(fileToDownload);
			        response.setContentType("application/force-download");
			        response.setHeader("Content-Disposition", "attachment; filename="+fileToDownload.getName()); 
			        IOUtils.copy(inputStream, response.getOutputStream());
			        response.flushBuffer();
			        inputStream.close();
		        }
		    } catch (Exception e){
		        logger.debug("Request could not be completed at this moment. Please try again.");
		        e.printStackTrace();
		    }

		}
		
		@RequestMapping(value="/updateStatus/{macID}/{status}",method = RequestMethod.GET)
		public void updateStatus(@PathVariable String macID,@PathVariable String status, HttpServletRequest req,HttpServletResponse response )
				throws Exception {
			macID = macID.toUpperCase();
			logger.debug("Update Status of Software.");
		    try {
		    	payLoadService.updateSoftwareStatus(macID, status);
		    } catch (Exception e){
		        logger.debug("Request could not be completed at this moment. Please try again.");
		        e.printStackTrace();
		    }

		}
}
