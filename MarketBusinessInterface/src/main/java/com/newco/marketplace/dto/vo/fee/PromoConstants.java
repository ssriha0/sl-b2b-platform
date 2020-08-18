package com.newco.marketplace.dto.vo.fee;

import java.util.HashMap;
import java.util.Map;

public final class PromoConstants {
	
	public final static String BACKGROUND_CHECK_TYPE = "PromoBackgroundCheck";
	public final static String POSTING_FEE_TYPE = "PromoPostingFee";
	public final static String OTHER_TYPE = "PromoOther";
	public final static String SERVICE_FEE_TYPE = "PromoServiceFee";
	public final static String CANCELLATION_FEE_TYPE = "PromoCancellationFee";
	public final static String WALLET_CREDIT = "PromoWalletCredit";

	public final static Double POSTING_FEE_DEFAULT = 10.0;
	public final static Double SERVICE_FEE_DEFAULT = .1;
	

	public final static Map<String, Double> promoMap = new HashMap<String, Double>();
	static {
		promoMap.put(SERVICE_FEE_TYPE, SERVICE_FEE_DEFAULT);
		promoMap.put(POSTING_FEE_TYPE, POSTING_FEE_DEFAULT);
	}
	
	
	


}
