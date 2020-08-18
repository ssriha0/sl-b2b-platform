/**
 * 
 */
package com.newco.marketplace.business.iBusiness.provider;

import com.newco.marketplace.vo.provider.LicensesVO;

/**
 * @author MTedder
 *
 */
public interface ILicensesBO {
	public abstract void insert(LicensesVO licensesVO);	
	public abstract LicensesVO get(LicensesVO licensesVO);
	public abstract void delete(LicensesVO licensesVO);
	public abstract void update(LicensesVO licensesVO);	
}
