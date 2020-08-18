package com.newco.marketplace.search.solr.dto;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class ZipDto implements Serializable,  Comparable<ZipDto>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Field 
	String zip;
	
	@Field 
	double lng;
	
	@Field 
	double lat;
	
	@Field("geo_distance")
	private String distance;
	
	//private float geoDistance;
	
	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getDistance() {
		if (distance.length() > 6) {
		  return distance.substring(0, 6);
		}
	  return distance;	 
	}

	public float getGeoDistance() {
		return Float.parseFloat(getDistance());
	}
	
	public void setDistance(String distance) {
		this.distance = distance.substring(0, 2);
	}

	public int compareTo(ZipDto arg0) {
		float myDis = this.getGeoDistance();
		float hisDis = arg0.getGeoDistance();
		if (myDis > hisDis)
			return 1;
		else if (myDis < hisDis)
			return -1;
		else
			return 0;
	}	
	
}
