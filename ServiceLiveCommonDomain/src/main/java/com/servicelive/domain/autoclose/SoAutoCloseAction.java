package com.servicelive.domain.autoclose;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.servicelive.domain.LoggableBaseDomain;

/**
 * AutoCloseRuleCriteria entity.
 * 
 */
@Entity
@Table(name = "so_auto_close_action")
public class SoAutoCloseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3451711761168686416L;

	// Fields
	/**
	 * 
	 */

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "autoclose_id", unique = true, nullable = false)
	private Integer autoCloseId;
	
    @Column(name = "so_id")
    private String soId;
	
    @Column(name = "autoclose_status")
    private String autoCloseStatus;
	
    @Column(name = "autoclose_date")
	private Date autoCloseDate;

	// Constructors

	/** default constructor */
	public SoAutoCloseAction() {
		super();
	}

	public Integer getAutoCloseId() {
		return autoCloseId;
	}

	public void setAutoCloseId(Integer autoCloseId) {
		this.autoCloseId = autoCloseId;
	}

	

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	

	

	public String getAutoCloseStatus() {
		return autoCloseStatus;
	}

	public void setAutoCloseStatus(String autoCloseStatus) {
		this.autoCloseStatus = autoCloseStatus;
	}

	public Date getAutoCloseDate() {
		return autoCloseDate;
	}

	public void setAutoCloseDate(Date autoCloseDate) {
		this.autoCloseDate = autoCloseDate;
	}
	
}