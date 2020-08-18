package com.newco.marketplace.api.beans.so.offer;

import java.sql.Timestamp;
import java.util.List;

import com.newco.marketplace.business.iBusiness.om.api.beans.common.UserIdentificationRequest;
import com.newco.marketplace.api.beans.so.OfferReasonCodes;
import com.newco.marketplace.api.beans.so.CounterOfferResources;
import com.newco.marketplace.business.iBusiness.om.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing request information for 
 * the CreateCounterOfferRequest
 * @author Infosys
 *
 */
@XStreamAlias("counterOffer")
public class CreateCounterOfferRequest extends UserIdentificationRequest{
	
	@XStreamAlias("version")   
	@XStreamAsAttribute()   
	private String version = PublicAPIConstant.SORESPONSE_VERSION;
	 
	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation;
	 
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace = PublicAPIConstant.COUNTER_OFFER_NAMESPACE;
	 
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
	
	@XStreamAlias("serviceDateTime1")
	private String serviceDateTime1;
	
	@XStreamAlias("serviceDateTime2")
	private String serviceDateTime2;
	
	@XStreamAlias("spendLimit")
	private String spendLimit;

	@XStreamAlias("offerExpirationDate")
	private String offerExpirationDate;
	
	private Timestamp serviceDate1;
	private Timestamp serviceDate2;
	private String serviceTimeStart;
	private String serviceTimeEnd;
	private Timestamp conditionalExpirationDate;
	private String conditionalExpirationTime;
	
	@XStreamAlias("resourceIds")
	private CounterOfferResources resourceIds;	
	
	@XStreamAlias("reasonCodes")
	private OfferReasonCodes reasonCodes;

	public List<Integer> getReasonCodes() {
		if(reasonCodes != null)
			return reasonCodes.getReasonCode();
		else 
			return null;
	}

	public void setReasonCodes(OfferReasonCodes reasonCodes) {
		this.reasonCodes = reasonCodes;
	}
	
	public List<Integer> getResourceIds() {
		if(resourceIds != null)
			return resourceIds.getResourceId();
		else 
			return null;
	}

	public void setResourceIds(CounterOfferResources resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getServiceDateTime1() {
		return serviceDateTime1;
	}

	public void setServiceDateTime1(String serviceDateTime1) {
		this.serviceDateTime1 = serviceDateTime1;
	}

	public String getServiceDateTime2() {
		return serviceDateTime2;
	}

	public void setServiceDateTime2(String serviceDateTime2) {
		this.serviceDateTime2 = serviceDateTime2;
	}

	public String getSpendLimit() {
		return spendLimit;
	}

	public void setSpendLimit(String spendLimit) {
		this.spendLimit = spendLimit;
	}

	public String getOfferExpirationDate() {
		return offerExpirationDate;
	}

	public void setOfferExpirationDate(String offerExpirationDate) {
		this.offerExpirationDate = offerExpirationDate;
	}

	public Timestamp getServiceDate1() {
		return serviceDate1;
	}

	public void setServiceDate1(Timestamp serviceDate1) {
		this.serviceDate1 = serviceDate1;
	}

	public Timestamp getServiceDate2() {
		return serviceDate2;
	}

	public void setServiceDate2(Timestamp serviceDate2) {
		this.serviceDate2 = serviceDate2;
	}

	public String getServiceTimeStart() {
		return serviceTimeStart;
	}

	public void setServiceTimeStart(String serviceTimeStart) {
		this.serviceTimeStart = serviceTimeStart;
	}

	public String getServiceTimeEnd() {
		return serviceTimeEnd;
	}

	public void setServiceTimeEnd(String serviceTimeEnd) {
		this.serviceTimeEnd = serviceTimeEnd;
	}


	public String getConditionalExpirationTime() {
		return conditionalExpirationTime;
	}

	public void setConditionalExpirationTime(String conditionalExpirationTime) {
		this.conditionalExpirationTime = conditionalExpirationTime;
	}

	public Timestamp getConditionalExpirationDate() {
		return conditionalExpirationDate;
	}

	public void setConditionalExpirationDate(Timestamp conditionalExpirationDate) {
		this.conditionalExpirationDate = conditionalExpirationDate;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}


	public String getSchemaLocation() {
		return schemaLocation;
	}


	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}


	public String getNamespace() {
		return namespace;
	}


	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}


	public String getSchemaInstance() {
		return schemaInstance;
	}


	public void setSchemaInstance(String schemaInstance) {
		this.schemaInstance = schemaInstance;
	}

}
