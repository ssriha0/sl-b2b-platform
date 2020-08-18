package com.newco.marketplace.business.businessImpl.orderGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.ZipParameterBean;
import com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO;
import com.newco.marketplace.business.iBusiness.orderGroup.IRouteOrderGroupBO;
import com.newco.marketplace.business.iBusiness.providersearch.IMasterCalculatorBO;
import com.newco.marketplace.business.iBusiness.providersearch.IProviderSearchBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IBuyerSOTemplateBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchCriteriaVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.orderGroup.IOrderGroupDao;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.util.so.ServiceOrderUtil;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;

/**
 * @author sldev
 * 
 */
public class RouteOrderGroupBOImpl implements IRouteOrderGroupBO {

	private Logger logger = Logger.getLogger(RouteOrderGroupBOImpl.class);

	private IServiceOrderBO serviceOrderBO;
	private IProviderSearchBO provSearchBO;
	private IMasterCalculatorBO masterCalcBO;
	private IBuyerSOTemplateBO soTemplateBO;
	private ServiceOrderDao serviceOrderDAO;
	private IOrderGroupDao orderGroupDAO;
	private IOrderGroupBO orderGroupBO;

	/**
	 * JIRA SL:9966 fix: Checking if there is only one order in the group. It is
	 * possible in multi-treaded scenario if there is a concurrency issue. If
	 * there is only one order in the group then treating the order as
	 * individual order. Sometimes deleted orders are remain in the group due to
	 * concurrency issue, skip such orders.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.business.iBusiness.orderGroup.IRouteOrderGroupBO#processRouteOrderGroup(java.util.List,
	 *      java.lang.Integer, com.newco.marketplace.auth.SecurityContext)
	 */
	public Integer processRouteOrderGroup(
			List<ServiceOrderSearchResultsVO> grpOrdersList, Integer tierId,
			SecurityContext securityContext) throws BusinessServiceException {
		List<List<Integer>> providersList = new ArrayList<List<Integer>>();
		List<ServiceOrderSearchResultsVO> grpOrdersListNew = new ArrayList<ServiceOrderSearchResultsVO>();

		int grpSize = grpOrdersList.size();

		if (grpSize == 0) {
			return 0;
		} else if (grpSize == 1) {
			return handleSingleOrderInGroup(grpOrdersList.get(0), tierId);
		}

		for (ServiceOrderSearchResultsVO soResultVO : grpOrdersList) {
			ServiceOrder so = getServiceOrder(soResultVO);

			// The state might have changed by now to concurrent execution. So
			// lets read it again.
			Integer wfStateId = so.getWfStateId();

			if (wfStateId == OrderConstants.ROUTED_STATUS
					|| wfStateId == OrderConstants.DRAFT_STATUS) {
				List<Integer> routedResourceIds = getProvidersListForSO(so,
						tierId);
				providersList.add(routedResourceIds);
				grpOrdersListNew.add(soResultVO);
			}
		}

		int grpSizeNew = grpOrdersListNew.size();

		if (grpSizeNew == 0) {
			return 0; // returning 0 provider.
		} else if (grpSizeNew == 1) {
			return handleSingleOrderInGroup(grpOrdersListNew.get(0), tierId);
		} else {
			return routeOrdersInGrp(grpOrdersListNew, providersList, tierId,
					securityContext);
		}
	}

	private Integer handleSingleOrderInGroup(ServiceOrderSearchResultsVO child,
			Integer tierId) throws BusinessServiceException {
		String groupId = child.getParentGroupId();
		if (StringUtils.isNotBlank(groupId))
			orderGroupBO.unGroupServiceOrder(child, groupId, 1);
		return routeIndividualOrder(child, tierId);
	}

	private ServiceOrder getServiceOrder(ServiceOrderSearchResultsVO soResultVO)
			throws BusinessServiceException {
		String soId = soResultVO.getSoId();
		ServiceOrder so = null;
		try {
			so = serviceOrderBO.getServiceOrder(soId);
		} catch (com.newco.marketplace.exception.core.BusinessServiceException ex) {
			String message = "Service order not found for SoId:RouteOrderGroupBOImpl "
					+ soId;
			logger.info(message);
			throw new BusinessServiceException(message);
		}

		// check if the service order was found
		if (null == so) {
			logger
					.info("Service order ---> null for SoId:RouteOrderGroupBOImpl "
							+ soId);
		}
		return so;
	}

	public List<Integer> getProvidersListForSO(ServiceOrder so, Integer tierId) {
		BuyerSOTemplateDTO template = getTemplateForOrder(so);
		// setup filters
		ArrayList<RatingParameterBean> ratingParamBeans = getRatingParamBean(so);

		// Setup search criteria and get the providers for the criteria
		ProviderSearchCriteriaVO provSearchVO = getProviderSearchCriteria(so,
				template, tierId);
		ArrayList<ProviderResultVO> resultsAL = provSearchBO
				.getProviderList(provSearchVO);

		// Apply filters and get refined results
		List<ProviderResultVO> filteredResultsAL = masterCalcBO
				.getFilteredProviderList(ratingParamBeans, resultsAL);

		List<Integer> routedResourceIds = new ArrayList<Integer>();
		// Check the percentage match if there is percentage match configured in
		// the template
		if (template != null && template.getSpnPercentageMatch() != null) {
			for (ProviderResultVO vo : filteredResultsAL) {
				if (vo.getPercentageMatch() != null
						&& vo.getPercentageMatch().doubleValue() >= template
								.getSpnPercentageMatch().doubleValue()) {
					routedResourceIds.add(vo.getResourceId());
				} else {
					continue;
				}
			}
		} else {
			for (ProviderResultVO vo : filteredResultsAL) {
				routedResourceIds.add(vo.getResourceId());
			}
		}
		return routedResourceIds;
	}

	public Integer routeIndividualOrder(
			ServiceOrderSearchResultsVO orphanResultVO, Integer tierId)
			throws BusinessServiceException {
		String soId = orphanResultVO.getSoId();
		ServiceOrder so = getServiceOrder(orphanResultVO);

		Buyer buyer = so.getBuyer();
		Integer buyerId = buyer.getBuyerId();
		List<Integer> routedResourceIds = getProvidersListForSO(so, tierId);
		if (tierId != null && tierId > 1) {
			List<Integer> existingRoutedResourceIds = serviceOrderBO
					.getRoutedResourceIds(soId);
			// remove the existing routed providers from the current list of
			// routed providers
			if (existingRoutedResourceIds != null
					&& existingRoutedResourceIds.size() > 0) {
				routedResourceIds.removeAll(existingRoutedResourceIds);
			}
		}
		SecurityContext securityContext = ServiceOrderUtil
				.getSecurityContextForBuyer(buyerId);
		ProcessResponse businessProcessResponse = serviceOrderBO
				.processRouteSO(buyerId, soId, routedResourceIds, tierId,
						securityContext);

		handlePostRoutingLogging(soId, routedResourceIds, securityContext,
				businessProcessResponse);

		return routedResourceIds.size();
	}

	private void handlePostRoutingLogging(String soId,
			List<Integer> routedResourceIds, SecurityContext securityContext,
			ProcessResponse businessProcessResponse)
			throws BusinessServiceException {

		if (businessProcessResponse.isError()) {
			String message = "Error thrown while routing SO with soId--> "
					+ soId + " due to "
					+ businessProcessResponse.getMessages().get(0);
			throw new BusinessServiceException(message);
		}
		if (routedResourceIds.size() > 0) {
			logger.info("Service Order " + soId + " is routed successfully to "
					+ routedResourceIds.size() + " providers.");

		} else {
			logger.info("Service Order " + soId
					+ " is not routed due to 'no providers.'");
		}
	}

	public BuyerSOTemplateDTO getTemplateForOrder(ServiceOrder so) {
		String templateName = null;
		List<ServiceOrderCustomRefVO> customRefList = so.getCustomRefs();
		if (customRefList != null && !customRefList.isEmpty()) {
			for (ServiceOrderCustomRefVO customRef : customRefList) {
				if (OrderConstants.CUSTOM_REF_TEMPLATE_NAME.equals(customRef
						.getRefType())) {
					templateName = customRef.getRefValue();
				}
			}
		}
		BuyerSOTemplateDTO template = soTemplateBO.loadBuyerSOTemplate(so
				.getBuyer().getBuyerId(), templateName);
		return template;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.business.iBusiness.orderGroup.IRouteOrderGroupBO
	 * #unRouteOrdersInPostedGroup(java.lang.String, java.lang.Integer,
	 * com.newco.marketplace.auth.SecurityContext)
	 */
	public boolean unRouteOrdersInPostedGroup(String groupId, Integer buyerId,
			SecurityContext securityContext) throws BusinessServiceException {

		boolean postedGroupUnrouted = false;
		List<ServiceOrderSearchResultsVO> childOrders = null;
		try {
			childOrders = orderGroupDAO.getServiceOrdersForGroup(groupId);
		} catch (DataServiceException dataServiceEx) {
			throw new BusinessServiceException(dataServiceEx);
		}

		if (childOrders.get(0).getSoStatus() == OrderConstants.ROUTED_STATUS) {
			List<List<Integer>> providersList = null; // No providers, in order
			// to remove all
			// providers from all
			// children
			routeOrdersInGrp(childOrders, providersList, null, securityContext);
			postedGroupUnrouted = true;
		}
		return postedGroupUnrouted;
	}

	/**
	 * route each order in group to providers who are in orders provider's list
	 * 
	 * @param grpOrdersList
	 * @param providersList
	 * @param tierId
	 * @param securityContext
	 * @return the number of providers the order routed to
	 * @throws BusinessServiceException
	 */
	private Integer routeOrdersInGrp(
			List<ServiceOrderSearchResultsVO> grpOrdersList,
			List<List<Integer>> providersList, Integer tierId,
			SecurityContext securityContext) throws BusinessServiceException {
		String groupId = "";
		Integer numOfProviders = new Integer(0);
		List<Integer> orderGrpProvidersId = new ArrayList<Integer>();
		if (grpOrdersList != null && !grpOrdersList.isEmpty()) {
			groupId = (grpOrdersList.get(0)).getParentGroupId();
		}
		// remove already existing providers list if it's non TR or first tier
		if (tierId == null || tierId == 1) {
			removeRoutedProviders(groupId);
		}
		if (providersList != null) {
			orderGrpProvidersId = getProviderForOrderGrp(providersList);
			// If it's TR and not first tier remove the existing providers from
			// the current list
			if (tierId != null && tierId > 1) {
				List<Integer> existingRoutedResourceIds = getRoutedResourceIds(groupId);
				// remove the existing routed providers from the current list of
				// routed providers
				if (existingRoutedResourceIds != null
						&& existingRoutedResourceIds.size() > 0) {
					orderGrpProvidersId.removeAll(existingRoutedResourceIds);
				}
			}
		}
		// route to providers
		numOfProviders = routeChildOrdersInGroup(grpOrdersList,
				orderGrpProvidersId, groupId, tierId, securityContext);
		return numOfProviders;
	}

	/**
	 * route each child Order, then update routed group Info
	 * 
	 * @param grpOrdersList
	 * @param orderGrpProvidersId
	 * @param groupId
	 * @param tierId
	 * @param securityContext
	 * @return the number of providers the order routed to
	 * @throws BusinessServiceException
	 */
	private Integer routeChildOrdersInGroup(
			List<ServiceOrderSearchResultsVO> grpOrdersList,
			List<Integer> orderGrpProvidersId, String groupId, Integer tierId,
			SecurityContext securityContext) throws BusinessServiceException {

		ProcessResponse businessProcessResponse = new ProcessResponse();
		for (ServiceOrderSearchResultsVO soResultVO : grpOrdersList) {
			String soId = soResultVO.getSoId();
			try {
				if (tierId == null || tierId == 1) {
					serviceOrderBO.deleteRoutedProviders(soId);
				}
			} catch (com.newco.marketplace.exception.core.BusinessServiceException coreBSEx) {
				String strMessage = "Failed to delete earlier routed providers list while routing SO:"
						+ soId
						+ " in Group:"
						+ soResultVO.getParentGroupId()
						+ " due to error:" + coreBSEx.getMessage();
				throw new BusinessServiceException(strMessage);
			}

			businessProcessResponse = serviceOrderBO.processRouteSO(soResultVO
					.getBuyerID(), soId, orderGrpProvidersId, tierId,
					securityContext);
			if (businessProcessResponse.isError()) {
				String strMessage = "Failed to route SO:" + soId + " in Group:"
						+ groupId + " due to error:"
						+ businessProcessResponse.getMessages().get(0);
				throw new BusinessServiceException(strMessage);
			}
		}

		if (businessProcessResponse.isSuccess() && orderGrpProvidersId != null
				&& !orderGrpProvidersId.isEmpty()) {
			// insert providers list
			populateSOGroupRoutedProviders(groupId, orderGrpProvidersId);
			// update so_group status
			try {
				orderGroupDAO.updateSOGroupStatus(groupId, String
						.valueOf(OrderConstants.ROUTED_STATUS));
			} catch (DataServiceException dsEx) {
				String strMessage = "Failed to update status for Group:"
						+ groupId + " due to error:" + dsEx.getMessage();
				throw new BusinessServiceException(strMessage);
			}
			for (ServiceOrderSearchResultsVO soResultVO : grpOrdersList) {
				logger.info("Service Orders " + soResultVO.getSoId()
						+ " in group " + groupId
						+ " has been routed successfully.");
			}
		} else {
			logger
					.info("SO_GROUP_ROUTED was not populated; SO_GROUP status was not updated. Group Id --> "
							+ groupId);
		}
		return orderGrpProvidersId.size();
	}

	public ProcessResponse routeGroupToSelectedProviders(
			List<ServiceOrderSearchResultsVO> grpOrdersList,
			List<Integer> orderGrpProvidersId, String groupId, Integer buyerId,
			SecurityContext securityContext) throws BusinessServiceException {
		ProcessResponse processResp = new ProcessResponse();
		try {
			// remove already existing providers list
			logger.info("Deleting routed providers for the group:" + groupId);
			removeRoutedProviders(groupId);

			// route to providers
			logger.info("Route the group:" + groupId
					+ " to selected providers; buyerId = " + buyerId);
			routeChildOrdersInGroup(grpOrdersList, orderGrpProvidersId,
					groupId, null, securityContext);

			processResp.setCode(ServiceConstants.VALID_RC);
			processResp.setSubCode(ServiceConstants.VALID_RC);
			processResp.setObj(OrderConstants.GROUP_ORDER_SUCCESSFULLY_ROUTED);
		} catch (Exception e) {
			logger.error("route service order:: error:  " + e.getMessage(), e);
			processResp.setCode(ServiceConstants.SYSTEM_ERROR_RC);
			processResp.setMessage(e.getMessage());
			processResp.setObj(groupId);
			String message = "Erro occured while routing grouped order to selected Providers "
					+ e.getMessage();
			throw new BusinessServiceException(message);
		}

		return processResp;
	}

	private void removeRoutedProviders(String groupId)
			throws BusinessServiceException {
		try {
			orderGroupDAO.deleteGroupRoutedProviders(groupId);
		} catch (DataServiceException e) {
			logger
					.error(
							"Error while removing routed providers before re routing to group",
							e);
			throw new BusinessServiceException(e.getMessage());
		}
	}

	private void populateSOGroupRoutedProviders(String groupId,
			List<Integer> orderGrpProvidersId) throws BusinessServiceException {
		try {
			orderGroupDAO.insertGroupRoutedProviders(groupId,
					orderGrpProvidersId);
		} catch (Exception e) {
			logger
					.error(
							"Error while inserting routed providers before rerouting group",
							e);
			throw new BusinessServiceException(e.getMessage());
		}
	}

	public List<Integer> getRoutedResourceIds(String groupId)
			throws BusinessServiceException {
		List<Integer> routedResourceIds = null;
		try {
			routedResourceIds = orderGroupDAO.getRoutedResourceIds(groupId);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e.getMessage());
		}
		return routedResourceIds;
	}

	private ProviderSearchCriteriaVO getProviderSearchCriteria(ServiceOrder so,
			BuyerSOTemplateDTO template, Integer tierId) {
		ProviderSearchCriteriaVO provSearchVO = new ProviderSearchCriteriaVO();
		provSearchVO.setBuyerID(Integer.valueOf(so.getBuyer().getBuyerId()));
		provSearchVO.setServiceLocation(so.getServiceLocation());
		provSearchVO.setServiceOrderID(so.getSoId());
		if (so.getPrimarySkillCatId() != null) {
			ArrayList<Integer> skillNodeIds = new ArrayList<Integer>();
			List<Integer> skillTypes = new ArrayList<Integer>();
			List<ServiceOrderTask> soTasks = so.getTasks();
			if (null != soTasks && !soTasks.isEmpty()) {
				// If we have tasks then assign the SubCategory/Category to the
				// list
				// else select the main category
				for (ServiceOrderTask taskDto : soTasks) {
					if (taskDto.getSubCategoryName() != null) {
						skillNodeIds.add(taskDto.getSkillNodeId());
					} else if (taskDto.getCategoryName() != null) {
						skillNodeIds.add(taskDto.getSkillNodeId());
					} else {
						skillNodeIds.add(so.getPrimarySkillCatId());
					}

					if (taskDto.getServiceTypeId() != null) {
						skillTypes.add(taskDto.getServiceTypeId());
					}
				}
				provSearchVO.setSkillNodeIds(skillNodeIds);
				provSearchVO.setSkillServiceTypeId(skillTypes);
			} else {
				skillNodeIds.add(so.getPrimarySkillCatId());
				provSearchVO.setSkillNodeIds(skillNodeIds);
			}
		}

		if (template != null && template.getSpnId() != null) {
			provSearchVO.setSpnID(template.getSpnId());
			provSearchVO.setIsNewSpn(template.getIsNewSpn());
		}
		if (tierId != null && tierId.equals(OrderConstants.OVERFLOW)) {
			provSearchVO.setSpnID(null);
		} else
			provSearchVO.setTierId(tierId);

		return provSearchVO;
	}

	private ArrayList<RatingParameterBean> getRatingParamBean(ServiceOrder so) {
		// setup filters
		ArrayList<RatingParameterBean> ratingParamBeans = new ArrayList<RatingParameterBean>();
		ZipParameterBean zipBean = new ZipParameterBean();
		// TODO distance move to application properties table
		zipBean.setRadius(OrderConstants.SO_ROUTE_CRITERIA_DIST);
		zipBean.setZipcode(so.getServiceLocation().getZip());
		zipBean.setCredentialId(so.getPrimarySkillCatId());
		ratingParamBeans.add(zipBean);

		/*
		 * BackgroundCheckParameterBean backgroundBean = new
		 * BackgroundCheckParameterBean();
		 * backgroundBean.setBackgroundCheck(OrderConstants
		 * .SO_ROUTE_CRITERIA_BC); ratingParamBeans.add(backgroundBean);
		 */

		return ratingParamBeans;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.business.iBusiness.orderGroup.IRouteOrderGroupBO
	 * #getProviderForOrderGrp(java.util.List)
	 */
	public List<Integer> getProviderForOrderGrp(
			List<List<Integer>> providersList) {
		List<Integer> orderGrpProvidersId = providersList.get(0);
		for (List<Integer> providers : providersList) {
			orderGrpProvidersId.retainAll(providers);
		}
		return orderGrpProvidersId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.business.iBusiness.orderGroup.IRouteOrderGroupBO
	 * #getRoutedDateForResourceId(java.lang.String, java.lang.Integer)
	 */
	public Date getRoutedDateForResourceId(String groupId, Integer resourceId)
			throws BusinessServiceException {
		Date routedDt = null;
		try {
			routedDt = orderGroupDAO.getRoutedDateForResourceId(groupId,
					resourceId);
		} catch (DataServiceException e) {
			logger
					.error("Error occurred while in getRoutedDateForResourceId due to "
							+ e.getMessage());
			throw new BusinessServiceException(e);
		}
		return routedDt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.business.iBusiness.orderGroup.IRouteOrderGroupBO
	 * #getTheRemainingTimeToAcceptSO(java.lang.String, java.lang.Integer)
	 */
	public int getTheRemainingTimeToAcceptGrpOrder(String groupId,
			Integer resourceId) throws BusinessServiceException {
		try {
			Date routedDate = getRoutedDateForResourceId(groupId, resourceId);
			return serviceOrderBO.getTheRemainingTimeToAcceptSO(routedDate);
		} catch (Exception e) {
			throw new BusinessServiceException(
					"Error occurred while in getTheRemainingTimeToAcceptSO due to",
					e);
		}
	}

	/**
	 * go thru all orders and see if all spns are same if they are not same, get
	 * spn value from template and set to all serviceOrders only if "isNewSpn"
	 * is set to true
	 */
	public boolean validateAndUpdateSpnForGroupedOrders(String groupId) {
		boolean isvalidForNewSpn = true;
		try {
			List<ServiceOrderSearchResultsVO> groupedOrdersInfo = orderGroupDAO
					.getSOSearchResultsVOsForGroup(groupId);
			ServiceOrderSearchResultsVO eachOrderInfo = groupedOrdersInfo
					.get(0);
			Integer eachOrderSpnId = eachOrderInfo.getSpnId();
			String eachOrderSoId = eachOrderInfo.getSoId();

			for (ServiceOrderSearchResultsVO childOrderInfo : groupedOrdersInfo) {
				Integer childOrderSpnId = childOrderInfo.getSpnId();
				if ((childOrderSpnId == null) || (eachOrderSpnId == null)
						|| !(eachOrderSpnId.equals(childOrderSpnId))) {
					ServiceOrder so = serviceOrderBO
							.getServiceOrder(eachOrderSoId);
					BuyerSOTemplateDTO template = getTemplateForOrder(so);
					if (template.getIsNewSpn().booleanValue()) {
						/*
						 * update spnId with given value for all orders in a
						 * group
						 */
						orderGroupDAO.updateServiceOrderSpnId(groupId, template
								.getSpnId());
						return true;
					}
					return false;
				}

			}
		} catch (Exception e) {
			logger
					.info("error occurred in validateAndUpdateSpnForGroupedOrders due to "
							+ e.getMessage());
		}
		return isvalidForNewSpn;
	}
	
	/**
	 * get the remaining time left for firm to wait to Accept So
	 * @param groupId
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public int getTheRemainingTimeToAcceptGrpOrderFirm(String groupId,
			Integer vendorId) throws BusinessServiceException{
		try {
			Date routedDate = getRoutedDateForFirm(groupId, vendorId);
			return serviceOrderBO.getTheRemainingTimeToAcceptSO(routedDate);
		} catch (Exception e) {
			throw new BusinessServiceException(
					"Error occurred while in getTheRemainingTimeToAcceptSO due to",
					e);
		}
	}
	
	public Date getRoutedDateForFirm(String groupId, Integer vendorId) throws BusinessServiceException {
		Date routedDt = null;
		try {
			routedDt = orderGroupDAO.getRoutedDateForFirm(groupId, vendorId);
		} catch (DataServiceException e) {
			logger.error("Error occurred while in getRoutedDateForResourceId due to "
							+ e.getMessage());
			throw new BusinessServiceException(e);
		}
		return routedDt;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public IProviderSearchBO getProvSearchBO() {
		return provSearchBO;
	}

	public void setProvSearchBO(IProviderSearchBO provSearchBO) {
		this.provSearchBO = provSearchBO;
	}

	public IMasterCalculatorBO getMasterCalcBO() {
		return masterCalcBO;
	}

	public void setMasterCalcBO(IMasterCalculatorBO masterCalcBO) {
		this.masterCalcBO = masterCalcBO;
	}

	public IBuyerSOTemplateBO getSoTemplateBO() {
		return soTemplateBO;
	}

	public void setSoTemplateBO(IBuyerSOTemplateBO soTemplateBO) {
		this.soTemplateBO = soTemplateBO;
	}

	public IOrderGroupDao getOrderGroupDAO() {
		return orderGroupDAO;
	}

	public void setOrderGroupDAO(IOrderGroupDao orderGroupDAO) {
		this.orderGroupDAO = orderGroupDAO;
	}

	public ServiceOrderDao getServiceOrderDAO() {
		return serviceOrderDAO;
	}

	public void setServiceOrderDAO(ServiceOrderDao serviceOrderDAO) {
		this.serviceOrderDAO = serviceOrderDAO;
	}

	public IOrderGroupBO getOrderGroupBO() {
		return orderGroupBO;
	}

	public void setOrderGroupBO(IOrderGroupBO orderGroupBO) {
		this.orderGroupBO = orderGroupBO;
	}

}
