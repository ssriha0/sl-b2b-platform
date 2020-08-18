package com.servicelive.orderfulfillment.test.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.testng.Assert;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.common.Contact;
import com.servicelive.marketplatform.client.MarketPlatformRemoteServiceClient;
import com.servicelive.marketplatform.common.vo.ServiceOrderNotificationTask;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformProviderBO;
import com.servicelive.orderfulfillment.dao.ServiceOrderDao;
import com.servicelive.orderfulfillment.domain.SOLogging;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.test.helper.dao.ServiceLiveDao;
import com.servicelive.orderfulfillment.test.helper.jbpm.JbpmDao;
import com.servicelive.orderfulfillment.test.helper.jbpm.JbpmExecution;
import com.servicelive.orderfulfillment.test.helper.notification.NotificationTaskInMemoryStorage;
import com.servicelive.wallet.remoteclient.WalletBO;
import com.servicelive.wallet.serviceinterface.vo.LedgerEntryType;
import com.servicelive.wallet.serviceinterface.vo.ReceiptVO;

public class TestHelper {
    JbpmDao jbpmDao;
    ServiceOrderDao serviceOrderDao;
    ServiceLiveDao serviceLiveDao;
    WalletBO walletBO;
    IMarketPlatformProviderBO mpProviderBO;
    IMarketPlatformBuyerBO mpBuyerBO;
    MarketPlatformRemoteServiceClient marketPlatformRemoteServiceClient;

    public ServiceOrder retrieveServiceOrder(String serviceOrderId) {
        return serviceOrderDao.getServiceOrder(serviceOrderId);
    }

    public String getServiceOrderActivity(String serviceOrderId) {
        JbpmExecution jbpmExecution = jbpmDao.findJbpmExecution(serviceOrderId);
        if (jbpmExecution == null) return null;
        return jbpmExecution.getActivity();
    }

    public List<ServiceOrderNotificationTask> getSONotifyTaskList(String serviceOrderId) {
        Map<String, List<ServiceOrderNotificationTask>> notifyTaskMap = NotificationTaskInMemoryStorage.getNotifyTaskMapCopy();
        if (notifyTaskMap.containsKey(serviceOrderId)) {
            return notifyTaskMap.get(serviceOrderId);
        }
        return new ArrayList<ServiceOrderNotificationTask>();
    }

    public ServiceOrderNotificationTask findSONotifyTask(String serviceOrderId, long templateId) {
        List<ServiceOrderNotificationTask> soNotifyTaskList = getSONotifyTaskList(serviceOrderId);
        for (ServiceOrderNotificationTask notifyTask : soNotifyTaskList) {
            if (notifyTask.getTemplateId() == templateId) return notifyTask;
        }

        return null;
    }

    public boolean taskExistsWithTemplateForSO(String serviceOrderId, long templateId) {
        return (findSONotifyTask(serviceOrderId, templateId) != null);
    }

    public List<SOLogging> getSOLoggingList(String serviceOrderId) {
        List<SOLogging> soLoggingList;
        try {
            soLoggingList = serviceOrderDao.getServiceOrderLog(serviceOrderId);
        } catch (javax.persistence.NoResultException e) {
            soLoggingList = new ArrayList<SOLogging>();
        }
        return soLoggingList;
    }

    public SOLogging findSOLoggingEntry(String serviceOrderId, long logActionId) {
        List<SOLogging> soLoggingList = getSOLoggingList(serviceOrderId);

        for (SOLogging soLogging : soLoggingList) {
            if (soLogging.getActionId().longValue() == logActionId) return soLogging;
        }

        return null;
    }

    public boolean soLogEntryExists(String serviceOrderId, long logActionId) {
        return (findSOLoggingEntry(serviceOrderId, logActionId) != null);
    }

    public boolean walletReceiptExistsForBuyer(Long buyerId, String serviceOrderId, LedgerEntryType ledgerEntryType) {
        return walletReceiptExists(buyerId, Long.valueOf(CommonConstants.LEDGER_ENTITY_TYPE_BUYER).intValue(), serviceOrderId, ledgerEntryType);
    }

    public boolean walletReceiptExistsForProvider(Long providerId, String serviceOrderId, LedgerEntryType ledgerEntryType) {
        return walletReceiptExists(providerId, Long.valueOf(CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER).intValue(), serviceOrderId, ledgerEntryType);
    }

    public boolean walletReceiptExists(Long entityId, int entityType, String serviceOrderId, LedgerEntryType ledgerEntryType) {
        ReceiptVO transactionReceipt = null;
        try {
            transactionReceipt = walletBO.getTransactionReceipt(
                    entityId,
                    entityType,
                    ledgerEntryType,
                    serviceOrderId);
        } catch (SLBusinessServiceException e) {
            e.printStackTrace();
        }

        return (transactionReceipt != null);
    }

    public void waitForState(String serviceOrderId, String expectedStateName) {
        final int MAX_RETRY_COUNT = 20;
        int retryCount = 0;
        String currState = StringUtils.EMPTY;

        while (!StringUtils.equalsIgnoreCase(expectedStateName, currState) && retryCount <= MAX_RETRY_COUNT) {
            waitSeconds(2);
            retryCount++;
            currState = getServiceOrderActivity(serviceOrderId);
        }

        if (!StringUtils.equalsIgnoreCase(expectedStateName, currState)) {
            Assert.fail("ServiceOrder " + serviceOrderId + " still not in " + expectedStateName + " activity after wait.");
        }
    }

    public void waitForNotificationTask(String serviceOrderId) {
        final int MAX_RETRY_COUNT = 5;
        int retryCount = 0;
        Map<String, List<ServiceOrderNotificationTask>> notifyTaskMap;
        while (retryCount <= MAX_RETRY_COUNT) {
            waitSeconds(2);
            retryCount++;
            notifyTaskMap = NotificationTaskInMemoryStorage.getNotifyTaskMapCopy();
            if (notifyTaskMap.containsKey(serviceOrderId)) return;
        }
    }
    
    public void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {}
    }

    public Buyer getBuyerInfo(int buyerId) {
        return serviceLiveDao.findBuyerById(buyerId);
    }

    public Contact getBuyerContactInfo(long buyerId) {
        return mpBuyerBO.retrieveBuyerContactInfo(buyerId);
    }

    ////////////////////////////////////////////////////////////////////////////
    // SPRING INJECTION SETTERS
    ////////////////////////////////////////////////////////////////////////////
    public void setJbpmDao(JbpmDao jbpmDao) {
        this.jbpmDao = jbpmDao;
    }

    public void setServiceOrderDao(ServiceOrderDao serviceOrderDao) {
        this.serviceOrderDao = serviceOrderDao;
    }

    public void setServiceLiveDao(ServiceLiveDao serviceLiveDao) {
        this.serviceLiveDao = serviceLiveDao;
    }

    public void setWalletBO(WalletBO walletBO) {
        this.walletBO = walletBO;
    }

    public void setMpProviderBO(IMarketPlatformProviderBO mpProviderBO) {
        this.mpProviderBO = mpProviderBO;
    }

    public void setMpBuyerBO(IMarketPlatformBuyerBO mpBuyerBO) {
        this.mpBuyerBO = mpBuyerBO;
    }

    public void setMarketPlatformRemoteServiceClient(MarketPlatformRemoteServiceClient marketPlatformRemoteServiceClient) {
        this.marketPlatformRemoteServiceClient = marketPlatformRemoteServiceClient;
    }

    public MarketPlatformRemoteServiceClient getMarketPlatformRemoteServiceClient() {
        return marketPlatformRemoteServiceClient;
    }
}
