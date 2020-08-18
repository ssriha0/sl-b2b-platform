package com.newco.marketplace.dto.vo.fullfillment;

import java.util.ArrayList;

public class FullfillmentAdminToolVO {
	private ArrayList<String> fullfillmentEntryIdList;
	private String fullfillmentEntryId;
	private String userName;
	private String comments;

	public ArrayList<String> getFullfillmentEntryIdList() {
		return fullfillmentEntryIdList;
	}

	public void setFullfillmentEntryIdList(ArrayList<String> fullfillmentEntryIdList) {
		this.fullfillmentEntryIdList = fullfillmentEntryIdList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getFullfillmentEntryId() {
		return fullfillmentEntryId;
	}

	public void setFullfillmentEntryId(String fullfillmentEntryId) {
		this.fullfillmentEntryId = fullfillmentEntryId;
	}
}
