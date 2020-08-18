package com.newco.marketplace.web.delegatesImpl.provider;


import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.ICompanyProfileBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.provider.CompanyProfileVO;
import com.newco.marketplace.web.delegates.provider.ICompanyProfileDelegate;
import com.newco.marketplace.web.dto.provider.CompanyProfileDto;
import com.newco.marketplace.web.utils.CompanyProfileMapper;
/**
 * @author LENOVO USER
 * 
 */
public class CompanyProfileDelegateImpl implements ICompanyProfileDelegate {

	private ICompanyProfileBO companyProfileBO;
	private CompanyProfileMapper companyProfileMapper;
	private static final Logger localLogger = Logger
			.getLogger(CompanyProfileDelegateImpl.class.getName());

	public CompanyProfileDelegateImpl(ICompanyProfileBO companyProfileBO,CompanyProfileMapper companyProfileMapper) {
		this.companyProfileBO = companyProfileBO;
		this.companyProfileMapper = companyProfileMapper;

	}
	
	public CompanyProfileDto getCompleteProfile(int vendorId) throws DelegateException
	{
		
		CompanyProfileDto companyProfileDto = new CompanyProfileDto();
		CompanyProfileVO companyProfileVO = new CompanyProfileVO();
		try
		{
			
			
			companyProfileVO = companyProfileBO.getCompleteProfile(vendorId);
			
			
			if(companyProfileVO != null)
			{
				companyProfileMapper.convertVOtoDTO(companyProfileVO, companyProfileDto);

			}
			
		}catch (BusinessServiceException bse) {
			throw new DelegateException(bse);
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger
					.info("[General Exception Occured at CompanyProfileDelegateImpl.getCompleteProfile]"
							+ ex.getMessage());
			throw new DelegateException(
					"[General Exception Occured at CompanyProfileDelegateImpl.getCompleteProfile]"
							+ ex.getMessage());
		}
		return companyProfileDto;
	}
}
