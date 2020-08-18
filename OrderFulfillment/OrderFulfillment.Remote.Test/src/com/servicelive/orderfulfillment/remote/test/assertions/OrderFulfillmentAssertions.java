package com.servicelive.orderfulfillment.remote.test.assertions;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;

import com.servicelive.marketplatform.common.vo.ServiceOrderNotificationTask;
import com.servicelive.marketplatform.common.vo.ServiceOrderNotificationTaskAddresses;
import com.servicelive.orderfulfillment.domain.SOLogging;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.test.helper.TestHelper;
import com.servicelive.wallet.serviceinterface.vo.LedgerEntryType;


public class OrderFulfillmentAssertions {
    AssertionValuesResolver assertionValuesResolver;
    TestHelper testHelper;

    private static final Log log = LogFactory.getLog(OrderFulfillmentAssertions.class);

    @Transactional(readOnly = true)
    public void runAssertions(String serviceOrderId, TestCommand command) {
        TestCommandAssertionValues assertionValues = assertionValuesResolver.getCommandAssertionValues(command);
        runActivityAssertion(serviceOrderId, assertionValues);
        runNotificationTaskAssertions(serviceOrderId, assertionValues);
        runSOLoggingAssertion(serviceOrderId, assertionValues);
        runBuyerTransactionReceiptAssertions(serviceOrderId, assertionValues);
        runProviderTransactionReceiptAssertions(serviceOrderId, assertionValues);
    }

    void runActivityAssertion(String serviceOrderId, TestCommandAssertionValues assertionValues) {
        String expectedActivity = assertionValues.getActivity();
        if (expectedActivity == null) return;

        String resultActivity = testHelper.getServiceOrderActivity(serviceOrderId);
        dumpActivityInfo(resultActivity);

        String assertionMsg = "Activity check for service order " + serviceOrderId;
        if (StringUtils.equalsIgnoreCase("null", expectedActivity)) {
            Assert.assertNull(resultActivity, assertionMsg);
        } else {
            Assert.assertEquals(resultActivity, expectedActivity, assertionMsg);
        }
    }

    void runNotificationTaskAssertions(String serviceOrderId, TestCommandAssertionValues assertionValues) {
        List<Long> expectedTemplateIdList = assertionValues.getTemplateIdList();
        if (expectedTemplateIdList == null) return;

        testHelper.waitForNotificationTask(serviceOrderId);

        String assertionMsg = "Notification task exists check for service order " + serviceOrderId;
        for (Long expectedTemplateId : expectedTemplateIdList) {
            ServiceOrderNotificationTask notifyTask = testHelper.findSONotifyTask(serviceOrderId, expectedTemplateId);
            dumpNotificationTask(serviceOrderId, notifyTask);
            Assert.assertNotNull(notifyTask, assertionMsg);
        }
    }

    void runSOLoggingAssertion(String serviceOrderId, TestCommandAssertionValues assertionValues) {
        Long expectedActionId = assertionValues.getSoLogActionId();
        if (expectedActionId == null) return;
        String assertionMsg = "SOLogging record exists check for service order " + serviceOrderId;
        SOLogging soLogging = testHelper.findSOLoggingEntry(serviceOrderId, expectedActionId);
        dumpSOLoggingInfo(serviceOrderId, soLogging);
        Assert.assertNotNull(soLogging, assertionMsg);
    }

    void runBuyerTransactionReceiptAssertions(String serviceOrderId, TestCommandAssertionValues assertionValues) {
        List<LedgerEntryType> ledgerEntryTypes = assertionValues.getBuyerTransactionList();
        if (ledgerEntryTypes == null) return;

        String assertionMsg = "Buyer wallet transaction receipt exists check for service order " + serviceOrderId;
        ServiceOrder serviceOrder = testHelper.retrieveServiceOrder(serviceOrderId);
        for (LedgerEntryType ledgerEntryType : ledgerEntryTypes) {
            boolean exists = testHelper.walletReceiptExistsForBuyer(serviceOrder.getBuyerId(), serviceOrderId, ledgerEntryType);
            Assert.assertTrue(exists, assertionMsg);
        }
    }

    void runProviderTransactionReceiptAssertions(String serviceOrderId, TestCommandAssertionValues assertionValues) {
        List<LedgerEntryType> ledgerEntryTypes = assertionValues.getAcceptedProviderTransactionList();
        if (ledgerEntryTypes == null) return;

        String assertionMsg = "Accepted provider wallet transaction receipt exists check for service order " + serviceOrderId;
        ServiceOrder serviceOrder = testHelper.retrieveServiceOrder(serviceOrderId);
        for (LedgerEntryType ledgerEntryType : ledgerEntryTypes) {
            boolean exists = testHelper.walletReceiptExistsForProvider(serviceOrder.getAcceptedProviderId(), serviceOrderId, ledgerEntryType);
            Assert.assertTrue(exists, assertionMsg);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // data dumps
    ////////////////////////////////////////////////////////////////////////////
    private static final String NEWLINE = "\n";

    private void dumpActivityInfo(String resultActivity) {
        log.info("\nResulting workflow state: " + resultActivity);
    }

    private void dumpNotificationTask(String serviceOrderId, ServiceOrderNotificationTask notificationTask) {
        StringBuilder strBldr = new StringBuilder("\nNotification tasks for service order : ");
        strBldr.append(serviceOrderId).append(NEWLINE);
        if (notificationTask!=null) {
            strBldr.append("template id : ").append(notificationTask.getTemplateId()).append(NEWLINE);
            ServiceOrderNotificationTaskAddresses addresses = notificationTask.getAddresses();
            strBldr.append("from        : ").append(addresses.getFrom()).append(NEWLINE);
            strBldr.append("to          : ").append(addresses.getTo()).append(NEWLINE);
            strBldr.append("cc          : ").append(addresses.getCc()).append(NEWLINE);
            strBldr.append("bcc         : ").append(addresses.getBcc()).append(NEWLINE);
            strBldr.append("value map   : ").append(notificationTask.getTemplateMergeValueMap()).append(NEWLINE);
        } else {
            strBldr.append("null");
        }
        log.info(strBldr.toString());
    }

    private void dumpSOLoggingInfo(String serviceOrderId, SOLogging soLogging) {
        StringBuilder strBldr = new StringBuilder("\nSOLogging info for service order : ");
        strBldr.append(serviceOrderId).append(NEWLINE);
        if (soLogging!=null) {
            strBldr.append("action id   : ").append(soLogging.getActionId()).append(NEWLINE);
            strBldr.append("chg comment : ").append(soLogging.getChgComment()).append(NEWLINE);
            strBldr.append("modified by : ").append(soLogging.getModifiedBy()).append(NEWLINE);
        } else {
            strBldr.append("null");
        }
        log.info(strBldr.toString());
    }

    ////////////////////////////////////////////////////////////////////////////
    // SPRING INJECTION SETTERS
    ////////////////////////////////////////////////////////////////////////////
    public void setAssertionValuesResolver(AssertionValuesResolver assertionValuesResolver) {
        this.assertionValuesResolver = assertionValuesResolver;
    }

    public void setTestHelper(TestHelper testHelper) {
        this.testHelper = testHelper;
    }
}
