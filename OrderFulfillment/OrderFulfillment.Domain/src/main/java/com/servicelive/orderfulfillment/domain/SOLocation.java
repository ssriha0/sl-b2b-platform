package com.servicelive.orderfulfillment.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.servicelive.orderfulfillment.domain.type.LocationClassification;
import com.servicelive.orderfulfillment.domain.type.LocationType;

@Entity
@Table(name = "so_location")
@XmlRootElement()
public class SOLocation extends SOChild implements Comparable<SOLocation> {
    
	/**
	 *
	 */
	private static final long serialVersionUID = 4020273533733141746L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "so_locn_id")
	private Integer locationId;

	@Column(name = "street_1")
	private String street1;

	@Column(name = "street_2")
	private String street2;

	@Column(name = "city")
	private String city;

	@Column(name = "state_cd")
	private String state;

	@Column(name = "zip")
	private String zip;

	@Column(name = "zip4")
	private String zip4;

	@Column(name = "apt_no")
	private String aptNumber;

	@Column(name = "country")
	private String country;
	
	@Column(name = "so_locn_type_id")
	private Integer soLocationTypeId;
	
	@Column(name = "so_locn_class_id")
	private Integer soLocationClassId;

	@Column(name = "locn_name")
	private String locationName;

	@Column(name = "locn_notes")
	private String locationNote;
	

	public String getAptNumber() {
		return aptNumber;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public String getLocationNote() {
		return locationNote;
	}

	public LocationClassification getSoLocationClassId() {
        if (soLocationClassId != null) {
		    return LocationClassification.fromId(soLocationClassId);
        }
        return null;
	}

	public LocationType getSoLocationTypeId() {
		if (soLocationTypeId != null) {
			return LocationType.fromId(soLocationTypeId);
		}
		return null;
	}

	public String getState() {
		return state;
	}

	public String getStreet1() {
		return street1;
	}

	public String getStreet2() {
		return street2;
	}

	public String getZip() {
		return zip;
	}

	public String getZip4() {
		return zip4;
	}

	@XmlElement()
	public void setAptNumber(String aptNumber) {
		this.aptNumber = aptNumber;
	}

	@XmlElement()
	public void setCity(String city) {
		this.city = city;
	}

	@XmlElement()
	public void setCountry(String country) {
		this.country = country;
	}

	@XmlElement()
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	@XmlElement()
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	@XmlElement()
	public void setLocationNote(String locationNote) {
		this.locationNote = locationNote;
	}

	@XmlElement()
	public void setSoLocationClassId(LocationClassification soLocationClassId) {
		if (soLocationClassId != null) {
			this.soLocationClassId = soLocationClassId.getId();
		}
		else {
			this.soLocationClassId = null;
		}
	}

	@XmlElement()
	public void setSoLocationTypeId(LocationType soLocationTypeId) {
		if (soLocationTypeId != null) {
			this.soLocationTypeId = soLocationTypeId.getId();
		}
		else {
			this.soLocationTypeId = null;
		}
	}

	@XmlElement()
	public void setState(String state) {
		this.state = state;
	}

	@XmlElement()
	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	@XmlElement()
	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	@XmlElement()
	public void setZip(String zip) {
		this.zip = zip;
	}

	@XmlElement()
	public void setZip4(String zip4) {
		this.zip4 = zip4;
	}

    @Override
     public List<String> validate() {
        List<String> returnVal = new ArrayList<String>();
        if(StringUtils.isBlank(street1))
            returnVal.add("Street address needs to be specified.");

        if(StringUtils.isBlank(city))
            returnVal.add("City needs to be specified.");

        if(StringUtils.isBlank(zip))
            returnVal.add("Zip code needs to be specified.");

        if(StringUtils.isBlank(state))
            returnVal.add("State needs to be specified.");
        return returnVal;
    }
    
	@Override 
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	// changed for SL-16791
	public int compareTo(SOLocation o) {
		CompareToBuilder compareToBuilder = new CompareToBuilder();
		return compareToBuilder.append(aptNumber, o.aptNumber).append(city, o.city)
							 	.append(locationName, o.locationName)
							 	.append(locationNote, o.locationNote).append(soLocationClassId, o.soLocationClassId)
							 	.append(soLocationTypeId, o.soLocationTypeId).append(state, o.state)
							 	.append(street1, o.street1).append(street2, o.street2)
							 	.append(zip, o.zip).append(zip4, o.zip4).toComparison();
	}
	
	/**
   	* A class that overrides equals must also override hashCode.
  	*/
	@Override 
	public int hashCode(){
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(aptNumber).append(city)
		 	.append(country).append(locationName)
		 	.append(locationNote).append(soLocationClassId)
		 	.append(soLocationTypeId).append(state)
		 	.append(street1).append(street2)
		 	.append(zip).append(zip4);
		 return hcb.toHashCode();
	}
	
	@Override 
	public boolean equals(Object aThat){
	    if ( this == aThat ) return true;
	    if ( !(aThat instanceof SOLocation) ) return false;
	
	    SOLocation o = (SOLocation)aThat;
	    EqualsBuilder eBuilder = new EqualsBuilder();
	    return eBuilder.append(aptNumber, o.aptNumber).append(city, o.city)
					 	.append(country, o.country).append(locationName, o.locationName)
					 	.append(locationNote, o.locationNote).append(soLocationClassId, o.soLocationClassId)
					 	.append(soLocationTypeId, o.soLocationTypeId).append(state, o.state)
					 	.append(street1, o.street1).append(street2, o.street2)
					 	.append(zip, o.zip).append(zip4, o.zip4).isEquals();
	}
}
