package com.newco.marketplace.web.delegates;

import java.util.Map;

import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.FundingVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.SoDocumentVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public interface ISOWizardPersistDelegate {
	public String processCreateDraftSO(ServiceOrder serviceOrder);
	public String processUpdateDraftSO(ServiceOrder serviceOrder,boolean routingPriorityApplied) throws BusinessServiceException;
	public String processRouteSO(String soId, Integer buyerId, boolean postFromFE, boolean routingPriorityApplied) throws BusinessServiceException;
	public ProcessResponse insertSODocument(DocumentVO documentVO);
	public ProcessResponse deleteSODocument(DocumentVO documentVO);
	public ProcessResponse deleteSODocumentforTask(String soId);
	public Buyer getBuyerAttrFromBuyerId(Integer buyerId) throws BusinessServiceException;
	public void processRouteSimpleBuyerSO(ServiceOrder so) throws BusinessServiceException;
	public FundingVO checkBuyerFundsForIncreasedSpendLimit(ServiceOrder serviceOrder, Integer buyerId) throws BusinessServiceException;
	public String processReRouteSO(String soId, Integer buyerId) throws BusinessServiceException;
	public void processReRouteSimpleBuyerSO(ServiceOrder so) throws BusinessServiceException;
	public void autoPostSO(String soId) throws BusinessServiceException;
	public Map getTaskPrice(Integer taskId) throws BusinessServiceException, DataServiceException;
	public void insertSODocuments(SoDocumentVO documentVO);
}
