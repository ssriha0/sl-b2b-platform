package com.newco.marketplace.api.beans.hi.account.create.provider;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("roles")
public class Roles {
    
	@XStreamImplicit(itemFieldName="role")
    private List<String> role;

	public List<String> getRole() {
		return role;
	}

	public void setRole(List<String> role) {
		this.role = role;
	}

   
}
