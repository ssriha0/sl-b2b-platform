package com.newco.marketplace.business.iBusiness.provider;

import java.util.List;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.dto.vo.provider.VendorCredentialsLookupVO;
import com.newco.marketplace.dto.vo.provider.VendorCredentialsVO;

public interface IVendorCredentialBO {
	
	public List<VendorCredentialsLookupVO> getCatListByTypeId(VendorCredentialsLookupVO teamCredentialsLookupVO) throws BusinessServiceException;
	public List<VendorCredentialsLookupVO> getTypeList() throws BusinessServiceException;
	public boolean uploadLMSCreadentials(String fileName) throws BusinessServiceException;
	public VendorCredentialsVO insertCredentials(VendorCredentialsVO vo) throws BusinessServiceException;
	public Integer checkVendorCredExists(VendorCredentialsVO vendorCredentialsVO) throws BusinessServiceException;
	public void updateCredentials(VendorCredentialsVO vo) throws BusinessServiceException;
	public List<VendorCredentialsLookupVO> getCatListByType(VendorCredentialsLookupVO vendorCredentialsLookupVO) throws BusinessServiceException;
	public VendorCredentialsLookupVO getVendorCredLookup(String credDesc) throws BusinessServiceException;
}
