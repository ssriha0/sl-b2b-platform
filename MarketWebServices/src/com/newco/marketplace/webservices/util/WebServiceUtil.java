package com.newco.marketplace.webservices.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.utils.RandomGUID;
import com.newco.marketplace.webservices.response.WSErrorInfo;
import com.newco.marketplace.webservices.validation.so.draft.CreateDraftValidator;

public class WebServiceUtil {
	
	private static final Logger logger = Logger.getLogger(WebServiceUtil.class);
	
	/**
	 * add warning as notes to ServiceOrder so that it will clear what caused SO to be in missed Info substatus
	 * @param so
	 * @param war
	 */	
	public static void addWarningAsNotes(ServiceOrder so, List<WSErrorInfo> war) {
		List<ServiceOrderNote> notes = new ArrayList<ServiceOrderNote>();
		for(WSErrorInfo warning : war){
			ServiceOrderNote soNote = new ServiceOrderNote();
			soNote.setCreatedByName("WebService");
			soNote.setCreatedDate(new Date());
			soNote.setNote(warning.getMessage());
			soNote.setSubject(warning.getCode());
			//TODO find constant for buyer role
			soNote.setRoleId(new Integer(3));
			soNote.setEntityId(so.getBuyerResourceId());
			//1 = support
			soNote.setNoteTypeId(new Integer(2));//General
//			RandomGUID randomNo = new RandomGUID();
//			try {
//				//soNote.setNoteId(randomNo.generateGUID().longValue());
//			} catch (Exception e) {
//				logger.error(e.getMessage());
//			}
			notes.add(soNote);
		}
		List<ServiceOrderNote> oldNotes = so.getSoNotes();
		if(oldNotes == null){
			oldNotes = new ArrayList<ServiceOrderNote>();
		}
		oldNotes.addAll(notes);
		so.setSoNotes(oldNotes);

			
	}
	
	

}
