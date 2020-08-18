
package com.newco.marketplace.persistance.daoImpl.providerBackground;


import java.util.List;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.providerBackground.ProviderBackgroundCheckDao;
import com.newco.marketplace.vo.provider.ExpiryNotificationVO;
import com.newco.marketplace.vo.provider.ProviderBackgroundCheckVO;
import com.newco.marketplace.vo.provider.VendorNotesVO;
import com.sears.os.dao.impl.ABaseImplDao;


public class ProviderBackgroundCheckDaoImpl extends ABaseImplDao implements ProviderBackgroundCheckDao 
{
	public List<ProviderBackgroundCheckVO> getProviderInfoForNotification() throws DataServiceException
    {
    	try {

			return (List<ProviderBackgroundCheckVO>) queryForList("getProviderInfoNotification.query");
		} catch (IllegalStateException ex) {
			
            throw new DataServiceException("Exception while fetching provider information", ex);

		}
		catch (Exception ex) {
			
            throw new DataServiceException("Exception while fetching provider information", ex);

		}
    }

	public void insertVendorNotes(List<VendorNotesVO> voList) throws DataServiceException{
		

		try {
			batchInsert("vendorNotesInfo.insert", voList);
			logger.info("Done inserting into the table vendor notes !!!!!!!!!!!!!!!!");
			
		}catch (DataAccessException ex) {
			
            throw new DataServiceException("VendorNotesImpl.insert - Exception", ex);

		}
		catch (Exception ex) {
			logger.error("[VendorNotesImpl.insert - Exception] "
                + ex.getMessage());
			
			throw new DataServiceException("[VendorNotesImpl.insert - Exception] ", ex);
		}

	}
	
	
	public boolean addAlertToQueue(List<AlertTask> alertTaskList) throws DataServiceException {
        try {
        	batchInsert("alertInfo.insert", alertTaskList);
        }catch (DataAccessException e) {
            throw new DataServiceException("Exception occured at AlertDaoImpl-addAlertToQueue", e);
        } catch (Exception e) {
            throw new DataServiceException("Exception occured at AlertDaoImpl-addAlertToQueue", e);
        }
        return true;
    }
	
	
	 public void insertExpiryNotificationForThirtyDays(List<ExpiryNotificationVO> expiryNotificationList)throws DataServiceException
	    {
	    	 try
	        {
	    		 batchInsert("expiryNotificationInfoThirty.insert", expiryNotificationList);
	            
	        }
	    	 catch (DataAccessException e)
		        {
		            throw new DataServiceException("Exception thrown while inserting thirtydaysexpiryNotification", e);

		         }
	        catch (Exception e)
	        {
	            throw new DataServiceException("Exception thrown while inserting thirtydaysexpiryNotification", e);

	         }
	     }
	
	public void updateSevenDaysExpiryNotification(List<Integer> notificationIdsList) throws DataServiceException {
			update("updateNotificationTypeSeven.query",notificationIdsList);
		
	}

	public void updateZeroDaysExpiryNotification(List<Integer> notificationIdsList) throws DataServiceException {
		update("updateNotificationTypeZero.query",notificationIdsList);

	}
	public void insertExpiryNotificationForSevenDays(List<ExpiryNotificationVO> expiryNotificationList)throws DataServiceException
    {
    	 try
        {
    		 batchInsert("expiryNotificationInfoSeven.insert", expiryNotificationList);
            
        }
    	 catch (DataAccessException e)
	        {
	            throw new DataServiceException("Exception thrown while inserting sevendaysexpiryNotification ", e);

	         }
        catch (Exception e)
        {
            throw new DataServiceException("Exception thrown while inserting expiryNotification for seven days", e);

         }
     }
	public void insertExpiryNotificationForZeroDays(List<ExpiryNotificationVO> expiryNotificationList)throws DataServiceException
    {
    	 try
        {
    		 batchInsert("expiryNotificationInfoZero.insert", expiryNotificationList);
            
        }
    	 catch (DataAccessException e)
	        {
	            throw new DataServiceException("Exception thrown inserting expiryNotification for zero days ", e);

	         }
        catch (Exception e)
        {
            throw new DataServiceException("Exception thrown inserting expiryNotification for zero days ", e);

         }
     }

	public void updateAndClearExpiryNotification(List<Integer> notificationIdsList) throws DataServiceException {
		update("updateNotificationTypeThirty.query",notificationIdsList);

		
	}
	
	//R11_2
	//SL-20437
	public List<ProviderBackgroundCheckVO> getProvInfoForNotification() throws DataServiceException
    {
    	try {
			return (List<ProviderBackgroundCheckVO>) queryForList("getProvInfoNotification.query");
			
		} catch (IllegalStateException ex) {
            throw new DataServiceException("Exception while fetching provider information", ex);
		}
		catch (Exception ex) {	
            throw new DataServiceException("Exception while fetching provider information", ex);
		}
    }
	
	public void insertExpiryNotification(List<ExpiryNotificationVO> expiryNotificationList)throws DataServiceException
    {
		try{
			batchInsert("expiryNotificationInfo.insert", expiryNotificationList); 
        }
    	 catch (DataAccessException e){
	       throw new DataServiceException("Exception thrown inserting expiryNotification ", e);
    	}
        catch (Exception e){
           throw new DataServiceException("Exception thrown inserting expiryNotification ", e);
        }
     }

}
