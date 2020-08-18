package com.servicelive.esb.dto;

import java.io.Serializable;

public class Message implements Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = -644658770711186898L;
	private String Date;
	private String Text;
	private String Time;
	
	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}
	
}
