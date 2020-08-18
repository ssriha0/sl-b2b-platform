package com.newco.marketplace.api.common;

public interface IAPIRequest {

	public void setVersion(String version);

	/**
	 * Set xml schemaLocation
	 * @param schemaLocation
	 */
	public void setSchemaLocation(String schemaLocation);

	/**
	 * Set xml namespace
	 * @param namespace
	 */
	public void setNamespace(String namespace);

	/**
	 * Set xml schemaInstance
	 * @param schemaInstance
	 */
	public void setSchemaInstance(String schemaInstance);
}
