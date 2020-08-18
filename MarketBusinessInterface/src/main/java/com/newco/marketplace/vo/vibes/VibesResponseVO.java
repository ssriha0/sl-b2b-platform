package com.newco.marketplace.vo.vibes;

//R16_1: SL-18979: New VO class to store response details for Add Participant/Delete Subscription API for Vibes
public class VibesResponseVO {

	private String statusText;
	private int statusCode;
	private String response;
	private String error;

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
