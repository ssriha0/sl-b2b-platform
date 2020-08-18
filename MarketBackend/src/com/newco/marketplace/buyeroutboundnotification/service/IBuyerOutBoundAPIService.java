package com.newco.marketplace.buyeroutboundnotification.service;

import com.newco.marketplace.buyeroutboundnotification.beans.RequestMsgBody;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundFailOverVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public interface IBuyerOutBoundAPIService {
	
	public BuyerOutboundFailOverVO callAPIService(String xml, String soId) throws BusinessServiceException ;
	public BuyerOutboundFailOverVO callAPIService(String xml,RequestMsgBody request,String soId, BuyerOutboundFailOverVO notification) throws BusinessServiceException;

}
