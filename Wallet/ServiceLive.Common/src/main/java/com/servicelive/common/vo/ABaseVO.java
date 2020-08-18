package com.servicelive.common.vo;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;


/**
 * The Class ABaseVO.
 */
@SuppressWarnings("serial")
public abstract class ABaseVO implements Serializable {

	/** The business transaction id. */
	protected Long businessTransactionId;

	/** The buyer id. */
	protected Long buyerId;

	/** The funding type id. */
	protected Long fundingTypeId;

	/** The provider id. */
	protected Long providerId;

	/** The service order id. */
	protected String serviceOrderId;

	/** The user name. */
	protected String userName;

	/**
	 * Instantiates a new a base vo.
	 */
	public ABaseVO() {

	}

	/**
	 * Copy constructor.
	 * 
	 * @param aBaseVO 
	 */
	public ABaseVO(ABaseVO aBaseVO) {

		super();
		this.businessTransactionId = aBaseVO.getBusinessTransactionId();
		this.fundingTypeId = aBaseVO.getFundingTypeId();
		this.serviceOrderId = aBaseVO.getServiceOrderId();
		this.userName = aBaseVO.getUserName();
		this.buyerId = aBaseVO.getBuyerId();
		this.providerId = aBaseVO.getProviderId();
	}

	/**
	 * Instantiates a new a base vo.
	 * 
	 * @param businessTransactionId 
	 * @param fundingTypeId 
	 * @param serviceOrderId 
	 * @param userName 
	 * @param buyerId 
	 * @param providerId 
	 */
	public ABaseVO(Long businessTransactionId, Long fundingTypeId, String serviceOrderId, String userName, Long buyerId, Long providerId) {

		super();
		this.businessTransactionId = businessTransactionId;
		this.fundingTypeId = fundingTypeId;
		this.serviceOrderId = serviceOrderId;
		this.userName = userName;
		this.buyerId = buyerId;
		this.providerId = providerId;
	}

	/**
	 * Gets the business transaction id.
	 * 
	 * @return the business transaction id
	 */
	public Long getBusinessTransactionId() {

		return businessTransactionId;
	}

	/**
	 * Gets the buyer id.
	 * 
	 * @return the buyer id
	 */
	public Long getBuyerId() {

		return buyerId;
	}

	/**
	 * Gets the funding type id.
	 * 
	 * @return the funding type id
	 */
	public Long getFundingTypeId() {

		return fundingTypeId;
	}

	/**
	 * Gets the provider id.
	 * 
	 * @return the provider id
	 */
	public Long getProviderId() {

		return providerId;
	}

	/**
	 * Gets the service order id.
	 * 
	 * @return the service order id
	 */
	public String getServiceOrderId() {

		return serviceOrderId;
	}

	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public String getUserName() {

		return userName;
	}

	/**
	 * Sets the business transaction id.
	 * 
	 * @param businessTransactionId the new business transaction id
	 */
	@XmlElement()
	public void setBusinessTransactionId(Long businessTransactionId) {

		this.businessTransactionId = businessTransactionId;
	}

	/**
	 * Sets the buyer id.
	 * 
	 * @param buyerId the new buyer id
	 */
	@XmlElement()
	public void setBuyerId(Long buyerId) {

		this.buyerId = buyerId;
	}

	/**
	 * Sets the funding type id.
	 * 
	 * @param fundingTypeId the new funding type id
	 */
	@XmlElement()
	public void setFundingTypeId(Long fundingTypeId) {

		this.fundingTypeId = fundingTypeId;
	}

	/**
	 * Sets the provider id.
	 * 
	 * @param providerId the new provider id
	 */
	@XmlElement()
	public void setProviderId(Long providerId) {

		this.providerId = providerId;
	}

	/**
	 * Sets the service order id.
	 * 
	 * @param serviceOrderId the new service order id
	 */
	@XmlElement()
	public void setServiceOrderId(String serviceOrderId) {

		this.serviceOrderId = serviceOrderId;
	}

	/**
	 * Sets the user name.
	 * 
	 * @param userName the new user name
	 */
	@XmlElement()
	public void setUserName(String userName) {

		this.userName = userName;
	}

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
}
