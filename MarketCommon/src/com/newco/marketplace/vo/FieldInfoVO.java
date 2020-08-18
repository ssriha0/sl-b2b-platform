/*
 * Created on Jul 4, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.newco.marketplace.vo;

import java.util.ArrayList;

/**
 * @author PChandrika1
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FieldInfoVO extends MPBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4399336321723857026L;
	private String strFieldCode;
	private String strFieldDesc;
	private ArrayList arrFieldInfo;
	
	
	/**
	 * @return
	 */
	public String getStrFieldCode() {
		return strFieldCode;
	}

	/**
	 * @return
	 */
	public String getStrFieldDesc() {
		return strFieldDesc;
	}

	/**
	 * @param string
	 */
	public void setStrFieldCode(String string) {
		strFieldCode = string;
	}

	/**
	 * @param string
	 */
	public void setStrFieldDesc(String string) {
		strFieldDesc = string;
	}

	/**
	 * @return
	 */
	public ArrayList getArrFieldInfo() {
		return arrFieldInfo;
	}

	/**
	 * @param list
	 */
	public void setArrFieldInfo(ArrayList list) {
		arrFieldInfo = list;
	}

	public String toString(){
		return (null);
	
	}
}
