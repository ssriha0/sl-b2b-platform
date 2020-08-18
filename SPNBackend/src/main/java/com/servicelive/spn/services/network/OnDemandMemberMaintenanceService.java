/**
 * 
 */
package com.servicelive.spn.services.network;

import com.servicelive.domain.spn.network.SPNProviderFirmState;
import com.servicelive.spn.common.detached.MemberMaintenanceCriteriaVO;
import com.servicelive.spn.common.detached.ProviderMatchApprovalCriteriaVO;
import com.servicelive.spn.dao.network.SPNProviderFirmStateDao;

/**
 * @author hoza
 * 
 */
public class OnDemandMemberMaintenanceService extends MemberMaintenanceService {
	private SPNProviderFirmStateDao spnProviderFirmStateDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.spn.services.network.MemberMaintenanceService#deleteDuplicateMembdersofSPNs(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	protected void deleteDuplicateMembersOfSPNs(MemberMaintenanceCriteriaVO mmCriteriaVO) throws Exception {
		providerMatchService.deleteDuplicateMembersOfSpns(mmCriteriaVO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.spn.services.network.MemberMaintenanceService#processFirm(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	protected void processFirm(MemberMaintenanceCriteriaVO mmCriteriaVO,ProviderMatchApprovalCriteriaVO criteriaVO) throws Exception {
		// We are coding here such a way thet we can Use this Service for Ovverriden status as well as non Overriden status
		Integer spnId = mmCriteriaVO.getSpnId();
		Integer providerFirmId = mmCriteriaVO.getProviderFirmId();
		if (spnId == null || providerFirmId == null) {
			throw new IllegalArgumentException("Either SPNID or Provider Firm ID or Both are  NULL. Value Supplied are :spnid[" + spnId + "] and Provider Firm id [" + providerFirmId + "]");
		}
		// 3 possibilities
		// 1) Firm Not available --> Throws "NO Available exception"
		// 2) Firm is NOT Overridden --> RUN Firm Compliance Routine
		// 3) Firm is Overridden --> SKIP Firm Compliance and Move on to Service pro Compliance Routine findProviderFirmStateByprimaryKey

		// Step 1 : First we will retrieve the provider Firm supplied
		SPNProviderFirmState pfState = spnProviderFirmStateDao.findBySpnIdAndProviderFirmId(spnId, providerFirmId);
		// Step 2 : In case of NULL you we can scrutinize the provider firm for the compliance but for Member Mangemnet 3 we do not need that so I m throwing exception
		if (pfState == null) {
			throw new Exception("Could not find provider Firm  as a member of the SPN.  Value Supplied are :spnid[" + spnId + "] and Provider Firm id [" + providerFirmId + "]");
		}
		// If you return from here that means.....Service Provider is going to get checked for Compliance for this Provider Firm.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.spn.services.network.MemberMaintenanceService#processServiceProviders(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	protected void processServiceProviders(MemberMaintenanceCriteriaVO mmCriteriaVO,ProviderMatchApprovalCriteriaVO criteriaVO) throws Exception {
		complyServiceProvider(mmCriteriaVO,criteriaVO);
	}

	public Boolean complyProviderFirm(Integer spnId, Integer specificProviderFirmId) {
		Boolean result = Boolean.TRUE;
		try {
			handleSpn(getMMCriteriaVO(spnId, specificProviderFirmId));
		} catch (Exception e) {
			logger.debug("Couldn't complete Compliance process for spnid[" + spnId + "] and Provider Firm id [" + specificProviderFirmId + "]", e);
			result = Boolean.FALSE;
		}
		return result;
	}

	/**
	 * @param spnProviderFirmStateDao
	 *            the spnProviderFirmStateDao to set
	 */
	public void setSpnProviderFirmStateDao(SPNProviderFirmStateDao spnProviderFirmStateDao) {
		this.spnProviderFirmStateDao = spnProviderFirmStateDao;
	}

}
