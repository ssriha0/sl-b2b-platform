package com.newco.marketplace.persistence.daoImpl.ledger;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.ledger.ICreditCardDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;

public class CreditCardDaoImpl extends ABaseImplDao implements ICreditCardDao {

	private static final Logger logger = Logger.getLogger(CreditCardDaoImpl.class.getName());
	
	public CreditCardVO getCardDetails(CreditCardVO creditCardVO) throws DataServiceException {		
		try {
			creditCardVO = (CreditCardVO) queryForObject("creditCardDetails.query", creditCardVO);
        }
        catch (Exception ex) {
        	logger.info("[CreditCardDaoImpl.getCardDetails - Exception] " + StackTraceHelper.getStackTrace(ex));
            throw new DataServiceException("Error", ex);
        }
        
        return creditCardVO;
	}

	public CreditCardVO getCardDetailsByAccountId(CreditCardVO creditCardVO) throws DataServiceException {		
		try {
			creditCardVO = (CreditCardVO) queryForObject("creditCardDetailsByAccountId.query", creditCardVO);
        }
        catch (Exception ex) {
        	logger.info("[CreditCardDaoImpl.getCardDetailsByAccountId - Exception] " + StackTraceHelper.getStackTrace(ex));
            throw new DataServiceException("Error", ex);
        }
        
        return creditCardVO;
	}
	public CreditCardVO getCardDetailsAll(CreditCardVO creditCardVO) throws DataServiceException {		
		try {
			creditCardVO = (CreditCardVO) queryForObject("creditCardDetails_all.query", creditCardVO);
        }
        catch (Exception ex) {
        	logger.info("[CreditCardDaoImpl.getCardDetailsAll - Exception] " + StackTraceHelper.getStackTrace(ex));
            throw new DataServiceException("Error", ex);
        }
        
        return creditCardVO;
	}
	

	public void saveCardDetails(CreditCardVO creditCardVO) throws DataServiceException {
		try{			
			insert("creditCard.insert", creditCardVO);
		}
		catch (Exception ex) {
        	logger.info("[CreditCardDaoImpl.saveCardDetails - Exception] " + StackTraceHelper.getStackTrace(ex));
            throw new DataServiceException("Error", ex);
        }
		
	}
	
	public void saveAuthorizationResponse(CreditCardVO creditCardVO) throws DataServiceException{
		try{
			insert("creditCard.insertWSResponse", creditCardVO);
			
		}
		catch (Exception ex) {
        	logger.info("[CreditCardDaoImpl.saveCardDetails - Exception] " + StackTraceHelper.getStackTrace(ex));
            throw new DataServiceException("Error", ex);
        }
	}
	
	public ArrayList<CreditCardVO> getCardsForEntity(CreditCardVO creditCardVO) throws DataServiceException{
		ArrayList alCards = null;
		
		try {
			alCards = (ArrayList) queryForList("creditCardsForEntity.query", creditCardVO);
        }
        catch (Exception ex) {
        	logger.info("[CreditCardDaoImpl.getCardsForEntity - Exception] " + StackTraceHelper.getStackTrace(ex));
            throw new DataServiceException("Error", ex);
        }
        
        return alCards;
	}	
	
	public CreditCardVO getActiveCardsForBuyer(Integer buyerId) throws DataServiceException{
		CreditCardVO creditVO = null;
		ArrayList alCards = null;
		
		try {
			//creditVO = (CreditCardVO) queryForObject("creditCardsForBuyer.queryWithActiveInd", buyerId);
			alCards = (ArrayList) queryForList("creditCardsForBuyer.queryWithActiveInd", buyerId);
        }
        catch (Exception ex) {
        	logger.info("[CreditCardDaoImpl.getCardsForEntity - Exception] " + StackTraceHelper.getStackTrace(ex));
            throw new DataServiceException("Error", ex);
        }
        //KV Temporary fix on the launch night before the fina build!
        
        if(alCards != null && alCards.size() > 0) creditVO = (CreditCardVO)alCards.get(0);
        
        return creditVO;
	}
	
	//B2C API changes -- Start 
	
	public List<CreditCardVO> getActiveCardListForBuyer(Integer buyerId) throws DataServiceException{
		CreditCardVO creditVO = null;
		List<CreditCardVO> alCards = new ArrayList<CreditCardVO>();
		
		try {
			alCards = queryForList("creditCardsForBuyer.queryWithActiveInd", buyerId);
        }
        catch (Exception ex) {
        	logger.info("[CreditCardDaoImpl.getCardsForEntity - Exception] " + StackTraceHelper.getStackTrace(ex));
            throw new DataServiceException("Error", ex);
        }
        
        return alCards;
	}
	
	public CreditCardVO getActiveDefaultCardForBuyer(Integer buyerId) throws DataServiceException{
		CreditCardVO creditVO = null;
		List<CreditCardVO> alCards = new ArrayList<CreditCardVO>();
		
		try {
			alCards = queryForList("creditCardsForBuyer.queryWithActiveDefaultInd", buyerId);
        }
        catch (Exception ex) {
        	logger.info("[CreditCardDaoImpl.getCardsForEntity - Exception] " + StackTraceHelper.getStackTrace(ex));
            throw new DataServiceException("Error", ex);
        }
        
        if(alCards != null && alCards.size() > 0) creditVO = (CreditCardVO)alCards.get(0);
        
        return creditVO;
	}
	
	public List<CreditCardVO> getAllActiveAndAuthorizedCards(Integer buyerId) throws DataServiceException{
		List<CreditCardVO> alCards = new ArrayList<CreditCardVO>();
		
		try {
			alCards = queryForList("creditCardsForBuyer.queryAllActiveAndAuthorizedCards", buyerId);
        }
        catch (Exception ex) {
        	logger.info("[CreditCardDaoImpl.getCardsForEntity - Exception] " + StackTraceHelper.getStackTrace(ex));
            throw new DataServiceException("Error", ex);
        }
        
        return alCards;
	}
	
	public List<CreditCardVO> getAllEnabledCards(Integer buyerId) throws DataServiceException{
		List<CreditCardVO> alCards = new ArrayList<CreditCardVO>();
		
		try {
			alCards = queryForList("creditCardsForBuyer.queryActiveEnabledCards", buyerId);
        }
        catch (Exception ex) {
        	logger.info("[CreditCardDaoImpl.getCardsForEntity - Exception] " + StackTraceHelper.getStackTrace(ex));
            throw new DataServiceException("Error", ex);
        }
        
        return alCards;
	}

	
	public boolean resetDefaultCreditCard(CreditCardVO creditVO)
			throws DataServiceException {
		update("reset_defaultcard.update", creditVO);
		return true;
	}
	
	//B2C API changes -- End
	
	public boolean updateDeactivateCreditCardAccountInfo(CreditCardVO creditVO)
			throws DataServiceException {
		update("deactivate_creditcard.update", creditVO);
		return true;
		
	}
	
	public LookupVO getCreditCardResponseDetails(String response) throws DataServiceException
	{
		return (LookupVO)queryForObject("credit_card_response_code.query", response);
	}

	
	/**SL-20853 encryption flag fetch from applications_properties table
	 * @return
	 * @throws DataServiceException
	 */
	public String getEncryptFlag(String appKey)throws DataServiceException {
		try{
			return (String) queryForObject("get_encryption_flag.select", appKey);
		}
		catch (Exception ex) {
			throw new DataServiceException("Exception occurred in CreditCardDao.getEncryptFlag()", ex);
		}
	}
	
}
