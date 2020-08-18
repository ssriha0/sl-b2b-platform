package com.newco.marketplace.web.action.spn;


import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.security.NonSecurePage;

//Fix for issue SL-19316 : Commenting the annotation
//@NonSecurePage
public class SPNSubmitBuyerAgreementAction extends SLBaseAction implements OrderConstants {
	 private ISelectProviderNetworkBO spnCreateUpdateBO;
	 private Integer spnId;
	 private Integer firmId;
	 private Boolean auditRequired;
	 private static final Logger logger = Logger.getLogger(SPNBuyerAgreeModalAction.class.getName());
	 
	 public SPNSubmitBuyerAgreementAction(ISelectProviderNetworkBO spnCreateUpdateBOArg){
			this.spnCreateUpdateBO = spnCreateUpdateBOArg;			
	 }
	 /* (non-Javadoc)
		 * @see com.opensymphony.xwork2.ActionSupport#execute()
		 * The method submits Buyer agreements for the particular SPN.
		 */
		public String execute(){		
			try{			
				SecurityContext securityContext = (SecurityContext) getSession().getAttribute(Constants.SESSION.SECURITY_CONTEXT);
				String loggedUsername  = securityContext.getUsername();
				spnCreateUpdateBO.submitSPNBuyerAgreement(firmId, spnId,loggedUsername, auditRequired);
				setAttribute(SPNConstants.SPN_SELECTED,spnId); 
			}catch(Exception e){
				logger.error("Error returned while trying to submit buyer agreements:" ,e);
			}
			return SUCCESS;
		}
		public ISelectProviderNetworkBO getSpnCreateUpdateBO() {
			return spnCreateUpdateBO;
		}
		public void setSpnCreateUpdateBO(ISelectProviderNetworkBO spnCreateUpdateBO) {
			this.spnCreateUpdateBO = spnCreateUpdateBO;
		}
		public Integer getSpnId() {
			return spnId;
		}
		public void setSpnId(Integer spnId) {
			this.spnId = spnId;
		}
		public Integer getFirmId() {
			return firmId;
		}
		public void setFirmId(Integer firmId) {
			this.firmId = firmId;
		}
		public Boolean getAuditRequired() {
			return auditRequired;
		}
		public void setAuditRequired(Boolean auditRequired) {
			this.auditRequired = auditRequired;
		}
}
