package com.newco.marketplace.dto.response.skillTree;

import java.util.ArrayList;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author zizrale
 */
public class SkillAssignResponse extends SerializableBaseVO{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7038773313377886910L;
	private int errorId;
    private String message;
    private ArrayList skillTreeList;
    private ArrayList serviceList;
    private String resourceName;
    
    /** @return resourceName*/
    public String getResourceName() {
		return resourceName;
	}
	/** @param resourceName */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	/** @return ArrayList of services */
    public ArrayList getServiceList() {
		return serviceList;
	}
	/** @param serviceList */
	public void setServiceList(ArrayList serviceList) {
		this.serviceList = serviceList;
	}
	/** @return ArrayList of skills */
	public ArrayList getSkillTreeList() {
		return skillTreeList;
	}
	/** @param treeList */
	public void setSkillTreeList(ArrayList treeList) {
		this.skillTreeList = treeList;
	}
	/** @return int errorId */
	public int getErrorId()
    {
        return errorId;
    }
    /** @param errorId */
    public void setErrorId(int errorId)
    {
        this.errorId = errorId;
    }
    /** @return String message */
    public String getMessage()
    {
        return message;
    }
    /** @param message */
    public void setMessage(String message)
    {
        this.message = message;
    }
}
