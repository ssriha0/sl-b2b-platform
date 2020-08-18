package com.newco.marketplace.api.mobile.beans.addsoproviderpart;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("suppliers")
public class Suppliers {
	
	@XStreamImplicit(itemFieldName="supplier")
	private List<Supplier> supplierList;

	public List<Supplier> getSupplierList() {
		return supplierList;
	}

	public void setSupplierList(List<Supplier> supplierList) {
		this.supplierList = supplierList;
	}
}
