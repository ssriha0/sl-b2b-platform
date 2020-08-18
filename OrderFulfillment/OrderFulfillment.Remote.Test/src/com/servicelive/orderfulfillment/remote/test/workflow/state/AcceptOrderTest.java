package com.servicelive.orderfulfillment.remote.test.workflow.state;

import com.servicelive.orderfulfillment.remote.test.assertions.TestCommand;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AcceptOrderTest extends PostOrderTest {
    @BeforeClass(alwaysRun = true)
    public void init() {
        super.init();
    }

    @Test(dependsOnMethods={"postServiceOrder"})
    public void acceptServiceOrder(){
        acceptedProvider = testCfg.getDelegate().acceptServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, routedProviders);
        testCfg.getTestHelper().waitForState(serviceOrderId, "Accepted");
        testCfg.getTestHelper().waitSeconds(2);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.ACCEPT_SERVICE_ORDER);
    }
}