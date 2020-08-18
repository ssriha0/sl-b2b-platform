package com.newco.marketplace.vo.leadmanagement.lead;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Attachment {

	private String documentName;
	private Double documentSize;
	private Integer documentId;
	private String createdDate;
	private String firstName;
	private String lastName;
	private Integer docCategoryId;
	private String docPath;
	private String formatedCreatedDate;
	private static SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");	
	private static SimpleDateFormat popupDate = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");	

/*	public String getFormatedCreatedDate() {
		return formatedCreatedDate;
	}

	public void setFormatedCreatedDate() {
		this.formatedCreatedDate = getFormattedDate(this.createdDate);
	}*/

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public Double getDocumentSize() {
		return documentSize;
	}

	public void setDocumentSize(Double documentSize) {
		this.documentSize = documentSize;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	public void setCreatedDate() {
		this.createdDate = getFormattedDate(this.createdDate);
	}
	

	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFormattedDate(String inDate){
		Date date = formatStringToDate(inDate);
		String outDate = getPopUpDate(date);
		return outDate;

	}
	public String getFormattedTime(String inDate){
		Date date = formatStringToDate(inDate);
		String outDate = getPopUpTime(date);
		return outDate;

	}
	public static Date formatStringToDate(String source)
	{
			try {
				return defaultFormat.parse(source);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}	
	public static String getPopUpDate(Date pDate) {
		return popupDate.format(pDate);
	}
	public static String getPopUpTime(Date pDate) {
		return timeFormat.format(pDate);
	}

	public Integer getDocCategoryId() {
		return docCategoryId;
	}

	public void setDocCategoryId(Integer docCategoryId) {
		this.docCategoryId = docCategoryId;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	


}
