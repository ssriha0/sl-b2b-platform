package com.servicelive.orderfulfillment.remote.test.workflow;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.servicelive.orderfulfillment.remote.test.assertions.TestCommand;
import com.servicelive.orderfulfillment.remote.test.workflow.state.CompleteOrderTest;

public class AddNoteTests extends CompleteOrderTest {
    @Override
    @BeforeClass(alwaysRun = true)
    public void init() {
        super.init();
    }

    @Test(dependsOnMethods={"completeOrder"})
    public void addBuyerNoteAfterCompletion(){
        testCfg.getDelegate().addBuyerNote(testCfg.getDataFactory(), testCfg.getOfHelper(), serviceOrderId);
        testCfg.getOfAssert().runAssertions(serviceOrderId, TestCommand.BUYER_ADD_COMPLETION_NOTE);
    }
}