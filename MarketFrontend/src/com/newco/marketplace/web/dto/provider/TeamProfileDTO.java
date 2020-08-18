/*
** TeamProfileForm.java  1.0     2007/06/05
*/
package com.newco.marketplace.web.dto.provider;

import java.util.List;

import com.newco.marketplace.vo.provider.TeamMemberVO;

/**
 * Struts Form for Team member
 * 
 * @version
 * @author blars04
 *
 */

public class TeamProfileDTO extends BaseDto
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List <TeamMemberVO>teamMemberList= null;
    private String userName=null;
    private int count=0;
    private Integer vendorId=0;

    public int getCount() {
    	
    	if(teamMemberList!=null){
    		count=teamMemberList.size();
    	}
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
     * @return the teamMembers
     */
    public List <TeamMemberVO> getTeamMemberList() {
        return teamMemberList;
    }

    /**
     * @param teamMembers the teamMembers to set
     */
    public void setTeamMemberList(List <TeamMemberVO> teamMembers) {
        this.teamMemberList = teamMembers;
    }

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	

}//end class TeamProfileForm