package com.newco.marketplace.api.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("partCoverage")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartCoverage {

	@XStreamAlias("partCoverageId")
	private Integer partCoverageId;

	@XStreamAlias("partCoverageValue")
	private String partCoverageValue;

	/**
	 * @return the partCoverageId
	 */
	public Integer getPartCoverageId() {
		return partCoverageId;
	}

	/**
	 * @param partCoverageId
	 *            the partCoverageId to set
	 */
	public void setPartCoverageId(Integer partCoverageId) {
		this.partCoverageId = partCoverageId;
	}

	/**
	 * @return the partCoverageValue
	 */
	public String getPartCoverageValue() {
		return partCoverageValue;
	}

	/**
	 * @param partCoverageValue
	 *            the partCoverageValue to set
	 */
	public void setPartCoverageValue(String partCoverageValue) {
		this.partCoverageValue = partCoverageValue;
	}

}
