/**
 * 
 */
package com.newco.marketplace.web.dto.provider;


/**
 * @author MTedder
 *
 */
public class LicensesDto extends BaseDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7654660856327879515L;

	private String vendorID;//vendor_id

	private int addCredentialToFile;

	
	/**
	 * @return the addCredentialToFile
	 */
	public int getAddCredentialToFile() {
		return addCredentialToFile;
	}

	/**
	 * @param addCredentialToFile the addCredentialToFile to set
	 */
	public void setAddCredentialToFile(int addCredentialToFile) {
		this.addCredentialToFile = addCredentialToFile;
	}

	/**
	 * @return the vendorID
	 */
	public String getVendorID() {
		if(vendorID == null){
			vendorID="";
		}
		return vendorID;
	}

	/**
	 * @param vendorID the vendorID to set
	 */
	public void setVendorID(String vendorID) {
		this.vendorID = vendorID;
	}


}//
