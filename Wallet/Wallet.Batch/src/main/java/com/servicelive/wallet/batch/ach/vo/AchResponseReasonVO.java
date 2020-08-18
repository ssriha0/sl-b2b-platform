/**
 * 
 */
package com.servicelive.wallet.batch.ach.vo;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * 
 * 
 * @author schavda
 */
public class AchResponseReasonVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4714285430845933276L;

	/** The category desc. */
	private String categoryDesc;

	/** The category id. */
	private Integer categoryId;

	/** The file type code. */
	private String fileTypeCode;

	/** The file type id. */
	private Integer fileTypeId;

	/** The reason code. */
	private String reasonCode;

	/** The reason desc. */
	private String reasonDesc;

	/** The reason id. */
	private Integer reasonId;

	/** The reversal ind. */
	private Integer reversalInd;

	/**
	 * Gets the category desc.
	 * 
	 * @return the category desc
	 */
	public String getCategoryDesc() {

		return categoryDesc;
	}

	/**
	 * Gets the category id.
	 * 
	 * @return the category id
	 */
	public Integer getCategoryId() {

		return categoryId;
	}

	/**
	 * Gets the file type code.
	 * 
	 * @return the file type code
	 */
	public String getFileTypeCode() {

		return fileTypeCode;
	}

	/**
	 * Gets the file type id.
	 * 
	 * @return the file type id
	 */
	public Integer getFileTypeId() {

		return fileTypeId;
	}

	/**
	 * Gets the reason code.
	 * 
	 * @return the reason code
	 */
	public String getReasonCode() {

		return reasonCode;
	}

	/**
	 * Gets the reason desc.
	 * 
	 * @return the reason desc
	 */
	public String getReasonDesc() {

		return reasonDesc;
	}

	/**
	 * Gets the reason id.
	 * 
	 * @return the reason id
	 */
	public Integer getReasonId() {

		return reasonId;
	}

	/**
	 * Gets the reversal ind.
	 * 
	 * @return the reversal ind
	 */
	public Integer getReversalInd() {

		return reversalInd;
	}

	/**
	 * Sets the category desc.
	 * 
	 * @param categoryDesc the new category desc
	 */
	public void setCategoryDesc(String categoryDesc) {

		this.categoryDesc = categoryDesc;
	}

	/**
	 * Sets the category id.
	 * 
	 * @param categoryId the new category id
	 */
	public void setCategoryId(Integer categoryId) {

		this.categoryId = categoryId;
	}

	/**
	 * Sets the file type code.
	 * 
	 * @param fileTypeCode the new file type code
	 */
	public void setFileTypeCode(String fileTypeCode) {

		this.fileTypeCode = fileTypeCode;
	}

	/**
	 * Sets the file type id.
	 * 
	 * @param fileTypeId the new file type id
	 */
	public void setFileTypeId(Integer fileTypeId) {

		this.fileTypeId = fileTypeId;
	}

	/**
	 * Sets the reason code.
	 * 
	 * @param reasonCode the new reason code
	 */
	public void setReasonCode(String reasonCode) {

		this.reasonCode = reasonCode;
	}

	/**
	 * Sets the reason desc.
	 * 
	 * @param reasonDesc the new reason desc
	 */
	public void setReasonDesc(String reasonDesc) {

		this.reasonDesc = reasonDesc;
	}

	/**
	 * Sets the reason id.
	 * 
	 * @param reasonId the new reason id
	 */
	public void setReasonId(Integer reasonId) {

		this.reasonId = reasonId;
	}

	/**
	 * Sets the reversal ind.
	 * 
	 * @param reversalInd the new reversal ind
	 */
	public void setReversalInd(Integer reversalInd) {

		this.reversalInd = reversalInd;
	}

}
