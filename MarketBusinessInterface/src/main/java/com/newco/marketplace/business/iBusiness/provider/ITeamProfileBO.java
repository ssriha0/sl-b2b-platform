/**
 * 
 */
package com.newco.marketplace.business.iBusiness.provider;



import java.util.List;


import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.provider.BackgroundCheckVO;
import com.newco.marketplace.vo.provider.TeamMemberVO;
import com.newco.marketplace.vo.provider.UserProfile;



/**
 * @author LENOVO USER
 * 
 */
public interface ITeamProfileBO {

	public List<TeamMemberVO> getTeamMemberList(UserProfile userProfile) throws BusinessServiceException;
	public BackgroundCheckVO queryEmailForTeamMember(BackgroundCheckVO backgoundCheckVO) throws BusinessServiceException;//MTedder
	public boolean saveBackgroundCheckData(BackgroundCheckVO teamProfileDTO) throws BusinessServiceException;
	public List getResourceActivityStatus(int vendorId) throws BusinessServiceException;
	public List<TeamMemberVO> getTeamGridDetails(TeamMemberVO teamMemberVO)	throws BusinessServiceException;
	public String getEncryptedPlusOneKey(String resourceId) throws BusinessServiceException, DataServiceException;
	public String getBackgroundCheckStatus(String resourceId) throws BusinessServiceException, DataServiceException;
	public int getMarketPlaceIndicator(String resourceId)throws BusinessServiceException;
	//SL-19667 update background check status.
	public void updateBackgroundCheckStatus(String resourceId)throws BusinessServiceException; 
	//SL-19667 update recertification status.
	public void recertify(String resourceId)throws BusinessServiceException;
	//SL-19667  sharing background Info
	public void doShare(String resourceId)throws BusinessServiceException;  
	public BackgroundCheckVO getBackgroundCheckInfo(Integer bcCheckId)throws BusinessServiceException;
	public BackgroundCheckVO isBackgroundCheckRecertification(Integer resourceId)throws BusinessServiceException;

	public boolean isRecertificationDateDisplay (String resourceId)throws  BusinessServiceException;
	
	//R11_1
	//Jira SL-20434
	public String getResourceSSNLastFour(String resourceId)throws  BusinessServiceException;
	
	//R12_2
	//Jira SL-20553
	public String getBgOriginalResourceId(String resourceId)throws  BusinessServiceException;

}
