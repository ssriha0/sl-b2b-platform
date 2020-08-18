package com.newco.marketplace.dto.vo.serviceorder;

import java.util.ArrayList;

import com.newco.marketplace.dto.vo.assessQuestion.AssessSkillCatQuesVO;
import com.sears.os.vo.SerializableBaseVO;

public class ServiceOrderAssessRespVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7345011205606976931L;
	private int soId;
	private ArrayList<AssessSkillCatQuesVO> skillcatquestions;
	
	public int getSoId() {
		return soId;
	}
	public void setSoId(int soId) {
		this.soId = soId;
	}
	public ArrayList<AssessSkillCatQuesVO> getSkillcatquestions() {
		return skillcatquestions;
	}
	public void setSkillcatquestions(
			ArrayList<AssessSkillCatQuesVO> skillcatquestions) {
		this.skillcatquestions = skillcatquestions;
	}
	
}
