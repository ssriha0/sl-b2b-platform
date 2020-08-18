package com.newco.marketplace.web.action.details.zerobid.providerutils.viewobjects;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.servicelive.activitylog.domain.ActivityViewStatusName;

public class BidNoteViewObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3885728883989946934L;
	
	Long activityId;
	String createdByName;
	String icon;
	Date createdOn;
	Date lastActivity;
	String post;
	String status;
	private String viewStatusName;
	private Boolean showFollowupFlag;
	private String associationType;
	
	// Only show these 2 for Admin adopted users.
	String posterName;
	Long posterId;
	
	
	List<BidNoteViewObject> replies = new ArrayList<BidNoteViewObject>();
	
	SimpleDateFormat dateOutput = new SimpleDateFormat("MMMM d, h:mm a");
	
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getCreatedOn() {
		return dateOutput.format(createdOn);
	}
	public Date getCreatedOnDate() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public List<BidNoteViewObject> getReplies() {
		return replies;
	}
	public void setReplies(List<BidNoteViewObject> replies) {
		this.replies = replies;
	}
	public void addReply(BidNoteViewObject reply) {
		replies.add(reply);
	}
	public Date getLastActivity() {
		return lastActivity;
	}
	public void setLastActivity(Date lastActivity) {
		this.lastActivity = lastActivity;
	}
	public String getPosterName()
	{
		return posterName;
	}
	public void setPosterName(String posterName)
	{
		this.posterName = posterName;
	}
	public Long getPosterId()
	{
		return posterId;
	}
	public void setPosterId(Long posterId)
	{
		this.posterId = posterId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setShowFollowupFlag(Boolean showFollowupFlag) {
		this.showFollowupFlag = showFollowupFlag;
	}
	public Boolean getShowFollowupFlag() {
		return showFollowupFlag;
	}
	public void setViewStatusName(String viewStatusName) {
		this.viewStatusName = viewStatusName;
	}
	public String getViewStatusName() {
		return viewStatusName;
	}
	public void setAssociationType(String associationType) {
		this.associationType = associationType;
	}
	public String getAssociationType() {
		return associationType;
	}
}
