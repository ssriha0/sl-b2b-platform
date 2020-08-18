package com.newco.marketplace.translator.dao;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Contact information that was represented in an incident
 * @author gjacks8
 *
 */
@MappedSuperclass
public abstract class AbstractIncidentContact {

	private String city;
	private String firstName;
	/**
	 * Unique ID for a incident contact
	 */
	private Integer incidentContactID;
	private String lastName;
	private String phone1;
	private String phone1Ext;
	private String phone2;
	private String phone2Ext;
	private String state;
	private String street1;
	private String street2;
	private String zip;
	private String zipExt;
	private String email;
	
	/**
	 * Min constructor
	 */
	public AbstractIncidentContact() {
		// intentionally blank
	}
	
	/**
	 * Full Contructor
	 * @param incidentContactID
	 * @param firstName
	 * @param lastName
	 * @param street1
	 * @param street2
	 * @param city
	 * @param state
	 * @param zip
	 * @param zipExt
	 * @param phone1
	 * @param phone1Ext
	 * @param phone2
	 * @param phone2Ext
	 */
	public AbstractIncidentContact(Integer incidentContactID, String firstName,
			String lastName, String street1, String street2, String city,
			String state, String zip, String zipExt, String phone1,
			String phone1Ext, String phone2, String phone2Ext) {
		super();
		this.incidentContactID = incidentContactID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.street1 = street1;
		this.street2 = street2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.zipExt = zipExt;
		this.phone1 = phone1;
		this.phone1Ext = phone1Ext;
		this.phone2 = phone2;
		this.phone2Ext = phone2Ext;
	}

	/**
	 * Unique identifier of an incident contact
	 * @return
	 */
	@Id
	@TableGenerator(name="tg", table="lu_last_identifier",
	pkColumnName="identifier", valueColumnName="last_value",
	allocationSize=1
	)@GeneratedValue(strategy=GenerationType.TABLE, generator="tg") 
	@Column(name = "incident_contact_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getIncidentContactID() {
		return incidentContactID;
	}

	/**
	 * Unique identifier of an incident contact
	 * @param incidentContactID
	 */
	public void setIncidentContactID(Integer incidentContactID) {
		this.incidentContactID = incidentContactID;
	}

	@Column(name = "first_name", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "street1", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	
	@Column(name = "street2", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	@Column(name = "city", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "state", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "zip", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name = "zip_ext", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getZipExt() {
		return zipExt;
	}

	public void setZipExt(String zipExt) {
		this.zipExt = zipExt;
	}

	@Column(name = "phone1", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	@Column(name = "phone1_ext", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getPhone1Ext() {
		return phone1Ext;
	}

	public void setPhone1Ext(String phone1Ext) {
		this.phone1Ext = phone1Ext;
	}

	@Column(name = "phone2", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	
	@Column(name = "phone2_ext", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getPhone2Ext() {
		return phone2Ext;
	}

	public void setPhone2Ext(String phone2Ext) {
		this.phone2Ext = phone2Ext;
	}

	@Column(name = "email", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
