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
@Table ( name = "hsr_order_customer")
public class HSRStageOrderCustomer extends LoggableBaseDomain {
/**
	 * 
	 */
	private static final long serialVersionUID = 603752109417969576L;

/*	
	`hsr_order_cust_id` INT NOT NULL ,
	  `hsr_order_id` INT(11) NULL ,
	  `customer_no` VARCHAR(10) NULL ,
	  `customer_type` VARCHAR(1) NULL ,
	  `customer_first_name` VARCHAR(11) NULL ,
	  `customer_last_name` VARCHAR(18) NULL ,
	  `customer_phone` VARCHAR(10) NULL ,
	  `customer_alt_phone` VARCHAR(10) NULL ,
	  `customer_pref_lang` VARCHAR(1) NULL ,
	  */
	
	@Id @GeneratedValue (strategy = IDENTITY)
	@Column (name = "hsr_order_cust_id", unique = true, nullable = false, insertable = true, updatable = true)
	private Integer hsrOrderCustomerId;

	@Column (name = "customer_no", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private String customerNo;

	@Column (name = "customer_type", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	private String customerType;
	
	@Column (name = "customer_first_name", unique = false, nullable = true, insertable = true, updatable = true, length = 11)
	private String customerFirstName;

	@Column (name = "customer_last_name", unique = false, nullable = true, insertable = true, updatable = true, length = 18)
	private String customerLastName;
	
	@Column (name = "customer_phone", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private String customerPhone;
	
	@Column (name = "customer_alt_phone", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private String customerAltPhone;
	
	@Column (name = "customer_pref_lang", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	private String customerPrefLanguage;
	
	
	@ManyToOne(fetch=FetchType.EAGER, cascade= { CascadeType.ALL} , optional = false, targetEntity = HSRStageOrder.class)
    @PrimaryKeyJoinColumn(name="hsr_order_id")
    @JoinColumn(name="hsr_order_id", insertable=true, updatable=false)
    @NotNull
 	private HSRStageOrder hsrOrderId;
	
	

	/**
	 * @return the hsrOrderCustomerId
	 */
	public Integer getHsrOrderCustomerId() {
		return hsrOrderCustomerId;
	}

	/**
	 * @param hsrOrderCustomerId the hsrOrderCustomerId to set
	 */
	public void setHsrOrderCustomerId(Integer hsrOrderCustomerId) {
		this.hsrOrderCustomerId = hsrOrderCustomerId;
	}

	/**
	 * @return the customerNo
	 */
	public String getCustomerNo() {
		return customerNo;
	}

	/**
	 * @param customerNo the customerNo to set
	 */
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * @return the customerFirstName
	 */
	public String getCustomerFirstName() {
		return customerFirstName;
	}

	/**
	 * @param customerFirstName the customerFirstName to set
	 */
	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	/**
	 * @return the customerLastName
	 */
	public String getCustomerLastName() {
		return customerLastName;
	}

	/**
	 * @param customerLastName the customerLastName to set
	 */
	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	/**
	 * @return the customerPhone
	 */
	public String getCustomerPhone() {
		return customerPhone;
	}

	/**
	 * @param customerPhone the customerPhone to set
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	/**
	 * @return the customerAltPhone
	 */
	public String getCustomerAltPhone() {
		return customerAltPhone;
	}

	/**
	 * @param customerAltPhone the customerAltPhone to set
	 */
	public void setCustomerAltPhone(String customerAltPhone) {
		this.customerAltPhone = customerAltPhone;
	}

	/**
	 * @return the customerPrefLanguage
	 */
	public String getCustomerPrefLanguage() {
		return customerPrefLanguage;
	}

	/**
	 * @param customerPrefLanguage the customerPrefLanguage to set
	 */
	public void setCustomerPrefLanguage(String customerPrefLanguage) {
		this.customerPrefLanguage = customerPrefLanguage;
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
