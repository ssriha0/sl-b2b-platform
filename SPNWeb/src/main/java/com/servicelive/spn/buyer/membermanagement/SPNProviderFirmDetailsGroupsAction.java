package com.servicelive.spn.buyer.membermanagement;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.servicelive.domain.lookup.LookupPerformanceLevel;

/**
 * 
 * 
 * 
 */
@Validation
public class SPNProviderFirmDetailsGroupsAction extends SPNProviderDetailsBaseAction implements ModelDriven<SPNProviderDetailsGroupsModel> {

	private static final long serialVersionUID = 7358749808054349404L;
	private SPNProviderDetailsGroupsModel model = new SPNProviderDetailsGroupsModel();


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
		String modifiedBy = "scarpe5@searhc.com"; //FIXME need to get from user
		String performanceLevelChangeComment = getString("comments");

		try {
			getServiceProviderStateService().updateServiceProviderStatusPerformanceLevel(spnId, serviceProviderId, performanceLevelChangeComment, performanceLevel, modifiedBy);
		} catch (Exception e) {
			e.printStackTrace();
			//FIXME need to write properly to log
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
