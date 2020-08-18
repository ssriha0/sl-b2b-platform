package com.servicelive.domain.d2cproviderportal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.servicelive.domain.provider.ProviderFirm;

@Entity
@Table ( name = "vendor_service_offering")
public class VendorServiceOffering {
	public VendorServiceOffering() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	//@OneToMany(mappedBy="vendorServiceOffering",targetEntity=VendorServiceOfferPrice.class, fetch=FetchType.LAZY)
	//private List<VendorServiceOfferPrice> vendorServiceOfferPrice;
	
	@ManyToOne
	@JoinColumn(name="vendor_id")
	private ProviderFirm providerFirm;
	
	@ManyToOne
	@JoinColumn(name="sku_id")
	private BuyerSku buyerSku;
	
	public ProviderFirm getProviderFirm() {
		return providerFirm;
	}
	public void setProviderFirm(ProviderFirm providerFirm) {
		this.providerFirm = providerFirm;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	//public List<VendorServiceOfferPrice> getVendorServiceOfferPrice() {
	//	return vendorServiceOfferPrice;
	//}
	//public void setVendorServiceOfferPrice(
	//		List<VendorServiceOfferPrice> vendorServiceOfferPrice) {
	//	this.vendorServiceOfferPrice = vendorServiceOfferPrice;
	//}
	public BuyerSku getBuyerSku() {
		return buyerSku;
	}
	public void setBuyerSku(BuyerSku buyerSku) {
		this.buyerSku = buyerSku;
	}
	

}
