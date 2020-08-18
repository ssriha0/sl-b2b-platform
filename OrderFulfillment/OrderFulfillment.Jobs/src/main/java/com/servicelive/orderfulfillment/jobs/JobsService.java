package com.servicelive.orderfulfillment.jobs;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.servicelive.orderfulfillment.vo.CacheRefreshResponse;
import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.common.ControllerForRemoteServiceStartupDependentInitializer;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.orderprep.buyer.OrderBuyerCollection;


@Path("/")
public class JobsService {
	
	private Logger logger = Logger.getLogger(getClass());
	
	private QuickLookupCollection qckLkup;
	private ControllerForRemoteServiceStartupDependentInitializer remoteServiceStartupInitializer;
	private OrderBuyerCollection orderBuyers;


	@GET
	@Path("/cache/refresh/{cacheName}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public CacheRefreshResponse refreshCache(@PathParam("cacheName") String cacheName) {
		
		/* TO BE IMPLEMENTED */
		
		CacheRefreshResponse response = new CacheRefreshResponse();
		response.setMessage("Cache name: " + cacheName);
		return response;
		
	}
	
	@GET
	@Path("/cache/refreshAll")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public CacheRefreshResponse refreshAll() {
		
		CacheRefreshResponse response = new CacheRefreshResponse();
		try {
			orderBuyers.initializeAllOrderBuyers();
			qckLkup.initializeLocalNodeLookups();
	        remoteServiceStartupInitializer.runInitializers();
	        
			response.setMessage("All caches refreshed");
		} catch (Exception e) {
			response.addError(e.getMessage());
			logger.error(e);
		}
		
		
		return response;
		
	}
	
	public void setQckLkup(QuickLookupCollection qckLkup) {
		this.qckLkup = qckLkup;
	}

	public void setRemoteServiceStartupInitializer(ControllerForRemoteServiceStartupDependentInitializer remoteServiceStartupInitializer) {
		this.remoteServiceStartupInitializer = remoteServiceStartupInitializer;
	}

	public void setOrderBuyers(OrderBuyerCollection orderBuyers) {
		this.orderBuyers = orderBuyers;
	}

}
