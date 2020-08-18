package com.newco.marketplace.business.businessImpl.acctmgmt.rules;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.ledger.TransactionRuleVO;

/**
 *  Description of the Class
 *
 *@author     dmill03
 *@created    August 15, 2007
 */
public class EntryRule extends ABaseEntryRule implements IEntryRule {

	private RuleCriteria _debitRule;
	private RuleCriteria _creditRule;
	private static final Logger logger = Logger.getLogger(EntryRule.class.getName());
	/**
	 *  Constructor for the EntryRule object
	 */
	public EntryRule() { }


	/**
	 *  Constructor for the EntryRule object
	 *
	 *@param  aRule  Description of the Parameter
	 */
	public EntryRule(TransactionRuleVO aRule) {
		loadTransactionRule(aRule);
	}





	public RuleCriteria getDebitRuleCriteria() {
		return _debitRule;
	}


	private void setDebitRuleCriteria(RuleCriteria rule) {
		_debitRule = rule;
	}


	public RuleCriteria getCreditRuleCriteria() {
		return _creditRule;
	}


	private void setCreditRuleCriteria(RuleCriteria rule) {
		_creditRule = rule;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  tranRule  Description of the Parameter
	 *@param  type      Description of the Parameter
	 *@return           Description of the Return Value
	 */
	private RuleCriteria buildCriteria(TransactionRuleVO tranRule, boolean type) {
		//Determine the Rule Criteria
		RuleCriteria rCri = new RuleCriteria();
		//set common rules criteria for every transaction
		rCri.setBusTransId(tranRule.getBusTransId());
		rCri.setBusTransType(tranRule.getBusTransType());
		rCri.setBusTransDescr(tranRule.getBusTransDescr());
		
		//rCri.setEntityDefaultEntityId(tranRule.getEntityDefaultEntityId());
		rCri.setLedgerEntryRuleId(tranRule.getLedgerEntryRuleId());
		logger.debug("EntryRule-->buildCriteria()-->LedgerEntryRuleId = "+rCri.getLedgerEntryRuleId());
		rCri.setVirtualLedgerId(tranRule.getLedgerEntityTypeId());
		rCri.setTransactionTypeId(tranRule.getTransactionTypeId());
		rCri.setAffectsBalanceInd(tranRule.getAffectsBalanceInd());
		//	rCri.setTransRuleType(tranRule.)
		rCri.setAutoAchInd(tranRule.getAutoAchInd());
		
		if (type) {
			rCri.setTransRuleTAcctNo(tranRule.getTransRuleCreditTAcctNo());
			rCri.setTransRuleEntityTypeId(tranRule.getTransRuleCreditEntityTypeId());
			rCri.setIsACreditAcct(true);
		} else {
			rCri.setTransRuleTAcctNo(tranRule.getTransRuleDebitTAcctNo());
			rCri.setTransRuleEntityTypeId(tranRule.getTransRuleDebitEntityTypeId());
			rCri.setIsACreditAcct(false);
		}
		return rCri;
	}

	/**
	 *  Description of the Method
	 *
	 *@param  theRule  Description of the Parameter
	 */
	private void loadTransactionRule(final TransactionRuleVO theRule) {
//		 Determine the Rule Criteria
		RuleCriteria creditCriteria = buildCriteria(theRule, true);
		RuleCriteria debitCriteria = buildCriteria(theRule, false);
		setCreditRuleCriteria(creditCriteria);
		setDebitRuleCriteria(debitCriteria);
		logger.debug("EntryRule-->loadTransactionRule()-->Credit LedgerEntryRuleId = "+creditCriteria.getLedgerEntryRuleId());
		logger.debug("EntryRule-->loadTransactionRule()-->Debit LedgerEntryRuleId = "+debitCriteria.getLedgerEntryRuleId());
	}
	
}

