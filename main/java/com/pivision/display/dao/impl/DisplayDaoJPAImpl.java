package com.pivision.display.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.pivision.display.Display;
import com.pivision.display.dao.DisplayDao;
import com.pivision.schedule.ActiveSchedule;
import com.pivision.user.User;


@Repository
public class DisplayDaoJPAImpl implements DisplayDao {
	private static final Logger logger = Logger.getLogger(DisplayDaoJPAImpl.class);
	@PersistenceContext(unitName="PiVisionPersistence")
	private EntityManager entityManager;

	
	@Override
	public List<Display> selectAllDisplays(User user) {
		String qry = "SELECT c FROM Display c ";
		if(!user.getRole().equals("ADMIN")){
			qry +=" WHERE c.user.userId = '"+user.getUserId()+"'" ;
		}
		TypedQuery<Display> query = entityManager.createQuery(qry, Display.class);
		List<Display> displayList = query.getResultList();
		return displayList;
	}
	
	@Override
	public void addDisplay(Display display) {
		Display dbDisplay = findDisplayWithUserMacId(display);
		if(null == dbDisplay){
			entityManager.persist(display);
		}else{
			
		}
	}

	@Override
	public void deleteDisplayWithUserMacId(User user,String macId) {
		String qry = "SELECT c FROM Display c WHERE c.macId = '" + macId + "'";
		if(!user.getRole().equals("ADMIN")){
			qry += " AND c.user.userId = '"+user.getUserId()+"'";
		}
		
		TypedQuery<Display> query = entityManager.createQuery(qry, Display.class);
		List<Display> displayList = query.getResultList();
		if (displayList != null && displayList.size() > 0) {
			entityManager.remove(displayList.get(0));
		} 
	}
	public Display findDisplayWithUserMacId(Display display) {
		TypedQuery<Display> query = entityManager.createQuery(
				"SELECT c FROM Display c WHERE c.macId = '" + display.getMacId() +"'", Display.class);
		List<Display> displayList = query.getResultList();
		if (displayList != null && displayList.size() > 0) {
			return displayList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Display findDisplayWithMacId(String macId) {
		return entityManager.find(Display.class, macId);
	}

	@Override
	public int saveDisplayDetails(Display display) {
		Display dbDisplay = findDisplayWithMacId(display.getMacId());
		if (dbDisplay != null) {
			dbDisplay.setDuration(display.getDuration());
			dbDisplay.setFrequency(display.getFrequency());
			dbDisplay.setLastModified(new Date());
			
			//Change in active schedule
			logger.info("Changing display details in Active schedule for MAC :: "+ display.getMacId());
			TypedQuery<ActiveSchedule> query = entityManager.createQuery("SELECT s FROM ActiveSchedule s WHERE s.display.macId = '"
					+ display.getMacId() + "')", ActiveSchedule.class);
			List<ActiveSchedule> activeScheduleList = query.getResultList();
			for(int i= 0; i<activeScheduleList.size();i++){
				ActiveSchedule actvSchd = activeScheduleList.get(i);
				actvSchd.setConfigUpdate('Y');
				actvSchd.setDisplayDduration(display.getDuration());
				actvSchd.setDisplayFrequency(display.getFrequency());
				actvSchd.setLastAccessed(new Date());
			}
			entityManager.flush();
			return 1;
		}
		return 0;
	}
	
}
