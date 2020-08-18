package com.servicelive.orderfulfillment.domain;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlTransient;

@MappedSuperclass
public class InvoicePartChild extends InvoicePartRelative
{
  private static final long serialVersionUID = 3807084728543702789L;

  @ManyToOne(optional=false, cascade = CascadeType.PERSIST)
  @JoinColumn(name="invoice_part_id", referencedColumnName="so_provider_invoice_parts_id")
  protected SOProviderInvoiceParts invoiceParts;

  public SOProviderInvoiceParts getInvoiceParts()
  {
    return this.invoiceParts;
  }

  @XmlTransient
  public void setInvoiceParts(SOProviderInvoiceParts invoiceParts) {
    this.invoiceParts = invoiceParts;
  }
}