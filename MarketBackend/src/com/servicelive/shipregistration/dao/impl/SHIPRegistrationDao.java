package com.servicelive.shipregistration.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.vo.provider.ProviderRegistrationVO;
import com.sears.os.dao.impl.ABaseImplDao;
import com.servicelive.shipregistration.dao.ISHIPRegistrationDao;

public class SHIPRegistrationDao extends ABaseImplDao implements
		ISHIPRegistrationDao {
	public Integer getSLIndustry(String productDesc) throws DataServiceException{

		Integer slIndustryId = null;
		slIndustryId = (Integer) queryForObject(
					"shipIndustry.query", productDesc);
		return slIndustryId;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getUsernameLike(String username) throws DataServiceException{

		List<String> names = new ArrayList<String>();
		names = (List<String>) queryForList(
					"user_profile_like.query", username);
		return names;
	}
	public String getUserName(String username) throws DataServiceException {
		String isExistUserName=(String) queryForObject("user_profile_not_like.query",username);
		return isExistUserName;
	}
	public void insertSubContractorInfo(
			ProviderRegistrationVO providerRegistrationVO)throws DataServiceException
	{
		insert("shipContractor.insert", providerRegistrationVO);
		
	}
	public Integer getVendorIdForSubContractorForResource(Integer subContractorId)throws DataServiceException{
		Integer vendorId = null;
		try{
		vendorId = (Integer) queryForObject("slVendorIdForFirm.query", subContractorId);
		}
		catch (Exception e) {
			logger.info("Query Returned too many results"+ e.getMessage());
			return vendorId;
		}
		return vendorId;
	}
	public List<Integer> getVendorIdForProviderCrewId(ProviderRegistrationVO registrationVO) throws DataServiceException {
		List<Integer> vendorIdList = null;
		try{
			vendorIdList=queryForList("slvendorIdForProviderCrewId.query", registrationVO);
		}
		catch (Exception e) {
			logger.info("Query Returned unexpected results"+ e.getMessage());
			return vendorIdList;
		}
		return vendorIdList;
	}
	public Integer getVendorIdForFirm(Integer subContractorId)throws DataServiceException{
		Integer vendorId = null;
		try{
			vendorId = (Integer) queryForObject("slVendorIdForFirm.query", subContractorId);
		   }
		
		catch(Exception e){
			logger.info("Someother exception than no result"+ e.getMessage());
		}
		return vendorId;
	}

	public String getValidBusinessStateCd(String businessState)
			throws DataServiceException {
		String stateCd=null;
		try{
			stateCd=(String) queryForObject("getStateCodeForFirm.query",businessState);
		}
		catch(Exception e){
			logger.info("Someother exception than no result"+ e.getMessage());
		}
		return stateCd;
	}
	public void insertInsuranceTypes(CredentialProfile credentialProfile) throws DataServiceException {
		try {
			insert("insuranceTypesForFirm.insert", credentialProfile);
					
		} catch (Exception ex) {
			logger.info("General Exception @CredentialDaoImpl.insertInsuranceTypes() due to"+ StackTraceHelper.getStackTrace(ex));
                               }
	}

	public void updateInsuranceInd(Integer vendorId, int i)throws DataServiceException {
		try{
			if(i==41){
				logger.info("updating insurance ind for general liability");
				update("updateGeneralLiabilityInd.update",vendorId);
			}else if(i==42){
				logger.info("updating insurance ind for Auto liability");
				update("updateAutoLiabilityInd.update",vendorId);
			}else if(i==43){
				logger.info("updating insurance ind for Workmans Compensation");
				update("updateWorkmansCompensation.update",vendorId);
			}
		}catch (Exception e) {
			logger.info("Exception in updating insurance ind in vendor_hdr"+ e.getMessage());
		}
		
	}

	

	
}
