/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.provider;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.provider.ProviderDocumentVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.document.IProviderProfileDocumentDao;
import com.sears.os.dao.impl.ABaseImplDao;
import com.newco.marketplace.dto.vo.provider.VendorDocumentVO;

/**
 * @author hoza
 *
 */
public class ProviderProfileDocumentDaoImpl extends ABaseImplDao implements
		IProviderProfileDocumentDao {

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.document.IProviderProfileDocumentDao#getPrimaryProviderPicture(java.lang.Integer)
	 */
	public ProviderDocumentVO getPrimaryProviderPicture(Integer resourceId) throws DataServiceException {
		ProviderDocumentVO doc = new ProviderDocumentVO();
			try
			{
				List<ProviderDocumentVO> photos  = (List<ProviderDocumentVO>)  queryForList("document.query_provider_primary_photo", resourceId);
				if(photos != null && photos.size() > 0) {
					doc = photos.get(0);
				}
				
			}catch(Exception exception)
			{
				
				throw new DataServiceException(
						"General Exception @getPrimaryProviderPicture due to "
								+ exception.getMessage());
			}
			
			return doc;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.document.IProviderProfileDocumentDao#uploadProviderPicture(com.newco.marketplace.dto.vo.provider.ProviderDocumentVO)
	 */
	public ProviderDocumentVO uploadProviderPicture(ProviderDocumentVO vo)
			throws DataServiceException {
		try
		{
			 getSqlMapClient().insert("document.insert_provider_photo", vo);
			 return vo;
			
			
		}catch(Exception exception)
		{
			
			throw new DataServiceException(
					"General Exception @getPrimaryProviderPicture due to "
							+ exception.getMessage());
		}
		
	
	}
   
   	public VendorDocumentVO  uploadFirmLogo(VendorDocumentVO vo) throws DataServiceException{
		try
		{ 
			 getSqlMapClient().insert("document.insert_firm_logo", vo);
			 return vo;
			
			
		}catch(Exception exception)
		{
			
			throw new DataServiceException(
					"General Exception @uploadFirmLogo due to "
							+ exception.getMessage());
		}
		
	
	}
	
	public VendorDocumentVO  updateFirmLogo(VendorDocumentVO vo) throws DataServiceException{
		try
		{ 
			 getSqlMapClient().update("document.update_firm_logo", vo);
			 return vo;
			
			
		}catch(Exception exception)
		{
			
			throw new DataServiceException(
					"General Exception @updateFirmLogo due to "
							+ exception.getMessage());
		}
		
	
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.document.IProviderProfileDocumentDao#delete(com.newco.marketplace.dto.vo.provider.ProviderDocumentVO)
	 */
	public int delete(ProviderDocumentVO doc) throws DataServiceException {
		int i = getSqlMapClientTemplate().delete("document.delete_prvoider_photo", doc);
		return i;
	}


   public int delete(VendorDocumentVO doc) throws DataServiceException {
		int i = getSqlMapClientTemplate().delete("document.delete_vendor_logo", doc);
		return i;
	}


	public VendorDocumentVO getLogoForFirm(Integer vendorId)
			throws DataServiceException {
		VendorDocumentVO logo = new VendorDocumentVO();
		try
		{
			logo  = (VendorDocumentVO)  queryForObject("document.query_firm_logo", vendorId);
						
		}catch(Exception exception)
		{
			
			throw new DataServiceException(
					"General Exception @getPrimaryProviderPicture due to "
							+ exception.getMessage());
		}
		return logo;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.document.IProviderProfileDocumentDao#getAllPicturesForProvider(java.lang.Integer)
	 */
	public List<ProviderDocumentVO> getAllPicturesForProvider(Integer resourceId)
			throws DataServiceException {
		List<ProviderDocumentVO> photos = new ArrayList<ProviderDocumentVO>();
		try
		{
			 photos  = (List<ProviderDocumentVO>)  queryForList("document.query_provider_all_photo", resourceId);
			
			
		}catch(Exception exception)
		{
			
			throw new DataServiceException(
					"General Exception @getPrimaryProviderPicture due to "
							+ exception.getMessage());
		}
		return photos;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.document.IProviderProfileDocumentDao#update(com.newco.marketplace.dto.vo.provider.ProviderDocumentVO)
	 */
	public int update(ProviderDocumentVO doc) throws DataServiceException {
		int result = 0;
		try {
			result  = getSqlMapClient().update("document.update_prvoider_photo", doc);

		} catch(Exception exception)
		{
			
			throw new DataServiceException(
					"General Exception @getPrimaryProviderPicture due to "
							+ exception.getMessage());
		}
		return result;
	}
	
	public int update(VendorDocumentVO doc) throws DataServiceException{
		int result = 0;
		try {
			result  = getSqlMapClient().update("document.update_firm_logo", doc);

		} catch(Exception exception)
		{
			
			throw new DataServiceException(
					"General Exception @updateFirmLogo due to "
							+ exception.getMessage());
		}
		return result;
	} 
	

}
