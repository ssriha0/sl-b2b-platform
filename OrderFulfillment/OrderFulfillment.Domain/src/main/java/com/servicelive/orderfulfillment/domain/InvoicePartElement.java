package com.servicelive.orderfulfillment.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({SOProviderInvoiceParts.class, SoProviderInvoicePartLocation.class, SoProviderInvoicePartDocument.class})
@MappedSuperclass
public class InvoicePartElement
  implements Serializable
{
  private static final long serialVersionUID = -6401342746027210469L;

  public List<String> validate()
  {
    return Collections.emptyList();
  }

  public void assign(InvoicePartElement Ie)
  {
    if (Ie.getTypeName().equals(getTypeName()));
  }

  public void update(InvoicePartElement Ie)
  {
    if (Ie.getTypeName().equals("INVOICE_PARTS"));
  }

  public String getTypeName() {
    return getClass().getName();
  }
}