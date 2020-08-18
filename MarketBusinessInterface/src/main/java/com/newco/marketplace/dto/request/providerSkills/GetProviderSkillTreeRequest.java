package com.newco.marketplace.dto.request.providerSkills;

import com.sears.os.vo.SerializableBaseVO;

/**
 *  Description of the Class
 *
 *@author     dmill03
 *@created    August 4, 2007
 */
public class GetProviderSkillTreeRequest extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1687255436626037323L;
	private int skillsNodeId = -1;
	private String skillName;


	/**
	 *  Gets the skillsNodeId attribute of the GetProviderSkillTreeRequest
	 *  object
	 *
	 *@return    The skillsNodeId value
	 */
	public int getSkillsNodeId() {
		return skillsNodeId;
	}


	/**
	 *  Sets the skillsNodeId attribute of the GetProviderSkillTreeRequest
	 *  object
	 *
	 *@param  skillsNodeId  The new skillsNodeId value
	 */
	public void setSkillsNodeId(int skillsNodeId) {
		this.skillsNodeId = skillsNodeId;
	}



	/**
	 *  Gets the skillName attribute of the GetProviderSkillTreeRequest
	 *  object
	 *
	 *@return    The skillName value
	 */
	public String getSkillName() {
		return skillName;
	}


	/**
	 *  Sets the skillName attribute of the GetProviderSkillTreeRequest
	 *  object
	 *
	 *@param  skillName  The new skillName value
	 */
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

}

