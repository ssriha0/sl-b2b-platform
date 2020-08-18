package com.newco.marketplace.web.dto.d2cproviderportal;

import java.io.Serializable;
import java.util.List;

import com.newco.marketplace.dto.vo.DispatchLocationVO;

/**
 * 
 * @author rranja1
 * 
 */
public class DispatchLocationDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<DispatchLocationVO> dispatchLocs;

	public List<DispatchLocationVO> getDispatchLocs() {
		return dispatchLocs;
	}

	public void setDispatchLocs(List<DispatchLocationVO> dispatchLocs) {
		this.dispatchLocs = dispatchLocs;
	}
}
