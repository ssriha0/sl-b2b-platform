package com.servicelive.orderfulfillment.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.servicelive.orderfulfillment.domain.type.PhoneClassification;
import com.servicelive.orderfulfillment.domain.type.PhoneType;

@Entity
@Table(name = "so_contact_phones")
@XmlRootElement()
public class SOPhone extends SOBase implements Comparable<SOPhone> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -49100993851115552L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "so_contact_phone_id")
	private Integer soContactPhoneId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumns({
		@JoinColumn(name = "so_contact_id", referencedColumnName = "so_contact_id", nullable = false),
		@JoinColumn(name = "so_id", referencedColumnName = "so_id", nullable = false)
	})
	private SOContact contact;
	
	@Column(name = "phone_no")
	private String phoneNo;
	
	@Column(name = "phone_no_ext")
	private String phoneExt;
	
	@Column(name = "phone_type_id")
	private Integer phoneType;
	
	@Column(name = "so_phone_class_id")
	private Integer classId;
	
	@Column(name = "primary_contact_ind")
	private Integer primaryContactInd;

	public PhoneClassification getPhoneClass() {
		if (classId == null) {
			return null;
		}
		return PhoneClassification.fromId(classId);
	}

	public String getPhoneExt() {
		return phoneExt;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public PhoneType getPhoneType() {
		return PhoneType.fromId(phoneType);
	}
	
	public Integer getPhoneTypeId() {
		return phoneType;
	}

	public Integer getPrimaryContactInd() {
		return primaryContactInd;
	}

	public Integer getSoContactPhoneId() {
		return soContactPhoneId;
	}

	@XmlElement()
	public void setPhoneClass(PhoneClassification phoneClass) {
		if (phoneClass == null) {
			this.classId = null;
		} else {
		this.classId = phoneClass.getId();
	}
	}

	@XmlElement()
	public void setPhoneExt(String phoneExt) {
		this.phoneExt = phoneExt;
	}

	@XmlElement()
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@XmlElement()
	public void setPhoneType(PhoneType phoneType) {
		this.phoneType = phoneType.getId();
	}

	@XmlElement()
	public void setPrimaryContactInd(Integer primaryContactInd) {
		this.primaryContactInd = primaryContactInd;
	}

	@XmlElement()
	public void setSoContactPhoneId(Integer soContactPhoneId) {
		this.soContactPhoneId = soContactPhoneId;
	}

	public void afterUnmarshal(Unmarshaller  target, Object parent){
		this.setContact((SOContact)parent);
	}
	
	@XmlTransient()
	public void setContact(SOContact contact) {
		this.contact = contact;
	}

	public SOContact getContact() {
		return contact;
	}
	
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	
	public int compareTo(SOPhone o) {
		return new CompareToBuilder().append(classId, o.classId).append(phoneExt, o.phoneExt)
		 	.append(phoneNo, o.phoneNo).append(phoneType, o.phoneType)
		 	.append(primaryContactInd, o.primaryContactInd).toComparison();
	}
	
	@Override
	public boolean equals(Object aThat){
	    if ( this == aThat ) return true;
	    if ( !(aThat instanceof SOPhone) ) return false;
	
	    SOPhone o = (SOPhone)aThat;
	    return new EqualsBuilder().append(classId, o.classId).append(phoneExt, o.phoneExt)
					 	.append(phoneNo, o.phoneNo).append(phoneType, o.phoneType)
					 	.append(primaryContactInd, o.primaryContactInd).isEquals();
	}
	
	@Override
	public int hashCode(){
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(classId).append(phoneExt)
		 	.append(phoneNo).append(phoneType)
		 	.append(primaryContactInd);
		 return hcb.toHashCode();
	}
}
