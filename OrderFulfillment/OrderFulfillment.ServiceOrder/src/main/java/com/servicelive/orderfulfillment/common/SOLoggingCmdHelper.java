package com.servicelive.orderfulfillment.common;

import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.logging.IServiceOrderLogging;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;

public class SOLoggingCmdHelper {
    protected final Logger logger = Logger.getLogger(getClass());

    private HashMap<String,IServiceOrderLogging> logObjMap;

    public void logServiceOrderActivity(ServiceOrder serviceOrder, String loggingCfgMapKey){
        HashMap<String, Object> loggingDataMap = new HashMap<String, Object>();
        loggingDataMap.put("id", getAdminIdentification());
        logServiceOrderActivity(serviceOrder, loggingCfgMapKey, loggingDataMap);
    }
    
    public void logServiceOrderActivity(ServiceOrder serviceOrder ,String loggingCfgMapKey, HashMap<String, Object> loggingDataMap) {
        if (StringUtils.isBlank(loggingCfgMapKey)) return;

        if (!soLoggingObjectExists(loggingCfgMapKey)) {
            logger.error("IServiceOrderLogging object could not been resolved for key - "+ loggingCfgMapKey);
            return;
        }

        IServiceOrderLogging logObj = logObjMap.get(loggingCfgMapKey);
        logObj.logServiceOrder(serviceOrder, loggingDataMap);
    }

    public boolean soLoggingObjectExists(String loggingCfgMapKey) {
        return  !StringUtils.isBlank(loggingCfgMapKey)
                && logObjMap.containsKey(loggingCfgMapKey);
    }

    public void setLogObjMap(HashMap<String, IServiceOrderLogging> logObjMap) {
        this.logObjMap = logObjMap;
    }

    public Identification getAdminIdentification(){
        Identification id = new Identification();
        id.setUsername(OFConstants.SL_ADMIN_USERNAME);
        id.setRoleId(OFConstants.SL_ADMIN_ROLE_ID);
        id.setEntityType(Identification.EntityType.SLADMIN);
        return id;
    }

}
