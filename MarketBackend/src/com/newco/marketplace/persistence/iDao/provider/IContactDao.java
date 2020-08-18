/**
 *
 */
package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.BackgroundCheckVO;
import com.newco.marketplace.vo.provider.Contact;
import com.newco.marketplace.vo.provider.GeneralInfoVO;
import com.newco.marketplace.vo.provider.TMBackgroundCheckVO;

public interface IContactDao
{
    public int update(Contact contact)throws DBException;
    public Contact query(Contact contact) throws DBException;
    public Contact insert(Contact contact) throws DBException;
    public Contact registrationInsert(Contact contact) throws DBException;
    public List queryList(Contact vo)throws DBException ;

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

	public Contact getByBuyerId(Integer buyerId) throws DBException;
	/**
	 * @param generalInfoVO
	 * @return
	 * @throws DBException
	 */
	public GeneralInfoVO insert(GeneralInfoVO generalInfoVO) throws DBException;
	
	public GeneralInfoVO insertContact(GeneralInfoVO generalInfoVO) throws DBException;

	//Start of changes by Mayank for email validation
	public Contact queryPvalidateEmail(Contact contact) throws DBException;
	//End of changes by Mayank for email validation
	
	public Contact queryPvalidateEmailId(Contact contact) throws DBException;
	
	public Integer getContactIdByUserName(String username) throws DBException;
	
	public Contact getByVendorId(Integer vendorId) throws DBException;
	
	//SL-19667  - get the contact with same details  
	public Integer getBackgroundCheckIdWithSameContact(GeneralInfoVO generalInfoVO) throws DBException;
	//SL-19667  - update bg_check_id
	public int updateBcCheck(GeneralInfoVO generalInfoVO) throws DBException;
	//  SL-19667 insert background check information
	public Integer insertBcCheck(GeneralInfoVO generalInfoVO) throws DBException;
	public void insertBcCheckHistory(GeneralInfoVO generalInfoVO) throws DBException ;
     public Integer getBcCheckId (String resourceId)throws DBException ;
	public BackgroundCheckVO getBackgroundCheckInfo(Integer backgroundCheckId) throws DBException;

	// SL-19667 get background check Id of resource from vendor_resource
	public String getBackgroundCheckResourceInfo(String resourceId) throws DBException;

	//R12_2
	//SL-20553
	public Integer getCountWithSameBackgroundCheckId(String bgCheckId) throws DBException;
	
	public Integer insertBcCheckDetails (GeneralInfoVO backgroundInfo)throws DBException;
	
	public int updateBackgroundDetails(TMBackgroundCheckVO tMBackgroundCheckVO) throws DBException;

	public BackgroundCheckVO getBackgroundCheckInfoByResourceId(String resourceId) throws DBException;
	public Integer getBackgroundCheckDetailsWithSameContact(GeneralInfoVO provider) throws DBException;
	public GeneralInfoVO getProviderDetailsWithSameContact(GeneralInfoVO provider) throws DBException;
	public int updateBGStateInfo(GeneralInfoVO generalInfo)throws DBException;
}
