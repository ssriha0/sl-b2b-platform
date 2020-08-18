package com.newco.marketplace.vo.provider;


public class TemplateVo extends BaseVO {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 5046991556326963845L;
	private String templateSource = null;
    private String templateSubject = null;
    private String templateFrom = null;
    private String templateEid = null;
    private Integer templateId  = null; 
	private Integer templateTypeId = null; 
	private String priority = null;  
	
    public String getTemplateSource() {
        return templateSource;
    }

    public void setTemplateSource(String templateSource) {
        this.templateSource = templateSource;
    }

    public String getTemplateSubject() {
        return templateSubject;
    }

    public void setTemplateSubject(String templateSubject) {
        this.templateSubject = templateSubject;
    }

    public String getTemplateFrom() {
        return templateFrom;
    }

    public void setTemplateFrom(String templateFrom) {
        this.templateFrom = templateFrom;
    }

    @Override
	public String toString(){
        return ("<TemplateVO>"
                + "templateSource" + templateSource 
                + "templateSubject" +templateSubject 
                + "templateFrom" + templateFrom
                + "templateEid"  + templateEid.toString()
                + "templateId"  + templateId.toString()
                + "</TemplateVO>");
    }

	/**
	 * @return the templateEid
	 */
	public String getTemplateEid() {
		return templateEid;
	}

	/**
	 * @param templateEid the templateEid to set
	 */
	public void setTemplateEid(String templateEid) {
		this.templateEid = templateEid;
	}
	
	/**
	 * @return the templateId
	 */
	public Integer getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId the templateEid to set
	 */
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	
	/**
	 * @return the templateTypeId
	 */
	public Integer getTemplateTypeId() {
		return templateTypeId;
	}

	/**
	 * @param templateTypeId the templateTypeId to set
	 */
	public void setTemplateTypeId(Integer templateTypeId) {
		this.templateTypeId = templateTypeId;
	}

	
	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

}
