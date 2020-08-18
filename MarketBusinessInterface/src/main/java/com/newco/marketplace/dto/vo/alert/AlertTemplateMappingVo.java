package com.newco.marketplace.dto.vo.alert;

import com.sears.os.vo.ABaseVO;

public class AlertTemplateMappingVo extends ABaseVO {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7101036892904152438L;
	private Integer templateId = null;
    private String templateSource = null;
    private String templateSubject = null;
    private String templateFrom = null;
    private String workflowEntity = null;
    private String targetStateName = null;
    private Integer targetStateId = null;
    private Integer stateTransitionId = null;
        
    public Integer getTemplateId() {
        return templateId;
    }
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }
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
    public String getWorkflowEntity() {
        return workflowEntity;
    }
    public void setWorkflowEntity(String workflowEntity) {
        this.workflowEntity = workflowEntity;
    }
    public String getTargetStateName() {
        return targetStateName;
    }
    public void setTargetStateName(String targetStateName) {
        this.targetStateName = targetStateName;
    }
    public Integer getTargetStateId() {
        return targetStateId;
    }
    public void setTargetStateId(Integer targetStateId) {
        this.targetStateId = targetStateId;
    }
    
    @Override
	public String toString () { 
            return (" \n<AlertTemplateMappingVo> " + " | " + 
                            " | " + templateFrom + 
                            " | " + workflowEntity + " | "
                            + targetStateName + 
                            " | " + targetStateId + " | SUBJECT" 
                            + "   " +  " | " 
                            +"</AlertTemplateMappingVo> \n");
    }
    public Integer getStateTransitionId() {
        return stateTransitionId;
    }
    public void setStateTransitionId(Integer stateTransitionId) {
        this.stateTransitionId = stateTransitionId;
    }

}
