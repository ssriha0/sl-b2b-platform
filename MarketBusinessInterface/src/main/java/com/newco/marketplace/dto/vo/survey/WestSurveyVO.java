package com.newco.marketplace.dto.vo.survey;

import com.sears.os.vo.SerializableBaseVO;
import com.newco.marketplace.utils.ExcelAlias;

/**
 * This POJO represents one single record in West Survey spreadsheet
 * R15_3: SL-20908 Updated the VO based on the new Innovel file format
 */
public class WestSurveyVO extends SerializableBaseVO {

	private static final long serialVersionUID = -1076944496221476719L;

	// variables holds the values of fields from the input excel sheet
	@ExcelAlias("1")
	private String firmId;

	@ExcelAlias("2")
	private String soId;

	@ExcelAlias("3")
	private String unitNo;

	@ExcelAlias("4")
	private String orderNo;

	@ExcelAlias("5")
	private String surveyDate;

	@ExcelAlias("6")
	private String question1;

	@ExcelAlias("7")
	private String question2;

	@ExcelAlias("8")
	private String question3;

	@ExcelAlias("9")
	private String question4;

	@ExcelAlias("10")
	private String question5;

	@ExcelAlias("11")
	private String question6;

	//variables to hold the integer converted values of questions read from the excel
	private Integer intValueQuestion2;
	private Integer intValueQuestion3;
	private Integer intValueQuestion4;
	private Integer intValueQuestion5;
	private Integer intValueQuestion6;
	
	//variables to hold the SL rating of the corresponding Innovel CSAT values
	private Integer slCSATConvertedQ2;
	private Integer slCSATConvertedQ3;
	private Integer slCSATConvertedQ4;
	private Integer slCSATConvertedQ5;
	private Integer slCSATConvertedQ6;
	
	//variables to hold the rating values 
	private Integer timelinessRating;
	private Integer communicationsRating;
	private Integer professionalRating;
	private Integer qualityRating;
	private Integer valueRating;
	private Integer cleanlinessRating;
	/**
	 * @return the firmId
	 */
	public String getFirmId() {
		return firmId;
	}
	/**
	 * @param firmId the firmId to set
	 */
	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}
	/**
	 * @return the soId
	 */
	public String getSoId() {
		return soId;
	}
	/**
	 * @param soId the soId to set
	 */
	public void setSoId(String soId) {
		this.soId = soId;
	}
	/**
	 * @return the unitNo
	 */
	public String getUnitNo() {
		return unitNo;
	}
	/**
	 * @param unitNo the unitNo to set
	 */
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}
	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}
	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * @return the surveyDate
	 */
	public String getSurveyDate() {
		return surveyDate;
	}
	/**
	 * @param surveyDate the surveyDate to set
	 */
	public void setSurveyDate(String surveyDate) {
		this.surveyDate = surveyDate;
	}

	/**
	 * @return the question1
	 */
	public String getQuestion1() {
		return question1;
	}
	/**
	 * @param question1 the question1 to set
	 */
	public void setQuestion1(String question1) {
		this.question1 = question1;
	}
	/**
	 * @return the question2
	 */
	public String getQuestion2() {
		return question2;
	}
	/**
	 * @param question2 the question2 to set
	 */
	public void setQuestion2(String question2) {
		this.question2 = question2;
	}
	/**
	 * @return the question3
	 */
	public String getQuestion3() {
		return question3;
	}
	/**
	 * @param question3 the question3 to set
	 */
	public void setQuestion3(String question3) {
		this.question3 = question3;
	}
	/**
	 * @return the question4
	 */
	public String getQuestion4() {
		return question4;
	}
	/**
	 * @param question4 the question4 to set
	 */
	public void setQuestion4(String question4) {
		this.question4 = question4;
	}
	/**
	 * @return the question5
	 */
	public String getQuestion5() {
		return question5;
	}
	/**
	 * @param question5 the question5 to set
	 */
	public void setQuestion5(String question5) {
		this.question5 = question5;
	}
	/**
	 * @return the question6
	 */
	public String getQuestion6() {
		return question6;
	}
	/**
	 * @param question6 the question6 to set
	 */
	public void setQuestion6(String question6) {
		this.question6 = question6;
	}
	/**
	 * @return the intValueQuestion2
	 */
	public Integer getIntValueQuestion2() {
		return intValueQuestion2;
	}
	/**
	 * @param intValueQuestion2 the intValueQuestion2 to set
	 */
	public void setIntValueQuestion2(Integer intValueQuestion2) {
		this.intValueQuestion2 = intValueQuestion2;
	}
	/**
	 * @return the intValueQuestion3
	 */
	public Integer getIntValueQuestion3() {
		return intValueQuestion3;
	}
	/**
	 * @param intValueQuestion3 the intValueQuestion3 to set
	 */
	public void setIntValueQuestion3(Integer intValueQuestion3) {
		this.intValueQuestion3 = intValueQuestion3;
	}
	/**
	 * @return the intValueQuestion4
	 */
	public Integer getIntValueQuestion4() {
		return intValueQuestion4;
	}
	/**
	 * @param intValueQuestion4 the intValueQuestion4 to set
	 */
	public void setIntValueQuestion4(Integer intValueQuestion4) {
		this.intValueQuestion4 = intValueQuestion4;
	}
	/**
	 * @return the intValueQuestion5
	 */
	public Integer getIntValueQuestion5() {
		return intValueQuestion5;
	}
	/**
	 * @param intValueQuestion5 the intValueQuestion5 to set
	 */
	public void setIntValueQuestion5(Integer intValueQuestion5) {
		this.intValueQuestion5 = intValueQuestion5;
	}
	/**
	 * @return the intValueQuestion6
	 */
	public Integer getIntValueQuestion6() {
		return intValueQuestion6;
	}
	/**
	 * @param intValueQuestion6 the intValueQuestion6 to set
	 */
	public void setIntValueQuestion6(Integer intValueQuestion6) {
		this.intValueQuestion6 = intValueQuestion6;
	}
	/**
	 * @return the slCSATConvertedQ2
	 */
	public Integer getSlCSATConvertedQ2() {
		return slCSATConvertedQ2;
	}
	/**
	 * @param slCSATConvertedQ2 the slCSATConvertedQ2 to set
	 */
	public void setSlCSATConvertedQ2(Integer slCSATConvertedQ2) {
		this.slCSATConvertedQ2 = slCSATConvertedQ2;
	}
	/**
	 * @return the slCSATConvertedQ3
	 */
	public Integer getSlCSATConvertedQ3() {
		return slCSATConvertedQ3;
	}
	/**
	 * @param slCSATConvertedQ3 the slCSATConvertedQ3 to set
	 */
	public void setSlCSATConvertedQ3(Integer slCSATConvertedQ3) {
		this.slCSATConvertedQ3 = slCSATConvertedQ3;
	}
	/**
	 * @return the slCSATConvertedQ4
	 */
	public Integer getSlCSATConvertedQ4() {
		return slCSATConvertedQ4;
	}
	/**
	 * @param slCSATConvertedQ4 the slCSATConvertedQ4 to set
	 */
	public void setSlCSATConvertedQ4(Integer slCSATConvertedQ4) {
		this.slCSATConvertedQ4 = slCSATConvertedQ4;
	}
	/**
	 * @return the slCSATConvertedQ5
	 */
	public Integer getSlCSATConvertedQ5() {
		return slCSATConvertedQ5;
	}
	/**
	 * @param slCSATConvertedQ5 the slCSATConvertedQ5 to set
	 */
	public void setSlCSATConvertedQ5(Integer slCSATConvertedQ5) {
		this.slCSATConvertedQ5 = slCSATConvertedQ5;
	}
	/**
	 * @return the slCSATConvertedQ6
	 */
	public Integer getSlCSATConvertedQ6() {
		return slCSATConvertedQ6;
	}
	/**
	 * @param slCSATConvertedQ6 the slCSATConvertedQ6 to set
	 */
	public void setSlCSATConvertedQ6(Integer slCSATConvertedQ6) {
		this.slCSATConvertedQ6 = slCSATConvertedQ6;
	}
	/**
	 * @return the timelinessRating
	 */
	public Integer getTimelinessRating() {
		return timelinessRating;
	}
	/**
	 * @param timelinessRating the timelinessRating to set
	 */
	public void setTimelinessRating(Integer timelinessRating) {
		this.timelinessRating = timelinessRating;
	}
	/**
	 * @return the communicationsRating
	 */
	public Integer getCommunicationsRating() {
		return communicationsRating;
	}
	/**
	 * @param communicationsRating the communicationsRating to set
	 */
	public void setCommunicationsRating(Integer communicationsRating) {
		this.communicationsRating = communicationsRating;
	}
	/**
	 * @return the professionalRating
	 */
	public Integer getProfessionalRating() {
		return professionalRating;
	}
	/**
	 * @param professionalRating the professionalRating to set
	 */
	public void setProfessionalRating(Integer professionalRating) {
		this.professionalRating = professionalRating;
	}
	/**
	 * @return the qualityRating
	 */
	public Integer getQualityRating() {
		return qualityRating;
	}
	/**
	 * @param qualityRating the qualityRating to set
	 */
	public void setQualityRating(Integer qualityRating) {
		this.qualityRating = qualityRating;
	}
	/**
	 * @return the valueRating
	 */
	public Integer getValueRating() {
		return valueRating;
	}
	/**
	 * @param valueRating the valueRating to set
	 */
	public void setValueRating(Integer valueRating) {
		this.valueRating = valueRating;
	}
	/**
	 * @return the cleanlinessRating
	 */
	public Integer getCleanlinessRating() {
		return cleanlinessRating;
	}
	/**
	 * @param cleanlinessRating the cleanlinessRating to set
	 */
	public void setCleanlinessRating(Integer cleanlinessRating) {
		this.cleanlinessRating = cleanlinessRating;
	}

}
