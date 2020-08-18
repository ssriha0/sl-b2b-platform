package com.newco.marketplace.api.beans.wallet;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("promotion")
public class Promotion
{
  @XStreamAlias("promocode")
  private String promocode;

  @XStreamAlias("promotype")
  private String promotype;

  @XStreamAlias("value")
  private double value;

  public String getPromotype()
  {
    return this.promotype;
  }

  public void setPromotype(String promotype) {
    this.promotype = promotype;
  }

  public double getValue() {
    return this.value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  public void setPromocode(String promocode) {
	this.promocode = promocode;
  }

  public String getPromocode() {
	return promocode;
  }
}