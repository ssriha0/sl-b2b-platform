package com.newco.marketplace.dto.vo.account;

import com.newco.marketplace.dto.vo.provider.VendorHdrVO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.BuyerResource;
import com.newco.marketplace.webservices.base.CommonVO;

public class UserType extends CommonVO {
	
	private static final long serialVersionUID = -3889067456836411560L;
	private String roleId;
	protected Integer companyId;
	protected Integer resourceId;
	private Buyer buyer;
	private VendorHdrVO vendor;
	private VendorResource vendorResource;
	private BuyerResource buyerResource;
	
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Buyer getBuyer() {
		return buyer;
	}
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
		if(buyer!=null)
		this.companyId=buyer.getBuyerId();
	}
	public VendorResource getVendorResource() {
		return vendorResource;
	}
	public void setVendorResource(VendorResource vendorResource) {
		this.vendorResource = vendorResource;
		if(vendorResource!=null){
			this.companyId=this.vendorResource.getVendorId();
			this.resourceId=this.vendorResource.getResourceId();			
		}
	}
	public VendorHdrVO getVendor() {
		return vendor;
	}
	public void setVendor(VendorHdrVO vendor) {
		this.vendor = vendor;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public BuyerResource getBuyerResource() {
		return buyerResource;
	}
	public void setBuyerResource(BuyerResource buyerResource) {
		this.buyerResource = buyerResource;
		if(buyerResource!=null){
			this.companyId=this.buyerResource.getBuyerId();
			this.resourceId=this.buyerResource.getResourceId();			
		}
	}
}
