package com.newco.marketplace.web.dto;

import java.util.List;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.utils.SLStringUtils;

public class LoginAdditionalInfoDTO extends SOWBaseTabDTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -927605210264867586L;
	private String taxNumber;
	private String dateOfBirth;
	
	// Dropdown menu options
	private List<DropdownOptionDTO> monthOptions = null;	
	private List<DropdownOptionDTO> dayOptions = null;
	private String selectedMonth;
	private String selectedDay;
	
	private String year;
	
	@Override
	public void validate()
	{
				
		// Let's trim the values.
		if(year != null)
			year = year.trim();
		if(selectedDay != null)
			selectedDay = selectedDay.trim();
		if(selectedMonth != null)
			selectedMonth = selectedMonth.trim();
		if(taxNumber != null)
			taxNumber = taxNumber.trim();
		
		// See if any of the Date of Birth fields have been changed from the default
		boolean dobAttempt=false;
		if(!SLStringUtils.isNullOrEmpty(selectedDay) && !selectedDay.equals("-1"))
			dobAttempt = true;
		else if(!SLStringUtils.isNullOrEmpty(selectedMonth) && !selectedMonth.equals("-1"))
			dobAttempt = true;
		else if(!SLStringUtils.isNullOrEmpty(year))
			dobAttempt = true;
		
		if(SLStringUtils.isNullOrEmpty(taxNumber) || !dobAttempt)
		{
			addError("Additional Info", "At least one additional form of identification is required.", OrderConstants.SOW_TAB_ERROR);
		}
		
		
		
		if(!SLStringUtils.isNullOrEmpty(taxNumber))
		{
			if(!SLStringUtils.IsParsableNumber(taxNumber))
			{
				addError("EIN or SSN", "EIN/SSN needs to be 9 digits, no characters or dashes", OrderConstants.SOW_TAB_ERROR);
			}
			else if(taxNumber.length() != 9)
			{
				addError("EIN or SSN", "EIN/SSN needs to be 9 digits, no characters or dashes", OrderConstants.SOW_TAB_ERROR);				
			}
		}
		
		if(!selectedDay.equals("-1") || !selectedMonth.equals("-1") || !SLStringUtils.isNullOrEmpty(year))
		{
			if(!SLStringUtils.IsParsableNumber(year) || year.length() != 4)
			{
				addError("Year of birth", "You must enter a valid 4 digit year (YYYY)", OrderConstants.SOW_TAB_ERROR);
			}
			
			if(selectedDay.equals("-1"))
			{
				addError("Day of birth", "You must select a day", OrderConstants.SOW_TAB_ERROR);				
			}
			
			if(selectedMonth.equals("-1"))
			{
				addError("Month of birth", "You must select a month", OrderConstants.SOW_TAB_ERROR);				
			}
		}
		
		
	}

	
	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}




	public String getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getTaxNumber() {
		return taxNumber;
	}


	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}


	public List<DropdownOptionDTO> getMonthOptions() {
		return monthOptions;
	}


	public void setMonthOptions(List<DropdownOptionDTO> monthOptions) {
		this.monthOptions = monthOptions;
	}


	public List<DropdownOptionDTO> getDayOptions() {
		return dayOptions;
	}


	public void setDayOptions(List<DropdownOptionDTO> dayOptions) {
		this.dayOptions = dayOptions;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getSelectedMonth() {
		return selectedMonth;
	}


	public void setSelectedMonth(String selectedMonth) {
		this.selectedMonth = selectedMonth;
	}


	public String getSelectedDay() {
		return selectedDay;
	}


	public void setSelectedDay(String selectedDay) {
		this.selectedDay = selectedDay;
	}
	
}
