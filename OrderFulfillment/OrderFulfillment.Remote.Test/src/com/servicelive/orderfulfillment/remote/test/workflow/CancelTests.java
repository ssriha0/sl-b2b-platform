package com.servicelive.orderfulfillment.remote.test.workflow;

import com.servicelive.orderfulfillment.remote.test.assertions.TestCommand;
import com.servicelive.orderfulfillment.remote.test.workflow.state.CloseOrderTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CancelTests extends CloseOrderTest {

    @Override
    @BeforeClass(alwaysRun = true)
    public void init() {
        super.init();
    }

    @Test(dependsOnMethods={"postServiceOrder"})
    public void voidServiceOrder(){
        testCfg.getDelegate().voidServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.VOID_SERVICE_ORDER);
    }

    @Test(dependsOnMethods={"acceptServiceOrder"})
    public void cancelServiceOrder() {
        testCfg.getDelegate().cancelServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, "testing cancellation from accepted state");
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.CANCEL_SERVICE_ORDER);
    }

    @Test(dependsOnMethods={"requestServiceOrderCancellation"})
    public void completeOrderAfterCancelRequest() {
        testCfg.getDelegate().completeServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, acceptedProvider);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.COMPLETE_SERVICE_ORDER);
    }

    @Test(dependsOnMethods={"completeOrderAfterCancelRequest"})
    public void closeOrderAfterCancelRequestAndComplete(){
        testCfg.getDelegate().closeServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.CLOSE_SERVICE_ORDER);
    }

    @Test(dependsOnMethods={"activateOrder"})
    public void requestServiceOrderCancellation() {
        testCfg.getDelegate().requestServiceOrderCancellation(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, "50.00");
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.REQUEST_CANCEL_SERVICE_ORDER);
    }
}
