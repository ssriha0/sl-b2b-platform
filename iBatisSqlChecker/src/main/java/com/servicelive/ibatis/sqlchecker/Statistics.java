package com.servicelive.ibatis.sqlchecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servicelive.ibatis.sqlchecker.rule.Rule;
import com.servicelive.ibatis.sqlchecker.rule.TableNameRule;


public class Statistics {

	private Map<Rule, List<FullWrapper>> stats = new HashMap<Rule, List<FullWrapper>>();
	private List<Rule> rules = new ArrayList<Rule>();

	public Statistics(List<Rule> rules) {
		this.rules = rules;
	}

	public List<Rule> handleStatistics(FullWrapper fw) {
		List<Rule> rulesFired = new ArrayList<Rule>();
		for(Rule rule:rules) {
			boolean ruleFired;
			if(fw.getE() != null) {
				ruleFired = rule.handle(fw.getQuery(), fw.getE());
			} else {
				ruleFired = rule.handle(fw.getQuery(), fw.getExplainPlan());
			}
			if(ruleFired) {
				List<FullWrapper> previousSqlRun = stats.get(rule);
				if(previousSqlRun == null) {
					previousSqlRun = new ArrayList<FullWrapper>();
					stats.put(rule, previousSqlRun);
				}
				previousSqlRun.add(fw);
				rulesFired.add(rule);
				if(!rule.isContinueToNextRule()) {
					break;
				}
			}
		}
		return rulesFired;
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("Statistics").append("\n");
		sb.append("--------------------------------").append("\n");
		for(Rule key:stats.keySet()) {
			if(key instanceof TableNameRule) {
				sb.append(key.toString()).append("\n");
			} else {
				sb.append(key.getRuleName()).append(" = ").append(stats.get(key).size()).append("\n");
			}
		}
		return sb.toString();
	}
}
