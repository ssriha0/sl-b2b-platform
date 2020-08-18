package com.servicelive.orderfulfillment.command;

import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderNoteUtil;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Yunus Burhani
 * Date: Jun 25, 2010
 * Time: 4:36:48 PM
 */
public class SONoteCommand extends SOCommand {

    protected ServiceOrderNoteUtil noteUtil;
    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void execute(Map<String, Object> processVariables) {
        ServiceOrder so = getServiceOrder(processVariables);
        String noteType = SOCommandArgHelper.extractStringArg(processVariables, 1);
        String noteVar1 = SOCommandArgHelper.extractStringArg(processVariables, 2);
        String noteVar2 = SOCommandArgHelper.extractStringArg(processVariables, 3);
        String toBeRemoved = "";
        if (null != noteVar1 && noteVar1.contains(":")){
            String[] noteVars = noteVar1.split(":");
            processVariables.put(noteVars[0], noteVars[1]);
            toBeRemoved = noteVars[0];
        }
        
        //SL-10863.adding previous and current status of SO for pos cancellation       
        processVariables.put(OrderfulfillmentConstants.SO_ID,so.getSoId());  
        if(null != noteVar1 && null != noteVar2) {
        	processVariables.put(OrderfulfillmentConstants.OLD_STATUS,noteVar1);
        	processVariables.put(OrderfulfillmentConstants.NEW_STATUS,noteVar2);
        }
        //Non w2 priority 2 : Setting Note Type from process Variables
        if(null!= noteType && StringUtils.equals(noteType, ProcessVariableUtil.REPEAT_REPAIR_NOTE)){
        	logger.info("Note Type from command "+ noteType);
        	noteType = (String) processVariables.get(noteType);
        	logger.info("Note Type from PV"+ noteType);
        }
        logger.debug("Notes before addition " + so.getNotes().size());
        SONote note = noteUtil.getNewNote(so, noteType, processVariables);
        if(StringUtils.isNotBlank(toBeRemoved)){
            processVariables.remove(toBeRemoved);
        }
        serviceOrderDao.save(note);
        logger.debug("Notes after addition " + so.getNotes().size());
    }

    public void setNoteUtil(ServiceOrderNoteUtil noteUtil) {
        this.noteUtil = noteUtil;
    }
}
