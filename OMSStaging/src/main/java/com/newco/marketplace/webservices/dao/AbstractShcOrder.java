package com.newco.marketplace.webservices.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AbstractShcOrder entity provides the base persistence definition of the
 * ShcOrder entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractShcOrder implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 2894727129953034421L;
	private Integer shcOrderId;
	private String orderNo;
	private String unitNo;
	private String storeNo;
	private String soId;
	private Integer wfStateId;
	private Integer npsProcessId;
	private String npsStatus;
	private String omsProcessId;
	private String salesCheckNum;
	private String salesCheckDate;
	private String promisedDate;
	private Short processedInd;
	private Integer glProcessId;
	private String modifiedBy;
	private Date routedDate;
	private Date completedDate;
	private String resolutionDescr;
	private Set<ShcOrderSku> shcOrderSkus = new HashSet<ShcOrderSku>(0);
	private Set<ShcOrderTransaction> shcOrderTransactions = new HashSet<ShcOrderTransaction>(0);
	private Set<ShcMerchandise> shcMerchandises = new HashSet<ShcMerchandise>(0);
	private Set<ShcUpsellPayment> shcUpsellPayments = new HashSet<ShcUpsellPayment>(0);
	private Set<ShcOrderAddOn> shcOrderAddOns = new HashSet<ShcOrderAddOn>(0);
	private String shcUnitOrderNumber;

	// Constructors

	/** default constructor */
	public AbstractShcOrder() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractShcOrder(Integer shcOrderId, String orderNo, String unitNo,
			String storeNo) {
		this.shcOrderId = shcOrderId;
		this.orderNo = orderNo;
		this.unitNo = unitNo;
		this.storeNo = storeNo;
		this.shcUnitOrderNumber = unitNo + orderNo;
	}

	/** full constructor */
	public AbstractShcOrder(Integer shcOrderId, String orderNo, String unitNo,
			String storeNo, String soId,  Integer wfStateId, Integer npsProcessId, String omsProcessId, String salesCheckNum, String salesCheckDate, String promisedDate, Short processedInd,
			Integer glProcessId, Date createdDate, Date modifiedDate,
			String modifiedBy, Date completedDate, Date routedDate, String resolutionDescr,
			Set<ShcOrderSku> shcOrderSkus,
			Set<ShcOrderTransaction> shcOrderTransactions,
			Set<ShcMerchandise> shcMerchandises,Set<ShcUpsellPayment> shcUpsellPayments,Set<ShcOrderAddOn> shcOrderAddOns) {
		this.shcOrderId = shcOrderId;
		this.orderNo = orderNo;
		this.unitNo = unitNo;
		this.storeNo = storeNo;
		this.soId = soId;
		this.wfStateId = wfStateId;
		this.npsProcessId = npsProcessId;
		this.omsProcessId = omsProcessId;
		this.salesCheckNum = salesCheckNum;
		this.salesCheckDate = salesCheckDate;
		this.promisedDate = promisedDate;
		this.processedInd = processedInd;
		this.glProcessId = glProcessId;
		this.modifiedBy = modifiedBy;
		this.shcOrderSkus = shcOrderSkus;
		this.shcOrderTransactions = shcOrderTransactions;
		this.shcMerchandises = shcMerchandises;	
		this.shcUpsellPayments = shcUpsellPayments;
		this.completedDate = completedDate;
		this.routedDate = routedDate;
		this.resolutionDescr = resolutionDescr;
		this.shcUnitOrderNumber = unitNo + orderNo;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "shc_order_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getShcOrderId() {
		return this.shcOrderId;
	}

	public void setShcOrderId(Integer shcOrderId) {
		this.shcOrderId = shcOrderId;
	}

	@Column(name = "order_no", unique = false, nullable = false, insertable = true, updatable = true, length = 30)
	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
		if(unitNo != null)
			setShcUnitOrderNumber(unitNo + orderNo);
	}

	@Column(name = "unit_no", unique = false, nullable = false, insertable = true, updatable = true, length = 30)
	public String getUnitNo() {
		return this.unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
		if(orderNo != null)
			setShcUnitOrderNumber(unitNo + orderNo);
	}

	@Column(name = "store_no", unique = false, nullable = false, insertable = true, updatable = true, length = 30)
	public String getStoreNo() {
		return this.storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	@Column(name = "so_id", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public String getSoId() {
		return this.soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	@Column(name = "wf_state_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getWfStateId() {
		return this.wfStateId;
	}

	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}
	
	@Column(name = "oms_process_id", unique = false, nullable = true, insertable = true, updatable = true)
	public String getOmsProcessId() {
		return this.omsProcessId;
	}

	public void setOmsProcessId(String omsProcessId) {
		this.omsProcessId = omsProcessId;
	}

	@Column(name = "nps_process_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getNpsProcessId() {
		return this.npsProcessId;
	}

	public void setNpsProcessId(Integer npsProcessId) {
		this.npsProcessId = npsProcessId;
	}
	
	@Column(name = "sales_check_num", unique = false, nullable = true, insertable = true, updatable = true)
	public String getSalesCheckNum() {
		return salesCheckNum;
	}

	public void setSalesCheckNum(String salesCheckNum) {
		this.salesCheckNum = salesCheckNum;
	}

	@Column(name = "sales_check_date", unique = false, nullable = true, insertable = true, updatable = true)
	public String getSalesCheckDate() {
		return salesCheckDate;
	}

	public void setSalesCheckDate(String salesCheckDate) {
		this.salesCheckDate = salesCheckDate;
	}

	@Column(name = "promised_date", unique = false, nullable = true, insertable = true, updatable = true)
	public String getPromisedDate() {
		return promisedDate;
	}

	public void setPromisedDate(String promisedDate) {
		this.promisedDate = promisedDate;
	}
	
	@Column(name = "processed_ind", unique = false, nullable = true, insertable = true, updatable = true)
	public Short getProcessedInd() {
		return this.processedInd;
	}

	public void setProcessedInd(Short processedInd) {
		this.processedInd = processedInd;
	}

	@Column(name = "gl_process_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getGlProcessId() {
		return this.glProcessId;
	}

	public void setGlProcessId(Integer glProcessId) {
		this.glProcessId = glProcessId;
	}

	@Column(name = "modified_by", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "shcOrder")
	public Set<ShcOrderSku> getShcOrderSkus() {
		return this.shcOrderSkus;
	}

	public void setShcOrderSkus(Set<ShcOrderSku> shcOrderSkus) {
		this.shcOrderSkus = shcOrderSkus;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "shcOrder")
	public Set<ShcOrderTransaction> getShcOrderTransactions() {
		return this.shcOrderTransactions;
	}

	public void setShcOrderTransactions(
			Set<ShcOrderTransaction> shcOrderTransactions) {
		this.shcOrderTransactions = shcOrderTransactions;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "shcOrder")
	public Set<ShcMerchandise> getShcMerchandises() {
		return this.shcMerchandises;
	}

	public void setShcMerchandises(Set<ShcMerchandise> shcMerchandises) {
		this.shcMerchandises = shcMerchandises;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "shcOrder")
	public Set<ShcUpsellPayment> getShcUpsellPayments() {
		return shcUpsellPayments;
	}

	public void setShcUpsellPayments(Set<ShcUpsellPayment> shcUpsellPayments) {
		this.shcUpsellPayments = shcUpsellPayments;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "shcOrder")
	public Set<ShcOrderAddOn> getShcOrderAddOns() {
		return shcOrderAddOns;
	}

	public void setShcOrderAddOns(Set<ShcOrderAddOn> shcOrderAddOns) {
		this.shcOrderAddOns = shcOrderAddOns;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "completed_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getCompletedDate() {
		return this.completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "routed_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getRoutedDate() {
		return this.routedDate;
	}

	public void setRoutedDate(Date routedDate) {
		this.routedDate = routedDate;
	}
	
	@Column(name = "resolution_descr", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public String getResolutionDescr() {
		return this.resolutionDescr;
	}

	public void setResolutionDescr(String resolutionDescr) {
		this.resolutionDescr = resolutionDescr;
	}

	/**
	 * @return the npsStatus
	 */
	@Column(name = "nps_status", unique = false, nullable = false, insertable = true, updatable = true)
	public String getNpsStatus() {
		return npsStatus;
	}

	/**
	 * @param npsStatus the npsStatus to set
	 */
	public void setNpsStatus(String npsStatus) {
		this.npsStatus = npsStatus;
	}

	@Column(name = "shc_unit_order_no")
	public String getShcUnitOrderNumber() {
		return shcUnitOrderNumber;
	}

	public void setShcUnitOrderNumber(String shcUnitOrderNumber) {
		this.shcUnitOrderNumber = shcUnitOrderNumber;
	}

}