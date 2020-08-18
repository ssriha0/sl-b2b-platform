package com.newco.marketplace.web.action.spn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.spn.SPNDocumentVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.security.NonSecurePage;

/**
 * @author Infosys
 * The action class is used to display the Buyer agreement Modal popup showing the details of 
 * all Buyer Agreement documents.
 *
 */
//Fix for issue SL-19316 : Commenting the annotation
//@NonSecurePage
public class SPNBuyerAgreeModalAction extends SLBaseAction implements SPNConstants {
	
	private static final long serialVersionUID = 4690371474575121363L;
	
	private String providerId;
	private Integer spnDocId;
	private Date agreeDate;
	private ISelectProviderNetworkBO spnCreateUpdateBO;
	private static final Logger logger = Logger.getLogger(SPNBuyerAgreeModalAction.class.getName());
 
 	public SPNBuyerAgreeModalAction(ISelectProviderNetworkBO spnCreateUpdateBOArg){
		this.spnCreateUpdateBO = spnCreateUpdateBOArg;		
	}
 
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 * The method retrieves the list of Buyer agreements for the particular SPN.
	 */
	public String execute(){		
		String ID = getParameter(SPN_ID);
		int spnId=Integer.decode(ID);
		 List<SPNDocumentVO> resultList=  new ArrayList<SPNDocumentVO>();
		try{			
			resultList=spnCreateUpdateBO.getSPNBuyerAgreeModal(spnId);
			//spnCreateUpdateBO.getspn
			setAttribute(DOCUMENT_LIST, resultList);
		}catch(Exception e){
			logger.error("Error returned trying to get document list for spnId:" + ID,e);
		}
		return SUCCESS;
	}
	
	public String acceptBuyerAgreementAjax()
	{
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute(Constants.SESSION.SECURITY_CONTEXT);
		Integer pfId = securityContext.getCompanyId();
		String username = securityContext.getUsername();
		
		try {
			spnCreateUpdateBO.submitSPNBuyerAgreementForDoc(pfId, username, spnDocId);
			agreeDate = new Date();
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
		
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public ISelectProviderNetworkBO getSpnCreateUpdateBO() {
		return spnCreateUpdateBO;
	}
	public void setSpnCreateUpdateBO(ISelectProviderNetworkBO spnCreateUpdateBO) {
		this.spnCreateUpdateBO = spnCreateUpdateBO;
	}

	public Integer getSpnDocId() {
		return spnDocId;
	}

	public void setSpnDocId(Integer spnDocId) {
		this.spnDocId = spnDocId;
	}

	public Date getAgreeDate() {
		return agreeDate;
	}

	public void setAgreeDate(Date agreeDate) {
		this.agreeDate = agreeDate;
	}
	
}
