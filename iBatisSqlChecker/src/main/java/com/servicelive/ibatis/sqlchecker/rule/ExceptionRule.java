package com.servicelive.ibatis.sqlchecker.rule;

import com.servicelive.ibatis.sqlchecker.ExplainPlan;
import com.servicelive.ibatis.sqlchecker.Query;

/**
 * 
 * @author svanloon
 *
 */
public class ExceptionRule extends Rule {

	private String exceptionFragment;

	/**
	 * 
	 * @param ruleName
	 * @param continueToNextRule
	 * @param exceptionFragment
	 */
	public ExceptionRule(String ruleName, boolean continueToNextRule, String exceptionFragment) {
		super(ruleName, RuleType.EXCEPTION, continueToNextRule);
		this.exceptionFragment = exceptionFragment;
	}

	@Override
	public boolean handle(Query query, ExplainPlan explainPlan) {
		return false;
	}

	@Override
	public boolean handle(Query query, Exception e) {
		if(exceptionFragment == null) {
			return true;
		}

		if(e.getMessage().toUpperCase().indexOf(exceptionFragment.toUpperCase()) > 0) {
			return true;
		}
		return false;
	}
}
