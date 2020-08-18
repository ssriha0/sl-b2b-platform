package com.servicelive.orderfulfillment.dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.SOWorkflowControls;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;

public class ServiceOrderProcessDao implements IServiceOrderProcessDao {

	protected final Logger logger = Logger.getLogger(getClass());

    private EntityManager em;

	private int retries = 5;

    @PersistenceContext()
    public void setEntityManager(EntityManager em) {
    	this.em = em;
    }

	public ServiceOrderProcess getServiceOrderProcess(String soId){
		return em.find(ServiceOrderProcess.class, soId);
	}
	public SOWorkflowControls getSOWorkflowControls(String soId){
		return em.find(SOWorkflowControls.class, soId);
	}
	
	public ServiceOrderProcess getServiceOrderProcessWithLock(String soId){
		ServiceOrderProcess sop = em.getReference(ServiceOrderProcess.class, soId);
		int r = 0;
		while(true){
			r++;
			boolean repeat = false;
			try{
				em.lock(sop, LockModeType.WRITE);
			}catch(Exception e){
				repeat = true;
			}
			if(!repeat) break;
			if(r > retries) throw new ServiceOrderException("Could not get the lock on the Service Order Process for a service order after " + retries + " number of tries. Giving up.");
		}
		return sop;
	}

    public void markGroupNotSearchable(String soGroupId) {
        Query query = em.createQuery("update ServiceOrderProcess set groupingSearchable = false WHERE groupId = :groupId");
        query.setParameter("groupId", soGroupId);
        @SuppressWarnings("unused")
		int i = query.executeUpdate();
    }

	public void save(ServiceOrderProcess sop){
		em.persist(sop);
	}
	
    @SuppressWarnings("unchecked")
	public List<ServiceOrderProcess> findLikeServiceOrders(ServiceOrder serviceOrder){
     	//need to find if how do we check status and if we need to check substatus
    	Query query = em.createQuery("Select s FROM ServiceOrder so join          so.serviceOrderProcess s " +
                "Where s.groupingSearchable = true and s.buyerId = ? and         s.primarySkillCatId = ?" +
                " and s.serviceDate1 = ? and s.street1 = ?" +
                " and s.street2 = ? and s.city = ?" +
                " and s.state = ? and s.zip = ?" +
                "and so.soId not in (105,125)" );

    	query.setParameter(1, serviceOrder.getBuyerId());
    	query.setParameter(2, serviceOrder.getPrimarySkillCatId());
    	query.setParameter(3, serviceOrder.getSchedule().getServiceDate1());
    	SOLocation serviceLocation = serviceOrder.getServiceLocation();
    	query.setParameter(4, serviceLocation.getStreet1());
    	query.setParameter(5, serviceLocation.getStreet2());
    	query.setParameter(6, serviceLocation.getCity());
    	query.setParameter(7, serviceLocation.getState());
    	query.setParameter(8, serviceLocation.getZip());
    	
    	return (List<ServiceOrderProcess>)query.getResultList();
    }

	public void update(ServiceOrderProcess sop) {
		em.merge(sop);
	}

	@SuppressWarnings("unchecked")
	public List<ServiceOrderProcess> getProcessMapByGroupId(String groupId) {
		Query query = em.createQuery("FROM ServiceOrderProcess s WHERE groupId = ?");
		query.setParameter(1, groupId);
		return (List<ServiceOrderProcess>)query.getResultList();
	}
	
	public List<ServiceOrderProcess> getProcessMapByGroupIdWithLock(String groupId){
		int r = 0;
		while(!getLockByGroupId(groupId, UUID.randomUUID().toString())){
			r++;
			if (r > retries ) throw new ServiceOrderException("Could not get the lock after " + retries + " number of tries. Giving up.");
			try {
				Thread.sleep(1000);//sleep for a second
			} catch (InterruptedException e) {
				//should not be thrown
				//nothing to do will resume its operation
			}
		}
		return getProcessMapByGroupId(groupId);
	}
	
	private boolean getLockByGroupId(String groupId, String guid){
		Query query = em.createQuery("update ServiceOrderProcess set groupLock = :groupLock WHERE groupId = :groupId and groupLock is null");
		query.setParameter("groupLock", guid);
		query.setParameter("groupId", groupId);
		int i = query.executeUpdate();
		return i > 0;
	}
	
	public boolean unlockByGroupId(String groupId){
		Query query = em.createQuery("update ServiceOrderProcess set groupLock = null WHERE groupId = :groupId");
		query.setParameter("groupId", groupId);
		int i = query.executeUpdate();
		return i > 0;
	}
}
