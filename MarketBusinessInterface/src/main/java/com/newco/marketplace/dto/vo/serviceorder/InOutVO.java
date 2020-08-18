/**
 * 
 */
package com.newco.marketplace.dto.vo.serviceorder;

import com.newco.marketplace.webservices.base.CommonVO;

/**
 * @author rambewa
 *
 */
public class InOutVO <In, Out> extends CommonVO{
	   
	private In in;
	   
	private Out out;
	
	public InOutVO(In in, Out out) { 
	    this.in = in;
	    this.out = out;   
	}

	/**
	 * @return the in
	 */
	public In getIn() {
		return in;
	}

	/**
	 * @param in the in to set
	 */
	public void setIn(In in) {
		this.in = in;
	}

	/**
	 * @return the out
	 */
	public Out getOut() {
		return out;
	}

	/**
	 * @param out the out to set
	 */
	public void setOut(Out out) {
		this.out = out;
	}
	  
	
}
