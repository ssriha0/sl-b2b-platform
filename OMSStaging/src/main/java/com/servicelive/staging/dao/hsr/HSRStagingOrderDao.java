/**
 * 
 */
package com.servicelive.staging.dao.hsr;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.staging.dao.AbstractBaseDao;
import com.servicelive.staging.domain.hsr.HSRStageOrder;

/**
 * @author hoza
 *
 */
public class HSRStagingOrderDao extends AbstractBaseDao implements IHSRStagingOrderDao {

	/* (non-Javadoc)
	 * @see com.servicelive.staging.dao.hsr.IHSRStagingOrderDao#findAll(int[])
	 */
	public List<HSRStageOrder> findAll(int... rowStartIdxAndCount)
			throws Exception {
		
		@SuppressWarnings("unchecked")
		List <HSRStageOrder> resultsList = ( List <HSRStageOrder> )super.findAll("HSRStageOrder", rowStartIdxAndCount);
		return  resultsList;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.staging.dao.hsr.IHSRStagingOrderDao#findById(java.lang.Integer)
	 */
	public HSRStageOrder findById(Integer id) throws Exception {
	
		return ( HSRStageOrder) super.findById(HSRStageOrder.class, id);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.staging.dao.hsr.IHSRStagingOrderDao#save(com.servicelive.staging.domain.hsr.HSRStageOrder)
	 */
	@Transactional ( propagation = Propagation.REQUIRED)
	public void save(HSRStageOrder entity) throws Exception {
		super.save(entity);
		super.getEntityManager().flush();
		logger.debug("Save executed successfully for " + this.getClass().getName());
		
	}

	/* (non-Javadoc)
	 * @see com.servicelive.staging.dao.hsr.IHSRStagingOrderDao#update(com.servicelive.staging.domain.hsr.HSRStageOrder)
	 */
	public HSRStageOrder update(HSRStageOrder entity) throws Exception {
		HSRStageOrder hsrorder = (HSRStageOrder)super.update(entity);  
		super.getEntityManager().flush();
		return hsrorder;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.servicelive.staging.dao.hsr.IHSRStagingOrderDao#findByOrderNoAndUnitNo(java.lang.String, java.lang.String)
	 */
	@Transactional ( propagation = Propagation.SUPPORTS)	
	public HSRStageOrder findByOrderNoAndUnitNo(final String orderNo, final String unitNo) {
		if (logger.isInfoEnabled())
		  logger.info("finding HSRStageOrder instance with order number:" + orderNo + " and unit number: " + unitNo);
		try {
			final String hql = "from HSRStageOrder model where model.orderNumber" 
					+ "= :orderNo and model.unitNumber = :unitNo";
			Query query = super.getEntityManager().createQuery(hql);
			query.setParameter("orderNo", orderNo);
			query.setParameter("unitNo", unitNo);
			@SuppressWarnings("unchecked")
			List<HSRStageOrder> result = (List<HSRStageOrder>)query.getResultList(); 
			if(result != null && result.size() > 0 ){
				HSRStageOrder stagedOrder =   result.get(0);
				//Dirty Hack to get Only Objects I need to loaded as I know that I have define LAZY loading for most these.
				stagedOrder.getTransactions().size();
				stagedOrder.getUpdates().size();
				return stagedOrder;
			}
			return null;
			
		} catch(NoResultException ex){
			logger.info("No HSRStageOrder entity found for order number:" + orderNo + " and unit number: " + unitNo);
			return null;
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.staging.dao.hsr.IHSRStagingOrderDao#updateSoId(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional ( propagation = Propagation.REQUIRED)	
	public HSRStageOrder updateSoId(String orderNo, String unitNo, String soId)	throws Exception {
		if (logger.isInfoEnabled())
			  logger.info("Updating SOID for HSRStageOrder instance with order number:" + orderNo + " and unit number: " + unitNo + " with SoId = "+ soId);
			try {
				String hql = "update HSRStageOrder o set o.serviceOrderId = :soId, o.modifiedDate = :now where o.orderNumber =  :orderNo  and o.unitNumber = :unitNo";
				Query query = getEntityManager().createQuery(hql);
				query.setParameter("soId", soId);
				query.setParameter("orderNo", orderNo);
				query.setParameter("unitNo", unitNo);
				query.setParameter("now", new Date());
				int i = query.executeUpdate();
				if(i == 0) {
					return null; // this record doesnt exist
				} else if(i == 1) {
					return findByOrderNoAndUnitNo(orderNo, unitNo);
				}else {
					return null ; //Multiple record got updated thats Bad sign, i will flag that by passing NULL
				}
			} catch (RuntimeException re) {
				logger.error("Update SO Id failed for order number:" + orderNo + " and unit number: " + unitNo + " with SoId = "+ soId, re);
				throw re;
			}
	}

	@Transactional ( propagation = Propagation.SUPPORTS)	
	public List<HSRStageOrder> findAllByUnitAndOrderNumbers(
			List<String> unitAndOrderNumbers) {
		StringBuilder sb = new StringBuilder(unitAndOrderNumbers.size() * 16);
		if (logger.isInfoEnabled()) {
			for (String s : unitAndOrderNumbers) sb.append(s).append(", ");
			if (sb.length() > 2) sb.delete(sb.length() - 2, sb.length()); // remove trailing ", "
			logger.info("finding HSRStageOrder instances with unit and order numbers: " + sb.toString());
		}
		try {
			String hql = 
				" FROM HSRStageOrder model" +
				" WHERE CONCAT(model.unitNumber, model.orderNumber) IN (:unitAndOrderNumbers)";
			Query query = super.getEntityManager().createQuery(hql);
			query.setParameter("unitAndOrderNumbers", unitAndOrderNumbers);

			@SuppressWarnings("unchecked")
			List<HSRStageOrder> result = (List<HSRStageOrder>)query.getResultList();
			return result;
		} catch(NoResultException e){
			if (logger.isInfoEnabled()) {
				logger.info("No HSRStageOrder entities found for unit and order numbers:" + sb.toString());
			}
			return Collections.emptyList();
		} catch (RuntimeException e) {
			logger.error("find by unit and order numbers failed", e);
			throw e;
		}
	}

	@Transactional (propagation = Propagation.SUPPORTS)	
	public HSRStageOrder findLatestByUnitAndOrderNumbersWithTestSuffix(
			final String unitAndOrderNumber, final String testSuffix) {
		if (logger.isInfoEnabled()) {
			logger.info("finding HSRStageOrder instance with unit and order number:" + unitAndOrderNumber + " and test suffix: " + testSuffix);
		}
		try {
			String hql = 
				" FROM HSRStageOrder model" +
				" WHERE CONCAT(model.unitNumber, model.orderNumber) LIKE :unitAndOrderNumber" +
				" ORDER BY model.hsrOrderId DESC";
			Query query = super.getEntityManager().createQuery(hql);
			query.setParameter("unitAndOrderNumber", unitAndOrderNumber + testSuffix + "%");
			query.setMaxResults(1);
			HSRStageOrder result = (HSRStageOrder) query.getSingleResult();
			if(result != null) {
				//Dirty Hack to get Only Objects I need to loaded as I know that I have define LAZY loading for most these.
				result.getTransactions().size();
				result.getUpdates().size();
			}
			return result;
			
		} catch(NoResultException e){
			logger.info("No HSRStageOrder entity found for unit and order number:" + unitAndOrderNumber + " and test suffix: " + testSuffix);
			return null;
		} catch (RuntimeException e) {
			logger.error("find by property name failed", e);
			throw e;
		}
	}
}
