package com.servicelive.spn.core;

/**
 * 
 *
 */
public class SPNDropdownOption
{
	private String value;
	private String label;

	/**
	 * 
	 */
	public SPNDropdownOption()
	{
		super();
	}
	
	/**
	 * 
	 * @param label
	 * @param value
	 */
	public SPNDropdownOption(String label, String value)
	{
		this.label = label;
		this.value = value;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	
	
	
}
