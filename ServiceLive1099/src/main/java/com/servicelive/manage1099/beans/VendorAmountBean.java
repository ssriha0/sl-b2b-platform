/**
 * 
 */
package com.servicelive.manage1099.beans;

import java.util.Date;

/**
 * @author mjoshi1
 *
 */
public class VendorAmountBean {
	
	private String vendor_id = "";
	private String amount = "";
	private String taxpayer_id_number_type = "";
	private String ein_no = "";
	private String dba_name = "";
	private String business_name = "";
	private Date last_payment_date = new Date();
	private String city = "";
	private String street1 = "";
	private String street2 = "";
	private String state = "";
	private String zip = "";
	private String zip4 = "";
	
	public String getVendor_id() {
		return vendor_id;
	}
	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTaxpayer_id_number_type() {
		return taxpayer_id_number_type;
	}
	public void setTaxpayer_id_number_type(String taxpayer_id_number_type) {
		this.taxpayer_id_number_type = taxpayer_id_number_type;
	}
	public String getEin_no() {
		return ein_no;
	}
	public void setEin_no(String ein_no) {
		this.ein_no = ein_no;
	}
	public String getDba_name() {
		return dba_name;
	}
	public void setDba_name(String dba_name) {
		this.dba_name = dba_name;
	}
	public String getBusiness_name() {
		return business_name;
	}
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}
	public Date getLast_payment_date() {
		return last_payment_date;
	}
	public void setLast_payment_date(Date last_payment_date) {
		this.last_payment_date = last_payment_date;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet1() {
		return street1;
	}
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getZip4() {
		return zip4;
	}
	public void setZip4(String zip4) {
		this.zip4 = zip4;
	}
	
	
	
	

}
