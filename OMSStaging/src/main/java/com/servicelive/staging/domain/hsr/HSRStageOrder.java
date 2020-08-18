/**
 * 
 */
package com.servicelive.staging.domain.hsr;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.servicelive.staging.domain.LoggableBaseDomain;

/**
 * @author hoza
 *
 */
@Entity
@Table ( name = "hsr_order",  uniqueConstraints = { @UniqueConstraint(columnNames = {"order_no", "unit_number" }) })
public class HSRStageOrder extends LoggableBaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1100998979743729824L;

	@Id @GeneratedValue (strategy=IDENTITY)
	@Column (name = "hsr_order_id", unique = true, nullable = false, insertable = true, updatable = true)
	private Integer hsrOrderId;

	@Column (name = "order_no", unique = true, nullable = false, insertable = true, updatable = true, length = 8)
	private String orderNumber;

	@Column (name = "unit_number", unique = true, nullable = false, insertable = true, updatable = true, length = 8)
	private String unitNumber;
	
	@Column (name = "so_id", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	private String serviceOrderId;

	@Column (name = "repairtag_barcode", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private String repairTagBarCode;
	
	@Column (name = "payment_method_ind", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	private String paymentMethodInd;

	@Column (name = "customer_charge_acct_no", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	private String customerChargeAcctNumber;
	
	@Column (name = "customer_charge_acct_exp", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private String customerChargeAcctExpDate;

	@Column (name = "service_requested", unique = false, nullable = true, insertable = true, updatable = true, length = 70)
	private String serviceRequested;
	
	@Column (name = "order_status_code", unique = false, nullable = false, insertable = true, updatable = true, length = 2)
	private String orderStatusCode;
	
	
	@Column (name = "special_instruction1", unique = false, nullable = true, insertable = true, updatable = true, length = 60)
	private String specialInstruction1;

	@Column (name = "special_instruction2", unique = false, nullable = true, insertable = true, updatable = true, length = 60)
	private String specialInstruction2;
	
	@Column (name = "permanent_instruction", unique = false, nullable = true, insertable = true, updatable = true, length = 60)
	private String permanentInstruction;
	
	@Column (name = "priority_ind", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	private String priorityInd;
	
	@Column (name = "last_modified_emp_id", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	private String lastModifiedEmpId;
	
	@Column (name = "coverage_type_labor", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	private String coverageLaborType;
	
	@Column (name = "coverage_type_parts", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private String coveragePartsType;
	
	@Column (name = "exception_part_warr_exp_date", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private String exceptionPartWarrExpDate;
	
	@Column (name = "promotion_ind", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	private String promotionInd;
	
	@Column (name = "pat_warr_exp_date", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private String partWarrExpDate;
	
	@Column (name = "labor_warr_exp_date", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private String laborWarrExpDate;
	
	@Column (name = "labor_warrenty", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	private String laborWarrenty;
	
	@Column (name = "except_part_warr_exp_date", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private String exceptionPartExpirationDate;
	
	@Column (name = "repair_instruction", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	private String repairInstruction;
	
	@Column (name = "order_creator_emp_id", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	private String orderCreatorEmpId;
	
	
	@Column (name = "solicit_pa_ind", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	private String solicitProtectionAgreementInd;
	
	
	@Column (name = "order_creation_unit_no", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	private String orderCreationUnitNumber;
	
	@Column (name = "proc_id", unique = false, nullable = true, insertable = true, updatable = true, length = 6)
	private String procId;
	
	@Column (name = "contract_number", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	private String contractNumber;
	
	
	@Column (name = "contract_exp_date", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private String contractExpirationdate;
	
	@Column (name = "auth_number", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	private String authNumber;
	
	
	@OneToMany( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
	@JoinColumn(name = "hsr_order_id",nullable = false, insertable = true, updatable = true)
	private List<HSRStageOrderTransaction> transactions = new ArrayList<HSRStageOrderTransaction> (0);
	
	/* U can potentially add mutliple Customers  Db is designed such a way but we are restrcting that by code */
	@OneToOne (mappedBy="hsrOrderId",cascade=CascadeType.ALL ) 
	private HSRStageOrderCustomer customer;

	/* U can potentially add mutliple merchandise,  Db is designed such a way but we are restrcting that by code */
	@OneToOne(mappedBy="hsrOrderId",cascade=CascadeType.ALL ) 
	private HSRStageOrderMerchandise merchandise;
	
	@OneToOne(mappedBy="hsrOrderId",cascade=CascadeType.ALL ) 
	private HSRStageOrderClientOutflowUpdate clientOutflowUpdate;
	
	@OneToOne (mappedBy="hsrOrderId",cascade=CascadeType.ALL ) 
	private HSRStageOrderSchedule schedule; 
	
	
	@OneToOne (mappedBy="hsrOrderId",cascade=CascadeType.ALL ) 
	private HSRStageOrderProtectionAgreement protectionAgrement; 

	@OneToOne (mappedBy="hsrOrderId",cascade=CascadeType.ALL ) 
	private HSRStageOrderRepairLocation repairLocation; 
	
	@OneToMany( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
	@JoinColumn(name = "hsr_order_id",nullable = false, insertable = true, updatable = true)
	private List<HSRStageOrderUpdate> updates = new ArrayList<HSRStageOrderUpdate> (0);
	
	/**
	 * @return the hsrOrderId
	 */
	public Integer getHsrOrderId() {
		return hsrOrderId;
	}

	/**
	 * @param hsrOrderId the hsrOrderId to set
	 */
	public void setHsrOrderId(Integer hsrOrderId) {
		this.hsrOrderId = hsrOrderId;
	}

	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * @return the unitNumber
	 */
	public String getUnitNumber() {
		return unitNumber;
	}

	/**
	 * @param unitNumber the unitNumber to set
	 */
	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	/**
	 * @return the serviceOrderId
	 */
	public String getServiceOrderId() {
		return serviceOrderId;
	}

	/**
	 * @param serviceOrderId the serviceOrderId to set
	 */
	public void setServiceOrderId(String serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}

	/**
	 * @return the repairTagBarCode
	 */
	public String getRepairTagBarCode() {
		return repairTagBarCode;
	}

	/**
	 * @param repairTagBarCode the repairTagBarCode to set
	 */
	public void setRepairTagBarCode(String repairTagBarCode) {
		this.repairTagBarCode = repairTagBarCode;
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
	 * @return the customerChargeAcctExpDate
	 */
	public String getCustomerChargeAcctExpDate() {
		return customerChargeAcctExpDate;
	}

	/**
	 * @param customerChargeAcctExpDate the customerChargeAcctExpDate to set
	 */
	public void setCustomerChargeAcctExpDate(String customerChargeAcctExpDate) {
		this.customerChargeAcctExpDate = customerChargeAcctExpDate;
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
	 * @return the orderStatusCode
	 */
	public String getOrderStatusCode() {
		return orderStatusCode;
	}

	/**
	 * @param orderStatusCode the orderStatusCode to set
	 */
	public void setOrderStatusCode(String orderStatusCode) {
		this.orderStatusCode = orderStatusCode;
	}

	/**
	 * @return the specialInstruction1
	 */
	public String getSpecialInstruction1() {
		return specialInstruction1;
	}

	/**
	 * @param specialInstruction1 the specialInstruction1 to set
	 */
	public void setSpecialInstruction1(String specialInstruction1) {
		this.specialInstruction1 = specialInstruction1;
	}

	/**
	 * @return the specialInstruction2
	 */
	public String getSpecialInstruction2() {
		return specialInstruction2;
	}

	/**
	 * @param specialInstruction2 the specialInstruction2 to set
	 */
	public void setSpecialInstruction2(String specialInstruction2) {
		this.specialInstruction2 = specialInstruction2;
	}

	/**
	 * @return the permanentInstruction
	 */
	public String getPermanentInstruction() {
		return permanentInstruction;
	}

	/**
	 * @param permanentInstruction the permanentInstruction to set
	 */
	public void setPermanentInstruction(String permanentInstruction) {
		this.permanentInstruction = permanentInstruction;
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
	 * @return the lastModifiedEmpId
	 */
	public String getLastModifiedEmpId() {
		return lastModifiedEmpId;
	}

	/**
	 * @param lastModifiedEmpId the lastModifiedEmpId to set
	 */
	public void setLastModifiedEmpId(String lastModifiedEmpId) {
		this.lastModifiedEmpId = lastModifiedEmpId;
	}

	/**
	 * @return the coverageLaborType
	 */
	public String getCoverageLaborType() {
		return coverageLaborType;
	}

	/**
	 * @param coverageLaborType the coverageLaborType to set
	 */
	public void setCoverageLaborType(String coverageLaborType) {
		this.coverageLaborType = coverageLaborType;
	}

	/**
	 * @return the coveragePartsType
	 */
	public String getCoveragePartsType() {
		return coveragePartsType;
	}

	/**
	 * @param coveragePartsType the coveragePartsType to set
	 */
	public void setCoveragePartsType(String coveragePartsType) {
		this.coveragePartsType = coveragePartsType;
	}

	/**
	 * @return the exceptionPartWarrExpDate
	 */
	public String getExceptionPartWarrExpDate() {
		return exceptionPartWarrExpDate;
	}

	/**
	 * @param exceptionPartWarrExpDate the exceptionPartWarrExpDate to set
	 */
	public void setExceptionPartWarrExpDate(String exceptionPartWarrExpDate) {
		this.exceptionPartWarrExpDate = exceptionPartWarrExpDate;
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
	 * @return the laborWarrenty
	 */
	public String getLaborWarrenty() {
		return laborWarrenty;
	}

	/**
	 * @param laborWarrenty the laborWarrenty to set
	 */
	public void setLaborWarrenty(String laborWarrenty) {
		this.laborWarrenty = laborWarrenty;
	}

	/**
	 * @return the exceptionPartExpirationDate
	 */
	public String getExceptionPartExpirationDate() {
		return exceptionPartExpirationDate;
	}

	/**
	 * @param exceptionPartExpirationDate the exceptionPartExpirationDate to set
	 */
	public void setExceptionPartExpirationDate(String exceptionPartExpirationDate) {
		this.exceptionPartExpirationDate = exceptionPartExpirationDate;
	}

	/**
	 * @return the repairInstruction
	 */
	public String getRepairInstruction() {
		return repairInstruction;
	}

	/**
	 * @param repairInstruction the repairInstruction to set
	 */
	public void setRepairInstruction(String repairInstruction) {
		this.repairInstruction = repairInstruction;
	}

	/**
	 * @return the orderCreatorEmpId
	 */
	public String getOrderCreatorEmpId() {
		return orderCreatorEmpId;
	}

	/**
	 * @param orderCreatorEmpId the orderCreatorEmpId to set
	 */
	public void setOrderCreatorEmpId(String orderCreatorEmpId) {
		this.orderCreatorEmpId = orderCreatorEmpId;
	}

	/**
	 * @return the solicitProtectionAgreementInd
	 */
	public String getSolicitProtectionAgreementInd() {
		return solicitProtectionAgreementInd;
	}

	/**
	 * @param solicitProtectionAgreementInd the solicitProtectionAgreementInd to set
	 */
	public void setSolicitProtectionAgreementInd(
			String solicitProtectionAgreementInd) {
		this.solicitProtectionAgreementInd = solicitProtectionAgreementInd;
	}

	/**
	 * @return the orderCreationUnitNumber
	 */
	public String getOrderCreationUnitNumber() {
		return orderCreationUnitNumber;
	}

	/**
	 * @param orderCreationUnitNumber the orderCreationUnitNumber to set
	 */
	public void setOrderCreationUnitNumber(String orderCreationUnitNumber) {
		this.orderCreationUnitNumber = orderCreationUnitNumber;
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
	 * @return the contractExpirationdate
	 */
	public String getContractExpirationdate() {
		return contractExpirationdate;
	}

	/**
	 * @param contractExpirationdate the contractExpirationdate to set
	 */
	public void setContractExpirationdate(String contractExpirationdate) {
		this.contractExpirationdate = contractExpirationdate;
	}

	/**
	 * @return the authNumber
	 */
	public String getAuthNumber() {
		return authNumber;
	}

	/**
	 * @param authNumber the authNumber to set
	 */
	public void setAuthNumber(String authNumber) {
		this.authNumber = authNumber;
	}

	/**
	 * @return the transaction
	 */
	public List<HSRStageOrderTransaction> getTransactions() {
		return transactions;
	}

	/**
	 * @param transaction the transaction to set
	 */
	public void setTransactions(List<HSRStageOrderTransaction> transaction) {
		this.transactions = transaction;
	}

	public HSRStageOrderCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(HSRStageOrderCustomer customer) {
		this.customer = customer;
	}

	/**
	 * @return the merchandise
	 */
	public HSRStageOrderMerchandise getMerchandise() {
		return merchandise;
	}

	/**
	 * @param merchandise the merchandise to set
	 */
	public void setMerchandise(HSRStageOrderMerchandise merchandise) {
		this.merchandise = merchandise;
	}

	/**
	 * @return the clientOutflowUpdate
	 */
	public HSRStageOrderClientOutflowUpdate getClientOutflowUpdate() {
		return clientOutflowUpdate;
	}

	/**
	 * @param clientOutflowUpdate the clientOutflowUpdate to set
	 */
	public void setClientOutflowUpdate(
			HSRStageOrderClientOutflowUpdate clientOutflowUpdate) {
		this.clientOutflowUpdate = clientOutflowUpdate;
	}

	/**
	 * @return the schedule
	 */
	public HSRStageOrderSchedule getSchedule() {
		return schedule;
	}

	/**
	 * @param schedule the schedule to set
	 */
	public void setSchedule(HSRStageOrderSchedule schedule) {
		this.schedule = schedule;
	}

	/**
	 * @return the protectionAgrement
	 */
	public HSRStageOrderProtectionAgreement getProtectionAgrement() {
		return protectionAgrement;
	}

	/**
	 * @param protectionAgrement the protectionAgrement to set
	 */
	public void setProtectionAgrement(
			HSRStageOrderProtectionAgreement protectionAgrement) {
		this.protectionAgrement = protectionAgrement;
	}

	/**
	 * @return the repairLocation
	 */
	public HSRStageOrderRepairLocation getRepairLocation() {
		return repairLocation;
	}

	/**
	 * @param repairLocation the repairLocation to set
	 */
	public void setRepairLocation(HSRStageOrderRepairLocation repairLocation) {
		this.repairLocation = repairLocation;
	}

	/**
	 * @return the updates
	 */
	public List<HSRStageOrderUpdate> getUpdates() {
		return updates;
	}

	/**
	 * @param updates the updates to set
	 */
	public void setUpdates(List<HSRStageOrderUpdate> updates) {
		this.updates = updates;
	}

	/*public List<HSRStageOrderCustomer> getCustomer() {
		return customer;
	}

	public void setCustomer(List<HSRStageOrderCustomer> customer) {
		this.customer = customer;
	}*/

	

	
	
	
}
