package com.pivision.client;

import java.math.BigInteger;
import java.util.List;

public class InfoPayLoad {

	private String configUpdate = "N"; // respond with what type of info
										// configuration or content or both
	private String contentUpdate = "N";
	
	/*private String softwareUpdate = "N";
	private String downloaLink = "";
	private String fileChkSum = "";*/
	
	private int duration; // needed to change slot duration if required
	private int frequency; // needed to change HDMI switching frequency
	private List<BigInteger> delSchIds;


	
	public String getConfigUpdate() {
		return configUpdate;
	}

	public void setConfigUpdate(String configUpdate) {
		this.configUpdate = configUpdate;
	}

	public String getContentUpdate() {
		return contentUpdate;
	}

	public void setContentUpdate(String contentUpdate) {
		this.contentUpdate = contentUpdate;
	}

/*	public String getSoftwareUpdate() {
		return softwareUpdate;
	}

	
	public String getDownloaLink() {
		return downloaLink;
	}

	public void setDownloaLink(String downloaLink) {
		this.downloaLink = downloaLink;
	}

	public String getFileChkSum() {
		return fileChkSum;
	}

	public void setFileChkSum(String fileChkSum) {
		this.fileChkSum = fileChkSum;
	}

	public void setSoftwareUpdate(String softwareUpdate) {
		this.softwareUpdate = softwareUpdate;
	}
*/
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public List<BigInteger> getDelSchIds() {
		return delSchIds;
	}

	public void setDelSchIds(List<BigInteger> delSchIds) {
		this.delSchIds = delSchIds;
	}

	


}
