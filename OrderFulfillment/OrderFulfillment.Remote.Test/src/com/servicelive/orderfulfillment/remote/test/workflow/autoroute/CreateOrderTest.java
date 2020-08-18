package com.servicelive.orderfulfillment.remote.test.workflow.autoroute;

import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.remote.test.config.TestCfg;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class CreateOrderTest {
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
        serviceOrderId = testCfg.getDelegate().createServiceOrderForAutoRouting(testCfg.getDataFactory(), testCfg.getOfHelper());
        ServiceOrder serviceOrder = testCfg.getServiceOrderDao().getServiceOrder(serviceOrderId);
        // test assertions
        Assert.assertNotNull(serviceOrder);
    }
}
