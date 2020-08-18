package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

public class SPNCriteriaDTO extends SOWBaseTabDTO{
/**
 * $Revision: 1.2 $ $Author: glacy $ $Date: 2008/05/02 21:23:19 $
 */


	

	private static final long serialVersionUID = -1973103081240354144L;

	private Integer credentials = new Integer(-1);
	private Integer credentialCategory = new Integer(-1);
	
	private Integer resourceCredential = new Integer(-1);
	private Integer resourceCategory = new Integer(-1);
	private Integer selectedCategoryId = new Integer(-1);
	
	private Integer languages = new Integer(-1);
	private Boolean includeNonRated = false;
	private Double ratings= new Double(-1.0);
	private Integer ratingsNumber = new Integer(0); // Use this value 0-20 to determine which stars artwork to show.
	
	private Boolean vliInsurance = false;
	private Double vliInsuranceAmount = new Double(0.00);
	private Boolean wciInsurance = false;
	private Double wciInsuranceAmount = new Double(0.00);
	private Boolean cgliInsurance = false;
	private Double cgliInsuranceAmount = new Double(0.00);
	private Integer totalNumOfServiceOrdersClosed = new Integer(0);
	private Integer mainServiceCategoryId = new Integer(-1);
	private String mainServiceCategoryName;
	private List<SOTaskDTO> tasks = new ArrayList<SOTaskDTO>();
	private boolean containsTasks = false;
	private String languageDescr = null;

	
	
	public Boolean getCgliInsurance() {
		return cgliInsurance;
	}
	public void setCgliInsurance(Boolean cgliInsurance) {
		this.cgliInsurance = cgliInsurance;
	}
	public Boolean getVliInsurance() {
		return vliInsurance;
	}
	public void setVliInsurance(Boolean vliInsurance) {
		this.vliInsurance = vliInsurance;
	}
	public Boolean getWciInsurance() {
		return wciInsurance;
	}
	public void setWciInsurance(Boolean wciInsurance) {
		this.wciInsurance = wciInsurance;
	}
	public Boolean getIncludeNonRated() {
		return includeNonRated;
	}
	public void setIncludeNonRated(Boolean includeNonRated) {
		this.includeNonRated = includeNonRated;
	}
	public Double getCgliInsuranceAmount() {
		return cgliInsuranceAmount;
	}
	public void setCgliInsuranceAmount(Double cgliInsuranceAmount) {
		this.cgliInsuranceAmount = cgliInsuranceAmount;
	}
	public Integer getCredentialCategory() {
		return credentialCategory;
	}
	public void setCredentialCategory(Integer credentialCategory) {
		this.credentialCategory = credentialCategory;
	}
	public Integer getCredentials() {
		return credentials;
	}
	public void setCredentials(Integer credentials) {
		this.credentials = credentials;
	}
	public Integer getLanguages() {
		return languages;
	}
	public void setLanguages(Integer languages) {
		this.languages = languages;
	}
	public Double getRatings() {
		return ratings;
	}
	public void setRatings(Double ratings) {
		this.ratings = ratings;
	}
	public Integer getResourceCategory() {
		return resourceCategory;
	}
	public void setResourceCategory(Integer resourceCategory) {
		this.resourceCategory = resourceCategory;
	}
	public Integer getResourceCredential() {
		return resourceCredential;
	}
	public void setResourceCredential(Integer resourceCredential) {
		this.resourceCredential = resourceCredential;
	}
	public Integer getTotalNumOfServiceOrdersClosed() {
		return totalNumOfServiceOrdersClosed;
	}
	public void setTotalNumOfServiceOrdersClosed(
			Integer totalNumOfServiceOrdersClosed) {
		this.totalNumOfServiceOrdersClosed = totalNumOfServiceOrdersClosed;
	}
	public Double getVliInsuranceAmount() {
		return vliInsuranceAmount;
	}
	public void setVliInsuranceAmount(Double vliInsuranceAmount) {
		this.vliInsuranceAmount = vliInsuranceAmount;
	}
	public Double getWciInsuranceAmount() {
		return wciInsuranceAmount;
	}
	public void setWciInsuranceAmount(Double wciInsuranceAmount) {
		this.wciInsuranceAmount = wciInsuranceAmount;
	}
	public Integer getMainServiceCategoryId() {
		return mainServiceCategoryId;
	}
	public void setMainServiceCategoryId(Integer mainServiceCategoryId) {
		this.mainServiceCategoryId = mainServiceCategoryId;
	}
	public List<SOTaskDTO> getTasks() {
		return tasks;
	}
	
	public void addTask(SOTaskDTO task) {
		if (tasks == null) {
			tasks = new ArrayList<SOTaskDTO>();
		}
		tasks.add(task);
		containsTasks = true;
	}
	
	public void addTaskList(List<SOTaskDTO> tasksDTOList) {
		this.tasks.clear();
		this.tasks.addAll(tasksDTOList);
		containsTasks = true;
	}
	public String getMainServiceCategoryName() {
		return mainServiceCategoryName;
	}
	public void setMainServiceCategoryName(String mainServiceCategoryName) {
		this.mainServiceCategoryName = mainServiceCategoryName;
	}
	
	@Override
	public String getTabIdentifier() {
		return null;
	}
	@Override
	public void validate() {
	
	}
	public Integer getSelectedCategoryId() {
		return selectedCategoryId;
	}
	public void setSelectedCategoryId(Integer selectedCategoryId) {
		this.selectedCategoryId = selectedCategoryId;
	}
	public boolean getContainsTasks() {
		return containsTasks;
	}
	public void setContainsTasks(boolean containsTasks) {
		this.containsTasks = containsTasks;
	}
	public Integer getRatingsNumber() {
		return ratingsNumber;
	}
	public void setRatingsNumber(Integer ratingsNumber) {
		this.ratingsNumber = ratingsNumber;
	}
	public String getLanguageDescr() {
		return languageDescr;
	}
	public void setLanguageDescr(String languageDescr) {
		this.languageDescr = languageDescr;
	}
	
}
