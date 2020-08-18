
package com.newco.marketplace.api.services.so.v1_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.retrieve.v1_2.RetrieveServiceOrder;
import com.newco.marketplace.api.beans.so.retrieve.v1_2.SOGetResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.SOProviderRetrieveMapper;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;

/**
 * This class would act as a Servicer class for Provider Retrieve Service Order
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOProviderRetrieveService extends BaseService {

	private Logger logger = Logger.getLogger(SOProviderRetrieveService.class);
	private IServiceOrderBO serviceOrderBO;
	private SOProviderRetrieveMapper retrieveMapper;	
	
	/**
	 * This method is for retrieving SO Details for provider.
	 * 
	 * @param fromDate
	 *            String,toDate String
	 * @return String
	 */
	
	public SOProviderRetrieveService() {
		super(null, PublicAPIConstant.providerRetrieveSO.SORESPONSE_PRO_XSD,
				PublicAPIConstant.providerRetrieveSO.SORESPONSE_PRO_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_PRO,
				PublicAPIConstant.providerRetrieveSO.SORESPONSE_SCHEMALOCATION,
				null, SOGetResponse.class);
	}

	/**
	 * Implement your logic here.
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method");
		List<RetrieveServiceOrder> soResponses = new ArrayList<RetrieveServiceOrder>();
		SOGetResponse soGetResponse = new SOGetResponse();
		ServiceOrder serviceOrder = null;
		Map<String, String> requestMap = apiVO.getRequestFromGetDelete();
		boolean fullResponse = true; 
		boolean isAuthorizedToViewSODetails = false;
		String groupIndParam = requestMap.get(PublicAPIConstant.GROUP_IND_PARAM);
		try {
			// Tokenizing the SO IDs
			String soIds = (String) apiVO
					.getProperty(PublicAPIConstant.SO_ID_LIST);
			List<String> soIdList = new ArrayList<String>();
			StringTokenizer soIdStr = new StringTokenizer(soIds, PublicAPIConstant.SO_SEPERATOR);
			while (soIdStr.hasMoreTokens()) {
				String soId = soIdStr.nextToken();
				soIdList.add(soId);
			}
			//Getting the response filter from the request and adding into a list
			String responseFilter = requestMap.get(PublicAPIConstant.retrieveSO.RESPONSE_FILTER);	
			List<String> responseFilters = new ArrayList<String>();
			if (!StringUtils.isBlank(responseFilter)) {
				StringTokenizer strTok = new StringTokenizer(responseFilter,PublicAPIConstant.SEPARATOR, false);
				int noOfProfileIds = strTok.countTokens();
				for (int i = 1; i <= noOfProfileIds; i++) {
					String filter = new String(strTok.nextToken());
					// See if the filter is valid and already present
					if(!responseFilters.contains(filter)
							&& PublicAPIConstant.filters().containsKey(filter)){
							responseFilters.add(filter);
						}
				}
			}else{
				fullResponse = false;
			}
			logger.info("SO ID LIST:::::::" + soIdList.toString());
			logger.info("SO ID LIST SIZE:::::::" + soIdList.size());

			int listSize = soIdList.size();
			for (int index = 0; index < listSize; index++) {
				
				String soId = soIdList.get(index);
				//Checking whether the passed in vendor id is authorized to view the Service Order or not.
				if (StringUtils.isNotBlank(groupIndParam) && PublicAPIConstant.GROUPED_SO_IND.equalsIgnoreCase(groupIndParam)) {
					isAuthorizedToViewSODetails = serviceOrderBO.isAuthorizedToViewGroupSODetls(soId,apiVO.getProviderId());
				}else{
					isAuthorizedToViewSODetails = serviceOrderBO.isAuthorizedToViewSODetls(soId,apiVO.getProviderId());
				}
				
				RetrieveServiceOrder response = new RetrieveServiceOrder();
				//If vendor is authorized to view the Service Order, fetching the Service Order details.
				if(isAuthorizedToViewSODetails){									
					if(null != responseFilters && responseFilters.size()< PublicAPIConstant.filters().size()){
						fullResponse = false;
					}	
					if (StringUtils.isNotBlank(groupIndParam) && PublicAPIConstant.GROUPED_SO_IND.equalsIgnoreCase(groupIndParam)) {
						if(fullResponse){
							/*List<ServiceOrder>	serviceOrders = serviceOrderBO.getServiceOrdersForGroup(soId);
							serviceOrder = getGroupedOrder(serviceOrders);*/
						}else{
							//If response filters are there fetching what is requested.
							//TODO
							//serviceOrder = serviceOrderBO.getServiceOrderGroup(soId,responseFilters);
							serviceOrder = serviceOrderBO.getServiceOrdersForGroup(soId,responseFilters);
							//serviceOrder = getGroupedOrder(serviceOrders);
						}
					}else{
						if(fullResponse){
							serviceOrder = serviceOrderBO.getServiceOrderForAPI(soId);
						}else{
							//If response filters are there fetching what is requested.
							serviceOrder = serviceOrderBO.getServiceOrder(soId,responseFilters);
						}
					}
								
					//Returning error if Service Order is not found.
					if (null == serviceOrder) {
						Results results = Results.getError(
								ResultsCode.SERVICE_ORDER_DOES_NOT_EXIST
										.getMessage()+":"+soIdList.get(index), ResultsCode.FAILURE
										.getCode());
						response.setResults(results);							
					}
					if(null != serviceOrder && null != serviceOrder.getSoNotes()){
						List<ServiceOrderNote> serviceOrderNoteList = serviceOrder.getSoNotes();
						List<ServiceOrderNote> updatedServciceOrderNoteList = new ArrayList<ServiceOrderNote>();
						for (ServiceOrderNote serviceOrderNote : serviceOrderNoteList) {
							if (serviceOrderNote.getPrivateId() == 0)
								updatedServciceOrderNoteList.add(serviceOrderNote);
						}
						serviceOrder.setSoNotes(updatedServciceOrderNoteList);
					}
					response = retrieveMapper.adaptRequest(serviceOrder,responseFilters);
				}else{//if vendor is not authorized to view Service Order details, we are returning the error response.
					Results results = Results.getError(
							ResultsCode.NOT_AUTHORISED_PROVIDER_FIRM_ID
									.getMessage()+":"+soIdList.get(index), ResultsCode.FAILURE
									.getCode());
					response.setResults(results);
				}
				soResponses.add(response);
			}
			//Preparing the XML response.
			soGetResponse = retrieveMapper.mapResponse(soResponses);
			
		}catch (Exception e) {
			logger.info("Exception in execute method of SOProviderRetrieveService-->" + e.getMessage(), e);
		}
		logger.info("Exiting execute method");
		return soGetResponse;
	}

	/**
	 * Create a grouped SO from child orders. Works as a mapper method.
	 * @param serviceOrders : List of Child SOs.
	 * @return {@link ServiceOrder} : Group SO.
	 * */
	private ServiceOrder getGroupedOrder(List<ServiceOrder> serviceOrders) {
		ServiceOrder serviceOrder = new ServiceOrder();
		if(null != serviceOrders && serviceOrders.size() > 0){
			boolean flag = Boolean.FALSE;
			for(ServiceOrder order : serviceOrders){
				//TODO Set price..
				if(flag){
					continue;
				}else{
					serviceOrder.setSoId(order.getGroupId());
					serviceOrder.setServiceLocation(order.getServiceLocation());
					serviceOrder.setServiceContact(order.getServiceContact());
					serviceOrder.setBuyer(order.getBuyer());
					serviceOrder.setAltBuyerResource(order.getBuyerResource());
					serviceOrder.setGroupLaborSpendLimit(order.getGroupLaborSpendLimit());
					serviceOrder.setGroupPartsSpendLimit(order.getGroupPartsSpendLimit());
					serviceOrder.setServiceDate1(order.getServiceDate1());
					serviceOrder.setServiceDate2(order.getServiceDate2());
					serviceOrder.setStatus(order.getStatus());
					serviceOrder.setSowTitle(order.getSowTitle());
					flag = Boolean.TRUE;
				}
			}
		}
		
		return serviceOrder;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public SOProviderRetrieveMapper getRetrieveMapper() {
		return retrieveMapper;
	}

	public void setRetrieveMapper(SOProviderRetrieveMapper retrieveMapper) {
		this.retrieveMapper = retrieveMapper;
	}
	
}