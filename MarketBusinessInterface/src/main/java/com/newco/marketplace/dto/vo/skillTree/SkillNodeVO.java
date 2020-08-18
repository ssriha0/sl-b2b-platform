package com.newco.marketplace.dto.vo.skillTree;

import java.util.List;

import com.sears.os.vo.SerializableBaseVO;


/**
 * @author glacy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SkillNodeVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8895692626599121275L;
	protected String nodeName;
	private Integer parentNodeId;	
	protected Integer nodeId;
	private Integer level;
	private boolean active;
	private List<ServiceTypesVO> serviceTypes;
	private String rootNode;
	private String stateCd;
	private String buyerId;
	private Integer sortOrder;
	private Double matchFactorDecrement;
	private Integer rootNodeId;
	private String decriptionContent;


//	protected boolean root;
//	protected boolean leaf;
//	protected String nodeDescription;
//	protected NodeContent myContent;
//	private AWizzardAttribute aWizzardAttribute1;
//	protected AWizzardAttribute nodeAttributes[];
	
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	/** @return Array List of serviceTypes*/
	public List<ServiceTypesVO> getServiceTypes() {
		return serviceTypes;
	}
	/** @param serviceTypes */
	public void setServiceTypes(List<ServiceTypesVO> serviceTypes) {
		this.serviceTypes = serviceTypes;
	}
	/** @return boolean isActive */
	public boolean isActive() {
		return active;
	}
	/** @param active */
	public void setActive(boolean active) {
		this.active = active;
	}
	/**@return long parentNodeId*/
	public long getParentNodeId() {
		return parentNodeId;
	}
	/**@param parentNodeId */
	public void setParentNodeId(Integer parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	/**@return int nodeId*/
	public Integer getNodeId() {
		return nodeId;
	}
	/** @param nodeId 
	 */
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
	
	/** @return String nodeName */
	public String getNodeName() {
		return nodeName;
	}
	
	/** @param nodeName */
	public void setNodeName(String nodeName) {
		try{
			this.nodeName = nodeName;
		}catch(NullPointerException npe){
			this.nodeName = "";
		}
	}
	/** @return int level */
	public Integer getLevel() {
		return level;
	}
	/** @param level */
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@Override
	public String toString(){
		return nodeName;
	}
	public Double getMatchFactorDecrement() {
		return matchFactorDecrement;
	}
	public void setMatchFactorDecrement(Double matchFactorDecrement) {
		this.matchFactorDecrement = matchFactorDecrement;
	}
	

	/** @return String stateCd */
	public String getStateCd() {
		return stateCd;
	}
	
	/** @param stateCd */
	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}
	
	/** @return String buyerId */
	public String getBuyerId() {
		return buyerId;
	}
	
	/** @param buyerId */
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	
	/** @return String rootNode */
	public String getRootNode() {
		return rootNode;
	}
	
	/** @param rootNode */
	public void setRootNode(String rootNode) {
		this.rootNode = rootNode;
	}
	public Integer getRootNodeId() {
		return rootNodeId;
	}
	public void setRootNodeId(Integer rootNodeId) {
		this.rootNodeId = rootNodeId;
	}
	/**
	 * @return the decriptionContent
	 */
	public String getDecriptionContent() {
		return decriptionContent;
	}
	/**
	 * @param decriptionContent the decriptionContent to set
	 */
	public void setDecriptionContent(String decriptionContent) {
		this.decriptionContent = decriptionContent;
	}
}
