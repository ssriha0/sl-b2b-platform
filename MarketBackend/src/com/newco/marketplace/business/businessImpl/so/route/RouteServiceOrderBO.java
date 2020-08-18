package com.newco.marketplace.business.businessImpl.so.route;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.so.order.BaseOrderBO;
import com.newco.marketplace.business.iBusiness.ledger.ILedgerFacilityBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.ledger.MarketPlaceTransactionVO;
import com.newco.marketplace.dto.vo.serviceorder.FundingVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.feemanager.FeeDao;
import com.newco.marketplace.validator.order.RouteSOValidator;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.newco.marketplace.webservices.base.response.ValidatorResponse;

/**
 * $Revision: 1.15 $ $Author: glacy $ $Date: 2008/04/26 00:40:37 $
 */
public class RouteServiceOrderBO extends BaseOrderBO {
	private static final Logger logger = Logger.getLogger(RouteServiceOrderBO.class);
	private ILedgerFacilityBO transBo;
	private IServiceOrderBO serviceOrderBo;
	FeeDao feeDao = null;
	private boolean canRoute = true;

	public ProcessResponse process(Integer buyerId, String serviceOrderID,
			MarketPlaceTransactionVO marketVO) {

		ProcessResponse processResp = new ProcessResponse();
		Calendar calendar = Calendar.getInstance();
		logger.info("INSIDE RouteServiceOrderBO::: process()::::::");
		try {
			ValidatorResponse validatorResp = new RouteSOValidator()
					.validate(serviceOrderID);// here i am calling valid
			System.out.print((validatorResp.isError()));

			if (validatorResp.isError()) {
				if (logger.isDebugEnabled())
					logger
							.error("Route service order failed validation, reason: "
									+ validatorResp.getMessages());
				processResp.setCode(USER_ERROR_RC);
				processResp.setMessages(validatorResp.getMessages());
				System.out.print(validatorResp.getMessages());

				return processResp;
			}

			ServiceOrder serviceOrder = getServiceOrderDao()
					.getServiceOrder(serviceOrderID);
			marketVO.setServiceOrder(serviceOrder);
			if (serviceOrder == null) {
				canRoute = false;
				return setErrorMsg(processResp, SERVICE_ORDER_OBJ_NOT_FOUND);
			}

			// Check if the buyer is authorized to close the service order

			if (!isAuthorizedBuyer(buyerId, serviceOrder)) {
				canRoute = false;
				return setErrorMsg(processResp, BUYER_IS_NOT_AUTHORIZED);
			}
			// check for status draft
			if (!checkState(serviceOrder.getSoId())) {
				return setErrorMsg(processResp,
						"");
						//BUYER_SERVICE_ORDER_STATE_IS_NOT_DRAFT);
			}

			// Check if the buyer is having enough funds
			if (!enoughBuyerFunds(buyerId, serviceOrder, marketVO)) {
				return setErrorMsg(processResp,
						BUYER_DOES_NOT_HAVE_ENOUGH_FUNDS);
			}

			
			if (canRoute) {

				logger.info("Inside canRoute");
				HashMap<String, Object> map = new HashMap<String, Object>();
				logger.info("Inside canRoute:::after 1st creating the object");

				logger.info("Inside canRoute:::after creating the MarketPlaceTransactionVO object ");
				FundingVO fundVO = new FundingVO();
				transBo.routeServiceOrderLedgerAction(marketVO,fundVO);
				serviceOrder.setWfStateId(ROUTED_STATUS);
				serviceOrder.setLastStatusChange(new Timestamp(calendar
						.getTimeInMillis()));
				serviceOrder.setRoutedDate(new Timestamp(calendar
						.getTimeInMillis()));
				map.put("status", ROUTED);
				processResp.setCode(VALID_RC);
				processResp.setSubCode(VALID_RC);
				processResp.setObj(map);
				getServiceOrderDao().updateSOStatus(serviceOrder);
			}
			getServiceOrderDao().updateSOStatus(serviceOrder);
		} catch (Throwable t) {
			logger.error("route service order:: error:  " + t.getMessage(), t);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(t.getMessage());
			processResp.setObj(FAILED_SERVICE_ORDER_NO);
		}
		return processResp;
	}

	private boolean enoughBuyerFunds(Integer buyerId,
			ServiceOrder serviceOrder, MarketPlaceTransactionVO mvo)  throws Exception  {
		AjaxCacheVO avo = new AjaxCacheVO();
		avo.setCompanyId(buyerId);
		avo.setRoleType("BUYER");
		Integer accessfee = null;
		try {
			accessfee = feeDao.getAccessFee(mvo);
		} catch (Exception e) {
			System.out.print("Error in getting the value");
		}
	
		//System.out.print(accessfee + serviceOrder.getFinalPrice());
		if ( accessfee + serviceOrder.getSpendLimitLabor()+serviceOrder.getSpendLimitParts() > transBo.getAvailableBalance(avo)) {
			canRoute = false;
			return false;
		} else
			return true;
	}
	private boolean checkState(String serviceOrder)
{
	Integer state = null;
	try{
		 state=getServiceOrderDao().checkWfState(serviceOrder);
		 System.out.print("\n\n\n\n\n Following is the state"+ state+"\n\n\n\n");
	}
	catch(DataServiceException e)
	{
		System.out.print("error " + e);
	}
	if(state==ROUTED_STATUS)
	{
		return true;
	}else 
	{
		canRoute = false;
	return false;
	}
}
	
	public ILedgerFacilityBO getTransBo() {
		return transBo;
	}

	public void setTransBo(ILedgerFacilityBO transBo) {
		this.transBo = transBo;
	}

	public FeeDao getFeeDao() {
		return feeDao;
	}

	public void setFeeDao(FeeDao feeDao) {
		this.feeDao = feeDao;
	}

	public ProcessResponse routeServiceOrder (String serviceOrderId , ArrayList<Integer> providerList) {
	    return (new ProcessResponse());
	}

	public IServiceOrderBO getServiceOrderBo() {
		return serviceOrderBo;
	}

	public void setServiceOrderBo(IServiceOrderBO serviceOrderBo) {
		this.serviceOrderBo = serviceOrderBo;
	}
}
