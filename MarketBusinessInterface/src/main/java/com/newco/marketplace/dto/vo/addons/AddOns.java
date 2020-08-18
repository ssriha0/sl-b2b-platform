package com.newco.marketplace.dto.vo.addons;


import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


@XStreamAlias("addons")
public class AddOns {
	
	@XStreamAlias("addon")
	@XStreamImplicit(itemFieldName="addon")
	private List<AddOn> addon;

	public List<AddOn> getAddon() {
		return addon;
	}

	public void setAddon(List<AddOn> addon) {
		this.addon = addon;
	}

	

}


		
	