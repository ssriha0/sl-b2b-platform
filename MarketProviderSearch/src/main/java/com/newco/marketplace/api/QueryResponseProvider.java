package com.newco.marketplace.api;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.search.solr.dto.ProviderDto;
import com.newco.marketplace.search.solr.dto.ProviderSkill;

public class QueryResponseProvider {

	public final static int ERROR = -1;	 
	public final static int MAIN_CATEGORY = ProviderSkill.MAIN_CATEGORY;
	public final static int CATEGORY = ProviderSkill.CATEGORY;
	public final static int SUB_CATEGORY = ProviderSkill.SUB_CATEGORY;
	public final static int AMBIGUOUS = 3;
	public final static int MULTI_CHOISE = 4;
	public final static int WRONG_SPELLING = 5;
	
	public static final float MAX_SCORE_DIFF = 2.0f;
	public static final int MAX_DISAMBIGUATION_RESULT = 10;	
	private long totalCount; 
	
	private List<SearchFilter> availFilters;
	
	private List<ProviderDto> list;
	public List<ProviderDto> getList() {
		return list;
	}

	public void setList(List<ProviderDto> list) {
		this.list = list;
	}

	private String suggestedSpelling;
	private boolean wrongSpelling;		
	private int status = -1;
        
	public QueryResponseProvider(List<ProviderDto> list) {
		this.list  = list; 
		availFilters = new ArrayList<SearchFilter>();
	}
	
	public QueryResponseProvider() {
		this.list  = new ArrayList<ProviderDto>();
	}

	
	public String getSuggestedSpelling() {
		return suggestedSpelling;
	}

	public void setSuggestedSpelling(String suggestedSpelling) {
		this.suggestedSpelling = suggestedSpelling;
	}

	public boolean isWrongSpelling() {
		return wrongSpelling;
	}

	public void setWrongSpelling(boolean wrongSpelling) {
		this.wrongSpelling = wrongSpelling;
	}

	public int getStatus() {		
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}	

	
	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
	@Override
	public String toString() {
		StringBuilder bld = new StringBuilder();
				
		if (list == null) {
			bld.append("Tree is null");
			return bld.toString();
		}
		
		if (list.size() > 0) {
			String format = "|%1$-8s|%2$-25s|%3$-30s|%4$-8s|%5$-40s|%6$-30s|%7$-40s|%8$-25s|%9$-5s|%10$-2s";
			String n = String.format(format, "Id", "name", "Skill", "Score", "Licence", "City", "description", "businessName", "D", "R");
			bld.append(n + "\n");

			bld.append("---------------------------------------------------------------------------------------------------- Found: " + totalCount + " -------------------------------------------------------------------------------------------------------------------------------------\n");
			for (ProviderDto dto : list) {
				bld.append(dto +  "\n");
			}
			
			bld.append("\nAvailable Filters:");
			for (SearchFilter dto : availFilters) {
				bld.append(dto +  " |");
			}
			
			bld.append("\n");
			
		} else {
			bld.append("Found: " + totalCount);
		}
		return bld.toString();
	}

	public List<SearchFilter> getAvailFilters() {
		return availFilters;
	}

	public void setAvailFilters(List<SearchFilter> availFilters) {
		this.availFilters = availFilters;
	}
}
