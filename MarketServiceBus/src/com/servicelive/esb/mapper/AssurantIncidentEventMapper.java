package com.servicelive.esb.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.translator.business.IClientService;
import com.newco.marketplace.translator.business.IIncidentService;
import com.newco.marketplace.translator.dao.Client;
import com.newco.marketplace.translator.dao.Incident;
import com.newco.marketplace.translator.dao.IncidentContact;
import com.newco.marketplace.translator.dao.IncidentEvent;
import com.newco.marketplace.translator.dao.IncidentPart;
import com.newco.marketplace.translator.dao.WarrantyContract;
import com.newco.marketplace.translator.util.SpringUtil;
import com.servicelive.esb.constant.AssurantIncidentConstants;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.util.IncidentAlreadyExistsException;

public class AssurantIncidentEventMapper extends IncidentEventMapper implements Mapper {
	
	private Logger logger = Logger.getLogger(AssurantIncidentEventMapper.class);
	public IIncidentService incidentService = (IIncidentService) SpringUtil.factory.getBean("IncidentService");
	public IClientService clientService = (IClientService) SpringUtil.factory.getBean("ClientService");
	
	public IncidentEvent mapData(Object input) throws Exception {
		
		IncidentEvent incidentEvent = null;
		//the input should be a ~ delimited file with ^ being the second delimiter
		String incidentString = (String) input;
		if (null != incidentString) {
			SimpleDateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			incidentEvent = new IncidentEvent();
			//set up client object
			Client client = clientService.getClient(MarketESBConstant.Client.ASSURANT);
			String[] incidentFields = incidentString.split("~");
			String incidentStatus = incidentFields[AssurantIncidentConstants.STATUS];
			String receivedIncidentStatus = incidentFields[AssurantIncidentConstants.INCIDENT_ID];
			if (incidentStatus.equals("NEW") && incidentAlreadyExists(receivedIncidentStatus, client)) {
				// per business rule, we are ignoring the duplicate files
				throw new IncidentAlreadyExistsException(
						"A NEW incident file was received from Assurant when that incident already exists in ServiceLive. Assurant Incident ID: "
								+ receivedIncidentStatus);
			}
			String clientIncidentID = receivedIncidentStatus;
			incidentEvent.setAssociatedIncident(incidentFields[AssurantIncidentConstants.ASSOCIATED_INCIDENT]);
			incidentEvent.setAuthorizedAmount(incidentFields[AssurantIncidentConstants.AUTH_AMOUNT]);
			incidentEvent.setAuthorizingCode(incidentFields[AssurantIncidentConstants.AUTH_CODE]);
			IncidentContact contact = mapContact(incidentFields);
			incidentEvent.setContact(contact);
			incidentEvent.setCreatedDate(new Date());

			Incident incident = new Incident();
			incident.setClient(client);
			incident.setClientIncidentID(clientIncidentID);
			WarrantyContract warrantyContract = new WarrantyContract();
			warrantyContract.setClient(client);
			if (StringUtils.isNotBlank(incidentFields[AssurantIncidentConstants.CONTRACT_DATE])) {
				try {
					warrantyContract.setContractDate(dfmt.parse(incidentFields[AssurantIncidentConstants.CONTRACT_DATE]));
				}
				catch (ParseException e) {
					logger.error("Error parsing contract date for incident: " + incidentEvent.getIncident().getClientIncidentID(), e);
				}
			}
			warrantyContract.setContractNumber(incidentFields[AssurantIncidentConstants.CONTRACT_NUMBER]);
			warrantyContract.setContractTypeCode(incidentFields[AssurantIncidentConstants.CONTRACT_TYPE_CODE]);
			if (StringUtils.isNotBlank(incidentFields[AssurantIncidentConstants.EXP_DATE])) {
				try {
					warrantyContract.setExpirationDate(dfmt.parse(incidentFields[AssurantIncidentConstants.EXP_DATE]));
				}
				catch (ParseException e) {
					logger.error("Error parsing contract exp date for incident: " + incidentEvent.getIncident().getClientIncidentID(), e);
				}
			}
			incident.setWarrantyContract(warrantyContract);
			incidentEvent.setIncident(incident);
			
			//call bo
			incident = incidentService.getIncidentByIncidentEvent(incidentEvent);
			incidentEvent.setIncident(incident);
			incidentEvent.setIncidentComment(incidentFields[AssurantIncidentConstants.COMMENTS]);
			incidentEvent.setIncidentParts(mapParts(incidentFields, incidentEvent));
			incidentEvent.setManufacturer(incidentFields[AssurantIncidentConstants.MANUFACTURE]);
			incidentEvent.setModelNumber(incidentFields[AssurantIncidentConstants.MODEL_NUMBER]);
			incidentEvent.setNumberOfParts(incidentFields[AssurantIncidentConstants.NUMBER_OF_PARTS]);
			incidentEvent.setPartLaborIndicator(incidentFields[AssurantIncidentConstants.PART_LABOR_FLAG]);
			incidentEvent.setPartsWarrentySKU(incidentFields[AssurantIncidentConstants.PART_WARRENTY_SKU]);
			incidentEvent.setSpecialCoverageFlag(incidentFields[AssurantIncidentConstants.SPECIAL_COVERAGE_FLAG]);
			incidentEvent.setProductLine(incidentFields[AssurantIncidentConstants.PRODUCT_LINE]);
			incidentEvent.setRetailer(incidentFields[AssurantIncidentConstants.RETAILER]);
			incidentEvent.setSerialNumber(incidentFields[AssurantIncidentConstants.SERIAL_NUMBER]);
			incidentEvent.setServiceProviderLocation(incidentFields[AssurantIncidentConstants.SERVICE_PROVIDER_LOCATION]);
			incidentEvent.setServicerID(incidentFields[AssurantIncidentConstants.SERVICER_ID]);
			incidentEvent.setShippingMethod(incidentFields[AssurantIncidentConstants.SHIP_METHOD]);
			incidentEvent.setSupportGroup(incidentFields[AssurantIncidentConstants.SUPPORT_GROUP]);
			incidentEvent.setVendorClaimNumber(incidentFields[AssurantIncidentConstants.VENDOR_CLAIM_NUMBER]);
			incidentEvent.setWarrentyStatus(incidentFields[AssurantIncidentConstants.WARRENTY_STATUS]);
			incidentEvent.setStatus(incidentFields[AssurantIncidentConstants.STATUS]);
			if( ! incidentService.saveIncidentEvent(incidentEvent) )
				throw new Exception( "IncidnetEvent not saved" );
		}
		return incidentEvent;
	}

	private boolean incidentAlreadyExists(String receivedIncidentId, Client client) {
		Incident incident = incidentService.getIncidentByClientIncident(receivedIncidentId, client);
		return (incident != null && incident.getClientIncidentID().equals(receivedIncidentId));
	}

	/**
	 * Parts can be constructed from three subdelimited fields on the incidentEvent
	 * @param incidentFields
	 * @return
	 */
	private Set<IncidentPart> mapParts(String[] incidentFields, IncidentEvent incidentEvent) throws Exception {
		Set<IncidentPart> parts = new HashSet<IncidentPart>();
		String[] classCodes = StringUtils.splitPreserveAllTokens(incidentFields[AssurantIncidentConstants.CLASS_CODE], '^');
		String[] classComments = StringUtils.splitPreserveAllTokens(incidentFields[AssurantIncidentConstants.CLASS_COMMENTS], '^');
		String[] partNumbers = StringUtils.splitPreserveAllTokens(incidentFields[AssurantIncidentConstants.PART_NUMBER], '^');
		String[] partComments = StringUtils.splitPreserveAllTokens(incidentFields[AssurantIncidentConstants.PART_COMMENTS], '^');
		String[] oemNumbers = StringUtils.splitPreserveAllTokens(incidentFields[AssurantIncidentConstants.OEM_NUMBER], '^');
		if (classCodes.length == classComments.length 
				&& classComments.length == partNumbers.length 
				&& partNumbers.length == partComments.length
				&& partComments.length == oemNumbers.length) {
			for (int partIndex = 0; partIndex < classCodes.length; partIndex++) {
				IncidentPart part = new IncidentPart();
				String classCode = classCodes[partIndex].trim();
				if (StringUtils.isNotBlank(classCode)) {
					part.setClassCode(classCode);
					part.setClassComments(classComments[partIndex].trim());
					part.setIncidentEvent(incidentEvent);
					part.setCreatedDate(new Date());
					part.setOEMNumber(oemNumbers[partIndex].trim());
					part.setPartComments(partComments[partIndex].trim());
					part.setPartNumber(partNumbers[partIndex].trim());
					parts.add(part);
				}
			}
		}
		else {
			throw new Exception("Error parsing parts data.");
		}
		return parts;
	}

	private IncidentContact mapContact(String[] incidentFields) {
		IncidentContact contact = new IncidentContact();
		contact.setFirstName(incidentFields[AssurantIncidentConstants.FIRST_NAME]);
		contact.setLastName(incidentFields[AssurantIncidentConstants.LAST_NAME]);
		contact.setStreet1(incidentFields[AssurantIncidentConstants.ADDRESS1]);
		contact.setStreet2(incidentFields[AssurantIncidentConstants.ADDRESS2]);
		contact.setCity(incidentFields[AssurantIncidentConstants.CITY]);
		contact.setState(incidentFields[AssurantIncidentConstants.STATE]);
		contact.setZip(incidentFields[AssurantIncidentConstants.ZIP]);
		contact.setZipExt(incidentFields[AssurantIncidentConstants.PLUS4]);
		contact.setPhone1(incidentFields[AssurantIncidentConstants.PHONE]);
		contact.setPhone1Ext(incidentFields[AssurantIncidentConstants.PHONE_EXT]);
		contact.setPhone2(incidentFields[AssurantIncidentConstants.PHONE_ALT]);
		contact.setPhone2Ext(incidentFields[AssurantIncidentConstants.PHONE_ALT_EXT]);
		contact.setEmail(incidentFields[AssurantIncidentConstants.EMAIL_ADDRESS]);
		return contact;
	}

	public IIncidentService getIncidentService() {
		return incidentService;
	}

	public void setIncidentBO(IIncidentService incidentService) {
		this.incidentService = incidentService;
	}

}
