package com.newco.marketplace.util.acctmgmt;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.businessImpl.acctmgmt.rules.EntryRule;
import com.newco.marketplace.business.businessImpl.acctmgmt.rules.IEntryRule;
import com.newco.marketplace.business.businessImpl.acctmgmt.rules.RuleCriteria;
import com.newco.marketplace.dto.vo.ledger.LedgerBusinessTransactionVO;
import com.newco.marketplace.dto.vo.ledger.TransactionEntryVO;
import com.newco.marketplace.dto.vo.ledger.TransactionRuleVO;
import com.newco.marketplace.dto.vo.ledger.TransactionVO;
import com.newco.marketplace.interfaces.FullfillmentConstants;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.persistence.iDao.lookup.LookupDao;


/**
 *  Description of the Class
 *
 *@author     dmill03
 *@created    August 15, 2007
 */
public abstract class TransactionHelper {

	private static final Logger logger = Logger.getLogger(TransactionHelper.class.getName());
	private static LookupDao lookupDao;
	/**
	 *  Description of the Method
	 *
	 *@param  entryRule  Description of the Parameter
	 *@return            Description of the Return Value
	 */
	public static TransactionVO buildTransaction(IEntryRule entryRule) {
		TransactionVO trans = new TransactionVO();

		// Create Common Entries
		TransactionEntryVO creditEntry = commonCreateCriteria(entryRule.getCreditRuleCriteria());
		TransactionEntryVO debitEntry = commonCreateCriteria(entryRule.getDebitRuleCriteria());

		// added to Transaction and return
		trans.addTransactionEntry(creditEntry);
		trans.addTransactionEntry(debitEntry);
		return trans;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  entryRules  Description of the Parameter
	 *@return             Description of the Return Value
	 */
/*	public static LedgerBusinessTransactionVO
			buildBusinessTransaction(ArrayList<IEntryRule> entryRules, Integer businessTransId) 
	{
		LedgerBusinessTransactionVO businessTrans = new LedgerBusinessTransactionVO();
		
		businessTrans.setBusinessTransId(businessTransId);
		businessTrans.setBusinessTransDesc("TEST");
		businessTrans.setBusinessTransType("TEST");
		
		for (int i = 0; i < entryRules.size(); i++) {
			logger.debug("TransactionHelper-->buildBusinessTransaction()-->Credit RuleId = "+entryRules.get(i).getCreditRuleCriteria().getLedgerEntryRuleId());
			logger.debug("TransactionHelper-->buildBusinessTransaction()-->Debit RuleId = "+entryRules.get(i).getDebitRuleCriteria().getLedgerEntryRuleId());
			TransactionVO theTransaction = new TransactionVO();
			Integer transId = null;
			Integer transEntry1 = null;
			Integer transEntry2 = null;
			try {
				 //transId = new TransactionGUID().getTransactionId();
				 //transEntry1 = new TransactionGUID().getTransactionId();
				 //transEntry2 = new TransactionGUID().getTransactionId();
				 lookupDao = (LookupDao)MPSpringLoaderPlugIn.getCtx().getBean("lookupDao");
				 transId = lookupDao.getNextIdentifier(FullfillmentConstants.LEDGER_ENTRY_ID);
				 transEntry1 = lookupDao.getNextIdentifier(FullfillmentConstants.LEDGER_TRANS_ID);
				 transEntry2 = lookupDao.getNextIdentifier(FullfillmentConstants.LEDGER_TRANS_ID);
				 if (logger.isInfoEnabled()) {
					 logger.info("TransId" + transId);
					 logger.info("TransEntry1" + transEntry1);
					 logger.info("TransEntry2" + transEntry2);
				 }
			} 
			catch (Exception e) {
				e.printStackTrace();
			}	
			theTransaction.setLedgerEntryId( transId );
			
			// Create Common Entries
			TransactionEntryVO creditEntry = commonCreateCriteria(entryRules.get(i).getCreditRuleCriteria());
				creditEntry.setLedgerEntryId(transId);
				creditEntry.setTransactionId(transEntry1);
			TransactionEntryVO debitEntry = commonCreateCriteria(entryRules.get(i).getDebitRuleCriteria());
				debitEntry.setLedgerEntryId(transId);
				debitEntry.setTransactionId(transEntry2);
			// added to Transaction and return
			theTransaction.addTransactionEntry(creditEntry);
			theTransaction.addTransactionEntry(debitEntry);
			
			theTransaction.setBusinessTransId(businessTransId);
			theTransaction.setLedgerEntryRuleId(entryRules.get(i).getCreditRuleCriteria().getLedgerEntryRuleId());
			theTransaction.setAffectsBalanceInd(entryRules.get(i).getCreditRuleCriteria().getAffectsBalanceInd());
			theTransaction.setAutoAchInd(entryRules.get(i).getCreditRuleCriteria().getAutoAchInd());
			businessTrans.add_transaction(theTransaction);
		}
		return businessTrans;
	}

*/
	/**
	 *  Description of the Method
	 *
	 *@param  theRule  Description of the Parameter
	 *@return          Description of the Return Value
	 */
	public static IEntryRule buildEntryRule(TransactionRuleVO theRule) {
		// Create the EntryRule
		// Notice we do not directly set the criteria in the EntryRule Object
		// this is handled during Object creation
		return new EntryRule(theRule);
	}

	/**
	 *  Description of the Method
	 *
	 *@param  commonRule  Description of the Parameter
	 *@return             Description of the Return Value
	 */
	private static TransactionEntryVO commonCreateCriteria(RuleCriteria commonRule) {
		TransactionEntryVO entry = new TransactionEntryVO();
		if(commonRule.isCredit()){
			entry.setEntryTypeId(LedgerConstants.ENTRY_TYPE_CREDIT);
		}
		else{
			entry.setEntryTypeId(LedgerConstants.ENTRY_TYPE_DEBIT);
		}
		entry.setTaccountNo(commonRule.getTransRuleTAcctNo());
		
		entry.setLedgerEntityTypeId(commonRule.getTransRuleEntityTypeId());
		entry.setTransactionTypeId(commonRule.getTransactionTypeId());
		//entry.set
		if (commonRule.isCredit()) {
			entry.setIsACreditTransaction(true);
		} 
		else{
			entry.setIsACreditTransaction(false);
		}
		return entry;
	}


	public static LookupDao getLookupDao() {
		return lookupDao;
	}


	public static void setLookupDao(LookupDao lookupDao) {
		TransactionHelper.lookupDao = lookupDao;
	}

}

