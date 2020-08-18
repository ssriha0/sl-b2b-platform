/**
 * 
 */
package com.servicelive.domain.spn.detached;

import java.util.List;

import com.servicelive.domain.AbstractLookupDomain;
/**
 * @author ccarle5
 *
 */
public class LookupVO extends AbstractLookupDomain {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object id;
	private Object description;
	private List<Object> history;
	private Boolean exceptionInd;
	

	public List<Object> getHistory() {
		return history;
	}

	public void setHistory(List<Object> history) {
		this.history = history;
	}

	/* (non-Javadoc)
	 * @see com.com.servicelive.domaintLookupDomain#getDescription()
	 */
	@Override
	public Object getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see com.servcom.servicelive.domainkupDomain#getId()
	 */
	@Override
	public Object getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Object id) {
		this.id = id;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(Object description) {
		this.description = description;
	}

	public Boolean getExceptionInd() {
		return exceptionInd;
	}

	public void setExceptionInd(Boolean exceptionInd) {
		this.exceptionInd = exceptionInd;
	}

}
