package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class SOWProviderDTO extends SOWBaseTabDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3293042818330515826L;
	private Integer resourceId;
	private String firstName;
	private String lastName;
	private String middleInitial;
	private Integer vendorId;
	private double slRating = 0.0;
	private Integer slRatingsCount = 0;
	private double myRating = 0.0;
	private Integer myRatingsCount = 0;	
	private Integer match = 0;
	private Integer totalOrders = 0;
	private Integer myOrders = 0;
	private Double distance;
	private Integer location;
	private String locationString;
	private ArrayList<String> slStarsList = null;
	private ArrayList<String> myStarsList = null;
	private Boolean showFav = Boolean.FALSE;	// displays "FAV" icon
	private Boolean showCheck = Boolean.FALSE;	// displays checkmark icon
	
	private Boolean isChecked = Boolean.FALSE;
		 
	private Boolean vehLiabilityInsVerified = Boolean.FALSE;
	private Boolean genLiabilityInsVerified = Boolean.FALSE;
	private Boolean workCompInsVerified = Boolean.FALSE;
	private Date vehLiabilityInsVerifiedDate;
	private Date genLiabilityInsVerifiedDate;
	private Date workCompInsVerifiedDate;
	private Double vehLiabilityInsAmt;
	private Double genLiabilityInsAmt;
	
	private Double workCompInsAmt; 

	//Rating numbers calculated from slRating and myRating to map onto appropriate star image that is shown in jsp
	private int slRatingNumber;
	private int myRatingNumber;
	
	private Double performanceScore;
	private Double firmPerformanceScore;
	private Boolean routingPriorityApplied;
	private String firmName;
	
	// SPN List.
	// The network name is stored in the label.  The performance level is in the value
	private List<ProviderSPNDTO> networkAndPerformanceLevelList;
	
	private List additionalInsuranceList;
	
	
	public List<ProviderSPNDTO> getNetworkAndPerformanceLevelList()
	{
		return networkAndPerformanceLevelList;
	}
	public void setNetworkAndPerformanceLevelList(List<ProviderSPNDTO> networkAndPerformanceLevelList)
	{
		this.networkAndPerformanceLevelList = networkAndPerformanceLevelList;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleInitial() {
		return middleInitial;
	}
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public double getSlRating() {
		return slRating;
	}
	public void setSlRating(double slRating) {
		this.slRating = slRating;
	}
	public double getMyRating() {
		return myRating;
	}
	public void setMyRating(double myRating) {
		this.myRating = myRating;
	}
	public Integer getMatch() {
		return match;
	}
	public void setMatch(Integer match) {
		this.match = match;
	}
	public Integer getTotalOrders() {
		return totalOrders;
	}
	public void setTotalOrders(Integer totalOrders) {
		this.totalOrders = totalOrders;
	}
	public Integer getMyOrders() {
		return myOrders;
	}
	public void setMyOrders(Integer myOrders) {
		this.myOrders = myOrders;
	}
	
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public Integer getLocation() {
		return location;
	}
	public void setLocation(Integer location) {
		this.location = location;
	}
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		//return null;
	}
	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getLocationString() {
		return locationString;
	}
	public void setLocationString(String locationString) {
		this.locationString = locationString;
	}
	public ArrayList<String> getSlStarsList() {
		return slStarsList;
	}
	public void setSlStarsList(ArrayList<String> slStarsList) {
		this.slStarsList = slStarsList;
	}
	public ArrayList<String> getMyStarsList() {
		return myStarsList;
	}
	public void setMyStarsList(ArrayList<String> myStarsList) {
		this.myStarsList = myStarsList;
	}
	public Boolean getShowFav() {
		return showFav;
	}
	public void setShowFav(Boolean showFav) {
		this.showFav = showFav;
	}
	public Boolean getShowCheck() {
		return showCheck;
	}
	public void setShowCheck(Boolean showCheck) {
		this.showCheck = showCheck;
	}
	public Integer getSlRatingsCount() {
		return slRatingsCount;
	}
	public void setSlRatingsCount(Integer slRatingsCount) {
		this.slRatingsCount = slRatingsCount;
	}
	public Integer getMyRatingsCount() {
		return myRatingsCount;
	}
	public void setMyRatingsCount(Integer myRatingsCount) {
		this.myRatingsCount = myRatingsCount;
	}
	public Boolean getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	public int getSlRatingNumber() {
		return slRatingNumber;
	}
	public void setSlRatingNumber(int slRatingNumber) {
		this.slRatingNumber = slRatingNumber;
	}
	public int getMyRatingNumber() {
		return myRatingNumber;
	}
	public void setMyRatingNumber(int myRatingNumber) {
		this.myRatingNumber = myRatingNumber;
	}
	
	public Boolean getVehLiabilityInsVerified() {
		return vehLiabilityInsVerified;
	}
	public void setVehLiabilityInsVerified(Boolean vehLiabilityInsVerified) {
		this.vehLiabilityInsVerified = vehLiabilityInsVerified;
	}
	public Boolean getGenLiabilityInsVerified() {
		return genLiabilityInsVerified;
	}
	public void setGenLiabilityInsVerified(Boolean genLiabilityInsVerified) {
		this.genLiabilityInsVerified = genLiabilityInsVerified;
	}
	public Boolean getWorkCompInsVerified() {
		return workCompInsVerified;
	}
	public void setWorkCompInsVerified(Boolean workCompInsVerified) {
		this.workCompInsVerified = workCompInsVerified;
	}
	public Date getVehLiabilityInsVerifiedDate() {
		return vehLiabilityInsVerifiedDate;
	}
	public void setVehLiabilityInsVerifiedDate(Date vehLiabilityInsVerifiedDate) {
		this.vehLiabilityInsVerifiedDate = vehLiabilityInsVerifiedDate;
	}
	public Date getGenLiabilityInsVerifiedDate() {
		return genLiabilityInsVerifiedDate;
	}
	public void setGenLiabilityInsVerifiedDate(Date genLiabilityInsVerifiedDate) {
		this.genLiabilityInsVerifiedDate = genLiabilityInsVerifiedDate;
	}
	public Date getWorkCompInsVerifiedDate() {
		return workCompInsVerifiedDate;
	}
	public void setWorkCompInsVerifiedDate(Date workCompInsVerifiedDate) {
		this.workCompInsVerifiedDate = workCompInsVerifiedDate;
	}
	public Double getVehLiabilityInsAmt() {
		return vehLiabilityInsAmt;
	}
	public void setVehLiabilityInsAmt(Double vehLiabilityInsAmt) {
		this.vehLiabilityInsAmt = vehLiabilityInsAmt;
	}
	public Double getGenLiabilityInsAmt() {
		return genLiabilityInsAmt;
	}
	public void setGenLiabilityInsAmt(Double genLiabilityInsAmt) {
		this.genLiabilityInsAmt = genLiabilityInsAmt;
	}
	public Double getWorkCompInsAmt() {
		return workCompInsAmt;
	}
	public void setWorkCompInsAmt(Double workCompInsAmt) {
		this.workCompInsAmt = workCompInsAmt;
	}
	public Double getPerformanceScore() {
		return performanceScore;
	}
	public void setPerformanceScore(Double performanceScore) {
		this.performanceScore = performanceScore;
	}
	public Boolean getRoutingPriorityApplied() {
		return routingPriorityApplied;
	}
	public void setRoutingPriorityApplied(Boolean routingPriorityApplied) {
		this.routingPriorityApplied = routingPriorityApplied;
	}
	public Double getFirmPerformanceScore() {
		return firmPerformanceScore;
	}
	public void setFirmPerformanceScore(Double firmPerformanceScore) {
		this.firmPerformanceScore = firmPerformanceScore;
	}
	public String getFirmName() {
		return firmName;
	}
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}
	
	public static Comparator<SOWProviderDTO> getComparator(SortParameter sortParameter) {
        return new ScoreComparator(sortParameter);
    }
	
	public enum SortParameter {
	    SCORE_DESCENDING
	}
	
	private static class ScoreComparator implements Comparator<SOWProviderDTO> {
        private SortParameter parameter;

        private ScoreComparator(SortParameter parameters) {
            this.parameter = parameters;
        }

        public int compare(SOWProviderDTO o1, SOWProviderDTO o2) {
            int comparison;
                        comparison = (o2.getPerformanceScore() < o1.getPerformanceScore())? -1 : ((o2.getPerformanceScore() > o1.getPerformanceScore())? 1 : 0);
                        if (comparison != 0) return comparison;
            return 0;
        }
    }

	public List getAdditionalInsuranceList() {
		return additionalInsuranceList;
	}
	public void setAdditionalInsuranceList(List additionalInsuranceList) {
		this.additionalInsuranceList = additionalInsuranceList;
	}
	
	

}
