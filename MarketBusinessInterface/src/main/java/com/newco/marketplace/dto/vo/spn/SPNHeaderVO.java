package com.newco.marketplace.dto.vo.spn;

import java.util.Date;
import java.util.List;

import com.newco.marketplace.dto.vo.DocumentVO;
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
public class SPNHeaderVO extends SerializableBaseVO {

	private static final long serialVersionUID = -6440864773956530708L;
	
	private Integer spnId;
	private Integer buyerId;
	private String businessName;
	private String name;
	private String contactName;
	private String contactEmail;
	private String instruction;
	private String description;
	private boolean docRequired;
	private Integer docRequiredInt;
	private List<DocumentVO> spnRelatedDocumentIds;
	private SPNCriteriaVO spnCriteriaVO;
	private boolean spnLocked;
	private Integer spnLockedInt;
	private String criteriaDescr;
	private String spnName;
	private Date inviteDate;
	private Integer networkId;
	private String inviteeFirstName;
	private String inviteeLastName;
	
	public Integer getNetworkId() {
		return networkId;
	}


	public void setNetworkId(Integer networkId) {
		this.networkId = networkId;
	}


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
	 * @return the buyerId
	 */
	public Integer getBuyerId() {
		return buyerId;
	}


	/**
	 * @param buyerId the buyerId to set
	 */
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
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
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}


	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}


	/**
	 * @return the contactEmail
	 */
	public String getContactEmail() {
		return contactEmail;
	}


	/**
	 * @param contactEmail the contactEmail to set
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}


	/**
	 * @return the instruction
	 */
	public String getInstruction() {
		return instruction;
	}


	/**
	 * @param instruction the instruction to set
	 */
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the docRequired
	 */
	public boolean isDocRequired() {
		return docRequired;
	}


	/**
	 * @param docRequired the docRequired to set
	 */
	public void setDocRequired(boolean docRequired) {
		this.docRequired = docRequired;
		if (docRequired) {
			docRequiredInt = new Integer(1);
		} else {
			docRequiredInt = new Integer(0);
		}
	}


	/**
	 * @return the docRequiredInt
	 */
	public Integer getDocRequiredInt() {
		return docRequiredInt;
	}

	/**
	 * 
	 * @param docRequiredInt
	 */
	public void setDocRequiredInt(Integer docRequiredInt) {
		this.docRequiredInt = docRequiredInt;
		if (docRequiredInt.intValue() == 1) {
			docRequired = true;
		} else {
			docRequired = false;
		}
	}

	/**
	 * @return the spnRelatedDocumentIds
	 */
	public List<DocumentVO> getSpnRelatedDocumentIds() {
		return spnRelatedDocumentIds;
	}


	/**
	 * @param spnRelatedDocumentIds the spnRelatedDocumentIds to set
	 */
	public void setSpnRelatedDocumentIds(List<DocumentVO> spnRelatedDocumentIds) {
		this.spnRelatedDocumentIds = spnRelatedDocumentIds;
	}


	/**
	 * @return the spnCriteriaVO
	 */
	public SPNCriteriaVO getSpnCriteriaVO() {
		return spnCriteriaVO;
	}


	/**
	 * @param spnCriteriaVO the spnCriteriaVO to set
	 */
	public void setSpnCriteriaVO(SPNCriteriaVO spnCriteriaVO) {
		this.spnCriteriaVO = spnCriteriaVO;
	}


	/**
	 * @return the spnLocked
	 */
	public boolean isSpnLocked() {
		return spnLocked;
	}


	/**
	 * @param spnLocked the spnLocked to set
	 */
	public void setSpnLocked(boolean spnLocked) {
		this.spnLocked = spnLocked;
		if (spnLocked) {
			spnLockedInt = new Integer(1);
		} else {
			spnLockedInt = new Integer(0);
		}
	}


	/**
	 * @return the spnLockedInt
	 */
	public Integer getSpnLockedInt() {
		return spnLockedInt;
	}


	/**
	 * @param spnLockedInt the spnLockedInt to set
	 */
	public void setSpnLockedInt(Integer spnLockedInt) {
		this.spnLockedInt = spnLockedInt;
		if (spnLockedInt.intValue() == 1) {
			spnLocked = true;
		} else {
			spnLocked = false;
		}
	}


	/**
	 * @return the criteriaDescr
	 */
	public String getCriteriaDescr() {
		return criteriaDescr;
	}


	/**
	 * @param criteriaDescr the criteriaDescr to set
	 */
	public void setCriteriaDescr(String criteriaDescr) {
		this.criteriaDescr = criteriaDescr;
	}


	public String getSpnName() {
		return spnName;
	}


	public void setSpnName(String spnName) {
		this.spnName = spnName;
	}


	public String getBusinessName() {
		return businessName;
	}


	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}


	public Date getInviteDate() {
		return inviteDate;
	}


	public void setInviteDate(Date inviteDate) {
		this.inviteDate = inviteDate;
	}


	public String getInviteeLastName() {
		return inviteeLastName;
	}


	public void setInviteeLastName(String inviteeLastName) {
		this.inviteeLastName = inviteeLastName;
	}


	public String getInviteeFirstName() {
		return inviteeFirstName;
	}


	public void setInviteeFirstName(String inviteeFirstName) {
		this.inviteeFirstName = inviteeFirstName;
	}

	
}
/*
 * Maintenance History:
 * $Log: SPNHeaderVO.java,v $
 * Revision 1.2  2008/05/02 21:23:57  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.1.2.9  2008/04/25 18:33:56  cgarc03
 * Added new 'inviteeFirstName' and 'inviteeLastName' data members.
 *
 * Revision 1.1.2.8  2008/04/22 20:36:49  cgarc03
 * Added 2 new data members.
 *
 * Revision 1.1.2.7  2008/04/18 22:09:29  dmill03
 * *** empty log message ***
 *
 * Revision 1.1.2.6  2008/04/11 16:18:10  mhaye05
 * updates for spn criteria description
 *
 * Revision 1.1.2.5  2008/04/10 22:24:00  mhaye05
 * updated with removed class
 *
 * Revision 1.1.2.4  2008/04/10 21:49:03  mhaye05
 * updates
 *
 * Revision 1.1.2.3  2008/04/10 19:07:14  mhaye05
 * added additional attributes for skills
 *
 * Revision 1.1.2.2  2008/04/09 14:44:37  mhaye05
 * added additional attributes
 *
 * Revision 1.1.2.1  2008/04/08 20:48:50  mhaye05
 * Initial check in
 *
 */