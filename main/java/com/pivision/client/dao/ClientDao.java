package com.pivision.client.dao;

import java.math.BigInteger;
import java.util.List;

import com.pivision.schedule.ActiveSchedule;

public interface ClientDao {

	public List<ActiveSchedule> getActiveSchedule(String macId);

	public void updateActiveSchedule(ActiveSchedule activeSchedule);

	public void deleteSchdFromActiveSchedule(ActiveSchedule activeSchedule);

	public int chkForScheduleContent(String macId);

	public List<BigInteger> chkForDeActiveScheduleContent(String macId);

	public void removeDeActiveSchedule(List<BigInteger> delSchIds);

	public void updateDisplayStatus(String macId, String refreshSts);

	public ActiveSchedule getConfigDetails(String macId);

	public void updateConfigStatus(String macId);

	public ActiveSchedule getSoftwareUpdate(String macId);

	public void updatesoftwareUpdStatus(String macId, String sts);

}
