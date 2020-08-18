package com.newco.marketplace.aop;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.contact.ContactDao;
import com.newco.marketplace.persistence.iDao.logging.ILoggingDao;
import com.newco.marketplace.persistence.iDao.so.buyer.BuyerDao;
import com.newco.marketplace.persistence.iDao.template.TemplateDao;
import com.newco.marketplace.persistence.iDao.vendor.VendorResourceDao;
import com.newco.marketplace.webservices.base.Template;

public class LoggingAdvice{
	
	private static final Logger logger = Logger.getLogger(LoggingAdvice.class.getName());
	private Template template;
	private TemplateDao templateDao;
	private VelocityEngine velocityEngine;
	private ILoggingDao loggingDao;
	private  ContactDao contactDao;
	private BuyerDao buyerDao;
	private VendorResourceDao vendorResourceDao;

	
	public void insertLog(Map<String, Object> hmArguments) throws BusinessServiceException{
		SoLoggingVo soLoggingVo = new SoLoggingVo();
	
		try{
			//Fetch the template details
			template = templateDao.getTemplateById((Integer)hmArguments.get(AOPConstants.AOP_TEMPLATE_ID));
			
			StringWriter sw = new StringWriter();
			VelocityContext vContext = new VelocityContext();
			//set the variables in Velocity Context from the HashMap
			Set<String> keys = hmArguments.keySet();
			Iterator<String> iterator = keys.iterator();
			String key = null;
			while(iterator.hasNext()){
				key = (String)iterator.next();
				vContext.put(key, hmArguments.get(key));
			}
					
			int roleType = Integer.parseInt(hmArguments.get(AOPConstants.AOP_ROLE_TYPE).toString());
			if(roleType == OrderConstants.BUYER_ROLEID || roleType == OrderConstants.SIMPLE_BUYER_ROLEID){
				vContext.put("ROLE", AOPConstants.Buyer);
			}else if (roleType == OrderConstants.PROVIDER_ROLEID){
				vContext.put("ROLE", AOPConstants.Provider);
			}
			
			getVelocityEngine().evaluate(vContext, sw, "velocity template", template.getSource());
			if (sw == null)
				throw new Exception("Could not generate the message from template!");
			
			soLoggingVo.setComment(sw.getBuffer().toString());
			
			soLoggingVo.setServiceOrderNo((String)hmArguments.get(AOPConstants.AOP_SO_ID));
			soLoggingVo.setActionId((Integer)hmArguments.get(AOPConstants.AOP_ACTION_ID));
			if(hmArguments.get(AOPConstants.AOP_ROLE_TYPE) != null) {
				soLoggingVo.setRoleId((Integer)hmArguments.get(AOPConstants.AOP_ROLE_TYPE));
			}
			if(hmArguments.get(AOPConstants.AOP_CREATED_BY_NAME) != null) {
				soLoggingVo.setCreatedByName((String)hmArguments.get(AOPConstants.AOP_CREATED_BY_NAME));
			}
			if(hmArguments.get(AOPConstants.AOP_MODIFIED_BY) != null) {
				soLoggingVo.setModifiedBy((String)hmArguments.get(AOPConstants.AOP_MODIFIED_BY));
			}
			if (hmArguments.get(AOPConstants.AOP_LOGGING_NEWVAL) != null) {
				String newVal = Integer.toString((Integer) hmArguments.get(AOPConstants.AOP_LOGGING_NEWVAL));
				soLoggingVo.setNewValue(newVal);
			}
			/*
			 * Added code to insert Entity Id.
			 * Buyer Resource Id is inserted as Entity Id. 
			 */
			if(hmArguments.get(AOPConstants.AOP_RESOURCE_ID) != null){
				soLoggingVo.setEntityId((Integer)hmArguments.get(AOPConstants.AOP_RESOURCE_ID));
			}
			
			if(hmArguments.get(AOPConstants.AOP_TEMPLATE_ID) != null){
				Integer templateID = (Integer)hmArguments.get(AOPConstants.AOP_TEMPLATE_ID);
				if(AOPConstants.AOP_RELEASE_REROUTE_SO_TEMPLATE_ID == templateID){
					soLoggingVo.setRoleId(AOPConstants.AOP_SYSTEM_ROLE_ID);
					soLoggingVo.setCreatedByName(AOPConstants.AOP_SYSTEM);
					soLoggingVo.setModifiedBy(AOPConstants.AOP_SYSTEM);
				}
			}
			if(hmArguments.get(AOPConstants.AOP_TEMPLATE_ID) != null){
				Integer templateID = (Integer)hmArguments.get(AOPConstants.AOP_TEMPLATE_ID);
				if(AOPConstants.AOP_DELETE_DRAFT_SO_TEMPLATE_ID == templateID){
					if(soLoggingVo.getCreatedByName().trim().equals(AOPConstants.AOP_SYSTEM)){ 
						soLoggingVo.setRoleId(AOPConstants.AOP_SYSTEM_ROLE_ID);
					}
				}
			}
			
			//Log the changes in database
			loggingDao.insertLog(soLoggingVo);
			
		}
		catch(Exception e){
			logger.error("LoggingAdvice-->insertLog()-->EXCEPTION-->", e);
			throw new BusinessServiceException(e);
		}
		
		
	}
	

	public TemplateDao getTemplateDao() {
		return templateDao;
	}


	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}


	public ILoggingDao getLoggingDao() {
		return loggingDao;
	}


	public void setLoggingDao(ILoggingDao loggingDao) {
		this.loggingDao = loggingDao;
	}


    public ContactDao getContactDao() {
    
        return contactDao;
    }


    public void setContactDao(ContactDao contactDao) {
    
        this.contactDao = contactDao;
    }


    public BuyerDao getBuyerDao() {
    
        return buyerDao;
    }


    public void setBuyerDao(BuyerDao buyerDao) {
    
        this.buyerDao = buyerDao;
    }


    public VendorResourceDao getVendorResourceDao() {
    
        return vendorResourceDao;
    }


    public void setVendorResourceDao(VendorResourceDao vendorResourceDao) {
    
        this.vendorResourceDao = vendorResourceDao;
    }

}
