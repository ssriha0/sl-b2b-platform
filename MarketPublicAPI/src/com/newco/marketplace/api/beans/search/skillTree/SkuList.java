package com.newco.marketplace.api.beans.search.skillTree;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("skuList")
public class SkuList {
	
	@XStreamImplicit(itemFieldName="sku")
	private List<SkuDetail> skuList;

	public void addSku(SkuDetail skuDetail) {
		if (skuList == null)
			skuList = new ArrayList<SkuDetail>();
		this.skuList.add(skuDetail);
	}

	public List<SkuDetail> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<SkuDetail> skuList) {
		this.skuList = skuList;
	}	

}
