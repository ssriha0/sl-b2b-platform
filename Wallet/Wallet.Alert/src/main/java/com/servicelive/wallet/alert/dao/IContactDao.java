package com.servicelive.wallet.alert.dao;

import java.util.List;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.alert.vo.ContactVO;

// TODO: Auto-generated Javadoc
/**
 * Interface IContactDao.
 */
public interface IContactDao {

	/**
	 * getBuyerInformation.
	 * 
	 * @param buyerId 
	 * 
	 * @return List<ContactVO>
	 * 
	 * @throws DataServiceException 
	 */
	public List<ContactVO> getBuyerInformation(long buyerId) throws DataServiceException;;

	/**
	 * getProviderInformation.
	 * 
	 * @param providerId 
	 * 
	 * @return List<ContactVO>
	 * 
	 * @throws DataServiceException 
	 */
	public List<ContactVO> getProviderInformation(long providerId) throws DataServiceException;
}
