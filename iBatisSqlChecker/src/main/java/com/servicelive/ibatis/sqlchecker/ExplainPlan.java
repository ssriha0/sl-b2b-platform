package com.servicelive.ibatis.sqlchecker;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExplainPlan {
	private List<ExplainPlanRow> list = new java.util.ArrayList<ExplainPlanRow>();
	private String explainString = null;

	public void addRow(ExplainPlanRow row) {
		explainString = null;
		list.add(row);
	}

	@Override
	public String toString() {
		if(explainString != null) {
			return explainString;
		}

		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		int[] columnLengths = getColumnLengths();
		for(ExplainPlanRow row:list) {
			Map<String, String> map = row.getMap();
			if(isFirst) {
				int j = 0;
				for(String key : map.keySet()) {
					sb.append(key);
					sb.append(pad(columnLengths[j] - key.length()));
					sb.append("\t");
					j++;
				}
				sb.append("\n");
				for(int columnLength: columnLengths) {
					for(int t = 0; t < columnLength; t++) {
						sb.append("-");
					}
					sb.append("\t");
				}
				sb.append("\n");
				isFirst = false;
			}

			int j = 0;
			for(String key : map.keySet()) {
				String value = map.get(key);
				sb.append(value);
				sb.append(pad(columnLengths[j] - value.length()));
				sb.append("\t");
				j++;
			}
			sb.append("\n");
		}
		explainString = sb.toString();
		return explainString;
	}
	
	private String pad(int pad) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < pad; i++) {
			sb.append(" ");
		}
		return sb.toString();
		
	}

	private int[] getColumnLengths() {
		int[] columnLengths = null;
		for(ExplainPlanRow row: list) {
			Map<String, String> map = row.getMap();
			if(columnLengths == null) {
				columnLengths = new int[map.values().size()];
			}
			Set<String> keys = map.keySet();
			Iterator<String> keysIter = keys.iterator();
			for(int i = 0; i < columnLengths.length; i++) {
				String key = keysIter.next();
				String value = map.get(key);
				columnLengths[i] = Math.max(columnLengths[i], key.length());
				columnLengths[i] = Math.max(columnLengths[i], value.length());
			}
		}
		return columnLengths;
	}
}
