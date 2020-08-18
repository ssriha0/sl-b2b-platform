package com.servicelive.orderfulfillment.domain;

import java.io.Serializable;
import java.util.Date;

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

import com.servicelive.orderfulfillment.domain.type.ContactLocationType;

@Entity
@Table(name = "so_contact_locn")
@XmlRootElement()
public class SOContactLocation implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -30467941173785715L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "so_contact_locn_id")
	private Integer soContactLocationId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumns({
		@JoinColumn(name = "so_contact_id", referencedColumnName = "so_contact_id", nullable = false),
		@JoinColumn(name = "so_id", referencedColumnName = "so_id", nullable = false)
	})
	private SOContact contact;
	
	@Column(name = "so_contact_locn_type_id")
	private Integer contactLocationTypeId;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "so_locn_id")
	private SOLocation location;
	
	@Column(name = "created_date")
	private Date createdDate;


	public SOContact getContact() {
		return contact;
	}

	public ContactLocationType getContactLocationTypeId() {
		return ContactLocationType.fromId(contactLocationTypeId);
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public SOLocation getLocation() {
		return location;
	}

	public Integer getSoContactLocationId() {
		return soContactLocationId;
	}

	@XmlTransient()
	public void setContact(SOContact contact) {
		this.contact = contact;
	}

	@XmlElement()
	public void setContactLocationTypeId(ContactLocationType contactLocationTypeId) {
		this.contactLocationTypeId = contactLocationTypeId.getId();
	}

	@XmlElement()
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@XmlTransient()
	public void setLocation(SOLocation location) {
		this.location = location;
	}

	@XmlElement()
	public void setSoContactLocationId(Integer soContactLocationId) {
		this.soContactLocationId = soContactLocationId;
	}
	
	public void afterUnmarshal(Unmarshaller  target, Object parent){
		if (parent instanceof SOContact){
			this.setContact((SOContact)parent);
		}
	}
	
}
