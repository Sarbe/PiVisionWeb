package com.pivision.schedule;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pivision.display.Display;
import com.pivision.presentation.Presentation;
import com.pivision.user.User;

@Entity
@Table(name = "active_schedule")
@NamedQuery(name="ActiveSchedule.findAll", query="SELECT s FROM ActiveSchedule s")
public class ActiveSchedule implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="active_schedule_id")
	private BigInteger activeScheduleId;
	
	@Temporal(TemporalType.DATE)
	@Column(name="active_date")
	private Date activeDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="end_date")
	private Date endDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="last_accessed")
	private Date lastAccessed;
	
	@Column(name="schedule_name")
	private String scheduleName;
	
	/**
	 * D-Deleted, Y-Downloaded, N-Not downloaded,C-Created, DR- Deleted Read by Client
	 */
	@Column(name="action_flag")
	private String actionFlag; 
	
	@Column(name="schedule_duration") //slot duration
	private Integer scheduleDuration;
	
	@Column(name="schedule_priority") 
	private Integer schedulePriority;
	
	@Column(name="config_update") //sets a flag that allows new config to be downloaded to machines.
	private char configUpdate;

	@Column(name="software_update")
	private char softwareUpdate;

	
	@Column(name="display_duration") 
	private Integer displayDduration;
	
	@Column(name="display_frequency") 
	private Integer displayFrequency;
	
	@ManyToOne
	@JoinColumn(name="mac_id")
	private Display display;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	
	@ManyToOne
	@JoinColumn(name="schedule_id")
	private Schedule schedule;

	@ManyToOne
	@JoinColumn(name="presentation_id")
	private Presentation presentation;

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getScheduleName() {
		return scheduleName;
	}

	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}

	public BigInteger getActiveScheduleId() {
		return activeScheduleId;
	}

	public void setActiveScheduleId(BigInteger activeScheduleId) {
		this.activeScheduleId = activeScheduleId;
	}

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Presentation getPresentation() {
		return presentation;
	}

	public void setPresentation(Presentation presentation) {
		this.presentation = presentation;
	}

	public String getActionFlag() {
		return actionFlag;
	}

	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}

	public char getConfigUpdate() {
		return configUpdate;
	}

	public void setConfigUpdate(char configUpdate) {
		this.configUpdate = configUpdate;
	}

	public Date getLastAccessed() {
		return lastAccessed;
	}

	public void setLastAccessed(Date lastAccessed) {
		this.lastAccessed = lastAccessed;
	}

	public Integer getScheduleDuration() {
		return scheduleDuration;
	}

	public void setScheduleDuration(Integer scheduleDuration) {
		this.scheduleDuration = scheduleDuration;
	}

	public Integer getSchedulePriority() {
		return schedulePriority;
	}

	public void setSchedulePriority(Integer schedulePriority) {
		this.schedulePriority = schedulePriority;
	}

	public char getSoftwareUpdate() {
		return softwareUpdate;
	}

	public void setSoftwareUpdate(char softwareUpdate) {
		this.softwareUpdate = softwareUpdate;
	}

	public Integer getDisplayDduration() {
		return displayDduration;
	}

	public void setDisplayDduration(Integer displayDduration) {
		this.displayDduration = displayDduration;
	}

	public Integer getDisplayFrequency() {
		return displayFrequency;
	}

	public void setDisplayFrequency(Integer displayFrequency) {
		this.displayFrequency = displayFrequency;
	}

		
}
