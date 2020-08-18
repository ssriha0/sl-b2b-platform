package com.servicelive.orderfulfillment.domain.type;

public enum SOTaskType {
	Primary(0), Permit(1), Delivery(2), NonPrimary(3);

	private int databaseId;

	private SOTaskType(int id) {
		databaseId = id;
	}

	public int getId() {
		return databaseId;
	}
}
