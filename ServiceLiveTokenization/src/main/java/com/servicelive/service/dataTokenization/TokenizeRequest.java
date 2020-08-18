package com.servicelive.service.dataTokenization;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


@XStreamAlias("ns2:TokenizeRequest")
public class TokenizeRequest { 

	 
	@XStreamAsAttribute
    final String xmlns = "http://service.credit.som.hs.searshc.com/Request/Tokenize/";
	
	@XStreamAsAttribute 
    @XStreamAlias("xmlns:ns2")
    final String xlink="http://service.credit.som.hs.searshc.com/Request/Tokenize/";
    
	@XStreamAlias("ns2:acctNo")
	private String acctNo;
    
	@XStreamAlias("ns2:termId")
	private String termId;
	
	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

}
