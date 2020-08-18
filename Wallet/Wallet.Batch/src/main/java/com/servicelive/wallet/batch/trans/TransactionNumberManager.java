package com.servicelive.wallet.batch.trans;

// TODO: Auto-generated Javadoc
/**
 * The Class TransactionNumberManager.
 */
public final class TransactionNumberManager {

	/** The _transaction number manager. */
	private static TransactionNumberManager _transactionNumberManager;

	/** The register number. */
	private static int registerNumber = 1;

	/** The tran number. */
	private static int tranNumber = 1;

	/**
	 * Gets the single instance of TransactionNumberManager.
	 * 
	 * @return single instance of TransactionNumberManager
	 */
	public static TransactionNumberManager getInstance() {

		if (_transactionNumberManager == null) {
			_transactionNumberManager = new TransactionNumberManager();
		}
		return _transactionNumberManager;
	}

	/**
	 * Instantiates a new transaction number manager.
	 */
	private TransactionNumberManager() {

	}

	/**
	 * Gets the register number.
	 * 
	 * @return the register number
	 */
	public int getRegisterNumber() {

		if (tranNumber > 9999) {
			registerNumber++;
			tranNumber = 0;
		}
		if (registerNumber > 9999) {
			registerNumber = 1;
			tranNumber = 0;
		}
		return registerNumber;

	}

	/**
	 * Gets the tran number.
	 * 
	 * @return the tran number
	 */
	public int getTranNumber() {

		if (tranNumber > 9999) {
			tranNumber = 0;
		}
		tranNumber++;
		return tranNumber;

	}
}
