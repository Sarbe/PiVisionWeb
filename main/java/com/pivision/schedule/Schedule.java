package com.pivision.schedule;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pivision.presentation.Presentation;
import com.pivision.user.User;


/**
 * The persistent class for the schedule database table.
 * 
 */
@Entity
@Table(name = "schedule")
@NamedQuery(name="Schedule.findAll", query="SELECT s FROM Schedule s")
public class Schedule implements Serializable {
	private static final long serialVersionUID = 1L;


	@Column(name="schedule_name")
	private String scheduleName;

	@Column(name="gmt_diff")
	private double gmtDiff;
	
	@Temporal(TemporalType.DATE)
	@Column(name="from_date")
	private Date from_date;
	
	@Temporal(TemporalType.DATE)
	@Column(name="to_date")
	private Date to_date;
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_accessed")
	private Date lastAccessed;

	@Id
	@GeneratedValue
	@Column(name="schedule_id")
	private BigInteger scheduleId;
	

	@Column(name="is_published")
	private String published;
	
	@Column(name="time_of_day")
	private String timeOfDay;
	//bi-directional many-to-one association to Presentation
	@ManyToOne
	@JoinColumn(name="presentation_id")
	private Presentation presentation;
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	//bi-directional many-to-one association to Presentation

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="schedule_id",referencedColumnName="schedule_id")
	private List<ActiveSchedule> activSschedules;

	public Schedule() {
	}

	public String getScheduleName() {
		return this.scheduleName;
	}

	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}

	public double getGmtDiff() {
		return this.gmtDiff;
	}

	public void setGmtDiff(double gmtDiff) {
		this.gmtDiff = gmtDiff;
	}

	public Date getLastAccessed() {
		return this.lastAccessed;
	}

	public void setLastAccessed(Date lastAccessed) {
		this.lastAccessed = lastAccessed;
	}

	public BigInteger getScheduleId() {
		return this.scheduleId;
	}

	public void setScheduleId(BigInteger scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	

	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getFrom_date() {
		return from_date;
	}

	public void setFrom_date(Date from_date) {
		this.from_date = from_date;
	}

	public Date getTo_date() {
		return to_date;
	}

	public void setTo_date(Date to_date) {
		this.to_date = to_date;
	}

	public Presentation getPresentation() {
		return this.presentation;
	}

	public void setPresentation(Presentation presentation) {
		this.presentation = presentation;
	}

	public List<ActiveSchedule> getActivSschedules() {
		return activSschedules;
	}

	public void setActivSschedules(List<ActiveSchedule> activSschedules) {
		this.activSschedules = activSschedules;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getTimeOfDay() {
		return timeOfDay;
	}

	public void setTimeOfDay(String timeOfDay) {
		this.timeOfDay = timeOfDay;
	}
	
	

	

}