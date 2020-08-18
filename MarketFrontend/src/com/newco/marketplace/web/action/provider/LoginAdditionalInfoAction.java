package com.newco.marketplace.web.action.provider;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ISODashBoardDelegate;
import com.newco.marketplace.web.delegates.ISecurityDelegate;
import com.newco.marketplace.web.delegates.provider.ILoginDelegate;
import com.newco.marketplace.web.dto.DropdownOptionDTO;
import com.newco.marketplace.web.dto.LoginAdditionalInfoDTO;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.newco.ofac.utils.OFACConstants;
import com.newco.ofac.vo.BuyerOfacVO;
import com.newco.ofac.vo.ContactOfacVO;
import com.newco.ofac.vo.ProviderOfacVO;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class LoginAdditionalInfoAction extends SLBaseAction implements SessionAware, ServletRequestAware, Preparable, ModelDriven<LoginAdditionalInfoDTO> {

	
	private static final long serialVersionUID = 1552071435947310640L;
	private ISecurityDelegate securityDel = null;
	private Integer companyId ;
	private Integer roleID ; 
	private LoginAdditionalInfoDTO model = new LoginAdditionalInfoDTO();
	private static final Logger logger = Logger.getLogger(ManageUsersAction.class.getName());
	private SecurityContext securityCntxt = null;
	private HttpSession session = null;
	public LoginAdditionalInfoAction(ISecurityDelegate securityDel , ILoginDelegate loginDelegate ,ISODashBoardDelegate dashboardDelegate ){
		
		this.securityDel = securityDel;

	}
	public String displayPage() throws Exception
	{
		initDropdowns();

		try{
			securityCntxt =	(SecurityContext)ActionContext.getContext().getSession().get("SecurityContext");
			
			roleID = securityCntxt.getRoleId();
			companyId = securityCntxt.getCompanyId();	
			logout();
			session = ServletActionContext.getRequest().getSession(true);
			
			session.setAttribute("RoleID", roleID);
			session.setAttribute("CompanyId", companyId);
			}
			catch( Exception e)
			{
				e.printStackTrace();
				logger.info("Exception in method displayPage of LoginAdditionalInfoAction");	
				logger.info(e.getMessage());
			}

		return "additionalInfo";
	}


	
	private String checkTaxNumberAndDOB(Integer taxNumber, Date dob)
	{
		ContactOfacVO contact = null;
		String returnPage="accountLockedPage";
		boolean oneInfoStored = false;
		try
		{
			session = ServletActionContext.getRequest().getSession(false);

			if(session!= null)
			{
//			securityCntxt = (SecurityContext)session.getAttribute("SecurityContext");
			companyId = (Integer)session.getAttribute("CompanyId");
			roleID = (Integer)session.getAttribute("RoleID");
			
			
				if (roleID != null && companyId != null && roleID == OrderConstants.BUYER_ROLEID)
				{
					contact = securityDel.getBuyerContactIdEin(companyId);
					contact.setResourceID(companyId);
					
					if (!SLStringUtils.isNullOrEmpty(taxNumber+""))
					{
						contact.setTaxID(taxNumber.toString());
						securityDel.addBuyerTaxID(contact);
						oneInfoStored = true;
					}
					
					//if(securityCntxt.getAdminResId() == securityCntxt.getVendBuyerResId())
					//{
						if (!SLStringUtils.isNullOrEmpty(dob+"")) {
							contact.setDob(dob);
							securityDel.addAdminDOBForOfac(contact);
							oneInfoStored = true;
						}
					//}
					
					BuyerOfacVO buyerOfacVO = new BuyerOfacVO();
					buyerOfacVO.setBuyerID(companyId);
					if(oneInfoStored)
					{
						buyerOfacVO.setBuyerOfacIndicator(OFACConstants.OFAC_IND_MATCH_IN_PROCESS);
						securityDel.updateBuyerOfacDbFlag(buyerOfacVO);
					}
				}
				else if(roleID != null && companyId != null && roleID == OrderConstants.PROVIDER_ROLEID)
				{
		
					contact= securityDel.getProviderContactIdEin(companyId);
					contact.setResourceID(companyId);
		
					
					if (!SLStringUtils.isNullOrEmpty(taxNumber+""))
					{
						contact.setTaxID(taxNumber.toString());
						securityDel.addProviderTaxID(contact);
						oneInfoStored = true;
					}
					//if(securityCntxt.getAdminResId() == securityCntxt.getVendBuyerResId())
					//{
						if (SLStringUtils.isNullOrEmpty(dob+"")) {
							contact.setDob(dob);
							securityDel.addAdminDOBForOfac(contact); 
							oneInfoStored = true;
						}
					//}
					
					ProviderOfacVO providerOfacVO = new ProviderOfacVO();
					providerOfacVO.setProviderID(companyId);
					if(oneInfoStored)
					{
						providerOfacVO.setProviderOfacIndicator(OFACConstants.OFAC_IND_MATCH_IN_PROCESS);
						securityDel.updateProviderOfacDbFlag(providerOfacVO);
					}
					
				}
			}
			logout();
		}
		catch(Exception e)
		{
			logger.info("Exception in method checkTaxNumberAndDOB");	
		}
		return returnPage;
	}
	
	private void initDropdowns()
	{
		List<DropdownOptionDTO> dayOptions = new ArrayList<DropdownOptionDTO>();
		DropdownOptionDTO day;
		
		
		for(int i=0 ; i<32 ; i++)
		{
			day = new DropdownOptionDTO(i+"", i+"", null);
			dayOptions.add(day);			
		}
		
		getModel().setDayOptions(dayOptions);

		
		
		List<DropdownOptionDTO> monthOptions = getMonthOptions();		
		getModel().setMonthOptions(monthOptions);
		
	}	
	public String buttonSubmit() throws Exception
	{
		
		getModel().validate();
		
		
		if(getModel().getErrors().size() > 0)
		{		
			initDropdowns();
			return "additionalInfo";
		}

		
		Calendar dob=null;

		boolean formHasDOB = formHasDOB();
		if(formHasDOB)
		{
			dob= Calendar.getInstance();
			int int_day = Integer.parseInt(getModel().getSelectedDay());
			int int_month = Integer.parseInt(getModel().getSelectedMonth());
			int int_year = Integer.parseInt(getModel().getYear());
				
			dob.set(int_year, int_month, int_day);
		}
		
		Date dobDate = null;
		if(dob != null)
		{
			dobDate = dob.getTime();
		}		
		
		Integer taxNumber = null;
		if(SLStringUtils.IsParsableNumber(getModel().getTaxNumber()))
		{
			taxNumber = Integer.parseInt(getModel().getTaxNumber());
		}

		
		checkTaxNumberAndDOB(taxNumber, dobDate);
		return "accountLockedPage";
		
	}
	
	private boolean formHasDOB()
	{
		if(getModel().getSelectedDay().equals("-1") && getModel().getSelectedMonth().equals("-1") && getModel().getYear().length() <= 4)
		{
			return false;
		}
		
		return true;
	}

	public void setSession(Map arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		
	}

	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public LoginAdditionalInfoDTO getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	public void setModel(LoginAdditionalInfoDTO model) {
		this.model = model;
	}
	
	public String logout() throws Exception{	
	
		HttpSession session=ServletActionContext.getRequest().getSession(false);
		if(session!=null)session.invalidate();
		return SUCCESS;
	}
	

	
}