package com.servicelive.orderfulfillment.domain.type;

public enum PartSupplierType {
	Buyer(1),
	Provider(2),
	PartsNotRequired(3);
	
	
	Integer id;
	
	private PartSupplierType(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public static PartSupplierType fromId(int id){
	    if(id > 3 || id <=0) throw new IllegalArgumentException("Invalid ID provided for PartSupplierType: " + id);
	    switch(id){
	    case 1: return Buyer;
	    case 2: return Provider;
	    case 3: return PartsNotRequired;
	    }
		return null;
	}

}
