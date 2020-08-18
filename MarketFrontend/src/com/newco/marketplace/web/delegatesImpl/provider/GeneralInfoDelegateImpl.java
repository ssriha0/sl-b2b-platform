/**
 * 
 */
package com.newco.marketplace.web.delegatesImpl.provider;

import java.util.List;

import com.newco.marketplace.business.iBusiness.provider.IGeneralInfoBO;
import com.newco.marketplace.business.iBusiness.zipcoverage.IZipCodeCoverageBO;
import com.newco.marketplace.dto.vo.provider.StateZipcodeVO;
import com.newco.marketplace.vo.provider.GeneralInfoVO;
import com.newco.marketplace.web.delegates.provider.IGeneralInfoDelegate;
import com.newco.marketplace.web.dto.provider.GeneralInfoDto;
import com.newco.marketplace.web.utils.GeneralInfoMapper;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DelegateException;

/**
 * @author KSudhanshu
 *
 */
public class GeneralInfoDelegateImpl implements IGeneralInfoDelegate {

	IGeneralInfoBO generalInfoBO;
	GeneralInfoMapper generalInfoMapper;
	IZipCodeCoverageBO zipCodeCoverageBO;
	//GeneralInfoVO generalInfoVO;
	//GeneralInfoDto generalInfoDto;
	
	/**
	 * @param generalInfoBO
	 * @param generalInfoMapper
	 * @param generalInfoVO
	 */
	public GeneralInfoDelegateImpl(IGeneralInfoBO generalInfoBO,
			GeneralInfoMapper generalInfoMapper) {
		this.generalInfoBO = generalInfoBO;
		this.generalInfoMapper = generalInfoMapper;
//		this.generalInfoVO = generalInfoVO;
//		this.generalInfoDto = generalInfoDto;
	}

	public GeneralInfoDto saveUserInfo(GeneralInfoDto objGeneralInfoDto)
			throws Exception {
		GeneralInfoVO generalInfoVO = new GeneralInfoVO();
		generalInfoVO = generalInfoMapper.convertDTOtoVO(objGeneralInfoDto, generalInfoVO);
		GeneralInfoVO rGeneralInfoVO = generalInfoBO.saveUserInfo(generalInfoVO);
		objGeneralInfoDto = generalInfoMapper.convertVOtoDTO(rGeneralInfoVO, objGeneralInfoDto);
		
		return objGeneralInfoDto;
		
	}
	
	public boolean isUserExist(String userName, String resourceId) throws Exception {
		boolean isUserExist = false;
		isUserExist = generalInfoBO.isUserAlreadyExist(userName, resourceId);	
		return isUserExist;
	}
	
	// SL-19667 validate if the resource having same personal info existing for the same firm
	public boolean isSameResourceExist(GeneralInfoDto generalInfoDto) throws Exception{
		boolean isUserExist = false;
		GeneralInfoVO generalInfoVO = new GeneralInfoVO();
		generalInfoVO = generalInfoMapper.convertDTOObjecttoVO(generalInfoDto);
		isUserExist = generalInfoBO.isSameResourceExist(generalInfoVO);	
		return isUserExist;
	}


	public GeneralInfoDto getUserInfo(GeneralInfoDto objGeneralInfoDto)
			throws Exception {
		System.out.println("GeneralInfoDelegateImpl.getGeneralInfo()");

		GeneralInfoVO generalInfoVO = new GeneralInfoVO();
		generalInfoVO = generalInfoMapper.convertDTOtoVO(objGeneralInfoDto, generalInfoVO);
		
		GeneralInfoVO rGeneralInfoVO = generalInfoBO.getUserInfo(generalInfoVO);
		
		objGeneralInfoDto = generalInfoMapper.convertVOtoDTO(rGeneralInfoVO, objGeneralInfoDto);
		
		return objGeneralInfoDto;
	}
	
	/* 
	 * MTedder@covansys.com
	 * (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.provider.IGeneralInfoDelegate#loadZipSet(com.newco.marketplace.web.dto.provider.GeneralInfoDto)
	 */
	public GeneralInfoDto loadZipSet(GeneralInfoDto generalInfoDto)
			throws Exception {
		
		try {
			GeneralInfoVO generalInfoVO = new GeneralInfoVO();
			generalInfoVO = generalInfoMapper.convertDTOtoVO(generalInfoDto, generalInfoVO);
			generalInfoVO = generalInfoBO.loadZipSet(generalInfoVO);
			generalInfoDto = generalInfoMapper.convertVOtoDTO(generalInfoVO,generalInfoDto); 
			
		} catch (BusinessServiceException ex) {
			//localLogger.info("Business Service Exception @RegistrationDelegateImpl.loadZipSet() due to"+ ex.getMessage());
			throw new DelegateException(
					"Business Service @GeneralInfoDelegateImpl.loadZipSet() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			//localLogger.info("General Exception @GeneralInfoDelegateImpl.loadZipSet() due to" + ex.getMessage());
			throw new DelegateException(
					"General Exception @GeneralInfoDelegateImpl.loadZipSet() due to "
							+ ex.getMessage());
		}
		
		return generalInfoDto;
	}

	public boolean zipExists(String zip)
			throws Exception {
		return generalInfoBO.zipExists(zip);
	}	
	/**
	 * Description: This method Soft deletes the provider
	 * @param resourceId
 	 * @param userName
 	 * @param firstName
 	 * @param lastName
 	 * @param adminUserName
 	 * @param vendorId
 	 * @return boolean
	 */
	public boolean  removeUser(String resourceId, String userName, String firstName, String lastName, String adminUserName, String vendorId) throws Exception {
		boolean flagTemp= generalInfoBO.removeUser(resourceId, userName, firstName, lastName, adminUserName, vendorId);
		return flagTemp;
	}
	
	public List<StateZipcodeVO> getStateCdAndZipAgainstCoverageZip(List<String> zipList) throws Exception {
		List<StateZipcodeVO> stateNameList = null;
		try {
			stateNameList = generalInfoBO.getStateCdAndZipAgainstCoverageZip(zipList);
		} catch (Exception ex) {
			throw new DelegateException(
					"General Exception @GeneralInfoDelegateImpl.loadStateforZip() due to " + ex.getMessage());
		}

		return stateNameList;

	}
	
	public String[] getSelectedZipCodesByFirmIdAndFilter(Integer firmId, Integer providerId) throws Exception {
		return zipCodeCoverageBO.getSelectedZipCodesByFirmIdAndResourceId(firmId, providerId);
	}
	
	public List<StateZipcodeVO> getOutOfStateDetails(String resourceId) throws Exception{
		return zipCodeCoverageBO.getOutOfStateDetails(resourceId);
	}

	public IZipCodeCoverageBO getZipCodeCoverageBO() {
		return zipCodeCoverageBO;
	}

	public void setZipCodeCoverageBO(IZipCodeCoverageBO zipCodeCoverageBO) {
		this.zipCodeCoverageBO = zipCodeCoverageBO;
	}
	


}
