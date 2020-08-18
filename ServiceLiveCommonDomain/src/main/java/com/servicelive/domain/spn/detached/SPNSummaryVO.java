/**
 *
 */
package com.servicelive.domain.spn.detached;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hoza
 *
 */
public class SPNSummaryVO implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 20090121L;
	private Integer spnId;
	private String spnName;
	private Integer totalActiveCampaign;
	private Boolean isAliasNetwork;
	private Integer originalNetworkIdOfAlias;
	private Date aliasEffectiveDate;
	private Date createdDate; // First time SPn got created
	private Date modifiedDate; //last time SPN was modfied
	private ProviderMatchingCountsVO invitedPros = new ProviderMatchingCountsVO();
	private ProviderMatchingCountsVO interestedPros = new ProviderMatchingCountsVO();
	private ProviderMatchingCountsVO appliedPros = new ProviderMatchingCountsVO();
	private ProviderMatchingCountsVO notInterestedPros = new ProviderMatchingCountsVO();
	private ProviderMatchingCountsVO firmOutOfCompliancePros = new ProviderMatchingCountsVO();
	private ProviderMatchingCountsVO activePros = new ProviderMatchingCountsVO();
	private ProviderMatchingCountsVO incompletePros = new ProviderMatchingCountsVO();
	private ProviderMatchingCountsVO declinePros = new ProviderMatchingCountsVO();
	private ProviderMatchingCountsVO removedPros = new ProviderMatchingCountsVO();
	private Long activeServiceProCounts = Long.valueOf(0);
	private Long oocServiceProCounts = Long.valueOf(0);
	private Long removedServiceoProCounts = Long.valueOf(0);
	private SPNSummaryVO aliasSPN;
	private Integer exceptionInd;


	public Integer getExceptionInd() {
		return exceptionInd;
	}


	public void setExceptionInd(Integer exceptionInd) {
		this.exceptionInd = exceptionInd;
	}


	/**
	 * @return the aliasSPN
	 */
	public SPNSummaryVO getAliasSPN() {
		return aliasSPN;
	}


	/**
	 * @param aliasSPN the aliasSPN to set
	 */
	public void setAliasSPN(SPNSummaryVO aliasSPN) {
		this.aliasSPN = aliasSPN;
	}


	public void addALL(SPNSummaryVO addmetoThis) throws Exception {


		this.invitedPros.add(addmetoThis.getInvitedPros());
		this.interestedPros.add(addmetoThis.getInterestedPros());
		this.appliedPros.add(addmetoThis.getAppliedPros());
		this.notInterestedPros.add(addmetoThis.getNotInterestedPros());
		this.firmOutOfCompliancePros.add(addmetoThis.getFirmOutOfCompliancePros());
		this.activePros.add(addmetoThis.getActivePros());
		this.incompletePros.add(addmetoThis.getIncompletePros());
		this.declinePros.add(addmetoThis.getDeclinePros());
		this.removedPros.add(addmetoThis.getRemovedPros());

	}


	/**
	 * @return the spnId
	 */
	public Integer getSpnId() {
		return spnId;
	}
	/**
	 * @param spnId the spnId to set
	 */
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	/**
	 * @return the spnName
	 */
	public String getSpnName() {
		return spnName;
	}
	/**
	 * @param spnName the spnName to set
	 */
	public void setSpnName(String spnName) {
		this.spnName = spnName;
	}
	/**
	 * @return the totalActiveCampaign
	 */
	public Integer getTotalActiveCampaign() {
		return totalActiveCampaign;
	}
	/**
	 * @param totalActiveCampaign the totalActiveCampaign to set
	 */
	public void setTotalActiveCampaign(Integer totalActiveCampaign) {
		this.totalActiveCampaign = totalActiveCampaign;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the invitedPros
	 */
	public ProviderMatchingCountsVO getInvitedPros() {
		return invitedPros;
	}
	/**
	 * @param invitedPros the invitedPros to set
	 */
	public void setInvitedPros(ProviderMatchingCountsVO invitedPros) {
		this.invitedPros = invitedPros;
	}
	/**
	 * @return the interestedPros
	 */
	public ProviderMatchingCountsVO getInterestedPros() {
		return interestedPros;
	}
	/**
	 * @param interestedPros the interestedPros to set
	 */
	public void setInterestedPros(ProviderMatchingCountsVO interestedPros) {
		this.interestedPros = interestedPros;
	}
	/**
	 * @return the appliedPros
	 */
	public ProviderMatchingCountsVO getAppliedPros() {
		return appliedPros;
	}
	/**
	 * @param appliedPros the appliedPros to set
	 */
	public void setAppliedPros(ProviderMatchingCountsVO appliedPros) {
		this.appliedPros = appliedPros;
	}
	/**
	 * @return the notInterestedPros
	 */
	public ProviderMatchingCountsVO getNotInterestedPros() {
		return notInterestedPros;
	}
	/**
	 * @param notInterestedPros the notInterestedPros to set
	 */
	public void setNotInterestedPros(ProviderMatchingCountsVO notInterestedPros) {
		this.notInterestedPros = notInterestedPros;
	}
	/**
	 *
	 * @return ProviderMatchingCountsVO
	 */
	public ProviderMatchingCountsVO getFirmOutOfCompliancePros() {
		return firmOutOfCompliancePros;
	}
	/**
	 *
	 * @param firmOutOfCompliancePros
	 */
	public void setFirmOutOfCompliancePros(ProviderMatchingCountsVO firmOutOfCompliancePros) {
		this.firmOutOfCompliancePros = firmOutOfCompliancePros;
	}
	/**
	 *
	 * @return ProviderMatchingCountsVO
	 */
	public ProviderMatchingCountsVO getActivePros() {
		return activePros;
	}
	/**
	 *
	 * @param activePros
	 */
	public void setActivePros(ProviderMatchingCountsVO activePros) {
		this.activePros = activePros;
	}

	/**
	 * @return the isAliasNetwork
	 */
	public Boolean getIsAliasNetwork() {
		return isAliasNetwork;
	}
	/**
	 * @param isAliasNetwork the isAliasNetwork to set
	 */
	public void setIsAliasNetwork(Boolean isAliasNetwork) {
		this.isAliasNetwork = isAliasNetwork;
	}
	/**
	 * @return the originalNetworkIdOfAlias
	 */
	public Integer getOriginalNetworkIdOfAlias() {
		return originalNetworkIdOfAlias;
	}
	/**
	 * @param originalNetworkIdOfAlias the originalNetworkIdOfAlias to set
	 */
	public void setOriginalNetworkIdOfAlias(Integer originalNetworkIdOfAlias) {
		this.originalNetworkIdOfAlias = originalNetworkIdOfAlias;
	}


	/**
	 * @return the incompletePros
	 */
	public ProviderMatchingCountsVO getIncompletePros() {
		return incompletePros;
	}


	/**
	 * @param incompletePros the incompletePros to set
	 */
	public void setIncompletePros(ProviderMatchingCountsVO incompletePros) {
		this.incompletePros = incompletePros;
	}


	/**
	 * @return the declinePros
	 */
	public ProviderMatchingCountsVO getDeclinePros() {
		return declinePros;
	}


	/**
	 * @param declinePros the declinePros to set
	 */
	public void setDeclinePros(ProviderMatchingCountsVO declinePros) {
		this.declinePros = declinePros;
	}


	/**
	 * @return the removedPros
	 */
	public ProviderMatchingCountsVO getRemovedPros() {
		return removedPros;
	}


	/**
	 * @param removedPros the removedPros to set
	 */
	public void setRemovedPros(ProviderMatchingCountsVO removedPros) {
		this.removedPros = removedPros;
	}


	/**
	 * @return the aliasEffectiveDate
	 */
	public Date getAliasEffectiveDate() {
		return aliasEffectiveDate;
	}


	/**
	 * @param aliasEffectiveDate the aliasEffectiveDate to set
	 */
	public void setAliasEffectiveDate(Date aliasEffectiveDate) {
		this.aliasEffectiveDate = aliasEffectiveDate;
	}


	/**
	 * @return the activeServiceProCounts
	 */
	public Long getActiveServiceProCounts() {
		return activeServiceProCounts;
	}


	/**
	 * @param activeServiceProCounts the activeServiceProCounts to set
	 */
	public void setActiveServiceProCounts(Long activeServiceProCounts) {
		this.activeServiceProCounts = activeServiceProCounts;
	}


	/**
	 * @return the oocServiceProCounts
	 */
	public Long getOocServiceProCounts() {
		return oocServiceProCounts;
	}


	/**
	 * @param oocServiceProCounts the oocServiceProCounts to set
	 */
	public void setOocServiceProCounts(Long oocServiceProCounts) {
		this.oocServiceProCounts = oocServiceProCounts;
	}


	/**
	 * @return the removedServiceoProCounts
	 */
	public Long getRemovedServiceoProCounts() {
		return removedServiceoProCounts;
	}


	/**
	 * @param removedServiceoProCounts the removedServiceoProCounts to set
	 */
	public void setRemovedServiceoProCounts(Long removedServiceoProCounts) {
		this.removedServiceoProCounts = removedServiceoProCounts;
	}

}
