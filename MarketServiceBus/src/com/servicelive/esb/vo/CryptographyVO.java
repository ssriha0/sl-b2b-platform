package com.servicelive.esb.vo;


public class CryptographyVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3613350897971368169L;
	private String input;
	private String kAlias;
	private String kPwd;
	 private String response;
	/**
	 * @return the input
	 */
	public String getInput() {
		return input;
	}
	/**
	 * @param input the input to set
	 */
	public void setInput(String input) {
		this.input = input;
	}
	/**
	 * @return the kAlias
	 */
	public String getKAlias() {
		return kAlias;
	}
	/**
	 * @param alias the kAlias to set
	 */
	public void setKAlias(String alias) {
		kAlias = alias;
	}
	/**
	 * @return the kPwd
	 */
	public String getKPwd() {
		return kPwd;
	}
	/**
	 * @param pwd the kPwd to set
	 */
	public void setKPwd(String pwd) {
		kPwd = pwd;
	}
	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(String response) {
		this.response = response;
	}
}
