/**
 * 
 */
package com.newco.marketplace.dto.vo.powerbuyer;

import java.util.Date;

import com.newco.marketplace.webservices.base.CommonVO;

/**
 * @author rambewa
 *
 */
public class RequeVO extends CommonVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1244048870195481279L;
	/**
	 * 
	 */
	public RequeVO() {
	}
	
	private Date requeDate;
	private String requeTime;
	private String soId;
	private Integer resourceId;
	private Integer buyerId;
	private Integer filterId;

	public Date getRequeDate() {
		return requeDate;
	}
	public void setRequeDate(Date requeDate) {
		this.requeDate = requeDate;
	}
	public String getRequeTime() {
		return requeTime;
	}
	public void setRequeTime(String requeTime) {
		this.requeTime = requeTime;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getFilterId() {
		return filterId;
	}
	public void setFilterId(Integer filterId) {
		this.filterId = filterId;
	}
	
}
