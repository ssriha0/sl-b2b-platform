package com.newco.marketplace.dto.vo.skillTree;

import java.util.ArrayList;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author zizrale
 *
 */
public class SkillServiceTypeVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4450090622823736859L;
	private int skillNodeId;
	private ArrayList serviceTypes;
	private int serviceTypeId;
	
	
	/** @return int serviceTypeId */
	public int getServiceTypeId() {
		return serviceTypeId;
	}
	/** @param serviceTypeId */
	public void setServiceTypeId(int serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	/** @return int node id */
	public int getSkillNodeId() {
		return skillNodeId;
	}
	/** @param skillNodeId */
	public void setSkillNodeId(int skillNodeId) {
		this.skillNodeId = skillNodeId;
	}
	/** @return ArrayList of service types */
	public ArrayList getServiceTypes() {
		return serviceTypes;
	}
	/** @param skillTypes */
	public void setServiceTypes(ArrayList skillTypes) {
		this.serviceTypes = skillTypes;
	}
}
