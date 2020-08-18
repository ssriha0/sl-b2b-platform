/**
 *
 */
package com.newco.marketplace.business.iBusiness.provider;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.vo.provider.W9RegistrationVO;

/**
 * @author hoza
 *
 */
public interface IW9RegistrationBO {
	public Boolean isW9Exists(Integer vendorId) throws Exception;
	public Boolean isW9ExistsForResource(Integer resourceId) throws Exception;
	public W9RegistrationVO get(Integer vendorId) throws Exception;
	public Map<Integer,List<W9RegistrationVO>> getW9History(Integer vendorId) throws Exception;
	public W9RegistrationVO getForResource(Integer resourceId) throws Exception;
	public W9RegistrationVO getPrefillForResource(Integer resourceId) throws Exception;
	public W9RegistrationVO getPrefill(Integer vendorId) throws Exception;
	public W9RegistrationVO save(W9RegistrationVO input) throws Exception;
	public List<W9RegistrationVO> getAll() throws Exception;
	public Boolean isAvailableWithSSNInd(Integer vendorId) throws Exception;
	public Double getCompleteSOAmount(Integer vendorId);
	public Double getProviderThreshold();
	/* 
	 * This function is for checking the ein  number of the vendor is a valid
	 * value or not
	 * @param Integer
	 * @return boolean
	 */
	public boolean isW9ExistWithValidValue(Integer vendorId) throws Exception;
	/* 
	 * This function is to determine that SSN information was provided but date of birth was not provided
	 * @param Integer
	 * @return Boolean
	 */
	
	public Boolean isDobNotAvailableWithSSN(Integer vendorId) throws Exception;
	
	public String getOrginalEin(Integer vendorId)throws Exception;
	
	
	
	
}
