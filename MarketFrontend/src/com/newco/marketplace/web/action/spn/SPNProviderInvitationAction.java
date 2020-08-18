package com.newco.marketplace.web.action.spn;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.spn.SPNDocumentVO;
import com.newco.marketplace.dto.vo.spn.SPNMainMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNProvUploadedDocsVO;
import com.newco.marketplace.dto.vo.spn.SPNSignAndReturnDocumentVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.security.NonSecurePage;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Infosys
 * The action class is used to display the SPN provider invitation page
 *
 */

//Fix for issue SL-19316 : Commenting the annotation
//@NonSecurePage
public class SPNProviderInvitationAction extends SLBaseAction implements SPNConstants, Preparable {
	
	private static final long serialVersionUID = 4224385412881777770L;
	
	private static final Logger logger = Logger.getLogger(SPNProviderInvitationAction.class);
	
	private ISelectProviderNetworkBO spnCreateUpdateBO;
	private String rejectId;
	private String rejectReason;
	
	public SPNProviderInvitationAction(ISelectProviderNetworkBO spnCreateUpdateBOArg){
		this.spnCreateUpdateBO = spnCreateUpdateBOArg;		
	}
	
	public void prepare() throws Exception {		
	}
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 * The method retrieves the details of SPN to be displayed in SPN provider invitation page.
	 */
	public String loadInvitation() {
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute(Constants.SESSION.SECURITY_CONTEXT);
		Integer vendorId = securityContext.getCompanyId();
		String spnID = getParameter(SPN_ID);
		int spnId=Integer.decode(spnID);
		List<SPNDocumentVO> spnInfoDocuments=new ArrayList<SPNDocumentVO>();
		SPNMainMonitorVO invitationVO=new SPNMainMonitorVO();
		try{
			invitationVO = spnCreateUpdateBO.loadProviderInvitation(spnId, vendorId);
			if(null !=invitationVO){
				for(SPNDocumentVO spnDocumentVO :invitationVO.getSpnDocuments()){
					int spnDocTypeId = spnDocumentVO.getDocTypeId();
					if(spnDocTypeId == SPNConstants.ONE){
						spnInfoDocuments.add(spnDocumentVO);
					}
				}
				if(spnInfoDocuments.size()>0){
					invitationVO.setSpnInfoDocuments(spnInfoDocuments);
				}
				List<SPNSignAndReturnDocumentVO> signAndReturnDocVOList = invitationVO.getSpnSignAndReturnDocuments();
				List<SPNProvUploadedDocsVO> uploadedDocList = invitationVO.getSpnProvUploadedDocs();
				if(signAndReturnDocVOList.size() > 0){				
					for(SPNSignAndReturnDocumentVO spnSignAndReturnDocumentVO :signAndReturnDocVOList){	
						int docId = spnSignAndReturnDocumentVO.getDocId();
						if(uploadedDocList.size() > 0){
							for(SPNProvUploadedDocsVO spnProvUploadedDocsVO :uploadedDocList){
								int spnBuyerDocId = spnProvUploadedDocsVO.getSpnBuyerDocId();
								if(spnBuyerDocId == docId){  
									spnSignAndReturnDocumentVO.setProviderUploadInd(SPNConstants.SUCCESS_IND); 	
									spnSignAndReturnDocumentVO.setDocDescription(spnProvUploadedDocsVO.getDocDescription());
									spnSignAndReturnDocumentVO.setDocFileName(spnProvUploadedDocsVO.getDocFileName());
									spnSignAndReturnDocumentVO.setDocStateDesc(spnProvUploadedDocsVO.getDocStateDesc());
									spnSignAndReturnDocumentVO.setDocStateId(spnProvUploadedDocsVO.getDocStateId());
									spnSignAndReturnDocumentVO.setDocFormat(spnProvUploadedDocsVO.getDocFormat());
									spnSignAndReturnDocumentVO.setDocTitle(spnProvUploadedDocsVO.getDocTitle());
									spnSignAndReturnDocumentVO.setProvFirmUplDocId(spnProvUploadedDocsVO.getProvFirmUplDocId());
									spnSignAndReturnDocumentVO.setProvDocFormatDescription(spnProvUploadedDocsVO.getDocFormatDescription());
									break;
								}
							}						
						}
						if(spnSignAndReturnDocumentVO.getProviderUploadInd() != SPNConstants.SUCCESS_IND){
							spnSignAndReturnDocumentVO.setDocStateId(SPNConstants.DOC_INCOMPLETE_STATE_ID);
							spnSignAndReturnDocumentVO.setDocStateDesc(SPNConstants.DOC_INCOMPLETE);
						}
					}
				}
				invitationVO.setSpnSignAndReturnDocuments(signAndReturnDocVOList);
			}
			this.formatPhoneNumber(invitationVO);
			setAttribute(SPNConstants.PROVIDER_INVITATION, invitationVO);
		}catch(BusinessServiceException e){
			logger.error("Error retriving SPN Main Monitor Details for Provider Admin : ",e);
		}
		return SUCCESS;
	}
		
	/**
	 * Formats the phone number
	 * @param invitationVO
	 */
	private void formatPhoneNumber(SPNMainMonitorVO invitationVO) {		
			String contactPhone = invitationVO.getContactPhone();
			String contactPhoneFormatted = UIUtils.formatPhoneNumber(contactPhone);
			invitationVO.setContactPhone(contactPhoneFormatted);		
	}
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 * The method rejects the SPN invitation.
	 */
	public String submitRejectInvitation() {
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute(Constants.SESSION.SECURITY_CONTEXT);
		Integer vendorId = securityContext.getCompanyId();
		String ID = getParameter(SPNConstants.SELECTED_SPN_ID);
		String loggedInuserName = securityContext.getUsername();
		int spnId=Integer.parseInt(ID);
		try{
			spnCreateUpdateBO.rejectInvite(rejectId,rejectReason,spnId,vendorId,loggedInuserName);
			
		}catch(BusinessServiceException e){
			logger.error("Error while rejecting the SPN invitation ",e);
		}
		//setAttribute(OrderConstants.SPN_MAIN_MONITOR_LIST, spnMainMonitorList);
		return DASHBOARD;
	}
	
	public String getRejectId() {
		return rejectId;
	}
	
	public void setRejectId(String rejectId) {
		this.rejectId = rejectId;
	}
	
	public String getRejectReason() {
		return rejectReason;
	}
	
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
}
