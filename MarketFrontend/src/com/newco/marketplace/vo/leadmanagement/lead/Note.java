package com.newco.marketplace.vo.leadmanagement.lead;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Note {

	private String subject;

	private String message;
	
	private String noteBy;

	private  String noteDate;
	private  String formattedNoteDate;
	private Integer noteId;
	private Integer roleId;
	
	public Integer getNoteId() {
		return noteId;
	}

	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}

	public String getFormattedNoteDate() {
		return formattedNoteDate;
	}

	public void setFormattedNoteDate() {
		this.formattedNoteDate = getFormattedDate(this.noteDate);
	}
	//private static SimpleDateFormat defaultFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");	
	//private static SimpleDateFormat popupDate = new SimpleDateFormat("mm-dd-yy hh:mm a");
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");	
	private static SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");	
	private static SimpleDateFormat popupDate = new SimpleDateFormat("MMM d, yyyy hh:mm a");
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNoteBy() {
		return noteBy;
	}

	public void setNoteBy(String noteBy) {
		this.noteBy = noteBy;
	}

	public String getNoteDate() {
		return noteDate;
	}

	public void setNoteDate(String noteDate) {
		this.noteDate = noteDate;
	}
	
	public void setNoteDate() {
		this.noteDate = getFormattedTime(this.noteDate);
	}
	
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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
	
	
}
