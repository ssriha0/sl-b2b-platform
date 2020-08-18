package com.newco.marketplace.business.iBusiness.hi;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.hi.provider.ProviderAccountVO;
import com.newco.marketplace.vo.hi.provider.ProviderSkillVO;

public interface IProviderResourceBO {

	/**
	 * @param providerVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public ProviderAccountVO validateCreateProviderAccount(ProviderAccountVO providerVO)throws BusinessServiceException;

	/**
	 * @param providerVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public ProviderAccountVO createProviderProfile(ProviderAccountVO providerVO)throws BusinessServiceException;
	public boolean validateFirmID(int firmId) throws BusinessServiceException;
	public boolean validateResourceID(int resourceId) throws BusinessServiceException;
	
	public ProviderAccountVO validateUpdateProviderDetails(ProviderAccountVO providerAccountVO) throws Exception;

	public ProviderAccountVO updateProvider(ProviderAccountVO providerAccountVO) throws Exception;
	/**
	 * 
	 * @param addProviderSkillVO
	 * @return ProviderSkillVO
	 * @throws BusinessServiceException 
	 */
	public ProviderSkillVO validateAddSkillProvider(ProviderSkillVO addProviderSkillVO) throws BusinessServiceException;
	
	/**
	 * 
	 * @param addProviderSkillVO
	 * @return ProviderSkillVO
	 * @throws Exception
	 */

	public ProviderSkillVO insertProviderSkill(ProviderSkillVO addProviderSkillVO) throws Exception;
	
	/**
	 * 
	 * @param removeProviderSkillVO
	 * @return ProviderSkillVO
	 * @throws BusinessServiceException 
	 */

	public ProviderSkillVO validateRemoveSkillProvider(ProviderSkillVO removeProviderSkillVO) throws BusinessServiceException;
	
	/**
	 * 
	 * @param removeProviderSkillVO
	 * @return ProviderSkillVO
	 * @throws BusinessServiceException 
	 */

	public ProviderSkillVO removeProviderSkill(ProviderSkillVO removeProviderSkillVO) throws BusinessServiceException;
}
