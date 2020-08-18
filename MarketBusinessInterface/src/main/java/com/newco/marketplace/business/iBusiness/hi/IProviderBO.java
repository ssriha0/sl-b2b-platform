/**
 * 
 */
package com.newco.marketplace.business.iBusiness.hi;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.business.iBusiness.provider.IAuditStates;
import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.exception.EmailSenderException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.hi.provider.ApproveFirmsVO;
import com.newco.marketplace.vo.hi.provider.ApproveProvidersVO;
import com.newco.marketplace.vo.hi.provider.ProviderRegistrationVO;



public interface IProviderBO extends IAuditStates{
	public ProviderRegistrationVO validateFirmDetails(ProviderRegistrationVO providerRegistrationVO);

	public  ProviderRegistrationVO createFirm(
			ProviderRegistrationVO providerRegistrationVO)
			throws DBException, DataAccessException, DataServiceException,
			EmailSenderException, AuditException, com.newco.marketplace.exception.core.DataServiceException;

	public ProviderRegistrationVO updateFirm(ProviderRegistrationVO providerRegistrationVO) throws DBException, Exception;

	public ProviderRegistrationVO validateUpdateFirmDetails(ProviderRegistrationVO providerRegistrationVO);

	/**@Description: validating firms for status change.
	 * @param approveFirmsVOList
	 * @param admInResourceId 
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<ApproveFirmsVO> validateFirms(List<ApproveFirmsVO> approveFirmsVOList, Integer admInResourceId)throws BusinessServiceException;
	
	/**@Description: Removing Invalid firms from Parent and move to a new List.
	 * @param approveFirmsVOList
	 * @return
	 */
	public List<ApproveFirmsVO> removeInvalidOrValidFirms(List<ApproveFirmsVO> approveFirmsVOList,String indicator);

	/**@Description: Updating Wf status and reason codes for eligible firm.
	 * @param validFirmsList
	 * @param admInResourceId 
	 * @return
	 * @throws BusinessServiceException 
	 */
	public List<ApproveFirmsVO> updateWFStatusAndReasonCodes(List<ApproveFirmsVO> validFirmsList, Integer admInResourceId) throws BusinessServiceException;
	/**@Description: validating providers for status change.
	 * @param approveProvidersVOList
	 * @param admInResourceId 
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<ApproveProvidersVO> validateProviders(List<ApproveProvidersVO> approveProvidersVOList, Integer admInResourceId)throws BusinessServiceException;
	
	/**@Description: Removing Invalid providers from Parent and move to a new List.
	 * @param approveProvidersVOList
	 * @return
	 */
	public List<ApproveProvidersVO> removeInvalidOrValidProviders(List<ApproveProvidersVO> approveProvidersVOList,String indicator);
	
	/**@Description: Updating Wf status and reason codes for eligible provider.
	 * @param validProvidersList
	 * @param admInResourceId 
	 * @return
	 * @throws BusinessServiceException 
	 */
	public List<ApproveProvidersVO> updateProviderWFStatusAndReasonCodes(List<ApproveProvidersVO> validProvidersList, Integer admInResourceId) throws BusinessServiceException;
}
