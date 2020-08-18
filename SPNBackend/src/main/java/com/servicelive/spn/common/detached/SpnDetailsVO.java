/**
 * 
 */
package com.servicelive.spn.common.detached;

import java.io.Serializable;

/**
 * @author hoza
 *
 */
public class SpnDetailsVO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4317433894853677125L;
	
	private Integer spnId;
	private Boolean exceptionInd;
	
	public Integer getSpnId() {
		return spnId;
	}
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	public Boolean getExceptionInd() {
		return exceptionInd;
	}
	public void setExceptionInd(Boolean exceptionInd) {
		this.exceptionInd = exceptionInd;
	}
	
	
	
	
	

}
