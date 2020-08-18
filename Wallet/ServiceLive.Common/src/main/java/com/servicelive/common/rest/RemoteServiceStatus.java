package com.servicelive.common.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class RemoteServiceStatus {
	private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
