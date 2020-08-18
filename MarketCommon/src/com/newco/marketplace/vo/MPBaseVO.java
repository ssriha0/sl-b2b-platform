/*
 * Created on Jun 27, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.newco.marketplace.vo;

import com.sears.os.vo.ABaseVO;

/**
 * @author RHarini
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class MPBaseVO extends ABaseVO{
	
	private String processFlag; 	
	private String strLoggedinUserid;
	private String strLoggedinUnit;
	private String strLoggedinRole;
	private String username=null;
	

	/**
	 * @return
	 */
	public String getProcessFlag() {
		return processFlag;
	}

	/**
	 * @param string
	 */
	public void setProcessFlag(String string) {
		processFlag = string;
	}
 
	public String getUsername() {
		return username;
	}

	/**
	 * @param string
	 */
	public void setUsername(String string) {
		username = string;
	}


	/**
	 * @return
	 */
	public String getStrLoggedinRole() {
		return strLoggedinRole;
	}

	/**
	 * @return
	 */
	public String getStrLoggedinUnit() {
		return strLoggedinUnit;
	}

	/**
	 * @return
	 */
	public String getStrLoggedinUserid() {
		return strLoggedinUserid;
	}

	/**
	 * @param string
	 */
	public void setStrLoggedinRole(String string) {
		strLoggedinRole = string;
	}

	/**
	 * @param string
	 */
	public void setStrLoggedinUnit(String string) {
		strLoggedinUnit = string;
	}

	/**
	 * @param string
	 */
	public void setStrLoggedinUserid(String string) {
		strLoggedinUserid = string;
	}

}
