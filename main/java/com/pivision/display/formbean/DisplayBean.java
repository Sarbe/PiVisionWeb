package com.pivision.display.formbean;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class DisplayBean {

	private String userId;
	@NotBlank(message="Required")
	@Size(min=17,max=17,message = "Should be of 17 character")
	private String macId;
	private Date added;
	private String displayKey;
	@NotBlank(message="Required")
	private String displayName;
	private char displayStatus;
	private Date lastModified;
	private String resolution;

	private String partyName;
	private String partyPhone;
	
	private String displayLocation;
	private String displayLocationType;
	private String displayAddress ;
	private String displayCity;
	private String displayDistrict;
	private String displayState;
	private String displayPin;
	
	private Integer duration;
	private Integer frequency;
	private String imgRefreshSts;
	
	
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId.trim();
	}

	public String getMacId() {
		return macId;
	}

	public void setMacId(String macId) {
		this.macId = macId.trim();
	}

	public Date getAdded() {
		return added;
	}

	public void setAdded(Date added) {
		this.added = added;
	}

	public String getDisplayKey() {
		return displayKey;
	}

	public void setDisplayKey(String displayKey) {
		this.displayKey = displayKey.trim();
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName.trim();
	}


	public char getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(char displayStatus) {
		this.displayStatus = displayStatus;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution.trim();
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

	public String getImgRefreshSts() {
		return imgRefreshSts;
	}

	public void setImgRefreshSts(String imgRefreshSts) {
		this.imgRefreshSts = imgRefreshSts;
	}

}
