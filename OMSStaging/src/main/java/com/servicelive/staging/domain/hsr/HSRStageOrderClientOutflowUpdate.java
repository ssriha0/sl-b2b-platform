/**
 * 
 */
package com.servicelive.staging.domain.hsr;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.servicelive.staging.domain.LoggableBaseDomain;

/**
 * @author hoza
 *
 */
@Entity
@Table ( name = "hsr_client_outflow_update")
public class HSRStageOrderClientOutflowUpdate extends LoggableBaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5547979952894502108L;

	/*
	 `callclose_process_ind` TINYINT NOT NULL DEFAULT 0 ,
	  `gl_process_ind` TINYINT NOT NULL DEFAULT 0 ,
	  */
	
	@Id @GeneratedValue (strategy = IDENTITY)
	@Column (name = "hsr_client_update_id", unique = true, nullable = false, insertable = true, updatable = true)
	private Integer hsrOrderClientUpdateId;

	@Column (name = "callclose_process_ind", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer callCloseProcessId= Integer.valueOf(0);
	
	@Column (name = "gl_process_ind", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer glProcessInd= Integer.valueOf(0);
	
	@ManyToOne(fetch=FetchType.EAGER, cascade= { CascadeType.ALL} , optional = false, targetEntity = HSRStageOrder.class)
    @PrimaryKeyJoinColumn(name="hsr_order_id")
    @JoinColumn(name="hsr_order_id", insertable=true, updatable=false)
    @NotNull
 	private HSRStageOrder hsrOrderId;

	/**
	 * @return the hsrOrderClientUpdateId
	 */
	public Integer getHsrOrderClientUpdateId() {
		return hsrOrderClientUpdateId;
	}

	/**
	 * @param hsrOrderClientUpdateId the hsrOrderClientUpdateId to set
	 */
	public void setHsrOrderClientUpdateId(Integer hsrOrderClientUpdateId) {
		this.hsrOrderClientUpdateId = hsrOrderClientUpdateId;
	}

	/**
	 * @return the callCloseProcessId
	 */
	public Integer getCallCloseProcessId() {
		return callCloseProcessId;
	}

	/**
	 * @param callCloseProcessId the callCloseProcessId to set
	 */
	public void setCallCloseProcessId(Integer callCloseProcessId) {
		this.callCloseProcessId = callCloseProcessId;
	}

	/**
	 * @return the glProcessInd
	 */
	public Integer getGlProcessInd() {
		return glProcessInd;
	}

	/**
	 * @param glProcessInd the glProcessInd to set
	 */
	public void setGlProcessInd(Integer glProcessInd) {
		this.glProcessInd = glProcessInd;
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
}
