package com.servicelive.domain.so;

public enum SOTaskPriceType {
    PRIMARY(0),
	PERMIT(1),
    DELIVERY(2),
    NON_PRIMARY(3),
    DUPLICATE(4);
    
    private int type;
	private SOTaskPriceType(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
}
