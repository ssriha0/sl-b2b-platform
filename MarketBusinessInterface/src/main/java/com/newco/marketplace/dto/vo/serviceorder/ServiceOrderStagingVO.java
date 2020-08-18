package com.newco.marketplace.dto.vo.serviceorder;

import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

public class ServiceOrderStagingVO extends SerializableBaseVO{
	
	private static final long serialVersionUID = 2061356992515670999L;
	
	private String soId;
	private AdditionalPaymentVO soAdditionalPayment;
	private List<ServiceOrderAddonVO> soAddonSkusList;
	private ServiceOrder serviceOrder;
	private String serialNumber = "333444";
	private String modelNumber;
	private Double totalAddOnServiceFee;
	
	public Double getTotalAddOnServiceFee() {
		return totalAddOnServiceFee;
	}

	public void setTotalAddOnServiceFee(Double totalAddOnServiceFee) {
		this.totalAddOnServiceFee = totalAddOnServiceFee;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public AdditionalPaymentVO getSoAdditionalPayment() {
		return soAdditionalPayment;
	}

	public void setSoAdditionalPayment(AdditionalPaymentVO soAdditionalPayment) {
		this.soAdditionalPayment = soAdditionalPayment;
	}

	public List<ServiceOrderAddonVO> getSoAddonSkusList() {
		return soAddonSkusList;
	}

	public void setSoAddonSkusList(List<ServiceOrderAddonVO> soAddonSkusList) {
		this.soAddonSkusList = soAddonSkusList;
	}

	public ServiceOrder getServiceOrder() {
		return serviceOrder;
	}

	public void setServiceOrder(ServiceOrder serviceOrder) {
		this.serviceOrder = serviceOrder;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

}
