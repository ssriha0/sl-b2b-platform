package com.newco.marketplace.dto.vo.survey;

import com.newco.marketplace.dto.vo.survey.WestSurveyVO;
import com.sears.os.vo.SerializableBaseVO;

/**
 * The error object containing the error survey record along with corresponding error message and exception object if any
 */
public class WestSurveyErrorVO extends SerializableBaseVO {

	private static final long serialVersionUID = -4483858423043698854L;

	private WestSurveyVO westSurveyVO;
	private String errorMessage;
	private Exception exception;

	public WestSurveyVO getWestSurveyVO() {
		return westSurveyVO;
	}

	public void setWestSurveyVO(WestSurveyVO westSurveyVO) {
		this.westSurveyVO = westSurveyVO;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

}