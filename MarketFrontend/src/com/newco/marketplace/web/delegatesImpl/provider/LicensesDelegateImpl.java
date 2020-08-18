/**
 * 
 */
package com.newco.marketplace.web.delegatesImpl.provider;

import com.newco.marketplace.business.iBusiness.provider.ILicensesBO;
import com.newco.marketplace.vo.provider.LicensesVO;
import com.newco.marketplace.web.delegates.provider.ILicensesDelegate;
import com.newco.marketplace.web.dto.provider.LicensesDto;
import com.newco.marketplace.web.utils.LicensesMapper;

/**
 * @author MTedder
 *
 */
public class LicensesDelegateImpl implements ILicensesDelegate {

	//Accept DTO and convert to VO using object mapper
	private LicensesMapper licensesMapper;
	//private LicensesVO licensesVO;
	private ILicensesBO iLicensesBO;
	
	/**
	 * @param licensesMapper
	 * @param licensesVO
	 * @param licensesBO
	 */
	public LicensesDelegateImpl(LicensesMapper licensesMapper,
			 ILicensesBO licensesBO) {	
		this.licensesMapper = licensesMapper;
		//this.licensesVO = licensesVO;
		iLicensesBO = licensesBO;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ILicensesDelegate#delete(com.newco.marketplace.vo.LicensesVO)
	 */
	public void delete(LicensesDto licensesDto) {
		// TODO Auto-generated method stub
		//Map Dto to VO
		LicensesVO licensesVO = new LicensesVO();
		licensesVO = licensesMapper.convertDTOtoVO(licensesDto, licensesVO);
		//call BO method
		iLicensesBO.delete(licensesVO);	
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ILicensesDelegate#get(com.newco.marketplace.vo.LicensesVO)
	 */
	public LicensesVO get(LicensesDto licensesDto) {
		//Map Dto to VO
		LicensesVO licensesVO = new LicensesVO();
		licensesVO = licensesMapper.convertDTOtoVO(licensesDto, licensesVO);
		//call BO method
		licensesVO = iLicensesBO.get(licensesVO);	
		return licensesVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ILicensesDelegate#update(com.newco.marketplace.vo.LicensesVO)
	 */
	public void update(LicensesDto licensesDto) {
		//Map Dto to VO
		LicensesVO licensesVO = new LicensesVO();
		licensesVO = licensesMapper.convertDTOtoVO(licensesDto, licensesVO);
		//call BO method
		iLicensesBO.update(licensesVO);		
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ILicensesDelegate#save(com.newco.marketplace.vo.LicensesVO)
	 */
	public void insert(LicensesDto licensesDto) {
		
		//Map Dto to VO
		LicensesVO licensesVO = new LicensesVO();
		licensesVO = licensesMapper.convertDTOtoVO(licensesDto, licensesVO);
		//call BO method
		iLicensesBO.insert(licensesVO);
		
		//System.out.println("LicensesDelegateImpl save: " + licensesDto.getAddCredentialToFile());		
	}	
}
