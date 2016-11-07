package com.pivision.schedule.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pivision.schedule.ActiveSchedule;
import com.pivision.schedule.Schedule;
import com.pivision.schedule.dao.ScheduleDao;
import com.pivision.util.Constants;

@Component
public class ScheduleService {
	
	private static final Logger logger = Logger.getLogger(ScheduleService.class);
	
	@Autowired
	ScheduleDao scheduleDao;

	@Transactional
	public List<Schedule> findAllSchedule(String userId) {
		return scheduleDao.selectAllUserSchedules(userId);
	}
	
	@Transactional
	public void saveScheduleBean(Schedule schedule) {
		// TODO Auto-generated method stub
		try {
			schedule.setLastAccessed(new Date());
			schedule.setGmtDiff(5.5);
			schedule.setPublished("N");
			scheduleDao.saveSchedule(schedule);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error creationg Schedule"+e.getMessage());
		}
	      
	}
	

	@Transactional
	public void deleteSchedule(String userId, String scheduleId) {
		scheduleDao.deleteSchedule(userId, scheduleId);
		
	}
	@Transactional
	public void unpublishSchedule(String userId, String scheduleId) {
		scheduleDao.stopSchedule(userId, scheduleId);
	}
	
	@Transactional
	public int addDisplayToSchd(ActiveSchedule activeSchedule) {
		// TODO Auto-generated method stub
		int count = 0;
		try {
			// Configuration
			activeSchedule.setConfigUpdate('N');
			if(activeSchedule.getSchedule().getPublished().equals(Constants.SCHD_PUBLISHED)){
				activeSchedule.setActionFlag(Constants.SCHD_READY_FOR_DWNLD);
			}else{
				activeSchedule.setActionFlag(Constants.SCHD_CREATED);
			}
			
			count = scheduleDao.addDisplayToSchd(activeSchedule);

			logger.debug("Published schedule"+ activeSchedule.getScheduleName()+" on machine "+activeSchedule.getDisplay().getMacId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error Publishing Schedule"+e.getMessage()); 
		}
		
		return count;
	}
	
	@Transactional
	public Schedule getScheduleByID(String scheduleId) {
		Schedule schd = scheduleDao.selectScheduleById(scheduleId);
		logger.debug("schedule details for"+ schd.getScheduleId());
		return schd;
	}
	
	@Transactional
	public void publishSchedule(Schedule schedule) {
		scheduleDao.publishSchedule(schedule);
	}

	@Transactional
	public boolean removeActiveSchedule(String userId, String activeScheduleIds) {
		return scheduleDao.deleteActiveSchedule(userId, activeScheduleIds);
		
	}

}
