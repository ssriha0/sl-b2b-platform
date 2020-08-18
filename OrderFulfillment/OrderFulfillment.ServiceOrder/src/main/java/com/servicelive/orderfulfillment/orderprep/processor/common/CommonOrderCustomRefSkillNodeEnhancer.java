package com.servicelive.orderfulfillment.orderprep.processor.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.servicelive.marketplatform.provider.domain.SkillNode;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;

public class CommonOrderCustomRefSkillNodeEnhancer extends AbstractOrderCustomRefEnhancer {

    @Override
    protected void addCustomReferences(ServiceOrder serviceOrder, IOrderBuyer orderBuyer) {
        addSkillNodesToSOCustomRefList(serviceOrder, orderBuyer);
    }
   
    private void addSkillNodesToSOCustomRefList(ServiceOrder serviceOrder, IOrderBuyer orderBuyer) {
        SOTask primaryTask = serviceOrder.getPrimaryTask();
        if (primaryTask.getSkillNodeId() != null) {
            SkillNode skillNode = quickLookupCollection.getSkillTreeLookup().getSkillNodeById(primaryTask.getSkillNodeId().longValue());
            addSkillNodeHierarchyToSOCustomRefList(serviceOrder, skillNode, orderBuyer);
            addServiceTypeSkillToCustomRefList(serviceOrder, orderBuyer);
        }
    }

    private void addSkillNodeHierarchyToSOCustomRefList(ServiceOrder serviceOrder, SkillNode skillNode, IOrderBuyer orderBuyer) {
        SOCustomReference custRef = new SOCustomReference();
        if (skillNode.getLevel().equals(1)) {
            custRef.setBuyerRefTypeId(orderBuyer.getBuyerReferenceTypeId(SOCustomReference.CREF_MAIN_SVC_CATEGORY));
            custRef.setBuyerRefTypeName(SOCustomReference.CREF_MAIN_SVC_CATEGORY);
            custRef.setBuyerRefValue(skillNode.getNodeName());
            serviceOrder.addCustomReference(custRef);
        } else if (skillNode.getLevel().equals(2)) {
            custRef.setBuyerRefTypeId(orderBuyer.getBuyerReferenceTypeId(SOCustomReference.CREF_CATEGORY));
            custRef.setBuyerRefTypeName(SOCustomReference.CREF_CATEGORY);
            custRef.setBuyerRefValue(skillNode.getNodeName());
            serviceOrder.addCustomReference(custRef);
            addSkillNodeHierarchyToSOCustomRefList(serviceOrder, quickLookupCollection.getSkillTreeLookup().getSkillNodeById(skillNode.getParentNodeId()), orderBuyer);
        } else if (skillNode.getLevel().equals(3)) {
            custRef.setBuyerRefTypeId(orderBuyer.getBuyerReferenceTypeId(SOCustomReference.CREF_SUB_CATEGORY));
            custRef.setBuyerRefTypeName(SOCustomReference.CREF_SUB_CATEGORY);
            custRef.setBuyerRefValue(skillNode.getNodeName());
            serviceOrder.addCustomReference(custRef);
            addSkillNodeHierarchyToSOCustomRefList(serviceOrder, quickLookupCollection.getSkillTreeLookup().getSkillNodeById(skillNode.getParentNodeId()), orderBuyer);
        }
    }

    private void addServiceTypeSkillToCustomRefList(ServiceOrder serviceOrder, IOrderBuyer orderBuyer) {
        SOTask primaryTask = serviceOrder.getPrimaryTask();
        SOCustomReference custRef = new SOCustomReference();
        custRef.setBuyerRefTypeId(orderBuyer.getBuyerReferenceTypeId(SOCustomReference.CREF_SKILL));
        custRef.setBuyerRefTypeName(SOCustomReference.CREF_SKILL);
        custRef.setBuyerRefValue(quickLookupCollection.getServiceTypeLookup().getLookupServiceTypeById(primaryTask.getServiceTypeId()).getDescription());
        serviceOrder.addCustomReference(custRef);
    }
	
}