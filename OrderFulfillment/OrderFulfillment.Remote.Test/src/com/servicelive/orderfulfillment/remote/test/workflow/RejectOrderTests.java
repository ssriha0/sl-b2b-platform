package com.servicelive.orderfulfillment.remote.test.workflow;

import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.remote.test.assertions.TestCommand;
import com.servicelive.orderfulfillment.remote.test.workflow.state.PostOrderTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RejectOrderTests extends PostOrderTest {
    @Override
    @BeforeClass(alwaysRun = true)
    public void init() {
        super.init();
    }

    @Test(dependsOnMethods={"postServiceOrder"})
    public void rejectPostedOrder() {
        testCfg.getDelegate().rejectServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, routedProviders);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.REJECT_POSTED_ORDER);
    }

    @Test(dependsOnMethods={"postServiceOrder"})
    public void rejectPostedOrderByAll() {
        for (RoutedProvider routedProvider : routedProviders) {
            testCfg.getDelegate().rejectServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, routedProvider);
        }
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.REJECT_POSTED_ORDER_BY_ALL);
    }

    @Test(dependsOnMethods={"createServiceOrder"})
    public void setServiceOrderSpendingLimit() {
        testCfg.getDelegate().setServiceOrderSpendingLimit(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.EDIT_DRAFT_SERVICE_ORDER);
    }

    @Test(dependsOnMethods={"setServiceOrderSpendingLimit"})
    public void postServiceOrderBid() {
        routedProviders = testCfg.getDelegate().postServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId);
        testCfg.getTestHelper().waitForState(serviceOrderId, "BidPosted");
        // test assertions
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.POST_SERVICE_ORDER_BID);
    }

    @Test(dependsOnMethods={"postServiceOrderBid"})
    public void rejectBidPostedOrder() {
        testCfg.getDelegate().rejectServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, routedProviders);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.REJECT_BIDPOSTED_ORDER);
    }

    @Test(dependsOnMethods={"postServiceOrderBid"})
    public void rejectBidPostedOrderByAll() {
        for (RoutedProvider routedProvider : routedProviders) {
            testCfg.getDelegate().rejectServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, routedProvider);
        }
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.REJECT_BIDPOSTED_ORDER_BY_ALL);
    }
}
