package com.servicelive.orderfulfillment.remote.test.assertions;

import com.servicelive.wallet.serviceinterface.vo.LedgerEntryType;

import java.util.List;

public class TestCommandAssertionValues {
    String activity;
    List<Long> templateIdList;
    Long soLogActionId;
    List<LedgerEntryType> buyerTransactionList;
    List<LedgerEntryType> acceptedProviderTransactionList;
    

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public List<Long> getTemplateIdList() {
        return templateIdList;
    }

    public void setTemplateIdList(List<Long> templateIdList) {
        this.templateIdList = templateIdList;
    }

    public Long getSoLogActionId() {
        return soLogActionId;
    }

    public void setSoLogActionId(Long soLogActionId) {
        this.soLogActionId = soLogActionId;
    }

    public List<LedgerEntryType> getBuyerTransactionList() {
        return buyerTransactionList;
    }

    public void setBuyerTransactionList(List<LedgerEntryType> buyerTransactionList) {
        this.buyerTransactionList = buyerTransactionList;
    }

    public List<LedgerEntryType> getAcceptedProviderTransactionList() {
        return acceptedProviderTransactionList;
    }

    public void setAcceptedProviderTransactionList(List<LedgerEntryType> acceptedProviderTransactionList) {
        this.acceptedProviderTransactionList = acceptedProviderTransactionList;
    }
}
