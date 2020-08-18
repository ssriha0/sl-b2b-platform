package com.servicelive.orderfulfillment.remote.test.workflow;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.servicelive.orderfulfillment.remote.test.assertions.TestCommand;
import com.servicelive.orderfulfillment.remote.test.workflow.state.AcceptOrderTest;

public class OnSiteVisitTests extends AcceptOrderTest {
    @Override
    @BeforeClass(alwaysRun = true)
    public void init() {
        super.init();
    }

    @Test(dependsOnMethods={"acceptServiceOrder"})
    public void providerOnSiteVisit(){
        testCfg.getDelegate().providerOnSiteVisit(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, acceptedProvider);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.INDICATE_ONSITE_VISIT);
    }
}