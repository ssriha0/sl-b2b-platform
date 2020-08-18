package com.newco.marketplace.web.action.financemanager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLFinanceManagerBaseAction;
import com.newco.marketplace.web.delegates.ISecurityDelegate;
import com.newco.marketplace.web.dto.FMRevenuePullTabDTO;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.SOWError;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class FMRevenuePullAction extends SLFinanceManagerBaseAction implements Preparable,ModelDriven<FMRevenuePullTabDTO>
{
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(FMRevenuePullAction.class.getName());

	private FMRevenuePullTabDTO revenuePullTabDTO = new FMRevenuePullTabDTO();
	private ISecurityDelegate securityBean = null;


	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();		
	}


	public String execute(){

		String role = get_commonCriteria().getRoleType();

		return role;
	}


	public String setRevenuePullRequest() throws Exception {

		logger.info("Inside setRevenuePullRequest");

		FMRevenuePullTabDTO dto = getModel();

		Integer companyId = get_commonCriteria().getCompanyId();
		String roleType = get_commonCriteria().getRoleType();
		String user = get_commonCriteria().getSecurityContext().getSlAdminUName();
		String firstName = _commonCriteria.getSecurityContext().getSlAdminFName();
		String lastName = _commonCriteria.getSecurityContext().getSlAdminLName();
		String note = "";
		double amount = 0.0;
		String amountString = "";
		String revenuePullDate = "";
		boolean dbRevenuePullDateCheck = false;

		List<IError> errorList = new ArrayList<IError>();

		Date calendarOnDate = new Date();
		Date todayDate = new Date();

		DateFormat dateFormatter= new SimpleDateFormat("yyyy-MM-dd");


		String successMsg = "";
		dto.setSuccessMsg(successMsg);


		if(!(dto.getRevenuePullAmount().toString().trim().equals(""))){
			if(NumberUtils.isNumber(dto.getRevenuePullAmount().toString().trim())){

				amount = Double.parseDouble(dto.getRevenuePullAmount().toString());
				amountString = String.valueOf(String.format("%.2f", (Math.round(amount * 100.0)/100.0)));


				if (Double.compare(amount, 0.0) <= 0){

					logger.info("If part valid amount error.");

					SOWError error = new SOWError("", REVENUE_PULL_ENTER_POSITIVE_AMOUNT, OrderConstants.FM_ERROR);
					errorList.add(error);
				}


				if(Double.compare(amount, 0.0) > 0){

					if (Double.compare(amount, 499999.0) > 0){

						logger.info("If part maximum amount error.");

						SOWError error = new SOWError("", REVENUE_PULL_MAXIMUM_AMOUNT, OrderConstants.FM_ERROR);
						errorList.add(error);
					}
				}
			}
			else{

				if (Double.compare(amount, 0.0) <= 0){

					logger.info("Else part numeric date error.");

					SOWError error = new SOWError("", REVENUE_PULL_ENTER_NUMERIC_AMOUNT, OrderConstants.FM_ERROR);
					errorList.add(error);
				}
			}
		}
		else{

			if (Double.compare(amount, 0.0) <= 0){

				logger.info("Else part valid amount error.");

				SOWError error = new SOWError("", REVENUE_PULL_ENTER_AMOUNT, OrderConstants.FM_ERROR);
				errorList.add(error);
			}

		}



		if(!(dto.getCalendarOnDate().toString().trim().equals(""))){

			revenuePullDate = dto.getCalendarOnDate().toString();

			calendarOnDate = dateFormatter.parse(revenuePullDate);
			revenuePullDate = dateFormatter.format(calendarOnDate);

			if (revenuePullDate == ""){

				logger.info("If part mandatory date error.");

				SOWError error = new SOWError("", REVENUE_PULL_MANDATORY_DATE, OrderConstants.FM_ERROR);
				errorList.add(error);
			}


			if(!(revenuePullDate.toString().equals(""))){

				if ((calendarOnDate).compareTo(todayDate) < 1){

					logger.info("If part valid date error.");

					SOWError error = new SOWError("", REVENUE_PULL_VALID_DATE, OrderConstants.FM_ERROR);
					errorList.add(error);
				}
			}
		}
		else{

			if (revenuePullDate == ""){

				logger.info("Else part mandatory date error.");

				SOWError error = new SOWError("", REVENUE_PULL_MANDATORY_DATE, OrderConstants.FM_ERROR);
				errorList.add(error);
			}

		}



		if(!(dto.getRevenuePullNote().toString().trim().equals(""))){

			note = dto.getRevenuePullNote();

			if (note.length() > 255){

				logger.info("If part reason comment error for maximum characters.");

				SOWError error = new SOWError("", REVENUE_PULL_MAXIMUM_REASON_COMMENT, OrderConstants.FM_ERROR);
				errorList.add(error);
			}
		}
		else{

			dto.setRevenuePullNote(dto.getRevenuePullNote().toString().trim());

			if (note == ""){

				logger.info("Else part reason comment error.");

				SOWError error = new SOWError("", REVENUE_PULL_REASON_COMMENT, OrderConstants.FM_ERROR);
				errorList.add(error);
			}
		}



		if(get_commonCriteria().getSecurityContext() != null && get_commonCriteria().getSecurityContext().getSLAdminInd() && (errorList.isEmpty())){

			double availableBalance = fmPersistDelegate.getAvailableBalanceForRevenuePull();

			dbRevenuePullDateCheck = fmPersistDelegate.getAvailableDateCheckForRevenuePull(calendarOnDate);

			logger.info("Available Balance - Amount comparison : "+Double.compare(availableBalance, amount));

			logger.info("Revenue Pull Date Check : "+dbRevenuePullDateCheck);

			if(Double.compare(availableBalance, amount) < 0){

				SOWError error = new SOWError("", "Available balance $"+availableBalance+" in the account is less than the amount $"+amountString+" requested.", OrderConstants.FM_ERROR);
				errorList.add(error);
			}

			if(dbRevenuePullDateCheck){

				SOWError error = new SOWError("", "Revenue Pull for "+revenuePullDate+" has been already requested. Only one request is allowed per day.", OrderConstants.FM_ERROR);
				errorList.add(error);
			}

			if(errorList.isEmpty()){

				logger.info("Inserting data and sending e-mail confirmation.");

				fmPersistDelegate.insertEntryForRevenuePull(amount,calendarOnDate,note,user);
				fmPersistDelegate.sendRevenuePullConfirmationEmail(firstName,lastName,amount,revenuePullDate);

				successMsg = "Revenue pull for $"+amountString+" will be processed on "+revenuePullDate+" between 1 AM - 2 AM CST.";

				getSession().setAttribute("revenuePullStatus", "success");
				dto.setRevenuePullAmount("0.00");
				dto.setRevenuePullNote("");
				dto.setCalendarOnDate("");
				dto.setSuccessMsg(successMsg);


			}

		}


		if(!(errorList.isEmpty())){

			dto.setErrors(errorList);
			getSession().setAttribute("errors", dto.getErrors());
			getSession().setAttribute("revenuePullError", "true");
		}


		logger.info("companyId : "+companyId);
		logger.info("roleType : "+roleType);
		logger.info("user : "+user);
		logger.info("amount : "+amount);
		logger.info("revenuePullDate : "+revenuePullDate);
		logger.info("note : "+note);
		logger.info("SLAdminInd : "+get_commonCriteria().getSecurityContext().getSLAdminInd());

		getRequest().getSession().setAttribute("revenuePullTabDTO", dto);
		setDefaultTab(OrderConstants.FM_REVENUE_PULL);

		return GOTO_COMMON_FINANCE_MGR_CONTROLLER;
	}


	public FMRevenuePullTabDTO getRevenuePullTabDTO() {
		return revenuePullTabDTO;
	}

	public void setRevenuePullTabDTO(FMRevenuePullTabDTO revenuePullTabDTO) {
		this.revenuePullTabDTO = revenuePullTabDTO;
	}	


	public FMRevenuePullTabDTO getModel() {
		if(getRequest().getSession().getAttribute("revenuePullTabDTO") != null){
			revenuePullTabDTO = (FMRevenuePullTabDTO)getRequest().getSession().getAttribute("revenuePullTabDTO");
		}
		return revenuePullTabDTO;
	}

	public ISecurityDelegate getSecurityBean() {
		return securityBean;
	}


	public void setSecurityBean(ISecurityDelegate securityBean) {
		this.securityBean = securityBean;
	}
}
