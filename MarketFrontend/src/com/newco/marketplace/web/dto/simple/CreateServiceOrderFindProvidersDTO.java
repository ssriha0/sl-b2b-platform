package com.newco.marketplace.web.dto.simple;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.sitestatistics.PopularServicesVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.dto.SOWBaseTabDTO;

/**
 * $Revision: 1.5 $ $Author: mhaye05 $ $Date: 2008/05/27 19:17:56 $
 */
public class CreateServiceOrderFindProvidersDTO extends SOWBaseTabDTO
{
	private static final long serialVersionUID = -8273151978179217174L;
	/**
	 *  this echos the GwtFindProvidersDTO
	 */
	private List selectedProviders = new ArrayList(); //This is List of SimpleProviderSearchProviderResultVO
	private PopularServicesVO selectedCategorys;
	private String selectedFilterRating = "-1";
	private String selectedFilterDistance = "-1";
	private String selectedSortLable = "1"; //1 is for % match 2 is for Distance
	private List checkedJobs; //List of SimpleProviderSearchSkillTypeVO
	private String zip;
	private String redirectUrl;
	private String state;
	private String so_id;
	private boolean simpleBuyer;
	private boolean lockedProviderList;
	private int resourceID;
	private List<SkillNodeVO> fetchedVerticles = new ArrayList<SkillNodeVO>();

	/**
	 * @return the so_id
	 */
	public String getSo_id() {
		return so_id;
	}
	/**
	 * @param so_id the so_id to set
	 */
	public void setSo_id(String so_id) {
		this.so_id = so_id;
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
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.dto.SOWBaseTabDTO#getTabIdentifier()
	 */
	@Override
	public String getTabIdentifier() {
		return OrderConstants.SSO_FIND_PROVIDERS_DTO;
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.dto.SOWBaseTabDTO#validate()
	 */
	@Override
	public void validate() {

		if (null == selectedCategorys.getMainCategoryId() || selectedCategorys.getMainCategoryId().intValue() <= 0) {
			addError("Main Category",
					"You need to select a Main Category to continue",
					OrderConstants.SOW_TAB_ERROR);
		}
		if (null == checkedJobs ||  checkedJobs.size() == 0) {
			addError("Select Type of Service",
					"You need to select a type of service to continue",
					OrderConstants.SOW_TAB_ERROR);
		}
		if(selectedProviders == null || selectedProviders.size() == 0)
		{
			addError("Selected Provider(s)",
						"You need to select at least one provider to continue",
						OrderConstants.SOW_TAB_ERROR);
		}

	}
	public PopularServicesVO getSelectedCategorysVO() {
		return selectedCategorys;
	}
	public void setSelectedCategorys(PopularServicesVO selectedCategorys) {
		this.selectedCategorys = selectedCategorys;
	}
	public boolean isSimpleBuyer() {
		return simpleBuyer;
	}
	public void setSimpleBuyer(boolean simpleBuyer) {
		this.simpleBuyer = simpleBuyer;
	}
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
	public List getFetchedVerticles() {
		return fetchedVerticles;
	}
	public void setFetchedVerticles(List fetchedVerticles) {
		this.fetchedVerticles = fetchedVerticles;
	}



}
