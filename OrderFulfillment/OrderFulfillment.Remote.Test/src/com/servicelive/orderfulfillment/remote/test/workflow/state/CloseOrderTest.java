package com.servicelive.orderfulfillment.remote.test.workflow.state;

import com.servicelive.orderfulfillment.remote.test.assertions.TestCommand;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CloseOrderTest extends CompleteOrderTest {
    @BeforeClass(alwaysRun = true)
    public void init() {
        super.init();
    }

    @Test(dependsOnMethods={"completeOrder"})
    public void closeOrder(){
        testCfg.getDelegate().closeServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.CLOSE_SERVICE_ORDER);
    }
}