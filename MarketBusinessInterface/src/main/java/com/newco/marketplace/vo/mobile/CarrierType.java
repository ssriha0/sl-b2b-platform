package com.newco.marketplace.vo.mobile;

public enum CarrierType {
	UPS(1),
	FedEx(2),
	Other(3),
	DHL(4),
	USPS(5);
	Integer id;
	
	private CarrierType(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public static CarrierType fromId(int id){
	    if(id > 5 || id <=0) throw new IllegalArgumentException("Invalid ID provided for PartSupplierType: " + id);
	    switch(id){
	    case 1: return UPS;
	    case 2: return FedEx;
	    case 3: return Other;
	    case 4: return DHL;
	    case 5: return USPS;
	    }
		return null;
	}

}
