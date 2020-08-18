package com.newco.marketplace.utils;

import java.util.Date;

import junit.framework.Assert;
import junit.framework.TestCase;

public class CryptoUtilTest extends TestCase {

	public void testEncryptingAndDecryptingObject() {
		Integer buyerId = Integer.valueOf(1000);
		Date now = new Date();
		String ipAddress = "127.0.0.1";
		String userName = "scarpe5@searshc.com";
		SharedSecret ss = new SharedSecret();
		ss.setBuyerId(buyerId);
		ss.setCreatedDate(now);
		ss.setIpAddress(ipAddress);
		ss.setUserName(userName);
		
		try {
			String hash = CryptoUtil.encryptObject(ss);
			System.out.println("hash = " + hash);
			SharedSecret result = (SharedSecret) CryptoUtil.decryptObject(hash);
			Assert.assertEquals(buyerId, result.getBuyerId());
			Assert.assertEquals(now, result.getCreatedDate());
			Assert.assertEquals(ipAddress, result.getIpAddress());
			Assert.assertEquals(userName, result.getUserName());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
