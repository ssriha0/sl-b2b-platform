package com.newco.marketplace.persistence.iDao.userProfile;

import java.util.List;

import com.newco.marketplace.vo.apiUserProfile.FirmDetailsVO;
import com.newco.marketplace.vo.apiUserProfile.LocationResponseDate;
import com.newco.marketplace.vo.apiUserProfile.UserProfileData;
import com.newco.marketplace.vo.leadprofile.LeadProfileDetailsVO;



public interface IUserProfileDao {
	public UserProfileData findById(String userName) throws Exception ;
	public List<LocationResponseDate> getLocationDetails(int contactId) throws Exception;
	public FirmDetailsVO  getFirmDetails(int contactId) throws Exception;
	public LeadProfileDetailsVO getLeadDetails(int vendorId) throws Exception;
	public int lauchMarketCheck(String stateCd) throws Exception;

}
