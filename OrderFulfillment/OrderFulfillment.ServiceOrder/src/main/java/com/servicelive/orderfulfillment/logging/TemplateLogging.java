package com.servicelive.orderfulfillment.logging;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.tools.generic.NumberTool;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOLogging;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

public class TemplateLogging extends EnabledLogging {

	private int actionId;
	private String newValueProperty;
	private String oldValueProperty;
	
	private Integer roleId = 6;
	private String userName = "system";
	private String fullName = "System";
	private Long entityId = 0L;
	
	private ITemplateResolver templateResolver;
	
	public TemplateLogging() {
		super();
	}

	protected SOLogging createSOLogging(ServiceOrder so,  HashMap<String,Object> dataMap) throws ServiceOrderException {

		// resolve template for the message
		// this task is delegated to the resolver configured for us 
		String template = getMessageTemplate();
		if( template == null ) {
			throw new ServiceOrderException("Failed to obtain logging template");
		}
		
		// start preparing data for so-logging
		VelocityContext vContext = new VelocityContext();
		vContext.put("number", new NumberTool());

		String oldValue = evaluateProperty(so, oldValueProperty);
		
		// Identification is passed in via data map
		Identification id = (Identification)dataMap.get("id");
		Integer _roleId = id!=null&&id.getRoleId()!=null?id.getRoleId():roleId;
		Long _entityId = id!=null&&id.getEntityId()!=null?id.getEntityId():entityId;
		String _userName = id!=null&&id.getUsername()!=null?id.getUsername():userName;
		String _fullName = id!=null&&id.getFullname()!=null?id.getFullname():fullName;
		vContext.put("ROLE_ID", _roleId);
		vContext.put("ENTITY_ID", _entityId);
		vContext.put("USER_NAME", _userName);
		vContext.put("FULL_NAME", _fullName);
		
		// Request info
		SOElement request = (SOElement)dataMap.get("request");
		String newValue="";
		if (request!=null) {
			newValue = evaluateProperty(request, newValueProperty);
		}

		// add all keys from the dataMap
		for (String key: dataMap.keySet()) {
			vContext.put(key, dataMap.get(key));
		}

		// SO info
		vContext.put("so",so);

		// can merge template now
		String message = evaluateVelocityTemplate(template, vContext);
		// create log object
		SOLogging rval = new SOLogging();
		rval.setServiceOrder(so);
		if(this.actionId==253 || this.actionId==254 || this.actionId==255 || this.actionId==256 || this.actionId==276 ||
				277 == this.actionId || 279== this.actionId || 280== this.actionId || 283== this.actionId){
			rval.setEntityId(0L);
			rval.setModifiedBy("System");
			rval.setCreatedByName("System");
			rval.setRoleId(6);	
		}else{
		    rval.setEntityId(_entityId);
		    rval.setModifiedBy(_userName);
		    rval.setCreatedByName(_fullName);
		    rval.setRoleId(_roleId);
		}
		
		if(this.actionId==253 || this.actionId==32){
			rval.setEntityId(0L);
			rval.setModifiedBy("System");
			rval.setCreatedByName("System");
			rval.setRoleId(6);	
		}
		if(this.actionId==270 ||this.actionId==271){
			rval.setRoleId(7);	
		}
		
		//Added for Cancellation API (v1.2 sl:18041)
		String cancellation_API = (String) dataMap.get(OrderfulfillmentConstants.PVKEY_CANCELLATION_API_CODE);
		if(OrderfulfillmentConstants.CANCELLATION_API.equalsIgnoreCase(cancellation_API)){
			if(25==this.actionId || 23==this.actionId||258==this.actionId||259==this.actionId||260==this.actionId){
				//User Type : Buyer API
				rval.setRoleId(7);
			}
		}
		
		//SL 15642 Start Setting the information for  order history
		if(this.actionId==272){
			rval.setModifiedBy("System");
			rval.setCreatedByName("System");
			java.util.Date today = new java.util.Date();
			Date now = new Date(today.getTime());
			rval.setCreatedDate(now);
		}
		//SL 15642 End Setting the information for  order history
		rval.setActionId(this.actionId);
		rval.setOldValue(oldValue);
		rval.setNewValue(newValue);
		rval.setChgComment(message);
		return rval;
	}

		// PROVIDER_COMMENT release in problem, release in active, release in accepted
		// ROLE, RESPONSE_TO_RESCHEDULE reject/accept resched req
		// COMMENT resch so comment
		// SUBSTATUS_DESC update sub-status
		// ROLE, OVERALL_SCORE, SURVEY_RATING_COMMENT save response
		// ACTION_NAME update draft
		// CANCEL_AMT cancel in active
		// ROLE cancel resch req
		// RESPONSE_TO_RESCHEDULE, ROLE  respond to resch
		// SPEND_LIMIT_TO_LABOR, SCHEDULE_FROM, SCHEDULE_TO, SCHEDULE_START_TIME, SCHEDULE_END_TIME
		//    create cond offer
		// SO_RESOLUTION complete so
		// COMMENT report resolution
		// ROLE request resch
		// COMMENT report problem
		// PROVIDERFIRSTNAME, PROVIDERLASTNAME - accept
		// SPEND_LIMIT_TO_LABOR, SPEND_LIMIT_TO_PART, TOTAL_SPEND_LIMIT - update spend limit
		// COMMENT - cancel in accepted
		// LABOR_FINAL_PRICE, PARTS_FINAL_PRICE, FINAL_PRICE - close

	public String getMessageTemplate() throws ServiceOrderException {
		return this.templateResolver.getTemplate();
	}	

	private String evaluateVelocityTemplate(String template, VelocityContext vContext) throws ServiceOrderException {
		StringWriter sw = new StringWriter();
		try {
			VelocityEngine velocityEngine = new VelocityEngine();
			velocityEngine.evaluate(vContext, sw, "velocity template", template);
		} catch (ParseErrorException e) {
			failVelocityTemplateEvaluation(template, e);
		} catch (MethodInvocationException e) {
			failVelocityTemplateEvaluation(template, e);
		} catch (ResourceNotFoundException e) {
			failVelocityTemplateEvaluation(template, e);
		} catch (IOException e) {
			failVelocityTemplateEvaluation(template, e);
		}
		return sw.toString();
	}
	
	private String evaluateProperty( Object o, String propertyName ) throws ServiceOrderException {
		String rval = "";
		if( !StringUtils.isBlank(propertyName) ) {
			try {
				Object propertyValue = PropertyUtils.getProperty(o, propertyName);
				if( propertyValue != null ) {
					rval = ConvertUtils.convert(propertyValue);
				}
			} catch (IllegalAccessException e) {
				failPropertyEvaluation(o, propertyName, e);
			} catch (InvocationTargetException e) {
				failPropertyEvaluation(o, propertyName, e);
			} catch (NoSuchMethodException e) {
				failPropertyEvaluation(o, propertyName, e);
			}
		}
		return rval;
	}
	
	private void failPropertyEvaluation( Object o, String propertyName, Exception e ) throws ServiceOrderException {
		throw new ServiceOrderException("Unable to evaluate property " + o.getClass().getName() + "." + propertyName,e);
	}

	private void failVelocityTemplateEvaluation(String template, Exception e)
			throws ServiceOrderException {
		throw new ServiceOrderException("Unable to evaluate velocity template " + template,e);
	}
	
	public void setNewValueProperty(String newValueProperty) {
		this.newValueProperty = newValueProperty;
	}
	
	public void setOldValueProperty(String oldValueProperty) {
		this.oldValueProperty = oldValueProperty;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	public void setTemplateResolver(ITemplateResolver templateResolver) {
		this.templateResolver = templateResolver;
	}
	
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
}