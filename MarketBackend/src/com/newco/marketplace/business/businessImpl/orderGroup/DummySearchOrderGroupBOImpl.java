package com.newco.marketplace.business.businessImpl.orderGroup;

import com.newco.marketplace.business.businessImpl.so.order.ServiceOrderBO;
import com.newco.marketplace.persistence.daoImpl.orderGroup.OrderGroupDaoImpl;

public class DummySearchOrderGroupBOImpl {
	
	private OrderGroupDaoImpl orderGroupDAO;
	private ServiceOrderBO serviceOrderBo;
	
	public boolean justReturn() {
		return true;
	}

	public void setOrderGroupDAO(OrderGroupDaoImpl orderGroupDAO) {
		this.orderGroupDAO = orderGroupDAO;
	}

	public void setServiceOrderBo(ServiceOrderBO serviceOrderBo) {
		this.serviceOrderBo = serviceOrderBo;
	}

	
}
