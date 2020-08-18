package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.web.dto.ajax.AjaxSPNCampaignXTO;
import com.newco.marketplace.web.dto.ajax.AjaxSelectItemDTO;
import com.newco.marketplace.web.dto.ajax.AjaxSubSelectItemDTO;
import com.newco.marketplace.web.utils.Ajaxable;

public abstract class AbstractAjaxResultsDTO extends SerializedBaseDTO implements Ajaxable{
	
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
	
	private String addtionalInfo13 = "none";
	
	//SL-21233: Document Retrieval Code Starts
	
	private String addtionalInfo14 = "none";
	private String addtionalInfo15 = "none";
	
	//SL-21233: Document Retrieval Code Ends
	
	private String selectSet = "selected";
	
	private List<AjaxSelectItemDTO> _selectList = new ArrayList<AjaxSelectItemDTO>();
	private List<AjaxSubSelectItemDTO> _subSelectList = new ArrayList<AjaxSubSelectItemDTO>();
	private List<AjaxSPNCampaignXTO> _spnCampaign = new ArrayList<AjaxSPNCampaignXTO>();
	private List<Ajaxable>_ajaxAbleItems = new ArrayList<Ajaxable>();
	private boolean containsNestedAjaxableItems = false;
	
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

	


	public void addSubSelectItem( AjaxSubSelectItemDTO subSelect ) {
		if(subSelect != null)
			_subSelectList.add(subSelect);
	}
	
	public void addSelectItem( AjaxSelectItemDTO selectItem ) {
		if(selectItem != null)
			_selectList.add(selectItem);
	}
	
	public String getSelectListToXml() {
		StringBuffer sb1 = new StringBuffer();
		for( int i = 0; i<get_selectList().size(); i++ )
		{
			sb1.append( get_selectList().get(i).toXml());
		}
		return sb1.toString();
	}
	
	public String getSubSelectListToXml() {
		StringBuffer sb1 = new StringBuffer();
		for( int i = 0; i<get_subSelectList().size(); i++ )
		{
			sb1.append( get_subSelectList().get(i).toXml().trim());
		}
		return sb1.toString().trim();
	}
	
	public String fireToXMLOnSubList() {
		StringBuffer sb1 = new StringBuffer();
		for( int i = 0; i<get_ajaxAbleItems().size(); i++ )
		{
			sb1.append( get_ajaxAbleItems().get(i).toXml().trim());
		}
		return sb1.toString().trim();
	}
	
	public List<AjaxSelectItemDTO> get_selectList() {
		return _selectList;
	}

	public List<AjaxSubSelectItemDTO> get_subSelectList() {
		return _subSelectList;
	}
	
	public String getSelectSetName() {
		return selectSet;
	}

	public void setSelectSetName(String selectSet) {
		this.selectSet = selectSet;
	}

	public List<Ajaxable> get_ajaxAbleItems() {
		return _ajaxAbleItems;
	}

	public void addAjaxAbleItems(Ajaxable ableItems) {
		_ajaxAbleItems.add(ableItems);
		setContainsNestedAjaxableItems(true);
	}

	public boolean isContainsNestedAjaxableItems() {
		return containsNestedAjaxableItems;
	}

	public void setContainsNestedAjaxableItems(boolean containsNestedAjaxableItems) {
		this.containsNestedAjaxableItems = containsNestedAjaxableItems;
	}
	
	protected void clearAjaxableState(){
		_ajaxAbleItems.clear();
		_selectList.clear();
		_subSelectList.clear();
		_spnCampaign.clear();
		
	}

	public List<AjaxSPNCampaignXTO> get_spnCampaign() {
		return _spnCampaign;
	}

	public void set_spnCampaign(List<AjaxSPNCampaignXTO> campaign) {
		_spnCampaign = campaign;
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

	public String getAddtionalInfo13() {
		return addtionalInfo13;
	}

	public void setAddtionalInfo13(String addtionalInfo13) {
		this.addtionalInfo13 = addtionalInfo13;
	}
	
	//SL-21233: Document Retrieval Code Starts
	
	public String getAddtionalInfo14() {
		return addtionalInfo14;
	}

	public void setAddtionalInfo14(String addtionalInfo14) {
		this.addtionalInfo14 = addtionalInfo14;
	}

	public String getAddtionalInfo15() {
		return addtionalInfo15;
	}

	public void setAddtionalInfo15(String addtionalInfo15) {
		this.addtionalInfo15 = addtionalInfo15;
	}
	
	//SL-21233: Document Retrieval Code Ends
	
}
