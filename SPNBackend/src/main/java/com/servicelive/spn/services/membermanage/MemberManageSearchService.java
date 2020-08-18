/**
 * 
 */
package com.servicelive.spn.services.membermanage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.domain.spn.detached.MemberManageSearchCriteriaVO;
import com.servicelive.domain.spn.detached.MemberManageSearchResultVO;
import com.servicelive.domain.spn.network.SPNProviderFirmState;
import com.servicelive.domain.spn.network.SPNServiceProviderState;
import com.servicelive.spn.dao.network.SPNProviderFirmStateDao;
import com.servicelive.spn.dao.network.SPNServiceProviderStateDao;
import com.servicelive.spn.dao.provider.ServiceProviderDao;
import com.servicelive.spn.services.BaseServices;

/**
 * @author hoza
 *
 */
public class MemberManageSearchService extends BaseServices {
	
	private SPNServiceProviderStateDao spnServiceProviderStateDao;
	private SPNProviderFirmStateDao spnProviderFirmStateDao;
	private ServiceProviderDao serviceProviderDao;

	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.BaseServices#handleDates(java.lang.Object)
	 */
	@Override
	protected void handleDates(Object entity) {
		// do nothing

	}
	/**
	 * 
	 * @param searchCriteriaVO
	 * @return
	 * @throws Exception
	 */
	public List<MemberManageSearchResultVO> getMemberSearchForServiceProviders
	(MemberManageSearchCriteriaVO searchCriteriaVO) throws Exception{
		 List<SPNServiceProviderState> result = spnServiceProviderStateDao.searchForMemberManage(searchCriteriaVO);
		 return getResultsListforServicePro(result);
		
	}
	private  List<MemberManageSearchResultVO> getResultsListforServicePro( List<SPNServiceProviderState> result){
		 Map<Integer,MemberManageSearchResultVO> resultMap = new HashMap<Integer,MemberManageSearchResultVO>();
		 for(SPNServiceProviderState spState : result){
			ServiceProvider provider = spState.getServiceProviderStatePk().getServiceProvider(); 
			Integer  pid = provider.getId();
			MemberManageSearchResultVO vo;
			if(resultMap.containsKey(pid)) {
				vo = resultMap.get(pid);
			}else {
				vo = new MemberManageSearchResultVO();
				vo.setServiceProvider(provider);
				resultMap.put(pid,vo);
			}
			//Assumption we wil not  reapeat the same SPN + SPNSTATUS + SProvider Combination,  It may have mutliple SPNs with same status and same provider id
			vo.getServiceProviderNetworkInfo().add(spState);
		 }
		 return retrieveListofResultVoFromMap( resultMap);
	}
	// I know its a repeatation.. -- later when i will have time I will refactor
	private  List<MemberManageSearchResultVO> getResultsListforProFirm( List<SPNProviderFirmState> result,Map<Integer,List<ServiceProvider>> countsMap){
		 Map<Integer,MemberManageSearchResultVO> resultMap = new HashMap<Integer,MemberManageSearchResultVO>();
		 for(SPNProviderFirmState spState : result){
			ProviderFirm firm = spState.getProviderFirmStatePk().getProviderFirm(); 
			Integer  pfid = firm.getId();
			MemberManageSearchResultVO vo;
			Integer proCount = Integer.valueOf(0);
			if(countsMap.containsKey(pfid)){
				proCount = Integer.valueOf(countsMap.get(pfid).size());
			}
			firm.setServiceProCount(proCount);
			if(resultMap.containsKey(pfid)) {
				vo = resultMap.get(pfid);
			}else {
				vo = new MemberManageSearchResultVO();
				vo.setProviderFirm(firm);
				resultMap.put(pfid,vo);
			}
			
			//Assumption we wil not  reapeat the same SPN + SPNSTATUS + SProvider Combination,  It may have mutliple SPNs with same status and same provider id
			vo.getProviderFirmNetworkInfo().add(spState);
		 }
		 return retrieveListofResultVoFromMap( resultMap);
	}
	
	private Set<Integer> getListofProviderFirmIds(List<SPNProviderFirmState> result) {
		Set<Integer> setofPro = new HashSet<Integer>(0);
		 for(SPNProviderFirmState spState : result){
				ProviderFirm firm = spState.getProviderFirmStatePk().getProviderFirm(); 
				setofPro.add(firm.getId());
		 }
		return setofPro;
	}
	/**
	 * @param list
	 * @param resultMap
	 */
	private  List<MemberManageSearchResultVO> retrieveListofResultVoFromMap(Map<Integer, MemberManageSearchResultVO> resultMap) {
		List<MemberManageSearchResultVO> list = new  ArrayList<MemberManageSearchResultVO>();
		for(Integer key : resultMap.keySet()){
			 list.add(resultMap.get(key));
		 }
		return list;
	}
	
	 
	/**
	 * 
	 * @param searchCriteriaVO
	 * @return
	 * @throws Exception
	 */
	public List<MemberManageSearchResultVO> getMemberSearchForProviderFirms
	(MemberManageSearchCriteriaVO searchCriteriaVO) throws Exception{
		 List<SPNProviderFirmState> result = spnProviderFirmStateDao.searchForMemberManage(searchCriteriaVO);
		 List<ServiceProvider> providersList = fetchMarketReadyServiceProviders(result);
		 Map<Integer,List<ServiceProvider>> map = getServiceProMap(providersList);
		// updateResultsWithCounts(result,providersList);
		 return getResultsListforProFirm(result,map);
		
	}
	
	private Map<Integer,List<ServiceProvider>> getServiceProMap( List<ServiceProvider> providersList)  {
		Map<Integer,List<ServiceProvider>> map = new HashMap<Integer,List<ServiceProvider>>();
		for(ServiceProvider sp : providersList ){
			if(!map.containsKey(sp.getProviderFirm().getId())) {
				List<ServiceProvider> list = new ArrayList<ServiceProvider>();
				list.add(sp);
				map.put(sp.getProviderFirm().getId(),list);
			}else {
				map.get(sp.getProviderFirm().getId()).add(sp);
			}
		}
		return map;
	}
	
	/*private void updateResultsWithCounts( List<SPNProviderFirmState> result,  List<ServiceProvider> providersList) {
		
		for(SPNProviderFirmState  proFirmState : result) {
			
			
			List<ServiceProvider> proList = map.get(proFirmState.getProviderFirmStatePk().getProviderFirm().getId());
			if(proList != null) proFirmState.setServiceProCount(proList.size());
			else  proFirmState.setServiceProCount(Integer.valueOf(0));
			
		}
	}*/
	/**
	 * 
	 * @param proFirmStates
	 * @return
	 */
	private List<ServiceProvider> fetchMarketReadyServiceProviders( List<SPNProviderFirmState> proFirmStates){
		Set<Integer> proFirmIds = getListofProviderFirmIds(proFirmStates);
		List<Integer> list = new ArrayList<Integer>(0);
		for(Integer i : proFirmIds) {
			list.add(i);
		}
		return serviceProviderDao.findMarketReadyServiceProForProviderFirms(list);
	}
	
	
	/**
	 * @return the spnServiceProviderStateDao
	 */
	public SPNServiceProviderStateDao getSpnServiceProviderStateDao() {
		return spnServiceProviderStateDao;
	}
	/**
	 * @param spnServiceProviderStateDao the spnServiceProviderStateDao to set
	 */
	public void setSpnServiceProviderStateDao(
			SPNServiceProviderStateDao spnServiceProviderStateDao) {
		this.spnServiceProviderStateDao = spnServiceProviderStateDao;
	}
	/**
	 * @return the spnProviderFirmStateDao
	 */
	public SPNProviderFirmStateDao getSpnProviderFirmStateDao() {
		return spnProviderFirmStateDao;
	}
	/**
	 * @param spnProviderFirmStateDao the spnProviderFirmStateDao to set
	 */
	public void setSpnProviderFirmStateDao(
			SPNProviderFirmStateDao spnProviderFirmStateDao) {
		this.spnProviderFirmStateDao = spnProviderFirmStateDao;
	}
	/**
	 * @return the serviceProviderDao
	 */
	public ServiceProviderDao getServiceProviderDao() {
		return serviceProviderDao;
	}
	/**
	 * @param serviceProviderDao the serviceProviderDao to set
	 */
	public void setServiceProviderDao(ServiceProviderDao serviceProviderDao) {
		this.serviceProviderDao = serviceProviderDao;
	}
	

}
