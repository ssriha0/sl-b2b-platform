package com.newco.marketplace.translator.business.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.newco.marketplace.translator.business.IZipService;
import com.newco.marketplace.translator.dao.ZipGeocode;
import com.newco.marketplace.translator.dao.ZipGeocodeDAO;
import com.newco.marketplace.translator.exception.InvalidZipCodeException;

public class ZipService implements IZipService {
	
	private ZipGeocodeDAO zipGeocodeDAO;

	public TimeZone getTimeZoneByZip(String zipCode) 
		throws InvalidZipCodeException
	{
		if (zipCode == null || zipCode.trim().length() < 5) 
			throw new InvalidZipCodeException( zipCode );
		ZipGeocode geo = getZipGeocodeDAO().findById(zipCode);
		if (geo == null) 
			throw new InvalidZipCodeException( zipCode );
		TimeZone timezone = TimeZone.getTimeZone(geo.getTimeZone());
		return timezone;
	}

	public Date getDateGMT(Date date, String zipCode) 
		throws ParseException, InvalidZipCodeException
	{
	    TimeZone localTimeZone = getTimeZoneByZip(zipCode);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		SimpleDateFormat out = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		out.setTimeZone(localTimeZone);
		date = out.parse(sdf.format(date));
	    return date;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.business.IZipService#getTimeZonesByState(java.lang.String)
	 */
	public List<TimeZone> getTimeZonesByState(String stateCd) {
		List<ZipGeocode> geocodes = getZipGeocodeDAO().findByStateCd(stateCd);
		List<TimeZone> timezones = new ArrayList<TimeZone>();
		for (ZipGeocode geocode : geocodes) {
			TimeZone timeZone = TimeZone.getTimeZone(geocode.getTimeZone());
			timezones.add(timeZone);
		}
		return timezones;
	}
	
	public ZipGeocodeDAO getZipGeocodeDAO() {
		return zipGeocodeDAO;
	}

	public void setZipGeocodeDAO(ZipGeocodeDAO zipGeocodeDAO) {
		this.zipGeocodeDAO = zipGeocodeDAO;
	}
}
