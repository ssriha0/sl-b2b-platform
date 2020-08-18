package com.newco.marketplace.search.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.newco.marketplace.search.solr.dto.ProviderDto;
import com.newco.marketplace.search.types.Category;
import com.newco.marketplace.search.types.CustomerFeedback;
import com.newco.marketplace.search.types.ProviderCredential;

/**
 * @author ndixit
 *
 */
public class ProviderSearchResponseVO extends BaseResponseVO{
	
	private String name;
	private Date memberSince;
	private float distance;
	private String jobTitle;
	
	private int resourceId;
	private String imageUrl; 	
	
	private String primaryIndustry;
	
	// Company Start
	private String overview;
	private int companyId;
	private String businessStructure;
	private int yearsInBusiness;
	private String companySize;
	private int numberOfSLProjectsCompleted;
	// policy start
	private int laborWarrantyDays;
	private int laborPartsDays;
	private boolean offersFreeEstimatesInd;
	// policy end
	// insurance start
	private int workersCompInsurance;
	private int vehicalInsurance;
	private int generalInsurance;
	private boolean insVerifiedWorker;
	private String insVerificationDateWorker;
	private boolean insVerifiedAuto;
	private String insVerificationDateAuto;
	private boolean insVerifiedGen;
	private String insVerificationDateGen;
	// insurance end
	// Company end	
	
	
	// matrix		
	private int closedOrderTotal;		
	private int reviewCount;
	
	private int ratingTotal;
	private float avgRating;
	private float avgReviewerRating;
	private int credentialTotal;	// number of Licenses and Certifications
	private int credentialTotalCompany;
	//KPI
	private float timelinessRatings;
	private float communicationRatings;
	private float professionalismRatings;
	private float qualityRatings;
	private float valueRatings;
	private float cleanlinessRatings;
	private float overallRating;
	//KPI END
	
	private List<Category> categories;
	private List<ProviderCredential> licenses;
	private List<CustomerFeedback> reviews;
	
	//Provider's availability
	private Availability availability;
	private List<String> languages;
	private String zip;
	
	//R 16_2_0_1: SL-21376: added skills and business name
	private List<String> skills;
	private String businessName;
	
	public ProviderSearchResponseVO(){
		// intentionally blank
	}
	
	public ProviderSearchResponseVO (ProviderDto dto) {
		this.name = dto.getName();	
		this.jobTitle = dto.getTitle();
		this.resourceId = dto.getResourceId();
		this.imageUrl = dto.getImage();
		this.primaryIndustry = dto.getPrimaryIndustry();
		this.overview = dto.getOverview();
		this.companyId = dto.getCompanyId();
		this.businessStructure = dto.getBusinessStructure();
		this.yearsInBusiness = dto.getYrsOfExperience();
		this.companySize = dto.getCompanySize();
		this.numberOfSLProjectsCompleted = dto.getTotalSoCompleted();
		this.distance = dto.getGeoDistance();
		this.zip = dto.getZip();
		// policy 
		this.laborWarrantyDays = dto.getWarrPeriodLabor();
		this.laborPartsDays = dto.getWarrPeriodParts();
		
		if (dto.getFreeEstimate() > 0)
		  this.offersFreeEstimatesInd = true;
		
		// insurance
		this.workersCompInsurance = (int)dto.getInsWorkman();
		this.vehicalInsurance = (int) dto.getInsVehicle();
		this.generalInsurance = (int)dto.getInsGeneral();
		this.insVerifiedAuto = dto.isInsVerifiedAuto();
		this.insVerifiedGen = dto.isInsVerifiedGen();
		this.insVerifiedWorker = dto.isInsVerifiedWorker();
		
		this.insVerificationDateGen = dto.getInsVerificationDateGen();
		this.insVerificationDateAuto = dto.getInsVerificationDateAuto();
		this.insVerificationDateWorker = dto.getInsVerificationDateWorker();
		
		// matrix	
		this.closedOrderTotal = dto.getTotalSoCompleted();
		this.credentialTotal = dto.getLicenseCount();	
		this.reviewCount = dto.getReviewCount();
		this.ratingTotal = dto.getRatingCount();
		this.avgRating = dto.getProviderRating();		
		this.avgReviewerRating = dto.getReviewRating();		
		//KPI
		this.timelinessRatings = dto.getTimeliness();
		this.communicationRatings = dto.getCommunication();
		this.professionalismRatings = dto.getProfessionalism();
		this.qualityRatings = dto.getQuality();
		this.valueRatings = dto.getValueRating();
		this.cleanlinessRatings = dto.getCleanliness();
		this.overallRating = dto.getOverall();
		this.credentialTotalCompany = dto.getLicenseCountCompany();
		
		this.availability = new Availability(dto);
		this.languages = dto.getLangList();
			
		final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
		SimpleDateFormat ff = new SimpleDateFormat(DATE_FORMAT);
		try {
			if (dto.getMemberSince() != null)
			  this.memberSince = ff.parse(dto.getMemberSince());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//R 16_2_0_1: SL-21376: added skills and business name
		this.skills = dto.getSkillList();
		this.businessName = dto.getBusinessName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getMemberSince() {
		return memberSince;
	}

	public void setMemberSince(Date memberSince) {
		this.memberSince = memberSince;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPrimaryIndustry() {
		return primaryIndustry;
	}

	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getBusinessStructure() {
		return businessStructure;
	}

	public void setBusinessStructure(String businessStructure) {
		this.businessStructure = businessStructure;
	}

	public int getYearsInBusiness() {
		return yearsInBusiness;
	}

	public void setYearsInBusiness(int yearsInBusiness) {
		this.yearsInBusiness = yearsInBusiness;
	}

	public String getCompanySize() {
		return companySize;
	}

	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}

	public int getNumberOfSLProjectsCompleted() {
		return numberOfSLProjectsCompleted;
	}

	public void setNumberOfSLProjectsCompleted(int numberOfSLProjectsCompleted) {
		this.numberOfSLProjectsCompleted = numberOfSLProjectsCompleted;
	}

	public int getLaborWarrantyDays() {
		return laborWarrantyDays;
	}

	public void setLaborWarrantyDays(int laborWarrantyDays) {
		this.laborWarrantyDays = laborWarrantyDays;
	}

	public int getLaborPartsDays() {
		return laborPartsDays;
	}

	public void setLaborPartsDays(int laborPartsDays) {
		this.laborPartsDays = laborPartsDays;
	}

	public boolean isOffersFreeEstimatesInd() {
		return offersFreeEstimatesInd;
	}

	public void setOffersFreeEstimatesInd(boolean offersFreeEstimatesInd) {
		this.offersFreeEstimatesInd = offersFreeEstimatesInd;
	}

	public int getWorkersCompInsurance() {
		return workersCompInsurance;
	}

	public void setWorkersCompInsurance(int workersCompInsurance) {
		this.workersCompInsurance = workersCompInsurance;
	}

	public int getVehicalInsurance() {
		return vehicalInsurance;
	}

	public void setVehicalInsurance(int vehicalInsurance) {
		this.vehicalInsurance = vehicalInsurance;
	}

	public int getGeneralInsurance() {
		return generalInsurance;
	}

	public void setGeneralInsurance(int generalInsurance) {
		this.generalInsurance = generalInsurance;
	}

	public int getClosedOrderTotal() {
		return closedOrderTotal;
	}

	public void setClosedOrderTotal(int closedOrderTotal) {
		this.closedOrderTotal = closedOrderTotal;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public int getRatingTotal() {
		return ratingTotal;
	}

	public void setRatingTotal(int ratingTotal) {
		this.ratingTotal = ratingTotal;
	}

	public float getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(float avgRating) {
		this.avgRating = avgRating;
	}

	public float getAvgReviewerRating() {
		return avgReviewerRating;
	}

	public void setAvgReviewerRating(float avgReviewerRating) {
		this.avgReviewerRating = avgReviewerRating;
	}

	public float getTimelinessRatings() {
		return timelinessRatings;
	}

	public void setTimelinessRatings(float timelinessRatings) {
		this.timelinessRatings = timelinessRatings;
	}

	public float getCommunicationRatings() {
		return communicationRatings;
	}

	public void setCommunicationRatings(float communicationRatings) {
		this.communicationRatings = communicationRatings;
	}

	public float getProfessionalismRatings() {
		return professionalismRatings;
	}

	public void setProfessionalismRatings(float professionalismRatings) {
		this.professionalismRatings = professionalismRatings;
	}

	public float getQualityRatings() {
		return qualityRatings;
	}

	public void setQualityRatings(float qualityRatings) {
		this.qualityRatings = qualityRatings;
	}

	public float getValueRatings() {
		return valueRatings;
	}

	public void setValueRatings(float valueRatings) {
		this.valueRatings = valueRatings;
	}

	public float getCleanlinessRatings() {
		return cleanlinessRatings;
	}

	public void setCleanlinessRatings(float cleanlinessRatings) {
		this.cleanlinessRatings = cleanlinessRatings;
	}

	public float getOverallRating() {
		return overallRating;
	}

	public void setOverallRating(float overallRating) {
		this.overallRating = overallRating;
	}

	public List<ProviderCredential> getLicenses() {
		return licenses;
	}

	public void setLicenses(List<ProviderCredential> licenses) {
		this.licenses = licenses;
	}

	public List<CustomerFeedback> getReviews() {
		return reviews;
	}

	public void setReviews(List<CustomerFeedback> reviews) {
		this.reviews = reviews;
	}

	
	public int getCredentialTotal() {
		return credentialTotal;
	}

	public void setCredentialTotal(int credentialTotal) {
		this.credentialTotal = credentialTotal;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}	
	
	public void addLicense(ProviderCredential license) {
		if (licenses == null)
			licenses = new ArrayList<ProviderCredential>();
		licenses.add(license);
	}
	
	public void addReview(CustomerFeedback feedback) {
		if (reviews == null)
			reviews = new ArrayList<CustomerFeedback>();
		reviews.add(feedback);
	}
	
	public void addCategorie(Category category) {
		if (categories == null)
			categories = new ArrayList<Category>();
		categories.add(category);
	}
	
	

	public Availability getAvailability() {
		return availability;
	}

	public void setAvailability(Availability availability) {
		this.availability = availability;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public int getCredentialTotalCompany() {
		return credentialTotalCompany;
	}

	public void setCredentialTotalCompany(int credentialTotalCompany) {
		this.credentialTotalCompany = credentialTotalCompany;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	public boolean isInsVerifiedWorker() {
		return insVerifiedWorker;
	}

	public void setInsVerifiedWorker(boolean insVerifiedWorker) {
		this.insVerifiedWorker = insVerifiedWorker;
	}

	public String getInsVerificationDateWorker() {
		return insVerificationDateWorker;
	}

	public void setInsVerificationDateWorker(String insVerificationDateWorker) {
		this.insVerificationDateWorker = insVerificationDateWorker;
	}

	public boolean isInsVerifiedAuto() {
		return insVerifiedAuto;
	}

	public void setInsVerifiedAuto(boolean insVerifiedAuto) {
		this.insVerifiedAuto = insVerifiedAuto;
	}

	public String getInsVerificationDateAuto() {
		return insVerificationDateAuto;
	}

	public void setInsVerificationDateAuto(String insVerificationDateAuto) {
		this.insVerificationDateAuto = insVerificationDateAuto;
	}

	public boolean isInsVerifiedGen() {
		return insVerifiedGen;
	}

	public void setInsVerifiedGen(boolean insVerifiedGen) {
		this.insVerifiedGen = insVerifiedGen;
	}

	public String getInsVerificationDateGen() {
		return insVerificationDateGen;
	}

	public void setInsVerificationDateGen(String insVerificationDateGen) {
		this.insVerificationDateGen = insVerificationDateGen;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

}
