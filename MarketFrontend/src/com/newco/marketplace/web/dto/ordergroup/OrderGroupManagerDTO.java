package com.newco.marketplace.web.dto.ordergroup;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.LocationUtils;
import com.newco.marketplace.web.dto.SOWBaseTabDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.utils.SLStringUtils;

public class OrderGroupManagerDTO extends SOWBaseTabDTO
{
	private Logger logger = Logger.getLogger(OrderGroupManagerDTO.class);

	private static final long serialVersionUID = 1L;
	
	private List<LabelValueBean> searchTypeDropdowns;
	private String searchType;
	
	private List<LabelValueBean> statusDropdowns;
	private Integer status;
	
	private String searchTerm;
	
	
	// Grouped Orders Panel
	private List<OrderGroupDTO> orderGroups;
	
	//Ungrouped Orders Panel
	private List<ServiceOrderDTO> ungroupedOrders;
	
	
	public boolean isValidAddress()
	{
		if(isSearchTermFieldBlank())
		{
			return false;
		}
		
		return true;
	}
	
	public boolean isValidName()
	{
		if(isSearchTermFieldBlank())
		{
			return false;
		}
		
		searchTerm.trim();
		int spaceIndex = searchTerm.indexOf(" ");
		int commaIndex = searchTerm.indexOf(",");
		
		if(spaceIndex < 0 && commaIndex < 0)
		{
			addError("searchTerm",
					"Enter both firstname and lastname, separated by either a space or a comma. Examples: 'Joe, Smith' or 'Smith Joe",
					OrderConstants.SOW_TAB_ERROR);
			return false;			
		}
		
		
		String firstPart="", secondPart="";
		if(spaceIndex > 0)
		{
			firstPart = searchTerm.substring(0, spaceIndex);
			secondPart = searchTerm.substring(spaceIndex + 1);
		}
		else if(commaIndex > 0)
		{
			firstPart = searchTerm.substring(0, commaIndex);
			secondPart = searchTerm.substring(commaIndex + 1);			
		}
		
		if(firstPart.length() == 0 || secondPart.length() == 0)
		{
			addError("searchTerm",
					"Enter both firstname and lastname, separated by either a space or a comma",
					OrderConstants.SOW_TAB_ERROR);
			return false;
		}
		
		return true;		
	}
	
	public boolean isValidSoID()
	{
		if(isSearchTermFieldBlank())
		{
			return false;
		}

		int minLength=10; // TODO get this from some other constant
		if(searchTerm.length() < minLength)
		{
			addError("searchTerm",
					"Service Order ID is too short. Minimum characters is " + minLength,
					OrderConstants.SOW_TAB_ERROR);			
			
			return false;
		}
		
		return true;		
	}
	
	public boolean isValidPhone()
	{
		logger.info("Phone Number = [" + searchTerm + "]");
		
		int minLength=10; // TODO get this from some other constant
		if(searchTerm.length() < minLength)
		{
			addError("searchTerm",
					"Phone number needs to be a" + minLength + " digit number",
					OrderConstants.SOW_TAB_ERROR);			
			
			return false;
		}
		
		if(SLStringUtils.IsParsableNumber(searchTerm) == false)
		{
			addError("searchTerm",
					"Phone number needs to be a " + minLength + " digit number.",
					OrderConstants.SOW_TAB_ERROR);			
			
			return false;
		}
		
		
		return true;		
	}
	
	public boolean isValidZip() {
		int validZipLength = 5; // TODO get this from some other constant
		if(searchTerm.length() != validZipLength) {
			addError("searchTerm", "Zip code needs to be a " + validZipLength + " digit number.", OrderConstants.SOW_TAB_ERROR);
			return false;
		} else if(!SLStringUtils.IsParsableNumber(searchTerm)) {
			addError("searchTerm", "Zip code needs to be a " + validZipLength + " digit number.", OrderConstants.SOW_TAB_ERROR);
			return false;
		} else if (Constants.LocationConstants.ZIP_VALID != LocationUtils.checkIfZipAndStateValid(searchTerm, null)) {
			addError("searchTerm", "Zip code '"+searchTerm+"' is not valid.", OrderConstants.SOW_TAB_ERROR);
			return false;
		}
		
		return true;
	}
	
	public boolean isSearchTermFieldBlank()
	{		
		if(SLStringUtils.isNullOrEmpty(searchTerm))
		{
			addError("searchTerm",
					"Please enter a Search Term.",
					OrderConstants.SOW_TAB_ERROR);			
			return true;
		}
		return false;
	}
	
	public List<ServiceOrderDTO> getUngroupedOrders() {
		return ungroupedOrders;
	}
	public void setUngroupedOrders(List<ServiceOrderDTO> ungroupedOrders) {
		this.ungroupedOrders = ungroupedOrders;
	}
	public String getSearchTerm()
	{
		return searchTerm;
	}
	public void setSearchTerm(String searchTerm)
	{
		if (searchTerm != null) {
			searchTerm = searchTerm.trim();
		}
		this.searchTerm = searchTerm;
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
		if (status == -1) {
			addError("status", "Please select status.", OrderConstants.SOW_TAB_ERROR);
		}
		if (searchType.equals("-1")) {
			addError("searchType", "Please select search type.", OrderConstants.SOW_TAB_ERROR);
		}
		if (org.apache.commons.lang.StringUtils.isBlank(searchTerm)) {
			addError("searchTerm", "Please specify search term.", OrderConstants.SOW_TAB_ERROR);
		} else {
			if (searchType.equals("4")) {
				// No validations required for "name" field
			} else if (searchType.equals("1")) {
				isValidPhone();
			} else if (searchType.equals("2")) {
				isValidZip();
			}
		}
	}

	public String getSearchType()
	{
		return searchType;
	}

	public void setSearchType(String searchType)
	{
		this.searchType = searchType;
	}

	public List<OrderGroupDTO> getOrderGroups()
	{
		return orderGroups;
	}

	public void setOrderGroups(List<OrderGroupDTO> orderGroups)
	{
		this.orderGroups = orderGroups;
	}

	public List<LabelValueBean> getStatusDropdowns()
	{
		return statusDropdowns;
	}

	public void setStatusDropdowns(List<LabelValueBean> statusDropdowns)
	{
		this.statusDropdowns = statusDropdowns;
	}

	public List<LabelValueBean> getSearchTypeDropdowns()
	{
		return searchTypeDropdowns;
	}

	public void setSearchTypeDropdowns(List<LabelValueBean> searchTypeDropdowns)
	{
		this.searchTypeDropdowns = searchTypeDropdowns;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}


}
