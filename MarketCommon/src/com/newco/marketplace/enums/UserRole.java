package com.newco.marketplace.enums;

public enum UserRole {

	PROVIDER(1) {
		@Override
		public String getTypeDescription() {
			return "Provider";
		}
	},
	
	NEWCO(2) {
		@Override
		public String getTypeDescription() {
			return "NewCo";
		}
	},
	
	BUYER(3) {
		@Override
		public String getTypeDescription() {
			return "Buyer";
		}
	},
	
	SIMPLE_BUYER(5) {
		@Override
		public String getTypeDescription() {
			return "SimePleBuyer";
		}
	};
	
	private int type;
	private UserRole(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
	
	public abstract String getTypeDescription();
}
