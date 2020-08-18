/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 24-Jun-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.so.addNote.SOAddNoteRequest;
import com.newco.marketplace.api.beans.so.addNote.SOAddNoteResponse;
import com.newco.marketplace.api.security.SecurityProcess;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.SOAddNoteMapper;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

/**
 * This class is a service class for adding notes to a service order
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOAddNoteService {
	private Logger logger = Logger.getLogger(SOAddNoteService.class);
	private XStreamUtility conversionUtility;
	private SecurityProcess securityProcess;
	private IServiceOrderBO serviceOrderBO;
	ProcessResponse processResponse = new ProcessResponse();
	private SOAddNoteMapper addNoteMapper;

	/**
	 * This method dispatches the add note service order request.
	 * 
	 * @param String addNoteRequest
	 * @param String soId
	 * @throws Exception
	 * @return String
	 */
	public String dispatchAddNoteServiceRequest(String addNoteRequest,
			String soId) throws Exception {
		logger.info("Entering SOAddNoteService.dispatchAddNoteServiceRequest()");
		SOAddNoteResponse addNoteResponse = new SOAddNoteResponse();
		String responseString = null;
		SOAddNoteRequest soAddNoteRequest = conversionUtility
				.getAddNoteRequestObject(addNoteRequest);
		SecurityContext securityContext = securityProcess.getSecurityContext(
				soAddNoteRequest.getIdentification().getUsername(),
				soAddNoteRequest.getIdentification().getPassWord());
		if (securityContext == null) {
			logger.error("SecurityContext is null");
			throw new BusinessServiceException("SecurityContext is null");

		} else {
			// Getting service order details
			ServiceOrder serviceOrder = serviceOrderBO.getServiceOrder(soId);
			if (null != serviceOrder) {
				LoginCredentialVO lvRoles = securityContext.getRoles();
				Integer resourceId = securityContext.getVendBuyerResId();
				if (securityContext.isSlAdminInd()) {
					lvRoles.setRoleName(OrderConstants.NEWCO_ADMIN);
					lvRoles.setCompanyId(LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION);

					// If the admin-role id is not set, use the role id
					if (securityContext.getAdminRoleId() == -1) {
						resourceId = securityContext.getVendBuyerResId();
						lvRoles.setRoleId(securityContext.getRoleId());
					} else {
						resourceId = securityContext.getAdminResId();
						lvRoles.setRoleId(securityContext.getAdminRoleId());
					}
					lvRoles.setVendBuyerResId(securityContext.getAdminResId());
				}
				String createdBy = lvRoles.getLastName() + ", "
						+ lvRoles.getFirstName();
				String modifiedBy = lvRoles.getUsername();
				// Resource Id is set as Entity Id
				Integer entityId = resourceId;
				String privateInd = null;
				// Note Type is set as 2 for General Notes
				int noteType = PublicAPIConstant.TWO;
				boolean isEmptyNoteAllowed = false;
				boolean isEmailToBeSent = true;
				if (soAddNoteRequest.getNewNoteType().isPrivateInd()) {
					privateInd = PublicAPIConstant.ONE;
				} else {
					privateInd = PublicAPIConstant.ZERO;
				}
				logger.info("Going to invoke serviceOrderBO.processAddNote()");
				processResponse = serviceOrderBO.processAddNote(resourceId,
						lvRoles.getRoleId(), soId, soAddNoteRequest
								.getNewNoteType().getSubject(),
						soAddNoteRequest.getNewNoteType().getNoteBody(),
						noteType, createdBy, modifiedBy, entityId, privateInd,
						isEmailToBeSent, isEmptyNoteAllowed, securityContext);
				addNoteResponse = addNoteMapper
						.mapAddNoteResponse(processResponse);
				responseString = conversionUtility
						.getAddNoteResponseXML(addNoteResponse);
			} else {
				return null;
			}
		}
		logger.info("Leaving SOAddNoteService.dispatchAddNoteServiceRequest()");
		return responseString;
	}

	public XStreamUtility getConversionUtility() {
		return conversionUtility;
	}

	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
	}

	public SecurityProcess getSecurityProcess() {
		return securityProcess;
	}

	public void setSecurityProcess(SecurityProcess securityProcess) {
		this.securityProcess = securityProcess;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public void setAddNoteMapper(SOAddNoteMapper addNoteMapper) {
		this.addNoteMapper = addNoteMapper;
	}
	
}
