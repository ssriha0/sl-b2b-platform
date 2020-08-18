package com.newco.marketplace.search.solr.dto;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class ProviderDto {	
	
	@Field
	String id;
	
	@Field("resource_id")
	int resourceId;
	
	@Field
	float score;

    @Field
    String name;
    
    @Field
    String city;
		
    @Field
    String state;
    
    @Field
    double lat;
    
    @Field
    double lng;
    
    @Field
    String title;
    
    @Field
    String image;
    
    @Field
    String langs;
    
    @Field("business_name")
    String businessName;
    
	@Field("member_since")	
	String memberSince;
	
	@Field
	String overview;
	
	@Field("zip")
	private String zip;
	
	@Field("yrs_of_experience")
	int yrsOfExperience;
	
	@Field("company_size")
	String companySize;
	
	@Field("business_structure")
	String businessStructure;
	
	@Field("warr_period_labor")
	int warrPeriodLabor;
	
	@Field("warr_period_parts")
	int warrPeriodParts;
	
	@Field("free_estimate")
	int freeEstimate;
	
	@Field("ins_vehicle")
	double insVehicle;
	
	@Field("ins_workman")
	double insWorkman;
	
	@Field("ins_general")
	double insGeneral;
	
	@Field("total_so_completed")
	int totalSoCompleted;
	
	@Field("provider_rating")
	float providerRating;
	
	@Field("survey_count")
	int ratingCount;
	
	@Field("review_count")
	int reviewCount;
	
	@Field("review_rating")
	float reviewRating;
	
	@Field("timeliness")
	float timeliness;
	
	@Field("communication")
	float communication;
	
	@Field("professionalism")
	float professionalism;
	
	@Field("quality")
	float quality;
	
	@Field("mvalue")
	float valueRating;
	
	@Field("cleanliness")
	float cleanliness;
	
	@Field("overall")
	float overall;
	
	@Field("license_count")
	int licenseCount; 
	
	@Field("license_count_company")
	int licenseCountCompany; 
	
	@Field("geo_distance")
	String geo_distance;
	
	@Field("skills")
	String skills;
	
	@Field("vendor_id")
	int companyId;
	
	@Field("primary_industry")
	String primaryIndustry;
	
	@Field("avail_mon")
	String availMonday;
	
	@Field("avail_tue")
	String availTuesday;
	
	@Field("avail_wed")
	String availWednesday;
	
	@Field("avail_thu")
	String availThursday;
	
	@Field("avail_fri")
	String availFriday;
	
	@Field("avail_sat")
	String availSaturday;
	
	@Field("avail_sun")
	String availSunday;
	
	@Field("time_zone")
	String timeZone;
	
	
	// insurance start
	@Field("ins_verified_workman")
	private boolean insVerifiedWorker;
	
	@Field("ins_verified_date_workman")
	private String insVerificationDateWorker;
	
	@Field("ins_verified_auto")
	private boolean insVerifiedAuto;
	
	@Field("ins_verified_date_auto")
	private String insVerificationDateAuto;
	
	@Field("ins_verified_general")
	private boolean insVerifiedGen;
	
	@Field("ins_verified_date_general")
	private String insVerificationDateGen;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	
	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLangs() {
		return langs;
	}

	public void setLangs(String langs) {
		this.langs = langs;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getMemberSince() {
		return memberSince;
	}

	public void setMemberSince(String memberSince) {
		this.memberSince = memberSince;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public int getYrsOfExperience() {
		return yrsOfExperience;
	}

	public void setYrsOfExperience(int yrsOfExperience) {
		this.yrsOfExperience = yrsOfExperience;
	}

	public String getCompanySize() {
		return companySize;
	}

	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}

	public String getBusinessStructure() {
		return businessStructure;
	}

	public void setBusinessStructure(String businessStructure) {
		this.businessStructure = businessStructure;
	}

	public int getWarrPeriodLabor() {
		return warrPeriodLabor;
	}

	public void setWarrPeriodLabor(int warrPeriodLabor) {
		this.warrPeriodLabor = warrPeriodLabor;
	}

	public int getWarrPeriodParts() {
		return warrPeriodParts;
	}

	public void setWarrPeriodParts(int warrPeriodParts) {
		this.warrPeriodParts = warrPeriodParts;
	}

	public int getFreeEstimate() {
		return freeEstimate;
	}

	public void setFreeEstimate(int freeEstimate) {
		this.freeEstimate = freeEstimate;
	}
	

	public double getInsVehicle() {
		return insVehicle;
	}

	public void setInsVehicle(double insVehicle) {
		this.insVehicle = insVehicle;
	}

	public double getInsWorkman() {
		return insWorkman;
	}

	public void setInsWorkman(double insWorkman) {
		this.insWorkman = insWorkman;
	}

	public double getInsGeneral() {
		return insGeneral;
	}

	public void setInsGeneral(double insGeneral) {
		this.insGeneral = insGeneral;
	}

	public int getTotalSoCompleted() {
		return totalSoCompleted;
	}

	public void setTotalSoCompleted(int totalSoCompleted) {
		this.totalSoCompleted = totalSoCompleted;
	}

	public float getProviderRating() {
		return providerRating;
	}

	public void setProviderRating(float providerRating) {
		this.providerRating = providerRating;
	}


	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public float getReviewRating() {
		return reviewRating;
	}

	public void setReviewRating(float reviewRating) {
		this.reviewRating = reviewRating;
	}

	public float getTimeliness() {
		return timeliness;
	}

	public void setTimeliness(float timeliness) {
		this.timeliness = timeliness;
	}

	public float getCommunication() {
		return communication;
	}

	public void setCommunication(float communication) {
		this.communication = communication;
	}

	public float getProfessionalism() {
		return professionalism;
	}

	public void setProfessionalism(float professionalism) {
		this.professionalism = professionalism;
	}

	public float getQuality() {
		return quality;
	}

	public void setQuality(float quality) {
		this.quality = quality;
	}

	public float getValueRating() {
		return valueRating;
	}

	public void setValue(float value) {
		this.valueRating = value;
	}

	public float getCleanliness() {
		return cleanliness;
	}

	public void setCleanliness(float cleanliness) {
		this.cleanliness = cleanliness;
	}

	public float getOverall() {
		return overall;
	}

	public void setOverall(float overall) {
		this.overall = overall;
	}

	public int getLicenseCount() {
		return licenseCount;
	}

	public void setLicenseCount(int licenseCount) {
		this.licenseCount = licenseCount;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	@Override
	public String toString() {		
		return ReflectionToStringBuilder.toString(this);
	}
	
	public float getGeoDistance() {		
		return formatFloat(geo_distance).floatValue();
	}
	
	
	private Float formatFloat(String value) {
		if (value != null) {
			DecimalFormat format = new DecimalFormat("0.##");
			String temp = format.format(Float.valueOf(value));		
			return Float.valueOf(temp);
		}
		return Float.valueOf(0.0f);
	}
	
	//public float setGeoDistance(float distance) {
	//	geo_distance = distance;		
	//}
	
	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}

	public String getPrimaryIndustry() {
		return primaryIndustry;
	}

	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}
	
	public int getLicenseCountCompany() {
		return licenseCountCompany;
	}

	public void setLicenseCountCompany(int licenseCountCompany) {
		this.licenseCountCompany = licenseCountCompany;
	}

	public String getAvailMonday() {
		return availMonday;
	}

	public void setAvailMonday(String availMonday) {
		this.availMonday = availMonday;
	}

	public String getAvailTuesday() {
		return availTuesday;
	}

	public void setAvailTuesday(String availTuesday) {
		this.availTuesday = availTuesday;
	}

	public String getAvailWednesday() {
		return availWednesday;
	}

	public void setAvailWednesday(String availWednesday) {
		this.availWednesday = availWednesday;
	}

	public String getAvailThursday() {
		return availThursday;
	}

	public void setAvailThursday(String availThursday) {
		this.availThursday = availThursday;
	}

	public String getAvailFriday() {
		return availFriday;
	}

	public void setAvailFriday(String availFriday) {
		this.availFriday = availFriday;
	}

	public String getAvailSaturday() {
		return availSaturday;
	}

	public void setAvailSaturday(String availSaturday) {
		this.availSaturday = availSaturday;
	}

	public String getAvailSunday() {
		return availSunday;
	}

	public void setAvailSunday(String availSunday) {
		this.availSunday = availSunday;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	
	public List<String> getLangList() {
		List<String> list = new ArrayList<String>();
		if (langs == null)
			return list;
		
		String arr[] = langs.split(",");
		for (String aa:arr) {
			if (!StringUtils.isEmpty(aa))
			  list.add(aa);
		}
		return list;
	}

	public boolean isInsVerifiedWorker() {
		return insVerifiedWorker;
	}

	public void setInsVerifiedWorker(boolean insVerifiedWorker) {
		this.insVerifiedWorker = insVerifiedWorker;
	}

	public String getInsVerificationDateWorker() {
		if (insVerificationDateWorker != null && insVerificationDateWorker.equalsIgnoreCase("null")) {
			return null;
		}
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
		if (insVerificationDateAuto != null && 
				insVerificationDateAuto.equalsIgnoreCase("null")) {
			return null;
		}
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
		if (insVerificationDateGen!= null 
				&& insVerificationDateGen.equalsIgnoreCase("null")) {
			return null;		
		}
		return insVerificationDateGen;
	}

	public void setInsVerificationDateGen(String insVerificationDateGen) {
		this.insVerificationDateGen = insVerificationDateGen;
	}
	
	/**R 16_2_0_1: SL-21376:added to get skill list
	 * @return
	 */
	public List<String> getSkillList() {
		List<String> list = new ArrayList<String>();
		if (skills == null)
			return list;
		
		String arr[] = skills.split(",");
		for (String aa:arr) {
			if (!StringUtils.isEmpty(aa))
			  list.add(aa);
		}
		return list;
	}
}
