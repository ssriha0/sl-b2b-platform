package com.newco.marketplace.business.businessImpl.downloadsignedcopy;

import java.util.List;

import org.apache.log4j.Logger;
import com.servicelive.common.exception.DataNotFoundException;
import com.newco.marketplace.business.iBusiness.downloadsignedcopy.IDownloadSignedCopyBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.downloadsignedcopy.IDownloadSignedCopyDao;
import com.newco.marketplace.vo.downloadsignedcopy.SignedCopyVO;
import com.servicelive.common.properties.IApplicationProperties;

/**
 * @author Infosys
 *
 */
public class DownloadSignedCopyBOImpl implements  IDownloadSignedCopyBO {
	private static Logger logger = Logger.getLogger(DownloadSignedCopyBOImpl.class);
	private IDownloadSignedCopyDao downloadsignedCopyDao;
	private IApplicationProperties applicationProperties;
	
	/**@Description: Method will retrieve document related informations
	 * @param  soIdList
	 * @return Map
	 * @throws BusinessServiceException
	 */
	public List<SignedCopyVO> getSignedCopyForSo(List<String> soIdList,String title) throws BusinessServiceException {
		 List<SignedCopyVO> resultList = null;
		try{
			resultList = downloadsignedCopyDao.getSignedCopyForSo(soIdList,title);
		}catch (Exception e) {
			logger.error("Exception in retrieving signed Copy Document for list of service orders: "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return resultList;
	}
	
	
	//R12_1
	//SL-20420
	/**@Description: Method will retrieve all so documents related informations
	 * @param  soIdList
	 * @return Map
	 * @throws BusinessServiceException
	 */
	public List<SignedCopyVO> getDocumentsForSo(String soId) throws BusinessServiceException {
		 List<SignedCopyVO> resultList = null;
		try{
			resultList = downloadsignedCopyDao.getDocumentsForSo(soId);
		}catch (Exception e) {
			logger.error("Exception in retrieving all SO Documents for list of service orders: "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return resultList;
	}
	
	
	//R12_1
	//SL-20420
	/**@Description: Method will retrieve all so documents related informations for buyers
	 * @param  soIdList
	 * @return Map
	 * @throws BusinessServiceException
	 */
	public List<SignedCopyVO> getBuyerDocumentsForSo(String soId) throws BusinessServiceException {
		 List<SignedCopyVO> resultList = null;
		try{
			resultList = downloadsignedCopyDao.getBuyerDocumentsForSo(soId);
		}catch (Exception e) {
			logger.error("Exception in retrieving all SO Documents(Buyer) for list of service orders: "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return resultList;
	}	

	//R12_1
	//SL-20420
	/**@Description: Method will retrieve all so documents related informations for providers
	 * @param  soIdList
	 * @return Map
	 * @throws BusinessServiceException
	 */
	public List<SignedCopyVO> getProviderDocumentsForSo(String soId) throws BusinessServiceException {
		 List<SignedCopyVO> resultList = null;
		try{
			resultList = downloadsignedCopyDao.getProviderDocumentsForSo(soId);
		}catch (Exception e) {
			logger.error("Exception in retrieving all SO Documents(Provider) for list of service orders: "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return resultList;
	}	
	
	
	
	
	/**@Description:Method to check the existence of buyer 
	 * @param  buyerId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public boolean validateBuyer(Integer buyerId)throws BusinessServiceException {
		boolean result;
		try{
			result = downloadsignedCopyDao.validateBuyerId(buyerId);
		}catch (DataServiceException e) {
			logger.error("Exception in validating buyerId: "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return result;
	}
	
	/**@Description:Method to check the existence of service order.
	 * @param  soId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public boolean validateServiceOrder(String soId)throws BusinessServiceException {
		boolean result;
		try{
			result = downloadsignedCopyDao.validateServiceOrder(soId);
		}catch (DataServiceException e) {
			logger.error("Exception in validating service order: "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return result;
	}
	/**@Description:Method to validate so status
	 * @param  soId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public boolean validateSoStatus(String soId)throws BusinessServiceException {
		boolean result;
		try{
			result = downloadsignedCopyDao.validateSoStatus(soId);
		}catch (DataServiceException e) {
			logger.error("Exception in validating so status: "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return result;
	}
	/**@Description:Method to validate so association with buyer.
	 * @param  soId
	 * @param  buyerId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public boolean validateSoIdAssociation(String soId, Integer buyerId)throws BusinessServiceException {
		boolean result;
		try{
			result = downloadsignedCopyDao.validateSoIdAssociation(soId,buyerId);
		}catch (DataServiceException e) {
			logger.error("Exception in validating so id association with buyer: "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return result;
	}
	public String getApplicationPropertyFromDB(String key) throws BusinessServiceException{
		String value ="";
		try{
			value =applicationProperties.getPropertyFromDB(key);
		}catch (DataNotFoundException e) {
			logger.error("Exception in fetching application properties for the key: "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return value;
		
	}
    public IDownloadSignedCopyDao getDownloadsignedCopyDao() {
		return downloadsignedCopyDao;
	}

	public void setDownloadsignedCopyDao(IDownloadSignedCopyDao downloadsignedCopyDao) {
		this.downloadsignedCopyDao = downloadsignedCopyDao;
	}
	public IApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}
	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
	

	
}
