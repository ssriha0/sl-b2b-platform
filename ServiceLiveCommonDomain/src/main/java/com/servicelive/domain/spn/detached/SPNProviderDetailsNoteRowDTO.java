package com.servicelive.domain.spn.detached;

import java.io.Serializable;
import java.util.Date;

public class SPNProviderDetailsNoteRowDTO implements Serializable, Comparable<SPNProviderDetailsNoteRowDTO>{

	private static final long serialVersionUID = 1360270755176478748L;

	private Date date;
	private String enteredByName;
	private Integer enteredByID;
	private String notes;
	private Integer serviceProIdForFirm; // this is used specifically for Provider Firm Notes Section to store Service pro under him 
	private String serviceProNameForFirm; // this is used specifically for Provider Firm Notes Section to store Service pro under him
	
	public Integer getServiceProIdForFirm() {
		return serviceProIdForFirm;
	}
	public void setServiceProIdForFirm(Integer serviceProIdForFirm) {
		this.serviceProIdForFirm = serviceProIdForFirm;
	}
	public String getServiceProNameForFirm() {
		return serviceProNameForFirm;
	}
	public void setServiceProNameForFirm(String serviceProNameForFirm) {
		this.serviceProNameForFirm = serviceProNameForFirm;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getEnteredByName() {
		return enteredByName;
	}
	public void setEnteredByName(String enteredByName) {
		this.enteredByName = enteredByName;
	}
	public Integer getEnteredByID() {
		return enteredByID;
	}
	public void setEnteredByID(Integer enteredByID) {
		this.enteredByID = enteredByID;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(SPNProviderDetailsNoteRowDTO o) {
		if(o.getDate() != null  && this.getDate() != null) {
			return (this.getDate().after(o.getDate()) ? -1 : 1 ) ;
		}
		return 0;
	}
	
	
	
	
}
