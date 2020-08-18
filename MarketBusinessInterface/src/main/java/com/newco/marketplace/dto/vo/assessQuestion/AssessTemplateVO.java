package com.newco.marketplace.dto.vo.assessQuestion;
import com.sears.os.vo.SerializableBaseVO;

public class AssessTemplateVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2442051126226032446L;
	private int templateId;
	private String templateDesc;
	private String templateCntrl;
	private int answerTypeId;
	
	public int getTemplateId() {
		return templateId;
	}
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	public String getTemplateDesc() {
		return templateDesc;
	}
	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}
	public String getTemplateCntrl() {
		return templateCntrl;
	}
	public void setTemplateCntrl(String templateCntrl) {
		this.templateCntrl = templateCntrl;
	}
	public int getAnswerTypeId() {
		return answerTypeId;
	}
	public void setAnswerTypeId(int answerTypeId) {
		this.answerTypeId = answerTypeId;
	}
	
	

}
