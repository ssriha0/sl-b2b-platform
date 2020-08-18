package com.servicelive.domain.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "zip_geocode")
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleZipTimeZone implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="zip", unique=true, nullable=false)
    private String zip;

    @Column(name="time_zone")
    private String timeZoneId;

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }
}
