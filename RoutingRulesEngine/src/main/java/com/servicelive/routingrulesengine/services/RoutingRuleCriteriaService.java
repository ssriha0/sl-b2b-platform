package com.servicelive.routingrulesengine.services;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.servicelive.routingrulesengine.vo.JobCodeVO;

public interface RoutingRuleCriteriaService extends Comparator{

	public List<Map<String,String>> sortZipsMarkets(List<String> zipMainList);
	
	public List<Map<String,String>> sortZipsAndMarkets(List<String> zipMainList,List<String> zips,List<String> markets);
	
	public List<Map<String,String>> sortCustReference(List<String> custMainList);
	
	public List<Map<String,String>> setCriteriaPagination(List<Map<String,String>> mainList,String criteria,int pagenum);
	
	public List<JobCodeVO> setJobCodeCriteriaPagination(List<JobCodeVO> mainList,int pagenum);
	
	public List<JobCodeVO> sortJobCodes(List<JobCodeVO> jobCodeList);
}
