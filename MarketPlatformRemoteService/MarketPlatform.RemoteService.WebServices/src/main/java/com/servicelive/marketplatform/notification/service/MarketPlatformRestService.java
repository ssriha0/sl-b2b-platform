package com.servicelive.marketplatform.notification.service;

import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.servicelive.common.rest.RemoteServiceStatus;
import com.servicelive.marketplatform.common.exception.MarketPlatformException;
import com.servicelive.marketplatform.common.vo.BuyerCallbackEventVO;
import com.servicelive.marketplatform.common.vo.CondRoutingRuleVO;
import com.servicelive.marketplatform.common.vo.FeeType;
import com.servicelive.marketplatform.common.vo.ItemsForCondAutoRouteRepriceVO;
import com.servicelive.marketplatform.common.vo.MarketPlatformServiceRequest;
import com.servicelive.marketplatform.common.vo.MarketPlatformServiceResponse;
import com.servicelive.marketplatform.common.vo.ProviderFirmInfoVO;
import com.servicelive.marketplatform.common.vo.ProviderListCriteriaForAutoRoutingVO;
import com.servicelive.marketplatform.common.vo.ServiceOrderBuyerDocumentsVO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformCommonLookupBO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformDocumentServiceBO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformNotificationBO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformPromoBO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformProviderBO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformRoutingRulesBO;

@Path("/")
public class MarketPlatformRestService {
    private IMarketPlatformProviderBO marketPlatformProviderBO;
    private IMarketPlatformBuyerBO marketPlatformBuyerBO;
    private IMarketPlatformNotificationBO marketPlatformNotificationBO;
    private IMarketPlatformRoutingRulesBO marketPlatformRoutingRulesBO;
    private IMarketPlatformCommonLookupBO marketPlatformCommonLookupBO;
    private IMarketPlatformPromoBO marketPlatformPromoBO;
    private IMarketPlatformDocumentServiceBO marketPlatformDocumentServiceBO;
    private Logger logger = Logger.getLogger(MarketPlatformRestService.class);
    @GET
    @Path("/servicestatus")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public RemoteServiceStatus isServiceActive() {
        RemoteServiceStatus serviceStatus = new RemoteServiceStatus();
        serviceStatus.setActive(true);
        return serviceStatus;
    }

    @GET
    @Path("/notification/contacts/providers/{providerRsrcId}")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse retrieveProviderRsrcContactInfoViaRest(@PathParam("providerRsrcId") Long providerRsrcId) {
        if (providerRsrcId == null) return null;

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformProviderBO.retrieveProviderResourceContactInfo(providerRsrcId));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/notification/contacts/providers/primary/{providerId}")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse retrieveProviderPrimaryRsrcContactInfoViaRest(@PathParam("providerId") Long providerId) {
        if (providerId == null) return null;

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformProviderBO.retrieveProviderPrimaryResourceContactInfo(providerId));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/providers/skills/list/")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse retrieveAllAvailableProviderSkills() {
        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformProviderBO.retrieveAllAvailableProviderSkills());
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/providers/skillCategory/{categoryId}")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse retrieveSkillCategoryNodeViaRest(@PathParam("categoryId") Long categoryId) {
        if (categoryId == null) return null;

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformProviderBO.getSkillNodeById(categoryId));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @POST
    @Path("/notification/contacts/providers/list")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@SuppressWarnings("unchecked")
    public MarketPlatformServiceResponse retrieveProviderContactInfoListViaRest(MarketPlatformServiceRequest serviceRequest) {
        if (serviceRequest == null) return null;
        if (serviceRequest.getRequestObj() == null) return null;
        List<Long> providerIdList = (List<Long>)serviceRequest.getRequestObj();

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformProviderBO.retrieveProviderResourceContactInfoList(providerIdList));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @POST
    @Path("/notification/contacts/providers/admin/list")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@SuppressWarnings("unchecked")
    public MarketPlatformServiceResponse retrieveProviderAdminContactInfoListViaRest(MarketPlatformServiceRequest serviceRequest) {
        if (serviceRequest == null) return null;
        if (serviceRequest.getRequestObj() == null) return null;
        List<Long> providerFirmIdList = (List<Long>)serviceRequest.getRequestObj();

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformProviderBO.retrieveProviderAdminContactInfoList(providerFirmIdList));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/notification/contacts/buyers/{buyerId}")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse retrieveBuyerContactInfoViaRest(@PathParam("buyerId") Long buyerId) {
        if (buyerId == null) return null;

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformBuyerBO.retrieveBuyerContactInfo(buyerId));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
            	logger.info("error in retrieveBuyerContactInfoViaRest: " + error.getStackTrace());
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @POST
    @Path("/notification/contacts/buyers/list")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@SuppressWarnings("unchecked")
    public MarketPlatformServiceResponse retrieveBuyerContactInfoListViaRest(MarketPlatformServiceRequest serviceRequest) {
        if (serviceRequest == null) return null;
        if (serviceRequest.getRequestObj() == null) return null;
        List<Long> buyerIdList = (List<Long>)serviceRequest.getRequestObj();

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformBuyerBO.retrieveBuyerContactInfoList(buyerIdList));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }


    @GET
    @Path("/buyers/{buyerId}/resources/contact/{contactId}")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse findBuyerResourceIdUsingContactId(@PathParam("buyerId") Long buyerId, @PathParam("contactId") Integer contactId) {
        if (buyerId == null || contactId == null) return null;

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformBuyerBO.findBuyerResourceIdUsingContactId(buyerId, contactId));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/notification/contacts/buyers/resources/{buyerRsrcId}")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse retrieveBuyerResourceContactInfoViaRest(@PathParam("buyerRsrcId") Long buyerRsrcId) {
        if (buyerRsrcId == null) return null;

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformBuyerBO.retrieveBuyerResourceContactInfo(buyerRsrcId));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @POST
    @Path("/notification/contacts/buyers/resources/list")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@SuppressWarnings("unchecked")
    public MarketPlatformServiceResponse retrieveBuyerResourceContactInfoListViaRest(MarketPlatformServiceRequest serviceRequest) {
        if (serviceRequest == null) return null;
        if (serviceRequest.getRequestObj() == null) return null;
        List<Long> buyerIdList = (List<Long>)serviceRequest.getRequestObj();

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformBuyerBO.retrieveBuyerResourceContactInfoList(buyerIdList));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/notification/templates/{templateId}")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse retrieveNotificationTemplateViaRest(@PathParam("templateId") Long templateId) {
        if (templateId == null) return null;

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformNotificationBO.retrieveNotificationTemplate(templateId));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @POST
    @Path("/providers/autorouting/list")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse findProvidersForAutoRouting(MarketPlatformServiceRequest serviceRequest) {
        if (serviceRequest == null) return null;
        if (serviceRequest.getRequestObj() == null) return null;

        ProviderListCriteriaForAutoRoutingVO providerListCriteria = (ProviderListCriteriaForAutoRoutingVO)serviceRequest.getRequestObj();
        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformProviderBO.findProvidersForAutoRouting(providerListCriteria));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @POST
    @Path("/providers/autoacceptance/list")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse getProviderForAutoAcceptance(MarketPlatformServiceRequest serviceRequest) {
        if (serviceRequest == null) return null;
        if (serviceRequest.getRequestObj() == null) return null;

        ProviderFirmInfoVO pf = (ProviderFirmInfoVO)serviceRequest.getRequestObj();
        Integer eligibleFirmId=pf.getProviderFirmId();
        Integer spnId=pf.getSpnId();
        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformProviderBO.getProviderForAutoAcceptance(eligibleFirmId,spnId));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }
    
    
    @GET
    @Path("/buyers/{buyerId}")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse retrieveBuyerViaRest(@PathParam("buyerId") Long buyerId) {
        if (buyerId == null) return null;

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformBuyerBO.retrieveBuyer(buyerId));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
            	logger.info("error in retrieveBuyerContactInfoViaRest: " + error.getStackTrace());
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/buyers/resource/{buyerResourceId}")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse retrieveBuyerResourceViaRest(@PathParam("buyerResourceId") Long buyerResourceId) {
        if (buyerResourceId == null) return null;

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformBuyerBO.getBuyerResource(buyerResourceId));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/buyers/features/list")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse retrieveAllSimpleBuyerFeaturesViaRest() {
        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformBuyerBO.retrieveAllSimpleBuyerFeatures());
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/buyers/holdTime/list")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse retrieveAllBuyerHoldTimeViaRest() {
        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformBuyerBO.retrieveAllBuyerHoldTime());
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/buyers/{buyerId}/tripCharge/{skillCategoryId}")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse retrieveTripChargeViaRest(@PathParam("buyerId") Long buyerId, @PathParam("skillCategoryId") Integer skillCategoryId) {
        if (buyerId == null) return null;

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformBuyerBO.getTripChargeByBuyerAndSkillCategory(buyerId, skillCategoryId));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @POST
    @Path("/routingrules/conditional/getruleid")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse getConditionalRoutingRuleId(MarketPlatformServiceRequest serviceRequest) {
    	if (serviceRequest == null) return null;
        if (serviceRequest.getRequestObj() == null) return null;

        CondRoutingRuleVO condRoutingRuleVO = (CondRoutingRuleVO)serviceRequest.getRequestObj();
       
        
        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformRoutingRulesBO.getConditionalRoutingRuleId(condRoutingRuleVO));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @POST
    @Path("/routingrules/conditional/reprice")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse repriceCondAutoRouteItems(MarketPlatformServiceRequest serviceRequest) {
        if (serviceRequest == null) return null;
        if (serviceRequest.getRequestObj() == null) return null;

        ItemsForCondAutoRouteRepriceVO itemsForCondAutoRouteReprice = (ItemsForCondAutoRouteRepriceVO)serviceRequest.getRequestObj();
        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformRoutingRulesBO.repriceItems(itemsForCondAutoRouteReprice));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/spn/{spnId}/tier/{tierId}")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse getTierRoutingInfo(@PathParam("spnId") Integer spnId, @PathParam("tierId") Integer tierId) {
        if (spnId == null) return null;

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformRoutingRulesBO.retrieveTierReleaseInfo(spnId, tierId));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/spn/tierreleaseinfo/list/")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse retrieveAllAvailableTierReleaseInfo() {
        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformRoutingRulesBO.retrieveAllAvailableTierReleaseInfo());
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/contacts/{contactId}")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse lookupContactInfo(@PathParam("contactId") Long contactId) {
        if (contactId == null) return null;

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformCommonLookupBO.lookupContactInfo(contactId.intValue()));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/timezoneidbyzip/{zip}")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse lookupContactInfo(@PathParam("zip") String zip) {
        if (zip == null) return null;

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformCommonLookupBO.lookupTimeZoneForZip(zip));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/buyerreftypes/buyer/{buyerId}")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse getBuyerReferenceTypes(@PathParam("buyerId") Long buyerId) {
        if (buyerId == null) return null;

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformBuyerBO.getBuyerReferenceTypes(buyerId));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/buyers/{buyerId}/documentidbytitle/{title}")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse retrieveBuyerDocumentIdByTitle(@PathParam("buyerId") Long buyerId, @PathParam("title") String title) {
        if (buyerId == null) return null;

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformBuyerBO.retrieveBuyerDocumentIdByTitle(buyerId, title));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/promotion/serviceorder/{serviceOrderId}/buyerId/{buyerId}")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse setPromotionAssociations(@PathParam("serviceOrderId") String serviceOrderId, @PathParam("buyerId") Long buyerId) {
        if (serviceOrderId == null || buyerId == null) return null;

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            marketPlatformPromoBO.applySOPromotions(serviceOrderId, buyerId);
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/promotion/serviceorder/{serviceOrderId}/buyerId/{buyerId}/feeType/{feeType}")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse getPromotionFeeValue(@PathParam("serviceOrderId") String serviceOrderId, @PathParam("buyerId") Long buyerId, @PathParam("feeType") FeeType feeType) {
        if (serviceOrderId == null || buyerId == null) return null;

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformPromoBO.getPromoFee(serviceOrderId, buyerId, feeType));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/document/{title}/buyer/{buyerId}")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse getDocumentIdForBuyer(@PathParam("title") String title, @PathParam("buyerId") Long buyerId) {
        if (title == null || buyerId == null) return null;

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformDocumentServiceBO.retrieveBuyerDocumentIdByTitleAndOwnerId(title, buyerId.intValue()));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/applicationProperties/")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse getApplicationProperties() {
        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformCommonLookupBO.getApplicationProperties());
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }
    
    @GET
    @Path("/applicationFlags/")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse getApplicationFlags() {
        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformCommonLookupBO.getApplicationFlags());
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }

    @GET
    @Path("/outBoundStatusMessages/")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MarketPlatformServiceResponse getOutBoundStatusMessages() {
        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformCommonLookupBO.getOutBoundStatusMessages());
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }
    
    @GET
    @Path("/buyerCallbackEvent/buyerId/{buyerId}/actionId/{actionId}")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
   public MarketPlatformServiceResponse fetchBuyerCallbackEventViaRest(@PathParam("buyerId") String buyerId,@PathParam("actionId") String actionId) {
        if (buyerId == null) return null;

        MarketPlatformServiceResponse serviceResponse = new MarketPlatformServiceResponse();
        try {
            serviceResponse.setResponseObj(marketPlatformBuyerBO.fetchBuyerCallbackEvent(buyerId, actionId));
        } catch (MarketPlatformException mpe) {
            for (Throwable error : mpe.getErrors()) {
                serviceResponse.addError(error.getClass().getName() + " : " + error.getMessage());
            }
        }

        return serviceResponse;
    }
    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////
    public void setMarketPlatformProviderBO(IMarketPlatformProviderBO marketPlatformProviderBO) {
        this.marketPlatformProviderBO = marketPlatformProviderBO;
    }

    public void setMarketPlatformBuyerBO(IMarketPlatformBuyerBO marketPlatformBuyerBO) {
        this.marketPlatformBuyerBO = marketPlatformBuyerBO;
    }

    public void setMarketPlatformNotificationBO(IMarketPlatformNotificationBO marketPlatformNotificationBO) {
        this.marketPlatformNotificationBO = marketPlatformNotificationBO;
    }

    public void setMarketPlatformRoutingRulesBO(IMarketPlatformRoutingRulesBO marketPlatformRoutingRulesBO) {
        this.marketPlatformRoutingRulesBO = marketPlatformRoutingRulesBO;
    }

    public void setMarketPlatformCommonLookupBO(IMarketPlatformCommonLookupBO marketPlatformCommonLookupBO) {
        this.marketPlatformCommonLookupBO = marketPlatformCommonLookupBO;
    }

	public void setMarketPlatformPromoBO(IMarketPlatformPromoBO marketPlatformPromoBO) {
		this.marketPlatformPromoBO = marketPlatformPromoBO;
	}

	public void setMarketPlatformDocumentServiceBO(
			IMarketPlatformDocumentServiceBO marketPlatformDocumentServiceBO) {
		this.marketPlatformDocumentServiceBO = marketPlatformDocumentServiceBO;
	}
}
