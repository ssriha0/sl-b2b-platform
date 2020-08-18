package com.newco.marketplace.dto.vo.survey;

import com.sears.os.vo.SerializableBaseVO;

public class SurveyOptionVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5115461186680890525L;
	private int id;
	private String type;
	private String description;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
