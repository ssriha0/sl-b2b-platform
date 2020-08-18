package com.newco.marketplace.web.service.impl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.ManageTaskVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.dao.buyersku.BuyerSkuDao;
import com.newco.marketplace.web.action.details.SODetailsCompleteForPaymentAction;
import com.newco.marketplace.web.service.ManageScopeService;
import com.newco.marketplace.web.utils.OFUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.domain.lookup.LookupServiceType;
import com.servicelive.domain.reasoncodemgr.ReasonCode;
import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import com.servicelive.reasoncode.dao.ManageReasonCodeDao;
import com.servicelive.wallet.serviceinterface.IWalletBO;


public class ManageScopeServiceImpl implements ManageScopeService{
	
	private static final Logger logger = Logger.getLogger(ManageScopeServiceImpl.class.getName());
	
	private BuyerSkuDao buyerSkuDAO;
	private IWalletBO walletBO;
	private ManageReasonCodeDao reasonCodeDao;
	private OFHelper ofHelper= new OFHelper();

	public List<Map<Integer, String>> fetchBuyerSkus(String buyerId){
		List<Map<Integer, String>> skuList = buyerSkuDAO.findSKUsForManageScope(buyerId);
		return skuList;
	}
	
	public List<Map<Integer, String>> findSKUsForCategory(final String buyerID,final Integer soMainCatId){
		List<Map<Integer, String>> skuList = buyerSkuDAO.findSKUsForCategory(buyerID, soMainCatId);
		return skuList;
	}
	public List<Map<Integer, String>> findSKUsForDivision(String primarySku,String buyerID)
	{
		List<Map<Integer, String>> skuList = new ArrayList<Map<Integer,String>>();
		try{
			if(null==primarySku){
				return null;			
			}
			//using the primary SKU we will try to find if this is an exception SKU or not
			String division=buyerSkuDAO.getSkuDivisionException(primarySku);
			
			if(null==division){
				division=primarySku.substring(0,2);				
			}
			if(null!=division){
				skuList= buyerSkuDAO.findSKUsForDivision(division,buyerID);	
			}
		}catch(Exception e){
			logger.info("Exception in findSKUsForDivision method of ManageScopeServiceImpl: " +e.getStackTrace());
		}		
		
		return skuList;
	}
	
	public BigDecimal getAvailableBalance(Integer buyerId) throws Exception{
		double availableBalance = walletBO.getBuyerAvailableBalance(buyerId.longValue());
		BigDecimal bal = new BigDecimal(availableBalance).setScale(2,RoundingMode.HALF_UP);
		return bal;
	}
	public Integer getSoLevelFundingTypeId(String soId)throws Exception{
		Integer fundingTypeId=0;
		try{
			fundingTypeId=buyerSkuDAO.getSoLevelFundingTypeIdForBuyer(soId);
		}catch(Exception e){
			return fundingTypeId;
			}
		return fundingTypeId;
		
	}
	public List<ReasonCode> getScopeChangeReasonCodes(String buyerId){
		List<ReasonCode> reasonCodes = reasonCodeDao.getActiveReasonCodeByType(Integer.valueOf(buyerId),OrderConstants.TYPE_SCOPE);
		return reasonCodes;
	}
	
	public ProcessResponse changeScope(String serviceOrderId, HashMap<Integer, ManageTaskVO> tasks, Integer reasonId, String comment, SecurityContext securityContext){
		String reason = reasonCodeDao.getReasonCodeById(reasonId);
		OrderFulfillmentRequest ofRequest = OFUtils.createManageScopeOFRequest(tasks, reasonId, reason, comment, securityContext);
		return OFUtils.mapProcessResponse(ofHelper.runOrderFulfillmentProcess(serviceOrderId, SignalType.MANAGE_SCOPE, ofRequest));
	}

	public void setBuyerSkuDAO(BuyerSkuDao buyerSkuDAO) {
		this.buyerSkuDAO = buyerSkuDAO;
	}
	
	public void setWalletBO(IWalletBO walletBO) {
		this.walletBO = walletBO;
	}

	public void setReasonCodeDao(ManageReasonCodeDao reasonCodeDao) {
		this.reasonCodeDao = reasonCodeDao;
	}

	public BuyerOrderSku fetchSKU(Integer skuId) {
		return buyerSkuDAO.findSkuBySkuId(skuId);
	}
	
	public List<LookupServiceType> fetchSkills(Integer node){
		return buyerSkuDAO.fetchSkills(node);
	}
}