package com.servicelive.spn.buyer.membermanagement;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.servicelive.domain.spn.detached.SPNProviderDetailsFirmHistoryRowDTO;
import com.servicelive.domain.spn.detached.SPNProviderDetailsNoteRowDTO;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * 
 * 
 * 
 */
@Validation
public class SPNProviderDetailsNotesAction extends SPNProviderDetailsBaseAction implements ModelDriven<SPNProviderDetailsNotesModel> {

	private static final long serialVersionUID = 20100210L;
	private SPNProviderDetailsNotesModel model = new SPNProviderDetailsNotesModel();

	/**
	 * 
	 * @return String
	 */
	public String displayNotesAjax() {
		initNotes();
		initHistoryChanges();
		return "success_notes";
	}

	private void initNotes() {
		Integer buyerId = getCurrentBuyerId();
		Integer serviceProviderId = getServiceProviderId();
		try {
			List<SPNProviderDetailsNoteRowDTO> list =  getBuyerNotesService().getServiceProviderNotesAsDto(buyerId, serviceProviderId);
			Collections.sort(list);
			getModel().setNoteList(list);
			
		} catch (Exception e) {
			logger.error("Could not Init Notes for Service Provider  "+serviceProviderId +  " buyer id " + buyerId,e);
		}
	}

	/**
	 * 
	 * @return String
	 */
	public String addNoteAjax() {
		Integer buyerId = getCurrentBuyerId();
		Integer serviceProviderId = getServiceProviderId();
		String comments = getString("note");
		String modifiedBy = getCurrentBuyerResourceUserName();
		try {
			getBuyerNotesService().persistServiceProNote(buyerId, serviceProviderId, modifiedBy, comments);
		} catch (Exception e) {
			e.printStackTrace();
			//FIXME need to add logger
		}
		return "success_notes";
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
	public SPNProviderDetailsNotesModel getModel() {
		return model;
	}


}
