package com.servicelive.orderfulfillment.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;

@MappedSuperclass
public class InvoicePartBase extends InvoicePartElement
{
  private static final long serialVersionUID = -5179497794221697898L;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="created_date")
  private Date createdDate = new Date();

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="modified_date")
  private Date modifiedDate = new Date();

  public Date getCreatedDate() {
    return this.createdDate;
  }

  public Date getModifiedDate() {
    return this.modifiedDate;
  }
  @XmlElement
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
  @XmlElement
  public void setModifiedDate(Date modifiedDate) {
    this.modifiedDate = modifiedDate;
  }
}