package com.servicelive.orderfulfillment.remote.test;

import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class ConcurrencyTests {
    private DataFactory dataFactory;
    private OFHelper ofHelper;
    private TestDelegate delegate = new TestDelegate();    

    //Number of threads:
    private static final int THREAD_GROUP_SIZE=5;
    //Number of times to execute the tests:
    private static final int INVOCATION_COUNT=THREAD_GROUP_SIZE * 1;
    
    @BeforeClass(alwaysRun = true)
    public void setUp() {
        CommonTestCfg cfg = new CommonTestCfg();
        cfg.init();
        dataFactory = cfg.getDataFactory();
        ofHelper = cfg.getOFHelper();
    }

    @Test(groups={"concurrent","integration"},invocationCount=INVOCATION_COUNT, threadPoolSize=THREAD_GROUP_SIZE)
    public void executeWorkflow(){
        String soId = delegate.createServiceOrder(dataFactory, ofHelper);
        List<RoutedProvider> routedProviders = delegate.postServiceOrder(dataFactory, ofHelper, soId);
        delegate.acceptServiceOrder(dataFactory, ofHelper, soId, routedProviders);
    }
}
