package com.newco.marketplace.business.businessImpl.mobile;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


import com.newco.marketplace.api.mobile.beans.sodetails.AddOn;
import com.newco.marketplace.api.mobile.beans.sodetails.AddOnList;
import com.newco.marketplace.api.mobile.beans.sodetails.AddOns;
import com.newco.marketplace.api.mobile.beans.sodetails.AddonPayment;
import com.newco.marketplace.api.mobile.beans.sodetails.CompletionDetails;
import com.newco.marketplace.api.mobile.beans.sodetails.CompletionDocuments;
import com.newco.marketplace.api.mobile.beans.sodetails.CompletionStatus;
import com.newco.marketplace.api.mobile.beans.sodetails.Document;
import com.newco.marketplace.api.mobile.beans.sodetails.DocumentType;
import com.newco.marketplace.api.mobile.beans.sodetails.Documents;
import com.newco.marketplace.api.mobile.beans.sodetails.Note;
import com.newco.marketplace.api.mobile.beans.sodetails.Part;
import com.newco.marketplace.api.mobile.beans.sodetails.PartCoverage;
import com.newco.marketplace.api.mobile.beans.sodetails.PartTrack;
import com.newco.marketplace.api.mobile.beans.sodetails.PermitAddon;
import com.newco.marketplace.api.mobile.beans.sodetails.PermitAddons;
import com.newco.marketplace.api.mobile.beans.sodetails.PermitTask;
import com.newco.marketplace.api.mobile.beans.sodetails.PermitTasks;
import com.newco.marketplace.api.mobile.beans.sodetails.Permits;
import com.newco.marketplace.api.mobile.beans.sodetails.PickUpLocation;
import com.newco.marketplace.api.mobile.beans.sodetails.Price;
import com.newco.marketplace.api.mobile.beans.sodetails.ProviderReference;
import com.newco.marketplace.api.mobile.beans.sodetails.RetrieveServiceOrderMobile;
import com.newco.marketplace.api.mobile.beans.sodetails.ServiceOrderDetails;
import com.newco.marketplace.api.mobile.beans.sodetails.SignatureDocument;
import com.newco.marketplace.api.mobile.beans.sodetails.SignatureDocuments;
import com.newco.marketplace.api.mobile.beans.sodetails.SupportNote;
import com.newco.marketplace.api.mobile.beans.sodetails.Task;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.InvoicePart;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.InvoiceParts;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.LatestTripDetails;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.RetrieveSOCompletionDetailsMobile;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.RetrieveSODetailsMobile;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.SOTrip;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOManagementBO;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.persistence.iDao.lookup.LookupDao;
import com.newco.marketplace.persistence.iDao.mobile.IMobileSOManagementDao;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.util.Cryptography128;
import com.newco.marketplace.util.EsapiUtility;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.utils.TimezoneMapper;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.vo.mobile.FeedbackVO;
import com.newco.marketplace.vo.mobile.ProviderSOSearchVO;
import com.newco.marketplace.vo.mobile.SOStatusVO;
import com.servicelive.common.properties.IApplicationProperties;


public class MobileSOManagementBOImpl implements IMobileSOManagementBO {
	private IMobileSOManagementDao mobileSOManagementDao;
	private IApplicationProperties applicationProperties;
	private LookupDao lookupDao;
	private static final Logger logger = Logger
			.getLogger(MobileSOManagementBOImpl.class);

	public static final String EMPTY_STR = "";
	public static final String SPACE = " ";
	public static final String UNDERSCORE = "_";
	public final static String RANGE_DATE = "2";
	public final static String FIXED_DATE = "1";
	private static final String TIME_STAMP_FORMAT_IN_DB_TWELVE_HOUR = "yyyy-MM-dd hh:mm";
	private static final String DMY_DATE_FORMAT = "yyyy-MM-dd";
	public static final Integer ONE=1;
	private Cryptography cryptography;
	private Cryptography128 cryptography128;

	public Cryptography128 getCryptography128() {
		return cryptography128;
	}

	public void setCryptography128(Cryptography128 cryptography128) {
		this.cryptography128 = cryptography128;
	}

	public Cryptography getCryptography() {
		return cryptography;
	}

	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}
	/**
	 * Get the Service orders for the provider based on the criteria
	 * 
	 * @param params
	 * @return List<ProviderSOSearchVO> resultList
	 * @throws BusinessServiceException
	 */
	public List<ProviderSOSearchVO> getProviderSOSearchList(
			HashMap<String, Object> params) throws BusinessServiceException {
		List<ProviderSOSearchVO> resultList = new ArrayList<ProviderSOSearchVO>();
		try {
			SimpleDateFormat dmyFormat = new SimpleDateFormat(DMY_DATE_FORMAT);
			resultList = mobileSOManagementDao.getProviderSOSearchList(params);
			if (!resultList.isEmpty()) {
				for (ProviderSOSearchVO result : resultList) {
					final HashMap<String, Object> serviceStartDateTime = TimeUtils
							.convertGMTToGivenTimeZone(
									result.getServiceDate1(),
									result.getServiceTime1(),
									result.getServiceLocationTimeZone());
					String sDate = dmyFormat.format((Date) serviceStartDateTime
							.get(OrderConstants.GMT_DATE));
					result.setServiceStartDate(sDate);
					result.setServiceTime1((String) serviceStartDateTime
							.get(OrderConstants.GMT_TIME));

					if (result.getDateType().equals(
							Integer.parseInt(FIXED_DATE))) {
						final HashMap<String, Object> serviceEndDateTime = TimeUtils
								.convertGMTToGivenTimeZone(
										result.getServiceDate1(),
										result.getServiceTime2(),
										result.getServiceLocationTimeZone());
						result.setServiceTime2((String) serviceEndDateTime
								.get(OrderConstants.GMT_TIME));
					} else {
						final HashMap<String, Object> serviceEndDateTime = TimeUtils
								.convertGMTToGivenTimeZone(
										result.getServiceDate2(),
										result.getServiceTime2(),
										result.getServiceLocationTimeZone());
						String eDate = dmyFormat
								.format((Date) serviceEndDateTime
										.get(OrderConstants.GMT_DATE));
						result.setServiceEndDate(eDate);
						result.setServiceTime2((String) serviceEndDateTime
								.get(OrderConstants.GMT_TIME));
					}

					String mdy = dmyFormat.format((Date) serviceStartDateTime
							.get(OrderConstants.GMT_DATE));

					String serviceStartDate = mdy + "T"
							+ result.getServiceTime1();
					String timeZone = getTimeZoneFromDate(result.getZip(),
							serviceStartDate,
							result.getServiceLocationTimeZone());
					result.setServiceLocationTimeZone(timeZone);
				}
			}

		} catch (Exception ex) {
			logger.error("Exception Occured in MobileSOManagementBO-->getProviderSOSearchList()");
			throw new BusinessServiceException(ex.getMessage());
		}
		return resultList;
	}

	/**
	 * Get the count of Service orders for the provider based on the criteria
	 * 
	 * @param params
	 * @return count
	 * @throws BusinessServiceException
	 */
	public Integer getProviderSOSearchCount(HashMap<String, Object> params)
			throws BusinessServiceException {
		Integer count = 0;
		try {
			count = (Integer) mobileSOManagementDao
					.getProviderSOSearchCount(params);
		} catch (Exception ex) {
			logger.error("Exception Occured in MobileSOManagementBO-->getProviderSOSearchCount()");
			throw new BusinessServiceException(ex.getMessage());
		}
		return count;
	}

	/**
	 * check if the provided status is valid or not
	 * 
	 * @param statusString
	 * @return List<Integer> wfStateIds
	 * @throws BusinessServiceException
	 */
	public List<Integer> validateSOStatus(String statusString)
			throws BusinessServiceException {
		List<Integer> wfStateIds = new ArrayList<Integer>();
		List<SOStatusVO> statusVO = new ArrayList<SOStatusVO>();
		try {
			// fetch the available statuses with their status codes from the
			// database,
			// which are available for a Service Order.
			statusVO = mobileSOManagementDao.getSOStatus();

			// If the status received from request is empty, make the default
			// search status as 'Active'
			// set the 'wfStateIds' to that of 'Active'
			if (StringUtils.isBlank(statusString)) {
				for (SOStatusVO soStatusVO : statusVO) {
					if (soStatusVO.getStatusString().equals(
							PublicAPIConstant.SO_STATUS_ACTIVE)) {
						wfStateIds.add(soStatusVO.getStatusId());
					}
				}
			} else {
				// To convert the underscore separated status from the request to a
				// list
				// of
				// status

				List<String> statusStringList = new ArrayList<String>(
						Arrays.asList(statusString.split(UNDERSCORE)));

				for (String status : statusStringList) {
					for (SOStatusVO soStatusVO : statusVO) {
						if (status.equals(soStatusVO.getStatusString()))
							wfStateIds.add(soStatusVO.getStatusId());
					}
				}

				// If any one of the status passed is invalid then clear the
				// list and return empty list
				if (wfStateIds.size() != statusStringList.size()) {
					wfStateIds.clear();
				}

			}
		} catch (Exception ex) {
			logger.error("Exception Occured in MobileSOManagementBO-->validateSOStatus()");
			throw new BusinessServiceException(ex.getMessage());
		}

		return wfStateIds;
	}

	/**
	 * Get the response based on the response filter
	 * 
	 * @param Map<String, Object> param
	 * @param responseFilters
	 * @return RetrieveServiceOrderMobile
	 * @throws BusinessServiceException
	 */
	
	
	public RetrieveServiceOrderMobile getServiceOrderDetails(
			Map<String, Object> param, String responseFilter)
			throws BusinessServiceException {
		RetrieveServiceOrderMobile serviceOrderResponse =new RetrieveServiceOrderMobile();
		CompletionDetails completionDetails = null;
		ServiceOrderDetails serviceOrderDetails = null;
		try {
			if (responseFilter.equalsIgnoreCase(MPConstants.SO_DETAILS)) {
				// calling function to get serviceOrderDetails
				serviceOrderDetails = mobileSOManagementDao
						.getServiceOrderDetails(param);
				logger.info(
						"Inside Bo before mapping -->"+serviceOrderDetails);
				if (null != serviceOrderDetails) {
					
					serviceOrderDetails=mapServiceOrderDetails(serviceOrderDetails);	
					logger.info(
							"Inside Boafter mapping -->"+serviceOrderDetails);
					serviceOrderResponse.setSoDetails(serviceOrderDetails);
					logger.info(
							"Inside Bo-->"+serviceOrderDetails);
				}
			} else {
				// calling function to get completionDetails
				completionDetails = mobileSOManagementDao
						.getCompletionDetails(param);
				  if (null != completionDetails) {
					  
					completionDetails=mapCompletionDetails(completionDetails);
					serviceOrderResponse.setCompletionDetails(completionDetails);
				}
			}
			
		} catch (DataServiceException ex) {
			logger.error("Exception Occured in MobileSOManagementBO-->getServiceOrderDetails()");
			throw new BusinessServiceException(ex.getMessage());
		}
		return serviceOrderResponse;
	}
	
	

	/**
	 * To check if given firm is authorized to view so
	 * 
	 * @param soId
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */

public boolean isAuthorizedInViewSODetails(String soId,String resourceId) throws BusinessServiceException{
		boolean valid= Boolean.FALSE;
		try {
			valid=mobileSOManagementDao.isAuthorizedInViewSODetails(soId,resourceId);
		} catch (DataServiceException ex) {
			logger.error("Exception Occured in MobileSOManagementBO-->isAuthorizedInViewSODetls()");
			throw new BusinessServiceException(ex.getMessage());
		}
		return valid;
	}
	public IMobileSOManagementDao getMobileSOManagementDao() {
		return mobileSOManagementDao;
	}

	public void setMobileSOManagementDao(
			IMobileSOManagementDao mobileSOManagementDao) {
		this.mobileSOManagementDao = mobileSOManagementDao;
	}

	/**
	 * To check if given provider id is valid or not
	 * 
	 * @param providerId
	 * @return firmId
	 * @throws DataServiceException
	 */
	public Integer validateProviderId(String providerId)
			throws BusinessServiceException {
		Integer firmId;
		try {
			firmId = mobileSOManagementDao.validateProviderId(providerId);
		} catch (Exception e) {
			logger.error("Exception in validating Resource Id "
					+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return firmId;
	}
	
	public boolean isValidProvider(String providerId) throws BusinessServiceException
	{
		// TODO Auto-generated method stub
				boolean valid =  Boolean.FALSE;
				
				try{
					valid = mobileSOManagementDao.isValidProvider(providerId);
					
				}catch(Exception e){
					logger.error("Exception occured in isValidProvider() due to "+e.getMessage());
				}
				return valid;
		
	}
	
	/**
	 * To check if given service order is a valid one or not
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean isValidServiceOrder(String soId) throws BusinessServiceException
	{
		// TODO Auto-generated method stub
		boolean valid =  Boolean.FALSE;
		try{
			valid = mobileSOManagementDao.isValidServiceOrder(soId);
			
		}catch(Exception e){
			logger.error("Exception occured in isValidServiceOrder() due to "+e.getMessage());
		}
		return valid;
	}
	public boolean isValidProviderResource(String providerId) throws BusinessServiceException {
		boolean valid =  Boolean.FALSE;
		try{
			valid = mobileSOManagementDao.isValidProviderResource(providerId);
			
		}catch(Exception e){
			logger.error("Exception occured in isValidProviderResource() due to "+e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return valid;
    }
	
	/**
	 * To check if provider can upload document in the current status of service order
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getValidServiceOrderWfStatus(String soId) throws BusinessServiceException
	{
		// TODO Auto-generated method stub
		Integer wfStatus = null;
		try{
			
			wfStatus = mobileSOManagementDao.getValidServiceOrderWfStatus(soId);
			
		}catch(Exception e){
			logger.error("Exception occured in getValidServiceOrderWfStatus() due to "+e.getMessage());
		}
		return wfStatus;
		
	}

	public String getDocumentPath(String documentId)throws BusinessServiceException {
		String filePath="";
		try{
			filePath=mobileSOManagementDao.getDocumentPath(documentId);
		}catch(DataServiceException e){
			logger.info("Exception in getting file path"+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return filePath;
	}
	/**
	 * Gets the time zone (String) of the service location based on the day
	 * light saving.
	 * 
	 * @param zipCode
	 *            :
	 * @param serviceStartDate
	 *            : yyyy-MM-ddThh:mm should be the format
	 * @return timeZone
	 * */
	private String getTimeZoneFromDate(String zipCode, String serviceStartDate,
			String timeZone) {
		String dlsFlag = EMPTY_STR;
		String resultTimeZone = timeZone;
		String strArr[] = serviceStartDate.split("T");
		String servDate = strArr[0];
		String servTime = strArr[1];
		try {
			dlsFlag =getLookupDao().getDaylightSavingsFlg(zipCode);
		} catch (DataServiceException e1) {
			logger.info("Exception in getDaylightSavingsFlg"+ e1.getMessage());
			dlsFlag = "N";
		}
		if ("Y".equals(dlsFlag)) {
			TimeZone tz = TimeZone.getTimeZone(timeZone);
			Timestamp timeStampDate = null;
			if (StringUtils.isNotBlank(servDate)
					&& StringUtils.isNotBlank(servTime)) {
				Date date = stringToDate(serviceStartDate.replace("T", SPACE),
						TIME_STAMP_FORMAT_IN_DB_TWELVE_HOUR);
				timeStampDate = new Timestamp(date.getTime());
			}
			if (null != timeStampDate) {
				boolean isDLSActive = tz.inDaylightTime(timeStampDate);
				resultTimeZone = isDLSActive ? TimezoneMapper
						.getDSTTimezone(timeZone) : TimezoneMapper
						.getStandardTimezone(timeZone);
			}
		} else {
			resultTimeZone = TimezoneMapper.getStandardTimezone(timeZone);
		}
		return resultTimeZone;
	}
	/**
	 * Converts String to Date using the supplied Format.
	 * 
	 * @param strDate
	 *            : Date in String
	 * @param format
	 *            : Format of the source date.
	 * @return : Date : Null will be returned for any parse exception
	 * */
	private Date stringToDate(String strDate, String format) {
		DateFormat formatter;
		Date date = null;
		formatter = new SimpleDateFormat(format);
		try {
			date = (Date) formatter.parse(strDate);
		} catch (ParseException e) {
			logger.info("Exception in stringToDate"+ e.getMessage());
		}
		return date;
	}

	public boolean isAuthorizedToViewDoc(Map<String, String> param) throws BusinessServiceException {
		boolean isValid=false;
		try{
			isValid=mobileSOManagementDao.isAuthorizedToViewDoc(param);
		}catch(DataServiceException e){
			logger.info("Exception in validating document association"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return isValid;
	}
     public boolean isValidDocument(String documentId)throws BusinessServiceException {
    	 boolean isValid=false;
    	 try{
 			isValid=mobileSOManagementDao.isValidDocument(documentId);
 		}catch(DataServiceException e){
 			logger.info("Exception in validating document"+ e.getMessage());
 			throw new BusinessServiceException(e.getMessage());
 		}
 		return isValid;
	}
	
	//method for mapping fetched service order object
	private ServiceOrderDetails mapServiceOrderDetails(
			ServiceOrderDetails serviceOrderDetails) {
		logger.info("mapServiceOrderDetails");
		
		
		// setting MaxTimeWindow and MinTimeWindow from retrieved buyer object
		// to appointment object	
		if (null != serviceOrderDetails.getBuyer()) {
			if (null != serviceOrderDetails.getBuyer().getMaxTimeWindow()) {
				serviceOrderDetails.getAppointment().setMaxTimeWindow(
						serviceOrderDetails.getBuyer().getMaxTimeWindow());
			}
			if (null != serviceOrderDetails.getBuyer().getMinTimeWindow()) {
				serviceOrderDetails.getAppointment().setMinTimeWindow(
						serviceOrderDetails.getBuyer().getMinTimeWindow());
			}

			//format Buyer phone numbers
			String buyerPrimaryPhone=serviceOrderDetails.getBuyer().getPrimaryphone();
			serviceOrderDetails.getBuyer().setPrimaryphone(formatPhoneNumber(buyerPrimaryPhone));
			String buyerAltPhone = serviceOrderDetails.getBuyer().getAlternatePhone();
			serviceOrderDetails.getBuyer().setAlternatePhone(formatPhoneNumber(buyerAltPhone));
			String fax = serviceOrderDetails.getBuyer().getFax();
			serviceOrderDetails.getBuyer().setFax(formatPhoneNumber(fax));
		}
		//-----------------------------------------------
       //--> Finished Formatting appointment details
		
		//-- >Formatting Service Location and Alternate service Location
		
         //for getting service location phone number
         //defining map in order to pass soId and contactId to the query
		Map<String, String> param = new HashMap<String, String>();
		param.put("soid", serviceOrderDetails.getOrderDetails().getSoId());
		String primaryPhone = EMPTY_STR;
		String alternatePhone = EMPTY_STR;
		// for getting primary phone number and alternate phone number
		if (null != serviceOrderDetails.getServiceLocation()
				&& null != serviceOrderDetails.getServiceLocation()
						.getSoContactId()) {
			// putting so contactId in the map
			param.put("socontactid", serviceOrderDetails.getServiceLocation()
					.getSoContactId());
			// calling common method for getting primary phone number and
			// alternate phone number
			primaryPhone = getContactPrimaryPhone(param);
			// setting retrieved primary phone number and alternate phone number
			// into
			// serviceOrderDetails object
			alternatePhone = getContactAlternatePhone(param);
			serviceOrderDetails.getServiceLocation().setPrimaryPhone(primaryPhone);
			serviceOrderDetails.getServiceLocation().setAlternatePhone(alternatePhone);
		}
		// for getting service location alternate phone number
		if (null != serviceOrderDetails.getAlternateServiceLocation()
				&& null != serviceOrderDetails.getAlternateServiceLocation()
						.getSoContactId()) {

			// putting so contactId in the map
			param.remove("socontactid");
			param.put("socontactid", serviceOrderDetails
					.getAlternateServiceLocation().getSoContactId());
			// calling common method for getting primary phone number and
			// alternate phone number
			primaryPhone = getContactPrimaryPhone(param);
			alternatePhone = getContactAlternatePhone(param);
			// setting retrieved primary phone number and alternate phone number
			// into
			// serviceOrderDetails object
			serviceOrderDetails.getAlternateServiceLocation()
					.setPrimaryPhone(primaryPhone);
			serviceOrderDetails.getAlternateServiceLocation()
					.setAlternatePhone(alternatePhone);
		}
		//-----------------------------------------------
		//--> Finishing Formatting Service Location and Alternate service Location
	
		//--> Formatting BuyerReferences
		
		// checking BuyerReferences and setting BuyerRefPresentInd appropriately
		if (null != serviceOrderDetails.getBuyerReferences()
				&& null != serviceOrderDetails.getBuyerReferences()
						.getBuyerReference()
				&& serviceOrderDetails.getBuyerReferences().getBuyerReference()
						.size() != 0) {
			serviceOrderDetails.setBuyerRefPresentInd(MPConstants.YES);
		} else {
			serviceOrderDetails.setBuyerRefPresentInd(MPConstants.NO);
		}
		//-----------------------------------------------
		//--> Finished Formatting BuyerReferences
		
		//-->Formatting document types
		//iterating fetched Documents and setting  document types as empty if
		//not an allowed document type		
		if (null != serviceOrderDetails.getDocuments()
				&& null != serviceOrderDetails.getDocuments().getDocument()
				&& serviceOrderDetails.getDocuments().getDocument().size() != 0&& null!=serviceOrderDetails.getBuyer().getBuyerUserId()) {
			//List<Document> docList=formatDocumentTypes(serviceOrderDetails.getDocuments().getDocument(),serviceOrderDetails.getBuyer().getBuyerUserId());
			// serviceOrderDetails.getDocuments().setDocument(docList);
		}
		//-----------------------------------------------
		//-->Finished Formatting document types
		//-- > Formatting Part details
		//setting Count of parts
		
		if (null != serviceOrderDetails.getParts()
				&& null != serviceOrderDetails.getParts().getPart()) {
			serviceOrderDetails.getParts().setCountofParts(
					serviceOrderDetails.getParts().getPart().size());
		}

		//format provider phone numbers
		if(null!=serviceOrderDetails.getProvider()){
		String providerPrimaryPhone=serviceOrderDetails.getProvider().getProviderPrimaryPhone();
		serviceOrderDetails.getProvider().setProviderPrimaryPhone(formatPhoneNumber(providerPrimaryPhone));
		String providerAltPhone = serviceOrderDetails.getProvider().getProviderAltPhone();
		serviceOrderDetails.getProvider().setProviderAltPhone(formatPhoneNumber(providerAltPhone));
		String firmPrimaryPhone=serviceOrderDetails.getProvider().getFirmPrimaryPhone();
		serviceOrderDetails.getProvider().setFirmPrimaryPhone(formatPhoneNumber(firmPrimaryPhone));
		String firmAltPhone=serviceOrderDetails.getProvider().getFirmAltPhone();
		serviceOrderDetails.getProvider().setFirmAltPhone(formatPhoneNumber(firmAltPhone));
		String smsNum=serviceOrderDetails.getProvider().getSmsNumber();
		serviceOrderDetails.getProvider().setSmsNumber(smsNum);
		}
		
		if(null!=serviceOrderDetails.getParts()&& null!=serviceOrderDetails.getParts().getPart()){
			List<Part> parts = serviceOrderDetails.getParts().getPart();
			for(Part part : parts){
				PickUpLocation locn = part.getPickupLocation();
				if(null==locn||(null!=locn && (null == locn.getPickupLocationStreet1() || locn.getPickupLocationStreet1().equals(MPConstants.EMPTY_STRING)) && (null == locn.getPickupLocationStreet2() || locn.getPickupLocationStreet2().equals(MPConstants.EMPTY_STRING)) && ( null == locn.getPickupLocationCity() || locn.getPickupLocationCity().equals(MPConstants.EMPTY_STRING)))){
					part.setPickupLocationAvailability(MPConstants.NOT_AVAILABLE);
					part.setPickupLocation(null);
				}else{
					part.setPickupLocationAvailability(MPConstants.AVAILABLE);
				}
			}
		}
		
		// Decode the HTML encoded notes and support notes
		if(null!= serviceOrderDetails.getNotes()){
			for(Note note : serviceOrderDetails.getNotes().getNote()){
				if(StringUtils.isNotEmpty(note.getNoteSubject())){
					String decodedSub = EsapiUtility.getDecodedString(note.getNoteSubject());
					note.setNoteSubject(decodedSub);
				}
				if(StringUtils.isNotEmpty(note.getNoteBody())){
					String decodedBody = EsapiUtility.getDecodedString(note.getNoteBody());
					note.setNoteBody(decodedBody);
				}
			}
		}
		
		// Decode the HTML encoded notes and support notes
		if(null!= serviceOrderDetails.getSupportNotes()){
			for(SupportNote supportNote : serviceOrderDetails.getSupportNotes().getSupportNote()){
				if(StringUtils.isNotEmpty(supportNote.getNoteSubject())){
					String decodedSub = EsapiUtility.getDecodedString(supportNote.getNoteSubject());
					supportNote.setNoteSubject(decodedSub);
				}
				if(StringUtils.isNotEmpty(supportNote.getNote())){
					String decodedBody = EsapiUtility.getDecodedString(supportNote.getNote());
					supportNote.setNote(decodedBody);
				}
			}
		}

		return serviceOrderDetails;
	}
	
	private String formatPhoneNumber(String number){
		String formattedNum = StringUtils.EMPTY;;
		if(null!=number){
			formattedNum = UIUtils.formatPhoneNumber(number.replaceAll("-", ""));
		}
		return formattedNum;
	}

	//method for formatting document type
	private List<Document> formatDocumentTypes(List<Document> documentList,Integer buyerId){
	
		List<DocumentType> documentTypeList=null;
		try {
			documentTypeList = mobileSOManagementDao.documentTypeList(buyerId);
		} catch (DataServiceException e) {
			logger.info("Exception in MobileSOManagementBOImpl.getDocumentTypes() "+e.getMessage());
			return documentList;
		}
		if(null != documentList && null != documentTypeList){
			for(Document doc : documentList){
				int flag = 0;
				for (DocumentType type : documentTypeList){
					if(doc.getDocumentType().equals(type.getDocumentTitle())){
						flag = 1;
						break;
					}
					else{
						continue;
					}
				}
				if(0 == flag){
					doc.setDocumentType("");
				}
			}		
		}		
		return documentList;
	}

	// for mapping completion details
	private CompletionDetails mapCompletionDetails(
			CompletionDetails completionDetails) {
		
		if(!completionDetails.getBuyerId().equals(MPConstants.HSR_BUYER_ID)){
			completionDetails.getPrice().setTotalInvoicePartsMaximumPrice(null);
		}
			
		List<ProviderReference> existingProvRef = new ArrayList<ProviderReference>();
		  try{
				// -->Formatting ProviderReference	
				existingProvRef = mobileSOManagementDao
						.getMandatoryProviderRefs(Integer.parseInt(completionDetails
								.getBuyerId()));
			    }
				catch (Exception e) {
					return completionDetails;
				}
		if (null != existingProvRef && existingProvRef.size() != 0) 
		{
			// checking ProviderReferences and setting ProviderReferenceInd
			// appropriately
			completionDetails.setProviderRefPresentInd(MPConstants.YES);
			if(null!=completionDetails.getProviderReferences())
			{
			List<ProviderReference> editedProvRef = completionDetails
					.getProviderReferences().getProviderReference();

			if ( null != editedProvRef && editedProvRef.size() != 0)
			{
				for (ProviderReference ref1 : editedProvRef) {
					for (ProviderReference ref2 : existingProvRef) { 
						if (ref1.getRefName().equals(ref2.getRefName()))
						{
							ref2.setRefValue(ref1.getRefValue());
						}
					}
				}
			}
			
			}
		}
		else{
			
			completionDetails.setProviderRefPresentInd(MPConstants.NO);
		}
		
		completionDetails.getProviderReferences().setProviderReference(existingProvRef);
		
		if(null!=completionDetails.getAddons()){
			Price price=new Price();
			if(null!=completionDetails.getPrice()){
				price=completionDetails.getPrice();	
			}
			
			//calculate non permit addon price		
			Double totalNonPermitaddonprice = calculateNonPermitAddonPrice(completionDetails.getAddons());
			price.setTotalNonPermitaddonprice(totalNonPermitaddonprice);
			
			//calculate permit addon price
			Double totalPermitaddonprice = calculatePermitaddonprice(completionDetails.getPermits());
			price.setTotalPermitaddonprice(totalPermitaddonprice);
			
			//calculate total prepaid permit price
			Double totalPrepaidPermitPrice = calculatePrepaidPermitPrice(completionDetails.getPermits());
			price.setTotalPrepaidPermitPrice(totalPrepaidPermitPrice);
			
			// total max price is the difference between spend limit and total selling price of the add on
			Double totalPrepaidPermitSellingPrice = calculatePrepaidPermitSettingPrice(completionDetails.getPermits()); 
			
			Double totalLaborMaxPrice =  MoneyUtil.getRoundedMoney(price.getTotalLaborMaximumPrice() - totalPrepaidPermitSellingPrice);
			price.setTotalLaborMaximumPrice(totalLaborMaxPrice);
			
			//rounding off price
			if(null!=price.getTotalPartsMaximumPrice()){
				price.setTotalPartsMaximumPrice(MoneyUtil.getRoundedMoney(price.getTotalPartsMaximumPrice()));
			}
			if(null!=price.getFinalLaborPriceByProvider()){				
				price.setFinalLaborPriceByProvider(MoneyUtil.getRoundedMoney(price.getFinalLaborPriceByProvider()));
			}
			if(null!=price.getFinalPartsPriceByProvider()){				
				price.setFinalPartsPriceByProvider(MoneyUtil.getRoundedMoney(price.getFinalPartsPriceByProvider()));
			}			
			
			completionDetails.setPrice(price);
		}
		return completionDetails;

	}
	
	/**
	 * Map the completion details with trip
	 * @param completionDetails
	 * @return
	 */
	private com.newco.marketplace.api.mobile.beans.sodetails.v2_0.CompletionDetails mapCompletionDetailsWithTrip(
			com.newco.marketplace.api.mobile.beans.sodetails.v2_0.CompletionDetails completionDetails,Map<String, Object> param) {
		
		// R12_0 Sprint 5: SLM 90: Commenting this as it will now be fetched from the DB
		/*if(!completionDetails.getBuyerId().equals(MPConstants.HSR_BUYER_ID)){
			completionDetails.getPrice().setTotalInvoicePartsMaximumPrice(null);
			completionDetails.setCustomerEmailRequired(MPConstants.NO);
		}else{
			completionDetails.setCustomerEmailRequired(MPConstants.YES);
		}*/
		
		//R16_0 : Sl-20728 :Removing the html tags of task comment
		if( null != completionDetails.getTasks().getTask()){
			List <Task> taskResponse = completionDetails.getTasks().getTask();
			if(null != taskResponse && !taskResponse.isEmpty()){
				for(Task taskDetail : taskResponse){		
					//SL-20728 : removing html tags
					taskDetail.setTaskComments(ServiceLiveStringUtils.removeHTML(taskDetail.getTaskComments()));
				}
			}
			completionDetails.getTasks().setTask(taskResponse);
		}	
		
		//R12_0 Sprint 5: SLM 90
		if(null != completionDetails.getBuyerEmailRequiredInd()){
			if(completionDetails.getBuyerEmailRequiredInd()==true){
				completionDetails.setCustomerEmailRequired(MPConstants.YES);
			}
			else{
				completionDetails.setCustomerEmailRequired(MPConstants.NO);
			}
		}
		
		List<ProviderReference> existingProvRef = new ArrayList<ProviderReference>();
		  try{
				// -->Formatting ProviderReference	
				existingProvRef = mobileSOManagementDao
						.getMandatoryProviderRefs(Integer.parseInt(completionDetails
								.getBuyerId()));
		    }
			catch (Exception e) {
				return completionDetails;
			}
		if (null != existingProvRef && existingProvRef.size() != 0) {
			// checking ProviderReferences and setting ProviderReferenceInd
			// appropriately
			completionDetails.setProviderRefPresentInd(MPConstants.YES);
			if(null!=completionDetails.getProviderReferences())
			{
			List<ProviderReference> editedProvRef = completionDetails
					.getProviderReferences().getProviderReference();

			if ( null != editedProvRef && editedProvRef.size() != 0)
			{
				for (ProviderReference ref1 : editedProvRef) {
					for (ProviderReference ref2 : existingProvRef) { 
						if (ref1.getRefName().equals(ref2.getRefName()))
						{
							ref2.setRefValue(ref1.getRefValue());
						}
					}
				}
			}
			
			}
		}else{
			
			completionDetails.setProviderRefPresentInd(MPConstants.NO);
		}
		
		completionDetails.getProviderReferences().setProviderReference(existingProvRef);
		
		if(null!=completionDetails.getAddons()){
			Price price=new Price();
			if(null!=completionDetails.getPrice()){
				price=completionDetails.getPrice();	
			}
			
			//calculate non permit addon price		
			Double totalNonPermitaddonprice = calculateNonPermitAddonPrice(completionDetails.getAddons());
			price.setTotalNonPermitaddonprice(totalNonPermitaddonprice);
			
			//calculate permit addon price
			Double totalPermitaddonprice = calculatePermitaddonprice(completionDetails.getPermits());
			price.setTotalPermitaddonprice(totalPermitaddonprice);
			
			//calculate total prepaid permit price
			Double totalPrepaidPermitPrice = calculatePrepaidPermitPrice(completionDetails.getPermits());
			price.setTotalPrepaidPermitPrice(totalPrepaidPermitPrice);
			
			// total max price is the difference between spend limit and total selling price of the add on
			Double totalPrepaidPermitSellingPrice = calculatePrepaidPermitSettingPrice(completionDetails.getPermits()); 
			
			Double totalLaborMaxPrice =  MoneyUtil.getRoundedMoney(price.getTotalLaborMaximumPrice() - totalPrepaidPermitSellingPrice);
			price.setTotalLaborMaximumPrice(totalLaborMaxPrice);
			
			//rounding off price
			if(null!=price.getTotalPartsMaximumPrice()){
				price.setTotalPartsMaximumPrice(MoneyUtil.getRoundedMoney(price.getTotalPartsMaximumPrice()));
			}
			if(null!=price.getFinalLaborPriceByProvider()){				
				price.setFinalLaborPriceByProvider(MoneyUtil.getRoundedMoney(price.getFinalLaborPriceByProvider()));
			}
			if(null!=price.getFinalPartsPriceByProvider()){				
				price.setFinalPartsPriceByProvider(MoneyUtil.getRoundedMoney(price.getFinalPartsPriceByProvider()));
			}			
			
			completionDetails.setPrice(price);
		}
		
		// Map additional payment details
		
		if(null!=completionDetails.getAddonPayment()){
			if(MPConstants.PAYMENT_TYPE_CHECK.equalsIgnoreCase(completionDetails.getAddonPayment().getPaymentType())){
				completionDetails.getAddonPayment().setPaymentType(MPConstants.CHECK);
			}else{
				completionDetails.getAddonPayment().setPaymentType(MPConstants.CREDIT_CARD);
				if (StringUtils.isNotBlank(completionDetails.getAddonPayment().getMaskedAccNumber())) {
					completionDetails.getAddonPayment().setCcNumber(formatCardNumber(completionDetails.getAddonPayment().getMaskedAccNumber()));
				}
			}
		}
		
		//mapping customer and provider signature status
		SignatureDocuments signatureDocuments = mapSignatureStatuses(completionDetails);
		
		completionDetails.setSignatures(signatureDocuments);
		
		CompletionStatus completionStatus = mapCompletionStatus(completionDetails);
		
		completionDetails.setCompletionStatus(completionStatus);
		
		// Map the invoice parts coverage type based on the custom reference CoverageTypeLabor
		// PA OR SP -- Protection Agreement
		// IW -- In warranty
		// Anything else -- Both 
		
		// Get the custom reference value for CoverageTypeLabor
		
		try{
			String customRefValue = mobileSOManagementDao.
					getCustomReference(param.get("soId").toString(), MPConstants.COVERAGE_TYPE_LABOR);			
			List<PartCoverage> partCovremoveList = new ArrayList<PartCoverage>();
			if(null!=customRefValue && (MPConstants.COVERAGE_TYPE_LABOR_PA.equalsIgnoreCase(customRefValue) 
					|| (MPConstants.COVERAGE_TYPE_LABOR_SP.equalsIgnoreCase(customRefValue)))){
		
				// Remove in-warranty
				if(null!=completionDetails.getInvoiceParts() && null!=completionDetails.getInvoiceParts().getPartCoverage()
						&& completionDetails.getInvoiceParts().getPartCoverage().size()>0){
					List<PartCoverage> partCovList = completionDetails.getInvoiceParts().getPartCoverage();
					for(PartCoverage partCoverage : partCovList){
						if(MPConstants.IN_WARRANTY.equalsIgnoreCase(partCoverage.getPartCoverageValue())){
							partCovremoveList.add(partCoverage);
						}	
					}
				}
				
			}else if(null!=customRefValue && (MPConstants.COVERAGE_TYPE_LABOR_IW.equalsIgnoreCase(customRefValue))){
				
				// Remove protection agreement
				if(null!=completionDetails.getInvoiceParts() && null!=completionDetails.getInvoiceParts().getPartCoverage()
						&& completionDetails.getInvoiceParts().getPartCoverage().size()>0){
					List<PartCoverage> partCovList = completionDetails.getInvoiceParts().getPartCoverage();
					for(PartCoverage partCoverage : partCovList){
						if(MPConstants.PROTECTION_AGREEMENT.equalsIgnoreCase(partCoverage.getPartCoverageValue())){
							partCovremoveList.add(partCoverage);
						}	
					}
				}
			}
			
			if(null!=partCovremoveList && partCovremoveList.size()>0
					&& null!=completionDetails.getInvoiceParts() 
					&& null!=completionDetails.getInvoiceParts().getPartCoverage()
					&& completionDetails.getInvoiceParts().getPartCoverage().size()>0){
				completionDetails.getInvoiceParts().getPartCoverage().removeAll(partCovremoveList);
			}
			
			
		}catch (Exception e) {
			return completionDetails;
		}		
		
		//SL-20673: Edit Completion Details
		LatestTripDetails latestTrip=new LatestTripDetails();
		try{
			latestTrip=mobileSOManagementDao.getLatestTripDetails(param);
			if(null!=latestTrip){
				completionDetails.setLatestTrip(latestTrip);
			}
		}
		catch (Exception e) {
			logger.error("Exception occured in mapCompletionDetailsWithTrip()-->fetching LatestTripDetails due to "+e.getMessage());
			return completionDetails;
		}
		return completionDetails;

	}

	/**
	 * @param completionDetails
	 * @return
	 * 
	 * to map signature statuses
	 */
	private SignatureDocuments mapSignatureStatuses(
			com.newco.marketplace.api.mobile.beans.sodetails.v2_0.CompletionDetails completionDetails) {
		SignatureDocuments signatureDocuments = completionDetails.getSignatures();
		signatureDocuments.setCustomerSignatureStatus(PublicAPIConstant.REQUIRED);
		signatureDocuments.setProviderSignatureStatus(PublicAPIConstant.REQUIRED);
		if(null!=signatureDocuments && null!=signatureDocuments.getSignature()
				&& signatureDocuments.getSignature().size()>0){
			for(SignatureDocument signatureDocument : signatureDocuments.getSignature()){
				if(StringUtils.equalsIgnoreCase(PublicAPIConstant.PROVIDER_SIGNATURE
						,signatureDocument.getDocumentType())){
					signatureDocuments.setProviderSignatureStatus(PublicAPIConstant.COMPLETED);
				}else if(StringUtils.equalsIgnoreCase(PublicAPIConstant.CUSTOMER_SIGNATURE
						,signatureDocument.getDocumentType())){
					signatureDocuments.setCustomerSignatureStatus(PublicAPIConstant.COMPLETED);
				}
			}
			
		}
		return signatureDocuments;
		
	}

	/**
	 * Map the completion status
	 * @param completionDetails
	 * @return
	 */
	private CompletionStatus mapCompletionStatus(
			com.newco.marketplace.api.mobile.beans.sodetails.v2_0.CompletionDetails	completionDetails) {
		CompletionStatus completionStatus = new CompletionStatus();
			
		// resolution comments - Default Required	
		completionStatus.setResolutionComments(PublicAPIConstant.REQUIRED);
		if(!StringUtils.isBlank(completionDetails.getResolutionComments())){
			completionStatus.setResolutionComments(PublicAPIConstant.COMPLETED);
		}
		
		// Photos - Default Not Started
		completionStatus.setPhotos(PublicAPIConstant.NOT_STARTED);
		CompletionDocuments compDocs = completionDetails.getDocuments();
		if(null!=compDocs && null!=compDocs.getDocument() && 
				compDocs.getDocument().size()>0){
			
			// At least one document by the provider - Mark as completed
			for(Document document : compDocs.getDocument()){
				if(StringUtils.equalsIgnoreCase(
					PublicAPIConstant.PROVIDER,document.getUploadedBy())){
					completionStatus.setPhotos(PublicAPIConstant.COMPLETED);
					break;
				}
			}
		}
		
		// Custom References
		// Check if all the mandatory custom references has values
		boolean custRequired = false;
		int mandatoryCount = 0;
		int nonMandatoryCount = 0;
		boolean nonMandatoryWithValue = false;
		if(null!=completionDetails.getProviderReferences() && null!= completionDetails.
				getProviderReferences().getProviderReference() && completionDetails.
				getProviderReferences().getProviderReference().size()>0){
			
			for(ProviderReference reference : completionDetails.getProviderReferences().
					getProviderReference()){
				if(StringUtils.equalsIgnoreCase(reference.getMandatoryInd(),PublicAPIConstant.Y)){
					mandatoryCount++;
					if(StringUtils.isBlank(reference.getRefValue())){
						custRequired = true;
						break;
					}
				}else{
					nonMandatoryCount++;
					if(StringUtils.isNotEmpty(reference.getRefValue())){
						nonMandatoryWithValue=true;
					}
				}
			}
		}
		
		// Custom reference  - Default Not started		
		completionStatus.setCustomReferences(PublicAPIConstant.NOT_STARTED);
		if (mandatoryCount > 0){		
			
			// Mandatory reference and at least one has blank value
			if(custRequired){
				completionStatus.setCustomReferences(PublicAPIConstant.REQUIRED);
			}else{
				// Mandatory reference and all of them has values
				completionStatus.setCustomReferences(PublicAPIConstant.COMPLETED);
			}
		}else if(nonMandatoryCount>0 && nonMandatoryWithValue){ 
			//if at least one non mandatory custom reference got a value
			completionStatus.setCustomReferences(PublicAPIConstant.COMPLETED);
		}
		
		// Permits - Default Not started
		completionStatus.setPermits(PublicAPIConstant.NOT_STARTED);
		Permits permits = completionDetails.getPermits();		
		if(null!=permits && ((null!=permits.getPermitTasks()
				&& null!=permits.getPermitTasks().getPermitTask()
				&& permits.getPermitTasks().getPermitTask().size()>0)
				||(null!=permits.getPermitAddons() 
					&& null!=permits.getPermitAddons().getPermitAddon()
					&& permits.getPermitAddons().getPermitAddon().size()>0))){
			boolean permitTaskRequired = false;
			boolean permitTaskPresent = false;
			
			if(null!=permits.getPermitTasks() && null!=permits.getPermitTasks().getPermitTask()
					&& permits.getPermitTasks().getPermitTask().size() > 0){
				permitTaskPresent=true;
				for(PermitTask permitTask : permits.getPermitTasks().getPermitTask()){
					if(!permitTaskRequired && ((null == permitTask.getFinalPermitPriceByProvider())
							|| StringUtils.isBlank(permitTask.getPermitType()))){
						permitTaskRequired = true;
					}
				}
			}
			
			if(!permitTaskRequired && null!=permits.getPermitAddons() 
					&& null!=permits.getPermitAddons().getPermitAddon()){
				for(PermitAddon permitAddon :permits.getPermitAddons().getPermitAddon()){
					if(!permitTaskRequired && null!=permitAddon.getQty() && permitAddon.getQty().intValue()> 0){
						permitTaskPresent=true;
						if(null==permitAddon.getCustomerCharge()){
							permitTaskRequired = true;
						}
						
 						
					}
				}
			}
			
			if(permitTaskPresent && permitTaskRequired){
				completionStatus.setPermits(PublicAPIConstant.REQUIRED);
			}else if (permitTaskPresent && !permitTaskRequired){
				// Check if the permit document is present.
				// Initializing boolean variable for proof permit required
				
				/*
				 * Change after R12_0_2 PROD Release
				 * Date 04/22/2015
				 * Avoid the validation of permit document.
				 * Completion API will validate the same.
				 */
				completionStatus.setPermits(PublicAPIConstant.COMPLETED);
				
				/*boolean proofOfPermitPresent = false;
				CompletionDocuments completionDocs = completionDetails.getDocuments();
				if(null!=completionDocs && null!=completionDocs.getDocument() && 
						completionDocs.getDocument().size()>0){
					
					for(Document document : completionDocs.getDocument()){
						if(StringUtils.equalsIgnoreCase(PublicAPIConstant.PROOF_OF_PERMIT
								,document.getDocumentType())){
							proofOfPermitPresent = true;
						}
					}
				}
				//setting completion status irrespective of completion documents uploaded or not
				if(proofOfPermitPresent){
					completionStatus.setPermits(PublicAPIConstant.COMPLETED);
				}else{
					completionStatus.setPermits(PublicAPIConstant.REQUIRED);
				}*/
			}
		}
		
		
		// Add-ons : Default not started 
		completionStatus.setAdditionalServices(PublicAPIConstant.NOT_STARTED);			
		AddOns addOns = completionDetails.getAddons();
		if(null!=addOns && null!=addOns.getAddonList() 
				&& null!=addOns.getAddonList().getAddon()
				&& addOns.getAddonList().getAddon().size()>0){
			for(AddOn addOn : addOns.getAddonList().getAddon()){
				if(addOn.getQty() > 0){
					completionStatus.setAdditionalServices(PublicAPIConstant.COMPLETED);
					break;
				}
			}
		}
		
		// Customer payment - Default Not started 
		completionStatus.setCustomerPayment(PublicAPIConstant.NOT_STARTED);
		BigDecimal addonAmt = BigDecimal.ZERO;
		BigDecimal permitTaskAmt = BigDecimal.ZERO;
		BigDecimal permitAddonAmt = BigDecimal.ZERO;
		BigDecimal totalCustCharge = BigDecimal.ZERO;
		
		
		if(null!=addOns && null!=addOns.getAddonList() 
				&& null!=addOns.getAddonList().getAddon()
				&& addOns.getAddonList().getAddon().size()>0){
			for(AddOn addOn : addOns.getAddonList().getAddon()){
				if(addOn.getQty() > 0){
					BigDecimal qty = new BigDecimal(addOn.getQty());
					BigDecimal retailPrice = new BigDecimal(addOn.getCustomerCharge());
					BigDecimal currentAddonAmt = retailPrice.multiply(qty);
					addonAmt = addonAmt.add(currentAddonAmt);
				}
			}
		}
			
		if(null!=permits && null!=permits.getPermitTasks()
				&& null!=permits.getPermitTasks().getPermitTask()
				&& permits.getPermitTasks().getPermitTask().size()>0){
			for(PermitTask permitTask : permits.getPermitTasks().getPermitTask()){
				Double customerPrepaidAmnt = permitTask.getCustPrePaidAmount();
				Double finalProviderPrice = permitTask.getFinalPermitPriceByProvider();
				
				if(finalProviderPrice > customerPrepaidAmnt){
					BigDecimal permitTaskcustCharge = new BigDecimal(finalProviderPrice - customerPrepaidAmnt);
					permitTaskAmt = permitTaskAmt.add(permitTaskcustCharge);
				}
			}
		}

		if(null!=permits && null!=permits.getPermitAddons()
				&& null!=permits.getPermitAddons().getPermitAddon()
				&& permits.getPermitAddons().getPermitAddon().size()>0){
			
			for(PermitAddon permitAddon : permits.getPermitAddons().getPermitAddon()){
				BigDecimal qty = new BigDecimal(permitAddon.getQty());
				BigDecimal retailPrice = new BigDecimal(permitAddon.getCustomerCharge());
				BigDecimal currentAddonAmt = retailPrice.multiply(qty);
				permitAddonAmt = permitAddonAmt.add(currentAddonAmt);
			}
		}
		
		AddonPayment addonPayment = completionDetails.getAddonPayment();
		
		// Sum of customer charge for add on , permit task, permit add on
		totalCustCharge = addonAmt.add(permitTaskAmt).add(permitAddonAmt);
		totalCustCharge = MoneyUtil.getRoundedMoneyBigDecimal(totalCustCharge.doubleValue());
		BigDecimal zero = new BigDecimal("0.00");
				
		if(null!=totalCustCharge && !totalCustCharge.equals(zero) && null==addonPayment){
			completionStatus.setCustomerPayment(PublicAPIConstant.REQUIRED);
		}else if(null!=totalCustCharge && !totalCustCharge.equals(zero) && null!=addonPayment){
			if(null!=addonPayment.getPaymentType() && addonPayment.getPaymentType().equalsIgnoreCase(PublicAPIConstant.CHECK)
					&& StringUtils.isBlank(addonPayment.getCheckNumber())){
				//if payment type is check and no check number is present
				completionStatus.setCustomerPayment(PublicAPIConstant.REQUIRED);
			}else if (null!=addonPayment.getPaymentType() && !(addonPayment.getPaymentType().equalsIgnoreCase(PublicAPIConstant.CHECK))
					&& (StringUtils.isBlank(addonPayment.getMaskedAccNumber())
							||StringUtils.isBlank(addonPayment.getPreAuthNumber())
							||(null==addonPayment.getCardExpireMonth()||0==addonPayment.getCardExpireMonth())
							||(null==addonPayment.getCardExpireYear()||0==addonPayment.getCardExpireYear()))){
				//if payment type is credit card and details like month, year, preauth etc are not present
				completionStatus.setCustomerPayment(PublicAPIConstant.REQUIRED);
			}else if(null==addonPayment.getAmountAuthorized()){
				//if amount authorized is not present
				completionStatus.setCustomerPayment(PublicAPIConstant.REQUIRED);
			}else if(null!=addonPayment.getAmountAuthorized()){
				BigDecimal amntAuthorized = new BigDecimal(addonPayment.getAmountAuthorized());
				//if customer charge is different from amount authorized
				if(null!=totalCustCharge && null!=amntAuthorized && (totalCustCharge.doubleValue() != amntAuthorized.doubleValue())){
					completionStatus.setCustomerPayment(PublicAPIConstant.REQUIRED);
				}else{
					completionStatus.setCustomerPayment(PublicAPIConstant.COMPLETED);
				}
			}
		}
		
			
		
		// Parts - Default - Nothing
		InvoiceParts invoiceParts = completionDetails.getInvoiceParts();
		completionStatus.setParts(PublicAPIConstant.NOT_STARTED);
		if(completionDetails.getBuyerId().equalsIgnoreCase(MPConstants.HSR_BUYER_ID)){
			if(null!=invoiceParts &&  null != invoiceParts.getInvoicePartsList()
					&& ((MPConstants.INDICATOR_NO_PARTS_REQUIRED).equals(invoiceParts.getInvoicePartsList().getNoPartsRequiredInd()))){
				completionStatus.setParts(PublicAPIConstant.COMPLETED);
			}else if (null!=invoiceParts &&  null != invoiceParts.getInvoicePartsList()
					&& (null==invoiceParts.getInvoicePartsList().getInvoicePart()
							||invoiceParts.getInvoicePartsList().getInvoicePart().size()==0)
					&& ((MPConstants.INDICATOR_NO_PARTS_ADDED).equals(invoiceParts.getInvoicePartsList().getNoPartsRequiredInd()))){
				completionStatus.setParts(PublicAPIConstant.REQUIRED);
			}else if(null!=invoiceParts &&  null != invoiceParts.getInvoicePartsList()
					&& ((MPConstants.INDICATOR_PARTS_ADDED).equals(invoiceParts.getInvoicePartsList().getNoPartsRequiredInd()))
					&& null!=invoiceParts.getInvoicePartsList().getInvoicePart()
					&& invoiceParts.getInvoicePartsList().getInvoicePart().size()>0){
				boolean invoicePartsRequired = false;
				for(InvoicePart invoicePart : invoiceParts.getInvoicePartsList().getInvoicePart()){
					//In order to mark invoice as completed all parts should be in Installed or Not Installed status
					if((!PublicAPIConstant.INSTALLED.equalsIgnoreCase(invoicePart.getPartStatus())
							&& !PublicAPIConstant.NOT_INSTALLED.equalsIgnoreCase(invoicePart.getPartStatus()))
							// TODO - Add the condition for NO PARTS REQUIRED IS FALSE
							){
						invoicePartsRequired = true;
					}
				}
				if(invoicePartsRequired){
					completionStatus.setParts(PublicAPIConstant.REQUIRED);
				}else{
					completionStatus.setParts(PublicAPIConstant.COMPLETED);
				}
			}
		}
		
		
		//Signature : Default Required
		completionStatus.setSignature(PublicAPIConstant.REQUIRED);
		
		SignatureDocuments signatureDocuments = completionDetails.getSignatures();
		if(null!=signatureDocuments && null!=signatureDocuments.getSignature()
				&& signatureDocuments.getSignature().size()>0){
			boolean custSignature = false;
			boolean proSignature = false;
			for(SignatureDocument signatureDocument : signatureDocuments.getSignature()){
				if(StringUtils.equalsIgnoreCase(PublicAPIConstant.PROVIDER_SIGNATURE
						,signatureDocument.getDocumentType())){
					proSignature = true;
				}else if(StringUtils.equalsIgnoreCase(PublicAPIConstant.CUSTOMER_SIGNATURE
						,signatureDocument.getDocumentType())){
					custSignature = true;
				}
			}
			if(custSignature&&proSignature){
				completionStatus.setSignature(PublicAPIConstant.COMPLETED);
			}
		}
		
					
		// Parts tracking : Default Not started  
		completionStatus.setPartsTracking(PublicAPIConstant.NOT_STARTED);
		
		if(null!=completionDetails.getPartsTracking() && null!= completionDetails.
				getPartsTracking().getPart() && completionDetails.getPartsTracking().
				getPart().size()>0){
			boolean partsTrackingcompleted = false;
			for(PartTrack partTrack: completionDetails.getPartsTracking().getPart()){
				if(StringUtils.isNotBlank(partTrack.getReturnCarrier()) && 
						StringUtils.isNotBlank(partTrack.getReturnTrackingNumber())){
					partsTrackingcompleted = true;
					break;
				}
			}
			if(partsTrackingcompleted){
				completionStatus.setPartsTracking(PublicAPIConstant.COMPLETED);
			}
		}
		
		
		return completionStatus;
	}
	
	private Double calculateNonPermitAddonPrice(AddOns addons){
		Double nonPermitaddonprice = 0.00;
		AddOnList list = new AddOnList();
		if(null!=addons){
			list = addons.getAddonList();
		for(AddOn addonOn:list.getAddon()){
			if(addonOn.getQty()>=1 && ((null== addonOn.getCoverageType())||!(null!= addonOn.getCoverageType() && addonOn.getCoverageType().equals("CC") && addonOn.getMiscInd().equals("N")))){
				/*nonPermitaddonprice = nonPermitaddonprice
						+ MoneyUtil.getRoundedMoney(addonOn.getQty()
								* (addonOn.getCustomerCharge() - (addonOn
										.getCustomerCharge() * addonOn
										.getMargin())));*/
			
			addonOn.getAddonPrice();
			nonPermitaddonprice = nonPermitaddonprice+addonOn.getProviderPayment();
			}
		}
		}
		return nonPermitaddonprice;
		
	}
	
	private Double calculatePermitaddonprice(Permits permits){
		Double permitAddonprice = 0.00;
		PermitAddons list = new PermitAddons();
		if(null!=permits){
			list = permits.getPermitAddons();
		
		for(PermitAddon addonOn:list.getPermitAddon()){
			if(addonOn.getQty()>=1){
				permitAddonprice = permitAddonprice
						+ MoneyUtil.getRoundedMoney(addonOn.getQty()* addonOn.getCustomerCharge());
			}
		}
		}
		return permitAddonprice;
		
	}
	
	private Double calculatePrepaidPermitPrice(Permits permits){
		Double prepaidPermitPrice = 0.00;
		PermitTasks list = new PermitTasks();
		if(null!=list){
			list = permits.getPermitTasks();
		
		for(PermitTask task:list.getPermitTask()){
				prepaidPermitPrice = prepaidPermitPrice + MoneyUtil.getRoundedMoney(task.getFinalPermitPriceByProvider());
		}
		}
		return prepaidPermitPrice;
		
	}
		
	private Double calculatePrepaidPermitSettingPrice(Permits permits){
		Double prepaidPermitPrice = 0.00;
		PermitTasks list = new PermitTasks();
		if(null!=list){
			list = permits.getPermitTasks();
		
		for(PermitTask task:list.getPermitTask()){
				prepaidPermitPrice = prepaidPermitPrice + MoneyUtil.getRoundedMoney(task.getCustPrePaidAmount());
		}
		}
		return prepaidPermitPrice;
		
	}
	//for decrypting credit card number
	public String decryptCreditCardInfo(String ccNumber) {
						
			if(StringUtils.isNotBlank(ccNumber)) {
				CryptographyVO cryptographyVO = new CryptographyVO();
				cryptographyVO.setInput(ccNumber);
								
				//Commenting the code for SL-18789
				//cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
				//cryptographyVO =  cryptography.decryptKey(cryptographyVO);
				cryptographyVO.setKAlias(MPConstants.CC_ENCRYPTION_KEY);
				cryptographyVO =  cryptography128.decryptKey128Bit(cryptographyVO);
				
				ccNumber=cryptographyVO.getResponse();
				}
			
			return formatCardNumber(ccNumber);
			
		}
	  
	  //method to  mask credit card number with asterisk(*)
   private String formatCardNumber(String ccNumber)
	  {
			int last=ccNumber.length();
			int start=last-MPConstants.VISIBLE_NO_OF_DIGITS_CREDIT_CARD;
			String value="";
			for(int i=0;i<start;i++){
				value=value+"*";
			}
			ccNumber=value+ccNumber.substring(start, last);
			return ccNumber;
	  }
	//method for mapping addon price
	private void mapAddonPrice( List<AddOn> addonList){
		for(AddOn addon:addonList){
			addon.setQuantity(addon.getQty());
			if(addon.getQty()==0){
				//variable quantity was included for calculating add on price
				addon.setQuantity(ONE);
			}
			addon.getAddonPrice();
		}
	}
	//method for removing time part from date
	private String getDatePart(String source){
		SimpleDateFormat sdf = new SimpleDateFormat(MPConstants.REQUIRED_DATE_FORMAT);
		String formattedDate =EMPTY_STR ;
		SimpleDateFormat defaultDateFormat = new SimpleDateFormat(MPConstants.REQUIRED_DATE_FORMAT);
		Date dt;
		try {
			dt = defaultDateFormat.parse(source);
			formattedDate=sdf.format(dt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			formattedDate=source;
		}
		return formattedDate;
	}
	
	/**
	 * @return the lookupDao
	 */
	public LookupDao getLookupDao() {
		return lookupDao;
	}

	/**
	 * @param lookupDao the lookupDao to set
	 */
	public void setLookupDao(LookupDao lookupDao) {
		this.lookupDao = lookupDao;
	}
	
	public Integer getFirmId(String providerId) throws BusinessServiceException{
		Integer firmId = null;
		try{
			firmId = mobileSOManagementDao.getFirmId(providerId);
			
		}catch(Exception e){
			logger.info("Exception in MobileSOManagementBOImpl.getFirmId() "+e.getMessage());
		}
		return firmId;
	}
	//method for getting primary phone number
	private String getContactPrimaryPhone(Map<String, String> param){
		String phoneNo=EMPTY_STR;
		try {
			phoneNo=mobileSOManagementDao.getContactPrimaryPhone(param);
			if(null!=phoneNo){
				phoneNo=formatPhoneNumber(phoneNo);
			}
		} catch (DataServiceException e) {
			return phoneNo;
			}
		return phoneNo;
	}
	//method for getting alternate phone number
	private String getContactAlternatePhone(Map<String, String> param){
		String phoneNo=EMPTY_STR;
		try {			
			phoneNo=mobileSOManagementDao.getContactAlternatePhone(param);
			if(null!=phoneNo){
				phoneNo=formatPhoneNumber(phoneNo);
			}
		} catch (DataServiceException e) {
			return phoneNo;
		}
		return phoneNo;
	}
	
	public IApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
	
	/**
	 * @return the buyer logo urls
	 */
	public HashMap<String, Object> getURLs() throws BusinessServiceException{
		HashMap<String, Object> pathParam=new HashMap<String, Object>();
		try {
			
			String baseUrl = applicationProperties.getPropertyFromDB(MPConstants.BASE_URL);
			String pathUrl = applicationProperties.getPropertyFromDB(MPConstants.PATH_URL);
			pathParam.put("baseUrl", baseUrl);
			pathParam.put("pathUrl", pathUrl);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BusinessServiceException(e.getMessage());
		}
		return pathParam;
	}
	
	public HashMap<String, Object> getDocumentDetails(String soId,String documentName)throws BusinessServiceException{
		HashMap<String, Object> docDetails = new HashMap<String, Object>();
		
		try{
			docDetails = mobileSOManagementDao.getDocumentDetails(soId,documentName);
			
		}catch(Exception e){
			logger.info("Exception in ProviderUploadDocumentService.getFirmId() "+e.getMessage());
		}
		return docDetails;
	}

	
	/**
	 * Get the completion details v2.0
	 *@param Map<String, Object> param
	 * @return RetrieveSOCompletionDetailsMobile
	 * @throws BusinessServiceException
	 */
	public RetrieveSOCompletionDetailsMobile getCompletionDetails(
			Map<String, Object> param) throws BusinessServiceException {
		
		RetrieveSOCompletionDetailsMobile retrieveSOCompletionDetailsMobile = new RetrieveSOCompletionDetailsMobile();	
		com.newco.marketplace.api.mobile.beans.sodetails.v2_0.CompletionDetails completionDetails = null;
		try {
			completionDetails = mobileSOManagementDao.getCompletionDetailsWithTrip(param);
			if (null != completionDetails) {
				completionDetails = mapCompletionDetailsWithTrip(completionDetails,param);
			}
			retrieveSOCompletionDetailsMobile.setCompletionDetails(completionDetails);
		} catch (DataServiceException ex) {
			logger.error("Exception Occured in MobileSOManagementBO-->getServiceOrderDetails()");
			throw new BusinessServiceException(ex.getMessage());
		}
		return retrieveSOCompletionDetailsMobile;
	}
	
	/**
	 * Get the service order details for mobile v2.0
	 * 
	 * @param Map<String, Object> param
	 * @return RetrieveSODetailsMobile
	 * @throws BusinessServiceException
	 */
	public RetrieveSODetailsMobile getServiceOrderDetails(
			Map<String, Object> param)
			throws BusinessServiceException {
		RetrieveSODetailsMobile serviceOrderResponse =new RetrieveSODetailsMobile();
		com.newco.marketplace.api.mobile.beans.sodetails.v2_0.ServiceOrderDetails serviceOrderDetails = null;
		try {
				// calling function to get serviceOrderDetails
				serviceOrderDetails = mobileSOManagementDao
						.getServiceOrderDetailsWithTrip(param);
				logger.info("Inside Bo before mapping -->"+serviceOrderDetails);
				if (null != serviceOrderDetails) {
					
					serviceOrderDetails=mapServiceOrderDetailsWithTrip(serviceOrderDetails);	
					logger.info(
							"Inside Boafter mapping -->"+serviceOrderDetails);
					serviceOrderResponse.setSoDetails(serviceOrderDetails);
					logger.info(
							"Inside Bo-->"+serviceOrderDetails);
				}
		} catch (DataServiceException ex) {
			logger.error("Exception Occured in MobileSOManagementBO-->getServiceOrderDetails()");
			throw new BusinessServiceException(ex.getMessage());
		}
		return serviceOrderResponse;
	}
	
	
	
	/**
	 * Map service order details V2.0
	 * @param serviceOrderDetails
	 * @return
	 */
	private com.newco.marketplace.api.mobile.beans.sodetails.v2_0.ServiceOrderDetails mapServiceOrderDetailsWithTrip(
			com.newco.marketplace.api.mobile.beans.sodetails.v2_0.ServiceOrderDetails serviceOrderDetails) {
		logger.info("mapServiceOrderDetails");
		//R16_0 : Sl-20728 :Removing the html tags of general section
		serviceOrderDetails.getOrderDetails().setBuyerTerms(ServiceLiveStringUtils.removeHTML(serviceOrderDetails.getOrderDetails().getBuyerTerms()));
		serviceOrderDetails.getOrderDetails().setOverView(ServiceLiveStringUtils.removeHTML(serviceOrderDetails.getOrderDetails().getOverView()));
		serviceOrderDetails.getOrderDetails().setSpecialInstuctions(ServiceLiveStringUtils.removeHTML(serviceOrderDetails.getOrderDetails().getSpecialInstuctions()));
	
		//R16_0 : Sl-20728 :Removing the html tags of task comment
		if( null != serviceOrderDetails.getScope().getTasks().getTask()){
			List <Task> taskResponse = serviceOrderDetails.getScope().getTasks().getTask();
			if(null != taskResponse && !taskResponse.isEmpty()){
				for(Task taskDetail : taskResponse){		
					//SL-20728 : removing html tags
					taskDetail.setTaskComments(ServiceLiveStringUtils.removeHTML(taskDetail.getTaskComments()));
				}
			}
			serviceOrderDetails.getScope().getTasks().setTask(taskResponse);
		}
		
		// setting MaxTimeWindow and MinTimeWindow from retrieved buyer object
		// to appointment object	
		if (null != serviceOrderDetails.getBuyer()) {
			if (null != serviceOrderDetails.getBuyer().getMaxTimeWindow()) {
				serviceOrderDetails.getAppointment().setMaxTimeWindow(
						serviceOrderDetails.getBuyer().getMaxTimeWindow());
			}
			if (null != serviceOrderDetails.getBuyer().getMinTimeWindow()) {
				serviceOrderDetails.getAppointment().setMinTimeWindow(
						serviceOrderDetails.getBuyer().getMinTimeWindow());
			}

			//format Buyer phone numbers
			String buyerPrimaryPhone=serviceOrderDetails.getBuyer().getPrimaryphone();
			serviceOrderDetails.getBuyer().setPrimaryphone(formatPhoneNumber(buyerPrimaryPhone));
			String buyerAltPhone = serviceOrderDetails.getBuyer().getAlternatePhone();
			serviceOrderDetails.getBuyer().setAlternatePhone(formatPhoneNumber(buyerAltPhone));
			String fax = serviceOrderDetails.getBuyer().getFax();
			serviceOrderDetails.getBuyer().setFax(formatPhoneNumber(fax));
		}
		//-----------------------------------------------
       //--> Finished Formatting appointment details
		
		//-- >Formatting Service Location and Alternate service Location
		
         //for getting service location phone number
         //defining map in order to pass soId and contactId to the query
		Map<String, String> param = new HashMap<String, String>();
		param.put("soid", serviceOrderDetails.getOrderDetails().getSoId());
		String primaryPhone = EMPTY_STR;
		String alternatePhone = EMPTY_STR;
		// for getting primary phone number and alternate phone number
		if (null != serviceOrderDetails.getServiceLocation()
				&& null != serviceOrderDetails.getServiceLocation()
						.getSoContactId()) {
			// putting so contactId in the map
			param.put("socontactid", serviceOrderDetails.getServiceLocation()
					.getSoContactId());
			// calling common method for getting primary phone number and
			// alternate phone number
			primaryPhone = getContactPrimaryPhone(param);
			// setting retrieved primary phone number and alternate phone number
			// into
			// serviceOrderDetails object
			alternatePhone = getContactAlternatePhone(param);
			serviceOrderDetails.getServiceLocation().setPrimaryPhone(primaryPhone);
			serviceOrderDetails.getServiceLocation().setAlternatePhone(alternatePhone);
		}
		// for getting service location alternate phone number
		if (null != serviceOrderDetails.getAlternateServiceLocation()
				&& null != serviceOrderDetails.getAlternateServiceLocation()
						.getSoContactId()) {

			// putting so contactId in the map
			param.remove("socontactid");
			param.put("socontactid", serviceOrderDetails
					.getAlternateServiceLocation().getSoContactId());
			// calling common method for getting primary phone number and
			// alternate phone number
			primaryPhone = getContactPrimaryPhone(param);
			alternatePhone = getContactAlternatePhone(param);
			// setting retrieved primary phone number and alternate phone number
			// into
			// serviceOrderDetails object
			serviceOrderDetails.getAlternateServiceLocation()
					.setPrimaryPhone(primaryPhone);
			serviceOrderDetails.getAlternateServiceLocation()
					.setAlternatePhone(alternatePhone);
		}
		//-----------------------------------------------
		//--> Finishing Formatting Service Location and Alternate service Location
	
		//--> Formatting BuyerReferences
		
		// checking BuyerReferences and setting BuyerRefPresentInd appropriately
		if (null != serviceOrderDetails.getBuyerReferences()
				&& null != serviceOrderDetails.getBuyerReferences()
						.getBuyerReference()
				&& serviceOrderDetails.getBuyerReferences().getBuyerReference()
						.size() != 0) {
			serviceOrderDetails.setBuyerRefPresentInd(MPConstants.YES);
		} else {
			serviceOrderDetails.setBuyerRefPresentInd(MPConstants.NO);
		}
		//-----------------------------------------------
		//--> Finished Formatting BuyerReferences
		
		//-->Formatting document types
		//iterating fetched Documents and setting  document types as empty if
		//not an allowed document type		
		if (null != serviceOrderDetails.getDocuments()
				&& null != serviceOrderDetails.getDocuments().getDocument()
				&& serviceOrderDetails.getDocuments().getDocument().size() != 0&& null!=serviceOrderDetails.getBuyer().getBuyerUserId()) {
			//List<Document> docList=formatDocumentTypes(serviceOrderDetails.getDocuments().getDocument(),serviceOrderDetails.getBuyer().getBuyerUserId());
			// serviceOrderDetails.getDocuments().setDocument(docList);
			
			// To remove documents in case of customer signature or provider signature while fetching so details v2.0
			List<Document> removeList = new ArrayList<Document>();
			for(Document document: serviceOrderDetails.getDocuments().getDocument() ){
				if(null != document){
					if(MPConstants.CUSTOMER_SIGNATURE.equalsIgnoreCase(document.getDocumentType())||MPConstants.PROVIDER_SIGNATURE.equalsIgnoreCase(document.getDocumentType())){
						removeList.add(document);
					}
				}
			}
			serviceOrderDetails.getDocuments().getDocument().removeAll(removeList);
		}
		//-----------------------------------------------
		//-->Finished Formatting document types
		//-- > Formatting Part details
		//setting Count of parts
		
		if (null != serviceOrderDetails.getParts()
				&& null != serviceOrderDetails.getParts().getPart()) {
			serviceOrderDetails.getParts().setCountofParts(
					serviceOrderDetails.getParts().getPart().size());
		}

		//format provider phone numbers
		if(null!=serviceOrderDetails.getProvider()){
		String providerPrimaryPhone=serviceOrderDetails.getProvider().getProviderPrimaryPhone();
		serviceOrderDetails.getProvider().setProviderPrimaryPhone(formatPhoneNumber(providerPrimaryPhone));
		String providerAltPhone = serviceOrderDetails.getProvider().getProviderAltPhone();
		serviceOrderDetails.getProvider().setProviderAltPhone(formatPhoneNumber(providerAltPhone));
		String firmPrimaryPhone=serviceOrderDetails.getProvider().getFirmPrimaryPhone();
		serviceOrderDetails.getProvider().setFirmPrimaryPhone(formatPhoneNumber(firmPrimaryPhone));
		String firmAltPhone=serviceOrderDetails.getProvider().getFirmAltPhone();
		serviceOrderDetails.getProvider().setFirmAltPhone(formatPhoneNumber(firmAltPhone));
		String smsNum=serviceOrderDetails.getProvider().getSmsNumber();
		serviceOrderDetails.getProvider().setSmsNumber(smsNum);
		}
		
		if(null!=serviceOrderDetails.getParts()&& null!=serviceOrderDetails.getParts().getPart()){
			List<Part> parts = serviceOrderDetails.getParts().getPart();
			for(Part part : parts){
				PickUpLocation locn = part.getPickupLocation();
				if(null==locn||(null!=locn && (null == locn.getPickupLocationStreet1() || locn.getPickupLocationStreet1().equals(MPConstants.EMPTY_STRING)) && (null == locn.getPickupLocationStreet2() || locn.getPickupLocationStreet2().equals(MPConstants.EMPTY_STRING)) && ( null == locn.getPickupLocationCity() || locn.getPickupLocationCity().equals(MPConstants.EMPTY_STRING)))){
					part.setPickupLocationAvailability(MPConstants.NOT_AVAILABLE);
					part.setPickupLocation(null);
				}else{
					part.setPickupLocationAvailability(MPConstants.AVAILABLE);
				}
			}
		}
		
		// Decode the HTML encoded notes and support notes
		if(null!= serviceOrderDetails.getNotes()){
			for(Note note : serviceOrderDetails.getNotes().getNote()){
				if(StringUtils.isNotEmpty(note.getNoteSubject())){
					String decodedSub = EsapiUtility.getDecodedString(note.getNoteSubject());
					note.setNoteSubject(decodedSub);
				}
				if(StringUtils.isNotEmpty(note.getNoteBody())){
					String decodedBody = EsapiUtility.getDecodedString(note.getNoteBody());
					note.setNoteBody(decodedBody);
				}
			}
		}
		
		// Decode the HTML encoded notes and support notes
		if(null!= serviceOrderDetails.getSupportNotes()){
			for(SupportNote supportNote : serviceOrderDetails.getSupportNotes().getSupportNote()){
				if(StringUtils.isNotEmpty(supportNote.getNoteSubject())){
					String decodedSub = EsapiUtility.getDecodedString(supportNote.getNoteSubject());
					supportNote.setNoteSubject(decodedSub);
				}
				if(StringUtils.isNotEmpty(supportNote.getNote())){
					String decodedBody = EsapiUtility.getDecodedString(supportNote.getNote());
					supportNote.setNote(decodedBody);
				}
			}
		}
		
		//SL-20673: Edit Completion Details
		//Fetching the details of latest trip
		LatestTripDetails latestTrip = null;
		Map<String, Object> params = new HashMap<String, Object>();
		
		try{
			if(null != serviceOrderDetails && null != serviceOrderDetails.getOrderDetails()){
				params.put("soId", serviceOrderDetails.getOrderDetails().getSoId());
				latestTrip = mobileSOManagementDao.getLatestTripDetails(params);
				if(null != latestTrip){
					serviceOrderDetails.setLatestTrip(latestTrip);
				}
			}
		}
		catch (Exception e) {
			logger.error("Exception occured in mapServiceOrderDetailsWithTrip()-->fetching LatestTripDetails due to "+e.getMessage());
			return serviceOrderDetails;
		}
		return serviceOrderDetails;
	}

	/**
	 * @param feedbackVO
	 * 
	 * to insert feedback details
	 * 
	 */
	public void insertFeedbackDetails(FeedbackVO feedbackVO)
			throws BusinessServiceException {
		
		try{
			mobileSOManagementDao.insertFeedbackDetails(feedbackVO);
		}
		catch (DataServiceException ex) {
			logger.error("Exception Occured in MobileSOManagementBO-->insertFeedbackDetails()");
			throw new BusinessServiceException(ex.getMessage());
		}
	}
	/**@param soStatusList 
	 * @Description: Getting Service Order status Id for the status Specified in List 
	 * @return  List<Integer>
	 * @throws BusinessServiceException
	 */
	public List<SOStatusVO> getServiceOrderStatus()throws BusinessServiceException {
		List<SOStatusVO> statusVo =null;
		try{
			statusVo = mobileSOManagementDao.getSOStatus();
		}catch (DataServiceException e) {
			logger.error("Exception Occured in MobileSOManagementBO-->getServiceOrderStatus()");
			throw new BusinessServiceException(e.getMessage());
		}
		return statusVo;
	}
	
	public ProviderSOSearchVO  convertServiceDate(ProviderSOSearchVO result ) throws BusinessServiceException{
		  SimpleDateFormat dmyFormat = new SimpleDateFormat(MPConstants.DMY_DATE_FORMAT);
		  HashMap<String, Object> serviceStartDateTime= new HashMap<String, Object>();
		  HashMap<String, Object> serviceEndDateTime = new HashMap<String, Object>();
		  try{
			  serviceStartDateTime = TimeUtils.convertGMTToGivenTimeZone(result.getServiceDate1(),result.getServiceTime1(),result.getServiceLocationTimeZone());
			  String sDate = dmyFormat.format((Date) serviceStartDateTime.get(OrderConstants.GMT_DATE));
			  result.setServiceStartDate(sDate);
			  result.setServiceTime1((String) serviceStartDateTime.get(OrderConstants.GMT_TIME));
	          //Setting End Date Details after checking Date Type
			   if(result.getDateType().equals(Integer.parseInt(FIXED_DATE))) {
					serviceEndDateTime = TimeUtils.convertGMTToGivenTimeZone(result.getServiceDate1(),result.getServiceTime2(),result.getServiceLocationTimeZone());
					result.setServiceTime2((String) serviceEndDateTime.get(OrderConstants.GMT_TIME));
				} else {
					serviceEndDateTime = TimeUtils.convertGMTToGivenTimeZone(result.getServiceDate2(),result.getServiceTime2(),result.getServiceLocationTimeZone());
					String eDate = dmyFormat.format((Date) serviceEndDateTime.get(OrderConstants.GMT_DATE));
					result.setServiceEndDate(eDate);
					result.setServiceTime2((String) serviceEndDateTime.get(OrderConstants.GMT_TIME));
				}
	            String mdy = dmyFormat.format((Date) serviceStartDateTime.get(OrderConstants.GMT_DATE));
	            String serviceStartDate = mdy + "T"+ result.getServiceTime1();
				String timeZone = getTimeZoneFromDate(result.getZip(),serviceStartDate,result.getServiceLocationTimeZone());
				result.setServiceLocationTimeZone(timeZone);
		 }catch (Exception e) {
			logger.error("Exception in Converting Date  to service location timezone"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		  return result;
		  
	  }

}

