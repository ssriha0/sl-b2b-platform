package com.servicelive.marketplatform.client;

import com.servicelive.common.rest.ServiceLiveBaseRestClient;
import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.buyer.BuyerHoldTime;
import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.buyer.SimpleBuyerFeature;
import com.servicelive.domain.common.ApplicationProperties;
import com.servicelive.domain.common.ApplicationFlags;
import com.servicelive.domain.common.InHomeOutBoundMessages;
import com.servicelive.domain.common.Contact;
import com.servicelive.domain.so.BuyerReferenceType;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.marketplatform.common.exception.MarketPlatformBusinessException;
import com.servicelive.marketplatform.common.vo.*;
import com.servicelive.marketplatform.notification.domain.NotificationTemplate;
import com.servicelive.marketplatform.provider.domain.SkillNode;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformCommonLookupBO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformDocumentServiceBO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformNotificationBO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformPromoBO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformProviderBO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformRoutingRulesBO;
import com.sun.jersey.api.client.WebResource;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class MarketPlatformRemoteServiceClient extends ServiceLiveBaseRestClient implements IMarketPlatformProviderBO, IMarketPlatformBuyerBO, 
				IMarketPlatformRoutingRulesBO, IMarketPlatformCommonLookupBO, IMarketPlatformPromoBO, IMarketPlatformDocumentServiceBO, IMarketPlatformNotificationBO {

    private JmsTemplate jmsTemplate;

    private Logger logger = Logger.getLogger(MarketPlatformRemoteServiceClient.class);

    public Contact retrieveProviderResourceContactInfo(Long providerRsrcId) {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet("/notification/contacts/providers/", providerRsrcId);
        return (Contact)serviceResponse.getResponseObj();
    }

    public Contact retrieveProviderPrimaryResourceContactInfo(Long providerId) {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet("/notification/contacts/providers/primary/", providerId);
        return (Contact)serviceResponse.getResponseObj();
    }

	@SuppressWarnings("unchecked")
    public List<Contact> retrieveProviderResourceContactInfoList(List<Long> providerRsrcIdList) {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaPost("/notification/contacts/providers/list", providerRsrcIdList);
        return (List<Contact>) serviceResponse.getResponseObj();
    }

	@SuppressWarnings("unchecked")
	public List<Contact>  retrieveProviderAdminContactInfoList(List<Long> providerFirmIdList) {
		MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaPost("/notification/contacts/providers/admin/list", providerFirmIdList);
        return (List<Contact>) serviceResponse.getResponseObj();
	}

    public Contact retrieveBuyerContactInfo(Long buyerId) {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet("/notification/contacts/buyers/", buyerId);
        return (Contact)serviceResponse.getResponseObj();
    }

	@SuppressWarnings("unchecked")
    public List<Contact> retrieveBuyerContactInfoList(List<Long> buyerIdList) {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaPost("/notification/contacts/buyers/list", buyerIdList);
        return (List<Contact>) serviceResponse.getResponseObj();
    }

    public Contact retrieveBuyerResourceContactInfo(Long buyerRsrcId) {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet("/notification/contacts/buyers/resources/", buyerRsrcId);
        return (Contact)serviceResponse.getResponseObj();
    }

    public Integer findBuyerResourceIdUsingContactId(Long buyerId, Integer contactId) {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet(String.format("/buyers/%1$d/resources/contact/%2$d", buyerId, contactId));
        return (Integer)serviceResponse.getResponseObj();
    }

	@SuppressWarnings("unchecked")
    public List<Contact> retrieveBuyerResourceContactInfoList(List<Long> buyerRsrcIdList) {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaPost("/notification/contacts/buyers/resources/list", buyerRsrcIdList);
        return (List<Contact>) serviceResponse.getResponseObj();
    }

    public SkillNode getSkillNodeById(Long skillNodeId) {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet("/providers/skillCategory/", skillNodeId);
        return (SkillNode)serviceResponse.getResponseObj();
    }

	@SuppressWarnings("unchecked")
    public List<SkillNode> retrieveAllAvailableProviderSkills() {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet("/providers/skills/list/");
        return (List<SkillNode>)serviceResponse.getResponseObj();
    }

	@SuppressWarnings("unchecked")
    public List<ProviderIdVO> findProvidersForAutoRouting(ProviderListCriteriaForAutoRoutingVO providerListCriteria) {
		logger.info("inside the market rest clent for provider list");
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaPost("/providers/autorouting/list", providerListCriteria);
        logger.info("success with no error");
        return (List<ProviderIdVO>) serviceResponse.getResponseObj();
    }

    public Buyer retrieveBuyer(Long buyerId) {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet(String.format("/buyers/%1$d", buyerId));
        return (Buyer)serviceResponse.getResponseObj();
    }

	public BuyerResource getBuyerResource(Long buyerResourceId) {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet(String.format("/buyers/resource/%1$d", buyerResourceId));
        return (BuyerResource)serviceResponse.getResponseObj();
	}

    @SuppressWarnings("unchecked")
	public List<SimpleBuyerFeature> retrieveAllSimpleBuyerFeatures() {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet("/buyers/features/list");
        return (List<SimpleBuyerFeature>)serviceResponse.getResponseObj();
    }

	@SuppressWarnings("unchecked")
	public List<BuyerHoldTime> retrieveAllBuyerHoldTime() {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet("/buyers/holdTime/list");
        return (List<BuyerHoldTime>)serviceResponse.getResponseObj();
	}

    public BigDecimal getTripChargeByBuyerAndSkillCategory(Long buyerId, Integer skillCategoryId){
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet(String.format("/buyers/%1$d/tripCharge/%2$d", buyerId, skillCategoryId));
        return (BigDecimal)serviceResponse.getResponseObj();
    	
    }

    public CondRoutingRuleVO getConditionalRoutingRuleId(CondRoutingRuleVO condRoutingRuleVO) {   
    	logger.info("Inside getConditionalRoutingRuleId of MarkerPlatformRemoteServiceClient "); 	
    	MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaPost("/routingrules/conditional/getruleid", condRoutingRuleVO);
        logger.info("Inside getConditionalRoutingRuleId method of marketplatforremoteserviceclient");
        
        return (CondRoutingRuleVO) serviceResponse.getResponseObj();
    }

    public ItemsForCondAutoRouteRepriceVO repriceItems(ItemsForCondAutoRouteRepriceVO itemsForReprice) {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaPost("/routingrules/conditional/reprice", itemsForReprice);
        return (ItemsForCondAutoRouteRepriceVO) serviceResponse.getResponseObj();
    }

    public TierReleaseInfoVO retrieveTierReleaseInfo(Integer spnId, Integer currentTier) {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet(String.format("/spn/%1$d/tier/%2$d", spnId, currentTier));
        return (TierReleaseInfoVO)serviceResponse.getResponseObj();
    }

    @SuppressWarnings("unchecked")
    public List<TierReleaseInfoVO> retrieveAllAvailableTierReleaseInfo() {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet("/spn/tierreleaseinfo/list/");
        return (List<TierReleaseInfoVO>)serviceResponse.getResponseObj();
    }

    public Contact lookupContactInfo(Integer contactId) {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet("/contacts/", contactId.longValue());
        return (Contact)serviceResponse.getResponseObj();
    }

    public String lookupTimeZoneForZip(String zip) {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet("/timezoneidbyzip/" + zip);
        return (String)serviceResponse.getResponseObj();
    }

    @SuppressWarnings("unchecked")
	public List<ApplicationProperties> getApplicationProperties() {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet("/applicationProperties/");
        return (List<ApplicationProperties>)serviceResponse.getResponseObj();
    }

    @SuppressWarnings("unchecked")
	public List<ApplicationFlags> getApplicationFlags() {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet("/applicationFlags/");
        return (List<ApplicationFlags>)serviceResponse.getResponseObj();
    }    

    @SuppressWarnings("unchecked")
	public List<InHomeOutBoundMessages> getOutBoundStatusMessages() {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet("/outBoundStatusMessages/");
        return (List<InHomeOutBoundMessages>)serviceResponse.getResponseObj();
    } 
    
    @SuppressWarnings("unchecked")
    public List<BuyerReferenceType> getBuyerReferenceTypes(long buyerId) {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet("/buyerreftypes/buyer/", buyerId);
        return (List<BuyerReferenceType>)serviceResponse.getResponseObj();
    }

    public Integer retrieveBuyerDocumentIdByTitle(Long buyerId, String title) {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet(String.format("/buyers/%1$d/documentidbytitle/%2$s", buyerId, title));
        return (Integer)serviceResponse.getResponseObj();
    }

	@SuppressWarnings("unchecked")
    public List<String> insertServiceOrderBuyerDocuments(String serviceOrderId, Long buyerId, List<String> documentTitles) {
        ServiceOrderBuyerDocumentsVO serviceOrderBuyerDocumentsVO = new ServiceOrderBuyerDocumentsVO(serviceOrderId, buyerId, documentTitles);
        jmsTemplate.send(newMessageCreator(serviceOrderBuyerDocumentsVO, "ServiceOrderBuyerDocumentsVO"));
        return null;
//        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaPost("/buyers/sodocuments/insert", serviceOrderBuyerDocumentsVO);
//        return (List<String>) serviceResponse.getResponseObj();
    }

	public void applySOPromotions(String soId, Long buyerId) {
        getPlatformServiceResponseViaGet(String.format("/promotion/serviceorder/%1$s/buyerId/%2$d", soId, buyerId));
	}

	public double getPromoFee(String soId, Long buyerId, FeeType feeType) {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet(String.format("/promotion/serviceorder/%1$s/buyerId/%2$d/feeType/%3$s", soId, buyerId, feeType.name()));
        return (Double)serviceResponse.getResponseObj();
	}

	public Integer retrieveBuyerDocumentIdByTitleAndOwnerId(String title, Integer buyerId) {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet(String.format("/document/%1$s/buyer/%2$d", title, buyerId));
        return (Integer)serviceResponse.getResponseObj();
	}

    public void queueNotificationTask(final ServiceOrderNotificationTask serviceOrderNotificationTask) {
        logger.debug("creating new notification JMS message " + serviceOrderNotificationTask.toString());
        jmsTemplate.send(newMessageCreator(serviceOrderNotificationTask, "ServiceOrderNotificationTask"));
    }

    public NotificationTemplate retrieveNotificationTemplate(Long templateId) {
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet("/notification/templates/" + templateId);
        return (NotificationTemplate)serviceResponse.getResponseObj();
    }

    private MessageCreator newMessageCreator(final Serializable obj, final String payloadName) {
        logger.debug("creating a new message for payload " + payloadName);
        return new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage objectMessage = session.createObjectMessage();
                objectMessage.setStringProperty("Payload", payloadName);
                objectMessage.setObject(obj);
                return objectMessage;
            }
        };
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // rest service calls
    ////////////////////////////////////////////////////////////////////////////
	private MarketPlatformServiceResponse getPlatformServiceResponseViaGet(String relativeUrl, Long entityId){
		return getPlatformServiceResponseViaGet(relativeUrl + entityId);
	}
	
	private MarketPlatformServiceResponse getPlatformServiceResponseViaGet(String relativeUrl){
        WebResource resource = client.resource(baseUrl);
        MarketPlatformServiceResponse serviceResponse = resource.path(relativeUrl)
                .accept(MediaType.APPLICATION_XML_TYPE).get(MarketPlatformServiceResponse.class);
        logger.info("Response:"+serviceResponse.getResponseObj()+":");
        if (serviceResponse.isError()){
            logger.error(serviceResponse.getErrorMessage());
            throw new MarketPlatformBusinessException(serviceResponse.getErrorMessage());
        }

        return serviceResponse;
    }

    private MarketPlatformServiceResponse getPlatformServiceResponseViaPost(String relativeUrl, Object requestObject) {
        MarketPlatformServiceRequest serviceRequest = new MarketPlatformServiceRequest();
        serviceRequest.setRequestObj(requestObject);       
        WebResource resource = client.resource(baseUrl);
        MarketPlatformServiceResponse serviceResponse = resource.path(relativeUrl)
                .accept(MediaType.APPLICATION_XML_TYPE).post(MarketPlatformServiceResponse.class, serviceRequest);
        logger.info("Error Message"+serviceResponse.getErrorMessage());
        if (serviceResponse.isError()){
            logger.error(serviceResponse.getErrorMessage());
            logger.info("Error Message before throwing exception"+serviceResponse.getErrorMessage());
            throw new MarketPlatformBusinessException(serviceResponse.getErrorMessage());
        }
        return serviceResponse;
    }

    public RoutingRuleHdrVO getProvidersListOfRule(Integer ruleId)
    {
    	return null;
    }
    public void setMethodOfRoutingOfSo(String soId,String methodOfRouting)
    {}
    public void setMethodOfAcceptanceOfSo(String soId,String methodOfAcceptance)
    {}
    public List<ProviderIdVO> getProviderForAutoAcceptance(Integer eligibleFirmId,Integer spnId)
    {
    	logger.info("inside the  getProviderForAutoAcceptance market rest clent for provider list");
    	ProviderFirmInfoVO pfInfoVO=new ProviderFirmInfoVO();
    	pfInfoVO.setProviderFirmId(eligibleFirmId);
    	pfInfoVO.setSpnId(spnId);
        MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaPost("/providers/autoacceptance/list", pfInfoVO);
        logger.info("success with getProviderForAutoAcceptance no error");
        return (List<ProviderIdVO>) serviceResponse.getResponseObj();
    }
    //modified for JUnit development::not invoked from any module in the application
    public SPNHeader fetchSpnDetails(Integer spnId){
    	SPNHeader hdr = new SPNHeader();
    	hdr.setSpnName("Test SPN");
    	hdr.setIsAlias(true);
    	
    	return hdr;
    }
    public List<Integer> findRanks(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers){
    	return null;
    }
    public List<ProviderIdVO> findProvidersForTierRouting(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers,List<Integer> perfScores){
    	return null;
    }
    //SL-20436
    @SuppressWarnings("unchecked")
    public List<Long> getVendorIdsToExclude(String soId){
    	//MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaPost("/firms/autoacceptance/exclude/list", soId);
    	MarketPlatformServiceResponse serviceResponse = getPlatformServiceResponseViaGet(String.format("/serviceorder/%1$s/opportunityemailoff/vendors", soId));
    	logger.info("inside the Rest client getVendorIdsToExclude:"+serviceResponse.getResponseObj()+":");
        return (List<Long>) serviceResponse.getResponseObj();
    }

	
	public BuyerCallbackEventVO fetchBuyerCallbackEvent(String buyerId,
			String actionId) {
			logger.info("inside the  fetchBuyerCallbackEvent market rest clent for buyer event list");
		    	 MarketPlatformServiceResponse serviceResponse =  getPlatformServiceResponseViaGet(String.format("/buyerCallbackEvent/buyerId/%1$s/actionId/%2$s",  buyerId,actionId));
		    	
		        logger.info("success fetchBuyerCallbackEvent");
		        return (BuyerCallbackEventVO) serviceResponse.getResponseObj();
	}
   
    
}
