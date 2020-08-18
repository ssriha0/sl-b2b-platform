/**
 *
 */
package com.newco.marketplace.vo.searchportal;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.newco.marketplace.vo.MPBaseVO;

/**
 * @author hoza
 *
 */
public class SearchPortalLocationVO extends MPBaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 5883497490767426561L;

	private String phoneNumber;
	private String buyerPhoneNumber;
	private String emailAddress;
	private String buyerEmailAddress;
	private String state;
	private String city;
	private String zip;
	private String buyerZip;
	private Integer marketId;
	private Integer districtId;
	private Integer regionId;
	private String marketName;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @return the marketId
	 */
	public Integer getMarketId() {
		return marketId;
	}
	/**
	 * @param marketId the marketId to set
	 */
	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}
	/**
	 * @return the districtId
	 */
	public Integer getDistrictId() {
		return districtId;
	}
	/**
	 * @param districtId the districtId to set
	 */
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}
	/**
	 * @return the regionId
	 */
	public Integer getRegionId() {
		return regionId;
	}
	/**
	 * @param regionId the regionId to set
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	/**
	 * @return the marketName
	 */
	public String getMarketName() {
		return marketName;
	}
	/**
	 * @param marketName the marketName to set
	 */
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public boolean isFilterEmpty() {
		boolean result = false;
		if(


				(marketId == null || ( new Integer(-1).compareTo(marketId)) == 0  )
				&&
				(districtId == null || ( new Integer(-1).compareTo(districtId)) == 0  )

				 &&
				( StringUtils.isBlank(phoneNumber))
				&&
				( StringUtils.isBlank(emailAddress))
				&&
				( StringUtils.isBlank(state) || "-1".equals(state)  )
				&&
				( StringUtils.isBlank(city))
				&&
				( StringUtils.isBlank(zip))
				&&
				(regionId == null || ( new Integer(-1).compareTo(regionId)) == 0  )

				){
			result = true;


		}
	return result;
	}
	public String getBuyerEmailAddress() {
		return buyerEmailAddress;
	}
	public void setBuyerEmailAddress(String buyerEmailAddress) {
		this.buyerEmailAddress = buyerEmailAddress;
	}
	public String getBuyerPhoneNumber() {
		return buyerPhoneNumber;
	}
	public void setBuyerPhoneNumber(String buyerPhoneNumber) {
		this.buyerPhoneNumber = buyerPhoneNumber;
	}
	public String getBuyerZip() {
		return buyerZip;
	}
	public void setBuyerZip(String buyerZip) {
		this.buyerZip = buyerZip;
	}

}
