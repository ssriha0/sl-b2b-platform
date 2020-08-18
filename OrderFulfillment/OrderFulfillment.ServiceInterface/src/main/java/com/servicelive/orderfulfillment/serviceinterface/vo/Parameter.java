package com.servicelive.orderfulfillment.serviceinterface.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Mustafa Motiwala
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="parameter")
public class Parameter {
    @XmlAttribute(name="name")
    private String name;
    @XmlElement(name="value")
    private String value;
    
    public Parameter(){} //Here to keep frameworks happy.
    
    public Parameter(String name,  String value) {
        super();
        this.name = name;
        this.value = value;
    }
    /**
     * @return the key
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the key to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
    /**
     * @param value the value to set
     */
    public void  setValue(String value) {
        this.value = value;
    }
}
