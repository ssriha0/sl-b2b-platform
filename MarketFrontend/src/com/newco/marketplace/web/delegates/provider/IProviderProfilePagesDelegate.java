/**
 * 
 */
package com.newco.marketplace.web.delegates.provider;

import com.newco.marketplace.dto.vo.provider.ProviderDocumentVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.web.dto.provider.ProviderInfoPagesDto;
import com.newco.marketplace.web.dto.provider.ResourceSkillAssignDto;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.newco.marketplace.dto.vo.provider.VendorDocumentVO;

/**
 * @author hoza
 *
 */
public interface IProviderProfilePagesDelegate {
	/**
	 * The center piece of this delegate is the resouceId. We gathter rest of the company information from resource Id, as there is a one to onew relation ship between resource Id and vendor Id.
	 * A vendor can  have many resources. but resource can have only one verndor id
	 * @param resourceId
	 * @return
	 * @throws DelegateException
	 */
	public ProviderInfoPagesDto getPublicProfile(int resourceId) throws DelegateException;
	public ResourceSkillAssignDto getSkills(int resourceId, int rootNodeId)throws DelegateException;
	public ProviderDocumentVO getPrimaryPicture(int resourceId) throws DelegateException;
	public ProcessResponse uploadResourcePicture(ProviderDocumentVO providedocument) throws DelegateException;
	public ProviderInfoPagesDto getPublicFirmProfile(int vendorId) throws DelegateException; 
	public Boolean isSPFirmNetworkTabViewable(int buyerId, int providerFirmId) throws DelegateException;
	public Boolean isProviderNetworkTabViewable(int buyerId, int providerId) throws DelegateException;
	public VendorDocumentVO getFirmLogo(int vendorId)throws DelegateException;
	public ProcessResponse uploadFirmLogo(VendorDocumentVO vendordocument) throws DelegateException;


}
