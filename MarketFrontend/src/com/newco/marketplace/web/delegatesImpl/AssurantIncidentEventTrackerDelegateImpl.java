package com.newco.marketplace.web.delegatesImpl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IIncidentBO;
import com.newco.marketplace.dto.vo.incident.IncidentResponseVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.web.delegates.IAssurantIncidentEventTrackerDelegate;
import com.newco.marketplace.web.utils.OFUtils;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

public class AssurantIncidentEventTrackerDelegateImpl implements
		IAssurantIncidentEventTrackerDelegate {

	private OFHelper ofHelper;
	private IIncidentBO incidentBO;
	
	public void notifyBuyer(IncidentResponseVO incidentResponseVO, String action, 
			SecurityContext securityContext) throws BusinessServiceException{
		
		String soID = incidentResponseVO.getSoId();
		if(getOfHelper().isNewSo(soID)){
			
			SONote soNote = createSoNote(incidentResponseVO);			
			
			OrderFulfillmentRequest request = new OrderFulfillmentRequest();
			request.setElement(soNote);
			request.setIdentification(OFUtils.createOFIdentityFromSecurityContext(securityContext));
			
			getOfHelper().runOrderFulfillmentProcess(soID, SignalType.NOTIFY_BUYER, request);
		} else {
			// This path will not end up going through the MarketESB, so remove the prepended
			// assurantStatusId from the subject
			String subject = incidentResponseVO.getSubject();
			int index = subject.indexOf(")");
			if (index > -1) {
				if (subject.length() > index + 2) {
					incidentResponseVO.setSubject(subject.substring(index + 1));
				} else {
					incidentResponseVO.setSubject("");
				}
			}
			
			getIncidentBO().processIncidentResponse(incidentResponseVO, securityContext);
		}

	}

	private SONote createSoNote(IncidentResponseVO incidentResponseVO) {
		SONote soNote = new SONote();
		soNote.setRoleId(incidentResponseVO.getRoleId());
		soNote.setSubject(incidentResponseVO.getSubject());
		soNote.setNote(incidentResponseVO.getNoteDescription());
		soNote.setNoteTypeId(incidentResponseVO.getNoteTypeId());
		soNote.setCreatedByName(incidentResponseVO.getCreatedByName());
		soNote.setCreatedDate(new Date(System.currentTimeMillis()));
		soNote.setModifiedBy(incidentResponseVO.getModifiedBy());
		soNote.setEntityId(incidentResponseVO.getEntityId().longValue());
		
		// Convert private indicator from string to boolean
		String privateIndString = incidentResponseVO.getPrivateInd();
		if (StringUtils.isEmpty(privateIndString) || !StringUtils.isNumeric(privateIndString)) {
			privateIndString = "0";
		}
		int privateIndInt = Integer.parseInt(privateIndString);
		soNote.setPrivate(privateIndInt != 0);
		
		return soNote;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}

	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setIncidentBO(IIncidentBO incidentBO) {
		this.incidentBO = incidentBO;
	}

	public IIncidentBO getIncidentBO() {
		return incidentBO;
	}

}
