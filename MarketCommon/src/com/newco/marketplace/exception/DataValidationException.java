package com.newco.marketplace.exception;


 
/**
 * @author dgold1
 *
 * * 
 * Revision History
 * ------------------------------------------------------------------------------
 * dgold1 Aug 9, 2006 - Initial release
 *
 *
 */
public class DataValidationException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String value;
	 
	public DataValidationException(String msg) {
		super(msg);
	}
	
	public DataValidationException(String name, String value) {
		super(null);
		this.name = name;
		this.value = value;
	}
	
	public DataValidationException(String msg, String name, String value) {
		super(msg);
		this.name = name;
		this.value = value;
	}

	public DataValidationException(String msg, Throwable ex) {
		super(msg, ex);
	}
	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the value.
	 * @return String
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the value.
	 * @param value The value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
