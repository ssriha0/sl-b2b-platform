/**
 * 
 */
package com.servicelive.serviceordercreation;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author himanshu
 *
 */
public class MessageCorrelationIDBaseDistributor {
	private String validCorrelationIdsStr; // this string will get parsed and pool will  get created, its comma separated string
	//private Integer totalOrdersTobeCreated;
	private Map<String,Integer> pool = new ConcurrentHashMap<String,Integer>();// the use of 
	private List<String> poolKeyList = new ArrayList<String> ();
	private MessageDigest digest;
	
	
	
	
	
	

	/**
	 * @param validCorrelationIdStr
	 */
	public MessageCorrelationIDBaseDistributor(String validCorrelationIdStr) {
		this.validCorrelationIdsStr = validCorrelationIdStr;
		try {
			initPool();
			digest = java.security.MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			//Currently swallowing the exception
			e.printStackTrace();
		}
	}

	private void initPool() throws Exception {
		if(this.validCorrelationIdsStr != null && this.validCorrelationIdsStr.length() > 0) {
			StringTokenizer tokenizer = new StringTokenizer(this.validCorrelationIdsStr,",");
			while(tokenizer.hasMoreTokens()) {
				String corelationid = tokenizer.nextToken();
				pool.put(corelationid, Integer.valueOf(0));
				poolKeyList.add(corelationid);
			}
		}
		else {
			throw new RuntimeException(" Could not find Valid Correlation Id String in default.properties");
		}
		System.out.println(" Pool Size = "+ pool.size());
	}
	
	 
	/**
	 * @return the totalOrdersTobeCreated
	 */
	/*public Integer getTotalOrdersTobeCreated() {
		return totalOrdersTobeCreated;
	}

	*//**
	 * @param totalOrdersTobeCreated the totalOrdersTobeCreated to set
	 *//*
	public void setTotalOrdersTobeCreated(Integer totalOrdersTobeCreated) {
		this.totalOrdersTobeCreated = totalOrdersTobeCreated;
	}*/

	/**
	 * @return the validCorrelationIdStr
	 */
	public String getValidCorrelationIdsStr() {
		return validCorrelationIdsStr;
	}

	/**
	 * @param validCorrelationIdStr the validCorrelationIdStr to set
	 */
	public void setValidCorrelationIdsStr(String validCorrelationIdStr) {
		this.validCorrelationIdsStr = validCorrelationIdStr;
	}
	
	/**
	 * @param currentOrderNumber
	 * @return
	 * @throws Exception
	 */
	public String getCorrelationId(String orderNumberUnitNumber) throws Exception{
		/*if(this.totalOrdersTobeCreated == null) throw new RuntimeException("TotalOrders tobe created is not set in the code");
		if(this.totalOrdersTobeCreated <= 0 ) throw new RuntimeException("TotalOrders tobe created CANNOT be zero");*/
		
		if(pool.size() <= 0 ) throw new RuntimeException("Pool is not initiated with more than zero values.. this is due to validCorrelationIdString is not in correct format or is Missing in property file");
		int poolsize = pool.size();
		
		digest.reset();
		digest.update(orderNumberUnitNumber.getBytes());
		byte[] hashedByte =  digest.digest();
		int hashed = new DataInputStream(new ByteArrayInputStream(hashedByte)).readUnsignedByte();

		int mod = hashed % poolsize;
		//System.out.println("" + poolsize + ","+orderNumberUnitNumber + ","+ hashed + "," + mod);
		
		return poolKeyList.get(mod);
		
	}
	
	public void increasePoolbyOne(String correlationId) {
		if(correlationId != null && correlationId.length() > 0 ) {
			int nextvalue = pool.get(correlationId) == null ? 0 : pool.get(correlationId) + 1;
			pool.put(correlationId, Integer.valueOf(nextvalue));
		}
	}
	
	public void spitPoolReport() {
		System.out.println(" Orders Distribution : ");
		for(String correlationId : poolKeyList ) {
			System.out.println(" "+ correlationId + " --> " + pool.get(correlationId));
		}
	}
    
	public static void main(String args[]) {
		int totalOrder = 1000;
		MessageCorrelationIDBaseDistributor dis = new MessageCorrelationIDBaseDistributor("2,3,4,5");
		//dis.setTotalOrdersTobeCreated(totalOrder);
		for(int i = 0 ;  i < totalOrder;  i++) {
			try {
				String value = dis.getCorrelationId("som-10"+(i+1)+"sun3-"+(i+1));
				System.out.println("Corr ID = " + value);
				dis.increasePoolbyOne(value);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		dis.spitPoolReport();
	}

}
