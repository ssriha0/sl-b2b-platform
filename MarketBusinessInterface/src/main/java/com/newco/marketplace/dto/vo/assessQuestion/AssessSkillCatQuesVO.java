package com.newco.marketplace.dto.vo.assessQuestion;

import java.util.ArrayList;

import com.newco.marketplace.dto.vo.skillTree.ServiceTypesVO;
import com.sears.os.vo.SerializableBaseVO;

public class AssessSkillCatQuesVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6391102762533610666L;
	private int skillCatId;
	private ArrayList<ServiceTypesVO> servType;
	private ArrayList<AssessQuestionVO> questions;
		
	public int getSkillCatId() {
		return skillCatId;
	}
	public void setSkillCatId(int skillCatId) {
		this.skillCatId = skillCatId;
	}
	public ArrayList<ServiceTypesVO> getServType() {
		return servType;
	}
	public void setServType(ArrayList<ServiceTypesVO> servType) {
		this.servType = servType;
	}
	public ArrayList<AssessQuestionVO> getQuestions() {
		return questions;
	}
	public void setQuestions(ArrayList<AssessQuestionVO> questions) {
		this.questions = questions;
	}
	
}
