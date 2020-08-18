package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.dto.vo.alert.AlertTemplateMappingVo;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.ITemplateDao;
import com.newco.marketplace.vo.provider.AuditTemplateMappingVo;
import com.newco.marketplace.vo.provider.TemplateVo;

public class TemplateDaoImpl extends SqlMapClientDaoSupport implements ITemplateDao {
	 private static final Logger localLogger = Logger.getLogger(ContactDaoImpl.class);
	 
    public List<AuditTemplateMappingVo> getAuditEmailTemplateMappings()
            throws DBException {
        List<AuditTemplateMappingVo> etList = new ArrayList<AuditTemplateMappingVo>();
        // List<TemplateMappingVo> list = null;
        try {
            // rows in the table for application level
            // caching.
            etList = (List)getSqlMapClient().queryForList("emailTemplate.auditEmailTemplateMappings",
                    null);
            // for (Object o: list) {
            // etList.add((AuditEmailVo) o);
            // }
        } catch (SQLException ex) {
            ex.printStackTrace();
		     localLogger.info("SQL Exception @TemplateDaoImpl.getAuditEmailTemplateMappings() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @TemplateDaoImpl.getAuditEmailTemplateMappings() due to "+ex.getMessage());
       }catch (Exception ex) {
           ex.printStackTrace();
		     localLogger.info("General Exception @TemplateDaoImpl.getAuditEmailTemplateMappings() due to"+ex.getMessage());
		     throw new DBException("General Exception @TemplateDaoImpl.getAuditEmailTemplateMappings() due to "+ex.getMessage());
      }
        return (etList);
    }// getEmailTemplateMappings

    public List<AlertTemplateMappingVo> getAlertEmailTemplateMappings()
            throws DBException {
        List<AlertTemplateMappingVo> etList = new ArrayList<AlertTemplateMappingVo>();
        // List<TemplateMappingVo> list = null;
        try {
            // rows in the table for application level
            // caching.
            etList =(List) getSqlMapClient().queryForList("emailTemplate.alertEmailTemplateMappings", null);
            // for (Object o: list) {
            // etList.add((AuditEmailVo) o);
            // }
        } catch (SQLException ex) {
            ex.printStackTrace();
		     localLogger.info("SQL Exception @TemplateDaoImpl.getAlertEmailTemplateMappings() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @TemplateDaoImpl.getAlertEmailTemplateMappings() due to "+ex.getMessage());
       }catch (Exception ex) {
           ex.printStackTrace();
		     localLogger.info("General Exception @TemplateDaoImpl.getAlertEmailTemplateMappings() due to"+ex.getMessage());
		     throw new DBException("General Exception @TemplateDaoImpl.getAlertEmailTemplateMappings() due to "+ex.getMessage());
      }
        return (etList);
    }//getEmailTemplateMappings

    public TemplateVo getTemplate (String templateName) throws DBException{
    	TemplateVo rvTemplate=null;
    	try{
    		rvTemplate = (TemplateVo) getSqlMapClient().queryForObject("emailTemplate.getTemplateFromName", templateName);	
    	}catch (SQLException ex) {
            ex.printStackTrace();
		     localLogger.info("SQL Exception @TemplateDaoImpl.getTemplate() due to "+ex.getMessage());
		     throw new DBException("SQL Exception @TemplateDaoImpl.getTemplate() due to "+ex.getMessage());
       }catch (Exception ex) {
           ex.printStackTrace();
		     localLogger.info("General Exception @TemplateDaoImpl.getTemplate() due to"+ex.getMessage());
		     throw new DBException("General Exception @TemplateDaoImpl.getTemplate() due to "+ex.getMessage());
      }
      
        if (null == rvTemplate) { 
            throw new DBException(
                "[AuditDaoImpl] Select query returned null for method getEmailAddressFromResourceId."); 
        }
        return (rvTemplate);    
    }
    
    public TemplateVo getTemplate (Integer templateId) throws DBException{
//        TemplateVo rvTemplate = (TemplateVo) queryForObject("emailTemplate.getTemplateFromId", templateId);
//        if (null == rvTemplate) { 
//            throw new DataServiceException(
//                "[AuditDaoImpl] Select query returned null for method getEmailAddressFromResourceId."); 
//        }
//        return (rvTemplate);
        
        
        TemplateVo rvTemplate=null;
    	try{
    		rvTemplate = rvTemplate = (TemplateVo) getSqlMapClient()
					.queryForObject("emailTemplate.getTemplateFromId",
							templateId);
    	}catch (SQLException ex) {
            ex.printStackTrace();
		     localLogger.info("SQL Exception @TemplateDaoImpl.getTemplate() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @TemplateDaoImpl.getTemplate() due to "+ex.getMessage());
       }catch (Exception ex) {
           ex.printStackTrace();
		     localLogger.info("General Exception @TemplateDaoImpl.getTemplate() due to"+ex.getMessage());
		     throw new DBException("General Exception @TemplateDaoImpl.getTemplate() due to "+ex.getMessage());
      }
      
        if (null == rvTemplate) { 
            throw new DBException(
                "[AuditDaoImpl] Select query returned null for method getEmailAddressFromResourceId."); 
        }
        return (rvTemplate); 
    }
    
}//TemplateDaoImpl
