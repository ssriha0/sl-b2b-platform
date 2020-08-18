/*
**  TeamProfileDAOImpl.java     1.0     2007/06/05
*/
package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.vo.ExploreMktPlace.BPSearchCriteria;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.provider.ITeamProfileDAO;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.provider.BackgroundCheckVO;
import com.newco.marketplace.vo.provider.ServiceProviderRegistrationVO;
import com.newco.marketplace.vo.provider.TeamMemberVO;

/**
 * Spring/Ibatis implementation of TeamProfileDAO uses teamProfileMap.xml
 * 
 * $Revision: 1.12 $ $Author: glacy $ $Date: 2008/04/26 00:40:31 $
 *
 */
public class TeamProfileDAOImpl extends SqlMapClientDaoSupport implements ITeamProfileDAO 
{
    private static final Logger logger = Logger.getLogger(TeamProfileDAOImpl.class.getName());   

    /* (non-Javadoc)
     * @see com.newco.provider.team.dao.TeamProfileDAO#queryList(java.lang.String)
     */
    public List queryList(String userName) throws DataServiceException 
    {
   
        try
        {
            return getSqlMapClient().queryForList("teamProfile.queryList", userName);
            
        }//end try
        catch (SQLException dae)
        {
            logger.info("[SQLException thrown in queryList() of TeamProfileDAOImpl when processing the queryList, user name(" + userName + ").]" + StackTraceHelper.getStackTrace(dae));
            throw new DataServiceException("[SQLException thrown in queryList() of TeamProfileDAOImpl when processing the queryList, user name(" + userName + ").]", dae);
            
        }//end catch DataAccessException
        catch (Exception e)
        {
            logger.info("[General Exception thrown in queryList() of TeamProfileDAOImpl when processing the queryList, user name(" + userName + ").]"+ StackTraceHelper.getStackTrace(e));
            throw new DataServiceException("[General Exception thrown in queryList() of TeamProfileDAOImpl when processing the queryList, user name(" + userName + ").]", e);
            
        }//end catch Exception
        
    }//end method queryList()

    /*
     * (non-Javadoc)
     * @see com.newco.provider.team.dao.TeamProfileDAO#queryListForNonResource(int)
     */
    public List queryListForNonResource(Integer vendorId) throws DataServiceException
    {
    	List result=null;
        try
        {
        	System.out.println("------teamProfile.queryListForNonResource----------------");
        	result= getSqlMapClient().queryForList("teamProfile.queryListForNonResource", vendorId);
        	if(result!=null){
        		System.out.println("------teamProfile.queryListForNonResource----------------No Null"+result.size());
        	}
            
        }//end try
        catch (SQLException dae)
        {
            logger.info("[SQLException thrown in queryListForNonResource() of TeamProfileDAOImpl when processing for team profile non-resources, vendorId(" + vendorId + ").]" + StackTraceHelper.getStackTrace(dae));
            throw new DataServiceException("[SQLException thrown in queryListForNonResource() of TeamProfileDAOImpl when processing for team profile non-resources, vendorId(" + vendorId + ").]", dae);
            
        }//end catch DataAccessException
        catch (Exception e)
        {
            logger.info("[Exception thrown querying for team profile non-resources, vendorId(" + vendorId + ").] " + StackTraceHelper.getStackTrace(e));
            throw new DataServiceException("[General Exception thrown in queryListForNonResource() of TeamProfileDAOImpl when processing for team profile non-resources, vendorId(" + vendorId + ").]", e);
            
        }//end catch Exception
        return result;
    }//end method
    
    /*
     * (non-Javadoc)
     * @see com.newco.provider.team.dao.TeamProfileDAO#queryListForResource(int)
     */
    public List queryListForResource(int vendorId) throws DataServiceException
    {
    	List result=null;
        try
        {
        	
        	result= getSqlMapClient().queryForList("teamProfile.queryListForResource", vendorId);
            if(result!=null){
        		System.out.println("------teamProfile.queryListForResource----------------No Null"+result.size());
        	}
            
        }//end try
        catch (SQLException dae)
        {
            logger.info("[SQLException thrown in queryListForResource() of TeamProfileDAOImpl when processing for team profile resources" + StackTraceHelper.getStackTrace(dae));
            throw new DataServiceException("[SQLException thrown in queryListForNonResource() of TeamProfileDAOImpl when processing for team profile non-resources", dae);
            
        }//end catch DataAccessException
        catch (Exception e)
        {
            logger.info("[Exception thrown querying for team profile resources, vendorId(" + vendorId + ").] " + StackTraceHelper.getStackTrace(e));
            throw new DataServiceException("[General Exception thrown in queryListForResource()of TeamProfileDAOImpl when processing for team profile non-resources, vendorId(" + vendorId + ").]", e);
            
        }//end catch Exception
        return result;
    }//end method

	/* 
	 * MTedder
	 * (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.ITeamProfileDAO#queryEmailForTeamMember(com.newco.marketplace.vo.provider.BackgroundCheckVO)
	 */
	public BackgroundCheckVO queryEmailForTeamMember(BackgroundCheckVO backgroundCheckVO) throws DataServiceException {

		
		try {
			
			
			backgroundCheckVO = (BackgroundCheckVO)getSqlMapClient().queryForObject("teamProfile.queryEmail", backgroundCheckVO);
				
			//get background confirm ind from vendor_resource table
			Integer bci = (Integer)getSqlMapClient().queryForObject("teamProfile.queryBackgroundConfirmInd", backgroundCheckVO.getResourceId());
			backgroundCheckVO.setBackgroundConfirmInd(bci);
		
		} catch (SQLException e) {
			logger.info("Caught Exception and ignoring",e);
		}
			
		return backgroundCheckVO;
	}
	
	
	/*
	 * MTedder@covansys.com - ServicePRovider registration
	 * (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.ITeamProfileDAO#queryEmailForTeamMember(com.newco.marketplace.vo.provider.BackgroundCheckVO)
	 */
	public ServiceProviderRegistrationVO queryEmailForServiceProvider(ServiceProviderRegistrationVO serviceProviderRegistrationVO) throws DataServiceException {

		ServiceProviderRegistrationVO result = null;
		
		System.out.println("TeamProfileDAOImpl-queryEmailForTeamMember(): ");
		try {
			result = (ServiceProviderRegistrationVO)getSqlMapClient().queryForObject("teamProfileServiceProvider.queryEmail", serviceProviderRegistrationVO);
			System.out.println("TeamProfileDAOImpl.queryEmailForServiceProvider-RESULT: " + result.getEmail());
		} catch (SQLException e) {
			logger.info("Caught Exception and ignoring",e);
		}
				
		return result;
	}
	/*
	 * MTedder@covansys.com - Added update contact table functionality - 
	 */
    public int update(BackgroundCheckVO backgroundCheckVO) throws DataServiceException
    {
    	System.out.println("TeamProfileDAOImpl-update(): ");
        int result = 0;

        try {
            result = getSqlMapClient().update("contact.updateEmail", backgroundCheckVO);
            
        } catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @TeamProfileDAOImpl.update() due to"
					+ ex.getMessage());
			throw new DataServiceException(
					"SQL Exception @TeamProfileDAOImpl.update() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @TeamProfileDAOImpl.update() due to"
							+ ex.getMessage());
			throw new DataServiceException(
					"General Exception @TeamProfileDAOImpl.update() due to "
							+ ex.getMessage());
		}
	return result;
    }
	/*
	 * MTedder@covansys.com - Added update vendor_resource table functionality - 
	 */
    public int updateBackgroundConfirmInd(BackgroundCheckVO backgroundCheckVO) throws DataServiceException
    {
    	System.out.println("TeamProfileDAOImpl-updateBackgroundConfirmInd(): ");
    	
    	 int result = 0;
    	 
        try {
            result = getSqlMapClient().update("teamProfile.updateBackgroundConfirmInd", backgroundCheckVO);
            System.out.println("TeamProfileDAOImpl-updateBackgroundConfirmInd(): UPDATE!!!");
            
        } catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @TeamProfileDAOImpl.updateBackgroundConfirmInd() due to"
					+ ex.getMessage());
			throw new DataServiceException(
					"SQL Exception @TeamProfileDAOImpl.updateBackgroundConfirmInd() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @TeamProfileDAOImpl.updateBackgroundConfirmInd() due to"
							+ ex.getMessage());
			throw new DataServiceException(
					"General Exception @TeamProfileDAOImpl.updateBackgroundConfirmInd() due to "
							+ ex.getMessage());
		}
		return result;
    }//
    
    
    
    /*
     * (non-Javadoc)
     * @see com.newco.provider.team.dao.TeamProfileDAO#queryListForResource(int)
     */
    public List getTeamGridDetails(TeamMemberVO teamMemberVO) throws DataServiceException
    {
    	List result=null;
        try
        {
        	logger.info("-----------------------Start of getTeamGridDetails()-------------------");
        	result= getSqlMapClient().queryForList("teamProfile.getTabStatusForResources",teamMemberVO);
            if(result!=null){
        		System.out.println("------teamProfile.getTeamGridDetails----------------No Null"+result.size());
        	}
            logger.info("-----------------------Start of getTeamGridDetails()-------------------");
            
        }//end try
        catch (SQLException dae)
        {
            logger.info("[SQLException thrown in getTeamGridDetails() of TeamProfileDAOImpl when processing for team profile Grid");
            throw new DataServiceException("[SQLException thrown in getTeamGridDetails() of TeamProfileDAOImpl when processing for team profile Grid");
            
        }//end catch DataAccessException
        catch (Exception e)
        {
        	e.printStackTrace();
            throw new DataServiceException("[General Exception thrown in queryListForResource()of TeamProfileDAOImpl when processing for team profile Grid");
            
        }//end catch Exception
        return result;
    }//end method
    
    /*
	 * getBackgroundStatus functionality - 
	 */
    public int getBackgroundStatus(BackgroundCheckVO backgroundCheckVO) throws DataServiceException
    {
    	
    	int result = 0;

        try {
        	result = (java.lang.Integer)getSqlMapClient().queryForObject("teamProfile.queryForBackgroundState", backgroundCheckVO);
            
        } catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @TeamProfileDAOImpl.getBackgroundStatus() due to"
					+ ex.getMessage());
			throw new DataServiceException(
					"SQL Exception @TeamProfileDAOImpl.getBackgroundStatus() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @TeamProfileDAOImpl.getBackgroundStatus() due to"
							+ ex.getMessage());
			throw new DataServiceException(
					"General Exception @TeamProfileDAOImpl.getBackgroundStatus() due to "
							+ ex.getMessage());
		}
	return result;
    }
    
	public int getTotalRecordCount(int vendorId, CriteriaMap criteriaMap) throws DataServiceException {
		Map<String, Object> request = buildFilterVo(criteriaMap, Boolean.FALSE);
		request.put("vendorId", vendorId);
		Integer resultCount;
		try {
			resultCount = (Integer) getSqlMapClient().queryForObject("teamProfile.getFilterCount.query", request);
		} catch (SQLException e) {
            logger.info("[SQLException thrown in getTotalRecordCount() of TeamProfileDAOImpl when processing for team profile resources" + StackTraceHelper.getStackTrace(e));
            throw new DataServiceException("[SQLException thrown in queryListForNonResource() of TeamProfileDAOImpl when processing for team profile non-resources", e);
		}
		return resultCount;
	}
    
    @SuppressWarnings("unchecked")
	public List<TeamMemberVO> queryListForResource(int vendorId, CriteriaMap criteriaMap) throws DataServiceException
    {
    	List<TeamMemberVO> result=null;
    	Map<String, Object> filterRequest = buildFilterVo(criteriaMap, Boolean.TRUE);
    	filterRequest.put("vendorId", vendorId);
        try
        {
        	result= getSqlMapClient().queryForList("teamProfile.queryListForResourceWithPagination", filterRequest);
        }
        catch (SQLException dae)
        {
            logger.info("[SQLException thrown in queryListForResource() of TeamProfileDAOImpl when processing for team profile resources" + StackTraceHelper.getStackTrace(dae));
            throw new DataServiceException("[SQLException thrown in queryListForNonResource() of TeamProfileDAOImpl when processing for team profile non-resources", dae);
            
        }
        catch (Exception e)
        {
            logger.info("[Exception thrown querying for team profile resources, vendorId(" + vendorId + ").] " + StackTraceHelper.getStackTrace(e));
            throw new DataServiceException("[General Exception thrown in queryListForResource()of TeamProfileDAOImpl when processing for team profile non-resources, vendorId(" + vendorId + ").]", e);
            
        }
        return result;
    }
    
	private Map<String, Object> buildFilterVo(CriteriaMap criteria, boolean pagination)
	{
		Map<String, Object> filterRequest = new HashMap<String, Object>();
		PagingCriteria pagingObj = (PagingCriteria) criteria.get(OrderConstants.PAGING_CRITERIA_KEY);
		SortCriteria sortObj = (SortCriteria) criteria.get(OrderConstants.SORT_CRITERIA_KEY);
		BPSearchCriteria filterObj = (BPSearchCriteria) criteria.get(OrderConstants.SEARCH_CRITERIA_KEY);
		
		if (pagination && pagingObj != null) {
			logger.debug("SL-20916 startIndex === " +  pagingObj.getStartIndex());
			logger.debug("SL-20916 numberOfRecords === " +  pagingObj.getEndIndex());
			filterRequest.put("startIndex", pagingObj.getStartIndex() - 1); // Limit stats from 0. The start index that is passed is 1.
			filterRequest.put("numberOfRecords", pagingObj.getPageSize());
		}

		if (pagination && sortObj != null) {
			Map<String, String> sortOrder = ensureSortProviderSearch(sortObj.getSortColumnName(), sortObj.getSortOrder());
			if (sortOrder != null) {
				filterRequest.put("sortColumnName", sortOrder.get(OrderConstants.SORT_COLUMN_KEY));
				filterRequest.put("sortOrder", sortOrder.get(OrderConstants.SORT_ORDER_KEY));
			}
		}

		if (filterObj != null) {
			filterRequest.put("flName", filterObj.getFlName());
			filterRequest.put("userId", filterObj.getUserId() != -1 ? filterObj.getUserId() : null);
		}

		return filterRequest;
	}
	
	protected Map<String, String> ensureSortProviderSearch(String sortColumn, String sortOrder)
	{
		Map<String, String> sort = new HashMap<String, String>();
		boolean sortOrderSet = false;

		if (ServiceLiveStringUtils.isNotEmpty(sortColumn) && !ServiceLiveStringUtils.equals(sortColumn, OrderConstants.NULL_STRING)) {
			if ("flName".equalsIgnoreCase(sortColumn)) {
				sort.put(OrderConstants.SORT_COLUMN_KEY, "flName");
			} else {
				sort.put(OrderConstants.SORT_COLUMN_KEY, "flName");
				sort.put(OrderConstants.SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
				sortOrderSet = true;
			}
		} else {
			sort.put(OrderConstants.SORT_COLUMN_KEY, "flName");
			sort.put(OrderConstants.SORT_ORDER_KEY, OrderConstants.SORT_ORDER_ASC);
			sortOrderSet = true;
		}

		if (!sortOrderSet) {
			if (ServiceLiveStringUtils.isNotEmpty(sortOrder) && !ServiceLiveStringUtils.equals(sortColumn, OrderConstants.NULL_STRING)) {
				sort.put(OrderConstants.SORT_ORDER_KEY, sortOrder);
			} else {
				sort.put(OrderConstants.SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
			}
		}

		return sort;
	}
 
    
    
    
}//end class