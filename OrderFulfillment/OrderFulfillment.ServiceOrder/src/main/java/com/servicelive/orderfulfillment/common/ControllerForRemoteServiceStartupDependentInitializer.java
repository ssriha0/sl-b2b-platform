package com.servicelive.orderfulfillment.common;

import java.util.List;

import org.apache.log4j.Logger;

public class ControllerForRemoteServiceStartupDependentInitializer {
	Logger logger = Logger.getLogger(ControllerForRemoteServiceStartupDependentInitializer.class);
    IRemoteServicesStartupChecker remoteServicesStartupChecker;
    List<RemoteServiceStartupDependentInitializer> remoteServiceStartupDependentInitializers;

    public ControllerForRemoteServiceStartupDependentInitializer(IRemoteServicesStartupChecker remoteServicesStartupChecker, List<RemoteServiceStartupDependentInitializer> remoteServiceStartupDependentInitializers) {
        this.remoteServicesStartupChecker = remoteServicesStartupChecker;
        this.remoteServiceStartupDependentInitializers = remoteServiceStartupDependentInitializers;
        runInitializers();
    }

    public void runInitializers() {
        RemoteStartupCheckThread remoteStartupCheckThread = new RemoteStartupCheckThread(this.remoteServicesStartupChecker, this.remoteServiceStartupDependentInitializers);
        remoteStartupCheckThread.start();
    }

    class RemoteStartupCheckThread extends Thread {
        IRemoteServicesStartupChecker remoteServicesStartupChecker;
        List<RemoteServiceStartupDependentInitializer> remoteServiceStartupDependentInitializers;

        RemoteStartupCheckThread(IRemoteServicesStartupChecker remoteServicesStartupChecker, List<RemoteServiceStartupDependentInitializer> remoteServiceStartupDependentInitializers) {
            this.remoteServicesStartupChecker = remoteServicesStartupChecker;
            this.remoteServiceStartupDependentInitializers = remoteServiceStartupDependentInitializers;
        }

        @Override
        public void run() {
            this.remoteServicesStartupChecker.waitForAllActiveServices();
            if (this.remoteServiceStartupDependentInitializers != null) {
                for (RemoteServiceStartupDependentInitializer remoteServiceStartupDependentInitializer : this.remoteServiceStartupDependentInitializers) {
                	try{
                		remoteServiceStartupDependentInitializer.doRemoteServiceDependentInitialization();
                	}catch(Exception e){
                		logger.error("Error happened while initializing the remote service initializer ", e);
                	}
                }
            }
        }
    }
}
