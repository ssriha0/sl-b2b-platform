package com.newco.marketplace.gwt.providersearch.client;
import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;


public class SimpleProviderSearchSkillTypeVO implements IsSerializable, Serializable {

	int skillTypeId;
	boolean checked;
	String skillTypeDescr;
	public int getSkillTypeId() {
		return skillTypeId;
	}
	public void setSkillTypeId(int skillTypeId) {
		this.skillTypeId = skillTypeId;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getSkillTypeDescr() {
		return skillTypeDescr;
	}
	public void setSkillTypeDescr(String skillTypeDescr) {
		this.skillTypeDescr = skillTypeDescr;
	}
	
}
