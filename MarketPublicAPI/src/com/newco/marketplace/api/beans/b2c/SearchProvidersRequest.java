package com.newco.marketplace.api.beans.b2c;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;


@XSD(name="searchProvidersRequest.xsd", path="/resources/schemas/b2c/")
@XmlRootElement(name="searchProvidersRequest")
@XStreamAlias("searchProvidersRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchProvidersRequest {

}
