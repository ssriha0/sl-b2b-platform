package com.newco.marketplace.web.action.financemanager;

import java.util.HashMap;

import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLFinanceManagerBaseAction;
import com.newco.marketplace.web.dto.FMFinancialProfileTabDTO;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class FMFinancialProfileSaveAction extends SLFinanceManagerBaseAction implements Preparable, ModelDriven<FMFinancialProfileTabDTO> {

	private static final long serialVersionUID = 1L;
	
	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();	
	}	
	
	
	public FMFinancialProfileTabDTO getModel() {
		return financialProfileTabDTO;
	}


	@SuppressWarnings("unchecked")
	public String save() throws Exception{
		
		financialProfileTabDTO.validate();

		HashMap<String, String> map = new HashMap<String, String>();
		map =(HashMap<String, String>) getRequest().getSession().getAttribute("hasMapForTabIcons");
		map = setTabIcon(map, OrderConstants.FM_FINANCIAL_PROFILE_TAB, financialProfileTabDTO.getErrors(), financialProfileTabDTO.getWarnings());
		getSession().setAttribute("hasMapForTabIcons",map);
		getRequest().getSession().setAttribute("FinancialProfileTabDTO", financialProfileTabDTO);
		if((financialProfileTabDTO.getErrors()!=null && financialProfileTabDTO.getErrors().size()>0) 
				|| (financialProfileTabDTO.getWarnings()!=null && financialProfileTabDTO.getWarnings().size()>0) ){
			setDefaultTab(OrderConstants.FM_FINANCIAL_PROFILE);
		}else{
			SecurityContext securityContext = (SecurityContext) ServletActionContext.getRequest().getSession().getAttribute("SecurityContext");
			String role = securityContext.getRole();
			boolean buyerFlag=false;
			if (role != null && (role.equalsIgnoreCase(OrderConstants.BUYER) || role.equalsIgnoreCase(OrderConstants.SIMPLE_BUYER))) buyerFlag = true; 
			
			getFinanceManagerDelegate().saveFinancialProfile(financialProfileTabDTO,get_commonCriteria().getCompanyId(), buyerFlag);
			//getRequest().getSession().removeAttribute("FinancialProfileTabDTO");
			setDefaultTab(OrderConstants.FM_MANAGE_ACCOUNTS);
		}
		return GOTO_COMMON_FINANCE_MGR_CONTROLLER;
		
	}

	public FMFinancialProfileTabDTO getFinancialProfileTabDTO() {
		return financialProfileTabDTO;
	}

	public void setFinancialProfileTabDTO(
			FMFinancialProfileTabDTO financialProfileTabDTO) {
		this.financialProfileTabDTO = financialProfileTabDTO;
	}
	
}
