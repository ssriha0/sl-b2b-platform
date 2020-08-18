package com.newco.marketplace.vo; 

import java.util.ArrayList;

import com.sears.os.vo.SerializableBaseVO;


public class PaginationVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2310690717333552232L;
	private int pageSize;  // The number of resultant records that user will see. Our default is 25 
	private ArrayList<PaginetVO> pageSizeBuckets; // The hot link numbers that are available for the user to set the page size
	private int totalRecords; // The total number of records available for the given condition
	private int totalPaginets; // The total number of page numbers that is available for the user
	private ArrayList<PaginetVO> currentResultSetBuckets; // The paginets that are available for the user to select in the current bucket.
	boolean previousIndicator; // This will let the front end know whether to print the Previous hyperlink or not
	boolean nextIndicator; // This will let the front end know whether to print the next hyperlink or not
	private PaginetVO previousPaginet; // 
	private PaginetVO nextPaginet;
	private ArrayList resultSetObjects; // This is the list that will hold all the objects that will 
							        //comprise the current result set and will have the details of each record
	private int startIndex; // This is set by the client
	private int endIndex;// This is set by the client
	private PaginetVO currentPaginet;
	private PaginetVO lastPaginet;
	
	public PaginetVO getLastPaginet() {
		return lastPaginet;
	}
	public void setLastPaginet(PaginetVO lastPaginet) {
		this.lastPaginet = lastPaginet;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public ArrayList<PaginetVO> getPageSizeBuckets() {
		return pageSizeBuckets;
	}
	public void setPageSizeBuckets(ArrayList<PaginetVO> pageSizeBuckets) {
		this.pageSizeBuckets = pageSizeBuckets;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getTotalPaginets() {
		return totalPaginets;
	}
	public void setTotalPaginets(int totalPaginets) {
		this.totalPaginets = totalPaginets;
	}
	public ArrayList<PaginetVO> getCurrentResultSetBuckets() {
		return currentResultSetBuckets;
	}
	public void setCurrentResultSetBuckets(
			ArrayList<PaginetVO> currentResultSetBuckets) {
		this.currentResultSetBuckets = currentResultSetBuckets;
	}
	public boolean isPreviousIndicator() {
		return previousIndicator;
	}
	public void setPreviousIndicator(boolean previousIndicator) {
		this.previousIndicator = previousIndicator;
	}
	public boolean isNextIndicator() {
		return nextIndicator;
	}
	public void setNextIndicator(boolean nextIndicator) {
		this.nextIndicator = nextIndicator;
	}
	public PaginetVO getPreviousPaginet() {
		return previousPaginet;
	}
	public void setPreviousPaginet(PaginetVO previousPaginet) {
		this.previousPaginet = previousPaginet;
	}
	public PaginetVO getNextPaginet() {
		return nextPaginet;
	}
	public void setNextPaginet(PaginetVO nextPaginet) {
		this.nextPaginet = nextPaginet;
	}
	public ArrayList getResultSetObjects() {
		return resultSetObjects;
	}
	public void setResultSetObjects(ArrayList resultSetObjects) {
		this.resultSetObjects = resultSetObjects;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	public PaginetVO getCurrentPaginet() {
		return currentPaginet;
	}
	public void setCurrentPaginet(PaginetVO currentPaginet) {
		this.currentPaginet = currentPaginet;
	}
	
	public ArrayList<PaginetVO> getCurrentResultSetBucket() {
		return currentResultSetBuckets;
	}
	public void setCurrentResultSetBucketList(ArrayList<PaginetVO> currentResultSetBuckets) {
		this.currentResultSetBuckets = currentResultSetBuckets;
	}
	
	
public String toString(){
	
	StringBuffer sb = new StringBuffer();
	PaginetVO p1 = getCurrentPaginet();
	sb.append("Current Paginet ");
	sb.append(p1.toString());
	sb.append("\n");
	ArrayList<PaginetVO> al = getCurrentResultSetBucket();
	
	sb.append("-------------------Current Paginet Bucket--------------\n");
	for(int e=0;e<al.size();e++){
		PaginetVO p = (PaginetVO)al.get(e);
		sb.append(p.toString());
		sb.append("\n");
	}
	sb.append("-------------------------------------------------------");
	sb.append("\n");
	sb.append("Previous Indicator: "+isPreviousIndicator()+"\n");
	sb.append("Next Indicator: "+isNextIndicator()+"\n");
	ArrayList<PaginetVO> pg = getPageSizeBuckets();
	sb.append("--------Page Size Buckets------------------------------\n");
	for (int r=0;r<pg.size();r++) {
		PaginetVO pp = (PaginetVO)pg.get(r);
		sb.append(pp.toString());
		sb.append("\n");
	}
	sb.append("\n");
	sb.append("-------------------------------------------------------");
	sb.append("\n");
	PaginetVO nextPaginet = getNextPaginet();
	sb.append("Next Paginet: "+nextPaginet.toString()+"\n");

	PaginetVO previousPaginet = getPreviousPaginet();
	sb.append("Previous Paginet: "+previousPaginet.toString()+"\n");
	
	return sb.toString();


	
}
}
