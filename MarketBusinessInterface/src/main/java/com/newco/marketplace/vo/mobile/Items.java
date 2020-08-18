package com.newco.marketplace.vo.mobile;

import java.util.List;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("items")
public class Items {
	
	@XStreamImplicit(itemFieldName = "item")
	private List<Item>item;
		
	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}


	
}
