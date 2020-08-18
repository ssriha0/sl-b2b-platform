package com.newco.marketplace.dto.vo.financemanger;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.vo.PaginationVO;
import com.newco.marketplace.vo.provider.BaseVO;


public class ReportContentVO  extends BaseVO {

private static final long serialVersionUID = 1L;
private List<String> headerList =new ArrayList<String>();
private List<String> footerList=new ArrayList<String>();
private List<ArrayList<String>> commonBody=new ArrayList<ArrayList<String>>();
private PaginationVO paginationVO = new PaginationVO();
private int startIndex;
private int numberOfRecords;
private int totalPage;
private String infoMessage;
private String pageTitle;

public List<String> getHeaderList() {
	return headerList;
}
public void setHeaderList(List<String> headerList) {
	this.headerList = headerList;
}
public List<ArrayList<String>> getCommonBody() {
	return commonBody;
}
public void setCommonBody(List<ArrayList<String>> commonBody) {
	this.commonBody = commonBody;
}
public List<String> getFooterList() {
	return footerList;
}
public void setFooterList(List<String> footerList) {
	this.footerList = footerList;
}
public PaginationVO getPaginationVO() {
	return paginationVO;
}
public void setPaginationVO(PaginationVO paginationVO) {
	this.paginationVO = paginationVO;
}
public int getTotalPage() {
	return totalPage;
}
public void setTotalPage(int totalPage) {
	this.totalPage = totalPage;
}
public int getStartIndex() {
	return startIndex;
}
public void setStartIndex(int startIndex) {
	this.startIndex = startIndex;
}
public int getNumberOfRecords() {
	return numberOfRecords;
}
public void setNumberOfRecords(int numberOfRecords) {
	this.numberOfRecords = numberOfRecords;
}
public String getInfoMessage() {
	return infoMessage;
}
public void setInfoMessage(String infoMessage) {
	this.infoMessage = infoMessage;
}
public String getPageTitle() {
	return pageTitle;
}
public void setPageTitle(String pageTitle) {
	this.pageTitle = pageTitle;
}


}
