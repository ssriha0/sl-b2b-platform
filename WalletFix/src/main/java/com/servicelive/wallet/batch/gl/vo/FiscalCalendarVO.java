package com.servicelive.wallet.batch.gl.vo;

//import com.sears.os.vo.SerializableBaseVO;

public class FiscalCalendarVO /*extends SerializableBaseVO*/{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4019316159539773341L;
	private Integer accountingYear;
	private Integer accountingQuarter;
	private String accountingMonth;
	private Integer calendarStartWeek;
	private String calendarEndWeek;
	private Integer preiodWeek;
	private Integer numberWorkPeriod;
	private String quarterFiscalWeek;
	private String yearFiscalWeek;
	private String accountingMonthDesc;
	private Integer checkFiscalWeek;
	

	public String getAccountingMonth() {
		return accountingMonth;
	}
	public void setAccountingMonth(String accountingMonth) {
		this.accountingMonth = accountingMonth;
	}
	public String getCalendarEndWeek() {
		return calendarEndWeek;
	}
	public void setCalendarEndWeek(String calendarEndWeek) {
		this.calendarEndWeek = calendarEndWeek;
	}
	public String getQuarterFiscalWeek() {
		return quarterFiscalWeek;
	}
	public void setQuarterFiscalWeek(String quarterFiscalWeek) {
		this.quarterFiscalWeek = quarterFiscalWeek;
	}
	public String getYearFiscalWeek() {
		return yearFiscalWeek;
	}
	public void setYearFiscalWeek(String yearFiscalWeek) {
		this.yearFiscalWeek = yearFiscalWeek;
	}
	public String getAccountingMonthDesc() {
		return accountingMonthDesc;
	}
	public void setAccountingMonthDesc(String accountingMonthDesc) {
		this.accountingMonthDesc = accountingMonthDesc;
	}
	public Integer getAccountingQuarter() {
		return accountingQuarter;
	}
	public void setAccountingQuarter(Integer accountingQuarter) {
		this.accountingQuarter = accountingQuarter;
	}
	public Integer getAccountingYear() {
		return accountingYear;
	}
	public void setAccountingYear(Integer accountingYear) {
		this.accountingYear = accountingYear;
	}
	public Integer getCalendarStartWeek() {
		return calendarStartWeek;
	}
	public void setCalendarStartWeek(Integer calendarStartWeek) {
		this.calendarStartWeek = calendarStartWeek;
	}
	public Integer getCheckFiscalWeek() {
		return checkFiscalWeek;
	}
	public void setCheckFiscalWeek(Integer checkFiscalWeek) {
		this.checkFiscalWeek = checkFiscalWeek;
	}
	public Integer getNumberWorkPeriod() {
		return numberWorkPeriod;
	}
	public void setNumberWorkPeriod(Integer numberWorkPeriod) {
		this.numberWorkPeriod = numberWorkPeriod;
	}
	public Integer getPreiodWeek() {
		return preiodWeek;
	}
	public void setPreiodWeek(Integer preiodWeek) {
		this.preiodWeek = preiodWeek;
	}

	
	
	
}
