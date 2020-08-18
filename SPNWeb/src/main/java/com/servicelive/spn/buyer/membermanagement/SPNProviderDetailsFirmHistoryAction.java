package com.servicelive.spn.buyer.membermanagement;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.servicelive.domain.spn.detached.SPNProviderDetailsFirmHistoryRowDTO;

/**
 * 
 */
@Validation
public class SPNProviderDetailsFirmHistoryAction extends SPNProviderDetailsBaseAction implements ModelDriven<SPNProviderDetailsFirmHistoryModel> {

	private static final long serialVersionUID = 20100210L;
	private SPNProviderDetailsFirmHistoryModel model = new SPNProviderDetailsFirmHistoryModel();

	public String displayFirmHistoryAjax() {
		initHistoryChanges();
		return "success_firm_history";
	}

	private void initHistoryChanges() {
		List<SPNProviderDetailsFirmHistoryRowDTO> list = new ArrayList<SPNProviderDetailsFirmHistoryRowDTO>();
		getModel().setFirmChangeHistoryList(list);

		Integer buyerId = getCurrentBuyerId();
		Integer serviceProviderId = getServiceProviderId();
		if (buyerId == null || serviceProviderId == null) {
			//FIXME add logger call
			return;
		}

		list.addAll(getServiceProviderStateService().findServiceProviderStateHistoryAsDto(buyerId, serviceProviderId));

	}

	/**
	 * @return the model
	 */
	public SPNProviderDetailsFirmHistoryModel getModel() {
		return model;
	}
}
