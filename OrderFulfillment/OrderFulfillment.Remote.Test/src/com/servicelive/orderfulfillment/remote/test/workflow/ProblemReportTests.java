package com.servicelive.orderfulfillment.remote.test.workflow;

import com.servicelive.orderfulfillment.remote.test.assertions.TestCommand;
import com.servicelive.orderfulfillment.remote.test.workflow.state.CompleteOrderTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ProblemReportTests extends CompleteOrderTest {
    @Override
    @BeforeClass(alwaysRun = true)
    public void init() {
        super.init();
    }

    @Test(dependsOnMethods = {"acceptServiceOrder"})
    public void buyerReportProblemInAccept(){
        testCfg.getDelegate().buyerReportProblem(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.BUYER_REPORT_PROBLEM_IN_ACCEPTED);
    }

    @Test(dependsOnMethods = {"acceptServiceOrder"})
    public void providerReportProblemInAccept(){
        testCfg.getDelegate().providerReportProblem(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, acceptedProvider);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.PROVIDER_REPORT_PROBLEM_IN_ACCEPTED);
    }

    @Test(dependsOnMethods = {"providerReportProblemInAccept"})
    public void buyerResolveProblemBackToAccept(){
        testCfg.getDelegate().buyerResolveProblem(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.BUYER_RESOLVE_PROBLEM_BACK_TO_ACCEPTED);
    }

    @Test(dependsOnMethods = {"buyerReportProblemInAccept"})
    public void providerResolveProblemBackToAccept(){
        testCfg.getDelegate().providerResolveProblem(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, acceptedProvider);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.PROVIDER_RESOLVE_PROBLEM_BACK_TO_ACCEPTED);
    }

    @Test(dependsOnMethods = {"completeOrder"})
    public void buyerReportProblemInComplete(){
        testCfg.getDelegate().buyerReportProblem(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.BUYER_REPORT_PROBLEM_IN_COMPLETED);
    }

    @Test(dependsOnMethods = {"completeOrder"})
    public void providerReportProblemInComplete(){
        testCfg.getDelegate().providerReportProblem(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, acceptedProvider);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.PROVIDER_REPORT_PROBLEM_IN_COMPLETED);
    }

    @Test(dependsOnMethods = {"providerReportProblemInComplete"})
    public void buyerResolveProblemBackToActive(){
        testCfg.getDelegate().buyerResolveProblem(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.BUYER_RESOLVE_PROBLEM_BACK_TO_ACTIVE);
    }

    @Test(dependsOnMethods = {"buyerReportProblemInComplete"})
    public void providerResolveProblemBackToActive(){
        testCfg.getDelegate().providerResolveProblem(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId, acceptedProvider);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.PROVIDER_RESOLVE_PROBLEM_BACK_TO_ACTIVE);
    }
}
