package com.newco.marketplace.dto.vo.provider;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.leadsmanagement.FirmServiceVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ReviewVO;
import com.newco.marketplace.vo.provider.LastClosedOrderVO;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.vo.provider.WarrantyVO;

/**
 * This class would act as the VO class for getFirmDetails Service
 * @author neenu_manoharan
 *
 */
public class FirmDetailsResponseVO {
	
	private Map<String,BasicFirmDetailsVO> basicDetailsVOs;
	private Map<String,List<LicensesAndCertVO>>insuranceDetailsVOs;
	private Map<String,List<LicensesAndCertVO>> credentialDetailsVOs;
	private Map<String,LastClosedOrderVO> lastClosedOrderVOs ;
	private Map<String,WarrantyVO> warrantPolicyVOs;
	private Map<String,List<FirmServiceVO>> firmServiceVOs;
	private Map<String,List<ReviewVO>> reviewVOs;
	
	
	public Map<String, BasicFirmDetailsVO> getBasicDetailsVOs() {
		return basicDetailsVOs;
	}

	public void setBasicDetailsVOs(Map<String, BasicFirmDetailsVO> basicDetailsVOs) {
		this.basicDetailsVOs = basicDetailsVOs;
	}

	public Map<String, List<LicensesAndCertVO>> getInsuranceDetailsVOs() {
		return insuranceDetailsVOs;
	}

	public void setInsuranceDetailsVOs(
			Map<String, List<LicensesAndCertVO>> insuranceDetailsVOs) {
		this.insuranceDetailsVOs = insuranceDetailsVOs;
	}

	public Map<String, List<LicensesAndCertVO>> getCredentialDetailsVOs() {
		return credentialDetailsVOs;
	}

	public void setCredentialDetailsVOs(
			Map<String, List<LicensesAndCertVO>> credentialDetailsVOs) {
		this.credentialDetailsVOs = credentialDetailsVOs;
	}

	public Map<String, LastClosedOrderVO> getLastClosedOrderVOs() {
		return lastClosedOrderVOs;
	}

	public void setLastClosedOrderVOs(
			Map<String, LastClosedOrderVO> lastClosedOrderVOs) {
		this.lastClosedOrderVOs = lastClosedOrderVOs;
	}

	public Map<String, WarrantyVO> getWarrantPolicyVOs() {
		return warrantPolicyVOs;
	}

	public void setWarrantPolicyVOs(Map<String, WarrantyVO> warrantPolicyVOs) {
		this.warrantPolicyVOs = warrantPolicyVOs;
	}

	public Map<String, List<FirmServiceVO>> getFirmServiceVOs() {
		return firmServiceVOs;
	}

	public void setFirmServiceVOs(Map<String, List<FirmServiceVO>> firmServiceVOs) {
		this.firmServiceVOs = firmServiceVOs;
	}

	public Map<String, List<ReviewVO>> getReviewVOs() {
		return reviewVOs;
	}

	public void setReviewVOs(Map<String, List<ReviewVO>> reviewVOs) {
		this.reviewVOs = reviewVOs;
	}
	
}
