package com.newco.marketplace.api.beans.alerts;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information on a list of Parameters.
 * @author Infosys
 *
 */
@XStreamAlias("parameters")
public class Parameters {

	@XStreamImplicit(itemFieldName="parameter")
	private  List<Parameter> parameterList ;

	public List<Parameter> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<Parameter> parameterList) {
		this.parameterList = parameterList;
	}


}
