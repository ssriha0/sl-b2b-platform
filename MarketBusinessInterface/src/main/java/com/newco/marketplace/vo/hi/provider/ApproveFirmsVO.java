package com.newco.marketplace.vo.hi.provider;

import java.util.List;

import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.vo.provider.AuditEmailVo;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.vo.provider.VendorNotesVO;

public class ApproveFirmsVO {
	//holding request values
	private Integer firmId;
	private String[] reasonCodes;
	private String firmStatus;
	private Integer wfStatus;
	private List<Integer> reasonCodeList;
	private List<ReasonCodeVO> reasonCodeVoList;
	private Integer firmStatusInt;
	private ResultsCode validationCode;
	private boolean emailIndicator;
	private boolean isEligibleForStatusChange;
	//holding status change update and mail sending
	private AuditVO auditVo;
	private VendorHdr vendorHdr;
	private VendorNotesVO vendorNotesVO;
	private AuditEmailVo auditEmailVo;
    private String hiFirmType = ProviderConstants.VENDOR_TYPE_HI;
	public AuditVO getAuditVo() {
		return auditVo;
	}

	public void setAuditVo(AuditVO auditVo) {
		this.auditVo = auditVo;
	}

	public VendorHdr getVendorHdr() {
		return vendorHdr;
	}

	public void setVendorHdr(VendorHdr vendorHdr) {
		this.vendorHdr = vendorHdr;
	}

	public VendorNotesVO getVendorNotesVO() {
		return vendorNotesVO;
	}

	public void setVendorNotesVO(VendorNotesVO vendorNotesVO) {
		this.vendorNotesVO = vendorNotesVO;
	}

	public AuditEmailVo getAuditEmailVo() {
		return auditEmailVo;
	}

	public void setAuditEmailVo(AuditEmailVo auditEmailVo) {
		this.auditEmailVo = auditEmailVo;
	}

	public Integer getFirmId() {
		return firmId;
	}

	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}

	public String getFirmStatus() {
		return firmStatus;
	}

	public void setFirmStatus(String firmStatus) {
		this.firmStatus = firmStatus;
	}

	public Integer getFirmStatusInt() {
		return firmStatusInt;
	}

	public void setFirmStatusInt(Integer firmStatusInt) {
		this.firmStatusInt = firmStatusInt;
	}

	public ResultsCode getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(ResultsCode validationCode) {
		this.validationCode = validationCode;
	}

	public boolean isEligibleForStatusChange() {
		return isEligibleForStatusChange;
	}

	public void setEligibleForStatusChange(boolean isEligibleForStatusChange) {
		this.isEligibleForStatusChange = isEligibleForStatusChange;
	}

	public Integer getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(Integer wfStatus) {
		this.wfStatus = wfStatus;
	}

	public List<Integer> getReasonCodeList() {
		return reasonCodeList;
	}

	public void setReasonCodeList(List<Integer> reasonCodeList) {
		this.reasonCodeList = reasonCodeList;
	}

	public String[] getReasonCodes() {
		return reasonCodes;
	}

	public void setReasonCodes(String[] reasonCodes) {
		this.reasonCodes = reasonCodes;
	}

	public List<ReasonCodeVO> getReasonCodeVoList() {
		return reasonCodeVoList;
	}

	public void setReasonCodeVoList(List<ReasonCodeVO> reasonCodeVoList) {
		this.reasonCodeVoList = reasonCodeVoList;
	}

	public boolean isEmailIndicator() {
		return emailIndicator;
	}

	public void setEmailIndicator(boolean emailIndicator) {
		this.emailIndicator = emailIndicator;
	}

}
