package com.servicelive.domain.spn.detached;

import java.util.Comparator;
import java.util.Date;

public class SPNCoverageDTO {
	
	private String provFirstName;
	private String provLastName;
	private Integer memberId;
	private String firmName;
	private Integer firmId;
	private Double score = 0.0;
	private String rank;
	private String jobTitle;
	private String slStatus;
	private Integer noOfEligibleProvs = 0;
	private Integer spnId;
	private String state;
	private String stateName;
	private String zip;
	private Integer marketId;
	private String market;
	private Date completedDate;
	private int noScoreInd = 0;
	
	public int getNoScoreInd() {
		return noScoreInd;
	}
	public void setNoScoreInd(int noScoreInd) {
		this.noScoreInd = noScoreInd;
	}
	public String getProvFirstName() {
		return provFirstName;
	}
	public void setProvFirstName(String provFirstName) {
		this.provFirstName = provFirstName;
	}
	public String getProvLastName() {
		return provLastName;
	}
	public void setProvLastName(String provLastName) {
		this.provLastName = provLastName;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getFirmName() {
		return firmName;
	}
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}
	public Integer getFirmId() {
		return firmId;
	}
	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getSlStatus() {
		return slStatus;
	}
	public void setSlStatus(String slStatus) {
		this.slStatus = slStatus;
	}
	public Integer getNoOfEligibleProvs() {
		return noOfEligibleProvs;
	}
	public void setNoOfEligibleProvs(Integer noOfEligibleProvs) {
		this.noOfEligibleProvs = noOfEligibleProvs;
	}
	public Integer getSpnId() {
		return spnId;
	}
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public Integer getMarketId() {
		return marketId;
	}
	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public Date getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}
	
	//comparator to rank the providers/firms
	public static Comparator<SPNCoverageDTO> getComparator(SortParameter... sortParameters) {
        return new CoverageComparator(sortParameters);
    }
	
	public enum SortParameter {
		SCORE_ASCENDING, SCORE_DESCENDING, DATE_ASCENDING, PROVIDER_NAME_ASCENDING, PROVIDER_NAME_DESCENDING, FIRM_NAME_ASCENDING, FIRM_NAME_DESCENDING,
		SL_STATUS_ASCENDING, SL_STATUS_DESCENDING, TITLE_ASCENDING, TITLE_DESCENDING, NO_OF_APPROVED_PROVIDERS_ASCENDING, NO_OF_APPROVED_PROVIDERS_DESCENDING
	}
	
	private static class CoverageComparator implements Comparator<SPNCoverageDTO> {
        private SortParameter[] parameters;

        private CoverageComparator(SortParameter[] parameters) {
            this.parameters = parameters;
        }

        public int compare(SPNCoverageDTO o1, SPNCoverageDTO o2) {
            int comparison;
            for (SortParameter parameter : parameters) {
                switch (parameter) {
                	case SCORE_ASCENDING:
                		comparison = (o2.getScore() > o1.getScore())? -1 : ((o2.getScore() < o1.getScore())? 1 : 0);
                		if (comparison != 0) return comparison;
                		break;
                    case SCORE_DESCENDING:
                        comparison = (o2.getScore() < o1.getScore())? -1 : ((o2.getScore() > o1.getScore())? 1 : 0);
                        if (comparison != 0) return comparison;
                        break;
                    case DATE_ASCENDING:
                        comparison = o1.getCompletedDate().compareTo(o2.getCompletedDate());
                        if (comparison != 0) return comparison;
                        break;
                    case PROVIDER_NAME_ASCENDING:
                        comparison = o1.getProvFirstName().trim().concat(o1.getProvLastName().trim()).compareTo(o2.getProvFirstName().trim().concat(o2.getProvLastName().trim()));
                        if (comparison != 0) return comparison;
                        break;
                    case PROVIDER_NAME_DESCENDING:
                        comparison = o2.getProvFirstName().trim().concat(o2.getProvLastName().trim()).compareTo(o1.getProvFirstName().trim().concat(o1.getProvLastName().trim()));
                        if (comparison != 0) return comparison;
                        break;
                    case FIRM_NAME_ASCENDING:
                        comparison = o1.getFirmName().trim().compareTo(o2.getFirmName().trim());
                        if (comparison != 0) return comparison;
                        break;
                    case FIRM_NAME_DESCENDING:
                        comparison = o2.getFirmName().trim().compareTo(o1.getFirmName().trim());
                        if (comparison != 0) return comparison;
                        break;
                    case SL_STATUS_ASCENDING:
                        comparison = o1.getSlStatus().trim().compareTo(o2.getSlStatus().trim());
                        if (comparison != 0) return comparison;
                        break;
                    case SL_STATUS_DESCENDING:
                    	comparison = o2.getSlStatus().trim().compareTo(o1.getSlStatus().trim());
                        if (comparison != 0) return comparison;
                        break;
                    case TITLE_ASCENDING:
                        comparison = o1.getJobTitle().trim().compareTo(o2.getJobTitle().trim());
                        if (comparison != 0) return comparison;
                        break;
                    case TITLE_DESCENDING:
                    	comparison = o2.getJobTitle().trim().compareTo(o1.getJobTitle().trim());
                        if (comparison != 0) return comparison;
                        break;
                    case NO_OF_APPROVED_PROVIDERS_ASCENDING:
                		comparison = (o2.getNoOfEligibleProvs() > o1.getNoOfEligibleProvs())? -1 : ((o2.getNoOfEligibleProvs() < o1.getNoOfEligibleProvs())? 1 : 0);
                		if (comparison != 0) return comparison;
                		break;
                    case NO_OF_APPROVED_PROVIDERS_DESCENDING:
                    	comparison = (o2.getNoOfEligibleProvs() < o1.getNoOfEligibleProvs())? -1 : ((o2.getNoOfEligibleProvs() > o1.getNoOfEligibleProvs())? 1 : 0);
                        if (comparison != 0) return comparison;
                        break;
                }
            }
            return 0;
        }
    }

}
