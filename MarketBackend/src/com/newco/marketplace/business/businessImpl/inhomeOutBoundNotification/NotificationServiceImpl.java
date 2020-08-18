package com.newco.marketplace.business.businessImpl.inhomeOutBoundNotification;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.buyeroutboundnotification.constatns.BuyerOutBoundConstants;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeoutboundnotification.beans.OrderUpdateRequest;
import com.newco.marketplace.inhomeoutboundnotification.beans.RequestInHomeDetails;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.inhomeoutboundnotification.vo.CustomReferenceVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeOutBoundNotificationVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeRescheduleVO;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeSODetailsVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.leadoutboundnotification.vo.LeadNotificationStatusEnum;
import com.newco.marketplace.persistence.iDao.inhomeOutBoundNotification.NotificationDao;
import com.newco.marketplace.utils.TimeUtils;
import com.servicelive.inhome.notification.mapper.NotificationServiceMapper;
import com.servicelive.inhome.notification.mapper.NotificationServiceValidator;
import com.thoughtworks.xstream.XStream;
 
public class NotificationServiceImpl implements INotificationService {

	private static final Logger LOGGER = Logger.getLogger(NotificationServiceImpl.class.getName());
	private NotificationDao notificationDao;
    private NotificationServiceMapper inhomeNotificationMapper;
    private NotificationServiceValidator inhomeNotificationValidator;
	
    // To get the outBoundFlag
	public String getOutBoundFlag(String appKey)throws BusinessServiceException {
		String outBoundFlag ="";
		try {
			outBoundFlag = notificationDao.getOutBoundFlag(appKey);
		} catch (DataServiceException de) {
			LOGGER.error("Exception in getting outboundFlag value "+ de.getMessage(), de);
			throw new BusinessServiceException("Exception in getting outboundFlag value "+ de.getMessage(), de);
		}
		return outBoundFlag;
	}
    /** Description:To insert into DB for sub status change
     * @param
     * soId 
     * statusId 
     * substatusId 
     * empId 
     * @throws BusinessServiceException
     */
	public void insertNotification(String soId, Integer statusId,Integer substatusId,Integer empId) throws BusinessServiceException {
		String message = "";
		String result="";
		try {
			message = notificationDao.getMessageForStatusAndSubStatus(statusId,substatusId);
		} catch (DataServiceException de) {
			LOGGER.error("Exception in getting message " + de.getMessage(), de);
			throw new BusinessServiceException("Exception in getting message"+ de.getMessage(), de);
		}
		if (StringUtils.isNotBlank(message)) {
				RequestInHomeDetails requestDetails = new RequestInHomeDetails();
				InHomeOutBoundNotificationVO notificationVO = new InHomeOutBoundNotificationVO();
				List<CustomReferenceVO> custVOList = new ArrayList<CustomReferenceVO>();
				try {
					Map paramMap= new HashMap();
					paramMap.put("soId", soId);
					paramMap.put("orderNumber",InHomeNPSConstants.INHOME_ORDER_NUMBER);
					paramMap.put("unitNumber",InHomeNPSConstants.INHOME_UNIT_NUMBER);
					paramMap.put("techId",InHomeNPSConstants.INHOME_EMP_ID);
					custVOList = notificationDao.getDetailsOfSo(paramMap);
					String orderNumber="";
					String unitNumber="";
					String techId = null;
					if(null != custVOList && !custVOList.isEmpty()){
						for(CustomReferenceVO customReferenceVO : custVOList){
							if(InHomeNPSConstants.INHOME_ORDER_NUMBER.equals(customReferenceVO.getCustomReferenceName())){
								orderNumber=customReferenceVO.getCustomeReferenceValue();
							}if(InHomeNPSConstants.INHOME_UNIT_NUMBER.equals(customReferenceVO.getCustomReferenceName())){
								unitNumber=customReferenceVO.getCustomeReferenceValue();
							}if(InHomeNPSConstants.INHOME_EMP_ID.equals(customReferenceVO.getCustomReferenceName())){
								techId = customReferenceVO.getCustomeReferenceValue();
								}
							  }
						}
				    requestDetails = inhomeNotificationMapper.mapDetails(orderNumber,unitNumber,techId,message);
					result = inhomeNotificationValidator.validateDetails(requestDetails);
					notificationVO=createRequestXML(soId, requestDetails, notificationVO,result);
					notificationDao.insertInHomeOutBoundDetails(notificationVO);
				} catch (DataServiceException e) {
					LOGGER.error("Exception in inserting details into outbound notification "+ e.getMessage(), e);
					throw new BusinessServiceException("Exception in inserting details into outbound notification" + e.getMessage(), e);
				}
		   }
		}
	//This method will be called when we add note from frondEnd
	public void insertNotification(InHomeSODetailsVO inHomeSODetailsVO)throws BusinessServiceException{
		String message = "";
		String result="";
		boolean isValidNote=false;
		if(InHomeNPSConstants.PROVIDER.equals(inHomeSODetailsVO.getRoleId())){
			if(InHomeNPSConstants.PUBLIC_NOTE.equals(inHomeSODetailsVO.getNoteTypeId())){
				isValidNote=true;
				if(inHomeSODetailsVO.isSlAdmin()){
					message=InHomeNPSConstants.ADMIN_NOTE+inHomeSODetailsVO.getSubjMessage() +" Created By : "+inHomeSODetailsVO.getCreatedBy();
				}else{
					message=InHomeNPSConstants.PROVIDER_NOTE +inHomeSODetailsVO.getSubjMessage()+" Created By : "+inHomeSODetailsVO.getCreatedBy();
				}
				
			}
		}else if(InHomeNPSConstants.BUYER.equals(inHomeSODetailsVO.getRoleId())){
			if((InHomeNPSConstants.PUBLIC_NOTE.equals(inHomeSODetailsVO.getNoteTypeId()))|| (InHomeNPSConstants.PRIVATE_NOTE.equals(inHomeSODetailsVO.getNoteTypeId()))){
				isValidNote=true;
				if(inHomeSODetailsVO.isSlAdmin()){
					message=InHomeNPSConstants.ADMIN_NOTE+inHomeSODetailsVO.getSubjMessage()+" Created By : "+inHomeSODetailsVO.getCreatedBy();
				}else{
					message=InHomeNPSConstants.BUYER_NOTE +inHomeSODetailsVO.getSubjMessage()+" Created By : "+inHomeSODetailsVO.getCreatedBy();
				}
			}
		}
		if(isValidNote && StringUtils.isNotBlank(message)){
			RequestInHomeDetails requestDetails = new RequestInHomeDetails();
			InHomeOutBoundNotificationVO notificationVO = new InHomeOutBoundNotificationVO();
			List<CustomReferenceVO> custVOList = new ArrayList<CustomReferenceVO>();
			try {
				Map paramMap= new HashMap();
				paramMap.put("soId", inHomeSODetailsVO.getSoId());
				paramMap.put("orderNumber",InHomeNPSConstants.INHOME_ORDER_NUMBER);
				paramMap.put("unitNumber",InHomeNPSConstants.INHOME_UNIT_NUMBER);
				paramMap.put("techId",InHomeNPSConstants.INHOME_EMP_ID);
				custVOList = notificationDao.getDetailsOfSo(paramMap);
				String orderNumber="";
				String unitNumber="";
				String techId = null;
				if(null != custVOList && !custVOList.isEmpty()){
					for(CustomReferenceVO customReferenceVO : custVOList){
						if(InHomeNPSConstants.INHOME_ORDER_NUMBER.equals(customReferenceVO.getCustomReferenceName())){
							orderNumber=customReferenceVO.getCustomeReferenceValue();
						}if(InHomeNPSConstants.INHOME_UNIT_NUMBER.equals(customReferenceVO.getCustomReferenceName())){
							unitNumber=customReferenceVO.getCustomeReferenceValue();
						}if(InHomeNPSConstants.INHOME_EMP_ID.equals(customReferenceVO.getCustomReferenceName())){
							techId = customReferenceVO.getCustomeReferenceValue();
							}
						  }
					}
				    requestDetails = inhomeNotificationMapper.mapDetails(orderNumber,unitNumber,techId,message);
					result = inhomeNotificationValidator.validateDetails(requestDetails);
					//check if SO is inactive
					Integer npsIndicator = 1;
					try{
						npsIndicator = notificationDao.getNpsIndicator(inHomeSODetailsVO.getSoId());
			        }catch (DataServiceException de) {
			        	LOGGER.error("Exception in getNpsIndicator " + de.getMessage(), de);
			        	throw new BusinessServiceException("Exception in getNpsIndicator"+ de.getMessage(), de);
			        }
			        if(null != npsIndicator && 1 == npsIndicator.intValue()){
			        	result = InHomeNPSConstants.INACTIVE;
			        }
					notificationVO = createRequestXML(inHomeSODetailsVO.getSoId(), requestDetails, notificationVO,result);
					notificationDao.insertInHomeOutBoundDetails(notificationVO);
				
			} catch (DataServiceException e) {
				LOGGER.error("Exception in inserting details into outbound notification "+ e.getMessage(), e);
				throw new BusinessServiceException("Exception in inserting details into outbound notification" + e.getMessage(), e);
			}
		
		}
		
	}
	
	
	//This method will be called when we release
		public void insertNotification(InHomeRescheduleVO inHomeRescheduleVO)throws BusinessServiceException{
			String message = "";
			String result="";
		        RequestInHomeDetails requestDetails = new RequestInHomeDetails();
				InHomeOutBoundNotificationVO notificationVO = new InHomeOutBoundNotificationVO();
				List<CustomReferenceVO> custVOList = new ArrayList<CustomReferenceVO>();
				try {
					Map paramMap= new HashMap();
					paramMap.put("soId", inHomeRescheduleVO.getSoId());
					paramMap.put("orderNumber",InHomeNPSConstants.INHOME_ORDER_NUMBER);
					paramMap.put("unitNumber",InHomeNPSConstants.INHOME_UNIT_NUMBER);
					paramMap.put("techId",InHomeNPSConstants.INHOME_EMP_ID);
					custVOList = notificationDao.getDetailsOfSo(paramMap);
				    String orderNumber="";
					String unitNumber="";
					String techId ="";
					if(null != custVOList && !custVOList.isEmpty()){
						for(CustomReferenceVO customReferenceVO : custVOList){
							if(InHomeNPSConstants.INHOME_ORDER_NUMBER.equals(customReferenceVO.getCustomReferenceName())){
								orderNumber=customReferenceVO.getCustomeReferenceValue();
							}if(InHomeNPSConstants.INHOME_UNIT_NUMBER.equals(customReferenceVO.getCustomReferenceName())){
								unitNumber=customReferenceVO.getCustomeReferenceValue();
							}if(InHomeNPSConstants.INHOME_EMP_ID.equals(customReferenceVO.getCustomReferenceName())){
								techId = customReferenceVO.getCustomeReferenceValue();
								}
							  }
						}
					    message = createRescheduleMessageForBuyerRelease(inHomeRescheduleVO);
					    requestDetails = inhomeNotificationMapper.mapDetails(orderNumber,unitNumber,techId,message);
						result = inhomeNotificationValidator.validateDetails(requestDetails);
						notificationVO=createRequestXML(inHomeRescheduleVO.getSoId(), requestDetails, notificationVO,result);
						notificationDao.insertInHomeOutBoundDetails(notificationVO);
					
				} catch (DataServiceException e) {
					LOGGER.error("Exception in inserting details into outbound notification "+ e.getMessage(), e);
					throw new BusinessServiceException("Exception in inserting details into outbound notification" + e.getMessage(), e);
				}
			
		
			
		}
		
		/** 
		 * R12.0 Sprint 2 : Adding a new method to update NPS for In-home for revisit needed
	     * @param
	     * soId 
	     * message
	     * @throws BusinessServiceException
	     */
		public void insertNotification(String soId, String message) throws BusinessServiceException {
			String result="";
			String orderNumber="";
			String unitNumber="";
			String techId = null;
			if (StringUtils.isNotBlank(message)) {
				RequestInHomeDetails requestDetails = new RequestInHomeDetails();
				InHomeOutBoundNotificationVO notificationVO = new InHomeOutBoundNotificationVO();
				List<CustomReferenceVO> custVOList = new ArrayList<CustomReferenceVO>();
				try {
					Map paramMap= new HashMap();
					paramMap.put("soId", soId);
					paramMap.put("orderNumber",InHomeNPSConstants.INHOME_ORDER_NUMBER);
					paramMap.put("unitNumber",InHomeNPSConstants.INHOME_UNIT_NUMBER);
					paramMap.put("techId",InHomeNPSConstants.INHOME_EMP_ID);
					custVOList = notificationDao.getDetailsOfSo(paramMap);
					
					if(null != custVOList && !custVOList.isEmpty()){
						for(CustomReferenceVO customReferenceVO : custVOList){
							if(InHomeNPSConstants.INHOME_ORDER_NUMBER.equals(customReferenceVO.getCustomReferenceName())){
								orderNumber=customReferenceVO.getCustomeReferenceValue();
							}
							if(InHomeNPSConstants.INHOME_UNIT_NUMBER.equals(customReferenceVO.getCustomReferenceName())){
								unitNumber=customReferenceVO.getCustomeReferenceValue();
							}
							if(InHomeNPSConstants.INHOME_EMP_ID.equals(customReferenceVO.getCustomReferenceName())){
								techId = customReferenceVO.getCustomeReferenceValue();
							}
						}
					}
				    requestDetails = inhomeNotificationMapper.mapDetails(orderNumber,unitNumber,techId,message);
					result = inhomeNotificationValidator.validateDetails(requestDetails);
					notificationVO=createRequestXML(soId, requestDetails, notificationVO,result);
					notificationDao.insertInHomeOutBoundDetails(notificationVO);
				} catch (DataServiceException e) {
					LOGGER.error("Exception in inserting details into outbound notification "+ e.getMessage(), e);
					throw new BusinessServiceException("Exception in inserting details into outbound notification" + e.getMessage(), e);
				}
			}
		}		
		
		
		/** 
		 * R12.0 : Adding a new method to update NPS for In-home for revisit needed(NPS-Service Operations API)
	     * @param
	     * soId 
	     * message
	     * @throws BusinessServiceException
	     */
		public void insertNotificationServiceOperationsAPI(String soId,InHomeRescheduleVO input) throws BusinessServiceException {
			String result="";
			String orderNumber="";
			String unitNumber="";
			String techId = null;
			String coverageTypeLabor="";
			String modelNo = InHomeNPSConstants.NOT_APPLICABLE;
			String brand = InHomeNPSConstants.NOT_APPLICABLE;
			
				OrderUpdateRequest requestDetails = new OrderUpdateRequest();
				InHomeOutBoundNotificationVO notificationVO = new InHomeOutBoundNotificationVO();
				List<CustomReferenceVO> custVOList = new ArrayList<CustomReferenceVO>();
				try {
					Map paramMap= new HashMap();
					paramMap.put("soId", soId);
					paramMap.put("orderNumber",InHomeNPSConstants.INHOME_ORDER_NUMBER);
					paramMap.put("unitNumber",InHomeNPSConstants.INHOME_UNIT_NUMBER);
					paramMap.put("techId",InHomeNPSConstants.INHOME_EMP_ID);
					paramMap.put("coverageTypeLabor",InHomeNPSConstants.COVERAGE_TYPE_LABOR);
					paramMap.put("brand", InHomeNPSConstants.BRAND);
					paramMap.put("modelNo", InHomeNPSConstants.MODEL);
					custVOList = notificationDao.getDetailsOfInHomeSo(paramMap);
					
					if(null != custVOList && !custVOList.isEmpty()){
						for(CustomReferenceVO customReferenceVO : custVOList){
							if(InHomeNPSConstants.INHOME_ORDER_NUMBER.equals(customReferenceVO.getCustomReferenceName())){
								orderNumber=customReferenceVO.getCustomeReferenceValue();
							}
							if(InHomeNPSConstants.INHOME_UNIT_NUMBER.equals(customReferenceVO.getCustomReferenceName())){
								unitNumber=customReferenceVO.getCustomeReferenceValue();
							}
							if(InHomeNPSConstants.INHOME_EMP_ID.equals(customReferenceVO.getCustomReferenceName())){
								techId = customReferenceVO.getCustomeReferenceValue();
							}
							if(InHomeNPSConstants.COVERAGE_TYPE_LABOR.equals(customReferenceVO.getCustomReferenceName())){
								coverageTypeLabor = customReferenceVO.getCustomeReferenceValue();
							}
							if(InHomeNPSConstants.BRAND.equals(customReferenceVO.getCustomReferenceName())){
								brand = customReferenceVO.getCustomeReferenceValue();
							}
							if(InHomeNPSConstants.MODEL.equals(customReferenceVO.getCustomReferenceName())){
								modelNo = customReferenceVO.getCustomeReferenceValue();
							}
						}
					}
					String correlationId=generateSeqNo();
					requestDetails = inhomeNotificationMapper.mapInHomeDetails(requestDetails,orderNumber,unitNumber,techId,correlationId,input,coverageTypeLabor);
					result = inhomeNotificationValidator.validateInHomeDetails(requestDetails);
					notificationVO=populateNotification(result,requestDetails,modelNo,brand);
					notificationDao.insertInHomeOutBoundDetails(notificationVO);
				} catch (DataServiceException e) {
					LOGGER.error("Exception in inserting details into outbound notification "+ e.getMessage(), e);
					throw new BusinessServiceException("Exception in inserting details into outbound notification" + e.getMessage(), e);
				}
			}
				
		
		public InHomeRescheduleVO getSoDetailsForReschedule(InHomeRescheduleVO inHomeRescheduleVO)throws BusinessServiceException{
			InHomeRescheduleVO result=new InHomeRescheduleVO();
			try {
				result = notificationDao.getSoDetailsForReschedule(inHomeRescheduleVO);
				if(inHomeRescheduleVO.getIsNPSMessageRequired()){
					if(null != inHomeRescheduleVO.getRescheduleDate1() && StringUtils.isNotEmpty(inHomeRescheduleVO.getRescheduleDate1())){
						result.setRescheduleDate1(inHomeRescheduleVO.getRescheduleDate1());
						result.setRescheduleDate2(inHomeRescheduleVO.getRescheduleDate2());
						result.setRescheduleStartTime(inHomeRescheduleVO.getRescheduleStartTime());
						result.setRescheduleEndTime(inHomeRescheduleVO.getRescheduleEndTime());
					}
					if(null != inHomeRescheduleVO.getServiceDate1()){
						result.setServiceDate1(inHomeRescheduleVO.getServiceDate1());
						result.setServiceDate2(inHomeRescheduleVO.getServiceDate2());
						result.setStartTime(inHomeRescheduleVO.getStartTime());
						result.setEndTime(inHomeRescheduleVO.getEndTime());
					}
					
					//Setting separate message for revisit reason : Customer Not Home
					if(InHomeNPSConstants.HSRBUYER.equals(result.getBuyerId())){
						if(null!=inHomeRescheduleVO && null!=inHomeRescheduleVO.getRevisitReason() && InHomeNPSConstants.CUSTOMER_NOT_HOME_REASON.equals(inHomeRescheduleVO.getRevisitReason()))
						{
							result.setRescheduleMessage(InHomeNPSConstants.CUSTOMER_NOT_HOME_NPS_MESSAGE);
						}
						else
						{
							result.setRescheduleMessage(createRescheduleMessage(result));
						}
					}
				}			
			} catch (DataServiceException e) {
				LOGGER.info("error in getting SO Detaisl"+e);
				return null;
			}
			return result;
		}
		
		
		public InHomeSODetailsVO getSoDetailsForNotes(InHomeSODetailsVO inHomeSODetailsVO)throws BusinessServiceException{
			InHomeSODetailsVO result=new InHomeSODetailsVO();
			try {
				result = notificationDao.getSoDetailsForNotes(inHomeSODetailsVO);
			} catch (DataServiceException e) {
				LOGGER.info("error in getting SO Detaisl"+e);
				return null;
			}
			return result;
		}

		/**
		 * @param soId
		 * @param requestDetails
		 * @param notificationVO
		 * @param result
		 * @return
		 * @throws BusinessServiceException
		 */
	private InHomeOutBoundNotificationVO createRequestXML(String soId, RequestInHomeDetails requestDetails,InHomeOutBoundNotificationVO notificationVO, String result) throws BusinessServiceException {
		String seqNo=generateSeqNo();
		requestDetails.setCorrelationId(seqNo);
		XStream xstream = new XStream();
		xstream.processAnnotations(RequestInHomeDetails.class);
		String createRequestXml = (String) xstream.toXML(requestDetails);
		notificationVO.setSeqNo(seqNo);
		notificationVO.setSoId(soId);
		notificationVO.setXml(createRequestXml);
		notificationVO.setCreatedDate(new Date());
		notificationVO.setModifiedDate(new Date());
		notificationVO.setRetryCount(InHomeNPSConstants.NO_OF_RETRIES);
		notificationVO.setServiceId(InHomeNPSConstants.SUBSTATUS_SERVICE_ID_INT);
		notificationVO.setActiveInd(InHomeNPSConstants.DEFAULT_ACTIVE_IND);
		if(InHomeNPSConstants.VALIDATION_SUCCESS.equals(result)){
			notificationVO.setStatus(LeadNotificationStatusEnum.WAITING.name());
			notificationVO.setException(null);
		}
		else if(InHomeNPSConstants.INACTIVE.equals(result)){
			notificationVO.setStatus(LeadNotificationStatusEnum.EXCLUDED.name());
			notificationVO.setException(result);
		}
		else{
			notificationVO.setStatus(LeadNotificationStatusEnum.ERROR.name());
			notificationVO.setException(result);
		}
		return notificationVO;
	}
	
	
    /**Description:This method will validate whether we need to send notification for the service order
     * @param buyerId
     * @param currentServiceOrderId
     * @return boolean
     * @throws BusinessServiceException
     */
	public boolean validateNPSNotificationEligibility(Integer buyerId,String currentServiceOrderId) throws BusinessServiceException {
		boolean isEligible=false;
		String npsFlag="";
		if(InHomeNPSConstants.HSRBUYER.equals(buyerId)){
		    try {
			    npsFlag = notificationDao.getOutBoundFlag(InHomeNPSConstants.INHOME_NPS_MESSAGE_FLAG);
		     }catch (DataServiceException de) {
			    LOGGER.error("Exception in getting outboundFlag value "+ de.getMessage(), de);
			    throw new BusinessServiceException("Exception in getting outboundFlag value "+ de.getMessage(), de);
		      }
		      if(InHomeNPSConstants.HSRBUYER.equals(buyerId) && InHomeNPSConstants.OUTBOUND_FLAG_ON.equals(npsFlag)){
		    	  isEligible=true;
		      }
		}
		return isEligible;
	}
	/**
	 * @return
	 * @throws BusinessServiceException
	 */
	public String generateSeqNo() throws BusinessServiceException {
		String seqNo = new String();
		try {
			seqNo =  notificationDao.generateSeqNO().toString();
		} catch (DataServiceException bse) {
			LOGGER.error("Exception in fetching count due to"+ bse.getMessage(), bse);
			 throw new BusinessServiceException("Exception in getting the sequence No. due to" + bse.getMessage(), bse);
		}
		int length = seqNo.length();
		length = 8 - length;
		for (int i = 0; i < length; i++) {
			seqNo = BuyerOutBoundConstants.SEQ_ZERO + seqNo;

		}
		return seqNo;

	}
	
	private String createRescheduleMessage(InHomeRescheduleVO result) {
		StringBuffer reschedulePeriod = new StringBuffer();
		HashMap<String, Object> rescheduleStartDate = null;
		HashMap<String, Object> rescheduleEndDate = null;
		String format = OrderConstants.RESCHEDULE_DATE_FORMAT;
		try{
            if(null != result && null != result.getServiceDate1()){
            	if (null != result.getServiceDate1() && null != result.getStartTime()) {
            		rescheduleStartDate = TimeUtils.convertGMTToGivenTimeZone(result.getServiceDate1(), result.getStartTime(), result.getTimeZone());
            		if (null != rescheduleStartDate && !rescheduleStartDate.isEmpty()) {
            			reschedulePeriod.append(InHomeNPSConstants.RESCHEDULE_MESSAGE);
            			reschedulePeriod.append(formatDate(format, (Date) rescheduleStartDate.get(OrderConstants.GMT_DATE)));
            			reschedulePeriod.append(" ");
            			reschedulePeriod.append((String) rescheduleStartDate.get(OrderConstants.GMT_TIME));
            		}
    			}
            	if(null != result.getServiceDate2() && null != result.getEndTime()){
            		rescheduleEndDate = TimeUtils.convertGMTToGivenTimeZone(result.getServiceDate2(), result.getEndTime(), result.getTimeZone());
            		if (null != rescheduleEndDate && !rescheduleEndDate.isEmpty()) {
            			reschedulePeriod.append(" - ");
            			reschedulePeriod.append(formatDate(format, (Date) rescheduleEndDate.get(OrderConstants.GMT_DATE)));
                		reschedulePeriod.append(" ");
                		reschedulePeriod.append((String) rescheduleEndDate.get(OrderConstants.GMT_TIME));
            			}
            		}
            	//reschedulePeriod.append(" "+getTimeZone(formatDate(format, (Date) rescheduleStartDate.get(OrderConstants.GMT_DATE))+" "+(String) rescheduleStartDate.get(OrderConstants.GMT_TIME), OrderConstants.RESCHEDULE_DATE_TIME_STAMP_FORMAT1, result.getTimeZone()));
            	if(null != result.getRescheduleDate1() && StringUtils.isNotEmpty(result.getRescheduleDate1())){
            		if(null != result.getRescheduleStartTime()){
	            		Date newDate = new SimpleDateFormat(OrderConstants.REPORT_DATE_FORMAT, Locale.ENGLISH).parse(result.getRescheduleDate1());
	            		reschedulePeriod.append(" to "+formatDate(format, newDate)+" "+result.getRescheduleStartTime());
	            	}
	            	if(null != result.getRescheduleDate2() && null != result.getRescheduleEndTime() && StringUtils.isNotEmpty(result.getRescheduleDate2())){
	            		Date newDate = new SimpleDateFormat(OrderConstants.REPORT_DATE_FORMAT, Locale.ENGLISH).parse(result.getRescheduleDate2());
	            		reschedulePeriod.append(" - "+formatDate(format, newDate)+" "+result.getRescheduleEndTime());
	            	}
	            	//reschedulePeriod.append(" "+getTimeZone(result.getRescheduleDate1()+" "+result.getRescheduleStartTime(), OrderConstants.RESCHEDULE_DATE_TIME_STAMP_FORMAT2, result.getTimeZone()));
            	}
            	else{
	            	if (null != result.getRescheduleServiceDate1() && null != result.getRescheduleStartTime()) {
	            		rescheduleStartDate = TimeUtils.convertGMTToGivenTimeZone(result.getRescheduleServiceDate1(), result.getRescheduleStartTime(), result.getTimeZone());
	            		if (null != rescheduleStartDate && !rescheduleStartDate.isEmpty()) {
	            			reschedulePeriod.append(" to " + formatDate(format, (Date) rescheduleStartDate.get(OrderConstants.GMT_DATE)));
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
	            	//reschedulePeriod.append(" "+getTimeZone(formatDate(format, (Date) rescheduleStartDate.get(OrderConstants.GMT_DATE))+" "+(String) rescheduleStartDate.get(OrderConstants.GMT_TIME), OrderConstants.RESCHEDULE_DATE_TIME_STAMP_FORMAT1, result.getTimeZone()));
            	}
            	LOGGER.info(reschedulePeriod.toString());
            	}
    		}
            catch(Exception e){
            	LOGGER.error("Exception occured in createRescheduleMessage:"+e);
            }
		return reschedulePeriod.toString();
	}
	
	public String createRescheduleMessageForProviderRescheduleAPI(InHomeRescheduleVO result) {
		StringBuffer reschedulePeriod = new StringBuffer();
		HashMap<String, Object> rescheduleStartDate = null;
		HashMap<String, Object> rescheduleEndDate = null;
		String format = OrderConstants.RESCHEDULE_DATE_FORMAT;
		try{
            if(null != result && null != result.getServiceDate1()){
            	if (null != result.getServiceDate1() && null != result.getStartTime()) {
            		rescheduleStartDate = TimeUtils.convertGMTToGivenTimeZone(result.getServiceDate1(), result.getStartTime(), result.getTimeZone());
            		if (null != rescheduleStartDate && !rescheduleStartDate.isEmpty()) {
            			reschedulePeriod.append(InHomeNPSConstants.RESCHEDULE_MESSAGE);
            			reschedulePeriod.append(formatDate(format, (Date) rescheduleStartDate.get(OrderConstants.GMT_DATE)));
            			reschedulePeriod.append(" ");
            			reschedulePeriod.append((String) rescheduleStartDate.get(OrderConstants.GMT_TIME));
            		}
    			}
            	if(null != result.getServiceDate2() && null != result.getEndTime()){
            		rescheduleEndDate = TimeUtils.convertGMTToGivenTimeZone(result.getServiceDate2(), result.getEndTime(), result.getTimeZone());
            		if (null != rescheduleEndDate && !rescheduleEndDate.isEmpty()) {
            			reschedulePeriod.append(" - ");
            			reschedulePeriod.append(formatDate(format, (Date) rescheduleEndDate.get(OrderConstants.GMT_DATE)));
                		reschedulePeriod.append(" ");
                		reschedulePeriod.append((String) rescheduleEndDate.get(OrderConstants.GMT_TIME));
            			}
            		}
            	//reschedulePeriod.append(" "+getTimeZone(formatDate(format, (Date) rescheduleStartDate.get(OrderConstants.GMT_DATE))+" "+(String) rescheduleStartDate.get(OrderConstants.GMT_TIME), OrderConstants.RESCHEDULE_DATE_TIME_STAMP_FORMAT1, result.getTimeZone()));
            	if(null != result.getRescheduleDate1() && StringUtils.isNotEmpty(result.getRescheduleDate1())){
            		reschedulePeriod.append(" to "+result.getRescheduleDate1());
            	}
            	if(null != result.getRescheduleDate2() && StringUtils.isNotEmpty(result.getRescheduleDate2())){
            		reschedulePeriod.append(" - "+result.getRescheduleDate2());
            	}
            	//reschedulePeriod.append(" "+getTimeZone(result.getRescheduleDate1()+" "+result.getRescheduleStartTime(), OrderConstants.RESCHEDULE_DATE_TIME_STAMP_FORMAT2, result.getTimeZone()));
            }
        LOGGER.info(reschedulePeriod.toString());
    	}
        catch(Exception e){
        	LOGGER.error("Exception occured in createRescheduleMessage:"+e);
        }
		return reschedulePeriod.toString();
	}
	
	private String createRescheduleMessageForBuyerRelease(InHomeRescheduleVO result) {
		StringBuffer reschedulePeriod = new StringBuffer();
		HashMap<String, Object> rescheduleStartDate = null;
		HashMap<String, Object> rescheduleEndDate = null;
		String format = OrderConstants.RESCHEDULE_DATE_FORMAT;
		try{
            if(null != result && null != result.getServiceDate1()){
            	if (null != result.getServiceDate1() && null != result.getStartTime()) {
            		rescheduleStartDate = TimeUtils.convertGMTToGivenTimeZone(result.getServiceDate1(), result.getStartTime(), result.getTimeZone());
            		if (null != rescheduleStartDate && !rescheduleStartDate.isEmpty()) {
            			reschedulePeriod.append(InHomeNPSConstants.RESCHEDULE_MESSAGE);
            			reschedulePeriod.append(formatDate(format, (Date) rescheduleStartDate.get(OrderConstants.GMT_DATE)));
            			reschedulePeriod.append(" ");
            			reschedulePeriod.append((String) rescheduleStartDate.get(OrderConstants.GMT_TIME));
            		}
    			}
            	if(null != result.getServiceDate2() && null != result.getEndTime()){
            		rescheduleEndDate = TimeUtils.convertGMTToGivenTimeZone(result.getServiceDate2(), result.getEndTime(), result.getTimeZone());
            		if (null != rescheduleEndDate && !rescheduleEndDate.isEmpty()) {
            			reschedulePeriod.append(" - ");
            			reschedulePeriod.append(formatDate(format, (Date) rescheduleEndDate.get(OrderConstants.GMT_DATE)));
                		reschedulePeriod.append(" ");
                		reschedulePeriod.append((String) rescheduleEndDate.get(OrderConstants.GMT_TIME));
            			}
            		}
            	//reschedulePeriod.append(" "+getTimeZone(formatDate(format, (Date) rescheduleStartDate.get(OrderConstants.GMT_DATE))+" "+(String) rescheduleStartDate.get(OrderConstants.GMT_TIME), OrderConstants.RESCHEDULE_DATE_TIME_STAMP_FORMAT1, result.getTimeZone()));
            	
            	if (null != result.getBuyerRescheduleServiceDate1() && null != result.getBuyerRescheduleStartTime()) {
            		rescheduleStartDate = TimeUtils.convertGMTToGivenTimeZone(result.getBuyerRescheduleServiceDate1(), result.getBuyerRescheduleStartTime(), result.getTimeZone());
            		if (null != rescheduleStartDate && !rescheduleStartDate.isEmpty()) {
            			reschedulePeriod.append(" to " + formatDate(format, (Date) rescheduleStartDate.get(OrderConstants.GMT_DATE)));
            			reschedulePeriod.append(" ");
            			reschedulePeriod.append((String) rescheduleStartDate.get(OrderConstants.GMT_TIME));
            		}
    			}
            	if(null != result.getBuyerRescheduleServiceDate2() && null != result.getBuyerRescheduleEndTime()){
            		rescheduleEndDate = TimeUtils.convertGMTToGivenTimeZone(result.getBuyerRescheduleServiceDate2(), result.getBuyerRescheduleEndTime(), result.getTimeZone());
            		if (null != rescheduleEndDate && !rescheduleEndDate.isEmpty()) {
            			reschedulePeriod.append(" - ");
            			reschedulePeriod.append(formatDate(format, (Date) rescheduleEndDate.get(OrderConstants.GMT_DATE)));
                		reschedulePeriod.append(" ");
                		reschedulePeriod.append((String) rescheduleEndDate.get(OrderConstants.GMT_TIME));
            		}
            	}
            	//reschedulePeriod.append(" "+getTimeZone(formatDate(format, (Date) rescheduleStartDate.get(OrderConstants.GMT_DATE))+" "+(String) rescheduleStartDate.get(OrderConstants.GMT_TIME), OrderConstants.RESCHEDULE_DATE_TIME_STAMP_FORMAT1, result.getTimeZone()));
        	
            	LOGGER.info(reschedulePeriod.toString());
            	}
    		}
            catch(Exception e){
            	LOGGER.error("Exception occured in edit reschedule:"+e);
            }
		return reschedulePeriod.toString();
	}
	
	private String formatDate(String format, Date date){
		DateFormat formatter = new SimpleDateFormat(format);
		String formattedDate = null;
		try {
			formattedDate = formatter.format(date);
		} catch (Exception e) {
			LOGGER.error("exception in formatDate()"+ e);
		}
		return formattedDate;
	}
	
	public String getTimeZone(String modifiedDate, String format, String timeZone){
		Date newDate = null;
		try {
			newDate = new SimpleDateFormat(format, Locale.ENGLISH).parse(modifiedDate);
		} catch (ParseException e) {
			LOGGER.error("Parse Exception getTimeZone "+ e);
		}
        TimeZone gmtTime = TimeZone.getTimeZone(timeZone);
        if(gmtTime.inDaylightTime(newDate)){
        	return "("+getDSTTimezone(timeZone)+")";
	    }
	    return "("+getStandardTimezone(timeZone)+")";
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

	
	//R12_0: setting the values to be inserted to buyer_outbound_notification table 
	private InHomeOutBoundNotificationVO populateNotification(String result, OrderUpdateRequest inHomeDetails,String modelNo,String brand) {
		
		//generating the request xml as string
		//Setting model No and Brand No in request
		String modelNoNA = InHomeNPSConstants.NOT_APPLICABLE;
		String brandNA = InHomeNPSConstants.NOT_APPLICABLE;
		if(StringUtils.isNotBlank(modelNo)){
			inHomeDetails.setModelNum(modelNo);
		}else{
			inHomeDetails.setModelNum(modelNoNA);
		}
		if(StringUtils.isNotBlank(brand)){
			inHomeDetails.setApplBrand(brand);
		}else{
			inHomeDetails.setApplBrand(brandNA);
		}				
		XStream xstream = new XStream();
		xstream.processAnnotations(OrderUpdateRequest.class);
		String requestXml = (String) xstream.toXML(inHomeDetails);
		
		//setting notification object
		InHomeOutBoundNotificationVO outBoundNotificationVO = new InHomeOutBoundNotificationVO();
		outBoundNotificationVO.setSeqNo(inHomeDetails.getCorrelationId());
		outBoundNotificationVO.setSoId(inHomeDetails.getSoId());
		// Service Id, defaulted to '2'
		outBoundNotificationVO.setServiceId(InHomeNPSConstants.IN_HOME_SERVICE_ID);
		outBoundNotificationVO.setXml(requestXml);
		// retry count, defaulted to '-1'
		outBoundNotificationVO.setRetryCount(InHomeNPSConstants.IN_HOME_RETRY_COUNT);
		outBoundNotificationVO.setCreatedDate(new Date());
		outBoundNotificationVO.setModifiedDate(new Date());
		outBoundNotificationVO.setActiveInd(0);
		if (InHomeNPSConstants.VALIDATION_SUCCESS.equalsIgnoreCase(result)) {
			// no validation error
			outBoundNotificationVO.setStatus(LeadNotificationStatusEnum.WAITING.name());
		}
		else if(InHomeNPSConstants.INACTIVE.equalsIgnoreCase(result)){
			//if SO is inactive
			outBoundNotificationVO.setStatus(LeadNotificationStatusEnum.EXCLUDED.name());
			outBoundNotificationVO.setException(result);
		}
		else {
			// Changes starts for SL-20602
			String temp = result;
			temp = temp.replaceAll(",", "");
			boolean exclude = true;
			String searchString = "Missing Information :callCd partCoverageCode for Part 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 | Invalid Information :jobCoverageCd : Not in valid set ";
			String[] words = temp.split("\\s+"); // splits by whitespace
			for (String word : words) {
			    if (!searchString.contains(word))
			    	exclude = false;
			}
			if (exclude){
				outBoundNotificationVO.setStatus(LeadNotificationStatusEnum.EXCLUDED.name());
				outBoundNotificationVO.setException(result);
			}
			else {
				// validation error
				outBoundNotificationVO.setStatus(LeadNotificationStatusEnum.ERROR.name());
				outBoundNotificationVO.setException(result);
			}
			//Changes ends for SL-20602
		}
		return outBoundNotificationVO;
	}

	
	public NotificationDao getNotificationDao() {
		return notificationDao;
	}

	public void setNotificationDao(NotificationDao notificationDao) {
		this.notificationDao = notificationDao;
	}
	public NotificationServiceMapper getInhomeNotificationMapper() {
		return inhomeNotificationMapper;
	}
	public void setInhomeNotificationMapper(
			NotificationServiceMapper inhomeNotificationMapper) {
		this.inhomeNotificationMapper = inhomeNotificationMapper;
	}
	public NotificationServiceValidator getInhomeNotificationValidator() {
		return inhomeNotificationValidator;
	}
	public void setInhomeNotificationValidator(
			NotificationServiceValidator inhomeNotificationValidator) {
		this.inhomeNotificationValidator = inhomeNotificationValidator;
	}

	
}
