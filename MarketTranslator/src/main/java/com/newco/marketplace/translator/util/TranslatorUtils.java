package com.newco.marketplace.translator.util;

import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.translator.business.IZipService;
import com.newco.marketplace.translator.business.impl.ZipService;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CustomRef;
import com.newco.marketplace.webservices.dto.serviceorder.LocationRequest;
import com.newco.marketplace.webservices.dto.serviceorder.NoteRequest;

public class TranslatorUtils {
	/**
	 * Method to get the order number from Reference fields
	 * @param CreateDraftRequest
	 * @return String orderNo
	 */
	public static String getOMSOrderNumberFromReferenceFields(CreateDraftRequest createDraftReq){
		String orderNo ="";
		CustomRef[] cusRefs = createDraftReq.getCustomRef().toArray(new CustomRef[0]);
		for(CustomRef ref : cusRefs){
			if (Constants.CUSTOM_REF_ORDER_NUM.equals(ref.getKey())) {
				orderNo = ref.getValue();
			}
		}
		return orderNo;
	}	
	/**
	 * Method to get the unit number from Reference fields
	 * @param CreateDraftRequest
	 * @return String unitNo
	 */
	public static String getOMSUnitNumberFromReferenceFields(CreateDraftRequest createDraftReq){
		String unitNo ="";
		CustomRef[] cusRefs = createDraftReq.getCustomRef().toArray(new CustomRef[0]);
		for(CustomRef ref : cusRefs){
			if (Constants.CUSTOM_REF_UNIT_NUM.equals(ref.getKey())) {
				unitNo = ref.getValue();
			}
		}
		return unitNo;
	}
	
	
	public static TimeZone zipCodeNotFoundActions(CreateDraftRequest request,List<NoteRequest> notes,String client) {
		IZipService zipService = (ZipService) SpringUtil.factory.getBean("ZipService");
		LocationRequest  serviceLocation = request.getServiceLocation();
		// Get the time-zone based on state; so that injection doesn't fail
		String stateCd = serviceLocation.getState();
		TimeZone localTimezone = zipService.getTimeZonesByState(stateCd).get(0); // Rule: Get the first one always; some state may have multiple timezones e.g. Indiana!
		String zip = serviceLocation.getZip();

		// Create a warning note with this Service Order
		NoteRequest note = new NoteRequest();
		note.setSubject("zip code not found in ServiceLive: " + zip);
		note.setNote("Visit http://www.zipcodedownload.com/ to verify the zip code: " + zip
				+ "\nEnter " + zip + " in the Search box"
				+ "\nContact ServiceLive Production Support if necessary");
		notes.add(note);
		request.setAutoRoute(new Boolean(false));	
		
		// Get order identifiers for email body
		String orderNo = null;
		String unitNo = null;
		String incidentId = null;
		if(client.equals(Constants.Client.OMS) || client.equals(Constants.Client.HSR))
		{
			for (CustomRef customRef : request.getCustomRef()) {
				
				if (Constants.CUSTOM_REF_ORDER_NUM.equals(customRef.getKey())) {
					orderNo = customRef.getValue();
				} else if (Constants.CUSTOM_REF_UNIT_NUM.equals(customRef.getKey())) {
					unitNo = customRef.getValue();
				} 
				if (orderNo != null && unitNo != null) {
					break;
				}
			}
		}
		else
		{
			for (CustomRef customRef : request.getCustomRef()) {
				if(Constants.INCIDENT_ID.equals(customRef.getKey())) {
					incidentId = customRef.getValue();
				}
				if (incidentId != null) {
					break;
				}
			}
		}
		
		// Get location details for email subject/body
		String street1 = serviceLocation.getStreet1();
		String street2 = serviceLocation.getStreet2();
		String aptNo = serviceLocation.getAptNo();
		String city = serviceLocation.getCity();
		String state = serviceLocation.getState();
		String zip4 = serviceLocation.getZip4();
		
		// Email subject and body
		StringBuilder emailSubject = new StringBuilder("Order Injection Warning: Missing ZIP Code: ").append(zip);
		StringBuilder emailBody = null;
		
		if(client.equals(Constants.Client.OMS))
			emailBody = new StringBuilder("Order Details:\r\n\tOrder No: ").append(orderNo).append("\r\n\tUnit No: ").append(unitNo);
		else
			emailBody = new StringBuilder("Order Details:\r\n\tIncident Id: ").append(incidentId);
		
		emailBody.append("\r\n\r\nLocation Details:\r\n\tStreet 1: ").append(street1)
										.append("\r\n\tStreet 2: ").append(street2)
										.append("\r\n\tApt No: ").append(aptNo)
										.append("\r\n\tCity: ").append(city)
										.append("\r\n\tState: ").append(state)
										.append("\r\n\tZip: ").append(zip);
		if (StringUtils.isNotBlank(zip4)) {
			emailBody.append("-").append(zip4);
		}
		emailBody.append("\r\n\r\nACTION NEEDED:")
		.append("\t\r\nValidate zip code using the Search tool on http://zip4.usps.com/zip4/citytown_zip.jsp")
		.append("\t\r\nTo update zip code data: http://www.zipcodedownload.com/")
		.append("\t\r\nlogin greg@servicelive.com/Test123! and download 'Fixed Width' data")
		.append("\t\r\nLocate zip code " + zip + " and create entry in the zip_goecode table")
		.append("\r\n\t\r\nVisit: http://jira.intra.sears.com/jira/browse/SL-8136 for details on how to load all new zip code data.");
	
		// Send email
		EmailSender.sendMessage(emailSubject, emailBody);
		return localTimezone;
	}
	
}
