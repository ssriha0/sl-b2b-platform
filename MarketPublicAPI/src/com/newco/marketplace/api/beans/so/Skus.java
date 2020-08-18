package com.newco.marketplace.api.beans.so;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing sku information.
 * @author Infosys
 *
 */
@XStreamAlias("skus")
public class Skus {
	
	@XStreamImplicit(itemFieldName="sku")
	private List<String> skuList;

	public List<String> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<String> skuList) {
		this.skuList = skuList;
	}
}
