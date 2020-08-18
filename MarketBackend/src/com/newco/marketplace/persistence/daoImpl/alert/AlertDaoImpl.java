package com.newco.marketplace.persistence.daoImpl.alert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.business.businessImpl.alert.AlertBuyerEmail;
import com.newco.marketplace.business.businessImpl.alert.AlertDisposition;
import com.newco.marketplace.business.businessImpl.alert.AlertProviderEmail;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.business.businessImpl.alert.AlertTaskUpdate;
import com.newco.marketplace.business.businessImpl.alert.BuyerCallbackNotification;
import com.newco.marketplace.business.businessImpl.vibePostAPI.BuyerCallbackNotificationStatusVo;
import com.newco.marketplace.business.businessImpl.vibePostAPI.PushAlertStatusVo;
import com.newco.marketplace.business.businessImpl.vibePostAPI.VibeAlertStatusVO;
import com.newco.marketplace.business.businessImpl.vibePostAPI.VibeSMSAlertProcessor;
import com.newco.marketplace.dao.impl.MPBaseDaoImpl;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackNotificationVO;
import com.newco.marketplace.dto.vo.serviceorder.ProviderDetail;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.persistence.iDao.alert.AlertDao;
import com.sears.os.context.ContextValue;
import com.sears.os.factory.BeanFactory;

public class AlertDaoImpl extends MPBaseDaoImpl implements AlertDao {
	private static final Logger logger = Logger.getLogger(AlertDaoImpl.class.getName());

	public boolean addAlertToQueue(AlertTask alertTask) throws DataServiceException {
        try {
            insert("alert.insert", alertTask);
        } catch (Exception e) {
            throw new DataServiceException("Exception occured at AlertDaoImpl-addAlertToQueue", e);
        }
        return true;
    }

	public boolean addAlertListToQueue(List<AlertTask> alertTaskList) throws DataServiceException
	{
		 try {
	        	batchInsert("alert.insert", alertTaskList);
	        }catch (DataAccessException e) {
	            throw new DataServiceException("Exception occured at AlertDaoImpl-addAlertToQueue", e);
	        } catch (Exception e) {
	            throw new DataServiceException("Exception occured at AlertDaoImpl-addAlertToQueue", e);
	        }
	        return true;
	}
	
	public Long addAlertTask(AlertTask alertTask) throws DataServiceException
	{
		Long alertTaskId = null;
		logger.info("AlertDaoImpl:::addAlertTask");
		 try {
			 Date start = new Date();
			 alertTaskId= (Long)insert("alert.insert.selectId", alertTask);
			 Date end = new Date();
			 logger.info("AlertDaoImpl:::addAlertTask sucess with ID: "+ alertTaskId+" in "+(end.getTime() - start.getTime()) + " ms");
	        }catch (DataAccessException e) {
	        	logger.error("Exception occured in alert.insert.selectId due to " + e.getMessage());
	            throw new DataServiceException("Exception occured at AlertDaoImpl-addAlertTask", e);
	        }
		 return alertTaskId;
	}

    public AlertDisposition getAlertDisposition(String alertName, String aopActionId) throws DataServiceException {
        AlertDisposition alertDisposition;
        Map<String, String> paramMap = new HashMap<String, String>();
		
		try {
			paramMap.put("alertName", alertName);
			paramMap.put("aopActionId", aopActionId);
			
            alertDisposition = (AlertDisposition) queryForObject("alert.disposition", paramMap);
        } catch (Exception e) {
            throw new DataServiceException("Exception occured at AlertDaoImpl-getAlertDisposition", e);
        }
        return alertDisposition;
    }

    public ProviderDetail getProviderDetailForNotes(String soId) throws DataServiceException {
        ProviderDetail providerDetail;
        try {
            providerDetail = (ProviderDetail) queryForObject("notes.provider", soId);
        } catch (Exception e) {
            throw new DataServiceException("Exception occured at AlertDaoImpl-getProviderDetailForNotes", e);
        }
        return providerDetail;
    }

    public ServiceOrderNote getLatestNoteDetail(String soId) throws DataServiceException {
        ServiceOrderNote serviceOrderNote;
        try {
            serviceOrderNote = (ServiceOrderNote) queryForObject("latest_note", soId);
        } catch (Exception e) {
            throw new DataServiceException("Exception occured at AlertDaoImpl-getLatestNoteDetail", e);
        }
        return serviceOrderNote;
    }
/**
 * 
 * 
 * 
 * WHAT is this for????
 */
    public ArrayList getAllBuyerDetail() throws DataServiceException {
        ArrayList list = null;
        try {
            list = (ArrayList) queryForList("allBuyer.query", null);
        } catch (Exception e) {
            throw new DataServiceException("Exception Occured - AlertDaoImpl- getAllBuyerDetail()", e);
        }
        return list;
    }

    public ArrayList<ProviderDetail> retrieveAllVendors() throws DataServiceException {
        try {
            return (ArrayList) queryForList("allVendors.query", null);
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.retrieveAllVendors - Exception", ex);
        }
    }

    public ArrayList retrieveAllAdmins() throws DataServiceException {
        try {
            return (ArrayList) queryForList("allAdmins.query", null);
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.retrieveAllAdmins - Exception", ex);
        }
    }
    
    /**
     * \WHAT is this for.
     * 
     * 
     * 
     * 
     * 
     */
    
    

    public ArrayList<AlertTask> retrieveAlertTasks(String priority, String alertTypeId) throws DataServiceException {
        try {
            AlertTask alertTask = new AlertTask();
            String ppp = priority;
            alertTask.setPriority(ppp);
            int typeID = new Integer(alertTypeId).intValue();
            alertTask.setAlertTypeId(typeID);
            return (ArrayList) queryForList("alert.queue", alertTask);
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.retrieveAlertTasks - Exception", ex);
        }
    }

    public ArrayList<AlertTask> retrieveAlertTypes() throws DataServiceException {
        try {
            return (ArrayList) queryForList("alert.types", null);
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.retrieveAlertTypes - Exception", ex);
        }
    }

    public void updateAlertStatus(Long alertTaskId) throws DataServiceException {
    	logger.info("AlertDaoImpl:::updateAlertStatus");
        try {
            if (alertTaskId != null) {
            	
                //AlertTaskUpdate atp = new AlertTaskUpdate();
                //atp.setAlertTaskIds(alertTaskIds);
            	logger.info("AlertDaoImpl:::updateAlertStatus:updating for alertTaskId --"+alertTaskId);

                update("alertTask.setSMSStatusFlag", alertTaskId);
            	logger.info("AlertDaoImpl:::updateAlertStatus:updating for alertTaskId --"+alertTaskId+"::COMPLETED");

            }
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.updateAlertStatus - Exception", ex);
        }
    }
    
    public void updateAlertStatusPushNotification(Long alertTaskId) throws DataServiceException {
    	logger.info("AlertDaoImpl:::updateAlertStatusPushNotification");

        try {
            if (alertTaskId != null) {
            	logger.info("AlertDaoImpl:::updateAlertStatusPushNotification:updating for alertTaskId --"+alertTaskId);
                update("alertTask.setPushNotificationStatusFlag", alertTaskId);
            }
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.updateAlertStatusPushNotification - Exception", ex);
        }
    }  

    public ArrayList<AlertProviderEmail> getProviderAndResourceEmails(String soId, Integer tierId) throws DataServiceException {
        try {
            AlertProviderEmail alertProviderEmail = new AlertProviderEmail();
            alertProviderEmail.setSoId(soId);
            alertProviderEmail.setTierId(tierId);
            return (ArrayList) queryForList("alert.getProviderEmails", alertProviderEmail);
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.getProviderAndResourceEmails - Exception", ex);
        }
    }
    public ArrayList<AlertProviderEmail> getProviderAdminEmails(String soId) throws DataServiceException {
        try {
            AlertProviderEmail alertProviderEmail = new AlertProviderEmail();
            alertProviderEmail.setSoId(soId);
            return (ArrayList) queryForList("alert.getProviderAdminEmails", alertProviderEmail);
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.getProviderAdminEmails - Exception", ex);
        }
    }
    
    public ArrayList<AlertProviderEmail> getProviderAdminEmailForRejectedSO(String soId) throws DataServiceException {
        try {           
        	AlertProviderEmail alertProviderEmail = new AlertProviderEmail();
            alertProviderEmail.setSoId(soId);
            return (ArrayList) queryForList("alert.getProviderAdminEmail.RejectedSO", alertProviderEmail);
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.getProviderAdminEmailForRejectedSO - Exception", ex);
        }
    }
    
    public ArrayList<AlertProviderEmail> getProviderAdminEmailsForAddNotesAccepted(String soId) throws DataServiceException {
        try {
            AlertProviderEmail alertProviderEmail = new AlertProviderEmail();
            alertProviderEmail.setSoId(soId);
            return (ArrayList) queryForList("alert.getProviderAdminEmailsAccepted", alertProviderEmail);
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.getProviderAdminEmails - Exception", ex);
        }
    }

    public ArrayList<AlertProviderEmail> getProviderAdminEmailsForReleasedSO(String soId) throws DataServiceException {
        try {
            AlertProviderEmail alertProviderEmail = new AlertProviderEmail();
            alertProviderEmail.setSoId(soId);
            return (ArrayList) queryForList("alert.getProviderAdminEmailsForReleasedSO", alertProviderEmail);
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.getProviderAdminEmails - Exception", ex);
        }
    }
    
    public ArrayList<AlertProviderEmail> getProviderAdminEmailsForAddNotes(String soId) throws DataServiceException {
        try {
            AlertProviderEmail alertProviderEmail = new AlertProviderEmail();
            alertProviderEmail.setSoId(soId);
            return (ArrayList) queryForList("alert.getProviderAdminEmails", alertProviderEmail);
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.getProviderAdminEmails - Exception", ex);
        }
    }

    public AlertBuyerEmail getBuyerAdminEmailAddr(String soId) throws DataServiceException {
    	AlertBuyerEmail email = null;
        try {
            email = (AlertBuyerEmail) queryForObject("alert.getBuyerAdminEmailAddr", soId);
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.getBuyerAdminEmailAddr - Exception", ex);
        }
        return email;
    }

    public AlertBuyerEmail getSOCreatorEmailAddr(String soId) throws DataServiceException {
    	AlertBuyerEmail email = null;
        try {
            email = (AlertBuyerEmail) queryForObject("alert.getSOCreatorEmailAddr", soId);
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.getProviderAndResourceEmails - Exception", ex);
        }
        return email;
    }

    public ArrayList<AlertProviderEmail> getAllRoutedProvidersExceptAccepted(String soId) throws DataServiceException {
        ArrayList<AlertProviderEmail> proEmails = null;
        try {
            proEmails = (ArrayList) queryForList("alert.getAllRoutedProvidersExceptAccepted", soId);
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.getAllRoutedProvidersExceptAccepted - Exception", ex);
        }
        return proEmails;
    }
    public String getLoggedInProvider(Integer vid) throws DataServiceException {
        String loggedInProvider = null;
        try {
        	loggedInProvider = (String) queryForObject("alert.logged_in_provider", vid);
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.getLoggedInProvider - Exception", ex);
        }
        return loggedInProvider;
    }
    
    public boolean resendWelcomeMail(String userName, String emailAddress) {       
    	if (StringUtils.isBlank(userName)||
    			StringUtils.isBlank(emailAddress)) {
    		System.out.println("Either username or email is null:" + 
    				userName + "," + emailAddress);
    		return false;
    	}
    	
        try {
        	Map<String, String> map = new HashMap<String, String>();
        	map.put("userName", userName);
        	map.put("email", emailAddress);
        	getSqlMapClient().update("alert.resend_registration_email", map);
        	return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }        
    }
    /*
	 * This method retrieves the Provider Admin Email
	 * parameters String soId
	 * return String
	 * throws DataServiceException
	 */
    public String getProvAdminEmailAddrForChangeOfScopeSO(String soId) throws DataServiceException {
    	String strAlertAddr = "";
        try {
            strAlertAddr = (String)queryForObject("alert.getProviderAdminEmailForChangeOfScopeSO", soId);            
        } catch (Exception ex) {
        	logger.error("determineProvAdminEmailAddrForChangeOfScopeSO", ex);
            throw new DataServiceException("AlertDaoImpl.getProvAdminEmailAddrForChangeOfScopeSO - Exception", ex);
        }
        return strAlertAddr;
    }
    
	public void optOutSMS(String smsNo)throws DataServiceException{
		Integer id = null;
        try {
            id = (Integer)getSqlMapClient().update("update_contact_sms.update", smsNo);
          
        }
        catch (Exception ex) {
             logger.error("General Exception @AlertDaoImpl.optOutSMS() due to"+ex.getMessage());
		     throw new DataServiceException("General Exception @AlertDaoImpl.optOutSMS() due to "+ex.getMessage());
        }	
	}
	//gets the email of the user with corresponding sms no
	public ArrayList<String> getEmailFromSmsNo(String smsNo)throws DataServiceException{
		ArrayList<String> email= new ArrayList();
		try {
			email=(ArrayList)getSqlMapClient().queryForList("emailFromSmsNo.query",smsNo);
			

		} catch (SQLException ex) {
			 logger.error("SQLException @AlertDaoImpl.getEmailFromSmsNo() due to"+ex.getMessage());
		     throw new DataServiceException("General Exception @AlertDaoImpl.getEmailFromSmsNo() due to "+ex.getMessage());
			
		} catch (Exception ex) {
			 logger.error("General Exception @AlertDaoImpl.getEmailFromSmsNo() due to"+ex.getMessage());
		     throw new DataServiceException("General Exception @AlertDaoImpl.getEmailFromSmsNo() due to "+ex.getMessage());
		}
		return email;
		
	}
    

    public static void main(String h[]) {
        ContextValue.setContextFile("resources/spring/applicationContext.xml");
        System.out.println("loaded applicationContext.xml");
        AlertDaoImpl alertDaoImpl = (AlertDaoImpl) BeanFactory.getBean("alertDao");
        AlertDaoImpl a = new AlertDaoImpl();
        AlertTask at = new AlertTask();
        at.setAlertedTimestamp(new Date());
        at.setAlertTaskId(12343245);
        at.setAlertTypeId(1);
        at.setCreatedDate(new Date());
        at.setModifiedBy("ServiceL");
        at.setModifiedDate(new Date());
        at.setTemplateId(1);
        try {
            alertDaoImpl.addAlertToQueue(at);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //R16_1: SL-18979 retrieveAlertTasksForVibe
  	public ArrayList<AlertTask> retrieveAlertTasksForVibe(int noOfRecords) throws DataServiceException{
  		ArrayList<AlertTask> alertTasksList = null;
  		try {
			alertTasksList = (ArrayList<AlertTask>) getSqlMapClient().queryForList("alerttasksForVibe.query",noOfRecords);
		}catch (SQLException ex) {
			 logger.error("SQLException @AlertDaoImpl.retrieveAlertTasksForVibe() due to"+ex.getMessage());
		     throw new DataServiceException("General Exception @AlertDaoImpl.retrieveAlertTasksForVibe() due to "+ex);
			
		}catch (Exception ex) {
			 logger.error("General Exception @AlertDaoImpl.retrieveAlertTasksForVibe() due to"+ex.getMessage());
		     throw new DataServiceException("General Exception @AlertDaoImpl.retrieveAlertTasksForVibe() due to "+ex);
		}
  		return alertTasksList;
  		
  	}
  	
  	 	
	// Retrieve notifications for buyer callback
	public ArrayList<BuyerCallbackNotification> retrieveBuyerCallbackNotification(
			int noOfRecords) throws DataServiceException {
		ArrayList<BuyerCallbackNotification> notificationList = null;
		try {
			logger.info("AlertDaoImpl::retrieveBuyerCallbackNotification");
			notificationList = (ArrayList<BuyerCallbackNotification>) getSqlMapClient()
					.queryForList("buyerCallbackNotifications.query",
							noOfRecords);
			logger.info("AlertDaoImpl::retrieveBuyerCallbackNotification ::::::::::alertTaksList--> "
					+ notificationList);

		} catch (SQLException ex) {
			logger.error("SQLException @AlertDaoImpl.retrieveBuyerCallbackNotification() due to"
					+ ex.getMessage());
			throw new DataServiceException(
					"General Exception @AlertDaoImpl.retrieveBuyerCallbackNotification() due to "
							+ ex);

		} catch (Exception ex) {
			logger.error("General Exception @AlertDaoImpl.retrieveBuyerCallbackNotification() due to"
					+ ex.getMessage());
			throw new DataServiceException(
					"General Exception @AlertDaoImpl.retrieveBuyerCallbackNotification() due to "
							+ ex);
		}
		return notificationList;

	}
  	
  	//Retrieve Tasks for Push Notifications
  	public ArrayList<AlertTask> retrieveAlertTasksForPushNotification(int noOfRecords) throws DataServiceException{
  		ArrayList<AlertTask> alertTasksList = null;
  		try {
  	        logger.info("AlertDaoImpl::retrieveAlertTasksForPushNotification");
			alertTasksList = (ArrayList<AlertTask>) getSqlMapClient().queryForList("alertTasksForPushNotification.query",noOfRecords);
  	        logger.info("AlertDaoImpl::retrieveAlertTasksForPushNotification ::::::::::alertTaksList--> "+alertTasksList);

		}catch (SQLException ex) {
			 logger.error("SQLException @AlertDaoImpl.retrieveAlertTasksForPushNotifications() due to"+ex.getMessage());
		     throw new DataServiceException("General Exception @AlertDaoImpl.retrieveAlertTasksForPushNotifications() due to "+ex);
			
		}catch (Exception ex) {
			 logger.error("General Exception @AlertDaoImpl.retrieveAlertTasksForPushNotifications() due to"+ex.getMessage());
		     throw new DataServiceException("General Exception @AlertDaoImpl.retrieveAlertTasksForPushNotifications() due to "+ex);
		}
  		return alertTasksList;
  		
  	}

	//Buyer callback notification changes
	public Map <String, String> getBuyerCallbackNotificationConstants() throws DataServiceException{
		List<String> apiDetailsNeeded = new ArrayList<String>();
        HashMap <String, String> apiDetails = new HashMap <String, String>();
        
        apiDetailsNeeded.add(AlertConstants.CALLACK_SERVICE_URL);
        apiDetailsNeeded.add(AlertConstants.CALLBACK_NITIFICATION_QUERY_LIMIT);
        apiDetailsNeeded.add(AlertConstants.SL_WRAPPER_API_TOKEN);
        try{
                apiDetails =(HashMap <String, String>) getSqlMapClient().queryForMap("fetchAddParticipantApiDetails.query", apiDetailsNeeded, "app_constant_key","app_constant_value");
        }
        catch(Exception e){
                logger.error("Exception occurred in getNotificationConstants: "+e.getMessage());
                throw new DataServiceException("Exception occurred in getNotificationConstants: "+e.getMessage(),e);
        }
        return apiDetails;
	}
	
	//Push notification changes
	public Map <String, String> getNotificationConstants() throws DataServiceException{
		List<String> apiDetailsNeeded = new ArrayList<String>();
        HashMap <String, String> apiDetails = new HashMap <String, String>();
        
        apiDetailsNeeded.add(AlertConstants.PUSH_NOTIFICATION_SERVICE_URL);
        apiDetailsNeeded.add(AlertConstants.NOTIFICATION_QUERY_LIMIT);
        try{
                apiDetails =(HashMap <String, String>) getSqlMapClient().queryForMap("fetchAddParticipantApiDetails.query", apiDetailsNeeded, "app_constant_key","app_constant_value");
        }
        catch(Exception e){
                logger.error("Exception occurred in getNotificationConstants: "+e.getMessage());
                throw new DataServiceException("Exception occurred in getNotificationConstants: "+e.getMessage(),e);
        }
        return apiDetails;
	}
	
	public String getTemplateData(int templateId) throws DataServiceException{
		String messageBody  =null;
		try{
			messageBody = (String) getSqlMapClient().queryForObject(
					"fetchTemplate.query", templateId);
		} 
		catch(Exception e){
            logger.error("Exception occurred in getTemplateData: "+e.getMessage());
            throw new DataServiceException("Exception occurred in getTemplateData: "+e.getMessage(),e);
    }
    return messageBody;
	}
  	
	//R16_1: SL-18979: Fetching constant values from application constants
	public Map <String, String> getVibeConstants() throws DataServiceException{
		List<String> apiDetailsNeeded = new ArrayList<String>();
        HashMap <String, String> apiDetails = new HashMap <String, String>();
        
        apiDetailsNeeded.add(AlertConstants.VIBES_COMPANYID);
        apiDetailsNeeded.add(AlertConstants.VIBES_CREATE_EVENT_API_URL);
        apiDetailsNeeded.add(AlertConstants.VIBES_EVENT_TYPE);
        apiDetailsNeeded.add(AlertConstants.VIBES_HEADER);
        apiDetailsNeeded.add(AlertConstants.VIBES_DEEP_LINK_URL);
        apiDetailsNeeded.add(AlertConstants.VIBES_QUERY_LIMIT);
        apiDetailsNeeded.add(AlertConstants.VIBES_SID);
        try{
                apiDetails =(HashMap <String, String>) getSqlMapClient().queryForMap("fetchAddParticipantApiDetails.query", apiDetailsNeeded, "app_constant_key","app_constant_value");
        }
        catch(Exception e){
                logger.error("Exception occurred in fetchAddParticipantApiDetails: "+e.getMessage());
                throw new DataServiceException("Exception occurred in fetchAddParticipantApiDetails: "+e.getMessage(),e);
        }
        return apiDetails;
	}
	// R16_1 SL-18979 insert into sms_alert_tatus
	public void insertSMSAlertStatus(List<VibeAlertStatusVO> smsAlertStatusVOList) throws DataServiceException{
		try {
			logger.info("AlertDaoImpl:::insertSMSAlertStatus");
			batchInsert("sms.alert.status.insert", smsAlertStatusVOList);
			logger.info("AlertDaoImpl:::insertSMSAlertStatus::COMPLETED");
		} catch (Exception e) {
			throw new DataServiceException("Exception occured at AlertDaoImpl-insertSMSAlertStatus", e);
		}
	}

	public void insertPushAlertStatus(List<PushAlertStatusVo> pushAlertStatusVOList) throws DataServiceException{
		try {
			logger.info("AlertDaoImpl:::insertPushAlertStatus");
			batchInsert("push.alert.status.insert", pushAlertStatusVOList);
			logger.info("AlertDaoImpl:::insertPushAlertStatus::COMPLETED");
		} catch (Exception e) {
			throw new DataServiceException("Exception occured at AlertDaoImpl-insertPushAlertStatus", e);
		}
	}

    public void updateAlertStatus(List<Long> alertTaskIds) throws DataServiceException {
        try {
            if (alertTaskIds != null && !alertTaskIds.isEmpty()) {
                AlertTaskUpdate atp = new AlertTaskUpdate();
                atp.setAlertTaskIds(alertTaskIds);
                update("alertTask.setStatusFlag", atp);
            }
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.updateAlertStatus - Exception", ex);
        }
    }
    
	public void updateCallbackNotificationStatus(
			BuyerCallbackNotificationStatusVo notificationVo)
			throws DataServiceException {
		try {
			update("callbackNotification.setStatus", notificationVo);

		} catch (Exception ex) {
			throw new DataServiceException(
					"AlertDaoImpl.updateCallbackNotificationStatus - Exception", ex);
		}
	}

}