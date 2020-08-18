package com.servicelive.orderfulfillment.jobs;

import com.servicelive.orderfulfillment.common.RemoteServiceStartupDependentInitializer;
import org.apache.log4j.Logger;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.jobexecutor.JobExecutor;

public class OFJobExecutor implements RemoteServiceStartupDependentInitializer {
    protected final Logger logger = Logger.getLogger(getClass());

    private JobExecutor jobExecutor;
    private EnvironmentFactory environmentFactory;

    public void doRemoteServiceDependentInitialization() {
        logger.info("Starting OrderFulfillment JobExecutor.");
        jobExecutor = environmentFactory.get(JobExecutor.class);
        jobExecutor.start();
        logger.info("OrderFulfillment JobExecutor started.");
    }

    public void stop() {
        jobExecutor.stop();
    }

    public void finalize() {
        stop();
    }

    public void setEnvironmentFactory(EnvironmentFactory environmentFactory) {
        this.environmentFactory = environmentFactory;
    }
}
