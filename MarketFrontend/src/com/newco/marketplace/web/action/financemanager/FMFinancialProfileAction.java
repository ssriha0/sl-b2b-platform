package com.newco.marketplace.web.action.financemanager;

import java.util.ArrayList;

import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.web.action.base.SLFinanceManagerBaseAction;
import com.newco.marketplace.web.dto.FMFinancialProfileTabDTO;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class FMFinancialProfileAction extends SLFinanceManagerBaseAction implements Preparable, ModelDriven<FMFinancialProfileTabDTO>
{
	private static final long serialVersionUID = 1L;
		
	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();	
		this.initFinancialProflie();
	}

	public String execute() throws Exception
	{
		String role = get_commonCriteria().getRoleType();
		return role;
	}
	

	public FMFinancialProfileTabDTO getModel()
	{
		if(getRequest().getSession().getAttribute("FinancialProfileTabDTO") != null){
			financialProfileTabDTO = (FMFinancialProfileTabDTO)getRequest().getSession().getAttribute("FinancialProfileTabDTO");
		}
		return financialProfileTabDTO;
	}
	public FMFinancialProfileTabDTO getFinancialProfileTabDTO() {
		return getModel();
	}

	public void setFinancialProfileTabDTO(FMFinancialProfileTabDTO financialProfileTabDTO) {
		this.financialProfileTabDTO = financialProfileTabDTO;
	}

	public ArrayList<LookupVO> getPercentageLists() {
		return (ArrayList<LookupVO>)getSession().getAttribute("percentageMap");
	}

	public void setPercentageLists(ArrayList<LookupVO> percentageLists) {
		this.percentageLists = percentageLists;
	}
	
	
}
