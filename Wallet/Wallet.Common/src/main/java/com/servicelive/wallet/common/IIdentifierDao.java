package com.servicelive.wallet.common;

// TODO: Auto-generated Javadoc
/**
 * The Interface IIdentifierDao.
 */
public interface IIdentifierDao {

	/** The Constant BALANCE_ENQUIRY_STAN. */
	public static final String BALANCE_ENQUIRY_STAN = "BAL_ENQ";

	/** The Constant IDENTIFIER_FULLFILLMENT_STAN_MAX. */
	public static final long IDENTIFIER_FULLFILLMENT_STAN_MAX = 999999;

	/** The Constant LEDGER_ENTRY_ID. */
	public static final String LEDGER_ENTRY_ID = "LEDGER_ENTRY_ID";

	/** The Constant LEDGER_TRANS_ID. */
	public static final String LEDGER_TRANS_ID = "LEDGER_TRANS_ID";

	/** The Constant LEDGER_TRANS_ID_ADD_VALUE. */
	public static final long LEDGER_TRANS_ID_ADD_VALUE = 100000000;

	/** The Constant STAN_ID. */
	public static final String STAN_ID = "STAN_ID";

	/**
	 * Gets the next identifier.
	 * 
	 * @param key 
	 * 
	 * @return the next identifier
	 */
	Long getNextIdentifier(String key);
}
