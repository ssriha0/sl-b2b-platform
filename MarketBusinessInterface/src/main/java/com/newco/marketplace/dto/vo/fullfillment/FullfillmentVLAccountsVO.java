/**
 * 
 */
package com.newco.marketplace.dto.vo.fullfillment;

import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author schavda
 *
 */
public class FullfillmentVLAccountsVO  extends SerializableBaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 250244074584458431L;
	
	private Long ledgerEntityId;
	private Integer entityTypeId;
	private Integer accountId;	//primary key of lu_fullfillment_vlaccounts table
	private String accountCode;
	private String accountDesc;
	private Long v1AccountNo;
	private Long v2AccountNo;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private String modifiedBy;
	
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getEntityTypeId() {
		return entityTypeId;
	}
	public void setEntityTypeId(Integer entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
	public Long getLedgerEntityId() {
		return ledgerEntityId;
	}
	public void setLedgerEntityId(Long ledgerEntityId) {
		this.ledgerEntityId = ledgerEntityId;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Long getV1AccountNo() {
		return v1AccountNo;
	}
	public void setV1AccountNo(Long accountNo) {
		v1AccountNo = accountNo;
	}
	public Long getV2AccountNo() {
		return v2AccountNo;
	}
	public void setV2AccountNo(Long accountNo) {
		v2AccountNo = accountNo;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getAccountDesc() {
		return accountDesc;
	}
	public void setAccountDesc(String accountDesc) {
		this.accountDesc = accountDesc;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	
	
	
	
}
