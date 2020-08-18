package com.servicelive.common.rest;

import javax.ws.rs.core.MediaType;

import com.servicelive.common.rest.IRemoteServiceChecker;
import com.servicelive.common.rest.ServiceLiveBaseRestClient;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import org.apache.log4j.Logger;

public class ServiceLiveRemoteServiceChecker extends ServiceLiveBaseRestClient implements IRemoteServiceChecker {

    public static final String DFLT_SERVICE_STATUS_PATH = "/servicestatus";

    private Logger logger = Logger.getLogger(getClass());
    private String remoteServiceName = "Remote Service";
    private String serviceStatusPath;

    public boolean isServiceActive() {
        WebResource resource = client.resource(baseUrl);
        RemoteServiceStatus svcStatus;

        try {
            svcStatus = resource
                    .path((serviceStatusPath!=null)? serviceStatusPath : DFLT_SERVICE_STATUS_PATH)
                    .accept(MediaType.APPLICATION_XML_TYPE)
                    .get(RemoteServiceStatus.class);
        } catch (UniformInterfaceException ex) {
            logger.error(remoteServiceName + " status check resulted in jersey error.\n", ex);
            return false;
        } catch (Exception uiex) {
        	logger.error(remoteServiceName + " status check resulted in an error.\n", uiex);
        	return false;
        }

        if (svcStatus == null) {
            logger.error(remoteServiceName + "status check returned null response.\n");
            return false;
        }

        return svcStatus.isActive();
    }

    public String getRemoteServiceName() {
        return remoteServiceName;
    }

    public void setRemoteServiceName(String remoteServiceName) {
        this.remoteServiceName = remoteServiceName;
    }

    public void setServiceStatusPath(String serviceStatusPath) {
        this.serviceStatusPath = serviceStatusPath;
    }
}
