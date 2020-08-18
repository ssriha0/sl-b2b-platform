package com.newco.marketplace.business.businessImpl.serviceorder;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.businessImpl.so.order.BaseOrderBO;
import com.newco.marketplace.business.iBusiness.serviceorder.ISOEventBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ABaseRequestDispatcher;
import com.newco.marketplace.dto.vo.serviceorder.InOutVO;
import com.newco.marketplace.dto.vo.serviceorder.SOEventVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.onsiteVisit.IOnsiteVisitDao;
import com.newco.marketplace.persistence.iDao.so.ISOEventDao;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class SOEventBOImpl extends BaseOrderBO implements ISOEventBO {

	private static final Logger LOGGER = Logger.getLogger(SOEventBOImpl.class);

	private ISOEventDao eventDao;
	private IOnsiteVisitDao visitDao;
	private IServiceOrderBO serviceOrderBO;

	public ProcessResponse insertEvent(String so_id, String resourceID, long reasonCode, long eventType) {
		ProcessResponse response = new ProcessResponse();
		SOEventVO vo = new SOEventVO();
		try {
			// convert event request to VO
//			RandomGUID random = new RandomGUID();
//			vo.setEventID(random.generateGUID().longValue());
			vo.setEventTypeID(eventType);
			vo.setReasonCode(reasonCode);
			vo.setResourceID(resourceID);
			vo.setServiceOrderID(so_id);
		}
		catch (Exception ex) {
			response.setCode(SYSTEM_ERROR_RC);
			response.setMessage(ex.getMessage());
			LOGGER.error(ex.getMessage());
			return response;
		}
		// check for required fields
//		if (vo.getEventID() == 0) {
//			response.setCode(USER_ERROR_RC);
//			response.getMessages().add("ERROR - eventID could not be generated");
//			return response;
//		}
		if (vo.getServiceOrderID() == null || vo.getServiceOrderID().length() == 0) {
			response.setCode(USER_ERROR_RC);
			response.getMessages().add("ERROR - service order does not exisit or is invalid");
			return response;
		}
//		if (vo.getEventID() == 0) {
//			response.setCode(USER_ERROR_RC);
//			response.getMessages().add("ERROR - service order does not exisit or is invalid");
//			return response;
//		}
		if (vo.getEventTypeID() == 0) {
			response.setCode(USER_ERROR_RC);
			response.getMessages().add("ERROR - an event type code is required");
			return response;
		}
		// depart with issues must have a reason
		if (vo.getEventTypeID() == 3) {
			if (vo.getEventReasonCode() == 0) {
				response.setCode(USER_ERROR_RC);
				response.getMessages().add("ERROR - a reason must be given for a departure with issues");
				return response;
			}
		}
		if (vo.getResourceID() == null || vo.getResourceID().length() == 0) {
			response.setCode(USER_ERROR_RC);
			response.getMessages().add("ERROR - a resourceID is required");
			return response;
		}

		// see if service order exists
		IServiceOrderBO bo;
		Object beanFacility = null;
		try {
			beanFacility = MPSpringLoaderPlugIn.getCtx().getBean( ABaseRequestDispatcher.SERVICE_ORDER_BUSINESS_OBJECT_REFERENCE );
		} catch (BeansException e) {
			e.printStackTrace();
		}
		bo = (IServiceOrderBO)beanFacility;
		if (!bo.isValidServiceOrder(vo.getServiceOrderID())) {
			response.setCode(USER_ERROR_RC);
			response.getMessages().add("ERROR - service order does not exisit or is invalid");
		}
		else {
			getEventDao().insert(vo);
			response.setCode(VALID_RC);
		}
		return response;
	}

	public ISOEventDao getEventDao() {
		return eventDao;
	}

	public void setEventDao(ISOEventDao eventDao) {
		this.eventDao = eventDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.ISOEventBO#processOnSiteVisits()
	 */
	@SuppressWarnings("unchecked")
//	public void processOnSiteVisits() throws BusinessServiceException {
//		try {
//			String[] outEvent={"so_id","create_date","event_type_id"},outVisit={"so_id","arrival_date"};
//			SOEventVO inSOEventVO=new SOEventVO(),outSOEventVO=new SOEventVO();
//			InOutVO inOutVO=new InOutVO(inSOEventVO, outSOEventVO),inOutVisit=new InOutVO(null, outVisit),inOutwhereVisit=new InOutVO(null, null);
//			SOOnsiteVisitVO lastVisit=null;
//			inSOEventVO.setProcessInd("new");
//			outSOEventVO.setProcessInd("start");				
//			eventDao.updateSOEvent(inOutVO);				
//			inOutVO=new InOutVO(inSOEventVO, outEvent);
//			inSOEventVO.setProcessInd("start");
//			List<SOEventVO> resultSOEventVO=eventDao.selectSOEventVO(inOutVO);
//			String prevSoId=null,errorSoId=null;
//			List<SOOnsiteVisitVO> resultVisit = null;
//			Integer status=null;
//			SecurityContext securityContext = SecurityUtil.getSystemSecurityContext();
//			if (securityContext.getCompanyId() != null){
//				Integer buyerId = securityContext.getCompanyId();
//				ServiceOrderUtil.enrichSecurityContext(securityContext, buyerId);
//			}
//
//			inOutVO=new InOutVO(inSOEventVO, outSOEventVO);
//			ServiceOrder so=null;
//			for(SOEventVO event : resultSOEventVO ){
//				if(prevSoId==null || !prevSoId.equals(event.getServiceOrderID())){
//					prevSoId=event.getServiceOrderID();
//					so=	getServiceOrderDao().getServiceOrder(prevSoId);
//					if(so!=null)
//						status=so.getWfStateId();
//					if(OrderConstants.ACCEPTED_STATUS==status.intValue()){
//						ProcessResponse pr=serviceOrderBO.activateAcceptedSO(prevSoId, securityContext);
//						if(!pr.getCode().equals(VALID_RC)){
//							inSOEventVO=new SOEventVO();
//							inSOEventVO.setServiceOrderID(prevSoId);
//							inSOEventVO.setProcessInd("start");
//							outSOEventVO.setProcessInd("new");	
//							inOutVO=new InOutVO(inSOEventVO, outSOEventVO);
//							eventDao.updateSOEvent(inOutVO);	
//							errorSoId=prevSoId;	
//							continue;
//						}
//						status=OrderConstants.ACTIVE_STATUS;
//					}else if(status==null || (OrderConstants.ACTIVE_STATUS!=status.intValue()  && OrderConstants.PROBLEM_STATUS!=status.intValue())){
//						inSOEventVO=new SOEventVO();
//						inSOEventVO.setServiceOrderID(prevSoId);
//						inSOEventVO.setProcessInd("start");
//						outSOEventVO.setProcessInd("invalid state");	
//						inOutVO=new InOutVO(inSOEventVO, outSOEventVO);
//						eventDao.updateSOEvent(inOutVO);	
//						errorSoId=prevSoId;
//					}else{errorSoId=null;}
//					SOOnsiteVisitVO inSOOnSiteVisitVO=new SOOnsiteVisitVO();
//					inSOOnSiteVisitVO.setSoId(event.getServiceOrderID());
//					inOutVisit.setIn(inSOOnSiteVisitVO);
//					resultVisit=visitDao.selectSOOnSiteVisit(inOutVisit);	
//					lastVisit=null;
//					if(resultVisit!=null && !resultVisit.isEmpty()){
//						lastVisit = resultVisit.get(resultVisit.size()-1);
//					}
//				}
//				if(errorSoId!=null){continue;}
//				event.setCreateDate(new Date(TimeUtils.convertToGMT(new Timestamp(event.getCreateDate().getTime()), so.getServiceLocationTimeZone()).getTime()));
//				SOOnsiteVisitVO outSOOnSiteVisitVO=new SOOnsiteVisitVO();					
//				if(event.getEventTypeID().equals(new Long(1))){
//					outSOOnSiteVisitVO.setSoId(event.getServiceOrderID());
//					outSOOnSiteVisitVO.setArrivalDate(event.getCreateDate());
//					outSOOnSiteVisitVO.setArrivalInputMethod(1);
//					outSOOnSiteVisitVO.setResourceId(securityContext.getVendBuyerResId());
//					outSOOnSiteVisitVO.setCreatedDate(event.getCreateDate());
//					outSOOnSiteVisitVO.setIvrcreatedate(event.getCreateDate());
//					visitDao.insert(outSOOnSiteVisitVO);
//					if(OrderConstants.ACTIVE_STATUS==status.intValue()){
//						serviceOrderBO.updateSOSubStatus(event.getServiceOrderID(), OrderConstants.PROVIDER_ONSITE, securityContext);
//					}
//				}
//				else{
//					outSOOnSiteVisitVO.setDepartureDate(event.getCreateDate());
//					outSOOnSiteVisitVO.setDepartureInputMethod(1);	
//					outSOOnSiteVisitVO.setDepartureResourceId(securityContext.getVendBuyerResId());
//					outSOOnSiteVisitVO.setDepartureCondition(Constants.EVENT_SUBSTATUS_MAP.get(event.getEventReasonCode()));					
//					if(lastVisit!=null && lastVisit.getDepartureDate()==null && lastVisit.getArrivalDate().before(event.getCreateDate())){
//						SOOnsiteVisitVO inSOOnSiteVisitVO=new SOOnsiteVisitVO();
//						inSOOnSiteVisitVO.setVisitId(lastVisit.getVisitId());
//						inOutwhereVisit.setIn(inSOOnSiteVisitVO);
//						inOutwhereVisit.setOut(outSOOnSiteVisitVO);
//						visitDao.updateSOOnSiteVisit(inOutwhereVisit);
//					}
//					else{						
//						outSOOnSiteVisitVO.setSoId(event.getServiceOrderID());
//						outSOOnSiteVisitVO.setCreatedDate(event.getCreateDate());
//						outSOOnSiteVisitVO.setIvrcreatedate(event.getCreateDate());							
//						lastVisit=visitDao.insert(outSOOnSiteVisitVO);
//					}
//					if(event.getEventReasonCode().intValue()!=OrderConstants.NOTAPPLICABLE.intValue()){
//						serviceOrderBO.reportProblem(event.getServiceOrderID(), outSOOnSiteVisitVO.getDepartureCondition()!=null?outSOOnSiteVisitVO.getDepartureCondition().intValue():0, "Depart Issues", securityContext.getVendBuyerResId(), securityContext.getRoleId(), "IVR update", securityContext.getUsername(), false,securityContext);
//					}else{
//						serviceOrderBO.updateSOSubStatus(event.getServiceOrderID(), OrderConstants.JOB_DONE, securityContext);
//					}						
//				}
//				lastVisit=outSOOnSiteVisitVO;
//				inSOEventVO=new SOEventVO();
//				inSOEventVO.setProcessInd("start");
//				inSOEventVO.setEventID(event.getEventID());
//				outSOEventVO.setProcessInd("processed");	
//				inOutVO=new InOutVO(inSOEventVO, outSOEventVO);
//				eventDao.updateSOEvent(inOutVO);				
//			}
//		} catch (DataServiceException e) {
//			logger.error("processOnSiteVisits failed", e);
//			throw new BusinessServiceException("processOnSiteVisits failed", e);
//		}
//	}

	/**
	 * @return the visitDao
	 */
	public IOnsiteVisitDao getVisitDao() {
		return visitDao;
	}

	/**
	 * @param visitDao the visitDao to set
	 */
	public void setVisitDao(IOnsiteVisitDao visitDao) {
		this.visitDao = visitDao;
	}

	/**
	 * @return the serviceOrderBO
	 */
	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	/**
	 * @param serviceOrderBO the serviceOrderBO to set
	 */
	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public List<SOEventVO> selectSOEventVO(InOutVO inOutVO) throws BusinessServiceException {
		try {
			return eventDao.selectSOEventVO(inOutVO);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
	}

	public void updateSOEvent(InOutVO inOutVO) throws BusinessServiceException {
		try {
			eventDao.updateSOEvent(inOutVO);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}		
	}

	/*
	 * SOEventVO outSOEventVO=new SOEventVO(); outSOEventVO.setProcessInd("1aa");
	 * 
	 * 
	 * SOOnsiteVisitVO outSOOnSiteVisitVO=new SOOnsiteVisitVO();
	 * System.out.println(resultSOEventVO); System.out.println(eventDao.insert(new
	 * SOOnsiteVisitVO())); inSOOnSiteVisitVO.setResourceId(0);
	 * inOutVO.setIn(inSOOnSiteVisitVO); out.clear(); out.add("so_id");
	 * out.add("arrival_date");
	 * System.out.println(eventDao.selectSOOnSiteVisit(inOutVO));
	 * System.out.println(eventDao.updateSOEvent(new
	 * InOutVO(inSOEventVO,outSOEventVO)));
	 * inSOOnSiteVisitVO.setSoId("100-6660-8927-15");
	 * outSOOnSiteVisitVO.setDepartureDate(new Date());
	 * 
	 * System.out.println(eventDao.updateSOOnSiteVisit(new
	 * InOutVO(inSOOnSiteVisitVO,outSOOnSiteVisitVO)));
	 */		



}
