package com.servicelive.bus.util;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: Mustafa Motiwala
 * Date: Mar 26, 2010
 * Time: 6:51:48 PM
 */
@XmlRootElement(name = "TestEventSource")
@XmlAccessorType(XmlAccessType.FIELD)
public class TestEventSource implements Serializable{
    /** generated serialVersionUID */
	private static final long serialVersionUID = 3738669600546359600L;
	
	@SuppressWarnings("unused")
	@XmlAttribute(name = "name")
    private String id = "This is an example Event Source.";
}
