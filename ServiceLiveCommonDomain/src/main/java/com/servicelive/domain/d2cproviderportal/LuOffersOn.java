package com.servicelive.domain.d2cproviderportal;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lu_offers_on")
public class LuOffersOn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
	private Integer id;
    @Column
	private String name;
   /* @OneToMany(mappedBy = "luOffersOn", cascade = CascadeType.ALL)
    public List<VendorServiceOfferedOn> vendorServiceOfferedOn;*/
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
