package com.newco.marketplace.web.utils;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.powerbuyer.ClaimVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.powerbuyer.ISOClaimDao;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;

public class SOClaimedFacility {
	
	private static final Logger logger = Logger.getLogger(SOClaimedFacility.class.getName());
	private ISOClaimDao pbToolsDel;
	private ServiceOrderDao soDAO;
	
	public boolean isCurrentUserTheClaimedUser(String soId, ServiceOrdersCriteria commonCriteria) throws BusinessServiceException {
		useSpringHelper(false);
		try {
			ClaimVO currentClaim = pbToolsDel.getClaimedSerivceOrderByServiceOrderId(soId);
			if (currentClaim == null) {
				return true;
			} else {
				return evaluateClaim(currentClaim, commonCriteria);
			}
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		throw new BusinessServiceException("Exception at "+SOClaimedFacility.class.getName());
	}
	
	public boolean isWorkflowTheStartingPoint(Map<String, Object> sessionMap) {
		Object workFlowStartPoint = sessionMap.get(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR);
		if(workFlowStartPoint != null) {
			return true;
		} else {
			return false;
		}
	}
	
	//SL-19820
	public boolean isWorkflowTheStartingPoint(Map<String, Object> sessionMap, String soId) {
		//SL-19820
		//Object workFlowStartPoint = sessionMap.get(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR);
		Object workFlowStartPoint = sessionMap.get(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR+"_"+soId);
		if(workFlowStartPoint != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasTheServiceOrderChanged(String soId) throws BusinessServiceException {
		useSpringHelper(false);
		ClaimVO currentClaim = null;
		try {
			currentClaim = pbToolsDel.getClaimedSerivceOrderByServiceOrderId(soId);
		} catch (DataServiceException e) {
			throw new BusinessServiceException("Exception at "+SOClaimedFacility.class.getName());
		}
		return (currentClaim.getSoModifiedDate().equals(currentClaim.getCurrentSOModifiedDate()));
	}
	
	
	public void updateClaimedSO( String soId) throws BusinessServiceException{
		useSpringHelper(true);
		ClaimVO currentClaim = null;
		try {
			currentClaim = pbToolsDel.getClaimedSerivceOrderByServiceOrderId(soId);
			Date soModifiedDate = soDAO.getServiceOrderModifiedDate(soId);
			currentClaim.setSoModifiedDate(soModifiedDate);
			currentClaim.setSoId(soId);
			pbToolsDel.updateClaimSO(currentClaim);
		} catch (DataServiceException e) {
			throw new BusinessServiceException("Exception at "+SOClaimedFacility.class.getName());
		}
	}
	
	private boolean evaluateClaim(ClaimVO aClaim, ServiceOrdersCriteria commonCriteria) {
		Integer claimResourceId = aClaim.getResourceId();
		Integer loggedInResourceId = commonCriteria.getVendBuyerResId();
		return (claimResourceId.equals(loggedInResourceId));
	}
	
	private void useSpringHelper(boolean getSO) {
//		ClassPathXmlApplicationContext context =
//						MPSpringLoaderPlugin.ctx;
//		pbToolsDel = (ISOClaimDao)context.getBean("soClaimDAO");
//		if(getSO){
//			soDAO = (ServiceOrderDao)context.getBean("serviceOrderDao");
//		}
		
//		pbToolsDel = (ISOClaimDao)SpringBeanFactoryLocator.getBean("soClaimDAO");
//		if(getSO) {
//			soDAO = (ServiceOrderDao)SpringBeanFactoryLocator.getBean("serviceOrderDao");
//		}

		pbToolsDel = (ISOClaimDao)MPSpringLoaderPlugIn.getCtx().getBean("soClaimDAO");
		if(getSO) {
			soDAO = (ServiceOrderDao)MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderDao");
		}
	}
}
