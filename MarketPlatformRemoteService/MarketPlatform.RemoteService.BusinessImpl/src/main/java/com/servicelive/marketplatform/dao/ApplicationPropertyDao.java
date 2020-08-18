package com.servicelive.marketplatform.dao;

import com.servicelive.domain.common.ApplicationFlags;
import com.servicelive.domain.common.ApplicationProperties;
import com.servicelive.domain.common.InHomeOutBoundMessages;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: yburhani
 * Date: Jun 18, 2010
 * Time: 4:31:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationPropertyDao extends BaseEntityDao implements IApplicationPropertyDao{

    public List<ApplicationProperties> getAllApplicationProperties() {
        Query query = entityMgr.createQuery("SELECT props FROM ApplicationProperties props");
        return (List<ApplicationProperties>) query.getResultList();  
    }

	public List<ApplicationFlags> getAllApplicationFlags() {
		Query query = entityMgr.createQuery("SELECT flags FROM ApplicationFlags flags");
        return (List<ApplicationFlags>) query.getResultList(); 
	}

	public List<InHomeOutBoundMessages> getOutBoundStatusMessages() {
		Query query = entityMgr.createQuery("SELECT messages FROM InHomeOutBoundMessages messages where messages.wfSubStatus is null");
        return (List<InHomeOutBoundMessages>) query.getResultList(); 
	}
	
}
