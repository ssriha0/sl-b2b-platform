package com.servicelive.orderfulfillment.notification.address;

public class AddressCodeSet {
    private AddressCode alertFromCode;
    private AddressCode alertToCode;
    private AddressCode alertCcCode;
    private AddressCode alertBccCode;

    public AddressCode getAlertFromCode() {
        return alertFromCode;
    }

    public void setAlertFromCode(AddressCode alertFromCode) {
        this.alertFromCode = alertFromCode;
    }

    public AddressCode getAlertToCode() {
        return alertToCode;
    }

    public void setAlertToCode(AddressCode alertToCode) {
        this.alertToCode = alertToCode;
    }

    public AddressCode getAlertCcCode() {
        return alertCcCode;
    }

    public void setAlertCcCode(AddressCode alertCcCode) {
        this.alertCcCode = alertCcCode;
    }

    public AddressCode getAlertBccCode() {
        return alertBccCode;
    }

    public void setAlertBccCode(AddressCode alertBccCode) {
        this.alertBccCode = alertBccCode;
    }
}
