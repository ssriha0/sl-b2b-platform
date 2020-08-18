/**
 * 
 */
package com.newco.marketplace.business.iBusiness.provider;

import java.util.List;

import com.newco.marketplace.dto.vo.provider.ProviderDocumentVO;
import com.newco.marketplace.dto.vo.survey.CustomerFeedbackVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.provider.PublicProfileVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.newco.marketplace.dto.vo.provider.VendorDocumentVO;
/**
 * @author hoza
 *
 */
public interface IProviderInfoPagesBO {
	public PublicProfileVO getProviderDetails(Integer resourceId) throws BusinessServiceException;
	public ProviderDocumentVO getProviderPrimaryPhoto(Integer resourceId) throws BusinessServiceException;
	public ProcessResponse uploadResourcePicture(ProviderDocumentVO providerdocument) throws BusinessServiceException;	 
	public List <CustomerFeedbackVO> getCustomerFeedbacks(int resourceId, int count) throws BusinessServiceException;
	public List <CustomerFeedbackVO> getCustomerFeedbacks(int resourceId, int pageSize, int pageNumber, String sortColumn, String order,
			java.util.Date startDate, java.util.Date endDate,Double maxRating,Double minRating) throws BusinessServiceException;
	public Integer getCustomerFeedbacksCount(int resourceId) throws BusinessServiceException;
	public PublicProfileVO getProviderFirmDetails(Integer vendorId)throws BusinessServiceException; 
    public VendorDocumentVO getFirmLogo(Integer vendorId)throws BusinessServiceException;
    public ProcessResponse uploadFirmLogo(VendorDocumentVO vendorDocument) throws BusinessServiceException;	    
    public List<String> getAllProvidersForExternalCalenderSync() throws BusinessServiceException; 
}
