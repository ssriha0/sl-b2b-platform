package com.newco.marketplace.gwt.providersearch.client;

import java.io.Serializable;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;



public class SimpleProviderSearchCriteraVO implements IsSerializable, Serializable {

	SimpleProviderSearchSkillNodeVO theSkillNode;
	int distance;
	float starRating;
	int languageId;
	List SkillTypes;
	String zip;
	String percentMatch;
	boolean isPercentMatchSort;
	boolean isDistanceSort;
	boolean isProviderLocked;
	boolean isViewAll;
	int lockedResourceId;

	public boolean isProviderLocked() {
		return isProviderLocked;
	}
	public void setProviderLocked(boolean isProviderLocked) {
		this.isProviderLocked = isProviderLocked;
	}
	public int getLockedResourceId() {
		return lockedResourceId;
	}
	public void setLockedResourceId(int lockedResourceId) {
		this.lockedResourceId = lockedResourceId;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public SimpleProviderSearchSkillNodeVO getTheSkillNode() {
		return theSkillNode;
	}
	public void setTheSkillNode(SimpleProviderSearchSkillNodeVO theSkillNode) {
		this.theSkillNode = theSkillNode;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public float getStarRating() {
		return starRating;
	}
	public void setStarRating(float starRating) {
		this.starRating = starRating;
	}
	public List getSkillTypes() {
		return SkillTypes;
	}
	public void setSkillTypes(List skillTypes) {
		SkillTypes = skillTypes;
	}
	public String getPercentMatch() {
		return percentMatch;
	}
	public void setPercentMatch(String percentMatch) {
		this.percentMatch = percentMatch;
	}
	public boolean isPercentMatchSort() {
		return isPercentMatchSort;
	}
	public void setPercentMatchSort(boolean isPercentMatchSort) {
		this.isPercentMatchSort = isPercentMatchSort;
	}
	public boolean isDistanceSort() {
		return isDistanceSort;
	}
	public void setDistanceSort(boolean isDistanceSort) {
		this.isDistanceSort = isDistanceSort;
	}
	public int getLanguageId() {
		return languageId;
	}
	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}
	public boolean isViewAll() {
		return isViewAll;
	}
	public void setViewAll(boolean isViewAll) {
		this.isViewAll = isViewAll;
	}

}
