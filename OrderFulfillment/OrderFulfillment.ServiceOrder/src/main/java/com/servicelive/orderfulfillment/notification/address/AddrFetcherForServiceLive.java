package com.servicelive.orderfulfillment.notification.address;

public class AddrFetcherForServiceLive {
    private String serviceLiveEmailAddress;

    public String fetchDestAddr() {
        return serviceLiveEmailAddress;
    }

    public void setServiceLiveEmailAddress(String serviceLiveEmailAddress) {
        this.serviceLiveEmailAddress = serviceLiveEmailAddress;
    }
}
