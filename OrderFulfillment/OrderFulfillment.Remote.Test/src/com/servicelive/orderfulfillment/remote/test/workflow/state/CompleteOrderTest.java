package com.servicelive.orderfulfillment.remote.test.workflow.state;

import com.servicelive.orderfulfillment.remote.test.assertions.TestCommand;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CompleteOrderTest extends ActivateOrderTest {
    @BeforeClass(alwaysRun = true)
    public void init() {
        super.init();
    }

    @Test(dependsOnMethods={"activateOrder"})
    public void completeOrder(){
        testCfg.getDelegate().completeServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, acceptedProvider);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.COMPLETE_SERVICE_ORDER);
    }
}