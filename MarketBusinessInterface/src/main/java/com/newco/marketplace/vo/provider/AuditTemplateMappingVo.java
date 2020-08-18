package com.newco.marketplace.vo.provider;


public class AuditTemplateMappingVo extends BaseVO {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2859858375109269927L;
	private String stateTransitionId = null;
    private Integer targetStateId = null;
    private Integer sourceStateId = null;
    private String sourceStateName = null;
    private String targetStateName = null;
    private String workflowEntity = null;
    private String templateSource = null;
    private String templateSubject = null;
    private String templateFrom = null;
    private Integer templateId = null;

    public String getStateTransitionId() {
        return stateTransitionId;
    }

    public void setStateTransitionId(String stateTransitionId) {
        this.stateTransitionId = stateTransitionId;
    }

    public String getTemplateSource() {
        return templateSource;
    }

    public void setTemplateSource(String templateSource) {
        this.templateSource = templateSource;
    }

    public String getWorkflowEntity() {
        return workflowEntity;
    }

    public void setWorkflowEntity(String workflowEntity) {
        this.workflowEntity = workflowEntity;
    }

    public String getSourceStateName() {
        return sourceStateName;
    }

    public void setSourceStateName(String sourceStateName) {
        this.sourceStateName = sourceStateName;
    }

    public String getTargetStateName() {
        return targetStateName;
    }

    public void setTargetStateName(String targetStateName) {
        this.targetStateName = targetStateName;
    }

    public Integer getSourceStateId() {
        return sourceStateId;
    }

    public void setSourceStateId(Integer sourceStateId) {
        this.sourceStateId = sourceStateId;
    }

    @Override
	public String toString() {
        return ("\n<templateMappingvo>" + stateTransitionId + "  | target ID "
                + targetStateId + "  | target Name " + targetStateName
                + "  |  workflowEntity " + workflowEntity + " | templateSource"+ templateSource +"</templateMappingVo> \n");
    }

    public Integer getTargetStateId() {
        return targetStateId;
    }

    public void setTargetStateId(Integer targetStateId) {
        this.targetStateId = targetStateId;
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

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
}
