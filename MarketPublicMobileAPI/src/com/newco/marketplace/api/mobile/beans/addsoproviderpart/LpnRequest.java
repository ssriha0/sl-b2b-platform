package com.newco.marketplace.api.mobile.beans.addsoproviderpart;



import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ns2:LpnRequest")
public class LpnRequest {
	
	@XStreamAsAttribute
    final String xmlns = "http://base.hs.searshc.com/Request/";

    @XStreamAsAttribute 
    @XStreamAlias("xmlns:ns2")
    final String xlink="http://service.lpn.parts.hs.searshc.com/Request/FindSuppliers/";
	@XStreamAlias("ns2:latitude")
	private Float latitude;
	
	@XStreamAlias("ns2:longitude")
	private Float longitude;
	
	@XStreamAlias("ns2:maxDistance")
	private byte maxDistance;
	
	@XStreamImplicit(itemFieldName = "ns2:parts")
	private List<LpnPart> parts;

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public byte getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(byte maxDistance) {
		this.maxDistance = maxDistance;
	}

	public List<LpnPart> getParts() {
		return parts;
	}

	public void setParts(List<LpnPart> parts) {
		this.parts = parts;
	}
	
}
