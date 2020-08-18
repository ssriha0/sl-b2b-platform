package com.servicelive.orderfulfillment.remote.test.workflow.state;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AcceptOrderNoWaitTest extends PostOrderTest {
    @BeforeClass(alwaysRun = true)
    public void init() {
        super.init();
    }

    @Test(dependsOnMethods={"postServiceOrder"})
    public void acceptServiceOrderNoWait(){
        acceptedProvider = testCfg.getDelegate().acceptServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, routedProviders);
    }
}
