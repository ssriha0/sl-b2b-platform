package com.servicelive.orderfulfillment.serviceinterface.vo;

import com.servicelive.orderfulfillment.domain.SOElement;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement()
public class OrderFulfillmentRequest {

	private Identification identification;
	private SOElement element;
	private List<Parameter> miscParameters=new ArrayList<Parameter>(5);
	
	////////////////////////////////////////////////////////////////////////////
	// SETTERS AND GETTERS
	////////////////////////////////////////////////////////////////////////////
	@XmlElement() 
	public void setIdentification(Identification identification) {
		this.identification = identification;
	}
	public Identification getIdentification() {
		return identification;
	}

	@XmlElement() 
	public void setElement(SOElement element) {
		this.element = element;
	}
	public SOElement getElement() {
		return element;
	}
	
	@XmlElement()
	public void setMiscParameters(List<Parameter> miscParameters) {
		this.miscParameters = miscParameters;
	}
	public List<Parameter> getMiscParameters() {
		return miscParameters;
	}
	
	////////////////////////////////////////////////////////////////////////////
	// MISC HELPERS
	////////////////////////////////////////////////////////////////////////////
	public void addMiscParameter(String parameterName, String  parameter) {
		miscParameters.add(new Parameter(parameterName, parameter));
	}
	
	public Serializable getParameter(String parameterName) {
	    Parameter returnVal = null;
	    for(Parameter param:miscParameters){
	        if(param.getName().equals(parameterName)){
	            returnVal = param;
	            break;
	        }
	    }
	    return returnVal.getValue();
	}
	
	public void setParameter(String parameterName, String parameterValue) {
	    Parameter returnVal = null;
	    boolean found = false;
	    for(Parameter param:miscParameters){
	        if(param.getName().equals(parameterName)){
	        	param.setValue(parameterValue);
	        	found = true;
	            break;
	        }
	    }
	    if(!found){
	    	miscParameters.add(new Parameter(parameterName, parameterValue));
	    }
	}
}
