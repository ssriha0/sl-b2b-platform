package com.servicelive.spn.dao.provider.impl;

import static com.servicelive.spn.common.SPNBackendConstants.MAX_PARAMETER;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.core.DataServiceException;
import com.servicelive.domain.common.ProviderLocationDetails;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.lookup.LookupWfStates;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.provider.ServiceProviderDao;

/**
 * 
 * @author svanloon
 * 
 */
public class ServiceProviderDaoImpl extends AbstractBaseDao implements
		ServiceProviderDao {

	/**
	 * 
	 * @return ServiceProvider
	 */
	@SuppressWarnings("unchecked")
	public List<ServiceProvider> findAdmin(Integer providerFirmId) {
		List<ServiceProvider> spnAuditor = new ArrayList<ServiceProvider>();
		String hql = "from ServiceProvider o where o.providerFirm.id = :providerFirmId and o.primaryInd = true";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("providerFirmId", providerFirmId);
		try {

			List<ServiceProvider> spnAuditorDetails = query.getResultList();
			
			//SL-19381
			//Code added to fetch contact information of a firm
			//Fetch location details
			String firmDetails = StringUtils.EMPTY;
			firmDetails = firmDetails +"SELECT l.locn_id ,l.locn_name ,l.street_1 ,l.street_2 ,l.city ,l.state_cd ,l.zip ,l.zip4 ,l.country ,l.locn_type_id ,l.created_date ,l.modified_date ,l.modified_by FROM location l "
			 		+"INNER JOIN vendor_location vl WHERE vl.locn_id = l.locn_id AND l.locn_type_id=1 AND vl.vendor_id = :providerFirmId ";
			Query firmDetailsquery = getEntityManager().createNativeQuery(firmDetails);
			firmDetailsquery.setParameter("providerFirmId", providerFirmId);
			
			List<Object[]> locationList = firmDetailsquery.getResultList();
			ProviderLocationDetails provLocDetails = new ProviderLocationDetails();
			if(null != locationList){
				Object[] location = locationList.get(0);
				provLocDetails.setLocnId((Integer)location[0]);
				provLocDetails.setLocnName((String)location[1]);
				provLocDetails.setStreet1((String)location[2]);
				provLocDetails.setStreet2((String)location[3]) ;
				provLocDetails.setCity((String)location[4]) ;
				provLocDetails.setStateCd((String)location[5]) ;
				provLocDetails.setZip((String)location[6]) ;
				provLocDetails.setZip4((String)location[7]) ;
				provLocDetails.setCountry((String)location[8]) ;
				provLocDetails.setLocnTypeId((Integer)location[9]) ;
				provLocDetails.setCreatedDate((Timestamp)location[10]) ;
				provLocDetails.setModifiedDate((Timestamp)location[11]);
				provLocDetails.setModifiedBy((String)location[12]);
			}
			
			//Set the location details in ServiceProvider object
			for(ServiceProvider serviceProvider : spnAuditorDetails)
			{
				serviceProvider.setProviderLocationDetails(provLocDetails);
			}
			
			return spnAuditorDetails;

		} catch (IllegalStateException e) {
			logger.debug("ServiceProviderDaoImp.findAdmin IllegalStateException"
					+ e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<ServiceProvider> findAdminsBySpnIdAndWfStates(Integer spnId,
			List<LookupSPNWorkflowState> wfStates) {
		String hql = "select o from ServiceProvider o, SPNProviderFirmState s where o.providerFirm.id = s.providerFirmStatePk.providerFirm.id and o.primaryInd = true and s.providerFirmStatePk.spnHeader.spnId = :spnId and s.wfState in (:states)";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("spnId", spnId);
		query.setParameter("states", wfStates);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.spn.dao.provider.ServiceProviderDao#
	 * findMarketReadyServiceProForProviderFirms(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<ServiceProvider> findMarketReadyServiceProForProviderFirms(
			List<Integer> proFirmIds) {
		List<ServiceProvider> providers = new ArrayList<ServiceProvider>(0);
		String hql = "select o from ServiceProvider o where o.providerFirm.id  IN ( :proFirmIds ) AND o.serviceLiveWorkFlowId  = :marketReadyState  AND o.resourceInd = :resourceInd ";
		int mod = proFirmIds.size() % MAX_PARAMETER.intValue();
		int loopSize = (proFirmIds.size() / MAX_PARAMETER.intValue())
				+ (mod == 0 ? 0 : 1);
		int fromIndex = 0;
		int endIndex = 0;
		for (int i = 0; i < loopSize; i++) {

			if (i > 0) {
				fromIndex = (i * MAX_PARAMETER);
			}
			endIndex = fromIndex + MAX_PARAMETER;
			if (endIndex > proFirmIds.size()) {
				endIndex = proFirmIds.size();
			}
			;
			List<Integer> partialList = proFirmIds.subList(fromIndex, endIndex);
			Query query = getEntityManager().createQuery(hql);
			query.setParameter("proFirmIds", partialList);
			LookupWfStates marketReadyState = new LookupWfStates();
			marketReadyState.setId(Integer.valueOf(6)); // 6 is market Ready
			query.setParameter("marketReadyState", marketReadyState);
			query.setParameter("resourceInd", Boolean.TRUE);
			providers.addAll(query.getResultList());
		}
		return providers;

	}

	// fetches the list of spns of the provider for which notifications are send
	public List<Object[]> getSPNForProvider(Integer providerFirmId,
			Integer buyerId, Integer requirementType)
			throws DataServiceException {
		String hql = StringUtils.EMPTY;

		if (null == requirementType || 1 == requirementType) {
			hql = hql
					+ "select distinct(spn_name),spn.spn_id from audit_cred_expiry_notification audit "
					+ "join vendor_credentials vendor "
					+ "on audit.cred_id = vendor.vendor_cred_id and audit.vendor_id = :vendorId and audit.credential_ind = 1 "
					+

					"join spnet_provider_firm_state spfs "
					+ "on spfs.provider_firm_id = audit.vendor_id and spfs.provider_wf_state = 'PF SPN MEMBER'"
					+

					"join spnet_buyer sb "
					+ "on sb.spn_id = spfs.spn_id and sb.buyer_id = :buyerId " +

					"join spnet_approval_criteria sp "
					+ "on sp.spn_id = sb.spn_id and sp.criteria_id = 14 "
					+ "and vendor.cred_category_id = sp.value " +

					"join spnet_hdr spn " + "on spn.spn_id = sp.spn_id ";
		}
		if (null == requirementType || 2 == requirementType) {
			if (null == requirementType) {
				hql = hql + "union ";
			}
			hql = hql
					+ "select distinct(spn_name),spn.spn_id from audit_cred_expiry_notification audit "
					+ "join vendor_credentials vendor "
					+ "on audit.cred_id = vendor.vendor_cred_id and audit.vendor_id = :vendorId and audit.credential_ind = 2 "
					+

					"join spnet_provider_firm_state spfs "
					+ "on spfs.provider_firm_id = audit.vendor_id and spfs.provider_wf_state = 'PF SPN MEMBER'"
					+

					"join spnet_buyer sb "
					+ "on sb.spn_id = spfs.spn_id and sb.buyer_id = :buyerId "
					+

					"join spnet_approval_criteria sp "
					+ "on sp.spn_id = sb.spn_id and sp.criteria_id in (8,11,27) "
					+ "and ((vendor.cred_category_id = 41 and sp.criteria_id = 11) "
					+ "or (vendor.cred_category_id = 42 and sp.criteria_id = 8) "
					+ "or (vendor.cred_category_id = 43 and sp.criteria_id = 27)) "
					+

					"join spnet_hdr spn " + "on spn.spn_id = sp.spn_id ";
		}
		if (null == requirementType || 3 == requirementType) {
			if (null == requirementType) {
				hql = hql + "union ";
			}
			hql = hql
					+ "select distinct(spn_name),spn.spn_id from audit_cred_expiry_notification audit "
					+ "join resource_credentials resource "
					+ "on audit.cred_id = resource.resource_cred_id and audit.vendor_id = :vendorId and audit.credential_ind = 3 "
					+

					"join spnet_serviceprovider_state spnet "
					+ "on audit.resource_id = spnet.service_provider_id and spnet.provider_wf_state = 'SP SPN APPROVED'"
					+

					"join spnet_buyer sb "
					+ "on sb.spn_id = spnet.spn_id and sb.buyer_id = :buyerId "
					+

					"join spnet_approval_criteria sp "
					+ "on sp.spn_id = sb.spn_id and sp.criteria_id = 17 "
					+ "and resource.cred_category_id = sp.value " +

					"join spnet_hdr spn " + "on spn.spn_id = sp.spn_id ";

		}

		Query query = getEntityManager().createNativeQuery(hql);
		query.setParameter("vendorId", providerFirmId);
		query.setParameter("buyerId", buyerId);

		try {
			return query.getResultList();

		} catch (Exception e) {
			logger.debug("Exception in ServiceProviderDaoImp.getSPNForProvider due to "
					+ e.getMessage());
			throw new DataServiceException(e.getMessage(), e);
		}

	}

	// fetches the list of credentials for which notifications are send
	public List<Object[]> getRequirementType(Integer providerFirmId,
			Integer buyerId, Integer spnId) throws DataServiceException {
		String hql = "select distinct(lu.credential_name),lu.credential_ind from lu_credential_ind lu "
				+ "join audit_cred_expiry_notification audit "
				+ "on lu.credential_ind = audit.credential_ind and audit.vendor_id = :vendorId "
				+

				"join vendor_credentials vendor "
				+ "on audit.cred_id = vendor.vendor_cred_id and audit.credential_ind in (1,2) "
				+

				"join spnet_provider_firm_state spfs "
				+ "on spfs.provider_firm_id = audit.vendor_id and spfs.provider_wf_state = 'PF SPN MEMBER'"
				+

				"join spnet_buyer sb "
				+ "on sb.spn_id = spfs.spn_id and sb.buyer_id = :buyerId "
				+

				"join spnet_approval_criteria sp "
				+ "on sp.spn_id = sb.spn_id and sp.criteria_id in (14,8,11,27) "
				+ "and ((vendor.cred_category_id = sp.value and sp.criteria_id = 14)"
				+ "or (vendor.cred_category_id = 41 and sp.criteria_id = 11) "
				+ "or (vendor.cred_category_id = 42 and sp.criteria_id = 8) "
				+ "or (vendor.cred_category_id = 43 and sp.criteria_id = 27)) ";
		if (null != spnId) {
			hql = hql + "and sp.spn_id = :spnId ";
		}
		hql = hql
				+ "union "
				+

				"select distinct(lu.credential_name),lu.credential_ind from lu_credential_ind lu "
				+ "join audit_cred_expiry_notification audit "
				+ "on lu.credential_ind = audit.credential_ind and audit.vendor_id = :vendorId "
				+

				"join resource_credentials resource "
				+ "on audit.cred_id = resource.resource_cred_id and audit.credential_ind = 3 "
				+

				"join spnet_serviceprovider_state spnet "
				+ "on audit.resource_id = spnet.service_provider_id and spnet.provider_wf_state = 'SP SPN APPROVED'"
				+

				"join spnet_buyer sb "
				+ "on sb.spn_id = spnet.spn_id and sb.buyer_id = :buyerId " +

				"join spnet_approval_criteria sp "
				+ "on sp.spn_id = sb.spn_id and sp.criteria_id = 17 "
				+ "and resource.cred_category_id = sp.value ";
		if (null != spnId) {
			hql = hql + "and sp.spn_id = :spnId ";
		}

		Query query = getEntityManager().createNativeQuery(hql);
		query.setParameter("vendorId", providerFirmId);
		query.setParameter("buyerId", buyerId);
		if (null != spnId) {
			query.setParameter("spnId", spnId);
		}
		try {
			return query.getResultList();

		} catch (Exception e) {
			logger.debug("Exception in ServiceProviderDaoImp.getRequirementType due to "
					+ e.getMessage());
			throw new DataServiceException(e.getMessage(), e);
		}

	}

	// fetches the details of expiration notifications
	public List<Object[]> getExpirationDetailsForProvider(
			Integer providerFirmId, Integer buyerId, Integer spnId,
			Integer requirementType) throws DataServiceException {
		String hql = StringUtils.EMPTY;

		if (null == requirementType || 1 == requirementType) {
			hql = hql
					+ "select distinct(audit.credential_name), audit.expiration_date, audit.notification_type, audit.notification_date, audit.cred_id from audit_cred_expiry_notification audit "
					+ "join vendor_credentials vendor "
					+ "on audit.cred_id = vendor.vendor_cred_id and audit.vendor_id = :vendorId and audit.credential_ind = 1 "
					+

					"join spnet_provider_firm_state spfs "
					+ "on spfs.provider_firm_id = audit.vendor_id and spfs.provider_wf_state = 'PF SPN MEMBER'"
					+

					"join spnet_buyer sb "
					+ "on sb.spn_id = spfs.spn_id and sb.buyer_id = :buyerId " +

					"join spnet_approval_criteria sp "
					+ "on sp.spn_id = sb.spn_id and sp.criteria_id = 14 "
					+ "and vendor.cred_category_id = sp.value ";
			if (null != spnId) {
				hql = hql + "and sp.spn_id = :spnId ";
			}
		}
		if (null == requirementType || 2 == requirementType) {
			if (null == requirementType) {
				hql = hql + "union ";
			}
			hql = hql
					+ "select distinct(audit.credential_name), audit.expiration_date, audit.notification_type, audit.notification_date, audit.cred_id from audit_cred_expiry_notification audit "
					+ "join vendor_credentials vendor "
					+ "on audit.cred_id = vendor.vendor_cred_id and audit.vendor_id = :vendorId and audit.credential_ind = 2 "
					+

					"join spnet_provider_firm_state spfs "
					+ "on spfs.provider_firm_id = audit.vendor_id and spfs.provider_wf_state = 'PF SPN MEMBER'"
					+

					"join spnet_buyer sb "
					+ "on sb.spn_id = spfs.spn_id and sb.buyer_id = :buyerId "
					+

					"join spnet_approval_criteria sp "
					+ "on sp.spn_id = sb.spn_id and sp.criteria_id in (8,11,27) "
					+ "and ((vendor.cred_category_id = 41 and sp.criteria_id = 11) "
					+ "or (vendor.cred_category_id = 42 and sp.criteria_id = 8) "
					+ "or (vendor.cred_category_id = 43 and sp.criteria_id = 27)) ";
			if (null != spnId) {
				hql = hql + "and sp.spn_id = :spnId ";
			}
		}
		if (null == requirementType || 3 == requirementType) {
			if (null == requirementType) {
				hql = hql + "union ";
			}
			hql = hql
					+ "select distinct(audit.credential_name), audit.expiration_date, audit.notification_type, audit.notification_date, audit.cred_id from audit_cred_expiry_notification audit "
					+ "join resource_credentials resource "
					+ "on audit.cred_id = resource.resource_cred_id and audit.vendor_id = :vendorId and audit.credential_ind = 3 "
					+

					"join spnet_serviceprovider_state spnet "
					+ "on audit.resource_id = spnet.service_provider_id and spnet.provider_wf_state = 'SP SPN APPROVED'"
					+

					"join spnet_buyer sb "
					+ "on sb.spn_id = spnet.spn_id and sb.buyer_id = :buyerId "
					+

					"join spnet_approval_criteria sp "
					+ "on sp.spn_id = sb.spn_id and sp.criteria_id = 17 "
					+ "and resource.cred_category_id = sp.value ";
			if (null != spnId) {
				hql = hql + "and sp.spn_id = :spnId ";
			}
		}

		hql = hql + "order by expiration_date";
		Query query = getEntityManager().createNativeQuery(hql);
		query.setParameter("vendorId", providerFirmId);
		query.setParameter("buyerId", buyerId);
		if (null != spnId) {
			query.setParameter("spnId", spnId);
		}
		try {
			return query.getResultList();

		} catch (Exception e) {
			logger.debug("Exception in ServiceProviderDaoImp.getExpirationDetailsForProvider due to "
					+ e.getMessage());
			throw new DataServiceException(e.getMessage(), e);
		}
	}

	// Code Added for Jira SL-19728
	// For Sending emails specific to NON FUNDED buyer when a
	// provider is approved in a SPN
	// As per new requirements
	/**
	 * Method added to query the email template specific to NON FUNDED BUYER
	 * 
	 * @param buyerId
	 *            ,templateId
	 * @return
	 * @throws DataServiceException
	 */

	public Integer getEmailTemplateSpecificToBuyer(Integer buyerId,
			Integer templateId) throws DataServiceException {
		Integer tempId = 0;
		String hql = "SELECT original_template_id FROM buyer_email_mapping where buyer_id=:buyerId and template_id=:templateId";
		try {
			Query q = getEntityManager().createNativeQuery(hql);
			q.setParameter(Constants.BUYER_ID, buyerId);
			q.setParameter(Constants.TEMPLATE_ID, templateId);
			tempId = (Integer) q.getSingleResult();
			return tempId;
		} catch (Exception e) {
			logger.debug("Exception in getEmailTemplateSpecificToBuyer" + e);
			throw new DataServiceException(e.getMessage(), e);
		}

	}

}
