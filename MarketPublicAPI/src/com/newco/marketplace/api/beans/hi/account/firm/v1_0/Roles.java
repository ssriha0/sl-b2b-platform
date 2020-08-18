

package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XmlRootElement(name = "roles")
@XStreamAlias("roles")
@XmlAccessorType(XmlAccessType.FIELD)
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
