package com.newco.marketplace.webservices.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.RandomGUID;
import com.newco.marketplace.webservices.response.WSErrorInfo;

public class DraftValidatorUtil {
	
	private static final Logger logger = Logger.getLogger(DraftValidatorUtil.class);
	
	/**
	 * add warning as notes to ServiceOrder so that it will clear what caused SO to be in missed Info substatus
	 * @param so
	 * @param warnings
	 */	
	public static void addWarningsAsNotes(ServiceOrder so, List<WSErrorInfo> warnings) {
		
		List<ServiceOrderNote> soNotes = so.getSoNotes();
		if(soNotes == null){
			soNotes = new ArrayList<ServiceOrderNote>();
			so.setSoNotes(soNotes);
		}
		
		for(WSErrorInfo warning : warnings){
			ServiceOrderNote soNote = new ServiceOrderNote();
			soNote.setCreatedByName(OrderConstants.NEWCO_DISPLAY_SYSTEM);
			soNote.setCreatedDate(new Date());
			soNote.setNote(warning.getMessage());
			soNote.setSubject(warning.getCode());
			soNote.setRoleId(new Integer(WSConstants.BUYER_ROLE_ID));
			soNote.setEntityId(so.getBuyerResourceId());
			soNote.setNoteTypeId(new Integer(WSConstants.NOTE_TYPE_ID_GENERAL));
//			RandomGUID randomNo = new RandomGUID();
			try {
				//soNote.setNoteId(randomNo.generateGUID().longValue());
				soNotes.add(soNote);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}
