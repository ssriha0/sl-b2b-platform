package com.servicelive.orderfulfillment.remote.test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.util.TimeChangeUtil;
import com.servicelive.orderfulfillment.test.helper.TestHelper;
import com.servicelive.wallet.serviceinterface.vo.LedgerEntryType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MiscTests {
    private DataFactory dataFactory;
    private OFHelper ofHelper;
    TestHelper testHelper;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        CommonTestCfg cfg = new CommonTestCfg();
        cfg.init();
        dataFactory = cfg.getDataFactory();
        ofHelper = cfg.getOFHelper();
        testHelper = cfg.getTestHelper();
    }

    @Test()
    public void testReceipt() {
        testHelper.walletReceiptExistsForBuyer(505342L, "565-2134-5013-25", LedgerEntryType.LEDGER_ENTRY_RULE_ID_SO_POSTING_FEE);
    }

    @Test(groups={"hbm-unit"})
    public void isNewSO(){
        Assert.assertTrue(ofHelper.isNewSo("559-1610-9078-14"),"ServiceOrder is not created through OrderFulfillment.");
    }

    @Test(groups={"hbm-unit"})
    public void isNotNewSO(){
        Assert.assertFalse(ofHelper.isNewSo("359-1702-3967-12"),"ServiceOrder has been created through OF when it shouldn't have been.");
    }

    @Test(groups={"hbm-unit"})
    public void testPersistence(){
        dataFactory.addLogging("554-0049-5273-86");
    }

    @Test()
    public void dateConversions(){
        Date today = new Date();
        TimeZone tzUTC = TimeZone.getTimeZone("GMT");
        TimeZone tzEST = TimeZone.getTimeZone("EST5EDT");
        //TimeZone tzPST = TimeZone.getTimeZone("PST");
        Calendar actualGMT = TimeChangeUtil.getCalTimeFromParts(today,"12:00 PM", tzUTC);
        System.out.println("Actual TimeZone:" + actualGMT.getTimeZone());
        System.out.println("Expected TimeZone" + tzUTC);
        Assert.assertEquals(actualGMT.get(Calendar.HOUR_OF_DAY),12);
        Assert.assertEquals(actualGMT.get(Calendar.MINUTE),0);
        System.out.println("Date for GMT for 12:00" + actualGMT.getTime());

        Calendar actualEST = TimeChangeUtil.getCalTimeFromParts(today,"12:00 PM", tzEST);
        System.out.println("Actual TimeZone:" + actualEST.getTimeZone());
        System.out.println("Expected TimeZone" + tzEST);
        Assert.assertEquals(actualEST.get(Calendar.HOUR_OF_DAY),12);
        Assert.assertEquals(actualEST.get(Calendar.MINUTE),0);
        System.out.println(String.format("Requesting conversion to GMT for: %1$tY/%1$tb/%1$td - %1$tH:%1$tM",actualGMT));
        Date timeInGMT = TimeChangeUtil.getDate(actualGMT, tzUTC);
        System.out.println(String.format("Time for GMT is: %1$tY/%1$tb/%1$td - %1$tH:%1$tM",timeInGMT));
        System.out.println(String.format("Requesting conversion to EST for: %1$tY/%1$tb/%1$td - %1$tH:%1$tM",actualGMT));
        Date timeInEST = TimeChangeUtil.getDate(actualGMT, tzEST);
        System.out.println(String.format("Time for EST is: %1$tY/%1$tb/%1$td - %1$tH:%1$tM",timeInEST));
    }
}