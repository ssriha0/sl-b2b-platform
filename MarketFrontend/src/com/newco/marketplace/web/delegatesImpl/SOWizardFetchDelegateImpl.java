package com.newco.marketplace.web.delegatesImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO;
import com.newco.marketplace.business.iBusiness.providersearch.IMasterCalculatorBO;
import com.newco.marketplace.business.iBusiness.providersearch.IProviderSearchBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IBuyerSOTemplateBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.vo.BuyerDocumentTypeVO;
import com.newco.marketplace.dto.vo.BuyerSOTemplateForSkuVO;
import com.newco.marketplace.dto.vo.BuyerSkuCategoryVO;
import com.newco.marketplace.dto.vo.BuyerSkuTaskForSoVO;
import com.newco.marketplace.dto.vo.BuyerSkuVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.LuBuyerRefVO;
import com.newco.marketplace.dto.vo.LuServiceTypeTemplateVO;
import com.newco.marketplace.dto.vo.SkillTreeForSkuVO;
import com.newco.marketplace.dto.vo.ordergroup.OrderGroupVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchCriteriaVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;
import com.newco.marketplace.dto.vo.serviceorder.ContactLocationVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.web.delegates.ILookupDelegate;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.dto.SODocumentDTO;
import com.newco.marketplace.web.dto.SOWBrandingInfoDTO;
import com.newco.marketplace.web.dto.SOWPricingTabDTO;
import com.newco.marketplace.web.dto.SOWProviderDTO;
import com.newco.marketplace.web.dto.SOWProviderSearchDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderDescribeAndScheduleDTO;
import com.newco.marketplace.web.utils.OFUtils;
import com.newco.marketplace.web.utils.ObjectMapper;
import com.newco.marketplace.web.utils.ObjectMapperWizard;
import com.opensymphony.xwork2.ActionContext;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * $Revision: 1.52 $ $Author: gjacks8 $ $Date: 2008/05/27 16:03:14 $
 *
 */
public class SOWizardFetchDelegateImpl  implements ISOWizardFetchDelegate {

    private static final Logger logger = Logger.getLogger("SOWizardFetchDelegateImpl");
	private ILookupDelegate luDelegate;
	private IBuyerBO buyerBo;
	private IServiceOrderBO serviceOrderBO = null;
	private IDocumentBO documentBO;
	private IProviderSearchBO providerSearchBO;
	private IMasterCalculatorBO masterCalculatorBO;
	private IBuyerSOTemplateBO buyerSOTemplateBO;
	private IOrderGroupBO orderGroupBO;
    private OFHelper orderFulfillmentHelper;
    private IBuyerFeatureSetBO buyerFeatureSetBO;

 	private static int _defaultDistFilter = Integer.MIN_VALUE;

	private int getDefaultDistanceFilter() {
		if(_defaultDistFilter == Integer.MIN_VALUE) {
			_defaultDistFilter = Integer.parseInt(PropertiesUtils.getPropertyValue(Constants.AppPropConstants.PROVIDER_SEARCH_DISTANCE_FILTER));
		}
		return _defaultDistFilter;
	}
    
	public IDocumentBO getDocumentBO() {
		return documentBO;
	}

	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}

	public ArrayList<SOWProviderDTO> getProviderList(ProviderSearchCriteriaVO searchCriteria, String soId){
		ArrayList<ProviderResultVO> providers = new ArrayList<ProviderResultVO>();
		ArrayList<ProviderResultVO> masterProviders = new ArrayList<ProviderResultVO>();
		
		providers = providerSearchBO.getProviderList(searchCriteria);
		if (searchCriteria!= null && searchCriteria.getIsNewSpn()!= null && searchCriteria.getIsNewSpn()){
			Integer spnID = searchCriteria.getSpnID();
			searchCriteria.setIsNewSpn(Boolean.FALSE);
			searchCriteria.setSpnID(null);
			masterProviders = providerSearchBO.getProviderList(searchCriteria);		
			searchCriteria.setSpnID(spnID);
			searchCriteria.setIsNewSpn(Boolean.TRUE);
		}
		
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		if(null != sessionMap){
			//SL-19820
			//sessionMap.put(ProviderConstants.ALL_PROVIDERS, providers);
			sessionMap.put(ProviderConstants.ALL_PROVIDERS+"_"+soId, providers);
			if(masterProviders!= null && masterProviders.size()>0){
				sessionMap.put(ProviderConstants.SO_MASTER_PROVIDERS_LIST+"_"+soId, masterProviders);
			}
		}
		ArrayList<SOWProviderDTO> providersDto = ObjectMapperWizard.assingProviderResultVOToProvidersDto(providers);
		
		return providersDto;
	}
	
	
	public ArrayList<SOWProviderDTO> getRefinedProviderList(SOWProviderSearchDTO providerSearchDto,LocationVO serviceLocation, String soId){
		ArrayList<RatingParameterBean> ratingParamBeans = ObjectMapperWizard.ConvertProviderSearchDtoToRatingBean(providerSearchDto,serviceLocation);
		ArrayList<ProviderResultVO> providersVo = new ArrayList<ProviderResultVO>();
		ArrayList<ProviderResultVO> refinedProviders = new ArrayList<ProviderResultVO>();
		ArrayList<ProviderResultVO> savedProviders = new ArrayList<ProviderResultVO>();
		ArrayList<ProviderResultVO> masterProviders = new ArrayList<ProviderResultVO>();
		
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		
		if(null != sessionMap){
			//SL-19820
			//masterProviders = (ArrayList<ProviderResultVO>)sessionMap.get(ProviderConstants.SO_MASTER_PROVIDERS_LIST);
			masterProviders = (ArrayList<ProviderResultVO>)sessionMap.get(ProviderConstants.SO_MASTER_PROVIDERS_LIST+"_"+soId);
			
			if(masterProviders != null && masterProviders.size()> 0){
				//SL-19820
				//providersVo = (ArrayList<ProviderResultVO>)sessionMap.get(ProviderConstants.SO_MASTER_PROVIDERS_LIST);
				providersVo = (ArrayList<ProviderResultVO>)sessionMap.get(ProviderConstants.SO_MASTER_PROVIDERS_LIST+"_"+soId);
			}else{
				//SL-19820
				//providersVo = (ArrayList<ProviderResultVO>)sessionMap.get(ProviderConstants.ALL_PROVIDERS);
				providersVo = (ArrayList<ProviderResultVO>)sessionMap.get(ProviderConstants.ALL_PROVIDERS+"_"+soId);
			}
		}
		
		Integer selectedDistance=null;
		if(null!=sessionMap.get(ProviderConstants.SELECTED_DISTANCE+"_"+soId))
		{
			selectedDistance=(Integer)sessionMap.get(ProviderConstants.SELECTED_DISTANCE+"_"+soId);
		}
		
		Integer distance = getDefaultDistanceFilter();
		
		if(null!=providerSearchDto.getDistance() && null!=distance 
				&& providerSearchDto.getDistance().intValue() > distance.intValue() && null == selectedDistance)
		{	
			ProviderSearchCriteriaVO providerSearchCriteriaVO=(ProviderSearchCriteriaVO)sessionMap.get(ProviderConstants.SEARCH_CRITERIA+"_"+soId); 
			providerSearchCriteriaVO.setSelectedDistance(providerSearchDto.getDistance());
			providersVo = fetchProvidersWithDistance(providerSearchCriteriaVO,soId);
			sessionMap.put(ProviderConstants.SELECTED_DISTANCE+"_"+soId, providerSearchDto.getDistance());
		}else if(null!=providerSearchDto.getDistance() && null!=distance && null!=selectedDistance 
				&& providerSearchDto.getDistance().intValue() > distance.intValue() 
				&& providerSearchDto.getDistance().intValue() > selectedDistance.intValue())
		{
			ProviderSearchCriteriaVO providerSearchCriteriaVO=(ProviderSearchCriteriaVO)sessionMap.get(ProviderConstants.SEARCH_CRITERIA+"_"+soId); 
			providerSearchCriteriaVO.setSelectedDistance(providerSearchDto.getDistance());
			providersVo = fetchProvidersWithDistance(providerSearchCriteriaVO,soId);
			sessionMap.put(ProviderConstants.SELECTED_DISTANCE+"_"+soId, providerSearchDto.getDistance());
		}
		savedProviders = copyProvidersList(providersVo);
		refinedProviders = masterCalculatorBO.getFilteredProviderList(ratingParamBeans, savedProviders);
		if(!refinedProviders.isEmpty() && providerSearchDto.getSpn()!=null){
			List<Integer> vendorIds = new ArrayList<Integer>();
			for(ProviderResultVO vo:refinedProviders){
				vendorIds.add(vo.getVendorID());
			}
			List<LookupVO> perfScores = providerSearchBO.fetchFirmPerfScores(providerSearchDto.getSpn(), vendorIds);
			
			refinedProviders = setFirmPerfScore(refinedProviders,perfScores);
		}
		
		ArrayList<SOWProviderDTO> providersDto = ObjectMapperWizard.assingProviderResultVOToProvidersDto(refinedProviders);
		
		//SL-19820
		//sessionMap.put(ProviderConstants.FILTERED_PROVIDERS, providersDto);
		sessionMap.put(ProviderConstants.FILTERED_PROVIDERS+"_"+soId, providersDto);
		//ArrayList<SOWProviderDTO> refinedProviders = masterCalculatorBO.getFilteredProviderList(ratingParamBeans, providers)
		return providersDto;
	}
	
	private ArrayList<ProviderResultVO> fetchProvidersWithDistance(
			ProviderSearchCriteriaVO providerSearchCriteriaVO,String soId) {
		ArrayList<ProviderResultVO> providersVo;
		ArrayList<ProviderResultVO> provider = new ArrayList<ProviderResultVO>();
		ArrayList<ProviderResultVO> masterProvider = new ArrayList<ProviderResultVO>();
		
		provider = providerSearchBO.getProviderList(providerSearchCriteriaVO);
		if (providerSearchCriteriaVO!= null && providerSearchCriteriaVO.getIsNewSpn()!= null && providerSearchCriteriaVO.getIsNewSpn()){
			Integer spnID = providerSearchCriteriaVO.getSpnID();
			providerSearchCriteriaVO.setIsNewSpn(Boolean.FALSE);
			providerSearchCriteriaVO.setSpnID(null);
			masterProvider = providerSearchBO.getProviderList(providerSearchCriteriaVO);			
		}
		
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		if(null != sessionMap){
			sessionMap.put(ProviderConstants.ALL_PROVIDERS+"_"+soId, provider);
			if(masterProvider!= null && masterProvider.size()>0){
				sessionMap.put(ProviderConstants.SO_MASTER_PROVIDERS_LIST+"_"+soId, masterProvider);
			}
		}
		
		if(masterProvider != null && masterProvider.size()> 0){
			providersVo=masterProvider;
		}else{
			providersVo=provider;
		}
		
		return providersVo;
	}
	//setting performance score for firm
	private ArrayList<ProviderResultVO> setFirmPerfScore(ArrayList<ProviderResultVO> refinedProviders,List<LookupVO> perfScores){
		ArrayList<ProviderResultVO> refinedProvidersList = refinedProviders;
			for(LookupVO lvo:perfScores){
				for(ProviderResultVO pvo:refinedProvidersList){
					if(pvo.getVendorID().equals(lvo.getId())){
						pvo.setFirmPerformanceScore(lvo.getdId());
					}
				}
			}
			return refinedProvidersList;
	}

	public List<String> getServiceOrderIDsForGroup(String groupId) throws BusinessServiceException {
		List<String> soIds = new ArrayList<String>();
		try {
			soIds = getServiceOrderBO().getServiceOrderIDsForGroup(groupId);	
		} catch(BusinessServiceException bse) {
			logger.debug("Exception thrown while getting service order IDs for Group ID (" + groupId + ")");
			throw bse;
		}
		
		return soIds;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ISOWizardFetchDelegate#getServiceOrderVO(java.lang.String)
	 */
	public HashMap<String, Object> getServiceOrderDTOs(String soID, int roleID) throws BusinessServiceException {
		switch (roleID) {
		case OrderConstants.BUYER_ROLEID:
			return getProServiceOrderDTOs(soID);
		case OrderConstants.SIMPLE_BUYER_ROLEID:
			return getSimpleServiceOrderDTOs(soID);
		}
		return null;
	}//end method
	//
	@SuppressWarnings("null")
	public Boolean fetchSkuIndicatorFromSoWorkFlowControl(String soID)
	{
	IServiceOrderBO soBO = null;
	soBO = this.getServiceOrderBO();
	Boolean fetchSkuIndicatorFromWfmc=false;
	fetchSkuIndicatorFromWfmc=soBO.fetchSkuIndicatorFromSoWorkFlowControl(soID);
	return fetchSkuIndicatorFromWfmc;
		}
	
	private HashMap getProServiceOrderDTOs(String soID) throws BusinessServiceException{
		IServiceOrderBO soBO = null;
		ServiceOrder so = null;
		Boolean skuIndicatorFromSoWorkFlowControl=false;
		HashMap<String, Object> dtoMap = null;
		
		soBO = this.getServiceOrderBO();
		
		try
		{
		    so = soBO.getServiceOrder(soID);
		    if(null!=so){
		    if(null!=  so.getBuyer()){
			    boolean permitInd = buyerFeatureSetBO.validateFeature(so.getBuyer().getBuyerId(), BuyerFeatureConstants.TASK_LEVEL);
			    so.getBuyer().setPermitInd(permitInd);
		    	}		    
		    }
		    
		}//end try
		catch (BusinessServiceException bse)
		{
			logger.debug("Exception thrown while getting service order(" + soID + ")");
			throw bse;
			
		}//end try
		//Fetching the sku indicator value from so_workflowcontrol
		if(null!=so)
		{
		skuIndicatorFromSoWorkFlowControl=fetchSkuIndicatorFromSoWorkFlowControl(soID);
		if(skuIndicatorFromSoWorkFlowControl!=null)
		{
		if(skuIndicatorFromSoWorkFlowControl.equals(true))
		{
		//If service order created using sku setting the information into sku list rather than tasks list
			
			 so.setSkuTaskIndicator(true);
		}
		}
		}
		//convert VO to Hashmap of DTO
		if(null!=so)
		{
		dtoMap = ObjectMapperWizard.convertSOTOSOWTabDTOs(so);
		String groupId = so.getGroupId();
		if(StringUtils.isNotBlank(groupId)){
			updateGroupPriceInSOWPricingTabDTO(dtoMap, groupId);
		}
		}
		
		
		
		return dtoMap;
	}
	/*
	 * Setting the status of sku indicator attribute of service order entity class
	 */
//	@SuppressWarnings("null")
//	public Boolean checkSkuIndicatorValue(String soID)
//	{
//		Boolean fetchSkuIndicatorFromWfmc=false;
//		fetchSkuIndicatorFromWfmc=fetchSkuIndicatorFromSoWorkFlowControl(soID);
//		return fetchSkuIndicatorFromWfmc;
//	}
	
	
	
	/*
	 * set group level price into pricing dto
	 */
	private void updateGroupPriceInSOWPricingTabDTO(
				HashMap<String, Object> dtoMap, String groupId) throws BusinessServiceException {
		
		SOWPricingTabDTO sowPricingTabDTO = (SOWPricingTabDTO)dtoMap.get(OrderConstants.SOW_PRICING_TAB);
		
		OrderGroupVO ogVO = null;
		try {
			ogVO = getOrderGroupBO().getOrderGroupByGroupId(groupId);
		} catch (com.newco.marketplace.exception.BusinessServiceException e) {
			logger.error(e.getMessage(), e);
			throw new BusinessServiceException("Error thrown while geting groupVo for groupId " + groupId , e);
		}
		Double groupTotalPermitPrice = ogVO.getTotalPermitPrice();
		if (groupTotalPermitPrice == null) {
			groupTotalPermitPrice = MoneyUtil.DOUBLE_ZERO;
		}
		sowPricingTabDTO.setGroupTotalPermits(groupTotalPermitPrice);		
		// Get the current spend limits for labor and parts
		Double currentSpendLimitLabor = ogVO.getFinalSpendLimitLabor();
		Double currentSpendLimitParts = ogVO.getFinalSpendLimitParts();		
		
		if(currentSpendLimitLabor == null) {
			currentSpendLimitLabor = ogVO.getDiscountedSpendLimitLabor();
			if (currentSpendLimitLabor == null) {
				currentSpendLimitLabor = MoneyUtil.DOUBLE_ZERO;
			}
		}
		
		if(currentSpendLimitParts == null) {
			currentSpendLimitParts = ogVO.getDiscountedSpendLimitParts();
			if (currentSpendLimitParts == null) {
				currentSpendLimitParts = MoneyUtil.DOUBLE_ZERO;
			}
		}
		
		// Save the original value for use upon save
		sowPricingTabDTO.setOgCurrentLaborSpendLimit(currentSpendLimitLabor);
		sowPricingTabDTO.setOgCurrentPartsSpendLimit(currentSpendLimitParts);			
		
		if(sowPricingTabDTO.isTaskLevelPricingInd()){
			currentSpendLimitLabor =currentSpendLimitLabor-groupTotalPermitPrice;
		}
		// Init the value to be displayed on the tab/panel
		sowPricingTabDTO.setOgLaborSpendLimit(currentSpendLimitLabor);
		sowPricingTabDTO.setOgPartsSpendLimit(currentSpendLimitParts);
	}

/*	private HashMap<String, Object> getProServiceOrderDTOs(String orderID, boolean groupedInd) throws BusinessServiceException {
		
		IServiceOrderBO soBO = getServiceOrderBO();
		List<ServiceOrder> serviceOrders = null;
		ServiceOrder so = null;
		HashMap<String, Object> dtoMap = null;
		
		try {
			if(groupedInd) {
				serviceOrders = soBO.getServiceOrdersForGroup(orderID);
			} else {
				so = soBO.getServiceOrder(orderID);
				serviceOrders = new ArrayList<ServiceOrder>();
				serviceOrders.add(so);
			}
		    
		} catch(BusinessServiceException bse) {
			logger.debug("Exception thrown while getting service order(" + orderID + ")");
			throw bse;
		}
		
		//convert VO to Hashmap of DTO
		dtoMap = ObjectMapperWizard.convertSOTOSOWTabDTOs(serviceOrders);
		
		return dtoMap;
	}
	
	private HashMap<String, Object> getProGroupOrderDTOs(String groupID) throws BusinessServiceException {
		
		List<ServiceOrder> groupOrder = null;
		HashMap<String, Object> dtoMap = null;
		
		try {
			//groupOrder = groupBO.getServiceOrderVOsForGroup(groupID);
			groupOrder = getServiceOrderBO().getServiceOrdersForGroup(groupID);
		} catch(BusinessServiceException bse) {
			logger.debug("Exception thrown while getting service order(" + groupID + ")");
			throw bse;			
		}
		
		//convert VO to Hashmap of DTO
		dtoMap = ObjectMapperWizard.convertGroupSOTOSOWTabDTOs(groupOrder);
		
		return dtoMap;
	}
*/	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ISOWizardFetchDelegate#getSimpeServiceOrderDTOs(java.lang.String, boolean)
	 */
	public HashMap<String, Object> getSimpleServiceOrderDTOs(String soID) throws BusinessServiceException {
		IServiceOrderBO soBO = null;
		ServiceOrder so = null;
		HashMap<String, Object> dtoMap = null;
		Boolean skuIndicatorFromSoWorkFlowControl;
		soBO = this.getServiceOrderBO();
		
		try
		{
		    so = soBO.getServiceOrder(soID);
		    
		}//end try
		catch (BusinessServiceException bse)
		{
			logger.debug("Exception thrown while getting service order(" + soID + ")");
			throw bse;
			
		}//end try
		//Fetching the sku indicator value from so_workflowcontrol
		if(null!=so)
		{
		skuIndicatorFromSoWorkFlowControl=fetchSkuIndicatorFromSoWorkFlowControl(soID);
		if(skuIndicatorFromSoWorkFlowControl==true)
		{
		//If service order created using sku setting value in session
			//setAttribute("skuAndTaskIndicatorValue",skuIndicatorFromSoWorkFlowControl);
			 so.setSkuTaskIndicator(true);
		}
		}
		//convert VO to Hashmap of DTO
		dtoMap = ObjectMapperWizard.convertSOtoSSODTOs(so);
		return dtoMap;
	}
	
	public CreateServiceOrderDescribeAndScheduleDTO loadDefaultLocation(Integer buyerID, Integer buyerResourceID) {
		//load loacationVO from IDs
		ContactLocationVO clocationVO = getBuyerBo().getBuyerResDefaultLocationVO(buyerResourceID);
		if (clocationVO != null && clocationVO.getBuyerPrimaryLocation() != null) {
			return ObjectMapperWizard.convertLocationVOToDescribeDTO(clocationVO.getBuyerPrimaryLocation(), new CreateServiceOrderDescribeAndScheduleDTO());
		}
		return null;
	}
	
	public ILookupDelegate getLuDelegate() {
		return luDelegate;
	}

	public void setLuDelegate(ILookupDelegate luDelegate) {
		this.luDelegate = luDelegate;
	}

	public void setServiceOrderEditIndicator(String soID)
	{	
	}
	
	public IBuyerBO getBuyerBo() {
		return buyerBo;
	}

	public void setBuyerBo(IBuyerBO buyerBo) {
		this.buyerBo = buyerBo;
	}

	public ContactLocationVO getBuyerContLocDetails(int buyerId) {
		ContactLocationVO conLocVo=buyerBo.getBuyerContactLocationVO(buyerId);
		return conLocVo;
	}
	
	/**
	 * Get Contact and Location details for the Buyer Resource
	 * @param buyerId Buyer Id of the Buyer Resource
	 * @param buyerResId Buyer Resource Id
	 */
	public ContactLocationVO getBuyerResContLocDetails(int buyerId, int buyerResId) {
		ContactLocationVO conLocVo=buyerBo.getBuyerResContactLocationVO(buyerId, buyerResId);
		return conLocVo;
	}

	public void setLockEditMode(String soId, Integer editIndex, SecurityContext securityContext) {
        if(orderFulfillmentHelper.isNewSo(soId)){
            OrderFulfillmentResponse ofResponse;
            OrderFulfillmentRequest ofRequest = OFUtils.newOrderFulfillmentRequest(new SOElementCollection(), securityContext);
            //only make a call if the buyer is editing
            if(ofRequest.getIdentification().isBuyer()){
	            if(OrderConstants.SO_VIEW_MODE_FLAG == editIndex){
	            	logger.debug("Invoking signal RELEASE_LOCK_FOR_EDIT on SO ID: " + soId);
	                ofResponse=orderFulfillmentHelper.runOrderFulfillmentProcess(soId, SignalType.RELEASE_LOCK_FOR_EDIT,ofRequest);
	            }else{
	            	logger.debug("Invoking signal LOCK_FOR_EDIT on SO ID: " + soId);
	                ofResponse=orderFulfillmentHelper.runOrderFulfillmentProcess(soId, SignalType.LOCK_FOR_EDIT,ofRequest);
	            }
	            if(ofResponse.isError())
	                logger.error(ofResponse.getErrorMessage());
            }
            return;
        }
		try {
			this.getServiceOrderBO().setLockMode(soId, editIndex);
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
	}

	public ArrayList<SkillNodeVO> getSkillTreeMainCategories() throws BusinessServiceException {
		ArrayList<SkillNodeVO> al = null;
		try {
			al = (ArrayList) luDelegate.getSkillTreeMainCategories();
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
		return al;		
	}
	
	public ArrayList<SkillNodeVO> getSkillTreeCategoriesOrSubCategories(
			Integer selectedId) throws BusinessServiceException {
		ArrayList<SkillNodeVO> al = null;
		try {
			al = (ArrayList) luDelegate.getSkillTreeCategoriesOrSubCategories(selectedId);
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
		return al;		
	}
	
	public ArrayList<SkillNodeVO> getNotBlackedOutSkillTreeMainCategories(String buyerId,String stateCd) throws BusinessServiceException {
		ArrayList<SkillNodeVO> al = null;
		try {
			al = (ArrayList) luDelegate.getNotBlackedOutSkillTreeMainCategories(buyerId,stateCd);
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
		return al;		
	}
	
	public ArrayList<SkillNodeVO> getNotBlackedOutSkillTreeCategoriesOrSubCategories(
			Integer selectedId,String buyerId,String stateCd) throws BusinessServiceException {
		ArrayList<SkillNodeVO> al = null;
		try {
			al = (ArrayList) luDelegate.getNotBlackedOutSkillTreeCategoriesOrSubCategories(selectedId,buyerId,stateCd);
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
		return al;		
	}
	
	public List<LookupVO> getStateCodes() throws DataServiceException {
		return luDelegate.getStateCodes();		
	}	
	
	public ArrayList<LookupVO> getPercentOwnedList() throws DataServiceException{
		ArrayList<LookupVO>  percentageList = null;
		try {
			percentageList = (ArrayList) luDelegate.getPercentOwnedList();
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return percentageList;		
	}
	
	public ArrayList<DocumentVO> getDocumentsBySOID(DocumentVO documentVO, Integer userId) throws DataServiceException {
		 ArrayList<DocumentVO>  sc = null;
		try {
			
			sc = (ArrayList) documentBO.retrieveDocumentsByServiceOrderId(
						documentVO.getSoId(), documentVO.getRoleId(), userId);
			//sc = (ArrayList) luDelegate.getStateCodes();
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return sc;		
	}	
	
	public ArrayList<DocumentVO> getDocumentsMetaDataBySOID(DocumentVO documentVO,Integer userId,String docSource) throws DataServiceException {
		 ArrayList<DocumentVO>  sc = null;
		try {
			
			sc = (ArrayList) documentBO.retrieveDocumentsMetaDataByServiceOrderId(
					 documentVO.getSoId(), documentVO.getRoleId(), userId,docSource);
			//sc = (ArrayList) luDelegate.getStateCodes();
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return sc;		
	}	
	
	public ArrayList<LookupVO> getPhoneTypes() throws DataServiceException {
		 ArrayList<LookupVO>  aList = null;
		try {
			aList = (ArrayList) luDelegate.getPhoneTypes();
			
			//Need to take out Fax as a type from this phone list. Fax numbers have separate business/display rules
			Iterator i = aList.iterator();
			while(i.hasNext()){
				LookupVO l = (LookupVO)i.next();
				if(l.getDescr().equalsIgnoreCase("Fax")){
					i.remove();
				}
			}
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return aList;		
	}	
	public ArrayList<LookupVO> getShippingCarrier() throws DataServiceException {
		 ArrayList<LookupVO>  aList = null;
		try {
			aList = (ArrayList) luDelegate.getShippingCarrier();
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return aList;		
	}
	/*Method to fetch sku category on the basis of buyer id for service order creation by sku*/
	public List<BuyerSkuCategoryVO>  fetchBuyerSkuCategories(Integer buyerId) throws DelegateException
	{
		List<BuyerSkuCategoryVO> resultSer = null;
		
		resultSer = luDelegate.fetchBuyerSkuCategories(buyerId);
	
		
		return resultSer;
	}
	/*Ajax Method to fetch sku name on the basis of category id for service order creation by sku*/
	public List<BuyerSkuVO>fetchBuyerSkuNameByCategory(Integer categoryId) throws DelegateException
	{
			List<BuyerSkuVO> resultSer = null;
		
			resultSer = luDelegate.fetchBuyerSkuNameByCategory(categoryId);
	
		
		return resultSer;
	}
	/*Ajax Method to fetch detail on the basis of sku id for service order creation by sku*/
	public BuyerSkuTaskForSoVO fetchBuyerSkuDetailBySkuId(Integer skuId)throws DelegateException
	{
		BuyerSkuTaskForSoVO buyerSkuDetailBySkuId=null;
		buyerSkuDetailBySkuId = luDelegate.fetchBuyerSkuDetailBySkuId(skuId);
		return buyerSkuDetailBySkuId;
		
	}
	/*Ajax Method to fetch bid price on the basis of sku id for service order creation by sku*/
	public Double fetchBidPriceBySkuId(Integer skuId)throws DelegateException
	{
		Double skuBidPrice=0.0;
		skuBidPrice = luDelegate.fetchBidPriceBySkuId(skuId);
		return skuBidPrice;
		
	}
	/*Ajax Method to fetch template detail on the basis of sku id for service order creation by sku*/
	public LuServiceTypeTemplateVO fetchServiceTypeTemplate(Integer serviceTypeTemplateId) throws DelegateException
	{
		LuServiceTypeTemplateVO luServiceTemplate=null;
		luServiceTemplate=luDelegate.fetchServiceTypeTemplate(serviceTypeTemplateId);
		return luServiceTemplate;
	}
	/*Ajax Method to fetch skill tree detail on the basis of node id for service order creation by sku*/
	public SkillTreeForSkuVO fetchSkillTreeDetailBySkuId(Integer nodeId) throws DelegateException
	{
		SkillTreeForSkuVO buyerSkillTreeDetail=null;
		buyerSkillTreeDetail=luDelegate.fetchSkillTreeDetailBySkuId( nodeId);
		return buyerSkillTreeDetail;
	}
	/*Ajax Method to fetch template detail on the basis of template id for service order creation by sku*/
	public BuyerSOTemplateForSkuVO fetchBuyerTemplateDetailBySkuId(Integer templateId) throws DelegateException
	{
	
		BuyerSOTemplateForSkuVO templateDetail=null;
		templateDetail=luDelegate.fetchBuyerTemplateDetailBySkuId(templateId);
			return templateDetail;
	
	}
	public  List<DocumentVO> retrieveDocumentByTitleAndEntityID(String title,Integer buyerId)
	{
		List<DocumentVO> documentForVO=null;
		try {
			documentForVO=luDelegate.retrieveDocumentByTitleAndEntityID(title,buyerId);
		} catch (DelegateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return documentForVO;
		
	}
	
	/**This method will fetch the document id for the given title uploaded by the buyer
	 * @param title
	 * @param buyerId
	 * @return
	 * @throws DelegateException
	 * @throws BusinessServiceException 
	 */
	
	public Integer retrieveBuyerLogDocumentId(String title, Integer buyerId)throws BusinessServiceException {
		Integer buyerDocumentLogo =null;
		try{
			List<DocumentVO> documentForVO = luDelegate.retrieveDocumentByTitleAndEntityID(title,buyerId);
			if(null!= documentForVO && !documentForVO.isEmpty()){
				for(DocumentVO documentVO : documentForVO){
					if(StringUtils.equals(title, documentVO.getTitle())){
						buyerDocumentLogo = documentVO.getDocumentId();
						break;
					}
				}
			}
		}catch (Exception e) {
			logger.error("Exception in fetching the document id for the given title for the buyer", e);
			throw new BusinessServiceException(e);
		}
		return buyerDocumentLogo;
	}
	
	public boolean getIfZipInServiceBlackout(Integer nodeId, String zip) throws BusinessServiceException {
		try {
			return luDelegate.getIfZipInServiceBlackout(nodeId, zip);
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
		return false;
	}


	/**
	 * @return the serviceOrderBO
	 */
	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}


	/**
	 * @param serviceOrderBO the serviceOrderBO to set
	 */
	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}	
	
	public SOWBrandingInfoDTO retrieveBuyerDocumentByDocumentId(Integer documentId)throws BusinessServiceException
	{
		DocumentVO documentVO = documentBO.retrieveBuyerDocumentByDocumentId(documentId);
		SOWBrandingInfoDTO brandingInfoDTO = ObjectMapper.convertLogoImageVOToDTO(documentVO);
		return brandingInfoDTO;
	}
	
	
	public SODocumentDTO retrieveServiceOrderDocumentByDocumentId (Integer documentId) throws BusinessServiceException{
		DocumentVO documentVO = documentBO.retrieveServiceOrderDocumentByDocumentId(documentId);
		SODocumentDTO documentDTO = ObjectMapper.convertDocumentVOToDTO(documentVO);
		
		return documentDTO;
	}
	
	
	public void applyBrandingLogo(String soId, Integer logoDocumentId)throws BusinessServiceException {
		try {
			serviceOrderBO.updateSoHdrLogoDocument(soId, logoDocumentId);
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
	}
	
	
	public ArrayList<SOWBrandingInfoDTO> retrieveBuyerDocumentsByBuyerIdAndCategory(Integer buyerId, Integer categoryId, Integer roleId, Integer userId) throws BusinessServiceException{
		ArrayList<DocumentVO> brandingInfoListVO = (ArrayList)documentBO.retrieveBuyerDocumentsByBuyerIdAndCategory(buyerId, categoryId, roleId, userId);//serviceOrderDao;
		ArrayList<SOWBrandingInfoDTO> brandingInfoListDTO = ObjectMapper.convertBrandInfoVOToDTO(brandingInfoListVO);
		return brandingInfoListDTO;
	}
	
	public ArrayList<LuBuyerRefVO> getBuyerRef(String buyerId) {
		ArrayList<LuBuyerRefVO> bRef = new ArrayList<LuBuyerRefVO>();

		try {
			bRef = getLuDelegate().getBuyerRef(buyerId);
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return bRef;
	}

	public Boolean saveServiceOrderAsTemplate(Integer buyerID,ServiceOrderDTO serviceOrderDto){
		BuyerSOTemplateDTO templateDto = ObjectMapper.convertServiceOrderToTemplateDto(serviceOrderDto);
		String title = "";
		if(serviceOrderDto != null){
			title = serviceOrderDto.getTitle();
		}
		return getBuyerSOTemplateBO().saveBuyerSOTemplate(buyerID, title, templateDto);
	}
	
	
	public IProviderSearchBO getProviderSearchBO() {
		return providerSearchBO;
	}


	public void setProviderSearchBO(IProviderSearchBO providerSearchBO) {
		this.providerSearchBO = providerSearchBO;
	}

	public IMasterCalculatorBO getMasterCalculatorBO() {
		return masterCalculatorBO;
	}

	public void setMasterCalculatorBO(IMasterCalculatorBO masterCalculatorBO) {
		this.masterCalculatorBO = masterCalculatorBO;
	}
	
	private ArrayList<ProviderResultVO> copyProvidersList(ArrayList<ProviderResultVO> providers){
		ArrayList<ProviderResultVO> newProviderList = new ArrayList<ProviderResultVO>();
		ProviderResultVO providerVo = null;
		for(int i=0;i<providers.size();i++)
		{
			providerVo = new ProviderResultVO();
			ProviderResultVO vo = providers.get(i);
			providerVo.setBackgroundCheckStatus(vo.getBackgroundCheckStatus());
			providerVo.setCity(vo.getCity());
			providerVo.setCredentials(vo.getCredentials());
			providerVo.setDistance(vo.getDistance());
			providerVo.setDistanceFromBuyer(vo.getDistanceFromBuyer());
			providerVo.setIsSLVerified(vo.getIsSLVerified());
			providerVo.setLanguages(vo.getLanguages());
			providerVo.setPercentageMatch(vo.getPercentageMatch());			
			providerVo.setProviderFirstName(vo.getProviderFirstName());
			providerVo.setProviderLastName(vo.getProviderLastName());
			providerVo.setProviderLatitude(vo.getProviderLatitude());
			providerVo.setProviderLongitude(vo.getProviderLongitude());
			providerVo.setProviderRating(vo.getProviderRating());
			providerVo.setProviderStarRating(vo.getProviderStarRating());
			providerVo.setResourceId(vo.getResourceId());
			providerVo.setSkillNodes(vo.getSkillNodes());
			providerVo.setState(vo.getState());
			providerVo.setVendorID(vo.getVendorID());
			providerVo.setVendorInsuranceTypes(vo.getVendorInsuranceTypes());
			providerVo.setAddtionalInsuranceTypes(vo.getAddtionalInsuranceTypes());
			providerVo.setSpnetStates(vo.getSpnetStates());
			providerVo.setFirmName(vo.getFirmName());
			providerVo.setAdditionalInsuranceList(vo.getAdditionalInsuranceList());
			newProviderList.add(providerVo);
		}		
		return newProviderList;
	}
	
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
	

	public IBuyerSOTemplateBO getBuyerSOTemplateBO() {
		return buyerSOTemplateBO;
	}

	public void setBuyerSOTemplateBO(IBuyerSOTemplateBO buyerSOTemplateBO) {
		this.buyerSOTemplateBO = buyerSOTemplateBO;
	}

	/**
	 * @return the orderGroupBO
	 */
	public IOrderGroupBO getOrderGroupBO() {
		return orderGroupBO;
	}

	/**
	 * @param orderGroupBO the orderGroupBO to set
	 */
	public void setOrderGroupBO(IOrderGroupBO orderGroupBO) {
		this.orderGroupBO = orderGroupBO;
	}
	/**
	 * This method provides the valid extensions from the DB
	 * @return String
	 */
	public String getValidDisplayExtensions(){
		String validDisplayExtension=documentBO.getValidDisplayExtensions();
		return validDisplayExtension;
	}
	/**
	 * This method retrives a boolean value after checking if the Order can be routed.
	 * @param serviceOrderId
	 * @return boolean
	 */
	public boolean checkStatusForRoute(String serviceOrderId){
		return serviceOrderBO.checkStateForRoute(serviceOrderId);
	}
	/**
	 * Get Contact and Location details for the Buyer Contact associated with the SO
	 * @param soID
	 * @return conLocVo
	 */
	public ContactLocationVO getBuyerResContLocDetailsForSO(String soID)throws DelegateException {
		try{
			ContactLocationVO conLocVo=buyerBo.getBuyerResContactLocationVOForSO(soID);
			return conLocVo;
		}catch(BusinessServiceException bse){
			throw new DelegateException(bse.getMessage(),bse);
		}
	}


	/**
	 * Get Contact and Location details for the Buyer Contact Id
	 * @param contactId
	 * @return conLocVo
	 */
	public ContactLocationVO getBuyerResContLocDetailsForContactId(Integer contactId)throws DelegateException {
		try{
			ContactLocationVO conLocVo=buyerBo.getBuyerResContactLocationVOForContactId(contactId);
			return conLocVo;
		}catch(BusinessServiceException bse){
			throw new DelegateException(bse.getMessage(),bse);
		}
	}


	/**
	 * This method retrieves parent node id for a subCategoryId.
	 * @param subCategoryId
	 * @return Integer
	 * throws DelegateException
	 */
	public Integer getParentNodeId(Integer subCategoryId) throws DelegateException{		
			Integer parentNodeId = luDelegate.getParentNodeId(subCategoryId);		
			return parentNodeId;		
	}

	/**
	 * This method retrieves the list of valid part Status from lu_part_status
	 * @return Arraylist<LookupVO>
	 */
	public ArrayList<LookupVO> getPartStatus() throws DataServiceException {
		 ArrayList<LookupVO>  aList = null;
		try {
			aList = (ArrayList) luDelegate.getPartStatus();
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return aList;		
	}
	
	/**
	 * This method retrieves the list of document types for a buyer
	 * @return Arraylist<BuyerDocumentTypeVO>
	 */
	public List<BuyerDocumentTypeVO> retrieveDocumentByBuyerIdAndSource(Integer buyerId,
			Integer source) throws BusinessServiceException {
		
		List<BuyerDocumentTypeVO> buyerexistingTypes = null;
		
		
		//Retrieving documents type to be uploaded by the provider
		buyerexistingTypes =  documentBO.retrieveDocTypesByBuyerId(buyerId,source);	
		// TODO Auto-generated method stub
		return buyerexistingTypes;
	}
	
	/**
	 * Retrieves reference documents by buyer id, to be listed in Create SO
	 * @param buyerId
	 * @param soId
	 * @return List<DocumentVO>
	 */
	public  List<DocumentVO> retrieveRefBuyerDocumentsByBuyerId(Integer buyerId, String soId)
	{
		List<DocumentVO> buyerDocList=null;
		try {
			buyerDocList = documentBO.retrieveRefBuyerDocumentsByBuyerId(buyerId, soId);
		} catch (BusinessServiceException e) {
			logger.error("Error retrieving buyer ref documents in SOWizardFetchDelegateImpl.retrieveRefBuyerDocumentsByBuyerId()", e);
			e.printStackTrace();
		}
			return buyerDocList;
		
	}
	
	/**
	 * fetch view prov doc permission for buyer
	 * @param userName
	 * @return 
	 */
	//to check whether buyer has permission to view prov docs
	public boolean getViewDocPermission(String userName){
		boolean viewDocPermission = false;
		try {
			viewDocPermission = luDelegate.getViewDocPermission(userName);
		} catch (BusinessServiceException e) {
			logger.error("Error while retrieving viewDocPermission in SOWizardFetchDelegateImpl.getViewDocPermission()", e);
			e.printStackTrace();
		}
			return viewDocPermission;
	}
	
	/**@Description: Fetching the logo document id for the service order
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	
	public Integer getLogoDocumentId(String soId)throws BusinessServiceException {
		Integer logoDocumentId =null;
		try{
			logoDocumentId = serviceOrderBO.getLogoDocumentId(soId);
		}catch (Exception e) {
			logger.error("Error while retrieving document Id  in SOWizardFetchDelegateImpl.getLogoDocumentId()", e);
			e.printStackTrace();
		}
		return logoDocumentId;
	}
	
    public void setOrderFulfillmentHelper(OFHelper orderFulfillmentHelper) {
        this.orderFulfillmentHelper = orderFulfillmentHelper;
    }

	public IBuyerFeatureSetBO getBuyerFeatureSetBO() {
		return buyerFeatureSetBO;
	}

	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}

	

	

	
}//end class