package com.newco.marketplace.web.validator.sow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.web.delegates.IFinanceManagerDelegate;
import com.newco.marketplace.web.delegates.IOrderGroupDelegate;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.delegates.ISOWizardPersistDelegate;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.IWarning;
import com.newco.marketplace.web.dto.SOTaskDTO;
import com.newco.marketplace.web.dto.SOWAdditionalInfoTabDTO;
import com.newco.marketplace.web.dto.SOWBaseTabDTO;
import com.newco.marketplace.web.dto.SOWPartDTO;
import com.newco.marketplace.web.dto.SOWPartsTabDTO;
import com.newco.marketplace.web.dto.SOWPricingTabDTO;
import com.newco.marketplace.web.dto.SOWProviderDTO;
import com.newco.marketplace.web.dto.SOWProviderSearchDTO;
import com.newco.marketplace.web.dto.SOWProvidersTabDTO;
import com.newco.marketplace.web.dto.SOWScopeOfWorkTabDTO;
import com.newco.marketplace.web.dto.SOWTabStateVO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrderWizardBean;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.dto.TabNavigationDTO;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.routing.tiered.schedulermanager.TierRoutingSchedulerManager;

public class SOWSessionFacility
{

	private static final long serialVersionUID = 0L;
	private static final Logger logger = Logger.getLogger("SOWSessionFacility");
	private static SOWSessionFacility _instance = new SOWSessionFacility();

	
	public static SOWSessionFacility getInstance(){
		return _instance;
	}

	/**
	 * This method creates a skeleton record for a Service Order when user
	 * intends to create a new Service Order using 'Create Service Order'
	 *
	 * @param isoWizardPersistDelegate
	 *            ISOWizardPersistDelegate
	 * @return serviceOrder ServiceOrder
	 * @throws BusinessServiceException
	 */
	public ServiceOrder createBareBonesServiceOrder(ISOWizardPersistDelegate isoWizardPersistDelegate) throws BusinessServiceException
	{
		IPersistor persistor = PersistorFactory.getInstance().getPersistor(OrderConstants.DB_PERSIST);
		ServiceOrder serviceOrder = null;
		Integer buyerId = getBuyerId();
		Integer buyerResourceId = getBuyerResourceId();
		Integer buyerResContactId = getBuyerResourceContactId();
		String userName = this.getUserName();

		if (buyerId != null) {
			serviceOrder = persistor.createBareBonesServiceOrder(isoWizardPersistDelegate, buyerId, buyerResourceId, buyerResContactId,userName);
		}
		return serviceOrder;

	}

	/**
	 * This method evaluates SOW (Service Order Wizard) bean. This typically
	 * gets called when user clicks [Next] or [Previous]
	 *
	 * @param sowTabDTO
	 *            SOWBaseTabDTO
	 * @param tabNavigationDTO
	 *            TabNavigationDTO
	 * @return serviceOrderWizardBean ServiceOrderWizardBean
	 * @throws BusinessServiceException
	 */
	public ServiceOrderWizardBean evaluateSOWBean(SOWBaseTabDTO sowTabDTO, TabNavigationDTO tabNavigationDTO)
			throws BusinessServiceException
	{
		//SL-19820
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		ServiceOrderWizardBean serviceOrderWizardBean = null;
		if (tabNavigationDTO != null  && sowTabDTO != null)
		{
			//SL-19820
			//serviceOrderWizardBean = (ServiceOrderWizardBean)getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY);
			serviceOrderWizardBean = (ServiceOrderWizardBean)getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY+"_"+soId);
			validateAndIntrospectSOWBean(serviceOrderWizardBean, sowTabDTO, tabNavigationDTO);
		}
		return serviceOrderWizardBean;
	}

	/**
	 * This method evaluates (Service Order Wizard) SOW bean by loading the SOW
	 * bean, evaluating for errors and warnings and setting the Tab states. This
	 * method is called in create and edit mode( initial load) as well as in
	 * Cancel functionality.
	 *
	 * @param sowTabDTO
	 *            SOWBaseTabDTO
	 * @param tabNavigationDTO
	 *            TabNavigationDTO
	 * @param fetchDelegate
	 *            ISOWizardFetchDelegate
	 * @param securityContext
     * @throws BusinessServiceException
     * SL-19820 : added soID as method parameter
	 */
	public void evaluateSOWBean(SOWBaseTabDTO sowTabDTO, TabNavigationDTO tabNavigationDTO, ISOWizardFetchDelegate fetchDelegate, SecurityContext securityContext, String soId)
			throws BusinessServiceException
	{

		//Map<String, Object> sessionMap = ActionContext.getContext().getSession();
		//SL-19820
		setAttribute(OrderConstants.SO_ID, soId);
		//ServiceOrderWizardBean serviceOrderWizardBean = (ServiceOrderWizardBean) getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY);
		ServiceOrderWizardBean serviceOrderWizardBean = (ServiceOrderWizardBean) getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY+"_"+soId);
		String soID = null;
		HashMap<String, Object> soDTOs = null;
		String mode = tabNavigationDTO.getSowMode();
		String actionPerformed = tabNavigationDTO.getActionPerformed();
		Boolean skuIndicatorFromSoWorkFlowControl=false;
		if (actionPerformed != null && actionPerformed.equals(OrderConstants.SOW_CANCEL_ACTION))
		{
			logger.debug("Invoking setFlagOnServiceOrder from SOWSessionFacility.evaluateSOWBean on Cancel action" );
			//SL-19820
			//setFlagOnServiceOrder((String) getSession().getAttribute(OrderConstants.SO_ID), OrderConstants.SO_VIEW_MODE_FLAG, fetchDelegate, securityContext);
			//SL-21070
			Boolean lockEditInd = (Boolean) getSession().getAttribute(OrderConstants.LOCK_EDIT_SESSION_VAR+soId);
			if(null != lockEditInd && !lockEditInd){
				setFlagOnServiceOrder(soId, OrderConstants.SO_VIEW_MODE_FLAG, fetchDelegate, securityContext);
			}
			getSession().setAttribute(OrderConstants.LOCK_EDIT_SESSION_VAR+soId, null);
			return;
		}
		if (mode != null)
		{
			if (sowTabDTO == null && (actionPerformed == null))
			{
				logger.debug("Beginning edit mode setup");

				//Sl-19820
				//String tmpID = (String)getSession().getAttribute(OrderConstants.SO_ID);
				String tmpID = soId;
				if (tmpID != null)
				{
					soID = tmpID;
				}
				else
				{
					logger.debug("Service Order is not available in session.");
					throw new BusinessServiceException("Service Order ID unavailable");

				}

				// load the service order DTOs
				try
				{
					soDTOs = loadServiceOrderDTO(soID, fetchDelegate);
					//SL-21070
					boolean lockEditInd = ((SOWScopeOfWorkTabDTO) soDTOs.get(OrderConstants.SOW_SOW_TAB)).getLockEditInd();
					Boolean lockEditIndFromSession = (Boolean) getSession().getAttribute(OrderConstants.LOCK_EDIT_SESSION_VAR+soId);
					if(null ==lockEditIndFromSession || lockEditIndFromSession){
						getSession().setAttribute(OrderConstants.LOCK_EDIT_SESSION_VAR+soId,lockEditInd);
					}else{
						getSession().setAttribute(OrderConstants.LOCK_EDIT_SESSION_VAR+soId,false);
					}
					//Fetching the status of  sku indicator value
//					skuIndicatorFromSoWorkFlowControl=checkSkuIndicatorValue(soID,fetchDelegate);
//					//Checking service order created using sku or not 
//				if(skuIndicatorFromSoWorkFlowControl!=null)
//					{
//					if(skuIndicatorFromSoWorkFlowControl==true)
//					{
////						//If service order created using sku setting value in session
//						getSession().setAttribute("skuAndTaskIndicatorValue",skuIndicatorFromSoWorkFlowControl);
//					
//					}
//					}
					logger.debug("Invoking setFlagOnServiceOrder from SOWSessionFacility.evaluateSOWBean" );
					//SL-21070
					if(!lockEditInd){
						setFlagOnServiceOrder(soID, OrderConstants.SO_EDIT_MODE_FLAG, fetchDelegate, securityContext);
					}
					
				}// end try
				catch (BusinessServiceException bse)
				{
					logger.debug("Exception thrown while getting service order(" + soID + ")");
					throw bse;

				}// end catch

				serviceOrderWizardBean = new ServiceOrderWizardBean();
				loadSOWBean(serviceOrderWizardBean);
				serviceOrderWizardBean.setTabDTOs(soDTOs);
				serviceOrderWizardBean.setSoId(soID);
				serviceOrderWizardBean.setComingFromTab(OrderConstants.SOW_SOW_TAB);
				serviceOrderWizardBean.setStartPoint(tabNavigationDTO.getStartingPoint());
				//SL-19820
				/*if (getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY) != null)
				{
					getSession().removeAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY);
				}
				getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY, serviceOrderWizardBean);*/
				if (getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY+"_"+soID) != null)
				{
					getSession().removeAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY+"_"+soID);
				}
				getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY+"_"+soID, serviceOrderWizardBean);
				logger.debug("Ending edit mode setup");
				if (sowTabDTO != null && tabNavigationDTO != null && serviceOrderWizardBean != null)
				{
					validateAndIntrospectSOWBean(serviceOrderWizardBean, sowTabDTO, tabNavigationDTO);
				}
				else if (sowTabDTO == null && tabNavigationDTO != null && serviceOrderWizardBean != null)
				{
					validateAndIntrospectSOWBean(serviceOrderWizardBean, getTabDTO(OrderConstants.SOW_SOW_TAB), tabNavigationDTO);
				}

			}// end if
		}
	}

	public void evaluateSOWBeanForCopy(SOWBaseTabDTO sowTabDTO, TabNavigationDTO tabNavigationDTO, ISOWizardFetchDelegate fetchDelegate,
                                       ISOWizardPersistDelegate isoWizardPersistDelegate, String serviceOrderId, Integer buyerResourceId, Integer buyerId, SecurityContext securityContext)
			throws BusinessServiceException
	{
		//SL-19820
		//ServiceOrderWizardBean serviceOrderWizardBean = (ServiceOrderWizardBean) getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY);
		ServiceOrderWizardBean serviceOrderWizardBean = (ServiceOrderWizardBean) getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY+"_"+serviceOrderId);
		String soID = null;
		HashMap<String, Object> soDTOs = null;  
		String mode = tabNavigationDTO.getSowMode();
		String actionPerformed = tabNavigationDTO.getActionPerformed();
		if (actionPerformed != null && actionPerformed.equals(OrderConstants.SOW_CANCEL_ACTION))
		{
			logger.debug("Invoking setFlagOnServiceOrder from SOWSessionFacility.evaluateSOWBeanForCopy on Cancel action" );
			//Sl-19820
			//setFlagOnServiceOrder((String) getSession().getAttribute(OrderConstants.SO_ID), OrderConstants.SO_VIEW_MODE_FLAG, fetchDelegate, securityContext);
			setFlagOnServiceOrder((String) getAttribute(OrderConstants.SO_ID), OrderConstants.SO_VIEW_MODE_FLAG, fetchDelegate, securityContext);
			return;
		}
		if (mode != null)
		{
			if (sowTabDTO == null && (actionPerformed == null))
			{
				logger.debug("Beginning edit mode setup");

				//get Service Order ID
				//SL-19820
				//String tmpID = (String)getSession().getAttribute(OrderConstants.SO_ID);
				String tmpID = (String) getAttribute(OrderConstants.SO_ID);
				if (tmpID != null)
				{
					//check on cast
					soID = tmpID;

				}// end if
				else
				{
					logger.debug("Service Order is not available in session.");
					throw new BusinessServiceException("Service Order ID unavailable");

				}// end else

				// load the service order DTOs
				try
				{
					soDTOs = loadServiceOrderDTO(soID, fetchDelegate);
					mapCopyServiceOrder(soDTOs);
					// Get the documents from of the Old so id and copy it to the new so id
					DocumentVO documentVO = new DocumentVO();
					documentVO.setSoId(soID);
					documentVO.setRoleId(OrderConstants.BUYER_ROLEID);
					try
					{
						ArrayList<DocumentVO> documentList = fetchDelegate.getDocumentsBySOID(documentVO, buyerResourceId);
						if (documentList != null && documentList.size() > 0)
						{
							for (int i = 0; i < documentList.size(); i++)
							{
								DocumentVO documentVo = documentList.get(i);
								documentVo.setSoId(serviceOrderId);
								documentVo.setCompanyId(buyerId);
								isoWizardPersistDelegate.insertSODocument(documentVo);
							}
						}
					}
					catch (DataServiceException e)
					{
						logger.info("Caught Exception and ignoring", e);
					}
					logger.debug("Invoking setFlagOnServiceOrder from SOWSessionFacility.evaluateSOWBeanForCopy" );
					setFlagOnServiceOrder(serviceOrderId, OrderConstants.SO_EDIT_MODE_FLAG, fetchDelegate, securityContext);

				}// end try
				catch (BusinessServiceException bse)
				{
					logger.debug("Exception thrown while getting service order(" + soID + ")");
					throw bse;

				}// end catch

				// Dont do validation on all the tabs. Reset the mode to skip validation
				tabNavigationDTO.setSowMode("COPY");

				serviceOrderWizardBean = new ServiceOrderWizardBean();
				loadSOWBean(serviceOrderWizardBean);
				serviceOrderWizardBean.setTabDTOs(soDTOs);
				serviceOrderWizardBean.setSoId(serviceOrderId);
				serviceOrderWizardBean.setComingFromTab(OrderConstants.SOW_SOW_TAB);
				serviceOrderWizardBean.setStartPoint(tabNavigationDTO.getStartingPoint());
				//SL-19820
				//getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY, serviceOrderWizardBean);
				getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY+"_"+serviceOrderId, serviceOrderWizardBean);
				logger.debug("Ending edit mode setup");
			}// end if
		}
	}

	private void mapCopyServiceOrder(HashMap<String, Object> soDTOs)
	{
		if (soDTOs != null)
		{
			SOWScopeOfWorkTabDTO scopeOfWorkDto = (SOWScopeOfWorkTabDTO) soDTOs.get(OrderConstants.SOW_SOW_TAB);

			if (scopeOfWorkDto != null)
			{
				// The next 6 lines were commented out for testing purposes
				// These should not be checked in commented out. Carlos
				/*scopeOfWorkDto.setServiceDate1(null);
				scopeOfWorkDto.setServiceDate2(null);
				scopeOfWorkDto.setServiceDateType(null);
				scopeOfWorkDto.setServiceLocationContact(new SOWContactLocationDTO());
				scopeOfWorkDto.setStartTime(null);
				scopeOfWorkDto.setEndTime(null);*/
				ArrayList<SOTaskDTO> taskList= new ArrayList<SOTaskDTO>();
				ArrayList<SOTaskDTO> taskCopyList= new ArrayList<SOTaskDTO>();

				taskList=scopeOfWorkDto.getTasks();
				
				for (SOTaskDTO task : taskList) {
					task.setEntityId(null);
					task.setTaskId(null);
					task.setPriceHistoryList(null);
					taskCopyList.add(task);
					
				}
				scopeOfWorkDto.setTasks(taskCopyList);
				
				scopeOfWorkDto.setCreatedDate(null);
				scopeOfWorkDto.setCreatedDateString(null);
				scopeOfWorkDto.setModifiedDate(null);
				scopeOfWorkDto.setWarnings(new ArrayList<IWarning>());
				scopeOfWorkDto.setErrors(new ArrayList<IError>());
				scopeOfWorkDto.setDoFullValidation(Boolean.FALSE);
			}
			soDTOs.put(OrderConstants.SOW_SOW_TAB, scopeOfWorkDto);

			SOWAdditionalInfoTabDTO addInfoDto = (SOWAdditionalInfoTabDTO) soDTOs.get(OrderConstants.SOW_ADDITIONAL_INFO_TAB);

			if (addInfoDto != null)
			{
				//addInfoDto.setCustomRefs(null);
				addInfoDto.setCreatedDate(null);
				addInfoDto.setModifiedDate(null);
				addInfoDto.setCreatedDateString(null);
				addInfoDto.setDoFullValidation(Boolean.FALSE);
				addInfoDto.setWarnings(new ArrayList<IWarning>());
				addInfoDto.setErrors(new ArrayList<IError>());
				addInfoDto.setStatusString(null);//Reset the status for the copied order as it's not yet saved or posted.
				addInfoDto.setWfStateId(null);
			}
			soDTOs.put(OrderConstants.SOW_ADDITIONAL_INFO_TAB, addInfoDto);

			SOWPartsTabDTO partsTabDto = (SOWPartsTabDTO) soDTOs.get(OrderConstants.SOW_PARTS_TAB);

			if (partsTabDto != null)
			{
				partsTabDto.setContainsTasks(Boolean.FALSE);
				partsTabDto.setCreatedDate(null);
				partsTabDto.setCreatedDateString(null);
				partsTabDto.setIsFreshTask(Boolean.FALSE);
				partsTabDto.setModifiedDate(null);
				//partsTabDto.setParts(null);
				List<SOWPartDTO> parts = partsTabDto.getParts();
				for (SOWPartDTO partDTO : parts) {
					partDTO.setPartId(null);
					partDTO.setEntityId(null);
				}
				partsTabDto.setParts(parts);
				partsTabDto.setWarnings(new ArrayList<IWarning>());
				partsTabDto.setErrors(new ArrayList<IError>());
				//partsTabDto.setDoFullValidation(Boolean.FALSE);
			}
			soDTOs.put(OrderConstants.SOW_PARTS_TAB, partsTabDto);

			SOWProvidersTabDTO providerDto = (SOWProvidersTabDTO) soDTOs.get(OrderConstants.SOW_PROVIDERS_TAB);
			if (providerDto != null)
			{
				providerDto.setProviders(null);
				providerDto.setCreatedDate(null);
				providerDto.setCreatedDateString(null);
				providerDto.setDoFullValidation(Boolean.FALSE);
				providerDto.setWarnings(new ArrayList<IWarning>());
				providerDto.setErrors(new ArrayList<IError>());
				//setting SPN ID as 0 while copying of Service Order
				if(null!=providerDto.getSpnId()&&providerDto.getSpnId()>0){
					providerDto.setSpnId(0);
				}				
			}
			soDTOs.put(OrderConstants.SOW_PROVIDERS_TAB, providerDto);

			SOWPricingTabDTO pricingTabDto = (SOWPricingTabDTO) soDTOs.get(OrderConstants.SOW_PRICING_TAB);
			if (pricingTabDto != null)
			{
				pricingTabDto.setCreatedDate(null);
				pricingTabDto.setCreatedDateString(null);
				pricingTabDto.setLaborSpendLimit("0.00");
				pricingTabDto.setModifiedDate(null);
				pricingTabDto.setPartsSpendLimit("0.00");				
				pricingTabDto.setWarnings(new ArrayList<IWarning>());
				pricingTabDto.setErrors(new ArrayList<IError>());
				pricingTabDto.setDoFullValidation(Boolean.FALSE);
				pricingTabDto.setInitialLaborSpendLimit(0.0);
				pricingTabDto.setInitialPartsSpendLimit(0.0);
				pricingTabDto.setPermitPrepaidPrice(0.0);
				
				pricingTabDto.setLaborTaxPercentage("0.00");
				pricingTabDto.setPartsTax("0.00");
				if(pricingTabDto.getTasks()!=null && pricingTabDto.getTasks().size()>0)
				{
					ArrayList<SOTaskDTO> taskList= new ArrayList<SOTaskDTO>();
					ArrayList<SOTaskDTO> taskCopyList= new ArrayList<SOTaskDTO>();

					taskList=pricingTabDto.getTasks();
					
					for (SOTaskDTO task : taskList) {
						task.setEntityId(null);
						task.setTaskId(null);
						task.setPriceHistoryList(null);
						taskCopyList.add(task);
						
					}
					
					pricingTabDto.setTasks(taskCopyList);
					
				for(int count=0;count<pricingTabDto.getTasks().size();count++)
				{
					if(pricingTabDto.getTasks().get(count).getTaskType()!=null && pricingTabDto.getTasks().get(count).getTaskType().intValue()==1)
					{
						pricingTabDto.getTasks().get(count).setFinalPrice(0.0);
						pricingTabDto.getTasks().get(count).setRetailPrice(0.0);
						pricingTabDto.getTasks().get(count).setSellingPrice(0.0);
			}
					else
					{
				pricingTabDto.getTasks().get(count).setFinalPrice(null);
					}
				if(pricingTabDto.getTasks().get(count).getPriceHistoryList()!=null)
				{
				pricingTabDto.getTasks().get(count).setPriceHistoryList(null);
				}
				}
			}
			}
			soDTOs.put(OrderConstants.SOW_PRICING_TAB, pricingTabDto);
		}
	}

	/**
	 * This method evaluates (Service Order Wizard) SOW bean and persists it in
	 * database. This gets called when user clicks [Save as Draft] from any of
	 * the tabs on Service Order Wizard
	 *
	 * @param sowTabDTO
	 *            SOWBaseTabDTO
	 * @param tabNavigationDTO
	 *            TabNavigationDTO
	 * @param isoWizardPersistDelegate
	 *            ISOWizardPersistDelegate
	 * @param commonCriteria TODO
	 * @param orderGroupDelegate TODO
	 * @throws BusinessServiceException
	 */
	public void evaluateAndSaveSOWBean(SOWBaseTabDTO sowTabDTO, TabNavigationDTO tabNavigationDTO,
			ISOWizardPersistDelegate isoWizardPersistDelegate, ServiceOrdersCriteria commonCriteria, IOrderGroupDelegate orderGroupDelegate) throws BusinessServiceException
	{
		//SL-19820
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		//ServiceOrderWizardBean serviceOrderWizardBean = (ServiceOrderWizardBean) getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY);
		ServiceOrderWizardBean serviceOrderWizardBean = (ServiceOrderWizardBean) getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY+"_"+soId);

		String actionPerformed = null;
		if (sowTabDTO != null && tabNavigationDTO != null)
		{
			actionPerformed = tabNavigationDTO.getActionPerformed();
			serviceOrderWizardBean = evaluateSOWBean(sowTabDTO, tabNavigationDTO);
		}
		if (actionPerformed != null)
		{
			if (actionPerformed.equals(OrderConstants.SOW_SAVE_AS_DRAFT_ACTION))
			{
				List<IError> errorList = sowTabDTO.getErrorsOnly();
				if (errorList != null)
				{
					if (errorList.size() > 0)
					{
						// Don't save it to the database if there are errors
					}
					else
					{	
						//Get spnId used for provider Search and save to DB.
						//SL-19820
						//SOWProviderSearchDTO providerSearchDto = (SOWProviderSearchDTO) getSession().getAttribute(ProviderConstants.PROVIDERS_FILTER_CRITERIA);
						SOWProviderSearchDTO providerSearchDto = (SOWProviderSearchDTO) getSession().getAttribute(ProviderConstants.PROVIDERS_FILTER_CRITERIA+"_"+soId);
						
						HashMap<String, Object> tabDTOs = serviceOrderWizardBean.getTabDTOs();
						SOWProvidersTabDTO providerDto = (SOWProvidersTabDTO) tabDTOs.get(OrderConstants.SOW_PROVIDERS_TAB);
						if(null != providerSearchDto && null != providerSearchDto.getSpn()){							
							providerDto.setSpnId(providerSearchDto.getSpn());
							//providerDto.setRoutingPriorityApplied(providerSearchDto.isRoutingPriorityApplied());
							providerDto.setPerformanceScore(providerSearchDto.getPerformanceScore());
						}else{
							//SL-18914 set the searched spn id if providers filtered by spn else check for the spn id available in so hdr table  
							//set spn id from so_hdr table if already available otherwise set null			
							if(null==providerDto.getSpnId())
							{
							providerDto.setSpnId(0);
							}
						}
						boolean routingPriorityApplied = (null==providerDto.getRoutingPriorityAppliedInd()) ? false : providerDto.getRoutingPriorityAppliedInd();
						
						
						IPersistor persistor = PersistorFactory.getInstance().getPersistor(OrderConstants.DB_PERSIST);
						//SL-19820
						//String soId = (String) getSession().getAttribute(OrderConstants.SO_ID);
						if (soId != null)
						{
							serviceOrderWizardBean.setSoId(soId);
						}
						//SL-19820
						//String groupId = (String)getSession().getAttribute(OrderConstants.GROUP_ID);
						String groupId = (String) getAttribute(OrderConstants.GROUP_ID);
						if (getBuyerId() != null)
						{
							serviceOrderWizardBean.setGroupId(groupId);
							if(!sowTabDTO.isSaveAndAutoPost()){
								serviceOrderWizardBean.setSaveAsDraft(true);
							}else{
								serviceOrderWizardBean.setAutoPost(true);
							}
							persistor.persist(serviceOrderWizardBean, isoWizardPersistDelegate, getBuyerId(),routingPriorityApplied);
						}
						
						// Select Group changes here (Location, Schedule, Price)						
						if(StringUtils.isNotBlank(groupId))
						{
							OFHelper ofHelper = (OFHelper) MPSpringLoaderPlugIn.getCtx().getBean("ofHelper");
							if (ofHelper.isNewGroup(groupId)){
								return; // the group handling is already done in the OrderFulfillment Sub-system
							}
							//SL-19820
							//List<String> soIdList = (List<String>)getSession().getAttribute(OrderConstants.SOW_SO_ID_LIST);
							List<String> soIdList = (List<String>)getSession().getAttribute(OrderConstants.SOW_SO_ID_LIST+"_"+groupId);
							
							// Location and Schedule changes propagated to sibling orders
							SOWScopeOfWorkTabDTO scopeOfWorkDTO = (SOWScopeOfWorkTabDTO) SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_SOW_TAB);
							if(scopeOfWorkDTO != null)
							{
								scopeOfWorkDTO.handleOrderGroupChanges(groupId, soIdList, orderGroupDelegate, commonCriteria.getSecurityContext());
							}
							
							// Pricing changes propagated to sibling orders
							SOWPricingTabDTO pricingTabDTO = (SOWPricingTabDTO) SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PRICING_TAB);
							if(pricingTabDTO != null)
							{
								pricingTabDTO.handleOrderGroupChanges(groupId,  orderGroupDelegate, commonCriteria);
							}
							
						}
						
						
					}
				}
			}
		}

	}
	
	/**
	 * This method evaluates (Service Order Wizard) SOW bean and persists it in
	 * database. This gets called when user clicks [Save & AutoPost] from any of
	 * the tabs on Service Order Wizard
	 *
	 * @param sowTabDTO
	 *            SOWBaseTabDTO
	 * @param tabNavigationDTO
	 *            TabNavigationDTO
	 * @param isoWizardPersistDelegate
	 *            ISOWizardPersistDelegate
	 * @param commonCriteria TODO
	 * @param orderGroupDelegate TODO
	 * @throws BusinessServiceException
	 */
	public boolean evaluateAndSaveAndAutoPostSOWBean(SOWBaseTabDTO sowTabDTO, TabNavigationDTO tabNavigationDTO,
			ISOWizardPersistDelegate isoWizardPersistDelegate, ServiceOrdersCriteria commonCriteria, IOrderGroupDelegate orderGroupDelegate) throws BusinessServiceException
	{
		logger.info("inside evaluateAndSaveAndAutoPostSOWBean");
		boolean gotoReviewtab = false;
		ServiceOrderWizardBean serviceOrderWizardBean = evaluateSOWBean(sowTabDTO, tabNavigationDTO);
		
		HashMap<String, Object> tempTabDTOs = getAllTabDTO();
		HashMap<String, Object> tabDTOs = new HashMap<String, Object>();
		Set<String> tabKeys = tempTabDTOs.keySet();
		for (String tabKey : tabKeys) {
			// Do not validate the providers, since we are validating for auto-post
			if (!tabKey.equals(OrderConstants.SOW_PROVIDERS_TAB)) {  
				tabDTOs.put(tabKey, tempTabDTOs.get(tabKey));
			}
		}
		tabDTOs.put(OrderConstants.SOW_REVIEW_TAB, sowTabDTO);
		
		SOWValidatorFacility sowValidator = SOWValidatorFacility.getInstance();
		// 1. Validate
		sowValidator.validate(null, OrderConstants.SOW_EDIT_MODE, tabDTOs);
		SOWTabstateManagerFacility sowTabstateManagerFacility = SOWTabstateManagerFacility.getInstance();
		// sowTabstateManagerFacility.populateTabStates(serviceOrderWizardBean,
		// sowTabDTO, tabNavigationDTO)
		boolean reviewTabError = sowValidator.isReviewTabErrors(tabDTOs);
		boolean errorWarningFlag = sowValidator.isErrorsWarningExist(tabDTOs);
		logger.info("inside reviewTabError:"+reviewTabError);
		logger.info("inside errorWarningFlag::"+errorWarningFlag);

		if (reviewTabError == false)
		{
			logger.info("inside first if reviewTabError false");
			// 2. Check for errors/warnings
			if (errorWarningFlag == false)
			{		
				logger.info("going to call autoPostSO");
				Boolean tmpGoToReviewtab = validateMaxLimitPerTransaction(tabDTOs, sowTabDTO, sowTabstateManagerFacility, tabNavigationDTO);
				if (tmpGoToReviewtab != null){
					gotoReviewtab = tmpGoToReviewtab;
					return gotoReviewtab;
				}
				sowTabDTO.setSaveAndAutoPost(true);
				logger.info("setSaveAndAutoPost::"+sowTabDTO.isSaveAndAutoPost());
				evaluateAndSaveSOWBean(sowTabDTO, tabNavigationDTO, isoWizardPersistDelegate, commonCriteria, orderGroupDelegate);
				logger.info("before autoPostSO");
				isoWizardPersistDelegate.autoPostSO(serviceOrderWizardBean.getSoId());
			}
			else
			{
				logger.info("else else");
				//SL-19820
				//sowTabstateManagerFacility.setTabStateForReviewTab(getSOWBean(), OrderConstants.SOW_REVIEW_TAB_FAILED, tabNavigationDTO);
				sowTabstateManagerFacility.setTabStateForReviewTab(getSOWBean((String)getAttribute(OrderConstants.SO_ID)), OrderConstants.SOW_REVIEW_TAB_FAILED, tabNavigationDTO);
				// throw new
				// BusinessServiceException(OrderConstants.SERVICE_ORDER_VALIDATION_ERRORS_WARNINGS)
				// ;
				addReviewTabError("", OrderConstants.SERVICE_ORDER_VALIDATION_ERRORS_WARNINGS, OrderConstants.SOW_TAB_ERROR);
				gotoReviewtab = true;

			}
		}
		else
		{
			logger.info("inside second else");
			//SL-19820
			//sowTabstateManagerFacility.setTabStateForReviewTab(getSOWBean(), OrderConstants.SOW_REVIEW_TAB_FAILED, tabNavigationDTO);
			sowTabstateManagerFacility.setTabStateForReviewTab(getSOWBean((String)getAttribute(OrderConstants.SO_ID)), OrderConstants.SOW_REVIEW_TAB_FAILED, tabNavigationDTO);
			gotoReviewtab = true;
		}
		return gotoReviewtab;

	}

	/**
	 * This method evaluates SOW(Service Order Wizard) bean and posts it. This
	 * gets called when user clicks [Post Service Order] on the review tabs on
	 * Service Order Wizard.
	 *
	 * @param sowTabDTO
	 *            SOWBaseTabDTO
	 * @param tabNavigationDTO
	 *            TabNavigationDTO
	 * @param isoWizardPersistDelegate
	 *            ISOWizardPersistDelegate
	 * @param serviceOrdersCriteria 
	 * @param fetchDelegate
	 *            List
	 * @throws BusinessServiceException
	 */

	public boolean evaluateAndPostSOWBean(SOWBaseTabDTO sowTabDTO, ISOWizardPersistDelegate isoWizardPersistDelegate,
			IFinanceManagerDelegate financeManagerDelegate, IOrderGroupDelegate orderGroupDelegate, TabNavigationDTO tabNavigationDTO, ServiceOrdersCriteria serviceOrdersCriteria, 
			ISOWizardFetchDelegate fetchDelegate, IRelayServiceNotification relayNotificationService) throws BusinessServiceException
	{
		logger.info("inside evaluateAndPostSOWBean");
		HashMap<String, Object> tabDTOs = getAllTabDTO();
		tabDTOs.put(OrderConstants.SOW_REVIEW_TAB, sowTabDTO);
		// SOWPricingTabDTO tmpPricingTabDTO =(SOWPricingTabDTO) tabDTOs.get(OrderConstants.SOW_PRICING_TAB); tmpPricingTabDTO.getInitialLaborSpendLimit(); tmpPricingTabDTO.getInitialPartsSpendLimit()
		//Get spnId used for provider Search and save to DB while routing.
		//SL-19820
		//SOWProviderSearchDTO providerSearchDto = (SOWProviderSearchDTO) getSession().getAttribute(ProviderConstants.PROVIDERS_FILTER_CRITERIA);
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		SOWProviderSearchDTO providerSearchDto = (SOWProviderSearchDTO) getSession().getAttribute(ProviderConstants.PROVIDERS_FILTER_CRITERIA+"_"+soId);
		SOWProvidersTabDTO providerDto = (SOWProvidersTabDTO) tabDTOs.get(OrderConstants.SOW_PROVIDERS_TAB);
		if(null != providerSearchDto && null != providerSearchDto.getSpn()){							
			providerDto.setSpnId(providerSearchDto.getSpn());
			//providerDto.setRoutingPriorityApplied(providerSearchDto.isRoutingPriorityApplied());
			providerDto.setPerformanceScore(providerSearchDto.getPerformanceScore());
		}else{
			providerDto.setSpnId(0);
		}
		boolean routingPriorityApplied = (null==providerDto.getRoutingPriorityAppliedInd()) ? false : providerDto.getRoutingPriorityAppliedInd();
		boolean gotoReviewtab = false;

		Integer buyerId = getBuyerId();
		//SL-19820
		//String soId = getSoId();
		// ArrayList<Integer> routedResourcesIds = getResourceIds();
		SOWValidatorFacility sowValidator = SOWValidatorFacility.getInstance();
		// 1. Validate
		sowValidator.validate(null, OrderConstants.SOW_EDIT_MODE, tabDTOs);
		SOWTabstateManagerFacility sowTabstateManagerFacility = SOWTabstateManagerFacility.getInstance();
		// sowTabstateManagerFacility.populateTabStates(serviceOrderWizardBean,
		// sowTabDTO, tabNavigationDTO)
		boolean reviewTabError = sowValidator.isReviewTabErrors(tabDTOs);
		boolean errorWarningFlag = sowValidator.isErrorsWarningExist(tabDTOs);
		boolean postFromFE = false;
		boolean relayServicesNotifyFlag = relayNotificationService.isRelayServicesNotificationNeeded(buyerId, soId);
		if (reviewTabError == false)
		{
			// 2. Check for errors/warnings
			if (errorWarningFlag == false)
			{
				if (buyerId != null && soId != null)
				{
					ServiceOrderDTO serviceObj = (ServiceOrderDTO) sowTabDTO;
					
					Boolean tmpGoToReviewtab = validateMaxLimitPerTransaction(tabDTOs, sowTabDTO, sowTabstateManagerFacility, tabNavigationDTO);
					if (tmpGoToReviewtab != null){
						gotoReviewtab = tmpGoToReviewtab;
						return gotoReviewtab;
					}

					if(fetchDelegate.checkStatusForRoute(soId)){
					// 2.1 Save first before routing, new list of routers get
					// saved as well
					IPersistor persistor = PersistorFactory.getInstance().getPersistor(OrderConstants.DB_PERSIST);
					try
					{
						int numRoutedProviders = getNumRoutedProviders(tabDTOs);
						//SL-19820
						//String groupId = (String)getSession().getAttribute(OrderConstants.GROUP_ID);
						//getSOWBean().setGroupId(groupId);
						String groupId = (String) getAttribute(OrderConstants.GROUP_ID);
						getSOWBean(soId).setGroupId(groupId);
						//ServiceOrderDTO serviceObj = (ServiceOrderDTO) sowTabDTO; **VCommented
						String statusString = serviceObj.getStatusString();
						if(statusString == null ||statusString.equals("Draft") ||statusString.equals("PreDraft")){
							postFromFE =true;
							
						}
						//SL-19820
						//getSOWBean().setPost(postFromFE);
						//getSOWBean().setRouteFromFE(sowTabDTO.isRouteFromFE());
						//persistor.persist(getSOWBean(), isoWizardPersistDelegate, buyerId, serviceOrdersCriteria,routingPriorityApplied);
						getSOWBean(soId).setPost(postFromFE);
						getSOWBean(soId).setRouteFromFE(sowTabDTO.isRouteFromFE());
						persistor.persist(getSOWBean(soId), isoWizardPersistDelegate, buyerId, serviceOrdersCriteria,routingPriorityApplied);
						/* trOrderId is soId if its single order or  groupId in case of grouped Orders*/
						String trOrderId = soId;
						
						if (numRoutedProviders > 0)
						{
							if(StringUtils.isNotBlank(groupId)) {
								trOrderId = groupId;
								routeAllGroupOrderChilden(orderGroupDelegate);
							} else {
								// 2.2 Route next - Only if there is atleast one
								// provider available
								if(statusString==null || OrderConstants.TAB_DRAFT.equalsIgnoreCase(statusString))
									isoWizardPersistDelegate.processRouteSO(soId, buyerId, postFromFE,routingPriorityApplied);
								else
									isoWizardPersistDelegate.processReRouteSO(soId, buyerId);
								sowTabstateManagerFacility.setTabStateForReviewTab(getSOWBean(soId), OrderConstants.SOW_REVIEW_TAB_SUCCESS,
										tabNavigationDTO);
	
								//Send email to Buyer about posting fee
								logger.info("Sending mail for Posting SO by Velocity Context is removed::");
								//financeManagerDelegate.sendBuyerPostingFeeEmail(buyerId, soId);
							}
							TierRoutingSchedulerManager trSchedulerMgr = (TierRoutingSchedulerManager) MPSpringLoaderPlugIn.getCtx().getBean("tierroutingschedulermanager");
							// once routing is done, remove any schedule jobs for TIER ROUTING
							trSchedulerMgr.stopAndRemoveJob(trOrderId);
							
						}
						else
						{
							sowTabstateManagerFacility.setTabStateForReviewTab(getSOWBean(soId), OrderConstants.SOW_REVIEW_TAB_FAILED,
									tabNavigationDTO);
							// throw new
							// BusinessServiceException(OrderConstants.ROUTED_RESOUCES_NOT_SELECTED)
							// ;
							addReviewTabError("", OrderConstants.ROUTED_RESOUCES_NOT_SELECTED, OrderConstants.SOW_TAB_ERROR);
							gotoReviewtab = true;
						}
					}
					catch (BusinessServiceException bse)
					{
						bse.printStackTrace();
						if (bse.getMessage() == null || bse.getMessage().trim().equals(""))
						{
							addReviewTabError("", OrderConstants.SOW_REVIEW_REROUTE_SYSTEM_ERROR, OrderConstants.SOW_TAB_ERROR);
						} else {
							sowTabstateManagerFacility.setTabStateForReviewTab(
									//SL-19820
									//getSOWBean(),
									getSOWBean(soId),
									OrderConstants.SOW_REVIEW_TAB_FAILED,
									tabNavigationDTO);

							addReviewTabError(
									"",
									OrderConstants.SOW_REVIEW_REROUTE_BUSINESS_ERROR
											+ bse.getMessage(),
									OrderConstants.SOW_TAB_ERROR);
						}
						gotoReviewtab = true;
					}
				}else{
					//SL-19820
					//sowTabstateManagerFacility.setTabStateForReviewTab(getSOWBean(), OrderConstants.SOW_REVIEW_TAB_FAILED, tabNavigationDTO);
					sowTabstateManagerFacility.setTabStateForReviewTab(getSOWBean(soId), OrderConstants.SOW_REVIEW_TAB_FAILED, tabNavigationDTO);
					addReviewTabError("", OrderConstants.SERVICE_ORDER_MUST_BE_IN_DRAFT_STATE, OrderConstants.SOW_TAB_ERROR);
					gotoReviewtab = true;
				}
			  }
			}
			else
			{
				//SL-19820
				//sowTabstateManagerFacility.setTabStateForReviewTab(getSOWBean(), OrderConstants.SOW_REVIEW_TAB_FAILED, tabNavigationDTO);
				sowTabstateManagerFacility.setTabStateForReviewTab(getSOWBean(soId), OrderConstants.SOW_REVIEW_TAB_FAILED, tabNavigationDTO);
				// throw new
				// BusinessServiceException(OrderConstants.SERVICE_ORDER_VALIDATION_ERRORS_WARNINGS)
				// ;
				addReviewTabError("", OrderConstants.SERVICE_ORDER_VALIDATION_ERRORS_WARNINGS, OrderConstants.SOW_TAB_ERROR);
				gotoReviewtab = true;

			}
		}
		else
		{
			//SL-19820
			//sowTabstateManagerFacility.setTabStateForReviewTab(getSOWBean(), OrderConstants.SOW_REVIEW_TAB_FAILED, tabNavigationDTO);
			sowTabstateManagerFacility.setTabStateForReviewTab(getSOWBean(soId), OrderConstants.SOW_REVIEW_TAB_FAILED, tabNavigationDTO);
			gotoReviewtab = true;
		}
		
		logger.info("Checking for relay notification");
		if (relayServicesNotifyFlag) {
			logger.info("relayServicesNotifyFlag = "+relayServicesNotifyFlag);
			if(null != tabDTOs.get("isPriceChanged") && true == (Boolean)tabDTOs.get("isPriceChanged")){
				Map<String, String> params = new HashMap<String, String>();
				params.put("newLaborPrice", (null != tabDTOs.get("newLaborPrice") ? tabDTOs.get("newLaborPrice").toString() : "0"));
				params.put("newPartsPrice", (null != tabDTOs.get("newPartsPrice") ? tabDTOs.get("newPartsPrice").toString() : "0"));
				params.put("oldLaborPrice", (null != tabDTOs.get("oldLaborPrice") ? tabDTOs.get("oldLaborPrice").toString() : "0"));
				params.put("oldPartsPrice", (null != tabDTOs.get("oldPartsPrice") ? tabDTOs.get("oldPartsPrice").toString() : "0"));
				logger.info("sending WH to relay");
				relayNotificationService.sentNotificationRelayServices(MPConstants.ORDER_SPENDLIMIT_UPDATE_FROM_FRONTEND, soId, params);	
			}
		}
		return gotoReviewtab;

	}
	
	public boolean isOrderPriceAboveMaxSpendLimit(Double postingFee, Double total, SecurityContext securityContext) throws BusinessServiceException 
	{		
		if (null == postingFee) {
			postingFee = new Double(0.00);
		}
		double max = OrderConstants.NO_SPEND_LIMIT;
		
		if (null != securityContext) {
			max = securityContext.getMaxSpendLimitPerSO();
		}
		// check total against available balance
		if (max == OrderConstants.NO_SPEND_LIMIT || max >= total) {
			return false;
		} else {
			return true;
		}
	}
	public void routeAllGroupOrderChilden(IOrderGroupDelegate orderGroupDelegate)
	throws BusinessServiceException {
		try
		{
			if(orderGroupDelegate == null)
				return;
	
			//SL-19820
			//String groupId = (String)getSession().getAttribute(OrderConstants.GROUP_ID);
			String groupId = (String) getAttribute(OrderConstants.GROUP_ID);
			if(StringUtils.isBlank(groupId))
				return;
			
			OFHelper ofHelper = (OFHelper) MPSpringLoaderPlugIn.getCtx().getBean("ofHelper");
			if (ofHelper.isNewGroup(groupId)){
				return; // the group handling is already done in the OrderFulfillment Sub-system
			}
			SOWProvidersTabDTO providersTab = (SOWProvidersTabDTO)getInstance().getTabDTO(OrderConstants.SOW_PROVIDERS_TAB);
			
			// Need to get a list of IDs from the list of providers
			List<Integer> orderGrpPovidersId= convertToListOfProviderIDs(providersTab.getProviders());
	
			
			ServiceOrdersCriteria commonCriteria = (ServiceOrdersCriteria)getSession().getAttribute(OrderConstants.SERVICE_ORDER_CRITERIA_KEY);
			if(commonCriteria == null)
				return;
			
			SecurityContext securityContext = commonCriteria.getSecurityContext();
			
			Integer buyerId = getBuyerId();
	
			
			// Propagate Location and Schedule changes to children
			SOWScopeOfWorkTabDTO sowTabDTO = (SOWScopeOfWorkTabDTO)getInstance().getTabDTO(OrderConstants.SOW_SOW_TAB);
			if(sowTabDTO != null)
			{
				//SL-19820
				//List<String> soIdList = (List<String>)getSession().getAttribute(OrderConstants.SOW_SO_ID_LIST);
				List<String> soIdList = (List<String>)getSession().getAttribute(OrderConstants.SOW_SO_ID_LIST+"_"+groupId);
				sowTabDTO.handleOrderGroupChanges(groupId, soIdList, orderGroupDelegate, securityContext);
			}
			
			// Propagate Group Pricing changes to children
			List<ServiceOrderSearchResultsVO> childOrders = orderGroupDelegate.getChildServiceOrders(groupId);
			SOWPricingTabDTO pricingTabDTO = (SOWPricingTabDTO)getInstance().getTabDTO(OrderConstants.SOW_PRICING_TAB);
			if(pricingTabDTO != null)
			{
				orderGroupDelegate.priceOrderGroup(childOrders);
				pricingTabDTO.handleOrderGroupChanges(groupId, orderGroupDelegate, commonCriteria);
			}
			
			orderGroupDelegate.routeGroupToSelectedProviders(childOrders, orderGrpPovidersId, groupId, buyerId, securityContext);			
		
		}
		catch (Exception e)
		{
			throw new BusinessServiceException(e.getMessage());
		}
		
		
	}
	
	private List<Integer> convertToListOfProviderIDs(List<SOWProviderDTO> dtoList)
	{
		List<Integer> idList = new ArrayList<Integer>();
		if(dtoList == null)
			return idList;
		
		for(SOWProviderDTO dto : dtoList)
		{
			if(dto.getIsChecked())
				idList.add(dto.getResourceId());
		}
		
		return idList;
	}
	

	private void addReviewTabError(String fieldId, String errorMsg, String errorType)
	{
		ServiceOrderDTO serviceOrderDTO = (ServiceOrderDTO) getTabDTO(OrderConstants.SOW_REVIEW_TAB);
		serviceOrderDTO.addError(fieldId, errorMsg, errorType);
	}

	private void loadSOWBean(ServiceOrderWizardBean serviceOrderWizardBean)
	{
		// 1. Instantiate a ServiceOrderWizardBean
		// ServiceOrderWizardBean serviceOrderWizardBean = new
		// ServiceOrderWizardBean();
		// 2. Load the Tab state hash
		HashMap<String, Object> tabHash = loadTabStateHash();
		// 3. Set the tab state hash in the ServiceOrderWizardBean
		serviceOrderWizardBean.setTabStateDTOs(tabHash);
		// 4. Set other attributes in ServiceOrderWizardBean
		serviceOrderWizardBean.setComingFromTab(OrderConstants.SOW_SOW_TAB);
		serviceOrderWizardBean.setGoingToTab(OrderConstants.SOW_SOW_TAB);
		serviceOrderWizardBean.setNextURL(LookupHash.getMyActionName(OrderConstants.SOW_ADDITIONAL_INFO_TAB));
		serviceOrderWizardBean.setPreviousURL(null);
		// serviceOrderWizardBean.setStartPoint(startPoint);
		// return serviceOrderWizardBean;

	}

	private HashMap<String, Object> loadTabStateHash()
	{
		HashMap<String, Object> hashMap = new HashMap<String, Object>();

		SOWTabStateVO sowSowTabStateVO = new SOWTabStateVO();
		sowSowTabStateVO.setTabState(OrderConstants.SOW_TAB_ACTIVE);
		sowSowTabStateVO.setTabIdentifier(OrderConstants.SOW_SOW_TAB);
		hashMap.put(OrderConstants.SOW_SOW_TAB, sowSowTabStateVO);

		SOWTabStateVO sowAdditionalInfoTabStateVO = new SOWTabStateVO();
		sowAdditionalInfoTabStateVO.setTabState(OrderConstants.SOW_TAB_ENABLED);
		sowAdditionalInfoTabStateVO.setTabIdentifier(OrderConstants.SOW_ADDITIONAL_INFO_TAB);
		hashMap.put(OrderConstants.SOW_ADDITIONAL_INFO_TAB, sowAdditionalInfoTabStateVO);

		SOWTabStateVO sowPartsTabStateVO = new SOWTabStateVO();
		sowPartsTabStateVO.setTabState(OrderConstants.SOW_TAB_INACTIVE);
		sowPartsTabStateVO.setTabIdentifier(OrderConstants.SOW_PARTS_TAB);
		hashMap.put(OrderConstants.SOW_PARTS_TAB, sowPartsTabStateVO);

		SOWTabStateVO sowPricingTabStateVO = new SOWTabStateVO();
		sowPricingTabStateVO.setTabState(OrderConstants.SOW_TAB_ENABLED);
		sowPricingTabStateVO.setTabIdentifier(OrderConstants.SOW_PRICING_TAB);
		hashMap.put(OrderConstants.SOW_PRICING_TAB, sowPricingTabStateVO);

		SOWTabStateVO sowProvidersTabStateVO = new SOWTabStateVO();
		sowProvidersTabStateVO.setTabState(OrderConstants.SOW_TAB_INACTIVE);
		sowProvidersTabStateVO.setTabIdentifier(OrderConstants.SOW_PROVIDERS_TAB);
		hashMap.put(OrderConstants.SOW_PROVIDERS_TAB, sowProvidersTabStateVO);

		SOWTabStateVO sowReviewTabStateVO = new SOWTabStateVO();
		sowReviewTabStateVO.setTabState(OrderConstants.SOW_TAB_ENABLED);
		sowReviewTabStateVO.setTabIdentifier(OrderConstants.SOW_REVIEW_TAB);
		hashMap.put(OrderConstants.SOW_REVIEW_TAB, sowReviewTabStateVO);

		return hashMap;
	}

	private HashMap<String, Object> loadServiceOrderDTO(String soID, ISOWizardFetchDelegate delegate)
	throws BusinessServiceException {
		HashMap<String, Object> dtoMap = null;
		try
		{
			dtoMap = delegate.getServiceOrderDTOs(soID, OrderConstants.BUYER_ROLEID);

		} catch (BusinessServiceException bse) {
			logger.debug("Exception thrown while getting service order(" + soID + ")");
			throw bse;
		}

		return dtoMap;
	}
//Fetching sku indicator for the selected so id
//	private Boolean checkSkuIndicatorValue(String soID,ISOWizardFetchDelegate delegate)
//	{
//		Boolean fetchSkuIndicatorFromWfmc;
//	fetchSkuIndicatorFromWfmc=delegate.checkSkuIndicatorValue(soID);
//		return fetchSkuIndicatorFromWfmc;
//	}
	private ServiceOrderWizardBean getTheSOWBean()
	{
		return (ServiceOrderWizardBean) getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY);
	}

	public ServiceOrderWizardBean getSOWBean() {
		return (ServiceOrderWizardBean) getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY);
	}
	
	//SL-19820
	public ServiceOrderWizardBean getSOWBean(String soId) {
		return (ServiceOrderWizardBean) getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY+"_"+soId);
	}

	public HashMap<String, Object> getAllTabStates()
	{
		//return getSOWBean().getTabStateDTOs();
		return getSOWBean((String) getAttribute(OrderConstants.SO_ID)).getTabStateDTOs();
	}

	public HashMap<String, Object> getAllTabDTO()
	{
		//SL-19820
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		//return getSOWBean().getTabDTOs();
		return getSOWBean(soId).getTabDTOs();
	}

	public SOWBaseTabDTO getTabDTO(String tabName) {
		//Sl-19820
		/*if (getSOWBean() == null || getSOWBean().getTabDTOs() == null) {
			return null;
		}
		if ((SOWBaseTabDTO) getSOWBean().getTabDTOs().get(tabName) == null) {
			return null;
		}
		return (SOWBaseTabDTO) getSOWBean().getTabDTOs().get(tabName);*/
		String soId = (String)getAttribute(OrderConstants.SO_ID);
		if (getSOWBean(soId) == null || getSOWBean(soId).getTabDTOs() == null) {
			return null;
		}
		if ((SOWBaseTabDTO) getSOWBean(soId).getTabDTOs().get(tabName) == null) {
			return null;
		}
		return (SOWBaseTabDTO) getSOWBean(soId).getTabDTOs().get(tabName);
	}
	
	/**SL-19820
	 * @param tabName
	 * @param soId
	 * @return
	 * New method to be called from wizardTemplate.jsp
	 */
	public SOWBaseTabDTO getTabDTO(String tabName, String soId) {
		//Sl-19820
		/*if (getSOWBean() == null || getSOWBean().getTabDTOs() == null) {
			return null;
		}
		if ((SOWBaseTabDTO) getSOWBean().getTabDTOs().get(tabName) == null) {
			return null;
		}
		return (SOWBaseTabDTO) getSOWBean().getTabDTOs().get(tabName);*/
		if (getSOWBean(soId) == null || getSOWBean(soId).getTabDTOs() == null) {
			return null;
		}
		if ((SOWBaseTabDTO) getSOWBean(soId).getTabDTOs().get(tabName) == null) {
			return null;
		}
		return (SOWBaseTabDTO) getSOWBean(soId).getTabDTOs().get(tabName);
	}

	public String getGoingToTab() {
		//SL-19820
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		//ServiceOrderWizardBean serviceOrderWizardBean = (ServiceOrderWizardBean) getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY);
		ServiceOrderWizardBean serviceOrderWizardBean = (ServiceOrderWizardBean) getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY+"_"+soId);
		if (serviceOrderWizardBean != null) {
			return serviceOrderWizardBean.getGoingToTab();
		}
		return null;
	}

	public String getStartPoint() {
		//SL-19820
		//ServiceOrderWizardBean serviceOrderWizardBean = (ServiceOrderWizardBean) getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY);
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		ServiceOrderWizardBean serviceOrderWizardBean = (ServiceOrderWizardBean) getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY+"_"+soId);
		if (serviceOrderWizardBean != null) {
			return serviceOrderWizardBean.getStartPoint();
		}
		return null;
	}

	public String getStartPointAndInvalidate(ISOWizardFetchDelegate fetchDelegate, SecurityContext securityContext) {
		String startPoint = getStartPoint();
		//Map<String, Object> sessionMap = ActionContext.getContext().getSession();
		
		logger.debug("Invoking setFlagOnServiceOrder from SOWSessionFacility.getStartPointAndInvalidate" );
		
		//Added for Incidents 4148358(Sl-19820)
		if(null != getSession().getAttribute(OrderConstants.EDITED_SO_ID)){
			//SL-21070
			setFlagOnServiceOrder((String) getSession().getAttribute(OrderConstants.EDITED_SO_ID), OrderConstants.SO_VIEW_MODE_FLAG, fetchDelegate, securityContext);
		}
		//SL-19820
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		//SL-21070
		setFlagOnServiceOrder(soId, OrderConstants.SO_VIEW_MODE_FLAG, fetchDelegate, securityContext);
		getSession().setAttribute(OrderConstants.LOCK_EDIT_SESSION_VAR+soId, null);
		getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY, null);
		getSession().setAttribute(OrderConstants.SO_ID, null);
		getSession().setAttribute(ProviderConstants.ALL_PROVIDERS, null);
		getSession().setAttribute(ProviderConstants.SO_MASTER_PROVIDERS_LIST, null);
		getSession().setAttribute(ProviderConstants.FILTERED_PROVIDERS, null);
		getSession().setAttribute(ProviderConstants.PROVIDERS_FILTER_CRITERIA, null);
		getSession().setAttribute(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG, null);
		getSession().setAttribute(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS, null);
		getSession().setAttribute(ProviderConstants.SELECTED_MAIN_CATEGORY_ID, null);
		getSession().setAttribute(ProviderConstants.CHECKED_PROVIDERS, null);
		getSession().setAttribute(ProviderConstants.SERVICE_LOCATION, null);
		getSession().setAttribute(OrderConstants.STATES_MAP, null);
		getSession().setAttribute(OrderConstants.PHONE_TYPES, null);
		getSession().setAttribute(OrderConstants.MAIN_SERVC_CAT_NAMES_MAP, null);
		getSession().setAttribute(OrderConstants.MAIN_SERVC_CAT, null);
		getSession().setAttribute(OrderConstants.SKILL_SELCTN_MAP, null);
		getSession().setAttribute(OrderConstants.CATEGORY_SELCTN, null);
		getSession().setAttribute(OrderConstants.SKILL_SELCTN, null);
		getSession().setAttribute("actionType", null);
		getSession().setAttribute("entryTab", null);
		getSession().setAttribute("spnBuilderFormInfo", null);
		
		//SL-19820
		getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY+"_"+soId, null);
		getSession().setAttribute(ProviderConstants.ALL_PROVIDERS+"_"+soId, null);
		getSession().setAttribute(ProviderConstants.SO_MASTER_PROVIDERS_LIST+"_"+soId, null);
		getSession().setAttribute(ProviderConstants.FILTERED_PROVIDERS+"_"+soId, null);
		getSession().setAttribute(ProviderConstants.PROVIDERS_FILTER_CRITERIA+"_"+soId, null);
		getSession().setAttribute(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG+"_"+soId, null);
		getSession().setAttribute(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS+"_"+soId, null);
		getSession().setAttribute(ProviderConstants.SELECTED_MAIN_CATEGORY_ID+"_"+soId, null);
		getSession().setAttribute(ProviderConstants.CHECKED_PROVIDERS+"_"+soId, null);
		getSession().setAttribute(ProviderConstants.SERVICE_LOCATION+"_"+soId, null);
		getSession().setAttribute(OrderConstants.STATES_MAP+"_"+soId, null);
		getSession().setAttribute(OrderConstants.PHONE_TYPES+"_"+soId, null);
		getSession().setAttribute(OrderConstants.MAIN_SERVC_CAT_NAMES_MAP+"_"+soId, null);
		getSession().setAttribute(OrderConstants.MAIN_SERVC_CAT+"_"+soId, null);
		getSession().setAttribute(OrderConstants.SKILL_SELCTN_MAP+"_"+soId, null);
		getSession().setAttribute(OrderConstants.CATEGORY_SELCTN+"_"+soId, null);
		getSession().setAttribute(OrderConstants.SKILL_SELCTN+"_"+soId, null);
		getSession().setAttribute("actionType_"+soId, null);
		getSession().setAttribute("entryTab_"+soId, null);
		getSession().setAttribute("spnBuilderFormInfo_"+soId, null);
		getSession().setAttribute(OrderConstants.GROUP_ID+"_"+soId, null);
		getSession().setAttribute(OrderConstants.SOW_SO_ID_LIST+"_"+soId, null);
		getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR+"_"+soId, null);
		
		setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY, null);
		setAttribute(OrderConstants.SO_ID, null);
		setAttribute(ProviderConstants.ALL_PROVIDERS, null);
		setAttribute(ProviderConstants.SO_MASTER_PROVIDERS_LIST, null);
		setAttribute(ProviderConstants.FILTERED_PROVIDERS, null);
		setAttribute(ProviderConstants.PROVIDERS_FILTER_CRITERIA, null);
		setAttribute(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG, null);
		setAttribute(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS, null);
		setAttribute(ProviderConstants.SELECTED_MAIN_CATEGORY_ID, null);
		setAttribute(ProviderConstants.CHECKED_PROVIDERS, null);
		setAttribute(ProviderConstants.SERVICE_LOCATION, null);
		setAttribute(OrderConstants.STATES_MAP, null);
		setAttribute(OrderConstants.PHONE_TYPES, null);
		setAttribute(OrderConstants.MAIN_SERVC_CAT_NAMES_MAP, null);
		setAttribute(OrderConstants.MAIN_SERVC_CAT, null);
		setAttribute(OrderConstants.SKILL_SELCTN_MAP, null);
		setAttribute(OrderConstants.CATEGORY_SELCTN, null);
		setAttribute(OrderConstants.SKILL_SELCTN, null);
		setAttribute("actionType", null);
		setAttribute("entryTab", null);
		setAttribute("spnBuilderFormInfo", null);
		//Remove session flag to indicate that the user has exited the Service Order Wizard
		getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR, null);
		return startPoint;
	}

	public void exitSOW() {
			getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY, null);
			getSession().setAttribute(ProviderConstants.ALL_PROVIDERS, null);
			getSession().setAttribute(ProviderConstants.SO_MASTER_PROVIDERS_LIST, null);
			getSession().setAttribute(ProviderConstants.FILTERED_PROVIDERS, null);
			getSession().setAttribute(ProviderConstants.PROVIDERS_FILTER_CRITERIA, null);
			getSession().setAttribute(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG, null);
			getSession().setAttribute(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS, null);
			getSession().setAttribute(ProviderConstants.SELECTED_MAIN_CATEGORY_ID, null);
			getSession().setAttribute(ProviderConstants.CHECKED_PROVIDERS, null);
			getSession().setAttribute(ProviderConstants.SERVICE_LOCATION, null);
			getSession().setAttribute(OrderConstants.STATES_MAP, null);
			getSession().setAttribute(OrderConstants.PHONE_TYPES, null);
			getSession().setAttribute(OrderConstants.MAIN_SERVC_CAT_NAMES_MAP, null);
			getSession().setAttribute(OrderConstants.MAIN_SERVC_CAT, null);
			getSession().setAttribute(OrderConstants.SKILL_SELCTN_MAP, null);
			getSession().setAttribute(OrderConstants.CATEGORY_SELCTN, null);
			getSession().setAttribute(OrderConstants.SKILL_SELCTN, null);
			getSession().setAttribute("entryTab", null);
			getSession().setAttribute("actionType", null);
			//Remove session flag to indicate that the user has exited the Service Order Wizard
			getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR, null);
	}

	private Integer getBuyerId() throws BusinessServiceException {
		SecurityContext securityCntxt = (SecurityContext) getSession().getAttribute("SecurityContext");
		if (securityCntxt == null)
		{
			throw new BusinessServiceException("SecurityContext is null");
		}
		return securityCntxt.getCompanyId();
	}

	private Integer getBuyerResourceId() throws BusinessServiceException
	{
		SecurityContext securityCntxt = (SecurityContext) getSession().getAttribute("SecurityContext");
		if (securityCntxt == null)
		{
			throw new BusinessServiceException("SecurityContext is null");
		}
		return securityCntxt.getVendBuyerResId();
	}

	private Integer getBuyerResourceContactId() throws BusinessServiceException
	{
		SecurityContext securityCntxt = (SecurityContext) getSession().getAttribute("SecurityContext");
		if (securityCntxt == null) {
			throw new BusinessServiceException("SecurityContext is null");
		}
		if((OrderConstants.BUYER_ROLEID == securityCntxt.getRoleId().intValue()) && (null != securityCntxt.getBuyerAdminContactId())){
			//For professional buyers return buyer admin contact id.			
			return securityCntxt.getBuyerAdminContactId();
		}else{
			return securityCntxt.getRoles().getContactId();
		}
	}
	
	/**
	 * This method gets user name from seecurity context
	 * @return userName String
	 * @throws BusinessServiceException
	 */
	private String getUserName() throws BusinessServiceException {
		SecurityContext securityCntxt = (SecurityContext) getSession().getAttribute(OrderConstants.SECURITY_CONTEXT);
		if (securityCntxt == null) {
			throw new BusinessServiceException("SecurityContext is null");
		}
		if((OrderConstants.BUYER_ROLEID == securityCntxt.getRoleId().intValue()) && (null != securityCntxt.getUsername())){
			//For professional buyers return user name.			
			return securityCntxt.getUsername();
		}else{
			return securityCntxt.getRoles().getUsername();
		}
	}

	public String getSoId() throws BusinessServiceException
	{
		return (String) getSession().getAttribute(OrderConstants.SO_ID);
	}

	private void validateAndIntrospectSOWBean(ServiceOrderWizardBean serviceOrderWizardBean, SOWBaseTabDTO sowTabDTO,
			TabNavigationDTO tabNavigationDTO)
	{
		// 1. Get the instance of SOWValidatorFacility
		SOWValidatorFacility validatorFacility = SOWValidatorFacility.getInstance();

		HashMap<String, Object> tabDTOs = serviceOrderWizardBean.getTabDTOs();
		if (tabDTOs == null)
		{
			tabDTOs = new HashMap<String, Object>();
		}
		tabDTOs.put(tabNavigationDTO.getComingFromTab(), sowTabDTO);

		// 2. Call validate and this will validate and populate the errors
		validatorFacility.validate(sowTabDTO, tabNavigationDTO.getSowMode(), tabDTOs);

		// 5. Call StateValidator to validate/evaluate the state of
		// ServiceOrderWizardBean
		SOWTabstateManagerFacility stateManagerFacility = SOWTabstateManagerFacility.getInstance();
		stateManagerFacility.populateTabStates(serviceOrderWizardBean, sowTabDTO, tabNavigationDTO);
		//SL-19820
		//String action = (String) getSession().getAttribute("actionType");
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		String action = (String) getSession().getAttribute("actionType_"+soId);
		if (action != null && action.equals("edit"))
		{
			stateManagerFacility.populateTabStates(serviceOrderWizardBean, tabDTOs, tabNavigationDTO.getSowMode());
		}

	}

	public void setFlagOnServiceOrder(String soId, Integer flagValue,
                                      ISOWizardFetchDelegate soWizardFetchDelegate, SecurityContext securityContext) {
		// Check if so id is not null
		if (soId != null)
		{
			//check if the flag value is same as the last time than ignore it
			Integer sessionFlagValue = (Integer) getSession().getAttribute("Service_order_edit_mode_flag:" + soId);
			if (sessionFlagValue == null || !sessionFlagValue.equals(flagValue)){
				logger.debug("Invoking setLockEditMode on soId: " + soId);
				soWizardFetchDelegate.setLockEditMode(soId, flagValue, securityContext);
			}
			//save the value in the session
			getSession().setAttribute("Service_order_edit_mode_flag:" + soId, flagValue);
		}
	}

	private ArrayList<Integer> getResourceIds()
	{
		SOWProvidersTabDTO sowProviderTabDTO = (SOWProvidersTabDTO) getTabDTO(OrderConstants.SOW_PROVIDERS_TAB);
		ArrayList<Integer> routedResourcesIds = null;
		if (sowProviderTabDTO != null)
		{
			ArrayList<SOWProviderDTO> providerList = sowProviderTabDTO.getProviders();
			if (providerList != null && providerList.size() > 0)
			{
				routedResourcesIds = new ArrayList<Integer>();
				for (int r = 0; r < providerList.size(); r++)
				{
					SOWProviderDTO providerDTO = (SOWProviderDTO) providerList.get(r);
					if (providerDTO != null)
					{
						Integer i = providerDTO.getResourceId();
						routedResourcesIds.add(i);
					}
				}
			}
		}
		return routedResourcesIds;
	}

	public void setStartNavigation(String key)
	{
		if (getTheSOWBean() != null && getTheSOWBean().getStartPoint() != null && getTheSOWBean().getStartPoint().length() > 0)
		{
			// skip setting start point
		}
		else
		{
			getTheSOWBean().setStartPoint(key);
		}
	}


	private int getNumRoutedProviders(HashMap<String, Object> tabDTOs) {
		int numSelectedProviders = 0;
		SOWProvidersTabDTO providerTab = (SOWProvidersTabDTO) tabDTOs.get(OrderConstants.SOW_PROVIDERS_TAB);
		ArrayList<SOWProviderDTO> al = providerTab.getProviders();
		if (al != null)
		{
			for (int i = 0; i < al.size(); i++)
			{
				SOWProviderDTO p = (SOWProviderDTO) al.get(i);
				if (p != null && p.getIsChecked()) {
						numSelectedProviders++;
				}
			}
		}
		return numSelectedProviders;
	}

	protected HttpSession getSession()
	{
		return ServletActionContext.getRequest().getSession();
	}
	protected boolean isGroupOrder(){
		String groupId = (String)getSession().getAttribute(OrderConstants.GROUP_ID);						
		if(StringUtils.isNotBlank(groupId)){
			return true;
		}else{
			return false;
		}
	}
	
	protected Boolean validateMaxLimitPerTransaction(HashMap<String, Object> tabDTOs, SOWBaseTabDTO sowTabDTO, SOWTabstateManagerFacility sowTabstateManagerFacility, TabNavigationDTO tabNavigationDTO) throws BusinessServiceException{
		Boolean gotoReviewtab = null;
		ServiceOrderDTO serviceObj = (ServiceOrderDTO) sowTabDTO;
		ServiceOrdersCriteria commonCriteria = (ServiceOrdersCriteria)getSession().getAttribute(OrderConstants.SERVICE_ORDER_CRITERIA_KEY);
		if(commonCriteria == null)
			return false;
		
		SecurityContext securityContext = commonCriteria.getSecurityContext();
		SOWPricingTabDTO tmpsowPricingTabDTO =(SOWPricingTabDTO) tabDTOs.get(OrderConstants.SOW_PRICING_TAB);
		String postingFee  = serviceObj.getAccessFee() != null ? serviceObj.getAccessFee().substring(1) : null; 
		Double accessFee  =  postingFee != null ?  Double.parseDouble(postingFee) : 0.00;
		double totalTransAmt = 0.00;							
		Double newLabor  =  0.00 ;					
		Double newParts  = 0.00;
		boolean isPriceChanged = false;
		
		if(isGroupOrder()){
			newLabor  =  tmpsowPricingTabDTO.getOgLaborSpendLimit();					
			newParts  = tmpsowPricingTabDTO.getOgPartsSpendLimit();			
			if (null == newLabor) {
				return false;
			}					
			totalTransAmt += newLabor.doubleValue()+ newParts.doubleValue()+tmpsowPricingTabDTO.getGroupTotalPermits();
		}else {
			newLabor  =  serviceObj.getLaborSpendLimit() ;					
			newParts  = serviceObj.getPartsSpendLimit()!=null? serviceObj.getPartsSpendLimit():0.00;
			if (null == newLabor) {
				return false;
			}					
			totalTransAmt +=newLabor.doubleValue()+ newParts.doubleValue();
		}
		tabDTOs.put("newLaborPrice", String.valueOf(newLabor));
		tabDTOs.put("newPartsPrice", String.valueOf(newParts));
		
		if(OrderConstants.POSTED.equals(serviceObj.getStatusString())){						
			Double oldLabor= 0.00;
			Double oldParts= 0.00;					
									
			if(isGroupOrder()){
				oldLabor = tmpsowPricingTabDTO.getOgCurrentLaborSpendLimit(); //.getOgLaborSpendLimit();
				oldParts = tmpsowPricingTabDTO.getOgCurrentPartsSpendLimit(); // .getOgPartsSpendLimit();
				totalTransAmt =  MoneyUtil.subtract(totalTransAmt ,(oldLabor.doubleValue()+oldParts.doubleValue()));
				
				
			}else{
				oldLabor = tmpsowPricingTabDTO.getInitialLaborSpendLimit()!=null?tmpsowPricingTabDTO.getInitialLaborSpendLimit():0D ;
				oldParts = tmpsowPricingTabDTO.getInitialPartsSpendLimit()!=null?tmpsowPricingTabDTO.getInitialPartsSpendLimit():0D ;
				totalTransAmt =  MoneyUtil.subtract(totalTransAmt ,(oldLabor.doubleValue()+oldParts.doubleValue()));
			}
			tabDTOs.put("oldLaborPrice", String.valueOf(oldLabor));
			tabDTOs.put("oldPartsPrice", String.valueOf(oldParts));
			
		}else{			
			totalTransAmt += accessFee.doubleValue();
		}
		
		if (isOrderPriceAboveMaxSpendLimit(accessFee, totalTransAmt,securityContext)) {	
			//SL-19820
			//sowTabstateManagerFacility.setTabStateForReviewTab(getSOWBean(), OrderConstants.SOW_REVIEW_TAB_FAILED, tabNavigationDTO);
			sowTabstateManagerFacility.setTabStateForReviewTab(getSOWBean((String)getAttribute(OrderConstants.SO_ID)), OrderConstants.SOW_REVIEW_TAB_FAILED, tabNavigationDTO);
			addReviewTabError("", OrderConstants.BUYER_UPFUND_LIMIT_PER_TRANSACTION_ERROR, OrderConstants.SOW_TAB_ERROR);
			gotoReviewtab = true;			
		}
		if(totalTransAmt > 0) {
			isPriceChanged = true;
		}
		tabDTOs.put("isPriceChanged", isPriceChanged);
		return gotoReviewtab;
	}
	
	//SL-19820
	private void setAttribute(String name, Object obj) {
		ServletActionContext.getRequest().setAttribute(name, obj);
	}

	private Object getAttribute(String name) {
		return ServletActionContext.getRequest().getAttribute(name);
	}
	
}
