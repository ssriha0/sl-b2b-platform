/**
 * 
 */
package com.newco.marketplace.persistence.iDao.document;

import java.util.List;

import com.newco.marketplace.dto.vo.provider.ProviderDocumentVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.dto.vo.provider.VendorDocumentVO;

/**
 * @author hoza
 *
 */
public interface IProviderProfileDocumentDao {
	
	public ProviderDocumentVO  getPrimaryProviderPicture(Integer resourceId) throws DataServiceException;
	public ProviderDocumentVO  uploadProviderPicture(ProviderDocumentVO vo) throws DataServiceException;
	public List<ProviderDocumentVO> getAllPicturesForProvider(Integer resourceId) throws DataServiceException;
	public int update(ProviderDocumentVO doc) throws DataServiceException;
	public int delete(ProviderDocumentVO doc) throws DataServiceException;
	public VendorDocumentVO  uploadFirmLogo(VendorDocumentVO vo) throws DataServiceException;
	public VendorDocumentVO  updateFirmLogo(VendorDocumentVO vo) throws DataServiceException;
	public VendorDocumentVO getLogoForFirm(Integer vendorId)throws DataServiceException;
	public int update(VendorDocumentVO doc) throws DataServiceException;
	public int delete(VendorDocumentVO doc) throws DataServiceException;
	

}
