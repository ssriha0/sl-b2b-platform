/**
 * 
 */
package com.newco.marketplace.dto.vo.serviceorder;

import java.util.Date;

import com.newco.marketplace.webservices.base.CommonVO;

/**
 * @author hoza
 *
 */
public class ServiceOrderOutFileTrackingVO extends CommonVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6121685169943691365L;
	/*`so_outfile_track_id` INT(11) NOT NULL AUTO_INCREMENT ,

	  `so_id` VARCHAR(30) NOT NULL  ,

	   `modified_date` TIMESTAMP NOT NULL  ,

	  `created_date` TIMESTAMP NOT NULL  ,

	  `modified_by` VARCHAR(30) NOT NULL ,

	  `transaction_type` VARCHAR(25)  NULL ,

	  `out_file_name` VARCHAR(100) NOT NULL ,

	  `out_file_fragment` LONGTEXT NOT NULL ,*/
	private Long soOutfileTrackId;
	private String soId;
	private Date modifiedDate;
	private Date createdDate;
	private String modifiedBy;
	private String transactionType;
	private String outFileName;
	private String outFileFragment;
	/**
	 * @return the soOutfileTrackId
	 */
	public Long getSoOutfileTrackId() {
		return soOutfileTrackId;
	}
	/**
	 * @param soOutfileTrackId the soOutfileTrackId to set
	 */
	public void setSoOutfileTrackId(Long soOutfileTrackId) {
		this.soOutfileTrackId = soOutfileTrackId;
	}
	/**
	 * @return the soId
	 */
	public String getSoId() {
		return soId;
	}
	/**
	 * @param soId the soId to set
	 */
	public void setSoId(String soId) {
		this.soId = soId;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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
	 * @return the outFileName
	 */
	public String getOutFileName() {
		return outFileName;
	}
	/**
	 * @param outFileName the outFileName to set
	 */
	public void setOutFileName(String outFileName) {
		this.outFileName = outFileName;
	}
	/**
	 * @return the outFileFragment
	 */
	public String getOutFileFragment() {
		return outFileFragment;
	}
	/**
	 * @param outFileFragment the outFileFragment to set
	 */
	public void setOutFileFragment(String outFileFragment) {
		this.outFileFragment = outFileFragment;
	}
	
	


}
