package com.pivision.display;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pivision.schedule.ActiveSchedule;
import com.pivision.user.User;


/**
 * The persistent class for the display database table.
 * 
 */
@Entity
@Table(name = "display")
@NamedQuery(name="Display.findAll", query="SELECT d FROM Display d")
public class Display implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="mac_id")
	private String macId;

	@Temporal(TemporalType.DATE)
	private Date added;

	@Column(name="display_key")
	private String displayKey;

	@Column(name="display_name")
	private String displayName;

	@Column(name="display_status")
	private char displayStatus;

	@Temporal(TemporalType.DATE)
	@Column(name="last_modified")
	private Date lastModified;
	
	private String resolution;
	
	@Column(name="location")
	private String displayLocation;
	@Column(name="location_type")
	private String displayLocationType;
	@Column(name="address")
	private String displayAddress ;
	@Column(name="city")
	private String displayCity;
	@Column(name="district")
	private String displayDistrict;
	@Column(name="state")
	private String displayState;
	@Column(name="pin")
	private String displayPin;
	
	@Column(name="partyName")
	private String partyName;
	
	@Column(name="partyPhone")
	private String partyPhone;
	
	@Column(name="duration")
	private Integer duration;
	@Column(name="frequency")
	private Integer frequency;
	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id",referencedColumnName="user_id")
	private User user;
	
	//bi-directional many-to-one association to Presentation
	@OneToMany
	@JoinColumn(name="mac_id",referencedColumnName="mac_id")
	private List<ActiveSchedule> activSschedules;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_pinged")
	private Date lastPinged;
	
	@Column(name="imgrefresh_sts")
	private String imgRefreshSts;
	
	
	public Display() {
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}


	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public String getMacId() {
		return this.macId;
	}

	public void setMacId(String macId) {
		this.macId = macId;
	}

	public Date getAdded() {
		return this.added;
	}

	public void setAdded(Date added) {
		this.added = added;
	}

	public String getDisplayKey() {
		return this.displayKey;
	}

	public void setDisplayKey(String displayKey) {
		this.displayKey = displayKey;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	
	public char getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(char displayStatus) {
		this.displayStatus = displayStatus;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getResolution() {
		return this.resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ActiveSchedule> getActivSschedules() {
		return activSschedules;
	}

	public void setActivSschedules(List<ActiveSchedule> activSschedules) {
		this.activSschedules = activSschedules;
	}

	public Date getLastPinged() {
		return lastPinged;
	}

	public void setLastPinged(Date lastPinged) {
		this.lastPinged = lastPinged;
	}

	public String getImgRefreshSts() {
		return imgRefreshSts;
	}

	public void setImgRefreshSts(String imgRefreshSts) {
		this.imgRefreshSts = imgRefreshSts;
	}

	public String getDisplayLocation() {
		return displayLocation;
	}

	public void setDisplayLocation(String displayLocation) {
		this.displayLocation = displayLocation;
	}

	public String getDisplayLocationType() {
		return displayLocationType;
	}

	public void setDisplayLocationType(String displayLocationType) {
		this.displayLocationType = displayLocationType;
	}

	public String getDisplayAddress() {
		return displayAddress;
	}

	public void setDisplayAddress(String displayAddress) {
		this.displayAddress = displayAddress;
	}

	public String getDisplayCity() {
		return displayCity;
	}

	public void setDisplayCity(String displayCity) {
		this.displayCity = displayCity;
	}

	public String getDisplayDistrict() {
		return displayDistrict;
	}

	public void setDisplayDistrict(String displayDistrict) {
		this.displayDistrict = displayDistrict;
	}

	public String getDisplayState() {
		return displayState;
	}

	public void setDisplayState(String displayState) {
		this.displayState = displayState;
	}

	public String getDisplayPin() {
		return displayPin;
	}

	public void setDisplayPin(String displayPin) {
		this.displayPin = displayPin;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getPartyPhone() {
		return partyPhone;
	}

	public void setPartyPhone(String partyPhone) {
		this.partyPhone = partyPhone;
	}
	
	

//	public Schedule addSchedule(Schedule schedule) {
//		getSchedules().add(schedule);
//		schedule.setDisplay(this);
//
//		return schedule;
//	}
//
//	public Schedule removeSchedule(Schedule schedule) {
//		getSchedules().remove(schedule);
//		schedule.setDisplay(null);
//
//		return schedule;
//	}

}