package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.interfaces.OrderConstants;

public class SOWPartsTabDTO extends SOWBaseTabDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6399868976810001341L;
	private List<SOWPartDTO> parts = new ArrayList<SOWPartDTO>();
	private String partsSuppliedBy = OrderConstants.SOW_SOW_PARTS_NOT_REQUIRED;
	private boolean containsTasks = false;
	private Boolean isFreshTask = false;
	private int deletePartIndex;
	private boolean addNewPart = false;
	
	

	public String getPartsSuppliedBy() {
		return partsSuppliedBy;
	}

	public void setPartsSuppliedBy(String partsSuppliedBy) {
		this.partsSuppliedBy = partsSuppliedBy;
	}

	public List<SOWPartDTO> getParts() {
	
		if(parts != null)
		{
			return parts;
		}
		else
		{
			return new ArrayList<SOWPartDTO>();
		}
	}

	public void setParts(List<SOWPartDTO> parts) {
		if (parts!=null && parts.size()>0)
		{
			containsTasks = true;
		}
		this.parts = parts;
	}

	public boolean isContainsTasks() {
		return containsTasks;
	}

	public void setContainsTasks(boolean containsTasks) {
		this.containsTasks = containsTasks;
	}

	public Boolean getIsFreshTask() {
		return isFreshTask;
	}

	public void setIsFreshTask(Boolean isFreshTask) {
		this.isFreshTask = isFreshTask;
	}

	public void addPart(SOWPartDTO part){
		if(parts == null){
			parts = new ArrayList<SOWPartDTO>();
			isFreshTask = true;
		}
		parts.add(part);
		containsTasks = true;
	}

	@Override
	public void validate() {
		List<IError> errorList = getErrorsOnly();
		setErrors(new ArrayList<IError>());
		setWarnings(new ArrayList<IWarning>());
		_doWorkFlowValidation();
		addZipCodeErrors(errorList);
		//clearAllErrors();
		//clearAllWarnings();
//		int count = 1;
//		set_index(count);
		if(isDoFullValidation())
		{
			List cleanErrorList = new ArrayList<IError>();
			List cleanWarningList = new ArrayList<IWarning>();
			List <SOWPartDTO>list = getParts();
			for(int i = 0 ; i< list.size(); i++)
			{
				set_index(i + 1); // setting index of array to in inner dto it will determine which part field is.
				if (list.get(i) != null )
					list.get(i).setTheResourceBundle(this.getTheResourceBundle());
				if(list.get(i) != null &&  list.get(i).validate(i + 1) != null)
					
					cleanErrorList.addAll( list.get(i).getErrors() );
					cleanWarningList.addAll( list.get(i).getWarnings());
			}
			if(getPartsSuppliedBy().equalsIgnoreCase("3") || getPartsSuppliedBy().equalsIgnoreCase("2")){
				cleanWarningList.clear();
			}
			if (getPartsSuppliedBy().equalsIgnoreCase(OrderConstants.SOW_SOW_PARTS_NOT_REQUIRED) || 
					getPartsSuppliedBy().equalsIgnoreCase(OrderConstants.SOW_SOW_PROVIDER_PROVIDES_PART)){
				cleanErrorList.clear();
				cleanWarningList.clear();
			}
			if(cleanErrorList.size() > 0 )
			{
				getErrors().addAll(cleanErrorList);
			}
			if(cleanWarningList.size() > 0)
			{
				getWarnings().addAll(cleanWarningList);
			}
		}
//
//
//		set_index(count++);
//		addError("Part:" + get_index() + " ----> " + "modelNumber", "Model Number is a required field.", OrderConstants.SOW_TAB_ERROR);
//		
//		set_index(count++);
//		addError("Part:" + get_index() + " ----> " + "modelNumber", "Model Number is a required field.", OrderConstants.SOW_TAB_ERROR);
		//return getErrors();
	}

	@Override
	public String getTabIdentifier() {
		return OrderConstants.SOW_PARTS_TAB;
	}
	
	public SOWPartDTO doSetup() {
		/* create empty part with it's chold objects and add in the list */
		SOWPartDTO part = new SOWPartDTO();
		part.setProviderBringparts(true);
		SOWContactLocationDTO location = new SOWContactLocationDTO();
		List<SOWPhoneDTO> phones = new ArrayList();
		SOWPhoneDTO  phone1 = new SOWPhoneDTO();
		phones.add(phone1);
		SOWPhoneDTO  phone2 = new SOWPhoneDTO();
		phones.add(phone2);			
		location.setPhones(phones);
		part.setPickupContactLocation(location);
		return part;
	}

	public int getDeletePartIndex() {
		return deletePartIndex;
	}

	public void setDeletePartIndex(int deletePartIndex) {
		this.deletePartIndex = deletePartIndex;
	}

	/**
	 * @return the addNewPart
	 */
	public boolean isAddNewPart() {
		return addNewPart;
	}

	/**
	 * @param addNewPart the addNewPart to set
	 */
	public void setAddNewPart(boolean addNewPart) {
		this.addNewPart = addNewPart;
	}
	
	

}
