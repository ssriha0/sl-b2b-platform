package com.servicelive.orderfulfillment.notification;

import java.util.Map;
import com.servicelive.orderfulfillment.notification.address.AddressCodeSet;

public class NotificationTaskBuilderContext {

    private Long templateId;
    private AddressCodeSet addressCodeSet;
    private Long estimationTemplateId = 331l;
    private Map<String, Object> processVariablesCopy;
    Map<String,String> dataMapValues;

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setAddressCodeSet(AddressCodeSet addressCodeSet) {
        this.addressCodeSet = addressCodeSet;
    }

    public AddressCodeSet getAddressCodeSet() {
        return addressCodeSet;
    }

    public void setProcessVariablesCopy(Map<String, Object> processVariablesCopy) {
        this.processVariablesCopy = processVariablesCopy;
    }

    public Map<String, Object> getProcessVariablesCopy() {
        return processVariablesCopy;
    }

    public void setDataMapValues(Map<String, String> dataMapValues) {
        this.dataMapValues = dataMapValues;
    }

    public Map<String, String> getDataMapValues() {
        return dataMapValues;
    }

	public Long getEstimationTemplateId() {
		return estimationTemplateId;
	}

	public void setEstimationTemplateId(Long estimationTemplateId) {
		this.estimationTemplateId = estimationTemplateId;
	}
}
