package com.servicelive.orderfulfillment.orderprep.processor.common;

import java.util.List;

public class PermitSkuChecker {
    List<String> workOrderPermitSkus;

    public boolean isWorkPermitSku(String sku) {
        return workOrderPermitSkus.contains(sku);
    }

    public void setWorkOrderPermitSkus(List<String> workOrderPermitSkus) {
        this.workOrderPermitSkus = workOrderPermitSkus;
    }
}
