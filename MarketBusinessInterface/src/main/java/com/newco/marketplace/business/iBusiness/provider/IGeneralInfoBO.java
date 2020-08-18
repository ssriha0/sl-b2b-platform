/**
 * 
 */
package com.newco.marketplace.business.iBusiness.provider;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.provider.StateZipcodeVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.GeneralInfoVO;
import com.newco.marketplace.vo.provider.TimeSlotDTO;


/**
 * @author KSudhanshu
 *
 */
public interface IGeneralInfoBO {

	/**
	 * Description: This method inserts the given general information to database.
	 * @param generalInfoVO
	 * @return
	 * @throws Exception
	 */
	public GeneralInfoVO saveUserInfo(GeneralInfoVO generalInfoVO) throws Exception;
	
	
	/**
	 * Description: This method returns the general info for given then given userId
	 * @param generalInfoVO
	 * @return
	 * @throws Exception
	 */
	public GeneralInfoVO getUserInfo(GeneralInfoVO generalInfoVO) throws Exception;
	
	/*
	 * MTedder@covansys.com
	 */
	public GeneralInfoVO loadZipSet(GeneralInfoVO generalInfoVO)throws Exception;	
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
	public Boolean removeUser(String resourceId, String userName, String firstName, String lastName, String adminUserName, String vendorId);
	/**
	 * Description: This method returns the true if zip code is found in DB
	 * @param zip
	 * @return <code>boolean</code>
	 */
	public boolean zipExists(String zip);
	
	public boolean isUserAlreadyExist(String userID, String resourceId) throws DBException;
	//sl-19667 is resource with same personal info exist for the same vendor
	public boolean isSameResourceExist(GeneralInfoVO generalInfoVO)throws DBException;
	
	public Map<String, TimeSlotDTO> getAvailableTimeSlots(List<String> providerList, Date date) throws Exception;

	public Map<String, TimeSlotDTO> getCapacityAvailableTimeSlots(List<String> providerList, Calendar startTimeCal, Calendar endTimeCal) throws Exception;
	
	public List<StateZipcodeVO> getStateCdAndZipAgainstCoverageZip(List<String> zipList)throws Exception;	

	
}
