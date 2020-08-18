/**
 * 
 */
package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;


import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.provider.StateZipcodeVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.vo.hi.provider.BackgroundCheckProviderVO;
import com.newco.marketplace.vo.provider.BackgroundCheckVO;
import com.newco.marketplace.vo.provider.GeneralInfoVO;
import com.newco.marketplace.vo.provider.PublicProfileVO;
import com.newco.marketplace.vo.provider.TMBackgroundCheckVO;
import com.newco.marketplace.vo.provider.VendorResource;

public interface IVendorResourceDao {
	/**
	 * 
	 * @param vendorResource
	 * @return
	 * @throws DataServiceException
	 */
	public int update(VendorResource vendorResource) throws DBException;

	public int updateResourceLocationType(VendorResource vendorResource)
			throws DBException;

	public VendorResource query(VendorResource vendorResource)
			throws DBException;

	/**
	 * Inserts a vendor resource into the datastore
	 * 
	 * @param vendorResource
	 * @return @
	 */
	public VendorResource insert(VendorResource vendorResource)
			throws DBException;

	public List queryList(VendorResource vo) throws DBException;

	public int updateBackgroundCheckStatus(TMBackgroundCheckVO tmbcVO)
			throws DBException;

	public String getBackgroundCheckStatus(TMBackgroundCheckVO tmbcVO)
			throws DBException;

	public String getTeamMemberEmail(TMBackgroundCheckVO tmbcVO)
			throws DBException;

	public String getVendorEmail(TMBackgroundCheckVO tmbcVO) throws DBException;

	public int updateWFState(VendorResource vendorResource) throws DBException;
	
	public int updateWFStateAndMarketInd(VendorResource vendorResource) throws DBException;
	
	public List<VendorResource> getResourcesReadyForApproval() throws DBException;
	/**
	 * @param generalInfoVO
	 * @return
	 * @throws DBException
	 */
	public int update(GeneralInfoVO generalInfoVO)throws DBException;
    
	/**
	 * @param generalInfoVO
	 * @return
	 * @throws DBException
	 */
	public GeneralInfoVO get(GeneralInfoVO generalInfoVO) throws DBException;
    
	/**
	 * @param generalInfoVO
	 * @return
	 * @throws DBException
	 */
	public GeneralInfoVO insert(GeneralInfoVO generalInfoVO) throws DBException;
	public String getUserNameAdmin(String id) throws DBException ;
	/**
	 * @param resourceId
	 * @return
	 * @throws DBException
	 */
	public String getUserName(String resourceId) throws DBException;
	/**
	 * 
	 * @param vendorId
	 * @return
	 * @throws DBException
	 */
	public String getPrimaryIndicator(Integer vendorId) throws DBException;

	
	/**
	 * @param userName
	 * @param resourceId
	 * @return
	 * @throws DBException
	 */
	public GeneralInfoVO getVendorResourceByUserId(String userName) throws DBException;
	
	/**
	 * @param vendorId
	 * @param resourceId
	 * @return
	 * @throws DBException
	 */
	public int getResourceCountWithServiceInMarket(String vendorId,String resourceId) throws DBException;
	
	
	/**
	 * Description: This method update the given plusonekey for given resourceId
	 * @param resourceId
	 * @param plusOneKey
	 * @return
	 * @throws DBException
	 */
	public int insertResourcePlusOneKey(String resourceId,String plusOneKey) throws DBException;

	/**
	 * Description: This method retuns the plusonekey for given resourceId
	 * @param resourceId
	 * @return
	 * @throws DBException
	 */
	public String getResourcePlusOneKey(String resourceId) throws DBException;
	public String getRadiusMiles(String typeId) throws DBException;
	public PublicProfileVO getPublicProfile(Integer resourceId) throws DBException;
	
	public String getResourcePrimaryInd(String resourceId) throws DBException;
	public VendorResource getQueryByVendorId(int vendorId) throws DBException;
	
	public String getResourceName(Integer resourceId) throws DBException;
	public Boolean removeUser(String resourceId,String userName) throws DataAccessException;
	public List openServiceOrders (String resourceId) throws  DBException;
	
	public String getBackgroundChckStatus(String  resourceId)throws DBException;
	public int getMarketPlaceIndicator(String resourceId)throws DBException;
	public void updateBackgroundChckStatus(String resourceId)throws DBException;
	//SL-19667 update background check status.
	public int updateBackgroundCheckStatus(String resourceId)throws DBException;
	//SL-19667 update recertification status.
	public int recertify(String resourceId)throws DBException;  
	public List<Contact> getVendorContactInfoFromVendorId(int vendorId) throws DBException;
	/**
 	 * Description: This method fetches email address of the provider Admin
 	 * @param vendorId
 	 * @return String
 	 * @throws DBException
 	 */
	public String getProviderAdminEmail(String vendorId) throws DBException;
	
	public Contact getVendorResourceContact(int resourceId);
	public BackgroundCheckVO getBackgroundCheckInfo(Integer resourceId)throws DBException;
	public BackgroundCheckVO isBackgroundCheckRecertification(Integer resourceId)throws DBException;
		//sl-19667 insert background check history
	 public void insertBcHistory(GeneralInfoVO generalInfoVO);
	 
   public boolean isSameResourceExist(GeneralInfoVO generalInfoVO)throws DBException;
	public boolean isRecertificationDateDisplay (String resourceId)throws  DBException;

	//R11_1
	//Jira SL-20434
	public String getResourceSSNLastFour(String resourceId)throws  DBException;

	//R12_2
	//SL-20553
	public String getBgOriginalResourceId(String bg_check_id) throws DBException;

	//R12_2
	//SL-20675
	public BackgroundCheckVO getBackgroundInfo(Integer resourceId) throws DBException;
	
	public int updateResourceNoCred(VendorResource vendorResource) throws DBException;
	
	/**
 	 * Description: This method updates sl_pro_bkgnd_chk table
 	 * @param backgroundCheckProviderVO
 	 * @return Integer
 	 * @throws DBException
 	 */
	public Integer updateProviderBackground(BackgroundCheckProviderVO backgroundCheckProviderVO)throws DBException;
	/**
 	 * Description: This method updates bg_Check_id in vendor_resource table
 	 * @param backgroundCheckProviderVO
 	 * @return int
 	 * @throws DBException
 	 */
	public int updateBgCheckId(BackgroundCheckProviderVO backgroundCheckProviderVO)throws DBException;

	/**
	 * @param tempGeneralInfoVO
	 * @return
	 * @throws DBException
	 */
	public GeneralInfoVO insertDetails(GeneralInfoVO tempGeneralInfoVO)throws DBException;

	/**
	 * @param maskedAccountNo
	 * @param token
	 * @throws DBException
	 */
	public void updateSoAdditionalPayement(String maskedAccountNo, String token,String soId) throws DBException;

	/**
	 * @param maskedAccountNo
	 * @param token
	 * @param soId
	 * @param responseXml
	 * @throws DBException
	 */
	public void updateSoAdditionalPayementHistory(String maskedAccountNo,String token, String soId, String responseXml)throws DBException;
	
	public GeneralInfoVO insertZipcodes(GeneralInfoVO generalInfoVO) throws DBException;

	public GeneralInfoVO updateZipcodes(GeneralInfoVO generalInfoVO) throws DBException;
	
	public void insertOutOfStateZips(StateZipcodeVO stateZipcodeVO, String resourceId) throws DBException;
	
	public void deleteOutOfStateZips(String resourceId) throws DBException;

}
