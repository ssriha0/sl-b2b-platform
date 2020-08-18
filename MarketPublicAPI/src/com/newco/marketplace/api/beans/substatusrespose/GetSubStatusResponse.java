package com.newco.marketplace.api.beans.substatusrespose;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("subStatusResponse") 
public class GetSubStatusResponse implements IAPIResponse{

	public GetSubStatusResponse() {
		this.schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
		this.namespace = PublicAPIConstant.GET_SUBSTATUS_NAMESPACE;
	}

	@XStreamAlias("version")
	@XStreamAsAttribute()
	private String version;

	@XStreamAlias("xsi:schemaLocation")
	@XStreamAsAttribute()
	private String schemaLocation;

	@XStreamAlias("xmlns")
	@XStreamAsAttribute()
	private String namespace;

	@XStreamAlias("xmlns:xsi")
	@XStreamAsAttribute()
	private String schemaInstance;

	@XStreamAlias("results")
	private Results results;
	
	@XStreamImplicit(itemFieldName="Status")
	private List<SoSubStatuses> soSubStatuses;
	
	@XStreamOmitField
	private Integer[] statusIdArray;
	
	@XStreamOmitField
	private Map<Integer, String> statusNameMap;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSchemaLocation() {
		return schemaLocation;
	}

	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getSchemaInstance() {
		return schemaInstance;
	}

	public void setSchemaInstance(String schemaInstance) {
		this.schemaInstance = schemaInstance;
	}

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public Integer[] getStatusIdArray() {
		return statusIdArray;
	}

	public void setStatusIdArray(Integer[] statusIdArray) {
		this.statusIdArray = statusIdArray;
	}

	public Map<Integer, String> getStatusNameMap() {
		return statusNameMap;
	}

	public void setStatusNameMap(Map<Integer, String> statusNameMap) {
		this.statusNameMap = statusNameMap;
	}

	public List<SoSubStatuses> getSoSubStatuses() {
		return soSubStatuses;
	}

	public void setSoSubStatuses(List<SoSubStatuses> soSubStatuses) {
		this.soSubStatuses = soSubStatuses;
	}

}
