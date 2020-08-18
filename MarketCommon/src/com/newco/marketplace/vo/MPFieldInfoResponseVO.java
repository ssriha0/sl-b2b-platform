package com.newco.marketplace.vo;

import java.util.ArrayList;

import com.sears.os.vo.SerializableBaseVO;


/**
 * 	<p> Title: RFS FieldInfo ResponseVO </p>
 *  <p> Description : Acts as a data carrier from business to action.</p>
 * @author PChandrika
 * @Date Jun 30, 2006
 *  
 */
public class MPFieldInfoResponseVO extends SerializableBaseVO{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6025550416409489505L;
	private ArrayList arrVO;
	private String strErrorCode;
	private String strErrorType;
	private String strErrorMessage;
	

	/**
	 * @return
	 */
	public ArrayList getArrVO() {
		return arrVO;
	}

	/**
	 * @param list
	 */
	public void setArrVO(ArrayList list) {
		arrVO = list;
	}

	/**
	 * @return
	 */
	public String getStrErrorCode() {
		return strErrorCode;
	}

	/**
	 * @return
	 */
	public String getStrErrorMessage() {
		return strErrorMessage;
	}

	/**
	 * @return
	 */
	public String getStrErrorType() {
		return strErrorType;
	}

	/**
	 * @param string
	 */
	public void setStrErrorCode(String string) {
		strErrorCode = string;
	}

	/**
	 * @param string
	 */
	public void setStrErrorMessage(String string) {
		strErrorMessage = string;
	}

	/**
	 * @param string
	 */
	public void setStrErrorType(String string) {
		strErrorType = string;
	}

}
