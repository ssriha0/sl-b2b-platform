package com.servicelive.routingrulesengine.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.exception.DataServiceException;
import com.servicelive.domain.routingrules.RoutingRuleCriteria;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.RoutingRuleSwitches;
import com.servicelive.routingrulesengine.dao.RoutingRuleBuyerAssocDao;
import com.servicelive.routingrulesengine.dao.impl.RoutingRuleBuyerAssocDaoImpl;
import com.servicelive.routingrulesengine.services.impl.OrderProcessingServiceImpl;




/**
 * This is the test class to test getConditionalRoutingRule() method
 *
 */
public class OrderProcessingServiceImplTest {
	
	private OrderProcessingServiceImpl orderProcessingServiceImpl = null;
	private RoutingRuleBuyerAssocDao routingRuleBuyerAssocDao = null;
	
	@Before
	public void setUp() throws DataServiceException {
		
		orderProcessingServiceImpl = new OrderProcessingServiceImpl();
		
		routingRuleBuyerAssocDao = mock(RoutingRuleBuyerAssocDaoImpl.class);
		orderProcessingServiceImpl.setRoutingRuleBuyerAssocDao(routingRuleBuyerAssocDao);
		
		Integer buyerId = 3000;
		Integer routingRuleBuyerAssocId = 3;
		String zip = "30076";
		String marketId = "373";
		String jobcode = "LOGSPLIT151Y"; 
		String sortOrder = "DESC"; 
		
		Set<String> ispIds = new HashSet<String>();
		ispIds.add("80001");
		
		RoutingRuleSwitches ruleSwitch = new RoutingRuleSwitches();
		ruleSwitch.setSwitchName("SortOrderSwitch");
		ruleSwitch.setValue(true);
		
		List<RoutingRuleSwitches> resultList = new ArrayList<RoutingRuleSwitches>();
		resultList.add(ruleSwitch);
				
		RoutingRuleCriteria zipCriteria = new RoutingRuleCriteria();
		zipCriteria.setCriteriaName("ZIP");
		zipCriteria.setCriteriaValue("30076");
		
		RoutingRuleCriteria ispIdCriteria = new RoutingRuleCriteria();
		ispIdCriteria.setCriteriaName("ISP_ID");
		ispIdCriteria.setCriteriaValue("80001");
		
		RoutingRuleCriteria orderCriteria = new RoutingRuleCriteria();
		orderCriteria.setCriteriaName("OrderNumber");
		orderCriteria.setCriteriaValue("2000");
		
		List<RoutingRuleCriteria> routingRuleCriteria = new ArrayList<RoutingRuleCriteria>();
		routingRuleCriteria.add(zipCriteria);
		routingRuleCriteria.add(ispIdCriteria);		
		routingRuleCriteria.add(orderCriteria);		
		
		RoutingRuleHdr routingRuleHdr = new RoutingRuleHdr();
		routingRuleHdr.setRoutingRuleHdrId(6044);
		routingRuleHdr.setRoutingRuleCriteria(routingRuleCriteria);
		
		List<RoutingRuleHdr> routingRuleHdrs = new ArrayList<RoutingRuleHdr>();
		routingRuleHdrs.add(routingRuleHdr);
		
		when(routingRuleBuyerAssocDao.findSortOrder()).thenReturn(resultList);
		when(routingRuleBuyerAssocDao.findRoutingRuleBuyerAssocId(buyerId)).thenReturn(routingRuleBuyerAssocId);
		when(routingRuleBuyerAssocDao.findMarketId(zip)).thenReturn(marketId);
		when(routingRuleBuyerAssocDao.findRoutingRuleForInhome(routingRuleBuyerAssocId, jobcode, zip, marketId, ispIds, sortOrder)).thenReturn(routingRuleHdrs);
		
	}
	
	@Test
	public void testGetConditionalRoutingRule() throws Exception{
		
		String jobcode = "LOGSPLIT151Y"; 
		
		Buyer buyer = new Buyer();
		buyer.setBuyerId(3000);
		
		SoLocation location = new SoLocation();
		location.setZip("30076");
		
		ServiceOrderCustomRefVO ispId = new ServiceOrderCustomRefVO();
		ispId.setRefType("ISP_ID");
		ispId.setRefValue("80001");
		
		ServiceOrderCustomRefVO orderNo = new ServiceOrderCustomRefVO();
		orderNo.setRefType("OrderNumber");
		orderNo.setRefValue("2000");
		
		List<ServiceOrderCustomRefVO> customRefs = new ArrayList<ServiceOrderCustomRefVO>();
		customRefs.add(ispId);
		customRefs.add(orderNo);
		
		ServiceOrder so = new ServiceOrder();
		so.setBuyer(buyer);
		so.setUpdate(false);
		so.setServiceLocation(location);
		so.setSoId("531-1477-9075-19");
		so.setCustomRefs(customRefs);
				
		String rule = orderProcessingServiceImpl.getConditionalRoutingRule(so, jobcode).getRoutingRuleHdrId().toString();
		Assert.assertEquals("6044", rule);
			
	}
	
}
