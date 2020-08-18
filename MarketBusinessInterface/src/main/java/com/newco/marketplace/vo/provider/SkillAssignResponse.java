package com.newco.marketplace.vo.provider;

import java.util.ArrayList;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author zizrale
 */
public class SkillAssignResponse extends SerializableBaseVO{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4091147857235661119L;
	private int errorId;
    private String message;
    private ArrayList skillTreeList;
    private ArrayList serviceList;
    private ArrayList languageList;
    private String resourceName;
    //Added for I-14
    
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
	public ArrayList getLanguageList() {
		return languageList;
	}
	public void setLanguageList(ArrayList languageList) {
		this.languageList = languageList;
	}
}
