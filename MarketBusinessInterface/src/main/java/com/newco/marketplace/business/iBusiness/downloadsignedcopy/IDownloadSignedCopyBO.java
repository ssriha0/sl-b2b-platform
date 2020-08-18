package com.newco.marketplace.business.iBusiness.downloadsignedcopy;

import java.util.List;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.downloadsignedcopy.SignedCopyVO;


public interface IDownloadSignedCopyBO {

	/**@Description: Method will retrieve document related informations
	 * @param  soIdList
	 * @return SignedCopyVO
	 * @throws BusinessServiceException
	 */
	public List<SignedCopyVO>  getSignedCopyForSo(List<String> soIdList,String title) throws BusinessServiceException;

	/**@Description:Method to check the existence of buyer 
	 * @param  buyerId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public boolean validateBuyer(Integer buyerId)throws BusinessServiceException;

	/**@Description:Method to check the existence of service order.
	 * @param  soId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public boolean validateServiceOrder(String soId)throws BusinessServiceException;

	/**@Description:Method to validate so status
	 * @param  soId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public boolean validateSoStatus(String soId)throws BusinessServiceException;

	/**@Description:Method to validate so association with buyer.
	 * @param  soId
	 * @param  buyerId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public boolean validateSoIdAssociation(String soId, Integer buyerId) throws BusinessServiceException;

	/**@Description: Gets the property value directly from the database.
	 * @param key
	 * @return value
	 * @throws BusinessServiceException
	 */
	public String getApplicationPropertyFromDB(String key) throws BusinessServiceException;
	
	
	
	//R12_1
	//SL-20420
	/**@Description: Method will retrieve all so documents related informations
		 * @param  soIdList
		 * @return Map
		 * @throws BusinessServiceException
	*/
	public List<SignedCopyVO> getDocumentsForSo(String soId) throws BusinessServiceException;
	
	//R12_1
	//SL-20420
	/**@Description: Method will retrieve all so documents related informations for buyers
		 * @param  soIdList
		 * @return Map
		 * @throws BusinessServiceException
	 */
	public List<SignedCopyVO> getBuyerDocumentsForSo(String soId) throws BusinessServiceException;
	
	
	//R12_1
	//SL-20420
	/**@Description: Method will retrieve all so documents related informations for providers
	 * @param  soIdList
	 * @return Map
	 * @throws BusinessServiceException
	 */
	public List<SignedCopyVO> getProviderDocumentsForSo(String soId) throws BusinessServiceException;
	
}
