/**
 * 
 */
package com.newco.marketplace.persistence.iDao.ledger;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * @author schavda
 *
 */
public interface ICreditCardDao {

	public CreditCardVO getCardDetails(CreditCardVO creditCardVO) throws DataServiceException;
	
	public CreditCardVO getCardDetailsAll(CreditCardVO creditCardVO) throws DataServiceException;
	
	public CreditCardVO getActiveCardsForBuyer(Integer buyerId) throws DataServiceException;
	
	public void saveCardDetails(CreditCardVO creditCardVO) throws DataServiceException;
	
	public boolean updateDeactivateCreditCardAccountInfo(CreditCardVO creditVO) throws DataServiceException;
	
	public void saveAuthorizationResponse(CreditCardVO creditCardVO) throws DataServiceException;
	
	public ArrayList<CreditCardVO> getCardsForEntity(CreditCardVO creditCardVO) throws DataServiceException;
	
	public LookupVO getCreditCardResponseDetails(String response) throws DataServiceException;
	
	//B2C - Start
	
	public List<CreditCardVO> getActiveCardListForBuyer(Integer buyerId) throws DataServiceException;
	
	public CreditCardVO getActiveDefaultCardForBuyer(Integer buyerId) throws DataServiceException;
	
	public boolean resetDefaultCreditCard(CreditCardVO creditVO) throws DataServiceException;
	
	public List<CreditCardVO> getAllActiveAndAuthorizedCards(Integer buyerId) throws DataServiceException;
	
	public List<CreditCardVO> getAllEnabledCards(Integer buyerId) throws DataServiceException;
	
	public CreditCardVO getCardDetailsByAccountId(CreditCardVO creditCardVO) throws DataServiceException;
	
	//B2C -End
	
	//SL 20853
	public String getEncryptFlag(String appKey)throws DataServiceException;
	
}
