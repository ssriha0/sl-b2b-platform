/**
 * 
 */
package com.newco.marketplace.dto.vo.ach;

/**  
* FileHeaderRecordVO.java - This class represents the file header record as specified by NACHA
* 
* @author  Siva
* @version 1.0  
*/
public class FileHeaderRecordVO extends NachaGenericRecordVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1411812187911653358L;
	private String fileIdModifier;
	private String achDateTime;

	public String getFileIdModifier() {
		return fileIdModifier;
	}

	public void setFileIdModifier(String fileIdModifier) {
		this.fileIdModifier = fileIdModifier;
	}

	public String getAchDateTime() {
		return achDateTime;
	}

	public void setAchDateTime(String achDateTime) {
		this.achDateTime = achDateTime;
	}
	
}
