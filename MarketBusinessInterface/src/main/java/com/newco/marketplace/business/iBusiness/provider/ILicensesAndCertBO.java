/**
 * 
 */
package com.newco.marketplace.business.iBusiness.provider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;


/**
 * @author LENOVO USER
 *
 */
public interface ILicensesAndCertBO {

	/**
	 * @param objLicensesAndCertVO TODO
	 * @return
	 * @throws Exceptiona
	 */
	public LicensesAndCertVO save(LicensesAndCertVO objLicensesAndCertVO) throws Exception;
	
	public LicensesAndCertVO getData(LicensesAndCertVO objLicensesAndCertVO) throws Exception;
	
	public LicensesAndCertVO getDataList(LicensesAndCertVO objLicensesAndCertVO) throws Exception;
	
	public abstract void update(LicensesAndCertVO LicensesAndCertVO);
	
	public LicensesAndCertVO viewDocument(
			LicensesAndCertVO licencesVO)
			throws BusinessServiceException, FileNotFoundException,
			IOException, ParseException ;
	public void removeDocument(LicensesAndCertVO credentialProfile)
			throws BusinessServiceException, FileNotFoundException,
			IOException, ParseException;
	
}
