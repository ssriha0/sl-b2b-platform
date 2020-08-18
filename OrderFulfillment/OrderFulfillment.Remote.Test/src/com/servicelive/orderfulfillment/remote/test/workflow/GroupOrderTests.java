package com.servicelive.orderfulfillment.remote.test.workflow;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.remote.test.assertions.TestCommand;
import com.servicelive.orderfulfillment.remote.test.config.TestCfg;

public class GroupOrderTests {
    protected String serviceOrderId = "";
    protected List<String> serviceOrderIds = new ArrayList<String>();
    protected RoutedProvider acceptedProvider;
    protected final TestCfg testCfg = TestCfg.getInstance();

    @BeforeClass(alwaysRun = true)
    public void init() {
        testCfg.init();
    }
    
    @Test()
    public void createServiceOrderTest() {
    	createServiceOrder();
    }

    @Test(dependsOnMethods = {"createServiceOrderTest"})
    public void deleteServiceOrder(){
        testCfg.getDelegate().cancelServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, "deleting the order");
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.DELETE_SERVICE_ORDER);
    }
    
    @Test()
    public void createGroupServiceOrder() {
    	createServiceOrder();
    	testCfg.getTestHelper().waitSeconds(1);
    	createServiceOrder();
    }

    @Test(dependsOnMethods = {"createGroupServiceOrder"})
    public void deleteLastGroupServiceOrder(){
        testCfg.getDelegate().cancelServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, "deleting the order");
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.DELETE_SERVICE_ORDER);
    }
    
    private void createServiceOrder(){
        serviceOrderId = testCfg.getDelegate().createServiceOrderForAutoRouting(testCfg.getDataFactory(), testCfg.getOfHelper());
        ServiceOrder serviceOrder = testCfg.getServiceOrderDao().getServiceOrder(serviceOrderId);
        // test assertions
        Assert.assertNotNull(serviceOrder);
        testCfg.getTestHelper().waitForState(serviceOrderId, "Draft");
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.CREATE_SERVICE_ORDER);
    }
    
    @Test
    public void createGroupServiceOrderFromFile(){
    	serviceOrderId = testCfg.getDelegate().createServiceOrdersFromFile("./test-input/ThreeGroupServiceOrders.xml", testCfg.getDataFactory(), testCfg.getOfHelper(), 1);
        ServiceOrder serviceOrder = testCfg.getServiceOrderDao().getServiceOrder(serviceOrderId);
        Assert.assertNotNull(serviceOrder.getSoGroup());
        SOGroup group = testCfg.getDataFactory().getGroup(serviceOrder.getSoGroup().getGroupId());
        for(ServiceOrder tmpSo:group.getServiceOrders()){
            serviceOrderIds.add(tmpSo.getSoId());
        }
    }

    @Test(dependsOnMethods = {"createGroupServiceOrderFromFile"})
    public void acceptGroupServiceOrder(){
        testCfg.getTestHelper().waitSeconds(75);
        ServiceOrder so = testCfg.getDataFactory().getServiceOrder(serviceOrderId);
        String groupId = so.getSoGroup().getGroupId();
        RoutedProvider acceptedProvider = so.getRoutedResources().get(0);
        testCfg.getDelegate().acceptGroupOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), groupId, acceptedProvider);
    }

    @Test(dependsOnMethods = {"acceptGroupServiceOrder"})
    public void releaseGroupServiceOrder(){
        for(String serviceOrderId:serviceOrderIds){
            ServiceOrder serviceOrder = testCfg.getDataFactory().getServiceOrder(serviceOrderId);
            RoutedProvider acceptedProvider = serviceOrder.getAcceptedResource();
            testCfg.getDelegate().releaseOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrder.getSoId(), acceptedProvider);
        }
        ServiceOrder serviceOrder = testCfg.getServiceOrderDao().getServiceOrder(serviceOrderId);
        Assert.assertNotNull(serviceOrder.getSoGroup());
    }
    
    @Test
    public void createNotGroupServiceOrderFromFile(){
    	serviceOrderId = testCfg.getDelegate().createServiceOrdersFromFile("./test-input/NotGroupedServiceOrders.xml", testCfg.getDataFactory(), testCfg.getOfHelper(), 1);
    	
    	testCfg.getTestHelper().waitForState(serviceOrderId, "Draft");
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.CREATE_SERVICE_ORDER);
        ServiceOrder serviceOrder = testCfg.getServiceOrderDao().getServiceOrder(serviceOrderId);
        Assert.assertNull(serviceOrder.getSoGroup());
    }
}
