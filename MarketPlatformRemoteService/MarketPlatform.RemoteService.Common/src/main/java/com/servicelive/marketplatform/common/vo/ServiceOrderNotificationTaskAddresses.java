package com.servicelive.marketplatform.common.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServiceOrderNotificationTaskAddresses implements Serializable {

    private static final long serialVersionUID = 1L;

    private String from;
    private String to;
    private String cc;
    private String bcc;

    @XmlElement
    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    @XmlElement
    public void setTo(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    @XmlElement
    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getCc() {
        return cc;
    }

    @XmlElement
    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getBcc() {
        return bcc;
    }
}
