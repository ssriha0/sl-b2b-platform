package com.newco.marketplace.web.dto;

public class SPNLandingTableRowDTO extends SerializedBaseDTO
{
	private String id;
	private String name;
	private Integer numMatches;
	private Integer numMembers;
	private Integer numInactive;
	private Integer numApplicants;
	private Integer numNonInterested;
	private Integer numRemoved;
	
	
	public Integer getNumNonInterested() {
		return numNonInterested;
	}

	public void setNumNonInterested(Integer numNonInterested) {
		this.numNonInterested = numNonInterested;
	}

	public Integer getNumRemoved() {
		return numRemoved;
	}

	public void setNumRemoved(Integer numRemoved) {
		this.numRemoved = numRemoved;
	}

	public SPNLandingTableRowDTO(String id, String name, Integer matches, Integer members, Integer inactive, Integer applicants)
	{
		this.id = id;
		this.name = name;
		this.numMatches = matches;
		this.numMembers = members;
		this.numInactive = inactive;
		this.numApplicants = applicants;
	}
	
	public SPNLandingTableRowDTO(){
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumMatches() {
		return numMatches;
	}
	public void setNumMatches(Integer numMatches) {
		this.numMatches = numMatches;
	}
	public Integer getNumMembers() {
		return numMembers;
	}
	public void setNumMembers(Integer numMembers) {
		this.numMembers = numMembers;
	}
	public Integer getNumInactive() {
		return numInactive;
	}
	public void setNumInactive(Integer numInactive) {
		this.numInactive = numInactive;
	}
	public Integer getNumApplicants() {
		return numApplicants;
	}
	public void setNumApplicants(Integer numApplicants) {
		this.numApplicants = numApplicants;
	}
	
	
	
}
