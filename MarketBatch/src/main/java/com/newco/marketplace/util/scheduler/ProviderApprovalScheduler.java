package com.newco.marketplace.util.scheduler;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.business.iBusiness.audit.IAuditProfileBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.AdminConstants;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.vo.provider.VendorResource;

public class ProviderApprovalScheduler extends ABaseScheduler implements Job{
	
	private static final Logger logger= Logger.getLogger(ProviderApprovalScheduler.class);

	public void execute(JobExecutionContext context) throws JobExecutionException  {
		logger.debug("Start of ETMCleanupScheduler --> execute() to clean the temp table");
		
		try {
			doProcess(getApplicationContext(context));	
		} catch (Exception e) {
			throw new JobExecutionException(e);
		}
	}
	
	public  void doProcess(ApplicationContext appCtx) 
	{
		IAuditProfileBO auditProfileBo = 
			(IAuditProfileBO)appCtx.getBean(Constants.ApplicationContextBeans.AUDIT_PROFILE_BO_BEAN);
		List<VendorResource> resourcesReadyForApproval;
		try
		{
			resourcesReadyForApproval = auditProfileBo.retrieveResourceForApproval();
			for(VendorResource vendorResource : resourcesReadyForApproval){
				AuditVO auditVO = buildAuditVOForResource(vendorResource);
				try
				{
					auditProfileBo.updateWFStatusAndReasonCodes(auditVO, true);
				}
				catch(AuditException ex)
				{
					logger.error("PROVIDER_APPROVAL failure - "+
							"Problem approving vendor resource",ex);
					auditProfileBo.handleVendorResourceUpdateError(auditVO);
				}
			}
		}
		catch(BusinessServiceException e)
		{
			logger.error( "PROVIDER_APPROVAL failure - " +
					"Could not retrieve resource list", e);
		}
		List<VendorHdr> companiesReadyForApproval;
		try
		{
			companiesReadyForApproval = auditProfileBo.retrieveCompaniesForApproval();	
			for(VendorHdr vendorHdr : companiesReadyForApproval){
				AuditVO auditVO = buildAuditVOForCompany(vendorHdr);
				try
				{
					auditProfileBo.updateWFStatusAndReasonCodes(auditVO, true);
				}
				catch(AuditException ex)
				{
					logger.error("PROVIDER_APPROVAL failure - "+
							"Problem approving vendor firm",ex);
					auditProfileBo.handleVendorUpdateError(auditVO);
				}
			}
		}
		catch(BusinessServiceException e)
		{
			logger.error( "PROVIDER_APPROVAL failure - " +
					"Could not retrieve vendor firm list", e);
		}
	}

	private AuditVO buildAuditVOForCompany(VendorHdr vendorHdr){
		AuditVO auditVO = new AuditVO();
		
		auditVO.setAuditKeyId(vendorHdr.getVendorId());
		auditVO.setAuditLinkId(AdminConstants.AUDIT_LINK_ID_COMPANY);		
		auditVO.setResourceId(Integer.valueOf(0));
		auditVO.setReviewedBy(AdminConstants.SYSTEM_USER);
		auditVO.setSendEmailNotice(true);
		auditVO.setVendorId(vendorHdr.getVendorId());		
		auditVO.setWfState(AdminConstants.SERVICE_LIVE_APPROVED);		
		auditVO.setWfStateId(AdminConstants.COMPANY_APPROVED_ID);
		auditVO.setWfStatusId(AdminConstants.COMPANY_APPROVED_ID);
		
		return auditVO;
	}
	
	private AuditVO buildAuditVOForResource(VendorResource vendorResource){
		AuditVO auditVO = new AuditVO();
		
		auditVO.setAuditKeyId(vendorResource.getResourceId());
		auditVO.setAuditLinkId(AdminConstants.AUDIT_LINK_ID_TEAM_MEMBER);		
		auditVO.setResourceId(vendorResource.getResourceId());
		auditVO.setReviewedBy(AdminConstants.SYSTEM_USER);
		auditVO.setSendEmailNotice(true);
		auditVO.setVendorId(vendorResource.getVendorId());		
		auditVO.setWfState(AdminConstants.TEAM_MEMBER_APPROVED);
		auditVO.setWfStateId(AdminConstants.TEAM_MEMBER_APPROVED_ID);
		auditVO.setWfStatusId(AdminConstants.TEAM_MEMBER_APPROVED_ID);
		
		return auditVO;
	}
}
