package com.newco.marketplace.webservices.validation.so.part;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.webservices.base.ABaseValidator;
import com.newco.marketplace.webservices.response.WSErrorInfo;
import com.newco.marketplace.webservices.util.WSConstants;

public class PartValidatorTest{

	private PartValidator validator;
	
	@SuppressWarnings("static-access")
	@Test
	public void testValidate() {
		validator = new PartValidator();
		Part part = new Part();
		part.setSoId("541-5430-5650-17");
		part.setReferencePartId("12345");
		List<WSErrorInfo> err = new ArrayList<WSErrorInfo>();
		List<WSErrorInfo> war = new ArrayList<WSErrorInfo>();
		Map<String, List<WSErrorInfo>> map = new HashMap<String, List<WSErrorInfo>>();
		try {
			map = validator.validate(part);
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean error =  map.get("error") != null;
		boolean warning =  map.get("warning") != null;
		assertFalse("Either So Id or Reference Id is nul",error);
		assertFalse("Warnings exists",warning);
	}

}
