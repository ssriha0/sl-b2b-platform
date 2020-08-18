package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceOrderWizardBean extends SerializedBaseDTO {

	private static final long serialVersionUID = -1369160255594009970L;
	private HashMap<String, Object> tabDTOs;
	private HashMap<String, Object> tabStateDTOs;
	
	//TODO - Carlos CG - Eventually phase out soId in favor of the new soIdList
	private String soId;
	private String groupId;
	private List<String> soIdList = new ArrayList<String>();
	
	
	private String comingFromTab;
	private String goingToTab;
	private String previousURL;
	private String nextURL;
	private String startPoint;
	private boolean post;
	private boolean saveAsDraft;
	private boolean isAutoPost;
	private boolean isRouteFromFE; 
	
	public boolean isRouteFromFE() {
		return isRouteFromFE;
	}
	public void setRouteFromFE(boolean isRouteFromFE) {
		this.isRouteFromFE = isRouteFromFE;
	}
	public boolean isAutoPost() {
		return isAutoPost;
	}
	public void setAutoPost(boolean isAutoPost) {
		this.isAutoPost = isAutoPost;
	}
	public HashMap<String, Object> getTabDTOs() {
		return tabDTOs;
	}
	public void setTabDTOs(HashMap<String, Object> tabDTOs) {
		this.tabDTOs = tabDTOs;
	}

	public String getPreviousURL() {
		return previousURL;
	}
	public void setPreviousURL(String previousURL) {
		this.previousURL = previousURL;
	}
	public String getNextURL() {
		return nextURL;
	}
	public void setNextURL(String nextURL) {
		this.nextURL = nextURL;
	}
	public String getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}
	public HashMap<String, Object> getTabStateDTOs() {
		return tabStateDTOs;
	}
	public void setTabStateDTOs(HashMap<String, Object> tabStateDTOs) {
		this.tabStateDTOs = tabStateDTOs;
	}
	public String getComingFromTab() {
		return comingFromTab;
	}
	public void setComingFromTab(String comingFromTab) {
		this.comingFromTab = comingFromTab;
	}
	public String getGoingToTab() {
		return goingToTab;
	}
	public void setGoingToTab(String goingToTab) {
		this.goingToTab = goingToTab;
	}
	public List<String> getSoIdList()
	{
		return soIdList;
	}
	public void setSoIdList(List<String> soIdList)
	{
		this.soIdList = soIdList;
	}
	public String getSoId()
	{
		return soId;
	}
	public void setSoId(String soId)
	{
		this.soId = soId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public boolean isPost() {
		return post;
	}
	public void setPost(boolean post) {
		this.post = post;
	}
	public boolean isSaveAsDraft() {
		return saveAsDraft;
	}
	public void setSaveAsDraft(boolean saveAsDraft) {
		this.saveAsDraft = saveAsDraft;
	}
}
