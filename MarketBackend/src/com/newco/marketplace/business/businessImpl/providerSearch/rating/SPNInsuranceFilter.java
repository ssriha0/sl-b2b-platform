package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import java.util.ArrayList;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.providerSearch.InsuranceResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.2 $ $Author: glacy $ $Date: 2008/05/02 21:23:38 $
 */

/*
 * Maintenance History: See bottom of file.
 */
public class SPNInsuranceFilter extends RatingCalculator {

	public SPNInsuranceFilter(){
		super(null);
	}
	
	public SPNInsuranceFilter(RatingCalculator calc){
		super(calc);
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.businessImpl.providerSearch.rating.RatingCalculator#getFilteredList(com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean, java.util.ArrayList)
	 */
	@Override
	public void getFilteredList(RatingParameterBean bean,
			ArrayList<ProviderResultVO> providers) {

		if (bean instanceof SPNInsuranceParameterBean) {
			SPNInsuranceParameterBean insuranceBean = (SPNInsuranceParameterBean) bean;
			Double genInsMinAmt = insuranceBean.getGeneralInsuranceMinAmount();
			Double autoInsMinAmt = insuranceBean.getAutoInsuranceMinAmount();
			Double workmanCompInsMinAmt = insuranceBean.getWorkmanCompInsuranceMinAmount();
			
			
			if (null == genInsMinAmt) {
				genInsMinAmt = new Double(0.0);
			}
			if (null == autoInsMinAmt) {
				autoInsMinAmt = new Double(0.0);
			}
			if (null == workmanCompInsMinAmt) {
				workmanCompInsMinAmt = new Double(0.0);
			}
			
			ProviderResultVO providerResultVO = null;
			int listSize = providers.size();
			int j = 0;
			for (int i = 0; i < listSize; i++) {
				providerResultVO = providers.get(j);
				
				if ( (null == providerResultVO.getVendorInsuranceTypes() || 
					  providerResultVO.getVendorInsuranceTypes().size() == 0) && 
					(insuranceBean.isAutoInsuranceChecked() || 
					 insuranceBean.isGeneralInsuranceChecked() ||
					 insuranceBean.isWorkmanCompInsuranceChecked()) 
				   ) {
					providers.remove(j);
					j--;
				} else {
					for (InsuranceResultVO insResultVO : providerResultVO.getVendorInsuranceTypes()) {
						
						Double insAmt = insResultVO.getAmount();
						boolean remove = false;
						
						switch (insResultVO.getVendorInsuranceTypes().intValue()) {
							case Constants.SPN.CRITERIA_TYPE.VENDOR_GENERAL_INS:
								if (null != insAmt &&
									insuranceBean.isGeneralInsuranceChecked() &&
									genInsMinAmt.doubleValue() > insAmt.intValue()) {
									remove = true;
								}
								break;
							case Constants.SPN.CRITERIA_TYPE.VENDOR_AUTO_INS:
								if (null != insAmt && 
									insuranceBean.isAutoInsuranceChecked() &&
									autoInsMinAmt.doubleValue() > insAmt.intValue()) {
									
									remove = true;
								}
								break;
							case Constants.SPN.CRITERIA_TYPE.VENDOR_WORKMAN_INS:
								if (null != insAmt && 
									insuranceBean.isWorkmanCompInsuranceChecked() &&
									workmanCompInsMinAmt.doubleValue() > insAmt.intValue()) {
									remove = true;
								}
								break;
							default:
								break;
						}
						
						if (remove) {
							// remove provider from list and break out of insurance loop
							providers.remove(j);
							j--;
							break;
						}
					}
				}
				j++;
			}
		} else {
			if (getChain() != null){
				getChain().getFilteredList(bean, providers);
			}
		}

	}

}

/*
 * Maintenance History:
 * $Log: SPNInsuranceFilter.java,v $
 * Revision 1.2  2008/05/02 21:23:38  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.1.2.3  2008/04/25 14:46:08  mhaye05
 * removed useless logging
 *
 * Revision 1.1.2.2  2008/04/18 22:46:04  mhaye05
 * updated to allow for spn save and the running of spn campaigns
 *
 * Revision 1.1.2.1  2008/04/18 00:23:53  mhaye05
 * added logic for inviting service pros to spn
 *
 */