package com.newco.marketplace.web.dto;

public class SODetailsTabDTO extends SerializedBaseDTO{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3848331658789780422L;
	private String id;
	private String selected;
	private String action;
	private String estimationId;
	//Added for Dojo to jQuery conversion
	private String selClass;
	private boolean refreshOnLoad = false;
	
	public SODetailsTabDTO(String id,  String selected, String action, boolean refreshOnLoad ){
		this.id = id;
		if(selected != null)
		{
			if(selected.equalsIgnoreCase(id))
				this.selected = "true";
			else
				this.selected = "false";
		}
		else
		{
			this.selected = "false";
		}
		if(selected != null && selected.equalsIgnoreCase(id))
		{
			this.selClass = "ui-tabs-selected";
		}
		else{
			this.selClass = " ";
		}
		this.action = action;
		
		this.refreshOnLoad = refreshOnLoad;
	}
	
	public SODetailsTabDTO(String id,  String selected, String action)
	{
		this.id = id;
		if(selected != null)
		{
			if(selected.equalsIgnoreCase(id))
				this.selected = "true";
			else
				this.selected = "false";
		}
		else
		{
			this.selected = "false";
		}
		if(selected != null && selected.equalsIgnoreCase(id))
		{
			this.selClass = "ui-tabs-selected";
		}
		else{
			this.selClass = " ";
		}
		
		this.action = action;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	public boolean isRefreshOnLoad() {
		return refreshOnLoad;
	}

	public void setRefreshOnLoad(boolean refreshOnLoad) {
		this.refreshOnLoad = refreshOnLoad;
	}

	public String getSelClass() {
		return selClass;
	}

	public void setSelClass(String selClass) {
		this.selClass = selClass;
	}

	public String getEstimationId() {
		return estimationId;
	}

	public void setEstimationId(String estimationId) {
		this.estimationId = estimationId;
	}

	
	
	
	
//	<div id="Support" dojoType="dijit.layout.ContentPane"
//		title="Support" selected="false"
//		href="${contextPath}/jsp/details/body/html_sections/modules/tab_support.jsp">
//	</div>
	
	
}
