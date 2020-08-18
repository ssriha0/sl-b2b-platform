package com.newco.marketplace.api.mobile.services.v3_0;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.rejectServiceOrder.MobileRejectSORequest;
import com.newco.marketplace.api.mobile.beans.rejectServiceOrder.MobileRejectSOResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v2_0.OFMapper;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.vo.mobile.v2_0.MobileSORejectVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;



/**
 * This class would act as a Servicer class for Provider Reject Service Order
 * 
 * @author Infosys
 * @version 1.0
 * @Date 2015/04/16
 */
@APIResponseClass(MobileRejectSOResponse.class)
@APIRequestClass(MobileRejectSORequest.class)
public class MobileSORejectService extends BaseService {

	private static final Logger LOGGER = Logger.getLogger(MobileSORejectService.class);
	private IMobileGenericBO mobileGenericBO;
	private MobileGenericMapper mobileGenericMapper;
	private MobileGenericValidator mobileGenericValidator;

	private OFHelper ofHelper;
	private IServiceOrderBO serviceOrderBo;
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		MobileRejectSORequest request;
		MobileRejectSOResponse rejectResponse = null;
		MobileSORejectVO rejectRequestVO=null;
		ProcessResponse processResponse = null;
		String soId = "";
		Integer providerResId= null;
		SecurityContext securityContext=null;
		Map<String, String> requestMap = null;
		String groupIndParam = StringUtils.EMPTY;
		String groupId = StringUtils.EMPTY;
		Integer roleId =null;

		try{
			request  = (MobileRejectSORequest) apiVO.getRequestFromPostPut();
			soId = (String)apiVO.getProperty(APIRequestVO.SOID);
			providerResId= apiVO.getProviderResourceId();
			roleId = apiVO.getRoleId();
			securityContext = getSecurityContextForVendor(providerResId);
			if(null != securityContext){
				//The following code to get the group SO indicator from request
				requestMap = (Map<String, String>) apiVO.getProperty(PublicMobileAPIConstant.REQUEST_MAP);
				if(null != requestMap && requestMap.containsKey(PublicMobileAPIConstant.GROUP_IND_PARAM)){
					groupIndParam = requestMap.get(PublicMobileAPIConstant.GROUP_IND_PARAM);
				}
				if (StringUtils.isNotBlank(groupIndParam) && PublicMobileAPIConstant.GROUPED_SO_IND.equalsIgnoreCase(groupIndParam)) {
					groupId = soId;
				}
				//Mapping the RejectSORequest to VO
				rejectRequestVO=mobileGenericMapper.mapRejectRequestToVO(request,apiVO,groupId);
				// validate the request
				processResponse = mobileGenericValidator.validateRejectRequest(rejectRequestVO);
				//Call the Reject Service Order method of the common BO class
				if (OrderConstants.ONE.equalsIgnoreCase(processResponse.getCode())) {
					processResponse= rejectServiceOrder(rejectRequestVO, securityContext);
					if(null!= processResponse && StringUtils.equals(processResponse.getCode(), ServiceConstants.VALID_RC)){
					     rejectResponse = mobileGenericMapper.createRejectSuccessResponse(processResponse);
					}else{
						rejectResponse= mobileGenericMapper.createRejectErrorResponse(processResponse);
					}
				}
				else{
					rejectResponse= mobileGenericMapper.createRejectErrorResponse(processResponse);
				}
				
			}
		}catch (BusinessServiceException ex) {
			LOGGER.error("MobileSORejectService-->execute()--> Exception-->", ex);
			Results results = Results.getError(
					ResultsCode.REJECT_SO_FAILED.getMessage(),
						ResultsCode.REJECT_SO_FAILED.getCode());
				rejectResponse = new MobileRejectSOResponse();
			rejectResponse.setResults(results);
		}
		catch (Exception e) {
			LOGGER.error("MobileSORejectService-->execute()--> Exception-->", e);
			Results results = Results.getError(
					ResultsCode.REJECT_SO_FAILED.getMessage(),
						ResultsCode.REJECT_SO_FAILED.getCode());
				rejectResponse = new MobileRejectSOResponse();
			rejectResponse.setResults(results);
		}
		return rejectResponse;
		
	}
	
	/**
	 * Method for rejecting service orders for providers
	 * @return ProcessResponse
	 * **/
	private ProcessResponse rejectServiceOrder(MobileSORejectVO requestVO, SecurityContext securityContext ) throws BusinessServiceException{

		OrderFulfillmentRequest ofRequest = null;
		OrderFulfillmentResponse ofResponse = null;
		try{
			String groupId=requestVO.getGroupId();
			String soId=requestVO.getSoId();

			//If process response code is 1 proceed. Else throw error response
				ofRequest = createOrderFulfillmentRejectSORequest(securityContext, requestVO);
				//groupId = so.getGroupId();
				//If Group order
				if (StringUtils.isNotBlank(groupId)) {
/*					if (ofHelper.isNewGroup(groupId)){
*/						ofResponse = ofHelper.runOrderFulfillmentGroupProcess(groupId, SignalType.REJECT_GROUP,ofRequest);
						return OFMapper.mapProcessResponse(ofResponse);
					/*}else{
						processResponse = getOrderGroupBo().rejectGroupedOrder(checkedResourceID.get(0), groupId, reasonId,
								OrderConstants.PROVIDER_RESP_REJECTED, securityContext.getVendBuyerResId().toString(), requestVO.getReasonDesc(),securityContext);
						return processResponse;
					}*/
				}else /*if(ofHelper.isNewSo(soId))*/{
					boolean isSOInEditMode = isSOInEditMode(soId);						
					ofResponse = ofHelper.runOrderFulfillmentProcess(soId, SignalType.REJECT_ORDER, ofRequest);
					return OFMapper.mapProcessResponse(ofResponse, isSOInEditMode );
				}/*else{
					processResponse = getServiceOrderBo().rejectServiceOrder(checkedResourceID.get(0), soId, reasonId,
							OrderConstants.PROVIDER_RESP_REJECTED, securityContext.getVendBuyerResId().toString(), requestVO.getReasonDesc(),securityContext);
					return processResponse;
				}
*/
			}
		catch(BusinessServiceException e){
			throw e;
		}

	}
	
	/**
	 * Determines if the service order, corresponding to the to provided soID,
	 * is currently in Edit Mode
	 */
	private boolean isSOInEditMode(String soID) throws BusinessServiceException{
		try{
			return getServiceOrderBo().isSOInEditMode(soID);
		}
		catch (BusinessServiceException e) {
			// TODO: handle exception
			LOGGER.error(this.getClass().getName()+ ":" + e.getMessage());
		}
		return false;

	}
	
	/**
	 * Creates OF Request from API Request. Sets only the list of resource for whom
	 * the service order is requested to be rejected.
	 * @return {@link OrderFulfillmentRequest}
	 * **/
	private OrderFulfillmentRequest createOrderFulfillmentRejectSORequest(SecurityContext securityContext, MobileSORejectVO request){
		OrderFulfillmentRequest rejectOfrequest = new OrderFulfillmentRequest();
		Identification idfn = OFMapper.createProviderIdentFromSecCtx(securityContext);
		rejectOfrequest.setIdentification(idfn);
		List<Integer> checkedResourceID=null;
		if(null != request.getResourceList()){
			checkedResourceID = request.getResourceList().getResourceId();
		}else{
			return null;
		}
		List<RoutedProvider> rejectProviders = getRejectedProviders(checkedResourceID, request.getReasonId(), request.getReasonCommentDesc(), OrderConstants.PROVIDER_RESP_REJECTED, securityContext.getVendBuyerResId());
		SOElementCollection collection=new SOElementCollection();
		collection.addAllElements(rejectProviders);				
		rejectOfrequest.setElement(collection);
		return rejectOfrequest;
	}
	
	/**
	 * Creates a List of {@link RoutedProvider} from the list for resource Ids for whom the SO to be
	 * rejected.
	 * @param checkedResourceID : The list of Resource Ids
	 * @param reasonId : Reason code Id
	 * @param responseId : {lu_so_provider_resp_reason}
	 * @param modifiedBy : Resource iD of Provider Firm
	 * @return List<RoutedProvider>
	 * */
	private List<RoutedProvider> getRejectedProviders(List<Integer> checkedResourceID ,Integer reasonId, String venderResonCommentDesc, int responseId, Integer modifiedBy) {
		List<RoutedProvider> rejectedResources=new ArrayList<RoutedProvider>();
		for(int resId: checkedResourceID){
			RoutedProvider routedProvider = new RoutedProvider();
			routedProvider.setProviderResourceId((long)resId);
			routedProvider.setProviderRespReasonId(reasonId);
			routedProvider.setProviderRespComment(venderResonCommentDesc);
			routedProvider.setProviderResponse(ProviderResponseType.fromId(responseId));
			routedProvider.setModifiedBy(String.valueOf(modifiedBy));
			Date now = Calendar.getInstance().getTime();
			routedProvider.setProviderRespDate(now);
			routedProvider.setModifiedDate(now);
			rejectedResources.add(routedProvider);
		}
		return rejectedResources;
	}
	
	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}

	public MobileGenericMapper getMobileGenericMapper() {
		return mobileGenericMapper;
	}

	public void setMobileGenericMapper(MobileGenericMapper mobileGenericMapper) {
		this.mobileGenericMapper = mobileGenericMapper;
	}

	public MobileGenericValidator getMobileGenericValidator() {
		return mobileGenericValidator;
	}

	public void setMobileGenericValidator(
			MobileGenericValidator mobileGenericValidator) {
		this.mobileGenericValidator = mobileGenericValidator;
	}

	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}

	public IServiceOrderBO getServiceOrderBo() {
		return serviceOrderBo;
	}

	public void setServiceOrderBo(IServiceOrderBO serviceOrderBo) {
		this.serviceOrderBo = serviceOrderBo;
	}

	
	
	
	

	
	
	

}