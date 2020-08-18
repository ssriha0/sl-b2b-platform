package com.newco.marketplace.api.mobile.beans.getTeamMembers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="getTeamMembersResponse.xsd", path="/resources/schemas/mobile/v3_1/")
@XStreamAlias("getTeamMembersResponse")
@XmlRootElement(name="getTeamMembersResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ViewTeamMembersResponse implements IAPIResponse {

	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("teamMemberDetails")
	private TeamMemberDetails teamMemberDetails;
	
	
	public TeamMemberDetails getTeamMemberDetails() {
		return teamMemberDetails;
	}

	public void setTeamMemberDetails(TeamMemberDetails teamMemberDetails) {
		this.teamMemberDetails = teamMemberDetails;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public void setVersion(String version) {
		// TODO Auto-generated method stub
		
	}

	public Results getResults() {
		return results;
	}

	public void setSchemaLocation(String schemaLocation) {
		// TODO Auto-generated method stub
		
	}

	public void setNamespace(String namespace) {
		// TODO Auto-generated method stub
		
	}

	public void setSchemaInstance(String schemaInstance) {
		// TODO Auto-generated method stub
		
	}

}
