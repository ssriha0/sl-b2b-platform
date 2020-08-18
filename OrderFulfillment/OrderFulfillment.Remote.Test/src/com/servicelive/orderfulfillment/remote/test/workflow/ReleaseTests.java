package com.servicelive.orderfulfillment.remote.test.workflow;

import com.servicelive.orderfulfillment.remote.test.assertions.TestCommand;
import com.servicelive.orderfulfillment.remote.test.workflow.state.ActivateOrderTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ReleaseTests extends ActivateOrderTest {
    @Override
    @BeforeClass(alwaysRun = true)
    public void init() {
        super.init();
    }

    @Test(dependsOnMethods={"acceptServiceOrder"})
    public void providerReleasedOrderInAccepted(){
    	testCfg.getDelegate().releaseOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, acceptedProvider);
        testCfg.getTestHelper().waitForState(serviceOrderId, "Posted");
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.PROVIDER_RELEASE_IN_ACCEPTED);
    }

    @Test(dependsOnMethods={"activateOrder"})
    public void providerReleasedOrderInActive(){
    	testCfg.getDelegate().releaseOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, acceptedProvider);
        testCfg.getTestHelper().waitForState(serviceOrderId, "Posted");
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.PROVIDER_RELEASE_IN_ACTIVE);
    }

    @Test(dependsOnMethods = {"acceptServiceOrder"})
    public void buyerReportProblemInAccept(){
        testCfg.getDelegate().buyerReportProblem(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.BUYER_REPORT_PROBLEM_IN_ACCEPTED);
    }

    @Test(dependsOnMethods={"buyerReportProblemInAccept"})
    public void providerReleasedOrderInProblem(){
    	testCfg.getDelegate().releaseOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, acceptedProvider);
        testCfg.getTestHelper().waitForState(serviceOrderId, "Posted");
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.PROVIDER_RELEASE_IN_PROBLEM);
    }
}