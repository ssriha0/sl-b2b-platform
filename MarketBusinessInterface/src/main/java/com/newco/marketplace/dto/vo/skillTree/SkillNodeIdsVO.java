package com.newco.marketplace.dto.vo.skillTree;

import java.util.ArrayList;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author zizrale
 *
 */
public class SkillNodeIdsVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3961268348014517373L;
	ArrayList<Integer> skillNodeIds;
	int rootNodeId;
	private Double zipLatitude;
	private Double zipLongitude;
	private Integer distanceFilter;
	private Integer spnId;
	private Integer lockedResourceId;
	private Integer tierId;
	private Double perfScore;
	//SLT-2545 Zip code based order routing changes START
	private String zipCode;
	//SLT-2545 Zip code based order routing changes END
	/**
	 * @return ArrayList
	 */
	public ArrayList<Integer> getSkillNodeIds() {
		return skillNodeIds;
	}

	/**
	 * @param skillNodeIds
	 * 
	 */
	public void setSkillNodeIds(ArrayList<Integer> skillNodeIds) {
		this.skillNodeIds = skillNodeIds;
	}

	public int getRootNodeId() {
		return rootNodeId;
	}

	public boolean contains(int node_id){
		return skillNodeIds.contains(node_id);
	}
	public void setRootNodeId(int rootNodeId) {
		this.rootNodeId = rootNodeId;
	}

	public Double getZipLatitude() {
		return zipLatitude;
	}

	public void setZipLatitude(Double zipLatitude) {
		this.zipLatitude = zipLatitude;
	}

	public Double getZipLongitude() {
		return zipLongitude;
	}

	public void setZipLongitude(Double zipLongitude) {
		this.zipLongitude = zipLongitude;
	}

	public Integer getDistanceFilter() {
		return distanceFilter;
	}

	public void setDistanceFilter(Integer distanceFilter) {
		this.distanceFilter = distanceFilter;
	}

	public Integer getSpnId() {
		return spnId;
	}

	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}

	public Integer getLockedResourceId() {
		return lockedResourceId;
	}

	public void setLockedResourceId(Integer lockedResourceId) {
		this.lockedResourceId = lockedResourceId;
	}

	public Integer getTierId() {
		return tierId;
	}

	public void setTierId(Integer tierId) {
		this.tierId = tierId;
	}

	public Double getPerfScore() {
		return perfScore;
	}

	public void setPerfScore(Double perfScore) {
		this.perfScore = perfScore;
	}
	//SLT-2545 Zip code based order routing changes START
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	//SLT-2545 Zip code based order routing changes END
}
