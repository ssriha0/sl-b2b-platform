package com.newco.marketplace.web.dto.provider;

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
@XStreamAlias("ResourceSkill")
public class ResourceSkillAssignDto extends BaseDto
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5587503838886930912L;
	@XStreamAlias("ResourceId")
	private long resourceId;
	@XStreamAlias("ResourceName")
	private String resourceName;
	@XStreamAlias("NodeId")
    private int nodeId;
	@XStreamAlias("RootNodeId")
    private int rootNodeId;
	@XStreamAlias("ModifiedBy")
    private String modifiedBy;
	@XStreamOmitField
    private ArrayList skillTreeList;
	@XStreamOmitField
	private ArrayList serviceTypes;
	@XStreamAlias("CheckedNodes")
    private ArrayList checkedNodes;
	@XStreamAlias("SkillServiceList")
    private ArrayList skillServiceList;
	@XStreamAlias("SkillServiceListToDelete")
    private ArrayList skillServiceListToDelete;
	@XStreamAlias("LanguageList")
    private ArrayList languageList;
	@XStreamAlias("LastServicePage")
    private boolean lastServicePage;
    // private String availSkills;
    // private String selectedSkills;
    // private String[] selectedItems = {};
    
    /** @return ArrayList checked nodes*/
    public ArrayList getCheckedNodes() {
		return checkedNodes;
	}
	/** @param checkedNodes */
	public void setCheckedNodes(ArrayList checkedNodes) {
		this.checkedNodes = checkedNodes;
	}
	/** @return ArrayList service types */
	public ArrayList getServiceTypes() {
		return serviceTypes;
	}
	/** @param skillAttributes */
	public void setServiceTypes(ArrayList skillAttributes) {
		this.serviceTypes = skillAttributes;
	}
	/** @return ArrayList skill tree list*/
	public ArrayList getSkillTreeList() {
		return skillTreeList;
	}
	/** @param skillTreeList */
	public void setSkillTreeList(ArrayList skillTreeList) {
		this.skillTreeList = skillTreeList;
	}
	/** @return String modifiedBy */
    public String getModifiedBy() {
		return modifiedBy;
	}
	/** @param modifiedBy */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/** @return int nodeId */
    public int getNodeId() {
		return nodeId;
	}
	/** @param nodeId */
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	/** @return long resourceId */
	public long getResourceId() {
		return resourceId;
	}
	/** @param resourceId */
	public void setResourceId(long resourceId) {
		this.resourceId = resourceId;
	}
	/** @return resourceName */
	public String getResourceName() {
		return resourceName;
	}
	/** @param resourceName */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public ArrayList getSkillServiceList() {
		return skillServiceList;
	}
	public ArrayList getSkillServiceListToDelete() {
		return skillServiceListToDelete;
	}
	public void setSkillServiceList(ArrayList skillServiceList) {
		this.skillServiceList = skillServiceList;
	}
	public void setSkillServiceListToDelete(ArrayList skillServiceListToDelete) {
		this.skillServiceListToDelete = skillServiceListToDelete;
	}
	public int getRootNodeId() {
		return rootNodeId;
	}
	public void setRootNodeId(int rootNodeId) {
		this.rootNodeId = rootNodeId;
	}
	public ArrayList getLanguageList() {
		return languageList;
	}
	public void setLanguageList(ArrayList languageList) {
		this.languageList = languageList;
	}
	public boolean isLastServicePage() {
		return lastServicePage;
	}
	public void setLastServicePage(boolean lastServicePage) {
		this.lastServicePage = lastServicePage;
	} 


}
