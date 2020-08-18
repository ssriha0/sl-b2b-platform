package com.servicelive.orderfulfillment.remote.test.workflow.state;

import com.servicelive.orderfulfillment.remote.test.assertions.TestCommand;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PostOrderTest extends CreateDraftOrderTest {
    @BeforeClass(alwaysRun = true)
    public void init() {
        super.init();
    }

    @Test(dependsOnMethods={"editDraftOrder"})
    public void postServiceOrder() {
        routedProviders = testCfg.getDelegate().postServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId);
        testCfg.getTestHelper().waitForState(serviceOrderId, "Posted");
        testCfg.getTestHelper().waitSeconds(2);
        // test assertions
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.POST_SERVICE_ORDER);
    }
}
