package com.servicelive.ibatis.sqlchecker.rule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.servicelive.ibatis.sqlchecker.Query;

public class TableNameRule extends SqlRule {
	private List<String> keywords = new ArrayList<String>();

	public TableNameRule(String ruleName) {
		super(ruleName, true, null);
		keywords.add("JOIN");
		keywords.add("FROM");
	}

	private Set<String> tableNames = new HashSet<String>();

	@Override
	protected boolean handle(Query query){
		String sql = query.getSqlWithSubstitutedParameters();
		String[] words = sql.split("\\s");
		String previousWord = "-1";
		for(String word:words) {
			if(word.equals("")) {
				continue;
			}
			for(String keyword: keywords) {
				if(previousWord.equalsIgnoreCase(keyword)) {
					String cleanWord = word;
					cleanWord = cleanWord.trim();
					cleanWord = cleanWord.replace("(", "");
					cleanWord = cleanWord.replace(")", "");
					cleanWord = cleanWord.replace("'", "");
					cleanWord = cleanWord.replace("`", "");
					String[] cleanerWords = cleanWord.split(",");
					if(cleanerWords.length > 2) {
						break;
					}
					cleanWord = cleanWord.replace(",", "");
					if(!cleanWord.equals("") && cleanWord.toUpperCase().indexOf("SELECT") < 0 && cleanWord.toUpperCase().indexOf("$") < 0) {
						tableNames.add(cleanWord);
						break;
					}
				}
			}
			previousWord = word;	
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String tableName:tableNames) {
			sb.append("'");
			sb.append(tableName);
			sb.append("'");
			sb.append(",");
			sb.append("\n");
		}
		return sb.toString();
	}
}
