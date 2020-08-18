package com.newco.marketplace.business.businessImpl.provider;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;



import com.newco.marketplace.business.iBusiness.provider.IBusinessinfoBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.persistence.iDao.provider.IBusinessinfoDao;
import com.newco.marketplace.persistence.iDao.provider.IZipDao;

import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.vo.provider.BusinessinfoVO;



public class BusinessinfoBOImpl implements IBusinessinfoBO{


	private IBusinessinfoDao iBusinessinfoDao;
	private ILookupDAO iLookupDAO;
	private IZipDao zipDao;
	private static final Logger logger = Logger
	.getLogger(RegistrationBOImpl.class.getName());
	/**
	 * @param businessinfoDao
	 */
	public BusinessinfoBOImpl(IBusinessinfoDao businessinfoDao,
			ILookupDAO lookupDAO,IZipDao zipDao) {
		iBusinessinfoDao = businessinfoDao;
		iLookupDAO = lookupDAO;
		this.zipDao = zipDao;
	
	}
	
	
	public BusinessinfoVO loadZipSet(
			BusinessinfoVO businessinfoVO)
			throws BusinessServiceException {

		try {

			List stateTypeList = null;
			if (businessinfoVO.getStateType() != null
			&& 	businessinfoVO.getStateType().length() > 0
			&&  businessinfoVO.getStateType().equalsIgnoreCase("business"))
			{
				stateTypeList = zipDao.queryList(businessinfoVO.getBusinessState());
			}

		if (businessinfoVO.getStateType() != null
			&& 	businessinfoVO.getStateType().length() > 0
			&&  businessinfoVO.getStateType().equalsIgnoreCase("mail"))
			{			
				stateTypeList = zipDao.queryList(businessinfoVO.getMailingState());
			}
			businessinfoVO.setStateTypeList(stateTypeList);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @RegistrationBOImpl.loadZipSet() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @RegistrationBOImpl.loadZipSet() due to "
							+ ex.getMessage());
		}
		return businessinfoVO;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.businessImpl.businessinfo.IBusinessinfoBO#save()
	 */
	public boolean save(BusinessinfoVO objBusinessinfoVO) throws Exception {
		
		BusinessinfoVO dbBusinessinfoVO = iBusinessinfoDao.save(objBusinessinfoVO);	
		return true;				
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.businessImpl.businessinfo.IBusinessinfoBO#getData()
	 */
	public BusinessinfoVO getData(BusinessinfoVO objBusinessinfoVO) throws Exception {
		
		BusinessinfoVO dbBusinessinfoVO = iBusinessinfoDao.getData(objBusinessinfoVO);
	
		dbBusinessinfoVO.setMapCompanySize(iLookupDAO.loadCompanySize());
		dbBusinessinfoVO.setMapBusStructure(iLookupDAO.loadBusinessTypes());
		dbBusinessinfoVO.setMapForeignOwnedPct(iLookupDAO.loadForeignOwnedPercent());
		dbBusinessinfoVO.setMapAnnualSalesRevenue(iLookupDAO.loadSalesVolume());
		dbBusinessinfoVO.setPrimaryIndList(iLookupDAO.loadPrimaryIndustry());
		if(StringUtils.isNumeric(objBusinessinfoVO.getVendorId()))
				dbBusinessinfoVO.setProviderList(iLookupDAO.loadProviderList(Integer
				.parseInt(objBusinessinfoVO.getVendorId()))); // Changes for for Admin Name Change -->
		
	
        	return dbBusinessinfoVO;			
	}

}
