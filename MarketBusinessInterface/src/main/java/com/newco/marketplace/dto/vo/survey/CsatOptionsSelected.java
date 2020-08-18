package com.newco.marketplace.dto.vo.survey;

public class CsatOptionsSelected {
    String id;
    String text;
    Boolean selected;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CsatOptionsSelected [id=").append(id).append(", text=").append(text).append(", selected=")
				.append(selected.toString()).append("]");
		return builder.toString();
	}
    
    

}
