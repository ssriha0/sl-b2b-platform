package com.newco.marketplace.web.dto;

public class SLTabDTO extends SerializedBaseDTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6514520746301423151L;
	private String className;
	private String action;
	private String title;
	private String onclick;

	private String selected;
	private String widgetId;
	private String icon="tabIcon ";

	
	public SLTabDTO(String widgetId, String className, String action, String title, String icon, String selected, String onclick)
	{
		this(className,action,title,icon,selected,onclick);
		this.widgetId = widgetId; 
	}
	
	public SLTabDTO(String className, String action, String title, String icon, String selected, String onclick)
	{
		this.className = className;
		this.action = action;
		this.title = title;
		if(icon != null)
			this.icon += icon;	
		if(selected != null)
		{
			if(selected.equalsIgnoreCase(title)){
				this.selected = "true";
			}
			else{
				this.selected = "false";
			}
		}
		else{
			this.selected = "false";
		}
		this.onclick = onclick;
		if(widgetId == null)
		{
			this.widgetId = title;
		}
	}
	
		
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	public String getSelected() {
		return selected;
	}


	public void setSelected(String selected) {
		this.selected = selected;
	}


	public String getOnclick() {
		return onclick;
	}


	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}


	public String getWidgetId() {
		return widgetId;
	}


	public void setWidgetId(String widgetId) {
		this.widgetId = widgetId;
	}
	
}
