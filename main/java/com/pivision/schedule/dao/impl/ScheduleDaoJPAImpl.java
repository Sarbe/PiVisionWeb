package com.pivision.schedule.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.pivision.schedule.ActiveSchedule;
import com.pivision.schedule.Schedule;
import com.pivision.schedule.dao.ScheduleDao;
import com.pivision.util.Constants;


@Repository
public class ScheduleDaoJPAImpl implements ScheduleDao {

	private static final Logger logger = Logger.getLogger(ScheduleDaoJPAImpl.class);
	
	@PersistenceContext(unitName = "PiVisionPersistence")
	private EntityManager entityManager;

	@Override
	public List<Schedule> selectAllUserSchedules(String userId) {
		TypedQuery<Schedule> query = entityManager.createQuery("SELECT s FROM Schedule s WHERE s.user.userId = '"
				+ userId + "')", Schedule.class);
		List<Schedule> scheduleList = query.getResultList();
		return scheduleList;
	}

	@Override
	public void saveSchedule(Schedule schd) {
		TypedQuery<Schedule> query = entityManager.createQuery("SELECT s FROM Schedule s WHERE s.scheduleId = '"
				+ schd.getScheduleId() + "')", Schedule.class);
		List<Schedule> scheduleList = query.getResultList();
		if (scheduleList != null && scheduleList.size() > 0) {
			Schedule dbSchedule = scheduleList.get(0);
			
			dbSchedule.setScheduleName(schd.getScheduleName());
			dbSchedule.setFrom_date(schd.getFrom_date());
			dbSchedule.setTo_date(schd.getTo_date());
			dbSchedule.setTimeOfDay(schd.getTimeOfDay());
			dbSchedule.setPresentation(schd.getPresentation());
			entityManager.flush();
		} else {
			entityManager.persist(schd);
		}
	}

	@Override
	public Schedule selectScheduleById(String scheduleId) {
		logger.debug("selecting ScheduleById:"+scheduleId);
		TypedQuery<Schedule> query = entityManager.createQuery("SELECT s FROM Schedule s WHERE s.scheduleId = '"
				+ scheduleId + "')", Schedule.class);
		List<Schedule> scheduleList = query.getResultList();
		if (scheduleList != null && scheduleList.size() > 0) {
			return scheduleList.get(0);
		} else {
			return null;
		}
	}

	public Schedule updatePublishFlag(Schedule schedule) {
		Schedule schd = entityManager.find(Schedule.class, schedule.getScheduleId());;
		schd.setPublished(schedule.getPublished());
		schd.setLastAccessed(new Date());
		entityManager.flush();
		return schd;
	}

	@Override
	public void deleteSchedule(String userId, String scheduleId) {
		
	/*	//Delete Active Schedule if Present
		TypedQuery<ActiveSchedule> query = entityManager.createQuery("SELECT s FROM ActiveSchedule s WHERE s.schedule.scheduleId = '"
				+ scheduleId + "')", ActiveSchedule.class);
		List<ActiveSchedule> actvScheduleList = query.getResultList();
		if (actvScheduleList != null && actvScheduleList.size() > 0) {
			entityManager.remove(actvScheduleList.get(0));
		}
		*/
		
		//Delete schedule
		TypedQuery<Schedule> query1 = entityManager.createQuery("SELECT s FROM Schedule s WHERE s.scheduleId = '" 
		+ scheduleId + "')", Schedule.class);
		List<Schedule> scheduleList = query1.getResultList();
		if (scheduleList != null && scheduleList.size() > 0) {
			entityManager.remove(scheduleList.get(0));
		}

	}

	
	@Override
	public boolean deleteActiveSchedule(String userId, String activeScheduleIds) {
		boolean status = false;
		//Delete Active Schedule if Present
		TypedQuery<ActiveSchedule> query = entityManager.createQuery("SELECT s FROM ActiveSchedule s WHERE s.activeScheduleId = '"
				+ activeScheduleIds + "' AND s.user.userId = '" + userId + "'", ActiveSchedule.class);
		List<ActiveSchedule> actvScheduleList = query.getResultList();
		if (actvScheduleList != null && actvScheduleList.size() > 0) {
			ActiveSchedule actvSchd = actvScheduleList.get(0);
			
			logger.debug("Before:: " + actvSchd.getSchedule().getActivSschedules().size());
			// if deleted do nothing
			/*if(actvSchd.getActionFlag().equals(Constants.SCHD_STOPPED)){
				// Don't delete the record
			}else if(actvSchd.getActionFlag().equals(Constants.SCHD_READY_FOR_DWNLD)
					||actvSchd.getActionFlag().equals(Constants.SCHD_NOT_USED)
					|| actvSchd.getActionFlag().equals(Constants.SCHD_DEL_ACK)
					|| actvSchd.getActionFlag().equals(Constants.SCHD_CREATED)){
				entityManager.remove(actvSchd);
				status = true;
			}else if(actvSchd.getActionFlag().equals(Constants.SCHD_DOWNLOADED)){
				actvSchd.setActionFlag(Constants.SCHD_STOPPED);
				entityManager.persist(actvSchd);
				status = true;
			}*/
			
			if (actvSchd.getActionFlag().equals(Constants.SCHD_DOWNLOADED)) {
				actvSchd.setActionFlag(Constants.SCHD_STOPPED);
				entityManager.persist(actvSchd);
				status = true;
			} else {
				entityManager.remove(actvSchd);
				status = true;
			}
			
			logger.debug("After:: " + actvSchd.getSchedule().getActivSschedules().size());
			
			// If the active schedule is the last active schedule of SCHEDULE, SCHEDULE to be unpublished.
			if(status && actvSchd.getSchedule().getActivSschedules().size() == 1){
				Schedule schd = actvSchd.getSchedule();
				schd.setPublished(Constants.SCHD_UNPUBLISHED); // Mark Unpublished
				schd.setLastAccessed(new Date());
				updatePublishFlag(schd);
			}
		}
		
		return status;
	}
	
	@Override
	public void stopSchedule(String userId, String scheduleId) {
		TypedQuery<ActiveSchedule> query = entityManager.createQuery("SELECT s FROM ActiveSchedule s WHERE s.schedule.scheduleId = '"
				+ scheduleId + "')", ActiveSchedule.class);
		List<ActiveSchedule> activeScheduleList = query.getResultList();
		if (activeScheduleList != null && activeScheduleList.size() > 0) {
			for (int s = 0; s < activeScheduleList.size(); s++) {
				ActiveSchedule actvSchd = activeScheduleList.get(s);
				if(actvSchd.getActionFlag().equals(Constants.SCHD_DOWNLOADED)){
					actvSchd.setActionFlag(Constants.SCHD_STOPPED);
				}else{
					actvSchd.setActionFlag(Constants.SCHD_NOT_USED);
				}
			}
			entityManager.flush();
		}
		
		// Mark schedule Unpublished
		
		TypedQuery<Schedule> query1 = entityManager.createQuery("SELECT s FROM Schedule s WHERE s.scheduleId = '" + scheduleId + "')", Schedule.class);
		List<Schedule> scheduleList = query1.getResultList();

		if (scheduleList != null && scheduleList.size() > 0) {
			Schedule schd = scheduleList.get(0);
			schd.setPublished(Constants.SCHD_UNPUBLISHED); // Mark Unpublished
			schd.setLastAccessed(new Date());
			updatePublishFlag(schd);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.pivision.schedule.dao.ScheduleDao#publishSchedule(com.pivision.schedule.ActiveSchedule)
	 * Logic: ONLY ONE ACTIVE SCHEDULE SHOULD BE PRESENT FOR PRESENTATION-DISPLAY 
	 * if active schedule is present for the mac_id, then do nothing else save
	 * NB- different schedules can be created using same presentation, but one presentation should be assigned to display only ONCE.
	 * 
	 */
	@Override
	public int addDisplayToSchd(ActiveSchedule activeSchedule) {
		
		int cnt = 0;
		//check if present
		TypedQuery<ActiveSchedule> query = entityManager.createQuery("SELECT actSchd FROM ActiveSchedule actSchd WHERE actSchd.display.macId = '"
				+ activeSchedule.getDisplay().getMacId()+ "' and actSchd.presentation.presentationId='"
				+ activeSchedule.getPresentation().getPresentationId() + "'", ActiveSchedule.class);
		 
		
		List<ActiveSchedule> activeSchdList = query.getResultList();
		if(activeSchdList.size()== 0){
			entityManager.persist(activeSchedule);
			cnt = 1;
		}
		
		return cnt;
	}
	
	/*
	scheduleing

	X- unused deleted -> N
	A- delete ack -> prompt N
	D- not yet ackn -> N
	C = newly added - N
	
	for stopping
	
	N- X 
	Y- D
	
	During stop period:
	D - A - if read by client
	
	delete schedule-- if any record present dont delete
	
	delete actv schedule individually
	X- allow
	A - Allow
	D- no
	C = allow
	Y - mark D
	N = allow
	
	add-- 
	if scheduled -- sts will be N	
	if not schedule- sts will be C

	 */
	
	@Override
	public void publishSchedule(Schedule schedule) {
		
		TypedQuery<ActiveSchedule> query = entityManager.createQuery("SELECT actSchd FROM ActiveSchedule actSchd WHERE actSchd.schedule.scheduleId='"
				+ schedule.getScheduleId() + "'", ActiveSchedule.class);
		 
		// Mark Schedules for download
		List<ActiveSchedule> activeSchdList = query.getResultList();
		for(int a=0 ; a<activeSchdList.size();a++){
			ActiveSchedule activeSchedule = activeSchdList.get(a);
			//if(!activeSchedule.getActionFlag().equals(Constants.SCHD_STOPPED)){
				activeSchedule.setActionFlag(Constants.SCHD_READY_FOR_DWNLD);
				//Save
				entityManager.persist(activeSchedule);
			//}
		}
		// Change flag in schedule for published-Y
		updatePublishFlag(schedule);
	}

	@Override
	public Schedule getScheduleByID(String scheduleId) {
		Schedule sch = new Schedule();

		Query q = entityManager.createNativeQuery("SELECT schedule_id,schedule_name, from_date, to_date, "
				+ "presentation_id , time_of_day" + " FROM Schedule where schedule_id = '" + scheduleId + "'");
		Object[] a = (Object[]) q.getResultList().get(0);
		
		sch.setScheduleId(new BigInteger(String.valueOf(a[0])));
		sch.setScheduleName(String.valueOf(a[1]));
		sch.setFrom_date((Date)a[2]);
		sch.setTo_date((Date)a[3]);
		sch.setTimeOfDay(String.valueOf(a[5]));

		return sch;
	}
	
}
