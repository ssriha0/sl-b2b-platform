/**
 *
 */
package com.newco.marketplace.gwt.providersearch.client;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author HOZA
 * This vo is GWT counter part for com.newco.marketplace.dto.vo.LookupVO just for pulling languages
 */
public class LanguageVO implements Serializable, IsSerializable{
	private Integer id = null;
	private String descr = null;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}



}
