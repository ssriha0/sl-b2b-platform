/**
 * 
 */
package com.newco.marketplace.web.delegates.provider;

import com.newco.marketplace.vo.provider.LicensesVO;
import com.newco.marketplace.web.dto.provider.LicensesDto;

/**
 * @author MTedder
 *
 */
public interface ILicensesDelegate {	
	public abstract void insert(LicensesDto licensesDto);
	public abstract LicensesVO get(LicensesDto licensesDto);
	public abstract void delete(LicensesDto licensesDto);
	public abstract void update(LicensesDto licensesDto);
}
