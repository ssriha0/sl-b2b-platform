package com.servicelive.orderfulfillment.orderprep.processor.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.domain.so.BuyerOrderTemplate;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;
import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;

public abstract class AbstractOrderCustomRefEnhancer extends
		AbstractOrderEnhancer {
	protected final Logger logger = Logger.getLogger(getClass());
	protected QuickLookupCollection quickLookupCollection;
	protected IServiceOrderDao serviceOrderDao;

	public void enhanceOrder(ServiceOrder serviceOrder,
			OrderEnhancementContext orderEnhancementContext) {
		IOrderBuyer orderBuyer = orderEnhancementContext.getOrderBuyer();
		List<SOCustomReference> newSoCustomReferenceList = new ArrayList<SOCustomReference>();
		List<Integer> emptyCustRefIdList = null;
		List<SOCustomReference> emptySoCustomReferenceList = new ArrayList<SOCustomReference>();

		for (SOCustomReference soCustomReference : serviceOrder
				.getCustomReferences()) {
			if (soCustomReference.getBuyerRefTypeName().equalsIgnoreCase(
					SOCustomReference.CREF_DIVISION)) {
				serviceOrder.setDivisionCode(soCustomReference
						.getBuyerRefValue());
			} else {
				Integer buyerRefTypeId = orderBuyer
						.getBuyerReferenceTypeId(soCustomReference
								.getBuyerRefTypeName());
				if (buyerRefTypeId == null) {
					String msg = "Buyer reference type ID not available for reference name - "
							+ soCustomReference.getBuyerRefTypeName()
							+ ". BuyerID = " + orderBuyer.getBuyerId();
					logger.warn(msg);
				} else {
					soCustomReference.setBuyerRefTypeId(buyerRefTypeId);
					newSoCustomReferenceList.add(soCustomReference);
				}
			}
			if (soCustomReference.getBuyerRefTypeName().equalsIgnoreCase(
					SOCustomReference.REPEAT_REPAIR)
					&& (null != soCustomReference.getBuyerRefValue() && !(StringUtils
							.isBlank(soCustomReference.getBuyerRefValue())))) {
				serviceOrder.setCustomRefPresent(true);
			}
		}
		// reset the old list to new list to eliminate filtered out custom
		// references
		serviceOrder.setCustomReferences(newSoCustomReferenceList);

		// Check if the order is created using tasks, not SKUs
		boolean taskPresent = false;
		taskPresent = serviceOrder.isTasksPresent();
		if (!(serviceOrder.isCreateWithOutTasks())) {
			// check to if the template name custom reference is required
			if (!taskPresent) {
				addTemplateNameCustRef(serviceOrder, orderBuyer);
			}

			// let implementor of this abstract class add their own custom
			// references
			addCustomReferences(serviceOrder, orderBuyer);
		}
		// validate mandatory custom ref for api created orders.
		logger.debug("Started validating mandatory custom ref for api created orders");
		if (serviceOrder.isCreatedViaAPI()) {
			logger.debug("Calling mandatory  custom Ref validate Method");
			validateMandatoryCustRef(serviceOrder);
			logger.debug("Mandatory Custom Ref set is"
					+ serviceOrder.isMandatoryCustomRefPresent());

		}

		//SL-18825
		//Fetching custom reference with empty value 
		emptyCustRefIdList = serviceOrderDao.getCustRefwithEmptyValue(Integer
				.parseInt(serviceOrder.getBuyerId().toString()));
	
		List<SOCustomReference> customRefList = serviceOrder
				.getCustomReferences();
		
		List<SOCustomReference> tempSoCustomRefList =new ArrayList<SOCustomReference>();
		tempSoCustomRefList=customRefList;
		
		int temp = 0;
		if (null != emptyCustRefIdList && !emptyCustRefIdList.isEmpty()) {
			for (Integer emptyRefId : emptyCustRefIdList) {
				
				if (null != tempSoCustomRefList && !tempSoCustomRefList.isEmpty()) {
					for (SOCustomReference soCustomList : tempSoCustomRefList) {
						
						if ((emptyRefId
								.equals(soCustomList.getBuyerRefTypeId()))) {
							temp = 1;
							break;
						} else {
							temp = 0;
						}
					}
					if (temp == 0) {
						SOCustomReference soCustomRef = new SOCustomReference();
						soCustomRef.setBuyerRefValue("");
						soCustomRef.setBuyerRefTypeId(emptyRefId);
						customRefList.add(soCustomRef);
					}
				} else {
					SOCustomReference soCustomRef = new SOCustomReference();
					soCustomRef.setBuyerRefValue("");
					soCustomRef.setBuyerRefTypeId(emptyRefId);
					customRefList.add(soCustomRef);
				}
			}
		}
		
		 //SL-18825
		//adding custom reference with empty value 
		serviceOrder.setCustomReferences(customRefList);

	}

	protected abstract void addCustomReferences(ServiceOrder serviceOrder,
			IOrderBuyer orderBuyer);

	private void addTemplateNameCustRef(ServiceOrder serviceOrder,
			IOrderBuyer orderBuyer) {
		// check to see if the template name custom reference already present

		/*
		 * If the service order creation is through API, override the custom
		 * reference with the custom reference from SKU.
		 */
		boolean createdFromAPI = false;
		createdFromAPI = serviceOrder.isCreatedViaAPI();
		if (ServiceOrder.extractCustomRefValueFromList(
				SOCustomReference.CREF_TEMPLATE_NAME,
				serviceOrder.getCustomReferences()) != null
				&& !createdFromAPI) {
			return;
		}
		logger.info("Get serviceOrder Primary task and external sku : ");
		logger.info("serviceOrder.getPrimaryTask() : "
				+ serviceOrder.getPrimaryTask());
		logger.info("serviceOrder.getPrimaryTask().getExternalSku()  : "
				+ serviceOrder.getPrimaryTask().getExternalSku());
		logger.info("serviceOrder.getPrimaryTask().getSpecialtyCode(): "
				+ serviceOrder.getPrimaryTask().getSpecialtyCode());
		String sku = serviceOrder.getPrimaryTask().getExternalSku();
		String specialtyCode = serviceOrder.getPrimaryTask().getSpecialtyCode();

		String processId = ServiceOrder.extractCustomRefValueFromList(
				SOCustomReference.CREF_PROCESSID,
				serviceOrder.getCustomReferences());
		String templateName = getTemplateName(sku, specialtyCode, orderBuyer,
				processId);

		if (templateName != null) {
			SOCustomReference templateNameCustRef = new SOCustomReference();
			templateNameCustRef
					.setBuyerRefTypeName(SOCustomReference.CREF_TEMPLATE_NAME);
			templateNameCustRef
					.setBuyerRefTypeId(orderBuyer
							.getBuyerReferenceTypeId(SOCustomReference.CREF_TEMPLATE_NAME));
			templateNameCustRef.setBuyerRefValue(templateName);
			if (createdFromAPI) {
				/* Remove the template if it is already present */
				String existingTemplate = ServiceOrder
						.extractCustomRefValueFromList(
								SOCustomReference.CREF_TEMPLATE_NAME,
								serviceOrder.getCustomReferences());
				if (null != existingTemplate
						&& !StringUtils.isBlank(existingTemplate)) {
					List<SOCustomReference> customRefenceList = serviceOrder
							.getCustomReferences();
					List<SOCustomReference> newCustomRefenceList = new ArrayList<SOCustomReference>();
					if (customRefenceList != null) {
						for (SOCustomReference customRef : customRefenceList) {
							if (null != customRef.getBuyerRefTypeName()
									&& !(customRef.getBuyerRefTypeName().trim()
											.equals(SOCustomReference.CREF_TEMPLATE_NAME))) {
								newCustomRefenceList.add(customRef);
							}
						}
					}
					if (null != newCustomRefenceList) {
						serviceOrder.setCustomReferences(newCustomRefenceList);
					}
				}
			}
			serviceOrder.addCustomReference(templateNameCustRef);
		}
	}

	/*
	 * This will fetch the mandatory custRef for the buyer and check these
	 * custRef are present in serviceOrder(Only for api Creation)
	 */

	private void validateMandatoryCustRef(ServiceOrder serviceOrder) {
		boolean isMandatoryCustRefPresent = true;
		boolean createdFromAPI = false;
		boolean isAllCustomRefPresent = false;
		createdFromAPI = serviceOrder.isCreatedViaAPI();
		if (createdFromAPI) {
			List<Integer> mandatoryCustRefId = null;
			// get the list of buyer_ref_type_id from DB for the buyer
			logger.debug("Buyer id from so is "
					+ Integer.parseInt(serviceOrder.getBuyerId().toString()));
			mandatoryCustRefId = serviceOrderDao.getMandatoryCustRef(Integer
					.parseInt(serviceOrder.getBuyerId().toString()));
			logger.debug("Size of mandatory custom Ref from DB is:"
					+ mandatoryCustRefId.size());
			List<SOCustomReference> customRefList = serviceOrder
					.getCustomReferences();
			logger.debug("Size of custom Ref from SO is :"
					+ customRefList.size());
			// Temp integer List to validate against customRefList
			List<Integer> tempList = new ArrayList<Integer>();
			for (SOCustomReference soCustomReference : customRefList) {
				if (null != soCustomReference
						&& null != soCustomReference.getBuyerRefTypeId()) {
					tempList.add(soCustomReference.getBuyerRefTypeId());
				}
			}
			logger.debug("Size of  temporary List for custom Ref id  from SO :"
					+ tempList.size());
			logger.debug("Value of isMandatoryCustRefPresent before validating two list is"
					+ isMandatoryCustRefPresent);
			isAllCustomRefPresent = tempList.containsAll(mandatoryCustRefId);
			logger.debug("Value of isMandatoryCustRefPresent after validating two list is"
					+ isMandatoryCustRefPresent);
			if (isAllCustomRefPresent) {
				isMandatoryCustRefPresent = true;
				serviceOrder
						.setMandatoryCustomRefPresent(isMandatoryCustRefPresent);
				logger.debug("All mandatory custom ref are present and isMandatoryCustRefPresent is:"
						+ isMandatoryCustRefPresent);
				for (SOCustomReference soCustomReference : customRefList) {
					for (Integer mandatoryRefId : mandatoryCustRefId) {
						logger.debug("mandatory id:" + mandatoryRefId
								+ "ref id from customRef list:"
								+ soCustomReference.getBuyerRefTypeId());
						if (soCustomReference.getBuyerRefTypeId().equals(
								mandatoryRefId)) {
							logger.debug("Mandatory id from templist :"
									+ mandatoryRefId
									+ "Value of customRef from so:"
									+ soCustomReference.getBuyerRefValue());
							if (StringUtils.isBlank(soCustomReference
									.getBuyerRefValue())) {
								isMandatoryCustRefPresent = false;
								logger.debug("Setting isMandatoryCustRefPresent as false as value is"
										+ " empty,the value for "
										+ soCustomReference.getBuyerRefTypeId()
										+ "is empty");
								serviceOrder
										.setMandatoryCustomRefPresent(isMandatoryCustRefPresent);
								break;
							}
						} else {
							continue;
						}
					}

				}

			} else {
				logger.debug("All mandatory custom ref are not present,hence setting isMandatorycustRefPresent as false");
				isMandatoryCustRefPresent = false;
				serviceOrder
						.setMandatoryCustomRefPresent(isMandatoryCustRefPresent);
			}
		} else {
			logger.debug("Not from api flow of creation of so");
			serviceOrder
					.setMandatoryCustomRefPresent(isMandatoryCustRefPresent);
		}
	}

	protected String getTemplateName(String sku, String specialtyCode,
			IOrderBuyer orderBuyer, String processId) {
		// -- SL-13580: Third Party Template Integration
		String thirdPartyProcessIDs = quickLookupCollection
				.getApplicationPropertyLookup().getPropertyValue(
						"ThirdPartyProcessIDs");
		if (StringUtils.isNotBlank(thirdPartyProcessIDs)) {
			List<String> processIdList = Arrays.asList(thirdPartyProcessIDs
					.split(","));
			if (StringUtils.isNotBlank(processId)
					&& processIdList.contains(processId)) {
				specialtyCode += "_" + processId;
			}
		}
		// -- End
		BuyerOrderSku buyerSku = orderBuyer.getBuyerSkuMap().getBuyerSku(
				specialtyCode, sku);
		Long buyerSOTemplateId = buyerSku.getTemplateId();
		BuyerOrderTemplate buyerOrderTemplate = orderBuyer.getTemplateMap()
				.getTemplate(buyerSOTemplateId.intValue());
		return buyerOrderTemplate.getTitle();
	}

	// ///////////////////////////////////////////
	// ///// SETTERS FOR SPRING INJECTION ////////
	// ///////////////////////////////////////////

	public void setQuickLookupCollection(
			QuickLookupCollection quickLookupCollection) {
		this.quickLookupCollection = quickLookupCollection;
	}

	public void setServiceOrderDao(IServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}
}
