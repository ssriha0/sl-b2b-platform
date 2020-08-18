package com.newco.marketplace.webservices.validation.so.addNote;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.ABaseValidator;
import com.newco.marketplace.webservices.base.response.ValidatorResponse;

public class AddNoteValidator extends ABaseValidator implements OrderConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8335573526800012767L;

	public ValidatorResponse validate(Integer buyerId, ServiceOrderNote note) throws Exception {
	
		ValidatorResponse resp = new ValidatorResponse(VALID_RC, VALID_RC);
		
		if(note == null) {
			setError(resp, NOTE_OBJ_REQUIRED);
			return resp;
		}
		
		// check some required fields
		if (isEmpty(buyerId))
			setError(resp, BUYER_ID_REQUIRED);
		
		if (isEmpty(note.getRoleId()))
			setError(resp, NOTE_TYPE_REQUIRED);
		
		if(StringUtils.isEmpty(note.getNote()))
			setError(resp, NOTE_TEXT_REQUIRED);
				
		if(StringUtils.isEmpty(note.getModifiedBy()))
			setError(resp, MODIFIED_BY_REQUIRED);
	
		if(StringUtils.isEmpty(note.getSoId()))
			setError(resp, SERVICE_ORDER_NUMBER_REQUIRED);

		return resp;
	}
}
