package com.servicelive.orderfulfillment.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class SOElementCollection extends SOElement 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5494949354733915663L;
	
	@XmlElementWrapper()
	@XmlElement(name = "element")
	private List<SOElement> elements = new ArrayList<SOElement>();

	public void addElement(SOElement se) {
		elements.add(se);
	}
	
	public void addAllElements(List<? extends SOElement> elements) {
		this.elements.addAll(elements);
	}
	
	// validate collection
	@Override
	public List<String> validate() {
		List<String> errors = new ArrayList<String>();
		for (SOElement se:elements) {
			errors.addAll(se.validate());	
		}
		return errors;
	}
	
	public List<SOElement> getElements(){
		return elements;
	}
}
