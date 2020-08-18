package com.servicelive.orderfulfillment.decision;

import java.util.List;

import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class TemplatePresentCheck extends AbstractServiceOrderDecision {

	private static final long serialVersionUID = 7258373364727565809L;
	/*
	 * Checks if template is present for this sos
	 */
	@SuppressWarnings("unchecked")
	public String decide(OpenExecution execution) {
		ServiceOrder so = getServiceOrder(execution);
		String templateName = getBuyerCustomRefTemplateName(so);
		if ((null != templateName && templateName != "")) {
			execution.setVariable(ProcessVariableUtil.TEMPLATE_NAME,
					templateName);
			return "HasTemplate";
		} else {
			// find template using sku if it is not given in the request xml
			String sku =null;
			if(null!=so.getPrimaryTask()){
				sku = so.getPrimaryTask().getExternalSku();
			}
			if (null != so.getBuyerId() && null != sku) {
				Integer templateId = serviceOrderDao.getTemplateId(sku, so
						.getBuyerId().intValue());
				if (null != templateId) {
					templateName = serviceOrderDao
							.getTemplateNameFromTemplateId(templateId);
					execution.setVariable(ProcessVariableUtil.TEMPLATE_NAME,
							templateName);
					return "HasTemplate";
				} else {
					return "NoTemplate";
				}
			}

		}
		return "NoTemplate";
	}

	/**
	 * Retrieve template name from custom reference "TEMPLATE NAME"
	 * @param serviceOrder
	 * @return
	 */
	private String getBuyerCustomRefTemplateName(ServiceOrder serviceOrder) {
		List<SOCustomReference> customReferences = serviceOrder
				.getCustomReferences();
		if (customReferences != null && !customReferences.isEmpty()) {
			for (SOCustomReference customRef : customReferences) {
				if (customRef.getBuyerRefTypeName() != null
						&& customRef.getBuyerRefTypeName().equals(
								SOCustomReference.CREF_TEMPLATE_NAME)) {
					return customRef.getBuyerRefValue();
				}
			}
		}
		return null;
	}

}