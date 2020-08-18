package com.newco.marketplace.util.acctmgmt;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.acctmgmt.rules.IEntryRule;
import com.newco.marketplace.dto.vo.ledger.LedgerBusinessTransactionVO;
import com.newco.marketplace.interfaces.LedgerConstants;

/**
 * $Revision: 1.9 $ $Author: glacy $ $Date: 2008/04/26 00:40:27 $
 */
public class TransactionFactory {
	
	private static final Logger logger = Logger.getLogger(TransactionFactory.class.getName());
	private static TransactionFactory _factory = new TransactionFactory();
	
	private TransactionFactory(){}
	
	public static TransactionFactory getInstance()
	{
		if(_factory == null){
			_factory = new TransactionFactory();
		}
		return _factory;
	}
	
/*
	public LedgerBusinessTransactionVO createTransactions( Integer orginator, Integer businessTransId, Integer fundingTypeId ) throws Exception	{
		logger.debug("TransactionFactory-->createTransactions()");
		if(orginator == null || businessTransId ==null){
			throw new Exception();
		}
		// Get all the transaction rules
		ArrayList<IEntryRule> eRules = getTransactionRules(orginator,businessTransId, fundingTypeId);
		// Build business transaction
		LedgerBusinessTransactionVO businessTran = TransactionHelper.buildBusinessTransaction(eRules,businessTransId );
		
		if(fundingTypeId != null && fundingTypeId.intValue() != 0){
			businessTran.setFundingTypeId(fundingTypeId);
		}
		else{
			//default to non-funded
			businessTran.setFundingTypeId(LedgerConstants.FUNDING_TYPE_NON_FUNDED);
		}
		return businessTran;
	}
*/	
	
	public ArrayList<IEntryRule> getTransactionRules(Integer orginator, int businessTransId, Integer fundingTypeId)
	{
		ArrayList <IEntryRule> _rules = null;
		try {
			_rules = AccountRulesEngineMatrix.getInstance().getRules(orginator, businessTransId, fundingTypeId);
			for(int i=0; i<_rules.size(); i++){
				IEntryRule rule = _rules.get(i);
				logger.debug("TransactionFactory-->getTransactionRules-->Credit LedgerEntryRuleId = "+rule.getCreditRuleCriteria().getLedgerEntryRuleId());
				logger.debug("TransactionFactory-->getTransactionRules-->Debit LedgerEntryRuleId = "+rule.getDebitRuleCriteria().getLedgerEntryRuleId());
			}
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return _rules;
	}
	
	
	
}
