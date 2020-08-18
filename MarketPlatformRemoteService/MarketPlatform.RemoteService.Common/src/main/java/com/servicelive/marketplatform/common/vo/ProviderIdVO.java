package com.servicelive.marketplatform.common.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

@XmlRootElement()
public class ProviderIdVO implements Serializable{

    /**
	 *
	 */
	private static final long serialVersionUID = 4891774862616124095L;
    
    private Integer resourceId;
    private Integer vendorId;
    private String firmName;
    private Double routingTimePerfScore;
    private Double routingTimeFirmPerfScore;
    private String providerFirstName;
    private String providerLastName;
    private Integer rank;
    private Date completedDate;
    @XmlElement
	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	@XmlElement
    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    @XmlElement
    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

	public Double getRoutingTimePerfScore() {
		return routingTimePerfScore;
	}

	public void setRoutingTimePerfScore(Double routingTimePerfScore) {
		this.routingTimePerfScore = routingTimePerfScore;
	}

	public Double getRoutingTimeFirmPerfScore() {
		return routingTimeFirmPerfScore;
	}

	public void setRoutingTimeFirmPerfScore(Double routingTimeFirmPerfScore) {
		this.routingTimeFirmPerfScore = routingTimeFirmPerfScore;
	}

	public String getProviderFirstName() {
		return providerFirstName;
	}

	public void setProviderFirstName(String providerFirstName) {
		this.providerFirstName = providerFirstName;
	}

	public String getProviderLastName() {
		return providerLastName;
	}

	public void setProviderLastName(String providerLastName) {
		this.providerLastName = providerLastName;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}
	
	public static Comparator<ProviderIdVO> getComparator(SortParameter... sortParameters) {
        return new RoutingComparator(sortParameters);
    }
	
	public enum SortParameter {
	    SCORE_DESCENDING, FIRM_SCORE_DESCENDING, DATE_ASCENDING, PROVIDER_NAME_ASCENDING,FIRM_NAME_ASCENDING, RANK
	}
	
	private static class RoutingComparator implements Comparator<ProviderIdVO> {
        private SortParameter[] parameters;

        private RoutingComparator(SortParameter[] parameters) {
            this.parameters = parameters;
        }

        public int compare(ProviderIdVO o1, ProviderIdVO o2) {
            int comparison;
            for (SortParameter parameter : parameters) {
                switch (parameter) {
                    case SCORE_DESCENDING:
                        comparison = (o2.getRoutingTimePerfScore() < o1.getRoutingTimePerfScore())? -1 : ((o2.getRoutingTimePerfScore() > o1.getRoutingTimePerfScore())? 1 : 0);
                        if (comparison != 0) return comparison;
                        break;
                    case FIRM_SCORE_DESCENDING:
                        comparison = (o2.getRoutingTimeFirmPerfScore() < o1.getRoutingTimeFirmPerfScore())? -1 : ((o2.getRoutingTimeFirmPerfScore() > o1.getRoutingTimeFirmPerfScore())? 1 : 0);
                        if (comparison != 0) return comparison;
                        break;
                    case DATE_ASCENDING:
                    	if(null!= o1.getCompletedDate() && null!= o2.getCompletedDate()){
                        comparison = o1.getCompletedDate().compareTo(o2.getCompletedDate());
                        if (comparison != 0) return comparison;
                    	}
                        break;
                    case PROVIDER_NAME_ASCENDING:
                    	if(null!=o1.getProviderFirstName() && null!= o1.getProviderLastName() && null!= o2.getProviderFirstName() && null!=o2.getProviderLastName()){
                        comparison = o1.getProviderFirstName().concat(o1.getProviderLastName()).compareTo(o2.getProviderFirstName().concat(o2.getProviderLastName()));
                        if (comparison != 0) return comparison;
                    	}
                        break;
                    case FIRM_NAME_ASCENDING:
                    	if(null!=o1.getFirmName() && null!=o2.getFirmName()){
                        comparison = o1.getFirmName().compareTo(o2.getFirmName());
                        if (comparison != 0) return comparison;
                    	}
                        break;
                    case RANK:
                    	if(null!=o1.getRank() && null!=o2.getRank()){
                        comparison = o1.getRank().compareTo(o2.getRank());
                        if (comparison != 0) return comparison;
                    	}
                        break;
                }
            }
            return 0;
        }
    }
	
}
