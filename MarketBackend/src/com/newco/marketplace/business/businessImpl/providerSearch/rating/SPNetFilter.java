package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSPNetStateResultsVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;
import com.newco.marketplace.interfaces.ProviderConstants;

public class SPNetFilter extends RatingCalculator{
	
	public SPNetFilter(){
		super(null);
	}
	
	public SPNetFilter(RatingCalculator calc){
		super(calc);
	}
	
	@Override
	public void getFilteredList(RatingParameterBean bean,
			ArrayList<ProviderResultVO> providers) {
		
		if (bean instanceof SPNetParameterBean) {
			SPNetParameterBean spNetBean = (SPNetParameterBean) bean;
			List<ProviderResultVO> providersNotMembersOfSpn = new ArrayList<ProviderResultVO>();

			if (null != spNetBean.getSpnetId() && spNetBean.getSpnetId() > 0) {
				Integer filterSpnId = spNetBean.getSpnetId();
				for(ProviderResultVO providerResultVO: providers){
					boolean memberOfSpn = false;
					List<ProviderSPNetStateResultsVO> spNetStatesList =  providerResultVO.getSpnetStates();
					for(ProviderSPNetStateResultsVO spNetResultVO :spNetStatesList){
						if(spNetResultVO.getSpnId().intValue() == filterSpnId &&
								((ProviderConstants.SERVICE_PROVIDER_SPNET_APPROVED).equals(spNetResultVO.getProviderSpnetStateId())
								|| (ProviderConstants.SERVICE_PROVIDER_SPNET_APPROVED).equals(spNetResultVO.getAliasProviderSpnetStateId()) )){
							
							    memberOfSpn = true;
							    // set filter spn & perfLevel info
							    providerResultVO.setFilteredPerfLevel(spNetResultVO.getPerformanceLevel());
							    providerResultVO.setFilterSpnetId(filterSpnId);
							    providerResultVO.setFilteredSpnName(spNetResultVO.getSpnName());
							    providerResultVO.setFilteredPerfLevelDesc(spNetResultVO.getPerformanceLevelDesc());
							    providerResultVO.setPerformanceScore(spNetResultVO.getPerformanceScore());
							    // if perfLevel filter is on 
							    List<Integer> perfLevelList = spNetBean.getPerformanceLevels();
							    if(perfLevelList!= null && perfLevelList.size() > 0){
							    	if(!perfLevelList.contains(spNetResultVO.getPerformanceLevel())){
							    		memberOfSpn = false;
							    	}
							    }
							    //filtering based on performance score
							    if(spNetBean.getRoutingPriorityApplied()){
							    	 Double perfScore = spNetBean.getPerformanceScore();
									    if(spNetResultVO.getPerformanceScore() < perfScore){
									    	memberOfSpn = false;
									    }
							    }
							   break;
						}
					}
					if(!memberOfSpn){
						providersNotMembersOfSpn.add(providerResultVO);
					}
				}
				
				providers.removeAll(providersNotMembersOfSpn);
			
			}
			
		} else {
			if (getChain() != null){
				getChain().getFilteredList(bean, providers);
			}
		}
	}


}


