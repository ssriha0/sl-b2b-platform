package com.newco.marketplace.web.delegates.provider;

import java.util.List
;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.provider.BackgroundCheckVO;
import com.newco.marketplace.web.dto.provider.BackgroundCheckDTO;
import com.newco.marketplace.web.dto.provider.TeamProfileDTO;

/**
 * @author LENOVO USER
 *
 */
public interface ITeamMemberDelegate {
	
	public TeamProfileDTO getTeamMemberList(TeamProfileDTO teamProfileDTO)throws DelegateException;
	public BackgroundCheckDTO queryEmailForTeamMember(BackgroundCheckDTO teamProfileDTO) throws DelegateException;//MTedder
	public boolean saveBackgroundCheckData(BackgroundCheckDTO teamProfileDTO) throws DelegateException;//MTedder
	public List getResourceActivityStatus(TeamProfileDTO teamProfileDTO) throws DelegateException;
	public TeamProfileDTO getTeamGridDetails(TeamProfileDTO teamProfileDTO) throws DelegateException;
	public String getEncryptedPlusOneKey(String resourceId)throws DelegateException;
	public String getBackgroundCheckStatus(String resourceId) throws DelegateException;
	public int getMarketPlaceIndicator(String resourceId) throws DelegateException;
	//SL-19667 update background check status.
	public void updateBackgroundCheckStatus(String resourceId)throws DelegateException;
	public BackgroundCheckVO getBackgroundCheckInfo(Integer resourceId)throws DelegateException;
	public BackgroundCheckVO isBackgroundCheckRecertification(Integer resourceId)throws DelegateException;
	public void recertify(String resourceId)throws DelegateException;
	//SL-19667 sharing background check status.
	public void doShare(String resourceId)throws DelegateException;

	public boolean isRecertificationDateDisplay (String resourceId)throws DelegateException;

	//R11_1
	//Jira SL-20434
	public String getResourceSSNLastFour(String resourceId) throws DelegateException;
	
	//R12_2
	//Jira SL-20553
	public String getBgOriginalResourceId(String resourceId) throws DelegateException;


}
