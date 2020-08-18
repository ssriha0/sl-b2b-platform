package com.servicelive.orderfulfillment.remote.test.config;

import com.servicelive.marketplatform.common.vo.ServiceOrderNotificationTask;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformNotificationBO;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.dao.ServiceOrderDao;
import com.servicelive.orderfulfillment.remote.test.DataFactory;
import com.servicelive.orderfulfillment.remote.test.TestDelegate;
import com.servicelive.orderfulfillment.remote.test.assertions.OrderFulfillmentAssertions;
import com.servicelive.orderfulfillment.test.helper.TestHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestCfg {
    ApplicationContext context;
    DataFactory dataFactory;
    OFHelper ofHelper;
    ServiceOrderDao serviceOrderDao;
    TestHelper testHelper;
    OrderFulfillmentAssertions ofAssert;
    IMarketPlatformNotificationBO notificationService;
    TestDelegate delegate;

    private Boolean initialized = new Boolean(false);

    private static TestCfg instance;

    static {
        instance = new TestCfg();
    }

    private TestCfg() {}

    public static TestCfg getInstance() {
        return instance;
    }

    public void init() {
        synchronized (initialized) {
            if (!initialized.booleanValue()) {
                setupProperties();
                setupTestContext();
                //testNotificationTaskListener();
                initialized = new Boolean(true);
            }
        }
    }

    public void setupProperties() {
        Properties sysProperties = new Properties();

        try {
            InputStream is = loadResourceStream("test-settings.properties");
            sysProperties.load(is);
        } catch (IOException e) {
            Assert.fail("Configuration problem: unable to load resource [test-settings.properties]");
        }

        try {
            InputStream inStreamLocalProperties = loadResourceStream("local-settings.properties");
            if (null != inStreamLocalProperties)
                sysProperties.load(inStreamLocalProperties);
        } catch (IOException e) {
            /*Its ok. If local.properties has not been defined we will ignore them. Otherwise use the overrides.*/
        }

        System.getProperties().putAll(sysProperties);
    }

    public void setupTestContext() {
        try {
            delegate = new TestDelegate();
            context = new ClassPathXmlApplicationContext("/spring/RemoteTestApplicationContext.xml");
            dataFactory = (DataFactory) context.getBean("dataFactory");
            ofHelper = (OFHelper) context.getBean("ofHelper");
            serviceOrderDao = (ServiceOrderDao) context.getBean("serviceOrderDao");
            testHelper = (TestHelper) context.getBean("testHelper");
            ofAssert = (OrderFulfillmentAssertions) context.getBean("orderFulfillmentAssertions");
            notificationService = (IMarketPlatformNotificationBO) context.getBean("notificationService");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void testNotificationTaskListener() {
        ServiceOrderNotificationTask notificationTask = new ServiceOrderNotificationTask();
        notificationTask.setServiceOrderId("test-service-order");
        notificationTask.setTemplateId(-1L);
        notificationService.queueNotificationTask(notificationTask);
        testHelper.waitForNotificationTask(notificationTask.getServiceOrderId());
        Assert.assertTrue(testHelper.taskExistsWithTemplateForSO(notificationTask.getServiceOrderId(), -1L), "notification task listener test");
    }

    InputStream loadResourceStream(String resourceName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
    }

    public ServiceOrderDao getServiceOrderDao() {
        return serviceOrderDao;
    }

    public OFHelper getOfHelper() {
        return ofHelper;
    }

    public DataFactory getDataFactory() {
        return dataFactory;
    }

    public TestHelper getTestHelper() {
        return testHelper;
    }

    public OrderFulfillmentAssertions getOfAssert() {
        return ofAssert;
    }

    public TestDelegate getDelegate() {
        return delegate;
    }
}