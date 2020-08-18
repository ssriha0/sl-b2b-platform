package com.servicelive.wallet.batch.activity.vo;

import java.io.Serializable;
import java.sql.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class DailyFulfillmentReportVO.
 */
public class DailyFulfillmentReportVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4884356591375088511L;

	/** The report day. */
	private Date reportDay;

	/** The total entries. */
	private Integer totalEntries;

	/** The total requests. */
	private Integer totalRequests;

	/** The unmatched responses. */
	private Integer unmatchedResponses;

	/** The unreconciled entries. */
	private Integer unreconciledEntries;

	/**
	 * Gets the report day.
	 * 
	 * @return the report day
	 */
	public Date getReportDay() {

		return reportDay;
	}

	/**
	 * Gets the total entries.
	 * 
	 * @return the total entries
	 */
	public Integer getTotalEntries() {

		return totalEntries;
	}

	/**
	 * Gets the total requests.
	 * 
	 * @return the total requests
	 */
	public Integer getTotalRequests() {

		return totalRequests;
	}

	/**
	 * Gets the unmatched responses.
	 * 
	 * @return the unmatched responses
	 */
	public Integer getUnmatchedResponses() {

		return unmatchedResponses;
	}

	/**
	 * Gets the unreconciled entries.
	 * 
	 * @return the unreconciled entries
	 */
	public Integer getUnreconciledEntries() {

		return unreconciledEntries;
	}

	/**
	 * Sets the report day.
	 * 
	 * @param reportDay the new report day
	 */
	public void setReportDay(Date reportDay) {

		this.reportDay = reportDay;
	}

	/**
	 * Sets the total entries.
	 * 
	 * @param totalEntries the new total entries
	 */
	public void setTotalEntries(Integer totalEntries) {

		this.totalEntries = totalEntries;
	}

	/**
	 * Sets the total requests.
	 * 
	 * @param totalRequests the new total requests
	 */
	public void setTotalRequests(Integer totalRequests) {

		this.totalRequests = totalRequests;
	}

	/**
	 * Sets the unmatched responses.
	 * 
	 * @param unmatchedResponses the new unmatched responses
	 */
	public void setUnmatchedResponses(Integer unmatchedResponses) {

		this.unmatchedResponses = unmatchedResponses;
	}

	/**
	 * Sets the unreconciled entries.
	 * 
	 * @param unreconciledEntries the new unreconciled entries
	 */
	public void setUnreconciledEntries(Integer unreconciledEntries) {

		this.unreconciledEntries = unreconciledEntries;
	}
}
