package com.newco.marketplace.vo.provider;


public class TeamMemberDocumentVO{
	
	/**
	 * VO to map documentId and resourceId to the response
	 */
	//R16_1 Get Team Member API
    private Integer documentId;
    private Integer resourceId;
    
    public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
}
