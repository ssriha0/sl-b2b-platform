package com.newco.marketplace.interfaces;

import com.newco.marketplace.api.beans.Results;

public interface IAPIResponse {
	public void setResults(Results results);
	public void setVersion(String version);
	public void setSchemaLocation(String schemaLocation);
	public void setNamespace(String namespace);
	public void setSchemaInstance(String schemaInstance);
}
