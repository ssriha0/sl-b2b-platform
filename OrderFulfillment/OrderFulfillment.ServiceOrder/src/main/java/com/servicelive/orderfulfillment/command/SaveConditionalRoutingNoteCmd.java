package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.NoteType;
import com.servicelive.orderfulfillment.domain.type.RoleType;

public class SaveConditionalRoutingNoteCmd extends SOCommand {
    @Override
    public void execute(Map<String, Object> processVariables) {
        ServiceOrder serviceOrder = getServiceOrder(processVariables);
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("SaveConditionalRoutingNoteCmd starting with %s containing %d notes.", serviceOrder.getSoId(), serviceOrder.getNotes().size()));
        }
        String ruleName = (String)processVariables.get(ProcessVariableUtil.CONDITIONAL_AUTO_ROUTING_RULE_NAME);
        Integer ruleId = (Integer)processVariables.get(ProcessVariableUtil.CONDITIONAL_AUTO_ROUTING_RULE_ID);

        SONote conditionalRouteNote = createConditionalRouteNote(serviceOrder, ruleName, ruleId);
        conditionalRouteNote.setServiceOrder(serviceOrder);
        serviceOrderDao.save(conditionalRouteNote);
        serviceOrder.addNote(conditionalRouteNote);
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("SaveConditionalRoutingNoteCmd added note. Service order %s now has %d notes.", serviceOrder.getSoId(), serviceOrder.getNotes().size()));
        }
        //serviceOrderDao.update(serviceOrder);
    }

    private SONote createConditionalRouteNote(ServiceOrder serviceOrder, String ruleName, Integer ruleId) {
        String message;
        if (hasProvidersForPosting(serviceOrder)) {
            message = "Service Order Routed - ";
        } else {
            message = "Service Order NOT Routed  due to NO PROVIDERS - ";
        }
        message = message + ruleName + "(" + ruleId + ")";

        SONote conditionalRouteNote = new SONote();
        conditionalRouteNote.setSubject("Service Order - Conditional Routing");
        conditionalRouteNote.setNote(message);
        conditionalRouteNote.setCreatedByName("System");
        conditionalRouteNote.setEntityId(serviceOrder.getBuyerId());
        conditionalRouteNote.setNoteTypeId(NoteType.GENERAL.getId());
        conditionalRouteNote.setRoleId(RoleType.Buyer.getId());
        return conditionalRouteNote;
    }

    private boolean hasProvidersForPosting(ServiceOrder serviceOrder) {
        return serviceOrder.getRoutedResources().size() > 0;
    }
}
