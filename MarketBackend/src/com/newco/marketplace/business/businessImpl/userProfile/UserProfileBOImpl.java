package com.newco.marketplace.business.businessImpl.userProfile;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.business.iBusiness.userProfile.IUserProfileBO;
import com.newco.marketplace.persistence.iDao.userProfile.IUserProfileDao;

import com.newco.marketplace.vo.apiUserProfile.FirmDetailsVO;
import com.newco.marketplace.vo.apiUserProfile.LocationResponseDate;
import com.newco.marketplace.vo.apiUserProfile.UserProfileData;
import com.newco.marketplace.vo.leadprofile.LeadProfileDetailsVO;




public class UserProfileBOImpl implements IUserProfileBO {
	
	private  IUserProfileDao userAuthProfileDao;
	
	public UserProfileData findById(String userName) throws Exception {
		UserProfileData user = userAuthProfileDao.findById(userName);
		return user;
	}
	public List<LocationResponseDate> getLocationDetails(int contactId) throws Exception{
		List<LocationResponseDate> locationList =  new ArrayList<LocationResponseDate>(); 
		locationList = userAuthProfileDao.getLocationDetails(contactId);
		return locationList;
	}
	public FirmDetailsVO  getFirmDetails(int contactId) throws Exception{
		FirmDetailsVO firmDetailsVO = userAuthProfileDao.getFirmDetails(contactId);
		return firmDetailsVO;
	}
	public LeadProfileDetailsVO getLeadDetails(int vendorId) throws Exception{
		LeadProfileDetailsVO leadProfileDetailsVO = userAuthProfileDao.getLeadDetails(vendorId);
		return leadProfileDetailsVO;
	}
	public int lauchMarketCheck(String stateCd) throws Exception {
		// TODO Auto-generated method stub
		return userAuthProfileDao.lauchMarketCheck(stateCd);
	}
	/**
	 * @return the userAuthProfileDao
	 */
	public IUserProfileDao getUserAuthProfileDao() {
		return userAuthProfileDao;
	}

	/**
	 * @param userAuthProfileDao the userAuthProfileDao to set
	 */
	public void setUserAuthProfileDao(IUserProfileDao userAuthProfileDao) {
		this.userAuthProfileDao = userAuthProfileDao;
	}
	
}
