package com.servicelive.orderfulfillment.remote.test;

import com.servicelive.marketplatform.serviceinterface.IMarketPlatformNotificationBO;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.dao.ServiceOrderDao;
import com.servicelive.orderfulfillment.remote.test.assertions.OrderFulfillmentAssertions;
import com.servicelive.orderfulfillment.test.helper.TestHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CommonTestCfg {
    protected ApplicationContext context;

    public void init() {
        Properties sysProperties = new Properties();

        try {
            InputStream is = loadResourceStream("test-settings.properties");
            sysProperties.load(is);
        } catch (IOException e) {
            Assert.fail("Configuration problem: unable to load resource [test-settings.properties]");
        }

        try{
            InputStream inStreamLocalProperties = loadResourceStream("local-settings.properties");
            if(null != inStreamLocalProperties)
                sysProperties.load(inStreamLocalProperties);
        }catch(IOException e){
            /*Its ok. If local.properties has not been defined we will ignore them. Otherwise use the overrides.*/
        }

        System.getProperties().putAll(sysProperties);
        
        context = new ClassPathXmlApplicationContext("/spring/RemoteTestApplicationContext.xml");
    }

    private InputStream loadResourceStream(String resourceName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
    }

    public DataFactory getDataFactory() {
        return (DataFactory) context.getBean("dataFactory");
    }

    public OFHelper getOFHelper() {
        return (OFHelper) context.getBean("ofHelper");
    }

    public ServiceOrderDao getServiceOrderDao() {
        return (ServiceOrderDao) context.getBean("serviceOrderDao");
    }

    public TestHelper getTestHelper() {
        return (TestHelper) context.getBean("testHelper");
    }

    public OrderFulfillmentAssertions getOrderFulfillmentAssertions() {
        return (OrderFulfillmentAssertions) context.getBean("orderFulfillmentAssertions");
    }

    public IMarketPlatformNotificationBO getNotificationServiceClient() {
        return (IMarketPlatformNotificationBO) context.getBean("notificationService");
    }
}
