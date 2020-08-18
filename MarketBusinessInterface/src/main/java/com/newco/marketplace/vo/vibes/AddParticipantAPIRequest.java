package com.newco.marketplace.vo.vibes;
import com.thoughtworks.xstream.annotations.XStreamAlias;


//R16_1: SL-18979: Request class for Add Participant API for Vibes
public class AddParticipantAPIRequest {
	
	@XStreamAlias("mobile_phone")
	private MobilePhone mobile_phone;
	
	@XStreamAlias("custom_field")
	private CustomField custom_field;


	public MobilePhone getMobile_phone() {
		return mobile_phone;
	}

	public void setMobile_phone(MobilePhone mobile_phone) {
		this.mobile_phone = mobile_phone;
	}

	public CustomField getCustom_field() {
		return custom_field;
	}

	public void setCustom_field(CustomField custom_field) {
		this.custom_field = custom_field;
	}

	

	
	
	
	
}

