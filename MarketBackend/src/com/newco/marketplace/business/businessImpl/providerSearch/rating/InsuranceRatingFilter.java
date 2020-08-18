package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.InsuranceRatingsLookupVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;
import com.newco.marketplace.dto.vo.providerSearch.InsuranceResultVO;
import com.newco.marketplace.interfaces.OrderConstants;


public class InsuranceRatingFilter extends RatingCalculator{
	
	public InsuranceRatingFilter(){
		super(null);
	}
	
	public InsuranceRatingFilter(RatingCalculator calc){
		super(calc);
	}
	
	public void getFilteredList(RatingParameterBean bean, ArrayList<ProviderResultVO> providers) {
		
		if (bean instanceof InsuranceRatingParameterBean) {
			InsuranceRatingParameterBean insuranceBean = (InsuranceRatingParameterBean)bean;
			Map<Integer, InsuranceRatingsLookupVO> selectedInsuranceMap = insuranceBean.getSelectedInsuranceMap();
			List<ProviderResultVO> providersList = new ArrayList<ProviderResultVO>();			
			if(null != selectedInsuranceMap){
				for(ProviderResultVO providerVO : providers){
					Boolean removalInd = false;
					for(InsuranceResultVO providerInsuranceList : providerVO.getVendorInsuranceTypes()){
						Integer vendorInsuranceType = providerInsuranceList.getVendorInsuranceTypes();
						InsuranceRatingsLookupVO insuranceVO = selectedInsuranceMap.get(vendorInsuranceType);
						if(removalInd.equals(true)){
							break;
						}
						if(selectedInsuranceMap.containsKey(vendorInsuranceType)
								&& (OrderConstants.GL_CREDENTIAL_CATEGORY_ID == vendorInsuranceType.intValue()
										||OrderConstants.AL_CREDENTIAL_CATEGORY_ID == vendorInsuranceType.intValue())){							
							if(insuranceVO.isCheckBoxValue()==true && providerInsuranceList.getVerifiedByServiceLive()==false
									&& removalInd.equals(false)){
								removalInd =true;
							}
							if(!(insuranceVO.getId().equals(0))){
								if((insuranceVO.getId().equals(1)||insuranceVO.getId().equals(8))
										&& providerInsuranceList.getInsurancePresent().equals(true)
										&& removalInd.equals(false)){
									removalInd =true;
								}else if((insuranceVO.getId().equals(2)||insuranceVO.getId().equals(9))
										&& providerInsuranceList.getInsurancePresent().equals(true)
										&& providerInsuranceList.getAmount()>=insuranceVO.getLimitValue()
										&& removalInd.equals(false)){
									removalInd =true;
								}else if((!(insuranceVO.getId().equals(2)||insuranceVO.getId().equals(9)))
										&& providerInsuranceList.getInsurancePresent().equals(true)
										&& providerInsuranceList.getAmount()<insuranceVO.getLimitValue()
										&& removalInd.equals(false)){
									removalInd =true;
								}
								if(!(insuranceVO.getId().equals(1)||insuranceVO.getId().equals(8))
										&& providerInsuranceList.getInsurancePresent().equals(false)
										&& removalInd.equals(false)){
									removalInd =true;
								}
							}								
							
						}else if(selectedInsuranceMap.containsKey(vendorInsuranceType)
								&& OrderConstants.WC_CREDENTIAL_CATEGORY_ID == vendorInsuranceType.intValue() ){
							if(insuranceVO.isCheckBoxValue()==true && providerInsuranceList.getVerifiedByServiceLive()==false
									&& removalInd.equals(false)){
								removalInd =true;
							}
							if(!(insuranceVO.getId().equals(0))){
								if(insuranceVO.getId().equals(14)&& removalInd.equals(false)
										&& providerInsuranceList.getInsurancePresent().equals(true)){
									removalInd =true;
								}else if(!(insuranceVO.getId().equals(14))
										&& providerInsuranceList.getInsurancePresent().equals(false)){
									removalInd =true;
								}
							}					
						}						
					}
					if(removalInd.equals(true)){
						providersList.add(providerVO);
					}
				}
				providers.removeAll(providersList);
			}
			
			//SL-10809 Additional Insurance
			Map<Integer, InsuranceRatingsLookupVO> addnSelectedInsuranceMap = insuranceBean.getAddnSelectedInsuranceMap();
			List<ProviderResultVO> providersList2 = new ArrayList<ProviderResultVO>();	
			if(null != addnSelectedInsuranceMap){
				for(ProviderResultVO providerVO : providers){
					Boolean removalInd = false;
					if(providerVO.getAddtionalInsuranceTypes()!=null)
					{
						for(InsuranceResultVO providerInsuranceList : providerVO.getAddtionalInsuranceTypes()){
							Integer vendorInsuranceType = providerInsuranceList.getVendorInsuranceTypes();
							InsuranceRatingsLookupVO insuranceVO = addnSelectedInsuranceMap.get(vendorInsuranceType);
							if(removalInd.equals(true)){
								break;
							}
							if(addnSelectedInsuranceMap.containsKey(vendorInsuranceType)
									&& (OrderConstants.EMPLOYEE_DISHONESTY_INS == vendorInsuranceType.intValue()
										)){

								if(insuranceVO.isCheckBoxValue()==true && providerInsuranceList.getVerifiedByServiceLive()==false
										&& removalInd.equals(false)){
									removalInd =true;
								}
								if(!(insuranceVO.getId().equals(0))){
									if(insuranceVO.getId().equals(16)&& removalInd.equals(false)
											&& providerInsuranceList.getInsurancePresent().equals(true)){
										removalInd =true;
									}else if(!(insuranceVO.getId().equals(16))
											&& providerInsuranceList.getInsurancePresent().equals(false)){
										removalInd =true;
									}
								}					
							
							}else if(addnSelectedInsuranceMap.containsKey(vendorInsuranceType)
									&& OrderConstants.CARGO_LEGAL_LIABILITY == vendorInsuranceType.intValue() ){
								if(insuranceVO.isCheckBoxValue()==true && providerInsuranceList.getVerifiedByServiceLive()==false
										&& removalInd.equals(false)){
									removalInd =true;
								}
								if(!(insuranceVO.getId().equals(0))){
									if(insuranceVO.getId().equals(16)&& removalInd.equals(false)
											&& providerInsuranceList.getInsurancePresent().equals(true)){
										removalInd =true;
									}else if(!(insuranceVO.getId().equals(16))
											&& providerInsuranceList.getInsurancePresent().equals(false)){
										removalInd =true;
									}
								}					
							}
							else if(addnSelectedInsuranceMap.containsKey(vendorInsuranceType)
									&& OrderConstants.WAREHOUSEMEN_LEGAL_LIABILITY == vendorInsuranceType.intValue() ){
								if(insuranceVO.isCheckBoxValue()==true && providerInsuranceList.getVerifiedByServiceLive()==false
										&& removalInd.equals(false)){
									removalInd =true;
								}
								if(!(insuranceVO.getId().equals(0))){
									if(insuranceVO.getId().equals(16)&& removalInd.equals(false)
											&& providerInsuranceList.getInsurancePresent().equals(true)){
										removalInd =true;
									}else if(!(insuranceVO.getId().equals(16))
											&& providerInsuranceList.getInsurancePresent().equals(false)){
										removalInd =true;
									}
								}					
							}
							else if(addnSelectedInsuranceMap.containsKey(vendorInsuranceType)
									&& OrderConstants.UMBRELLA_COVERAGE_INSURANCE == vendorInsuranceType.intValue() ){
								if(insuranceVO.isCheckBoxValue()==true && providerInsuranceList.getVerifiedByServiceLive()==false
										&& removalInd.equals(false)){
									removalInd =true;
								}
								if(!(insuranceVO.getId().equals(0))){
									if(insuranceVO.getId().equals(16)&& removalInd.equals(false)
											&& providerInsuranceList.getInsurancePresent().equals(true)){
										removalInd =true;
									}else if(!(insuranceVO.getId().equals(16))
											&& providerInsuranceList.getInsurancePresent().equals(false)){
										removalInd =true;
									}
								}					
							}
							else if(addnSelectedInsuranceMap.containsKey(vendorInsuranceType)
									&& OrderConstants.PROFESSIONAL_LIABILITY_INS == vendorInsuranceType.intValue() ){
								if(insuranceVO.isCheckBoxValue()==true && providerInsuranceList.getVerifiedByServiceLive()==false
										&& removalInd.equals(false)){
									removalInd =true;
								}
								if(!(insuranceVO.getId().equals(0))){
									if(insuranceVO.getId().equals(16)&& removalInd.equals(false)
											&& providerInsuranceList.getInsurancePresent().equals(true)){
										removalInd =true;
									}else if(!(insuranceVO.getId().equals(16))
											&& providerInsuranceList.getInsurancePresent().equals(false)){
										removalInd =true;
									}
								}					
							}
							else if(addnSelectedInsuranceMap.containsKey(vendorInsuranceType)
									&& OrderConstants.ADULT_CARE_INS == vendorInsuranceType.intValue() ){
								if(insuranceVO.isCheckBoxValue()==true && providerInsuranceList.getVerifiedByServiceLive()==false
										&& removalInd.equals(false)){
									removalInd =true;
								}
								if(!(insuranceVO.getId().equals(0))){
									if(insuranceVO.getId().equals(16)&& removalInd.equals(false)
											&& providerInsuranceList.getInsurancePresent().equals(true)){
										removalInd =true;
									}else if(!(insuranceVO.getId().equals(16))
											&& providerInsuranceList.getInsurancePresent().equals(false)){
										removalInd =true;
									}
								}					
							}
							else if(addnSelectedInsuranceMap.containsKey(vendorInsuranceType)
									&& OrderConstants.NANNY_INS == vendorInsuranceType.intValue() ){
								if(insuranceVO.isCheckBoxValue()==true && providerInsuranceList.getVerifiedByServiceLive()==false
										&& removalInd.equals(false)){
									removalInd =true;
								}
								if(!(insuranceVO.getId().equals(0))){
									if(insuranceVO.getId().equals(16)&& removalInd.equals(false)
											&& providerInsuranceList.getInsurancePresent().equals(true)){
										removalInd =true;
									}else if(!(insuranceVO.getId().equals(16))
											&& providerInsuranceList.getInsurancePresent().equals(false)){
										removalInd =true;
									}
								}					
							}
							else if(addnSelectedInsuranceMap.containsKey(vendorInsuranceType)
									&& OrderConstants.OTHER_INS == vendorInsuranceType.intValue() ){
								if(insuranceVO.isCheckBoxValue()==true && providerInsuranceList.getVerifiedByServiceLive()==false
										&& removalInd.equals(false)){
									removalInd =true;
								}
								if(!(insuranceVO.getId().equals(0))){
									if(insuranceVO.getId().equals(16)&& removalInd.equals(false)
											&& providerInsuranceList.getInsurancePresent().equals(true)){
										removalInd =true;
									}else if(!(insuranceVO.getId().equals(16))
											&& providerInsuranceList.getInsurancePresent().equals(false)){
										removalInd =true;
									}
								}					
							}
							else if(addnSelectedInsuranceMap.containsKey(vendorInsuranceType)
									&& OrderConstants.PROFESSIONAL_INDEMNITY_INS == vendorInsuranceType.intValue() ){
								if(insuranceVO.isCheckBoxValue()==true && providerInsuranceList.getVerifiedByServiceLive()==false
										&& removalInd.equals(false)){
									removalInd =true;
								}
								if(!(insuranceVO.getId().equals(0))){
									if(insuranceVO.getId().equals(16)&& removalInd.equals(false)
											&& providerInsuranceList.getInsurancePresent().equals(true)){
										removalInd =true;
									}else if(!(insuranceVO.getId().equals(16))
											&& providerInsuranceList.getInsurancePresent().equals(false)){
										removalInd =true;
									}
								}					
							}
						}
					}
					
					
					if(removalInd.equals(true)){
						providersList2.add(providerVO);
					}
				}
				providers.removeAll(providersList2);
			}
						
		}else{
			if (getChain() != null){
				getChain().getFilteredList(bean, providers);
			}
		}
		
	}
}