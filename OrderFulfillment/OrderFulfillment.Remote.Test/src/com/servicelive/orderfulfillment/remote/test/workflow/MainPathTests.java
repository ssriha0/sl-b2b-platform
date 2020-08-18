package com.servicelive.orderfulfillment.remote.test.workflow;

import com.servicelive.orderfulfillment.remote.test.assertions.TestCommand;
import com.servicelive.orderfulfillment.remote.test.workflow.state.CloseOrderTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MainPathTests extends CloseOrderTest {
    @Override
    @BeforeClass(alwaysRun = true)
    public void init() {
        super.init();
    }

    @Test(dependsOnMethods={"completeOrder"})
    public void recallOrderCompletion(){
        testCfg.getDelegate().recallOrderCompletion(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, acceptedProvider);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.RECALL_COMPLETION);
    }

    @Test(dependsOnMethods={"recallOrderCompletion"})
    public void completeOrderAfterRecall() {
        testCfg.getDelegate().completeServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, acceptedProvider);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.COMPLETE_SERVICE_ORDER);
    }

    @Test(dependsOnMethods={"completeOrderAfterRecall"})
    public void closeAfterRecall() {
        testCfg.getDelegate().closeServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.CLOSE_SERVICE_ORDER);
    }
}
