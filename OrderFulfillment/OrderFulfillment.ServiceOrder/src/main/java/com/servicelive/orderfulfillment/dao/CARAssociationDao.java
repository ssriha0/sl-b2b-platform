package com.servicelive.orderfulfillment.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.servicelive.domain.so.RoutingRuleVendorAssoc;
import com.servicelive.domain.so.SORoutingRuleAssoc;
import com.servicelive.marketplatform.common.vo.RoutingRuleHdrVO;
import com.servicelive.marketplatform.common.vo.RoutingRuleVendorVO;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by IntelliJ IDEA.
 * User: Yunus Burhani
 * Date: Jul 21, 2010
 * Time: 3:44:32 PM
 */
public class CARAssociationDao implements ICARAssociationDao {

    protected final Logger logger = Logger.getLogger(getClass());

    @PersistenceContext()
    private EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public SORoutingRuleAssoc getCARAssociation(String soId) {
        Query query = em.createQuery("From SORoutingRuleAssoc rra where rra.soId = ?");
        query.setParameter(1, soId);
       return (SORoutingRuleAssoc) query.setMaxResults(1).getSingleResult();  
    }

	@Transactional(propagation=Propagation.SUPPORTS)
	public SORoutingRuleAssoc getRuleId(String soId){
		String hql = "SELECT assoc.* FROM so_routing_rule_assoc assoc WHERE assoc.so_id='"+soId+"'";
		logger.info(hql);
		Query query = em.createNativeQuery(hql,SORoutingRuleAssoc.class);

		try {
			SORoutingRuleAssoc soRoutingRuleAssoc= (SORoutingRuleAssoc) query.getSingleResult();
			return soRoutingRuleAssoc;
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
			return null;
		}
	}
    
	//SL 15642 
	@Transactional(propagation=Propagation.SUPPORTS)
	public RoutingRuleHdrVO getProvidersListOfRule(Integer ruleId)
	{
		logger.info("Before flush");
		//em.flush();
		logger.info("After flush");
		//em=em.merge(em);
		String hql1 = "SELECT rrvendor.* FROM routing_rule_vendor rrvendor WHERE rrvendor.routing_rule_hdr_id='"+ruleId+"'";
		logger.info(hql1);
		Query query1 = em.createNativeQuery(hql1,RoutingRuleVendorAssoc.class);
		logger.info("Query of  provider list of rule:"+query1);

		try {
			logger.info("Get result list size:"+query1.getResultList().size());
		//	List vendorList=query1.getResultList();
		//	logger.info("Get result list size after vendorlist:"+vendorList.size()+":ResultList size:"+query1.getResultList().size());
			
//			logger.info("After iterator madhup:---> ");
		//	Iterator iterator = query1.getResultList().iterator();
			List<RoutingRuleVendorAssoc> rrva=query1.getResultList();
			List<RoutingRuleVendorVO> rrvList=new ArrayList<RoutingRuleVendorVO>();
			RoutingRuleHdrVO rrh=new RoutingRuleHdrVO();
			rrh.setRoutingRuleHdrId(ruleId);
			 if(rrva!=null) {
			//	 while(iterator.hasNext()) {
				 for(RoutingRuleVendorAssoc rrv:rrva)
				 {
		//	Object[] tuple = (Object[]) iterator.next();
	    	//logger.info("Inside For loop of routing rule vendor"+tuple.toString());
	    	RoutingRuleVendorVO rrvNew=new RoutingRuleVendorVO();
	    	logger.info("Before using tuple of auto accept status value");
	    	//logger.info("Vendor Id:"+(Integer)tuple[0]);
	    	//Object autoAccpetStatus1=tuple[1];
	    	//String str = (String)autoAccpetStatus1;
	    	//logger.info("Tintus:str:"+str);
	    	String autoAccpetStatus=rrv.getAutoAcceptStatus();
	    	logger.info("To solve class cast exception for auto accept status"+autoAccpetStatus);
	    	rrvNew.setAutoAcceptStatus(autoAccpetStatus);
	    	rrvNew.setRoutingRuleVendorId(rrv.getVendorId());
	    //	logger.info("AutoAccpet Status:"+(String)tuple[1]+"Vendor Id:"+(Integer)tuple[0]);
	    	logger.info("Values after obtaining results:Auto Status-"+rrvNew.getAutoAcceptStatus()+"VendorId"+rrvNew.getRoutingRuleVendorId());
	    	rrvList.add(rrvNew);
	    	}	
			 }
				 
			logger.info("Size of resultant class before setting in routingrulevendorVO:"+rrvList.size());
			rrh.setRoutingRuleVendor(rrvList);
			logger.info("Size of resultant class After setting in routingrulevendorVO:"+rrh.getRoutingRuleVendor().size());
				 
			 return rrh;
		}
			 
		catch (NoResultException e) {
			logger.info(e.getMessage());
			return null;
		}
	
	}
	
    public void save(SORoutingRuleAssoc soRoutingRuleAssoc) {
        em.persist(soRoutingRuleAssoc);
    }

    public void delete(SORoutingRuleAssoc soRoutingRuleAssoc) {
        em.remove(soRoutingRuleAssoc);
    }
}
