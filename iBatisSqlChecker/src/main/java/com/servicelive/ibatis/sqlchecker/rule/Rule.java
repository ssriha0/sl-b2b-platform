package com.servicelive.ibatis.sqlchecker.rule;

import com.servicelive.ibatis.sqlchecker.ExplainPlan;
import com.servicelive.ibatis.sqlchecker.Query;

/**
 * 
 * @author svanloon
 *
 */
public abstract class Rule {

	private RuleType type;
	private boolean continueToNextRule;
	private String ruleName;

	/**
	 * 
	 * @param ruleName
	 * @param type
	 * @param continueToNextRule
	 */
	public Rule(String ruleName, RuleType type, boolean continueToNextRule) {
		this.type = type;
		this.continueToNextRule = continueToNextRule;
		this.ruleName = ruleName;
	}

	/**
	 * @return the type
	 */
	public RuleType getType() {
		return type;
	}
	
	/**
	 * @return the continueToNextRule
	 */
	public boolean isContinueToNextRule() {
		return continueToNextRule;
	}

	/**
	 * 
	 * @param query
	 * @param explainPlan
	 * @return ruleApplied
	 */
	public abstract boolean handle(Query query, ExplainPlan explainPlan);
	
	/**
	 * 
	 * @param query
	 * @param e
	 * @return ruleApplied
	 */
	public abstract boolean handle(Query query, Exception e);

	/**
	 * @return the ruleName
	 */
	public String getRuleName() {
		return ruleName;
	}
}
