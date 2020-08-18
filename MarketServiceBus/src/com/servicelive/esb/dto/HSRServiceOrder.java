package com.servicelive.esb.dto;

import java.io.Serializable;

/**
 * @author sldev
 *
 */
public class HSRServiceOrder implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String serviceUnitNumber;
	
	private String serviceOrderNumber;
	
	private String repairTagBarCodeNumber;
	
	private HSRSoDatesAndTimes datesAndTimes;
	
	private HSRSoCustomer customer;
	
	private HSRRepairLocation repairLocation;
	
	private String paymentMethodInd;
	
	private String customerChargeAcctNumber;
	
	private String customerChargeAcctExp;
	
	private String serviceRequested;
	
	private String serviceOrderStatusCode;	
	
	private String specialInstructions1;
	
	private String specialInstructions2;
	
	private String permanentInstructions;
	
	private String priorityInd;
	
	private String lastModifiedByEmpId;
	
	private String coverageTypeLabor;
	
	private String coverageTypeParts;
	
	private String excepPartWarrExpDate;
	
	private HSRProtectionAgreement protectionAgreement;
	
	private HSRMerchandise merchandise;		
	
	private String promotionInd;
	
	private String partWarrExpDate;
	
	private String laborWarrExpDate;
	
	private String excepPartWarrExpDate2;
	
	private String laborWarranty;
	
	private String repairInstructions;
	
	private String soCreatorEmpNumber;
	
	private String solicitPAInd;
	
	private String soCreationUnitNumber;
	
	private String procId;
	
	private String contractNumber;
	
	private String contractExpDate;
	
	private String authorizationNumber;
	
	/* fields from update file START*/
	private String employeeIDNumber;
	
	private String filler;
	
	private String modifyingUnitId;
	/* fields from update file END*/
	
	private String inputFragment;
	
	private Boolean isCameInUpdateFile = Boolean.FALSE;
	
	private Boolean isUpdateCamePriorToNew = Boolean.FALSE;


	/**
	 * @return the serviceUnitNumber
	 */
	public String getServiceUnitNumber() {
		return serviceUnitNumber;
	}

	/**
	 * @param serviceUnitNumber the serviceUnitNumber to set
	 */
	public void setServiceUnitNumber(String serviceUnitNumber) {
		this.serviceUnitNumber = serviceUnitNumber;
	}

	/**
	 * @return the serviceOrderNumber
	 */
	public String getServiceOrderNumber() {
		return serviceOrderNumber;
	}

	/**
	 * @param serviceOrderNumber the serviceOrderNumber to set
	 */
	public void setServiceOrderNumber(String serviceOrderNumber) {
		this.serviceOrderNumber = serviceOrderNumber;
	}

	/**
	 * @return the repairTagBarCodeNumber
	 */
	public String getRepairTagBarCodeNumber() {
		return repairTagBarCodeNumber;
	}

	/**
	 * @param repairTagBarCodeNumber the repairTagBarCodeNumber to set
	 */
	public void setRepairTagBarCodeNumber(String repairTagBarCodeNumber) {
		this.repairTagBarCodeNumber = repairTagBarCodeNumber;
	}

	/**
	 * @return the datesAndTimes
	 */
	public HSRSoDatesAndTimes getDatesAndTimes() {
		return datesAndTimes;
	}

	/**
	 * @param datesAndTimes the datesAndTimes to set
	 */
	public void setDatesAndTimes(HSRSoDatesAndTimes datesAndTimes) {
		this.datesAndTimes = datesAndTimes;
	}

	/**
	 * @return the customer
	 */
	public HSRSoCustomer getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(HSRSoCustomer customer) {
		this.customer = customer;
	}

	/**
	 * @return the repairLocation
	 */
	public HSRRepairLocation getRepairLocation() {
		return repairLocation;
	}

	/**
	 * @param repairLocation the repairLocation to set
	 */
	public void setRepairLocation(HSRRepairLocation repairLocation) {
		this.repairLocation = repairLocation;
	}

	/**
	 * @return the paymentMethodInd
	 */
	public String getPaymentMethodInd() {
		return paymentMethodInd;
	}

	/**
	 * @param paymentMethodInd the paymentMethodInd to set
	 */
	public void setPaymentMethodInd(String paymentMethodInd) {
		this.paymentMethodInd = paymentMethodInd;
	}

	/**
	 * @return the customerChargeAcctNumber
	 */
	public String getCustomerChargeAcctNumber() {
		return customerChargeAcctNumber;
	}

	/**
	 * @param customerChargeAcctNumber the customerChargeAcctNumber to set
	 */
	public void setCustomerChargeAcctNumber(String customerChargeAcctNumber) {
		this.customerChargeAcctNumber = customerChargeAcctNumber;
	}

	/**
	 * @return the customerChargeAcctExp
	 */
	public String getCustomerChargeAcctExp() {
		return customerChargeAcctExp;
	}

	/**
	 * @param customerChargeAcctExp the customerChargeAcctExp to set
	 */
	public void setCustomerChargeAcctExp(String customerChargeAcctExp) {
		this.customerChargeAcctExp = customerChargeAcctExp;
	}

	/**
	 * @return the serviceRequested
	 */
	public String getServiceRequested() {
		return serviceRequested;
	}

	/**
	 * @param serviceRequested the serviceRequested to set
	 */
	public void setServiceRequested(String serviceRequested) {
		this.serviceRequested = serviceRequested;
	}

	/**
	 * @return the serviceOrderStatusCode
	 */
	public String getServiceOrderStatusCode() {
		return serviceOrderStatusCode;
	}

	/**
	 * @param serviceOrderStatusCode the serviceOrderStatusCode to set
	 */
	public void setServiceOrderStatusCode(String serviceOrderStatusCode) {
		this.serviceOrderStatusCode = serviceOrderStatusCode;
	}

	/**
	 * @return the specialInstructions1
	 */
	public String getSpecialInstructions1() {
		return specialInstructions1;
	}

	/**
	 * @param specialInstructions1 the specialInstructions1 to set
	 */
	public void setSpecialInstructions1(String specialInstructions1) {
		this.specialInstructions1 = specialInstructions1;
	}

	/**
	 * @return the permanentInstructions
	 */
	public String getPermanentInstructions() {
		return permanentInstructions;
	}

	/**
	 * @param permanentInstructions the permanentInstructions to set
	 */
	public void setPermanentInstructions(String permanentInstructions) {
		this.permanentInstructions = permanentInstructions;
	}

	/**
	 * @return the priorityInd
	 */
	public String getPriorityInd() {
		return priorityInd;
	}

	/**
	 * @param priorityInd the priorityInd to set
	 */
	public void setPriorityInd(String priorityInd) {
		this.priorityInd = priorityInd;
	}

	/**
	 * @return the lastModifiedByEmpId
	 */
	public String getLastModifiedByEmpId() {
		return lastModifiedByEmpId;
	}

	/**
	 * @param lastModifiedByEmpId the lastModifiedByEmpId to set
	 */
	public void setLastModifiedByEmpId(String lastModifiedByEmpId) {
		this.lastModifiedByEmpId = lastModifiedByEmpId;
	}

	/**
	 * @return the coverageTypeLabor
	 */
	public String getCoverageTypeLabor() {
		return coverageTypeLabor;
	}

	/**
	 * @param coverageTypeLabor the coverageTypeLabor to set
	 */
	public void setCoverageTypeLabor(String coverageTypeLabor) {
		this.coverageTypeLabor = coverageTypeLabor;
	}

	/**
	 * @return the coverageTypeParts
	 */
	public String getCoverageTypeParts() {
		return coverageTypeParts;
	}

	/**
	 * @param coverageTypeParts the coverageTypeParts to set
	 */
	public void setCoverageTypeParts(String coverageTypeParts) {
		this.coverageTypeParts = coverageTypeParts;
	}

	/**
	 * @return the excepPartWarrExpDate
	 */
	public String getExcepPartWarrExpDate() {
		return excepPartWarrExpDate;
	}

	/**
	 * @param excepPartWarrExpDate the excepPartWarrExpDate to set
	 */
	public void setExcepPartWarrExpDate(String excepPartWarrExpDate) {
		this.excepPartWarrExpDate = excepPartWarrExpDate;
	}

	/**
	 * @return the protectionAgreement
	 */
	public HSRProtectionAgreement getProtectionAgreement() {
		return protectionAgreement;
	}

	/**
	 * @param protectionAgreement the protectionAgreement to set
	 */
	public void setProtectionAgreement(HSRProtectionAgreement protectionAgreement) {
		this.protectionAgreement = protectionAgreement;
	}

	/**
	 * @return the merchandise
	 */
	public HSRMerchandise getMerchandise() {
		return merchandise;
	}

	/**
	 * @param merchandise the merchandise to set
	 */
	public void setMerchandise(HSRMerchandise merchandise) {
		this.merchandise = merchandise;
	}

	/**
	 * @return the promotionInd
	 */
	public String getPromotionInd() {
		return promotionInd;
	}

	/**
	 * @param promotionInd the promotionInd to set
	 */
	public void setPromotionInd(String promotionInd) {
		this.promotionInd = promotionInd;
	}

	/**
	 * @return the partWarrExpDate
	 */
	public String getPartWarrExpDate() {
		return partWarrExpDate;
	}

	/**
	 * @param partWarrExpDate the partWarrExpDate to set
	 */
	public void setPartWarrExpDate(String partWarrExpDate) {
		this.partWarrExpDate = partWarrExpDate;
	}

	/**
	 * @return the laborWarrExpDate
	 */
	public String getLaborWarrExpDate() {
		return laborWarrExpDate;
	}

	/**
	 * @param laborWarrExpDate the laborWarrExpDate to set
	 */
	public void setLaborWarrExpDate(String laborWarrExpDate) {
		this.laborWarrExpDate = laborWarrExpDate;
	}

	/**
	 * @return the excepPartWarrExpDate2
	 */
	public String getExcepPartWarrExpDate2() {
		return excepPartWarrExpDate2;
	}

	/**
	 * @param excepPartWarrExpDate2 the excepPartWarrExpDate2 to set
	 */
	public void setExcepPartWarrExpDate2(String excepPartWarrExpDate2) {
		this.excepPartWarrExpDate2 = excepPartWarrExpDate2;
	}

	/**
	 * @return the laborWarranty
	 */
	public String getLaborWarranty() {
		return laborWarranty;
	}

	/**
	 * @param laborWarranty the laborWarranty to set
	 */
	public void setLaborWarranty(String laborWarranty) {
		this.laborWarranty = laborWarranty;
	}

	/**
	 * @return the repairInstructions
	 */
	public String getRepairInstructions() {
		return repairInstructions;
	}

	/**
	 * @param repairInstructions the repairInstructions to set
	 */
	public void setRepairInstructions(String repairInstructions) {
		this.repairInstructions = repairInstructions;
	}

	/**
	 * @return the soCreatorEmpNumber
	 */
	public String getSoCreatorEmpNumber() {
		return soCreatorEmpNumber;
	}

	/**
	 * @param soCreatorEmpNumber the soCreatorEmpNumber to set
	 */
	public void setSoCreatorEmpNumber(String soCreatorEmpNumber) {
		this.soCreatorEmpNumber = soCreatorEmpNumber;
	}

	/**
	 * @return the solicitPAInd
	 */
	public String getSolicitPAInd() {
		return solicitPAInd;
	}

	/**
	 * @param solicitPAInd the solicitPAInd to set
	 */
	public void setSolicitPAInd(String solicitPAInd) {
		this.solicitPAInd = solicitPAInd;
	}

	/**
	 * @return the soCreationUnitNumber
	 */
	public String getSoCreationUnitNumber() {
		return soCreationUnitNumber;
	}

	/**
	 * @param soCreationUnitNumber the soCreationUnitNumber to set
	 */
	public void setSoCreationUnitNumber(String soCreationUnitNumber) {
		this.soCreationUnitNumber = soCreationUnitNumber;
	}

	/**
	 * @return the procId
	 */
	public String getProcId() {
		return procId;
	}

	/**
	 * @param procId the procId to set
	 */
	public void setProcId(String procId) {
		this.procId = procId;
	}

	/**
	 * @return the contractNumber
	 */
	public String getContractNumber() {
		return contractNumber;
	}

	/**
	 * @param contractNumber the contractNumber to set
	 */
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	/**
	 * @return the authorizationNumber
	 */
	public String getAuthorizationNumber() {
		return authorizationNumber;
	}

	/**
	 * @param authorizationNumber the authorizationNumber to set
	 */
	public void setAuthorizationNumber(String authorizationNumber) {
		this.authorizationNumber = authorizationNumber;
	}

	public String getSpecialInstructions2() {
		return specialInstructions2;
	}

	public void setSpecialInstructions2(String specialInstructions2) {
		this.specialInstructions2 = specialInstructions2;
	}

	public String getContractExpDate() {
		return contractExpDate;
	}

	public void setContractExpDate(String contractExpDate) {
		this.contractExpDate = contractExpDate;
	}

	public String getEmployeeIDNumber() {
		return employeeIDNumber;
	}

	public void setEmployeeIDNumber(String employeeIDNumber) {
		this.employeeIDNumber = employeeIDNumber;
	}

	public String getFiller() {
		return filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	public String getModifyingUnitId() {
		return modifyingUnitId;
	}

	public void setModifyingUnitId(String modifyingUnitId) {
		this.modifyingUnitId = modifyingUnitId;
	}

	/**
	 * @return the inputFragment
	 */
	public String getInputFragment() {
		return inputFragment;
	}

	/**
	 * @param inputFragment the inputFragment to set
	 */
	public void setInputFragment(String inputFragment) {
		this.inputFragment = inputFragment;
	}

	/**
	 * @return the isCameInUpdateFile
	 */
	public Boolean getIsCameInUpdateFile() {
		return isCameInUpdateFile;
	}

	/**
	 * @param isCameInUpdateFile the isCameInUpdateFile to set
	 */
	public void setIsCameInUpdateFile(Boolean isCameInUpdateFile) {
		this.isCameInUpdateFile = isCameInUpdateFile;
	}

	/**
	 * @return the isUpdateCamePriorToNew
	 */
	public Boolean getIsUpdateCamePriorToNew() {
		return isUpdateCamePriorToNew;
	}

	/**
	 * @param isUpdateCamePriorToNew the isUpdateCamePriorToNew to set
	 */
	public void setIsUpdateCamePriorToNew(Boolean isUpdateCamePriorToNew) {
		this.isUpdateCamePriorToNew = isUpdateCamePriorToNew;
	}

	
	
		
}
