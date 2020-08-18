package com.servicelive.orderfulfillment.remote.test.workflow.state;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CancelPendingTransactionTest extends AcceptOrderNoWaitTest {
    @BeforeClass(alwaysRun = true)
    public void init() {
        super.init();
    }
    
	@Test // (dependsOnMethods={"acceptServiceOrderNoWait"})
	public void getPendingServiceOrdersTest() {
//		List<ServiceOrder> sos = testCfg.getDelegate().getPendingServiceOrders(testCfg.getOfHelper());
//		for( ServiceOrder so : sos ) {
//			System.out.println( so.getSoId() + ":" + so.getServiceOrderProcess().getJmsMessageId() );
//		}
	}

    @Test(dependsOnMethods={"getPendingServiceOrdersTest"})
    public void cancelWalletTransaction(){
        testCfg.getDelegate().cancelPendingWalletTransaction(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId);
        testCfg.getTestHelper().waitForState(serviceOrderId, "Draft");
        testCfg.getTestHelper().waitSeconds(2);
    }
}
