package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.interfaces.OrderConstants;

public class SOPartsListDTO extends SOWBaseTabDTO{
	
	/**
	 * Serial version of the object
	 */
	private static final long serialVersionUID = 6351558109318801430L;
	
	private List<SOPartsDTO> partsList = new ArrayList<SOPartsDTO>();

	public List<SOPartsDTO> getPartsList() {
		return partsList;
	}

	public void setPartsList(List<SOPartsDTO> partsList) {
		this.partsList = partsList;
	}
	
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return "SOPartsListDTO";
	}
	
	public void validate(){
		
		List<SOPartsDTO> soPartsDTOs = getPartsList();
		int i = 0;
		//Get List of ref names
		for (SOPartsDTO soPartDTO : soPartsDTOs) {
			
			if(soPartDTO.getShippingCarrierId() == null && (StringUtils.isNotBlank(soPartDTO.getShippingTrackingNumber()) || soPartDTO.getShipDate() != null)){
				addError("partsList[" + i + "]shippingCarrierId" , "Please select a Shipping carrier " , OrderConstants.SOW_TAB_ERROR);
			}
			if(soPartDTO.getCoreReturnCarrierId() == null && StringUtils.isNotBlank(soPartDTO.getCoreReturnTrackingNumber())){
				addError("partsList[" + i + "]coreReturnCarrierId" , "Please select a core return Shipping carrier " , OrderConstants.SOW_TAB_ERROR);
			}
			i++;
		}
		
		List<IError> errorList = getErrorsOnly();
		setErrors(errorList);
	}
	
}
