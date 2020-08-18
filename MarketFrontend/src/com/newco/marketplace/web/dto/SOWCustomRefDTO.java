package com.newco.marketplace.web.dto;

import com.newco.marketplace.interfaces.OrderConstants;

public class SOWCustomRefDTO extends SOWBaseTabDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4382798966176892989L;
	private Integer refTypeId;
	private String refValue;
	private String refType;
	

	public Integer getRefTypeId() {
		return refTypeId;
	}

	public void setRefTypeId(Integer refTypeId) {
		this.refTypeId = refTypeId;
	}

	public String getRefValue() {
		if (refValue != null) {
			refValue = refValue.trim();
		}
		return refValue;
	}

	public void setRefValue(String refValue) {
		this.refValue = refValue;
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate() {
			
		/*if(( refValue == null ||refValue.equals("")) && refTypeId!=null)
		{
			addError(getTheResourceBundle().getString("Custom_Reference_Value"), 
					 getTheResourceBundle().getString("Custome_Reference_Validation_Msg")
					,OrderConstants.SOW_TAB_ERROR );
		}*/

	}

}
