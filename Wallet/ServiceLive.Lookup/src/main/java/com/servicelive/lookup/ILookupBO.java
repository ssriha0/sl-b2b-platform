package com.servicelive.lookup;

import com.servicelive.common.BusinessServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.vo.SLAccountVO;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.lookup.vo.AdminLookupVO;
import com.servicelive.lookup.vo.BuyerLookupVO;
import com.servicelive.lookup.vo.ProviderLookupVO;
import java.util.Map;
// TODO: Auto-generated Javadoc
/**
 * Interface ILookupBO.
 */
public interface ILookupBO {

//	/**
//	 * Account exists.
//	 * 
//	 * @param accountId 
//	 * 
//	 * @return true, if successful
//	 * 
//	 * @throws BusinessServiceException 
//	 */
//	boolean accountExists(Long accountId) throws BusinessServiceException;
	
	/**
 * lookupAdmin.
 * 
 * @return AdminLookupVO
 * 
 * @throws SLBusinessServiceException 
 */
	public AdminLookupVO lookupAdmin() throws SLBusinessServiceException;

	/**
	 * lookupBuyer.
	 * 
	 * @param buyerId 
	 * 
	 * @return BuyerLookupVO
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public BuyerLookupVO lookupBuyer(Long buyerId) throws SLBusinessServiceException;

	/**
	 * lookupCreditCard.
	 * 
	 * @param cardId 
	 * 
	 * @return CreditCardAccountVO
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public SLCreditCardVO lookupCreditCard(Long cardId) throws SLBusinessServiceException;

	/**
	 * lookupProvider.
	 * 
	 * @param providerId 
	 * 
	 * @return ProviderLookupVO
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public ProviderLookupVO lookupProvider(Long providerId) throws SLBusinessServiceException;
	/**
	 * lookupAccount.
	 * 
	 * @return SLAccountVO
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public SLAccountVO lookupAccount(Long accountId) throws SLBusinessServiceException;
	
	
	/**
	 * Description:  Get first active account, limit 1.
	 * @param entityId
	 * @return
	 * @throws BusinessServiceException
	 */
	public SLAccountVO getActiveAccountDetails(long entityId) throws BusinessServiceException;
	
	public Long getCreditcardAccountNoForNonAutofundedBuyer(long entityId)throws BusinessServiceException;
	


}
