package com.newco.marketplace.dto.vo.serviceorder;

import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

public class ServiceOrderStatusVO extends SerializableBaseVO{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 753014298680505371L;
private int statusId;
private String statusName;
private List<ServiceOrderSubStatusVO>  serviceOrderSubStatusVO;
private String statusQuery;
private Boolean selected;
public Boolean getSelected() {
	return selected;
}
public void setSelected(Boolean selected) {
	this.selected = selected;
}
public String getStatusQuery() {
	return statusQuery;
}
public void setStatusQuery(String statusQuery) {
	this.statusQuery = statusQuery;
}
public int getStatusId() {
	return statusId;
}
public void setStatusId(int statusId) {
	this.statusId = statusId;
}
public String getStatusName() {
	return statusName;
}
public void setStatusName(String statusName) {
	this.statusName = statusName;
}
public List<ServiceOrderSubStatusVO> getServiceOrderSubStatusVO() {
	return serviceOrderSubStatusVO;
}
public void setServiceOrderSubStatusVO(List<ServiceOrderSubStatusVO>  serviceOrderSubStatusVO) {
	this.serviceOrderSubStatusVO = serviceOrderSubStatusVO;
}

@Override
public String toString(){
	StringBuffer sb = new StringBuffer();
	sb.append("[ ");
	sb.append("statusId: "+statusId);
	sb.append(", statusName: "+statusName);
	if(serviceOrderSubStatusVO!=null && serviceOrderSubStatusVO.size()>0){
		sb.append("\n");
		for(int i=0;i<serviceOrderSubStatusVO.size();i++){
			ServiceOrderSubStatusVO s= serviceOrderSubStatusVO.get(i);
			sb.append("\t");
			sb.append(s.toString());
			sb.append("\n");
		}
		if(null!=selected)
		{
		sb.append(selected.toString());
		}
	}
	
	sb.append(" ]");
	return sb.toString();
	
}
}
