package com.sears.os.service;




public abstract class ABaseValidator implements ServiceConstants {

	protected ABaseService service;

	public ABaseValidator(ABaseService service) {
		this.service = service;
	}

	/**
	 * Returns the service.
	 * @return ABaseService
	 */
	public ABaseService getService() {
		return service;
	}

	/**
	 * Sets the service.
	 * @param service The service to set
	 */
	public void setService(ABaseService service) {
		this.service = service;
	}
}
