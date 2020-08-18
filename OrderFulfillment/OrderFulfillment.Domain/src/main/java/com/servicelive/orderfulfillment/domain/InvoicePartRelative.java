package com.servicelive.orderfulfillment.domain;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlTransient;

public abstract class InvoicePartRelative extends InvoicePartBase
{
  private static final long serialVersionUID = -2948203165334809285L;

  @XmlTransient
  public abstract void setInvoiceParts(SOProviderInvoiceParts paramSOProviderInvoiceParts);

  public abstract SOProviderInvoiceParts getInvoiceParts();

  public void afterUnmarshal(Unmarshaller unmarshaller, Object parent)
  {
    if ((parent instanceof SOProviderInvoiceParts))
      setInvoiceParts((SOProviderInvoiceParts)parent);
  }
}