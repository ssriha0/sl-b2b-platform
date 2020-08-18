package com.servicelive.reasoncode.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.autoclose.dao.impl.AbstractBaseDao;
import com.servicelive.domain.reasoncodemgr.ReasonCode;
import com.servicelive.domain.reasoncodemgr.ReasonCodeHist;
import com.servicelive.domain.reasoncodemgr.ReasonCodeTypes;
import com.servicelive.reasoncode.dao.ManageReasonCodeDao;

/** 
 * 
 * @author Infosys
 * 
 */
	@Transactional(propagation = Propagation.REQUIRED)
	public class ManageReasonCodeDaoImpl extends AbstractBaseDao implements ManageReasonCodeDao  {
	
		/**
		 * Method to fetch active & archive reason codes
		 * @return
		 */
	public List<ReasonCode> getAllReasonCodes(int buyerId) {
		String hql = "from ReasonCode where (general = "
				+ 1
				+ " or (general ="
				+ 0
				+ " and buyerId = :buyerId)) and reasonCodeStatus != :status order by reasonCode";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
		query.setParameter("status", OrderConstants.DELETED_CODE);
		
		try {			
			@SuppressWarnings("unchecked")
			List<ReasonCode> resultDao = query.getResultList();
			return resultDao;
		}
		catch (NoResultException e) {
			logger.error("ManageReasonCodeDaoImpl.findByBuyerId NoResultException");
			return null;
		}
	}
	
	/**
	 * Method to fetch cancel codes
	 * @return
	 */
	public List<ReasonCode> getAllCancelReasonCodes(int buyerId, String type)
	{	
		String hql = "from ReasonCode where (general = "+1+" or (general ="+0+" and buyerId = :buyerId)) and reasonCodeType = :type and reasonCodeStatus = :status ORDER BY reasonCode";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
		query.setParameter("type", type);
		query.setParameter("status",OrderConstants.ACTIVE_CODE);
				
		try {			
			@SuppressWarnings("unchecked")
		    List<ReasonCode> reasonList = query.getResultList();
			List <ReasonCodeHist> historyList= this.getHistory(buyerId);	
			
			//removing general reasons having deleted/archived status in history table 
			List<ReasonCode> newList = new ArrayList<ReasonCode>();
			Iterator historyIt = historyList.iterator();
			while(historyIt.hasNext()){
				Object[] reasonCodeHist = (Object[])historyIt.next();
				Integer reasonId = (Integer)reasonCodeHist[1];
				
				Iterator <ReasonCode>reasonIt = reasonList.iterator();
				while(reasonIt.hasNext()){
					ReasonCode reasonCode = reasonIt.next();
					if(reasonId.intValue() == reasonCode.getReasonCodeId()){
						newList.add(reasonCode);
					}
				}				
			}
			List<ReasonCode> resultList = new ArrayList<ReasonCode>(reasonList); 
			resultList.removeAll(newList);
			return resultList;
		}
		catch (NoResultException e) {
			logger.error("ManageReasonCodeDaoImpl.getAllCancelReasonCodes NoResultException");
			return null;
		}
	}
	
	/**
	 * Method to delete/archive reason code 
	 * @return
	 */
	public String delete(ReasonCode rc){
	
		String hql = "from ReasonCode where reasonCodeId = :id";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("id", rc.getReasonCodeId());
		
		
		try {			
			@SuppressWarnings("unchecked")
			ReasonCode reasonCode = (ReasonCode)query.getSingleResult();
			//if general_ind = 1, reason_codes table need not be updated
			if(1 != reasonCode.getGeneral()){
				rc.setCreatedDate(reasonCode.getCreatedDate());
				rc.setGeneralInd(reasonCode.getGeneral());
				getEntityManager().merge(rc);
			}		
			
			//updating history table
			ReasonCodeHist rcHist = new ReasonCodeHist();
			rcHist.setReasonCodeId(rc.getReasonCodeId());
			rcHist.setBuyerId(rc.getBuyerId());
			rcHist.setModifiedBy(rc.getModifiedBy());
			rcHist.setDate(rc.getModifiedDate());
			
			if(OrderConstants.DELETED_CODE.equals(rc.getReasonCodeStatus())) {
				rcHist.setAction(OrderConstants.DELETED_CODE);
				this.logHistory(rcHist);
				return OrderConstants.DELETE_STATUS;
			}
			else if(OrderConstants.ARCHIVED_CODE.equals(rc.getReasonCodeStatus())) {
				rcHist.setAction(OrderConstants.ARCHIVED_CODE);
				this.logHistory(rcHist);
				return OrderConstants.ARCHIVE_STATUS;
			}			
			return null;
			
		}
		catch (NoResultException e) {
			logger.error("ManageReasonCodeDaoImpl.deleteReasonCode NoResultException");
			return null;
		}
	}
	
	/**
	 * Method to add the reason code 
	 * @return
	 */
	public String add(ReasonCode rc){
	
		try{
			String code = rc.getReasonCode().toLowerCase();
			List<ReasonCode> codeList = this.getReasonCode(rc.getBuyerId(),code,rc.getReasonCodeType());
			boolean createReasonCode=false;
			
			//if this list is empty then it means there are no buyer specific reason code with same name.
			if(codeList.isEmpty()) {
				//checking if there are any general reason code with same name.
				codeList = this.getGeneralReasonCode(rc.getBuyerId(),code,rc.getReasonCodeType());
				if(codeList.isEmpty()){
					//if there are no matching general reason code with same name we can add the new reason code.
					createReasonCode=true;					
				}else{
					//but if there is any general reason code with same name, we are checking whether is deleted or archived by the buyer or not.
					codeList = this.getDelGenReasonCode(rc.getBuyerId(),code,rc.getReasonCodeType());
					if(!(codeList.isEmpty())){
						//if it is deleted or archived by the buyer we are adding it as a new one.
						createReasonCode=true;	
					}						
				}				
			}
			if(createReasonCode){
				getEntityManager().persist(rc);	
				ReasonCodeHist rcHist = new ReasonCodeHist();
				rcHist.setReasonCodeId(rc.getReasonCodeId());
				rcHist.setBuyerId(rc.getBuyerId());
				rcHist.setAction(OrderConstants.ADDED_CODE);
				rcHist.setModifiedBy(rc.getModifiedBy());
				rcHist.setDate(rc.getModifiedDate());
				this.logHistory(rcHist);
				return OrderConstants.SAVE;
			}else{
				return OrderConstants.EXISTS;
			}
			
		}
		catch(Exception e){
			logger.error("ManageReasonCodeDaoImpl.saveReasonCode Exception");
			logger.error(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Method to check whether reason code is used by existing SO
	 * @return
	 */
	public String checkInSO(int reasonCodeId,String type,int buyerId,String reasonCode){
		String hql = null;
		
		if(OrderConstants.TYPE_CANCEL.equals(type)) {
			hql = "select count(so_id) from so_cancellation where cancellation_reason_code = :code and modified_by in " +
					"(select user_name from buyer_resource where buyer_id = :id)";
		}
		else if(OrderConstants.TYPE_SCOPE.equals(type)) {
			hql = "select count(so_id) from so_manage_scope where reason_code_id = :code and modified_by in " +
					"(select user_name from buyer_resource where buyer_id = :id)";
		}else if (OrderConstants.TYPE_SPEND_LIMIT.equals(type)){
			hql = "SELECT COUNT(sph.so_id) FROM so_spend_limit_history sph JOIN so_hdr shr ON sph.so_id=shr.so_id" +
					" WHERE  sph.reason_code_id=:code  AND shr.buyer_id=:id";
		}else{
			//since new types are not used by SO,it can be deleted
			return OrderConstants.DELETE_STATUS;
		}
				
		Query query = getEntityManager().createNativeQuery(hql);
			query.setParameter("code", reasonCodeId);	
			query.setParameter("id", buyerId);

		try {
			BigInteger count = (BigInteger)query.getSingleResult();				
			
			if(count.intValue() == 0) {			
				return OrderConstants.DELETE_STATUS;
			}
			else{
				return OrderConstants.ARCHIVE_STATUS;
			}
	
		} catch (NoResultException e) {
			logger.error("ManageReasonCodeDaoImpl.checkInSO NoResultException");
			return null;
		}		
	}
	
	/**
	 * Method to log add/delete/archive history
	 * @return
	 */
	public void logHistory(ReasonCodeHist rcHist){
		
		try{		
			getEntityManager().persist(rcHist);	
		}
		catch(Exception e){
			logger.error("ManageReasonCodeDaoImpl.logHist Exception");
		}
	}
	
	/**
	 * Method to fetch reason code by id
	 * @return
	 */
	public String getReasonCodeById(Integer id){
		try{
			ReasonCode reason = (ReasonCode)findById(ReasonCode.class, id);
			return reason.getReasonCode();
		}
		catch (NoResultException e) {
			logger.error("ManageReasonCodeDaoImpl.findById NoResultException");
		return null;
		}
	}
	
	/**
	 * Method to fetch reason code by type
	 * @return
	 */
	public List<ReasonCode> getActiveReasonCodeByType(int buyerId,String type) {
		String hql = "from ReasonCode where (general = "
				+ 1
				+ " or (general ="
				+ 0
				+ " and buyerId = :buyerId)) and reasonCodeType = :type and reasonCodeStatus = :status order by reasonCode";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);		
		query.setParameter("status", OrderConstants.ACTIVE_CODE);
		query.setParameter("type", type);
		
		try {
			@SuppressWarnings("unchecked")
			List<ReasonCode> reasonList = query.getResultList();	
			List <ReasonCodeHist> historyList= this.getHistory(buyerId);	
			
			//removing general reasons having deleted/archived status in history table 
			List<ReasonCode> newList = new ArrayList<ReasonCode>();
			Iterator historyIt = historyList.iterator();
			while(historyIt.hasNext()){
				Object[] reasonCodeHist = (Object[])historyIt.next();
				Integer reasonId = (Integer)reasonCodeHist[1];				
				Iterator <ReasonCode>reasonIt = reasonList.iterator();
				while(reasonIt.hasNext()){
					ReasonCode reasonCode = reasonIt.next();
					if(reasonId.intValue() == reasonCode.getReasonCodeId()){
						newList.add(reasonCode);
					}
				}				
			}
			List<ReasonCode> resultList = new ArrayList<ReasonCode>(reasonList); 
			resultList.removeAll(newList); 
			return resultList;
		} catch (NoResultException e) {
			logger.error("ManageReasonCodeDaoImpl.getActiveReasonCodeByType NoResultException");
			return null;
		}
	}
	
	/**
	 * Method to check for duplicate reason code
	 * @return
	 */
	private List<ReasonCode> getReasonCode(int buyerId,String reasonCode,String type) {
		
		String hql = "from ReasonCode where lower(reasonCode) = :code and reasonCodeType = :type and reasonCodeStatus = :status and buyerId= :id";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("id", buyerId);
		query.setParameter("code", reasonCode);
		query.setParameter("type", type);
		query.setParameter("status", OrderConstants.ACTIVE_CODE);		
		
		try {
			@SuppressWarnings("unchecked")
			List<ReasonCode> codeList = query.getResultList();
			return codeList;
		} catch (NoResultException e) {
			return null;
		}
		
	}
	/**
	 * Method to check if there are any general reason code
	 * @return
	 */
	private List<ReasonCode> getGeneralReasonCode(int buyerId,String reasonCode,String type) {
		
		String hql = "from ReasonCode where lower(reasonCode) = :code and reasonCodeType = :type and reasonCodeStatus = :status and general_ind = :general";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("general", 1);
		query.setParameter("code", reasonCode);
		query.setParameter("type", type);
		query.setParameter("status", OrderConstants.ACTIVE_CODE);
		
		try {
			@SuppressWarnings("unchecked")
			List<ReasonCode> codeList = query.getResultList();			
			return codeList;
		} catch (NoResultException e) {
			return null;
		}
	}
	/**
	 * Method to check if the general reason code is deleted or archived by the buyer
	 * @return
	 */
	private List<ReasonCode> getDelGenReasonCode(int buyerId,String reasonCode,String type) {
		
		String hql = "SELECT * FROM reason_codes rc JOIN reason_code_history rch ON rch.reason_code_id=rc.reason_code_id " +
							"WHERE rc.reason_code= :code AND rc.reason_code_type= :type  AND rch.buyer_id = :id AND rc.general_ind= :general " +
							"AND (rch.action = :delstatus OR rch.action= :archstatus)";
		
		Query query = getEntityManager().createNativeQuery(hql);
		query.setParameter("id", buyerId);
		query.setParameter("general", 1);
		query.setParameter("code", reasonCode);
		query.setParameter("type", type);
		query.setParameter("delstatus", OrderConstants.DELETED_CODE);
		query.setParameter("archstatus", OrderConstants.ARCHIVED_CODE);
		try {
			@SuppressWarnings("unchecked")
			List<ReasonCode> codeList = query.getResultList();			
			return codeList;
		} catch (NoResultException e) {
			return null;
		}
	}
	/**
	 * Method to fetch reason types
	 * @return
	 */
	public List<ReasonCodeTypes> getReasonTypes() {
		String hql = "FROM ReasonCodeTypes ORDER BY reason_code_type";
		Query query = getEntityManager().createQuery(hql);
				
		try {
			@SuppressWarnings("unchecked")
			List<ReasonCodeTypes> typeList = query.getResultList();			
			return typeList;
		} catch (NoResultException e) {
			return null;
		}
	}

	//fetch deleted & archived general reasons for a specific buyer from history table
	public List<ReasonCodeHist> getHistory(int buyerId) {
		String hql = "select * from reason_code_history where action != :action and buyer_id=:id and reason_code_id in " +
				"(select reason_code_id from reason_codes where general_ind = :ind)";
		Query query = getEntityManager().createNativeQuery(hql);
		query.setParameter("id", buyerId);
		query.setParameter("action", OrderConstants.ADDED_CODE);
		query.setParameter("ind", 1);
				
		try {
			@SuppressWarnings("unchecked")
			List<ReasonCodeHist> list = query.getResultList();			
			return list;
		} catch (NoResultException e) {
			return null;
		}
	}
}
