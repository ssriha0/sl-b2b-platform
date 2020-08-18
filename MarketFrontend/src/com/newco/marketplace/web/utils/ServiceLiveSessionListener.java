package com.newco.marketplace.web.utils;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.provider.AuditLogBOImpl;
import com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO;
import com.newco.marketplace.business.iBusiness.promo.PromoBO;
import com.newco.marketplace.business.iBusiness.provider.IAuditLogBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.audit.LoginAuditVO;
import com.newco.marketplace.web.delegates.IExploreTheMarketplaceDelegate;
import com.newco.marketplace.web.dto.PromoDto;

public class ServiceLiveSessionListener implements HttpSessionListener{

	private static final Logger logger = Logger.getLogger(ServiceLiveSessionListener.class.getName());
	private IAuditLogBO auditLogBOImpl;
	
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		PromoDto aPromoDto = new PromoDto();
		PromoBO myPromoBO = (PromoBO) MPSpringLoaderPlugIn.getCtx().getBean("promoBO");
		if (myPromoBO.retrievePromoPostingFeeByRoleId(OrderConstants.SIMPLE_BUYER_ROLEID) != null) {
		aPromoDto.setPromoActive(true);
		}
		// aPromoDto.setPromoActive(false);

		arg0.getSession().setAttribute(PromoDto.PROMO_DTO, aPromoDto);
		logger.debug("Setup the promo thing ");
		}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		// In Explore Market Place, the search results are saved in temp table.
		// When the session is expired, we are cleaning up the temp table.
		logger.debug("In ServiceLiveSessionListener --> sessionDestroyed()");
		SecurityContext securityCntxt = (SecurityContext) arg0.getSession().getAttribute(Constants.SESSION.SECURITY_CONTEXT);
		IAuditLogBO auditLogBOImpl = (AuditLogBOImpl) MPSpringLoaderPlugIn.getCtx().getBean("auditLogBOImpl");
		if(securityCntxt != null && securityCntxt.getLoginAuditId() != 0) {
			int loginAuditId = securityCntxt.getLoginAuditId();
			int activeSessionAuditId = securityCntxt.getActiveSessionAuditId();
			logger.info("LoginLogging: SessionDestroyed: LoginAuditId = ["+loginAuditId+"] activeSessionAuditId = ["+activeSessionAuditId+"]");
			LoginAuditVO loginAuditVO = new LoginAuditVO();
			loginAuditVO.setCompanyId(securityCntxt.getCompanyId());
			loginAuditVO.setResourceId(securityCntxt.getVendBuyerResId());
			loginAuditVO.setRoleId(securityCntxt.getRoleId());
			auditLogBOImpl.updateLoginAudit(loginAuditVO, loginAuditId, activeSessionAuditId, 1);
			securityCntxt.setLoginAuditId(0);
			securityCntxt.setLoginAuditId(0);
		}
		String etmSearchQueryKey = (String)arg0.getSession().getAttribute(OrderConstants.ETM_SEARCH_KEY);
		logger.debug("Search Query Key is "+etmSearchQueryKey);
		
		if(!SLStringUtils.isNullOrEmpty(etmSearchQueryKey)){
			IExploreTheMarketplaceDelegate etmDelegate = null;
			Integer numRowsDeleted = null;			

			etmDelegate = (IExploreTheMarketplaceDelegate)MPSpringLoaderPlugIn.getCtx().getBean("exploreTheMarketplaceDelegate");
			
			if(etmDelegate != null){
				try {
					logger.debug("Loading ExploreTheMarketplaceDelegate and calling cleanETMTempTable");
					numRowsDeleted = etmDelegate.cleanETMTempTable(etmSearchQueryKey);
					
					if(numRowsDeleted != null){
						logger.info("In ServiceLiveSessionListener --> sessionDestroyed()");
						logger.info("Cleaning the temp table. Deleted "+numRowsDeleted+" number of rows");
					}
				} catch (BusinessServiceException e) {
					logger.error("Error in ServiceLiveSessionListener --> sessionDestroyed()"+e.getMessage());
				}
			}			
		}
		
		// unclaim
		if (securityCntxt != null) {
			Integer resourceId = securityCntxt.getVendBuyerResId();
			if (resourceId != null) {
				logger.info("===***=== DEBUG_UNCLAIM=== Session expired for resourceId: " + resourceId);
				IPowerBuyerBO powerBuyerBO = (IPowerBuyerBO) MPSpringLoaderPlugIn.getCtx().getBean("powerBuyerBO");
				powerBuyerBO.unClaimByResource(resourceId);
			}
		}
	}

	public IAuditLogBO getAuditLogBOImpl() {
		return auditLogBOImpl;
	}

	public void setAuditLogBOImpl(IAuditLogBO auditLogBOImpl) {
		this.auditLogBOImpl = auditLogBOImpl;
	}

//	private ClassPathXmlApplicationContext loadApplicationContext() {
//		logger.debug("In ServiceLiveSessionListener --> loadApplicationContext()");
//		logger.info("Loading application context ");
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//		return context;
//	}
}
