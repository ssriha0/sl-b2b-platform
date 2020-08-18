package com.newco.marketplace.business.businessImpl.audit.email;


import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;

import com.newco.marketplace.aop.AOPHashMap;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.business.iBusiness.audit.email.IAuditEmailBO;
import com.newco.marketplace.business.iBusiness.provider.IEmailTemplateBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.exception.DBException;
//import com.newco.marketplace.exception.core.AuditException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.interfaces.AuditStatesInterface;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.daoImpl.alert.AlertDaoImpl;
import com.newco.marketplace.persistence.daoImpl.template.TemplateDaoImpl;
import com.newco.marketplace.persistence.iDao.audit.IAuditEmailDao;
import com.newco.marketplace.persistence.iDao.provider.ITemplateDao;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.AchConstants;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.vo.provider.AuditEmailVo;
import com.newco.marketplace.vo.provider.AuditTemplateMappingVo;
import com.newco.marketplace.webservices.base.Template;

public class AuditEmailBOImpl implements IAuditEmailBO,AuditStatesInterface{

	private static final Logger logger = Logger.getLogger(AuditEmailBOImpl.class.getName());  
    private VelocityEngine velocityEngine = null;
    private IAuditEmailDao auditEmailDao = null;
    private JavaMailSender mailSender = null;
    private String logoPath = null;
    private ITemplateDao templateDao = null;
    private IEmailTemplateBO emailTemplateBean = null;
    private HashMap<Integer, AuditTemplateMappingVo> auditEmailTemplateMappings = null;
    ClassPathResource emailImgPath = new ClassPathResource("/images/icon_logo.gif");
    public static final String AUDIT_ERROR_EMAIL_TO_ADDRESS = 
    	PropertiesUtils.getPropertyValue(Constants.EMAIL_ADDRESSES.AUDIT_ERROR_EMAIL_TO_ADDRESS);
    public static final String AUDIT_ERROR_EMAIL_FROM_ADDRESS = 
    	PropertiesUtils.getPropertyValue(Constants.EMAIL_ADDRESSES.AUDIT_ERROR_EMAIL_FROM_ADDRESS);
    private static HashMap<Integer, Template> templateCache;
    private TemplateDaoImpl templateDaoImpl;
    private AlertDaoImpl alertDao;
    
    public AuditEmailBOImpl() {
    	if (templateCache == null)
    	  templateCache = new HashMap<Integer, Template>();
    }
    // expose business methods which will be used in the
    public void sendAuditEmail(String workflowEntity,  AuditVO auditVo) 
    	throws AuditException
    {
        logger.info("******************************* Entered audit Email \n"
                        + auditVo);
        AuditTemplateMappingVo templateMapping = getAuditEmailTemplateMappings().get(
        		auditVo.getWfStatusId());
        if (templateMapping != null) {
            AuditEmailVo auditEmailVo = new AuditEmailVo();
            auditEmailVo.setTargetStateId(templateMapping.getTargetStateId());
            auditEmailVo.setTargetStateName(templateMapping.getTargetStateName());
            auditEmailVo.setTemplateMapping(templateMapping);
            auditEmailVo.setWorkflowEntity(auditEmailVo.getTemplateMapping().getWorkflowEntity());
            auditEmailVo.setVendorId(auditVo.getVendorId());
            auditEmailVo.setResourceId(auditVo.getResourceId());            

            		            ////Adding this for EMAIL change in Addressing Name for rebranding
			                    try {									
								AuditEmailVo newAuditEmailVoPrimeContact = getAuditEmailDao().getResourceNamePrimeContact(auditEmailVo.getVendorId() );
			                    StringBuffer sbPrimeContact = new StringBuffer();
			                    sbPrimeContact.append(newAuditEmailVoPrimeContact.getFirstName() + " ");
			                    if(StringUtils.isNotBlank(newAuditEmailVoPrimeContact.getMiddleName())){
			                        sbPrimeContact.append(newAuditEmailVoPrimeContact.getMiddleName() + " ");
			    				}
			                    sbPrimeContact.append(newAuditEmailVoPrimeContact.getLastName() + " ");
			                    auditEmailVo.setFirstNameLastName(sbPrimeContact.toString());

								}catch (DataServiceException e) {
									logger.error("Error in AuditEmailBusinessBean due to "+e.getMessage());
								}
                                ////ENd of changes for Rebranding


            // Reason descriptions
            // this state transition requires an email
            // continue with sending the email
            try {
                logger.info("******************************* Getting auditemailDao " + auditEmailVo);
                auditEmailVo.setTo(getAuditEmailDao().getEmailAddressFromVendorId(auditEmailVo.getVendorId()));
                if (auditEmailVo.getTemplateMapping() != null) {
                    auditEmailVo.setSubject(auditEmailVo.getTemplateMapping().getTemplateSubject());
                }
                auditEmailVo.setLogoPath("/images/icon_logo.gif");
                logger.info("******************************* email: \n"
                                + auditEmailVo);
            } catch (DataServiceException e) {
            	logger.error("Error in AuditEmailBusinessBean due to "+e.getMessage());
            }

            // send the email

            // populate email template fields
            // if this is a resource related audit populate the resource related
            // fields
            logger.info("******************************* entity: "
                            + auditEmailVo.getWorkflowEntity());
            if ((auditEmailVo.getWorkflowEntity().equals(RESOURCE))
                    || (auditEmailVo.getWorkflowEntity()
                            .equals(RESOURCE_CREDENTIAL))) {
                // get the team members name:
                StringBuffer sb = new StringBuffer();

                try {
                    AuditEmailVo newAuditEmailVo = getAuditEmailDao().getResourceName(auditEmailVo.getResourceId());
                    logger.info("******************************* resourceID: "
                                    + auditEmailVo.getResourceId());
                    sb.append(newAuditEmailVo.getFirstName()).append(" ");
                    sb.append(newAuditEmailVo.getMiddleName()).append(" ");
                    sb.append(newAuditEmailVo.getLastName() ).append(" ");
                    auditEmailVo.setResourceName(sb.toString());
                    auditEmailVo.setResFirstName(newAuditEmailVo.getFirstName());
                    auditEmailVo.setResLastName(newAuditEmailVo.getLastName());
                    auditEmailVo.setFirstName(newAuditEmailVo.getFirstName());
                    auditEmailVo.setLastName(newAuditEmailVo.getLastName());

                    logger.info("******************************* name: " + sb);
                } catch (DataServiceException e) {
                	logger.error("Error in AuditEmailBusinessBean due to "+e.getMessage());
                }
            }
            // if this is a credential type populate the credential related
            // information.
            if ((auditEmailVo.getWorkflowEntity().equals(RESOURCE_CREDENTIAL))) {
                AuditEmailVo newAuditEmailVo = new AuditEmailVo();
                newAuditEmailVo.setResourceId(auditEmailVo
                        .getResourceId());
                auditEmailVo.setResourceId(auditEmailVo.getResourceId());
                logger.info("******************************* resourceID: "
                                + auditEmailVo.getResourceId());
                newAuditEmailVo.setCredentialId(auditVo.getResourceCredentialId());
                auditEmailVo.setCredentialId(auditVo.getResourceCredentialId());
                logger.info("******************************* resourceCredentialID: "
                		+ auditVo.getResourceCredentialId());
                try {
                    newAuditEmailVo = getAuditEmailDao()
                            .getSpecificResourceCredential(auditEmailVo);
                    logger.info("******************************* newAuditEmailVO"
                                    + newAuditEmailVo);
                } catch (DataServiceException e) {
                	logger.error("Error in AuditEmailBusinessBean due to "+e.getMessage());
                }
                // populate the credential relaed details
                auditEmailVo.setCredentialId(newAuditEmailVo.getCredentialId());
                auditEmailVo.setCredentialName(newAuditEmailVo.getCredentialName());
                auditEmailVo.setCredentialType(newAuditEmailVo.getCredentialType());
                auditEmailVo.setCredentialCategory(newAuditEmailVo.getCredentialCategory());
                auditEmailVo.setCredentialNumber(newAuditEmailVo.getCredentialNumber());
                //populating review comments
                auditEmailVo.setReviewComments(auditVo.getReviewComments());
            }

            if ((auditEmailVo.getTemplateMapping().getWorkflowEntity().equals(VENDOR_CREDENTIAL))) {
                AuditEmailVo newAuditEmailVo = new AuditEmailVo();
                newAuditEmailVo.setVendorId(auditEmailVo.getVendorId());
                auditEmailVo.setVendorId(auditEmailVo.getVendorId());
                logger.info("******************************* vendorID: "
                                + auditEmailVo.getVendorId());
                newAuditEmailVo.setCredentialId(auditVo.getVendorCredentialId());
                auditEmailVo.setCredentialId(auditVo.getVendorCredentialId());
                try {
                    newAuditEmailVo = getAuditEmailDao().getSpecificVendorCredential(auditEmailVo);
                    logger.info("******************************* newAuditEmailVO" + newAuditEmailVo);
                } catch (DataServiceException e) {
                	logger.error("Error in AuditEmailBusinessBean due to "+e.getMessage());
                }
                // populate the credential relaed details
                auditEmailVo.setCredentialId(newAuditEmailVo.getCredentialId());
                auditEmailVo.setCredentialName(newAuditEmailVo.getCredentialName());
                auditEmailVo.setCredentialType(newAuditEmailVo.getCredentialType());
                auditEmailVo.setCredentialCategory(newAuditEmailVo.getCredentialCategory());
                auditEmailVo.setCredentialNumber(newAuditEmailVo.getCredentialNumber());
                logger.info("******************************* credentialName: "
                                + auditEmailVo.getCredentialName());
                logger.info("******************************* CredentialType: "
                                + auditEmailVo.getCredentialType());
                //populating review comments
                auditEmailVo.setReviewComments(auditVo.getReviewComments());
            }
            //assign company name to auditEmailVo if it is not null
            if(null != auditVo.getAuditBusinessName()){
            	auditEmailVo.setBusinessName(auditVo.getAuditBusinessName());
            }
            populateReasonCodeDescription(auditEmailVo, auditVo);
            // finally send the email
            try {
                //converting Firm approved mail from velocity to responsys
            	if(auditEmailVo.getTemplateMapping().getWorkflowEntity().equals(VENDOR)){
                	HashMap<String, Object> map = new AOPHashMap();
                	
                	map.put(AOPConstants.PRIME_CONTACT_STRING, auditEmailVo.getFirstNameLastName().trim());
                	map.put(AOPConstants.PRIME_CONTACT, auditEmailVo.getFirstNameLastName().trim());
                	map.put(AOPConstants.AOP_USER_EMAIL, auditEmailVo.getTo());
                	map.put(AOPConstants.AOP_TEMPLATE_ID, templateMapping.getTemplateId());
                	map.put(AOPConstants.REASONDESCRIPTION, auditEmailVo.getReasonDescription());
                	map.put(AOPConstants.CREDENTIALNAME, auditEmailVo.getCredentialName());
                	map.put(AOPConstants.CREDENTIALTYPE, auditEmailVo.getCredentialType());
                	map.put(AOPConstants.CREDENTIALNUMBER, auditEmailVo.getCredentialNumber());
                	map.put(AOPConstants.RESOURCENAME, auditEmailVo.getResourceName());
                	map.put(AOPConstants.TARGETSTATENAME, auditEmailVo.getTargetStateName());
                	map.put(AOPConstants.BUSINESS_NAME, auditEmailVo.getBusinessName());
            		AlertTask alertTask = getEmailAlertTask(map);             
            		try {
            			alertDao.addAlertToQueue(alertTask);
            		} catch (Exception e) {
            			logger.error("Error in AuditEmailBOImpl->inserting to alert task"+e.getMessage());
            		}
                }
                else{ // for all other velocity emails
                	sendAuditEmail(auditEmailVo);
                }
            	
            } catch (BusinessServiceException e) {
//            	logger.error(e);
            	throw new AuditException("Error sending audit email", e);
            }
        }
    }


    private HashMap<Integer, AuditTemplateMappingVo> getAuditEmailTemplateMappings() 
    	throws AuditException
    {
        logger.debug("[AuditEmailTemplateBean]************************************************* "
                + "Entered get Email Template Mappings");
        if (auditEmailTemplateMappings == null) {
            try {
                auditEmailTemplateMappings = new HashMap<Integer, AuditTemplateMappingVo> ();
                for (AuditTemplateMappingVo atmVo: getTemplateDao().getAuditEmailTemplateMappings()) {
                    auditEmailTemplateMappings.put (atmVo.getTargetStateId(), atmVo);
                    logger.debug("***********************************************KEY"+ atmVo.getTargetStateId() + "ATMVO" +atmVo);
                }
            } catch (DBException e) {
				throw new AuditException("Could not get the template mappings", e);
            }
        }
        return auditEmailTemplateMappings;
    }// getEmailTemplates


    private void populateReasonCodeDescription(AuditEmailVo auditEmailVo,AuditVO auditVo) {
        logger.info("******************************* REASON "
                + auditEmailVo);
        String reasonCodeDescription = null;
        try {
        	if(auditVo.getReasonCodeIds() != null){
	            for (String reasonCode : auditVo.getReasonCodeIds()) {
	                reasonCodeDescription = getAuditEmailDao()
	                        .getReasonCodeDescription(reasonCode);
	                if(auditVo.getWfStatusId().equals(OrderConstants.SERVICE_PRO_CREDENTIAL_OOC)||auditVo.getWfStatusId().equals(OrderConstants.FIRM_CREDENTIAL_OOC)){
	                	reasonCodeDescription = updateReasonDescForCredentialOOC(reasonCodeDescription,auditVo);
	                }
	                auditEmailVo.addReason(reasonCode, reasonCodeDescription);
	                logger.info("------------------------ Reason: ---- " + reasonCode   + "                         " + reasonCodeDescription);
	            }	           
	            logger.info("*******************************" + auditEmailVo.getReasonDescriptionLogger());
	            logger.info("*******************************" + auditEmailVo.getFirstNameLastName());
	            logger.info("*******************************" + auditEmailVo.getFirstNameLastName());
        	}
        } catch (DataServiceException e) {
        	logger.info("Caught Exception and ignoring",e);
        }
    }

    private void sendAuditEmail(AuditEmailVo auditEmailVo) throws BusinessServiceException {
        String templateString = null;
        StringWriter sw = new StringWriter();
        templateString = auditEmailVo.getTemplateMapping().getTemplateSource();
        String reasonDesc = getReasonDescriptionSentence(auditEmailVo);
        if(reasonDesc != null){
        	auditEmailVo.setReasonDescriptionParsed(reasonDesc);
        }
        VelocityContext vContext = new VelocityContext();
        logger.info("******************************** Audit email mapping found ! --- VContext " + vContext);
        vContext.put("PrimeContact", auditEmailVo.getFirstNameLastName().trim() );
        vContext.put("auditEmailVO", auditEmailVo);
        vContext.put("auditTemplateVO", auditEmailVo.getTemplateMapping());
        try 
        {
            velocityEngine.evaluate(vContext, sw, "velocity template", templateString);
            if (sw == null) 
                logger.warn("Could not generate email text"); 
            getEmailTemplateBean().sendGenericEmailWithLogo(auditEmailVo.getTo(),
                    auditEmailVo.getTemplateMapping().getTemplateFrom(),
                    auditEmailVo.getTemplateMapping().getTemplateSubject(), sw.toString());
        } 
	    catch( IOException e1)
	    {
        	throw new BusinessServiceException("Problems with velocity", e1);
	    }
        catch (Exception e2) 
        {
        	throw new BusinessServiceException("Error in sendGenericEmailWithLogo()", e2);
        }
    }// send email alert




	private String getReasonDescriptionSentence(AuditEmailVo auditEmailVo) {
		VelocityContext vContext = new VelocityContext();
		vContext.put("auditEmailVO", auditEmailVo);
		vContext.put("PrimeContact", auditEmailVo.getFirstNameLastName().trim() );
		Writer sw = new StringWriter();
		try {
			velocityEngine.evaluate(vContext, sw , "", auditEmailVo.getReasonDescription());
		} catch(Exception e) {
			logger.error("Reason Description evaluation failed " + auditEmailVo.getReasonDescriptionLogger(),e);
		}

		return sw.toString();
	}
	public void sendAuditErrorEmail(AuditVO vo, String errorMessage)
	{
		getEmailTemplateBean().sendErrorEmail(vo, errorMessage,
				AUDIT_ERROR_EMAIL_TO_ADDRESS,AUDIT_ERROR_EMAIL_FROM_ADDRESS);
		return;
	}
	/** This private method is used to append the reviewer comments with reason code
	 * for company and team member credentials out of compliance
	 * @param String reasonCodeDescription
	 * @param AuditVO
	 * @return String
	 */
	private String updateReasonDescForCredentialOOC(String reasonCodeDescription,AuditVO auditVo){
		int lastIndex = 0;
		lastIndex = reasonCodeDescription.indexOf("</li>");
		String reasonDesc = reasonCodeDescription.substring(0, lastIndex);
		reasonCodeDescription = reasonDesc +OrderConstants.DASH_WITH_SPACES+auditVo.getReviewComments()+"</li></ul>";
		return reasonCodeDescription;	
	}


	/**
	 * @return the auditEmailDao
	 */
	public IAuditEmailDao getAuditEmailDao() {
		return auditEmailDao;
	}


	/**
	 * @param auditEmailDao the auditEmailDao to set
	 */
	public void setAuditEmailDao(IAuditEmailDao auditEmailDao) {
		this.auditEmailDao = auditEmailDao;
	}

	/**
	 * @return the emailTemplateBean
	 */
	public IEmailTemplateBO getEmailTemplateBean() {
		return emailTemplateBean;
	}


	/**
	 * @param emailTemplateBean the emailTemplateBean to set
	 */
	public void setEmailTemplateBean(IEmailTemplateBO emailTemplateBean) {
		this.emailTemplateBean = emailTemplateBean;
	}


	/**
	 * @return the templateDao
	 */
	public ITemplateDao getTemplateDao() {
		return templateDao;
	}


	/**
	 * @param templateDao the templateDao to set
	 */
	public void setTemplateDao(ITemplateDao templateDao) {
		this.templateDao = templateDao;
	}


	/**
	 * @return the emailImgPath
	 */
	public ClassPathResource getEmailImgPath() {
		return emailImgPath;
	}


	/**
	 * @param emailImgPath the emailImgPath to set
	 */
	public void setEmailImgPath(ClassPathResource emailImgPath) {
		this.emailImgPath = emailImgPath;
	}


	/**
	 * @return the logoPath
	 */
	public String getLogoPath() {
		return logoPath;
	}


	/**
	 * @param logoPath the logoPath to set
	 */
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}


	/**
	 * @return the mailSender
	 */
	public JavaMailSender getMailSender() {
		return mailSender;
	}


	/**
	 * @param mailSender the mailSender to set
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}


	/**
	 * @return the velocityEngine
	 */
	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}


	/**
	 * @param velocityEngine the velocityEngine to set
	 */
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}


	/**
	 * @param auditEmailTemplateMappings the auditEmailTemplateMappings to set
	 */
	public void setAuditEmailTemplateMappings(
			HashMap<Integer, AuditTemplateMappingVo> auditEmailTemplateMappings) {
		this.auditEmailTemplateMappings = auditEmailTemplateMappings;
	}
	/*
	 * Method to get the alert task object
	 */
	private AlertTask getEmailAlertTask(Map<String, Object> aopHashMap) {		
		Integer templateId  = (Integer)(aopHashMap.get(AOPConstants.AOP_TEMPLATE_ID));
		Template template = getTemplateDetail(templateId);
		AlertTask alertTask = new AlertTask();
		Date date = new Date();
		alertTask.setAlertedTimestamp(null); 
		alertTask.setAlertTypeId(template.getTemplateTypeId());
		alertTask.setCreatedDate(date);
		alertTask.setModifiedDate(date);
		alertTask.setTemplateId(templateId);
		if(template.getTemplateFrom()!=null)
			alertTask.setAlertFrom(template.getTemplateFrom());
		else
			alertTask.setAlertFrom(AlertConstants.SERVICE_LIVE_MAILID);
		alertTask.setAlertTo((String)aopHashMap.get(AOPConstants.AOP_USER_EMAIL));
		alertTask.setAlertBcc("");
		alertTask.setAlertCc("");
		alertTask.setCompletionIndicator(AlertConstants.INCOMPLETE_INDICATOR);
		alertTask.setPriority(template.getPriority());
		alertTask.setTemplateInputValue(aopHashMap.toString());
		return alertTask; 	
	}
	/*
	 * Method to get the template details for a particular
	 * template id and put the current template in a hash map.
	 */
	private Template getTemplateDetail(Integer templateId){
 		Template template = templateCache.get(templateId);
 		if ( template == null) {
 			TemplateDaoImpl templateDaoImpl = getTemplateDaoImpl();

 			try {
 				template = new Template();
 				template.setTemplateId(templateId);
 				template = templateDaoImpl.query(template);
 				templateCache.put(templateId, template);
 			}
 			catch(DataServiceException dse){
 				logger.error("AuditEmailBOImpl-->getTemplateDetail-->DataServiceException-->", dse);
 			}
 		} 		
 		return template;
 	}


	public TemplateDaoImpl getTemplateDaoImpl() {
		return templateDaoImpl;
	}


	public void setTemplateDaoImpl(TemplateDaoImpl templateDaoImpl) {
		this.templateDaoImpl = templateDaoImpl;
	}


	public AlertDaoImpl getAlertDao() {
		return alertDao;
	}


	public void setAlertDao(AlertDaoImpl alertDao) {
		this.alertDao = alertDao;
	}
}
