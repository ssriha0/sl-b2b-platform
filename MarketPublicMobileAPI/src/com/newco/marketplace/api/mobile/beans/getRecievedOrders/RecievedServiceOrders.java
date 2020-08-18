package com.newco.marketplace.api.mobile.beans.getRecievedOrders;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/10
* for fetching response 0f eligible providers forSO for mobile
*
*/

@XStreamAlias("recievedServiceOrders")  
@XmlAccessorType(XmlAccessType.FIELD)
public class RecievedServiceOrders {

	@XStreamAlias("totalSOCount")
	private Integer totalSOCount;
	
	@XStreamAlias("recievedOrders")
	private List<RecievedOrder> recievedOrders;
	
	/**
	 * @return the totalSOCount
	 */
	public Integer getTotalSOCount() {
		return totalSOCount;
	}
	/**
	 * @param totalSOCount the totalSOCount to set
	 */
	public void setTotalSOCount(Integer totalSOCount) {
		this.totalSOCount = totalSOCount;
	}
	/**
	 * @return the recievedOrders
	 */
	public List<RecievedOrder> getRecievedOrders() {
		return recievedOrders;
	}
	/**
	 * @param recievedOrders the recievedOrders to set
	 */
	public void setRecievedOrders(List<RecievedOrder> recievedOrders) {
		this.recievedOrders = recievedOrders;
	}
	
	
}
