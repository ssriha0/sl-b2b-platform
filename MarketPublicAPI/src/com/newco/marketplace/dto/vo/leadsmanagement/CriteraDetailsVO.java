package com.newco.marketplace.dto.vo.leadsmanagement;

public class CriteraDetailsVO {
private Integer firmId;
private Double rating;
private Double distance;
private Integer completesPerWeek;
private int rank;
private Double overallRating;
private String firmName;
public int getRank() {
	return rank;
}
public void setRank(int rank) {
	this.rank = rank;
}
public Integer getFirmId() {
	return firmId;
}
public void setFirmId(Integer firmId) {
	this.firmId = firmId;
}
public Double getRating() {
	return rating;
}
public void setRating(Double rating) {
	this.rating = rating;
}
public Double getDistance() {
	return distance;
}
public void setDistance(Double distance) {
	this.distance = distance;
}
public Integer getCompletesPerWeek() {
	return completesPerWeek;
}
public void setCompletesPerWeek(Integer completesPerWeek) {
	this.completesPerWeek = completesPerWeek;
}
public Double getOverallRating() {
	return overallRating;
}
public void setOverallRating(Double overallRating) {
	this.overallRating = overallRating;
}
public String getFirmName() {
	return firmName;
}
public void setFirmName(String firmName) {
	this.firmName = firmName;
}
}
