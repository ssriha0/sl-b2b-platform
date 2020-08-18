package com.servicelive.common.properties;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class ApplicationPropertiesVO.
 */
public class ApplicationPropertiesVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7989673075757516457L;

	/** The app key. */
	private String appKey;

	/** The app value. */
	private String appValue;
	
	/** The app file. */
	private byte[] appFile;

	/**
	 * Gets the app key.
	 * 
	 * @return the app key
	 */
	public String getAppKey() {

		return appKey;
	}

	/**
	 * Gets the app value.
	 * 
	 * @return the app value
	 */
	public String getAppValue() {

		return appValue;
	}

	/**
	 * Sets the app key.
	 * 
	 * @param appKey the new app key
	 */
	public void setAppKey(String appKey) {

		this.appKey = appKey;
	}

	/**
	 * Sets the app value.
	 * 
	 * @param appValue the new app value
	 */
	public void setAppValue(String appValue) {

		this.appValue = appValue;
	}

	public byte[] getAppFile() {
		return appFile;
	}

	public void setAppFile(byte[] appFile) {
		this.appFile = appFile;
	}

	
}
