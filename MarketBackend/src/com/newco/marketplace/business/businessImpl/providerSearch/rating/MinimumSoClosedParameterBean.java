package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.2 $ $Author: glacy $ $Date: 2008/05/02 21:23:38 $
 */

/*
 * Maintenance History: See bottom of file.
 */
public class MinimumSoClosedParameterBean extends RatingParameterBean {
	
	private static final long serialVersionUID = -1951714532986672091L;
	private Integer minSoClosed;

	/**
	 * @return the minSoClosed
	 */
	public Integer getMinSoClosed() {
		return minSoClosed;
	}

	/**
	 * @param minSoClosed the minSoClosed to set
	 */
	public void setMinSoClosed(Integer minSoClosed) {
		this.minSoClosed = minSoClosed;
	}
	
	
}

/*
 * Maintenance History:
 * $Log: MinimumSoClosedParameterBean.java,v $
 * Revision 1.2  2008/05/02 21:23:38  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.1.2.1  2008/04/18 00:23:52  mhaye05
 * added logic for inviting service pros to spn
 *
 */