/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-Oct-2009	MarketPublicAPI SHC			1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.search.providerProfile;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.api.common.FacetTypeEnum;
import com.newco.marketplace.search.types.Bucket;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This class defines the facet element used for Providers Search.
 * @author priti
 *
 */
@XStreamAlias("facetType")
public class FacetType {
	@OptionalParam
	@XStreamAlias("facetName")
	private String facetName;
	
	@OptionalParam
	@XStreamAlias("startRange")
	private String startRange;
	
	@OptionalParam
	@XStreamAlias("endRange")
	private String endRange;
	
	@OptionalParam
	@XStreamAlias("count")
	private Integer count;
	
	@XStreamAlias("type")
	@XStreamAsAttribute()
	private String type;

	public FacetType(Bucket bucket) {
		this.count = bucket.getCount();
		
		switch (bucket.getType()) {
		case Bucket.DISTANCE_TYPE:
			this.type = FacetTypeEnum.PROVIDER_DISTANCE.toString();
			this.startRange = bucket.getStartRange();
			this.endRange = bucket.getEndRange();
			this.count = bucket.getCount();
			break;
		case Bucket.RATING_TYPE:
			this.type = FacetTypeEnum.PROVIDER_RATINGS.toString();
			this.startRange = bucket.getStartRange();
			this.endRange = bucket.getEndRange();
			this.count = bucket.getCount();
			break;
		case Bucket.LANGUAGE_TYPE:
			this.type = FacetTypeEnum.PROVIDER_LANGUAGE.toString();
			this.facetName = bucket.getStartRange();
			break;
		}
	}
	
	public String getFacetName() {
		return facetName;
	}

	public void setFacetName(String facetName) {
		this.facetName = facetName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getStartRange() {
		return startRange;
	}

	public void setStartRange(String startRange) {
		this.startRange = startRange;
	}

	public String getEndRange() {
		return endRange;
	}

	public void setEndRange(String endRange) {
		this.endRange = endRange;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
