package com.newco.marketplace.search.vo;

public class BaseRequestVO {
	public final static int DEFAULT_RADIOUS = 25;
	public final static byte REQ_TYPE_BY_ZIP = 0;
	public final static byte REQ_TYPE_BY_RESOURCE_ID = 1;
	public final static byte REQ_TYPE_BY_CITY_STATE = 2;
	public final static byte REQ_TYPE_CITY_LIST = 3;
	public final static byte REQ_TYPE_BY_SKILLS = 4;
	public final static byte REQ_TYPE_BY_CITY_STATE_SKILLS = 5;
	public final static int DEFAULT_PAGE_SIZE = 25;

	private byte requestAPIType;
	private int pageNumber;
	private int pageSize;
	private String solrURL;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public byte getRequestAPIType() {
		return requestAPIType;
	}

	public void setRequestAPIType(byte requestAPIType) {
		this.requestAPIType = requestAPIType;
	}

	public String getSolrURL() {
		return solrURL;
	}

	public void setSolrURL(String solrURL) {
		this.solrURL = solrURL;
	}



}
