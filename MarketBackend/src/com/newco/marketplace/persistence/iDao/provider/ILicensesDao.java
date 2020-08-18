/**
 * 
 */
package com.newco.marketplace.persistence.iDao.provider;

import com.newco.marketplace.vo.provider.LicensesVO;

/**
 * @author MTedder
 *
 */
public interface ILicensesDao {
	public abstract void insert(LicensesVO licensesVO);
	public abstract LicensesVO get(LicensesVO licensesVO);
	public abstract void delete(LicensesVO licensesVO);
	public abstract void update(LicensesVO licensesVO);
}
