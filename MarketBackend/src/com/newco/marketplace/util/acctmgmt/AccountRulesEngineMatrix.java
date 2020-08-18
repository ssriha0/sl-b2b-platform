package com.newco.marketplace.util.acctmgmt;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.businessImpl.acctmgmt.rules.IEntryRule;
import com.newco.marketplace.dto.vo.ledger.TransactionRuleVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.persistence.daoImpl.ledger.TransactionDaoImpl;
import com.newco.marketplace.persistence.iDao.ledger.TransactionDao;

/**
 *  Description of the Class
 *
 * $Revision: 1.13 $ $Author: glacy $ $Date: 2008/04/26 00:40:27 $
 */
public class AccountRulesEngineMatrix {

	private static final Logger logger = Logger.getLogger(AccountRulesEngineMatrix.class.getName());
	private static AccountRulesEngineMatrix _rulesMax = new AccountRulesEngineMatrix();

	private TransactionDao transactionDao = null;

	/**
	 *  Constructor for the AccountRulesEngineMatrix object
	 */
	private AccountRulesEngineMatrix() { }


	/**
	 *  Gets the instance attribute of the AccountRulesEngineMatrix class
	 *
	 *@return    The instance value
	 */
	public static AccountRulesEngineMatrix getInstance() {
		if (_rulesMax == null) {
			_rulesMax = new AccountRulesEngineMatrix();
		}
		return _rulesMax;
	}


	/**
	 *  Gets the rule attribute of the AccountRulesEngineMatrix object
	 *
	 *@param  transType      Description of the Parameter
	 *@param  ledgerType     Description of the Parameter
	 *@return                The rule value
	 *@exception  Exception  Description of the Exception
	 */
	private IEntryRule
			getRule(TransactionRuleVO rule) throws Exception {
		return TransactionHelper.buildEntryRule(rule);
	}


	/**
	 *  Gets the rules attribute of the AccountRulesEngineMatrix object
	 *
	 *@param  orginator        Description of the Parameter
	 *@param  businessTransId  Description of the Parameter
	 *@return                  The rules value
	 */
	public ArrayList <IEntryRule>getRules(Integer orginator, int businessTransId, Integer fundingTypeId) {

		// for loop statement
		ArrayList<IEntryRule> rules = new ArrayList<IEntryRule>();
		
		// dao query call here
		ArrayList<TransactionRuleVO> theRules = new ArrayList<TransactionRuleVO>(); 
		
		TransactionDao dao = (TransactionDaoImpl)MPSpringLoaderPlugIn.getCtx().getBean("transactionDao");
		TransactionRuleVO vo = new TransactionRuleVO();
		vo.setBusTransId(businessTransId);
		vo.setLedgerEntityTypeId(orginator);
		if(fundingTypeId != null && fundingTypeId.intValue() != 0){
			vo.setFundingTypeId(fundingTypeId);
		}
		else{
			//default to non-funded
			vo.setFundingTypeId(LedgerConstants.FUNDING_TYPE_NON_FUNDED);
		}
		
		try {
			theRules = (ArrayList<TransactionRuleVO>) dao.queryTransactionRule(vo);
		} catch (DataServiceException e1) {
			logger.info("Caught Exception and ignoring",e1);
		}
		for(int i = 0; i<theRules.size(); i++)
		{
			try {
				rules.add(getRule(theRules.get(i)));
			} catch (Exception e) {
				logger.info("Caught Exception and ignoring",e);
			}
		}
		return rules;
	}
	
	
	//NOT USED - moved logic to table "ledger_trans_funding_rule"
	public List<Integer> getRulesByFundingType(Integer fundingTypeId, Integer businessTransactionId){
		List<Integer> ruleList = new ArrayList<Integer>();
		if(fundingTypeId.intValue() == LedgerConstants.FUNDING_TYPE_PRE_FUNDED){
			if(businessTransactionId.intValue() == LedgerConstants.BUSINESS_TRANSACTION_DEPOSITS_FUNDS_BUYER){
				ruleList.add(new Integer(LedgerConstants.RULE_ID_DEPOSIT_CASH));
			}
			else if(businessTransactionId.intValue() == LedgerConstants.BUSINESS_TRANSACTION_POST_SO){
				ruleList.add(new Integer(LedgerConstants.RULE_ID_APPLY_POSTING_FEE));
				ruleList.add(new Integer(LedgerConstants.RULE_ID_RESERVE_SO_FUNDING));
			}
			else if(businessTransactionId.intValue() == LedgerConstants.BUSINESS_TRANSACTION_INCREASE_SO_ESCROW){
				ruleList.add(new Integer(LedgerConstants.RULE_ID_INCREASE_SPEND_LIMIT));
			}
			else if(businessTransactionId.intValue() == LedgerConstants.BUSINESS_TRANSACTION_CLOSE_SO){
				ruleList.add(new Integer(LedgerConstants.RULE_ID_RELEASE_SO_PAYMENT));
				ruleList.add(new Integer(LedgerConstants.RULE_ID_APPLY_SERVICE_FEE));
			}
			else if(businessTransactionId.intValue() == LedgerConstants.BUSINESS_TRANSACTION_RELEASE_SO_PAYMENT){
				ruleList.add(new Integer(LedgerConstants.RULE_ID_REFUND_ESCROW));
			}
			else if(businessTransactionId.intValue() == LedgerConstants.BUSINESS_TRANSACTION_CANCEL_SO){
				ruleList.add(new Integer(LedgerConstants.RULE_ID_RELEASE_PENALITY_PAYMENT));
				ruleList.add(new Integer(LedgerConstants.RULE_ID_RETRUN_SO_FUNDING));
			}
		}
		if(fundingTypeId.intValue() == LedgerConstants.FUNDING_TYPE_DIRECT_FUNDED){
			if(businessTransactionId.intValue() == LedgerConstants.BUSINESS_TRANSACTION_POST_SO){
				ruleList.add(new Integer(LedgerConstants.RULE_ID_APPLY_POSTING_FEE));
				ruleList.add(new Integer(LedgerConstants.RULE_ID_RESERVE_SO_FUNDING));
			}
			else if(businessTransactionId.intValue() == LedgerConstants.BUSINESS_TRANSACTION_INCREASE_SO_ESCROW){
				ruleList.add(new Integer(LedgerConstants.RULE_ID_INCREASE_SPEND_LIMIT));
			}
			else if(businessTransactionId.intValue() == LedgerConstants.BUSINESS_TRANSACTION_CLOSE_SO){
				ruleList.add(new Integer(LedgerConstants.RULE_ID_RELEASE_SO_PAYMENT));
				ruleList.add(new Integer(LedgerConstants.RULE_ID_APPLY_SERVICE_FEE_VIRTUAL));
			}
			else if(businessTransactionId.intValue() == LedgerConstants.BUSINESS_TRANSACTION_RELEASE_SO_PAYMENT){
				ruleList.add(new Integer(LedgerConstants.RULE_ID_GL_DEBIT_PMT_FROM_CONTRACTOR_PMT));
				ruleList.add(new Integer(LedgerConstants.RULE_ID_CREDIT_RETAIL_COST_OF_SO));
				ruleList.add(new Integer(LedgerConstants.RULE_ID_REFUND_ESCROW));
			}
			else if(businessTransactionId.intValue() == LedgerConstants.BUSINESS_TRANSACTION_CANCEL_SO){
				ruleList.add(new Integer(LedgerConstants.RULE_ID_RELEASE_PENALITY_PAYMENT));
				ruleList.add(new Integer(LedgerConstants.RULE_ID_APPLY_PENALTY_TO_CONTRACTOR));
				ruleList.add(new Integer(LedgerConstants.RULE_ID_ACTUAL_PENALITY));
				ruleList.add(new Integer(LedgerConstants.RULE_ID_RETRUN_SO_FUNDING));
			}
		}
		if(fundingTypeId.intValue() == LedgerConstants.FUNDING_TYPE_NON_FUNDED){
			if(businessTransactionId.intValue() == LedgerConstants.BUSINESS_TRANSACTION_DEPOSITS_FUNDS_BUYER){
				ruleList.add(new Integer(LedgerConstants.RULE_ID_VIRTUAL_CASH_DEPOSIT));
			}
			else if(businessTransactionId.intValue() == LedgerConstants.BUSINESS_TRANSACTION_POST_SO){
				ruleList.add(new Integer(LedgerConstants.RULE_ID_APPLY_POSTING_FEE_VIRTUAL));
				ruleList.add(new Integer(LedgerConstants.RULE_ID_RESERVE_SO_FUNDING));
			}
			else if(businessTransactionId.intValue() == LedgerConstants.BUSINESS_TRANSACTION_INCREASE_SO_ESCROW){
				ruleList.add(new Integer(LedgerConstants.RULE_ID_INCREASE_SPEND_LIMIT));
			}
			else if(businessTransactionId.intValue() == LedgerConstants.BUSINESS_TRANSACTION_CLOSE_SO){
				ruleList.add(new Integer(LedgerConstants.RULE_ID_RELEASE_SO_PAYMENT));
				ruleList.add(new Integer(LedgerConstants.RULE_ID_APPLY_SERVICE_FEE_VIRTUAL));
			}
			else if(businessTransactionId.intValue() == LedgerConstants.BUSINESS_TRANSACTION_RELEASE_SO_PAYMENT){
				ruleList.add(new Integer(LedgerConstants.RULE_ID_REFUND_ESCROW));
			}
			else if(businessTransactionId.intValue() == LedgerConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_BUYER){
				ruleList.add(new Integer(LedgerConstants.RULE_ID_VIRTUAL_CASH_RETURN));
			}
			else if(businessTransactionId.intValue() == LedgerConstants.BUSINESS_TRANSACTION_CANCEL_SO){
				ruleList.add(new Integer(LedgerConstants.RULE_ID_RELEASE_PENALITY_PAYMENT));
			}
		}
		return ruleList;
	}


    public TransactionDao getTransactionDao() {
        return transactionDao;
    }


    public void setTransactionDao(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

}

