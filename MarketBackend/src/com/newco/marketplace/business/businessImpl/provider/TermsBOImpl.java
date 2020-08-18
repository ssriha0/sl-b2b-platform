package com.newco.marketplace.business.businessImpl.provider;


import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.IActivityRegistryBO;
import com.newco.marketplace.business.iBusiness.provider.IProviderEmailBO;
import com.newco.marketplace.business.iBusiness.provider.ITermsBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.persistence.iDao.provider.ITermsDao;
import com.newco.marketplace.persistence.iDao.provider.IUserProfileDao;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.vo.provider.TermsVO;

public class TermsBOImpl implements ITermsBO {

	private ITermsDao iTermsDao;
	private IActivityRegistryDao iactivityRegistryDao;
	private ILookupDAO lookupDAO;
	private IUserProfileDao iUserProfileDao;
	private IProviderEmailBO iProviderEmailBO;
	private IActivityRegistryBO activityRegistryBO;
	
	private static final Logger logger = Logger.getLogger(TermsBOImpl.class);
	
	/**
	 * 
	 * @param termsDao
	 * @param activityRegistryDao
	 */
	public TermsBOImpl(ITermsDao termsDao,
						IActivityRegistryDao activityRegistryDao,
						ILookupDAO lookupDAO,
						IUserProfileDao iUserProfileDao,
						IProviderEmailBO iProviderEmailBO,
						IActivityRegistryBO activityRegistryBO) {
		iTermsDao = termsDao;
		iactivityRegistryDao = activityRegistryDao;
		this.lookupDAO = lookupDAO;
		this.iUserProfileDao = iUserProfileDao;
		this.iProviderEmailBO = iProviderEmailBO;
		this.activityRegistryBO = activityRegistryBO;
	}
	
	public boolean save(TermsVO objTermsVO) throws Exception {
		
		TermsVO dbTermsVO = iTermsDao.save(objTermsVO);
		
		if((dbTermsVO!=null) && (objTermsVO.getResourceId()!=null)){
			iactivityRegistryDao.updateResourceActivityStatus(objTermsVO.getResourceId(),ActivityRegistryConstants.RESOURCE_TERMSANDCOND);
		}else {
			//throw new Exception("objTermsVO.getResourceId() is Null-TermsBOImpl.java");
			System.out.println("objTermsVO.getResourceId() is Null-TermsBOImpl.java=============>");
		}

		return true;				
	}
	
	
	public TermsVO getTermsCond() throws BusinessServiceException
	{
		LookupVO lookupVO = null;
		TermsVO termsVO = new TermsVO();
		try
		{
			lookupVO = lookupDAO.loadTermsConditions(ProviderConstants.SERVICE_PROVIDER_AGREEMENT);
			if(lookupVO != null)
			{
				logger.info("inside lookup vo not null**************"+lookupVO.getId());
				//termsAndCondVO.setId(Integer.parseInt(lookupVO.getId()));
				termsVO.setTermsContent(lookupVO.getDescr());
			}
		}
		catch (DBException ex) {
			logger.info("DB Exception @TermsBOImpl.getTermsCond() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @TermsBOImpl.getTermsCond() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @TermsBOImpl.getTermsCond() due to "
							+ ex.getMessage());
		}
		
		return  termsVO;
	}
	
	
	public TermsVO getData(TermsVO objTermsVO)	throws Exception {
		
		TermsVO condVO = getTermsCond();
		TermsVO dbTermsVO = iTermsDao.getData(objTermsVO);
		if(condVO != null)
			dbTermsVO.setTermsContent(condVO.getTermsContent());
	
		return dbTermsVO ;
	}
	
	public TermsVO sendEmailForNewUser(TermsVO objTermsVO, String provUserName) throws BusinessServiceException
	{
		try
		{
			Integer resourceId = Integer.parseInt(objTermsVO.getResourceId());
			activityRegistryBO.sendEmailTeamMemberRegistration(resourceId, provUserName);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @TermsBOImpl.sendEmailForNewUser() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @TermsBOImpl.sendEmailForNewUser() due to "
							+ ex.getMessage());
		}
		return objTermsVO;
	}
	
}
