package com.newco.marketplace.dto.vo.incident;

import com.sears.os.vo.SerializableBaseVO;

public class IncidentPartVO extends SerializableBaseVO {


	private static final long serialVersionUID = 1L;
	private Integer incidentPartID;
	private String classCode;
	private String classComments;
	private String partNumber;
	private String oemNumber;
	private String partComments;
	
	
	public Integer getIncidentPartID()
	{
		return incidentPartID;
	}
	public void setIncidentPartID(Integer incidentPartID)
	{
		this.incidentPartID = incidentPartID;
	}
	public String getClassCode()
	{
		return classCode;
	}
	public void setClassCode(String classCode)
	{
		this.classCode = classCode;
	}
	public String getClassComments()
	{
		return classComments;
	}
	public void setClassComments(String classComments)
	{
		this.classComments = classComments;
	}
	public String getPartNumber()
	{
		return partNumber;
	}
	public void setPartNumber(String partNumber)
	{
		this.partNumber = partNumber;
	}
	public String getOemNumber()
	{
		return oemNumber;
	}
	public void setOemNumber(String oemNumber)
	{
		this.oemNumber = oemNumber;
	}
	public String getPartComments()
	{
		return partComments;
	}
	public void setPartComments(String partComments)
	{
		this.partComments = partComments;
	}
	
	
}
