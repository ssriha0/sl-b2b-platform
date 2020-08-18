package com.servicelive.orderfulfillment.remote.test.workflow;

import com.servicelive.orderfulfillment.remote.test.assertions.TestCommand;
import com.servicelive.orderfulfillment.remote.test.workflow.state.AcceptOrderTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RescheduleTests extends AcceptOrderTest {
    @Override
    @BeforeClass(alwaysRun = true)
    public void init() {
        super.init();
    }

    @Test(dependsOnMethods={"acceptServiceOrder"})
    public void buyerRequestReschedule(){
        testCfg.getDelegate().buyerRequestReschedule(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.BUYER_REQUEST_RESCHEDULE_IN_ACCEPTED);
    }

    @Test(dependsOnMethods={"acceptServiceOrder"})
    public void providerRequestReschedule(){
        testCfg.getDelegate().providerRequestReschedule(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId,acceptedProvider);
        Assert.assertNotNull(testCfg.getDataFactory().getServiceOrder(serviceOrderId).getReschedule());
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.PROVIDER_REQUEST_RESCHEDULE_IN_ACCEPTED);
    }

    @Test(dependsOnMethods={"buyerRequestReschedule"})
    public void providerAcceptReschedule(){
        testCfg.getDelegate().providerAcceptReschedule(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, acceptedProvider);
        Assert.assertNull(testCfg.getDataFactory().getServiceOrder(serviceOrderId).getReschedule());
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.PROVIDER_ACCEPT_RESCHEDULE_IN_ACCEPTED);
    }

    @Test(dependsOnMethods={"providerRequestReschedule"})
    public void buyerAcceptReschedule(){
        testCfg.getDelegate().buyerAcceptReschedule(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId);
        Assert.assertNull(testCfg.getDataFactory().getServiceOrder(serviceOrderId).getReschedule());
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.BUYER_ACCEPT_RESCHEDULE_IN_ACCEPTED);
    }
}