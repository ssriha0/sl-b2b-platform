package com.newco.marketplace.webservices.seiImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.newco.marketplace.webservices.dao.ILuAuditMessagesDAO;
import com.newco.marketplace.webservices.dao.INpsAuditFilesDAO;
import com.newco.marketplace.webservices.dao.LuAuditMessages;
import com.newco.marketplace.webservices.dao.NpsAuditFiles;
import com.newco.marketplace.webservices.sei.INPSAuditStagingService;


public class NPSAuditStagingService implements INPSAuditStagingService {
   
	private Logger logger = Logger.getLogger(NPSAuditStagingService.class);
    private ILuAuditMessagesDAO luAuditMessagesDAO;
    private INpsAuditFilesDAO npsAuditFilesDAO;
    private JpaTransactionManager txManager;
    
	public List<LuAuditMessages> retrieveAuditMessageInfo(String message, String npsStatus)
			throws Exception {
		
		List<LuAuditMessages> npsAuditMessagesList = new ArrayList<LuAuditMessages>(); ; //get nps message calling respective Dao
		
		try{
			npsAuditMessagesList = luAuditMessagesDAO.findByMessageAndNpsStatus(message, npsStatus);
			
		}catch(Exception e){
			logger.error("Error occured in NPSAuditTranslationService.retrieveAuditMessageInfo due to " + e.getMessage());
		}
		
		return npsAuditMessagesList;
	}

	public ILuAuditMessagesDAO getLuAuditMessagesDAO() {
		return luAuditMessagesDAO;
	}

	public void setLuAuditMessagesDAO(ILuAuditMessagesDAO luAuditMessagesDAO) {
		this.luAuditMessagesDAO = luAuditMessagesDAO;
	}

	public void stageAuditOrdersInFile(NpsAuditFiles ordersInFiles) 
		throws Exception {
		
		TransactionStatus txStatus = txManager.getTransaction(new DefaultTransactionDefinition());
		try{
			npsAuditFilesDAO.save(ordersInFiles);
			txManager.commit(txStatus);
		}catch(Exception e){
			if(!txStatus.isCompleted()){
				txManager.rollback(txStatus);
			}
			throw new Exception("Exception in stageAuditOrdersInFile.");
		}
		
		
	}

	/**
	 * @return the npsAuditFilesDAO
	 */
	public INpsAuditFilesDAO getNpsAuditFilesDAO() {
		return npsAuditFilesDAO;
	}

	/**
	 * @param npsAuditFilesDAO the npsAuditFilesDAO to set
	 */
	public void setNpsAuditFilesDAO(INpsAuditFilesDAO npsAuditFilesDAO) {
		this.npsAuditFilesDAO = npsAuditFilesDAO;
	}

	public JpaTransactionManager getTxManager() {
		return txManager;
	}

	public void setTxManager(JpaTransactionManager txManager) {
		this.txManager = txManager;
	}
	
	

}
