package com.newco.marketplace.webservices.validation.so.part;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.webservices.base.ABaseValidator;
import com.newco.marketplace.webservices.response.WSErrorInfo;
import com.newco.marketplace.webservices.util.WSConstants;

public class PartValidator extends ABaseValidator{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6567846333943235185L;

	public static Map<String, List<WSErrorInfo>> validate(Part part) throws Exception {
		HashMap<String, List<WSErrorInfo>> map=new HashMap<String, List<WSErrorInfo>>();
		List<WSErrorInfo> err = new ArrayList<WSErrorInfo>();
		List<WSErrorInfo> war = new ArrayList<WSErrorInfo>();
		if(isNullOrEmpty(part.getSoId())){
			err.add(new WSErrorInfo(WSConstants.WSErrors.Codes.INVALID_SERVICEORDERID,WSConstants.WSErrors.Messages.INVALID_SERVICEORDERID));
		}
		if(isNullOrEmpty(part.getReferencePartId())){
			err.add(new WSErrorInfo(WSConstants.WSErrors.Codes.PART_REFERENCE_ID,WSConstants.WSErrors.Messages.PART_REFERENCE_ID));
		}
		if(!err.isEmpty())
			map.put("error",err );
		if(!war.isEmpty())
			map.put("warning",war );
		return map;
	}

	private static boolean isNullOrEmpty(Object o){
		return o==null || o.equals("");
	}
}
