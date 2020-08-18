/*
** TeamProfileDAO.java  1.0     2007/06/05
*/
package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.vo.provider.BackgroundCheckVO;
import com.newco.marketplace.vo.provider.ServiceProviderRegistrationVO;
import com.newco.marketplace.vo.provider.TeamMemberVO;

/**
 * Spring Data Access Object for Team Profile
 * 
 * @version
 * @author blars04
 *
 */
public interface ITeamProfileDAO 
{
    public List queryList(String userName) throws DataServiceException;
    
    /**
     * Query the datastore for the given vendor id and return all contact who
     * are NOT resources
     * 
     * @param vendorId
     * @return
     * @throws DataServiceException
     */
    public List queryListForNonResource(Integer vendorId) throws DataServiceException;
    
    
    /**
     * Query the datastore for the given vendor id and return all contact who
     * are resources.
     * 
     * @param vendorId
     * @return
     * @throws DataServiceException
     */
    public List queryListForResource(int vendorId) throws DataServiceException;
    public List<TeamMemberVO> queryListForResource(int vendorId, CriteriaMap criteriaMap) throws DataServiceException;
    /*
     * Query for teammember email and alternate email
     */
    public BackgroundCheckVO queryEmailForTeamMember(BackgroundCheckVO backgroundCheckVO) throws DataServiceException;
    /*
     * Query for Service provider email
     */
    public ServiceProviderRegistrationVO queryEmailForServiceProvider(ServiceProviderRegistrationVO serviceProviderRegistrationVO) throws DataServiceException;
    /*
     * MTedder@covansys.com Update contact table
     */
    public int update(BackgroundCheckVO backgroundCheckVO) throws DataServiceException;
    /*
     * MTedder@covansys.com Update vendor_resource
     */
    public int updateBackgroundConfirmInd(BackgroundCheckVO backgroundCheckVO) throws DataServiceException;
    public List getTeamGridDetails(TeamMemberVO teamMemberVO) throws DataServiceException;
    
    public int getBackgroundStatus(BackgroundCheckVO backgroundCheckVO) throws DataServiceException;
    public int getTotalRecordCount(int vendorId, CriteriaMap criteriaMap) throws DataServiceException;
}//end interface