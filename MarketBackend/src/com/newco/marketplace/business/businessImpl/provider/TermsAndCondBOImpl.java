package com.newco.marketplace.business.businessImpl.provider;


import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.ITermsAndCondBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.persistence.iDao.provider.ITermsAndCondDao;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.vo.provider.TermsAndCondVO;

public class TermsAndCondBOImpl implements ITermsAndCondBO {

	private ITermsAndCondDao iTermsAndCondDao;
	private ILookupDAO lookupDAO;
	private static final Logger logger = Logger.getLogger(TermsAndCondBOImpl.class);
	
	public TermsAndCondBOImpl(ITermsAndCondDao iTermsAndCondDao, ILookupDAO lookupDAO)
	{
		this.iTermsAndCondDao = iTermsAndCondDao;
		this.lookupDAO = lookupDAO;
	}
	
		
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.businessImpl.businessinfo.IBusinessinfoBO#save()
	 */
	public boolean save(TermsAndCondVO objTermsAndCondVO) throws Exception {
		
		TermsAndCondVO dbTermsAndCondVO = iTermsAndCondDao.save(objTermsAndCondVO);	
		return true;				
	}
	
	/**
	 * @param businessinfoDao
	 */
	public TermsAndCondBOImpl(ITermsAndCondDao termsAndCondDao) {
		iTermsAndCondDao = termsAndCondDao;
	}
	
	public TermsAndCondVO getData(TermsAndCondVO objTermsAndCondVO)
	throws Exception {
		
		TermsAndCondVO condVO = getTermsCond();
		TermsAndCondVO condBuckzVO = getBucksTermsCond();
		TermsAndCondVO dbTermsAndCondVO = iTermsAndCondDao.getData(objTermsAndCondVO);
		if(condVO != null)
			dbTermsAndCondVO.setTermsContent(condVO.getTermsContent());
		if(condBuckzVO != null)
			dbTermsAndCondVO.setSlBucksText(condBuckzVO.getTermsContent());
	return dbTermsAndCondVO ;
	}
	
	public TermsAndCondVO getBucksTermsCond() throws BusinessServiceException
	{
		LookupVO lookupVO = null;
		TermsAndCondVO termsAndCondVO = new TermsAndCondVO();
		try
		{
			lookupVO = lookupDAO.loadTermsConditions(ProviderConstants.PROVIDER_SERVICE_BUCKS);
			if(lookupVO != null)
			{
				logger.info("inside lookup vo not null**************"+lookupVO.getId());
				//termsAndCondVO.setId(Integer.parseInt(lookupVO.getId()));
				termsAndCondVO.setTermsContent(lookupVO.getDescr());
			}
		}
		catch (DBException ex) {
			logger
					.info("DB Exception @TermsAndCondBOImpl.getTermsCond() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @TermsAndCondBOImpl.getTermsCond() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @TermsAndCondBOImpl.getTermsCond() due to "
							+ ex.getMessage());
		}
		
		return  termsAndCondVO;
	}
	
	public TermsAndCondVO getTermsCond() throws BusinessServiceException
	{
		LookupVO lookupVO = null;
		TermsAndCondVO termsAndCondVO = new TermsAndCondVO();
		try
		{
			lookupVO = lookupDAO.loadTermsConditions(ProviderConstants.PROVIDER_FIRM_AGREEMENT);
			if(lookupVO != null)
			{
				logger.info("inside lookup vo not null**************"+lookupVO.getId());
				//termsAndCondVO.setId(Integer.parseInt(lookupVO.getId()));
				termsAndCondVO.setTermsContent(lookupVO.getDescr());
			}
		}
		catch (DBException ex) {
			logger
					.info("DB Exception @TermsAndCondBOImpl.getTermsCond() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @TermsAndCondBOImpl.getTermsCond() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @TermsAndCondBOImpl.getTermsCond() due to "
							+ ex.getMessage());
		}
		
		return  termsAndCondVO;
	}


	public Integer getData(Integer id) throws Exception {
		Integer ind = iTermsAndCondDao.getData(id);
		return ind;
	}


	public void save(Integer id) throws Exception {
		iTermsAndCondDao.save(id);
		
	}
	
}
