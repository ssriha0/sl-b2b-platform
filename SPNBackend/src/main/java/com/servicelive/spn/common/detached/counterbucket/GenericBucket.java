package com.servicelive.spn.common.detached.counterbucket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.servicelive.spn.common.detached.SpnMonitorCountVO;

public abstract class GenericBucket {

	private BucketType bucketType;

	private List<GenericBucket> buckets = new ArrayList<GenericBucket>();

	private Map<Integer, SpnMonitorCountVO> map = new TreeMap<Integer, SpnMonitorCountVO>();

	public GenericBucket(BucketType bucketType) {
		this.bucketType = bucketType;
	}

	/**
	 * 
	 * @return count
	 */
	public Integer getCount() {
		return map.entrySet().size();
	}
	

	/**
	 * 
	 * @param genericBucket
	 */
	public void addBucket(GenericBucket genericBucket) {
		buckets.add(genericBucket);
	}

	/**
	 * @return the bucketType
	 */
	public BucketType getBucketType() {
		return bucketType;
	}

	/**
	 * 
	 */
	public void handleInnerBuckets(SpnMonitorCountVO vo) {
		for(GenericBucket bucket:buckets) {
			bucket.process(vo);
		}
	}

	/**
	 * 
	 */
	public void process(SpnMonitorCountVO vo) {
		handleThisBucket(vo);
		handleInnerBuckets(vo);
	}

	/**
	 * 
	 */
	public abstract void handleThisBucket(SpnMonitorCountVO vo);

	/**
	 * @return the map
	 */
	public Map<Integer, SpnMonitorCountVO> getMap() {
		return map;
	}

	/**
	 * @return the buckets
	 */
	public List<GenericBucket> getBuckets() {
		return buckets;
	}
}
