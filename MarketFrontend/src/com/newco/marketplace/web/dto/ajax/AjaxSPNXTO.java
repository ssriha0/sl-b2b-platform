package com.newco.marketplace.web.dto.ajax;

import java.util.Calendar;

import com.newco.marketplace.dto.vo.spn.SPNCampaignVO;
import com.newco.marketplace.web.dto.AbstractAjaxResultsDTO;

public class AjaxSPNXTO extends AbstractAjaxResultsDTO{

	private StringBuffer sb = new StringBuffer();

	
	public String toXml() {
		getBuffer().append("<campaign_data>").append("no data").append("</campaign_data>");
		return getBuffer().toString();
	}

	public String toXmlDeepCopy() {
		getBuffer().append("<campaign_data>")
				   .append(fireToXMLOnSubList())
				   .append("</campaign_data>");
		return getBuffer().toString();
	}
	
	public StringBuffer getBuffer() {
		return sb;
	}
	
	public static void main(String args[]){
		AjaxSPNXTO t = new AjaxSPNXTO();
		SPNCampaignVO c = new SPNCampaignVO();
		c.setMarketName("Test Market");
		c.setCampaignId(10);
		c.setCampaignEndDate(Calendar.getInstance().getTime());
		c.setCampaignStateDate(Calendar.getInstance().getTime());
		c.setTotalProviderCnt(5);
		AjaxSPNCampaignXTO camp 	= new AjaxSPNCampaignXTO(c);
		AjaxSPNCampaignXTO camp1 	= new AjaxSPNCampaignXTO(c);
		AjaxSPNCampaignXTO camp2 	= new AjaxSPNCampaignXTO(c);
		AjaxSPNCampaignXTO camp3 	= new AjaxSPNCampaignXTO(c);
		t.addAjaxAbleItems(camp);
		t.addAjaxAbleItems(camp1);
		t.addAjaxAbleItems(camp2);
		t.addAjaxAbleItems(camp3);
		AjaxResultsDTO results = new AjaxResultsDTO();
		results.addAjaxAbleItems(t);
		System.out.println(results.toXmlDeepCopy());
	}

}
