package com.newco.marketplace.translator.rules;

import java.util.List;

import org.apache.log4j.Logger;
import org.drools.RuleBase;
import org.drools.WorkingMemory;

public class RulesEngine {
	
	private static final Logger logger = Logger.getLogger(RulesEngine.class);

	public static boolean fireRules(List<Object> objects) {
		boolean success = false;
		try {
			RuleBase ruleBase = RulesLoader.getRuleBase();
			WorkingMemory workingMemory = ruleBase.newStatefulSession();
			for (Object obj : objects) {
				workingMemory.insert(obj);
			}
			workingMemory.fireAllRules();
			success = true;
		} catch (Exception e) {
			logger.error("Error firing rules: " + e.getMessage(), e);
		}
		return success;
	}
	
}
