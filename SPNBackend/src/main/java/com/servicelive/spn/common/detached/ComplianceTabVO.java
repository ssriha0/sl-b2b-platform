/**
 * 
 */
package com.servicelive.spn.common.detached;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author hoza
 *
 */
public class ComplianceTabVO implements Serializable {

	
	private static final long serialVersionUID = 20090317;

	private Integer sEcho;
	private String iTotalRecords;
	private String iTotalDisplayRecords;
	private String aaData[][];
	
	public Integer getsEcho() {
		return sEcho;
	}
	public void setsEcho(Integer sEcho) {
		this.sEcho = sEcho;
	}
	public String getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(String iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public String getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(String iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public String[][] getAaData() {
		return aaData;
	}
	public void setAaData(String[][] aaData) {
		this.aaData = aaData;
	}
	

	
	
	
	
	
}
