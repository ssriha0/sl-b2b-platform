package com.servicelive.spn.buyer.membermanagement;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.servicelive.domain.lookup.LookupPerformanceLevel;

/**
 * 
 * 
 * 
 */
@Validation
public class SPNProviderDetailsGroupsAction extends SPNProviderDetailsBaseAction implements ModelDriven<SPNProviderDetailsGroupsModel> {
	private static final long serialVersionUID = -20100202L;

	private SPNProviderDetailsGroupsModel model = new SPNProviderDetailsGroupsModel();

	/**
	 * 
	 * @return String
	 */
	public String displayGroupsAjax() {
		
		try {
			List<LookupPerformanceLevel> performanceLevelList = getLookupService().getAllPerformanceLevels();
			Collections.reverse(performanceLevelList);
			getModel().setPerformanceLevels(performanceLevelList);
		} catch (Exception e) {
			e.printStackTrace();
			//FIXME need to add using log4j
		}
		
		
		
		String networkGroupStr = this.getString("networkGroup");
		Integer networkGroupID = null;
		if(StringUtils.isNumeric(networkGroupStr));
		{
			networkGroupID = Integer.parseInt(networkGroupStr);
		}
		
		getModel().setNetworkGroup(networkGroupID);
		
		return "success_network_group";
	}
	
	

	/**
	 * 
	 * @return String
	 */
	public String changeGroupsAjax() {
		Integer serviceProviderId = getInteger("vendorResourceId");
		Integer spnId = getInteger("networkId");

		// User selected Data
		Integer newGroup = getInteger("newGroup");
		LookupPerformanceLevel performanceLevel = new LookupPerformanceLevel();
		performanceLevel.setId(newGroup);
		String modifiedBy = getCurrentBuyerResourceUserName();
		String performanceLevelChangeComment = getString("comments");

		try {
			getServiceProviderStateService().updateServiceProviderStatusPerformanceLevel(spnId, serviceProviderId, performanceLevelChangeComment, performanceLevel, modifiedBy);
		} catch (Exception e) {
			logger.error(" Could not change the Group for " +serviceProviderId + " for SPN Id = "+spnId , e);
		}
		return "success_network_group";
	}

	/**
	 * @return the model
	 */
	public SPNProviderDetailsGroupsModel getModel() {
		return model;
	}
}
