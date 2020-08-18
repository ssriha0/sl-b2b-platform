/**
 * 
 */
package com.newco.marketplace.vo.provider;

import com.sears.os.vo.SerializableBaseVO;


/**
 * @author KSudhanshu
 *
 */
public class BaseVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6901442695102342273L;
	private String id;
	private String fullResoueceName;

	public String getFullResoueceName() {
		return fullResoueceName;
	}

	public void setFullResoueceName(String fullResoueceName) {
		this.fullResoueceName = fullResoueceName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
