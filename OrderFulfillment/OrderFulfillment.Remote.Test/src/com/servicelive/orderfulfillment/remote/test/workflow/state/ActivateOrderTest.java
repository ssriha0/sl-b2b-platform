package com.servicelive.orderfulfillment.remote.test.workflow.state;

import com.servicelive.orderfulfillment.remote.test.assertions.TestCommand;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ActivateOrderTest extends AcceptOrderTest {
    @BeforeClass(alwaysRun = true)
    public void init() {
        super.init();
    }

    @Test(dependsOnMethods={"acceptServiceOrder"})
    public void activateOrder(){
        testCfg.getDelegate().activateServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.ACTIVATE_SERVICE_ORDER);
    }
}