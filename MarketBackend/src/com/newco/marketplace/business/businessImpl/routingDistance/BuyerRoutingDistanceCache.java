package com.newco.marketplace.business.businessImpl.routingDistance;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;

public class BuyerRoutingDistanceCache implements InitializingBean {

	protected final Logger logger = Logger.getLogger(getClass());

	IBuyerBO buyerBo;

	private static Map<Long, Long> routingDistanceCacheMapForBuyer = new HashMap<Long, Long>();

	public void afterPropertiesSet() throws Exception {
		logger.info("Into BuyerRoutingDistanceCache.afterPropertiesSet(");
		loadBuyerRoutingDistance();
	}

	private void loadBuyerRoutingDistance() {
		logger.info("Into BuyerRoutingDistanceCache.loadBuyerRoutingDistance()");
		logger.info("Initializing buyer Routing distance map ");
		try {
			routingDistanceCacheMapForBuyer = buyerBo.getBuyerRoutingDistance();
		} catch (BusinessServiceException e) {
			logger.info(" Error occureed in BuyerRoutingDistanceCache.loadBuyerRoutingDistance() ");
			e.printStackTrace();
		}
		logger.info("routingDistanceCacheMapForBuyer = "
				+ routingDistanceCacheMapForBuyer.toString());
	}

	public Integer getBuyerRoutingDistance(Integer buyerId) {
		logger.info("Into BuyerRoutingDistanceCache.getBuyerRoutingDistance()");
		Integer routingRadius = null;
		Long buyer = new Long(buyerId);
		String distance;
		if (null != routingDistanceCacheMapForBuyer
				&& null != routingDistanceCacheMapForBuyer.get(buyer)) {
			distance = routingDistanceCacheMapForBuyer.get(buyer).toString();
			routingRadius = Integer.valueOf(distance);
			logger.info(" Setting routing radius : " + routingRadius);
		} else {
			routingRadius = OrderConstants.SO_ROUTE_CRITERIA_DIST;
			logger.info(" Setting default routing radius : " + routingRadius);
		}
		return routingRadius;
	}

	public IBuyerBO getBuyerBo() {
		return buyerBo;
	}

	public void setBuyerBo(IBuyerBO buyerBo) {
		this.buyerBo = buyerBo;
	}
	
}
