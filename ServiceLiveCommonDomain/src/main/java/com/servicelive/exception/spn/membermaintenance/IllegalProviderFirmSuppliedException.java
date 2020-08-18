/**
 * 
 */
package com.servicelive.exception.spn.membermaintenance;

/**
 * @author hoza
 *
 */
public class IllegalProviderFirmSuppliedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1425645607799185466L;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IllegalProviderFirmSuppliedException [getMessage()="
				+ getMessage() + ", toString()=" + super.toString() + "]";
	}

	/**
	 * 
	 */
	public IllegalProviderFirmSuppliedException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public IllegalProviderFirmSuppliedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public IllegalProviderFirmSuppliedException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public IllegalProviderFirmSuppliedException(Throwable cause) {
		super(cause);
	}
	

}
