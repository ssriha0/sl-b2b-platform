package com.servicelive.orderfulfillment.remote.test.workflow;

import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.remote.test.workflow.state.PostOrderTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * User: Mustafa Motiwala
 * Date: Apr 1, 2010
 * Time: 3:59:34 PM
 */
public class SubStatusTest extends PostOrderTest {
    @BeforeClass(alwaysRun = true)
    public void init() {
        super.init();
    }

    @Test(dependsOnMethods={"postServiceOrder"})
    public void changeSubStatus() {
        testCfg.getDelegate().changeSubStatus(testCfg.getDataFactory(), testCfg.getOfHelper(),serviceOrderId, 16);
        ServiceOrder serviceOrder = testCfg.getServiceOrderDao().getServiceOrder(serviceOrderId);
        Assert.assertEquals(serviceOrder.getWfSubStatusId(),new Integer(16));
    }

//    @Test(dependsOnMethods={"editDraftOrder"})
//    public void postServiceOrder() {
//        routedProviders = testCfg.getDelegate().postServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId);
//        testCfg.getTestHelper().waitForState(serviceOrderId, "Posted");
//        testCfg.getTestHelper().waitSeconds(2);
//        // test assertions
//        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.POST_SERVICE_ORDER);
//    }
    
}
