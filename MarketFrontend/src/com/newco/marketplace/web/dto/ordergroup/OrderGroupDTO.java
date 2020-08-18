package com.newco.marketplace.web.dto.ordergroup;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.web.dto.SerializedBaseDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;

// Grouped Orders Panel
public class OrderGroupDTO extends SerializedBaseDTO
{
	private String id;
	private String status;
	private String title;
	private String createdDateString;
	private String endCustomer;
	private String date;
	private String time;
	private String city;
	private String state;
	private String zip;
	
	private List<ServiceOrderDTO> orders= new ArrayList<ServiceOrderDTO>();
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEndCustomer() {
		return endCustomer;
	}
	public void setEndCustomer(String endCustomer) {
		this.endCustomer = endCustomer;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<ServiceOrderDTO> getOrders()
	{
		return orders;
	}
	public void setOrders(List<ServiceOrderDTO> orders)
	{
		this.orders = orders;
	}
	public String getCreatedDateString() {
		return createdDateString;
	}
	public void setCreatedDateString(String createdDateString) {
		this.createdDateString = createdDateString;
	}
	
}
