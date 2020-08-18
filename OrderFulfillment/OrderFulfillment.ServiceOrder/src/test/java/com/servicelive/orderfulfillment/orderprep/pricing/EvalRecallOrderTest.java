package com.servicelive.orderfulfillment.orderprep.pricing;



import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import junit.framework.Assert;
import org.jbpm.api.model.OpenExecution;
import org.junit.Before;
import org.junit.Test;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.dao.ServiceOrderDao;
import com.servicelive.orderfulfillment.decision.EvaluateRecallOrder;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.ServiceOrder;


public class EvalRecallOrderTest {
    private IServiceOrderDao serviceOrderDao;
    private EvaluateRecallOrder evaluateRecallOrder;
    ServiceOrder serviceOrder;
    OpenExecution execution;
    String soId;
    Integer spnId;
    Long inhomeAcceptedFirmId;
    String criteria;
    List<SOCustomReference> custList;
    
   
    @Before
	public void setUp() {
    	serviceOrderDao =mock(ServiceOrderDao.class);
    	evaluateRecallOrder = new  EvaluateRecallOrder();
    	evaluateRecallOrder.setServiceOrderDao(serviceOrderDao);
    	soId ="516-2803-3250-11";
    	spnId = 75;
    	inhomeAcceptedFirmId =10202L;
    	execution = mock(OpenExecution.class);
    	serviceOrder =new ServiceOrder();
    	serviceOrder.setSoId(soId);
    	serviceOrder.setSpnId(spnId);
    	serviceOrder.setInhomeAcceptedFirm(inhomeAcceptedFirmId);
    	criteria="carRoute";
    }
	
    @Test
    public void eavluateProviderFirmCompliance() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    	when(serviceOrderDao.validateFirmStatus(serviceOrder.getInhomeAcceptedFirm())).thenReturn(false);
    	when(serviceOrderDao.validateFirmSpnCompliance(serviceOrder.getSpnId(), serviceOrder.getInhomeAcceptedFirm())).thenReturn(false);
    	Class<? extends EvaluateRecallOrder> clazz = evaluateRecallOrder.getClass();
    	Method myMethod = clazz.getDeclaredMethod("eavluateProviderFirmCompliance",ServiceOrder.class,String.class,OpenExecution.class);
    	myMethod.setAccessible(true);
    	String result = (String) myMethod.invoke(evaluateRecallOrder,serviceOrder,soId, execution);
    	Assert.assertEquals(criteria, result);
	}

}
