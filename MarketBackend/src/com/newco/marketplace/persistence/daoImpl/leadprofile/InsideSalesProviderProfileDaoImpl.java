package com.newco.marketplace.persistence.daoImpl.leadprofile;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.leads.AddLeadRequest;
import com.newco.marketplace.api.leads.LoginInsideSalesRequest;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.persistence.iDao.leadprofile.ILeadDao;
import com.newco.marketplace.vo.leadprofile.LeadInsertFilterResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileBillingRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileBillingResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProjectVO;
import com.newco.marketplace.vo.leadprofile.LeadServicePriceVO;
import com.newco.marketplace.vo.leadprofile.ProjectDetailsList;
import com.servicelive.InsideSalesRestClient;
import com.servicelive.SimpleRestClient;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;



public class InsideSalesProviderProfileDaoImpl implements ILeadDao {
	
	private static final Logger LOGGER = Logger.getLogger(InsideSalesProviderProfileDaoImpl.class);

	private String isApiBaseUrl;

	
	public String getIsApiBaseUrl() {
		return isApiBaseUrl;
	}


	public void setIsApiBaseUrl(String isApiBaseUrl) {
		this.isApiBaseUrl = isApiBaseUrl;
	}


	public LeadProfileCreationResponseVO createLeadProfile(
			LeadProfileCreationRequestVO leadProfileCreationRequestVO)
			throws Exception {
		// TODO Auto-generated method stub
		// Logic to call the CRM APIs as per the specs shared goes here
		return null;
	}
	  
	
	public LeadProfileBillingResponseVO createLeadProfileBilling(
			LeadProfileBillingRequestVO leadProfileBillingRequestVO)
			throws Exception {
		// TODO Auto-generated method stub
		// Logic to call the CRM APIs as per the specs shared goes here
		return null;
	}



	public LeadProfileCreationRequestVO getProfileDetails(String vendorId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}



	public ProjectDetailsList getleadProjectTypeAndRates(
			String vendorId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	public void updateFilterSetId(Integer filterSetId, String partnerId, String filterType,String wfStates)
			throws DataServiceException {
		// TODO Auto-generated method stub
		
	}



	public boolean updateLeadProfile(LeadProfileCreationRequestVO leadProfileCreationRequestVO)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return true;
	}



	public void updatePartnerId(String partnerId, String vendorId,String wfStatus)
			throws DataServiceException {
		// TODO Auto-generated method stub
		
	}



	public LeadServicePriceVO validateProjectType(String providerFirmId,
			String projectType) throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}



	public boolean validateProviderFirmLeadEligibility(String providerFirmId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	

	public String createInsideSalesProfile(String request,String insideSalesLoginRequestJSON) throws DataServiceException {
		InsideSalesRestClient insideSalesRestClient = new InsideSalesRestClient();
		String isResponse = null; 
		try {
			isResponse = insideSalesRestClient.createInsideSalesProfile(insideSalesLoginRequestJSON, request,isApiBaseUrl);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info("IS Response reason Code " + isResponse);
		return isResponse;
	}

	
	public LeadInsertFilterResponseVO insertFilterSet(
			LeadProfileCreationRequestVO leadProfileCreationRequestVO,Map<String, List<String>> projectLists)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public String validateLeadPartnerId(String firmId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean validateVendor(String vendorId) throws DataServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	public LeadServicePriceVO getPriceDetails(LeadProjectVO projectVO) throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	public void updateCrmStatus(String crmStatus,Integer providerFirmId)throws DataServiceException
	{
	}

	public void updateLMSError(String vendorId, String errors)
			throws DataServiceException {
		// TODO Auto-generated method stub
		
	}

	public void updateLMSStatus(String vendorId, String status,
			String statusReason) throws DataServiceException {
		// TODO Auto-generated method stub
		
	}
	public LeadProfileCreationRequestVO getFilterDetails(String providerFirmId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean validateIfFilterPresent(String providerFirmId)
			throws DataServiceException {
			// TODO Auto-generated method stub
		return false;
	}
	public HashMap<String, Object> getPackagePriceDetails(String packageId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	public String getVendorIdFromPartnerId(String partnerId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public LeadProfileCreationRequestVO validateFilterPresent(String vendorId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getLookUpvalues(String commaSeperatedIds, Integer type)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteFilterForVendor(String vendorId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		
	}
	
	public void updateVendorStatus(Map<Integer,Integer> vendorIdMap)throws DataServiceException{
		
	}

	/**
	 * @return
	 * @throws DataServiceException
	 */
	public boolean getInsideSalesApiInvocationSwitch()
			throws DataServiceException {
		return false;
	}


	public int doCrmRegistration(String request) throws DataServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void updateInsideSalesResponse(String isResponse,
			Integer providerFirmId,Integer crmStatus) throws DataServiceException{
	}
	public String getState(String stateCd)
	{
		return null;	
	}

	

	
	
}
