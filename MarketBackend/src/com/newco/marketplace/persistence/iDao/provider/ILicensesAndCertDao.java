/**
 * 
 */
package com.newco.marketplace.persistence.iDao.provider;


import java.util.List;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;

/**
 * @author LENOVO USER
 *
 */
public interface ILicensesAndCertDao {
	public LicensesAndCertVO save(LicensesAndCertVO objLicensesAndCertVO);
	public LicensesAndCertVO getData(LicensesAndCertVO objLicensesAndCertVO);
	public LicensesAndCertVO getDataList(LicensesAndCertVO objLicensesAndCertVO);
	public abstract void update(LicensesAndCertVO objLicensesAndCertVO);
	public abstract LicensesAndCertVO get(LicensesAndCertVO objLicensesAndCertVO);
	public List getVendorCredentialsList(int vendorId) throws DBException;
	public List getVendorCredentialsInsurance(int vendorId) throws DBException;
    public List getMapState();
	public Boolean isVendorCredentialIdExist(LicensesAndCertVO licensesAndCertVO)throws DBException;
	public LicensesAndCertVO updateLicense(LicensesAndCertVO objLicensesAndCertVO) throws DBException;
	
}
