package com.newco.marketplace.api.mobile.services.v3_0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.mobile.beans.getRecievedOrders.RecievedOrdersResponse;
import com.newco.marketplace.api.mobile.beans.vo.RecievedOrdersCriteriaVO;
import com.newco.marketplace.api.mobile.beans.vo.RecievedOrdersVO;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.mobile.constants.MPConstants;
/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/23
 * for fetching the recieved orders
 *
 */
@APIResponseClass(RecievedOrdersResponse.class)
public class RecievedOrdersService extends BaseService {
	private static final Logger LOGGER = Logger.getLogger(RecievedOrdersService.class);

	private MobileGenericValidator mobileGenericValidator;
	
	private IMobileGenericBO mobileGenericBO;

	private MobileGenericMapper mobileGenericMapper;

	/**
	 * This method handle the main logic for fetching eligible providers.
	 * @param apiVO
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		Results results = null;
		RecievedOrdersResponse recievedOrdersResponse = null;
		Integer countOfSO = 0;
		List<RecievedOrdersVO> recievedOrdersList = null;
		
		Map<String, String> requestMap = apiVO.getRequestFromGetDelete();
		int roleId = apiVO.getRoleId();
		String firmId = apiVO.getProviderId();
		int resourceId = apiVO.getProviderResourceId();
		String pageNo = requestMap.get(MPConstants.QUERY_PARAM_PAGE_NO);
		String pageSize = requestMap.get(MPConstants.QUERY_PARAM_PAGE_SIZE);
		String bidOnly = requestMap.get(MPConstants.QUERY_PARAM_BID_ONLY_IND);
		boolean validateCount = false;
		
		try{
			
			RecievedOrdersCriteriaVO criteriaVO = mobileGenericMapper.mapRecievedOrdersCriteria(roleId,firmId,resourceId,pageNo,pageSize,bidOnly);
			//do all the basic validations, which does not require the count
			results = mobileGenericValidator.validateGetRecievedOrders(criteriaVO, countOfSO, validateCount);
			if(null!= results && null != results.getError() && results.getError().size()>0){
				recievedOrdersResponse = new RecievedOrdersResponse();
				recievedOrdersResponse.setResults(results);
			}else{
				countOfSO = mobileGenericBO.getRecievedOrdersCount(criteriaVO);
				validateCount = true;
				//do all the validations, which require the count of SO's
				results = mobileGenericValidator.validateGetRecievedOrders(criteriaVO, countOfSO, validateCount);
				if(null!= results && null != results.getError() && results.getError().size()>0){
					recievedOrdersResponse = new RecievedOrdersResponse();
					recievedOrdersResponse.setResults(results);
				}else{
					recievedOrdersList = mobileGenericBO.getRecievedOrdersList(criteriaVO);
					// for relay estimate details and the orderType
					List<String> serviceOrderIds= new ArrayList<String>();
					Map<String,String> orderTypeMap=new HashMap<String,String>();
					Map<String,List<EstimateVO>> estimateMap=new HashMap<String,List<EstimateVO>>();				
					if(null != recievedOrdersList && recievedOrdersList.size()>0){
						for(RecievedOrdersVO recievedOrdersVO: recievedOrdersList){
							if(null!=recievedOrdersVO && null!=recievedOrdersVO.getBuyerId() && recievedOrdersVO.getBuyerId().intValue()==3333){
								serviceOrderIds.add(recievedOrdersVO.getSoId());
							}
						}
					}
					if(null!=serviceOrderIds && serviceOrderIds.size()>0){
						orderTypeMap=mobileGenericBO.getOrderTypes(serviceOrderIds);
						criteriaVO.setServiceOrderIds(serviceOrderIds);
						estimateMap=mobileGenericBO.getEstimationList(criteriaVO);
					}
					
					recievedOrdersResponse = mobileGenericMapper.mapRecievedOrders(countOfSO,recievedOrdersList,criteriaVO.isBidOnlyInd(),orderTypeMap,estimateMap);
					recievedOrdersResponse.setResults(Results.getSuccess(MPConstants.GET_RECIEVED_ORDERS_SUCCESS_MESSAGE));
				}
			}
		}
		catch (BusinessServiceException ex) {
			LOGGER.error("Exception in getting recieved orders list");	
			recievedOrdersResponse = new RecievedOrdersResponse();
			results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			recievedOrdersResponse= new RecievedOrdersResponse();
			recievedOrdersResponse.setResults(results);
		}
		catch (Exception ex) {
			LOGGER.error("Exception in getting recieved orders list");	
			recievedOrdersResponse = new RecievedOrdersResponse();
			results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			recievedOrdersResponse= new RecievedOrdersResponse();
			recievedOrdersResponse.setResults(results);
		}
		return recievedOrdersResponse;
	}

	public MobileGenericValidator getMobileGenericValidator() {
		return mobileGenericValidator;
	}

	public void setMobileGenericValidator(
			MobileGenericValidator mobileGenericValidator) {
		this.mobileGenericValidator = mobileGenericValidator;
	}

	public MobileGenericMapper getMobileGenericMapper() {
		return mobileGenericMapper;
	}

	public void setMobileGenericMapper(MobileGenericMapper mobileGenericMapper) {
		this.mobileGenericMapper = mobileGenericMapper;
	}


	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}
}