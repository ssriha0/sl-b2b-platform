package com.servicelive.orderfulfillment.remote.test.workflow;

import java.util.Calendar;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.remote.test.workflow.state.CreateDraftOrderTest;

public class ConditionalOfferTests extends CreateDraftOrderTest {
    @Override
    @BeforeClass(alwaysRun = true)
    public void init() {
        super.init();
    }


    @Test(dependsOnMethods={"editDraftOrder"})
    public void postServiceOrderBid() {
        routedProviders = testCfg.getDelegate().postServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId);
        Assert.assertFalse(routedProviders.isEmpty());
    }

    @Test(dependsOnMethods={"postServiceOrderBid"})
    public void createConditionalOfferForBid(){
        testCfg.getDelegate().createConditionalOffer(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, routedProviders);
    }

    @Test(dependsOnMethods={"createConditionalOfferForBid"})
    public void acceptServiceOrderConditionally(){
        testCfg.getDelegate().buyerAcceptServiceOrderConditionally(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, routedProviders);
        testCfg.getTestHelper().waitForState(serviceOrderId, "Accepted");
        Calendar expected = Calendar.getInstance();
        expected.set(Calendar.YEAR,2012);
        expected.set(Calendar.MONTH,Calendar.JANUARY);
        expected.set(Calendar.DAY_OF_MONTH,1);
        Calendar actual = Calendar.getInstance();
        ServiceOrder so = testCfg.getDataFactory().getServiceOrder(serviceOrderId);
        actual.setTimeInMillis(so.getSchedule().getServiceDate1().getTime());
        Assert.assertTrue(expected.get(Calendar.DAY_OF_MONTH)==actual.get(Calendar.DAY_OF_MONTH),"Days do not match!");
        Assert.assertTrue(expected.get(Calendar.MONTH)==actual.get(Calendar.MONTH),"Months do not match!");
    }
}