/**
 *
 */
package com.newco.marketplace.persistence.daoImpl.provider;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.dto.vo.provider.StateZipcodeVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao;
import com.newco.marketplace.vo.adminLogging.AdminLoggingVO;
import com.newco.marketplace.vo.hi.provider.BackgroundCheckProviderVO;
import com.newco.marketplace.vo.provider.BackgroundCheckVO;
import com.newco.marketplace.vo.provider.GeneralInfoVO;
import com.newco.marketplace.vo.provider.PublicProfileVO;
import com.newco.marketplace.vo.provider.TMBackgroundCheckVO;
import com.newco.marketplace.vo.provider.VendorResource;
import com.sears.os.dao.impl.ABaseImplDao;


public class VendorResourceImpl extends ABaseImplDao implements IVendorResourceDao
{
    private static final Logger logger = Logger.getLogger(VendorResourceImpl.class);

    /**
     * (non-Javadoc)
     * @see com.newco.provider.registration.dao.VendorResourceDao#update(com.newco.provider.registration.dao.VendorResource)
     */
    public int update(VendorResource vendorResource)throws DBException
    {
    	int result=0;
        try
        {
        	result= getSqlMapClient().update("vendorResource.updateP", vendorResource);

        }//end try
        catch (SQLException ex) {
            ex.printStackTrace();
		     logger.info("SQL Exception @VendorResourceImpl.update() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @VendorResourceImpl.update() due to "+ex.getMessage());
        }catch (Exception ex) {
            ex.printStackTrace();
		     logger.info("General Exception @VendorResourceImpl.update() due to"+ex.getMessage());
		     throw new DBException("General Exception @VendorResourceImpl.update() due to "+ex.getMessage());
       }
        return result;
    }//end method


    public int updateResourceLocationType(VendorResource vendorResource) throws DBException
    {
    	int result =0;
    	try{
    		result= getSqlMapClient().update("vendorResource.updateResourceLocationTypeP", vendorResource);
    	}catch (SQLException ex) {
            ex.printStackTrace();
		     logger.info("SQL Exception @VendorResourceImpl.updateResourceLocationType() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @VendorResourceImpl.updateResourceLocationType() due to "+ex.getMessage());
       }catch (Exception ex) {
           ex.printStackTrace();
		     logger.info("General Exception @VendorResourceImpl.updateResourceLocationType() due to"+ex.getMessage());
		     throw new DBException("General Exception @VendorResourceImpl.updateResourceLocationType() due to "+ex.getMessage());
      }
    	return result;
	//return getSqlMapClient().update("vendorResource.updateResourceLocationType", vendorResource);
    }


    public VendorResource query(VendorResource vendorResource) throws DBException
    {
	//return (VendorResource) getSqlMapClient().queryForObject("vendorResource.query", vendorResource);
    	VendorResource result =null;
    	try{
    		result= (VendorResource) getSqlMapClient().queryForObject("vendorResource.queryP", vendorResource);
    	}catch (SQLException ex) {
            ex.printStackTrace();
		     logger.info("SQL Exception @VendorResourceImpl.query() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @VendorResourceImpl.query() due to "+ex.getMessage());
       }catch (Exception ex) {
           ex.printStackTrace();
		     logger.info("General Exception @VendorResourceImpl.query() due to"+ex.getMessage());
		     throw new DBException("General Exception @VendorResourceImpl.query() due to "+ex.getMessage());
      }
    	return result;

    }

    /*
     * (non-Javadoc)
     * @see com.newco.provider.registration.dao.VendorResourceDao#insert(com.newco.provider.registration.dao.VendorResource)
     */
    public VendorResource insert(VendorResource vendorResource)throws DBException
    {
        Integer id = null;

        try
        {
            id =  (Integer) getSqlMapClient().insert("vendorResource.insertP", vendorResource);

        }//end try
        catch (SQLException ex) {
            ex.printStackTrace();
		     logger.info("SQL Exception @VendorResourceImpl.insert() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @VendorResourceImpl.insert() due to "+ex.getMessage());
        }catch (Exception ex) {
            ex.printStackTrace();
		     logger.info("General Exception @VendorResourceImpl.insert() due to"+ex.getMessage());
		     throw new DBException("General Exception @VendorResourceImpl.insert() due to "+ex.getMessage());
       }

        vendorResource.setResourceId(id);
        return vendorResource;

    }//end method insert()

    public List queryList(VendorResource vendorResource) throws DBException
    {
    	List result =null;
    	try{
    		result= getSqlMapClient().queryForList("vendorResource.query", vendorResource);
    	}catch (SQLException ex) {
            ex.printStackTrace();
		     logger.info("SQL Exception @VendorResourceImpl.queryList() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @VendorResourceImpl.queryList() due to "+ex.getMessage());
       }catch (Exception ex) {
           ex.printStackTrace();
		     logger.info("General Exception @VendorResourceImpl.queryList() due to"+ex.getMessage());
		     throw new DBException("General Exception @VendorResourceImpl.queryList() due to "+ex.getMessage());
      }
    	return result;
    	//return getSqlMapClient().queryForList("vendorResource.query", vendorResource);
    } // queryList
    
	public List openServiceOrders(String resourceId) throws DBException{
		List affectedServiceOrders =null;
	    
	try{
	 affectedServiceOrders = getSqlMapClient().queryForList("serviceOrders.query",resourceId);
	}catch (Exception ex) {
        ex.printStackTrace();
	    
	     throw new DBException("General Exception @VendorResourceImpl.queryList() due to "+ex.getMessage());
	}
	return affectedServiceOrders;
	}
	public Boolean removeUser(String resourceId,String userName) throws DataAccessException {
		
		try{
			getSqlMapClient().update("disableUserNameProvider.update", resourceId);
			getSqlMapClient().update("disableUserNameProvider1.update", userName);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

    public int updateBackgroundCheckStatus (TMBackgroundCheckVO tmbcVO) throws DBException {
        //return (getSqlMapClient().update("vendorResource.updateBackgroundCheckStatus", tmbcVO));
    	int result =0;
    	try{
    		result= (getSqlMapClient().update("vendorResource.updateBackgroundCheckStatus", tmbcVO));
    	}catch (SQLException ex) {
            ex.printStackTrace();
		     logger.info("SQL Exception @VendorResourceImpl.updateBackgroundCheckStatus() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @VendorResourceImpl.updateBackgroundCheckStatus() due to "+ex.getMessage());
       }catch (Exception ex) {
           ex.printStackTrace();
		     logger.info("General Exception @VendorResourceImpl.updateBackgroundCheckStatus() due to"+ex.getMessage());
		     throw new DBException("General Exception @VendorResourceImpl.updateBackgroundCheckStatus() due to "+ex.getMessage());
      }
    	return result;
    }//updateBackgroundCheckStatus

    public String getBackgroundCheckStatus (TMBackgroundCheckVO tmbcVO)  throws DBException   {


        //String rvString = (String) getSqlMapClient().queryForObject ("vendorResource.getBackgroundCheckStatus", tmbcVO.getResourceId());

        String rvString=null;
    	try{
    		rvString= (String) getSqlMapClient().queryForObject ("vendorResource.getBackgroundCheckStatus", tmbcVO.getResourceId());
    	}catch (SQLException ex) {
            ex.printStackTrace();
		     logger.info("SQL Exception @VendorResourceImpl.update() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @VendorResourceImpl.update() due to "+ex.getMessage());
       }catch (Exception ex) {
           ex.printStackTrace();
		     logger.info("General Exception @VendorResourceImpl.update() due to"+ex.getMessage());
		     throw new DBException("General Exception @VendorResourceImpl.update() due to "+ex.getMessage());
      }
        if (null==rvString) {
            rvString = "";
        }
        return (rvString);
    }//getBackgroundCheckStatus

    public String getTeamMemberEmail (TMBackgroundCheckVO tmbcVO)   throws DBException  {
//        String rvString = (String) getSqlMapClient().queryForObject ("vendorResource.getTeamMemberEmail", tmbcVO.getResourceId());
//        if (null==rvString) {
//            rvString = "";
//        }
//        return (rvString);

        String rvString=null;
    	try{
    		rvString= (String) getSqlMapClient().queryForObject ("vendorResource.getTeamMemberEmail", tmbcVO.getResourceId());
    	}catch (SQLException ex) {
            ex.printStackTrace();
		     logger.info("SQL Exception @VendorResourceImpl.getTeamMemberEmail() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @VendorResourceImpl.getTeamMemberEmail() due to "+ex.getMessage());
       }catch (Exception ex) {
           ex.printStackTrace();
		     logger.info("General Exception @VendorResourceImpl.getTeamMemberEmail() due to"+ex.getMessage());
		     throw new DBException("General Exception @VendorResourceImpl.getTeamMemberEmail() due to "+ex.getMessage());
      }
        if (null==rvString) {
            rvString = "";
        }
        return (rvString);


    }//getTeamMemberEmail

    public String getVendorEmail (TMBackgroundCheckVO tmbcVO)    throws DBException {
//        String rvString = (String) getSqlMapClient().queryForObject ("vendorResource.getVendorEmail", tmbcVO.getResourceId());
//        if (null==rvString) {
//            rvString = "";
//        }
//        return (rvString);


        String rvString=null;
    	try{
    		rvString= (String) getSqlMapClient().queryForObject ("vendorResource.getVendorEmail", tmbcVO.getResourceId());
    	}catch (SQLException ex) {
            ex.printStackTrace();
		     logger.info("SQL Exception @VendorResourceImpl.getVendorEmail() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @VendorResourceImpl.getVendorEmail() due to "+ex.getMessage());
       }catch (Exception ex) {
           ex.printStackTrace();
		     logger.info("General Exception @VendorResourceImpl.getVendorEmail() due to"+ex.getMessage());
		     throw new DBException("General Exception @VendorResourceImpl.getVendorEmail() due to "+ex.getMessage());
      }
        if (null==rvString) {
            rvString = "";
        }
        return (rvString);

    }//getVendorEmail

    public int updateWFState(VendorResource vendorResource) throws DBException
    {
    	int result=0;
        try
        {
        	result= getSqlMapClient().update("vendorResource.updateWfStateId", vendorResource);

        }//end try
        catch (SQLException ex) {
            ex.printStackTrace();
		     logger.info("SQL Exception @VendorResourceImpl.updateWFState() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @VendorResourceImpl.updateWFState() due to "+ex.getMessage());
        }catch (Exception ex) {
            ex.printStackTrace();
		     logger.info("General Exception @VendorResourceImpl.updateWFState() due to"+ex.getMessage());
		     throw new DBException("General Exception @VendorResourceImpl.updateWFState() due to "+ex.getMessage());
       }
        return result;

    }//end method
    
    public int updateWFStateAndMarketInd(VendorResource vendorResource) throws DBException
    {
    	int result=0;
        try
        {
        	result= getSqlMapClient().update("vendorResource.updateWfStateIdAndMarketInd", vendorResource);

        }//end try
        catch (SQLException ex) {
            ex.printStackTrace();
		     logger.info("SQL Exception @VendorResourceImpl.updateWFStateAndMarketInd() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @VendorResourceImpl.updateWFStateAndMarketInd() due to "+ex.getMessage());
        }catch (Exception ex) {
            ex.printStackTrace();
		     logger.info("General Exception @VendorResourceImpl.updateWFStateAndMarketInd() due to"+ex.getMessage());
		     throw new DBException("General Exception @VendorResourceImpl.updateWFStateAndMarketInd() due to "+ex.getMessage());
       }
        return result;

    }//end method


	public GeneralInfoVO get(GeneralInfoVO generalInfoVO) throws DBException {
		try {
			generalInfoVO = (GeneralInfoVO) getSqlMapClient().queryForObject("generalInfo.vendorResource.get", generalInfoVO);
        }
        catch (SQLException ex) {
             logger.info("SQL Exception @VendorResourceImpl.get() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @VendorResourceImpl.get() due to "+ex.getMessage());
        }catch (Exception ex) {
             logger.info("General Exception @VendorResourceImpl.get() due to"+ex.getMessage());
		     throw new DBException("General Exception @VendorResourceImpl.get() due to "+ex.getMessage());
        }

        return generalInfoVO;
	}


	public GeneralInfoVO insert(GeneralInfoVO generalInfoVO) throws DBException {
		Integer id = null;

        try {
            id = (Integer)getSqlMapClient().insert("generalInfo.vendorResource.insert", generalInfoVO);
            generalInfoVO.setResourceId(id.toString());
        }
        catch (SQLException ex) {
             logger.info("SQL Exception @VendorResourceImpl.insert() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @VendorResourceImpl.insert() due to "+ex.getMessage());
        }catch (Exception ex) {
             logger.info("General Exception @VendorResourceImpl.insert() due to"+ex.getMessage());
		     throw new DBException("General Exception @VendorResourceImpl.insert() due to "+ex.getMessage());
        }

        return generalInfoVO;
	}
	public String getPrimaryIndicator(Integer vendorId) throws DBException
	{
		String primary_ind = null;
		try
		{
			primary_ind = (String) getSqlMapClient().queryForObject ("vendorResource.getPrimaryIndicator", vendorId);
			logger.info("primary ind from db for the"+vendorId+ "is --"+ primary_ind);
		}
		catch (SQLException ex) {
		logger.info("SQL Exception @VendorResourceImpl.getPrimaryIndicator() due to"
				+ ex.getMessage());
		throw new DBException(
				"SQL Exception @VendorResourceImpl.getPrimaryIndicator() due to "
						+ ex.getMessage());
	} catch (Exception ex) {
		logger
				.info("General Exception @VendorResourceImpl.getPrimaryIndicator() due to"
						+ ex.getMessage());
		throw new DBException(
				"General Exception @VendorResourceImpl.getPrimaryIndicator() due to "
						+ ex.getMessage());
	}
	if (null==primary_ind) {
		primary_ind = "";
    }
    return (primary_ind);
	}


	public int update(GeneralInfoVO generalInfoVO) throws DBException {
		int result = 0;

        try {
            result = getSqlMapClient().update("generalInfo.vendorResource.update", generalInfoVO);

        } catch (SQLException ex) {
			logger.info("SQL Exception @VendorResourceImpl.update() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorResourceImpl.update() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			logger
					.info("General Exception @VendorResourceImpl.update() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorResourceImpl.update() due to "
							+ ex.getMessage());
		}
	return result;
	}


	public GeneralInfoVO getVendorResourceByUserId(String userName)
			throws DBException {
		
		GeneralInfoVO tempInfoVO = null;
		try {
			tempInfoVO = (GeneralInfoVO) getSqlMapClient().queryForObject("generalInfo.vendorResource.getVendorResource", userName);
        }
        catch (SQLException ex) {
             logger.info("SQL Exception @VendorResourceImpl.get() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @VendorResourceImpl.get() due to "+ex.getMessage());
        }catch (Exception ex) {
             logger.info("General Exception @VendorResourceImpl.get() due to"+ex.getMessage());
		     throw new DBException("General Exception @VendorResourceImpl.get() due to "+ex.getMessage());
        }
        
        return tempInfoVO;
	}
	public String getUserNameAdmin(String id) throws DBException {
		 String userName=null;
		try
		{
		         userName = (String) getSqlMapClient().queryForObject("generalInfo.vendorResource.getUserNameAdmin",id);
		}  
		catch (Exception ex) {
            logger.info("General Exception @VendorResourceImpl.get() due to"+ex.getMessage());
		     throw new DBException("General Exception @VendorResourceImpl.get() due to "+ex.getMessage());
       }
		      return (userName);
		
	}
	public String getUserName(String id) throws DBException {
		 String userName=null;
		 try{
		   userName = (String) getSqlMapClient().queryForObject("generalInfo.vendorResource.getUserName",id);
		 }catch (Exception ex) {
           logger.info("General Exception @VendorResourceImpl.get() due to"+ex.getMessage());
		   throw new DBException("General Exception @VendorResourceImpl.get() due to "+ex.getMessage());
          }
		      return (userName);
		
	      }
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao#getResourceCountWithServiceInMarket(java.lang.String, java.lang.String)
	 */
	public int getResourceCountWithServiceInMarket(String vendorId,String resourceId) throws DBException {
		Integer count = null;
		VendorResource vResource = new VendorResource();
		if (vendorId!= null && vendorId.length()>0)
			vResource.setVendorId(Integer.parseInt(vendorId));
		else 
			vResource.setVendorId(0);
		
		if (resourceId!= null && resourceId.length()>0)
			vResource.setResourceId(Integer.parseInt(resourceId));
		else 
			vResource.setResourceId(0);
		
        try {
        	count = (Integer)getSqlMapClient().queryForObject("get.resourcecount.withserviceinmarket", vResource);            
        }
        catch (SQLException ex) {
             logger.info("SQL Exception @VendorResourceImpl.getResourceCountWithServiceInMarket() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @VendorResourceImpl.getResourceCountWithServiceInMarket() due to "+ex.getMessage());
        }catch (Exception ex) {
             logger.info("General Exception @VendorResourceImpl.getResourceCountWithServiceInMarket() due to"+ex.getMessage());
		     throw new DBException("General Exception @VendorResourceImpl.getResourceCountWithServiceInMarket() due to "+ex.getMessage());
        }

        return count;
	}	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao#insertResourcePlusOneKey(java.lang.String, java.lang.String)
	 */
	public int insertResourcePlusOneKey(String resourceId,String plusOneKey) throws DBException{
		int result = 0;
		GeneralInfoVO tGeneralInfoVO= new GeneralInfoVO();
		tGeneralInfoVO.setResourceId(resourceId);
		tGeneralInfoVO.setPlusOneKey(plusOneKey);

        try {
            result = getSqlMapClient().update("vendorResource.updatebackgroundplusonekey", tGeneralInfoVO);

        } catch (SQLException ex) {
			logger.info("SQL Exception @VendorResourceImpl.insertResourcePlusOneKey() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorResourceImpl.insertResourcePlusOneKey() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			logger
					.info("General Exception @VendorResourceImpl.insertResourcePlusOneKey() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorResourceImpl.insertResourcePlusOneKey() due to "
							+ ex.getMessage());
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao#getResourcePlusOneKey(java.lang.String)
	 */
	public String getResourcePlusOneKey(String resourceId) throws DBException {
		String plusOneKey = null;
		try {
			plusOneKey = (String) getSqlMapClient().queryForObject("vendorResource.getBackgroundplusonekey", resourceId);
        }
        catch (SQLException ex) {
             logger.info("SQL Exception @VendorResourceImpl.getResourcePlusOneKey() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @VendorResourceImpl.getResourcePlusOneKey() due to "+ex.getMessage());
        }catch (Exception ex) {
             logger.info("General Exception @VendorResourceImpl.getResourcePlusOneKey() due to"+ex.getMessage());
		     throw new DBException("General Exception @VendorResourceImpl.getResourcePlusOneKey() due to "+ex.getMessage());
        }
        
        return plusOneKey;
	}
	public String getRadiusMiles(String typeId) throws DBException {
		String radiusMiles = null;
		try {
			radiusMiles = (String) getSqlMapClient().queryForObject("query.getServiceRadius",typeId);
        }
        catch (SQLException ex) {
             logger.info("SQL Exception @VendorResourceImpl.getRadiusMiles() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @VendorResourceImpl.getRadiusMiles() due to "+ex.getMessage());
        }catch (Exception ex) {
             logger.info("General Exception @VendorResourceImpl.getRadiusMiles() due to"+ex.getMessage());
		     throw new DBException("General Exception @VendorResourceImpl.getRadiusMiles() due to "+ex.getMessage());
        }
        
        return radiusMiles;
	}
	public PublicProfileVO getPublicProfile(Integer resourceId) throws DBException
	{
		PublicProfileVO profileVO = null;
		try {
			profileVO = (PublicProfileVO) getSqlMapClient().queryForObject("query.getPublicProfile", resourceId);
        }
        catch (SQLException ex) {
             logger.info("SQL Exception @VendorResourceImpl.getPublicProfile() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @VendorResourceImpl.getPublicProfile() due to "+ex.getMessage());
        }catch (Exception ex) {
             logger.info("General Exception @VendorResourceImpl.getPublicProfile() due to"+ex.getMessage());
		     throw new DBException("General Exception @VendorResourceImpl.getPublicProfile() due to "+ex.getMessage());
        }
        
        return profileVO;
	}
	
	
	
	public String getResourcePrimaryInd(String resourceId) throws DBException
	{
		String primaryInd = null;
		try
		{
			primaryInd = (String) getSqlMapClient().queryForObject("getResource.primaryInd.query",resourceId); 
		}catch (SQLException ex) {
            logger.info("SQL Exception @VendorResourceImpl.getResourcePrimaryInd() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @VendorResourceImpl.getResourcePrimaryInd() due to "+ex.getMessage());
       }catch (Exception ex) {
            logger.info("General Exception @VendorResourceImpl.getResourcePrimaryInd() due to"+ex.getMessage());
		     throw new DBException("General Exception @VendorResourceImpl.getResourcePrimaryInd() due to "+ex.getMessage());
       }
       return primaryInd;
	}
	/**
	 * get the resource details by vendor id for prim ind 1
	 * @param vendorId
	 * @return
	 * @throws DBException
	 */
	 public VendorResource getQueryByVendorId(int vendorId) throws DBException
	    {
		//return (VendorResource) getSqlMapClient().queryForObject("vendorResource.query", vendorResource);
	    	VendorResource result =null;
	    	try{
	    		result= (VendorResource) getSqlMapClient().queryForObject("vendorResourceByVendorId.queryP", vendorId);
	    	}catch (SQLException ex) {
	            ex.printStackTrace();
			     logger.info("SQL Exception @VendorResourceImpl.getQueryByVendorId() due to"+ex.getMessage());
			     throw new DBException("SQL Exception @VendorResourceImpl.getQueryByVendorId() due to "+ex.getMessage());
	       }catch (Exception ex) {
	           ex.printStackTrace();
			     logger.info("General Exception @VendorResourceImpl.getQueryByVendorId() due to"+ex.getMessage());
			     throw new DBException("General Exception @VendorResourceImpl.getQueryByVendorId() due to "+ex.getMessage());
	      }
	    	return result;

	    }


	public String getResourceName(Integer resourceId) throws DBException {
		String resourceName = null;
		try{
			resourceName= (String) getSqlMapClient().queryForObject("getResourceNameFromId.query", resourceId);
    	}catch (Exception ex) {
			     logger.info("General Exception @VendorResourceImpl.getResourceName() due to"+ex.getMessage());
			     throw new DBException("General Exception @VendorResourceImpl.getResourceName() due to "+ex.getMessage());
	    }
	    return resourceName;
	}


	public List<VendorResource> getResourcesReadyForApproval() throws DBException {
		List<VendorResource> resourceList = null;
		try{
			resourceList= (List<VendorResource>) getSqlMapClient().queryForList("getTeamMembersReadyForMarketPlace.query", null);
    	}catch (Exception ex) {
			     logger.info("General Exception @VendorResourceImpl.getResourcesReadyForApproval() due to"+ex.getMessage());
			     throw new DBException("General Exception @VendorResourceImpl.getResourcesReadyForApproval() due to "+ex.getMessage());
	    }		
		return resourceList;
	}

	/**
	 * Returns the background check status for the given resource_id
	 * @param String resourceId
	 * @return String
	 */
	 public String getBackgroundChckStatus (String resourceId)    {
	        String backgroundCheckStatus = null;
			try {
				backgroundCheckStatus = (String) getSqlMapClient().queryForObject("vendorResource.getBackgroundChckStatus",resourceId);
				if (null==backgroundCheckStatus) {
			        	backgroundCheckStatus = "";
			    }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	       
	        logger.info("getBackgroundChckStatus.backgroundCheckStatus:"+backgroundCheckStatus);
	        return backgroundCheckStatus;
	 }//getBackgroundCheckStatus
	
	 /**
	  * Returns the marketplace indicator for the given resource_id
	  * @param String resourceId
	  * @return int
	  */
	 public int getMarketPlaceIndicator (String resourceId) { 
	        int marketPlaceIndicator = 0;
			try {
				marketPlaceIndicator = (Integer) getSqlMapClient().queryForObject("vendorResource.getMarketPlaceInd",resourceId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	       
	        logger.info("getMarketPlaceIndicator.marketPlaceIndicator:"+marketPlaceIndicator);
	        return marketPlaceIndicator;
	 }//getBackgroundCheckStatus
	 
	 /**
	  * Updates the background check status for the given resource_id
	  * @param String resourceId
	  * 
	  */
	 public void updateBackgroundChckStatus (String resourceId) { 
	        int result = 0;	    	
	        try {
				result= (getSqlMapClient().update("vendorResource.updateBackgroundChckStatus", resourceId));
				//result = (Integer) getSqlMapClient().queryForObject("vendorResource.getMarketPlaceInd",resourceId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	       
	       
	 }
	 
	  //SL-19667 update background check status.
	 public int updateBackgroundCheckStatus (String resourceId) { 
	        int result = 0;	    	
	        try {
				result= (getSqlMapClient().update("vendorResource.updateBackgroundCheckStatusAsPending", resourceId));
			} catch (SQLException e) {
			
				e.printStackTrace();
			}	 
	        return result;      
	 }
	 
	 //SL-19667 update bg_request_type as 'R', background_requested date
		public int recertify(String resourceId){
			int result = 0;	    	
	        try {
				result= (getSqlMapClient().update("vendorResource.recertify", resourceId));
			} catch (SQLException e) {
				e.printStackTrace();
			}	 
	        return result;
				
		}

	 //sl-19667 insert background check history
	 public void insertBcHistory(GeneralInfoVO generalInfoVO){

	        try
	        {
	       getSqlMapClient().insert("vendorResource.insertBcHistory", generalInfoVO);

	        }//end try
	        catch (SQLException ex) {
	            ex.printStackTrace();
			     logger.info("SQL Exception @VendorResourceImpl.insert() due to"+ex.getMessage());
	        }catch (Exception ex) {
	            ex.printStackTrace();
			     logger.info("General Exception @VendorResourceImpl.insert() due to"+ex.getMessage());
	       }
	         
		 
	 }
	 
	 
	 //getBackgroundCheckStatus
	 
	 public List<Contact> getVendorContactInfoFromVendorId(int vendorId) throws DBException
	 {

			try{
				return (List<Contact>)getSqlMapClient().queryForList("vendorContactInfoFromVendorId.select", vendorId);
	    	}catch (Exception ex) {
				     logger.info("General Exception @VendorResourceImpl.getVendorContactInfoFromVendorId() due to"+ex.getMessage());
				     throw new DBException("General Exception @VendorResourceImpl.getVendorContactInfoFromVendorId() due to "+ex.getMessage());
		    }

	 }		
	/**
	 * Description: This method fetches email address of the provider Admin
	 * @param vendorId
	 * @return String
	 * @throws DBException
	 */
	public String getProviderAdminEmail(String vendorId) throws DBException {
		String providerAdminEmail;
		try {
			providerAdminEmail = (String) getSqlMapClient().queryForObject("generalInfo.vendorResource.getProviderAdminEmail", vendorId);
		} catch (Exception ex) {
			logger.info("General Exception @VendorResourceImpl.getProviderAdminEmail() due to" + ex.getMessage());
			throw new DBException("General Exception @VendorResourceImpl.getProviderAdminEmail() due to " + ex.getMessage(), ex);
		}
		return providerAdminEmail;
	}
	
	public Contact getVendorResourceContact(int resourceId){
	    Contact returnVal = new Contact();
	    returnVal = (Contact)getSqlMapClientTemplate().queryForObject( "vendorResourceContactInfoFromResourceId.select", resourceId);
	    return returnVal;
	}      
	
	public BackgroundCheckVO getBackgroundCheckInfo(Integer resourceId)throws DBException{
		BackgroundCheckVO result =null;                                                                                    
		try{                                                                                                             
  		result= (BackgroundCheckVO) getSqlMapClient().queryForObject("getBackgroundCheckInfo.query", resourceId);     
    	}catch (SQLException ex) {                                                                                                  ex.printStackTrace();                                                                                           	     logger.info("SQL Exception @VendorResourceImpl.getQueryByVendorId() due to"+ex.getMessage());                  	               }catch (Exception ex) {                                                                                                     ex.printStackTrace();                                                                                            	     logger.info("General Exception @VendorResourceImpl.getQueryByVendorId() due to"+ex.getMessage());              	          }                                                                                                                     	return result;   
	}
	
	
	public BackgroundCheckVO isBackgroundCheckRecertification(Integer resourceId)throws DBException{
		BackgroundCheckVO result =null;                                                                                    
		try{                                                                                                             
  		result= (BackgroundCheckVO) getSqlMapClient().queryForObject("isBackgroundCheckRecertification.query", resourceId);     
    	}catch (SQLException ex) {                                                                                      
            ex.printStackTrace();                                                                                       
    	     logger.info("SQL Exception @VendorResourceImpl.getQueryByVendorId() due to"+ex.getMessage());              
    	        
       }catch (Exception ex) {                                                                                          
           ex.printStackTrace();                                                                                        
    	     logger.info("General Exception @VendorResourceImpl.getQueryByVendorId() due to"+ex.getMessage());          
    	    
      }                                                                                                                 
    	return result;   
	}
	
	
	//SL-19667 check whether a resource with same personal information exist for the same vendor.
	   public boolean isSameResourceExist(GeneralInfoVO generalInfoVO)throws DBException{
		  Integer resourceId=null;                                                                                   
			try{                                                                                                             
				resourceId= (Integer) getSqlMapClient().queryForObject("isSameResourceExistForVendor.query", generalInfoVO); 
				if(null!=resourceId){
					return true;
				}
	    	}catch (SQLException ex) {                                                                                      
	            ex.printStackTrace();                                                                                       
	    	     logger.info("SQL Exception @VendorResourceImpl.isSameResourceExist() due to"+ex.getMessage());              
	    	        
	       }catch (Exception ex) {                                                                                          
	           ex.printStackTrace();                                                                                        
	    	     logger.info("General Exception @VendorResourceImpl.isSameResourceExist() due to"+ex.getMessage());          
	    	    
	      }                                                                                                                 
			return false;
	   }

		public boolean isRecertificationDateDisplay (String resourceId)throws  DBException{
			Integer count =null;
			try{                                                                                                             
				count= (Integer) getSqlMapClient().queryForObject("isRecertificationDateDisplay.query", resourceId); 
				if(null!=count && count>0){
					return true;
				}
	    	}catch (SQLException ex) {                                                                                      
	            ex.printStackTrace();                                                                                       
	    	     logger.info("SQL Exception @VendorResourceImpl.isSameResourceExist() due to"+ex.getMessage());              
	    	        
	       }catch (Exception ex) {                                                                                          
	           ex.printStackTrace();                                                                                        
	    	     logger.info("General Exception @VendorResourceImpl.isSameResourceExist() due to"+ex.getMessage());          
	    	    
	      }                                                                                                                 
			return false;
			
			
		}
	   
		//R11_1
		//Jira SL-20434
		public String getResourceSSNLastFour(String resourceId) throws DBException {
			String ssnLastFour = null;
			try {
				ssnLastFour = (String) getSqlMapClient().queryForObject("vendorResource.getBackgroundSsnLastFour", resourceId);
	        }
	        catch (SQLException ex) {
	             logger.info("SQL Exception @VendorResourceImpl.getResourceSSNLastFour() due to"+ex.getMessage());
			     throw new DBException("SQL Exception @VendorResourceImpl.getResourceSSNLastFour() due to "+ex.getMessage());
	        }catch (Exception ex) {
	             logger.info("General Exception @VendorResourceImpl.getResourceSSNLastFour() due to"+ex.getMessage());
			     throw new DBException("General Exception @VendorResourceImpl.getResourceSSNLastFour() due to "+ex.getMessage());
	        }
	        
	        return ssnLastFour;
		}
		
		
		//R12_2
		//Jira SL-20553
		public String getBgOriginalResourceId(String resourceId) throws DBException {
			String originalResourceId = null;
			try {
						originalResourceId = (String) getSqlMapClient().queryForObject("vendorResource.getBgOriginalResourceId", resourceId);
			    }
			catch (SQLException ex) {
			        logger.info("SQL Exception @VendorResourceImpl.getBgOriginalResourceId() due to"+ex.getMessage());
					throw new DBException("SQL Exception @VendorResourceImpl.getBgOriginalResourceId() due to "+ex.getMessage());
			    }catch (Exception ex) {
			         logger.info("General Exception @VendorResourceImpl.getBgOriginalResourceId() due to"+ex.getMessage());
					 throw new DBException("General Exception @VendorResourceImpl.getBgOriginalResourceId() due to "+ex.getMessage());
			     }
			        
			   return originalResourceId;
		}
		
		//R12_2
		//Jira SL-20675
		public BackgroundCheckVO getBackgroundInfo(Integer resourceId)throws DBException{
			BackgroundCheckVO result =null;                                                                                    
			try{                                                                                                             
				result= (BackgroundCheckVO) getSqlMapClient().queryForObject("getBackgroundInfo.query", resourceId);     
	    	}catch (SQLException ex) {                                                                                                                                                                          
	    	     logger.info("SQL Exception @VendorResourceImpl.getBackgroundInfo() due to"+ex);              
	    	        
	       }catch (Exception ex) {                                                                                                                                                          
	    	     logger.info("General Exception @VendorResourceImpl.getBackgroundInfo() due to"+ex);          
	    	    
	      }                                                                                                                 
	    	return result;   
		}
		
		//Updating No Cred indicator in vendor_resource table
	    public int updateResourceNoCred(VendorResource vendorResource)throws DBException
	    {
	    	int result=0;
	        try
	        {
	        	result= getSqlMapClient().update("vendorResource.updateResourceNoCred", vendorResource);

	        }
	        catch (SQLException ex) {
	            ex.printStackTrace();
			     logger.info("SQL Exception @VendorResourceImpl.updateResourceNoCred() due to"+ex.getMessage());
			     throw new DBException("SQL Exception @VendorResourceImpl.updateResourceNoCred() due to "+ex.getMessage());
	        }catch (Exception ex) {
	            ex.printStackTrace();
			     logger.info("General Exception @VendorResourceImpl.updateResourceNoCred() due to"+ex.getMessage());
			     throw new DBException("General Exception @VendorResourceImpl.updateResourceNoCred() due to "+ex.getMessage());
	       }
	        return result;
	    }
	   
	    /**
	 	 * Description: This method updates sl_pro_bkgnd_chk table
	 	 * @param backgroundCheckProviderVO
	 	 * @return Integer
	 	 * @throws DBException
	 	 */
	    public Integer updateProviderBackground(BackgroundCheckProviderVO backgroundCheckProviderVO)throws DBException{
	    	Integer result = 0;
	        try
	        {
	        	
	        	result =  (Integer) getSqlMapClient().insert("providerBackGroundCheck.insert", backgroundCheckProviderVO);

	        }//end try
	        catch (SQLException ex) {
	            ex.printStackTrace();
			     logger.info("SQL Exception @VendorResourceImpl.updateProviderBackground() due to"+ex.getMessage());
	        }catch (Exception ex) {
	            ex.printStackTrace();
			     logger.info("General Exception @VendorResourceImpl.updateProviderBackground() due to"+ex.getMessage());
	       }
		 return result;
	 }
	    /**
	 	 * Description: This method updates bg_Check_id in vendor_resource table
	 	 * @param backgroundCheckProviderVO
	 	 * @return int
	 	 * @throws DBException
	 	 */
	   
	    public int updateBgCheckId(BackgroundCheckProviderVO backgroundCheckProviderVO)throws DBException
	    {
	    	int result=0;
	        try
	        {
	        	result= getSqlMapClient().update("vendorResourceBgCheckId.update", backgroundCheckProviderVO);

	        }//end try
	        catch (SQLException ex) {
	            ex.printStackTrace();
			     logger.info("SQL Exception @VendorResourceImpl.updateBgCheckId() due to"+ex.getMessage());
			     throw new DBException("SQL Exception @VendorResourceImpl.updateBgCheckId() due to "+ex.getMessage());
	        }catch (Exception ex) {
	            ex.printStackTrace();
			     logger.info("General Exception @VendorResourceImpl.update() due to"+ex.getMessage());
			     throw new DBException("General Exception @VendorResourceImpl.update() due to "+ex.getMessage());
	       }
	        return result;
	    }


		public GeneralInfoVO insertDetails(GeneralInfoVO generalInfoVO)throws DBException {
			Integer id = null;

	        try {
	            id = (Integer)getSqlMapClient().insert("generalInfo.hi.vendorResource.insert", generalInfoVO);
	            generalInfoVO.setResourceId(id.toString());
	        }
	        catch (SQLException ex) {
	             logger.info("SQL Exception @VendorResourceImpl.insert() due to"+ex.getMessage());
			     throw new DBException("SQL Exception @VendorResourceImpl.insert() due to "+ex.getMessage());
	        }catch (Exception ex) {
	             logger.info("General Exception @VendorResourceImpl.insert() due to"+ex.getMessage());
			     throw new DBException("General Exception @VendorResourceImpl.insert() due to "+ex.getMessage());
	        }

	        return generalInfoVO;
		}


		
		public void updateSoAdditionalPayement(String maskedAccountNo,String token,String soId) throws DBException {
			 Map paramMap = new HashMap();
			 paramMap.put("maskeddAccNo", maskedAccountNo);
			 paramMap.put("token", token);
			 paramMap.put("soId", soId);
			 try{
		         getSqlMapClient().update("updateAdditionalPayment.update", paramMap);
                }//end try
		        catch (SQLException ex) {
		             ex.printStackTrace();
				     logger.info("SQL Exception @VendorResourceImpl.updateSoAdditionalPayement() due to"+ex.getMessage());
				     throw new DBException("SQL Exception @VendorResourceImpl.updateSoAdditionalPayement() due to "+ex.getMessage());
		        }catch (Exception ex) {
		             ex.printStackTrace();
				     logger.info("General Exception @VendorResourceImpl.updateSoAdditionalPayement() due to"+ex.getMessage());
				     throw new DBException("General Exception @VendorResourceImpl.updateSoAdditionalPayement() due to "+ex.getMessage());
		       }
			
		}
		public void updateSoAdditionalPayementHistory(String maskedAccountNo,String token,String soId,String xml) throws DBException {
			 Map paramMap = new HashMap();
			 paramMap.put("maskeddAccNo", maskedAccountNo);
			 paramMap.put("token", token);
			 paramMap.put("soId", soId);
			 paramMap.put("xml", xml);
			 try{
		         getSqlMapClient().update("updateAdditionalPaymentHistory.update", paramMap);
               }//end try
	         catch (SQLException ex) {
	             ex.printStackTrace();
			     logger.info("SQL Exception @VendorResourceImpl.updateSoAdditionalPayementHistory() due to"+ex.getMessage());
			     throw new DBException("SQL Exception @VendorResourceImpl.updateSoAdditionalPayement() due to "+ex.getMessage());
	         }catch (Exception ex) {
	             ex.printStackTrace();
			     logger.info("General Exception @VendorResourceImpl.updateSoAdditionalPayementHistory() due to"+ex.getMessage());
			     throw new DBException("General Exception @VendorResourceImpl.updateSoAdditionalPayement() due to "+ex.getMessage());
	       }
			
		}
		@Override
		public GeneralInfoVO insertZipcodes(GeneralInfoVO generalInfoVO) throws DBException {
	        try {
	        	List<Map<String, Serializable>> objList = new ArrayList<Map<String, Serializable>>();
	    		for (String zip : generalInfoVO.getCoverageZip()){
	        		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
	    			map.put("resourceId", generalInfoVO.getResourceId());
	        		map.put("createdBy","System");
	    			map.put("zipCode", zip);
	        		objList.add(map);
	    		}
	        	batchInsert("generalInfo.zipcode.insert", objList);       
	        }
	        catch (Exception ex) {
	             logger.info("General Exception @VendorResourceImpl.insertZipcodes() due to"+ex.getMessage());
			     throw new DBException("General Exception @VendorResourceImpl.insertZipcodes() due to "+ex.getMessage());
	        }

	        return generalInfoVO;
		}
		
		@Override
		public GeneralInfoVO updateZipcodes(GeneralInfoVO generalInfoVO) throws DBException {
	        try {
	    		getSqlMapClientTemplate().delete("generalInfo.delete.zip.all", generalInfoVO.getResourceId());
	        	insertZipcodes(generalInfoVO);      
	        }
	        catch (Exception ex) {
	             logger.info("General Exception @VendorResourceImpl.updateZipcodes() due to"+ex.getMessage());
			     throw new DBException("General Exception @VendorResourceImpl.updateZipcodes() due to "+ex.getMessage());
	        }

	        return generalInfoVO;
		}
		
		/*public void updateStateLicense(String zipcodeConfirmed) throws DBException {
			try {
				getSqlMapClientTemplate().update("generalInfo.update.license", zipcodeConfirmed); 
			} catch (Exception ex) {
				logger.info("General Exception @VendorResourceImpl.updateStateLicense() due to" + ex.getMessage());
				throw new DBException("General Exception @VendorResourceImpl.updateStateLicense() due to " + ex.getMessage());
			}
		}*/
		
		public void insertOutOfStateZips(StateZipcodeVO stateZipcodeVO, String resourceId) throws DBException {
			try {
				List<Map<String, Serializable>> objList = new ArrayList<Map<String, Serializable>>();
				HashMap<String, Serializable> map = new HashMap<String, Serializable>();
				map.put("resourceId", resourceId);
				map.put("stateCode", stateZipcodeVO.getStateCd());
				map.put("licenseConfirmation", stateZipcodeVO.getLicenseConfirmation());
				map.put("createdBy", resourceId);
				objList.add(map);
				batchInsert("generalInfo.outofstatezipcode.insert", objList);
				
			} catch (Exception ex) {
				logger.info("General Exception @VendorResourceImpl.insertOutOfStateZips() due to" + ex.getMessage());
				throw new DBException("General Exception @VendorResourceImpl.insertOutOfStateZips() due to " + ex.getMessage());
			}
			
		}
		
		public void deleteOutOfStateZips(String resourceId) throws DBException {
			try {
				getSqlMapClientTemplate().delete("generalInfo.delete.outOfStateZipsForResource", resourceId);
			}catch (Exception ex) {
				logger.info("General Exception @VendorResourceImpl.deleteOutOfStateZips() due to" + ex.getMessage());
				throw new DBException("General Exception @VendorResourceImpl.deleteOutOfStateZips() due to " + ex.getMessage());
			}
		}
		

}                                                                                                                        