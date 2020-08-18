package com.newco.marketplace.persistence.daoImpl.downloadsignedcopy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.downloadsignedcopy.IDownloadSignedCopyDao;
import com.newco.marketplace.vo.downloadsignedcopy.SignedCopyVO;
import com.sears.os.dao.impl.ABaseImplDao;

public class DownloadSignedCopyDaoImpl extends ABaseImplDao implements IDownloadSignedCopyDao {
    private static Logger logger = Logger.getLogger(DownloadSignedCopyDaoImpl.class);
    
   
	/**@Description: Method will retrieve document related 
	 * informations for a list of service orders
	 * @param soIdList
	 * @return Map
	 * @throws DataServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<SignedCopyVO> getSignedCopyForSo(List<String> soIdList,String title) throws DataServiceException {
		List<SignedCopyVO> resultList = null;
		Map  parameterMap = new HashMap();
		parameterMap.put("soId", soIdList);
		parameterMap.put("title",title);
		    logger.info("Size of soId List is :"+ soIdList.size());
		try{
			resultList = queryForList("downloadSignedCopyForSoList.query", parameterMap);
			logger.info("Size of signedCopy VO List is :"+ resultList.size());
		}catch (Exception e) {
			logger.error("Exception in downloading signed Copy for the service order: "+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return resultList;
	}
	
	//R12_1
	//SL-20420
	/**@Description: Method will retrieve all so documents related 
	 * informations for a list of service orders
	 * @param soIdList
	 * @return Map
	 * @throws DataServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<SignedCopyVO> getDocumentsForSo(String soId) throws DataServiceException {
		List<SignedCopyVO> resultList = null;
		try{
			resultList = queryForList("downloadSODocumentsForSoList.query", soId);
			logger.info("Size of downloadSODocumentsForSoList VO List is :"+ resultList.size());
		}catch (Exception e) {
			logger.error("Exception in downloading SO documents for the service order: "+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return resultList;
	}
	
	//R12_1
	//SL-20420
	/**@Description: Method will retrieve all so documents related 
	 * informations for a list of service orders (Buyers)
	 * @param soIdList
	 * @return Map
	 * @throws DataServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<SignedCopyVO> getBuyerDocumentsForSo(String soId) throws DataServiceException {
		List<SignedCopyVO> resultList = null;
		try{
			resultList = queryForList("downloadSODocumentsBuyerForSoList.query", soId);
			logger.info("Size of downloadSODocumentsBuyerForSoList VO List is :"+ resultList.size());
		}catch (Exception e) {
			logger.error("Exception in downloading SO documents(Buyers) for the service order: "+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return resultList;
	}
	
	//R12_1
	//SL-20420
	/**@Description: Method will retrieve all so documents related 
	 * informations for a list of service orders (Providers)
	 * @param soIdList
	 * @return Map
	 * @throws DataServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<SignedCopyVO> getProviderDocumentsForSo(String soId) throws DataServiceException {
		List<SignedCopyVO> resultList = null;
		try{
			resultList = queryForList("downloadSODocumentsProviderForSoList.query", soId);
			logger.info("Size of downloadSODocumentsProviderForSoList VO List is :"+ resultList.size());
		}catch (Exception e) {
			logger.error("Exception in downloading SO documents(Providers) for the service order: "+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return resultList;
	}

	/**@Description:Method to check the existence of buyer 
	 * @param buyerId
	 * @return boolean
	 * @throws DataServiceException
	 */
	public boolean validateBuyerId(Integer buyerId) throws DataServiceException {
		Integer result =null;
		boolean isValid = false;
		try{
			result = (Integer) queryForObject("downloadSignedCopy.validateBuyerId", buyerId);
		    if(null!= result){
		    	isValid =true;
		    }
		}catch (Exception e) {
			logger.error("Exception in validating buyerId: "+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return isValid;
	}
	/**@Description:Method to check the existence of service order.
	 * @param  soId
	 * @return boolean
	 * @throws DataServiceException
	 */
	public boolean validateServiceOrder(String soId)throws DataServiceException {
		String result =null;
		boolean isValid = false;
		try{
			result = (String) queryForObject("downloadSignedCopy.validateSoId", soId);
		    if(null!= result){
		    	isValid =true;
		    }
		}catch (Exception e) {
			logger.error("Exception in validating service order : "+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return isValid;
	}
	/**@Description:Method to validate so status
	 * @param  soId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public boolean validateSoStatus(String soId) throws DataServiceException {
		Integer result =null;
		boolean isValid = false;
		try{
			result = (Integer) queryForObject("downloadSignedCopy.fetchSOStatus", soId);
			//TO DO: Validate wf status
		    if(null!= result){
		    	isValid =true;
		    }
		}catch (Exception e) {
			logger.error("Exception in validating service order status: "+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return isValid;
	}
	/**@Description:Method to validate so association with buyer.
	 * @param  soId
	 * @param  buyerId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public boolean validateSoIdAssociation(String soId, Integer buyerId)throws DataServiceException {
		Integer result =null;
		boolean isValid = false;
		Map  parameterMap = new HashMap();
		parameterMap.put("soId", soId);
		parameterMap.put("buyerId",buyerId);
		try{
			result = (Integer) queryForObject("downloadSignedCopy.validateSoAssociation", parameterMap);
		    if(null!= result){
		    	isValid =true;
		    }
		}catch (Exception e) {
			logger.error("Exception in validating buyerId: "+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return isValid;
	}

}
