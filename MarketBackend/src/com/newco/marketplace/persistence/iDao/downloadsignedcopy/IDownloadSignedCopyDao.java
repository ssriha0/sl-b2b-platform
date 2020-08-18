package com.newco.marketplace.persistence.iDao.downloadsignedcopy;

import java.util.List;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.downloadsignedcopy.SignedCopyVO;

public interface IDownloadSignedCopyDao {

	/**@Description: Method will retrieve document related informations
	 * @param  soIdList
	 * @param title
	 * @return Map
	 * @throws DataServiceException
	 */
	public List<SignedCopyVO> getSignedCopyForSo(List<String> soIdList,String title) throws DataServiceException;
	
	/**@Description:Method to check the existence of buyer 
	 * @param  buyerId
	 * @return boolean
	 * @throws DataServiceException
	 */
	public boolean validateBuyerId(Integer buyerId)  throws DataServiceException;
	
	/**@Description:Method to check the existence of service order.
	 * @param  soId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public boolean validateServiceOrder(String soId) throws DataServiceException;
	
	/**@Description:Method to validate so status
	 * @param  soId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public boolean validateSoStatus(String soId)throws DataServiceException;
	
	/**@Description:Method to validate so association with buyer.
	 * @param  soId
	 * @param  buyerId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public boolean validateSoIdAssociation(String soId, Integer buyerId)throws DataServiceException;
	
	//R12_1
	//SL-20420
	/**@Description: Method will retrieve all so documents related 
	 * informations for a list of service orders
	 * @param soIdList
	 * @return Map
	 * @throws DataServiceException
	 */
	public List<SignedCopyVO> getDocumentsForSo(String soId) throws DataServiceException;
	
	
	//R12_1
	//SL-20420
	/**@Description: Method will retrieve all so documents related 
	 * informations for a list of service orders (Buyers)
	 * @param soIdList
	 * @return Map
	 * @throws DataServiceException
	 */
	public List<SignedCopyVO> getBuyerDocumentsForSo(String soId) throws DataServiceException;
	
	
	//R12_1
	//SL-20420
	/**@Description: Method will retrieve all so documents related 
	 * informations for a list of service orders (Providers)
	 * @param soIdList
	 * @return Map
	 * @throws DataServiceException
	 */
	public List<SignedCopyVO> getProviderDocumentsForSo(String soId) throws DataServiceException;

}
