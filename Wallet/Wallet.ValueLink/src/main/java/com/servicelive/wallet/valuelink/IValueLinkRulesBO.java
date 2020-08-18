package com.servicelive.wallet.valuelink;

import java.util.List;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkVO;

// TODO: Auto-generated Javadoc
/**
 * Interface IValueLinkRulesBO.
 */
public interface IValueLinkRulesBO {

	/**
	 * Enum AutoCreateAccountBehavior.
	 */
	public enum AutoCreateAccountBehavior {
		
		/** AutoCreateBuyer. */
		AutoCreateBuyer, 
 /** AutoCreateBuyerAndProvider. */
 AutoCreateBuyerAndProvider, 
 /** AutoCreateBuyerOnly. */
 AutoCreateBuyerOnly, 
 /** AutoCreateProvider. */
 AutoCreateProvider, 
 /** NoAutoCreate. */
 NoAutoCreate
	}
	
	/**
	 * createBusinessTransaction.
	 * 
	 * @param request 
	 * @param autoCreateBehavior 
	 * 
	 * @return List<ValueLinkEntryVO>
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public List<ValueLinkEntryVO> createBusinessTransaction(ValueLinkVO request, AutoCreateAccountBehavior autoCreateBehavior) throws SLBusinessServiceException;

}
