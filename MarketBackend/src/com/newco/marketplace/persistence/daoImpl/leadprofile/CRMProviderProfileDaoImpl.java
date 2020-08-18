package com.newco.marketplace.persistence.daoImpl.leadprofile;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.iDao.leadprofile.ILeadDao;
import com.newco.marketplace.vo.leadprofile.LeadInsertFilterResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileBillingRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileBillingResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProjectVO;
import com.newco.marketplace.vo.leadprofile.LeadServicePriceVO;
import com.newco.marketplace.vo.leadprofile.ProjectDetailsList;
import com.servicelive.SimpleRestClient;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;



public class CRMProviderProfileDaoImpl implements ILeadDao {
	private static final Logger LOGGER = Logger.getLogger(CRMProviderProfileDaoImpl.class);
	private String crmApiBaseUrl;
	private String crmApiUserName;
	private String crmApiPassword;
	public LeadProfileCreationResponseVO createLeadProfile(
			LeadProfileCreationRequestVO leadProfileCreationRequestVO)
			throws Exception {
		// TODO Auto-generated method stub
		// Logic to call the CRM APIs as per the specs shared goes here
		return null;
	}
	  //TODO- uncommnet once CRM details are finalized SL 16943 Method to call CRM API
	/*public CreateProviderAccountResponse doCrmRegistration(CreateProviderAccountRequest request)
	{
		SimpleRestClient crmSimpleRestClient=new SimpleRestClient(crmApiBaseUrl,crmApiUserName,crmApiPassword);
		String serializedProviderRegVO=serializeRequest(request,CreateProviderAccountRequest.class);
	    String response =  crmSimpleRestClient.post(serializedProviderRegVO);
	    CreateProviderAccountResponse prvRegResponse = new CreateProviderAccountResponse();
	    prvRegResponse = (CreateProviderAccountResponse) deserializeResponse(response,CreateProviderAccountResponse.class);
		return prvRegResponse;
	}*/

	@SuppressWarnings("rawtypes")
	private Object deserializeResponse( String objectXml , Class<?> clazz) {
		//return this.<IAPIResponse>deserializeRequest(objectXml,c);
		Object obj = new Object();
		try {		
			XStream xstream = getXstream(clazz);
			obj = (Object) xstream.fromXML(objectXml);
			LOGGER.info("Exiting deserializeResponse()");		

		} catch (Exception e) {
			LOGGER.error(e);
		}
		return obj;
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
	private String serializeRequest(Object request, Class<?> classz){
		XStream xstream = this.getXstream(classz);		
		//String requestXml  = xstream.toXML(request).toString();
		return xstream.toXML(request).toString();
		/*try {
			OutputStream outputStream = new ByteArrayOutputStream();
		    JAXBContext ctx = JAXBContext.newInstance(classz);
		    Marshaller marshaller = ctx.createMarshaller();
		    marshaller.marshal(request, outputStream);
		    return outputStream.toString();
		}
		catch (JAXBException e) {
		    LOGGER.error(e);
		}
		return null;*/
	}
	private XStream getXstream(Class<?> classz) {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		xstream.registerConverter(new DateConverter("yyyy-MM-dd", new String[0]));
		xstream.addDefaultImplementation(java.sql.Date.class, java.util.Date.class);
		xstream.processAnnotations(classz);
		return xstream;
	}
	public String getCrmApiBaseUrl() {
		return crmApiBaseUrl;
	}

	public void setCrmApiBaseUrl(String crmApiBaseUrl) {
		this.crmApiBaseUrl = crmApiBaseUrl;
	}

	public String getCrmApiUserName() {
		return crmApiUserName;
	}

	public void setCrmApiUserName(String crmApiUserName) {
		this.crmApiUserName = crmApiUserName;
	}

	public String getCrmApiPassword() {
		return crmApiPassword;
	}

	public void setCrmApiPassword(String crmApiPassword) {
		this.crmApiPassword = crmApiPassword;
	}

	public int doCrmRegistration(String request) throws DataServiceException {
		SimpleRestClient client = null;
		int responseCode = 0;
		try {
			client = new SimpleRestClient(crmApiBaseUrl,crmApiUserName,crmApiPassword,true);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		responseCode = client.post(request);
		LOGGER.info("CRM Response reason Code " + responseCode);
		return responseCode;
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

	public String createInsideSalesProfile(String insideSalesRequestJSON, String insideSalesLoginRequestJSON) {
		// TODO Auto-generated method stub
		return null;
	}
	public void updateInsideSalesResponse(String isResponse,
			Integer providerFirmId,Integer crmStatus) throws DataServiceException{
	}
	public String getState(String stateCd)
	{
		return null;	
	}
	

	
	
	
}
