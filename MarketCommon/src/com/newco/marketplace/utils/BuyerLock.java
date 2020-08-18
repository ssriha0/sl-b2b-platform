package com.newco.marketplace.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BuyerLock {

	private Map<Integer, Lock> locks = new HashMap<Integer, Lock>();
	
	public void lock(Integer buyerId) {
		if (!locks.containsKey(buyerId)) {
			locks.put(buyerId, new ReentrantLock());
		}
	}
	
	public void unlock(Integer buyerId) {
		
	}
	
}
