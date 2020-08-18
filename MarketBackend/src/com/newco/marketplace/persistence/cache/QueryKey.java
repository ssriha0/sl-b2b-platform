package com.newco.marketplace.persistence.cache;

 
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 
 * File Name: QueryKey.java , Created By: Munish Joshi , Date: Jun 25, 2009
 * 
 */

public final class QueryKey implements Serializable {

	private final String queryName;
	private final List queryParameters;

	public QueryKey(final String queryName, final List queryParameters) {
		this.queryName = queryName;
		this.queryParameters = queryParameters;
	}

	/**
	 * 
	 * @return
	 */
	public String getQueryText() {
		return queryName;
	}

	/**
	 * 
	 * @return
	 */
	public List getQueryParameters() {
		return Collections.unmodifiableList(queryParameters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((queryName == null) ? 0 : queryName.hashCode());
		result = prime * result
				+ ((queryParameters == null) ? 0 : queryParameters.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryKey other = (QueryKey) obj;
		if (queryName == null) {
			if (other.queryName != null)
				return false;
		} else if (!queryName.equals(other.queryName))
			return false;
		if (queryParameters == null) {
			if (other.queryParameters != null)
				return false;
		} else if (!queryParameters.equals(other.queryParameters))
			return false;
		return true;
	}
	
	
	

  
}

