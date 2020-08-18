package com.newco.marketplace.api.beans.search.providerProfile;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XSD(name = "addReviewRequest.xsd", path = "/resources/schemas/search/")
@XStreamAlias("addReviewRequest")
public class AddReviewRequest {
	@XStreamAlias("soId")
	@XStreamAsAttribute()
	String soId;
	
	@XStreamAlias("cleanliness")
	@XStreamAsAttribute()
	int cleanliness;

	@XStreamAlias("timeliness")
	@XStreamAsAttribute()
	int timeliness;
	
	@XStreamAlias("communication")
	@XStreamAsAttribute()
	int communication;
	
	@XStreamAlias("professionalism")
	@XStreamAsAttribute()
	int professionalism;
	
	@XStreamAlias("quality")
	@XStreamAsAttribute()
	int quality;
	
	@XStreamAlias("value")
	@XStreamAsAttribute()
	int value;

	@XStreamAlias("comment")
	@XStreamAsAttribute()
	String comment;
	
	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public int getcleanliness() {
		return cleanliness;
	}

	public void setCleanliness(int cleanliness) {
		this.cleanliness = cleanliness;
	}

	public int getTimeliness() {
		return timeliness;
	}

	public void setTimeliness(int timeliness) {
		this.timeliness = timeliness;
	}
	
	public int getCommunication() {
		return communication;
	}

	public void setCommunication(int communication) {
		this.communication = communication;
	}
	
	public int getProfessionalism() {
		return professionalism;
	}

	public void setProfessionalism(int professionalism) {
		this.professionalism = professionalism;
	}
	
	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}	
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}	
}
