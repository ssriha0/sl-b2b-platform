/**
 *
 */
package com.servicelive.domain;

import java.io.Serializable;

/**
 * @author hoza
 *
 */
@LookupDomain
public abstract class AbstractLookupDomain implements Serializable {
	/**
	 * 
	 * @return Object
	 */
	public abstract Object getId();
	/**
	 * 
	 * @return Object
	 */
	public abstract Object getDescription();

}
