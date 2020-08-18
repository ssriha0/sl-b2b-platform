/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-May-2009	KMSTRSUP   	Infosys				1.0
 * 
 */
package com.newco.marketplace.api.mobile.security;

import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO;
import com.newco.marketplace.business.iBusiness.ledger.ICreditCardBO;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.dto.vo.serviceorder.ABaseRequestDispatcher;
import com.newco.marketplace.interfaces.OrderConstants;

/**
 * This class is a service class for getting Security Context.
 * 
 * @author Infosys
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SecurityProcess extends ABaseRequestDispatcher{

	private Logger logger = Logger.getLogger(SecurityProcess.class);
	 private IFinanceManagerBO financeManagerBO ;
	 private ICreditCardBO creditCardBO ;
	 
	 
	 /**
	  * This method is for getting Security Context using Buyer Resource Id.
	  * 
	  * @param buyerResourceId Integer
	  * @return SecurityContext
	  */
	 public SecurityContext getSecurityContext(Integer buyerResourceId) {
		 String userName = getBuyerUserName(buyerResourceId);
		 return getSecurityContext(userName, null);
	 }
	 /**
	  * This method is for getting Security Context using Buyer  Id.
	  * 
	  * @param buyerId Integer
	  * @return SecurityContext
	  */
	 public SecurityContext getSecurityContextBuyer(Integer buyerId) {
		 String userName = getBuyerAdminUserName(buyerId);
		 return getSecurityContext(userName, null);
	 }	 
	/**
	 * This method is for getting Security Context using Buyer_Credentials.
	 * 
	 * @param userName String
	 * @param Password String
	 * @return SecurityContext
	 */
	public SecurityContext getSecurityContext(String userName, String Password) {
		logger.info("Entering SecurityProcess.getSecurityContext()");
		SecurityContext securityContext = createAPISecurityContext(userName,
				Password);
		logger.info("Leaving SecurityProcess.getSecurityContext()");
		
		//Defaulting AutoACHEnabledInd to false
/*		Account acct=null;
		if( null != securityContext ){
			try {
				acct = getAutoACHEnabledInd(securityContext.getRoleId(),securityContext.getCompanyId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			securityContext.setAutoACH(acct.isEnabled_ind());
			securityContext.setAccountID(acct.getAccount_id());
		}*/
		if (securityContext != null)
		  securityContext.setAutoACH(false);		
		return securityContext;
	}
	
	
	/**
	  * This method is for getting Security Context using Buyer Resource Id.
	  * 
	  * @param buyerResourceId Integer
	  * @return SecurityContext
	  */
	 public SecurityContext getSecurityContextForVendor(Integer vendorResourceId) {
		 String userName = getVendorUserName(vendorResourceId);
		 if(null!=userName)
		 return getSecurityContext(userName, null);
		 else
			 return null;
	 }
	
	
	/**
	 * This method calls the business method to retrieve the value of Auto
	 * Funding Indicator 
	 * 
	 * @throws Exception
	 */
	protected Account getAutoACHEnabledInd(Integer roleId,Integer companyId) throws Exception {
		Account acct = new Account();
		CreditCardVO creditVO = null;

			if ((roleId != null) && (OrderConstants.BUYER_ROLEID == roleId.intValue() || OrderConstants.SIMPLE_BUYER_ROLEID == roleId.intValue())) {
				acct = financeManagerBO.getAutoFundingIndicator(companyId);
				if (OrderConstants.SIMPLE_BUYER_ROLEID == roleId.intValue()){
					creditVO = creditCardBO.getActiveCreditCardDetails(companyId);
					if(null!=creditVO){
						acct.setAccount_id(creditVO.getCardId());
					}					
				}
			}
		
		return acct;
	}
	/**
	  * This method is for getting Security Context using vendor Admin.
	  * 
	  * @param buyerResourceId Integer
	  * @return SecurityContext
	  */
	 public SecurityContext getSecurityContextForVendorAdmin(Integer vendorId) {
		 String userName = getVendorAdminName(vendorId);
		 if(null!=userName)
		 return getSecurityContext(userName, null);
		 else
			 return null;
	 }
	 protected String getVendorAdminName(Integer vendorId){
			return getAccessSecurity().getVendorAdminName(vendorId);		
		}
	protected String getBuyerUserName(Integer buyerResourceId){
		return getAccessSecurity().getBuyerUserName(buyerResourceId);		
	}
	
	protected String getBuyerAdminUserName(Integer buyerId){
		return getAccessSecurity().getBuyerAdminUserName(buyerId);		
	}
	
	protected String getVendorUserName(Integer vendorResourceId){
		return getAccessSecurity().getVendorUserName(vendorResourceId);		
	}
	
	public String getOAuthConsumerSecret(String consumerKey) {
		// TODO Auto-generated method stub
		return getAccessSecurity().getOAuthConsumerSecret(consumerKey);
	}

    public boolean isBuyerExist(Integer buyerId) {
    	return getAccessSecurity().verifyEntity(buyerId, "BUYER") ;
    }
    
    public boolean isProviderExist(Integer buyerId) {
    	return getAccessSecurity().verifyEntity(buyerId, "PROVIDER") ;
    }
    
	public IFinanceManagerBO getFinanceManagerBO() {
		return financeManagerBO;
	}


	public void setFinanceManagerBO(IFinanceManagerBO financeManagerBO) {
		this.financeManagerBO = financeManagerBO;
	}


	public ICreditCardBO getCreditCardBO() {
		return creditCardBO;
	}


	public void setCreditCardBO(ICreditCardBO creditCardBO) {
		this.creditCardBO = creditCardBO;
	}
	
	

}
