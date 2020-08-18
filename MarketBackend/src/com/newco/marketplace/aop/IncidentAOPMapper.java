package com.newco.marketplace.aop;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.incident.IncidentResponseVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.interfaces.ClientConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.DateUtils;

public class IncidentAOPMapper extends BaseAOPMapper {

	private static final Logger logger = Logger.getLogger(IncidentAOPMapper.class);
	
	public IncidentAOPMapper() {
		super();
	}

	public IncidentAOPMapper(Object[] params){
		super(params);
	}

	public Map<String, Object> processIncidentResponse() {

		logger.debug("IncidentAOPMapper-->processIncidentResponse()");

		IncidentResponseVO incidentResponseVO = (IncidentResponseVO)params[0];
		
		HashMap<String, Object> hmParams = new AOPHashMap();
		hmParams.put(AOPConstants.AOP_USER_ID, incidentResponseVO.getResourceId());
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, incidentResponseVO.getRoleId());
		hmParams.put(AOPConstants.AOP_SO_ID, incidentResponseVO.getSoId());
		hmParams.put(AOPConstants.AOP_NOTE_SUBJECT, incidentResponseVO.getSubject());
		hmParams.put(AOPConstants.AOP_NOTE_TYPE, incidentResponseVO.getNoteTypeId());

		if(OrderConstants.BUYER_ROLEID == incidentResponseVO.getRoleId()) {
			hmParams.put(AOPConstants.AOP_ROLE, OrderConstants.BUYER_ROLE);
		} else if(OrderConstants.PROVIDER_ROLEID == incidentResponseVO.getRoleId()) {
			hmParams.put(AOPConstants.AOP_ROLE, OrderConstants.PROVIDER_ROLE);
		}

		Date currentDate = new Date();
        String strDate = DateUtils.getFormatedDate(currentDate, "EEE, d MMM yyyy HH:mm:ss");
        hmParams.put(AOPConstants.AOP_SUPPORT_NOTE_DATE, strDate);
		
		return hmParams;
		
	}
	
	public Map<String, Object> processIncidentResponsePostSOInjection(Map<String, Object> hmParams) {
		IncidentResponseVO incidentResponseVO = (IncidentResponseVO)params[0];
		String incidentTrackerAction = incidentResponseVO.getAction();
		ResourceBundle resBundle = ResourceBundle.getBundle("/resources/properties/servicelive_copy");
		String incidentStatus = resBundle.getString("incident.tracker.assurantStatus." + incidentTrackerAction);
		logger.info("Assurant Status = [" + incidentStatus + "]");
		ServiceOrder so = (ServiceOrder) hmParams.get(AOPConstants.AOP_SERVICE_ORDER);
		String clientIncidentId = incidentResponseVO.getClientIncidentId();
		SecurityContext securityContext = (SecurityContext)hmParams.get(AOPConstants.AOP_SECURITY_CONTEXT);
		Integer slIncidentId = getSLIncidentId(clientIncidentId, securityContext.getClientId(), so.getSoId(), so.getBuyer().getBuyerId());
		if (slIncidentId != null) {
			hmParams.put(OrderConstants.SL_INCIDENT_REFERNECE_KEY, slIncidentId);
			String desc = incidentResponseVO.getNoteDescription();
			if (desc.length() > 3000) {
				desc = desc.substring(0, ClientConstants.ASSURANT_OUTFILE_DESC_MAX_LENGTH);
			}
			hmParams = updatePostSOInjection(hmParams, incidentStatus, desc);
		}
		return hmParams;
	}
}
