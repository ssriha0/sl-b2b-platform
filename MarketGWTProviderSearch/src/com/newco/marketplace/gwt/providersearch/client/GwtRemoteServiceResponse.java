/**
 * 
 */
package com.newco.marketplace.gwt.providersearch.client;

import java.io.Serializable;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author hoza
 *
 */
public class GwtRemoteServiceResponse implements Serializable, IsSerializable {
	
	private GwtFindProvidersDTO gwtDto ;
	private List valueList;
	private boolean isValidSession;
	public GwtFindProvidersDTO getGwtDto() {
		return gwtDto;
	}
	public void setGwtDto(GwtFindProvidersDTO gwtDto) {
		this.gwtDto = gwtDto;
	}
	public List getValueList() {
		return valueList;
	}
	public void setValueList(List valueList) {
		this.valueList = valueList;
	}
	public boolean isValidSession() {
		return isValidSession;
	}
	public void setValidSession(boolean isValidSession) {
		this.isValidSession = isValidSession;
	}
	

}
