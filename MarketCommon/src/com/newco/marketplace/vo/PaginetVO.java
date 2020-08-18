package com.newco.marketplace.vo;

import com.sears.os.vo.SerializableBaseVO;


public class PaginetVO extends SerializableBaseVO{
/**
	 * 
	 */
	private static final long serialVersionUID = -5248089054194403964L;
/* This holds the start index of the current paginet. It is the position from where the records to be displayed starts**/	
private int startIndex;
/* This holds the end index of the current paginet. It is the position at which the records to be displayed ends**/
private int endIndex;
/* This holds the label of the hyperlink. Examples include 1, 2, 3, Next, Previous, 25, 50, 100**/
private String displayName;

/* Not used by the PaginationFacility object**/
private String url; 

/* This holds an indicator to let the user know if this is the current paginet that the user has requested for**/
private boolean isCurrentPaginet;

public boolean isCurrentPaginet() {
	return isCurrentPaginet;
}
public void setCurrentPaginet(boolean isCurrentPaginet) {
	this.isCurrentPaginet = isCurrentPaginet;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public String getDisplayName() {
	return displayName;
}
public void setDisplayName(String displayName) {
	this.displayName = displayName;
}
public int getStartIndex() {
	return startIndex;
}
public void setStartIndex(int startIndex) {
	this.startIndex = startIndex;
}
public int getEndIndex() {
	return endIndex;
}
public void setEndIndex(int endIndex) {
	this.endIndex = endIndex;
}


/*
 * This displays the object in the following format: "3": [76, 100]
 * which means that the paginet refers page 3, the start index is 76 and end index is 100
 * 
 */
public String toString(){
	StringBuffer sb = new StringBuffer();
	sb.append("\""+displayName+"\": ");
	sb.append("["+startIndex);
	sb.append(", "+endIndex);
	sb.append(", "+isCurrentPaginet+"]");

	return sb.toString();
	
}
}
