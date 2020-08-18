package com.servicelive.orderfulfillment.remote.test.workflow.state;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.remote.test.assertions.TestCommand;
import com.servicelive.orderfulfillment.remote.test.config.TestCfg;

public class CreateDraftOrderTest {
    protected String serviceOrderId = "";
    protected List<RoutedProvider> routedProviders;
    protected RoutedProvider acceptedProvider;
    protected final TestCfg testCfg = TestCfg.getInstance();

    @BeforeClass(alwaysRun = true)
    public void init() {
        testCfg.init();
    }

    @Test()
    public void createServiceOrder() {
        serviceOrderId = testCfg.getDelegate().createServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper());
        ServiceOrder serviceOrder = testCfg.getServiceOrderDao().getServiceOrder(serviceOrderId);
        // test assertions
        Assert.assertNotNull(serviceOrder);
    }

    @Test(dependsOnMethods = {"createServiceOrder"})
    public void editDraftOrder() {
        testCfg.getDelegate().editServiceOrder(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.EDIT_DRAFT_SERVICE_ORDER);
    }
}