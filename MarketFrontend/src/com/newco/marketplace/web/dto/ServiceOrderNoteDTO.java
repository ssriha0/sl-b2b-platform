package com.newco.marketplace.web.dto;

import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.CreditCardValidatonUtil;

public class ServiceOrderNoteDTO extends SOWBaseTabDTO implements OrderConstants{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4364503290652165569L;
	private String soId = "";
	private String groupId="";
	private int noteTypeId = -1;
	private String subject = "";
	private String subjectSupport = "[Subject]";
	private String message = "";
	private String messageSupport = "[Message]";
	private String modifiedBy = "";
	private String radioSelection; // Public: 0, Public no email: 2, Private: 1 
	private List<Integer> noteTypeIds;
	private boolean emptyNoteAllowed;
	
	
	private boolean isEmailTobeSent = false;
	
	//SL-19050
	//Indicator to check new buyer feature set 'NOTES_FEATURE' 
	private boolean noteFeatureInd;
	
	/**
	 * @return the emptyNoteAllowed
	 */
	public boolean isEmptyNoteAllowed() {
		return emptyNoteAllowed;
	}

	/**
	 * @param emptyNoteAllowed the emptyNoteAllowed to set
	 */
	public void setEmptyNoteAllowed(boolean emptyNoteAllowed) {
		this.emptyNoteAllowed = emptyNoteAllowed;
	}

	// List of Group Level Notes 
	private List<ServiceOrderNote> groupNotes = null;
	
	// List of ServiceOrderNote 
	private List<ServiceOrderNote> notes = null;
	
	// List of ServiceOrderNote for Deleted Note
	private List<ServiceOrderNote> deletedNotes = null;
	
	// User role ID
	private Integer roleId = null;
	
	
	public int getNoteTypeId() {
		return noteTypeId;
	}

	public void setNoteTypeId(int noteTypeId) {
		this.noteTypeId = noteTypeId;
	}

	public String getSoId() {
		return soId;
	}
	
	public void setSoId(String soId) {
		this.soId = soId;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<ServiceOrderNote> getNotes() {
		return notes;
	}

	public void setNotes(List notes) {
		this.notes = notes;
	}
	
	public List<ServiceOrderNote> getDeletedNotes() {
		return deletedNotes;
	}

	public void setDeletedNotes(List deletedNotes) {
		this.deletedNotes = deletedNotes;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getMessageSupport() {
		return messageSupport;
	}

	public void setMessageSupport(String messageSupport) {
		this.messageSupport = messageSupport;
	}

	public String getSubjectSupport() {
		return subjectSupport;
	}

	public void setSubjectSupport(String subjectSupport) {
		this.subjectSupport = subjectSupport;
	}

	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub
	}
	
	public void validate(String fromPage) {

        if (soId.equalsIgnoreCase(""))
        {
       	 addError("So Id", MISSING_SOID, OrderConstants.SOW_TAB_ERROR);
        }
	       
		if (fromPage.equalsIgnoreCase("Note")){
			   if (subject.equalsIgnoreCase("") || subject.equalsIgnoreCase("[Subject]"))
		       {
		       	addError("Subject", MISSING_SUBJECT, OrderConstants.SOW_TAB_ERROR);
		       } else {
					if (CreditCardValidatonUtil.validateCCNumbers(subject)) {
						addError("Subject", VALIDATION_SUBJECT, OrderConstants.SOW_TAB_ERROR);
					}
				}
		       if (message.equalsIgnoreCase("") || message.equalsIgnoreCase("[Message]"))
		       {
		       	addError("Message", MISSING_MESSAGE, OrderConstants.SOW_TAB_ERROR);
		       } else {
					if (CreditCardValidatonUtil.validateCCNumbers(message)) {
						addError("Message", VALIDATION_MESSAGE, OrderConstants.SOW_TAB_ERROR);
					}
				}
		      
		    }else 
			{
		    	 if (subjectSupport.equalsIgnoreCase("") || subjectSupport.equalsIgnoreCase("[Subject]"))
			       {
			       	addError("SubjectSupport", MISSING_SUBJECT, OrderConstants.SOW_TAB_ERROR);
			       }
			       if (messageSupport.equalsIgnoreCase("") || messageSupport.equalsIgnoreCase("[Message]"))
			       {
			       	addError("messageSupport", MISSING_MESSAGE, OrderConstants.SOW_TAB_ERROR);
			       }
			}
		
	}

	public String getRadioSelection() {
		return radioSelection;
	}

	public void setRadioSelection(String radioSelection) {
		this.radioSelection = radioSelection;
	}

	public List<Integer> getNoteTypeIds() {
		return noteTypeIds;
	}

	public void setNoteTypeIds(List<Integer> noteTypeIds) {
		this.noteTypeIds = noteTypeIds;
	}

	public List<ServiceOrderNote> getGroupNotes()
	{
		return groupNotes;
	}

	public void setGroupNotes(List<ServiceOrderNote> groupNotes)
	{
		this.groupNotes = groupNotes;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public boolean isEmailTobeSent() {
		return isEmailTobeSent;
	}

	public void setEmailTobeSent(boolean isEmailTobeSent) {
		this.isEmailTobeSent = isEmailTobeSent;
	}
	
	public boolean isNoteFeatureInd() {
		return noteFeatureInd;
	}

	public void setNoteFeatureInd(boolean noteFeatureInd) {
		this.noteFeatureInd = noteFeatureInd;
	}


}
