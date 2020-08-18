package com.servicelive.wallet.batch.ach.vo;

import java.io.Serializable;
import java.sql.Timestamp;

// TODO: Auto-generated Javadoc
/**
 * The Class NachaProcessLogHistoryVO.
 */
public class NachaProcessLogHistoryVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4973522141560106165L;

	/** The ach process log history id. */
	private long achProcessLogHistoryId;

	/** The ach process log id. */
	private long achProcessLogId;

	/** The ach process status id. */
	private long achProcessStatusId;

	/** The comments. */
	private String comments;

	/** The updated by. */
	private String updatedBy;

	/** The updated date. */
	private Timestamp updatedDate;

	/**
	 * Gets the ach process log history id.
	 * 
	 * @return the ach process log history id
	 */
	public long getAchProcessLogHistoryId() {

		return achProcessLogHistoryId;
	}

	/**
	 * Gets the ach process log id.
	 * 
	 * @return the ach process log id
	 */
	public long getAchProcessLogId() {

		return achProcessLogId;
	}

	/**
	 * Gets the ach process status id.
	 * 
	 * @return the ach process status id
	 */
	public long getAchProcessStatusId() {

		return achProcessStatusId;
	}

	/**
	 * Gets the comments.
	 * 
	 * @return the comments
	 */
	public String getComments() {

		return comments;
	}

	/**
	 * Gets the updated by.
	 * 
	 * @return the updated by
	 */
	public String getUpdatedBy() {

		return updatedBy;
	}

	/**
	 * Gets the updated date.
	 * 
	 * @return the updated date
	 */
	public Timestamp getUpdatedDate() {

		return updatedDate;
	}

	/**
	 * Sets the ach process log history id.
	 * 
	 * @param achProcessLogHistoryId the new ach process log history id
	 */
	public void setAchProcessLogHistoryId(long achProcessLogHistoryId) {

		this.achProcessLogHistoryId = achProcessLogHistoryId;
	}

	/**
	 * Sets the ach process log id.
	 * 
	 * @param achProcessLogId the new ach process log id
	 */
	public void setAchProcessLogId(long achProcessLogId) {

		this.achProcessLogId = achProcessLogId;
	}

	/**
	 * Sets the ach process status id.
	 * 
	 * @param achProcessStatusId the new ach process status id
	 */
	public void setAchProcessStatusId(long achProcessStatusId) {

		this.achProcessStatusId = achProcessStatusId;
	}

	/**
	 * Sets the comments.
	 * 
	 * @param comments the new comments
	 */
	public void setComments(String comments) {

		this.comments = comments;
	}

	/**
	 * Sets the updated by.
	 * 
	 * @param updatedBy the new updated by
	 */
	public void setUpdatedBy(String updatedBy) {

		this.updatedBy = updatedBy;
	}

	/**
	 * Sets the updated date.
	 * 
	 * @param updatedDate the new updated date
	 */
	public void setUpdatedDate(Timestamp updatedDate) {

		this.updatedDate = updatedDate;
	}
}
