package com.servicelive.orderfulfillment.integration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.servicelive.orderfulfillment.common.RemoteServiceStartupDependentInitializer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * User: Mustafa Motiwala
 * Date: Apr 7, 2010
 * Time: 11:45:29 AM
 */
public class IntegrationContextInitializer implements InitializingBean, RemoteServiceStartupDependentInitializer {
    private static final Log log = LogFactory.getLog(IntegrationContextInitializer.class);
    private Integer instances=1;
    private ScheduledExecutorService executorService;
    private IntegrationBO integrationBO;
    private IntegrationProcessor processor;
    private List<ProcessOrchestrator> orchestrators;
    private Integer executionDelay = 10;

    public void setInstances(Integer instances) {
        this.instances = instances;
    }


    public void afterPropertiesSet() throws Exception {
        orchestrators = new ArrayList<ProcessOrchestrator>(instances);
        executorService = Executors.newScheduledThreadPool(instances);
        log.info("instances"+instances);
        for(int x=0;x<instances;x++){
            ProcessOrchestrator orchestrator = new ProcessOrchestrator(integrationBO, processor, UUID.randomUUID().toString());
            executorService.scheduleWithFixedDelay(orchestrator,0,executionDelay, TimeUnit.SECONDS);
            orchestrators.add(orchestrator);
        }
    }

    public void setIntegrationBO(IntegrationBO integrationBO) {
        this.integrationBO = integrationBO;
    }

    public void setProcessor(IntegrationProcessor processor) {
        this.processor = processor;
    }

    public void setExecutionDelay(Integer executionDelay) {
        this.executionDelay = executionDelay;
    }

    public void doRemoteServiceDependentInitialization() {
        for(ProcessOrchestrator orchestrator : orchestrators){
            orchestrator.setReady(true);
        }
    }  
    
  //SL-18883: CLONE - Duplicate Service Orders is being created for a single external order number
    //removing static keyword from ProcessOrchestrator method
    private class ProcessOrchestrator implements Runnable{
        private IntegrationBO integrationBO;
        private IntegrationProcessor integrationProcessor;
        private String orchestratorId;
        private boolean isReady=false;

        public ProcessOrchestrator(IntegrationBO bo, IntegrationProcessor processor, String id){
            integrationBO = bo;
            integrationProcessor = processor;
            orchestratorId = id;
        }

        public void run() {
            if(!isReady) return;//We are not yet ready to execute.
            log.debug("Orchestrate process.");
            try{
            	 //SL-18883: CLONE - Duplicate Service Orders is being created for a single external order number
                //Calling newly created acquireWorkAndProcess method with orchestratorId parameter
            	acquireWorkAndProcess(orchestratorId);
            }catch(Exception ex){
                log.error("Exception encountered while trying to Orchestrate data load.", ex);
                integrationBO.failWorker(orchestratorId, ex);
            }
        }


        public void setReady(boolean ready) {
            isReady = ready;
        }
    }
    
    //SL-18883: CLONE - Duplicate Service Orders is being created for a single external order number
    //Newly created method acquireWorkAndProcess 
    public void acquireWorkAndProcess(String workerId)
    {
    	 while(processor.acquireWork(workerId)){
             log.info("Processing transactions for the worker " + workerId);
             processor.process(workerId);
         }	
    }

}
