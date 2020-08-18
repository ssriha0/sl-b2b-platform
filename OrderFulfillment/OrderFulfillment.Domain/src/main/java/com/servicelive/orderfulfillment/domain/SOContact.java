package com.servicelive.orderfulfillment.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.servicelive.orderfulfillment.domain.type.ContactLocationType;
import com.servicelive.orderfulfillment.domain.type.ContactType;
import com.servicelive.orderfulfillment.domain.type.EntityType;


@Entity
@Table(name = "so_contact")
@XmlRootElement()
public class SOContact extends SOChild implements Comparable<SOContact> {

	private static final long serialVersionUID = 9180446939179439349L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "so_contact_id")
	private Integer soContactId;

	//normally it is primary contact
	@Column(name = "so_contact_type_id")
	private Integer contactTypeId = ContactType.PRIMARY.getId();
		
	@Column(name = "business_name")
	private String businessName= null;
	
	@Column(name = "last_name")
	private String lastName= null;
	
	@Column(name = "first_name")
	private String firstName=null;
	
	@Column(name = "mi")
	private String mi=null;
	
	@Column(name = "suffix")
	private String suffix=null;
	
	@Column(name = "email")
	private String email=null;
    
	@Column(name = "honorific")
	private String honorific = null;
     
	@Column(name = "entity_type_id")
	private Integer entityTypeId =null;
    
	@Column(name = "entity_id")
	private Integer entityId;
    
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "contact")
	private Set<SOPhone> phones = new HashSet<SOPhone>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "contact")
	private List<SOContactLocation> soContactLocations;

	public String getBusinessName() {
		return businessName;
	}

	public ContactType getContactTypeId() {
		return ContactType.fromId(contactTypeId);
	}

	public String getEmail() {
		return email;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public EntityType getEntityType() {
        if(entityTypeId==null)
            return EntityType.UNKNOWN;
        else
		    return EntityType.fromId(entityTypeId);
	}

	public String getFirstName() {
		return firstName;
	}

	public String getHonorific() {
		return honorific;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMi() {
		return mi;
	}

	public Set<SOPhone> getPhones() {
		return phones;
	}

	public Integer getSoContactId() {
		return soContactId;
	}

	public List<SOContactLocation> getSoContactLocations() {
		return soContactLocations;
	}

	public String getSuffix() {
		return suffix;
	}

	@XmlElement()
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@XmlElement()
	public void setContactTypeId(ContactType contactTypeId) {
		this.contactTypeId = contactTypeId.getId();
	}

	@XmlElement()
	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement()
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	@XmlElement()
	public void setEntityType(EntityType entityType) {
        switch(entityType){
            case UNKNOWN:
                entityTypeId=null;
                break;
            default:
                entityTypeId = entityType.getId();
        }
	}

	@XmlElement()
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@XmlElement()
	public void setHonorific(String honorific) {
		this.honorific = honorific;
	}

	@XmlElement()
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@XmlElement()
	public void setMi(String mi) {
		this.mi = mi;
	}

	@XmlElementWrapper()
	@XmlElement(name = "soPhone")
	public void setPhones(Set<SOPhone> phones) {
		this.phones = phones;
		for(SOPhone phone : this.phones){
			phone.setContact(this);
		}
	}

	@XmlElement()
	public void setSoContactId(Integer soContactId) {
		this.soContactId = soContactId;
	}
	
	@XmlElementWrapper()
	@XmlElement(name = "soContactLocation")
	public void setSoContactLocations(List<SOContactLocation> soContactLocations) throws Exception {
		//there can only be one contact location per contact
		//it is a weird relationship - because we keep the contact type actually in
		//contact location
		if(soContactLocations.size() > 1) throw new Exception("We can only have one contact location per contact");
		for(SOContactLocation scl : soContactLocations){
			scl.setContact(this);
		}
		this.soContactLocations = soContactLocations;
	}

	@XmlElement()
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public void addContactLocation(ContactLocationType contactLocationType){
		SOContactLocation contactLocation = new SOContactLocation();
		contactLocation.setContactLocationTypeId(contactLocationType);
		//there can only be one contact location per contact
		//it is a weird relationship - because we keep the contact type actually in
		//contact location
		if(soContactLocations == null) soContactLocations = new ArrayList<SOContactLocation>();
		soContactLocations.clear();
		contactLocation.setContact(this);
		soContactLocations.add(contactLocation);
	}
	
	public List<String> validate(){
		List<String> returnVal = new ArrayList<String>();
		if(StringUtils.isBlank(lastName) && StringUtils.isBlank(businessName)){
			returnVal.add("Please specify either Last Name or Company Name for this Contact");
		}
		return returnVal;
	}
	// changed for SL-16791
	public int compareTo(SOContact o) {
		Integer tContactLocationTypeId = 0;
		if(this.soContactLocations != null && this.soContactLocations.size() > 0){
			tContactLocationTypeId = this.soContactLocations.get(0).getContactLocationTypeId().getId();
		}
		Integer oContactLocationTypeId = 0;
		if(o.soContactLocations != null && o.soContactLocations.size() > 0){
			oContactLocationTypeId = o.soContactLocations.get(0).getContactLocationTypeId().getId();
		}
		CompareToBuilder builder = new CompareToBuilder().append(businessName, o.businessName)
							.append(contactTypeId, o.contactTypeId)
							.append(email, o.email).append(entityId, o.entityId)
							.append(entityTypeId, o.entityTypeId).append(firstName, o.firstName)
							.append(lastName, o.lastName).append(honorific, o.honorific)
							.append(tContactLocationTypeId, oContactLocationTypeId);
		int compare = builder.toComparison();
		if(compare != 0) return compare;
		compare = comparePhones(this.phones, o.phones);
		return compare;
	}
	
	private int comparePhones(Set<SOPhone> tPhones, Set<SOPhone> oPhones) {
		
		if(tPhones == null && oPhones != null) return -1;
		if(tPhones != null && oPhones == null) return 1;
		
		Set<SOPhone> result = new HashSet<SOPhone>(tPhones);
		result.removeAll(oPhones);
		if(result.size() == 0){
			result = new HashSet<SOPhone>(oPhones);
			result.removeAll(tPhones);
			return result.size();
		} else {
			return 1;
		}
	}
	
	public ContactLocationType getContactLocationType(){
		if(this.soContactLocations != null && this.soContactLocations.size() > 0){
			return this.soContactLocations.get(0).getContactLocationTypeId();
		}
		return null;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	/**
   	* A class that overrides equals must also override hashCode.
  	*/
	@Override 
	public int hashCode(){
		Integer tContactLocationTypeId = 0;
		if(this.soContactLocations != null && this.soContactLocations.size() > 0){
			tContactLocationTypeId = this.soContactLocations.get(0).getContactLocationTypeId().getId();
		}
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(businessName).append(contactTypeId)
		 	.append(email).append(entityId)
		 	.append(entityTypeId).append(firstName)
		 	.append(lastName).append(honorific)
		 	.append(mi).append(tContactLocationTypeId).append(phones);
		return hcb.toHashCode();
	}
	
	public boolean equals(Object aThat){
	    if ( this == aThat ) return true;
	    if ( !(aThat instanceof SOContact) ) return false;
	    SOContact o = (SOContact)aThat;
		Integer tContactLocationTypeId = 0;
		if(this.soContactLocations != null && this.soContactLocations.size() > 0){
			tContactLocationTypeId = this.soContactLocations.get(0).getContactLocationTypeId().getId();
		}
		Integer oContactLocationTypeId = 0;
		if(o.soContactLocations != null && o.soContactLocations.size() > 0){
			oContactLocationTypeId = o.soContactLocations.get(0).getContactLocationTypeId().getId();
		}
	    boolean isEqual = new EqualsBuilder().append(businessName, o.businessName)
					.append(contactTypeId, o.contactTypeId)
					.append(email, o.email).append(entityId, o.entityId)
					.append(entityTypeId, o.entityTypeId).append(firstName, o.firstName)
					.append(lastName, o.lastName).append(honorific, o.honorific)
					.append(mi, o.mi)							
					.append(tContactLocationTypeId, oContactLocationTypeId).isEquals();
	    if(!isEqual) return isEqual;
	    int compare = comparePhones(this.phones, o.phones);
	    return (compare == 0);
	}

	public void setLocation(SOLocation soLocation) {
		getSoContactLocations().get(0).setLocation(soLocation);
	}
	
}
