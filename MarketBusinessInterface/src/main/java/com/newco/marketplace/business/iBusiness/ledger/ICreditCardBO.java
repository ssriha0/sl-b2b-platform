/**
 * 
 */
package com.newco.marketplace.business.iBusiness.ledger;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

/**
 * @author schavda
 *
 */
public interface ICreditCardBO {
	
	public CreditCardVO getCardDetails(CreditCardVO creditCardVO) throws BusinessServiceException;
	
	public Long saveCardDetails(CreditCardVO creditCardVO) throws BusinessServiceException;
	
	public CreditCardVO getActiveCreditCardDetails(Integer entityId) throws BusinessServiceException;
	
	public List<CreditCardVO> getActiveCardListForBuyer(Integer entityId) throws BusinessServiceException;

	public CreditCardVO authorizeCardTransaction(CreditCardVO creditCardVO, String soId, String roleType) throws BusinessServiceException;
	
	public ArrayList<CreditCardVO> getCardsForEntity(CreditCardVO creditCardVO) throws BusinessServiceException;

	public CreditCardVO saveAndAuthorizeCardTransaction(CreditCardVO creditCardVO, String soId, String roleType) throws BusinessServiceException;
	
	public boolean updateDeactivateCreditCardAccountInfo(CreditCardVO creditCardVO) throws BusinessServiceException;
	
	public Long saveAsDefaultCard(CreditCardVO creditCardVO) throws BusinessServiceException;
	
	public void commit();
}
