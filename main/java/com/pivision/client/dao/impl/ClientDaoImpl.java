package com.pivision.client.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.pivision.client.dao.ClientDao;
import com.pivision.display.Display;
import com.pivision.schedule.ActiveSchedule;
import com.pivision.util.CommonFunction;

@Repository
public class ClientDaoImpl implements ClientDao {

	private static final Logger logger = Logger.getLogger(ClientDaoImpl.class);
	
	@PersistenceContext(unitName="PiVisionPersistence")
	private EntityManager entityManager;
	
	@Override
	public List<ActiveSchedule> getActiveSchedule(String macId) {
		// TODO Auto-generated method stub
		TypedQuery<ActiveSchedule> query = entityManager.createQuery(
				"SELECT a FROM ActiveSchedule a WHERE a.display.macId = '" + macId + "'", ActiveSchedule.class);
		List<ActiveSchedule> schedueleList = query.getResultList();
		return schedueleList;

		
	}
	
	@Override
	public int chkForScheduleContent(String macId) {
		List<Long> count = entityManager.createQuery(
		        "SELECT COUNT(a) FROM ActiveSchedule a WHERE a.display.macId = '" + macId + "' and action_flag='N' ",Long.class)
		        .getResultList();
		if(count.size()>0)
			return count.get(0).intValue();
		return 0;
	}
	
	@Override
	public List<BigInteger> chkForDeActiveScheduleContent(String macId) {
		List<BigInteger> deleSchIds = entityManager.createQuery(
				"SELECT a.activeScheduleId FROM ActiveSchedule a WHERE a.display.macId = '" + macId
						+ "' and action_flag='D' ", BigInteger.class).getResultList();
		//System.out.println(deleSchIds);
		return deleSchIds;

	}
	
	
	
	public void updateActiveSchedule(ActiveSchedule activeSchedule)
	{
		logger.debug("updating active schedule with download flag:"+activeSchedule.getActionFlag()+": config flag :"+activeSchedule.getConfigUpdate());
		entityManager.persist(activeSchedule);
	}

	@Override
	public void deleteSchdFromActiveSchedule(ActiveSchedule activeSchedule) {
		// TODO Auto-generated method stub
		entityManager.remove(activeSchedule);
	}

	@Override
	public void removeDeActiveSchedule(List<BigInteger> delSchIds) {
		entityManager.createQuery(
				"update ActiveSchedule a set a.actionFlag = 'A' WHERE a.activeScheduleId IN (" + CommonFunction.listToInClauseStr(delSchIds) + ")")
				.executeUpdate();
	}

	@Override
	public void updateDisplayStatus(String macId, String refreshSts) {
		TypedQuery<Display> query = entityManager.createQuery("SELECT d FROM Display d WHERE d.macId = '"
				+  macId + "')", Display.class);
		List<Display> displayList = query.getResultList();
		if (displayList != null && displayList.size() > 0) {
			Display display = displayList.get(0);
			display.setLastPinged(new Date());
			display.setImgRefreshSts(refreshSts);
			entityManager.flush();
		}
	}

	@Override
	public ActiveSchedule getConfigDetails(String macId) {
		List<ActiveSchedule> actvSchds = entityManager.createQuery(
				"SELECT a FROM ActiveSchedule a WHERE a.display.macId = '" + macId
						+ "' and configUpdate='Y' ", ActiveSchedule.class).getResultList();
		if(actvSchds != null && actvSchds.size()>0)
			return actvSchds.get(0);
		return null;
	}
	
	@Override
	public ActiveSchedule getSoftwareUpdate(String macId) {
		List<ActiveSchedule> actvSchds = entityManager.createQuery(
				"SELECT a FROM ActiveSchedule a WHERE a.display.macId = '" + macId
						+ "' and softwareUpdate='Y' ", ActiveSchedule.class).getResultList();
		if(actvSchds != null && actvSchds.size()>0)
			return actvSchds.get(0);
		return null;
	}

	@Override
	public void updateConfigStatus(String macId) {
		entityManager.createQuery(
				"update ActiveSchedule a set a.configUpdate = 'N' WHERE a.display.macId = '" + macId+ "'")
				.executeUpdate();
		
	}
	
	@Override
	public void updatesoftwareUpdStatus(String macId, String sts) {
		entityManager.createQuery(
				"update ActiveSchedule a set a.softwareUpdate = '" + sts
		+"' WHERE a.display.macId = '" + macId+ "'")
				.executeUpdate();
		
	}
}
