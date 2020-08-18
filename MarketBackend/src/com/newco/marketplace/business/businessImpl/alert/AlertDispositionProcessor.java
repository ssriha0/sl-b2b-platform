package com.newco.marketplace.business.businessImpl.alert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.dto.vo.serviceorder.BuyerDetail;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.ProviderDetail;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderDetail;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.daoImpl.alert.AlertDaoImpl;
import com.newco.marketplace.persistence.daoImpl.so.buyer.BuyerDaoImpl;
import com.newco.marketplace.persistence.daoImpl.so.order.ServiceOrderDaoImpl;
import com.newco.marketplace.persistence.iDao.contact.ContactDao;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.webservices.base.Template;



/*
 * This class is responsible to determine the alert dispositions given an Alert action  
 */
public class AlertDispositionProcessor implements AlertConstants{

	private AlertDaoImpl alertDao;
	private ServiceOrderDaoImpl serviceOrderDao;
	private BuyerDaoImpl buyerDao;
	private ContactDao contactDao;
	private Map<String, Object> aopHashMap;

	private static final Logger logger = Logger.getLogger(AlertDispositionProcessor.class.getName());

	public AlertDisposition getAlertDispositionDetail(String alertActionName, String soId, Template template, Map<String, Object> aopHashMap){
		
		this.aopHashMap = aopHashMap;
		String aopActionId = (String)aopHashMap.get(AOPConstants.AOP_AOP_ACTION_ID);
		Map<String, Object> alertHashMap = getAlertDisposition(alertActionName, aopActionId);
		
		String fromAddress = "";
		String toAddress = "";
		String ccAddress = "";
		String bccAddress = "";
		String role="";
		if (alertHashMap != null && alertHashMap.get(ALERT_DISP_TO) != null && ALERT_ROLE_X.equals(alertHashMap.get(ALERT_DISP_TO))) {
			alertHashMap = formatContextualAddress(alertHashMap, soId, template, aopHashMap);
		}
		
		if(alertActionName.equals(Constants.WCI_NOT_REQUIRED_ALERT)){
			SecurityContext  sc = (SecurityContext)aopHashMap.get(AOPConstants.AOP_SECURITY_CONTEXT);
			Integer loggedInProviderId = sc.getVendBuyerResId();
			fromAddress = formatFromAddressForWCI((String)alertHashMap.get(ALERT_DISP_FROM), loggedInProviderId);
			toAddress = formatToAddressForWCI((String)alertHashMap.get(ALERT_DISP_TO), loggedInProviderId);
			fromAddress = formatCcAddressForWCI((String)alertHashMap.get(ALERT_DISP_FROM), loggedInProviderId);
			toAddress = formatBccAddressForWCI((String)alertHashMap.get(ALERT_DISP_TO), loggedInProviderId);
		}else{
			SecurityContext  sc = (SecurityContext)aopHashMap.get(AOPConstants.AOP_SECURITY_CONTEXT);
			Integer loggedInUserId = sc.getRoles().getRoleId();
			fromAddress = formatFromAddress((String)alertHashMap.get(ALERT_DISP_FROM), soId, template,loggedInUserId);
			toAddress = formatToAddress((String)alertHashMap.get(ALERT_DISP_TO), soId,template,loggedInUserId);
			ccAddress = formatCcAddress((String)alertHashMap.get(ALERT_DISP_CC), soId, template,loggedInUserId);
			bccAddress = formatBccAddress((String)alertHashMap.get(ALERT_DISP_BCC), soId, template,loggedInUserId);
		}
		// for adding ROLE_IND in hashmap

		String alertDispositionTo = (String)alertHashMap.get(ALERT_DISP_TO);
		String alertDispositionBcc = (String)alertHashMap.get(ALERT_DISP_BCC);
		String alertDispositionCc = (String)alertHashMap.get(ALERT_DISP_CC);
		if((null == alertDispositionTo||
			ALERT_ROLE_ALL_ROUTED_PROVIDERS_EXCEPT_ACCEPTED.equals(alertDispositionTo)||
			ALERT_ROLE_PROVIDER_ADMIN.equals(alertDispositionTo)||	
			ALERT_ROLE_ACCEPTED_VENDOR_RESOURCE.equals(alertDispositionTo)||	
			ALERT_ROLE_ACCEPTED_PROVIDERS.equals(alertDispositionTo)||	
			ALERT_ROLE_ALL_PROVIDERS.equals(alertDispositionTo)||	
			ALERT_ROLE_ALL_ROUTED_RESOURCES.equals(alertDispositionTo))	
			&&	
			(null == alertDispositionBcc||
			ALERT_ROLE_ALL_ROUTED_PROVIDERS_EXCEPT_ACCEPTED.equals(alertDispositionBcc)||	
			ALERT_ROLE_PROVIDER_ADMIN.equals(alertDispositionBcc)||	
			ALERT_ROLE_ACCEPTED_VENDOR_RESOURCE.equals(alertDispositionBcc)||	
			ALERT_ROLE_ACCEPTED_PROVIDERS.equals(alertDispositionBcc)||	
			ALERT_ROLE_ALL_PROVIDERS.equals(alertDispositionBcc)||	
			ALERT_ROLE_ALL_ROUTED_RESOURCES.equals(alertDispositionBcc))
			&&	
			(null == alertDispositionCc||
			ALERT_ROLE_ALL_ROUTED_PROVIDERS_EXCEPT_ACCEPTED.equals(alertDispositionCc)||	
			ALERT_ROLE_PROVIDER_ADMIN.equals(alertDispositionCc)||	
			ALERT_ROLE_ACCEPTED_VENDOR_RESOURCE.equals(alertDispositionCc)||	
			ALERT_ROLE_ACCEPTED_PROVIDERS.equals(alertDispositionCc)||	
			ALERT_ROLE_ALL_PROVIDERS.equals(alertDispositionCc)||	
			ALERT_ROLE_ALL_ROUTED_RESOURCES.equals(alertDispositionCc)) )
		{
			role = AlertConstants.ROLE_PROVIDER;
		}
		else if(aopHashMap.get(CONSUMER_BUYER).equals(YES))
		{
			role = AlertConstants.ROLE_CONSUMER_BUYER;
		}
		else if(aopHashMap.get(CONSUMER_BUYER).equals(NO))
		{
			role = AlertConstants.ROLE_PROFESSIONAL_BUYER;
		}
		if( (null == alertDispositionTo||ALERT_ROLE_B_OR_P.equals(alertDispositionTo))&&
			(null == alertDispositionCc||ALERT_ROLE_B_OR_P.equals(alertDispositionCc))&&		
	    	(null == alertDispositionBcc||ALERT_ROLE_B_OR_P.equals(alertDispositionBcc))
		)
				{
			SecurityContext  sc = (SecurityContext)aopHashMap.get(AOPConstants.AOP_SECURITY_CONTEXT);
			Integer loggedInUserId = sc.getRoles().getRoleId();
			if(loggedInUserId != null && (OrderConstants.BUYER_ROLEID == loggedInUserId.intValue() || OrderConstants.SIMPLE_BUYER_ROLEID == loggedInUserId.intValue()))
			{
				role = AlertConstants.ROLE_PROVIDER;
			}
		}
		bccAddress = filterDuplicates(ccAddress,bccAddress);
		ccAddress = filterDuplicates(toAddress,ccAddress);

		// if SMS filter for numbers
		if(template.getTemplateTypeId() == AlertConstants.TEMPLATE_TYPE_SMS){
			filtersms(toAddress);
			filtersms(ccAddress);
			filtersms(bccAddress);
		}

		bccAddress = filterDuplicates(ccAddress,bccAddress);
		ccAddress = filterDuplicates(toAddress,ccAddress);

		// If SMS put all in the To address.
		if(template.getTemplateTypeId() == AlertConstants.TEMPLATE_TYPE_SMS){
			toAddress = toAddress + ";" + ccAddress + ";" + bccAddress;
		}
		AlertDisposition alertDisposition = new AlertDisposition();
		alertDisposition.setAlertFrom(fromAddress);
		alertDisposition.setAlertTo(toAddress);
		alertDisposition.setAlertCc(ccAddress);
		alertDisposition.setAlertBcc(bccAddress);
		alertDisposition.setRole(role);
		return alertDisposition;
		
	}
	
	private String determineProvEmailAddr(String soId ,Template template){
		String strAlertAddr = "";
		
		ArrayList<AlertProviderEmail> alertProviderEmailList =null;
		try{
			Integer tierId = new Integer(0);
			Object obj = aopHashMap.get(AOPConstants.AOP_SO_TIER_ID);
			if(obj != null && (obj instanceof Integer)) {
				tierId = (Integer)obj;
			}
			else if(aopHashMap.get(AOPConstants.AOP_TIER_OVERFLOW)!= null){
				tierId = OrderConstants.OVERFLOW;
			}
		    alertProviderEmailList = (ArrayList<AlertProviderEmail>)alertDao.getProviderAndResourceEmails(soId, tierId);
			 
			 String emailTemp = "";
			
		for (int x=0; x < alertProviderEmailList.size(); x++){
			// Here let us split it up for SMS
			// Lets get the 3 out of here. Where is that constant
			if(template.getTemplateTypeId() == AlertConstants.TEMPLATE_TYPE_SMS){
				if((alertProviderEmailList.get(x).getAltContactMethodId()!= null)&&
						(alertProviderEmailList.get(x).getAltContactMethodId() == 3)&&
						(!StringUtils.isBlank(alertProviderEmailList.get(x).getSmsNo()))
						){
					strAlertAddr += alertProviderEmailList.get(x).getSmsNo() + EMAIL_DELIMITER;
				}
			}else{
				emailTemp = alertProviderEmailList.get(x).getProviderAdminEmail();
				if (emailTemp != null && !emailTemp.equals(""))
					strAlertAddr += emailTemp + EMAIL_DELIMITER;
			}
		}
		
		}catch (Exception e){
			logger.error("determineProvEmailAddr", e);
		}
		return strAlertAddr;
	}

	private String determineProvAdminEmailAddr(String soId ,Template template){
		String strAlertAddr = "";
		/*********************************************************************************
		 * GKL...SMS Story Completion
		 * this is the only method that currently will add functionality for sending SMS
		 * I will get to refactoring this asap.
		 *********************************************************************************/

		ArrayList<AlertProviderEmail> i =null;
		try{
			 i = (ArrayList<AlertProviderEmail>)alertDao.getProviderAdminEmails(soId);
			 String emailTemp = "";
			
		for (int x=0; x < i.size(); x++){
			// Here let us split it up for SMS
			// Lets get the 3 out of here. Where is that constant
			if(template.getTemplateTypeId() == AlertConstants.TEMPLATE_TYPE_SMS){
				if((i.get(x).getAltContactMethodId()!= null)&&
						(i.get(x).getAltContactMethodId() == 3)&&
						(!StringUtils.isBlank(i.get(x).getSmsNo()))
						){
					strAlertAddr += i.get(x).getSmsNo() + EMAIL_DELIMITER;
				}
			}else{
				emailTemp = i.get(x).getProviderAdminEmail();
				if (emailTemp != null && !emailTemp.equals(""))
					strAlertAddr += emailTemp + EMAIL_DELIMITER;
			}
		}
		
		}catch (Exception e){
			logger.error("determineProvAdminEmailAddr", e);
		}
		return strAlertAddr;
	}

	private String determineProvAdminEmailAddrForRejectedSO(String soId ,Template template){
		String strAlertAddr = "";
		ArrayList<AlertProviderEmail> emails = null;
		try{
			emails = (ArrayList<AlertProviderEmail>)alertDao.getProviderAdminEmailForRejectedSO(soId);
			 String emailTemp = "";
			
			if(template.getTemplateTypeId() == AlertConstants.TEMPLATE_TYPE_EMAIL && emails.size()>0){
				emailTemp = emails.get(0).getProviderAdminEmail();
				if (emailTemp != null && !emailTemp.equals(""))
					strAlertAddr = emailTemp;
			}
		
		}catch (Exception e){
			logger.error("determineProvAdminEmailAddr", e);
		}
		return strAlertAddr;
	}

	private String determineProvAdminEmailAddrAddNotesAccepted(String soId ,Template template){
		String strAlertAddr = "";
		/*********************************************************************************
		 * GKL...SMS Story Completion
		 * this is the only method that currently will add functionality for sending SMS
		 * I will get to refactoring this asap.
		 *********************************************************************************/

		ArrayList<AlertProviderEmail> i =null;
		try{
			 i = (ArrayList<AlertProviderEmail>)alertDao.getProviderAdminEmailsForAddNotesAccepted(soId);
			 String emailTemp = "";
			
		for (int x=0; x < i.size(); x++){
			// Here let us split it up for SMS
			// Lets get the 3 out of here. Where is that constant
			if(template.getTemplateTypeId() == AlertConstants.TEMPLATE_TYPE_SMS){
				if((i.get(x).getAltContactMethodId()!= null)&&
						(i.get(x).getAltContactMethodId() == 3)&&
						(!StringUtils.isBlank(i.get(x).getSmsNo()))
						){
					strAlertAddr += i.get(x).getSmsNo() + EMAIL_DELIMITER;
				}
			}else{
				emailTemp = i.get(x).getProviderAdminEmail();
				if (emailTemp != null && !emailTemp.equals(""))
					strAlertAddr += emailTemp + EMAIL_DELIMITER;
			}
		}
		
		}catch (Exception e){
			logger.error("determineProvAdminEmailAddr", e);
		}
		return strAlertAddr;
	}

	private String determineProvAdminEmailAddrForReleasedSO(String soId ,Template template){
		String strAlertAddr = "";
		/*********************************************************************************
		 * GKL...SMS Story Completion
		 * this is the only method that currently will add functionality for sending SMS
		 * I will get to refactoring this asap.
		 *********************************************************************************/

		ArrayList<AlertProviderEmail> i =null;
		try{
			 i = (ArrayList<AlertProviderEmail>)alertDao.getProviderAdminEmailsForReleasedSO(soId);
			 String emailTemp = "";
			
		for (int x=0; x < i.size(); x++){
			// Here let us split it up for SMS
			// Lets get the 3 out of here. Where is that constant
			if(template.getTemplateTypeId() == AlertConstants.TEMPLATE_TYPE_SMS){
				if((i.get(x).getAltContactMethodId()!= null)&&
						(i.get(x).getAltContactMethodId() == 3)&&
						(!StringUtils.isBlank(i.get(x).getSmsNo()))
						){
					strAlertAddr += i.get(x).getSmsNo() + EMAIL_DELIMITER;
				}
			}else{
				emailTemp = i.get(x).getProviderAdminEmail();
				if (emailTemp != null && !emailTemp.equals(""))
					strAlertAddr += emailTemp + EMAIL_DELIMITER;
			}
		}
		
		}catch (Exception e){
			logger.error("determineProvAdminEmailAddr", e);
		}
		return strAlertAddr;
	}


	
	private String determineProvAdminEmailAddrForAddNotes(String soId ,Template template){
		String strAlertAddr = "";
		/*********************************************************************************
		 * GKL...SMS Story Completion
		 * this is the only method that currently will add functionality for sending SMS
		 * I will get to refactoring this asap.
		 *********************************************************************************/

		ArrayList<AlertProviderEmail> i =null;
		try{
			 i = (ArrayList<AlertProviderEmail>)alertDao.getProviderAdminEmailsForAddNotes(soId);
			 String emailTemp = "";
			
		for (int x=0; x < i.size(); x++){
			// Here let us split it up for SMS
			// Lets get the 3 out of here. Where is that constant
			if(template.getTemplateTypeId() == AlertConstants.TEMPLATE_TYPE_SMS){
				if((i.get(x).getAltContactMethodId()!= null)&&
						(i.get(x).getAltContactMethodId() == 3)&&
						(!StringUtils.isBlank(i.get(x).getSmsNo()))
						){
					strAlertAddr += i.get(x).getSmsNo() + EMAIL_DELIMITER;
				}
			}else{
				emailTemp = i.get(x).getProviderAdminEmail();
				if (emailTemp != null && !emailTemp.equals(""))
					strAlertAddr += emailTemp + EMAIL_DELIMITER;
			}
		}
		
		}catch (Exception e){
			logger.error("determineProvAdminEmailAddr", e);
		}
		return strAlertAddr;
	}

	
	protected String filterDuplicates(String toAddress, String ccAddress) {
		String toAddr[] = toAddress.split(EMAIL_DELIMITER);
		String ccAddr[] = ccAddress.split(EMAIL_DELIMITER);
		String ccTemp = ccAddress;
		
		// this is kind of sillyness but be aware, this is meant to eliminate the toAddresses from the CC addresses, if CC is empty
		// this will return an empty string.
		// To ensure toAddress emailId's are not repeated in Cc's
		if (toAddr != null && toAddr.length > 0 && ccAddr != null
				&& ccAddr.length > 0) {
			for (int i = 0; i < toAddr.length; i++) {
				if (StringUtils.isNotBlank(toAddr[i])) {
					toAddr[i] = toAddr[i] + ";";

					ccTemp = ccTemp.replace(toAddr[i], "");
				}

			}
		}
		return ccTemp;
	}
	
	protected String filtersms(String addresses) {
		String toAddr[] = addresses.split(EMAIL_DELIMITER);
		String outtemp = "";
		// To ensure toAddress emailId's are not repeated in Cc's
		if (toAddr != null && toAddr.length > 0 ){
			for (int i = 0; i < toAddr.length; i++) {
				if ((StringUtils.isNotBlank(toAddr[i]))&&(StringUtils.isNumeric(toAddr[i]))) {
					toAddr[i] = toAddr[i] + ";";
					outtemp = outtemp + toAddr[i];
				}
			}
		}
		return outtemp;
	}
	
	private HashMap<String, Object> getAlertDisposition(String alertActionName, String aopActionId){
		HashMap<String, Object> map = new HashMap<String, Object>();

		AlertDisposition alertDisposition = null;
		try {
			alertDisposition = alertDao.getAlertDisposition(alertActionName, aopActionId);

			map.put(ALERT_DISP_FROM, alertDisposition.getAlertFrom());
			map.put(ALERT_DISP_TO, alertDisposition.getAlertTo());
			map.put(ALERT_DISP_CC, alertDisposition.getAlertCc());
			map.put(ALERT_DISP_BCC, alertDisposition.getAlertBcc());
		}
		catch(DataServiceException dse){
			logger.error("getAlertDisposition()-->EXCEPTION-->",dse);
		}
		
		return map ;
	}
	
	private ServiceOrderDetail getServiceOrderDetail(String soId){
		ServiceOrderDetail serviceOrderDetail = null;
		if(soId !=null || !soId.equals("")) {
			try{
				serviceOrderDetail = serviceOrderDao.retrieveSoDetail(soId);
			}
			catch(DataServiceException dse){
				logger.error("getServiceOrderDetail()-->EXCEPTION-->"+ dse);
			}
		}
		return serviceOrderDetail;
	}
	
	private String getBuyerResourceAlertAddress(String soId){
		ServiceOrderDetail serviceOrder = getServiceOrderDetail(soId);
		String email = "";
		if (serviceOrder != null){
			//BuyerDetail buyerDetail = serviceOrder.getBuyer();
			//email = buyerDetail.getEmail();
			Contact buyerResourceContact = serviceOrder.getBuyerResourceSupportContact();
			if(buyerResourceContact != null && buyerResourceContact.getEmail() != null){
				email = serviceOrder.getBuyerResourceSupportContact().getEmail();
			}
		}
		return email;	
	}
	
	private String getBuyerAlertAddress(String soId){
		ServiceOrderDetail serviceOrder = getServiceOrderDetail(soId);
		String address = "";
		if (serviceOrder!=null){
			//BuyerDetail buyerDetail = serviceOrder.getBuyer();
			//email = buyerDetail.getEmail();
			
			if(serviceOrder.getBuyer() != null && serviceOrder.getBuyer().getBuyerContact() != null){
				serviceOrder.getBuyer().getBuyerContact();
				if(serviceOrder.getBuyer().getBuyerContact().getAltEmail() != null){
					address = serviceOrder.getBuyer().getBuyerContact().getAltEmail();  								
				}else{
				address = serviceOrder.getBuyer().getBuyerContact().getEmail();
			}
		}
		}
		return address;		
	}
	
	private String getProviderAlertAddress(String soId){ 
		String emailAddr = "";
		String altemailAddr="";
		ServiceOrderDetail serviceOrder = getServiceOrderDetail(soId);
		if (serviceOrder!=null){
			long contactIds[] = new long[1];
			if (serviceOrder.getAcceptedProvider() !=null && serviceOrder.getAcceptedProvider().getEmail() !=null){
				altemailAddr=serviceOrder.getAcceptedProvider().getAltEmail()+EMAIL_DELIMITER+altemailAddr;
				emailAddr = serviceOrder.getAcceptedProvider().getEmail()+EMAIL_DELIMITER;
				/**********************************************************
				 * LMA...Iteration18 Story 25437 
				 * (see AlertDispositionProcessor.determineProvEmailAddr()
				 * Need to get all of the contactIds and pass it into the 
				 * determinProvEmailAddr()
				 ***********************************************************/
				if (serviceOrder.getAcceptedProvider().getContactId()!=null){
					contactIds[0] = serviceOrder.getAcceptedProvider().getContactId();
	
					//emailAddr = determineProvEmailAddr(soId);
				}	
				// * LMA END
			}
		}
		return emailAddr;
		
	}
	
	private String getProviderAlertAddressForReleasedSO(String soId) { 
		String emailAddr = "";
		try {
			Contact contact = contactDao.queryBySOID(soId);
			if (contact != null){
				if(StringUtils.isNotBlank(contact.getEmail())) {
					emailAddr = contact.getEmail();
				}
				if(StringUtils.isNotBlank(contact.getAltEmail())) {
					emailAddr += EMAIL_DELIMITER + contact.getAltEmail();
				}
	
			}
		} catch (DataServiceException dse) {
			logger.error("getProviderAlertAddressForReleasedSO()-->EXCEPTION-->",dse);
		}
		return emailAddr;
		
	}
	
	@Deprecated
	private String getAllProviderAlertAddresses(Template template){
		String strEmail="";
		try{
			ArrayList<ProviderDetail> list = (ArrayList<ProviderDetail>)alertDao.retrieveAllVendors();
			long contactIds[] = new long[list.size()];

			for (int r=0;r<list.size();r++){
				ProviderDetail pd = list.get(r);
				/**********************************************************
				 * LMA...Iteration18 Story 25437 
				 * (see AlertDispositionProcessor.determineProvEmailAddr()
				 * Need to get all of the contactIds and pass it into the 
				 * determinProvEmailAddr()
				 ***********************************************************/
				contactIds[r] = pd.getContactId();

				
					if(template.getTemplateTypeId() == TEMPLATE_TYPE_EMAIL) {
						String pref = pd.getEmailPreference();
						if(pref.equals("Y")) {
							strEmail = strEmail+pd.getEmail()+EMAIL_DELIMITER;
						}
					}
					else if (template.getTemplateTypeId() == TEMPLATE_TYPE_SMS){
						String pref = pd.getSmsPreference();
						if(pref.equals("Y")) {
							strEmail = strEmail+pd.getMobileNo()+EMAIL_DELIMITER;							
						}							
					}

			}
			
			/**********************************************************
			 * LMA...Iteration18 Story 25437 
			 * (see AlertDispositionProcessor.determineProvEmailAddr()
			 * Need to get all of the contactIds and pass it into the 
			 * determinProvEmailAddr()
			 ***********************************************************/
			if (contactIds.length > 0){
				//strEmail = determineProvEmailAddr(contactIds, strEmail);
			}
			// * LMA END

		}
		catch(DataServiceException dse){
				logger.error("getAllProviderAlertAddresses()-->EXCEPTION-->",dse);
		}
		
		return strEmail;
	}
	
	//TODO: Chnage the query in the serviceOrderMap.xml to fetch Accepted Vendor Contact information from so_contact
	// table instead of contact table. 
	// Dependency - after copy of Vendor Resource Contact information is copied from contact to so_contact table
	
	
	private String getAcceptedVendorResourceAddress(Template template, String soId){
		String strAlertAddress="";
		ServiceOrder serviceOrder = null;
		VendorResource vendorResource = null;
		Contact resourceContact = null;
		try{ 
			serviceOrder = serviceOrderDao.getServiceOrder(soId);
		}
		catch(DataServiceException dse){
			logger.error("getAcceptedVendorResourceAddress()-->EXCEPTION-->"+ dse);
		}
		if (serviceOrder!= null){ 
			vendorResource = serviceOrder.getAcceptedResource();
		}
		if (vendorResource!=null){
			resourceContact = vendorResource.getResourceContact();
			if(resourceContact!=null){ 
				long contactIds[] = new long[1];
				if (template.getTemplateTypeId() == TEMPLATE_TYPE_EMAIL) {
					strAlertAddress = resourceContact.getEmail()+EMAIL_DELIMITER;
				}
				else if(template.getTemplateTypeId() == TEMPLATE_TYPE_SMS){
					strAlertAddress=resourceContact.getCellNo()+EMAIL_DELIMITER;
				}
				/**********************************************************
				 * LMA...Iteration18 Story 25437 
				 * (see AlertDispositionProcessor.determineProvEmailAddr()
				 * Need to get all of the contactIds and pass it into the 
				 * determinProvEmailAddr()
				 ***********************************************************/
				if (resourceContact.getContactId() != null){
					contactIds[0] = resourceContact.getContactId();
	
					//strAlertAddress = determineProvEmailAddr(contactIds, strAlertAddress);
				}
				// * LMA END
				
			}
		}
		return strAlertAddress;
	}
	
	@Deprecated
	private String getAllBuyerAlertAddresses(Template template){
		String strEmail="";		
		ArrayList<BuyerDetail> al =null;
		try{ 
			al = (ArrayList)alertDao.getAllBuyerDetail();
		}
		catch(DataServiceException dse){
			logger.error("getAllBuyerAlertAddresses()-->EXCEPTION-->"+ dse);
		}
		for (int r=0;r<al.size();r++){
			BuyerDetail buyer = (BuyerDetail)al.get(r);
			if(template.getTemplateTypeId() == TEMPLATE_TYPE_EMAIL) {
				if(buyer.getEmailPreference().equals("Y")){
					strEmail = strEmail + buyer.getEmail() + EMAIL_DELIMITER;	
				}
			}
			else if(template.getTemplateTypeId() == TEMPLATE_TYPE_SMS){
				if(buyer.getSmsPreference().equals("Y")){
					strEmail = strEmail + buyer.getMobileNo() + EMAIL_DELIMITER;	
				}
			}
		}
		return strEmail;
	}
	
	private String getAllRoutedVendorResourceAddresses(Template template, String soId, String filterCriteria){ 
		String strAlertAddress="";
		ServiceOrder serviceOrder = null;
		try{ 
			serviceOrder = serviceOrderDao.getServiceOrder(soId);
		}
		catch(DataServiceException dse){
			logger.error("getAllAcceptedVendorResourceAddresses()-->EXCEPTION-->"+ dse);
		}
		if (serviceOrder!= null){ 
			List list = serviceOrder.getRoutedResources();
			long contactIds[] = new long[list.size()];

			for (int i=0;i<list.size();i++) {
				RoutedProvider routedProvider = (RoutedProvider)list.get(i);
				if (routedProvider!=null){
					Contact contact = routedProvider.getProviderContact();
					if(contact!=null){ 
						/**********************************************************
						 * LMA...Iteration18 Story 25437 
						 * (see AlertDispositionProcessor.determineProvEmailAddr()
						 * Need to get all of the contactIds and pass it into the 
						 * determinProvEmailAddr()
						 ***********************************************************/
						contactIds[i] = contact.getContactId();
						//* LMA END
						
						if (template.getTemplateTypeId() == TEMPLATE_TYPE_EMAIL) {
							if(includeRoutedVendorResourceAddress(filterCriteria, routedProvider.getProviderRespId())){
									strAlertAddress = strAlertAddress + contact.getEmail() + EMAIL_DELIMITER;
							}
						}
						else if(template.getTemplateTypeId() == TEMPLATE_TYPE_SMS){
							if(includeRoutedVendorResourceAddress(filterCriteria, routedProvider.getProviderRespId())){
									strAlertAddress = strAlertAddress + contact.getCellNo() + EMAIL_DELIMITER;
							}
						}
					}
				}
			}

			/**********************************************************
			 * LMA...Iteration18 Story 25437 
			 * (see AlertDispositionProcessor.determineProvEmailAddr()
			 * Need to get all of the contactIds and pass it into the 
			 * determinProvEmailAddr()
			 ***********************************************************/
			if (contactIds.length > 0){
				//strAlertAddress = determineProvEmailAddr(contactIds, strAlertAddress);
			}
			// * LMA END
			
		}
	return strAlertAddress;

	}
	
	private boolean includeRoutedVendorResourceAddress(String filterCriteria, Integer providerRespId){
		boolean include = false;
		if(filterCriteria != null && ALERT_ROLE_ALL_ROUTED_RESOURCES_RELEASE.equals(filterCriteria)){
			if(!(providerRespId != null && 
					(OrderConstants.REJECTED.equals(providerRespId) ||
					OrderConstants.RELEASED.equals(providerRespId)))){
				
				include = true;
			}
		}
		else if(filterCriteria != null && ALERT_ROLE_ALL_ROUTED_RESOURCES.equals(filterCriteria)){
			include = true;
		}
		return include;
	}
	
	@Deprecated
	private String getAllAdminAlertAddresses(Template template){
		String strEmail="";		
		try{
			ArrayList<Contact> adminList = (ArrayList)alertDao.retrieveAllAdmins();
			long contactIds[] = new long[adminList.size()];

			for (int i=0;i<adminList.size();i++){
				Contact contact = (Contact)adminList.get(i);
				/**********************************************************
				 * LMA...Iteration18 Story 25437 
				 * (see AlertDispositionProcessor.determineProvEmailAddr()
				 * Need to get all of the contactIds and pass it into the 
				 * determinProvEmailAddr()
				 ***********************************************************/
				contactIds[i] = contact.getContactId();
				//* LMA END

				if(template.getTemplateTypeId()== TEMPLATE_TYPE_EMAIL) {
					if(contact.getEmailPreference().equals("Y")){
						strEmail = strEmail + contact.getEmail() + EMAIL_DELIMITER;	
					}
				}
				else if(template.getTemplateTypeId()== TEMPLATE_TYPE_SMS){
					if(contact.getSmsPreference().equals("Y")){
						strEmail = strEmail + contact.getCellNo() + EMAIL_DELIMITER;	
					}
				}
			}
			
			/**********************************************************
			 * LMA...Iteration18 Story 25437 
			 * (see AlertDispositionProcessor.determineProvEmailAddr()
			 * Need to get all of the contactIds and pass it into the 
			 * determinProvEmailAddr()
			 ***********************************************************/
			if (contactIds.length > 0){
				//strEmail = determineProvEmailAddr(contactIds, strEmail);
			}
			// * LMA END
			
		}
		catch(DataServiceException dse){
			logger.error("getAllAdminAlertAddresses()-->EXCEPTION-->"+ dse);
		}
	return strEmail;
	}	
	
	
	private String getAllRoutedProvidersExceptAccepted(String soId){
		String email="";
		try{
		ArrayList<AlertProviderEmail> proEmails= alertDao.getAllRoutedProvidersExceptAccepted(soId);	
		for(int r=0;r<proEmails.size();r++){
			
			email+=proEmails.get(r).getProviderAdminEmail()+ EMAIL_DELIMITER;
		}
		}
		catch(DataServiceException dse){
			logger.error("getAllRoutedProvidersExceptAccepted-->EXCEPTION-->"+ dse);
		}
		return email;
	}


	private AlertBuyerEmail  getBuyerAdminEmailAddr(String soId) {
		AlertBuyerEmail email = null;
		try{
	
		email = alertDao.getBuyerAdminEmailAddr(soId);
		}
		catch(DataServiceException dse){
			logger.error("getBuyerAdminEmailAddr-->EXCEPTION-->"+ dse);
		}
		return email;
		
	}
	// Need to look at caller for null check
	private AlertBuyerEmail  getSOCreatorEmailAddr(String soId) {
		AlertBuyerEmail email = null;
		try{
	
			email = alertDao.getSOCreatorEmailAddr(soId);
		}catch(DataServiceException dse){
			logger.error("getSOCreatorEmailAddr-->EXCEPTION-->"+ dse);
		}
		return email;
	}
	
	private String formatAddressForWCI(String alertDisposition, Integer vid){
		String strAlertAddress = "";
		if (ALERT_ROLE_SERVICELIVE.equals(alertDisposition)) {
			strAlertAddress= PropertiesUtils.getPropertyValue(Constants.AppPropConstants.SERVICE_LIVE_MAILID);
		}else if(LOGGED_IN_PROVIDER.equals(alertDisposition)){
			strAlertAddress = getLoggedInProvider(vid);
		}
		return strAlertAddress;
	}
	private String getLoggedInProvider(Integer vid){
		String loggedInProvider="";
		try{
			
			loggedInProvider = alertDao.getLoggedInProvider(vid);
		}catch(DataServiceException dse){
			logger.error("getLoggedInProvider-->EXCEPTION-->"+ dse);
		}
		return loggedInProvider;
	}
	
	/*
	 * This method returns a hashMap with dispositions of an alert message
	 * The following are the notations 
	 * A : Admins
	 * AB: All Buyer
	 * AP: All Provider
	 * AV: Accepted Vendor ( aka Provider)
	 * ALL_RVR: All Routed Vendor Resources
	 * AVR: Accepted Vendor Resource (aka Technician)
	 * B: Buyer
	 * SL: Service Live 
	 * X: Contextual 
	 */
	private String formatAddress(String alertDisposition, String soId, Template template,Integer loggedInUserId){
		String strAlertAddress = "";
		if (ALERT_ROLE_CLIENT_ASSURANT.equals(alertDisposition)) {
			strAlertAddress = "AssurantFTPDispatcher";
		}
		if (ALERT_ROLE_CLIENT_HSR.equals(alertDisposition)) {
			strAlertAddress = "HSRFTPDispatcher";
		}		
		else if (ALERT_ROLE_CLIENT_ASSURANT_EMAIL.equals(alertDisposition)) {
			strAlertAddress = getAssurantEmailAddress();
		}
		else if (ALERT_ROLE_BUYER.equals(alertDisposition)) {
			strAlertAddress = getBuyerAlertAddress(soId);
		}
		else if (ALERT_ROLE_BUYER_RESOURCE.equals(alertDisposition)) {
			strAlertAddress = getBuyerResourceAlertAddress(soId);
		}
		else if (ALERT_ROLE_ACCEPTED_PROVIDERS.equals(alertDisposition)) {
			if(AOPConstants.AOP_RELEASE_SO_ACTIVE_PROV_TEMPLATE_ID == template.getTemplateId()) {
					strAlertAddress = getProviderAlertAddressForReleasedSO(soId);
				}else{
					strAlertAddress = getProviderAlertAddress(soId);
				}
		}
		if (ALERT_ROLE_ALL_PROVIDERS.equals(alertDisposition)) {  
			strAlertAddress = getAllProviderAlertAddresses(template);
		}
		else if (ALERT_ROLE_ALL_BUYERS.equals(alertDisposition)) {
			strAlertAddress = getAllBuyerAlertAddresses(template);
		}
		else if (ALERT_ROLE_SERVICELIVE.equals(alertDisposition)) {
			return PropertiesUtils.getPropertyValue(Constants.AppPropConstants.SERVICE_LIVE_MAILID);
		}
		else if (ALERT_ROLE_SERVICELIVE_SUPPORT.equals(alertDisposition)) {
			return PropertiesUtils.getPropertyValue(Constants.AppPropConstants.SERVICE_LIVE_SUPPORT_MAILID);
		}
		else if (ALERT_ROLE_ADMIN.equals(alertDisposition)) {		
			strAlertAddress = getAllAdminAlertAddresses(template);
		}
		else if (ALERT_ROLE_BUYER_AND_PROVIDER.equals(alertDisposition)){
			String provAlertEmail = "";
			// For release SO, the accepted vendor resource id will be null and hence
			// other method is used to fetch the provider who releases the SO.
			if(AOPConstants.AOP_RELEASE_SO_ACCEPT_TEMPLATE_ID == template.getTemplateId()
				|| AOPConstants.AOP_RELEASE_SO_PROB_TEMPLATE_ID == template.getTemplateId()
				|| AOPConstants.AOP_RELEASE_SO_ACTIVE_PROV_TEMPLATE_ID == template.getTemplateId()) {
				provAlertEmail = getProviderAlertAddressForReleasedSO(soId);
			} else {
				provAlertEmail = getProviderAlertAddress(soId);
			}
			strAlertAddress = getBuyerAlertAddress(soId) + EMAIL_DELIMITER + provAlertEmail;
		}
		else if(ALERT_ROLE_ACCEPTED_VENDOR_RESOURCE.equals(alertDisposition)){ 
			strAlertAddress = getAcceptedVendorResourceAddress(template, soId);
		}
		else if (ALERT_ROLE_ALL_ROUTED_RESOURCES.equals(alertDisposition)){			
			//strAlertAddress = getAllRoutedVendorResourceAddresses(template, soId, ALERT_ROLE_ALL_ROUTED_RESOURCES);
			strAlertAddress = determineProvEmailAddr(soId,template);
			//	provEmailAddress = filterDuplicates(strAlertAddress, provEmailAddress);
			//	strAlertAddress = strAlertAddress + EMAIL_DELIMITER+ provEmailAddress;
		}	
		else if (ALERT_ROLE_ALL_ROUTED_RESOURCES_RELEASE.equals(alertDisposition)){    
			strAlertAddress = getAllRoutedVendorResourceAddresses(template, soId, ALERT_ROLE_ALL_ROUTED_RESOURCES_RELEASE);
			
		}
		else if (ALERT_ROLE_PROVIDER_ADMIN.equals(alertDisposition))
		{
			// For release SO scenarios, only the admin of the provider who released should be fetched
			// and not all the admins.
			if(AOPConstants.AOP_RELEASE_SO_ACCEPT_TEMPLATE_ID == template.getTemplateId()
					|| AOPConstants.AOP_RELEASE_SO_PROB_TEMPLATE_ID == template.getTemplateId()
					|| AOPConstants.AOP_RELEASE_SO_ACTIVE_PROV_TEMPLATE_ID == template.getTemplateId()) {
				strAlertAddress = determineProvAdminEmailAddrForReleasedSO(soId,template);
			} if(AOPConstants.AOP_REJECT_SO_TEMPLATE_ID == template.getTemplateId()){
				strAlertAddress = determineProvAdminEmailAddrForRejectedSO(soId,template);
			}else if(AOPConstants.AOP_CHANGE_OF_SCOPE_SO_TEMPLATE_ID == template.getTemplateId()){
				strAlertAddress = determineProvAdminEmailAddrForChangeOfScopeSO(soId,template);
			}else {
				strAlertAddress = determineProvAdminEmailAddr(soId,template);
			}
		}
		
		else if(ALERT_ROLE_BUYER_ADMIN_AND_SO_CREATOR.equals(alertDisposition))
		{
			// fix for null check
			AlertBuyerEmail adminEmail = getBuyerAdminEmailAddr(soId);
			strAlertAddress = (adminEmail==null)?"":adminEmail.getBuyerAdminEmail();
			String altBuyerEmailAddress = (adminEmail==null)?"":adminEmail.getAltEmailId();
			AlertBuyerEmail creatorEmail = getSOCreatorEmailAddr(soId);
			String soCreatorAddress = (creatorEmail==null)?"":creatorEmail.getBuyerAdminEmail();
			String altSOCreatorEmailAddress = (creatorEmail==null)?"":creatorEmail.getAltEmailId();
			
			if (strAlertAddress==null){
				strAlertAddress="";
			}
			
			if (soCreatorAddress==null){
				soCreatorAddress="";
			}
			if(StringUtils.isNotBlank(strAlertAddress)&&StringUtils.isNotBlank(soCreatorAddress)){					
			if(!strAlertAddress.equalsIgnoreCase(soCreatorAddress)){
			strAlertAddress = strAlertAddress + EMAIL_DELIMITER+ soCreatorAddress + EMAIL_DELIMITER;
			}else{
				strAlertAddress=strAlertAddress + EMAIL_DELIMITER;
			}				
			}else if(StringUtils.isNotBlank(strAlertAddress)&& StringUtils.isBlank(soCreatorAddress)){
				strAlertAddress=strAlertAddress + EMAIL_DELIMITER;
			}else if(StringUtils.isNotBlank(soCreatorAddress)&& StringUtils.isBlank(strAlertAddress)){
				strAlertAddress=soCreatorAddress + EMAIL_DELIMITER;
			}else if(StringUtils.isBlank(strAlertAddress)&& StringUtils.isBlank(soCreatorAddress)){						
				strAlertAddress="";
			}
			//for adding the alternate email addresses of buyer admin and SO creator if present
			StringBuilder emailStringBuilder = new StringBuilder();
			if(StringUtils.isNotBlank(altBuyerEmailAddress)){
				emailStringBuilder.append(altBuyerEmailAddress).append(EMAIL_DELIMITER);
			}else{
				altBuyerEmailAddress = "";
			}
			if(StringUtils.isNotBlank(altSOCreatorEmailAddress) && !altBuyerEmailAddress.equalsIgnoreCase(altSOCreatorEmailAddress)){
				emailStringBuilder.append(altSOCreatorEmailAddress).append(EMAIL_DELIMITER);
			}
			if(null!=emailStringBuilder){
				strAlertAddress = strAlertAddress + emailStringBuilder.toString();
			}
			
		}else if(ALERT_ROLE_BUYER_CONTACTS_AND_ACCEPTED_VENDOR_RESOURCE.equals(alertDisposition)){
			// fix for null check

			AlertBuyerEmail adminEmail = getBuyerAdminEmailAddr(soId);
			strAlertAddress = (adminEmail==null)?"":adminEmail.getBuyerAdminEmail();
			AlertBuyerEmail creatorEmail = getSOCreatorEmailAddr(soId);
			String soCreatorAddress = (creatorEmail==null)?"":creatorEmail.getBuyerAdminEmail();
			
			if (strAlertAddress==null){
				strAlertAddress="";
			}
			
			if (soCreatorAddress==null){
				soCreatorAddress="";
			}
			if(StringUtils.isNotBlank(strAlertAddress)&&StringUtils.isNotBlank(soCreatorAddress)){					
			if(!strAlertAddress.equalsIgnoreCase(soCreatorAddress)){
			strAlertAddress = strAlertAddress + EMAIL_DELIMITER+ soCreatorAddress + EMAIL_DELIMITER;
			}else{
				strAlertAddress= strAlertAddress + EMAIL_DELIMITER;
			}				
			}else if(StringUtils.isNotBlank(strAlertAddress)&& StringUtils.isBlank(soCreatorAddress)){
				strAlertAddress= strAlertAddress +EMAIL_DELIMITER;
			}else if(StringUtils.isNotBlank(soCreatorAddress)&& StringUtils.isBlank(strAlertAddress)){
				strAlertAddress=soCreatorAddress +EMAIL_DELIMITER;
			}else if(StringUtils.isBlank(strAlertAddress)&& StringUtils.isBlank(soCreatorAddress)){						
				strAlertAddress="";
			}
		
			 String strAcceptedVendorResource=getAcceptedVendorResourceAddress(template, soId);
			 if(strAcceptedVendorResource==null){
				 strAcceptedVendorResource="";
			 }
			 
			 strAlertAddress=strAcceptedVendorResource + strAlertAddress + EMAIL_DELIMITER;
						
		}else if(ALERT_ROLE_ALL_ROUTED_PROVIDERS_EXCEPT_ACCEPTED.equals(alertDisposition)){
			strAlertAddress = getAllRoutedProvidersExceptAccepted(soId);
		}		
		else if (ALERT_ROLE_B_OR_P.equals(alertDisposition) ){ 
			ServiceOrder serviceOrder=new ServiceOrder();			
			try{
				 serviceOrder = serviceOrderDao.getServiceOrder(soId);
			}catch(Exception exception){
				
			}
			
			//If BUYER added notes
			if(loggedInUserId != null && (OrderConstants.BUYER_ROLEID == loggedInUserId.intValue() || OrderConstants.SIMPLE_BUYER_ROLEID == loggedInUserId.intValue())){
				if(OrderConstants.ROUTED_STATUS==serviceOrder.getWfStateId()){
					strAlertAddress =getAllRoutedVendorResourceAddresses(template, soId, ALERT_ROLE_ALL_ROUTED_RESOURCES_RELEASE) + determineProvAdminEmailAddrForAddNotes (soId,template);
				}else{
					String provAlertEmail = getProviderAlertAddress(soId);
					provAlertEmail = (StringUtils.isNotBlank(provAlertEmail))? provAlertEmail: "";
					String provAdminEmail = determineProvAdminEmailAddrAddNotesAccepted(soId,template);
					if(provAlertEmail.equals(provAdminEmail)){
						strAlertAddress = provAlertEmail;
					} else {				
						strAlertAddress =provAlertEmail + EMAIL_DELIMITER + provAdminEmail; 
					}
				}
			}
			
			//If PROVIDER added notes
			if(loggedInUserId != null && OrderConstants.PROVIDER_ROLEID == loggedInUserId.intValue()){				
				//To get Buyer Admin
				AlertBuyerEmail adminEmail = getBuyerAdminEmailAddr(soId);
				strAlertAddress = (adminEmail==null)?"":adminEmail.getBuyerAdminEmail();
				String altBuyerEmailAddress = (adminEmail==null)?"":adminEmail.getAltEmailId();
				AlertBuyerEmail creatorEmail = getSOCreatorEmailAddr(soId);
				String soCreatorAddress = (creatorEmail==null)?"":creatorEmail.getBuyerAdminEmail();
				String altSOCreatorEmailAddress = (creatorEmail==null)?"":creatorEmail.getAltEmailId();
		
				if (strAlertAddress==null){
					strAlertAddress="";
				}				
				if (soCreatorAddress==null){
					soCreatorAddress="";
				}
				if(StringUtils.isNotBlank(strAlertAddress)&&StringUtils.isNotBlank(soCreatorAddress)){					
				if(!strAlertAddress.equalsIgnoreCase(soCreatorAddress)){
				strAlertAddress = strAlertAddress + EMAIL_DELIMITER+ soCreatorAddress + EMAIL_DELIMITER;
				}else{
					strAlertAddress=strAlertAddress + EMAIL_DELIMITER;
				}				
				}else if(StringUtils.isNotBlank(strAlertAddress)&& StringUtils.isBlank(soCreatorAddress)){
					strAlertAddress=strAlertAddress + EMAIL_DELIMITER;
				}else if(StringUtils.isNotBlank(soCreatorAddress)&& StringUtils.isBlank(strAlertAddress)){
					strAlertAddress=soCreatorAddress + EMAIL_DELIMITER;
				}else if(StringUtils.isBlank(strAlertAddress)&& StringUtils.isBlank(soCreatorAddress)){						
					strAlertAddress="";
				}		
				
				strAlertAddress = strAlertAddress + EMAIL_DELIMITER+ getBuyerAlertAddress(soId)+ EMAIL_DELIMITER;
				//for adding the alternate email addresses of buyer admin and SO creator if present
				StringBuilder emailStringBuilder = new StringBuilder();
				if(StringUtils.isNotBlank(altBuyerEmailAddress)){
					emailStringBuilder.append(altBuyerEmailAddress).append(EMAIL_DELIMITER);
				}else{
					altBuyerEmailAddress = "";
				}
				if(StringUtils.isNotBlank(altSOCreatorEmailAddress) && !altBuyerEmailAddress.equalsIgnoreCase(altSOCreatorEmailAddress)){
					emailStringBuilder.append(altSOCreatorEmailAddress).append(EMAIL_DELIMITER);
				}
				if(null!=emailStringBuilder){
					strAlertAddress = strAlertAddress + emailStringBuilder.toString();
				}
			}			
		}		
		return strAlertAddress;
	}
	
	private String getAssurantEmailAddress() {
		return PropertiesUtils.getPropertyValue(Constants.AppPropConstants.ASSURANT_INCIDENT_CANCELLATION_REQUEST_MAILID);
	}

	private String formatFromAddress(String alertDisposition , String soId, Template template,Integer loggedInUserId){
		return formatAddress(alertDisposition, soId, template,loggedInUserId);
	}
	
	private String formatToAddress(String alertDisposition , String soId, Template template,Integer loggedInUserId){
		return formatAddress(alertDisposition, soId,template,loggedInUserId );

	}
	
	private String formatCcAddress(String alertDisposition , String soId, Template template,Integer loggedInUserId){
		String str ="";
		if (alertDisposition !=  null) {
			if (!alertDisposition.equals("")){
				str = formatAddress(alertDisposition, soId, template,loggedInUserId);
			}
		}
		return str;
	}
	
	private String formatBccAddress(String alertDisposition , String soId, Template template,Integer loggedInUserId){
		String str ="";
		if (alertDisposition != null) {
			if (!alertDisposition.equals("")){
				str = formatAddress(alertDisposition, soId, template,loggedInUserId );
			}
		}
		return str;
	}
	
	private String formatFromAddressForWCI(String alertDisposition , Integer vid){
		return formatAddressForWCI(alertDisposition, vid);
	}
	
	private String formatToAddressForWCI(String alertDisposition , Integer vid){
		return formatAddressForWCI(alertDisposition, vid );

	}
	
	private String formatCcAddressForWCI(String alertDisposition , Integer vid){
		String str ="";
		if (alertDisposition !=  null) {
			if (!alertDisposition.equals("")){
				str = formatAddressForWCI(alertDisposition,vid);
			}
		}
		return str;
	}
	
	private String formatBccAddressForWCI(String alertDisposition , Integer vid){
		String str ="";
		if (alertDisposition != null) {
			if (!alertDisposition.equals("")){
				str = formatAddressForWCI(alertDisposition,vid);
			}
		}
		return str;
	}
	
	
	public Map<String, Object> formatContextualAddress(Map<String, Object> alertHashMap, String soId, Template template, Map<String, Object> aopHashMap){

		if(aopHashMap != null && aopHashMap.get(AOPConstants.ALERT_TO) != null && (!("").equals(aopHashMap.get(AOPConstants.ALERT_TO)))){
			alertHashMap.put(ALERT_DISP_TO, aopHashMap.get(AOPConstants.ALERT_TO));
		}
		if(aopHashMap != null && aopHashMap.get(AOPConstants.ALERT_CC) != null && (!("").equals(aopHashMap.get(AOPConstants.ALERT_CC)))){
			alertHashMap.put(ALERT_DISP_CC, aopHashMap.get(AOPConstants.ALERT_CC));
		}
		if(aopHashMap != null && aopHashMap.get(AOPConstants.ALERT_BCC) != null && (!("").equals(aopHashMap.get(AOPConstants.ALERT_BCC)))){
			alertHashMap.put(ALERT_DISP_BCC, aopHashMap.get(AOPConstants.ALERT_BCC));
		}
		
		return alertHashMap;

	}
	/*
	 * This private method returns a Provider Admin Email
	 * parameters String soId
	 * parameters Template
	 * return String
	 */
	private String determineProvAdminEmailAddrForChangeOfScopeSO(String soId ,Template template){
		String strAlertAddr = "";
		try{
			strAlertAddr = alertDao.getProvAdminEmailAddrForChangeOfScopeSO(soId);	
		}catch (DataServiceException e){
			logger.error("determineProvAdminEmailAddrForChangeOfScopeSO", e);
		}
		return strAlertAddr;
	}
	
	public static void main(String k[]){
		AlertDispositionProcessor adp = new AlertDispositionProcessor();
		Template t = new Template();
		t.setTemplateId(new Integer("12"));
		AlertDisposition ap  = adp.getAlertDispositionDetail("problemServiceOrder", "001-6434041185-11",t, new HashMap<String, Object>());
		System.out.println(ap.toString());
	}

	public AlertDaoImpl getAlertDao() {
		return alertDao;
	}

	public void setAlertDao(AlertDaoImpl alertDao) {
		this.alertDao = alertDao;
	}

	public ServiceOrderDaoImpl getServiceOrderDao() {
		return serviceOrderDao;
	}

	public void setServiceOrderDao(ServiceOrderDaoImpl serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}

	public BuyerDaoImpl getBuyerDao() {
		return buyerDao;
	}

	public void setBuyerDao(BuyerDaoImpl buyerDao) {
		this.buyerDao = buyerDao;
	}

	public ContactDao getContactDao() {
		return contactDao;
	}

	public void setContactDao(ContactDao contactDao) {
		this.contactDao = contactDao;
	}
}
