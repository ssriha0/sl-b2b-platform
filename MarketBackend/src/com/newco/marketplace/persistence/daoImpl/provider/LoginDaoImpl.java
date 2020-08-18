/**
 *
 */
package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.iDao.provider.ILoginDao;
import com.newco.marketplace.vo.common.LoginUserProfile;
import com.newco.marketplace.vo.provider.ChangePasswordVO;
import com.newco.marketplace.vo.provider.LoginVO;
import com.newco.marketplace.vo.security.SecurityVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileDetailsVO;
import com.newco.marketplace.interfaces.OrderConstants;

/**
 * @author KSudhanshu
 *
 */
public class LoginDaoImpl extends SqlMapClientDaoSupport implements ILoginDao {

	private final Log logger = LogFactory.getLog(getClass());
 
	/*
	 * (non-Javadoc)
	 *
	 * @see com.newco.marketplace.persistence.iDao.ILoginDao#query(com.newco.marketplace.web.dto.LoginDto)
	 * 
	 */
	public LoginVO query(LoginVO objLoginVO) throws Exception {

		try {
			objLoginVO = (LoginVO) getSqlMapClient().queryForObject(
					"query.getUser", objLoginVO);
		} catch (Exception ex) {
			logger.info("[LoginDaoImpl.select - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
			throw ex;
		}

		return objLoginVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.ILoginDao#authorizeUser(java.lang.String)
	 * it fetchs the entity id and enity type for the user
	 */
	public SecurityVO authorizeUser(String userName) throws Exception {
		SecurityVO securityVO = new SecurityVO();
		securityVO.setUsername(userName);
		try {
			securityVO = (SecurityVO) getSqlMapClient().queryForObject(
					"query.authorizeuser", securityVO);
		} catch (Exception ex) {
			logger.error("[LoginDaoImpl.authorize - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
			throw ex;
		}
		return securityVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.ILoginDao#checkUserCredentials(com.newco.marketplace.vo.security.SecurityVO)
	 * it check the data is present in the api_security table
	 */
	public SecurityVO checkUserCredentials(SecurityVO securityVO)
			throws Exception {
		try {
			securityVO = (SecurityVO) getSqlMapClient().queryForObject(
					"query.checkcredentials", securityVO);
		} catch (Exception ex) {
			logger.info("[LoginDaoImpl.checkcredentials - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
			throw ex;
		}
		return securityVO;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.newco.marketplace.persistence.iDao.ILoginDao#updatePassword(com.newco.marketplace.vo.ChangePasswordVO)
	 */
	public boolean updatePassword(ChangePasswordVO changePasswordVO)
			throws DataServiceException {
		int result = 0;

		try {
			result = getSqlMapClient().update("update.user.password",
					changePasswordVO);
			System.out.println("updated record count--" + result);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @@LoginDaoImpl.updatePassword() due to"
					+ ex.getMessage());
			throw new DataServiceException(
					"SQL Exception @@LoginDaoImpl.updatePassword() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @@LoginDaoImpl.updatePassword() due to"
							+ ex.getMessage());
			throw new DataServiceException(
					"General Exception @@LoginDaoImpl.updatePassword() due to "
							+ ex.getMessage());
		}
		if (result > 0)
			return true;
		else
			return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.newco.marketplace.persistence.iDao.ILoginDao#getSecretQuestionList()
	 */
	public Map getSecretQuestionList() {
		Map result = null;
		try {
			result = getSqlMapClient().queryForMap("get_secret_question.query",
					null, "id", "question_txt");
		} catch (Exception ex) {
			logger.info("[LoginDaoImpl.getSecretQuestionList - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.newco.marketplace.persistence.iDao.ILoginDao#getVendorId(java.lang.String)
	 */
	public String getVendorId(String username) throws Exception {
		String vendorId = null;
		try {
			vendorId = (String) getSqlMapClient().queryForObject(
					"getVendorId.query", username);
		} catch (Exception ex) {
			logger.info("[LoginDaoImpl.getVendorId - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
			throw ex;
		}

		return vendorId;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.newco.marketplace.persistence.iDao.ILoginDao#getProviderName(java.lang.String)
	 */
	public String getProviderName(String username) throws Exception {
		String providerName = null;
		try {
			providerName = (String) getSqlMapClient().queryForObject(
					"getProviderName.query", username);
		} catch (Exception ex) {
			logger.info("[LoginDaoImpl.getProviderName - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
			throw ex;
		}

		return providerName;
	}

	/**
	 *
	 * @param objLoginVO
	 * @return
	 * @throws Exception
	 */
	public boolean updateLoginInd(LoginVO objLoginVO) throws Exception {
		int result = 0;

		try {
			result = getSqlMapClient().update("updateInd.update", objLoginVO);
		} catch (Exception ex) {
			logger.info("[LoginDaoImpl.updateLoginInd - Exception] "
					+ ex.getStackTrace());
			throw ex;
		}
		if (result > 0)
			return true;
		else
			return false;
	}

	/**
	 *
	 */
	public List getPasswordList(String userName) throws DBException {
		List pwdList = null;
		try {
			// code change for SLT-2112
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("userName", userName);
			pwdList = getSqlMapClient().queryForList("passwordlist.query",
					parameter);
		}

		catch (Exception ex) {

			ex.printStackTrace();

			logger

			.info("General Exception @LoginDaoImpl.getPasswordList() due to"

			+ ex.getMessage());

			throw new DBException(

			"General Exception @LoginDaoImpl.getPasswordList() due to "

			+ ex.getMessage());

		}
		return pwdList;
	}

	public String getValidState(String vendorId) throws DBException {

		String stateId = null;

		try {

			stateId = (String) getSqlMapClient().queryForObject(
					"getActiveState.query", vendorId);

		}// end try

		catch (SQLException ex) {

			ex.printStackTrace();

			logger

			.info("SQL Exception @LoginDaoImpl.getValidState() due to"

			+ ex.getMessage());

			throw new DBException(

			"SQL Exception @LoginDaoImpl.getValidState() due to "

			+ ex.getMessage());

		} catch (Exception ex) {

			ex.printStackTrace();

			logger

			.info("General Exception @LoginDaoImpl.getValidState() due to"

			+ ex.getMessage());

			throw new DBException(

			"General Exception @LoginDaoImpl.getValidState() due to "

			+ ex.getMessage());

		}

		return stateId;

	}

	public Integer retrieveResourceIDByUserName(String username) {
		try {
			return (Integer) getSqlMapClient().queryForObject(
					"getAdminResourceId.query", username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public LoginUserProfile getBuyerLoginInfo(String userName) {
		try {
			return (LoginUserProfile) getSqlMapClient().queryForObject(
					"getBuyerLoginInfo.query", userName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public LoginUserProfile getContactInfo(String contactId) {
		try {
			return (LoginUserProfile) getSqlMapClient().queryForObject(
					"getContactInfo.query", contactId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public LoginUserProfile getProviderLoginInfo(String userName) {
		try {
			return (LoginUserProfile) getSqlMapClient().queryForObject(
					"getProviderLoginInfo.query", userName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Integer showMemberOffers(Integer vendorId){
		try{
			//fetching show_member_offers_ind
			Integer indicator = (Integer) getSqlMapClient().queryForObject(
					"getShowMemberOffersInd.query",vendorId);
			//updating show_member_offers_ind once user is logged in
			if(1 == indicator.intValue()){
				getSqlMapClient().update("updateShowMemberOffersInd.query", vendorId);
			}			
			return indicator;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//SL-19293: method to fetch new leads T&C indicator from vendor_lead_profile
	public Integer showLeadsTCIndicator(Integer vendorId){
		try{
			Integer indicator = (Integer) getSqlMapClient().queryForObject(
					"showLeadsTCIndicator.query",vendorId);		
			return indicator;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//get the count of un-archived CAR rules
	public int getUnarchivedCARRulesCount(Integer vendorId){
		Integer count = 0;
		try{
			count = (Integer)getSqlMapClient().queryForObject("getUnarchivedCARRulesCount.query", vendorId);
			return count;
			
		} catch(SQLException e) {
			logger.debug("Exception in LoginDaoImpl.getUnarchivedCARRulesCount due to "+e.getMessage());
			return count;
		}		
	}
	
	//get the count of active pending CAR rules
	public int getActivePendingCARRulesCount(Integer vendorId){
		Integer count = 0;
		try{
			count = (Integer)getSqlMapClient().queryForObject("getActivePendingCARRulesCount.query", vendorId);
			return count;
			
		} catch(SQLException e) {
			logger.debug("Exception in LoginDaoImpl.getActivePendingCARRulesCount due to "+e.getMessage());
			return count;
		}		
	}
	
	//checks whether provider has Manage Business Profile Permission
	public int getPermission(Integer resourceId){
		Integer count = 0;
		try{
			count = (Integer)getSqlMapClient().queryForObject("getBusinessProfilePermission.query", resourceId);
			return count;
			
		} catch(SQLException e) {
			logger.debug("Exception in LoginDaoImpl.getPermission due to "+e.getMessage());
			return count;
		}	
	}
	
	//checks whether vendor has Leads accnt
	public Integer showLeadsSignUp(Integer vendorId){
		Integer count = 0;
		try{
			count = (Integer)getSqlMapClient().queryForObject("getLeadsMemberInd.query", vendorId);
			return count;
			
		} catch(SQLException e) {
			logger.debug("Exception in LoginDaoImpl.showLeadsSignUp due to "+e.getMessage());
			return count;
		}	
	}
	//SL-19293 New T&C- START
	//update the new T&C indicator after user clicks on Agree button
	public void updateNewTandC(Integer vendorId, String userName){
		try{
			HashMap<String,Object> params = new HashMap<String, Object>();
			params.put("vendorId", vendorId);
			getSqlMapClient().update("updateNewTandC.query", params);
			params.put("tc", "1");
			params.put("actionName", OrderConstants.LEAD_TC_ACTION_NAME);
			params.put("userName", userName);
			getSqlMapClient().insert("insertTCHistory.query", params);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	//SL-19293 New T&C- END

	public boolean isNonFundedBuyer(Integer buyerId){
		boolean isNonFunded = false;
		try{
			HashMap params = new HashMap();
			params.put("buyerID",buyerId);
			params.put("feature","NON_FUNDED");
			String feature = (String)getSqlMapClient().queryForObject("buyerFeatuerSet.getFeature",params);
			if(null != feature && ("NON_FUNDED").equals(feature)){
				isNonFunded = true;
			}
		}catch(SQLException e){
			logger.info("Exception in getting buyer feature set"+ e);
		}
		return isNonFunded;
	}
}
