/**
 * 
 */
package com.servicelive.domain.spn.detached;

/**
 * This class is used for Log images in the EMAILs
 * @author hoza
 *
 */
public final class EmailClassPathResource {
	 private String nameOfResourceInTemplate;
	 private String locationOfResource;
	/**
	 * @return the nameOfResourceInTemplate
	 */
	public String getNameOfResourceInTemplate() {
		return nameOfResourceInTemplate;
	}
	/**
	 * @param nameOfResourceInTemplate the nameOfResourceInTemplate to set
	 */
	public void setNameOfResourceInTemplate(String nameOfResourceInTemplate) {
		this.nameOfResourceInTemplate = nameOfResourceInTemplate;
	}
	/**
	 * @return the locationOfResource
	 */
	public String getLocationOfResource() {
		return locationOfResource;
	}
	/**
	 * @param locationOfResource the locationOfResource to set
	 */
	public void setLocationOfResource(String locationOfResource) {
		this.locationOfResource = locationOfResource;
	}
}
