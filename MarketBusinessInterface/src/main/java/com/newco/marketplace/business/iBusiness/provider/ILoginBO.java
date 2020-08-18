/**
 * 
 */
package com.newco.marketplace.business.iBusiness.provider;

import java.util.Map;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.common.LoginUserProfile;
import com.newco.marketplace.vo.provider.ChangePasswordVO;
import com.newco.marketplace.vo.provider.LoginVO;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.vo.leadprofile.LeadProfileDetailsVO;

/**
 * @author KSudhanshu
 * 
 */
public interface ILoginBO {

	/**
	 * definition for the authorize process
	 * 
	 * @param userName	username from xml
	 * @param apiKey    apikey  from xml
	 * @param ipAddress client ipaddress
	 * @return
	 * @throws Exception
	 */
	public int authorize(String userName, String apiKey, String ipAddress)
			throws Exception;

	/**
	 * @param objLoginVO
	 * @return 
	 * @throws Exception
	 */
	public int validatePassword(LoginVO objLoginVO) throws Exception;

	/**
	 * @param changePasswordVO
	 * @return
	 * @throws Exception
	 */
	public Boolean updatePassword(ChangePasswordVO changePasswordVO)
			throws Exception;

	/**
	 * @return
	 * @throws Exception
	 */
	public Map getSecretQuestionList();

	/**
	 * @return
	 * @throws Exception
	 */
	public String getVendorId(String username) throws Exception;

	/**
	 * @return
	 * @throws Exception
	 */
	public String getProviderName(String username) throws Exception;

	/**
	 * 
	 * @param vendorId
	 * @return
	 * @throws BusinessServiceException
	 */
	public VendorHdr getVendorDetails(String vendorId)
			throws BusinessServiceException;

	/**
	 * 
	 * @param vendorId
	 * @return
	 * @throws BusinessServiceException
	 */

	public String getPrimaryIndicator(Integer vendorId)
			throws BusinessServiceException;

	/**
	 * Description: Retrieves resource ID from the admin_resource table for use
	 * in the workflow monitor.
	 * 
	 * @param username
	 * @return <code>Integer</code> which is the admin's resource id.
	 */
	public Integer retrieveResourceIDByUserName(String username);

	/**
	 * Description - Get temporary password indicator from username
	 * 
	 * @return <code>boolean</code>
	 */
	public boolean getTempPasswordIndicator(String username);

	/**
	 * Gets the password based on the user name
	 * 
	 * @param username
	 * @return
	 */
	public LoginVO getPassword(String username);

	public LoginUserProfile getLoginInfoForBuyer(String userName);

	public LoginUserProfile getLoginInfoForProvider(String userName);
	
	public Integer showMemberOffers(Integer vendorId);
	
	/**
	 * @param vendorId
	 * @return
	 */
	public Integer showLeadsTCIndicator(Integer vendorId);
	
	/**checks whether provider has any un-archived CAR rules
	 * @param vendorId
	 * @return
	 */
	public int getUnarchivedCARRulesCount(Integer vendorId);

	/**checks whether provider has any active pending CAR rules
	 * @param vendorId
	 * @return
	 */
	public int getActivePendingCARRulesCount(Integer vendorId);

	/**checks whether provider has manage business profile permission
	 * @param resourceId
	 * @return
	 */
	public int getPermission(Integer resourceId);
 
	/**checks whether vendor has Leads accnt
	 * @param vendorId
	 * @return int
	 */
	public Integer showLeadsSignUp(Integer vendorId);
	
	//SL-19293 New T&C- START
	//update the new T&C indicator after user clicks on Agree button
	public void updateNewTandC(Integer vendorId, String userName);
	//SL-19293 New T&C- END

	/**Description:Checking non funded feature set is enabled for the buyer
	 * @param buyerId
	 * @return
	 */
	public boolean isNonFundedBuyer(Integer buyerId);
	//Added for D2C provider permission
	String buyerSkuPrimaryIndustry(Integer vendorId);
}
