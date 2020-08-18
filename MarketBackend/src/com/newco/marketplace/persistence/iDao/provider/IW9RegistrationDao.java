/**
 *
 */
package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;
import com.newco.marketplace.vo.provider.W9RegistrationVO;

/**
 * @author hoza
 *
 */
public interface IW9RegistrationDao {
	public Boolean isW9Exists(Integer vendorId) throws Exception;
	public W9RegistrationVO get(Integer vendorId) throws Exception;
	public List<W9RegistrationVO> getW9History(Integer vendorId) throws Exception;
	public W9RegistrationVO getForResource(Integer resourceId) throws Exception;
	public W9RegistrationVO getPrefillForResource(Integer resourceId) throws Exception;
	public W9RegistrationVO getPrefill(Integer vendorId) throws Exception;
	public Boolean isW9ExistsForResource(Integer resourceId) throws Exception;
	public W9RegistrationVO save(W9RegistrationVO input) throws Exception;
	public W9RegistrationVO  insert (W9RegistrationVO input) throws Exception;
	public W9RegistrationVO  update (W9RegistrationVO input) throws Exception;
	public List<W9RegistrationVO> getAll() throws Exception;
	public Boolean isAvailableWithSSNInd(Integer vendorId) throws Exception;
	public Double getCompleteSOAmount(Integer vendorId) throws Exception;
	public Double getProviderThreshold() throws Exception;
	/* 
	 * This function is to fetch the ein number for the corresponding vendor.
	 * @param Integer
	 * @return String
	 */
	public String getEinNumber(Integer vendorId) throws Exception;
	/* 
	 * This function is to determine that SSN information was provided but date of birth was not provided
	 * @param Integer
	 * @return Boolean
	 */
	public Boolean isDobNotAvailableWithSSN(Integer vendorId) throws Exception;
	public String getOrginalEin(Integer vendorId)throws Exception;
}
