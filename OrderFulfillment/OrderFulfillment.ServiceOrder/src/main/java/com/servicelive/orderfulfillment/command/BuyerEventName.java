package com.servicelive.orderfulfillment.command;

public enum BuyerEventName {

		ACCEPT_ORDER (1),
		RESCHEDULE_ACCEPT (2),
		CANCEL_ORDER (3),
		CLOSE_ORDER (4);
		
		private final int statusId;
		
		private BuyerEventName(int eventStatusId) {
			statusId = eventStatusId;
		}
		
		public final int getId() {
			return statusId;
		}
	}