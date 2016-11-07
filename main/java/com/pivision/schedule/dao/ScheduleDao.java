package com.pivision.schedule.dao;

import java.util.List;

import com.pivision.schedule.ActiveSchedule;
import com.pivision.schedule.Schedule;

public interface ScheduleDao {
	
	public void saveSchedule(Schedule schedule);

	public List<Schedule> selectAllUserSchedules(String userId);

	public Schedule selectScheduleById(String scheduleId);
	
	public void deleteSchedule(String userId, String scheduleId);

	public Schedule getScheduleByID(String scheduleId);

	public Schedule updatePublishFlag(Schedule schedule);

	public int addDisplayToSchd(ActiveSchedule activeSchedule);

	public void stopSchedule(String userId, String scheduleId);

	public boolean deleteActiveSchedule(String userId, String activeScheduleIds);

	public void publishSchedule(Schedule schedule);
	
	

}
