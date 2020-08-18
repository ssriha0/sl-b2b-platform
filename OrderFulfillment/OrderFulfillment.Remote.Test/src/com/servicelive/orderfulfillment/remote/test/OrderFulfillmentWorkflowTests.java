package com.servicelive.orderfulfillment.remote.test;

import com.servicelive.marketplatform.common.vo.ServiceOrderNotificationTask;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformNotificationBO;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.dao.ServiceOrderDao;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.remote.test.assertions.OrderFulfillmentAssertions;
import com.servicelive.orderfulfillment.remote.test.assertions.TestCommand;
import com.servicelive.orderfulfillment.test.helper.TestHelper;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

public class OrderFulfillmentWorkflowTests {
    private DataFactory dataFactory;
    private OFHelper ofHelper;
    ServiceOrderDao serviceOrderDao;
    TestHelper testHelper;
    OrderFulfillmentAssertions ofAssert;
    private IMarketPlatformNotificationBO notificationService;
    private TestDelegate delegate = new TestDelegate();

    private String serviceOrderId = "";
    private List<RoutedProvider> routedProviders = Collections.emptyList();
    private RoutedProvider acceptedProvider = null;


    @BeforeClass(alwaysRun = true)
    public void setUp() {
        CommonTestCfg cfg = new CommonTestCfg();
        cfg.init();
        dataFactory = cfg.getDataFactory();
        ofHelper = cfg.getOFHelper();
        serviceOrderDao = cfg.getServiceOrderDao();
        testHelper = cfg.getTestHelper();
        ofAssert = cfg.getOrderFulfillmentAssertions();
        notificationService = cfg.getNotificationServiceClient();
    }

    @Test(groups={"unit"})
    public void testNotificationTaskListener() {
        ServiceOrderNotificationTask notificationTask = new ServiceOrderNotificationTask();
        notificationTask.setServiceOrderId("test-service-order");
        notificationTask.setTemplateId(-1L);
        notificationService.queueNotificationTask(notificationTask);
        testHelper.waitForNotificationTask(notificationTask.getServiceOrderId());
        Assert.assertTrue(testHelper.taskExistsWithTemplateForSO(notificationTask.getServiceOrderId(), -1L), "notification task listener test");
    }

    @Test(groups={"unit"}, dependsOnMethods={"testNotificationTaskListener"})
    public void createServiceOrder(){
        serviceOrderId = delegate.createServiceOrder(dataFactory, ofHelper);
        ServiceOrder serviceOrder = serviceOrderDao.getServiceOrder(serviceOrderId);
        // test assertions
        Assert.assertNotNull(serviceOrder);
        ofAssert.runAssertions(serviceOrderId, TestCommand.CREATE_SERVICE_ORDER);
    }

    @Test(groups={"unit"}, dependsOnMethods={"createServiceOrder"})
    public void editDraftOrder() {
        delegate.editServiceOrder(dataFactory, ofHelper, serviceOrderId);
        ofAssert.runAssertions(serviceOrderId, TestCommand.EDIT_DRAFT_SERVICE_ORDER);
    }

    @Test(groups={"unit"}, dependsOnMethods={"createServiceOrder"})
    public void setServiceOrderSpendingLimit() {
        delegate.setServiceOrderSpendingLimit(dataFactory, ofHelper, serviceOrderId);
        ofAssert.runAssertions(serviceOrderId, TestCommand.EDIT_DRAFT_SERVICE_ORDER);
    }

    @Test(groups={"unit"}, dependsOnMethods={"createServiceOrder"})
    public void postServiceOrder() {
        routedProviders = delegate.postServiceOrder(dataFactory, ofHelper, serviceOrderId);
        testHelper.waitForState(serviceOrderId, "Posted");
        // test assertions
        ofAssert.runAssertions(serviceOrderId, TestCommand.POST_SERVICE_ORDER);
    }

    @Test(groups={"unit"}, dependsOnMethods={"setServiceOrderSpendingLimit"})
    public void postServiceOrderBid() {
        routedProviders = delegate.postServiceOrder(dataFactory, ofHelper, serviceOrderId);
        testHelper.waitForState(serviceOrderId, "BidPosted");
        // test assertions
        ofAssert.runAssertions(serviceOrderId, TestCommand.POST_SERVICE_ORDER_BID);
    }

    @Test(groups={"unit"}, dependsOnMethods={"postServiceOrder"})
    public void acceptServiceOrder(){
        acceptedProvider = delegate.acceptServiceOrder(dataFactory, ofHelper, serviceOrderId, routedProviders);
        ofAssert.runAssertions(serviceOrderId, TestCommand.ACCEPT_SERVICE_ORDER);
    }

    @Test(groups={"unit"}, dependsOnMethods={"postServiceOrderBid"})
    public void createConditionalOfferForBid(){
        delegate.createConditionalOffer(dataFactory, ofHelper, serviceOrderId, routedProviders);
        ofAssert.runAssertions(serviceOrderId, TestCommand.CREATE_CONDITIONAL_OFFER_FOR_BID);
    }

    @Test(groups={"unit"}, dependsOnMethods={"createConditionalOfferForBid"})
    public void acceptServiceOrderConditionally(){
        delegate.buyerAcceptServiceOrderConditionally(dataFactory, ofHelper, serviceOrderId, routedProviders);
        testHelper.waitForState(serviceOrderId, "Accepted");
        ofAssert.runAssertions(serviceOrderId, TestCommand.ACCEPT_SERVICE_ORDER_CONDITIONALLY);
    }

    @Test(groups={"unit"}, dependsOnMethods={"postServiceOrder"})
    public void voidServiceOrder(){
        delegate.voidServiceOrder(dataFactory, ofHelper, serviceOrderId);
        ofAssert.runAssertions(serviceOrderId, TestCommand.VOID_SERVICE_ORDER);
    }

    @Test(groups={"unit"}, dependsOnMethods={"acceptServiceOrder"})
    public void buyerRequestReschedule(){
        delegate.buyerRequestReschedule(dataFactory, ofHelper, serviceOrderId);
    }

    @Test(groups={"unit"},dependsOnMethods={"acceptServiceOrder"})
    public void providerReleasedOrder(){
    	delegate.releaseOrder(dataFactory, ofHelper, serviceOrderId,acceptedProvider);
    }


    @Test(groups={"unit"},dependsOnMethods={"acceptServiceOrder"})
    public void providerOnSiteVisit(){
    	delegate.providerOnSiteVisit(dataFactory, ofHelper, serviceOrderId,acceptedProvider);
    }

    @Test(groups={"unit"},dependsOnMethods={"acceptServiceOrder"})
    public void providerRequestReschedule(){
        delegate.providerRequestReschedule(dataFactory, ofHelper, serviceOrderId,acceptedProvider);
        Assert.assertNotNull(dataFactory.getServiceOrder(serviceOrderId).getReschedule());
    }

    @Test(groups={"unit"},dependsOnMethods={"buyerRequestReschedule"})
    public void providerAcceptReschedule(){
        delegate.providerAcceptReschedule(dataFactory, ofHelper, serviceOrderId, acceptedProvider);
        Assert.assertNull(dataFactory.getServiceOrder(serviceOrderId).getReschedule());
    }

    @Test(groups={"unit"},dependsOnMethods={"providerRequestReschedule"})
    public void buyerAcceptReschedule(){
        delegate.buyerAcceptReschedule(dataFactory, ofHelper, serviceOrderId);
        Assert.assertNull(dataFactory.getServiceOrder(serviceOrderId).getReschedule());
    }

    @Test(groups = {"unit"}, dependsOnMethods = {"acceptServiceOrder"})
    public void buyerReportProblem(){
        delegate.buyerReportProblem(dataFactory, ofHelper, serviceOrderId);
        ofAssert.runAssertions(serviceOrderId, TestCommand.BUYER_REPORT_PROBLEM_IN_ACCEPTED);
    }

    @Test(groups={"unit"}, dependsOnMethods={"acceptServiceOrder"})
    public void cancelServiceOrder() {
        delegate.cancelServiceOrder(dataFactory, ofHelper, serviceOrderId, "testing cancellation from accepted state");
        ofAssert.runAssertions(serviceOrderId, TestCommand.CANCEL_SERVICE_ORDER);
    }

    @Test(groups = {"unit"}, dependsOnMethods = {"acceptServiceOrder"})
    public void providerReportProblem(){
        delegate.providerReportProblem(dataFactory, ofHelper, serviceOrderId, acceptedProvider);
        ofAssert.runAssertions(serviceOrderId, TestCommand.PROVIDER_REPORT_PROBLEM_IN_ACCEPTED);
    }

    @Test(groups = {"unit"}, dependsOnMethods = {"providerReportProblem"})
    public void buyerResolveProblem(){
        delegate.buyerResolveProblem(dataFactory, ofHelper, serviceOrderId);
        ofAssert.runAssertions(serviceOrderId, TestCommand.BUYER_RESOLVE_PROBLEM_BACK_TO_ACCEPTED);
    }

    @Test(groups = {"unit"}, dependsOnMethods = {"buyerReportProblem"})
    public void providerResolveProblem(){
        delegate.providerResolveProblem(dataFactory, ofHelper, serviceOrderId, acceptedProvider);
        ofAssert.runAssertions(serviceOrderId, TestCommand.PROVIDER_RESOLVE_PROBLEM_BACK_TO_ACCEPTED);
    }

    @Test(groups={"unit"},dependsOnMethods={"acceptServiceOrder"})
    public void activateOrder(){
        delegate.activateServiceOrder(dataFactory, ofHelper, serviceOrderId);
        ofAssert.runAssertions(serviceOrderId, TestCommand.ACTIVATE_SERVICE_ORDER);
    }

    @Test(groups={"unit"},dependsOnMethods={"activateOrder"})
    public void requestServiceOrderCancellation() {
        delegate.requestServiceOrderCancellation(dataFactory, ofHelper, serviceOrderId, "50.00");
        ofAssert.runAssertions(serviceOrderId, TestCommand.REQUEST_CANCEL_SERVICE_ORDER);
    }

    @Test(groups={"unit"},dependsOnMethods={"activateOrder"})
    public void completeOrder(){
        delegate.completeServiceOrder(dataFactory, ofHelper, serviceOrderId, acceptedProvider);
        ofAssert.runAssertions(serviceOrderId, TestCommand.COMPLETE_SERVICE_ORDER);
    }

    @Test(groups={"unit"},dependsOnMethods={"requestServiceOrderCancellation"})
    public void completeOrderAfterCancelRequest() {
        delegate.completeServiceOrder(dataFactory, ofHelper, serviceOrderId, acceptedProvider);
        ofAssert.runAssertions(serviceOrderId, TestCommand.COMPLETE_SERVICE_ORDER);
    }

    @Test(groups={"unit"},dependsOnMethods={"recallOrderCompletion"})
    public void completeOrderAfterRecall() {
        delegate.completeServiceOrder(dataFactory, ofHelper, serviceOrderId, acceptedProvider);
        ofAssert.runAssertions(serviceOrderId, TestCommand.COMPLETE_SERVICE_ORDER);
    }

    @Test(groups={"unit"},dependsOnMethods={"completeOrder"})
    public void recallOrderCompletion(){
        delegate.recallOrderCompletion(dataFactory, ofHelper, serviceOrderId, acceptedProvider);
        ofAssert.runAssertions(serviceOrderId, TestCommand.RECALL_COMPLETION);
    }

    @Test(groups={"unit"},dependsOnMethods={"completeOrder"})
    public void closeOrder(){
        delegate.closeServiceOrder(dataFactory, ofHelper, serviceOrderId);
        ofAssert.runAssertions(serviceOrderId, TestCommand.CLOSE_SERVICE_ORDER);
    }

    @Test(groups={"unit"},dependsOnMethods={"completeOrderAfterCancelRequest"})
    public void closeOrderAfterCancelRequest(){
        delegate.closeServiceOrder(dataFactory, ofHelper, serviceOrderId);
        ofAssert.runAssertions(serviceOrderId, TestCommand.CLOSE_SERVICE_ORDER);
    }

    @Test(groups={"unit"},dependsOnMethods={"completeOrderAfterRecall"})
    public void closeAfterRecall() {
        delegate.closeServiceOrder(dataFactory, ofHelper, serviceOrderId);
        ofAssert.runAssertions(serviceOrderId, TestCommand.CLOSE_SERVICE_ORDER);
    }
}
