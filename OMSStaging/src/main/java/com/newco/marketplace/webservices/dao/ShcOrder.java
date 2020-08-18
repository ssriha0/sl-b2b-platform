package com.newco.marketplace.webservices.dao;

import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * ShcOrder entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shc_order", uniqueConstraints = { @UniqueConstraint(columnNames = {"order_no", "unit_no" }) })
public class ShcOrder extends AbstractShcOrder implements java.io.Serializable {
	
	private static final long serialVersionUID = 3497463360270854506L;
	
	// Constructors
	
	/** default constructor */
	public ShcOrder() {
		// intentionally blank
	}

	/** minimal constructor */
	public ShcOrder(Integer shcOrderId, String orderNo, String unitNo,
			String storeNo) {
		super(shcOrderId, orderNo, unitNo, storeNo);
	}

	/** full constructor */
	public ShcOrder(Integer shcOrderId, String orderNo, String unitNo,
			String storeNo, String soId,Integer wfStateId, Integer npsProcessId, String npsStatus ,
			String omsProcessId, String salesCheckNum, String salesCheckDate, String promisedDate, Short processedInd,
			Integer glProcessId, Date createdDate, Date modifiedDate,
			String modifiedBy, Date completedDate, Date routedDate, String resolutionDescr,
			Set<ShcOrderSku> shcOrderSkus,Set<ShcOrderTransaction> shcOrderTransactions,
			Set<ShcMerchandise> shcMerchandises,Set<ShcNPSProcessLog> shcNPSProcessLogs,
			Set<ShcUpsellPayment> shcUpsellPayments,Set<ShcOrderAddOn> shcOrderAddOns) {
		super(shcOrderId, orderNo, unitNo, storeNo, soId,wfStateId, npsProcessId, omsProcessId, salesCheckNum, salesCheckDate, promisedDate, processedInd,
				glProcessId, createdDate, modifiedDate, modifiedBy, completedDate, routedDate, resolutionDescr,
				shcOrderSkus, shcOrderTransactions, shcMerchandises,shcUpsellPayments,shcOrderAddOns);
	}

}
