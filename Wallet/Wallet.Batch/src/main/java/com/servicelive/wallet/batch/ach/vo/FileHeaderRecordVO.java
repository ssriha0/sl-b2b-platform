/**
 * 
 */
package com.servicelive.wallet.batch.ach.vo;

// TODO: Auto-generated Javadoc
/**
 * FileHeaderRecordVO.java - This class represents the file header record as specified by NACHA
 * 
 * @author Siva
 * @version 1.0
 */
public class FileHeaderRecordVO extends NachaGenericRecordVO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1411812187911653358L;

	/** The ach date time. */
	private String achDateTime;

	/** The file id modifier. */
	private String fileIdModifier;

	/**
	 * Gets the ach date time.
	 * 
	 * @return the ach date time
	 */
	public String getAchDateTime() {

		return achDateTime;
	}

	/**
	 * Gets the file id modifier.
	 * 
	 * @return the file id modifier
	 */
	public String getFileIdModifier() {

		return fileIdModifier;
	}

	/**
	 * Sets the ach date time.
	 * 
	 * @param achDateTime the new ach date time
	 */
	public void setAchDateTime(String achDateTime) {

		this.achDateTime = achDateTime;
	}

	/**
	 * Sets the file id modifier.
	 * 
	 * @param fileIdModifier the new file id modifier
	 */
	public void setFileIdModifier(String fileIdModifier) {

		this.fileIdModifier = fileIdModifier;
	}

}
