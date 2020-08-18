/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.search.providerProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.newco.marketplace.search.vo.ProviderSearchResponseVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * This is a bean class for storing all information of 
 * the AvgByKPI 
 * @author Infosys
 *
 */
@XStreamAlias("avgByKPI")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderAvgByKPI {
	
	@XStreamAlias("cleanliness")
	private float cleanliness;
	
	@XStreamAlias("timeliness")
	private float timeliness;
	
	@XStreamAlias("communication")
	private float communication;
	
	@XStreamAlias("professionalism")
	private float professionalism;
	
	@XStreamAlias("quality")
	private float quality;
	
	@XStreamAlias("value")
	private float value;

	public ProviderAvgByKPI() {
		
	}
	
	public ProviderAvgByKPI(ProviderSearchResponseVO providerSearchResponseVO) {
		this.setCleanliness(providerSearchResponseVO
				.getCleanlinessRatings());
		this.setCommunication(providerSearchResponseVO
				.getCommunicationRatings());
		this.setProfessionalism(providerSearchResponseVO
				.getProfessionalismRatings());
		this.setQuality(providerSearchResponseVO
				.getQualityRatings());
		this.setTimeliness(providerSearchResponseVO
				.getTimelinessRatings());
		this.setValue(providerSearchResponseVO.getValueRatings());
	}
	
	public float getCleanliness() {
		return cleanliness;
	}

	public void setCleanliness(float cleanliness) {
		this.cleanliness = cleanliness;
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

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

}
