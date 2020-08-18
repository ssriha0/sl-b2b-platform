package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;


public class ZipParameterBean extends RatingParameterBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1570310172454495260L;
	private Integer credentialId;
    private Integer radius = null;
    
    private String street = null;
    
    private String zipcode = null;
    private String locState = null; 
    private String city = null;
    
    

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLocState() {
        return locState;
    }

    public void setLocState(String locState) {
        this.locState = locState;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }
    
    public Integer getRadius() {
		return radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

	public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
