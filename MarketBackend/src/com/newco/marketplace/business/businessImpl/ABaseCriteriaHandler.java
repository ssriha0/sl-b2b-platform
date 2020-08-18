package com.newco.marketplace.business.businessImpl;

import com.newco.marketplace.criteria.OrderCriteria;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.dto.vo.ExploreMktPlace.ExploreMktPlaceSearchCriteria;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.pagination.PaginationFacility;
import com.newco.marketplace.vo.PaginationVO;

public class ABaseCriteriaHandler {

	private PaginationFacility paginationFacility = new PaginationFacility();

	private PaginationVO  pagination= null;
	private int clientPageSize = 0;
	private int clientStartIndex = 1;
	private int clientEndIndex = OrderConstants.DEFAULT_PAGE_SIZE;
	private String subStatus = "0";
	private String filterSubStatus = "0";
	private String filterStatus = "0";
	private String buyerId = "0";
	private Long zipCode = null;
	private Integer nodeId;
	private Integer marketReady = 1;
	private String searchQueryKey = null;
	private String priceModel = null;
	
	public OrderCriteria getOrderCriteria (CriteriaMap criteriaMap){
		OrderCriteria orderCriteria = (OrderCriteria)criteriaMap.get(OrderConstants.ORDER_CRITERIA_KEY);
		return orderCriteria;
	}
	
	public Integer[] getValidatedStatusCodes(Integer statusId, Integer roleId){
		Integer valdiatedStatusIds[] = new Integer[1];
		if (statusId!=null)
		{
			if ( statusId.intValue()== OrderConstants.TODAY_STATUS){
				valdiatedStatusIds = new Integer[4];
				valdiatedStatusIds[0]=new Integer(OrderConstants.ACTIVE_STATUS);
				valdiatedStatusIds[1]= new Integer(OrderConstants.COMPLETED_STATUS);
				valdiatedStatusIds[2]= new Integer(OrderConstants.PENDING_CANCEL_STATUS);
				if(OrderConstants.BUYER_ROLEID == roleId.intValue() || OrderConstants.SIMPLE_BUYER_ROLEID == roleId.intValue()){
					valdiatedStatusIds[3]= new Integer(OrderConstants.EXPIRED_STATUS);
				}
				
			}
			else if (statusId.intValue()== OrderConstants.ROUTED_STATUS){
				if(OrderConstants.BUYER_ROLEID == roleId.intValue() || OrderConstants.SIMPLE_BUYER_ROLEID == roleId.intValue()){
					valdiatedStatusIds = new Integer[1];
					valdiatedStatusIds[0]= new Integer(OrderConstants.ROUTED_STATUS);
				}
				else{
					valdiatedStatusIds[0]=new Integer(statusId);
				}
			}
			else if (statusId.intValue()== OrderConstants.INACTIVE_STATUS){
				if(OrderConstants.BUYER_ROLEID == roleId.intValue() 
						|| OrderConstants.SIMPLE_BUYER_ROLEID == roleId.intValue()){
					valdiatedStatusIds = new Integer[4];
					valdiatedStatusIds[0]= new Integer(OrderConstants.CANCELLED_STATUS);
					valdiatedStatusIds[1]= new Integer(OrderConstants.CLOSED_STATUS);
					valdiatedStatusIds[2]= new Integer(OrderConstants.VOIDED_STATUS);
					valdiatedStatusIds[3]= new Integer(OrderConstants.DELETED_STATUS);
				}
				else{
					valdiatedStatusIds = new Integer[2];
					valdiatedStatusIds[0]= new Integer(OrderConstants.CANCELLED_STATUS);
					valdiatedStatusIds[1]= new Integer(OrderConstants.CLOSED_STATUS);
				}
				
			}
			else if (statusId.intValue()== OrderConstants.SEARCH_STATUS){
				valdiatedStatusIds = new Integer[12];
				if(OrderConstants.BUYER_ROLEID == roleId.intValue() || OrderConstants.SIMPLE_BUYER_ROLEID == roleId.intValue()){
					valdiatedStatusIds[0]= new Integer(OrderConstants.DRAFT_STATUS);
				}
				valdiatedStatusIds[1]= new Integer(OrderConstants.ROUTED_STATUS);
				valdiatedStatusIds[2]= new Integer(OrderConstants.ACCEPTED_STATUS);
				valdiatedStatusIds[3]= new Integer(OrderConstants.ACTIVE_STATUS);
				valdiatedStatusIds[4]= new Integer(OrderConstants.COMPLETED_STATUS);
				valdiatedStatusIds[5]= new Integer(OrderConstants.CANCELLED_STATUS);
				valdiatedStatusIds[6]= new Integer(OrderConstants.CLOSED_STATUS);
				valdiatedStatusIds[7]= new Integer(OrderConstants.PROBLEM_STATUS);
				if(OrderConstants.BUYER_ROLEID == roleId.intValue()){
					valdiatedStatusIds[8]= new Integer(OrderConstants.EXPIRED_STATUS);
					valdiatedStatusIds[9]= new Integer(OrderConstants.VOIDED_STATUS);	
					valdiatedStatusIds[10]= new Integer(OrderConstants.DELETED_STATUS);		
				}
				valdiatedStatusIds[11] = new Integer(OrderConstants.PENDING_CANCEL_STATUS);
			}
			else{
				valdiatedStatusIds[0]=new Integer(statusId);
	
			}
		}
		else{
			valdiatedStatusIds[0]=new Integer(0);
		}
		return valdiatedStatusIds;
	}
	
	public void doCommonQueryInit( CriteriaMap criteraMap, boolean exploreTheMarket) {
		
		Object pageObj = null;
		Object orderObj = null;
		Object sortObj = null;
		Object filterObj = null;
		Object etmSearchObj = null;
		Object searchQryKey = null;
				
		if(!exploreTheMarket){
			pageObj = criteraMap.get(OrderConstants.PAGING_CRITERIA_KEY);
			orderObj = criteraMap.get(OrderConstants.ORDER_CRITERIA_KEY);
			sortObj = criteraMap.get(OrderConstants.SORT_CRITERIA_KEY);
			filterObj = criteraMap.get(OrderConstants.FILTER_CRITERIA_KEY);
		}else{
			pageObj = criteraMap.get(OrderConstants.ETM_PAGING_CRITERIA_KEY);
			orderObj = criteraMap.get(OrderConstants.ETM_ORDER_CRITERIA_KEY);
			sortObj = criteraMap.get(OrderConstants.ETM_SORT_CRITERIA_KEY);
			filterObj = criteraMap.get(OrderConstants.ETM_FILTER_CRITERIA_KEY);
			etmSearchObj = criteraMap.get(OrderConstants.ETM_SEARCH_CRITERIA_KEY);
			searchQryKey = criteraMap.get(OrderConstants.ETM_SEARCH_KEY);
		}
		
		if(etmSearchObj != null )
		{
			ExploreMktPlaceSearchCriteria etmSearchCriteria 
					= (ExploreMktPlaceSearchCriteria)etmSearchObj;
			this.zipCode = etmSearchCriteria.getBuyerZipCode();
			this.nodeId = etmSearchCriteria.getServiceCategoryId().intValue();
			this.marketReady = etmSearchCriteria.getMktReady();
		}
		
		if(searchQryKey != null )
		{
			this.searchQueryKey = (String)searchQryKey;
		}
		if(pageObj != null)
		{
			PagingCriteria pCriter = 
					(PagingCriteria)pageObj;
			clientPageSize = pCriter.getPageSize();
			clientStartIndex = pCriter.getStartIndex();
			clientEndIndex = pCriter.getEndIndex();
			subStatus = pCriter.getSubStatus();
		}
		if(orderObj != null)
		{
			OrderCriteria oCritera = 
				(OrderCriteria)orderObj;
			if(oCritera.getVendBuyerResId() != null)
			{
				buyerId = oCritera.getVendBuyerResId().toString();
			}
		}

		
	}
	
	//R12_1
	//SL-20379
	public void doCommonQueryInit( CriteriaMap criteraMap, boolean exploreTheMarket, String tab) {
		
		Object pageObj = null;
		Object orderObj = null;
		Object sortObj = null;
		Object filterObj = null;
		Object etmSearchObj = null;
		Object searchQryKey = null;
				
		if(!exploreTheMarket){
			pageObj = criteraMap.get(OrderConstants.PAGING_CRITERIA_KEY+"_"+tab);
			orderObj = criteraMap.get(OrderConstants.ORDER_CRITERIA_KEY+"_"+tab);
			sortObj = criteraMap.get(OrderConstants.SORT_CRITERIA_KEY+"_"+tab);
			filterObj = criteraMap.get(OrderConstants.FILTER_CRITERIA_KEY+"_"+tab);
		}else{
			pageObj = criteraMap.get(OrderConstants.ETM_PAGING_CRITERIA_KEY);
			orderObj = criteraMap.get(OrderConstants.ETM_ORDER_CRITERIA_KEY);
			sortObj = criteraMap.get(OrderConstants.ETM_SORT_CRITERIA_KEY);
			filterObj = criteraMap.get(OrderConstants.ETM_FILTER_CRITERIA_KEY);
			etmSearchObj = criteraMap.get(OrderConstants.ETM_SEARCH_CRITERIA_KEY);
			searchQryKey = criteraMap.get(OrderConstants.ETM_SEARCH_KEY);
		}
		
		if(etmSearchObj != null )
		{
			ExploreMktPlaceSearchCriteria etmSearchCriteria 
					= (ExploreMktPlaceSearchCriteria)etmSearchObj;
			this.zipCode = etmSearchCriteria.getBuyerZipCode();
			this.nodeId = etmSearchCriteria.getServiceCategoryId().intValue();
			this.marketReady = etmSearchCriteria.getMktReady();
		}
		
		if(searchQryKey != null )
		{
			this.searchQueryKey = (String)searchQryKey;
		}
		if(pageObj != null)
		{
			PagingCriteria pCriter = 
					(PagingCriteria)pageObj;
			clientPageSize = pCriter.getPageSize();
			clientStartIndex = pCriter.getStartIndex();
			clientEndIndex = pCriter.getEndIndex();
			subStatus = pCriter.getSubStatus();
		}
		if(orderObj != null)
		{
			OrderCriteria oCritera = 
				(OrderCriteria)orderObj;
			if(oCritera.getVendBuyerResId() != null)
			{
				buyerId = oCritera.getVendBuyerResId().toString();
			}
		}

		
	}

	
	public PaginationVO _getPaginationDetail(int totalRecordCount,
			int pageSize, int startIndex, int endIndex) {
		return paginationFacility.getPaginationDetail(totalRecordCount,
				pageSize, startIndex, endIndex);
	}


	public String getBuyerId() {
		return buyerId;
	}


	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}


	public int getClientEndIndex() {
		return clientEndIndex;
	}


	public void setClientEndIndex(int clientEndIndex) {
		this.clientEndIndex = clientEndIndex;
	}


	public int getClientPageSize() {
		return clientPageSize;
	}


	public void setClientPageSize(int clientPageSize) {
		this.clientPageSize = clientPageSize;
	}


	public int getClientStartIndex() {
		return clientStartIndex;
	}


	public void setClientStartIndex(int clientStartIndex) {
		this.clientStartIndex = clientStartIndex;
	}


	public PaginationFacility getPaginationFacility() {
		return paginationFacility;
	}


	public void setPaginationFacility(PaginationFacility paginationFacility) {
		this.paginationFacility = paginationFacility;
	}


	public String getSubStatus() {
		return subStatus;
	}


	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	public String getSearchQueryKey() {
		return searchQueryKey;
	}

	public void setSearchQueryKey(String searchQueryKey) {
		this.searchQueryKey = searchQueryKey;
	}

	public Long getZipCode() {
		return zipCode;
	}

	public void setZipCode(Long zipCode) {
		this.zipCode = zipCode;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public Integer getMarketReady() {
		return marketReady;
	}

	public void setMarketReady(Integer marketReady) {
		this.marketReady = marketReady;
	}

	public void setPriceModel(String priceModel) {
		this.priceModel = priceModel;
	}

	public String getPriceModel() {
		return priceModel;
	}

}
