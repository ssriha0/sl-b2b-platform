package com.newco.marketplace.web.action.ordergroup;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.ordergroup.OrderGroupVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.ServiceOrderMonitorResultVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.IOrderGroupDelegate;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.dto.ordergroup.OrderGroupDTO;
import com.newco.marketplace.web.dto.ordergroup.OrderGroupManagerDTO;
import com.newco.marketplace.web.utils.CriteriaHandlerFacility;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class OrderGroupManagerAction extends SLBaseAction implements Preparable, ModelDriven<OrderGroupManagerDTO>
{
	private static final long serialVersionUID = -1193809896993830913L;

	Logger logger = Logger.getLogger(OrderGroupManagerAction.class);
	
	private String REDIRECT_TO_SOW = "SOW";
	private String REDIRECT_TO_SOD = "SOD";
	
	OrderGroupManagerDTO orderGroupManagerDTO = new OrderGroupManagerDTO();
	private IOrderGroupDelegate orderGroupDelegate;
	private HashMap<String, String> orderGroupRadio = new HashMap<String, String>();
	private String ogmSoId;
	private String action = "edit";
	private String tab = "draft";

	public OrderGroupManagerAction() {
		// Do nothing for now
	}
	
	public void prepare() throws DelegateException {
		createCommonServiceOrderCriteria();
		initDropdownMenus();
	}

	private void initDropdownMenus() throws DelegateException{
		
		List<LabelValueBean> statusDropdowns = new ArrayList<LabelValueBean>();
		statusDropdowns.add(new LabelValueBean("Draft", String.valueOf(OrderConstants.DRAFT_STATUS)));
		statusDropdowns.add(new LabelValueBean("Posted", String.valueOf(OrderConstants.ROUTED_STATUS)));
		statusDropdowns.add(new LabelValueBean("Expired", String.valueOf(OrderConstants.EXPIRED_STATUS)));
		orderGroupManagerDTO.setStatusDropdowns(statusDropdowns);
	
		List<LabelValueBean> searchTypeDropdowns = new ArrayList<LabelValueBean>();
		searchTypeDropdowns.add(new LabelValueBean("Zip", OrderConstants.SEARCH_BY_ZIP_CD));
		searchTypeDropdowns.add(new LabelValueBean("Phone", OrderConstants.SEARCH_BY_PHONE_NO));
		searchTypeDropdowns.add(new LabelValueBean("Customer Name", OrderConstants.SEARCH_BY_USR_NAME));
		searchTypeDropdowns.add(new LabelValueBean("Address", OrderConstants.SEARCH_BY_ADDR));
		searchTypeDropdowns.add(new LabelValueBean("SL Order #", OrderConstants.SEARCH_BY_ORDER_ID));		
		this.initBuyerCustomRefs(searchTypeDropdowns);
		orderGroupManagerDTO.setSearchTypeDropdowns(searchTypeDropdowns);
	}
	
	/**
	 * Method to populate custom refs - unit no & sales check in search dropdown
	 * @param searchTypeDropdowns
	 * @throws DelegateException 
	 * @throws DelegateException 
	 */
	private void initBuyerCustomRefs(List<LabelValueBean> searchTypeDropdowns) throws DelegateException {
		List<BuyerReferenceVO> buyerRefs = null;
		//Retrieves unitNo and salesCheckNo custom refs for the buyer
		buyerRefs = orderGroupDelegate.getCustomReferenceForOGMSearch(this.get_commonCriteria().getCompanyId());

		//Adding to searchTypeDropdown
		if(buyerRefs != null && !buyerRefs.isEmpty()){
			for(BuyerReferenceVO buyerCustomRef:buyerRefs){
				String buyerRefType = buyerCustomRef.getReferenceType();					
				String  buyerRefId = OrderConstants.CUSTOM_REF_SEARCH_IDENTIFIER+ buyerCustomRef.getBuyerRefTypeId();
				searchTypeDropdowns.add(new LabelValueBean(SLStringUtils.capitalizeFirstLetter(buyerRefType),buyerRefId));				
			}
		}
	}
	
	public String displayPage() {
		
		// Not logged in? Redirect to homepage
		ServiceOrdersCriteria criteria = get_commonCriteria();
		if (criteria == null) {
			return "homepage";
		}

		return SUCCESS;
	}

	public String jumpToSODorSOW() {
		
		String statusStr = (String)getParameter("status");
		int statusInt = Integer.parseInt(statusStr);

		// Need to set this if sending the user to SOW for draft orders
		String soID = getParameter("soId");
		if(StringUtils.isNotBlank(soID))
		{
			ogmSoId = soID;
		}
		else
		{
			return SUCCESS;			
		}
		
		if(OrderConstants.DRAFT_STATUS == statusInt)
		{
			/*SL-19820
			getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR, true);*/
			getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR+"_"+ogmSoId, true);	
			return "SOW";			
		}
		else// if(OrderConstants.ROUTED_STATUS == statusInt)
		{
			return "SOD";
		}
		
	}
	
	public String searchButton() {
		
		// Perform basic front-end validations
		OrderGroupManagerDTO ogmDTO = getModel();
		ogmDTO.validate();
		List<IError> errors = ogmDTO.getErrors();
		if (errors != null && !errors.isEmpty()) {
			// Add all error messages to struts field error messages collection; and redirect to Order Group Manager JSP page to show these validation errors
			for (IError errorObj : errors) {
				addFieldError(errorObj.getFieldId(), errorObj.getMsg());
			}
			return SUCCESS;
		}
		
		// Get search input parameters
		Integer soStatus = ogmDTO.getStatus();
		String searchType = ogmDTO.getSearchType(); 
		String searchValue = ogmDTO.getSearchTerm();
		
		// Setup Criteria Handler, Criteria Map
		setDefaultCriteria();
		CriteriaHandlerFacility criteriaHandler = loadOGMCriteria(soStatus, searchType, searchValue);
		
		// Get individual service orders
		List<String> soIds = soMonitorDelegate.getSoSearchIdsList(criteriaHandler.getCriteria());
		if (soIds != null && !soIds.isEmpty()) {
			criteriaHandler.getSearchingCriteria().setSoIds(soIds);
			ServiceOrderMonitorResultVO serviceOrderMonitorResultVO = soMonitorDelegate.getSoSearchList(criteriaHandler.getCriteria());
			List<ServiceOrderDTO> serviceOrderResults = serviceOrderMonitorResultVO.getServiceOrderResults();		
			seperateIndividualAndGroupedOrders(serviceOrderResults);
		} else {
			logger.info("No service orders found matching given search criteria!");
			addFieldError("Search Criteria", "No service orders found matching given search criteria!");
		}

		return SUCCESS;
	}

	private void seperateIndividualAndGroupedOrders(List<ServiceOrderDTO> serviceOrderResults) {
		
		List<ServiceOrderDTO> ungroupedOrders = new ArrayList<ServiceOrderDTO>();
		Map<String, OrderGroupDTO> orderGroupMap = new HashMap<String, OrderGroupDTO>();
		
		// Iterate all orders and separate out grouped and individual orders
		for(ServiceOrderDTO dto :  serviceOrderResults) {
			// Grouped order:
			if(dto.getParentGroupId() != null) {
				// No entry yet, create
				if(!orderGroupMap.containsKey(dto.getParentGroupId())) {
					OrderGroupDTO orderGroup = new OrderGroupDTO();
					orderGroup.setId(dto.getParentGroupId());
					orderGroup.setTitle(dto.getParentGroupTitle());
					orderGroup.setCreatedDateString(dto.getGroupCreatedDateString());
					orderGroup.setZip(dto.getZip5());
					orderGroup.setCity(dto.getCity());
					orderGroup.setEndCustomer(dto.getEndCustomerName());
					orderGroup.setDate(dto.getServiceOrderDateString());
					orderGroup.getOrders().add(dto);
					orderGroupMap.put(dto.getParentGroupId(), orderGroup);
				} else {
					((OrderGroupDTO)orderGroupMap.get(dto.getParentGroupId())).getOrders().add(dto);
				}
			} else {
				// Add ungrouped order to ungroupedOrderList.
				ungroupedOrders.add(dto);
			}
		}
		orderGroupManagerDTO.setUngroupedOrders(ungroupedOrders);
		orderGroupManagerDTO.setOrderGroups(new ArrayList<OrderGroupDTO>(orderGroupMap.values()));
		setModel(orderGroupManagerDTO);
		//getRequest().setAttribute("orderGroupManagerDTO", orderGroupManagerDTO);
	}

	// Ungroup Selected Order Group
	public String ungroupSelectedButton() throws Exception {
		
		List<String> orderGroupIDs = getCheckedOrderGroups();
		if(orderGroupIDs == null)
			return SUCCESS;

        SecurityContext securityContext = get_commonCriteria().getSecurityContext();

		for(String groupId : orderGroupIDs) {
			orderGroupDelegate.ungroupOrderGroup(groupId, securityContext);
		}

		if(orderGroupIDs.size() > 0)
		{
			String msg = "The following order group(s) have been ungrouped: ";
			for(String groupId : orderGroupIDs)
			{
				msg += " " + groupId;
			}			
			addActionMessage(msg);
		}
		
		return SUCCESS;
	}

	// Used for bottom section labeled 'Previously Grouped Orders'
	private List<String> getCheckedOrderGroups() {
		
		Enumeration<String> names = getRequest().getParameterNames();

		List<String> orderGroupIDs = new ArrayList<String>();
		String name;
		while (names.hasMoreElements()) {
			name = (String) names.nextElement();
			int index = name.indexOf("checkbox_group_");
			if (index == 0) {
				orderGroupIDs.add(name.substring(15));
			}
		}

		return orderGroupIDs;
	}

	private List<String> getCheckedUngroupedOrders() {
		
		Enumeration<String> names = getRequest().getParameterNames();

		List<String> checkedServiceOrders = new ArrayList<String>();
		String name;
		while (names.hasMoreElements()) {
			name = (String) names.nextElement();
			int index = name.indexOf("ugo_");
			if (index == 0) {
				checkedServiceOrders.add(name.substring(4));
			}
		}

		return checkedServiceOrders;
	}

	// Create a new Order Group
	public String createNewGroupButton() throws Exception {
		
		List<String> checkedServiceOrders = getCheckedUngroupedOrders();

		// Minimum 2 service orders needed to make a new group
		if (checkedServiceOrders.size() >= 2) {
			// Put service order in group
			Integer buyerId = get_commonCriteria().getCompanyId();
			SecurityContext securityContext = get_commonCriteria().getSecurityContext();
			OrderGroupVO groupVO = orderGroupDelegate.addServiceOrdersToNewGroup(buyerId, checkedServiceOrders, securityContext);
			
			if (groupVO.getWfStateId() != null && groupVO.getWfStateId() == OrderConstants.ROUTED_STATUS) {
				// Get first SO and set that as a parameter to be read in orderGroup.xml
				ogmSoId = checkedServiceOrders.get(0);
	
				// Need to set this session variable, otherwise SOW will complain.
			/*	SL-19820
				getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR, true);*/
				getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR+"_"+ogmSoId, true);	
				return REDIRECT_TO_SOW;			
			} else {
				addActionMessage("Order group \""+groupVO.getGroupId()+"\" successfully created.");
				return SUCCESS;
			}
		}
		else
		{
			addFieldError("ungroupedOrders", "Please select at least 2 service orders to create a group.");			
			searchButton();
			
			return SUCCESS;
		}

	}

	// Take the selected/checked ungrouped service orders and add them to the selected Order Group.
	public String groupSelectedButton() throws Exception {
		
		// Get Service Orders checked.
		List<String> checkedServiceOrders = getCheckedUngroupedOrders();
		
		// Get Order Group ID
		String orderGroupId = getRequest().getParameter("radioOrderGroup");

		boolean validInputs = true;
		if (checkedServiceOrders.size() < 1) {
			addFieldError("ungroupedOrders", "Please select at least one service order to group.");
			validInputs = false;
		}
		
		if (org.apache.commons.lang.StringUtils.isBlank(orderGroupId)) {
			addFieldError("ungroupedOrders", "Please select the group.");
			validInputs = false;
		}
		
		boolean postedGroupUnrouted = false;
		if (validInputs) {
			Integer buyerId = get_commonCriteria().getCompanyId();
			SecurityContext securityContext = get_commonCriteria().getSecurityContext();
			try {
				postedGroupUnrouted = orderGroupDelegate.addServiceOrdersToExistingGroup(orderGroupId, checkedServiceOrders, buyerId, securityContext);
			} catch (DelegateException delegateException) {
				addActionError(delegateException.getMessage());
				return SUCCESS;
			}
		}

		if (postedGroupUnrouted) {
			
			// Get first SO and set that as a parameter to be read in orderGroup.xml
			List<ServiceOrderSearchResultsVO> soList = orderGroupDelegate.getChildServiceOrders(orderGroupId);
			if(soList != null && soList.size() > 0)
			{
				String soID = soList.get(0).getSoId(); 				
				getSession().setAttribute("soId", soID);
				getRequest().setAttribute("soId", soID);				
				ogmSoId = soID;
				tab = OrderConstants.POSTED;
	
				// Need to set this session variable, otherwise SOW will complain.
				//sl-19820
			/*	getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR, true);	*/	
				getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR+"_"+ogmSoId, true);				
				return REDIRECT_TO_SOW; // Redirect to first child SOW
			}
			else
			{
				return SUCCESS;
			}
		} else {
			searchButton();
			return SUCCESS;
		}
	}

	public void setModel(OrderGroupManagerDTO model) {
		orderGroupManagerDTO = model;
	}

	public OrderGroupManagerDTO getModel() {
		return orderGroupManagerDTO;
	}

	public OrderGroupManagerDTO getOrderGroupManagerDTO() {
		return orderGroupManagerDTO;
	}

	public void setOrderGroupManagerDTO(OrderGroupManagerDTO orderGroupManagerDTO) {
		this.orderGroupManagerDTO = orderGroupManagerDTO;
	}

	public IOrderGroupDelegate getOrderGroupDelegate() {
		return orderGroupDelegate;
	}

	public void setOrderGroupDelegate(IOrderGroupDelegate orderGroupDelegate) {
		this.orderGroupDelegate = orderGroupDelegate;
	}

	public HashMap<String, String> getOrderGroupRadio() {
		return orderGroupRadio;
	}

	public void setOrderGroupRadio(HashMap<String, String> orderGroupRadio) {
		this.orderGroupRadio = orderGroupRadio;
	}



	public String getOgmSoId() {
		return ogmSoId;
	}

	public void setOgmSoId(String ogmSoId) {
		this.ogmSoId = ogmSoId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTab()
	{
		return tab;
	}

	public void setTab(String tab)
	{
		this.tab = tab;
	}
}
