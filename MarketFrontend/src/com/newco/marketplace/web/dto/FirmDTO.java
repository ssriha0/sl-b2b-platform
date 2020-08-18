package com.newco.marketplace.web.dto;

import java.sql.Timestamp;


public class FirmDTO implements Comparable<FirmDTO>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8835852878039152726L;
	
	private Integer firmId;
	private Double firmscore;
	private String firmRank;
	private Timestamp routedDate;
	
	public Integer getFirmId() {
		return firmId;
	}
	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}
	public Double getFirmscore() {
		return firmscore;
	}
	public void setFirmscore(Double firmscore) {
		this.firmscore = firmscore;
	}
	public String getFirmRank() {
		return firmRank;
	}
	public void setFirmRank(String firmRank) {
		this.firmRank = firmRank;
	}
	public Timestamp getRoutedDate() {
		return routedDate;
	}
	public void setRoutedDate(Timestamp routedDate) {
		this.routedDate = routedDate;
	}
	public int compareTo(FirmDTO arg0) {
		return firmId.compareTo(arg0.firmId);
	}

}
