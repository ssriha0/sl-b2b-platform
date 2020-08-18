/**
 *
 */
package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.vo.common.LoginUserProfile;
import com.newco.marketplace.vo.leadprofile.LeadProfileDetailsVO;
import com.newco.marketplace.vo.provider.ChangePasswordVO;
import com.newco.marketplace.vo.provider.LoginVO;
import com.newco.marketplace.vo.security.SecurityVO;


/**
 * @author KSudhanshu
 *
 */
public interface ILoginDao {

	 
	
	/**
	 * @param securityVO
	 * @return
	 * @throws Exception
	 */
	public SecurityVO checkUserCredentials(SecurityVO securityVO) throws Exception;
	
	/**
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public SecurityVO authorizeUser(String userName) throws Exception;
	
	/**
	 * @param objLoginVO
	 * @return
	 */
	public LoginVO query(LoginVO objLoginVO) throws Exception;



	/**
	 * @param objLoginVO
	 * @return
	 */
	public String getVendorId(String username) throws Exception;

	/**
	 * @param objLoginVO
	 * @return
	 */
	public String getProviderName(String username) throws Exception;

	/**
	 * @param changePasswordVO
	 * @return
	 */
	public boolean updatePassword(ChangePasswordVO changePasswordVO)
			throws DataServiceException;

	/**
	 * @return
	 * @throws Exception
	 */
	public Map getSecretQuestionList();

	/**
	*
	* @param objLoginVO
	* @return
	* @throws Exception
	*/
	public boolean updateLoginInd(LoginVO objLoginVO)
	throws Exception;
	public String getValidState(String vendorId) throws DBException;

	public List getPasswordList(String userName) throws DBException;

	/**
	 * Description:  Retrieves resource ID from the admin_resource table for use in
	 * the workflow monitor.
	 * @param username
	 * @return <code>Integer</code> which is the admin's resource id.
	 */
	public Integer retrieveResourceIDByUserName(String username);

	public LoginUserProfile getBuyerLoginInfo(String userName);

	public LoginUserProfile getContactInfo(String contactId);

	public LoginUserProfile getProviderLoginInfo(String userName);
	
	public Integer showMemberOffers(Integer vendorId);
	
	/**
	 * @param vendorId
	 * @return
	 */
	public Integer showLeadsTCIndicator(Integer vendorId);

	//checks whether provider has any un-archived CAR rules
	public int getUnarchivedCARRulesCount(Integer vendorId);
	
	//checks whether provider has any active pending CAR rules
	public int getActivePendingCARRulesCount(Integer vendorId);

	//checks whether provider has Manage Business Profile Permission
	public int getPermission(Integer resourceId);

	//checks whether vendor has Leads accnt
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
}
