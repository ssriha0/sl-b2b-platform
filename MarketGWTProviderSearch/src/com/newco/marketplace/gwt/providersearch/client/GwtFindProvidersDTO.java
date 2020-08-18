/**
 * 
 */
package com.newco.marketplace.gwt.providersearch.client;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author HOZA 
 *
 */
public class GwtFindProvidersDTO implements IsSerializable, Serializable {
	public final static String MAIN_VERTICLES = "PRIMARY";
	public final static String CATEGORY = "CATEGORY";
	public final static String SUB_SUB_CATEGORY = "SUB_CATEGORY";
	private List selectedProviders;
	private Map selectedCategorys; //Map for verticles  with  SimpleProviderSearchSkillNodeVO as values
	private String selectedFilterRating = "-1";
	private String selectedFilterDistance = "-1";
	private String selectedSortLable = "1"; //1 is for % match 2 is for Distance
	private List checkedJobs; //List of SimpleProviderSearchSkillTypeVO
	private String zip;
	private String redirectUrl = null;
	private String state;
	private String so_id;
	private boolean lockedProviderList = false;
	private int resourceID;
	
	public boolean isLockedProviderList() {
		return lockedProviderList;
	}
	public void setLockedProviderList(boolean lockedProviderList) {
		this.lockedProviderList = lockedProviderList;
	}
	public int getResourceID() {
		return resourceID;
	}
	public void setResourceID(int resourceID) {
		this.resourceID = resourceID;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List getSelectedProviders() {
		return selectedProviders;
	}
	public void setSelectedProviders(List selectedProviders) {
		this.selectedProviders = selectedProviders;
	}
	public Map getSelectedCategorys() {
		return selectedCategorys;
	}
	public void setSelectedCategorys(Map selectedCategorys) {
		this.selectedCategorys = selectedCategorys;
	}
	public String getSelectedFilterRating() {
		return selectedFilterRating;
	}
	public void setSelectedFilterRating(String selectedFilterRating) {
		this.selectedFilterRating = selectedFilterRating;
	}
	public String getSelectedFilterDistance() {
		return selectedFilterDistance;
	}
	public void setSelectedFilterDistance(String selectedFilterDistance) {
		this.selectedFilterDistance = selectedFilterDistance;
	}
	public String getSelectedSortLable() {
		return selectedSortLable;
	}
	public void setSelectedSortLable(String selectedSortLable) {
		this.selectedSortLable = selectedSortLable;
	}
	public List getCheckedJobs() {
		return checkedJobs;
	}
	public void setCheckedJobs(List checkedJobs) {
		this.checkedJobs = checkedJobs;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public String getSo_id() {
		return so_id;
	}
	public void setSo_id(String so_id) {
		this.so_id = so_id;
	}
	
}
