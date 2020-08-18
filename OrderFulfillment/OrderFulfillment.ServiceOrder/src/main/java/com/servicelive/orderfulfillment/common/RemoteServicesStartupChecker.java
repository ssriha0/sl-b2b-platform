package com.servicelive.orderfulfillment.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import com.servicelive.common.rest.IRemoteServiceChecker;

public class RemoteServicesStartupChecker implements IRemoteServicesStartupChecker {
    protected final Logger logger = Logger.getLogger(getClass());
    private static final int DEFAULT_RETRY_INTERVAL_SECS = 30;
    private static final int DEFAULT_RETRIES_BETWEEN_WARNS = 10;

    private int retryIntervalInSeconds = DEFAULT_RETRY_INTERVAL_SECS;
    private int retriesBetweenWarnings = DEFAULT_RETRIES_BETWEEN_WARNS;

    private List<IRemoteServiceChecker> remoteServiceCheckers;

    public void waitForAllActiveServices() {
        if (remoteServiceCheckers==null) {
            return;
        }
        waitSeconds(retryIntervalInSeconds);
        for (IRemoteServiceChecker remoteServiceChecker : remoteServiceCheckers) {
            waitForServiceUsingChecker(remoteServiceChecker);
        }
    }

    public void waitForServiceUsingChecker(IRemoteServiceChecker svcChecker) {
        int retryCount = 0;
        while (!svcChecker.isServiceActive()) {
            retryCount++;
            logInactiveServiceAndWait(Priority.INFO, svcChecker.getRemoteServiceName());

            if (retryCount == retriesBetweenWarnings) {
                retryCount = 0;
                logInactiveServiceAndWait(Priority.ERROR, svcChecker.getRemoteServiceName());
            }
        }
    }

    public void logInactiveServiceAndWait(Priority logLevel, String serviceName) {
        logger.log(logLevel, String.format("%s not active. Retrying in %d seconds.", serviceName, retryIntervalInSeconds));
        waitSeconds(retryIntervalInSeconds);
    }

    public void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {}
    }

    ////////////////////////////////////////////////////////////////////////////
    // SPRING INJECTION SETTERS
    ////////////////////////////////////////////////////////////////////////////
    public void setRetryIntervalInSeconds(int retryIntervalInSeconds) {
        this.retryIntervalInSeconds = retryIntervalInSeconds;
    }

    public void setRetriesBetweenWarnings(int retriesBetweenWarnings) {
        this.retriesBetweenWarnings = retriesBetweenWarnings;
    }

    public void setRemoteServiceCheckers(List<IRemoteServiceChecker> remoteServiceCheckers) {
        this.remoteServiceCheckers = remoteServiceCheckers;
    }
}
