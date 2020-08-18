/**
 * 
 */
package com.servicelive.staging.domain.hsr;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.servicelive.staging.domain.LoggableBaseDomain;

/**
 * @author hoza
 *
 */
@Entity
@Table ( name = "hsr_order_transaction")
public class HSRStageOrderTransaction extends LoggableBaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1730015612904827497L;
	
	@Id @GeneratedValue(strategy=IDENTITY)
	@Column(name="hsr_order_tran_id")
	private Integer hsrOrderTranId ;

	@ManyToOne
    @JoinColumn(name="hsr_order_id", insertable=false, updatable=false)
	private HSRStageOrder hsrOrderId;
	
	@Column (name = "transaction_type", unique = false, nullable = false, insertable = true, updatable = true, length = 25)
	private String transactionType;
	
	@Column (name = "input_file_name", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	private String inputFileName;
	
	@Column (name = "input_fragment", unique = false, nullable = false, insertable = true, updatable = true)
	private String inputFragment;

	/**
	 * @return the hsrOrderTranId
	 */
	public Integer getHsrOrderTranId() {
		return hsrOrderTranId;
	}

	/**
	 * @param hsrOrderTranId the hsrOrderTranId to set
	 */
	public void setHsrOrderTranId(Integer hsrOrderTranId) {
		this.hsrOrderTranId = hsrOrderTranId;
	}

	/**
	 * @return the hsrOrderId
	 */
	public HSRStageOrder getHsrOrderId() {
		return hsrOrderId;
	}

	/**
	 * @param hsrOrderId the hsrOrderId to set
	 */
	public void setHsrOrderId(HSRStageOrder hsrOrderId) {
		this.hsrOrderId = hsrOrderId;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the inputFileName
	 */
	public String getInputFileName() {
		return inputFileName;
	}

	/**
	 * @param inputFileName the inputFileName to set
	 */
	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
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
	
	
}
