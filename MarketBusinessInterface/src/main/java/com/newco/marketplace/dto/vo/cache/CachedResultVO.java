/**
 * 
 */
package com.newco.marketplace.dto.vo.cache;

import java.sql.Timestamp;
import java.util.HashMap;

import com.sears.os.vo.SerializableBaseVO;


/**
 * @author SALI030
 *
 */

@org.jboss.cache.pojo.annotation.Replicable
public abstract class CachedResultVO  extends SerializableBaseVO{
	private HashMap<String, Integer> summaryCount;
	
	private HashMap<Integer, BuyerDetailCountVO> detailedCount;
	
	private Timestamp createTime = new Timestamp(System.currentTimeMillis());
	
	public HashMap<String, Integer> getSummaryCount() {
		return summaryCount;
	}
	
	public void setSummaryCount(HashMap<String, Integer> summaryCount) {
		this.summaryCount = summaryCount;
	}
	
	public HashMap<Integer, BuyerDetailCountVO> getDetailedCount() {
		return detailedCount;
	}
	
	public void setDetailedCount(HashMap<Integer, BuyerDetailCountVO> detailedCount) {
		this.detailedCount = detailedCount;
	}
	
	public Timestamp getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
