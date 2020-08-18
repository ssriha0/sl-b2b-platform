/**
 * 
 */
package com.newco.marketplace.business.iBusiness.provider;

import java.util.List;
import java.util.Map;


import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.provider.PublicProfileVO;
import com.newco.marketplace.vo.provider.TeamCredentialsVO;


/**
 * @author LENOVO USER
 * 
 */
public interface IPublicProfileBO {

	
	public PublicProfileVO getPublicProfile(PublicProfileVO publicProfileVO) throws BusinessServiceException;
	public boolean checkVendorResource(String resourceId, String vendorId) throws BusinessServiceException;
	public List<TeamCredentialsVO> getCredentials(int resourceId);
	public Map<String,List> getCheckedSkillsTree(int resourceId);
}
