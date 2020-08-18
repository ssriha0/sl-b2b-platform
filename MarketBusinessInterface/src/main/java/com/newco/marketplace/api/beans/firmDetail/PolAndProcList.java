package com.newco.marketplace.api.beans.firmDetail;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("polAndProcList")
@XmlAccessorType(XmlAccessType.FIELD)
public class PolAndProcList {

	@XStreamImplicit(itemFieldName="polAndProc")
	private List<PolAndProc> polAndProc;

	public List<PolAndProc> getPolAndProc() {
		return polAndProc;
	}

	public void setPolAndProc(List<PolAndProc> polAndProc) {
		this.polAndProc = polAndProc;
	}
	
	
}
