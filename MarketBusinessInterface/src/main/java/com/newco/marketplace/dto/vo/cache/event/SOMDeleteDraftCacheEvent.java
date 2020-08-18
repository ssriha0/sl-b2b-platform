package com.newco.marketplace.dto.vo.cache.event;

import java.util.HashMap;


public class SOMDeleteDraftCacheEvent extends CacheEvent {

	public SOMDeleteDraftCacheEvent(HashMap<String, Object> params) {
		super(params);
		clearBuyerDetails = true;
	}

}
