package com.newco.batch.leadManagement;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.newco.batch.ABatchProcess;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.business.iBusiness.leadsmanagement.ILeadProcessingBO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadReminderVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.NewServiceConstants;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.dto.vo.DocumentVO;

public class LeadReminderProcessor extends ABatchProcess {

	private static final Logger logger = Logger
			.getLogger(LeadReminderProcessor.class);

	private ILeadProcessingBO leadProcessingBO;
	private String baseUrl;
	private String docPath;
	@Override
	public int execute(){
		try {
			logger.info("Entered in execute() method of LeadReminderProcessor");
			process();
			logger.info("Leaving execute() method of LeadReminderProcessor");
		} catch (Exception ex) {
			logger.error("Unexpected exception occurred while sending lead Reminder notifications!", ex);
		}
		return 0;
	}

	public void process() {
		logger.info("Entered process()method of LeadReminderProcessor");
		Date today = Calendar.getInstance().getTime(); 
		Date tomorrow = DateUtils.addDaysToDate(today, 1);	
		String serviceDate=DateUtils.getFormatedDate(tomorrow,"yyyy-MM-dd");
		logger.info("tomorrow in :" + serviceDate);
		List<LeadReminderVO> reminderVOs = null;
		try {
			logger.info("Getting details for reminder mail");
			reminderVOs = leadProcessingBO.getDetailsForRemindMail(serviceDate);			
			if (null != reminderVOs && reminderVOs.size() > 0) {
				logger.info("If reminderdetails is not empty,Size:"+reminderVOs.size());
				for (LeadReminderVO e : reminderVOs) {
					if (null != e.getWorkDate()) {
					logger.info("service date:"+e.getWorkDate());				
								
			     		if(StringUtils.isBlank(e.getProviderMobileNo())){
							e.setProviderMobileNo(e.getProviderPhoneNo());
						}
						if(StringUtils.isBlank(e.getReview())){
							e.setReview("He has done a good work");
						}
						
						sendEmail(e);
					}
					
					}
				}
			

		} catch (BusinessServiceException e) {
			logger.info("Exception in Processs() method of LeadReminderProcessor "+ e.getMessage());			
		}

	}
	private void sendEmail(LeadReminderVO reminderVO) throws BusinessServiceException{
		// Send Notification
		logger.info("Entering in sendMail of LeadReminderProcessor ");
		AlertTask alertTask = new AlertTask();	
		Map<String, Object> alertMap = new HashMap<String, Object>();
		alertMap.put(NewServiceConstants.LEAD_FIRST_NAME,reminderVO.getCustomerName());
		String custName="";
		custName=reminderVO.getCustomerName();
		if(null!=reminderVO.getCustLastName()&&StringUtils.isNotBlank(reminderVO.getCustLastName())){
		alertMap.put(NewServiceConstants.CUSTOMER_NAME,custName+" "+reminderVO.getCustLastName());	
		}
		else{
		alertMap.put(NewServiceConstants.CUSTOMER_NAME,custName);		
		}
		alertMap.put(NewServiceConstants.FIRM_OWNER,reminderVO.getFirmOwner());					
		alertMap.put(NewServiceConstants.CITY,reminderVO.getServiceCity());				
		alertMap.put(NewServiceConstants.ZIP,reminderVO.getServiceZip());
		alertMap.put(NewServiceConstants.STATE,reminderVO.getServiceState());
		alertMap.put(NewServiceConstants.PROJECT_TYPE,reminderVO.getProjectType());
	   	alertMap.put(NewServiceConstants.PROJECT_DESCRIPTION,reminderVO.getDescription());
		alertMap.put(NewServiceConstants.ADDITIONAL_PROJECT_DESCRIPTION, reminderVO.getAdditionalProjects());		
		alertMap.put(NewServiceConstants.LEAD_REFERENCE,reminderVO.getSlLeadId());
		alertMap.put(NewServiceConstants.FIRM_EMAIL,reminderVO.getFirmEmail());	
		String serviceDate="";
		serviceDate=leadProcessingBO.getFormattedDateAsString(reminderVO.getWorkDate());
		alertMap.put(NewServiceConstants.SERVICE_DATE,serviceDate);
		String serviceStartTime="";
		serviceStartTime=leadProcessingBO.getFormattedTime(reminderVO.getServiceStartTime());	    
	    alertMap.put(NewServiceConstants.SERVICE_START_TIME,serviceStartTime);
	    String serviceEndTime="";
	    serviceEndTime=leadProcessingBO.getFormattedTime(reminderVO.getServiceEndTime());
	    alertMap.put(NewServiceConstants.SERVICE_END_TIME,serviceEndTime);
	    alertMap.put(NewServiceConstants.FIRM_PHONE_NO,reminderVO.getFirmPhoneNo());
	    alertMap.put(NewServiceConstants.RESOURCE_MOBILE_NO, reminderVO.getProviderMobileNo());
	    alertMap.put(NewServiceConstants.RESOURCE_PHONE_NO, reminderVO.getProviderPhoneNo());
	    alertMap.put(NewServiceConstants.RESOURCE_NAME, reminderVO.getProviderName());
	    String imageUrl=getImageUrl(reminderVO.getResourceId());
	    logger.info("imageUrl:"+imageUrl);
	    if(imageUrl.equalsIgnoreCase("NO"))
	    {
	    alertMap.put(NewServiceConstants.IMAGE,"N");	
	    }
	    else {
	    alertMap.put(NewServiceConstants.IMAGE,"Y");
	    alertMap.put("IMAGE_URL",imageUrl);
	    	 
	    }    
	    if(StringUtils.isNotBlank(reminderVO.getSWYRID())){
	    	alertMap.put(NewServiceConstants.SWYR_MEMBERSHIP_ID,reminderVO.getSWYRID());
		   	}
		else
			{
			alertMap.put(NewServiceConstants.SWYR_MEMBERSHIP_ID,"Not Available");
			}
	    if(null!=reminderVO.getSkill() &&reminderVO.getSkill().equalsIgnoreCase("REPAIR")){
			alertMap.put(NewServiceConstants.SKILL,"Repair");
	    }else if(null!=reminderVO.getSkill() &&reminderVO.getSkill().equalsIgnoreCase("DELIVERY")){
			alertMap.put(NewServiceConstants.SKILL,"Delivery");
	    }else if(null!=reminderVO.getSkill()&&reminderVO.getSkill().equalsIgnoreCase("INSTALL")){
			alertMap.put(NewServiceConstants.SKILL,"Install");
	    }else{
	    	 alertMap.put(NewServiceConstants.SKILL,reminderVO.getSkill());
	    }	   
	    alertMap.put(NewServiceConstants.SERVICE,reminderVO.getService());
	    alertMap.put(NewServiceConstants.RESOURCE_LAST_NAME,reminderVO.getProviderLastName());
	    alertMap.put(NewServiceConstants.FIRM_NAME,reminderVO.getFirmName());
	    alertMap.put(NewServiceConstants.RESOURCE_CITY,reminderVO.getProviderCity());
	    alertMap.put(NewServiceConstants.RESOURCE_STATE,reminderVO.getProviderState());
	    alertMap.put(NewServiceConstants.RESOURCE_EMAIL,reminderVO.getProviderEmail());	    
	    String leadCategory=reminderVO.getLeadCategory();
	    		if(StringUtils.equals(leadCategory,"Electrical")){
	    			leadCategory=NewServiceConstants.ELECTRICIAN	;
	    		}
	    		else if(StringUtils.equals(leadCategory,"Plumbing")){
	    			leadCategory=NewServiceConstants.PLUMBER;
	    		}
	    alertMap.put(NewServiceConstants.LEAD_CATEGORY,leadCategory);
	    String address=reminderVO.getStreet1();
		String street2=reminderVO.getStreet2();
		if(StringUtils.isNotBlank(street2))
			    {
				address=address+","+street2;
				}
		alertMap.put(NewServiceConstants.ADDRESS,address);	
		String templateInputValue = leadProcessingBO.createKeyValueStringFromMap(alertMap);
		alertTask.setTemplateInputValue(templateInputValue);
		alertTask.setTemplateId(NewServiceConstants.TEMPLATE_SEND_REMINDER_MAIL_CUSTOMER);
		alertTask.setAlertTo(reminderVO.getCustomerEmail());
	    
		try{
			logger.info("Sending mail details to lead processingBO");
			leadProcessingBO.sendToDestination(alertTask);
		}
		catch (BusinessServiceException e) {
			logger.info("Exception in sendMail.updateAlertTask due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		
	}
	
	private String getImageUrl(String resourceId) throws BusinessServiceException{

		// link="http://157.241.130.43:1780/sl_image/2014/1/1/40/18107040_joe%20scharick.jpg";
		logger.info("in getImage URl method");
		String mainUrl =baseUrl;
		logger.info("baseUrl"+baseUrl);
		String pathReplace = docPath;
		logger.info("docPath:"+docPath);
		DocumentVO documentVO = new DocumentVO();
		try {
			
			documentVO = leadProcessingBO.getProviderImageDocument(resourceId);
			
			if (null != documentVO
					&& StringUtils.isNotBlank(documentVO.getDocPath())) {
				logger.info("after getting doc"+documentVO.getDocPath());
				/*if (StringUtils.equals(documentVO.getFormat(), "image/bmp")
						|| StringUtils.equals(documentVO.getFormat(),
								"image/gif")
						|| StringUtils.equals(documentVO.getFormat(),
								"image/jpeg")
						|| StringUtils.equals(documentVO.getFormat(),
								"image/jpg")
						|| StringUtils.equals(documentVO.getFormat(),
								"image/pjpeg")
						|| StringUtils.equals(documentVO.getFormat(),
								"image/tiff")) {*/

					String dataPath = documentVO.getDocPath();
					
					if (dataPath.contains(pathReplace)) {
						logger.info("Inside replace path");
						mainUrl = dataPath.replace(pathReplace, mainUrl);
						logger.info("after replace path"+mainUrl);
					}
					else
					{
						mainUrl="NO";
					}
				/*} else {
					mainUrl="NO";
				}*/
			}
			else {
				mainUrl="NO";
			}
		} catch (BusinessServiceException e) {
			logger.info("Exception in sendMail.updateAlertTask due to "
					+ e.getMessage());
			mainUrl="NO";
			return mainUrl;
		}
		return mainUrl;
	}
	@Override
	public void setArgs(String[] args) {
		// NOOP
		
	}

	public ILeadProcessingBO getLeadProcessingBO() {
		return leadProcessingBO;
	}
	public void setLeadProcessingBO(ILeadProcessingBO leadProcessingBO) {
		this.leadProcessingBO = leadProcessingBO;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

}
