package com.pivision.client.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pivision.client.InfoPayLoad;
import com.pivision.client.PayLoad;
import com.pivision.client.dao.ClientDao;
import com.pivision.presentation.dao.PresentationDao;
import com.pivision.schedule.ActiveSchedule;
import com.pivision.schedule.dao.ScheduleDao;
import com.pivision.util.CommonFunction;
import com.pivision.util.Config;
import com.pivision.util.Constants;

@Component
public class PayLoadService {
	
	private static final Logger logger = Logger.getLogger(PayLoadService.class);
	
	@Autowired
	private PresentationDao presentationDao;
	
	@Autowired
	private ClientDao clientDao;
	
	@Autowired
	ScheduleDao scheduleDao;

   	@Transactional
	public List<PayLoad> getPayLoad(String macId,String rootPath)
	{
		ActiveSchedule  activeSchedule = null;
		List<ActiveSchedule> activeSchedules = null;
		List<PayLoad> payLoadList = null;
		PayLoad payLoad =null;
		String imageBytes = null;
		try {
			
			payLoadList = new ArrayList<PayLoad>();
			activeSchedules = clientDao.getActiveSchedule(macId);
			//logger.info("Active Schedule FOund for macId :" + activeSchedules.size());
			for (Iterator iterator = activeSchedules.iterator(); iterator.hasNext();) {
				activeSchedule = (ActiveSchedule) iterator.next();
						
				if (activeSchedule.getActionFlag().equals(Constants.SCHD_READY_FOR_DWNLD)) {
					
					logger.info("Active Schedule retreived for macId :" + macId + " Schedule Name:" + activeSchedule.getScheduleName());
					String imageFileName = activeSchedule.getPresentation().getFileName();
					String contentType = activeSchedule.getPresentation().getContentType();
					// first get images
					imageBytes = CommonFunction.imagteToByte(rootPath + "/media/presentation/"+ imageFileName, contentType);
					
					
					if(!imageBytes.equals("")){
						payLoad = new PayLoad();
						payLoad.setMedia(imageBytes.getBytes());
						
						//payLoad.setScheduleId(String.valueOf(activeSchedule.getSchedule().getScheduleId()));
						payLoad.setScheduleId(String.valueOf(activeSchedule.getActiveScheduleId()));
						payLoad.setEndDate(CommonFunction.dtToStr(activeSchedule.getEndDate(),"yyyy-MM-dd"));
						payLoad.setStartDate(CommonFunction.dtToStr(activeSchedule.getActiveDate(),"yyyy-MM-dd"));
						payLoad.setFileName(imageFileName);
						payLoad.setMimeType(activeSchedule.getPresentation().getContentType());
						payLoad.setFileType(activeSchedule.getPresentation().getFileType());
						payLoad.setTimeOfDay(activeSchedule.getSchedule().getTimeOfDay());
						payLoad.setDuration(activeSchedule.getScheduleDuration());
						
						payLoadList.add(payLoad);
					
					
						// update the table marking content as downloaded and date
						activeSchedule.setLastAccessed(new Date());
						activeSchedule.setActionFlag(Constants.SCHD_DOWNLOADED);
						clientDao.updateActiveSchedule(activeSchedule);
						
					}else{
						logger.error("Image Details not found for schedule :: "+ activeSchedule.getSchedule().getScheduleId());
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("Error retreiving active schedule for mac id:"+macId+" reason:"+e.getMessage());
		}
		finally
		{
			activeSchedules= null;
			 payLoad = null;
		}
		
		return payLoadList;
	}
	
	@Transactional
	public List<PayLoad> getOldPayLoad(String macId,String rootPath)
	{
		ActiveSchedule  activeSchedule = null;
		List<ActiveSchedule> activeSchedules = null;
		List<PayLoad> payLoadList = null;
		PayLoad payLoad =null;
		String imageBytes = null;
		try {
			
			Calendar cal = Calendar.getInstance();
			java.util.Date date = cal.getTime();
			long time = date.getTime();
			 java.sql.Date sqlDate = new java.sql.Date(time);
			
			payLoadList = new ArrayList<PayLoad>();
			activeSchedules = clientDao.getActiveSchedule(macId);
			for (Iterator iterator = activeSchedules.iterator(); iterator.hasNext();) {
				activeSchedule = (ActiveSchedule) iterator.next();
						
			
				payLoad = new PayLoad();
				logger.debug("Active Schedule retreived for macId :" + macId
						+ " Schedule Name:" + activeSchedule.getScheduleName());

				payLoad.setEndDate(CommonFunction.dtToStr(activeSchedule.getEndDate(),"yyyyMMdd"));
				payLoad.setStartDate(CommonFunction.dtToStr(activeSchedule.getActiveDate(),"yyyyMMdd"));
				payLoad.setFileName(activeSchedule.getPresentation().getFileName());
				payLoad.setMimeType(activeSchedule.getPresentation().getContentType());
				payLoad.setFileType(activeSchedule.getPresentation().getFileType());
				imageBytes = CommonFunction.imagteToByte(rootPath + "/media/presentation/"+ payLoad.getFileName(), payLoad.getFileType());
				payLoad.setMedia(imageBytes.getBytes());
				payLoadList.add(payLoad);
				//update the table marking content as downloaded and date 
				activeSchedule.setLastAccessed(sqlDate);
				activeSchedule.setActionFlag(Constants.SCHD_DOWNLOADED);
				clientDao.updateActiveSchedule(activeSchedule);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("Error retreiving active schedule for mac id:"+macId+" reason:"+e.getMessage());
		}
		finally
		{
			activeSchedules= null;
			 payLoad = null;
		}
		
		return payLoadList;
	}
	
	@Transactional
	public InfoPayLoad generateInfoPayLoad(String macId,String rootPath)
	{
		ActiveSchedule  activeSchedule = null;
		InfoPayLoad infoPayLoad =null;
	
		try {
			infoPayLoad = new InfoPayLoad();
			// Get Config details
			activeSchedule = clientDao.getConfigDetails(macId);
			if(activeSchedule != null){
				infoPayLoad.setConfigUpdate(Constants.STATUS_YES);
				infoPayLoad.setDuration(activeSchedule.getDisplayDduration());
				infoPayLoad.setFrequency(activeSchedule.getDisplayFrequency());
				clientDao.updateConfigStatus(macId);
			}
			
			/*// Get software update details
			activeSchedule = clientDao.getSoftwareUpdate(macId);
			if(activeSchedule != null){
				infoPayLoad.setSoftwareUpdate(Constants.STATUS_YES);
				infoPayLoad.setDownloaLink(Config.getKey("software.link"));
				infoPayLoad.setFileChkSum("e968669e0e6227e285a0e6733d7da65c7d2aabe7");
				infoPayLoad.setDuration(activeSchedule.getDisplayDduration());
				infoPayLoad.setFrequency(activeSchedule.getDisplayFrequency());
				//clientDao.updatesoftwareUpdStatus(macId);
			}*/
			// Get content details
			int noOfContent = clientDao.chkForScheduleContent(macId);
			if(noOfContent>0){
				infoPayLoad.setContentUpdate(Constants.STATUS_YES);
			}
			
			// chk if both present
			/*if(activeSchedule != null && noOfContent>0){
				infoPayLoad.setInfoType(Constants.STATUS_BOTH);
			}*/
			
			////////////////////
			//Delete Schd Details
			List<BigInteger> delSchIds = clientDao.chkForDeActiveScheduleContent(macId);
			
			if( delSchIds.size()>0){
				infoPayLoad.setDelSchIds (delSchIds);
				clientDao.removeDeActiveSchedule(delSchIds);
			}
			
			logger.debug("Active Schedule retreived for macId :" + macId);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("Error retreiving active schedule for mac id:"+macId+" reason:"+e.getMessage());
		}
		finally{
			activeSchedule = null;
		}
		return infoPayLoad;
	}
	
	@Transactional
	public void updateDisplayStaus(String macID, String refreshSts) {
		// TODO Auto-generated method stub
		clientDao.updateDisplayStatus(macID, refreshSts);	
	}
	
	@Transactional
	public boolean getSoftwareUpdDetails(String macId)
	{
		ActiveSchedule  activeSchedule = clientDao.getSoftwareUpdate(macId);
		if(activeSchedule != null){	
			return true;
		}
		return false;
	}
	
	@Transactional
	public void updateSoftwareStatus(String macId, String status)
	{
		clientDao.updatesoftwareUpdStatus(macId , status);
	}

	
	
}
