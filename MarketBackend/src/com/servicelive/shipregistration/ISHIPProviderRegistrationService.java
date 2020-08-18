package com.servicelive.shipregistration;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.business.iBusiness.provider.IAuditStates;
import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.exception.EmailSenderException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.provider.ProviderRegistrationVO;

public interface ISHIPProviderRegistrationService extends IAuditStates {
	
	public Integer getSLIndustry(String productDesc) ;
	public ProviderRegistrationVO doRegisterFirm(
			ProviderRegistrationVO providerRegistrationVO)
			throws DBException, DataAccessException, DataServiceException,
			EmailSenderException, AuditException ;
	public ProviderRegistrationVO doRegisterResource(
			ProviderRegistrationVO providerRegistrationVO)
	throws BusinessServiceException, AuditException;
	public void insertSubContractorInfo(
			ProviderRegistrationVO providerRegistrationVO)
	throws BusinessServiceException;
	public Integer getVendorIdForSubContractorForResource(Integer subContractorId);
	public String getApplicationProperty(String key);
	public Integer getVendorIdForFirm(Integer subContractorId);
	public String getValidBusinessStateCd(ProviderRegistrationVO registrationVO);
	public List<Integer> getVendorIdForSubContractorCrewIdForResource(ProviderRegistrationVO registrationVO);
	
	
}
