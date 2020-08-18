package com.newco.marketplace.web.delegatesImpl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sears.os.service.ServiceConstants;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.cache.ICacheManagerBO;
import com.newco.marketplace.business.iBusiness.ledger.ILedgerFacilityBO;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO;
import com.newco.marketplace.business.iBusiness.serviceorder.ISOAjaxBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderMonitor;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderSearchBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderUpsellBO;
import com.newco.marketplace.business.iBusiness.so.order.ServiceOrderRoleBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.criteria.FilterCriteria;
import com.newco.marketplace.criteria.OrderCriteria;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.vo.EstimateHistoryVO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.InitialPriceDetailsVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.LuProviderRespReasonVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.ordergroup.OrderGroupVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.serviceorder.ProviderDetail;
import com.newco.marketplace.dto.vo.serviceorder.ReasonLookupVO;
import com.newco.marketplace.dto.vo.serviceorder.SearchFilterVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderWfStatesCountsVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.dto.vo.so.order.SoCancelVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.BuyerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.DateDisplayUtil;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.vo.ServiceOrderMonitorResultVO;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.ILookupDelegate;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.dto.IncreaseSpendLimitDTO;
import com.newco.marketplace.web.dto.RejectServiceOrderDTO;
import com.newco.marketplace.web.dto.SOCancelDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrderMonitorTabTitleCount;
import com.newco.marketplace.web.dto.ServiceOrderNoteDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.utils.CriteriaHandlerFacility;
import com.newco.marketplace.web.utils.OFUtils;
import com.newco.marketplace.web.utils.ObjectMapper;
import com.newco.marketplace.web.utils.ObjectMapperDetails;
import com.newco.marketplace.web.utils.TabsMapper;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ActionContext;
import com.servicelive.activitylog.client.IActivityLogHelper;
import com.servicelive.activitylog.domain.ActivityCounts;
import com.servicelive.activitylog.domain.ActivityFilter;
import com.servicelive.activitylog.domain.ActivityLogGroupingType;
import com.servicelive.activitylog.domain.ActivityStatusType;
import com.servicelive.activitylog.domain.ActivityType;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import com.servicelive.wallet.serviceinterface.IWalletBO;
import com.servicelive.orderfulfillment.domain.SOElementCollection;

import javax.servlet.http.HttpSession;

public class SOMonitorDelegateImpl implements ISOMonitorDelegate {
	private static final Logger logger = Logger.getLogger(SOMonitorDelegateImpl.class);
 
	private ISOAjaxBO somAjaxBo = null;
	private ICacheManagerBO cacheManagerBO = null;
	private ILedgerFacilityBO accountingTransactionManagementImpl;
	private IServiceOrderSearchBO soSearchBO = null;
	private IServiceOrderBO serviceOrderBO = null;
	private IOrderGroupBO orderGroupBO = null;
	private IServiceOrderMonitor soMonitor = null;
	private ILookupDelegate luDelegate = null;
	private IServiceOrderUpsellBO  serviceOrderUpsellBO = null;
	private ServiceOrderRoleBO soRole;
	private static final String SERVICEDATE= "ServiceDate";
	private IWalletBO walletBO;
	private IActivityLogHelper activityLogHelper;
	private ILookupBO lookupBO = null;
	private OFHelper ofHelper;

	public ServiceOrderRoleBO getSoRole() {
		return soRole;
	}

	public void setSoRole(ServiceOrderRoleBO soRole) {
		this.soRole = soRole;
	}

	public double getAvailableBalance(AjaxCacheVO ajaxCacheVo)  throws BusinessServiceException {
		double value = 0.0;

		value = accountingTransactionManagementImpl
				.getAvailableBalance(ajaxCacheVo);

		return value;
	}
	
	public double getCurrentBalance(AjaxCacheVO ajaxCacheVo)  throws BusinessServiceException {
		double value = 0.0;

		value = accountingTransactionManagementImpl
				.getCurrentBalance(ajaxCacheVo);

		return value;
	}

//	public String getAllSOMCounts(AjaxCacheVO ajaxCacheVo) {
//		try {
//			CachedResultVO cachedResult;
//
//			cachedResult = cacheManagerBO.getAllSOMCounts(ajaxCacheVo);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return "";
//	}

	// TODO: Call should be made to CacheManager instead of BO
	public String getSummaryCounts(AjaxCacheVO ajaxCacheVO) {
		String xmlString = "";
		somAjaxBo = this.getSomAjaxBo();
		try {
			xmlString = somAjaxBo.getSummaryCounts(ajaxCacheVO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return xmlString;
	}

	// TODO: Call should be made to CacheManager instead of BO
	public String getDetailedCounts(AjaxCacheVO ajaxCacheVO) {
		String xmlString = "";
		somAjaxBo = this.getSomAjaxBo();
		try {
			xmlString = somAjaxBo.getDetailedCounts(ajaxCacheVO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return xmlString;
	}

	public ArrayList<ServiceOrderMonitorTabTitleCount> getTabs(
			AjaxCacheVO ajaxCacheVo) {
		ArrayList<ServiceOrderWfStatesCountsVO> sowfs = soRole
				.getTabs(ajaxCacheVo);
		
		//SL-15642 Start Modifying TabsMapper method to accommodate latest changes
		ArrayList<ServiceOrderMonitorTabTitleCount> soTabs = (ArrayList<ServiceOrderMonitorTabTitleCount>) TabsMapper
				.convertVOToDTO(sowfs, ajaxCacheVo);
		//SL-15642 End Modifying TabsMapper method to accommodate latest changes
		return soTabs;
	}
	
		
	public void setAddonPricesForServiceOrderVOList(List<ServiceOrderSearchResultsVO> serviceOrderSearchResults) {  
		int statusId;
		long startTime = System.currentTimeMillis();
		
		if(serviceOrderSearchResults != null) {
			for (ServiceOrderSearchResultsVO so : serviceOrderSearchResults) {
				if (so != null) {
					statusId = so.getSoStatus(); 
					Integer buyerId = so.getBuyerID()!= null ? so.getBuyerID().intValue() : -1;
					/**
					 * Modified by Shekhar as part of scalability
					 * Addon price is applicable only for following status :
					 * CANCELLED_STATUS, CLOSED_STATUS, COMPLETED_STATUS
					 * 
					 */
					if (statusId == OrderConstants.CANCELLED_STATUS || statusId == OrderConstants.CLOSED_STATUS 
							|| statusId == OrderConstants.COMPLETED_STATUS) {
						Double addonPrice = getAddonPrice(so.getSoId(), buyerId);
						if (logger.isDebugEnabled())
						  logger.debug("setAddonPricesForServiceOrderVOList: Addoin price for " + so.getSoId() + ":" + addonPrice);
						so.setAddonPrice(addonPrice);
						logger.debug("Inside close cancel complete");
					}
				}
			}
		}
		
		long endTime = System.currentTimeMillis();
		logger.debug("Total time taken by Inactive tab = [" + (endTime - startTime) + "] seconds");
	}



	public void setAddonPricesForServiceOrderDTOList(List<ServiceOrderDTO> dtoList)
	{
		if(dtoList != null)
		{
			for (ServiceOrderDTO so : dtoList) 
			{
				if (so != null)
				{
					String buyerIdStr  = so.getBuyerID();
					Integer buyerId = buyerIdStr!= null ? new Integer(buyerIdStr) : -1;
					Double addonPrice = getAddonPrice(so.getSoId(), buyerId);
					so.setAddonPrice(addonPrice);
					
				}
			}
		}		
	}
	
	
	// Utility method for getting the Upsell provider paid totals for a given Service Order
	private Double getAddonPrice(String soId, Integer buyerId) {
		
		Double providerPaidTotal = 0.0;
		
		// Fetch the Addons related to ServiceOrder
		List<ServiceOrderAddonVO> addons = serviceOrderUpsellBO.getAddonswithQtybySoId(soId);
		
		if(addons == null)
			return providerPaidTotal;
		
		if(addons.size() == 0)
			return providerPaidTotal;
		
		
		// Convert Raw Data to DTO
		for(ServiceOrderAddonVO vo : addons)
		{
			if(vo.getQuantity() > 0){
				Double providerPaid = vo.getRetailPrice() - (vo.getMargin() * vo.getRetailPrice());
				System.out.println("providerPaid: "+providerPaid);
				providerPaid = MoneyUtil.getRoundedMoney(providerPaid * vo.getQuantity());
				
				/* for HSR buyer do not add REQ Addon to providerPaidTotal as its already part of Spendlimit*/
				if(buyerId.intValue() == BuyerConstants.HSR_BUYER_ID && 
						OrderConstants.UPSELL_PAYMENT_TYPE_CREDIT.equals(vo.getCoverage()) ){
					continue;
				}
				
				providerPaidTotal = providerPaidTotal + providerPaid;
			}
		}
		return MoneyUtil.getRoundedMoney(providerPaidTotal);	
	}


	/*
	 * public String getBuyerTabCounts(AjaxCacheVO ajaxCacheVO) { String
	 * xmlString = ""; somAjaxBo = this.getSomAjaxBo(); try { xmlString =
	 * somAjaxBo.getBuyerCounts(ajaxCacheVO); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * return xmlString; }
	 * 
	 * public String getVendorTabCounts(AjaxCacheVO ajaxCacheVO) { String
	 * xmlString = ""; somAjaxBo = this.getSomAjaxBo(); try { xmlString =
	 * somAjaxBo.getVendorCounts(ajaxCacheVO); } catch (Exception e) {
	 * e.printStackTrace(); } return xmlString; }
	 */

	// SC-CORRECT METHOD
	public ArrayList<ServiceOrderDTO> getServiceOrdersByStatusTypes(
			ServiceOrdersCriteria soCriteria, FilterCriteria filters,
			SortCriteria sorts, PagingCriteria paging) throws Exception {

		CriteriaMap cMap = ObjectMapper.buildCriteraMap(soCriteria, filters,
				sorts, paging);

		ArrayList<Integer> statusCodes = new ArrayList<Integer>();
		statusCodes.add(soCriteria.getStatusId());
		/*
		 * ServiceOrderMonitorResultVO resultList =
		 * getSoMonitor().getServiceOrdersByStatusTypes(cMap );
		 */
		ServiceOrderMonitorResultVO resultList = soSearchBO
				.getServiceOrdersByStatusTypes(cMap);

		ArrayList<ServiceOrderDTO> resultSet = ObjectMapper
				.convertVOToDTO(resultList.getServiceOrderResults(), soCriteria.getRoleId());

		// update the paging. sorting, and filtering criteria
		CriteriaHandlerFacility.getInstance().updatePagingCriteria(
				OrderConstants.PAGING_CRITERIA_KEY, paging,
				resultList.getPaginationVO());

		return resultSet;
	}

	public List<String> getSoSearchIdsList(CriteriaMap criteriaMap) {
		
		List<String> soDTOList = new ArrayList<String>();

		soSearchBO = this.getSoSearchBO();

		try {
			ProcessResponse response = soSearchBO.retrieveSOSearchResultsIds(criteriaMap);
			
			soDTOList = (List<String>)response.getObj();
		} catch (DataServiceException e) {
			//do nothing 
		}

		return soDTOList;
	}

	public ServiceOrderMonitorResultVO getSoSearchList(CriteriaMap criteriaMap) {
		
		//List<ServiceOrderDTO> soDTOList = new ArrayList<ServiceOrderDTO>();
		//ServiceOrderDTO soDTO = new ServiceOrderDTO();
		ServiceOrderMonitorResultVO serviceOrderMonitorResultVO = null;
		soSearchBO = this.getSoSearchBO();

		try {
			ProcessResponse response = soSearchBO.retrievePagedSOSearchResults(criteriaMap);
			
			OrderCriteria orderCriteria = (OrderCriteria) criteriaMap
			.get(OrderConstants.ORDER_CRITERIA_KEY);
			
			if (!response.isError()) {
				serviceOrderMonitorResultVO = (ServiceOrderMonitorResultVO) response.getObj();
				setAddonPricesForServiceOrderVOList(serviceOrderMonitorResultVO.getServiceOrderResults());
				
				/**
				 * No data corresponding to buyer, provider, end customer & location is getting displayed in 
				 * Order Express Menu from Search tab in the case of SLAdmin Adoption as the role Id is reset as 
				 * that of Admin itself. Hence resting it back to the adopted user once the search is over, 
				 * just for this flow. 
				 * Fix to solve the same - start
				 */
				Integer roleID = 0;
				if (null != orderCriteria){
					if (null != orderCriteria.getAdoptedRoleId()){
						roleID = orderCriteria.getAdoptedRoleId();
					}else if (null != orderCriteria.getRoleId()){
						roleID = orderCriteria.getRoleId();
					}
				}
								
				List<ServiceOrderDTO> resultSet = ObjectMapper.convertVOToDTO(serviceOrderMonitorResultVO.getServiceOrderResults(), roleID);
				
				//Fix ends here
				
				/*List<ServiceOrderDTO> resultSet = ObjectMapper.convertVOToDTO(serviceOrderMonitorResultVO
						.getServiceOrderResults(), orderCriteria.getRoleId());*/
				serviceOrderMonitorResultVO.setServiceOrderResults(resultSet);

				for (ServiceOrderDTO record : resultSet) {
					if (null != resultSet) {
						record.setServiceOrderDateString(DateDisplayUtil.getDisplayDateWithTimeZone(record.getServiceOrderDateString(), record.getSystemTimezone(), record.getZip5()));
					}
				}	
				
			} else {

			}		
	
		} catch (DataServiceException e) {

		}

		return serviceOrderMonitorResultVO;
	}
	public ISOAjaxBO getSomAjaxBo() {
		return somAjaxBo;
	}

	public void setSomAjaxBo(ISOAjaxBO somAjaxBo) {
		this.somAjaxBo = somAjaxBo;
	}

	public IServiceOrderSearchBO getSoSearchBO() {
		return soSearchBO;
	}

	public void setSoSearchBO(IServiceOrderSearchBO soSearchBO) {
		this.soSearchBO = soSearchBO;
	}

	public ProcessResponse serviceOrderAddNote(Integer resourceId, Integer roleId, ServiceOrderNoteDTO noteDto,LoginCredentialVO lvRoles) 
										throws BusinessServiceException {
		
		//ServiceOrderNote theNote = ObjectMapper.convertNoteDTOToVO(noteDto);
		StringBuffer sbMessages = new StringBuffer();
		ProcessResponse pResp = null;
		try {
		    
		    ServiceOrderNote soNote = ObjectMapperDetails.convertServiceOrderNoteDTOtoVO(noteDto);
	        soNote.setCreatedDate(new Date(System.currentTimeMillis()));
	        soNote.setRoleId(lvRoles.roleId);
	        soNote.setCreatedByName(lvRoles.getLastName() + ", " + lvRoles.getFirstName());
	        soNote.setModifiedDate(null);
	        soNote.setModifiedBy(lvRoles.getUsername());

	        
	        soNote.setNoteTypeId(noteDto.getNoteTypeId());
	        String strPrivateNote = "false";
	        if(SOConstants.PRIVATE_NOTE.toString().equals(noteDto.getRadioSelection())){
	            strPrivateNote = "true";
	        }
			SecurityContext securityContext = getSecurityContext();
            soNote.setEntityId(securityContext.getVendBuyerResId());
			//Begin new OF System{
	        if(ofHelper.isNewSo(noteDto.getSoId())){
	            OrderFulfillmentResponse ofResponse;
	            OrderFulfillmentRequest request = OFUtils.mapSONote(soNote, noteDto.isEmailTobeSent());
	            request.setIdentification(OFUtils.createOFIdentityFromSecurityContext(securityContext));
	            ofResponse= ofHelper.runOrderFulfillmentProcess(noteDto.getSoId(), SignalType.ADD_NOTE, request);
	            return OFUtils.mapProcessResponse(ofResponse);
	        }
			
			pResp = getServiceOrderBO().processAddNote(resourceId, roleId, soNote.getSoId(), soNote.getSubject(), soNote.getNote(),
											noteDto.getNoteTypeId(), soNote.getCreatedByName(),soNote.getModifiedBy(), resourceId, noteDto.getRadioSelection(), noteDto.isEmailTobeSent(), noteDto.isEmptyNoteAllowed(), securityContext);
			
		} 
		catch (BusinessServiceException bse) {
			logger.error("serviceOrderAddNote()-->EXCEPTION-->", bse);
			sbMessages.append("ERROR_OCCURED");
		}

		return pResp;
		// return ((String) pResp.getObj());
	}

/*	public List<ServiceOrderNote> serviceOrderGetNotes(Integer buyerId,
			String serviceOrderId) {
		// ProcessResponse pResp =
		// getServiceOrderBO().addNoteServiceOrder(buyerId,
		// note);
		ProcessResponse pResp = getServiceOrderBO().processGetNotes(buyerId,
				serviceOrderId);
		return ((List<ServiceOrderNote>) pResp.getObj());
	}
*/	
	public ProcessResponse serviceOrderVoid(SOCancelDTO soCancelDto) throws BusinessServiceException {
		logger.debug("inside serviceOrderVoid");
		ProcessResponse pResp = null;
		String soId = "";
		int buyerId = 0;
		Integer reasonCode = null;
		String comment = null;
		String reason = null;
		
		if (soCancelDto != null){
			soId = soCancelDto.getSoId();
			buyerId = soCancelDto.getBuyerId();
			SecurityContext securityContext = getSecurityContext();
			securityContext.setActionSource(OrderConstants.FRONTEND_ACTION_SOURCE);
		    if(ofHelper.isNewSo(soId)){
			    OrderFulfillmentRequest ofRequest = OFUtils.createRequestForCancel(soCancelDto, securityContext);
			    OrderFulfillmentResponse ofResponse = ofHelper.runOrderFulfillmentProcess(soId, SignalType.SL_CANCEL_ORDER, ofRequest);
			    pResp = OFUtils.mapCancelProcessResponse(ofResponse, soCancelDto);
		    }else{	
				pResp = getServiceOrderBO().processVoidSO(buyerId, soId, securityContext);
		    }
			
		}
		return pResp;
	}// end method
	
	public ServiceOrder getServiceOrder(String soId )
	{
		try
		{
			return serviceOrderBO.getServiceOrder(soId);
		}
		catch (BusinessServiceException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public SoCancelVO getServiceOrderForCancel(String soId )
	{
		try
		{
			return serviceOrderBO.getServiceOrderForCancel(soId);
		}
		catch (BusinessServiceException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public OrderGroupVO getOrderGroupById(String groupId) {
		OrderGroupVO orderGroupVO = null; 
		try {
			orderGroupVO = orderGroupBO.getOrderGroupByGroupId(groupId);
		} catch (com.newco.marketplace.exception.BusinessServiceException e) {
			// exception should already have been logged at source
		}
		return orderGroupVO;
	}
	
	//SL-21465
	public EstimateVO getServiceOrderEstimationDetails(String soID,int vendorId,int ResourceId) {
		EstimateVO estimateVO = null; 
		try {
			estimateVO = orderGroupBO.getServiceOrderEstimationDetails(soID,vendorId,ResourceId);
		} catch (com.newco.marketplace.exception.BusinessServiceException e) {
			// exception should already have been logged at source
		}
		return estimateVO;
	}
	
	public EstimateVO getServiceOrderEstimationDetails(String soID) {
		EstimateVO estimateVO = null; 
		try {
			estimateVO = orderGroupBO.getServiceOrderEstimationDetails(soID);
		} catch (com.newco.marketplace.exception.BusinessServiceException e) {
			// exception should already have been logged at source
		}
		return estimateVO;
	}
	
	public List<EstimateHistoryVO> getServiceOrderEstimationHistoryDetailsForVendor(String soID,int vendorId,int ResourceId) {
		List<EstimateHistoryVO> estimateHistoryVO = null; 
		try {
			estimateHistoryVO = orderGroupBO.getServiceOrderEstimationHistoryDetailsForVendor(soID,vendorId,ResourceId);
		} catch (com.newco.marketplace.exception.BusinessServiceException e) {
			// exception should already have been logged at source
		}
		return estimateHistoryVO;
	}
	
	public List<EstimateHistoryVO> getServiceOrderEstimationHistoryDetailsForBuyer(String soID) {
		List<EstimateHistoryVO> estimateHistoryVO = null; 
		try {
			estimateHistoryVO = orderGroupBO.getServiceOrderEstimationHistoryDetailsForBuyer(soID);
		} catch (com.newco.marketplace.exception.BusinessServiceException e) {
			// exception should already have been logged at source
		}
		return estimateHistoryVO;
	}
	
	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public ProcessResponse increaseSpendLimit(IncreaseSpendLimitDTO soIncSLDto)
			throws BusinessServiceException {
		logger
				.debug("----Start of SOMonitorDelegateImpl.increaseSpendLimit----");
		String soId = soIncSLDto.getSelectedSO();
		String modifiedByName = "";
        SecurityContext securityContext = getSecurityContext();
        if(ofHelper.isNewSo(soId)){
        	
        	/*String errorMessage = validationErrors(currentSpendLimitLabor, currentSpendLimitParts,
    				totalSpendLimit, totalSpendLimitParts);
    		if (errorMessage != null) {
    			ProcessResponse pr = null;
    			pr = new ProcessResponse();
    			pr.setCode(ProcessResponse.USER_ERROR_RC);
    			pr.setMessage(errorMessage);
    			return pr;
    		}*/

            ProcessResponse pr = null;
        	try{
        		LoginCredentialVO roles = securityContext.getRoles();
              	if(null!=roles && null!=roles.getFirstName()&& null!=roles.getLastName()){
            		modifiedByName = (roles.getFirstName()+" "+roles.getLastName());
    	        }
        	}catch(Exception e){
        		logger.error("Error while fetching buyer details");        	
            }
        	 logger.debug("SL-16058. Starting increase price action");
            OrderFulfillmentRequest ofRequest = OFUtils.createAddFundsRequest(soIncSLDto, securityContext, modifiedByName);
            OrderFulfillmentResponse ofResponse = ofHelper.runOrderFulfillmentProcess(soId, SignalType.ADD_FUND, ofRequest);
            logger.debug("SL-16058. Ending increase price action");
            return OFUtils.mapProcessResponse(ofResponse);
        }
        ProcessResponse pr = null;
        /*double totalSLLabor = totalSpendLimit != null?totalSpendLimit.doubleValue():0.0;
        double totalSLParts = totalSpendLimitParts != null?totalSpendLimitParts.doubleValue():0.0;
		if (StringUtils.isNotBlank(groupId)) {
			try {
				pr = getOrderGroupBO().increaseSpendLimit(groupId, totalSLLabor, totalSLParts,
						increasedSpendLimitComment, buyerId, true, securityContext);
			} catch (com.newco.marketplace.exception.BusinessServiceException bsEx) {
				pr = new ProcessResponse();
				pr.setCode(ProcessResponse.USER_ERROR_RC);
				pr.setMessage("Unexpected error in increasing spend limit of group:" + groupId);
			}
		} else {
			pr = getServiceOrderBO().updateSOSpendLimit(soId, totalSLLabor, totalSLParts,
					increasedSpendLimitComment, buyerId, true, false,securityContext);
		}*/
		logger.debug("----End of SOMonitorDelegateImpl.increaseSpendLimit---- response code: " + pr.getCode());
		return pr;
	}
	
	private String validationErrors(Double currentSpendLimitLabor, Double currentSpendLimitParts,
			Double totalSpendLimit, Double totalSpendLimitParts) {
		
		if (totalSpendLimit == null){
			return OrderConstants.INCREASE_IN_SPEND_LIMIT_REQUIRED;
		}
		if (totalSpendLimitParts == null) {
			return OrderConstants.NUMBER_INCREASE_PARTS_LIMIT;
		}
		
		// Make sure the new limit is higher than the old limit
		if (currentSpendLimitLabor != null && currentSpendLimitParts != null){
			if (totalSpendLimit + totalSpendLimitParts <= currentSpendLimitLabor + currentSpendLimitParts) {
				return OrderConstants.INCREASE_IN_SPEND_LIMIT_MUST_INCREASE;
			}
		}
		
		// If there are no validation errors, return null
		return null;
	}

	public ProcessResponse rejectServiceOrder(List<Integer> checkedResourceID, String soId, String groupId,
			int reasonId, int responseId, String modifiedBy, String reasonDesc)
			throws BusinessServiceException {

		logger
				.debug("----Start of SOMonitorDelegateImpl.rejectServiceOrder----");
		ProcessResponse pr = null;
		SecurityContext securityContext = getSecurityContext();
		if (StringUtils.isNotBlank(groupId)) {
			if (ofHelper.isNewGroup(groupId)){
				Identification idfn = OFUtils.createOFIdentityFromSecurityContext(securityContext);
				List<RoutedProvider> rejectProviders = OFUtils.getRejectedProviders(checkedResourceID, reasonId, responseId, reasonDesc, modifiedBy);
				SOElementCollection coll=new SOElementCollection();
				coll.addAllElements(rejectProviders);				
				 logger.debug("SL-16058. Starting group reject action");
		        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentGroupProcess(groupId, SignalType.REJECT_GROUP, coll, idfn);
		        logger.debug("SL-16058. Ending group reject action");
		        return OFUtils.mapProcessResponse(response);
		        
		       
			}
			
			try {
				pr = getOrderGroupBO().rejectGroupedOrder(checkedResourceID.get(0), groupId, reasonId,
						responseId, modifiedBy, reasonDesc,securityContext);
			} catch (com.newco.marketplace.exception.BusinessServiceException bsEx) {
				pr = new ProcessResponse();
				pr.setCode(ProcessResponse.USER_ERROR_RC);
				pr.setMessage("Unexpected error in rejecting group:" + groupId);
			}
		} else {
		    //If the order is from the OrderFulfillment Flow:
		    if(ofHelper.isNewSo(soId)){
                boolean isSOInEditMode = false;
                try {
                    isSOInEditMode = getServiceOrderBO().isSOInEditMode(soId);
                } catch (BusinessServiceException e) {
                    //this should not happen just logging it
                    logger.error("At the time of acceptance getting service order ", e);
                }
		        Identification idfn = OFUtils.createOFIdentityFromSecurityContext(securityContext);
		        List<RoutedProvider> rejectProviders = OFUtils.getRejectedProviders(checkedResourceID, reasonId, responseId, reasonDesc, modifiedBy);
				SOElementCollection coll=new SOElementCollection();
				coll.addAllElements(rejectProviders);
				 logger.debug("SL-16058. Starting reject action");				
		        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.REJECT_ORDER, coll, idfn);
		        logger.debug("SL-16058. Ending reject action");
		        return OFUtils.mapProcessResponse(response, isSOInEditMode);
		        
		    }
			pr = getServiceOrderBO().rejectServiceOrder(checkedResourceID.get(0), soId, reasonId,
					responseId, modifiedBy, reasonDesc,securityContext);
		}
		logger.debug("----End of SOMonitorDelegateImpl.rejectServiceOrder----");
		sendRejectEmailToBuyer(soId);
		return pr;
	}

	private void sendRejectEmailToBuyer(String soId){
		boolean isAlertNeeded = getServiceOrderBO().isRejectAlertNeeded(soId);
		if (isAlertNeeded){
			SecurityContext securityContext = getSecurityContext();
			getServiceOrderBO().sendAllProviderRejectAlert(soId, securityContext);
		}
	}
	
	public String rejectServiceOrder(RejectServiceOrderDTO rso,List<Integer> checkedResourceID)
			throws BusinessServiceException {

//		ProcessResponse pr = null;
//		SecurityContext securityContext = getSecurityContext();
//		
//		if (StringUtils.isNotBlank(rso.getGroupId())) {
//			try {
//				pr = getOrderGroupBO().rejectGroupedOrder(rso.getResourceId(),
//						rso.getGroupId(), rso.getReasonId(), rso.getResponseId(),
//						rso.getModifiedBy(), rso.getReasonDesc(),securityContext);
//			} catch (com.newco.marketplace.exception.BusinessServiceException bsEx) {
//				pr = new ProcessResponse();
//				pr.setCode(ProcessResponse.USER_ERROR_RC);
//				pr.setMessage("Unexpected error in rejecting group:" + rso.getGroupId());
//			}
//		} else {
//			pr = getServiceOrderBO().rejectServiceOrder(rso.getResourceId(),
//					rso.getSoId(), rso.getReasonId(), rso.getResponseId(),
//					rso.getModifiedBy(), rso.getReasonDesc(),securityContext);
//		}
//		ArrayList arrMsgList = (ArrayList) pr.getMessages();
//		StringBuffer sbMessages = new StringBuffer();
//		for (int i = 0; i < arrMsgList.size(); i++) {
//			sbMessages.append(arrMsgList.get(i) + "\n");
//		}
//
//		String strMessage = sbMessages.toString();
//		sendRejectEmailToBuyer(rso.getSoId());
//		return strMessage;
		
		ProcessResponse response = this.rejectServiceOrder(checkedResourceID, rso.getSoId(), rso.getGroupId(), rso.getReasonId(), 
				rso.getResponseId(), rso.getModifiedBy(), rso.getReasonDesc());
		
		StringBuffer sbMessages = new StringBuffer();
		for(String s : response.getMessages())
			sbMessages.append(s).append("\n");
		return sbMessages.toString();
	}

	public ArrayList<ReasonLookupVO> getRejectReasonCodes()
			throws BusinessServiceException {
		ArrayList<ReasonLookupVO> arrRejectReasonCodeList = null;
		arrRejectReasonCodeList = getServiceOrderBO().queryListRejectReason();
		return arrRejectReasonCodeList;
	}

	public ArrayList<LuProviderRespReasonVO> getRejectReasons( LuProviderRespReasonVO luReasonVO ) throws BusinessServiceException
	{
		 
		/* Calling lookup delegate and putting reject codes in to servletContext if not already */
			luReasonVO.setSearchByResponse(SOConstants.PROVIDER_RESP_REJECTED);
			ArrayList<LuProviderRespReasonVO>  al = new ArrayList<LuProviderRespReasonVO>();
			al = luDelegate.getLuProviderRespReason(luReasonVO);
			return al;
	}
	
	public List<LookupVO> getAutocloseRules() throws BusinessServiceException
	{
		List<LookupVO> autocloseRulesList= new ArrayList<LookupVO>();
		autocloseRulesList=luDelegate.getLuAutocloseRules();
		return autocloseRulesList;
	}
	
	
	public String serviceOrderAccept(String soId, String userName,
			Integer resourceId, Integer companyId,Integer termAndCond, boolean validate) {
		SecurityContext securityContext = getSecurityContext();
		ProcessResponse pr = getServiceOrderBO().processAcceptServiceOrder(
				soId, resourceId, companyId,null, validate,false,true,securityContext);
		getServiceOrderBO().sendallProviderResponseExceptAccepted(soId,securityContext);
		ArrayList arrMsgList = (ArrayList) pr.getMessages();
		StringBuffer sbMessages = new StringBuffer();
		for (int i = 0; i < arrMsgList.size(); i++) {
			sbMessages.append(arrMsgList.get(i) + "<br />");
		}
		String strMessage = sbMessages.toString();
		return strMessage;
	}

	public ArrayList getSubStatusDetails(Integer statusId, Integer roleId) {
		ArrayList serviceOrderStatusVOList = null;
		if (statusId != null) {
			serviceOrderStatusVOList = soMonitor.getSOSubStatusForStatusId(
					statusId.intValue(), roleId);
		}
		return serviceOrderStatusVOList;

	}


	public ServiceOrderMonitorResultVO fetchServiceOrderResults(
			CriteriaMap criteriaMap) {
		ServiceOrderMonitorResultVO serviceOrderMonitorResultVO = null;
		//long startTime = System.currentTimeMillis();
		String sortAttributeName ;
		String sortOrder;

		try {
			
			serviceOrderMonitorResultVO = soSearchBO
					.getServiceOrdersByStatusTypes(criteriaMap);

			OrderCriteria orderCriteria = (OrderCriteria) criteriaMap
			.get(OrderConstants.ORDER_CRITERIA_KEY);
			
			List<ServiceOrderSearchResultsVO> soSearchResultsList = serviceOrderMonitorResultVO.getServiceOrderResults();
			setAddonPricesForServiceOrderVOList(soSearchResultsList);
			
			List<ServiceOrderDTO> resultSet = ObjectMapper.convertVOToDTO(serviceOrderMonitorResultVO.getServiceOrderResults(), orderCriteria.getRoleId());
			List<String> soSearchResultSoIdList = new ArrayList<String>(resultSet.size());

			for (ServiceOrderDTO record : resultSet) {
				if (record == null) continue;
				soSearchResultSoIdList.add(record.getSoId());
				record.setServiceOrderDateString(DateDisplayUtil.getDisplayDateWithTimeZone(record.getServiceOrderDateString(), record.getSystemTimezone(), record.getZip5()));				
			}
			this.findActivityCountsForOrderIdsAndAssignInResultSet(soSearchResultSoIdList, resultSet);			

			//JIRA : 7606 Special handling for date sorting.Just for Date, sort DTO before displaying.   
			SortCriteria sortCriteria  = (SortCriteria)criteriaMap.get(OrderConstants.SORT_CRITERIA_KEY);
			if (sortCriteria != null) {
				sortAttributeName = sortCriteria.getSortColumnName();
				sortOrder = sortCriteria.getSortOrder();
				if (StringUtils.isNotEmpty(sortAttributeName) && sortAttributeName.equalsIgnoreCase(SERVICEDATE)) {
					if (OrderConstants.SORT_ORDER_ASC.equals(sortOrder)) {
						Collections.sort(resultSet);
					} else {
						Collections.sort(resultSet, Collections.reverseOrder());
					}
	
				}
			}
			//
			serviceOrderMonitorResultVO.setServiceOrderResults(resultSet);
		} catch (DataServiceException dataServiceException) {
			dataServiceException.printStackTrace();
		}
		//long endTime = System.currentTimeMillis();
		//logger.info("Total time taken by SOM (Inactive) = [" + (endTime - startTime) + "] seconds");

		return serviceOrderMonitorResultVO;
	}

	private void findActivityCountsForOrderIdsAndAssignInResultSet(List<String> orderIds,
			List<ServiceOrderDTO> resultSet) {
		if (resultSet == null || resultSet.isEmpty()) return;

		Map<String, Long> noteCountsMap = this.getNoteCountsForOrderIds(orderIds);
		Map<String, Long> newNoteCountsMap = this.getNewNoteCountsForOrders(orderIds);
		Map<String, Long> newBidCountsMap = this.getNewBidCountsForOrders(orderIds);
		
		for (ServiceOrderDTO record : resultSet) {
			if (record == null) continue;
			if (OrderConstants.ROUTED_STATUS !=  record.getStatus().intValue()) continue;
			String recordSoId = record.getSoId();
			int noteCount = noteCountsMap.containsKey(recordSoId) ? noteCountsMap.get(recordSoId).intValue() : 0;
			int newNoteCount = newNoteCountsMap.containsKey(recordSoId) ? newNoteCountsMap.get(recordSoId).intValue() : 0;
			int newBidCount = newBidCountsMap.containsKey(recordSoId) ? newBidCountsMap.get(recordSoId).intValue() : 0;
			record.setNoteOrQuestionCount(noteCount);
			record.setNewNoteOrQuestionCount(newNoteCount);
			record.setNewBidCount(newBidCount);
		}
	}
	
	private Map<String, Long> extractMapOfCountsFromActivityCounts(ActivityCounts activityCounts) {
		if (activityCounts == null) return Collections.emptyMap();
		if (activityCounts.getGroupedCounts() == null) return Collections.emptyMap();
		if (activityCounts.getGroupedCounts().getGroups() == null) return Collections.emptyMap();
		return activityCounts.getGroupedCounts().getGroups();
	}
	
	private Map<String, Long> getNoteCountsForOrderIds(List<String> orderIds) {
		ActivityFilter filter = new ActivityFilter();
		for (String orderId : orderIds) {
			filter.addOrderId(orderId);
		}
		filter.addActivityType(ActivityType.Post);
		filter.addActivityStatusType(ActivityStatusType.ENABLED);
		ActivityCounts activityCounts = activityLogHelper.getActivityCounts(filter, ActivityLogGroupingType.ORDER_ID);
		return extractMapOfCountsFromActivityCounts(activityCounts);
	}
	private Map<String, Long> getNewNoteCountsForOrders(List<String> orderIds) {
		ActivityCounts activityCounts = activityLogHelper.getBuyersActionablePostCountsForOrders(orderIds);
		return extractMapOfCountsFromActivityCounts(activityCounts);
	}

	private Map<String, Long> getNewBidCountsForOrders(List<String> orderIds) {
		ActivityCounts activityCounts = activityLogHelper.getBuyersActionableBidCountsForOrders(orderIds);
		return extractMapOfCountsFromActivityCounts(activityCounts);
	}

	public ByteArrayOutputStream getPDF(String soId) {

		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out = getServiceOrderBO().getPDFForSO(soId);

		return out;
	}
	
	//for SL_15642

	//for creating a merged PDF
	public ByteArrayOutputStream printPaperwork(List<String> checkedSoIds,
			List<String> checkedOptions) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out = getServiceOrderBO().printPaperwork(checkedSoIds,checkedOptions);
		return out;
	}	

	public ProcessResponse withdrawCondOffer(String soId, Integer resourceId, Integer providerRespId) throws BusinessServiceException {
		SecurityContext securityContext = getSecurityContext();
		if(ofHelper.isNewSo(soId)){
		    com.servicelive.orderfulfillment.domain.RoutedProvider ofRoutedProvider=new com.servicelive.orderfulfillment.domain.RoutedProvider();
		    ofRoutedProvider.setProviderResourceId((long)resourceId);
		    Identification idfn = OFUtils.createOFIdentityFromSecurityContext(securityContext);
		    OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.WITHDRAW_CONDITIONAL_OFFER, ofRoutedProvider, idfn);
		    return OFUtils.mapProcessResponse(response);
		}
		ProcessResponse processResponse = getServiceOrderBO().withdrawConditionalAcceptance(soId,resourceId,providerRespId,securityContext);
		return processResponse;
	}
	
	public ProcessResponse withdrawGroupCondOffer(String groupId, Integer resourceId, Integer providerRespId){
		ProcessResponse processResponse = null;
		try
		{
			SecurityContext securityContext = getSecurityContext();
			if(ofHelper.isNewGroup(groupId)){
			    com.servicelive.orderfulfillment.domain.RoutedProvider ofRoutedProvider=new com.servicelive.orderfulfillment.domain.RoutedProvider();
			    ofRoutedProvider.setProviderResourceId((long)resourceId);
			    Identification idfn = OFUtils.createOFIdentityFromSecurityContext(securityContext);
			    OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentGroupProcess(groupId, SignalType.WITHDRAW_GROUP_CONDITIONAL_OFFER, ofRoutedProvider, idfn);
			    return OFUtils.mapProcessResponse(response);
			}
			processResponse = orderGroupBO.withdrawGroupedConditionalAcceptance(groupId, resourceId, providerRespId, securityContext);
		}
		catch (com.newco.marketplace.exception.BusinessServiceException e)
		{
			e.printStackTrace();
		}
		return processResponse;
	}
	
	private SecurityContext getSecurityContext(){
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		SecurityContext securityContext = null;
		if(sessionMap != null){
			securityContext = (SecurityContext)sessionMap.get(Constants.SESSION.SECURITY_CONTEXT);
		}
		return securityContext;
	}
	

	public double getPostSOLedgerAmount (String soId)  throws BusinessServiceException{
		return walletBO.getCurrentSpendingLimit(soId);
	}
	
	public ILedgerFacilityBO getAccountingTransactionManagementImpl() {
		return accountingTransactionManagementImpl;
	}

	public void setAccountingTransactionManagementImpl(
			ILedgerFacilityBO accountingTransactionManagementImpl) {
		this.accountingTransactionManagementImpl = accountingTransactionManagementImpl;
	}

	public ICacheManagerBO getCacheManagerBO() {
		return cacheManagerBO;
	}

	public void setCacheManagerBO(ICacheManagerBO cacheManagerBO) {
		this.cacheManagerBO = cacheManagerBO;
	}

	public IServiceOrderMonitor getSoMonitor() {

		return soMonitor;
	}

	public void setSoMonitor(IServiceOrderMonitor soMonitor) {

		this.soMonitor = soMonitor;
	}

	public ILookupDelegate getLuDelegate() {
		return luDelegate;
	}

	public void setLuDelegate(ILookupDelegate luDelegate) {
		this.luDelegate = luDelegate;
	}

	/**
	 * 
	 */
	public ProcessResponse deleteDraft(SOCancelDTO soCancelDto)
			throws BusinessServiceException {
		logger.debug("inside deleteDraft");
		String soId = soCancelDto.getSoId();
		SecurityContext securityContext = getSecurityContext();
		securityContext.setActionSource(OrderConstants.FRONTEND_ACTION_SOURCE);
		ProcessResponse processResponse = null;
		if(ofHelper.isNewSo(soId)){
			OrderFulfillmentRequest ofRequest = OFUtils.createRequestForCancel(soCancelDto, securityContext);
			OrderFulfillmentResponse ofResponse = ofHelper.runOrderFulfillmentProcess(soId, SignalType.SL_CANCEL_ORDER, ofRequest);
			processResponse = OFUtils.mapCancelProcessResponse(ofResponse,soCancelDto);
		}else{
			processResponse = getServiceOrderBO().deleteDraftSO(soId,securityContext);
		}
		return processResponse;
	}
	public String getDSTTimezone(String timezone){
 		String tz = "";
 		if("EST5EDT".equals(timezone)){
 			tz ="EDT";
 		}
 		if("AST4ADT".equals(timezone)){
 			tz ="ADT";
 		}
 		if("CST6CDT".equals(timezone)){
 			tz ="CDT";
 		}
 		if("MST7MDT ".equals(timezone)){
 			tz ="MDT";
 		}
 		if("PST8PDT".equals(timezone)){
 			tz ="PDT";
 		}
 		if("HST".equals(timezone)){
 			tz ="HADT";
 		}
 		if("Etc/GMT+1".equals(timezone)){
 			tz ="CEDT";
 		}
 		if("AST".equals(timezone)){
 			tz ="AKDT";
 		}
 		return tz;
 	}
	public String getStandardTimezone(String timezone){
		String tz = "";
		if("EST5EDT".equals(timezone)){
			tz="EST";
		}
		if("AST4ADT".equals(timezone)){  
			tz="AST";
		}
		if("CST6CDT".equals(timezone)){
			tz="CST";
		}
		if("MST7MDT ".equals(timezone)){
			tz="MST";
		}
		if("PST8PDT".equals(timezone)){
			tz="PST";
		}
		if("HST".equals(timezone)){
			tz="HAST";
		}
		if("Etc/GMT+1".equals(timezone)){
			tz="CET";
		}
		if("AST".equals(timezone)){
			tz="AKST";
		}		
		if("Etc/GMT-9".equals(timezone)){
			tz="PST-7";
		}
		if("MIT".equals(timezone)){
			tz="PST-3";
		}
		if("NST".equals(timezone)){
			tz="PST-4";
		}
		if("Etc/GMT-10".equals(timezone)){
			tz="PST-6";
		}
		if("Etc/GMT-11".equals(timezone)){
			tz="PST-5";
		}		
 		return tz;
 	}
	
	public boolean isAssociatedToViewSOAsPDF(String soId, Integer roleId,
			Integer requestingUserId) throws Exception {
		
		boolean isAuthorized = false;
		try{
			isAuthorized = getServiceOrderBO().isAssociatedToViewSOAsPDF(soId, roleId, requestingUserId);
		}catch(Exception e){
			logger.error("isAuthorizedToViewSOAsPDF-->EXCEPTION-->", e);
			throw e;
		}
	
		return isAuthorized; 
		
	}
	
	public List<ServiceOrderSearchResultsVO> getServiceOrdersForGroup(String groupId)
	{
		if(StringUtils.isBlank(groupId))
			return null;
		
		try
		{
			return orderGroupBO.getServiceOrdersForGroup(groupId);
		}
		catch (com.newco.marketplace.exception.BusinessServiceException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * This method is used to fetch all the provider names under a firm to load the filter
	 * @param resourceId
	 * @return ArrayList
	 */
	public ArrayList<ProviderDetail> loadServiceProviders(Integer resourceId){
		ArrayList<ProviderDetail> arrServiceProvidersList = null;
		try{			
			arrServiceProvidersList = getServiceOrderBO().queryServiceProviders(resourceId);					
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return arrServiceProvidersList;	
	}
	/**
	 * This method is used to fetch all the market names under a firm to load the filter
	 * @param resourceId
	 * @return ArrayList
	 */
	public ArrayList<ProviderDetail> loadServiceProviderMarkets(Integer resourceId){
		ArrayList<ProviderDetail> arrServiceProvidersList = null;
		try{			
			arrServiceProvidersList = getServiceOrderBO().queryMarketNames(resourceId);						
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return arrServiceProvidersList;
	}
	/**
	 * This method is used to fetch all state codes
	 * 
	 * @return ArrayList
	 */
	public List<LookupVO> getNotBlackedOutStateCodes(){
		List<LookupVO> arrStateList = null;
		try{			
			arrStateList = getLookupBO().getNotBlackedOutStateCodes();						
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return arrStateList;
	}
	/**
	 * This method is used to fetch all skills
	 * 
	 * @return ArrayList
	 */
	public List<LookupVO> getSkills(){
		List<LookupVO> arrStateList = null;
		try{			
			arrStateList = luDelegate.getSkills();						
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return arrStateList;
	}
	/**
	 * This method is used to fetch all markets
	 * 
	 * @return ArrayList
	 */
	public List<LookupVO> getMarkets(){
		List<LookupVO> arrMarketList = null;
		try{			
			arrMarketList = luDelegate.getMarkets();						
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return arrMarketList;
	}
	/**
	 * This method is used to fetch all the main Service Categories 
	 * @return ArrayList
	 */
	public ArrayList<SkillNodeVO> loadMainCategories(){
		ArrayList<SkillNodeVO> arrMainCategoryList = null;
		try {
			arrMainCategoryList = getLookupBO().getSkillTreeMainCategories();
		} catch (Exception e) {
			logger.error("Error in executing loadMainCategories :"
					+ e.getMessage());
		}		
		return arrMainCategoryList;
	}
	/**
	 * This method is used to fetch all the Service Categories and Sub Categories
	 * @return ArrayList
	 */
	public ArrayList<SkillNodeVO> loadCategoryAndSubCategories(Integer selectedId){
		ArrayList<SkillNodeVO> arrCategoryList = null;		
		try {
			arrCategoryList = getLookupBO().getSkillTreeCategoriesOrSubCategories(selectedId);
		} catch (Exception e) {
			logger.error("Error in executing loadCategoryAndSubCategories :"
					+ e.getMessage());
		}		
		return arrCategoryList;
}
	/**
	 * This method is used to fetch all markets
	 * 
	 * @return ArrayList
	 */
	public List<LookupVO> getMarketsByIndex(String sIndex,String eIndex){
		List<LookupVO> arrMarketList = null;
		try{			
			arrMarketList = luDelegate.getMarketsByIndex(sIndex,eIndex);						
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return arrMarketList;
}

	/**
	 * This method is used to fetch all substatus
	 * 
	 * @return List<LookupVO>
	 */
	public List<LookupVO> getSubStatusList(Integer statusId){
		List<LookupVO> subStatusList = null;
		try{			
			subStatusList = getLookupBO().getSubStatusList(statusId);						
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return subStatusList;
	}
	public SearchFilterVO saveSearchFilter(SearchFilterVO searchFilterVO ){
		try{
			searchFilterVO = soSearchBO.saveSearchFilter(searchFilterVO);
		}catch(Exception e){
			logger.error("Error in saving Search Filter :"+ e.getMessage());
		}
		return searchFilterVO;
	}
	
	public List<SearchFilterVO> getSearchFilters(SearchFilterVO searchFilterVO){
		List<SearchFilterVO> savedFilters = new ArrayList<SearchFilterVO>();
		try{
			savedFilters = soSearchBO.getSearchFilters(searchFilterVO);
		}catch(Exception e){
			logger.error("Error in getting Search Filter :"+ e.getMessage());
		}
		return savedFilters;
	}
	public SearchFilterVO getSelectedSearchFilter(SearchFilterVO searchFilterVO){
		SearchFilterVO savedFilter = new SearchFilterVO();
		try{
			savedFilter = soSearchBO.getSelectedSearchFilter(searchFilterVO);
		}catch(Exception e){
			logger.error("Error in getting Search Filter :"+ e.getMessage());
		}
		return savedFilter;
	}
	public SearchFilterVO deleteSearchFilter(SearchFilterVO searchFilterVO ){
		try{
			searchFilterVO = soSearchBO.deleteSearchFilter(searchFilterVO);
		}catch(Exception e){
			logger.error("Error in saving Search Filter :"+ e.getMessage());
		}
		return searchFilterVO;
	}
	public ILookupBO getLookupBO() {
		return lookupBO;
	}

	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
}	

	public IOrderGroupBO getOrderGroupBO() {
		return orderGroupBO;
	}

	public void setOrderGroupBO(IOrderGroupBO orderGroupBO) {
		this.orderGroupBO = orderGroupBO;
	}

	public IServiceOrderUpsellBO getServiceOrderUpsellBO() {
		return serviceOrderUpsellBO;
	}

	public void setServiceOrderUpsellBO(IServiceOrderUpsellBO serviceOrderUpsellBO) {
		this.serviceOrderUpsellBO = serviceOrderUpsellBO;
	}
	
	/**
	 * Sets the wallet client bo.
	 * 
	 * @param walletBO the new wallet client bo
	 */
	public void setWalletBO(IWalletBO walletBO) {
		this.walletBO = walletBO;
	}

	public IActivityLogHelper getActivityLogHelper() {
		return activityLogHelper;
	}

	public void setActivityLogHelper(IActivityLogHelper activityLogHelper) {
		this.activityLogHelper = activityLogHelper;
	}

    /**
     * @return the ofHelper
     */
    public OFHelper getOfHelper() {
        return ofHelper;
    }

    /**
     * @param ofHelper the ofHelper to set
     */
    public void setOfHelper(OFHelper ofHelper) {
        this.ofHelper = ofHelper;
    }

	public ProcessResponse increaseSpendLimit(String soId, String groupId,
			Double currentSpendLimitLabor, Double currentSpendLimitParts,
			Double totalSpendLimit, Double totalSpendLimitParts,
			String increasedSpendLimitComment, int buyerId)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isNonFundedBuyer(Integer buyerId) {
		boolean isNonFunded = false;
		try{
			isNonFunded=serviceOrderBO.isNonFundedBuyer(buyerId);
		}catch(Exception e){
		  logger.info("Exception in fetching fetaure set for validatting non funded feature"+ e);
		  }
		return isNonFunded;
	}

	//R12_1
	//SL-20379
	public ServiceOrderMonitorResultVO fetchServiceOrderResults(
			CriteriaMap criteriaMap, String tab) {
		ServiceOrderMonitorResultVO serviceOrderMonitorResultVO = null;
		//long startTime = System.currentTimeMillis();
		String sortAttributeName ;
		String sortOrder;

		try {
			
			if(StringUtils.isNotBlank(tab)){
				serviceOrderMonitorResultVO = soSearchBO.getServiceOrdersByStatusTypes(criteriaMap, tab);
			}else{
				serviceOrderMonitorResultVO = soSearchBO.getServiceOrdersByStatusTypes(criteriaMap);
			}

			OrderCriteria orderCriteria;
			if(StringUtils.isNotBlank(tab)){
				orderCriteria = (OrderCriteria) criteriaMap
				.get(OrderConstants.ORDER_CRITERIA_KEY+"_"+tab);
			}else{
				orderCriteria = (OrderCriteria) criteriaMap
				.get(OrderConstants.ORDER_CRITERIA_KEY);
			}
			
			List<ServiceOrderSearchResultsVO> soSearchResultsList = serviceOrderMonitorResultVO.getServiceOrderResults();
			setAddonPricesForServiceOrderVOList(soSearchResultsList);
			
			List<ServiceOrderDTO> resultSet = ObjectMapper.convertVOToDTO(serviceOrderMonitorResultVO.getServiceOrderResults(), orderCriteria.getRoleId());
			List<String> soSearchResultSoIdList = new ArrayList<String>(resultSet.size());

			for (ServiceOrderDTO record : resultSet) {
				if (record == null) continue;
				soSearchResultSoIdList.add(record.getSoId());
				record.setServiceOrderDateString(DateDisplayUtil.getDisplayDateWithTimeZone(record.getServiceOrderDateString(), record.getSystemTimezone(), record.getZip5()));				
			}
			this.findActivityCountsForOrderIdsAndAssignInResultSet(soSearchResultSoIdList, resultSet);			

			//JIRA : 7606 Special handling for date sorting.Just for Date, sort DTO before displaying. 
			SortCriteria sortCriteria;
			if(StringUtils.isNotBlank(tab)){
				sortCriteria = (SortCriteria)criteriaMap.get(OrderConstants.SORT_CRITERIA_KEY+"_"+tab);
			}else{
				sortCriteria = (SortCriteria)criteriaMap.get(OrderConstants.SORT_CRITERIA_KEY+"_"+tab);
			}
			  
			if (sortCriteria != null) {
				sortAttributeName = sortCriteria.getSortColumnName();
				sortOrder = sortCriteria.getSortOrder();
				if (StringUtils.isNotEmpty(sortAttributeName) && sortAttributeName.equalsIgnoreCase(SERVICEDATE)) {
					if (OrderConstants.SORT_ORDER_ASC.equals(sortOrder)) {
						Collections.sort(resultSet);
					} else {
						Collections.sort(resultSet, Collections.reverseOrder());
					}
	
				}
			}
			//
			serviceOrderMonitorResultVO.setServiceOrderResults(resultSet);
		} catch (DataServiceException dataServiceException) {
			dataServiceException.printStackTrace();
		}
		//long endTime = System.currentTimeMillis();
		//logger.info("Total time taken by SOM (Inactive) = [" + (endTime - startTime) + "] seconds");

		return serviceOrderMonitorResultVO;
	}
	/**
	 * This method is used to fetch spend limit details of so
	 * 
	 * @param soid
	 * @return InitialPriceDetailsVO
	 * @throws BusinessServiceException
	 */
	public InitialPriceDetailsVO getInitialPrice(String soId) throws BusinessServiceException{
		InitialPriceDetailsVO initialPriceDetailsVO = null;
		try{
			initialPriceDetailsVO = serviceOrderBO.getInitialPrice(soId);
		}catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		return initialPriceDetailsVO;
	}
}
