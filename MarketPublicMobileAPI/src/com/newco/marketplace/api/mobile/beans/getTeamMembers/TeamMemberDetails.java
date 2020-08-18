package com.newco.marketplace.api.mobile.beans.getTeamMembers;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("teamMemberDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamMemberDetails {

	@XStreamAlias("totalUsers")
	private Integer totalUsers;
	
	@XStreamImplicit(itemFieldName="teamMemberDetail")
	private List<TeamMemberDetail> teamMemberDetail;

	public List<TeamMemberDetail> getTeamMemberDetail() {
		return teamMemberDetail;
	}

	public void setTeamMemberDetail(List<TeamMemberDetail> teamMemberDetail) {
		this.teamMemberDetail = teamMemberDetail;
	}
	
	public Integer getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(Integer totalUsers) {
		this.totalUsers = totalUsers;
	}

}
