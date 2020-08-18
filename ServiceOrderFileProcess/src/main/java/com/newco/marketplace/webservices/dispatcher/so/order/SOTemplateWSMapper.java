package com.newco.marketplace.webservices.dispatcher.so.order;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.BuyerSOTemplateContactDTO;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.webservices.dto.serviceorder.ContactRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.Document;

public class SOTemplateWSMapper {

		public static CreateDraftRequest mapTemplateToCreateDraftRequest(CreateDraftRequest request, BuyerSOTemplateDTO dto) {
			
			request.setBuyerTermsCond(dto.getTerms());
			request.setSowDs(dto.getOverview() + "\n" + request.getSowDs());
			request.setSowTitle(dto.getTitle() + "-" + (request.getSowTitle() != null ? request.getSowTitle() : ""));
			request.setPartsSuppliedBy(dto.getPartsSuppliedBy());
			
			request.setProviderInstructions(request.getProviderInstructions() + dto.getSpecialInstructions());
			if (dto.getDocumentTitles() != null && dto.getDocumentTitles().size() > 0) {
				request.setDocuments(mapDocumentListToDocumentRequest(dto.getDocumentTitles()));
			}
			if (dto.getBuyerContact() != null) {
				request.setServiceContactAlt(mapBuyerSOTemplateContactToContactRequest(dto.getAltBuyerContact()));
			}
			if (dto.getConfirmServiceTime() != null) {
				request.setConfirmServiceTime(dto.getConfirmServiceTime());
			}
			return request;
		}
		
		public static ContactRequest mapBuyerSOTemplateContactToContactRequest(BuyerSOTemplateContactDTO dto) {
			ContactRequest contact = new ContactRequest();
			contact.setBusinessName(dto.getCompanyName());
			contact.setContactTypeId(Integer.valueOf(1));
			contact.setEmail(dto.getEmail());
			contact.setFirstName(dto.getIndividualName());
			return contact;			
		}
		
		public static List<Document> mapDocumentListToDocumentRequest(List<String> titles) {
			List<Document> docs = new ArrayList<Document>();
			for (String title : titles) {
				Document document = new Document();
				document.setTitle(title);
				docs.add(document);
			}
			return docs;
		}
}
