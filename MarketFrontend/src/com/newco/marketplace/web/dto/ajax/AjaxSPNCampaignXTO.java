package com.newco.marketplace.web.dto.ajax;

import java.text.Format;
import java.text.SimpleDateFormat;

import com.newco.marketplace.dto.vo.spn.SPNCampaignVO;
import com.newco.marketplace.web.dto.AbstractAjaxResultsDTO;

public class AjaxSPNCampaignXTO extends AbstractAjaxResultsDTO{

	private SPNCampaignVO campaignVO;
	private static final Format formatter = new SimpleDateFormat("M/dd/yyyy");
	private StringBuffer sb = new StringBuffer();
	public AjaxSPNCampaignXTO(SPNCampaignVO campaignVO) {
		this.campaignVO = campaignVO;
	}
	
	public StringBuffer getBuffer() {
		// TODO Auto-generated method stub
		return sb;
	}
	
	public String toXml() {
		getBuffer().append("<campaign>");
			getBuffer().append("<campaign_id>").append(getCampaignVO().getCampaignId().toString()).append("</campaign_id>");
			getBuffer().append("<targeted_market>").append(getCampaignVO().getMarketName()).append("</targeted_market>");
			getBuffer().append("<campaign_dates>")
								//.append("<![CDATA[")
								.append( formatter.format(getCampaignVO().getCampaignStateDate()))
								//.append( getCampaignVO().getCampaignStateDate().toString() )
								.append(" to ")
								.append( formatter.format(getCampaignVO().getCampaignEndDate()))
								//.append( "test" )
								//.append("]]>")
					   .append("</campaign_dates>");
			getBuffer().append("<sp_matchs>").append(getCampaignVO().getTotalProviderCnt().toString()).append("</sp_matchs>");
		getBuffer().append("</campaign>");
		return getBuffer().toString();
	}

	public SPNCampaignVO getCampaignVO() {
		return campaignVO;
	}

	public void setCampaignVO(SPNCampaignVO campaignVO) {
		this.campaignVO = campaignVO;
	}


}
