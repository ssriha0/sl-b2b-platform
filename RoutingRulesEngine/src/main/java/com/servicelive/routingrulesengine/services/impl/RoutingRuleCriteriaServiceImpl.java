package com.servicelive.routingrulesengine.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.servicelive.domain.spn.detached.LabelValueBean;
import com.servicelive.routingrulesengine.RoutingRulesConstants;
import com.servicelive.routingrulesengine.comparator.CustomComparator;

import com.servicelive.routingrulesengine.services.RoutingRuleCriteriaService;
import com.servicelive.routingrulesengine.services.RoutingRulesService;
import com.servicelive.routingrulesengine.vo.JobCodeVO;
/**
 * This class contains various validation methods used while creating a rule
 * 
 * @author Infosys
 * 
 */
public class RoutingRuleCriteriaServiceImpl implements RoutingRuleCriteriaService{
	private RoutingRulesService routingRulesService;
	
	/**
	 * The method is to sort Zip Code/Market rule criteria
	 * 
	 * @param zipMainList
	 * @return List<Map<String,String>>
	 */
	public List<Map<String,String>> sortZipsMarkets(List<String> zipMainList){
		List<Map<String,String>> zipMarketList = new ArrayList<Map<String,String>>();
		List<LabelValueBean> markets= null;
		if(null!=zipMainList){
			for(String marketZipVal:zipMainList){
				Map<String,String> zipMarketMap = new HashMap<String, String>();
				if(marketZipVal.startsWith("market")){
					zipMarketMap.put("value",marketZipVal);
					zipMarketMap.put("name",  "Market");
					String marketLabel = "";
					String marketVal = marketZipVal.substring(6);
					if (markets==null) {
						markets= routingRulesService.getMarkets();
					}
					for (LabelValueBean market : markets) {
						if (market.getValue().equals(marketVal)) {
							marketLabel = market.getLabel();
							zipMarketMap.put("label", marketLabel);
							break;
						}
					}	
					zipMarketMap.put("val",marketZipVal);
					
				}else if(marketZipVal.startsWith("zip")){
					zipMarketMap.put("name",  "Zip Code");
					zipMarketMap.put("value",marketZipVal);
					String zipVal = marketZipVal.substring(3);
					String zipLabel = routingRulesService.getCityByZipcode(zipVal);
					zipMarketMap.put("label",zipLabel);
					marketZipVal = "#" + marketZipVal;
					zipMarketMap.put("val",marketZipVal);
				}
				zipMarketList.add(zipMarketMap);
				
			}
		}
		Collections.sort ( zipMarketList ,this) ;
		return zipMarketList;
	}
	
	
	/**
	 * The method is to sort Zip Code/Market rule criteria
	 * 
	 * @param zipMainList
	 * @return List<Map<String,String>>
	 */
	public List<Map<String,String>> sortZipsAndMarkets(List<String> zipMainList,
			List<String> zips,List<String> markets){
		List<Map<String,String>> zipMarketList = new ArrayList<Map<String,String>>();
		Map<String,String> marketsFromDB = null;
		Map<String,String> zipsAndCityFromDB = null;
		List<LabelValueBean> marketsLabelBean= null;
		if(markets!=null && markets.size()>0)
			marketsFromDB = routingRulesService.getMarkets(markets);
		if(zips!=null && zips.size()>0)
			zipsAndCityFromDB = routingRulesService.getCityByZipcodeList(zips);
			
		if(null!=zipMainList){
			for(String marketZipVal:zipMainList){
				Map<String,String> zipMarketMap = new HashMap<String, String>();
				if(marketZipVal.startsWith("market")){
					zipMarketMap.put("value",marketZipVal);
					zipMarketMap.put("name",  "Market");
					String marketLabel = "";
					String marketVal = marketZipVal.substring(6);
					if (marketsFromDB== null) {
						if (marketsLabelBean==null) {
							marketsLabelBean= routingRulesService.getMarkets();
						}
						for (LabelValueBean marketLabelBean : marketsLabelBean) {
							if (marketLabelBean.getValue().equals(marketVal)) {
								marketLabel = marketLabelBean.getLabel();
								zipMarketMap.put("label", marketLabel);
								break;
							}
						}
					}else{
						marketLabel = marketsFromDB.get(marketVal);
					}
					zipMarketMap.put("label", marketLabel);
					zipMarketMap.put("val",marketZipVal);
					
				}else if(marketZipVal.startsWith("zip") || marketZipVal.startsWith("#zip")){
					zipMarketMap.put("name",  "Zip Code");
					zipMarketMap.put("value",marketZipVal);
					
					String zipVal = "";
					if(marketZipVal.startsWith("zip")){
						zipVal = marketZipVal.substring(3);
					}else if(marketZipVal.startsWith("#zip")){
						zipVal = marketZipVal.substring(4);
					}
					
					String zipLabel = "";
					if(zipsAndCityFromDB== null){
						zipLabel = routingRulesService.getCityByZipcode(zipVal);
					}else{
						zipLabel = zipsAndCityFromDB.get(zipVal);
					}
					zipMarketMap.put("label",zipLabel);
					marketZipVal = "#" + marketZipVal;
					zipMarketMap.put("val",marketZipVal);
				}
				zipMarketList.add(zipMarketMap);
			}
		}
		Collections.sort ( zipMarketList ,this) ;
		return zipMarketList;
	}
	
	/**
	 * The method is to sort Job Codes rule criteria
	 * 
	 * @param jobCodeList
	 * @return List<JobPriceVO>
	 */
	public List<JobCodeVO> sortJobCodes(List<JobCodeVO> jobCodeList){
		
		Collections.sort ( jobCodeList ,this) ;
		return jobCodeList;
	}
	
	/**
	 * The method is to sort Custom Reference rule criteria
	 * 
	 * @param custMainList
	 * @return List<Map<String,String>>
	 */
	public List<Map<String,String>> sortCustReference(List<String> custMainList){
		List<Map<String,String>> custList = new ArrayList<Map<String,String>>();
		List<LabelValueBean> markets= null;
		if(custMainList!=null){
			for(String custVal:custMainList){
				Map<String,String> custMap = new HashMap<String, String>();
				String[] nameValue = custVal.split(RoutingRulesConstants.DELIMITER);
				int index = custVal.indexOf("@@");
				String value = custVal.substring(index+2, custVal.length());
				if(nameValue.length>1){
					custMap.put("value",value);
					custMap.put("label",nameValue[0]);
					custMap.put("name",nameValue[0]);
					custMap.put("val",custVal);
					custList.add(custMap);
				}
			}
		}
		
		// SL-16802 - Start
		
		// Step 1: Sort on the val
		CustomComparator ValComparator = new CustomComparator("val","DEFAULT");
		Collections.sort( custList ,ValComparator) ;
		
		/* Step 2: Split to two lists 
		 * 1.List of Custom references with Integers as value
		 * 2.List of Custom references with String as value
		 * */		
		List<Map<String,String>> stringList = new ArrayList<Map<String,String>>();
		List<Map<String,String>> integerList = new ArrayList<Map<String,String>>();
		if(custList!=null){
			for(Map<String,String> map : custList){
				if(isParsableToInt(map.get("value")))
					integerList.add(map);
				else
					stringList.add(map);
			}
		}
		
		// Step 3: Group based on custom reference type for both integer and string list
		Map<String,List<Map<String,String>>> integerListOnType = 
			new HashMap<String, List<Map<String,String>>>();
		Map<String,List<Map<String,String>>> stringListOnType = 
			new HashMap<String, List<Map<String,String>>>();
		
		// Group the integer list
		for(Map<String,String> map : integerList){
			String mapKey = map.get("name");
			if(integerListOnType.containsKey(mapKey)){
				List<Map<String,String>> existingMapList = integerListOnType.get(mapKey);
				existingMapList.add(map);
				integerListOnType.put(mapKey, existingMapList);
			}else{
				List<Map<String,String>> newMapList = new ArrayList<Map<String,String>>();
				newMapList.add(map);
				integerListOnType.put(mapKey, newMapList);
			}
		}
		
		// Group the string list
		for(Map<String,String> map : stringList){
			String mapKey = map.get("name");
			if(stringListOnType.containsKey(mapKey)){
				List<Map<String,String>> existingMapList = stringListOnType.get(mapKey);
				existingMapList.add(map);
				stringListOnType.put(mapKey, existingMapList);
			}else{
				List<Map<String,String>> newMapList = new ArrayList<Map<String,String>>();
				newMapList.add(map);
				stringListOnType.put(mapKey, newMapList);
			}
		}
		
		// Step 4: Sort each group
		List<Map<String,String>> finalList = new ArrayList<Map<String,String>>();
		
		// Integer group
		Iterator<String> intIter =  integerListOnType.keySet().iterator();
		while(intIter.hasNext()){
			String key = intIter.next();
			List<Map<String,String>> valueList = integerListOnType.get(key);
			
			// Sort each list
			CustomComparator integerComparator = new CustomComparator("value","INTEGER");
			Collections.sort(valueList,integerComparator);
			finalList.addAll(valueList);
		}
		
		// String group
		Iterator<String> strIter =  stringListOnType.keySet().iterator();
		while(strIter.hasNext()){
			String key = strIter.next();
			List<Map<String,String>> valueList = stringListOnType.get(key);
			
			// Sort each list
			CustomComparator stringComparator = new CustomComparator("value","DEFAULT");
			Collections.sort(valueList,stringComparator);
			finalList.addAll(valueList);
		}
		
		// Sort the consolidated list
		CustomComparator nameComparator =  new CustomComparator("name","DEFAULT");
		Collections.sort(finalList, nameComparator);
		
		// SL-16802 - End
		return finalList;
	}
	
	public static boolean isParsableToInt(String value){
		try{
			Integer.parseInt(value);
			return true;
		}catch(NumberFormatException nfe){
			return false;
		}
	}
	
	/**
	 * The method is to set pagination criteria for zips, markets and custom reference
	 * 
	 * @param mainList
	 * @param criteria
	 * @param pagenum
	 * @return List<Map<String,String>>
	 */
	public List<Map<String,String>> setCriteriaPagination(List<Map<String,String>> mainList,String criteria,int pagenum){
		List<Map<String,String>> paginatedList = new ArrayList<Map<String,String>>();
		if(mainList!=null){
			int totalCount = mainList.size();
			int noOfPages = totalCount/15;
			if(noOfPages * 15 < totalCount){
				noOfPages = noOfPages + 1;
			}
			int startIndex = (pagenum - 1) * 15;
			int endIndex = startIndex + 14;
			if(endIndex >= totalCount){
				endIndex = totalCount-1;
			}
			
			for(int index = startIndex;index<=endIndex ; index++){
				paginatedList.add(mainList.get(index));
			}
		}
		return paginatedList;
	}
	
	/**
	 * The method is to set pagination criteria for job codes
	 * 
	 * @param mainList
	 * @param pagenum
	 * @return List<Map<String,String>>
	 */
	public List<JobCodeVO> setJobCodeCriteriaPagination(List<JobCodeVO> mainList,int pagenum){
		List<JobCodeVO> paginatedList = new ArrayList<JobCodeVO>();
		if(null!=mainList){
			int totalCount = mainList.size();
			int noOfPages = totalCount/15;
			if(noOfPages * 15 < totalCount){
				noOfPages = noOfPages + 1;
			}
			int startIndex = (pagenum - 1) * 15;
			int endIndex = startIndex + 14;
			if(endIndex >= totalCount){
				endIndex = totalCount -1;
			}
			
			for(int index = startIndex;index<=endIndex ; index++){
				paginatedList.add(mainList.get(index));
			}
		}
		return paginatedList;
	}
	
	/**
	 * The method is to compare CAR Rule criteria for sorting
	 * 
	 * @param object
	 * @param compareObject
	 * @return 
	 */
	 public int compare(Object object, Object compareObject) {
		if (object.getClass() == JobCodeVO.class) {
			String objValue = (String) ((JobCodeVO) object).getJobCode();
			String compareObjValue = (String) ((JobCodeVO) compareObject).getJobCode();
			return objValue.compareTo(compareObjValue);
		} else {
			String objValue = ((String) ((HashMap) object).get("val")).toLowerCase();
			String compareObjValue = ((String) ((HashMap) compareObject).get("val")).toLowerCase();
			return objValue.compareTo(compareObjValue);
		}

	}
	 
	public void setRoutingRulesService(RoutingRulesService routingRulesService) {
		this.routingRulesService = routingRulesService;
	}
	
	
}
