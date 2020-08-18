package com.newco.marketplace.dto.vo.feedback;

import java.util.ArrayList;
import java.util.List;


import com.newco.marketplace.dto.vo.feedback.Ajaxable;

public abstract class FeedbackAbstractAjaxResultsDTO extends SerializedBaseDTO implements Ajaxable{
	private static final long serialVersionUID = -1527770441184997895L;
	private int actionState = 0;
	private String resultMessage = "N/A";
	private String addtionalInfo1 = "none";
	private String addtionalInfo2 = "none";
	private String addtionalInfo3 = "none";
	private String addtionalInfo4 = "none";
	private String addtionalInfo5 = "none";
	private String addtionalInfo6 = "none";
	private String addtionalInfo7 = "none";
	private String addtionalInfo8 = "none";
	private String addtionalInfo9 = "none"; 
	private String addtionalInfo10 = "none";
	private String addtionalInfo11 = "none";
	private String addtionalInfo12 = "none";
	private String selectSet = "selected";
	

	
	public String toJSON() {
		
		return getResultMessage();
	}

	public String toXml() {
		return null;
	}
	
	public String toXmlDeepCopy() {
		return null;
	}
	
	protected String marshallSelectContainer() {
		return "";
	}

	public int getActionState() {
		return actionState;
	}

	public void setActionState(int actionState) {
		this.actionState = actionState;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}


	public String getAddtionalInfo1() {
		return addtionalInfo1;
	}

	public void setAddtionalInfo1(String addtionalInfo1) {
		this.addtionalInfo1 = addtionalInfo1;
	}

	public String getAddtionalInfo2() {
		return addtionalInfo2;
	}

	public void setAddtionalInfo2(String addtionalInfo2) {
		this.addtionalInfo2 = addtionalInfo2;
	}

	public String getAddtionalInfo3() {
		return addtionalInfo3;
	}

	public void setAddtionalInfo3(String addtionalInfo3) {
		this.addtionalInfo3 = addtionalInfo3;
	}

	public String getAddtionalInfo4() {
		return addtionalInfo4;
	}

	public void setAddtionalInfo4(String addtionalInfo4) {
		this.addtionalInfo4 = addtionalInfo4;
	}

	
	public String getSelectSetName() {
		return selectSet;
	}

	public void setSelectSetName(String selectSet) {
		this.selectSet = selectSet;
	}


	public String getAddtionalInfo5() {
		return addtionalInfo5;
	}

	public void setAddtionalInfo5(String addtionalInfo5) {
		this.addtionalInfo5 = addtionalInfo5;
	}

	public String getAddtionalInfo6() {
		return addtionalInfo6;
	}

	public void setAddtionalInfo6(String addtionalInfo6) {
		this.addtionalInfo6 = addtionalInfo6;
	}

	public String getAddtionalInfo7() {
		return addtionalInfo7;
	}

	public void setAddtionalInfo7(String addtionalInfo7) {
		this.addtionalInfo7 = addtionalInfo7;
	}

	public String getAddtionalInfo8() { 
		return addtionalInfo8;
	}

	public void setAddtionalInfo8(String addtionalInfo8) {
		this.addtionalInfo8 = addtionalInfo8;
	}

	public String getAddtionalInfo9() {
		return addtionalInfo9;
	}

	public void setAddtionalInfo9(String addtionalInfo9) {
		this.addtionalInfo9 = addtionalInfo9;
	}

	public String getAddtionalInfo10() {
		return addtionalInfo10;
	}

	public void setAddtionalInfo10(String addtionalInfo10) {
		this.addtionalInfo10 = addtionalInfo10;
	}

	public String getAddtionalInfo11() {
		return addtionalInfo11;
	}

	public void setAddtionalInfo11(String addtionalInfo11) {
		this.addtionalInfo11 = addtionalInfo11;
	}

	public String getAddtionalInfo12() {
		return addtionalInfo12;
	}

	public void setAddtionalInfo12(String addtionalInfo12) {
		this.addtionalInfo12 = addtionalInfo12;
	}
}
