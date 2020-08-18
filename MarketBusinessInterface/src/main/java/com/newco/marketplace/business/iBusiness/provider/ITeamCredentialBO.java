/**
 * 
 */
package com.newco.marketplace.business.iBusiness.provider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.provider.TeamCredentialsLookupVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.provider.TeamCredentialsVO;
import com.newco.marketplace.vo.provider.VendorResource;

public interface ITeamCredentialBO {
	public void updateResource(VendorResource vendorResource) throws BusinessServiceException;
	public VendorResource queryResourceById(VendorResource vendorResource) throws BusinessServiceException;
	public void updateCredentials(TeamCredentialsVO vo)	throws BusinessServiceException;
	public TeamCredentialsVO insertCredentials(TeamCredentialsVO vo) throws BusinessServiceException;
	public TeamCredentialsVO removeCredentials(TeamCredentialsVO vo) throws BusinessServiceException;
	public TeamCredentialsVO getCredById(TeamCredentialsVO vo)throws BusinessServiceException;
	public List<TeamCredentialsVO> getCredListByResourceId(TeamCredentialsVO teamCredentialsVO)	throws BusinessServiceException;
	public List<TeamCredentialsLookupVO> getCatListByTypeId(TeamCredentialsLookupVO teamCredentialsLookupVO) throws BusinessServiceException;
	public List<TeamCredentialsLookupVO> getTypeList() throws BusinessServiceException;
	public List<LookupVO> getStateList()throws BusinessServiceException;
	public void removeDocument(TeamCredentialsVO teamVO) throws BusinessServiceException, FileNotFoundException,
															IOException;
	public TeamCredentialsVO viewDocument(TeamCredentialsVO teamVO) throws BusinessServiceException, FileNotFoundException,
															IOException;
	public TeamCredentialsVO getResourceName(TeamCredentialsVO vo);
	public Integer checkTeamCredExists(TeamCredentialsVO teamCredentialsVO) throws BusinessServiceException;
	public List<TeamCredentialsLookupVO> getCatListByType(TeamCredentialsLookupVO teamCredentialsLookupVO) throws BusinessServiceException;
	public TeamCredentialsLookupVO getTeamCredLookup(String credDesc) throws BusinessServiceException;
}
