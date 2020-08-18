package com.newco.marketplace.dto.vo.spn;

import com.sears.os.vo.SerializableBaseVO;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */

/*
 * Maintenance History: See bottom of file.
 */
public class SPNSummaryVO extends SerializableBaseVO {

	private static final long serialVersionUID = -4366340232819964389L;

	private Integer spnId;
	private String name;
	private Integer matchesCnt;
	private Integer invitedCnt;
	private Integer memberCnt;
	private Integer applicantCnt;
	private Integer inactiveCnt;
	private Integer notInterestedCnt;
	private Integer removedCnt;
	
	/**
	 * @return the spnId
	 */
	public Integer getSpnId() {
		return spnId;
	}
	/**
	 * @param spnId the spnId to set
	 */
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the matchesCnt
	 */
	public Integer getMatchesCnt() {
		return matchesCnt;
	}
	/**
	 * @param matchesCnt the matchesCnt to set
	 */
	public void setMatchesCnt(Integer matchesCnt) {
		this.matchesCnt = matchesCnt;
	}
	/**
	 * @return the memberCnt
	 */
	public Integer getMemberCnt() {
		return memberCnt;
	}
	/**
	 * @param memberCnt the memberCnt to set
	 */
	public void setMemberCnt(Integer memberCnt) {
		this.memberCnt = memberCnt;
	}
	/**
	 * @return the applicantCnt
	 */
	public Integer getApplicantCnt() {
		return applicantCnt;
	}
	/**
	 * @param applicantCnt the applicantCnt to set
	 */
	public void setApplicantCnt(Integer applicantCnt) {
		this.applicantCnt = applicantCnt;
	}
	/**
	 * @return the inactiveCnt
	 */
	public Integer getInactiveCnt() {
		return inactiveCnt;
	}
	/**
	 * @param inactiveCnt the inactiveCnt to set
	 */
	public void setInactiveCnt(Integer inactiveCnt) {
		this.inactiveCnt = inactiveCnt;
	}
	/**
	 * @return the notInterestedCnt
	 */
	public Integer getNotInterestedCnt() {
		return notInterestedCnt;
	}
	/**
	 * @param notInterestedCnt the notInterestedCnt to set
	 */
	public void setNotInterestedCnt(Integer notInterestedCnt) {
		this.notInterestedCnt = notInterestedCnt;
	}
	/**
	 * @return the removedCnt
	 */
	public Integer getRemovedCnt() {
		return removedCnt;
	}
	/**
	 * @param removedCnt the removedCnt to set
	 */
	public void setRemovedCnt(Integer removedCnt) {
		this.removedCnt = removedCnt;
	}
	/**
	 * @return the invitedCnt
	 */
	public Integer getInvitedCnt() {
		return invitedCnt;
	}
	/**
	 * @param invitedCnt the invitedCnt to set
	 */
	public void setInvitedCnt(Integer invitedCnt) {
		this.invitedCnt = invitedCnt;
	}

}
/*
 * Maintenance History
 * $Log: SPNSummaryVO.java,v $
 * Revision 1.2  2008/05/02 21:23:58  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.1.2.2  2008/04/10 16:18:06  mhaye05
 * updates to all for a scheduled job to load the summary table
 *
 * Revision 1.1.2.1  2008/04/08 20:48:51  mhaye05
 * Initial check in
 *
 */