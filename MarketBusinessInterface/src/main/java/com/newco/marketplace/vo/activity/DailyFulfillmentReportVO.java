package com.newco.marketplace.vo.activity;

import java.sql.Date;

import com.sears.os.vo.SerializableBaseVO;

public class DailyFulfillmentReportVO extends SerializableBaseVO {

	private static final long serialVersionUID = 4884356591375088511L;
	private Date reportDay;
	private Integer totalEntries;
	private Integer unreconciledEntries;
	private Integer totalRequests;
	private Integer unmatchedResponses;

	public Date getReportDay() {
		return reportDay;
	}

	public void setReportDay(Date reportDay) {
		this.reportDay = reportDay;
	}

	public Integer getTotalEntries() {
		return totalEntries;
	}

	public void setTotalEntries(Integer totalEntries) {
		this.totalEntries = totalEntries;
	}

	public Integer getUnreconciledEntries() {
		return unreconciledEntries;
	}

	public void setUnreconciledEntries(Integer unreconciledEntries) {
		this.unreconciledEntries = unreconciledEntries;
	}

	public Integer getTotalRequests() {
		return totalRequests;
	}

	public void setTotal_requests(Integer totalRequests) {
		this.totalRequests = totalRequests;
	}

	public Integer getUnmatchedResponses() {
		return unmatchedResponses;
	}

	public void setUnmatchedResponses(Integer unmatchedResponses) {
		this.unmatchedResponses = unmatchedResponses;
	}
}
