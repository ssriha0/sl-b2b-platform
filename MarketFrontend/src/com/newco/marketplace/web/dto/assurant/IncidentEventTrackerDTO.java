package com.newco.marketplace.web.dto.assurant;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.SOWBaseTabDTO;

public class IncidentEventTrackerDTO extends SOWBaseTabDTO
{
	private static final long serialVersionUID = 0L;

	private String incidentID;
	private String clientIncidentID;	
	private String soID;
	
	private String reasonText;
	private List<LabelValueBean> dropdownList;
	private String dropdownSelection;
	

	public String getReasonText()
	{
		return reasonText;
	}

	public void setReasonText(String reasonText)
	{
		this.reasonText = reasonText;
	}

	public String getDropdownSelection()
	{
		return dropdownSelection;
	}

	public void setDropdownSelection(String dropdownSelection)
	{
		this.dropdownSelection = dropdownSelection;
	}

	public List<LabelValueBean> getDropdownList()
	{
		return dropdownList;
	}

	public void setDropdownList(List<LabelValueBean> dropdownList)
	{
		this.dropdownList = dropdownList;
	}

	public String getIncidentID()
	{
		return incidentID;
	}

	public void setIncidentID(String incidentID)
	{
		this.incidentID = incidentID;
	}

	public String getClientIncidentID()
	{
		return clientIncidentID;
	}

	public void setClientIncidentID(String clientIncidentID)
	{
		this.clientIncidentID = clientIncidentID;
	}

	public String getSoID()
	{
		return soID;
	}

	public void setSoID(String soID)
	{
		this.soID = soID;
	}

	@Override
	public String getTabIdentifier()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate()
	{
		clearAllErrors();
		setErrors(new ArrayList<IError>());
		
		//First Name is required
        if(StringUtils.isBlank(getReasonText()))
        {
        	addError("Description", "You must enter text in the Description textarea before submitting.", OrderConstants.SOW_TAB_ERROR);
        }
        
		if(StringUtils.isBlank(getDropdownSelection()) || getDropdownSelection().equals("-1"))
		{
			addError("Note Type", "You must select a note type from the dropdown menu before submitting", OrderConstants.SOW_TAB_ERROR);			
		}
        
		
	}

}
