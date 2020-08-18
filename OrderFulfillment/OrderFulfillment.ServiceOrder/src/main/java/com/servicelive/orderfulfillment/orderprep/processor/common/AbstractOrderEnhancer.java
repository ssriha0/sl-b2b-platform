package com.servicelive.orderfulfillment.orderprep.processor.common;

import com.servicelive.orderfulfillment.orderprep.processor.IOrderEnhancementProcessor;
import com.servicelive.orderfulfillment.validation.IValidationUtil;
import org.apache.log4j.Logger;

public abstract class AbstractOrderEnhancer implements IOrderEnhancementProcessor {
    protected IValidationUtil validationUtil;
    protected final Logger logger = Logger.getLogger(getClass());

    public void setValidationUtil(IValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }
}
