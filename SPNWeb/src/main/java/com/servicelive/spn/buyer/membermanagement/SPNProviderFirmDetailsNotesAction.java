package com.servicelive.spn.buyer.membermanagement;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.servicelive.domain.buyer.BuyerProviderFirmNote;
import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.buyer.BuyerServiceProNote;
import com.servicelive.domain.spn.detached.SPNProviderDetailsFirmHistoryRowDTO;
import com.servicelive.domain.spn.detached.SPNProviderDetailsNoteRowDTO;

import edu.emory.mathcs.backport.java.util.Collections;


/**
 * 
 * 
 * 
 */
@Validation
public class SPNProviderFirmDetailsNotesAction extends SPNProviderDetailsBaseAction implements ModelDriven<SPNProviderDetailsNotesModel> {

	private static final long serialVersionUID = 7141732697725385211L;
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
		Integer providerFirmId = getProviderFirmIdFromParameter();
		try {
			List<BuyerProviderFirmNote> notes =  getBuyerNotesService().getProviderFirmNotes(buyerId, providerFirmId);
			List<SPNProviderDetailsNoteRowDTO> list = new ArrayList<SPNProviderDetailsNoteRowDTO>();
			for(BuyerProviderFirmNote note : notes) {
				SPNProviderDetailsNoteRowDTO dto = mapToDto(note);
				list.add(dto);
			}
			list.addAll(getAllServiceProNotesForProviderFirm(buyerId,providerFirmId));
			Collections.sort(list);
			getModel().setNoteList(list);
			
		} catch (Exception e) {
			logger.error("Could not Retrieve All the Notes for buyerId " + buyerId + " And provider Firm Id = "+ providerFirmId , e);
		}
	}
	
	private List<SPNProviderDetailsNoteRowDTO> getAllServiceProNotesForProviderFirm(Integer buyerId, Integer providerFirmId) throws Exception {
		List<BuyerServiceProNote> notes =  getBuyerNotesService().getServiceProviderNotesForproviderFirm(buyerId, providerFirmId);
		List<SPNProviderDetailsNoteRowDTO> list = new ArrayList<SPNProviderDetailsNoteRowDTO>();
		for(BuyerServiceProNote note : notes) {
			SPNProviderDetailsNoteRowDTO dto = mapToDto(note);
			String serviceProviderName = null;
			if(note.getServiceProId().getContact() != null && note.getServiceProId().getContact().getFirstName() != null &&  note.getServiceProId().getContact().getLastName() != null) {
				serviceProviderName = note.getServiceProId().getContact().getFirstName() + " " + note.getServiceProId().getContact().getLastName();
			}
			dto.setServiceProIdForFirm(note.getServiceProId().getId());
			dto.setServiceProNameForFirm(serviceProviderName);
			list.add(dto);
		}
		return  list;
	}
	
	private SPNProviderDetailsNoteRowDTO mapToDto(BuyerServiceProNote note) {
		SPNProviderDetailsNoteRowDTO dto = new SPNProviderDetailsNoteRowDTO();
		dto.setDate(note.getModifiedDate());
		String modifiedBy = note.getModifiedBy();
		updateBuyerUserNameAndId(modifiedBy,dto);
		dto.setNotes(note.getComments());
		return dto;
	}

	private SPNProviderDetailsNoteRowDTO mapToDto(BuyerProviderFirmNote note) {
		SPNProviderDetailsNoteRowDTO dto = new SPNProviderDetailsNoteRowDTO();
		dto.setDate(note.getModifiedDate());
		String modifiedBy = note.getModifiedBy();
		updateBuyerUserNameAndId(modifiedBy,dto);
		dto.setNotes(note.getComments());
		return dto;
	}

	private void updateBuyerUserNameAndId(String modifiedBy,SPNProviderDetailsNoteRowDTO dto) {
		try {
			BuyerResource buyerResource = getBuyerServices().getBuyerResourceForUserName(modifiedBy);
			dto.setEnteredByID(buyerResource.getResourceId());
			dto.setEnteredByName(buyerResource.getContact().getFirstName() + " " + buyerResource.getContact().getLastName());
		} catch (Exception e) {
			logger.error("Could not Retrieve buyer Resource  Id for USER NAME =  " + modifiedBy, e);
		}
	}

	/**
	 * 
	 * @return String
	 */
	public String addNoteAjax() {
		Integer buyerId = getCurrentBuyerId();
		Integer providerFirmId = getProviderFirmIdFromParameter();
		String comments = getString("note");
		String modifiedBy = getCurrentBuyerResourceUserName();
		try {
			getBuyerNotesService().persistProviderFirmNote(buyerId, providerFirmId, modifiedBy, comments);
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
		Integer providerFirmId = getProviderFirmIdFromParameter();
		if (buyerId == null || providerFirmId == null) {
			
			logger.error("initHistoryChanges() did not find either buyerId or providerFirmId");
			return;
		}

				
		List<SPNProviderDetailsFirmHistoryRowDTO> providerFirmStateHistoryList = getProviderFirmStateService().findProviderFirmStatusHistoryAsDto(buyerId, providerFirmId);
		
		getModel().setFirmChangeHistoryList(providerFirmStateHistoryList);
		
		
		
	}


	
	
	/**
	 * @return the model
	 */
	public SPNProviderDetailsNotesModel getModel() {
		return model;
	}


}
