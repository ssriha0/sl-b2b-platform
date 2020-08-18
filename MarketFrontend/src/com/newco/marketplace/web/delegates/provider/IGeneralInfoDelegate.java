/**
 * 
 */
package com.newco.marketplace.web.delegates.provider;

import java.util.List;

import com.newco.marketplace.dto.vo.provider.StateZipcodeVO;
import com.newco.marketplace.web.dto.provider.GeneralInfoDto;


/**
 * @author KSudhanshu
 *
 */
public interface IGeneralInfoDelegate {

	/**
	 * Description: This method inserts the given general information to database.
	 * @param generalInfoDto
	 * @return
	 * @throws Exception
	 */
	public GeneralInfoDto saveUserInfo(GeneralInfoDto generalInfoDto) throws Exception;
	
	/**
	 * Description: This method returns the general info for given then given userId
	 * @param generalInfoDto
	 * @return
	 * @throws Exception
	 */
	public GeneralInfoDto getUserInfo(GeneralInfoDto generalInfoDto) throws Exception;
	
	/*
	 * MTedder@covansys.com - validate zip code is validate for state
	 */
	public GeneralInfoDto loadZipSet(GeneralInfoDto generalInfoDto) throws Exception;
	public boolean zipExists(String zip) throws Exception;	
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
	public boolean  removeUser(String resourceId, String userName, String firstName, String lastName, String adminUserName, String vendorId) throws Exception;
	
	public boolean isUserExist(String userName, String resourceId) throws Exception;
	// SL-19667 validate if the resource having same personal info existing for the same firm
	public boolean isSameResourceExist(GeneralInfoDto generalInfoDto) throws Exception;
	
	public List<StateZipcodeVO> getStateCdAndZipAgainstCoverageZip(List<String> zipList) throws Exception;
	public String[] getSelectedZipCodesByFirmIdAndFilter(Integer firmId, Integer providerId) throws Exception;
	public List<StateZipcodeVO> getOutOfStateDetails(String resourceId) throws Exception;


}
