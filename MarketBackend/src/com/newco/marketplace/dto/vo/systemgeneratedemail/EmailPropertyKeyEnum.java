package com.newco.marketplace.dto.vo.systemgeneratedemail;

public enum EmailPropertyKeyEnum {

	BUYERNAME("BUYER_NAME"),
	BUYERLOGO("BUYER_LOGO"),
	BUYERSIGNATURE("BUYER_SIGNATURE"),
	FACEBOOKLINK("FACEBOOK_LINK"),
	TWITTERLINK("TWITTER_LINK"),
	INSTAGRAMLINK("INSTAGRAM_LINK"),
	PINTERESTLINK("PINTEREST_LINK"),
	COMPANYLINK("COMPANY_LINK"),
	SUPPORTPAGELINK("SUPPORTPAGE_LINK"),
	STOREPAGELINK("STOREPAGE_LINK"),
	TERMSANDCONDITIONSLINK("TERMSANDCONDITIONS_LINK"),
	PRIVACYPOLICYLINK("PRIVACYPOLICY_LINK"),
	ADDRESS("ADDRESS")
	;

	private String key;

	EmailPropertyKeyEnum(String propertyKey) {
		this.key = propertyKey;
	}

	public String getKey() {
		return key;
	}

}
