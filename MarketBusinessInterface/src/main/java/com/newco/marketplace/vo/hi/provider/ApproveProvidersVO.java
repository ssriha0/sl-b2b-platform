package com.newco.marketplace.vo.hi.provider;

import java.util.Date;
import java.util.List;

import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.vo.provider.AuditEmailVo;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.vo.provider.VendorNotesVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ApproveProvidersVO {
	
	private Integer providerId;
	private String[] reasonCodes;
	private String status;
	private Integer wfStatus;
	private List<Integer> reasonCodeList;
	private List<ReasonCodeVO> reasonCodeVoList;
	private Integer providerStatusInt;
	private ResultsCode validationCode;
	private boolean emailIndicator;
	private boolean isEligibleForStatusChange;
	private boolean needDBUpdatedForBgInfo = false;
	private String backgroundCheckStatus;
	private String currentbackgroundCheckStatus;
	private Date verificationDate;
	private Date reverificationDate;
	private Date requestDate; 
	private AuditVO auditVo;
	private VendorHdr vendorHdr;
	private VendorNotesVO vendorNotesVO;
	private AuditEmailVo auditEmailVo;
    private String hiFirmType = ProviderConstants.VENDOR_TYPE_HI;
    private Integer bgCheckId;
    private Integer dbBackgroundCheckId;
    private Integer firmId;
    
	public Integer getProviderId() {
		return providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	public String[] getReasonCodes() {
		return reasonCodes;
	}
	public void setReasonCodes(String[] reasonCodes) {
		this.reasonCodes = reasonCodes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public List<ReasonCodeVO> getReasonCodeVoList() {
		return reasonCodeVoList;
	}
	public void setReasonCodeVoList(List<ReasonCodeVO> reasonCodeVoList) {
		this.reasonCodeVoList = reasonCodeVoList;
	}
	public Integer getProviderStatusInt() {
		return providerStatusInt;
	}
	public void setProviderStatusInt(Integer providerStatusInt) {
		this.providerStatusInt = providerStatusInt;
	}
	public ResultsCode getValidationCode() {
		return validationCode;
	}
	public void setValidationCode(ResultsCode validationCode) {
		this.validationCode = validationCode;
	}
	public boolean isEmailIndicator() {
		return emailIndicator;
	}
	public void setEmailIndicator(boolean emailIndicator) {
		this.emailIndicator = emailIndicator;
	}
	public boolean isEligibleForStatusChange() {
		return isEligibleForStatusChange;
	}
	public void setEligibleForStatusChange(boolean isEligibleForStatusChange) {
		this.isEligibleForStatusChange = isEligibleForStatusChange;
	}
	public String getBackgroundCheckStatus() {
		return backgroundCheckStatus;
	}
	public void setBackgroundCheckStatus(String backgroundCheckStatus) {
		this.backgroundCheckStatus = backgroundCheckStatus;
	}
	public Date getVerificationDate() {
		return verificationDate;
	}
	public void setVerificationDate(Date verificationDate) {
		this.verificationDate = verificationDate;
	}
	public Date getReverificationDate() {
		return reverificationDate;
	}
	public void setReverificationDate(Date reverificationDate) {
		this.reverificationDate = reverificationDate;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
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
	public String getHiFirmType() {
		return hiFirmType;
	}
	public void setHiFirmType(String hiFirmType) {
		this.hiFirmType = hiFirmType;
	}
	public Integer getBgCheckId() {
		return bgCheckId;
	}
	public void setBgCheckId(Integer bgCheckId) {
		this.bgCheckId = bgCheckId;
	}
	public Integer getDbBackgroundCheckId() {
		return dbBackgroundCheckId;
	}
	public void setDbBackgroundCheckId(Integer dbBackgroundCheckId) {
		this.dbBackgroundCheckId = dbBackgroundCheckId;
	}
	public boolean isNeedDBUpdatedForBgInfo() {
		return needDBUpdatedForBgInfo;
	}
	public void setNeedDBUpdatedForBgInfo(boolean needDBUpdatedForBgInfo) {
		this.needDBUpdatedForBgInfo = needDBUpdatedForBgInfo;
	}
	public Integer getFirmId() {
		return firmId;
	}
	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}
	public String getCurrentbackgroundCheckStatus() {
		return currentbackgroundCheckStatus;
	}
	public void setCurrentbackgroundCheckStatus(String currentbackgroundCheckStatus) {
		this.currentbackgroundCheckStatus = currentbackgroundCheckStatus;
	}
    
	
}
