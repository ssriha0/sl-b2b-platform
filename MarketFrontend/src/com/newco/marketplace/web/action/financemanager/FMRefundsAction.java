package com.newco.marketplace.web.action.financemanager;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.web.action.base.SLFinanceManagerBaseAction;
import com.newco.marketplace.web.dto.FMFinancialProfileTabDTO;
import com.newco.marketplace.web.dto.FMManageFundsTabDTO;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;

public class FMRefundsAction extends SLFinanceManagerBaseAction implements Preparable, ModelDriven<FMFinancialProfileTabDTO> {

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
		ProcessResponse procResp = new ProcessResponse();
		HttpServletResponse response = ServletActionContext.getResponse();
	    response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
		String result = null;
		String notesError = null;
		AjaxResultsDTO actionResults = new AjaxResultsDTO();
		String refundAmount = financialProfileTabDTO.getAmount();
		String refundAccount = financialProfileTabDTO.getFmManageAccountsList();
		String refundNotes = financialProfileTabDTO.getRefundNote();
		try
		{
			if(StringUtils.isNotBlank(refundAccount) && !StringUtils.equals(refundAccount,"Select Code"))
			{
				FMManageFundsTabDTO dto = new FMManageFundsTabDTO();
				result = isValidNumber(Double.parseDouble(refundAmount),null,"amount");
				notesError = isNotesEmpty(refundNotes);
				if(result == null)
				{
					if(notesError!=null){
						actionResults.setActionState(0);
			        	actionResults.setResultMessage(notesError);
					}
					else{
					AjaxCacheVO ajaxCacheVO = new AjaxCacheVO();
					ajaxCacheVO.setCompanyId(_commonCriteria.getCompanyId());
					ajaxCacheVO.setRoleType(_commonCriteria.getRoleType());
					ajaxCacheVO.setVendBuyerResId(_commonCriteria.getVendBuyerResId());
					if(validateAvailableBalance(ajaxCacheVO , Double.parseDouble(refundAmount)))
					{
						dto.setAccountId(Long.parseLong(refundAccount));
						dto.setWithdrawAmount(refundAmount);
						dto.setRefundNote(financialProfileTabDTO.getRefundNote());
						procResp = fmPersistDelegate.issueRefunds(dto, get_commonCriteria().getCompanyId(), get_commonCriteria().getRoleType(), get_commonCriteria().getSecurityContext().getSlAdminUName());
						if (!procResp.getCode().equals(ServiceConstants.VALID_RC)) {
							actionResults.setActionState(0);
							if(dto.getErrorsOnly().size()>0){
								actionResults.setResultMessage(dto.getErrors().get(0).getMsg());
							}else{
								actionResults.setResultMessage("This request could not be processed. Please try again later");
							}
						} else {
							getSession().setAttribute("Status", "success");
							actionResults.setActionState(1);
							actionResults.setResultMessage("Refund has been issued successfully");
						}
					}
					else
					{
						getSession().setAttribute("Status", "failure");
						actionResults.setActionState(0);
			        	actionResults.setResultMessage("Amount entered exceeds available balance");
					}
				}
				}
				else
				{
					actionResults.setActionState(0);
		        	actionResults.setResultMessage(result);
				}				 
			}
			else
			{
				actionResults.setActionState(0);
	        	actionResults.setResultMessage("Please select a valid account");
			}
		}
		catch(NumberFormatException e)
		{
			actionResults.setActionState(0);
			actionResults.setResultMessage("Please enter a valid transaction amount");
		}
		catch(Exception e)
		{
			actionResults.setActionState(0);
			actionResults.setResultMessage("This request could not be processed. Please try again later");
		}
		
	    response.getWriter().write(actionResults.toXml());
	    
	    return NONE;		
	}
	
	private boolean validateAvailableBalance(AjaxCacheVO ajaxCacheVO , double amount)
	{
		double availableBal = getFinanceManagerDelegate().getAvailableBalance(ajaxCacheVO);
		if(availableBal < amount)
			return false;
		else
		return true;
	}
	
	private String isValidNumber(double num, String errorMsg, String fieldName) {
		String result = null;
		try {
				if (num <= 0.0) {
					result = "Please enter a positive transaction amount value";
					
				}
			} catch (NumberFormatException a_Ex) {
				result = "Please enter a valid transaction amount";
			} catch (Exception a_Ex) {
				result = "Please enter a valid transaction amount";
			}
		return result;
	}
	private String isNotesEmpty( String notes) {
		String result = null;	
		if (SLStringUtils.isNullOrEmpty(notes.trim())) {
				result = "Notes Required for Refund";					
		}			
		return result;
	}
}
