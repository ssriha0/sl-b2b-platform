package com.servicelive.ibatis.sqlchecker.rule;

import com.servicelive.ibatis.sqlchecker.ExplainPlan;
import com.servicelive.ibatis.sqlchecker.Query;

/**
 * 
 * @author svanloon
 *
 */
public class ExplainPlanRule extends Rule {

	private String explainFragment;

	/**
	 * 
	 * @param ruleName
	 * @param continueToNextRule
	 * @param explainFragment
	 */
	public ExplainPlanRule(String ruleName, boolean continueToNextRule, String explainFragment) {
		super(ruleName, RuleType.EXPLAIN_PLAN, continueToNextRule);
		this.explainFragment = explainFragment;
	}

	@Override
	public boolean handle(Query query, ExplainPlan explainPlan) {
		if(explainFragment == null) {
			return true;
		}

		if(explainPlan.toString().indexOf(explainFragment) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean handle(Query query, Exception e) {
		return false;
	}
}
