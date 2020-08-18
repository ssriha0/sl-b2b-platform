/**
 * 
 */
package com.newco.marketplace.web.delegates.provider;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.web.dto.provider.LicensesAndCertDto;

/**
 * @author LENOVO USER
 *
 */
public interface ILicensesAndCertDelegate {
	
	/**
	 * @param objLicensesAndCertDto
	 * @return
	 * @throws Exception
	 */
	public LicensesAndCertDto save(LicensesAndCertDto objLicensesAndCertDto) throws Exception;
	public abstract void update(LicensesAndCertDto objLicensesAndCertDto);
	
	public LicensesAndCertDto getData(LicensesAndCertDto objLicensesAndCertDto) throws Exception;
	
	public LicensesAndCertDto getDataList(LicensesAndCertDto objLicensesAndCertDto) throws Exception;
	
	
	/**
	 * @param licensesAndCertVO
	 * @param licensesAndCertDto
	 * @return
	 */
	public LicensesAndCertDto converVoToDto(LicensesAndCertVO licensesAndCertVO, LicensesAndCertDto LicensesAndCertDto);
	
	/**
	 * @param licensesAndCertDto
	 * @param licensesAndCertVO
	 * @return
	 */
	public LicensesAndCertVO converDtoToVo(LicensesAndCertDto licensesAndCertDto,LicensesAndCertVO licensesAndCertVO);
	
	public LicensesAndCertDto removeDocumentDetails(LicensesAndCertDto objLoginDto) throws DelegateException;
	public LicensesAndCertDto viewDocumentDetails(LicensesAndCertDto objLoginDto) throws DelegateException;
}
