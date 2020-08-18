package com.servicelive.ibatis.sqlchecker.rule;

import com.servicelive.ibatis.sqlchecker.ExplainPlan;
import com.servicelive.ibatis.sqlchecker.Query;

/**
 * 
 * @author svanloon
 *
 */
public class SqlRule extends Rule {

	private String sqlFragment;

	/**
	 * 
	 */
	public SqlRule(String ruleName, boolean continueToNextRule, String sqlFragment) {
		super(ruleName, RuleType.SQL, continueToNextRule);
		this.sqlFragment = sqlFragment;
	}

	@Override
	public boolean handle(Query query, ExplainPlan explainPlan) {
		return handle(query);
	}

	@Override
	public boolean handle(Query query, Exception e) {
		return handle(query);
	}

	protected boolean handle(Query query){
		String sql = query.getSqlWithSubstitutedParameters();
		if(sql.toUpperCase().indexOf(sqlFragment.toUpperCase()) > 0) {
			return true;
		}
		return false;
	}
}
