package com.newco.marketplace.web.delegatesImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.so.DepositionCodeDTO;
import com.newco.marketplace.api.mobile.beans.sodetails.InvoiceDocumentVO;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.business.businessImpl.so.pdf.SOPDFUtils;
import com.newco.marketplace.business.businessImpl.vibePostAPI.PushNotificationAlertTask;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistory;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.business.iBusiness.onSiteVisit.IOnSiteVisitBO;
import com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO;
import com.newco.marketplace.business.iBusiness.orderGroup.IRouteOrderGroupBO;
import com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO;
import com.newco.marketplace.business.iBusiness.promo.PromoBO;
import com.newco.marketplace.business.iBusiness.provider.IEmailTemplateBO;
import com.newco.marketplace.business.iBusiness.provider.IResourceBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderCloseBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderFeatureSetBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderMonitor;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderUpsellBO;
import com.newco.marketplace.business.iBusiness.survey.ISurveyBO;
import com.newco.marketplace.business.techtalk.ITeclTalkBuyerPortalBO;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestMsgBody;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestOrder;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestOrders;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestReschedInformation;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestRescheduleInfo;
import com.newco.marketplace.buyeroutboundnotification.constatns.BuyerOutBoundConstants;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationJMSService;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationService;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundFailOverVO;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundNotificationVO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.BuyerDocumentTypeVO;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.EmailVO;
import com.newco.marketplace.dto.vo.InitialPriceDetailsVO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.LuProviderRespReasonVO;
import com.newco.marketplace.dto.vo.SOWorkflowControlsVO;
import com.newco.marketplace.dto.vo.TermsAndConditionsVO;
import com.newco.marketplace.dto.vo.ValidationRulesVO;
import com.newco.marketplace.dto.vo.WFMBuyerQueueVO;
import com.newco.marketplace.dto.vo.WFMSOTasksVO;
import com.newco.marketplace.dto.vo.fee.PromoConstants;
import com.newco.marketplace.dto.vo.group.QueueTasksGroupVO;
import com.newco.marketplace.dto.vo.incident.AssociatedIncidentVO;
import com.newco.marketplace.dto.vo.logging.SoAutoCloseDetailVo;
import com.newco.marketplace.dto.vo.logging.SoChangeDetailVo;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.powerbuyer.PBFilterVO;
import com.newco.marketplace.dto.vo.powerbuyer.RequeueSOVO;
import com.newco.marketplace.dto.vo.price.PendingCancelPriceVO;
import com.newco.marketplace.dto.vo.provider.ProviderFirmVO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.AdditionalPaymentVO;
import com.newco.marketplace.dto.vo.serviceorder.Carrier;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.CounterOfferReasonsVO;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.PendingCancelHistoryVO;
import com.newco.marketplace.dto.vo.serviceorder.ProblemResolutionSoVO;
import com.newco.marketplace.dto.vo.serviceorder.ProviderInvoicePartsVO;
import com.newco.marketplace.dto.vo.serviceorder.RevisitNeededInfoVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitResultVO;
import com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitVO;
import com.newco.marketplace.dto.vo.serviceorder.SOPartLaborPriceReasonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStatusVO;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.dto.vo.so.order.ServiceOrderRescheduleVO;
import com.newco.marketplace.dto.vo.survey.SurveyQuesAnsVO;
import com.newco.marketplace.dto.vo.survey.SurveyVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.exception.gis.InsuffcientLocationException;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeRescheduleVO;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.CreditCardConstants;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.tokenize.HSTokenizeServiceCreditCardBO;
import com.newco.marketplace.tokenize.TokenizeResponse;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.util.Cryptography128;
import com.newco.marketplace.util.TimeChangeUtil;
import com.newco.marketplace.util.gis.GISUtil;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.vo.mobile.v2_0.SOTripVO;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.ILookupDelegate;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.AddonServiceRowDTO;
import com.newco.marketplace.web.dto.AddonServicesDTO;
import com.newco.marketplace.web.dto.ConditionalOfferDTO;
import com.newco.marketplace.web.dto.IncreaseSpendLimitDTO;
import com.newco.marketplace.web.dto.ReleaseServiceOrderDTO;
import com.newco.marketplace.web.dto.RescheduleDTO;
import com.newco.marketplace.web.dto.ResponseStatusDTO;
import com.newco.marketplace.web.dto.ResponseStatusTabDTO;
import com.newco.marketplace.web.dto.RevisitNeededInfoDTO;
import com.newco.marketplace.web.dto.SOCancelDTO;
import com.newco.marketplace.web.dto.SOCompleteCloseDTO;
import com.newco.marketplace.web.dto.SOContactDTO;
import com.newco.marketplace.web.dto.SOLoggingDTO;
import com.newco.marketplace.web.dto.SOPartsDTO;
import com.newco.marketplace.web.dto.SOPendingCancelDTO;
import com.newco.marketplace.web.dto.SOTaskDTO;
import com.newco.marketplace.web.dto.SOWSelBuyerRefDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrderNoteDTO;
import com.newco.marketplace.web.dto.SoChangeDetailsDTO;
import com.newco.marketplace.web.dto.WFMBuyerQueueDTO;
import com.newco.marketplace.web.dto.ajax.SOQueueNoteDTO;
import com.newco.marketplace.web.utils.OFUtils;
import com.newco.marketplace.web.utils.ObjectMapper;
import com.newco.marketplace.web.utils.ObjectMapperDetails;
import com.newco.marketplace.web.utils.SOPartsInfoMapper;
import com.newco.marketplace.web.utils.WFMBuyerQueueMapper;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ActionContext;
import com.sears.os.service.ServiceConstants;
import com.servicelive.activitylog.client.IActivityLogHelper;
import com.servicelive.activitylog.domain.ActivityCounts;
import com.servicelive.activitylog.domain.ActivityFilter;
import com.servicelive.activitylog.domain.ActivityLog;
import com.servicelive.activitylog.domain.ActivityStatusType;
import com.servicelive.activitylog.domain.ActivityType;
import com.servicelive.activitylog.domain.ActivityViewStatusAssociationType;
import com.servicelive.activitylog.domain.ActivityViewStatusName;
import com.servicelive.common.CommonConstants;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.SOAdditionalPayment;
import com.servicelive.orderfulfillment.domain.SOAddon;
import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.SOOnSiteVisit;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.type.SOScheduleType;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.Parameter;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import com.thoughtworks.xstream.XStream;

public class SODetailsDelegateImpl implements ISODetailsDelegate,
		OrderConstants, ServiceConstants {
	private static final Logger logger = Logger
			.getLogger(SODetailsDelegateImpl.class.getName());
	
	private static final String RIGHT_PARAN_SYM = ")";
	private static final String LEFT_PARAN_W_SPACE_SYM = " (";
	private static final String MAIN_DISPLAY = "Main";
    private static final String TOKENIZATION_ERROR_CODE="14";
    private static final String TOKENIZATION_ERROR_MSG="ERROR IN TOKENIZATION";
	private IServiceOrderBO serviceOrderBo;
	private IServiceOrderUpsellBO upsellBO;
	private IServiceOrderMonitor soMonitor;
	private IServiceOrderFeatureSetBO serviceOrderFeatureSetBO;
	private ILookupDelegate luDelegate = null;
	private IOnSiteVisitBO timeOnSiteVisitBO;
	private ISurveyBO surveyBO;
	private IOrderGroupBO orderGrpBO;
	private IServiceOrderCloseBO serviceOrderCloseBo;
	private Timestamp conditionalDate1;
	private Timestamp conditionalDate2;
	private Timestamp conditionalExpirationDate;
	private PromoBO promoBO;
	private IRouteOrderGroupBO routeOrderGroupBO;
	private IRelayServiceNotification relayNotificationService;
	private ITeclTalkBuyerPortalBO techTalkByerBO;
	private IPowerBuyerBO powerBuyerBO;
	
	private WFMBuyerQueueMapper wfmBuyerQueueMapper;
	private IBuyerFeatureSetBO buyerFeatureSetBO;
	
	private IEmailTemplateBO emailTemplateBO;
	private IResourceBO resourceBO;
	
	private IActivityLogHelper activityLogHelper;
	private IDocumentBO documentBO;

    private OFHelper ofHelper = new OFHelper();
    
    private Cryptography cryptography;
    
    private IBuyerOutBoundNotificationService  buyerOutBoundNotificationService;
    private IBuyerOutBoundNotificationJMSService buyerOutBoundNotificationJMSService;
    
    private INotificationService notificationService;
    
    private Cryptography128 cryptography128;
    private HSTokenizeServiceCreditCardBO hsTokenServiceCreditCardBo;
    private ServiceOrderDao serviceOrderDao;
	private PushNotificationAlertTask pushNotificationAlertTask;

	public INotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(INotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public IBuyerOutBoundNotificationJMSService getBuyerOutBoundNotificationJMSService() {
		return buyerOutBoundNotificationJMSService;
	}

	public void setBuyerOutBoundNotificationJMSService(
			IBuyerOutBoundNotificationJMSService buyerOutBoundNotificationJMSService) {
		this.buyerOutBoundNotificationJMSService = buyerOutBoundNotificationJMSService;
	}

	public Cryptography getCryptography() {
		return cryptography;
	}

	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}

	public IEmailTemplateBO getEmailTemplateBO()
	{
		return emailTemplateBO;
	}

	public void setEmailTemplateBO(IEmailTemplateBO emailTemplateBO)
	{
		this.emailTemplateBO = emailTemplateBO;
	}
	
	public ISurveyBO getSurveyBO() {
		return surveyBO;
	}

	public void setSurveyBO(ISurveyBO surveyBO) {
		this.surveyBO = surveyBO;
	}
	
	

	public IBuyerOutBoundNotificationService getBuyerOutBoundNotificationService() {
		return buyerOutBoundNotificationService;
	}

	public void setBuyerOutBoundNotificationService(
			IBuyerOutBoundNotificationService buyerOutBoundNotificationService) {
		this.buyerOutBoundNotificationService = buyerOutBoundNotificationService;
	}

	public SurveyVO retrieveQuestions(SurveyVO surveyVO) {

		try {
			return surveyBO.retrieveQuestions(surveyVO,null);
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String saveResponse(SurveyVO surveyVO) {
		SecurityContext securityContext = getSecurityContext();
		try {
			return surveyBO.saveResponse(surveyVO, securityContext,null);
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ProcessResponse serviceOrderAddNote(LoginCredentialVO lcv,
			ServiceOrderNoteDTO soNoteDTO, Integer resourceId)
			throws BusinessServiceException {
		ServiceOrderNote soNote = new ServiceOrderNote();
		ProcessResponse processResponse = null;

		soNote = ObjectMapperDetails.convertServiceOrderNoteDTOtoVO(soNoteDTO);
		soNote.setCreatedDate(new Date(System.currentTimeMillis()));
		soNote.setRoleId(lcv.roleId);
		soNote.setCreatedByName(lcv.getLastName() + ", " + lcv.getFirstName());
		soNote.setModifiedDate(null);
		soNote.setModifiedBy(lcv.getUsername());
		
		//SL-19050
		//Setting read_ind as 1 for buyer so notes
		if(lcv.getRoleId() == 3)
		{
		soNote.setReadInd(1);
		}
		else
		{
			soNote.setReadInd(0);	
		}
		
		// Ugly hack to save the note.
		if(StringUtils.isBlank(soNoteDTO.getSoId())){
			soNote.setSoId(soNoteDTO.getGroupId());
		}else{
			soNote.setSoId(soNoteDTO.getSoId());
		}
		
		
		soNote.setNoteTypeId(soNoteDTO.getNoteTypeId());

		SecurityContext securityContext = getSecurityContext();
        soNote.setEntityId(securityContext.getVendBuyerResId());

		//Begin new OF System{
		if(ofHelper.isNewSo(soNote.getSoId())){
		    OrderFulfillmentResponse ofResponse;
		    OrderFulfillmentRequest request = OFUtils.mapSONote(soNote, soNoteDTO.isEmailTobeSent());
            
		    request.setIdentification(OFUtils.createOFIdentityFromSecurityContext(securityContext));
            ofResponse= ofHelper.runOrderFulfillmentProcess(soNote.getSoId(), SignalType.ADD_NOTE, request);

		    return OFUtils.mapProcessResponse(ofResponse);
		}
		//}End new OF System.
		
		/*
		 * The below things are incorporated to implement the Advice Concepts for sending email
		 * through Cheetah Mail.
		 */
		/*
		 * The below things are incorporated to implement the Advice Concepts
		 * for sending email through Cheetah Mail.
		 */
		if (SOConstants.SUPPORT_NOTE.equals(soNoteDTO.getNoteTypeId())) {
			processResponse = getServiceOrderBo().processSupportAddNote(
					resourceId, lcv.getRoleId(), soNote.getSoId(),
					soNote.getSubject(), soNote.getNote(),
					soNoteDTO.getNoteTypeId(), soNote.getCreatedByName(),
					soNote.getModifiedBy(), soNoteDTO.getEntityId(),
					soNoteDTO.getRadioSelection(), soNoteDTO.isEmailTobeSent(),
					securityContext);
		} else {
			processResponse = getServiceOrderBo().processAddNote(resourceId,
					lcv.getRoleId(), soNote.getSoId(), soNote.getSubject(),
					soNote.getNote(), soNoteDTO.getNoteTypeId(),
					soNote.getCreatedByName(), soNote.getModifiedBy(),
					soNoteDTO.getEntityId(), soNoteDTO.getRadioSelection(),
					soNoteDTO.isEmailTobeSent(),soNoteDTO.isEmptyNoteAllowed(), securityContext);
		}

		return processResponse;
	}

	public List<ServiceOrderNote> serviceOrderGetNotes(
			ServiceOrderNoteDTO soNoteDTO) throws DataServiceException {
		ServiceOrderNote soNote = new ServiceOrderNote();

		soNote = ObjectMapperDetails.convertServiceOrderNoteDTOtoVO(soNoteDTO);

		ProcessResponse pr = getServiceOrderBo().processGetSONotes(soNote);
		if (pr == null)
			return null;
		else {
			@SuppressWarnings("unchecked")
			ArrayList<ServiceOrderNote> soNoteList = (ArrayList<ServiceOrderNote>) pr.getObj();
			return soNoteList;
		}
	}

	/**
	 * Function to get all the notes for SL Administrator.
	 */
	public List<ServiceOrderNote> serviceOrderGetAllNotes(
			ServiceOrderNoteDTO soNoteDTO) throws DataServiceException {
		ServiceOrderNote soNote = new ServiceOrderNote();

		soNote = ObjectMapperDetails.convertServiceOrderNoteDTOtoVO(soNoteDTO);

		ProcessResponse pr = getServiceOrderBo().processGetAllSONotes(soNote);
		if (pr == null)
			return null;
		else {
			@SuppressWarnings("unchecked")
			ArrayList<ServiceOrderNote> soNoteList = (ArrayList<ServiceOrderNote>) pr.getObj();
			return soNoteList;
		}
	}

	/**
	 * Function to get the Deleted notes for SL Administrator
	 */
	public List<ServiceOrderNote> serviceOrderGetDeletedNotes(
			ServiceOrderNoteDTO soNoteDTO) throws DataServiceException {
		ServiceOrderNote soNote = new ServiceOrderNote();

		soNote = ObjectMapperDetails.convertServiceOrderNoteDTOtoVO(soNoteDTO);

		ProcessResponse pr = getServiceOrderBo().processGetDeletedSONotes(
				soNote);
		if (pr == null)
			return null;
		else {
			@SuppressWarnings("unchecked")
			ArrayList<ServiceOrderNote> soNoteList = (ArrayList<ServiceOrderNote>) pr.getObj();
			return soNoteList;
		}
	}

	public TermsAndConditionsVO getAcceptServiceOrderTermsAndCond(
			String acceptTermsandCond) throws BusinessServiceException {
		TermsAndConditionsVO termsAndCond = new TermsAndConditionsVO();
		/*
		 * Calling lookup delegate and putting reject codes in to servletContext
		 * if not already
		 */
		try {
			termsAndCond = luDelegate
					.getTermsConditionsContent(acceptTermsandCond);
		} catch (Exception e) {
			logger
					.error("error in SLBaseAction.getAcceptServiceOrderTermsAndCond");
		}
		return termsAndCond;
	}

	public ArrayList<LuProviderRespReasonVO> getRejectReasons(
			LuProviderRespReasonVO luReasonVO) throws BusinessServiceException {

		/*
		 * Calling lookup delegate and putting reject codes in to servletContext
		 * if not already
		 */
		luReasonVO.setSearchByResponse(SOConstants.PROVIDER_RESP_REJECTED);
		ArrayList<LuProviderRespReasonVO> al = new ArrayList<LuProviderRespReasonVO>();
		al = luDelegate.getLuProviderRespReason(luReasonVO);
		return al;
	}
	public ArrayList<LuProviderRespReasonVO> getReleaseReasonCodes(LuProviderRespReasonVO luReasonVO)throws BusinessServiceException{
		ArrayList<LuProviderRespReasonVO> al = new ArrayList<LuProviderRespReasonVO>();
		al = luDelegate.getLuProviderRespReason(luReasonVO);
		return al;
	}

	public ArrayList<SoChangeDetailsDTO> getSOLogs(String serviceOrderId)
			throws DataServiceException {
		ArrayList<SoChangeDetailsDTO> soChangeDetailsDTOs = new ArrayList<SoChangeDetailsDTO>();
		ArrayList<SoChangeDetailVo> aList = new ArrayList<SoChangeDetailVo>();
		ArrayList<SoChangeDetailVo> aListTimeOnsiteDetail = new ArrayList<SoChangeDetailVo>();
		SOWorkflowControlsVO controlsVO =null;
		try {
			aList = (ArrayList<SoChangeDetailVo>) serviceOrderBo.getSoChangeDetailVoList(serviceOrderId);
			controlsVO = serviceOrderBo.getSoWorkflowControl(serviceOrderId);
			//adding time onsite records to so history
			List<SOOnsiteVisitVO> onsiteRecords=getTimeOnSiteResults(serviceOrderId);
			aListTimeOnsiteDetail=changeToSoChangeDetailVo(onsiteRecords,serviceOrderId);
			if(aListTimeOnsiteDetail!=null)
			aList.addAll(aListTimeOnsiteDetail);
			Collections.sort(aList);
			Collections.reverse(aList);

		} catch (Throwable t) {
			logger.error("Error in calling database", t);
		}
		soChangeDetailsDTOs = ObjectMapper.convertSoChangeDetailVoToDTO(aList,getSecurityContext().getRoleId(),controlsVO,getSecurityContext().getCompanyId());
		return soChangeDetailsDTOs;
	}

	public IServiceOrderBO getServiceOrderBo() {
		return serviceOrderBo;
	}

	public void setServiceOrderBo(IServiceOrderBO serviceOrderBo) {
		this.serviceOrderBo = serviceOrderBo;
	}

	public ResponseStatusTabDTO getResponseStatusDto(String soId,
			Integer wfStateId, Integer buyerId) {
		List<RoutedProvider> routedProviderList = getServiceOrderBo()
				.getAllProviders(soId, buyerId);
		routedProviderList = getServiceOrderBo().getProviderDistanceFromServiceLocation(soId, routedProviderList);

		ResponseStatusTabDTO responseStatusTabDTO = ObjectMapper
			.getResponseStatusTabDTOFromVO(routedProviderList, wfStateId);
		
		//check whether SO is tier routed
		boolean tierRoute = getServiceOrderBo().checkTierRoute(soId);
		if(tierRoute){
			//set the criteria level
			String criteriaLevel = "";
			if(null != routedProviderList){
				for(RoutedProvider routedProv : routedProviderList){
					if(null != routedProv && null != routedProv.getPerfScore()){
						criteriaLevel = "provider";
						break;
					}
					else if(null != routedProv && null != routedProv.getFirmPerfScore()){
						criteriaLevel = "firm";
						break;
					}
				}
			}
			responseStatusTabDTO.setRoutingCriteria(criteriaLevel);
		}
		return responseStatusTabDTO;
	}

	public ResponseStatusTabDTO getResponseStatusDtoForGroup(String groupId,
			Integer wfStateId, Integer buyerId) {

		List<RoutedProvider> routedProviderList = new ArrayList<RoutedProvider>();
		List<RoutedProvider> grpRoutedProvRespList = new ArrayList<RoutedProvider>();
		String priceType = null;

		try {
			List<ServiceOrderSearchResultsVO> soListInGroup = orderGrpBO
					.getServiceOrdersForGroup(groupId);
			if (soListInGroup != null && soListInGroup.size() > 0) {
				String firstSoId = soListInGroup.get(0).getSoId();
				priceType = soListInGroup.get(0).getPriceType();

				routedProviderList = serviceOrderBo.getAllProviders(firstSoId,
						buyerId);
				routedProviderList = getServiceOrderBo().getProviderDistanceFromServiceLocation(firstSoId, routedProviderList);
				List<RoutedProvider> grpRoutedProviderList = orderGrpBO
						.getRoutedProviderResponse(groupId);

				for (RoutedProvider providerResp : routedProviderList) {
					RoutedProvider updatedProcResp = providerResp;
					for (RoutedProvider grpProviderResp : grpRoutedProviderList) {
						if (updatedProcResp.getResourceId().intValue() == grpProviderResp
								.getResourceId().intValue()) {
							updatedProcResp
									.setConditionalSpendLimit(grpProviderResp
											.getConditionalSpendLimit());
							break;
						}
					}
					grpRoutedProvRespList.add(updatedProcResp);
				}

			}

		} catch (Exception e) {
			logger
					.error(
							"error thrown while getting all Providers in SODetails.getResponseStatusDtoForGroup",
							e);

		}

		ResponseStatusTabDTO responseStatusTabDTO = ObjectMapper
				.getResponseStatusTabDTOFromVO(grpRoutedProvRespList, wfStateId);
		if(null != priceType){
			if("TASK_LEVEL".equals(priceType)){
				responseStatusTabDTO.setTaskLevelIsOn(true);
			}
			else{
				responseStatusTabDTO.setTaskLevelIsOn(false);
			}
		}

		return responseStatusTabDTO;
	}

	public ProcessResponse updateMktMakerComments(
			ResponseStatusDTO responseStatusDto) {
		SecurityContext securityContext = getSecurityContext();
		responseStatusDto.setModifyingAdmin(securityContext.getSlAdminUName());

		RoutedProvider routedProvider = ObjectMapper
				.getResponseStatusVOFromResponseStatusDto(responseStatusDto);
		// routedProvider.setResourceId(securityContext.getVendBuyerResId());
		ProcessResponse pr = getServiceOrderBo().updateMktMakerComments(
				routedProvider);

		return pr;

	}

	public List<LookupVO> getCallStatusList() {
		return getServiceOrderBo().getCallStatusList();
	}

	public boolean hasCondOfferPending(String soId, Integer routedResourceId) {
		List<RoutedProvider> routedProviderList = 
			getServiceOrderBo().getRoutedProvidersWithBasicInfo(soId);
		
		if (routedProviderList != null && routedProviderList.size() == 0)
			return false;

		for (RoutedProvider provider : routedProviderList) {
			if (provider.getResourceId() != null
					&& routedResourceId != null
					&& routedResourceId.intValue() == provider.getResourceId()
							.intValue()) {
				if (provider.getProviderRespId() != null
						&& provider.getProviderRespId().intValue() == 2) {
					return true;
				}
			}
		}
		return false;
	}

	private OrderFulfillmentRequest getRequestForAcceptance(Long vendorId, Long resourceId, Integer termAndCond, SecurityContext securityContext,String assignee) throws BusinessServiceException {
        com.servicelive.orderfulfillment.domain.ServiceOrder so = new com.servicelive.orderfulfillment.domain.ServiceOrder();
        Contact vendorContact =null;
        so.setAcceptedProviderId(vendorId);
        so.setSoTermsCondId(termAndCond);	
        so.setProviderSOTermsCondInd(1);
        so.setProviderTermsCondDate(Calendar.getInstance().getTime());
        OrderFulfillmentRequest ofRequest = new OrderFulfillmentRequest();
        if(OrderConstants.ASSIGNMENT_TYPE_FIRM.equalsIgnoreCase(assignee)){
        	so.setAssignmentType(OrderConstants.SO_ASSIGNMENT_TYPE_FIRM);
        	String name = null;
    		try{
        		name = serviceOrderBo.getVendorBusinessName(securityContext.getCompanyId());
        		logger.info("getRequestForAcceptance PVKEY_ACCEPTED_NAME="+name);
        	}catch (BusinessServiceException bse) {
				logger.error("error fetching business name of firm", bse);
			}            		
        	ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_ACCEPTED_NAME,name);
        	ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_SEND_PROVIDER_EMAIL, "false");
        }else{
        	so.setAssignmentType(OrderConstants.SO_ASSIGNMENT_TYPE_PROVIDER);
        	so.setAcceptedProviderResourceId(resourceId);
        	 /*Get the Contact info for Vendor Resource!!!*/
            
            // get the state (location e.g. MI,IL,etc.) for the provider that accepted
            try {
                vendorContact = resourceBO.getVendorResourceContact(resourceId.intValue());
            } catch (BusinessServiceException bse) {
            	throw bse;
            }
            SOContact contact = OFUtils.mapContact(vendorContact);
            so.addContact(contact);
            SOLocation soLocation = OFUtils.mapLocation(vendorContact);
            so.addLocation(soLocation);
            ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_ACCEPTED_PROVIDER_STATE, vendorContact.getStateCd());
        }
        ofRequest.setElement(so);
        ofRequest.setIdentification(OFUtils.createOFIdentityFromSecurityContext(securityContext));
        
        return ofRequest;
	}
	
	private ProcessResponse newOFAcceptOrder(String soId, boolean soInEditMode, Long vendorId, Long resourceId, Integer termAndCond, SecurityContext securityContext, SignalType signalType,String assignee){
		try{
			OrderFulfillmentRequest ofRequest = getRequestForAcceptance(vendorId, resourceId, termAndCond, securityContext,assignee);
	        OrderFulfillmentResponse responseOF = ofHelper.runOrderFulfillmentProcess(soId, signalType, ofRequest);
	        return OFUtils.mapProcessResponse(responseOF, soInEditMode);
		}catch(BusinessServiceException bse){
			logger.error("Error happened while accepting order", bse);
			ProcessResponse response = new ProcessResponse();
			response.setMessage(bse.getMessage());
			return response;
		}catch(Exception e){
			logger.error("Error happened while accepting order", e);
			ProcessResponse response = new ProcessResponse();
			response.setMessage(e.getMessage());
			return response;			
		}
	}
	
	public String serviceOrderAccept(String soId, String userName,
			Integer resourceId, Integer companyId, Integer termAndCond,
			boolean validate,String assignee) {
		long providerId = 0L;
	    SecurityContext securityContext = getSecurityContext();
        boolean isSOInEditMode = false;
        try {
            isSOInEditMode = serviceOrderBo.isSOInEditMode(soId);
        } catch (BusinessServiceException e) {
            //this should not happen just logging it
            logger.error("At the time of acceptance getting service order ", e);
        }
        if(ofHelper.isNewSo(soId)){
        	// check the validity for acceptance
        	ProcessResponse response = new ProcessResponse();
        	boolean acceptable = false;
        	try{
        	if (serviceOrderBo.isSOInEditMode(soId)) {
        		response.setCode(USER_ERROR_RC);
        		response.setMessage(ORDER_BEING_EDITED);
			} else {	
				ServiceOrder soObj = serviceOrderBo.getServiceOrder(soId);
				acceptable = serviceOrderBo.determineAcceptability(true, response, soObj);
			}
        	} catch (BusinessServiceException bse) {
    			logger.debug("Exception thrown accepting SO", bse);
    			response.setCode(SYSTEM_ERROR_RC);
    			List<String> errorMessages = new ArrayList<String>();
    			errorMessages.add("BusinessService error");
    			response.setMessages(errorMessages);
    		}
        	SignalType signal =null;
        	if("typeProvider".equals(assignee)){
        		providerId =resourceId.longValue();
        		signal = SignalType.ACCEPT_ORDER;
        	}else{
        		providerId = 0L;
        		signal = SignalType.ACCEPT_FOR_FIRM;
        	}

        	if(acceptable){
		    	response = newOFAcceptOrder(soId, isSOInEditMode, companyId.longValue(),providerId, termAndCond, securityContext,signal,assignee);
	        }
	    	return response.getMessages().get(0);
	    }
	    
		ProcessResponse pr = this.serviceOrderBo.processAcceptServiceOrder(
				soId, resourceId, companyId, termAndCond, validate, false, true,
				securityContext);

		String strMessage = pr.getMessages().get(0);
		if (pr.isError()) {
			return strMessage;
		} else {
			this.serviceOrderBo.sendallProviderResponseExceptAccepted(soId, securityContext);
		}
				
		List<String> arrMsgList = (List<String>) pr.getMessages();
		StringBuffer sbMessages = new StringBuffer();
		for (int i = 0; i < arrMsgList.size(); i++) {
			sbMessages.append(arrMsgList.get(i));
		}
		strMessage = sbMessages.toString();
		return strMessage;
	}

	public ServiceOrderDTO getServiceOrder(String soId, Integer roleId,
			Integer resId) throws DataServiceException {
		long start = System.currentTimeMillis();
		ServiceOrderDTO serviceOrderDTO = new ServiceOrderDTO();
		String dispatchAddress = "";
		String soLocationTimeZone = "";
		try {
			double distance = 0.0;
			ServiceOrder serviceOrder = getServiceOrderBo().getServiceOrder(
					soId);
			
			//boolean permitInd = validateFeature(serviceOrder.getBuyerId(), BuyerFeatureConstants.TASK_LEVEL);
			//serviceOrder.getBuyer().setPermitInd(permitInd);
			if(null != serviceOrder){
				soLocationTimeZone = serviceOrder.getServiceLocationTimeZone();
			}
			
			if (!OrderConstants.ROUTED.equalsIgnoreCase(
					serviceOrder.getStatus())) {
				VendorResource acceptedRes = serviceOrder.getAcceptedResource();
				SoLocation acceptedResLoc = null;
				if (acceptedRes != null) {
					acceptedResLoc = acceptedRes.getResourceLocation();
				}
				SoLocation serviceLocation = serviceOrder.getServiceLocation();
				if (serviceLocation != null && acceptedResLoc != null) {
					LocationVO acceptedResLatLong = luDelegate
							.checkIfZipISValid(acceptedResLoc.getZip());
					LocationVO serviceLocLatLong = luDelegate
							.checkIfZipISValid(serviceLocation.getZip());
					Map<String, String> acceptedResLocationMap = new HashMap<String, String>();
					Map<String, String> serviceLocationMap = new HashMap<String, String>();
					acceptedResLocationMap.put("lat", acceptedResLatLong
							.getLatitude());
					acceptedResLocationMap.put("long", acceptedResLatLong
							.getLongitude());
					serviceLocationMap.put("lat", serviceLocLatLong
							.getLatitude());
					serviceLocationMap.put("long", serviceLocLatLong
							.getLongitude());
					try {
						distance = GISUtil.getDistanceInMiles(
								acceptedResLocationMap, serviceLocationMap);
					} catch (InsuffcientLocationException e) {
						logger.error("GISUtil error", e);
					}
					dispatchAddress = concatenateAddress(acceptedResLoc.getStreet1(),acceptedResLoc.getStreet2(),acceptedResLoc.getCity(),acceptedResLoc.getState(),acceptedResLoc.getZip());					
					serviceOrder.setResourceDispatchAddress(dispatchAddress);
				}
			} else if (OrderConstants.ROUTED.equalsIgnoreCase(serviceOrder.getStatus())) {
				
				List<RoutedProvider> routedL = serviceOrder
						.getRoutedResources();
				SoLocation routedResLoc = null;

				for (int i = 0; i < routedL.size(); i++) {
					RoutedProvider rp = routedL.get(i);
					if (resId != null
							&& rp.getResourceId() != null
							&& rp.getResourceId().intValue() == resId
									.intValue()) {
						routedResLoc = rp.getProviderLocation();
						break;
					}
				}

				SoLocation serviceLocation = serviceOrder.getServiceLocation();
				if (serviceLocation != null && routedResLoc != null) {
					LocationVO routedResLatLong = luDelegate
							.checkIfZipISValid(routedResLoc.getZip());
					LocationVO serviceLocLatLong = luDelegate
							.checkIfZipISValid(serviceLocation.getZip());
					Map<String, String> routedResLocationMap = new HashMap<String, String>();
					Map<String, String> serviceLocationMap = new HashMap<String, String>();
					routedResLocationMap.put("lat", routedResLatLong
							.getLatitude());
					routedResLocationMap.put("long", routedResLatLong
							.getLongitude());
					serviceLocationMap.put("lat", serviceLocLatLong
							.getLatitude());
					serviceLocationMap.put("long", serviceLocLatLong
							.getLongitude());
					try {
						distance = GISUtil.getDistanceInMiles(
								routedResLocationMap, serviceLocationMap);
					} catch (InsuffcientLocationException e) {
						logger.error("GISUtil error", e);
					}
					dispatchAddress = concatenateAddress(routedResLoc.getStreet1(),routedResLoc.getStreet2(),routedResLoc.getCity(),routedResLoc.getState(),routedResLoc.getZip());
					serviceOrder.setResourceDispatchAddress(dispatchAddress);
				}
			}
			

			String dlsFlag = "N";

			if (serviceOrder != null
					&& serviceOrder.getServiceLocation() != null
					&& !StringUtils.isBlank(serviceOrder.getServiceLocation()
							.getZip())) {
				String zip = serviceOrder.getServiceLocation().getZip();
				dlsFlag = luDelegate.getDaylightSavingsFlg(zip);
			}

			if ("Y".equals(dlsFlag)) {
				TimeZone tz = TimeZone.getTimeZone(serviceOrder
						.getServiceLocationTimeZone());
				Timestamp timeStampDate = null;
				try {
					if (null != serviceOrder.getServiceDate1()
							&& (StringUtils.isNotBlank(serviceOrder.getServiceTimeStart()))) {
						java.util.Date dt = (java.util.Date) TimeUtils
								.combineDateTime(
										serviceOrder.getServiceDate1(),
										serviceOrder.getServiceTimeStart());
						timeStampDate = new Timestamp(dt.getTime());
					}
				} catch (ParseException pe) {
					pe.printStackTrace();
				}
				if (null != timeStampDate) {
					boolean isDLSActive = tz.inDaylightTime(timeStampDate);
					if (isDLSActive) {
						serviceOrder = getDSTTimezone(serviceOrder);
					} else {
						serviceOrder = getStandardTimezone(serviceOrder);
					}
				}
			} else {
				serviceOrder = getStandardTimezone(serviceOrder);
			}
			if(null != serviceOrder.getServiceDatetimeSlots() && serviceOrder.getServiceDatetimeSlots().size()>0){
				getStandardORDSTTimezoneForSlots(serviceOrder);
			}
			try {
				serviceOrder.setServiceFeePercentage(promoBO.getPromoFee(
						serviceOrder.getSoId(), serviceOrder.getBuyer().getBuyerId().longValue(), PromoConstants.SERVICE_FEE_TYPE));
			} catch (Exception e) {
				logger.error("error while getting Promo fee for Service fee");
				e.printStackTrace();
			}
			if(roleId== OrderConstants.PROVIDER_ROLEID){
				Timestamp provRoutedDate = null;
				Integer resourceId = null;
				if(null != serviceOrder.getWfStateId() && serviceOrder.getWfStateId().equals(OrderConstants.ROUTED_STATUS)){
					resourceId = resId;
				}else{
					resourceId = serviceOrder.getAcceptedResourceId();
				}
				List<RoutedProvider> routedProvidersList = serviceOrder.getRoutedResources();
				for(RoutedProvider routedProv : routedProvidersList){
					if(routedProv.getResourceId().equals(resourceId)){
						provRoutedDate = routedProv.getRoutedDate();
						break;
					}
				}
				//if routed_date in so_routed_providers is null then continue using the 
				//routed_date in so_hdr
				if(null!=provRoutedDate){
					serviceOrder.setRoutedDate(provRoutedDate);
				}			
			}
			serviceOrderDTO = ObjectMapper.convertVOToDTOSummaryTab(serviceOrder, roleId);
			//Setting original Order and warranty provider firm.
			SOWorkflowControlsVO workflowControlls= serviceOrder.getSoWrkFlowControls();
			if(null!= workflowControlls && null!=workflowControlls.getOriginalSoId()){
				serviceOrderDTO.setOriginalSoId(workflowControlls.getOriginalSoId());
				if(roleId== OrderConstants.BUYER_ROLEID){
					serviceOrderDTO.setRecallProvider(true);
				}else if(roleId== OrderConstants.PROVIDER_ROLEID){
					Integer compnayId = getSecurityContext().getCompanyId();
					if(null!= compnayId && null!= workflowControlls.getWarrantyProvider()
							&& compnayId.equals(workflowControlls.getWarrantyProvider())){
						serviceOrderDTO.setRecallProvider(true);
					}
				}
			}
			if(null!= soLocationTimeZone){
			serviceOrderDTO.setSoLocationTimeZone(soLocationTimeZone);
			}
			NumberFormat formatter = new DecimalFormat("###0.00");
			serviceOrderDTO.setDistanceInMiles(new Double(formatter
					.format(distance)));
			
			if(roleId== OrderConstants.PROVIDER_ROLEID && null != serviceOrder.getWfStateId()
			&& serviceOrder.getWfStateId().equals(OrderConstants.ROUTED_STATUS)) {
				SecurityContext secContxt = getSecurityContext();
				Map<String, UserActivityVO> activities = secContxt.getRoleActivityIdList();
				boolean manageSOFlag = false;
				String resourceId = null;
				//Checking whether the user has the rights to manage SO[4 is the Activity Id for the Activity-Manage Service Orders]
				// and not an admin, then search for specific resourceId only
				if(activities != null && activities.containsKey("4") && !secContxt.isPrimaryInd() && !secContxt.isDispatchInd()){
					manageSOFlag = true;
					resourceId = secContxt.getVendBuyerResId().toString();
				}
				
				List <ProviderResultVO> routedResourcesForFirm = getServiceOrderBo().getRoutedResourcesForFirm(
						soId, secContxt.getCompanyId().toString(), resourceId, manageSOFlag, serviceOrder);
				
				List <ProviderResultVO> routedProvExceptCounterOffer = new ArrayList <ProviderResultVO>();
				if(!routedResourcesForFirm.isEmpty()) {
					for(ProviderResultVO provRes: routedResourcesForFirm) {
						if(!OrderConstants.CONDITIONAL_OFFER.equals(provRes.getProviderRespid())) {
							routedProvExceptCounterOffer.add(provRes);
						}
					}
				}
				serviceOrderDTO.setRoutedResourcesForFirm(routedResourcesForFirm);
				serviceOrderDTO.setRoutedProvExceptCounterOffer(routedProvExceptCounterOffer);
			} 
			//SL 18418 Changes for CAR routed Orders the substatus is not set as Exclusive
			//Calling method when the so is in posted state
			if(null!=serviceOrder.getWfStateId() && serviceOrder.getWfStateId()==110)
			{
			String methodOfRouting=getServiceOrderBo().getMethodOfRouting(soId);
			     if(null!=methodOfRouting)
			     {
			    	 serviceOrderDTO.setMethodOfRouting(methodOfRouting);
			     }
			}

			/* Set the service order accepted firm details
			 Only for buyer role
			 Only for service order status
			 - Accepted(150)
			 - Active(155)
			 - Problem(170)
			 - Closed(180)
			 - Cancelled(120)
			 - Pending Cancel(165)
			*/
			
			if(null!=serviceOrder.getWfStateId()
					&& (serviceOrder.getWfStateId()==150
						|| serviceOrder.getWfStateId()==155
						|| serviceOrder.getWfStateId()==170
						|| serviceOrder.getWfStateId()==180
						|| serviceOrder.getWfStateId()==120
						|| serviceOrder.getWfStateId()==165)
					&& null!=serviceOrder.getAcceptedVendorId()
					&& roleId !=PROVIDER_ROLEID ){
				ProviderFirmVO providerfirmVO = getServiceOrderBo().getAcceptedFirmDetails
					(serviceOrder.getAcceptedVendorId());	
				if(null!=providerfirmVO){
					serviceOrderDTO.setFirmName(providerfirmVO.getBusinessName());
					
					// Format the firm phone number and set the data.
					if(null!=providerfirmVO.getBusinessPhoneNumber()){
						
						String formattedPhoneNumber = SOPDFUtils.
							formatPhoneNumber(providerfirmVO.getBusinessPhoneNumber());
						formattedPhoneNumber = formattedPhoneNumber+LEFT_PARAN_W_SPACE_SYM+
							MAIN_DISPLAY+RIGHT_PARAN_SYM;
						serviceOrderDTO.setFirmPhoneNumber(formattedPhoneNumber);
					}
				}
				
			}
			
			
			
			
		} catch (BusinessServiceException e) {
			logger.error("getServiceOrder unsuccessful");
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		if (logger.isInfoEnabled()) {
		    logger.info("Time Taken to complete getServiceOrder() from DelegateImpl:>>>>>"+(end-start));
		}
		return serviceOrderDTO;
		
	}

	public ArrayList<ServiceOrderStatusVO> queryListSoProblem() throws BusinessServiceException {
		logger
				.debug("----Start of SODetailsDelegateImpl.queryListSoProblem----");
		@SuppressWarnings("unchecked")
		ArrayList<ServiceOrderStatusVO> serviceOrderStatusVOList = 
			soMonitor.getSOSubStatusForStatusId(
				OrderConstants.PROBLEM_STATUS, new Integer(0));
		logger.debug("----End of SODetailsDelegateImpl.queryListSoProblem----");
		return serviceOrderStatusVOList;
	}
	
	public ArrayList<ServiceOrderStatusVO> queryListSoProblemStatus()throws BusinessServiceException{
		logger
		       .debug("----Start of SODetailsDelegateImpl.queryListSoProblemStatus----");
		@SuppressWarnings("unchecked")
		ArrayList<ServiceOrderStatusVO>serviceOrderStatusVOList=
			soMonitor.getSOSubStatusProblemForStatusId(
					OrderConstants.PROBLEM_STATUS,new Integer(0));
		logger.debug("----End of SODetailsDelegateImpl.queryListSoProblemStatus----");
		return serviceOrderStatusVOList;
		
	}

	public String reportProblem(String strSoId, int intSubStatusId,
			String strComment, int intEntityId, int intRoleType, String pbDesc,
			String loggedInUser) throws BusinessServiceException {
		logger
				.debug("----Start of SODetailsDelegateImpl.reportProblemResolution----");
		ProcessResponse pr = null;
		SecurityContext securityContext = getSecurityContext();
		if(ofHelper.isNewSo(strSoId)){
		    OrderFulfillmentRequest request = new OrderFulfillmentRequest();
		    SignalType problemSignal = securityContext.isBuyer() ? SignalType.BUYER_REPORT_PROBLEM : SignalType.PROVIDER_REPORT_PROBLEM;
		    request.setIdentification(OFUtils.createOFIdentityFromSecurityContext(securityContext));
		    request.addMiscParameter(OrderfulfillmentConstants.PVKEY_PROBLEM_COMMENT, strComment);
            request.addMiscParameter(OrderfulfillmentConstants.PVKEY_PROBLEM_DESC, pbDesc);
		    OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(strSoId, problemSignal, request);
		    pr = OFUtils.mapProcessResponse(response);
		}else{
		    pr = getServiceOrderBo().reportProblem(strSoId, intSubStatusId,
		            strComment, intEntityId, intRoleType, pbDesc, loggedInUser,false,
		            securityContext);
		}

		List<String> arrMsgList = pr.getMessages();
		String returnVal="";
		for(String msg:arrMsgList){
		    returnVal = pr.getCode() + msg + System.getProperty("line.separator","\n");
		}
		logger.debug("reportProblemResolution validation messages :" + returnVal);
		return returnVal;
	}

	public String reportResolution(String strSoId, int intSubStatusId,
			String strComment, int intEntityId, int intRoleType,
			String strPbDesc, String strPbDetails, String strLoggedInUser)
			throws BusinessServiceException {
		logger
				.debug("----Start of SODetailsDelegateImpl.reportProblemResolution----");
		ProcessResponse pr = null;
		SecurityContext securityContext = getSecurityContext();
		if(ofHelper.isNewSo(strSoId)){
            OrderFulfillmentRequest request = new OrderFulfillmentRequest();
            /* TODO Made temporary fix to solve problems when SLAdmin adopts provider and resolve problem.
		     */
		    if(securityContext.isAdopted()&& !securityContext.isBuyer()){
		    	securityContext.getRoles().setRoleId(securityContext.getRoleId());
		    }
            SignalType resolveSignal = securityContext.isBuyer() ? SignalType.BUYER_RESOLVE_PROBLEM : SignalType.PROVIDER_RESOLVE_PROBLEM;
            request.setIdentification(OFUtils.createOFIdentityFromSecurityContext(securityContext));
            request.addMiscParameter(OrderfulfillmentConstants.PVKEY_PROBLEM_COMMENT, strComment);
            request.addMiscParameter(OrderfulfillmentConstants.PVKEY_PROBLEM_DESC, strPbDesc);
            OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(strSoId, resolveSignal, request);
            pr = OFUtils.mapProcessResponse(response);
        }else{
            pr = getServiceOrderBo().reportResolution(strSoId, intSubStatusId,
                    strComment, intEntityId, intRoleType, strPbDesc, strPbDetails,
                    strLoggedInUser, securityContext);
        }
		String returnVal="";
		for (String msg : pr.getMessages()) {
			returnVal = pr.getCode() + msg + System.getProperty("line.separator","\n");
			logger.debug("reportProblemResolution validation messages :" + msg);
		}
		return returnVal;
	}

	public ProblemResolutionSoVO getProblemDesc(String soId)
			throws BusinessServiceException {
		logger.debug("----Start of SODetailsDelegateImpl.getProblemDesc----");
		ProblemResolutionSoVO pbResVo = null;
		pbResVo = getServiceOrderBo().getProblemDesc(soId);
		logger.debug("----End of SOMonitorDelegateImpl.getProblemDesc----");
		return pbResVo;
	}
	
	
	public ProcessResponse serviceOrderPendingCancel(SOPendingCancelDTO soPendingCancelDto) throws BusinessServiceException
	{
		
		ProcessResponse pResp = null;
		String soId = "";
		String cancelComment = "TestComment";
		double cancelAmount = 0.0d;
		
						
		if (soPendingCancelDto != null) {
			if(null!=soPendingCancelDto.getSoId())
			{
				soId = soPendingCancelDto.getSoId();
			}
			
			if(null!=soPendingCancelDto.getCancelComment())
			{
			cancelComment = soPendingCancelDto.getCancelComment();
			}
			
			cancelAmount = soPendingCancelDto.getCancelAmount();
			
			
			SecurityContext securityContext = getSecurityContext();
			securityContext.setActionSource(FRONTEND_ACTION_SOURCE);
			SignalType signalType=null;
			
			if(soPendingCancelDto.getAction().equalsIgnoreCase(OrderfulfillmentConstants.BUYER_WITHDRAW_REQUEST))
			{
			signalType = SignalType.BUYER_WITHDRAW_REQUEST;
			}
			else if(soPendingCancelDto.getAction().equalsIgnoreCase(OrderfulfillmentConstants.BUYER_AGREE_AMOUNT))
			{
			signalType = SignalType.BUYER_AGREE_AMOUNT;
			}
			else if(soPendingCancelDto.getAction().equalsIgnoreCase(OrderfulfillmentConstants.BUYER_DISAGREE_AMOUNT))
			{
			signalType = SignalType.BUYER_DISAGREE_AMOUNT;
			}
			else if(soPendingCancelDto.getAction().equalsIgnoreCase(OrderfulfillmentConstants.PROVIDER_WITHDRAW_REQUEST))
			{
			signalType = SignalType.PROVIDER_WITHDRAW_REQUEST;
			}
			else if(soPendingCancelDto.getAction().equalsIgnoreCase(OrderfulfillmentConstants.PROVIDER_AGREE_AMOUNT))
			{
			signalType = SignalType.PROVIDER_AGREE_AMOUNT;
			}
			else if(soPendingCancelDto.getAction().equalsIgnoreCase(OrderfulfillmentConstants.PROVIDER_DISAGREE_AMOUNT))
			{
			signalType = SignalType.PROVIDER_DISAGREE_AMOUNT;
			}
			else if(soPendingCancelDto.getAction().equalsIgnoreCase(OrderfulfillmentConstants.PROVIDER_DISAGREE_AMOUNT))
			{
			signalType = SignalType.PROVIDER_DISAGREE_AMOUNT;
			}
			else if(soPendingCancelDto.getAction().equalsIgnoreCase("buyerReportProblem"))
			{
			signalType = SignalType.BUYER_REPORT_PROBLEM;
			}
			else if(soPendingCancelDto.getAction().equalsIgnoreCase("providerReportProblem"))
			{
			signalType = SignalType.PROVIDER_REPORT_PROBLEM;
			
			}

		    if(ofHelper.isNewSo(soId)){
			    OrderFulfillmentRequest ofRequest = OFUtils.createRequestForPendingCancel(soPendingCancelDto, securityContext);

			    
				OrderFulfillmentResponse ofResponse =
					ofHelper.runOrderFulfillmentProcess(soId, signalType, ofRequest);
				
				com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrderResponse = ofHelper
						.getServiceOrder(soId);
				if (serviceOrderResponse.getWfStateId().intValue() == ORDER_PENDINGCANCEL
						&& serviceOrderResponse.getWfSubStatusId() == null) {

					pResp = createProcResponseFromOFResponse(ofResponse,
							PENDINGCANCEL_SUCESS, PENDINGCANCEL_FAILURE);

				} else if (serviceOrderResponse.getWfStateId().intValue() == ORDER_PENDINGCANCEL
						&& serviceOrderResponse.getWfSubStatusId().intValue() == ORDER_PENDINGESPONSE && signalType.equals(SignalType.BUYER_DISAGREE_AMOUNT)) {
					pResp = createProcResponseFromOFResponse(ofResponse,
							PENDINGRESPONSE_SUCESS, PENDINGCANCEL_FAILURE);

				} else if (serviceOrderResponse.getWfStateId().intValue() == ORDER_PENDINGCANCEL
						&& serviceOrderResponse.getWfSubStatusId().intValue() == ORDER_PENDINGREVIEW && signalType.equals(SignalType.PROVIDER_DISAGREE_AMOUNT)) {
					pResp = createProcResponseFromOFResponse(ofResponse,
							PENDINGREVIEW_SUCESS, PENDINGCANCEL_FAILURE);

				} else {
					pResp = createProcResponseFromOFResponse(ofResponse,
							PENDINGCANCEL_SUCESS, PENDINGCANCEL_FAILURE);
					triggerPushNotifications(soId,signalType);

				}

		    }else{
		    	
		    }
		} 
		return pResp;
		
		
	}

	public ProcessResponse serviceOrderCancel(SOCancelDTO soCancelDto)
			throws BusinessServiceException {
		logger.info("inside serviceOrderCancelSOD");
		ProcessResponse pResp = null;
		String soId = "";
		int buyerId = 0;
		String cancelComment = null;
		double cancelAmt = 0.0d;
		String buyerName = "";
		int statusCd = 0;
		Integer  reasonCode = null;
		String  reason = null;
		boolean isTripChargeOn = false;
						
		if (soCancelDto != null) {
			soId = soCancelDto.getSoId();
			buyerId = soCancelDto.getBuyerId();
			cancelComment = soCancelDto.getCancelComment();
			cancelAmt = soCancelDto.getCancelAmt();
			buyerName = soCancelDto.getBuyerName();
			statusCd = soCancelDto.getStatusCd();
			SecurityContext securityContext = getSecurityContext();
			securityContext.setActionSource(FRONTEND_ACTION_SOURCE);

		    if(ofHelper.isNewSo(soId)){
			    OrderFulfillmentRequest ofRequest = OFUtils.createRequestForCancel(soCancelDto, securityContext);
			    OrderFulfillmentResponse ofResponse =
					ofHelper.runOrderFulfillmentProcess(soId, SignalType.SL_CANCEL_ORDER, ofRequest);
				pResp = OFUtils.mapCancelProcessResponse(ofResponse, soCancelDto);
				triggerPushNotifications(soId, SignalType.SL_CANCEL_ORDER);

		    }else{

			    if (statusCd == OrderConstants.ACCEPTED_STATUS)
					pResp = getServiceOrderBo().processCancelSOInAccepted(buyerId,
							soId, cancelComment, buyerName, securityContext);
				else
					pResp = getServiceOrderBo().processCancelRequestInActive(
							buyerId, soId, cancelAmt, buyerName, securityContext);
		    }
		}
		return pResp;
	}
	
	private void triggerPushNotifications(String soId,SignalType signalType) {
		try {
			logger.info("Inside SODetailsDelegateImpl.triggerPushNotifications---> Fetching order details");
			ServiceOrder order = getServiceOrderBo().getServiceOrderForPushNotfcn(soId);

			if (null != signalType && signalType.equals(SignalType.SL_CANCEL_ORDER) && (null != order.getWfStateId())
					&& (order.getWfStateId().intValue() == OrderConstants.PENDING_CANCEL_STATUS)
					&& (null != order.getAcceptedResourceId())) {
				logger.info("Trigger push notification to the assigned provider " + order.getAcceptedResourceId());
				triggerPushNotificationForAssignedTechnician(order,
						OrderConstants.BUYER_CANCELLATION_PUSH_NOTIFICATION_TEMPLATE);
			}

			if((null!=signalType) && signalType.equals(SignalType.BUYER_AGREE_AMOUNT) && (null != order.getWfStateId())
					&& (order.getWfStateId().intValue() == OrderConstants.CANCELLED_STATUS) && (null != order.getAcceptedResourceId())){
				logger.info("Inside SODetailsDelegateImpl.triggerPushNotifications---> assigned technician");
				triggerPushNotificationForAssignedTechnician(order, OrderConstants.ORDER_CANCEL_PUSH_NOTIFICATION_TEMPLATE);
			}
			logger.info("triggerPushNotification for primary resource");
			triggerPushNotificationForPrimaryResource(order);

		} catch (Exception e) {
			logger.error("Exception occurred in SODetailsDelegateImpl.triggerPushNotification" + e.getMessage());
		}

	}

	private void triggerPushNotificationForAssignedTechnician(ServiceOrder order, String templateName) {
		try {
			logger.info(
					"Inside SODetailsDelegateImpl.triggerPushNotificationForAssignedTechnician---> assigned technician");
			pushNotificationAlertTask.addAlert(order, templateName);
		} catch (Exception e) {
			logger.error("Exception occurred in SODetailsDelegateImpl.triggerPushNotificationForAssignedTechnician"
					+ e.getMessage());
		}
	}

	private void triggerPushNotificationForPrimaryResource(ServiceOrder order) {
		try {
			logger.info(
					"Inside SODetailsDelegateImpl.triggerPushNotificationForPrimaryResource---> Send Notification to Primary Technician");
			if (null != order && (null != order.getWfStateId()) && null != order.getAcceptedVendorId()) {
				if (order.getWfStateId().intValue() == OrderConstants.CANCELLED_STATUS) {
					logger.info("Trigger push notification for cancelled so : " + order.getSoId());
					populateAlertTaskForPrimaryResource(order, OrderConstants.ORDER_CANCEL_PUSH_NOTIFICATION_TEMPLATE);
				} else if (order.getWfStateId().intValue() == OrderConstants.PENDING_CANCEL_STATUS) {
					logger.info(
							"Trigger push notification for cancellation request for the order : " + order.getSoId());
					populateAlertTaskForPrimaryResource(order, OrderConstants.BUYER_CANCELLATION_PUSH_NOTIFICATION_TEMPLATE);
				}
			}

		} catch (Exception e) {
			logger.error("Exception occurred in SODetailsDelegateImpl.triggerPushNotification" + e.getMessage());
		}

	}

	private void populateAlertTaskForPrimaryResource(ServiceOrder order, String template) {
		try {
			Integer primaryResourceId = serviceOrderBo.getPrimaryResourceIdForPushNotfcn(order.getAcceptedVendorId());
			logger.info("Inside SODetailsDelegateImpl.triggerPushNotificationForPrimaryResource---> primaryResourceId");
			if (null != primaryResourceId) {
				if ((null == order.getAcceptedResourceId())
						|| (!(primaryResourceId.equals(order.getAcceptedResourceId())))) {
					logger.info(
							"Inside SODetailsDelegateImpl.triggerPushNotificationForPrimaryResource---> Insert into alert task");
					pushNotificationAlertTask.AddAlert(order, template);
				}
			}
		} catch (Exception e) {
			logger.error("Exception occurred while populating Alert task with Primary resource data " + e.getMessage());
		}
	}

	public ProcessResponse serviceOrderVoid(SOCancelDTO soCancelDto)
			throws BusinessServiceException {
		ProcessResponse pResp = null;
		String soId = "";
		int buyerId = 0;
		String comment = "";
		Integer reasonCode = null;
		String reason = "";
		
		if (soCancelDto != null) {
			soId = soCancelDto.getSoId();
			buyerId = soCancelDto.getBuyerId();
			comment = soCancelDto.getCancelComment();
			reasonCode = soCancelDto.getReasonCode();
			SecurityContext securityContext = getSecurityContext();
			securityContext.setActionSource(FRONTEND_ACTION_SOURCE);
		    if(ofHelper.isNewSo(soId)){
			    OrderFulfillmentRequest ofRequest = OFUtils.createRequestForCancel(soCancelDto, securityContext);
			    OrderFulfillmentResponse ofResponse = ofHelper.runOrderFulfillmentProcess(soId, SignalType.CANCEL_ORDER, ofRequest);
			    pResp = OFUtils.mapProcessResponse(ofResponse);
		    }else{	
				pResp = getServiceOrderBo().processVoidSO(buyerId, soId,
						securityContext);
		    }
		}
		return pResp;
	}

	public ProcessResponse createProviderConditionalOfferForGroup(
			ConditionalOfferDTO conditionalOfferDTO) {
		logger
				.info("In details delegate:::: createProviderConditionalOfferForGroup() ");
		ProcessResponse processResponse = new ProcessResponse();
		List<ServiceOrderSearchResultsVO> serviceOrders;
		try {
			if(ofHelper.isNewGroup(conditionalOfferDTO.getSoId())){
			    List<com.servicelive.orderfulfillment.domain.RoutedProvider> firmRoutedProviders = OFUtils.createConditionalOffer(conditionalOfferDTO);
			    SOElementCollection routedProviders=new SOElementCollection();
			    routedProviders.addAllElements(firmRoutedProviders);
			    OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentGroupProcess(conditionalOfferDTO.getSoId(), SignalType.CREATE_GROUP_CONDITIONAL_OFFER, routedProviders, OFUtils.createOFIdentityFromSecurityContext(getSecurityContext()));
			    return OFUtils.mapProcessResponse(response);
			}			
			
			serviceOrders = orderGrpBO
					.getServiceOrdersForGroup(conditionalOfferDTO.getSoId());
			getConditionalDateAndTime(conditionalOfferDTO, serviceOrders.get(0)
					.getSoId());
			if (conditionalOfferDTO != null) {
				SecurityContext securityContext = getSecurityContext();
				processResponse = orderGrpBO
						.processCreateConditionalOfferForGroup(
								conditionalOfferDTO.getSoId(),
								conditionalOfferDTO.getResourceId(),
								conditionalOfferDTO.getVendorOrBuyerID(),
								conditionalDate1, conditionalDate2,
								conditionalOfferDTO.getConditionalStartTime(),
								conditionalOfferDTO.getConditionalEndTime(),
								conditionalExpirationDate, conditionalOfferDTO
										.getConditionalSpendLimit(),
								conditionalOfferDTO.getSelectedCounterOfferReasonsList(),
								securityContext);

			}
		} catch (com.newco.marketplace.exception.BusinessServiceException e) {
			setProcessResponse(processResponse, e);
		}
		return processResponse;
	}

	/**
	 * Sets the process reponse with the error message
	 * 
	 * @param processResponse
	 * @param e
	 */
	private void setProcessResponse(ProcessResponse processResponse,
			com.newco.marketplace.exception.BusinessServiceException e) {
		processResponse.setCode(ServiceConstants.USER_ERROR_RC);
		List<String> messages = new ArrayList<String>();
		messages.add(e.getMessage());
		processResponse.setMessages(messages);
	}

	private ProcessResponse createProcResponseFromOFResponse(OrderFulfillmentResponse ordrFlflResponse,
			String msgOnSuccess, String msgOnFailure)
	{
		ProcessResponse procResp = new ProcessResponse();
        List<String> msgList = new ArrayList<String>();
        if (null != ordrFlflResponse && ordrFlflResponse.isError()) {
        	procResp.setCode(SYSTEM_ERROR_RC);
        	msgList.add(msgOnFailure);
        	msgList.add(ordrFlflResponse.getErrorMessage());
        } else {
        	procResp.setCode(VALID_RC);
        	procResp.setSubCode(VALID_RC);
        	msgList.add(msgOnSuccess);
        }
        procResp.setMessages(msgList);
        return procResp;
	}

	private void getConditionalDateAndTime(
			ConditionalOfferDTO conditionalOfferDTO, String soId) {
		conditionalDate1 = null;
		conditionalDate2 = null;
		conditionalExpirationDate = null;

		if (conditionalOfferDTO.getConditionalChangeDate1() != null
				&& StringUtils.isNotEmpty(conditionalOfferDTO
						.getConditionalChangeDate1()))
			conditionalDate1 = Timestamp.valueOf(conditionalOfferDTO
					.getConditionalChangeDate1()
					+ " 00:00:00");
		if (conditionalOfferDTO.getConditionalChangeDate2() != null
				&& StringUtils.isNotEmpty(conditionalOfferDTO
						.getConditionalChangeDate2()))
			conditionalDate2 = Timestamp.valueOf(conditionalOfferDTO
					.getConditionalChangeDate2()
					+ " 00:00:00");
		if (conditionalOfferDTO.getConditionalExpirationTime() != null
				&& StringUtils.isNotEmpty(conditionalOfferDTO
						.getConditionalExpirationTime())) {

			ServiceOrder so = null;
			try {
				so = getServiceOrderBo().getServiceOrder(soId);
			} catch (BusinessServiceException e) {
				e.printStackTrace();
			}

			if (so != null) {
				conditionalExpirationDate = new Timestamp(TimeUtils
						.combineDateAndTime(
								Timestamp.valueOf(conditionalOfferDTO
										.getConditionalExpirationDate()
										+ " 00:00:00"),
								conditionalOfferDTO
										.getConditionalExpirationTime(),
								so.getServiceLocationTimeZone()).getTime());
			}

		}
	}

	public ProcessResponse createProviderConditionalOffer(
			ConditionalOfferDTO conditionalOfferDTO) {

		Timestamp conditionalDate1 = null;
		Timestamp conditionalDate2 = null;
		Timestamp conditionalExpirationDate = null;

		logger.info("In details delegate:::: createProviderConditionalOffer() ");
		if(ofHelper.isNewSo(conditionalOfferDTO.getSoId())){
			List<com.servicelive.orderfulfillment.domain.RoutedProvider> firmRoutedProviders = OFUtils.createConditionalOffer(conditionalOfferDTO);
		    SOElementCollection routedProviders=new SOElementCollection();
		    routedProviders.addAllElements(firmRoutedProviders);
		    OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(conditionalOfferDTO.getSoId(), SignalType.CREATE_CONDITIONAL_OFFER, routedProviders, OFUtils.createOFIdentityFromSecurityContext(getSecurityContext()));
		    return OFUtils.mapProcessResponse(response);
		}
		if (conditionalOfferDTO.getConditionalChangeDate1() != null
				&& StringUtils.isNotEmpty(conditionalOfferDTO
						.getConditionalChangeDate1()))
			conditionalDate1 = Timestamp.valueOf(conditionalOfferDTO
					.getConditionalChangeDate1()
					+ " 00:00:00");
		if (conditionalOfferDTO.getConditionalChangeDate2() != null
				&& StringUtils.isNotEmpty(conditionalOfferDTO
						.getConditionalChangeDate2()))
			conditionalDate2 = Timestamp.valueOf(conditionalOfferDTO
					.getConditionalChangeDate2()
					+ " 00:00:00");
		if (conditionalOfferDTO.getConditionalExpirationTime() != null
				&& StringUtils.isNotEmpty(conditionalOfferDTO
						.getConditionalExpirationTime())) {

			ServiceOrder so = null;
			try {
				so = getServiceOrderBo().getServiceOrder(
						conditionalOfferDTO.getSoId());
			} catch (BusinessServiceException e) {
				e.printStackTrace();
			}

			if (so != null) {
				Timestamp expDateAsTimestamp = Timestamp.valueOf(conditionalOfferDTO.getConditionalExpirationDate()
						+ " 00:00:00");
				conditionalExpirationDate = new Timestamp(TimeUtils
						.combineDateAndTime(
								expDateAsTimestamp,
								conditionalOfferDTO.getConditionalExpirationTime(),
								so.getServiceLocationTimeZone()).getTime());
			}
		}

		ProcessResponse processResponse = null;
		if (conditionalOfferDTO != null) {
			SecurityContext securityContext = getSecurityContext();
			processResponse = getServiceOrderBo()
					.processCreateConditionalOffer(
							conditionalOfferDTO.getSoId(),
							conditionalOfferDTO.getResourceId(),
							conditionalOfferDTO.getVendorOrBuyerID(),
							conditionalDate1, conditionalDate2,
							conditionalOfferDTO.getConditionalStartTime(),
							conditionalOfferDTO.getConditionalEndTime(),
							conditionalExpirationDate,
							conditionalOfferDTO.getConditionalSpendLimit(),
							conditionalOfferDTO.getSelectedCounterOfferReasonsList(),
							securityContext,null);
		}
		return processResponse;
	}
	
	private ProcessResponse completeServiceOrder(SOCompleteCloseDTO soCompDto)
	throws BusinessServiceException {
		if (soCompDto == null) return null;
		
		
		com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder = ofHelper.getServiceOrder(soCompDto.getSoId());
		
		
		
		//for finance of HSR edit completion.
		Double soAddOnPrice=0.0d;
		//SM-59  not required since we are getting the previous values from so_work_flow_controls_table
				/*
		Double soPartsPrice=0.0d;
		
		 Double retailPrice = 0.00;
        Double reimbursementRetailPrice = 0.00;
        Double partsSLGrossup = 0.00;
        Double retailPriceSLGrossup = 0.00;*/
        

		 if(3000==serviceOrder.getBuyerId().intValue()){
			 soAddOnPrice =serviceOrder.getTotalAddon(true).doubleValue();
			 if(serviceOrder.getPrice()!=null)
			 {
				if(serviceOrder.getPrice().getTotalAddonPriceGL()==null) 
				{
					soAddOnPrice=0.0d;
				}
			 }
			   //SM-59  not required since we are getting the previous values from so_work_flow_controls_table
			// soPartsPrice=serviceOrder.getPartsPrice().doubleValue();
			 
			 
             // to fetch previous price for ledger reporting rules (1137-1140)
			 
            /* List <SOProviderInvoiceParts> invoiceParts = serviceOrder.getSoProviderInvoiceParts();
             if(null!=invoiceParts)
             {
            	 
             Iterator<SOProviderInvoiceParts> iterator = invoiceParts.iterator();
             
             while(iterator.hasNext()){
             	SOProviderInvoiceParts provInvoiceParts = (SOProviderInvoiceParts) iterator.next();
             	if(null != provInvoiceParts.getRetailCostToInventory() 
             			&& !StringUtils.isBlank(provInvoiceParts.getRetailCostToInventory())){
                 	retailPrice = retailPrice + (Double.parseDouble(provInvoiceParts.getRetailCostToInventory()) * provInvoiceParts.getQty());
             	}
             	if(null != provInvoiceParts.getRetailReimbursement() 
             			&& !StringUtils.isBlank(provInvoiceParts.getRetailReimbursement())){
                 	reimbursementRetailPrice = reimbursementRetailPrice + (Double.parseDouble(provInvoiceParts.getRetailReimbursement()) * provInvoiceParts.getQty());
             	}
             	if(null != provInvoiceParts.getRetailPriceSLGrossUp() 
             			&& !StringUtils.isBlank(provInvoiceParts.getRetailPriceSLGrossUp())){
                 	partsSLGrossup = partsSLGrossup + (Double.parseDouble(provInvoiceParts.getRetailPriceSLGrossUp()) * provInvoiceParts.getQty());
             	}
             	if(null != provInvoiceParts.getRetailSLGrossUp() 
             			&& !StringUtils.isBlank(provInvoiceParts.getRetailSLGrossUp())){
                 	retailPriceSLGrossup = retailPriceSLGrossup + (Double.parseDouble(provInvoiceParts.getRetailSLGrossUp()) * provInvoiceParts.getQty());
             	}
             }
		 }*/
			 
		        }
	        


		serviceOrder.setSoId(soCompDto.getSoId());
        serviceOrder.setResolutionDs(soCompDto.getResComments());
        serviceOrder.setTaskLevelPricing(soCompDto.isTaskLevelPricing());
        if (soCompDto.getFinalPartPrice() != null && StringUtils.isNotBlank(soCompDto.getFinalPartPrice())) {
        	serviceOrder.setFinalPriceParts(new BigDecimal(soCompDto.getFinalPartPrice()));
        }
        if (soCompDto.getFinalLaborPrice() != null && StringUtils.isNotBlank(soCompDto.getFinalLaborPrice())) {
        	serviceOrder.setFinalPriceLabor(new BigDecimal(soCompDto.getFinalLaborPrice()));
        }
        if(soCompDto.isTaskLevelPricing()){
	        if (soCompDto.getSoMaxLabor() != null && StringUtils.isNotBlank(soCompDto.getSoMaxLabor())) {
	        	serviceOrder.setSoMaxLabor(new BigDecimal(soCompDto.getSoMaxLabor()));
	        	double initialMaxLabor = Double.parseDouble(soCompDto.getSoInitialMaxLabor());
	        	double totalFinalPriceOfTasks=0.0;
	        	int nonPermitTaskCount=0;
	        	double taskAmount=0;
	        		for (com.servicelive.orderfulfillment.domain.SOTask task : serviceOrder.getActiveTasks()) {
	        		/*
	        		if(!task.isPermitTask() && task.getFinalPrice()!=null && !(task.getSequenceNumber().equals(0))){
	        			nonPermitTaskCount++;
	        			totalFinalPriceOfTasks=totalFinalPriceOfTasks + task.getFinalPrice().doubleValue();
	        		}*/
	        		//Task except Permit & Delivery
	        		if((PRIMARY_TASK.equals(task.getTaskType()) || NON_PRIMARY_TASK.equals(task.getTaskType())) && task.getFinalPrice()!=null){
	        			nonPermitTaskCount++;
	        			totalFinalPriceOfTasks=totalFinalPriceOfTasks + task.getFinalPrice().doubleValue();
	        		}
	        	}
	        	if(totalFinalPriceOfTasks==0 && nonPermitTaskCount!=0){
					taskAmount = Double.parseDouble(soCompDto.getSoMaxLabor())/nonPermitTaskCount;

				}
	        
	        		for (com.servicelive.orderfulfillment.domain.SOTask task : serviceOrder.getActiveTasks()) {
	        			if(null!=task.getTaskHistory()){
	        				task.setTaskHistory(task.getTaskHistory());
	        			}
	        			if(!PERMIT_TASK.equals(task.getTaskType())){
	        				if(Double.parseDouble(soCompDto.getSoMaxLabor())!=totalFinalPriceOfTasks){
	        					double amount=0;
	        					if(totalFinalPriceOfTasks==0 && !(DELIVERY_TASK.equals(task.getTaskType()))){
	        						task.setFinalPrice(BigDecimal.valueOf(taskAmount));
	        					}
	        					
	        					//divide new somaxlabor(the total price for tasks alone excluding permit task) among tasks.
	        					if(null!=task.getFinalPrice() && null!=soCompDto.getSoMaxLabor() && totalFinalPriceOfTasks !=0){
	        					double percentageShare=task.getFinalPrice().doubleValue()/totalFinalPriceOfTasks*100;
	        					amount =  Double.parseDouble(soCompDto.getSoMaxLabor())* percentageShare /100;
		        			    task.setFinalPrice(BigDecimal.valueOf(amount));
	        				}
		        			 }
	        			}else if(PERMIT_TASK.equals(task.getTaskType()) && soCompDto.getIncompleteService()!=OrderConstants.SERVICE_INCOMPLETE){
	        				for (SOTaskDTO permitTask : soCompDto.getPermitTaskList()) {
	                			if(null!=permitTask.getTaskType() && permitTask.getTaskType()==1 && null!=permitTask.getSequenceNumber() && null!=task.getSequenceNumber() && permitTask.getSequenceNumber().equals(task.getSequenceNumber())){
	                				if(permitTask.getFinalPrice()!=null){
		                				task.setFinalPrice(BigDecimal.valueOf(permitTask.getFinalPrice()));
		                				task.setPermitType(permitTask.getPermitType());
	                				}
	                				
	                			}
	                		}
	        				
	        			}else if(PERMIT_TASK.equals(task.getTaskType()) && soCompDto.getIncompleteService()==OrderConstants.SERVICE_INCOMPLETE){
	        				task.setFinalPrice(BigDecimal.valueOf(0.0));
	        			}
	        		}
	        	
	        	
	        }
        }else if (soCompDto.getSoMaxLabor() != null && StringUtils.isNotBlank(soCompDto.getSoMaxLabor())) {
        	serviceOrder.setSoMaxLabor(new BigDecimal(soCompDto.getFinalLaborPrice()));
        }
        
        
        // HSR
        if("3000".equals(soCompDto.getBuyerID()))
        {
        	List<SOPartLaborPriceReasonVO> reasonVO = new ArrayList<SOPartLaborPriceReasonVO>();
            SOPartLaborPriceReasonVO partReasonVO = new SOPartLaborPriceReasonVO();
            SOPartLaborPriceReasonVO laborReasonVO = new SOPartLaborPriceReasonVO();
	           
                partReasonVO.setSoId(soCompDto.getSoId());
	            partReasonVO.setPriceType("Parts");
	            partReasonVO.setReasonCodeId(soCompDto.getSelectReasonForParts());
	            partReasonVO.setReasonComments(soCompDto.getOtherReasonTextParts());
	            reasonVO.add(partReasonVO);
            
	            laborReasonVO.setSoId(soCompDto.getSoId());
	            laborReasonVO.setPriceType("Labor");
	            laborReasonVO.setReasonCodeId(soCompDto.getSelectReasonForLabor());
	            laborReasonVO.setReasonComments(soCompDto.getOtherReasonText());
	            reasonVO.add(laborReasonVO);
            
            
         
            soCompDto.setSoPartLaborPriceReason(reasonVO);
          //SM-59 no need to set the invoice parts 
        	//serviceOrder.setSoProviderInvoiceParts(OFUtils.mapPartsInvoice(soCompDto, serviceOrder));
        	serviceOrder.setSoPartLaborPriceReason(OFUtils.mapPartLaborPriceReason(soCompDto, serviceOrder));
        }	
        
        
        
        
        if(soCompDto.getAddonServicesDTO() != null){
        	TokenizeResponse tokenizeResponse = null;
        	List<SOAddon> soAddOns = new ArrayList<SOAddon>();
    		AddonServicesDTO addonDto = soCompDto.getAddonServicesDTO();
	        serviceOrder.setAddons(OFUtils.mapAddOns(soCompDto));
	        // only add additionalPayment record if there are addons
	        if (soCompDto.isValidateCC()){
		        serviceOrder.setAdditionalPayment(OFUtils.mapAdditionalPayment(addonDto));
		    	logger.info("Details delegate  before calling tokenize now commented : ");
		    	//if(null!= addonDto && (!StringUtils.contains(addonDto.getCreditCardNumber(),CommonConstants.SPECIAL_CHARACTER)) && addonDto.getPaymentRadioSelection().equals(OrderConstants.UPSELL_PAYMENT_TYPE_CREDIT))- changing due to tokenization moved to frontend
		    	if(null!= addonDto && addonDto.getPaymentRadioSelection().equals(OrderConstants.UPSELL_PAYMENT_TYPE_CREDIT)){
		    		
		    		logger.info("Details delegate  addonDto.getMaskedCardNumber() : " + addonDto.getMaskedCardNumber());
			    	logger.info("Details delegate  addonDto.getTokenizeCardNumber() : " + addonDto.getTokenizeCardNumber());
			    	logger.info("Details delegate  addonDto.getResponseXML() : " + addonDto.getResponseXML());
			    	
		    		if((!StringUtils.isBlank(addonDto.getMaskedCardNumber()) && !StringUtils.isBlank(addonDto.getTokenizeCardNumber()))){
		    			
						    SOAdditionalPayment addPayment = serviceOrder.getAdditionalPayment();
						 	logger.info("Details delegate  addPayment.getMaskedCardNumber() : " + addPayment.getMaskedAccountNumber());
					    	logger.info("Details delegate  addPayment.getTokenizeCardNumber() : " + addPayment.getToken());
					    	
						    addPayment = updateTokenMaskedAccountNo(addonDto,addPayment,tokenizeResponse);
						      
						    	logger.info("Details delegate  addPayment.getMaskedCardNumber() : " + addPayment.getMaskedAccountNumber());
						    	logger.info("Details delegate  addPayment.getTokenizeCardNumber() : " + addPayment.getToken());
						        
						    	serviceOrder.setAdditionalPayment(addPayment);
						}
		    	}
		    	
		    	//Commenting for SL-3678 moving below code to UI
		    	/*    try {
			        		  
					tokenizeResponse = tokenizeCreditCard(soCompDto.getUserName(),addonDto);
		    		
			        	  //Tokenization is required or not,if yes,response should be there.
					if(null!= addonDto && (!StringUtils.contains(addonDto.getCreditCardNumber(),CommonConstants.SPECIAL_CHARACTER)) &&
							addonDto.getPaymentRadioSelection().equals(OrderConstants.UPSELL_PAYMENT_TYPE_CREDIT)){
						if(null== tokenizeResponse ||(StringUtils.isBlank(tokenizeResponse.getMaskedAccount())||StringUtils.isBlank(tokenizeResponse.getToken()))){
						   return createProcResponseForTokenizationFailure();
						}
						
						
					 }
				} catch (Exception e) {
					logger.error("Exception in tokenizing crdit card used for transcation"+ e);
					return createProcResponseForTokenizationFailure();
					
				}*/
		        
	        } else {
	        	// if there are no addons, delete previous additional payment info
	        	serviceOrder.setAdditionalPayment(null);
	        }
	    }
        
        if (soCompDto.getBuyerRefs() != null && soCompDto.getBuyerRefs().size() > 0) {
        	serviceOrder.setCustomReferences(OFUtils.mapCustomRefs(soCompDto.getBuyerRefs()));
        }
       
        if (soCompDto.getPartList() != null && soCompDto.getPartList().size() > 0) {
        	serviceOrder.setParts(OFUtils.mapParts(soCompDto.getPartList()));
        }
        List<Parameter> parameters = new ArrayList<Parameter>();
        String autoClose="";
        if(soCompDto.getIncompleteService()==1)
       
        {
        	 autoClose="autoClose";
        	 soCompDto.setSoMaxLabor(soCompDto.getIncompletesoMaxLabor());
        	 soCompDto.setSoInitialMaxLabor(soCompDto.getIncompletesoInitialMaxLabor());
        	 soCompDto.setResComments(soCompDto.getIncompleteresComments());
        	 soCompDto.setFinalPartPrice(soCompDto.getIncompletefinalPartPrice());
        	 soCompDto.setFinalLaborPrice(soCompDto.getIncompletesoMaxLabor());
        }
        
        //for service incomplete
        
        
        if(soCompDto.getIncompleteService()==OrderConstants.SERVICE_INCOMPLETE)
        {
        	serviceOrder.setAdditionalPayment(null);
        if(serviceOrder.getAddons()!=null && serviceOrder.getAddons().size()>0)
        {
        for (int count=0;count<serviceOrder.getAddons().size();count++) {
        	
        	serviceOrder.getAddons().get(count).setQuantity(0);
        }
        }
        }

		 if(3000==serviceOrder.getBuyerId().intValue()){
		     //adding previous price in process variables which will be used in CompleteOrderCmd
			 
			parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_ADDONPRICE,soAddOnPrice.toString()));
			//SM-59  not required since we are getting the previous values from so_work_flow_controls_table
		    // parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_PARTPRICE,soPartsPrice.toString()));  
		   /*  parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RETAILPRICE,retailPrice.toString()));
		     parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_REIMBURSEMENTRETAILPRICE,reimbursementRetailPrice.toString()));
		     parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_PARTSSLGROSSUP,partsSLGrossup.toString()));
		     parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RETAILPRICESLGROSSUP,retailPriceSLGrossup.toString()));*/

			//Priority 5B changes
	        //Prevent auto close if model & serial no. validation exists
			//Set variable if no validation error exists
			if(!soCompDto.isModelError() && !soCompDto.isSerialError()){
	        	parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_MODEL_SERIAL_ERROR, Constants.FALSE));
	        }else{
	        	parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_MODEL_SERIAL_ERROR, OrderConstants.TRUE));
	        }

		 }
		 
		 if(Constants.TECH_TALK_BUYER.equals(serviceOrder.getBuyerId().toString())){
			 logger.info("****************** putting deposition id for techtalk buyer **************************");
			 logger.info("Deposition Code ID: "+soCompDto.getSelectedDepositionCode());
			 Boolean clientCharged = isClientCharged(soCompDto);
			 parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_DEPOSITION_CODE_IND,clientCharged.toString()));
		 }
         
        parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_AUTOCLOSE,autoClose));
        
        SecurityContext context = null;
        context=getSecurityContext();
	     if(null!=context && null!=context.getVendBuyerResId()){
	    	logger.info("PVKEY_RESOURCE_ID"+context.getVendBuyerResId());
		    parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RESOURCE_ID, context.getVendBuyerResId().toString()));
	     }
	     parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_CREATED_BY,soCompDto.getCreatedBy()));
        parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_MODIFIED_BY,soCompDto.getModifiedBy()));
	    	logger.info("soCompDto.getCreatedBy()"+soCompDto.getCreatedBy()+"modified by"+soCompDto.getModifiedBy());
	     parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_ROLE_ID,soCompDto.getRoleIds()));
	    	logger.info("soCompDto.getRoleId()"+soCompDto.getRoleIds());
	    	
        
        assignProvider(serviceOrder,soCompDto.getAcceptedProviderId(),parameters);
        
        OrderFulfillmentResponse ordrFlflResponse = ofHelper.runOrderFulfillmentProcess(
        		serviceOrder.getSoId(),
        		SignalType.COMPLETE_ORDER,
        		serviceOrder,
        		OFUtils.createOFIdentityFromSecurityContext(getSecurityContext()),parameters);
        
        
        
        ProcessResponse presponse;
    	com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrderResponse = ofHelper.getServiceOrder(serviceOrder.getSoId());
		if(serviceOrderResponse.getWfStateId().intValue()==ORDER_CLOSED)
		{
			
		    presponse=createProcResponseFromOFResponse(ordrFlflResponse, AUTOCLOSE_SO_SUCCESS, COMPLETESO_FAILURE);
		
			
		}
		else if(serviceOrderResponse.getWfStateId().intValue()==ORDER_CANCELELD)
		{
		     presponse=createProcResponseFromOFResponse(ordrFlflResponse, CANCELLED_SO_SUCCESS, COMPLETESO_FAILURE);
		
		}
		else if(soCompDto.isAutocloseOn())
		{
			//SL-20926
			presponse = createRespForAddonIssues(ordrFlflResponse);
			if(null != presponse){
				return presponse;
			}
			presponse=createProcResponseFromOFResponse(ordrFlflResponse, AUTOCLOSE_COMPLETESO_SUCCESS, COMPLETESO_FAILURE);
		
		}
		else
		{
			//SL-20926
			presponse = createRespForAddonIssues(ordrFlflResponse);
			if(null != presponse){
				return presponse;
			}
		    presponse=createProcResponseFromOFResponse(ordrFlflResponse, COMPLETESO_SUCCESS, COMPLETESO_FAILURE);
		
		}

		if(VALID_RC.equals(presponse.getCode())){
			try{
				// prepare for regenerating SO signature PDF
				boolean exists = documentBO.checkIfMobileSignatureDocumentExists(soCompDto.getSoId());
				if(exists){
					logger.info("check if PDF has to be regenerated.");
					Integer documentId = documentBO.checkIfSignaturePDFDocumentExisits(soCompDto.getSoId());
					if(null != documentId){
						logger.info("PDF has to be regenerated.");
						logger.debug("Remove document from so_document");
						documentBO.deleteSODocumentMapping(soCompDto.getSoId(), documentId);
						
						logger.debug("Remove document from document");
						documentBO.deleteResourceDocument(documentId);
						
						logger.debug("Update mobile so signature");
						documentBO.updatePDFBatchParamaters(soCompDto.getSoId());
					}
				}
			} catch (BusinessServiceException e){
				logger.error("Error while preparing for regenerating PDF : " + e);
			} catch (DataServiceException e) {
				logger.error("Error while preparing for regenerating PDF : " + e);
			}
			
			//Priority 5B changes
			try{
				if(StringUtils.isNotBlank(soCompDto.getBuyerID()) && 
						OrderConstants.INHOME_BUYER.equalsIgnoreCase(soCompDto.getBuyerID())){
					
					//If model no. or serial validation exists or if model or serial image is uploaded, 
					//update indicator in so_workflow_controls
					String invalidInd = serviceOrderResponse.getSOWorkflowControls().getInvalidModelSerialInd();
					if(soCompDto.isModelError() && soCompDto.isSerialError()){
						if(null == invalidInd || (null != invalidInd && !Constants.BOTH.equalsIgnoreCase(invalidInd))){
							serviceOrderBo.updateModelSerialInd(serviceOrder.getSoId(), Constants.BOTH.toUpperCase());
						}
					}
					else if(soCompDto.isModelError()){
						if(null == invalidInd || (null != invalidInd && !InHomeNPSConstants.MODEL.equalsIgnoreCase(invalidInd))){
							serviceOrderBo.updateModelSerialInd(serviceOrder.getSoId(), InHomeNPSConstants.MODEL.toUpperCase());
						}
					}
					else if(soCompDto.isSerialError()){
						if(null == invalidInd || (null != invalidInd && !InHomeNPSConstants.SERIAL_NUMBER.equalsIgnoreCase(invalidInd))){
							serviceOrderBo.updateModelSerialInd(serviceOrder.getSoId(), InHomeNPSConstants.SERIAL_NUMBER.toUpperCase());
						}
					}
					else if(!soCompDto.isModelError() && !soCompDto.isSerialError() && null != invalidInd){
						serviceOrderBo.updateModelSerialInd(serviceOrder.getSoId(), null);
					}
					
					//If model no. or serial no. validation exists, insert order history
					if(soCompDto.isModelError() || soCompDto.isSerialError()){
						//Populate logging object
						SoLoggingVo soLoggingVO = new SoLoggingVo();
						soLoggingVO.setServiceOrderNo(serviceOrder.getSoId());
						soLoggingVO.setActionId(Constants.INVALID_FIELD_ID);
						if(soCompDto.isModelError() && soCompDto.isSerialError()){
							soLoggingVO.setComment(Constants.INVALID_FIELDS_COMMENT);
							
						}else if(soCompDto.isModelError()){
							soLoggingVO.setComment(Constants.INVALID_MODEL_COMMENT);
							
						}else if(soCompDto.isSerialError()){
							soLoggingVO.setComment(Constants.INVALID_SERIAL_COMMENT);
						}
						Date date = new Date();
						soLoggingVO.setCreatedDate(date);
						soLoggingVO.setModifiedDate(date);
						SecurityContext securityContext = getSecurityContext();
						if(null != securityContext){
							soLoggingVO.setModifiedBy(securityContext.getUsername());
							soLoggingVO.setRoleId(securityContext.getRoleId());
							soLoggingVO.setCreatedByName(securityContext.getRoles().getFirstName()+" "+securityContext.getRoles().getLastName());
							soLoggingVO.setEntityId(securityContext.getVendBuyerResId());
						}
						
						//insert order history
						serviceOrderBo.insertSoLogging(soLoggingVO);
					}
				}
				
			}catch(Exception e){
				logger.error("Error while updating invalid_model_serial_ind in SODetailsDelegateImpl.completeServiceOrder(): " + e);
			}
		}	
	
		return presponse;
	}

	private Boolean isClientCharged(SOCompleteCloseDTO soCompDto) {
		Boolean ind = Boolean.TRUE;
		String depositionCodeID = soCompDto.getSelectedDepositionCode();
		for(DepositionCodeDTO depositionCodeDTO : soCompDto.getDepositionCodes()){
			 if(depositionCodeDTO.getDepositionCode().equals(depositionCodeID)){
				ind = depositionCodeDTO.isClientChargedInd();
				break;
			 }
		 }
		return ind;
	}
	
	
	/**@param addonDto 
	 * @Description : 
	 *   1) set masked account no and token in payment.
	 *   2) save Encrypted credit card based on switch.
	 * @param addPayment
	 * @param tokenizeResponse
	 * @return
	 */
	private SOAdditionalPayment updateTokenMaskedAccountNo(AddonServicesDTO addonDto, SOAdditionalPayment addPayment, TokenizeResponse tokenizeResponse) {
		String encryptFlag = null;
		boolean encrypted = false;
		try{
		  if(null!= addonDto && addonDto.getPaymentRadioSelection().equals(OrderConstants.UPSELL_PAYMENT_TYPE_CREDIT)){
			/**in edit so completion, we should not call tokenize service if
		     * the user havent made any changes in CC num. i.e CC number is still in masked format */
	         String CCNum = addonDto.getCreditCardNumber();
	         encryptFlag = serviceOrderBo.getAdditionalPaymentEncryptFlag(CommonConstants.ADDITIONAL_PAYMENT_ENCRYPTION_FLAG);
	         if(StringUtils.isNotBlank(CCNum) && (!StringUtils.contains(CCNum,CommonConstants.SPECIAL_CHARACTER))){
	        	     if(StringUtils.isNotBlank(encryptFlag) && encryptFlag.equalsIgnoreCase(CommonConstants.CC_ENCRYPTION_ON)){
	        	    	 encrypted = encryptCreditCardInfoTokenizeFailed(addPayment);
	 	        	 }// if encrypt switch is OFF, setting the CC number as null
	        	     else{
	 	        		addPayment.setCreditCardNumber(null);
	 	        	 }
	        	     //Setting values based on tokenised Response
	        	     if(null!= tokenizeResponse){
	        		    addPayment.setMaskedAccountNumber(tokenizeResponse.getMaskedAccount());
	        		    addPayment.setToken(addonDto.getTokenizeCardNumber());
	        		    addPayment.setResponseXML(tokenizeResponse.getResponseXML());
	        	     }//Setting encrypted credit card no irrespective of flag value
	        	     else{
	        	    	 if(!encrypted){
	        	    	   encryptCreditCardInfo(addPayment);
	        	    	 }
	        	     }
	         }// Edit Scenario with either masked account no or clear/masked account no 
	         else{
	        	 //Setting masked account no and token
	        	  logger.info("Details delegate  addonDto.getMaskedCardNumber() : " + addonDto.getMaskedCardNumber());
			    	logger.info("Details delegate  addonDto.getTokenizeCardNumber() : " + addonDto.getTokenizeCardNumber());
			    	logger.info("Details delegate  addonDto.getResponseXML() : " + addonDto.getResponseXML());
	        	// addPayment.setMaskedAccountNumber(addonDto.getMaskedAccountNo());
	        	// addPayment.setToken(addonDto.getToken());
		        	addPayment.setMaskedAccountNumber(addonDto.getMaskedCardNumber());
		        	addPayment.setToken(addonDto.getTokenizeCardNumber());
	        	 if(StringUtils.isNotBlank(encryptFlag) && encryptFlag.equalsIgnoreCase(CommonConstants.CC_ENCRYPTION_ON)){
	        		 addPayment= setEncryptedCreditCard(addonDto,addPayment);
	 	         }else{
	 	        	//if encrypt switch is OFF, setting the CC number as null
	 	        	addPayment.setCreditCardNumber(null);	
	 	         }
	         }
	       }
		}catch (Exception e) {
			logger.info("Exception in setting masked account no and token"+ e.getMessage());
			e.printStackTrace();
		}
		return addPayment;
	}

	/**@Descriprtion: setting encrypted credit card no.
	 * @param addonDto
	 * @param addPayment
	 * @return
	 */
	private SOAdditionalPayment setEncryptedCreditCard(AddonServicesDTO addonDto, SOAdditionalPayment addPayment) {
		if(null!=addonDto){
			addPayment.setCreditCardNumber(addonDto.getEnCreditCardNo());
		}
		return addPayment;
	}

	/**@Description: Tokenizing pre authorized credit card provided by provider 
	 * @param userName
	 * @param addonDto
	 * @param additionalPayment
	 * @return 
	 * @throws Exception 
	 */
	private TokenizeResponse tokenizeCreditCard(String userName, AddonServicesDTO addonDto) throws Exception {
		logger.info("Details delegate calling  : error");
		TokenizeResponse tokenizeResponse = null;
		try{
			if(null!= addonDto && addonDto.getPaymentRadioSelection().equals(OrderConstants.UPSELL_PAYMENT_TYPE_CREDIT)){
			 /**in edit so completion, we should not call tokenize service if
			  * the user havent made any changes in CC num. i.e CC number is still in masked format */
        	 String CCNum = addonDto.getCreditCardNumber();
        	 if(StringUtils.isNotBlank(CCNum) && (!StringUtils.contains(CCNum,CommonConstants.SPECIAL_CHARACTER))){
        		 tokenizeResponse = hsTokenServiceCreditCardBo.tokenizeHSSTransaction(addonDto.getCreditCardNumber(),userName);
        	  }
			}
		}catch (Exception e) {
			logger.info("Caught Exception in tokenizing credit card"+ e.getMessage());
			e.printStackTrace();
			throw e;
			/**This method is written to set masked account no and token in response in case of web service failure.
			This will be removed once the web service SSL implementation is fixed for DEV**/
		    //tokenizeResponse = setTestTokenAndMaskedAccountNo(addonDto.getCreditCardNumber());
		}
		return tokenizeResponse;
	}

	

	private void assignProvider(com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder,Integer providerId,List<Parameter> parameters)throws BusinessServiceException {
		Contact vendorContact =null;
		
		if(null != providerId){
				Long resourceId = new Long(providerId.longValue());
				serviceOrder.setAssignmentType("PROVIDER");
				serviceOrder.setAcceptedProviderResourceId(resourceId);
			  try {
	              vendorContact = resourceBO.getVendorResourceContact(providerId.intValue());
	          } catch (BusinessServiceException bse) {
	          	throw bse;
	          }
	          SOContact contact = OFUtils.mapContact(vendorContact);
	          serviceOrder.addContact(contact);
	          SOLocation soLocation = OFUtils.mapLocation(vendorContact);
	          serviceOrder.addLocation(soLocation);
	          parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_ACCEPTED_PROVIDER_STATE,vendorContact.getStateCd()));
		}
	}
	
	
	public ProcessResponse completeSO(SOCompleteCloseDTO soCompDto)
			throws BusinessServiceException {
		String soId = "";
		String resolutionDs = "";
		int EntityId = 0;
		String finalPriceLabor = "0.0";
		String finalPriceParts = "0.0";
		ProcessResponse pResp = null;

		if (soCompDto == null)
			return pResp;
		int errCnt = 0;
		/*InitialPriceDetailsVO initialPrice=new InitialPriceDetailsVO();
		try {
			initialPrice=getServiceOrderBo().getInitialPrice(soCompDto.getSoId());
		} catch (com.newco.marketplace.exception.BusinessServiceException e) {
			e.printStackTrace();
		}
		soCompDto.setSoInitialMaxLabor(initialPrice.getInitialLaborPrice()+"");
		soCompDto.setPartSpLimit(initialPrice.getInitialPartsPrice());*/
		if(soCompDto.getIncompleteService()==OrderConstants.SERVICE_INCOMPLETE)
		{
			 soCompDto.setSoMaxLabor(soCompDto.getIncompletesoMaxLabor());
        	 soCompDto.setSoInitialMaxLabor(soCompDto.getIncompletesoInitialMaxLabor());
        	 soCompDto.setResComments(soCompDto.getIncompleteresComments());
        	 soCompDto.setFinalPartPrice(soCompDto.getIncompletefinalPartPrice());
        	 soCompDto.setFinalLaborPrice(soCompDto.getIncompletesoMaxLabor());
        	 
			
		}
		else
		{	
			//SPM-1356:Fetching the decrypted credit card number from db for validation if the 
			//credit card number in completion tab is not edited.
			if(null != soCompDto.getAddonServicesDTO() && null != soCompDto.getAddonServicesDTO().getEditOrCancel() 
					&& ("edit").equals(soCompDto.getAddonServicesDTO().getEditOrCancel())){
				AdditionalPaymentVO additionalPayment = null;
				try{
					additionalPayment = upsellBO.getAdditionalPaymentInfo(soCompDto.getSoId(),Boolean.TRUE);
				}catch (BusinessServiceException e){
					logger.error("Exception in getAdditionalPaymentInfo - "+e);
				}
				if(null != additionalPayment && null != additionalPayment.getCardNo()){
					soCompDto.getAddonServicesDTO().setCreditCardNumber(additionalPayment.getCardNo());
					soCompDto.getAddonServicesDTO().setEnCreditCardNo(additionalPayment.getEncryptedCardNo());
					soCompDto.getAddonServicesDTO().setMaskedAccountNo(additionalPayment.getMaskedAccountNo());
					soCompDto.getAddonServicesDTO().setToken(additionalPayment.getToken());
					soCompDto.getAddonServicesDTO().setTokenizedMasked(additionalPayment.isTokenizedANdMasked());
				}
			}
			soCompDto.validate();
			if(soCompDto.isValidateTimeOnsite()){
				SOOnsiteVisitVO onsiteVisitVO = validateTimeOnsite(soCompDto);
				soCompDto.validateTimeOnsiteArrivalDeparture(onsiteVisitVO);
			}
			soCompDto.validateProviderAssignment();
			List<String> docsToUpload = new ArrayList<String>();
			List<BuyerDocumentTypeVO> buyerDocumentTypeVOList = new ArrayList<BuyerDocumentTypeVO>();
			Integer buyerid = null;
			if (null != soCompDto.getBuyerID()) {
				buyerid = Integer.parseInt(soCompDto.getBuyerID());
			}
			buyerDocumentTypeVOList = documentBO.retrieveDocTypesByBuyerId(
					buyerid, COMPLETION_SOURCE);
			if (null != buyerDocumentTypeVOList
					&& buyerDocumentTypeVOList.size() > 0) {
				docsToUpload = validateMandatoryDocuments(soCompDto,
						buyerDocumentTypeVOList);
				soCompDto.validateMandatoryDocs(docsToUpload);
			}
		}
		
		if (soCompDto.getErrors() != null) {
			errCnt = soCompDto.getErrors().size();
		}
		if (errCnt > 0) {
			pResp = new ProcessResponse();
			pResp.setCode(USER_ERROR_RC);
			pResp.setMessage("");
		} else {
			
			soId = soCompDto.getSoId();
			
			
			if (ofHelper.isNewSo(soId)) {
				ProcessResponse response=completeServiceOrder(soCompDto);
				//check tokenization error occurs in response,set the error in model for displaying in FE.
				if(null!= response && StringUtils.isNotBlank(response.getCode())
						&&StringUtils.equals(response.getCode(), TOKENIZATION_ERROR_CODE)){
					response.setCode(USER_ERROR_RC);
					response.setMessage("");
					soCompDto.setTokenizationErrorMessage();
				}
				//SL-20926
				if(null!= response && StringUtils.isNotBlank(response.getCode())
						&& StringUtils.equalsIgnoreCase(response.getCode(), ADDON_COMPLETE_ERROR)){
					response.setCode(USER_ERROR_RC);
					response.setMessage("");
					soCompDto.setAddonError();
				}
				return response;
			}
	
			ServiceOrder serviceOrder = getServiceOrderBo().getServiceOrder(soId);
			double serviceFeePercentage = 0.0;
			try {
				serviceFeePercentage = promoBO.getPromoFee(
						serviceOrder.getSoId(), serviceOrder.getBuyerId().longValue(),
						PromoConstants.SERVICE_FEE_TYPE);
			} catch (com.newco.marketplace.exception.BusinessServiceException e) {
				e.printStackTrace();
			}
	
			resolutionDs = soCompDto.getResComments();
			EntityId = soCompDto.getEntityId();
			finalPriceLabor = soCompDto.getFinalLaborPrice();
			finalPriceParts = soCompDto.getFinalPartPrice();
	
			// Convert list of Part DTOs to the format we need later in
			// processCompleteSO().
			List<Part> parts = convertParts(soCompDto);

			// insert/update add on info
			completeUpsell(soCompDto, serviceFeePercentage);

			SecurityContext securityContext = getSecurityContext();
			pResp = getServiceOrderBo().processCompleteSO(soId, resolutionDs,
					EntityId, Double.parseDouble(finalPriceParts),
					Double.parseDouble(finalPriceLabor), parts,
					soCompDto.getBuyerRefs(), securityContext);
			if (pResp.isSuccess()
					&& isSOWithAddOnsAndCheck(soCompDto.getAddonServicesDTO())) {
				List<String> tmpMessages = pResp.getMessages();
				// substatus to Awaiting Payment if payment is done by check
				// for Addon Services
				Integer subStatusId = OrderConstants.CONFIRM_ADDON_FUNDS_SUBSTATUS;
				pResp = getServiceOrderBo().updateSOSubStatus(soId,
						subStatusId, securityContext);
				if (pResp.getMessages() == null)
					pResp.setMessages(tmpMessages);
				else
					pResp.getMessages().addAll(tmpMessages);

			}
		}

		return pResp;
	}
	

	
	/**
	 * Description:Method to check if time onsite entries are present before completing the order
	 * 
	 */
	private SOOnsiteVisitVO validateTimeOnsite(SOCompleteCloseDTO soCompDto) throws BusinessServiceException{
		SOOnsiteVisitVO onsiteVisitVO = new SOOnsiteVisitVO();
		onsiteVisitVO = timeOnSiteVisitBO.validateTimeOnsiteArrivalDeparture(soCompDto.getSoId());
		return onsiteVisitVO;
	}
	/**
	 * Description: verify that mandatory documents are uploaded to the so
	 * The document types are fetched from the table buyer_upload_doc_type
	 * 
	 * @param SOCompleteCloseDTO
	 */
	private List<String> validateMandatoryDocuments(SOCompleteCloseDTO soCompDto,List<BuyerDocumentTypeVO> buyerDocumentTypeVOList) 
	{
		
		DocumentVO documentVO = new DocumentVO();
		documentVO.setSoId(soCompDto.getSoId());
		Integer buyerId = null;
		if(null != soCompDto.getBuyerID()){
			buyerId = Integer.parseInt(soCompDto.getBuyerID());
		}
		documentVO.setBuyerId(buyerId);
		List<DocumentVO> documentVoList = new ArrayList<DocumentVO>();
		List<String> soDocTitles = new ArrayList<String>();
		List<String> mandatoryDocTitles = new ArrayList<String>();
		List<String> docsToUpload = new ArrayList<String>();
		try {
			documentVoList = documentBO.getRequiredDocuments(documentVO);
			
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(DocumentVO obj:documentVoList)
		{
			soDocTitles.add(obj.getTitle());
		}
		
		for(BuyerDocumentTypeVO obj:buyerDocumentTypeVOList)
		{
			if(obj.getMandatoryInd()==1)
			{
				mandatoryDocTitles.add(obj.getDocumentTitle());
			}
		}
		for(String docTitle:mandatoryDocTitles){
			if(!soDocTitles.contains(docTitle)){
				docsToUpload.add(docTitle);
			}
		}
		if((soCompDto.getPermitTaskList()!=null && soCompDto.getPermitTaskList().size()>0) ||(soCompDto.getPermitInd()!=null && soCompDto.getPermitInd().equals(1))){
			if(!soDocTitles.contains("Proof of Permit")){
					docsToUpload.add("Proof of Permit");
			}
		}
		if(soCompDto.getInvoiceParts() != null && soCompDto.getInvoiceParts().size()>0 && 3000 == buyerId){
			List<ProviderInvoicePartsVO> invoiceParts = soCompDto.getInvoiceParts();
			boolean nonTruckStockInd =false;
			if(null !=  invoiceParts && !invoiceParts.isEmpty()){
				for(ProviderInvoicePartsVO providerInvoiceParts : invoiceParts){
					String source = providerInvoiceParts.getSource();
					if(OrderConstants.INSTALLED.equalsIgnoreCase(providerInvoiceParts.getPartStatus()) && null != source && !OrderConstants.TRUCK_STOCK.equals(source)){
						nonTruckStockInd = true;
						break;
					}
				}
			}
			
			if(nonTruckStockInd && !soDocTitles.contains("Parts Invoice")){
					docsToUpload.add("Parts Invoice");
			}
		}
		if(docsToUpload.size()>0){
			if(OrderConstants.BUYERIDOFSEARS.equals(buyerId)){
				validateRequiredDocuments(soCompDto,documentVoList,docsToUpload);
			}
		}
		
		// Changes may not be needed : to be removed
			//Priority 5B changes
			//validate model & serial image if the respective checkboxes are checked
			/*if(null != buyerId && OrderConstants.INHOME_BUYER.equalsIgnoreCase(buyerId.toString())){
				soCompDto.validateModelSerialImage(soDocTitles);
			}*/
		return docsToUpload;
		
	}
	

	/**
	 * Description: verify Signed Customer Copy was attached for all
		orders for Buyer ID 1000.verify proof of permit was attached if
		permit is part of order.
	 * 
	 * @param SOCompleteCloseDTO
	 */
	
	private List<String> validateRequiredDocuments(SOCompleteCloseDTO soCompDto,List<DocumentVO> documentVoList,List<String> docsToUpload) 
	{
		try
		{
			List<Integer> docCategoryIds=new ArrayList<Integer>();
			for(DocumentVO obj:documentVoList)
			{
				docCategoryIds.add(obj.getDocCategoryId());
			}
			if(docCategoryIds.contains(new Integer(Constants.DocumentTypes.CATEGORY.SIGNED_CUSTOMER_COPY))&&
					docsToUpload.contains("Signed Customer Copy Including Waiver of Lien")){
				docsToUpload.remove("Signed Customer Copy Including Waiver of Lien");
			}
			//check if permit is part of order
			if((soCompDto.getPermitTaskList()!=null && soCompDto.getPermitTaskList().size()>0) ||(soCompDto.getPermitInd()!=null && soCompDto.getPermitInd().equals(1))){
				if(docCategoryIds.contains(new Integer(Constants.DocumentTypes.CATEGORY.PROOF_OF_PERMIT))&&
							docsToUpload.contains("Proof of Permit")){
						docsToUpload.remove("Proof of Permit");
					}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return docsToUpload;
	}

	/**
	 * Description: Persist Addon and their payment info
	 * 
	 * @param form
	 * @param serviceFeePercentage
	 */
	private void completeUpsell(SOCompleteCloseDTO form,
			double serviceFeePercentage) {
		// If there are addons for this Service Order persist the info
		List<ServiceOrderAddonVO> addons = upsellBO.getAddonsbySoId(form
				.getSoId());

		if (addons == null)
			return;

		if (addons.size() == 0)
			return;

		// To delete the additional payment info if the the addons for this SO has been removed
		if (null != form.getAddonServicesDTO()){
			if ((null == form.getAddonServicesDTO().getHiddenEndCustomerSubtotalTotal()) || (form.getAddonServicesDTO().getAddonCheckbox().equals("false"))){
				upsellBO.deleteAdditionalPaymentInfo(form.getSoId());
			}
		}

		completeUpsellServices(form, serviceFeePercentage);

	}

	/**
	 * Description: Persist Addons
	 * 
	 * @param form
	 * @param serviceFeePercentage
	 */
	private void completeUpsellServices(SOCompleteCloseDTO form,
			double serviceFeePercentage) {
		if (form == null)
			return;

		if (form.getAddonServicesDTO() == null)
			return;

		if (form.getAddonServicesDTO().getAddonServicesList() == null)
			return;

		if (form.getAddonServicesDTO().getAddonServicesList().size() == 0)
			return;

		List<ServiceOrderAddonVO> addons = upsellBO.getAddonsbySoId(form
				.getSoId());
		List<ServiceOrderAddonVO> selectedAddons = new ArrayList<ServiceOrderAddonVO>();

		// Loop thru all the addons and see if the quantity has been updated.
		int i = 0;
		boolean change = false;
		for (AddonServiceRowDTO dto : form.getAddonServicesDTO()
				.getAddonServicesList()) {
			change = false;
			if (addons.get(i) == null)
				continue;

			if (dto.getQuantity() >= 0) {
				addons.get(i).setQuantity(dto.getQuantity());

				if (addons.get(i).isMiscInd()) {
					addons.get(i).setDescription(dto.getDescription());
					addons.get(i).setRetailPrice(dto.getEndCustomerCharge());
					addons.get(i)
							.setServiceFee(
									MoneyUtil.getRoundedMoneyCustom((dto
											.getQuantity() * (dto
													.getEndCustomerCharge()
													* (1 - dto.getMargin()))

									)
											* serviceFeePercentage));
				} else {
					addons.get(i)
							.setServiceFee(
									MoneyUtil.getRoundedMoneyCustom((dto
											.getQuantity() * (dto
													.getEndCustomerCharge()
													* (1 - dto.getMargin())))
											* serviceFeePercentage));
				}
				change = true;
			}

			if (change)
				selectedAddons.add(addons.get(i));

			i++;
		}

		upsellBO.updateAddonsQty(selectedAddons);
		@SuppressWarnings("unused") //FIXME Why is this being called?
		List<ServiceOrderAddonVO> selectedAddonsServiceFees = calculateAndSpreadAddonServiceFees(
				upsellBO.getAddonsbySoId(form.getSoId()), serviceFeePercentage);
		upsellBO.updateAddonsQty(selectedAddons);

		// Only save additional payment information if at least one addon sku
		// was selected and payment has been entered.
		if (selectedAddons != null && selectedAddons.size() > 0
				&& form.getAddonServicesDTO().getAddonCheckbox().equals("true")) {
			completeUpsellAdditionalPaymentInfo(form);
		}
	}

	/**
	 * Description: Subtract the service fee from the total of the upsell items
	 * instead of each upsell item seperately
	 * 
	 * @param addons
	 * @param serviceFeePercentage
	 * @return
	 */
	private List<ServiceOrderAddonVO> calculateAndSpreadAddonServiceFees(
			List<ServiceOrderAddonVO> addons, double serviceFeePercentage) {
		Double totAddons = 0.0;
		for (ServiceOrderAddonVO soAddonVO : addons) {
			totAddons = soAddonVO.getQuantity() * soAddonVO.getRetailPrice()
					* (1 - soAddonVO.getMargin());
		}
		Double totAddonServiceFee = MoneyUtil.getRoundedMoneyCustom(totAddons
				* serviceFeePercentage);
		for (int i = 0; i < addons.size(); i++) {
			if (i < addons.size() - 1) {
				addons.get(i)
						.setServiceFee(
								MoneyUtil.getRoundedMoneyCustom((addons.get(i)
										.getQuantity() * (addons.get(i)
												.getRetailPrice()
												* (1 - addons.get(i)
														.getMargin())))
										* serviceFeePercentage));
				totAddonServiceFee = totAddonServiceFee
						- addons.get(i).getServiceFee();
			} else {
				addons.get(i).setServiceFee(totAddonServiceFee);
			}
		}
		return addons;
	}

	/**
	 * Description: Persist Addon's payment info
	 * 
	 * @param form
	 */
	private void completeUpsellAdditionalPaymentInfo(SOCompleteCloseDTO form) {
		if (form == null)
			return;

		if (form.getAddonServicesDTO() == null)
			return;

		// Handle payment info
		AdditionalPaymentVO addPaymentVO = new AdditionalPaymentVO();
		String paymentType = form.getAddonServicesDTO()
				.getPaymentRadioSelection();
		String soId = form.getSoId();
		addPaymentVO.setSoId(soId);
		if (paymentType != null) {
			if (paymentType.equals(OrderConstants.UPSELL_PAYMENT_TYPE_CHECK)) {
				addPaymentVO.setCheckNo(form.getAddonServicesDTO()
						.getCheckNumber()
						+ "");
				addPaymentVO.setPaymentAmount(form.getAddonServicesDTO()
						.getCheckAmount());
				addPaymentVO.setPaymentType(paymentType);
				if (form.getAddonServicesDTO().getCheckAmount() != null
						&& form.getAddonServicesDTO().getCheckAmount() >= 0.0)
					upsellBO.insertPaymentInfobySoId(addPaymentVO);
			} else if (paymentType
					.equals(OrderConstants.UPSELL_PAYMENT_TYPE_CREDIT)) {
				addPaymentVO.setCardExpireMonth(form.getAddonServicesDTO()
						.getSelectedMonth());
				addPaymentVO.setCardExpireYear(form.getAddonServicesDTO()
						.getSelectedYear());
				addPaymentVO.setCardNo(form.getAddonServicesDTO()
						.getCreditCardNumber()
						+ "");
				addPaymentVO.setCardType(form.getAddonServicesDTO()
						.getSelectedCreditCardType()
						+ "");
				addPaymentVO.setPaymentAmount(form.getAddonServicesDTO()
						.getAmtAuthorized());
				addPaymentVO.setPaymentType(getCreditCardCode(form
						.getAddonServicesDTO().getSelectedCreditCardType()));
				addPaymentVO.setAuthNumber(form.getAddonServicesDTO()
						.getPreAuthNumber());
				if (form.getAddonServicesDTO().getAmtAuthorized() != null
						&& form.getAddonServicesDTO().getAmtAuthorized() > 0.0)
					upsellBO.insertPaymentInfobySoId(addPaymentVO);
			} else {
				logger
						.error("completeUpsellAdditionalPaymentInfo() - Unknown payment type coming from radio selection.");
			}
		}
	}

	// TODO eventually do this mapping with the credit card lookup table.
	private String getCreditCardCode(int intCode) {
		switch (intCode) {
		/*SLT-2591 and SLT-2592: Disable Amex
		 * case CreditCardConstants.CARD_ID_AMEX:
			return CreditCardConstants.CARD_ID_AMEX_STR;*/
		case CreditCardConstants.CARD_ID_DISCOVER:
			return CreditCardConstants.CARD_ID_DISCOVER_STR;
		case CreditCardConstants.CARD_ID_VISA:
			return CreditCardConstants.CARD_ID_VISA_STR;
		case CreditCardConstants.CARD_ID_MASTERCARD:
        case CreditCardConstants.CARD_ID_SEARS_MASTERCARD:
			return CreditCardConstants.CARD_ID_MASTERCARD_STR;
		case CreditCardConstants.CARD_ID_SEARS:
			return CreditCardConstants.CARD_ID_SEARS_CHARGE_STR;
		case CreditCardConstants.CARD_ID_SEARS_COMMERCIAL:
			return CreditCardConstants.CARD_ID_COMMERCIAL_ONE_STR;
		case CreditCardConstants.CARD_ID_SEARS_PLUS:
			return CreditCardConstants.CARD_ID_SEARS_CHARGE_PLUS_STR;
		}

		logger.error("getCreditCardCode() - Unknown credit card code: "
				+ intCode);
		return "";
	}

	// Convert list of Part DTOs to the format we need later in
	// processCompleteSO().
	private List<Part> convertParts(SOCompleteCloseDTO soCompDto) {

		List<Part> parts = new ArrayList<Part>();

		if (soCompDto == null)
			return parts;

		ArrayList<SOPartsDTO> soParts = null;
		soParts = soCompDto.getPartList();
		if (soParts == null)
			return parts;

		soParts = soCompDto.getPartList();
		String soId = soCompDto.getSoId();

		for (int i = 0; i < soParts.size(); i++) {
			SOPartsDTO soPartsDto = soParts.get(i);
			Part part = new Part();
			part.setSoId(soId);
			part.setPartId(soPartsDto.getPartId());
			Carrier returnCarrier = new Carrier();
			if (soPartsDto.getCoreReturnCarrierId() != null) {
				returnCarrier.setCarrierId((soPartsDto.getCoreReturnCarrierId()
						.intValue() == -1) ? null : soPartsDto
						.getCoreReturnCarrierId());
			}
			if (soPartsDto.getCoreReturnTrackingNumber() != null) {
				returnCarrier.setTrackingNumber(soPartsDto
						.getCoreReturnTrackingNumber());
			}
			part.setReturnCarrier(returnCarrier);
			parts.add(part);
		}

		return parts;
	}

	private boolean isSOWithAddOnsAndCheck(AddonServicesDTO addonServicesDTO) {
		boolean soWithAddonAndCheck = false;
		if (addonServicesDTO != null && "true".equals(addonServicesDTO.getAddonCheckbox())) {
			String paymentType = addonServicesDTO.getPaymentRadioSelection();
			if (paymentType != null
					&& paymentType
							.equals(OrderConstants.UPSELL_PAYMENT_TYPE_CHECK)) {
				return true;
			}
		}
		return soWithAddonAndCheck;
	}

	public ProcessResponse acceptConditionalOffer(String soId,
			Integer resourceId, Integer vendorId, Integer resReasonId,
			java.util.Date startDate, java.util.Date endDate, String startTime,
			String endTime, Double spendLimit, Integer buyerId) {
		SecurityContext securityContext = getSecurityContext();
		// web service call for NPS update
		String assignee="";
	    if(ofHelper.isNewSo(soId)){
	    	
	    	//get the counter Offer Details corresponding the vendor & resource Id
	    	BuyerOutboundNotificationVO buyerOutboundNotificationVO=null;
	    	BuyerOutboundNotificationVO counterOffer=new BuyerOutboundNotificationVO();
        	RequestRescheduleInfo requestRescheduleInfo=new RequestRescheduleInfo();
        	 counterOffer.setVendorId(vendorId);
             counterOffer.setResourceId(resourceId);
             counterOffer.setSoId(soId);
	    	RequestOrder order=new RequestOrder();
	    	boolean isRescheduleModify=false;
	    	if(Constants.SEARS_BUYER_ID.equals(buyerId))
	    	{
	    	try {
				buyerOutboundNotificationVO = buyerOutBoundNotificationService.getCounterOfferDetails(counterOffer);
	    	}
	    	catch(BusinessServiceException e)
	    	{
				logger.error("error in fetching schedule Details"+e);
	    	}
	    	}
	    	
	    	ProcessResponse pr=
	    	newOFAcceptOrder(soId, false, vendorId.longValue(), resourceId.longValue(), null, securityContext, SignalType.ACCEPT_CONDITIONAL_OFFER,assignee);
	  
	    	if(pr.getCode().equals(ServiceConstants.VALID_RC))
	    	{
	    	if(null!=buyerOutboundNotificationVO)
			{
				
				// logic to check whether the rescheduled & scheduled date matches.
				
				if(!buyerOutboundNotificationVO.getServiceOrderScheduleFromDate().equals(buyerOutboundNotificationVO.getServiceOrderRescheduleFromDate()))
				{
					isRescheduleModify=true;	

				}
				else if(!buyerOutboundNotificationVO.getServiceOrderScheduleFromTime().equals(buyerOutboundNotificationVO.getServiceOrderRescheduleFromTime()))
				{
					isRescheduleModify=true;
				}
				else if( (null==buyerOutboundNotificationVO.getServiceOrderScheduleToTime() && null!=buyerOutboundNotificationVO.getServiceOrderRescheduleToTime())
						|| (null!=buyerOutboundNotificationVO.getServiceOrderScheduleToTime() && null==buyerOutboundNotificationVO.getServiceOrderRescheduleToTime())
						)
				{
					isRescheduleModify=true;
				}
				else if(! ((null==buyerOutboundNotificationVO.getServiceOrderScheduleToTime() && null==buyerOutboundNotificationVO.getServiceOrderRescheduleToTime())
						|| (buyerOutboundNotificationVO.getServiceOrderScheduleToTime().equals(buyerOutboundNotificationVO.getServiceOrderRescheduleToTime()))))
				{
					isRescheduleModify=true;
				}
				 if(isRescheduleModify)
					{
					  
					 // get the rescheduled date in service order Timezone.
					 TimeZone timeZone=TimeZone.getTimeZone(buyerOutboundNotificationVO.getTimeZone());
						Calendar startDateTime = TimeChangeUtil.getCalTimeFromParts(buyerOutboundNotificationVO.getServiceOrderRescheduleFromDate(), buyerOutboundNotificationVO.getServiceOrderRescheduleFromTime(),TimeZone.getTimeZone("GMT"));
						Date serviceFromDate = TimeChangeUtil.getDate(startDateTime, timeZone);
						SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
						String fromDate=formatter.format(serviceFromDate);
						String serviceTimeStart = TimeChangeUtil.getTimeString(startDateTime, timeZone);
						Calendar endDateTime = null;
						if(null!=buyerOutboundNotificationVO.getServiceOrderRescheduleToDate() && null!=buyerOutboundNotificationVO.getServiceOrderRescheduleToTime())
						endDateTime=TimeChangeUtil.getCalTimeFromParts(buyerOutboundNotificationVO.getServiceOrderRescheduleToDate(), buyerOutboundNotificationVO.getServiceOrderRescheduleToTime(),TimeZone.getTimeZone("GMT"));
						String serviceTimeEnd ="";
						if(null!=endDateTime)
							serviceTimeEnd=TimeChangeUtil.getTimeString(endDateTime, timeZone);
						//set reschedule information
						order.setServiceScheduleDate(fromDate);
						order.setServiceScheduleFromTime(serviceTimeStart);
						if(StringUtils.isBlank(serviceTimeEnd))
						{
							order.setServiceScheduletoTime(serviceTimeStart);
						}
						else
						{
						order.setServiceScheduletoTime(serviceTimeEnd);
						}
						order.setServiceOrderRescheduledFlag(BuyerOutBoundConstants.RESHEDULE_FLAG_YES);
						
						//get the current date in server timezone.
						Calendar calender=Calendar.getInstance();
						SimpleDateFormat timeFormatter=new SimpleDateFormat("hh:mm a");
						String modifiedfromDate=timeFormatter.format(calender.getTime());
						Calendar modificationDateTime = TimeChangeUtil.getCalTimeFromParts(calender.getTime(),modifiedfromDate,calender.getTimeZone());
						Date modificationDate = TimeChangeUtil.getDate(modificationDateTime, timeZone);
						String modificationDateValue=formatter.format(modificationDate);
						String modificationTime = TimeChangeUtil.getTimeString(modificationDateTime, timeZone);
						//get the mandatory counter offer reason codes
						List <String> reasonCodes=buyerOutBoundNotificationService.
						getResonCodeDetailsforCounterOffer(buyerOutboundNotificationVO.getRoutedProviderId());
						//get the reasoncode Description by appending all reasoncodes.
						String reasonCodeValue="";
						int count=0;
						for(String reasonCode:reasonCodes)
						{
								if(count==0)
								reasonCodeValue=reasonCode;
								else
								reasonCodeValue=reasonCodeValue+","+reasonCode;
	
							count=count+1;
						}
						//set current date,reason code,modified resourceId
			        	requestRescheduleInfo.setReschedCancelModificationDate(modificationDateValue);
			        	requestRescheduleInfo.setReschedCancelModificationTime(modificationTime);
			        	requestRescheduleInfo.setRescheduleModificationID(resourceId.toString());
			        	requestRescheduleInfo.setRescheduleReasonCode(reasonCodeValue);
			        	requestRescheduleInfo.setRescheduleRsnCdDescription(reasonCodeValue);
					}
				
				
				
			}
	    	
	    	// call service by passing the request Object.
            if(isRescheduleModify)
			{
			RequestMsgBody requestMsgBody=new RequestMsgBody();
        	RequestOrders orders=new RequestOrders();
        	List<RequestOrder> orderList=new ArrayList<RequestOrder>();
        	RequestReschedInformation requestReschedInformation=new RequestReschedInformation();
        	List<RequestRescheduleInfo> requestRescheduleInf=new ArrayList<RequestRescheduleInfo>();
        	requestRescheduleInf.add(requestRescheduleInfo);
        	requestReschedInformation.setRequestRescheduleInf(requestRescheduleInf);
        	order.setRequestReschedInformation(requestReschedInformation);
        	orderList.add(order);
        	orders.setOrder(orderList);
        	requestMsgBody.setOrders(orders);
        	try {
        		BuyerOutboundFailOverVO failoverVO = buyerOutBoundNotificationService.callService(requestMsgBody, soId);
       		 if(null!=failoverVO)
       			 buyerOutBoundNotificationJMSService.callJMSService(failoverVO);
			} catch (BusinessServiceException e) {
				logger.error("Error in NPS update for reschedule"+e);
			}
        	
			}
	    }
	    	return pr;
	    }
		
		ProcessResponse pr = getServiceOrderBo().acceptConditionalOffer(soId,
				resourceId, vendorId, resReasonId, startDate, endDate,
				startTime, endTime, spendLimit, buyerId, true, securityContext);
		this.serviceOrderBo.sendallProviderResponseExceptAccepted(soId,
				securityContext);

		return pr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.newco.marketplace.web.delegates.ISODetailsDelegate#
	 * acceptConditionalOfferForGroup(java.lang.String, java.lang.Integer,
	 * java.lang.Integer, java.lang.Integer, java.util.Date, java.util.Date,
	 * java.lang.String, java.lang.String, java.lang.Double, java.lang.Integer)
	 */
	public ProcessResponse acceptConditionalOfferForGroup(String groupId,
			Integer resourceId, Integer vendorId, Integer resReasonId,
			java.util.Date startDate, java.util.Date endDate, String startTime,
			String endTime, Double spendLimit, Integer buyerId) {
		logger
				.info("In details delegate:::: acceptConditionalOfferForGroup() ");
		SecurityContext securityContext = getSecurityContext();
		String assignee = "";
		if (ofHelper.isNewGroup(groupId)){
			List<String> soIds;
	    	BuyerOutboundNotificationVO counterOffer=new BuyerOutboundNotificationVO();
        	RequestRescheduleInfo requestRescheduleInfo=new RequestRescheduleInfo();
        	   List<BuyerOutboundNotificationVO>	buyerOutboundNotificationList=null;
        	   boolean isRescheduleModify=false;
   	    	if(Constants.SEARS_BUYER_ID.equals(buyerId))
   	    	{
			try {
				soIds = buyerOutBoundNotificationService.getSoIdsForGroup(groupId);
				
	        	 counterOffer.setVendorId(vendorId);
	             counterOffer.setResourceId(resourceId);
	             counterOffer.setSoIds(soIds);
		    	
		    	buyerOutboundNotificationList= buyerOutBoundNotificationService.getCounterOfferDetailsForGroup(counterOffer);
		    
		    
		    	
			} catch (BusinessServiceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
			
	    	
			
			try {
				OrderFulfillmentRequest ofRequest = getRequestForAcceptance(vendorId.longValue(), resourceId.longValue(), null, securityContext,assignee);
				OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentGroupProcess(groupId, SignalType.ACCEPT_GROUP_CONDITIONAL_OFFER, ofRequest);
				ProcessResponse pr= OFUtils.mapProcessResponse(response);
				
				if(pr.getCode().equals(ServiceConstants.VALID_RC))
						{
	   	    	RequestOrder order=new RequestOrder();
	   	    	if(null!=buyerOutboundNotificationList && buyerOutboundNotificationList.size()>0)
	   	    	{
				for(BuyerOutboundNotificationVO buyerOutboundNotificationVO: buyerOutboundNotificationList){
					isRescheduleModify=false;
				if(null!=buyerOutboundNotificationVO)
				{
					
					
					
					if(!buyerOutboundNotificationVO.getServiceOrderScheduleFromDate().equals(buyerOutboundNotificationVO.getServiceOrderRescheduleFromDate()))
					{
						isRescheduleModify=true;	

					}
					else if(!buyerOutboundNotificationVO.getServiceOrderScheduleFromTime().equals(buyerOutboundNotificationVO.getServiceOrderRescheduleFromTime()))
					{
						isRescheduleModify=true;
					}
					else if( (null==buyerOutboundNotificationVO.getServiceOrderScheduleToTime() && null!=buyerOutboundNotificationVO.getServiceOrderRescheduleToTime())
							|| (null!=buyerOutboundNotificationVO.getServiceOrderScheduleToTime() && null==buyerOutboundNotificationVO.getServiceOrderRescheduleToTime())
							)
					{
						isRescheduleModify=true;
					}
					else if(! ((null==buyerOutboundNotificationVO.getServiceOrderScheduleToTime() && null==buyerOutboundNotificationVO.getServiceOrderRescheduleToTime())
							|| (buyerOutboundNotificationVO.getServiceOrderScheduleToTime().equals(buyerOutboundNotificationVO.getServiceOrderRescheduleToTime()))))
					{
						isRescheduleModify=true;
					}
					 if(isRescheduleModify)
						{

						 TimeZone timeZone=TimeZone.getTimeZone(buyerOutboundNotificationVO.getTimeZone());
							Calendar startDateTime = TimeChangeUtil.getCalTimeFromParts(buyerOutboundNotificationVO.getServiceOrderRescheduleFromDate(), buyerOutboundNotificationVO.getServiceOrderRescheduleFromTime(),TimeZone.getTimeZone("GMT"));
							Date serviceFromDate = TimeChangeUtil.getDate(startDateTime, timeZone);
							SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
							String fromDate=formatter.format(serviceFromDate);
							String serviceTimeStart = TimeChangeUtil.getTimeString(startDateTime, timeZone);

							Calendar endDateTime = null;
							if(null!=buyerOutboundNotificationVO.getServiceOrderRescheduleToDate() && null!=buyerOutboundNotificationVO.getServiceOrderRescheduleToTime())
							endDateTime = TimeChangeUtil.getCalTimeFromParts(buyerOutboundNotificationVO.getServiceOrderRescheduleToDate(), buyerOutboundNotificationVO.getServiceOrderRescheduleToTime(),TimeZone.getTimeZone("GMT"));
							//Date serviceEndDate = TimeChangeUtil.getDate(endDateTime, timeZone);
							//String endDate=formatter.format(serviceFromDate);
							String serviceTimeEnd ="";
							if(null!=endDateTime)
							serviceTimeEnd = TimeChangeUtil.getTimeString(endDateTime, timeZone);
							order.setServiceScheduleDate(fromDate);
							order.setServiceScheduleFromTime(serviceTimeStart);
							if(StringUtils.isBlank(serviceTimeEnd))
							{
								order.setServiceScheduletoTime(serviceTimeStart);
							}
							else
							{
							order.setServiceScheduletoTime(serviceTimeEnd);
							}
							order.setServiceOrderRescheduledFlag(BuyerOutBoundConstants.RESHEDULE_FLAG_YES);;
							
							Calendar calender=Calendar.getInstance();
							SimpleDateFormat timeFormatter=new SimpleDateFormat("hh:mm a");
							String modifiedfromDate=timeFormatter.format(calender.getTime());
							Calendar modificationDateTime = TimeChangeUtil.getCalTimeFromParts(calender.getTime(), modifiedfromDate,calender.getTimeZone());
							Date modificationDate = TimeChangeUtil.getDate(modificationDateTime, timeZone);
							String modificationDateValue=formatter.format(modificationDate);
							String modificationTime = TimeChangeUtil.getTimeString(modificationDateTime, timeZone);
							
							List <String> reasonCodes=buyerOutBoundNotificationService.
							getResonCodeDetailsforCounterOffer(buyerOutboundNotificationVO.getRoutedProviderId());
							String reasonCodeValue="";
							int count=0;
							for(String reasonCode:reasonCodes)
							{
									if(count==0)
									reasonCodeValue=reasonCode;
									else
									reasonCodeValue=reasonCodeValue+","+reasonCode;
		
								count=count+1;
							}
								
				        	requestRescheduleInfo.setReschedCancelModificationDate(modificationDateValue);
				        	requestRescheduleInfo.setReschedCancelModificationTime(modificationTime);
				        	requestRescheduleInfo.setRescheduleModificationID(resourceId.toString());
				        	requestRescheduleInfo.setRescheduleReasonCode(reasonCodeValue);
				        	requestRescheduleInfo.setRescheduleRsnCdDescription(reasonCodeValue);
						}
					
					
					
				}
		    	
		    	// call NPS update
	            if(isRescheduleModify)
				{
				RequestMsgBody requestMsgBody=new RequestMsgBody();
	        	RequestOrders orders=new RequestOrders();
	        	List<RequestOrder> orderList=new ArrayList<RequestOrder>();
	        	
	        	
	        	RequestReschedInformation requestReschedInformation=new RequestReschedInformation();
	        	List<RequestRescheduleInfo> requestRescheduleInf=new ArrayList<RequestRescheduleInfo>();

	        	//requestRescheduleInfo.setReschedCancelModificationDate();
	        	requestRescheduleInf.add(requestRescheduleInfo);
	        	requestReschedInformation.setRequestRescheduleInf(requestRescheduleInf);
	        	order.setRequestReschedInformation(requestReschedInformation);
	        	
	        	orderList.add(order);
	        	orders.setOrder(orderList);
	        	requestMsgBody.setOrders(orders);
	        	try {
	        		BuyerOutboundFailOverVO failoverVO = buyerOutBoundNotificationService.callService(requestMsgBody, buyerOutboundNotificationVO.getSoId());
	        		 if(null!=failoverVO)
	        			 buyerOutBoundNotificationJMSService.callJMSService(failoverVO);
				} catch (BusinessServiceException e) {
					logger.error("Error in NPS update for reschedule"+e);
				}
	        	
				}
				}
						}
				}
				return pr;
				
			} catch (Exception e) {
				ProcessResponse response = new ProcessResponse();
				response.setMessage(e.getMessage());
				return response;
			}
		}		
		ProcessResponse processResponse = new ProcessResponse();
		try {
			processResponse = orderGrpBO.acceptConditionalOfferForGroup(
					groupId, resourceId, vendorId, resReasonId, startDate,
					endDate, startTime, endTime, spendLimit, buyerId,
					securityContext);
			orderGrpBO.sendallProviderResponseExceptAcceptedForGroup(groupId,
					securityContext);
		} catch (com.newco.marketplace.exception.BusinessServiceException e) {
			setProcessResponse(processResponse, e);
		}
		return processResponse;
	}

	public ArrayList<SurveyQuesAnsVO> getSurveyResults(SurveyVO surveyVO) {
		try {
			return surveyBO.getSurveyResponse(surveyVO);
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ProcessResponse withDrawCondAcceptance(RoutedProvider routedProviders)
			throws BusinessServiceException {
		ProcessResponse pr = null;
		try {
			Integer resId = routedProviders.getResourceId();
			String soId = routedProviders.getSoId();
			Integer prResId = routedProviders.getProviderRespId();
			SecurityContext securityContext = getSecurityContext();

			if(routedProviders.isGroupedOrder())
			{
				if(ofHelper.isNewGroup(soId)){
				    com.servicelive.orderfulfillment.domain.RoutedProvider ofRoutedProvider=new com.servicelive.orderfulfillment.domain.RoutedProvider();
				    ofRoutedProvider.setProviderResourceId((long)resId);
				    Identification idfn = OFUtils.createOFIdentityFromSecurityContext(securityContext);
				    OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentGroupProcess(soId, SignalType.WITHDRAW_GROUP_CONDITIONAL_OFFER, ofRoutedProvider, idfn);
				    return OFUtils.mapProcessResponse(response);
				}
				pr = orderGrpBO.withdrawGroupedConditionalAcceptance(soId,
						resId, prResId, securityContext);
			}
			else
			{
				if(ofHelper.isNewSo(soId)){
				    com.servicelive.orderfulfillment.domain.RoutedProvider ofRoutedProvider=new com.servicelive.orderfulfillment.domain.RoutedProvider();
				    ofRoutedProvider.setProviderResourceId((long)resId);
				    Identification idfn = OFUtils.createOFIdentityFromSecurityContext(securityContext);
				    OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.WITHDRAW_CONDITIONAL_OFFER, ofRoutedProvider, idfn);
				    return OFUtils.mapProcessResponse(response);
				}
				pr = serviceOrderBo.withdrawConditionalAcceptance(soId,
					resId, prResId, securityContext);
			}
		} catch (com.newco.marketplace.exception.BusinessServiceException e) {
			e.printStackTrace();
		}
		return pr;
	}
	
	public ProcessResponse recallServiceOrderCompletion(String soId, SecurityContext ctx)
	throws BusinessServiceException {
		OrderFulfillmentResponse ordrFlflResponse = ofHelper.runOrderFulfillmentProcess(
        		soId,
        		SignalType.RECALL_COMPLETION,
        		null,
        		OFUtils.createOFIdentityFromSecurityContext(getSecurityContext()));
		return createProcResponseFromOFResponse(ordrFlflResponse, EDIT_COMPLETIONRECORD_SUCCESS, EDIT_COMPLETIONRECORD_FAILURE);
	}

	public ProcessResponse editCompletionRecordForSo(String soId,
			SecurityContext ctx) throws BusinessServiceException {
		logger
				.debug("----Start of SODetailsDelegateImpl.editCompletionRecordForSo----");
		
		if (ofHelper.isNewSo(soId)) {
			return recallServiceOrderCompletion(soId, ctx);
		}
		
		ProcessResponse pResp = null;
		pResp = getServiceOrderBo().editCompletionRecordForSo(soId, ctx);
		logger
				.debug("----End of SOMonitorDelegateImpl.editCompletionRecordForSo----");
		return pResp;
	}
	
	public ProcessResponse closeServiceOrder(String serviceOrderId, Integer buyerId, SOCompleteCloseDTO soCloseDto)
	throws BusinessServiceException {
		double finalPartsPrice = 0.0;
    	double finalLaborPrice = 0.0;
    	finalPartsPrice = Double.parseDouble(soCloseDto.getFinalPartPrice());
		finalLaborPrice = Double.parseDouble(soCloseDto.getFinalLaborPrice());
		com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder = new com.servicelive.orderfulfillment.domain.ServiceOrder();
		serviceOrder.setSoId(serviceOrderId);
		serviceOrder.setTaskLevelPricing(soCloseDto.isTaskLevelPricing());
		serviceOrder.setFinalPriceParts(new BigDecimal(finalPartsPrice).setScale(2, RoundingMode.HALF_EVEN));
		serviceOrder.setFinalPriceLabor(new BigDecimal(finalLaborPrice).setScale(2, RoundingMode.HALF_EVEN));
		logger.info("SL-16058. Starting close action");
		OrderFulfillmentResponse ordrFlflResponse = ofHelper.runOrderFulfillmentProcess(
				serviceOrderId,
				SignalType.CLOSE_ORDER,
				serviceOrder,
				OFUtils.createOFIdentityFromSecurityContext(getSecurityContext()));
		logger.info("SL-16058. Ending close action");
		return createProcResponseFromOFResponse(ordrFlflResponse, CLOSESO_SUCCESS, CLOSESO_FAILURE);
	}

	public ProcessResponse serviceOrderClose(Integer buyerId,
			String serviceOrderId, SOCompleteCloseDTO soCloseDto) throws BusinessServiceException {
		double finalPartsPrice = 0.0;
    	double finalLaborPrice = 0.0;
		finalPartsPrice = Double.parseDouble(soCloseDto.getFinalPartPrice());
		finalLaborPrice =  Double.parseDouble(soCloseDto.getFinalLaborPrice());
		if (ofHelper.isNewSo(serviceOrderId)) {
			return closeServiceOrder(serviceOrderId, buyerId, soCloseDto);
		}
		
		logger.info(serviceOrderId + " - serviceOrderClose - buyerId: "
				+ buyerId + " finalPartsPrice: " + finalPartsPrice
				+ " finalLaborPrice: " + finalLaborPrice);
		ProcessResponse pResp = null;

		SecurityContext securityContext = getSecurityContext();
		pResp = getServiceOrderBo().processCloseSO(buyerId, serviceOrderId,
				finalPartsPrice, finalLaborPrice, securityContext);
		if (pResp.isError()) {
			return pResp;
		}

		//SL-12246 Fix 
		if(buyerId.intValue() == 1000 && buyerFeatureSetBO.validateFeature(buyerId, BuyerFeatureConstants.SHC_GL_REPORTING)){
			serviceOrderCloseBo.stageUpsellPaymentAndSku(serviceOrderId);
			serviceOrderCloseBo.stageFinalPrice(serviceOrderId, finalLaborPrice + finalPartsPrice);
		}
		return pResp;
	}

	public ArrayList<ServiceOrderStatusVO> getSubStatusDetails(
			Integer statusId, Integer roleId) {
		logger
				.debug("----Start of SODetailsDelegateImpl.getSubStatusDetails----");
		@SuppressWarnings("unchecked")
		ArrayList<ServiceOrderStatusVO> serviceOrderStatusVOList = soMonitor.getSOSubStatusForStatusId(
		statusId, roleId);
		logger.debug("----End of SODetailsDelegateImpl.getSubStatusDetails----");
		return serviceOrderStatusVOList;
	}
	public ArrayList<LookupVO> getSubstatusDesc(List<Integer> id)
		throws BusinessServiceException {
		logger
				.debug("----Start of SODetailsDelegateImpl.getSubstatusDesc----");
		ArrayList<LookupVO> substatusList=null;
		try{
			substatusList = serviceOrderBo.getSubstatusDesc(
					id);
			logger.debug("----End of SODetailsDelegateImpl.getSubstatusDesc----");
		}
		catch (BusinessServiceException bse) {
			logger
					.error("SODetailsDelegateImpl:Unable to fetch the substatus list");
			throw new BusinessServiceException(bse.getMessage(), bse);
		}
		return substatusList;
	}
	
	public ArrayList<LookupVO> getRescheduleReasonCodes(Integer roleId)
		throws BusinessServiceException {
		logger
				.debug("----Start of SODetailsDelegateImpl.getRescheduleReasonCodes----");
		ArrayList<LookupVO> reasonCodeList=null;
		try{
			reasonCodeList = serviceOrderBo.getRescheduleReasonCodes(roleId);
			logger.debug("----End of SODetailsDelegateImpl.getRescheduleReasonCodes----");
		}
		catch (BusinessServiceException bse) {
			logger
					.error("SODetailsDelegateImpl:Unable to fetch the reason code list");
			throw new BusinessServiceException(bse.getMessage(), bse);
		}
		return reasonCodeList;
	}
	
	public ArrayList<LookupVO> getPermitTypes()
	throws BusinessServiceException {
	logger
			.debug("----Start of SODetailsDelegateImpl.getPermitTypes----");
	ArrayList<LookupVO> permitTypesList=null;
	try{
		permitTypesList = serviceOrderBo.getPermitTypes();
		logger.debug("----End of SODetailsDelegateImpl.getPermitTypes----");
	}
	catch (BusinessServiceException bse) {
		logger
				.error("SODetailsDelegateImpl:Unable to fetch the permit type list");
		throw new BusinessServiceException(bse.getMessage(), bse);
	}
	return permitTypesList;
}
	public ProcessResponse updateSOSubStatus(String serviceOrderId,
			Integer subStatusId, SecurityContext context)
			throws BusinessServiceException {
		logger
				.debug("----Start of SODetailsDelegateImpl.updateSOSubStatus----");
        if(ofHelper.isNewSo(serviceOrderId)){
            com.servicelive.orderfulfillment.domain.ServiceOrder tmpSO=new com.servicelive.orderfulfillment.domain.ServiceOrder();
            tmpSO.setWfSubStatusId(subStatusId);
            OrderFulfillmentRequest ofRequest = OFUtils.newOrderFulfillmentRequest(tmpSO, context);
            return OFUtils.mapProcessResponse(ofHelper.runOrderFulfillmentProcess(serviceOrderId, SignalType.UPDATE_SUBSTATUS, ofRequest));
        }
		ProcessResponse processResponse = getServiceOrderBo()
				.updateSOSubStatus(serviceOrderId, subStatusId, context);
		logger.debug("----End of SODetailsDelegateImpl.updateSOSubStatus----");
		return processResponse;
	}
	
	public ProcessResponse updateSOCustomReference(String soId, String referenceType, String referenceValue, String oldReferenceValue, SecurityContext securityContext)
			throws BusinessServiceException {
		logger
				.debug("----Start of SODetailsDelegateImpl.updateSOCustomReference----");
       
		ProcessResponse processResponse = getServiceOrderBo()
				.updateSOCustomReference(soId, referenceType, referenceValue, oldReferenceValue, securityContext);
		logger.debug("----End of SODetailsDelegateImpl.updateSOCustomReference----");
		return processResponse;
	}

	public ProcessResponse updateSOPartsShippingInfo(String serviceOrderId,
			List<SOPartsDTO> partShippingInfo, SecurityContext sc)
			throws BusinessServiceException {
		logger
				.debug("----Start of SODetailsDelegateImpl.updateSOPartsShippingInfo----");
		
		if (ofHelper.isNewSo(serviceOrderId)) {
			logger.debug("processing request through order fulfillment");
			OrderFulfillmentRequest ofRequest = OFUtils.createPartsShippedRequest(serviceOrderId, partShippingInfo, sc);
			return OFUtils.mapProcessResponse(ofHelper.runOrderFulfillmentProcess(serviceOrderId, SignalType.UPDATE_PARTS_SHIPMENT_TRACKING_DETAILS, ofRequest));
		}
		
		List<Part> parts = SOPartsInfoMapper
				.assignSOPartsShippingInfoListInfotoPartVO(serviceOrderId,
						partShippingInfo);
		ProcessResponse processResponse = getServiceOrderBo()
				.updatePartsShippingInfo(serviceOrderId, parts, sc);
		logger
				.debug("----End of SODetailsDelegateImpl.updateSOPartsShippingInfo----");
		return processResponse;
	}

	public ILookupDelegate getLuDelegate() {
		return luDelegate;
	}

	public void setLuDelegate(ILookupDelegate luDelegate) {
		this.luDelegate = luDelegate;
	}

	public ProcessResponse increaseSpendLimit(
			IncreaseSpendLimitDTO soIncSpendLimitDto)
			throws BusinessServiceException {
		SecurityContext securityContext = getSecurityContext();
		logger
				.debug("----Start of SODetailsDelegateImpl.increaseSpendLimit----");
        String soId = soIncSpendLimitDto.getSelectedSO();

        ProcessResponse pr = null;
        String modifiedByName ="";
        soIncSpendLimitDto.validate();
        
        if (!(soIncSpendLimitDto.getErrors() == null)) {
	        if (!soIncSpendLimitDto.getErrors().isEmpty()) {
	            pr = new ProcessResponse();
	            pr.setCode(USER_ERROR_RC);
	            pr.setMessage("");
	            return pr;
	        }
        }
        
    	try{
    		LoginCredentialVO roles = securityContext.getRoles();
          	if(null!=roles && null!=roles.getFirstName()&& null!=roles.getLastName()){
        		modifiedByName = (roles.getFirstName()+" "+roles.getLastName());
        	}
    	}catch(Exception e){
    		logger.error("Error while fetching buyer details");        	
    	}
		if(soIncSpendLimitDto.getTotalSpendLimit().equals("")){
			soIncSpendLimitDto.setTotalSpendLimit(soIncSpendLimitDto.getCurrentLimitLabor());
		}
       Double totalSpendLimitLabor = soIncSpendLimitDto.getTotalSpendLimit() != null ? Double
				.parseDouble(soIncSpendLimitDto.getTotalSpendLimit())
						: 0.0;			
        Double totalSpendLimitParts = soIncSpendLimitDto
				.getTotalSpendLimitParts() != null ? Double
				.parseDouble(soIncSpendLimitDto.getTotalSpendLimitParts())
				: 0.0;
        String increasedSpendLimitComment = soIncSpendLimitDto
				.getIncreasedSpendLimitComment();
        Integer buyerId = soIncSpendLimitDto.getBuyerId() != null ? Integer
				.parseInt(soIncSpendLimitDto.getBuyerId()) : 0;
        if(ofHelper.isNewSo(soId)){
            logger.info("SL-16058. Starting increase price action");
            OrderFulfillmentRequest ofRequest = OFUtils.createAddFundsRequest(soIncSpendLimitDto, securityContext,modifiedByName);
            OrderFulfillmentResponse ofResponse = ofHelper.runOrderFulfillmentProcess(soId, SignalType.ADD_FUND, ofRequest);
            logger.info("SL-16058. Ending increase price action");
            return OFUtils.mapProcessResponse(ofResponse);
        }
        String groupId = soIncSpendLimitDto.getGroupId();
       if (StringUtils.isNotBlank(groupId)) {
            try {
                pr = getOrderGrpBO().increaseSpendLimit(groupId,totalSpendLimitLabor, totalSpendLimitParts,increasedSpendLimitComment, buyerId, true,securityContext);
            } catch (com.newco.marketplace.exception.BusinessServiceException bsEx) {
                pr = new ProcessResponse();
                pr.setCode(ProcessResponse.USER_ERROR_RC);
                pr.setMessage("Unexpected error in increasing spend limit of group:" + groupId);
            }
        } else {
            pr = getServiceOrderBo().updateSOSpendLimit(soId,totalSpendLimitLabor, totalSpendLimitParts,increasedSpendLimitComment, buyerId, true, false,securityContext);
        }
		logger.debug("----End of SOMonitorDelegateImpl.increaseSpendLimit----");
		return pr;
	}

	public ProcessResponse requestRescheduleSO(RescheduleDTO reschedule) {
	    SecurityContext securityContext = getSecurityContext();
	    boolean isScheduleModify=false;
	    Long start = System.currentTimeMillis();
	    logger.info("Entering requestRescheduleSO in sodetailsdelegateimpl");
	    if(ofHelper.isNewSo(reschedule.getSoId())){
	        ProcessResponse returnVal = new ProcessResponse();
	        try{
	        	InHomeRescheduleVO result = null;
	        	BuyerOutboundNotificationVO buyerOutboundNotificationVO=null;
		    	RequestOrder order=new RequestOrder();
		    	boolean isRescheduleModify=false;
		    	String rescheduleMessage = "";
		    	// get schedule Date & Reschedule date details.
		    	try {
		    		Long start1 = System.currentTimeMillis();
					buyerOutboundNotificationVO = buyerOutBoundNotificationService.getScheduleDetails(reschedule.getSoId());
					Long end1 = System.currentTimeMillis();
					logger.info("Time taken to for buyerOutBoundNotificationService.getScheduleDetails"+(end1-start1));
		    	}
		    	catch(BusinessServiceException e)
		    	{
					logger.error("error in fetching schedule Details"+e);
		    	}
		    	// InHome NPS Notification for accept reschedule reschedule.
            	InHomeRescheduleVO input=new InHomeRescheduleVO();
            	input.setSoId(reschedule.getSoId());
            	if(!securityContext.isBuyer()){
            		input.setRescheduleDate1(reschedule.getNewStartDate());
            		input.setRescheduleDate2(reschedule.getNewEndDate());
            		input.setRescheduleStartTime(reschedule.getNewStartTime());
            		input.setRescheduleEndTime(reschedule.getNewEndTime());
            		input.setIsNPSMessageRequired(true);
            	}
            	
            	Long start2 = System.currentTimeMillis();
            	try{
        			result = notificationService.getSoDetailsForReschedule(input);
            	}catch(Exception e){
        			logger.info("exception in getting SO details"+e);
        		}
            	Long end2 = System.currentTimeMillis();
            	rescheduleMessage = result.getRescheduleMessage();
		    	Boolean autoAccept=false;
	        	String message="Reschedule Request processed successfully.";
	            SOSchedule schedule = OFUtils.mapRescheduleRequest(reschedule);
	            List<Parameter> parameters = new ArrayList<Parameter>();
	            //Modified for JIRA SL-19291
	            //altered to allow edit of reschedule request logging
	            //com.servicelive.orderfulfillment.domain.ServiceOrder so = ofHelper.getServiceOrder(reschedule.getSoId());
	            
	            //RescheduleVO soRescheduleInfo=serviceOrderBo.getRescheduleInfo(reschedule.getSoId());
	            
	            logger.info("Time taken for notificationService.getSoDetailsForReschedule"+(end2-start2));
	            
	            logger.info("Reschedule Info"+result.getRescheduleServiceDate1());
	            // Adding old and new reschedule date to so_logging data when a buyer try to edit reschedule.
	            HashMap<String, Object> rescheduleStartDate = null;
	    		HashMap<String, Object> rescheduleEndDate = null;
	    		String format = OrderConstants.RESCHEDULE_DATE_FORMAT;
	    		StringBuffer reschedulePeriod = new StringBuffer();
	    		try{
	            if(null != result && null != result.getRescheduleServiceDate1()){
	            	if (null != result.getRescheduleServiceDate1() && null != result.getRescheduleStartTime()) {
	            		rescheduleStartDate = TimeUtils.convertGMTToGivenTimeZone(result.getRescheduleServiceDate1(), result.getRescheduleStartTime(), result.getTimeZone());
	            		if (null != rescheduleStartDate && !rescheduleStartDate.isEmpty()) {
	            			if(reschedule.getRequestorRole() == 3){
	        	    			reschedulePeriod.append("<br/>Buyer ");
	        	    		}
	        	    		else{
	        	    			reschedulePeriod.append("<br/>Provider ");
	        	    		}
	            			reschedulePeriod.append("edited the reschedule request from ");
	            			reschedulePeriod.append(formatDate(format, (Date) rescheduleStartDate.get(OrderConstants.GMT_DATE)));
	            			reschedulePeriod.append(" ");
	            			reschedulePeriod.append((String) rescheduleStartDate.get(OrderConstants.GMT_TIME));
	            		}
	    			}
	            	if(null != result.getRescheduleServiceDate2() && null != result.getRescheduleEndTime()){
	            		rescheduleEndDate = TimeUtils.convertGMTToGivenTimeZone(result.getRescheduleServiceDate2(), result.getRescheduleEndTime(), result.getTimeZone());
	            		if (null != rescheduleEndDate && !rescheduleEndDate.isEmpty()) {
	            			reschedulePeriod.append(" - ");
	            			reschedulePeriod.append(formatDate(format, (Date) rescheduleEndDate.get(OrderConstants.GMT_DATE)));
	                		reschedulePeriod.append(" ");
	                		reschedulePeriod.append((String) rescheduleEndDate.get(OrderConstants.GMT_TIME));
	            			}
	            		}
	            	reschedulePeriod.append(" "+getTimeZone(formatDate(format, (Date) rescheduleStartDate.get(OrderConstants.GMT_DATE))+" "+(String) rescheduleStartDate.get(OrderConstants.GMT_TIME), OrderConstants.RESCHEDULE_DATE_TIME_STAMP_FORMAT1, result.getTimeZone()));
	            	if(null != reschedule.getNewStartDate() && null != reschedule.getNewStartTime() && StringUtils.isNotEmpty(reschedule.getNewStartDate())){
	            		Date newDate = new SimpleDateFormat(OrderConstants.REPORT_DATE_FORMAT, Locale.ENGLISH).parse(reschedule.getNewStartDate());
	            		reschedulePeriod.append(" to "+formatDate(format, newDate)+" "+reschedule.getNewStartTime());
	            	}
	            	if(null != reschedule.getNewEndDate() && null != reschedule.getNewEndTime() && StringUtils.isNotEmpty(reschedule.getNewEndDate())){
	            		Date newDate = new SimpleDateFormat(OrderConstants.REPORT_DATE_FORMAT, Locale.ENGLISH).parse(reschedule.getNewEndDate());
	            		reschedulePeriod.append(" - "+formatDate(format, newDate)+" "+reschedule.getNewEndTime());
	            	}
	            	reschedulePeriod.append(" "+getTimeZone(reschedule.getNewStartDate()+" "+reschedule.getNewStartTime(), OrderConstants.RESCHEDULE_DATE_TIME_STAMP_FORMAT2, result.getTimeZone()));
	            	logger.info(reschedulePeriod.toString());
	            }
	    		}
	            catch(Exception e){
	            	logger.error("Exception occured in edit reschedule:"+e);
	            }
	    		// R10.3: SL-20141: Remove extra space while logging the comment "Buyer requested reschedule" or "Provider requested reschedule"
	    		//this caused the request reschedule queue for ATT not work correctly.
	        	if(result.getRescheduleServiceDate1()!=null)
	        	{
	        		parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_EDIT_RESCHEDULE_REQUEST, "edited reschedule request"));
	        	}
	        	else{
	        		parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_EDIT_RESCHEDULE_REQUEST, "requested reschedule"));
	        	}
	        	parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RESCHEDULE_REQUEST_COMMENT, reschedule.getRescheduleComments()));
	        	parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_EDIT_RESCHEDULE_DATE_INFO, reschedulePeriod.toString()));
	            if(!securityContext.isBuyer()){
	            	String reasonCodeForSchedule="";
	            	if(null!=reschedule.getReasonCode() && !"".equals(reschedule.getReasonCode()))
	            		reasonCodeForSchedule=buyerOutBoundNotificationService.getReasonCode(Integer.parseInt(reschedule.getReasonCode()));
	            	if(null!=reasonCodeForSchedule && !("").equals(reasonCodeForSchedule))
	            		parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RESCHEDULE_REQUEST_REASON_CODE, " Reason: "+reasonCodeForSchedule));
	            }
	            // below condition for buyer added as part of SL-19240
	            if(securityContext.isBuyer()){
	            	String reasonCodeForSchedule="";
	            	if(null!=reschedule.getReasonCode() && !"".equals(reschedule.getReasonCode()))
	            		reasonCodeForSchedule=buyerOutBoundNotificationService.getRescheduleReason(Integer.parseInt(reschedule.getReasonCode()));
	            	if(null!=reasonCodeForSchedule && !("").equals(reasonCodeForSchedule))
	            		parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RESCHEDULE_REQUEST_REASON_CODE, " Reason: "+reasonCodeForSchedule));
	            }
	            logger.info("requestRescheduleSO.rescheduleMessage : "+rescheduleMessage);
	            parameters.add(new Parameter(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_AUTOACCEPT_MESSAGE, rescheduleMessage));
	            
	            //Setting call code for creating request xml of Service Operation API
	            parameters.add(new Parameter(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_RESCHEDULE_CALL_CODE, InHomeNPSConstants.RESCHD_CALLCODE));
     
	            SignalType rescheduleSignalType = securityContext.isBuyer() ? SignalType.BUYER_REQUEST_RESCHEDULE : SignalType.PROVIDER_REQUEST_RESCHEDULE;
                Identification identification = OFUtils.createOFIdentityFromSecurityContext(securityContext);
                Long start6 = System.currentTimeMillis();
                ofHelper.runOrderFulfillmentProcess(reschedule.getSoId(), rescheduleSignalType, schedule, identification, parameters);
                Long end6 = System.currentTimeMillis();
            	logger.info("Time taken for executing OF Signal"+(end6-start6));
	            if(!securityContext.isBuyer()){
	            	Long start3 = System.currentTimeMillis();
	            	com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder = ofHelper.getServiceOrder(reschedule.getSoId());
	            	Long end3 = System.currentTimeMillis();
	            	logger.info("Time taken for ofHelper.getServiceOrder"+(end3-start3));
        			/*logger.info("SO Serv Date1:"+serviceOrder.getSchedule().getServiceDate1());
        			logger.info("SO Serv Date2:"+serviceOrder.getSchedule().getServiceDate2());
        			logger.info("SO Serv Start:"+serviceOrder.getSchedule().getServiceTimeStart());
        			logger.info("SO Serv End:"+serviceOrder.getSchedule().getServiceTimeEnd());
        			logger.info("SO Serv date type id:"+serviceOrder.getSchedule().getServiceDateTypeId());
        			
        			logger.info("Schdeule Serv Date1:"+schedule.getServiceDate1());
        			logger.info("Schdeule Date2:"+schedule.getServiceDate2());
        			logger.info("Schdeule Start:"+schedule.getServiceTimeStart());
        			logger.info("Schdeule End:"+schedule.getServiceTimeEnd());
        			logger.info("Schdeule date type id:"+schedule.getServiceDateTypeId());*/
         	
	            	if(schedule.getServiceDateTypeId().equals(serviceOrder.getSchedule().getServiceDateTypeId())&&
	            			serviceOrder.getSchedule().getServiceDate1().equals(schedule.getServiceDate1())&&
	            			serviceOrder.getSchedule().getServiceTimeStart().equals(schedule.getServiceTimeStart())){
            			logger.info("Entered auto accept true");
	            		if((schedule.getServiceDateTypeId().equals(SOScheduleType.DATERANGE)&& 
	            				serviceOrder.getSchedule().getServiceDate2()!=null&&
	            				schedule.getServiceDate2().equals(serviceOrder.getSchedule().getServiceDate2())&&
	            				schedule.getServiceTimeEnd().equals(serviceOrder.getSchedule().getServiceTimeEnd()))||
	            				schedule.getServiceDateTypeId().equals(SOScheduleType.SINGLEDAY)){
	            			autoAccept=true;
	            			logger.info("Entered auto accept true 1");
	            		}
	            		
	            	}else if(serviceOrder.getSchedule().getServiceDate1().equals(schedule.getServiceDate1())&&
	            			serviceOrder.getSchedule().getServiceTimeStart().equals(schedule.getServiceTimeStart())){
	            		autoAccept=true;
            			logger.info("Entered auto accept true 2");
	            		
	            	}
	            	if(!autoAccept){
	            		logger.info("Not auto accept");
	            		
	            		if(serviceOrder.getReschedule()!=null&&serviceOrder.getReschedule().getServiceDate1()!=null){
		            		logger.info("Proccessed Succes mesg");
	            			message="Reschedule Request processed successfully.";
	            		}else{
		            		logger.info("Rejected mesg");
	            			message="Reschedule Request has been rejected.";
	            		}
	            	}else if(serviceOrder.getReschedule()!=null&&serviceOrder.getReschedule().getServiceDate1()!=null){
	            		autoAccept=false;
	            		message="Reschedule Request processed successfully.";
	            	}else{
	            		
	            		message="Reschedule Request has been accepted";
	            	}
	            	
	            }
	            returnVal.setCode(VALID_RC);
	            returnVal.setMessage(message);
	            if(null!=buyerOutboundNotificationVO && autoAccept){
	            	
	            	//get the service start Date on service order Timezone.
	            	TimeZone timeZoneForSo=TimeZone.getTimeZone(buyerOutboundNotificationVO.getTimeZone());
					Calendar startDateTimeSo = TimeChangeUtil.getCalTimeFromParts(buyerOutboundNotificationVO.getServiceOrderScheduleFromDate(), buyerOutboundNotificationVO.getServiceOrderScheduleFromTime(),TimeZone.getTimeZone("GMT"));
					Date serviceFromDateSo = TimeChangeUtil.getDate(startDateTimeSo, timeZoneForSo);
					String serviceTimeStartSo = TimeChangeUtil.getTimeString(startDateTimeSo, timeZoneForSo);
	            	
					//get the service end Date on service order Timezone.
					Calendar endDateTimeSo = null;
					if(null!=buyerOutboundNotificationVO.getServiceOrderScheduleToDate() && null!=buyerOutboundNotificationVO.getServiceOrderScheduleToTime())
					endDateTimeSo=TimeChangeUtil.getCalTimeFromParts(buyerOutboundNotificationVO.getServiceOrderScheduleToDate(), buyerOutboundNotificationVO.getServiceOrderScheduleToTime(),TimeZone.getTimeZone("GMT"));
					//Date serviceToDateSo = TimeChangeUtil.getDate(endDateTimeSo, timeZoneForSo);
					String serviceTimeEndSo="";
					if(null!=endDateTimeSo)
					serviceTimeEndSo = TimeChangeUtil.getTimeString(endDateTimeSo, timeZoneForSo);
					
					//logic to check whether the start date/start time/end time is same while rescheduling.
	            	if(null!=buyerOutboundNotificationVO){	     
	            		if(!schedule.getServiceDate1().equals(serviceFromDateSo)){
							isRescheduleModify=true;	
	            		}
	            		else if(!schedule.getServiceTimeStart().equals(serviceTimeStartSo))
						{
							isRescheduleModify=true;
						}
	            		else if((null==schedule.getServiceTimeEnd() && null!=serviceTimeEndSo)|| (null!=schedule.getServiceTimeEnd() && null==serviceTimeEndSo))
						{
							isRescheduleModify=true;
						}
	            		else if(!((null==schedule.getServiceTimeEnd() && null==serviceTimeEndSo)||(schedule.getServiceTimeEnd().equals(serviceTimeEndSo))))
						{
							isRescheduleModify=true;
						}
					}
	            	if(isRescheduleModify)
					{
	            		//get reason Code & Comments from service order logging.
	    	        	BuyerOutboundNotificationVO logging=new BuyerOutboundNotificationVO();
	    	        	try {
	    	        		Long start4 = System.currentTimeMillis();
							logging=buyerOutBoundNotificationService.getLoggingDetails(reschedule.getSoId());
							Long end4 = System.currentTimeMillis();
							logger.info("Time taken for buyerOutBoundNotificationService.getLoggingDetails"+(end4-start4));
	    	        	} catch (BusinessServiceException e) {
							logger.info("error in getting so Logging"+e);
						}
                      String modificationId=logging.getEntityId().toString();
                      String reasonLogging=logging.getReasonDescr();
               		  String reasonCode="";
                      String reasonDescr="";
                      
                      int reasonCodeIndex= reasonLogging.lastIndexOf("Reason:");
                      reasonCodeIndex=reasonCodeIndex+7;
                      int reasonDescrIndex= reasonLogging.lastIndexOf("Comments:");
                      reasonDescrIndex=reasonDescrIndex+9;
                      int length=reasonLogging.length();
                      // get reasonCode from the logging comment
                      if(6!=reasonCodeIndex)
                      reasonCode= reasonLogging.subSequence(reasonCodeIndex, reasonDescrIndex-9).toString().trim();
                      // get reasonCode Description from the logging comment
                      if(8!=reasonDescrIndex)
                      reasonDescr= reasonLogging.subSequence(reasonDescrIndex, length).toString().trim() ;
                      
                      if(StringUtils.isEmpty(reasonCode) && !StringUtils.isEmpty(reasonDescr)){
                    	  reasonCode = reasonDescr;
                      }
                     if(null!=logging.getEntityId())
                    	   modificationId=logging.getEntityId().toString();
                       
                     // convert the reschedule date date in service order Timezone.
                     	TimeZone timeZone=TimeZone.getTimeZone(buyerOutboundNotificationVO.getTimeZone());
						SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
						String fromDate=formatter.format(schedule.getServiceDate1());
						String serviceTimeStart = schedule.getServiceTimeStart();
						String serviceTimeEnd = "";
						if(null!=schedule.getServiceTimeEnd())
							serviceTimeEnd=schedule.getServiceTimeEnd();
						
						//set rescheduled Date for request Xml.
						order.setServiceScheduleDate(fromDate);
						order.setServiceScheduleFromTime(serviceTimeStart);
						if(StringUtils.isBlank(serviceTimeEnd))
						{
							order.setServiceScheduletoTime(serviceTimeStart);
						}
						else
						{
						order.setServiceScheduletoTime(serviceTimeEnd);
						}
						order.setServiceOrderRescheduledFlag(BuyerOutBoundConstants.RESHEDULE_FLAG_YES);
						
						// get the Modified date(current date) in server TimeZone.
						Calendar calender=Calendar.getInstance();
						SimpleDateFormat timeFormatter=new SimpleDateFormat("hh:mm a");
						String modifiedfromDate=timeFormatter.format(calender.getTime());
						Calendar modificationDateTime = TimeChangeUtil.getCalTimeFromParts(calender.getTime(), modifiedfromDate,calender.getTimeZone());
						Date modificationDate = TimeChangeUtil.getDate(modificationDateTime, timeZone);
						String modificationDateValue=formatter.format(modificationDate);
						String modificationTime = TimeChangeUtil.getTimeString(modificationDateTime, timeZone);

						RequestRescheduleInfo requestRescheduleInfo=new RequestRescheduleInfo();
						
						//set the modified date for request xml.
			        	requestRescheduleInfo.setReschedCancelModificationDate(modificationDateValue);
			        	requestRescheduleInfo.setReschedCancelModificationTime(modificationTime);
			        	requestRescheduleInfo.setRescheduleModificationID(modificationId);
			        	requestRescheduleInfo.setRescheduleReasonCode(reasonCode);
			        	requestRescheduleInfo.setRescheduleRsnCdDescription(reschedule.getRescheduleComments());	
	            	
			        	//set the reschedule Information for request xml
					RequestMsgBody requestMsgBody=new RequestMsgBody();
		        	RequestOrders orders=new RequestOrders();
		        	List<RequestOrder> orderList=new ArrayList<RequestOrder>();
		        	RequestReschedInformation requestReschedInformation=new RequestReschedInformation();
		        	List<RequestRescheduleInfo> requestRescheduleInf=new ArrayList<RequestRescheduleInfo>();
		        	requestRescheduleInf.add(requestRescheduleInfo);
		        	requestReschedInformation.setRequestRescheduleInf(requestRescheduleInf);
		        	order.setRequestReschedInformation(requestReschedInformation);
		        	orderList.add(order);
		        	orders.setOrder(orderList);
		        	requestMsgBody.setOrders(orders);
		        	//call service by passing the request Object.
		        	try {
		        		Long start5 = System.currentTimeMillis();
		        		BuyerOutboundFailOverVO failoverVO = buyerOutBoundNotificationService.callService(requestMsgBody, reschedule.getSoId());
		        		 if(null!=failoverVO)
		        			 buyerOutBoundNotificationJMSService.callJMSService(failoverVO);
		        		 Long end5 = System.currentTimeMillis();
		        		 logger.info("Time taken for buyerOutBoundNotificationService.callService"+(end5-start5));
					} catch (BusinessServiceException e) {
						logger.error("Error in NPS update for reschedule"+e);
					}
		        	
					}
	            }
	        /**This block executes when an inhome order is rescheduled and isEligible for Auto Acceptance*/
	        /*if(autoAccept){
	        	result.setReleasIndicator(0);
                try {
					insertRescheduleNPSInhomeNotificationMessages(result);
				} catch (BusinessServiceException e) {
					logger.error("Exception in notifying NPS for Inhome");
				}
            
	        }*/
	        }catch(ParseException pe){
	            returnVal.setCode(SYSTEM_ERROR_RC);
	            returnVal.setMessage("Error in the date format.");
	        }
	        Long end = System.currentTimeMillis();
   		 logger.info("Total Time taken for reschedule in SODetailsDelegateImpl."+(end-start));
	        return returnVal;
	    }
		ProcessResponse success = null;

		String serviceOrderId = reschedule.getSoId();
		Timestamp newStartDate = null;
		Timestamp newEndDate = null;
		String newStartTime = reschedule.getNewStartTime();
		String newEndTime = reschedule.getNewEndTime();
		Integer subStatus = reschedule.getSubStatus();
		Integer requestorRole = reschedule.getRequestorRole();
		String comments = reschedule.getRescheduleComments();

		if (reschedule.getNewStartDate() != null
				&& StringUtils.isNotEmpty(reschedule.getNewStartDate()))
			newStartDate = Timestamp.valueOf(reschedule.getNewStartDate()
					+ " 00:00:00");
		if (reschedule.getNewEndDate() != null
				&& StringUtils.isNotEmpty(reschedule.getNewEndDate()))
			newEndDate = Timestamp.valueOf(reschedule.getNewEndDate()
					+ " 00:00:00");

		try {
			String scheduleType = FIXED_DATE;
			if (StringUtils.equals("1", reschedule.getRangeOfDates())) {
				scheduleType = RANGE_DATE;
			}
			success = serviceOrderBo.rescheduleSOComments(serviceOrderId,
					newStartDate, newEndDate, newStartTime, newEndTime,
					subStatus, requestorRole, reschedule.getCompanyId(),
					scheduleType, comments, securityContext);
		} catch (BusinessServiceException bse) {
			logger.error("Request reschedule unsuccessful", bse);
		}
		return success;
	}

	public ArrayList<LookupVO> getShippingCarrier()
			throws BusinessServiceException {
		ArrayList<LookupVO> aList = null;
		aList = (ArrayList<LookupVO>) luDelegate.getShippingCarrier();
		return aList;
	}
	public String getAssignmentType(String soId) throws BusinessServiceException{
		String assignmentType ="";
		try{
			assignmentType = serviceOrderBo.getAssignmentType(soId);
		} catch (BusinessServiceException bse) {
			logger.error("getAssignmentType info unsuccessful", bse);
		}
		return assignmentType;
	}
	public List <ProviderResultVO> getRoutedProviderListForFirm (String soId, String vendorId)throws BusinessServiceException{
		List <ProviderResultVO> providerList = null;
		try{
			providerList = serviceOrderBo.getRoutedProviderListForFirm(soId,vendorId);
		} catch (BusinessServiceException bse) {
			logger.error("getAssignmentType info unsuccessful", bse);
		}
		return providerList;
	}

	public ServiceOrderRescheduleVO getRescheduleRequestInfo(
			String serviceOrderId) {
		ServiceOrderRescheduleVO reschedule = null;
		try {
			reschedule = serviceOrderBo
					.getRescheduleRequestInfo(serviceOrderId);
		} catch (BusinessServiceException bse) {
			logger.error("Request reschedule info unsuccessful", bse);
		}
		return reschedule;
	}

	public ProcessResponse respondToRescheduleRequest(RescheduleDTO reschedule) {

		ProcessResponse success = null;
		String serviceOrderId = reschedule.getSoId();
		boolean isRequestAccepted = reschedule.isRescheduleAccepted();
		Integer userRole = reschedule.getRequestorRole();
		SecurityContext securityContext = getSecurityContext();
		boolean isRescheduleModify=false;
		/*Start OrderFulfillment{*/
        Identification identification = OFUtils.createOFIdentityFromSecurityContext(securityContext);
		if(ofHelper.isNewSo(serviceOrderId)){
		    OrderFulfillmentResponse response;
		    if(isRequestAccepted){
		    	
		    	BuyerOutboundNotificationVO buyerOutboundNotificationVO=null;
		    	RequestOrder order=new RequestOrder();
	        	RequestRescheduleInfo requestRescheduleInfo=new RequestRescheduleInfo();
	        	// get the schedule & reschedule dates of the service order.
	        	try {
					buyerOutboundNotificationVO = buyerOutBoundNotificationService.getScheduleDetails(serviceOrderId);
				if(null!=buyerOutboundNotificationVO)
				{
					//logic to check whether reschedule & scheduled date matches.
						if(!buyerOutboundNotificationVO.getServiceOrderScheduleFromDate().equals(buyerOutboundNotificationVO.getServiceOrderRescheduleFromDate()))
					{
						isRescheduleModify=true;	

					}
						else if(!buyerOutboundNotificationVO.getServiceOrderScheduleFromTime().equals(buyerOutboundNotificationVO.getServiceOrderRescheduleFromTime()))
					{
						isRescheduleModify=true;
					}
					else if((null==buyerOutboundNotificationVO.getServiceOrderScheduleToTime() && null!=buyerOutboundNotificationVO.getServiceOrderRescheduleToTime())
								||(null!=buyerOutboundNotificationVO.getServiceOrderScheduleToTime() && null==buyerOutboundNotificationVO.getServiceOrderRescheduleToTime())
								)
					{
							isRescheduleModify=true;
					}
						else if(!((null==buyerOutboundNotificationVO.getServiceOrderScheduleToTime() && null==buyerOutboundNotificationVO.getServiceOrderRescheduleToTime()
								)||
								(buyerOutboundNotificationVO.getServiceOrderScheduleToTime().equals(buyerOutboundNotificationVO.getServiceOrderRescheduleToTime()))))
					{
						isRescheduleModify=true;
					}
					 if(isRescheduleModify)
						{
						 // get logging comment to retreive reson Code.
						 BuyerOutboundNotificationVO logging=new BuyerOutboundNotificationVO();
		    	        	try {
								logging=buyerOutBoundNotificationService.getLoggingDetails(reschedule.getSoId());
							} catch (BusinessServiceException e) {
								logger.info("error in getting so Logging"+e);
							}
							
							//get reson Code & reason Description from logging comment
	                       String modificationId=logging.getEntityId().toString();
	                       String reasonLogging=logging.getReasonDescr();
	               		String reasonCode="";
	                      String reasonDescr="";
	                      int reasonCodeIndex= reasonLogging.lastIndexOf("Reason:");
	                      reasonCodeIndex=reasonCodeIndex+7;
	                      int reasonDescrIndex= reasonLogging.lastIndexOf("Comments:");
	                      reasonDescrIndex=reasonDescrIndex+9;
	                      int length=reasonLogging.length();
	                      if(6!=reasonCodeIndex)
	                     reasonCode= reasonLogging.subSequence(reasonCodeIndex, reasonDescrIndex-9).toString().trim();
	                      if(8!=reasonDescrIndex)
	                    reasonDescr= reasonLogging.subSequence(reasonDescrIndex, length).toString().trim() ;
						 
	                      if(StringUtils.isEmpty(reasonCode) && !StringUtils.isEmpty(reasonDescr)){
	                    	  reasonCode = reasonDescr;
	                      }
	                      
	                      //get the rescheduled dates in service order Timezone.
						 TimeZone timeZone=TimeZone.getTimeZone(buyerOutboundNotificationVO.getTimeZone());
							Calendar startDateTime = TimeChangeUtil.getCalTimeFromParts(buyerOutboundNotificationVO.getServiceOrderRescheduleFromDate(), buyerOutboundNotificationVO.getServiceOrderRescheduleFromTime(),TimeZone.getTimeZone("GMT"));
							Date serviceFromDate = TimeChangeUtil.getDate(startDateTime, timeZone);
							SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
							String fromDate=formatter.format(serviceFromDate);
							String serviceTimeStart = TimeChangeUtil.getTimeString(startDateTime, timeZone);
							
							Calendar endDateTime=null;
							if(null!=buyerOutboundNotificationVO.getServiceOrderRescheduleToDate() && null!=buyerOutboundNotificationVO.getServiceOrderRescheduleToTime())
							endDateTime= TimeChangeUtil.getCalTimeFromParts(buyerOutboundNotificationVO.getServiceOrderRescheduleToDate(), buyerOutboundNotificationVO.getServiceOrderRescheduleToTime(),TimeZone.getTimeZone("GMT"));
							String serviceTimeEnd=""; 
							if(null!=endDateTime)
							serviceTimeEnd= TimeChangeUtil.getTimeString(endDateTime, timeZone);
							//set reschedule information in request Object.
							order.setServiceScheduleDate(fromDate);
							order.setServiceScheduleFromTime(serviceTimeStart);
							if(StringUtils.isBlank(serviceTimeEnd))
							{
								order.setServiceScheduletoTime(serviceTimeStart);

							}
							else
							{
							order.setServiceScheduletoTime(serviceTimeEnd);
							}
							order.setServiceOrderRescheduledFlag(BuyerOutBoundConstants.RESHEDULE_FLAG_YES);
							
							//get the current date in server timeZone 
							Calendar calender=Calendar.getInstance();
							SimpleDateFormat timeFormatter=new SimpleDateFormat("hh:mm a");
							String modifiedfromDate=timeFormatter.format(calender.getTime());
							Calendar modificationDateTime = TimeChangeUtil.getCalTimeFromParts(calender.getTime(), modifiedfromDate,calender.getTimeZone());
							Date modificationDate = TimeChangeUtil.getDate(modificationDateTime, timeZone);
							String modificationDateValue=formatter.format(modificationDate);
							String modificationTime = TimeChangeUtil.getTimeString(modificationDateTime, timeZone);
			                //set the current date ,modified Id,reasonCode in reschedule Object
				        	requestRescheduleInfo.setReschedCancelModificationDate(modificationDateValue);
				        	requestRescheduleInfo.setReschedCancelModificationTime(modificationTime);
				        	requestRescheduleInfo.setRescheduleModificationID(modificationId);
				        	requestRescheduleInfo.setRescheduleReasonCode(reasonCode);
				        	requestRescheduleInfo.setRescheduleRsnCdDescription(reasonDescr);
						}
					
					

					
					
				}
		    	} catch (BusinessServiceException e) {
					logger.error("error in fetching schedule Details"+e);
				}
	        	// InHome NPS Notification for accept reschedule reschedule.
            	InHomeRescheduleVO input=new InHomeRescheduleVO();
                input.setSoId(reschedule.getSoId());
                input.setIsNPSMessageRequired(true);
            	InHomeRescheduleVO result=new InHomeRescheduleVO();
            	try{
            	result=notificationService.getSoDetailsForReschedule(input);
            	}catch(Exception e){
            		logger.info("exception in getting SO details"+e);
            	}
            	List<Parameter> parameters = new ArrayList<Parameter>();
            	String rescheduleMessage = result.getRescheduleMessage(); 
            	logger.info("respondToRescheduleRequest.rescheduleMessage : "+rescheduleMessage);
            	parameters.add(new Parameter(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_RESCHEDULE_MESSAGE, rescheduleMessage));
            	
            	//Setting call code for creating request xml of Service Operation API
            	parameters.add(new Parameter(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_RESCHEDULE_CALL_CODE, InHomeNPSConstants.RESCHD_CALLCODE));
            	
                SignalType rescheduleSignalType = securityContext.isBuyer() ? SignalType.BUYER_ACCEPT_RESCHEDULE : SignalType.PROVIDER_ACCEPT_RESCHEDULE;
                response = ofHelper.runOrderFulfillmentProcess(serviceOrderId, rescheduleSignalType, new SOSchedule(), identification, parameters);
                // call service by passing the request Object.
                if(isRescheduleModify){
				RequestMsgBody requestMsgBody=new RequestMsgBody();
	        	RequestOrders orders=new RequestOrders();
	        	List<RequestOrder> orderList=new ArrayList<RequestOrder>();
	        	RequestReschedInformation requestReschedInformation=new RequestReschedInformation();
	        	List<RequestRescheduleInfo> requestRescheduleInf=new ArrayList<RequestRescheduleInfo>();
	        	requestRescheduleInf.add(requestRescheduleInfo);
	        	requestReschedInformation.setRequestRescheduleInf(requestRescheduleInf);
	        	order.setRequestReschedInformation(requestReschedInformation);
	        	orderList.add(order);
	        	orders.setOrder(orderList);
	        	requestMsgBody.setOrders(orders);
	        	try {
	        		 BuyerOutboundFailOverVO failoverVO = buyerOutBoundNotificationService.callService(requestMsgBody, serviceOrderId);
	        		 if(null!=failoverVO)
	        		 buyerOutBoundNotificationJMSService.callJMSService(failoverVO);
				} catch (BusinessServiceException e) {
					logger.error("Error in NPS update for reschedule"+e);
				  }
	        	}
				/**This block will NOtify NPS for inhome orders reschedule*/
				/*result.setReleasIndicator(0);
				try {
					insertRescheduleNPSInhomeNotificationMessages(result);
				} catch (BusinessServiceException e) {
					logger.error("Exception in notifying NPS for Inhome");

				}*/
                	
		    }else{
                SignalType rescheduleSignalType = securityContext.isBuyer() ? SignalType.BUYER_REJECT_RESCHEDULE : SignalType.PROVIDER_REJECT_RESCHEDULE;
		        response = ofHelper.runOrderFulfillmentProcess(serviceOrderId, rescheduleSignalType, new SOSchedule(), identification);
		    }
		    return OFUtils.mapProcessResponse(response);
		}
		/*}End OrderFulfillment*/
		try {
			
			// splitting the methods for accept or reject reschedule(action id
			// split in the database)..start
			if (isRequestAccepted) {
				success = serviceOrderBo.acceptRescheduleRequest(
						serviceOrderId, isRequestAccepted, userRole, reschedule
								.getCompanyId(), securityContext);
			} else {
				success = serviceOrderBo.rejectRescheduleRequest(
						serviceOrderId, isRequestAccepted, userRole, reschedule
								.getCompanyId(), securityContext);
			}
			// splitting the methods for accept or reject reschedule..End
		} catch (BusinessServiceException bse) {
			logger.error("Respond to requested reschedule unsuccessful", bse);
		}
		return success;
	}
	
	public void insertRescheduleNPSInhomeNotificationMessages(InHomeRescheduleVO inHomeRescheduleVO) throws BusinessServiceException{
    	String soId=inHomeRescheduleVO.getSoId();
    	Integer buyerId =inHomeRescheduleVO.getBuyerId();
		
		boolean isEligibleForNPSNotification=false;
		try {
			isEligibleForNPSNotification = notificationService.validateNPSNotificationEligibility(buyerId,soId);
		} catch (BusinessServiceException e) {
			logger.error("Exception in validatiing nps notification eligibility"+ e);
			throw new BusinessServiceException("Exception in validatiing nps notification eligibility");
		}
		if(isEligibleForNPSNotification){
			//Call Insertion method
			try {
				notificationService.insertNotification(inHomeRescheduleVO);
			 }catch (BusinessServiceException e){
				logger.error("Caught Exception while insertNotification",e);
				throw new BusinessServiceException("Caught Exception while insertNotification");
				}
		}
	} 

	public ProcessResponse cancelRescheduleRequest(RescheduleDTO reschedule) {

		ProcessResponse success = null;
		String serviceOrderId = reschedule.getSoId();
		Integer userRole = reschedule.getRequestorRole();
		SecurityContext securityContext = getSecurityContext();
		if(ofHelper.isNewSo(serviceOrderId)){
            SignalType rescheduleSignalType = securityContext.isBuyer() ? SignalType.BUYER_CANCEL_RESCHEDULE : SignalType.PROVIDER_CANCEL_RESCHEDULE;
            Identification identification = OFUtils.createOFIdentityFromSecurityContext(securityContext);
            OrderFulfillmentResponse ofResponse = ofHelper.runOrderFulfillmentProcess(serviceOrderId, rescheduleSignalType, new SOSchedule(), identification);
            return OFUtils.mapProcessResponse(ofResponse);
		}
		try {
			success = serviceOrderBo.cancelRescheduleRequest(serviceOrderId,
					userRole, reschedule.getCompanyId(), securityContext);
		} catch (BusinessServiceException bse) {
			logger.error("Cancel requested reschedule unsuccessful", bse);
		}
		return success;
	}

	public ProcessResponse releaseServiceOrder(ReleaseServiceOrderDTO release) {

		ProcessResponse success = null;
		OrderFulfillmentResponse response = null;
		SecurityContext securityContext = getSecurityContext();
		List<Integer> providerList = new ArrayList<Integer>();
    	String name = "";
    	Integer id = null;
    	List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_COMMENT,release.getComment()));
        parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_REASON,release.getReasonText()));
        if(release.getStatusId().equals(150)){
        	parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_ACTION_ID,"24"));
        }else if(release.getStatusId().equals(155)){
        	parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_ACTION_ID,"26"));
        }else if(release.getStatusId().equals(170)){
        	parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_ACTION_ID,"52"));
        }
    	parameters.add(new Parameter(OrderfulfillmentConstants.RELEASE_ACTION_PERFORMED_IND,"1"));

		if(ofHelper.isNewSo(release.getSoId())){
            logger.info("Resource Id that had been passed is " + release.getResourceId());
            logger.info("Inside release OF");
            // Adding changed service date to so_logging data if a buyer reschedule was occurred before release of SO.
            RescheduleVO rescheduleVO = null;
            HashMap<String, Object> rescheduleStartDate = null;
    		HashMap<String, Object> rescheduleEndDate = null;
    		String format = OrderConstants.RESCHEDULE_DATE_FORMAT;
    		StringBuffer reschedulePeriod = new StringBuffer();
            try{
            	rescheduleVO = serviceOrderBo.getBuyerRescheduleInfo(release.getSoId());
        	
            if(null != rescheduleVO && null != rescheduleVO.getRescheduleServiceStartDate()){
            	if (null != rescheduleVO.getRescheduleServiceTime1()) {
            		rescheduleStartDate = TimeUtils.convertGMTToGivenTimeZone(rescheduleVO.getRescheduleServiceStartDate(), rescheduleVO.getRescheduleServiceTime1(), rescheduleVO.getServiceLocnTimeZone());
            		if (null != rescheduleStartDate && !rescheduleStartDate.isEmpty()) {
            			reschedulePeriod.append("<br/>Service date has been updated");
            			if(null != rescheduleVO.getRescheduleServiceEndDate() && null != rescheduleVO.getRescheduleServiceTime2()){
            				reschedulePeriod.append(", from");
            			}
            			reschedulePeriod.append(" " + formatDate(format, (Date) rescheduleStartDate.get(OrderConstants.GMT_DATE)));
            			reschedulePeriod.append(" ");
            			reschedulePeriod.append((String) rescheduleStartDate.get(OrderConstants.GMT_TIME));
            		}
    			}
            	if(null != rescheduleVO.getRescheduleServiceEndDate() && null != rescheduleVO.getRescheduleServiceTime2()){
            		rescheduleEndDate = TimeUtils.convertGMTToGivenTimeZone(rescheduleVO.getRescheduleServiceEndDate(), rescheduleVO.getRescheduleServiceTime2(), rescheduleVO.getServiceLocnTimeZone());
            		if (null != rescheduleEndDate && !rescheduleEndDate.isEmpty()) {
            			reschedulePeriod.append(" to ");
            			reschedulePeriod.append(formatDate(format, (Date) rescheduleEndDate.get(OrderConstants.GMT_DATE)));
                		reschedulePeriod.append(" ");
                		reschedulePeriod.append((String) rescheduleEndDate.get(OrderConstants.GMT_TIME));
            			}
            		}
            	}
            reschedulePeriod.append(" "+getTimeZone(formatDate(format, (Date) rescheduleStartDate.get(OrderConstants.GMT_DATE))+" "+(String) rescheduleStartDate.get(OrderConstants.GMT_TIME), OrderConstants.RESCHEDULE_DATE_TIME_STAMP_FORMAT1, rescheduleVO.getServiceLocnTimeZone()));
            }catch (Exception ee) {
				logger.error("error fetching routed providers for firm", ee);
			}
            
            
            if(SOConstants.PROVIDER_RESP_RELEASE_BY_FIRM.equals(release.getProviderRespId())){
            	try{
            		providerList = serviceOrderBo.getRoutedProvidersForFirm(release.getSoId(),release.getVendorId());
            	}catch (BusinessServiceException bse) {
    				logger.error("error fetching routed providers for firm", bse);
    			}
            	try{
            		name = serviceOrderBo.getVendorBusinessName(securityContext.getCompanyId());
            	}catch (BusinessServiceException bse) {
    				logger.error("error fetching business name of firm", bse);
    			}            		
            		id = securityContext.getCompanyId();
            	parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_NAME,name));
            	parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_ID,id.toString()));
            	parameters.add(new Parameter(OrderfulfillmentConstants.RELEASE_CHANGED_SERVICE_DATE_INFO, reschedulePeriod.toString()));
            	SOElementCollection soElementCollection = OFUtils.createSOElementForProviderList(providerList,release);
            	response = ofHelper.runOrderFulfillmentProcess(release.getSoId(), SignalType.PROVIDER_FIRM_RELEASE_ORDER, soElementCollection, OFUtils.createOFIdentityFromSecurityContext(getSecurityContext()), parameters);
    		}else{
            		name = release.getReleaseByName();
            		id = release.getReleaseById();
            	parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_NAME,name));
            	parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_ID,id.toString()));
                parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_COMMENT,release.getComment()));
                parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_REASON,release.getReasonText()));
                parameters.add(new Parameter(OrderfulfillmentConstants.RELEASE_CHANGED_SERVICE_DATE_INFO, reschedulePeriod.toString()));
    			com.servicelive.orderfulfillment.domain.RoutedProvider routedProvider = OFUtils.createReleaseElement((long)release.getResourceId(), release.getComment(),release.getReleaseReasonCode());
			    response = ofHelper.runOrderFulfillmentProcess(release.getSoId(), SignalType.PROVIDER_RELEASE_ORDER, routedProvider, OFUtils.createOFIdentityFromSecurityContext(getSecurityContext()), parameters);
    		}
		    
		    return OFUtils.mapProcessResponse(response);
		}
        logger.info("Outside release OF");
        logger.info("Outside release OF:"+ release.getStatusId().intValue());

		if (release.getStatusId().intValue() == OrderConstants.ACCEPTED_STATUS) {

			try {
				success = serviceOrderBo
						.releaseServiceOrderInAccepted(release.getSoId(),
								release.getReasonCode(), release.getComment(),
								release.getResourceId(), securityContext);
			} catch (BusinessServiceException bse) {
				logger.error("Cancel requested reschedule unsuccessful", bse);
			}
			if (success.getCode().equalsIgnoreCase(ServiceConstants.VALID_RC)) {
				serviceOrderBo.releaseSOProviderAlert(release.getSoId(),
						securityContext);
			}
		} else if (release.getStatusId().intValue() == OrderConstants.ACTIVE_STATUS) {
			try {
				success = serviceOrderBo
						.releaseServiceOrderInActive(release.getSoId(), release
								.getReasonCode(), release.getComment(), release
								.getResourceId(), securityContext);
			} catch (BusinessServiceException bse) {
				logger.error("Cancel requested reschedule unsuccessful", bse);
			}
		} else if (release.getStatusId().intValue() == OrderConstants.PROBLEM_STATUS) {
			try {
				success = serviceOrderBo
						.releaseServiceOrderInProblem(release.getSoId(),
								release.getReasonCode(), release.getComment(),
								release.getResourceId(), securityContext);
			} catch (BusinessServiceException bse) {
				logger.error("Cancel requested reschedule unsuccessful", bse);
			}

		}
		return success;
	}

	private SecurityContext getSecurityContext() {
		@SuppressWarnings("unchecked")
		Map<String, Object> sessionMap = (Map<String, Object>) ActionContext
				.getContext().getSession();
		SecurityContext securityContext = null;
		if (sessionMap != null) {
			securityContext = (SecurityContext) sessionMap
					.get(Constants.SESSION.SECURITY_CONTEXT);
		}
		return securityContext;
	}

	public List<SOOnsiteVisitVO> getTimeOnSiteResults(String soId)
			throws BusinessServiceException {

		List<SOOnsiteVisitVO> results;

		try {

			results = timeOnSiteVisitBO.getTimeOnSiteResults(soId);

		} catch (BusinessServiceException bse) {
			logger
					.error(
							"SODetailsDelegateImpl :Unable to get the OnsiteVisit records  for thr given ServiceOrderID :"
									+ soId, bse);
			throw new BusinessServiceException(bse.getMessage(), bse);
		}

		return results;
	}
	
	// R12.0 Sprint 3 : Time On Site changes to incorporate trip.
	public List<SOOnsiteVisitResultVO> getTimeOnSiteRecords(String soId) throws BusinessServiceException {
		List<SOOnsiteVisitResultVO> results = null;
		try {
			results = timeOnSiteVisitBO.getTimeOnSiteRecords(soId);
		} catch (BusinessServiceException bse) {
			logger.error("SODetailsDelegateImpl :Unable to get the OnsiteVisit records  for the given ServiceOrderID :"
									+ soId, bse);
			throw new BusinessServiceException(bse.getMessage(), bse);
		}
		return results;
	}

	public void UpdateTimeOnSiteRow(SOOnsiteVisitVO soOnsiteVisitVO)
			throws BusinessServiceException {

		try {

			timeOnSiteVisitBO.UpdateTimeOnSiteRow(soOnsiteVisitVO);
			
			if (null != soOnsiteVisitVO.getDepartureDate() && null != soOnsiteVisitVO.getBuyerId() && soOnsiteVisitVO.getBuyerId().length() > 0) {
				// Senting Notification for Relay Services
				Integer buyerId = Integer.parseInt(soOnsiteVisitVO.getBuyerId());
				boolean relayServicesNotifyFlag = relayNotificationService.isRelayServicesNotificationNeeded(buyerId, soOnsiteVisitVO.getSoId());
				logger.info("calling relay webhook event for checkout: " + relayServicesNotifyFlag);
				logger.debug("calling relay webhook event for checkout: " + relayServicesNotifyFlag + " " + soOnsiteVisitVO.getSoId());
				if (relayServicesNotifyFlag) {
					relayNotificationService.sentNotificationRelayServices(MPConstants.TIME_ONSITE_API_EVENT, soOnsiteVisitVO.getSoId());
				} 
			}

		} catch (BusinessServiceException bse) {
			logger
					.error(
							"SODetailsDelegateImpl :Unable to update the OnsiteVisit record ",
							bse);
			throw new BusinessServiceException(bse.getMessage(), bse);
		}

	}

	public void InsertVisitResult(SOOnsiteVisitVO soOnsiteVisitVO)
			throws BusinessServiceException {

		if(ofHelper.isNewSo(soOnsiteVisitVO.getSoId())){
		    SOOnSiteVisit onSiteVisit = new SOOnSiteVisit();
		    onSiteVisit.setArrivalDate(soOnsiteVisitVO.getArrivalDate());
		    onSiteVisit.setArrivalInputMethod(soOnsiteVisitVO.getArrivalInputMethod());
		    onSiteVisit.setDeleteIndicator(soOnsiteVisitVO.getDeleteInd());
		    if (soOnsiteVisitVO.getDepartureCondition() != null)
		    	onSiteVisit.setDepartureCondition(soOnsiteVisitVO.getDepartureCondition().toString());
		    onSiteVisit.setDepartureDate(soOnsiteVisitVO.getDepartureDate());
		    onSiteVisit.setDepartureInputMethod(soOnsiteVisitVO.getDepartureInputMethod());
		    if(soOnsiteVisitVO.getDepartureResourceId() != null)
		    	onSiteVisit.setDepartureResourceId((long)soOnsiteVisitVO.getDepartureResourceId());
		    onSiteVisit.setIvrCreateDate(soOnsiteVisitVO.getIvrcreatedate());
		    if(soOnsiteVisitVO.getResourceId() != null)
		    	onSiteVisit.setResourceId((long)soOnsiteVisitVO.getResourceId());
		    if(soOnsiteVisitVO.getArrivalReason() != null) 
		    	onSiteVisit.setArrivalReason(soOnsiteVisitVO.getArrivalReason());
		    
		    OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soOnsiteVisitVO.getSoId(), SignalType.ON_SITE_VISIT, onSiteVisit, OFUtils.createOFIdentityFromSecurityContext(getSecurityContext()));
		    if (response.isError()){
		    	throw new BusinessServiceException(response.getErrorMessage());
		    }
		    return;
		}
		
		try {
			timeOnSiteVisitBO.InsertVisitResult(soOnsiteVisitVO);
			if (serviceOrderBo.getServiceOrder(soOnsiteVisitVO.getSoId())
					.getWfStateId() == OrderConstants.ACCEPTED_STATUS) {
				serviceOrderBo.activateAcceptedSO(soOnsiteVisitVO.getSoId(),
						getSecurityContext());
			}
		} catch (BusinessServiceException bse) {

			logger
					.error("SODetailsDelegateImpl :Unable to insert the OnsiteVisit record  ");
			throw new BusinessServiceException(bse.getMessage(), bse);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.newco.marketplace.business.iBusiness.onSiteVisit.IOnSiteVisitBO.
	 * getVisitResourceName(java.lang.Integer)
	 */

	public Contact getVisitResourceName(Integer resourceId)
			throws BusinessServiceException {

		Contact result;
		try {
			result = timeOnSiteVisitBO.getVisitResourceName(resourceId);

		} catch (BusinessServiceException bse) {

			logger
					.error("SODetailsDelegateImpl :Unable to Fetch the Conatct  record for given resourceid ");
			throw new BusinessServiceException(bse.getMessage(), bse);
		}

		return result;
	}

	
	
	public IServiceOrderMonitor getSoMonitor() {
		return soMonitor;
	}

	public void setSoMonitor(IServiceOrderMonitor soMonitor) {
		this.soMonitor = soMonitor;
	}

	public IOnSiteVisitBO getTimeOnSiteVisitBO() {
		return timeOnSiteVisitBO;
	}

	public void setTimeOnSiteVisitBO(IOnSiteVisitBO timeOnSiteVisitBO) {
		this.timeOnSiteVisitBO = timeOnSiteVisitBO;
	}

	public ArrayList<SOContactDTO> getRoutedResources(String soId)
			throws BusinessServiceException {
		ArrayList<SOContactDTO> routedResourcesList = null;
		try {
			SecurityContext securityContext = getSecurityContext();
			Integer companyId = (Integer) securityContext.getCompanyId();
			ArrayList<Contact> result = serviceOrderBo.getRoutedResources(soId,
					companyId.toString());
			routedResourcesList = ObjectMapperDetails
					.convertVOListtoContactDTOList(result);

		} catch (BusinessServiceException bse) {
			logger
					.error("SODetailsDelegateImpl:Unable to routed resources list for given service order");
			throw new BusinessServiceException(bse.getMessage(), bse);
		}
		return routedResourcesList;
	}

	public ProcessResponse saveReassignSO(SOLoggingDTO soLoggingDTO, String reassignReason) {
		ProcessResponse success = new ProcessResponse();
		SecurityContext securityContext = getSecurityContext();
		try {
			SoLoggingVo soLoggingVO = ObjectMapperDetails
					.convertLoggingDTOtoLoggingVO(soLoggingDTO);
			// Setting values in ServiceOrderNoteVo for inserting into so_notes
			// table also
			ServiceOrderNote soNote = new ServiceOrderNote();
			soNote.setSoId(soLoggingDTO.getServiceOrderNo());
			soNote.setCreatedDate(soLoggingDTO.getCreatedDate());
			soNote.setSubject(Constants.REASSIGNMENT_NOTE_SUBJECT);
			soNote.setRoleId(soLoggingDTO.getRoleId());
			soNote.setNote(soLoggingDTO.getComment());
			String createdByName = getCreatedByNameForNotes(soLoggingDTO.getCreatedByName(), " ");
			soNote.setCreatedByName(createdByName);
			soNote.setModifiedBy(soLoggingDTO.getModifiedBy());
			soNote.setModifiedDate(soLoggingDTO.getModifiedDate());
			soNote.setNoteTypeId(new Integer(2));
			soNote.setEntityId(soLoggingDTO.getEntityId());
			soNote.setPrivateId(new Integer(0));
					success = serviceOrderBo.saveReassignSO(soLoggingVO,soNote,reassignReason, securityContext);									} catch (BusinessServiceException bse) {




			logger.error("Cancel requested reschedule unsuccessful", bse);
		}
		return success;
	}

	/**
	 * Method which sets the created name for so notes from firstName lastname format.
	 * @param createdByName : firstname lasname.
	 * @param seperator : Char which separats frist name and last name.
	 * @return createdName  : lastName, firstName
	 * **/
	private String getCreatedByNameForNotes(String createdByName, String seperator){
		String firstName = "";
		String lastName= "";
		seperator = StringUtils.isBlank(seperator)?" ":seperator;
		if(StringUtils.isNotBlank(createdByName)){
			List<String> listStr = new ArrayList<String>(Arrays.asList(createdByName.split(" ")));
			//Enhanced for loop is not used to avoid the rare scenario that firstName or last name is Null
			if(null != listStr && listStr.size()>0){
				firstName = listStr.get(0);
				if(listStr.size() > 1){
					lastName = listStr.get(1);
				}
			}
		}
		return lastName +", "+firstName;
	}
	
	public ServiceOrder getDSTTimezone(ServiceOrder serviceOrder) {
		String timezone = serviceOrder.getServiceLocationTimeZone();
		if ("EST5EDT".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("EDT");
		}
		if ("AST4ADT".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("ADT");
		}
		if ("CST6CDT".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("CDT");
		}
		if ("MST7MDT".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("MDT");
		}
		if ("PST8PDT".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("PDT");
		}
		if ("HST".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("HADT");
		}
		if ("Etc/GMT+1".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("CEDT");
		}
		if ("AST".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("AKDT");
		}
		return serviceOrder;
	}
	
	public void getDSTTimezoneForSlots(ServiceDatetimeSlot serviceDatetimeSlot) {
		String timezone = serviceDatetimeSlot.getTimeZone();
		if ("EST5EDT".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("EDT");
		}
		if ("AST4ADT".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("ADT");
		}
		if ("CST6CDT".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("CDT");
		}
		if ("MST7MDT".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("MDT");
		}
		if ("PST8PDT".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("PDT");
		}
		if ("HST".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("HADT");
		}
		if ("Etc/GMT+1".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("CEDT");
		}
		if ("AST".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("AKDT");
		}
		
	}

	public ServiceOrder getStandardTimezone(ServiceOrder serviceOrder) {
		String timezone = serviceOrder.getServiceLocationTimeZone();
		if ("EST5EDT".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("EST");
		}
		if ("AST4ADT".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("AST");
		}
		if ("CST6CDT".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("CST");
		}
		if ("MST7MDT".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("MST");
		}
		if ("PST8PDT".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("PST");
		}
		if ("HST".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("HAST");
		}
		if ("Etc/GMT+1".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("CET");
		}
		if ("AST".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("AKST");
		}
		if ("Etc/GMT-9".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("PST-7");
		}
		if ("MIT".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("PST-3");
		}
		if ("NST".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("PST-4");
		}
		if ("Etc/GMT-10".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("PST-6");
		}
		if ("Etc/GMT-11".equals(timezone)) {
			serviceOrder.setServiceLocationTimeZone("PST-5");
		}
		return serviceOrder;
	}
	
	public void getStandardTimezoneForSlots(ServiceDatetimeSlot serviceDatetimeSlot) {
		String timezone = serviceDatetimeSlot.getTimeZone();
		if ("EST5EDT".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("EST");
		}
		if ("AST4ADT".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("AST");
		}
		if ("CST6CDT".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("CST");
		}
		if ("MST7MDT".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("MST");
		}
		if ("PST8PDT".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("PST");
		}
		if ("HST".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("HAST");
		}
		if ("Etc/GMT+1".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("CET");
		}
		if ("AST".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("AKST");
		}
		if ("Etc/GMT-9".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("PST-7");
		}
		if ("MIT".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("PST-3");
		}
		if ("NST".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("PST-4");
		}
		if ("Etc/GMT-10".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("PST-6");
		}
		if ("Etc/GMT-11".equals(timezone)) {
			serviceDatetimeSlot.setTimeZone("PST-5");
		}
		
	}

	public String groupedServiceOrderAccept(String groupId, String userName,
			Integer resourceId, Integer companyId, Integer termAndCond,
			boolean validate,String assignee) throws BusinessServiceException {
		SignalType signalType = null;
		SecurityContext securityContext = getSecurityContext();
		if(ofHelper.isNewGroup(groupId)){
		    OrderFulfillmentRequest ofRequest = this.getRequestForAcceptance(companyId.longValue(), resourceId.longValue(), termAndCond, securityContext,assignee);
		    if(OrderConstants.ASSIGNMENT_TYPE_FIRM.equals(assignee)){
		    	signalType = SignalType.ACCEPT_GROUP_FOR_FIRM;
		    }else{
		    	signalType = SignalType.ACCEPT_GROUP;
		    }
		    OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentGroupProcess(groupId,signalType, ofRequest);
		    if (response.isError()){
		    	return response.getErrorMessage();
		    }
		    return "SUCCESS";
		}
		
		List<ServiceOrderSearchResultsVO> groupedOrdsList = null;
		try {
			groupedOrdsList = orderGrpBO.getServiceOrdersForGroup(groupId);
		} catch (Exception e) {
			throw new BusinessServiceException(
					"error occured -- groupedServiceOrderAccept --orderGrpBO.getServiceOrdersForGroup(groupId)");
		}

		String strMessage = "";
		ProcessResponse pr = null;
		// iterate through to check if any of the child SO's are in edit mode.
		for (ServiceOrderSearchResultsVO soResult : groupedOrdsList) {
			if (soResult.getLockEditInd() != null && soResult.getLockEditInd().equals(new Integer(OrderConstants.SO_EDIT_MODE_FLAG))){
				strMessage=OrderConstants.ORDER_BEING_EDITED;
				return strMessage;
			}
		}
		// iterate thru all orders in group, call accept Service Order for each
		// SO
		for (ServiceOrderSearchResultsVO soResult : groupedOrdsList) {
			String soId = soResult.getSoId();

			pr = this.serviceOrderBo.processAcceptServiceOrder(soId,
					resourceId, companyId, termAndCond, validate, false, false,
					securityContext);
			// TODO modify AOP call so that email is not sent for each SO
			strMessage = pr.getMessages().get(0);
			if (pr.isError()) {
				return strMessage;
			}
		}

		// TODO accept grouped order-- update grouped routed provider
		if (ServiceConstants.VALID_RC.equals(pr.getCode())) {
			pr = orderGrpBO.processAcceptGroupOrder(groupId, resourceId,
					companyId, termAndCond, validate, securityContext);
			// TODO AOP call to sent email for group order
			orderGrpBO.sendallProviderResponseExceptAcceptedForGroup(groupId,
					securityContext);

		}
		StringBuffer sbMessages = new StringBuffer();
		for (String msg : pr.getMessages()) {
			sbMessages.append(msg).append("\n");
		}
		strMessage = sbMessages.toString();
		return strMessage;

	}

	public List<ServiceOrderNote> serviceOrderGetAllGroupNotes(
			ServiceOrderNoteDTO soNoteDTO) throws BusinessServiceException {

		ServiceOrderNote soNote = new ServiceOrderNote();
		ProcessResponse pr = null;

		String soId = soNoteDTO.getSoId();
		ServiceOrder serviceOrder = serviceOrderBo.getServiceOrder(soId);
		if (serviceOrder == null)
			return null;

		String groupId = serviceOrder.getGroupId();
		// If the order is accepted, get the original group ID
		if (StringUtils.isBlank(groupId)) {
			groupId = serviceOrder.getOrignalGroupId();
		}
		// get notes for groupId
		if (StringUtils.isNotBlank(groupId)) {
			ServiceOrderNoteDTO soNoteDTOUpdated = soNoteDTO;
			soNoteDTOUpdated.setSoId(groupId);
			soNote = ObjectMapperDetails
					.convertServiceOrderNoteDTOtoVO(soNoteDTOUpdated);
			pr = getServiceOrderBo().processGetAllSONotes(soNote);
			// revert the id to the ID that has been passed to this method
			soNoteDTO.setSoId(soId);
		}
		if (pr == null) {
			return null;
		} else {
			@SuppressWarnings("unchecked")
			ArrayList<ServiceOrderNote> soNoteList = (ArrayList<ServiceOrderNote>) pr.getObj();
			return soNoteList;
		}
	}

	public List<ServiceOrderNote> getAllChildNotes(String groupId) {
		if (StringUtils.isBlank(groupId))
			return null;

		List<ServiceOrderNote> notes = new ArrayList<ServiceOrderNote>();
		List<ServiceOrderSearchResultsVO> soList = null;
		try {
			// Get all the child orders
			soList = orderGrpBO.getServiceOrdersForGroup(groupId);
		} catch (com.newco.marketplace.exception.BusinessServiceException e) {
			e.printStackTrace();
		}

		// Loop thru child orders and get the list of notes. Add these to
		// aggregated notes list.
		List<ServiceOrderNote> soNotes = null;
		for (ServiceOrderSearchResultsVO so : soList) {
			ServiceOrderNoteDTO soNoteDTO = new ServiceOrderNoteDTO();
			soNoteDTO.setSoId(so.getSoId());
			try {
				soNotes = serviceOrderGetAllNotes(soNoteDTO);
				notes.addAll(soNotes);
			} catch (DataServiceException e) {
				e.printStackTrace();
			}
		}

		return notes;
	}

	public String getFirstChildInGroup(String groupId) {
		String childOrderId = "";
		try {
			List<ServiceOrderSearchResultsVO> soListInGroup = orderGrpBO
					.getServiceOrdersForGroup(groupId);
			if (soListInGroup != null && soListInGroup.size() > 0) {
				childOrderId = soListInGroup.get(0).getSoId();
			}
		} catch (Exception e) {
			logger.error("Error while getting 1st child for group");
		}

		return childOrderId;
	}
	
	public String getFirstChildInOrigGroup(String origGroupId) {
		String childOrderId = "";
		try {
			List<ServiceOrderSearchResultsVO> soListInGroup = orderGrpBO
					.getServiceOrdersForOrigGroup(origGroupId);
			if (soListInGroup != null && soListInGroup.size() > 0) {
				childOrderId = soListInGroup.get(0).getSoId();
			}
		} catch (Exception e) {
			logger.error("Error while getting 1st child for group");
		}

		return childOrderId;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ISODetailsDelegate#getWFMQueueDetails(java.lang.String, java.lang.String)
	 */
	public List<WFMBuyerQueueDTO> getWFMQueueDetails(String buyerId,
			String soId) throws BusinessServiceException{
		List<WFMBuyerQueueVO> wfmBuyerQueueVOList = powerBuyerBO.getWFMQueueDetails(buyerId, soId);
		List<WFMBuyerQueueDTO> wfmBuyerQueueDTOList = new ArrayList<WFMBuyerQueueDTO>();
		wfmBuyerQueueDTOList = wfmBuyerQueueMapper.convertVOListtoDTOList(
				wfmBuyerQueueVOList, wfmBuyerQueueDTOList);
		return wfmBuyerQueueDTOList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ISODetailsDelegate#getWFMQueueAndTasks(java.lang.String, java.lang.String)
	 */
	public List<WFMBuyerQueueDTO> getWFMQueueAndTasks(String buyerId,
			String soId) throws BusinessServiceException{
		QueueTasksGroupVO queueTasksGroupVO = powerBuyerBO.getWFMQueueAndTasks(buyerId, soId);
		List<WFMBuyerQueueDTO> wfmBuyerQueueDTOList = new ArrayList<WFMBuyerQueueDTO>();
		wfmBuyerQueueDTOList = wfmBuyerQueueMapper.convertQueueVOtoDTO(
				queueTasksGroupVO, wfmBuyerQueueDTOList);
		return wfmBuyerQueueDTOList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ISODetailsDelegate#getWFMQueueAndTasks(java.lang.String, java.lang.String)
	 */
	public List<WFMBuyerQueueDTO> getWFMQueueAndTasks(String buyerId,
			String soId, String groupId) throws BusinessServiceException{
		QueueTasksGroupVO queueTasksGroupVO = powerBuyerBO.getWFMQueueAndTasks(buyerId, soId, groupId);
		List<WFMBuyerQueueDTO> wfmBuyerQueueDTOList = new ArrayList<WFMBuyerQueueDTO>();
		wfmBuyerQueueDTOList = wfmBuyerQueueMapper.convertQueueVOtoDTO(
				queueTasksGroupVO, wfmBuyerQueueDTOList);
		return wfmBuyerQueueDTOList;
	}
	
	public WFMBuyerQueueDTO getWFMCallBackQueueAndTasks(String buyerId) throws BusinessServiceException{
		List<WFMSOTasksVO> wfmSOTasksVO = powerBuyerBO.getWFMCallBackQueueAndTasks(buyerId);
		WFMBuyerQueueDTO wfmBuyerQueueDTO = new WFMBuyerQueueDTO();
		wfmBuyerQueueDTO = wfmBuyerQueueMapper.convertQueueVOtoDTO(
				wfmSOTasksVO, wfmBuyerQueueDTO, buyerId);
		return wfmBuyerQueueDTO;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ISODetailsDelegate#updateCompleteIndicator(com.newco.marketplace.web.dto.ajax.SOQueueNoteDTO)
	 */
	public int updateCompleteIndicator(SOQueueNoteDTO sOQueueNoteDTO)  throws BusinessServiceException{
		RequeueSOVO requeueSOVO = wfmBuyerQueueMapper.convertDTOtoRequeueVO(sOQueueNoteDTO);
		int result = powerBuyerBO.updateCompleteIndicator(requeueSOVO);
		
		return result;
		
	}
	
	public boolean validateFeature(Integer buyerId, String feature)
	{
		return buyerFeatureSetBO.validateFeature(buyerId, feature);
	}
	
	public boolean validateServiceOrderFeature(String soId, String feature) {
		return getServiceOrderFeatureSetBO().validateFeature(soId, feature);
	}
	
	public void setServiceOrderFeature(String soId, String feature) {
		getServiceOrderFeatureSetBO().setFeature(soId, feature);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ISODetailsDelegate#updateRequeueDateTime(com.newco.marketplace.web.dto.ajax.SOQueueNoteDTO)
	 */
	public int updateRequeueDateTime(SOQueueNoteDTO sOQueueNoteDTO)  throws BusinessServiceException{
		RequeueSOVO requeueSOVO = wfmBuyerQueueMapper.convertDTOtoRequeueVO(sOQueueNoteDTO);
		int result = powerBuyerBO.updateRequeueDateTime(requeueSOVO);
		return result;
	}

	
	public int insertNewCallBackQueue(SOQueueNoteDTO sOQueueNoteDTO) throws BusinessServiceException{
		
		RequeueSOVO requeueSOVO = wfmBuyerQueueMapper.convertDTOtoRequeueVO(sOQueueNoteDTO);
		int result = powerBuyerBO.insertNewCallBackQueue(requeueSOVO);
		// TODO : return appropriately.
		return result;
		
	}
	

	/**
	 * Returns all child So Ids associated with the group order
	 * @param groupId
	 * @return List <String> soIds
	 * @throws BusinessServiceException
	 */
	public List<String> getServiceOrderIDsForGroup(String groupId) throws BusinessServiceException {
		List<String> soIds = new ArrayList<String>();
		try {
			soIds = serviceOrderBo.getServiceOrderIDsForGroup(groupId);	
		} catch(BusinessServiceException bse) {
			logger.debug("Exception thrown while getting service order IDs for Group ID (" + groupId + ")");
			throw bse;
		}
		
		return soIds;
	}

	/**
	 * Returns the associated incidents
	 * @param soId
	 * @return List<AssociatedIncidentVO> associatedIncidents
	 * @throws BusinessServiceException
	 */
	public List<AssociatedIncidentVO> getAssociatedIncidents(String soId) throws BusinessServiceException{
		List<AssociatedIncidentVO> associatedIncidents = null;
 		try {
 			 associatedIncidents = serviceOrderBo.getAssociatedIncidents(soId);		
 		}catch (BusinessServiceException bse) {
 			logger.error("SODetailsDelegateImpl:Unable to get associated incidents for given service order");
 			throw new BusinessServiceException(bse.getMessage(),bse);
 		}
  		return associatedIncidents;	
	}

	/**
	 * Returns first soId from a group
	 * 
	 * @param groupId
	 * @return String soId
	 * @throws BusinessServiceException
	 */

	public String getFirstSoIdForGroup(String groupId)
			throws BusinessServiceException {
		try {
			return orderGrpBO.getFirstSoIdForGroup(groupId);
		} catch (Exception e) {
			throw new BusinessServiceException(
					"Error in getFirstSoIdForGroup(groupId)" + e.getMessage());
		}
	}
	/**
	 * Concatenate the Street1,Street2,City,State,Zip
	 * 
	 * @param street1,street2,city,state,zip
	 * @return String 
	 * @throws BusinessServiceException
	 */
	private String concatenateAddress(String street1,String street2,String city, String state,String zip){
		StringBuilder dispatchAddress=new StringBuilder();
		if(StringUtils.isNotBlank(street1)){
			dispatchAddress.append(street1);
			dispatchAddress.append(Constants.SPACE);
		}
		if(StringUtils.isNotBlank(street2)){
			dispatchAddress.append(street2);
			dispatchAddress.append(Constants.SPACE);
		}
		if(StringUtils.isNotBlank(city)){
			dispatchAddress.append(city);
			dispatchAddress.append(Constants.SPACE);
		}
		if(StringUtils.isNotBlank(state)){
			dispatchAddress.append(state);
			dispatchAddress.append(Constants.SPACE);
		}
		if(StringUtils.isNotBlank(zip)){
			dispatchAddress.append(zip);
		}
		return dispatchAddress.toString();
	}
	/**
	 * Method to fetch the service order status and completed date 
	 * @param soId
	 * @return ServiceOrder 
	 * @throws DelegateException
	 */
	public ServiceOrder getServiceOrderStatusAndCompletdDate(String soId ) throws DelegateException{
		ServiceOrder so = null;
		try{
			so = getServiceOrderBo().getServiceOrderStatusAndCompletdDate(soId);
		}catch(Exception e){
			throw new DelegateException("Error occurred @ getServiceOrderStatusAndCompletdDate in SODetailsDelegate: " +e);
		}
		return so;
	}
	
	public int getTheRemainingTimeToAcceptSO(String soId, Integer resourceId) throws BusinessServiceException {
		int remainingSecondsToWait = 0;
		try{
			remainingSecondsToWait = getServiceOrderBo().getTheRemainingTimeToAcceptSO(soId, resourceId);
		}catch(Exception e){
			throw new BusinessServiceException("Error occurred @ getTheRemainingTimeToAcceptSO in SODetailsDelegate: " +e);
		}
        return remainingSecondsToWait;
    }
	
	public int getTheRemainingTimeToAcceptSOForFirm(String soId, Integer vendorId) throws BusinessServiceException{
		int remainingSecondsToWait = 0;
		try{
			remainingSecondsToWait = getServiceOrderBo().getTheRemainingTimeToAcceptSOForFirm(soId, vendorId);
		}catch(Exception e){
			throw new BusinessServiceException("Error occurred @ getTheRemainingTimeToAcceptSO in SODetailsDelegate: " +e);
		}
        return remainingSecondsToWait;
	}
	
	public int getTheRemainingTimeToAcceptGrpOrder(String groupId, Integer resourceId) throws BusinessServiceException{
		int remainingSecondsToWait = 0;
		try{
			remainingSecondsToWait = routeOrderGroupBO.getTheRemainingTimeToAcceptGrpOrder(groupId, resourceId);
		}catch(Exception e){
			throw new BusinessServiceException("Error occurred @ getTheRemainingTimeToAcceptSO in SODetailsDelegate: " +e);
		}
        return remainingSecondsToWait;
	}
	
	/**
	 * Method gets the reasons list for the selected counter offer condition
	 * @param providerRespId
	 * @return List<CounterOfferReasonsVO>
	 * @throws DelegateException
	 */
	public List<CounterOfferReasonsVO> getReasonsForSelectedCounterOffer(int providerRespId) throws DelegateException{
		List<CounterOfferReasonsVO> counterOfferReasonsList = null;
		try {
			counterOfferReasonsList = serviceOrderBo.getReasonsForSelectedCounterOffer(providerRespId);
		} catch (BusinessServiceException bse) {
			throw new DelegateException(
					"Error occured while getting selected counter offer reasons in SODetailsDelegateImpl.getReasonsForSelectedCounterOffer()",
					bse);
		}
		return counterOfferReasonsList;
	}

	public void sendEmailForNoteOrQuestion(String emailTo,  String soID, String soTitle, String message, String roleInd)
	{		
		EmailVO emailVO = new EmailVO();
		
		emailVO.setTo(emailTo);
		emailVO.setFrom(AlertConstants.SERVICE_LIVE_MAILID);
		emailVO.setMessage(message);
		emailVO.setTemplateId(13);
		
		try
		{
			emailTemplateBO.sendNoteOrQuestionEmail(emailVO, soID, soTitle, roleInd);
		}
		catch (MessagingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public IOrderGroupBO getOrderGrpBO() {
		return orderGrpBO;
	}

	public void setOrderGrpBO(IOrderGroupBO orderGrpBO) {
		this.orderGrpBO = orderGrpBO;
	}

	public PromoBO getPromoBO() {
		return promoBO;
	}

	public void setPromoBO(PromoBO promoBO) {
		this.promoBO = promoBO;
	}

	public IPowerBuyerBO getPowerBuyerBO() {
		return powerBuyerBO;
	}

	public void setPowerBuyerBO(IPowerBuyerBO powerBuyerBO) {
		this.powerBuyerBO = powerBuyerBO;
	}

	public WFMBuyerQueueMapper getWfmBuyerQueueMapper() {
		return wfmBuyerQueueMapper;
	}

	public void setWfmBuyerQueueMapper(WFMBuyerQueueMapper wfmBuyerQueueMapper) {
		this.wfmBuyerQueueMapper = wfmBuyerQueueMapper;
	}

	public IServiceOrderCloseBO getServiceOrderCloseBo() {
		return serviceOrderCloseBo;
	}

	public void setServiceOrderCloseBo(IServiceOrderCloseBO serviceOrderCloseBo) {
		this.serviceOrderCloseBo = serviceOrderCloseBo;
	}

	public IServiceOrderUpsellBO getUpsellBO() {
		return upsellBO;
	}

	public void setUpsellBO(IServiceOrderUpsellBO upsellBO) {
		this.upsellBO = upsellBO;
	}

	public boolean isMaxFollowUpCountReached(SOQueueNoteDTO sOQueueNoteDTO) throws BusinessServiceException {
		RequeueSOVO requeueSOVO = wfmBuyerQueueMapper.convertDTOtoRequeueVO(sOQueueNoteDTO);
		boolean result = powerBuyerBO.isMaxFollowUpCountReached(requeueSOVO);
		return result;
	}

	public PBFilterVO getDestinationTabForSO(String soId) throws BusinessServiceException {
		return powerBuyerBO.getDestinationTabForSO(soId);
	}
	
	public String getGroupId(String soId) throws BusinessServiceException {
		return powerBuyerBO.getGroupId(soId);
	}

	public IBuyerFeatureSetBO getBuyerFeatureSetBO() {
		return buyerFeatureSetBO;
	}

	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}

	public IRouteOrderGroupBO getRouteOrderGroupBO() {
		return routeOrderGroupBO;
	}

	public void setRouteOrderGroupBO(IRouteOrderGroupBO routeOrderGroupBO) {
		this.routeOrderGroupBO = routeOrderGroupBO;
	}

	public ProcessResponse processCreateBid(String soId, Integer resourceId, Integer vendorId, java.util.Date bidDate,
			BigDecimal totalLabor, BigDecimal totalHours, BigDecimal partsMaterials, java.util.Date bidExpirationDate,
			String bidExpirationTime, java.util.Date newServiceDateRangeTo, java.util.Date newServiceDateRangeFrom,
			String newServiceStartTime, String newServiceEndTime, String comment, BigDecimal totalLaborParts, SecurityContext securityContext)
	{
		if(ofHelper.isNewSo(soId)){
		    com.servicelive.orderfulfillment.domain.RoutedProvider routedProvider = OFUtils.createConditionalOffer(resourceId, vendorId, bidExpirationDate, bidExpirationTime,
		    		newServiceDateRangeFrom, newServiceStartTime, newServiceDateRangeTo, newServiceEndTime, totalLabor, totalHours, partsMaterials, comment, totalLaborParts);
		    List<com.servicelive.orderfulfillment.domain.RoutedProvider> firmRoutedProviders = new ArrayList<com.servicelive.orderfulfillment.domain.RoutedProvider>();
		    firmRoutedProviders.add(routedProvider);
		    SOElementCollection routedProviders=new SOElementCollection();
		    routedProviders.addAllElements(firmRoutedProviders);
		    OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.CREATE_CONDITIONAL_OFFER, routedProviders, OFUtils.createOFIdentityFromSecurityContext(getSecurityContext()));
		    return OFUtils.mapProcessResponse(response);
		}
		
		return serviceOrderBo.processCreateBid(soId, resourceId, bidDate, totalLabor, totalHours, partsMaterials, bidExpirationDate, bidExpirationTime, newServiceDateRangeTo, newServiceDateRangeFrom, newServiceStartTime, newServiceEndTime, comment, totalLaborParts, securityContext);
	}
	
	public int getBidNoteCount(String soID)
	{
		ActivityFilter filter = new ActivityFilter();
		filter.addOrderId(soID);
		filter.addActivityType(ActivityType.Post);
		filter.addActivityStatusType(ActivityStatusType.ENABLED);
		ActivityCounts count = getActivityLogHelper().getActivityCounts(filter);
		return count.getTotalCount().intValue();
	}
	
	public int getNewBidNoteCount(String soID) {
		List<String> orders = new ArrayList<String>();
		orders.add(soID);
		ActivityCounts count = getActivityLogHelper().getBuyersActionablePostCountsForOrders(orders);
		return count.getTotalCount().intValue();
	}
	
	public int getNewBidCount(String soID) {
		List<String> orders = new ArrayList<String>();
		orders.add(soID);
		ActivityCounts count = getActivityLogHelper().getBuyersActionableBidCountsForOrders(orders);
		return count.getTotalCount().intValue();
	}
	
	public ActivityLog getMostRecentCounterOffer(String soID, Integer providerResourceId) {
		ActivityFilter filter = new ActivityFilter();
		filter.addOrderId(soID);
		filter.addProviderResourceId(providerResourceId.longValue());
		filter.addActivityType(ActivityType.Bid);
		filter.addActivityStatusType(ActivityStatusType.ENABLED);
		List<ActivityLog> counterOffers = getActivityLogHelper().findActivities(filter);
		
		ActivityLog mostRecentCounterOffer = null;
		for (ActivityLog thisCounterOffer : counterOffers) {
			if (mostRecentCounterOffer == null) {
				mostRecentCounterOffer = thisCounterOffer;
			} else if (thisCounterOffer.getCreatedOn().after(mostRecentCounterOffer.getCreatedOn())) {
				mostRecentCounterOffer = thisCounterOffer;
			}
		}	
		
		return mostRecentCounterOffer;
	}
	
	public void markBidAsReadByBuyer(Long bidId, String reason, String userId) {
		getActivityLogHelper().markBidAsReadByBuyer(bidId, reason, userId);
	}
	
	public void markBidAsRequiringFollowUpByBuyer(Long bidId, String reason,String userId) {
		getActivityLogHelper().markBidAsRequiringFollowUpByBuyer(bidId, reason, userId);
	}
	
	public void markPostAsReadByBuyer(Long postId, String associationType, String reason, String userId) {
		ActivityViewStatusAssociationType viewStatusAssociationType = null;
		if (associationType.equals("source")) {
			viewStatusAssociationType = ActivityViewStatusAssociationType.SOURCE;
		} else {
			viewStatusAssociationType = ActivityViewStatusAssociationType.TARGET;
		}
		
		getActivityLogHelper().updateActivityViewStatus(postId, ActivityViewStatusName.READ, viewStatusAssociationType, reason, userId);
	}
	
	public void markPostAsRequiringFollowUpByBuyer(Long postId, String associationType, String reason,String userId) {
		ActivityViewStatusAssociationType viewStatusAssociationType = null;
		if (associationType.equals("source")) {
			viewStatusAssociationType = ActivityViewStatusAssociationType.SOURCE;
		} else {
			viewStatusAssociationType = ActivityViewStatusAssociationType.TARGET;
		}
		
		getActivityLogHelper().updateActivityViewStatus(postId, ActivityViewStatusName.REQUIRES_FOLLOW_UP, viewStatusAssociationType, reason, userId);
	}

    public void setHelperOF(OFHelper helperOF) {
        this.ofHelper = helperOF;
    }

    /**
     * @param resourceBO the resourceBO to set
     */
    public void setResourceBO(IResourceBO resourceBO) {
        this.resourceBO = resourceBO;
    }

	public void setServiceOrderFeatureSetBO(IServiceOrderFeatureSetBO serviceOrderFeatureSetBO) {
		this.serviceOrderFeatureSetBO = serviceOrderFeatureSetBO;
	}

	public IServiceOrderFeatureSetBO getServiceOrderFeatureSetBO() {
		return serviceOrderFeatureSetBO;
	}

	public void setActivityLogHelper(IActivityLogHelper activityLogHelper) {
		this.activityLogHelper = activityLogHelper;
	}

	public IActivityLogHelper getActivityLogHelper() {
		return activityLogHelper;
	}

	public List<ActivityLog> getAllBidsForOrder(String soId) {
		ActivityFilter activityFilter = new ActivityFilter();
		activityFilter.addOrderId(soId);
		activityFilter.addActivityType(ActivityType.Bid);
		List<ActivityLog> orderBids = this.activityLogHelper.findActivities(activityFilter);
		return orderBids;
	}
	
	private void encryptCreditCardInfo(SOAdditionalPayment addPayment) {
		
		String creditCardNo = addPayment.getCreditCardNumber();
		if(StringUtils.isNotBlank(creditCardNo)) {
			CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(creditCardNo);
					
			//Commenting the code for SL-18789
			//cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
			//cryptographyVO =  cryptography.encryptKey(cryptographyVO);
			cryptographyVO.setKAlias(MPConstants.CC_ENCRYPTION_KEY);
			cryptographyVO =  cryptography128.encryptKey128Bit(cryptographyVO);
			addPayment.setCreditCardNumber(cryptographyVO.getResponse());
		}
		
	}
    private boolean  encryptCreditCardInfoTokenizeFailed(SOAdditionalPayment addPayment) {
		boolean encrypted = false;
		String creditCardNo = addPayment.getCreditCardNumber();
		if(StringUtils.isNotBlank(creditCardNo)) {
			CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(creditCardNo);
			cryptographyVO.setKAlias(MPConstants.CC_ENCRYPTION_KEY);
			cryptographyVO =  cryptography128.encryptKey128Bit(cryptographyVO);
			addPayment.setCreditCardNumber(cryptographyVO.getResponse());
			if(StringUtils.isNotBlank(cryptographyVO.getResponse())){
				encrypted = true;
			}
		}
		return encrypted;
		
	}
	public List<SoAutoCloseDetailVo> getSoAutoCloseCompletionList(String soId) throws DataServiceException {
		List<SoAutoCloseDetailVo> list = serviceOrderBo.getSoAutoCloseCompletionList(soId);
		return list;
	}
	
	public String  getUserName(Integer roleId,Integer resourceId) throws DataServiceException
	{
		String  userName=serviceOrderBo.getUserName(roleId,resourceId);
		return userName;
	}
	
	
	public List<PendingCancelHistoryVO> getPendingCancelHistory(String soId) throws DataServiceException
	{
		return (List<PendingCancelHistoryVO>) serviceOrderBo.getPendingCancelHistory(soId);
	}
	public PendingCancelPriceVO getPendingCancelBuyerDetails(String soId) throws DataServiceException
	{
		return (PendingCancelPriceVO) serviceOrderBo.getPendingCancelBuyerDetails(soId);
	}
	
	public PendingCancelPriceVO getPendingCancelBuyerPriceDetails(String soId) throws DataServiceException
	{
		return (PendingCancelPriceVO) serviceOrderBo.getPendingCancelBuyerPriceDetails(soId);
	}
	
	public PendingCancelPriceVO getPendingCancelProviderDetails(String soId) throws DataServiceException
	{
		return (PendingCancelPriceVO) serviceOrderBo.getPendingCancelProviderDetails(soId);
	}
	

	public IDocumentBO getDocumentBO() {
		return documentBO;
	}

	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}

	
	/**
	 * 
	 * @param Onsiterecords
	 * @param soId
	 * @return
	 * @throws Exception
	 */
	public ArrayList<SoChangeDetailVo> changeToSoChangeDetailVo(List<SOOnsiteVisitVO> onsiteRecords, String soId) throws Exception {
		ArrayList<SoChangeDetailVo> soChangedetailForTimeOnSiteList = new ArrayList<SoChangeDetailVo>();

		if (onsiteRecords != null) {
			for (SOOnsiteVisitVO soOnsiteVisitRecord : onsiteRecords) {
				Date arrivalDate = soOnsiteVisitRecord.getArrivalDate();
				if (arrivalDate != null) {
					SoChangeDetailVo soChangeDetailForArrivalEntry = new SoChangeDetailVo();
					soChangeDetailForArrivalEntry.setSoId(soId);

					Integer arrivalInputMethod = soOnsiteVisitRecord.getArrivalInputMethod();
					if (arrivalInputMethod != null && arrivalInputMethod == 1) {
						soChangeDetailForArrivalEntry.setRoleName(OrderConstants.SYSTEM_IVR_TEXT);
						soChangeDetailForArrivalEntry.setCreatedByName(OrderConstants.SYSTEM_IVR_TEXT);
						soChangeDetailForArrivalEntry.setEntityId(0);
						soChangeDetailForArrivalEntry.setActionDescription(OrderConstants.ARRIVAL_INPUT_IVR);
					} else if(arrivalInputMethod != null && arrivalInputMethod == 2){
						Contact result = (Contact) getVisitResourceName(soOnsiteVisitRecord.getResourceId());
						soChangeDetailForArrivalEntry.setCreatedByName(result.getFirstName() + OrderConstants.SPACE + result.getLastName());
						soChangeDetailForArrivalEntry.setRoleName(OrderConstants.ROLE_PROVIDER);
						soChangeDetailForArrivalEntry.setEntityId(soOnsiteVisitRecord.getResourceId());
						soChangeDetailForArrivalEntry.setActionDescription(OrderConstants.ARRIVAL_INPUT_MOBILE);
					}
					else {
						if (arrivalInputMethod != null) {
							Contact result = (Contact) getVisitResourceName(arrivalInputMethod);
							soChangeDetailForArrivalEntry.setCreatedByName(result.getFirstName() + OrderConstants.SPACE + result.getLastName());
							soChangeDetailForArrivalEntry.setRoleName(OrderConstants.ROLE_PROVIDER);
							soChangeDetailForArrivalEntry.setEntityId(arrivalInputMethod);
							soChangeDetailForArrivalEntry.setActionDescription(OrderConstants.ARRIVAL_INPUT_WEB);
}
					}
					Date createdDate = soOnsiteVisitRecord.getCreatedDate();
					if (createdDate != null) {
						soChangeDetailForArrivalEntry.setCreatedDate(createdDate);
					}
					String serviceLocTimeZone = getServiceOrderBo().getServiceLocTimeZone(soId);
					String arrivalDateString = getDateStringOnZipcode(arrivalDate, serviceLocTimeZone);
					soChangeDetailForArrivalEntry.setChgComment(OrderConstants.ARRIVAL_TEXT + arrivalDateString);
					
					soChangedetailForTimeOnSiteList.add(soChangeDetailForArrivalEntry);
				}
				
				Date departureDate = soOnsiteVisitRecord.getDepartureDate();
				if (departureDate != null) {
					SoChangeDetailVo soChangeDetailForDepartureEntry = new SoChangeDetailVo();
					soChangeDetailForDepartureEntry.setSoId(soId);

					Integer departureInputMethod = soOnsiteVisitRecord.getDepartureInputMethod();
					if (departureInputMethod != null && departureInputMethod == 1) {
						soChangeDetailForDepartureEntry.setRoleName(OrderConstants.SYSTEM_IVR_TEXT);
						soChangeDetailForDepartureEntry.setCreatedByName(OrderConstants.SYSTEM_IVR_TEXT);
						soChangeDetailForDepartureEntry.setActionDescription(OrderConstants.DEPARTURE_INPUT_IVR);
						soChangeDetailForDepartureEntry.setEntityId(0);
					} 
					else if(departureInputMethod != null && departureInputMethod == 2){
						Contact result = (Contact) getVisitResourceName(soOnsiteVisitRecord.getDepartureResourceId());
						soChangeDetailForDepartureEntry.setCreatedByName(result.getFirstName() + OrderConstants.SPACE + result.getLastName());
						soChangeDetailForDepartureEntry.setRoleName(OrderConstants.ROLE_PROVIDER);
						soChangeDetailForDepartureEntry.setActionDescription(OrderConstants.DEPARTURE_INPUT_MOBILE);
						soChangeDetailForDepartureEntry.setEntityId(soOnsiteVisitRecord.getDepartureResourceId());
					}else {
						if (departureInputMethod != null) {
							Contact result = (Contact) getVisitResourceName(departureInputMethod);
							soChangeDetailForDepartureEntry.setCreatedByName(result.getFirstName() + OrderConstants.SPACE + result.getLastName());
							soChangeDetailForDepartureEntry.setRoleName(OrderConstants.ROLE_PROVIDER);
							soChangeDetailForDepartureEntry.setActionDescription(OrderConstants.DEPARTURE_INPUT_WEB);
							soChangeDetailForDepartureEntry.setEntityId(departureInputMethod);
						}

					}
					Date modifiedDate = soOnsiteVisitRecord.getModifiedDate();
					if (modifiedDate != null) {
						soChangeDetailForDepartureEntry.setCreatedDate(modifiedDate);
					}
					String serviceLocTimeZone = getServiceOrderBo().getServiceLocTimeZone(soId);
					String departureDateString = getDateStringOnZipcode(departureDate, serviceLocTimeZone);
					soChangeDetailForDepartureEntry.setChgComment(OrderConstants.DEPARTURE_TEXT + departureDateString);

					soChangedetailForTimeOnSiteList.add(soChangeDetailForDepartureEntry);
				}
					
			}
		}
		return soChangedetailForTimeOnSiteList;
	}         	     


	public  static String getDateStringOnZipcode(Date gmtDate,String timeZone){
		String timezoneDate=null;
		Timestamp timeStamp = new Timestamp(gmtDate.getTime());
		String timeInGMT= TimeUtils.getTimePartforTimeonsite(gmtDate.toString());

		if(timeStamp!= null && timeInGMT!=null && timeZone!=null )
		{ 
			timezoneDate = TimeUtils.convertGMTToGivenTimeZoneInString(timeStamp,timeInGMT,timeZone);
			if (timezoneDate!=null) 
			{	
				return timezoneDate;
			}
			else
			{
				timezoneDate="";
				logger.error("SODetailsDelegateImpl: unable to convert the date to timezone date");
			}
		}
		return timezoneDate;
	}
	
	public Double getCancelFeeForBuyer(Integer buyerId)
	{
		return buyerFeatureSetBO.getCancelFeeForBuyer(buyerId);
	}
	/**
	 * This method retrieves the count of document types for a buyer
	 * @return Arraylist<BuyerDocumentTypeVO>
	 */
	public Integer retrieveDocTypesCountByBuyerId(Integer buyerId,Integer source) throws BusinessServiceException {
		
		Integer docCount= null;	
		docCount =  documentBO.retrieveDocTypesCountByBuyerId(buyerId,source);	
		return docCount;
	}	
	//SL 18418 Changes for CAR routed Orders the substatus is not set as Exclusive
	//Calling method when the so is in posted state
	public String  getMethodOfRouting(String soId)
	{
		String methodOfRouting=null;
		try {
			 methodOfRouting=getServiceOrderBo().getMethodOfRouting(soId);
		
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return methodOfRouting;
	}
	public List<PreCallHistory> getScheduleHistory(String soId,
			Integer acceptedVendorId)
	{
		List<PreCallHistory> callHistory = getServiceOrderBo().getScheduleHistory(soId,acceptedVendorId);
		return callHistory;
	}

	public ProviderFirmVO getAcceptedFirmDetails(String soId) {
		ProviderFirmVO providerFirmVO=null;
		try{
			providerFirmVO=getServiceOrderBo().getFirmLevelDetailsSoId(soId);
		}catch (Exception e) {
			logger.info("exception in getting accepted FirmDetails"+ e.getMessage());
		}
		return null;
	}
	
	private String formatDate(String format, Date date){
		DateFormat formatter = new SimpleDateFormat(format);
		String formattedDate = null;
		try {
			formattedDate = formatter.format(date);
		} catch (Exception e) {
			logger.error("exception in formatDate()"+ e);
		}
		return formattedDate;
	}
	
	public String getTimeZone(Date modifiedDate, String timeZone){
	    TimeZone gmtTime = TimeZone.getTimeZone(timeZone);
	    if(gmtTime.inDaylightTime(modifiedDate)){
	    	return "("+getDSTTimezone(timeZone)+")";
	    }
	    return "("+getStandardTimezone(timeZone)+")";
	}
	
	public String getTimeZone(String modifiedDate, String format, String timeZone){
		Date newDate = null;
		try {
			newDate = new SimpleDateFormat(format, Locale.ENGLISH).parse(modifiedDate);
		} catch (ParseException e) {
			logger.info("Parse Exception SODetailsDelegateImpl.java "+ e);
		}
        TimeZone gmtTime = TimeZone.getTimeZone(timeZone);
        if(gmtTime.inDaylightTime(newDate)){
        	return "("+getDSTTimezone(timeZone)+")";
	    }
	    return "("+getStandardTimezone(timeZone)+")";
   }
	/**
	 * R12.0 Sprint5
	 * method to fetch documents for invoice parts and display the same
	 */
	
	public void  setInvoiceDocuments(SOCompleteCloseDTO soCompleteCloseDTO){
		
		List<Integer> invoiceIds= new ArrayList<Integer>();
		if(null!=soCompleteCloseDTO && null!=soCompleteCloseDTO.getInvoiceParts())
		{
			for (ProviderInvoicePartsVO invoiceVO : soCompleteCloseDTO.getInvoiceParts()) {
				
				if(null!=invoiceVO.getInvoicePartId()){
					invoiceIds.add(invoiceVO.getInvoicePartId());
				}
		
			}

		}
		
		 List<InvoiceDocumentVO> invoiceDocuments = new ArrayList<InvoiceDocumentVO>();
		 
			try {
				invoiceDocuments = serviceOrderBo.getInvoiceDocuments(invoiceIds);
				
			} catch (Exception e) {
				logger.error("Exception in SODetailsDelegateImpl getInvoiceDocuments due to:" + e);
			}
		
		Map<Integer,List<InvoiceDocumentVO>> invoiceDocumentMap=new HashMap<Integer,List<InvoiceDocumentVO>>();
			
		
		if(null!=invoiceDocuments && invoiceDocuments.size()>0){
		for(InvoiceDocumentVO invoiceDocumentVO: invoiceDocuments){
			
			if(null!=invoiceDocumentVO.getInvoicePartId()){
			if(null!=invoiceDocumentMap.get(invoiceDocumentVO.getInvoicePartId())){
				List<InvoiceDocumentVO> invoiceDocumentList =invoiceDocumentMap.get(invoiceDocumentVO.getInvoicePartId());	
				if(null!=invoiceDocumentList && invoiceDocumentList.size()>0){
				invoiceDocumentList.add(invoiceDocumentVO);
				invoiceDocumentMap.put(invoiceDocumentVO.getInvoicePartId(), invoiceDocumentList);
				}
			}else{
				 List<InvoiceDocumentVO> invoiceDocumentList = new ArrayList<InvoiceDocumentVO>();
				 invoiceDocumentList.add(invoiceDocumentVO);				 
				 invoiceDocumentMap.put(invoiceDocumentVO.getInvoicePartId(), invoiceDocumentList);
			}
		}
			
			
		 }
		}
		
		
		if(null!=soCompleteCloseDTO && null!=soCompleteCloseDTO.getInvoiceParts())
		{
			for (ProviderInvoicePartsVO invoiceVO : soCompleteCloseDTO.getInvoiceParts()) {
				
				if(null!=invoiceVO.getInvoicePartId()){
				
					List<InvoiceDocumentVO> invoiceDocumentList =invoiceDocumentMap.get(invoiceVO.getInvoicePartId());
					invoiceVO.setInvoiceDocuments(invoiceDocumentList);
					}			
				}		
			}		
		}
		
		
		
		
	
			
		
		
	


	public String getDSTTimezone(String timeZone) {
		
		if ("EST5EDT".equals(timeZone)) {
			timeZone = "EDT";
		}
		if ("AST4ADT".equals(timeZone)) {
			timeZone = "ADT";
		}
		if ("CST6CDT".equals(timeZone)) {
			timeZone = "CDT";
		}
		if ("MST7MDT".equals(timeZone)) {
			timeZone = "MDT";
		}
		if ("PST8PDT".equals(timeZone)) {
			timeZone = "PDT";
		}
		if ("HST".equals(timeZone)) {
			timeZone = "HADT";
		}
		if ("Etc/GMT+1".equals(timeZone)) {
			timeZone = "CEDT";
		}
		if ("AST".equals(timeZone)) {
			timeZone = "AKDT";
		}
		return timeZone;
	}

	public String getStandardTimezone(String timeZone) {
		if ("EST5EDT".equals(timeZone)) {
			timeZone = "EST";
		}
		if ("AST4ADT".equals(timeZone)) {
			timeZone = "AST";
		}
		if ("CST6CDT".equals(timeZone)) {
			timeZone = "CST";
		}
		if ("MST7MDT".equals(timeZone)) {
			timeZone = "MST";
		}
		if ("PST8PDT".equals(timeZone)) {
			timeZone = "PST";
		}
		if ("HST".equals(timeZone)) {
			timeZone = "HAST";
		}
		if ("Etc/GMT+1".equals(timeZone)) {
			timeZone = "CET";
		}
		if ("AST".equals(timeZone)) {
			timeZone = "AKST";
		}
		if ("Etc/GMT-9".equals(timeZone)) {
			timeZone = "PST-7";
		}
		if ("MIT".equals(timeZone)) {
			timeZone = "PST-3";
		}
		if ("NST".equals(timeZone)) {
			timeZone = "PST-4";
		}
		if ("Etc/GMT-10".equals(timeZone)) {
			timeZone = "PST-6";
		}
		if ("Etc/GMT-11".equals(timeZone)) {
			timeZone = "PST-5";
		}
		return timeZone;
	}

	public Integer getBuyerId(String soId) {
		Integer buyerId=null;
		try{
			buyerId=getServiceOrderBo().getBuyerId(soId);
		}catch(Exception e){
			logger.info("exception in getting buyer id"+ e.getMessage());
		}
		return buyerId;
	}	
	
	//For checking Non Funded feature for an SO
	public boolean checkNonFunded(String soId)
	{
		boolean inNonFunded=false;
		try {
			inNonFunded= serviceOrderBo.checkNonFunded(soId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("Exception in  SODetailsDelegateImpl.java-->checkNonFunded():"+ e.getMessage());
		}
		return inNonFunded;
	}

	//SL-19820
	public String getGroupedId(String soId) throws BusinessServiceException {
		return powerBuyerBO.getGroupedId(soId);
	}
	
	//SL-19050
	//Marking SO notes as Read
	public void markSOAsRead(String noteId){
	try {
		serviceOrderBo.markSOAsRead(noteId);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.info("Exception in  SODetailsDelegateImpl.java-->markSOAsRead():"+ e.getMessage());
	}
	}
	
	//SL-19050
	//Marking SO notes as UnRead
	public void markSOAsUnRead(String noteId){
		try {
			serviceOrderBo.markSOAsUnRead(noteId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("Exception in  SODetailsDelegateImpl.java-->markSOAsUnRead():"+ e.getMessage());
		}
		}
	
	/**
	 * get the remaining time left for firm to wait to Accept So
	 * @param groupId
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public int getTheRemainingTimeToAcceptGrpOrderFirm(String groupId, Integer vendorId) throws BusinessServiceException{
		int remainingSecondsToWait = 0;
		try{
			remainingSecondsToWait = routeOrderGroupBO.getTheRemainingTimeToAcceptGrpOrderFirm(groupId, vendorId);
		}catch(Exception e){
			throw new BusinessServiceException("Error occurred @ getTheRemainingTimeToAcceptGrpOrderFirm in SODetailsDelegate: " +e);
		}
        return remainingSecondsToWait;
	}
	
	/**
	 * check whether SO is CAR routed
	 * @param groupId
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean isCARroutedSO(String soId) throws BusinessServiceException{
		try{
			return serviceOrderBo.isCARroutedSO(soId);
		}catch(BusinessServiceException e){
			throw new BusinessServiceException("exception in isCARroutedSO() of SODetailsDelegateImpl",e);
		}
	}
	
	public Cryptography128 getCryptography128() {
		return cryptography128;
	}

	public void setCryptography128(Cryptography128 cryptography128) {
		this.cryptography128 = cryptography128;
	}
	
	
	/**
	 * get trip details
	 * @param soId
	 * @param soTripNo
	 * @return
	 * @throws BusinessServiceException
	 */
	public RevisitNeededInfoDTO getTripRevisitDetails(String soId, Integer soTripNo) throws BusinessServiceException{
		RevisitNeededInfoDTO revisitDTO= new RevisitNeededInfoDTO();
		try{
			RevisitNeededInfoVO revisitVO= timeOnSiteVisitBO.getTripRevisitDetails(soId,soTripNo);
			revisitDTO= ObjectMapperDetails.convertRevisitTripVOtoRevisitTripDTO(revisitVO);
		}catch(BusinessServiceException e){
			throw new BusinessServiceException("exception in getTripRevisitDetails() of SODetailsDelegateImpl",e);
		}
		return revisitDTO;
	}
	
	public String fetchUserName(String resourceId) throws BusinessServiceException{
		try{
			return timeOnSiteVisitBO.fetchUserName(resourceId);
		}catch(BusinessServiceException e){
			throw new BusinessServiceException("exception in fetchUserName() of SODetailsDelegateImpl",e);
		}
	}
	
	public void insertNewTripWeb(SOTripVO trip) throws BusinessServiceException{
		try{
			timeOnSiteVisitBO.insertNewTripIVR(trip);
		}catch(BusinessServiceException e){
			throw new BusinessServiceException("exception in insertNewTripWeb() of SODetailsDelegateImpl",e);
		}
	}
	
	public void updateTripWeb(SOTripVO trip) throws BusinessServiceException{
		try{
			timeOnSiteVisitBO.updateTripIVR(trip);
		}catch(BusinessServiceException e){
			throw new BusinessServiceException("exception in updateTripWeb() of SODetailsDelegateImpl",e);
		}
	}
	
	public String findlatestTripStatus(String soId, int tripNo) throws BusinessServiceException{
		try{
			return timeOnSiteVisitBO.findlatestTripStatus(soId,tripNo);
		}catch(BusinessServiceException e){
			throw new BusinessServiceException("exception in findlatestTripStatus() of SODetailsDelegateImpl",e);
		}
	}
	
	public Integer fetchLatestTripSO(String soId) throws BusinessServiceException{
		try{
			return timeOnSiteVisitBO.fetchLatestTripSO(soId);
		}catch(BusinessServiceException e){
			throw new BusinessServiceException("exception in fetchLatestTripSO() of SODetailsDelegateImpl",e);
		}
	}
	
	public Long findLatestOnsiteVisitEntry(String soId) throws BusinessServiceException{
		SOOnsiteVisitVO soOnsiteVisitVO=null;
		try{
			soOnsiteVisitVO = timeOnSiteVisitBO.findLatestOnsiteVisitEntry(soId);
		}catch(BusinessServiceException e){
			throw new BusinessServiceException("exception in findLatestOnsiteVisitEntry() of SODetailsDelegateImpl",e);
		}
		return soOnsiteVisitVO.getVisitId();
	}
	
	public boolean isRevisitNeededTrip(String soId, Integer currentTripNo) throws BusinessServiceException{
		try{
			return timeOnSiteVisitBO.isRevisitNeededTrip(soId,currentTripNo);
		}catch(BusinessServiceException e){
			throw new BusinessServiceException("exception in isRevisitNeededTrip() of SODetailsDelegateImpl",e);
		}
	}
	
	// Method to fetch time on site reason codes.
	public List<String> fetchTimeOnSiteReasons() throws BusinessServiceException {
		try{
			return timeOnSiteVisitBO.fetchTimeOnSiteReasons();
		}catch(BusinessServiceException e){
			throw new BusinessServiceException("exception in fetchTimeOnSiteReasons() of SODetailsDelegateImpl",e);
		}
	}
	// R12_0 Method to update substatus.
	public void updateSubStatusOfSO(String soId,Integer substatusValue)throws BusinessServiceException {
		try{
			timeOnSiteVisitBO.updateSubStatusOfSO(soId,substatusValue);
		}catch(BusinessServiceException e){
			throw new BusinessServiceException("exception in updateSubStatusOfSO() of SODetailsDelegateImpl",e);
		}
		
	}

	
	/**
	 * get closure method
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public String getMethodOfClosure(String soId) throws BusinessServiceException{
		try{
			return serviceOrderBo.getMethodOfClosure(soId);
			
		}catch(BusinessServiceException e){
			logger.error("Exception while trying to get method of closure :" + e);
			throw new BusinessServiceException("Exception in getMethodOfClosure() of SODetailsDelegateImpl",e);
		}
	}
	/**
	 * @param creditCardNumber
	 * @return
	 * @throws Exception 
	 */
	private TokenizeResponse setTestTokenAndMaskedAccountNo(String creditCardNumber) throws Exception {
		TokenizeResponse response =new TokenizeResponse();
		response.setCorrelationId("190833");
		response.setMaskedAccount(setMaskedAccountNo(creditCardNumber));
		response.setToken("5324660213268645");
		response.setResponseCode("00");
		response.setResponseMessage("SUCCESS");
		String xml = serializeAuthResponseToXml(response);
		response.setResponseXML(xml);
		return response;
	}
	
	/**
	 * priority 5B changes
	 * Insert order history
	 * @param soLoggingVO
	 * @return 
	 * @throws BusinessServiceException
	 */
	public void insertSoLogging(SoLoggingVo soLoggingVO) throws BusinessServiceException{
		try{
			serviceOrderBo.insertSoLogging(soLoggingVO);
			
		}catch(Exception e){
			throw new BusinessServiceException("Exception in insertSoLogging() of SODetailsDelegateImpl",e);
		}
	}
	
	/**
	 * priority 5B changes
	 * Insert a new row in so_custom_reference
	 * @param soLoggingVO
	 * @return 
	 * @throws BusinessServiceException
	 */
	public void insertSOCustomReference(String soId, String refType, String refVal, SecurityContext securityContext) throws BusinessServiceException
	{
		try{
			ServiceOrderCustomRefVO refVO = new ServiceOrderCustomRefVO();
			refVO.setsoId(soId);
			refVO.setModifiedDate(new java.sql.Date(System.currentTimeMillis()));
			refVO.setCreatedDate(new java.sql.Date(System.currentTimeMillis()));
			refVO.setModifiedBy(securityContext.getCompanyId().toString());
			refVO.setRefType(refType);
			if(InHomeNPSConstants.MODEL.equalsIgnoreCase(refType)){
				refVO.setRefTypeId(InHomeNPSConstants.MODEL_REF_ID);
			}
			else if(InHomeNPSConstants.SERIAL_NUMBER.equalsIgnoreCase(refType)){
				refVO.setRefTypeId(InHomeNPSConstants.SERIAL_REF_ID);
			}
			refVO.setRefValue(refVal);
			serviceOrderBo.insertSoCustomReference(refVO);
			
		}catch(Exception e){
			throw new BusinessServiceException("Exception in insertSOCustomReference() of SODetailsDelegateImpl",e);
		}
	}
	
	/**
	 * priority 5B changes
	 * get buyer first name & last name
	 * @param buyerId
	 * @return 
	 * @throws BusinessServiceException
	 */
	public String getBuyerName(Integer buyerResId) throws BusinessServiceException{
		try{
			return serviceOrderBo.getBuyerName(buyerResId);
			
		}catch(BusinessServiceException e){
			throw new BusinessServiceException("Exception in getBuyerName() of SODetailsDelegateImpl",e);
		}
	}
	
	/**
	 * priority 5B changes
	 * get the validation rules for the fields
	 * @param fields
	 * @return List<ValidationRulesVO>
	 * @throws BusinessServiceException
	 */
	public List<ValidationRulesVO> getValidationRules(List<String> fields)throws BusinessServiceException{
		try{
			return serviceOrderBo.getValidationRules(fields);
			
		}catch(BusinessServiceException e){
			throw new BusinessServiceException("Exception in getValidationRules() of SODetailsDelegateImpl",e);
		}
	}
	
	/**
	 * priority 5B changes
	 * update invalid_model_serial_ind in so_workflow_controls
	 * @param soId
	 * @return ind
	 * @throws BusinessServiceException
	 */
	public void updateModelSerialInd(String soId, String ind) throws BusinessServiceException {
		try{
			serviceOrderBo.updateModelSerialInd(soId, ind);
			
		}catch(BusinessServiceException e){
			throw new BusinessServiceException("Exception in updateModelSerialInd() of SODetailsDelegateImpl",e);
		}
	}
	
	/**
	 * priority 5B changes
	 * get CustomReferences for SO
	 * @param soId
	 * @param soDTO
	 * @return ServiceOrderDTO
	 * @throws BusinessServiceException
	 */
	public ServiceOrderDTO getCustomReferences(String soId, ServiceOrderDTO soDTO) throws BusinessServiceException{
		try{
			ArrayList<SOWSelBuyerRefDTO> custRefDtoList = new ArrayList<SOWSelBuyerRefDTO>();			
			List<ServiceOrderCustomRefVO> customReferences = serviceOrderBo.getCustomReferenceFields(soId);
			
			if(null != customReferences && !customReferences.isEmpty()){
				for(ServiceOrderCustomRefVO custRefVo : customReferences){
					if(null != custRefVo){
						SOWSelBuyerRefDTO buyerRefDto = new SOWSelBuyerRefDTO();
						buyerRefDto.setRefType(custRefVo.getRefType().toUpperCase());
						buyerRefDto.setRefValue(custRefVo.getRefValue());
						buyerRefDto.setRequiredInd(custRefVo.getRequiredInd());
						custRefDtoList.add(buyerRefDto);
					}
				}
				soDTO.setSelByerRefDTO(custRefDtoList);
			}
			return soDTO;
			
		}catch(BusinessServiceException e){
			throw new BusinessServiceException("Exception in getCustomReferences() of SODetailsDelegateImpl",e);
		}
	}
	
	/**
	 * priority 5B changes
	 * delete custom reference by type
	 * @param soId
	 * @param refType
	 * @throws BusinessServiceException
	 */
	public void deleteSOCustomReference(String soId, String refType) throws BusinessServiceException{
		
		try{
			serviceOrderBo.deleteSOCustomReference(soId, refType);
			
		}catch(BusinessServiceException e){
			throw new BusinessServiceException("Exception in deleteSOCustomReference() of SODetailsDelegateImpl",e);
		}
	}
	
	
	/**
	 * SL-20926 changes
	 * Create error response if addon issues exists
	 * @return ProcessResponse
	 */
	private ProcessResponse createRespForAddonIssues(OrderFulfillmentResponse ordrFlflResponse) {
		
		ProcessResponse procResp = null;
        if (null != ordrFlflResponse && ordrFlflResponse.isError() && 
        		ADDON_COMPLETE_ERROR.equalsIgnoreCase(ordrFlflResponse.getErrors().get(0))) {
        	
        	procResp = new ProcessResponse();
            List<String> msgList = new ArrayList<String>();
            procResp.setCode(ADDON_COMPLETE_ERROR);
            msgList.add(ADDON_COMPLETE_ERROR);
            procResp.setMessages(msgList);
        } 
        return procResp;
	}

	public HSTokenizeServiceCreditCardBO getHsTokenServiceCreditCardBo() {
		return hsTokenServiceCreditCardBo;
	}

	public void setHsTokenServiceCreditCardBo(
			HSTokenizeServiceCreditCardBO hsTokenServiceCreditCardBo) {
		this.hsTokenServiceCreditCardBo = hsTokenServiceCreditCardBo;
	}
	/**
	 * @param accNum
	 * @return
	 */
	private String setMaskedAccountNo(String accNum) {
		String accountNo = accNum;
		String MASK_CHAR ="*";
		int start = 0;
		int end = 0;
		int length = 0;
		if(StringUtils.isNotBlank(accountNo)){
			length = accNum.length();
			end = length-4;
			if(length> 13 && length <19){
				start = 6;
			}else if(length <=13){
				start = 4;
			}
		  String overlay = StringUtils.repeat(MASK_CHAR, end - start);	
		  return StringUtils.overlay(accountNo, overlay, start, end);	
		}
		return accountNo;
	}
	/** method to serailize auth response to xml
	 * serializeAuthResponseToXml.
	 * 
	 * @param resp 
	 * 
	 * @return String
	 * 
	 */
	private String serializeAuthResponseToXml(TokenizeResponse resp) throws Exception{
		XStream xstream = new XStream();
        xstream.alias("TokenizeResponse", TokenizeResponse.class);
		String xmlResponse = xstream.toXML(resp);
		return xmlResponse;
	}
	private ProcessResponse createProcResponseForTokenizationFailure(){
		ProcessResponse procResp = new ProcessResponse();
        List<String> msgList = new ArrayList<String>();
        msgList.add(TOKENIZATION_ERROR_MSG);
        procResp.setCode(TOKENIZATION_ERROR_CODE);
        procResp.setMessages(msgList);
        return procResp;
	}
	/**
	 * This method is used to fetch spend limit details of so
	 * 
	 * @param soid
	 * @return InitialPriceDetailsVO
	 * @throws BusinessServiceException
	 */
	public InitialPriceDetailsVO getInitialPrice(String soId) throws BusinessServiceException{
		InitialPriceDetailsVO initialPriceDetailsVO = null;
		try{
			initialPriceDetailsVO = getServiceOrderBo().getInitialPrice(soId);
		}catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		return initialPriceDetailsVO;
	}
	
	/**
	 * SL-21070
	 * Method fetches the lock_edit_ind of the so
	 * @param soId
	 * @return int lockEditInd
	 */
	public int getLockEditInd(String soId){
		return serviceOrderBo.getLockEditInd(soId);
	}

	public IRelayServiceNotification getRelayNotificationService() {
		return relayNotificationService;
	}

	public void setRelayNotificationService(IRelayServiceNotification relayNotificationService) {
		this.relayNotificationService = relayNotificationService;
	}
	
   public  List<ServiceDatetimeSlot> getSODateTimeSlots(String soId) throws DataServiceException {
		
		return getServiceOrderBo().getSODateTimeSlots(soId);
	}
   
   public  void updateAcceptedServiceDatetimeSlot(ServiceDatetimeSlot serviceDatetimeSlot) throws BusinessServiceException{
	    getServiceOrderBo().updateAcceptedServiceDatetimeSlot(serviceDatetimeSlot);
   }
   
   public void getStandardORDSTTimezoneForSlots(ServiceOrder serviceOrder){


	   String dlsFlag = "N";

	   if (serviceOrder != null
			   && serviceOrder.getServiceLocation() != null
			   && !StringUtils.isBlank(serviceOrder.getServiceLocation()
					   .getZip())) {
		   String zip = serviceOrder.getServiceLocation().getZip();
		   try{
			   dlsFlag = luDelegate.getDaylightSavingsFlg(zip);
		   }catch(Exception e){
			   logger.error("exception occured due to "+e.getMessage());
		   }
		  
	   }

	   if ("Y".equals(dlsFlag)) {
		   TimeZone tz = TimeZone.getTimeZone(serviceOrder.getServiceLocationTimeZone());
		   Timestamp timeStampDate = null;

		   for (ServiceDatetimeSlot serviceDatetimeSlot : serviceOrder.getServiceDatetimeSlots()) {
			   try {

				   if (null != serviceDatetimeSlot.getServiceStartDate()
						   && (StringUtils.isNotBlank(serviceDatetimeSlot.getServiceStartTime()))) {
					   java.util.Date dt = (java.util.Date) TimeUtils
							   .combineDateTime(
									   serviceDatetimeSlot.getServiceStartDate(),
									   serviceDatetimeSlot.getServiceStartTime());
					   timeStampDate = new Timestamp(dt.getTime());
				   }
			   } catch (ParseException pe) {
				   pe.printStackTrace();
			   }

			   if (null != timeStampDate) {
				   boolean isDLSActive = tz.inDaylightTime(timeStampDate);
				   if (isDLSActive) {
					   getDSTTimezoneForSlots(serviceDatetimeSlot);
				   } else {
					   getStandardTimezoneForSlots(serviceDatetimeSlot);
				   }
			   }


		   }


	   } else {

		   for (ServiceDatetimeSlot serviceDatetimeSlot : serviceOrder.getServiceDatetimeSlots()) {
			   getStandardTimezoneForSlots(serviceDatetimeSlot);

		   }

	   }


   }
   
   public List<DepositionCodeDTO> getAllDepositionCodes(){
	   List<DepositionCodeDTO> depositionCodes = null;
	   try{
		   depositionCodes = techTalkByerBO.getAllDipositionCodes();
	   }catch(BusinessServiceException e){
		   logger.error("unable to fetch list of deposition code");
	   }
	   return depositionCodes;
   }

   public boolean insertOrUpdateDepositionCode(String soID, String depositionCode){
	   try{
		   techTalkByerBO.insertOrUpdateDispositionCode(soID, depositionCode);
	   }catch(BusinessServiceException e){
		   logger.error("unable to store code");
	   }
	   return true;
   }
   
	public ITeclTalkBuyerPortalBO getTechTalkByerBO() {
		return techTalkByerBO;
	}
	
	public void setTechTalkByerBO(ITeclTalkBuyerPortalBO techTalkByerBO) {
		this.techTalkByerBO = techTalkByerBO;
	}

	public ServiceOrderDao getServiceOrderDao() {
		return serviceOrderDao;
	}

	public void setServiceOrderDao(ServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}

	public PushNotificationAlertTask getPushNotificationAlertTask() {
		return pushNotificationAlertTask;
	}

	public void setPushNotificationAlertTask(PushNotificationAlertTask pushNotificationAlertTask) {
		this.pushNotificationAlertTask = pushNotificationAlertTask;
	}	
   
   
}


