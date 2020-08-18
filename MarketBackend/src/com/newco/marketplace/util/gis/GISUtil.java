/**
 * 
 */
package com.newco.marketplace.util.gis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esri.schemas.arcgis._9.ArrayOfPropertySetProperty;
import com.esri.schemas.arcgis._9.ArrayOfRecord;
import com.esri.schemas.arcgis._9.ArrayOfValue;
import com.esri.schemas.arcgis._9.ObjectFactory;
import com.esri.schemas.arcgis._9.PointN;
import com.esri.schemas.arcgis._9.PropertySet;
import com.esri.schemas.arcgis._9.PropertySetProperty;
import com.esri.schemas.arcgis._9.Record;
import com.esri.schemas.arcgis._9.RecordSet;
import com.gis.webservice.GeocodeServerPort;
import com.gis.webservice.NewCoAddLocClient;
import com.newco.marketplace.dto.vo.provider.ProviderLocationVO;
import com.newco.marketplace.exception.gis.GISException;
import com.newco.marketplace.exception.gis.InsuffcientLocationException;
import com.newco.marketplace.exception.gis.LocationNotFoundException;

/**
 * @author ajain04
 */
public class GISUtil {

    /**
     * Returns the lat/long coordinates for a given address
     * Location object MUST contain Street, City, State, and
     * Zipcode. The lat/long values are returned in a
     * hashMap with 'x' and 'y' as the keys and the values
     * as lat/long values respective in String format
     * 
     * @throws InsuffcientLocationException
     *                 if Location object has missing
     *                 address info
     * @throws LocationNotFoundException
     *                 if location can't be determined by
     *                 GIS Server
     * @throws GISException
     *                 for any GIS server error
     */
	private static final Double R = (3949.901 + 3963.189 )/2; // earth is a sphere so split the r's for Radious of earth.
	
    public static HashMap<String, String> getLatLongfromAddress(
			ProviderLocationVO locationVO) throws InsuffcientLocationException,
			LocationNotFoundException, GISException {

		RecordSet results = new RecordSet();
		ObjectFactory objFactory = new ObjectFactory();

		NewCoAddLocClient client = new NewCoAddLocClient();
		GeocodeServerPort port = client.getGeocodeServerPort();
		PropertySet Address = new PropertySet();
		PropertySet PropMods = new PropertySet();
		PropertySetProperty street = new PropertySetProperty();
		PropertySetProperty city = new PropertySetProperty();
		PropertySetProperty state = new PropertySetProperty();
		PropertySetProperty zip = new PropertySetProperty();
		ArrayOfPropertySetProperty propertyArray = new ArrayOfPropertySetProperty();

		if (locationVO.getStreet() == null || locationVO.getState() == null
				|| locationVO.getCity() == null || locationVO.getZip() == null) {
			throw new InsuffcientLocationException(
					"Location data is not suffcient");
		}

		street.setKey("Street");
		street.setValue(objFactory.createPropertySetPropertyValue(locationVO
				.getStreet()));
		propertyArray.getPropertySetProperty().add(street);

		city.setKey("city");
		city.setValue(objFactory.createPropertySetPropertyValue(locationVO
				.getCity()));
		propertyArray.getPropertySetProperty().add(city);

		state.setKey("State Abbreviation");
		state.setValue(objFactory.createPropertySetPropertyValue(locationVO
				.getState()));
		propertyArray.getPropertySetProperty().add(state);

		zip.setKey("zip");
		zip.setValue(objFactory.createPropertySetPropertyValue(locationVO
				.getZip()));
		propertyArray.getPropertySetProperty().add(zip);

		Address.setPropertyArray(propertyArray);

		HashMap<String, String> xyMap = new HashMap<String, String>();
		Record rcd;
		PointN pointN;
		ArrayOfValue aov;
		try {
			results = port.findAddressCandidates(Address, PropMods);
			if (results == null) {
				throw new LocationNotFoundException("Address not found");
			}
			if (results != null) {
				ArrayOfRecord records = results.getRecords();
				List<Record> recordList = records.getRecord();
				if (recordList.size() < 1) {
					throw new LocationNotFoundException("Address not found");
				}
				rcd = recordList.get(0);
				aov = (ArrayOfValue) rcd.getValues();
				List<Object> aovList = (List<Object>) aov.getValue();
				pointN = (PointN) aovList.get(1);
				String xValue = String.valueOf(pointN.getX());
				String yValue = String.valueOf(pointN.getY());
				xyMap.put("long", xValue);
				xyMap.put("lat", yValue);
			}

		} catch (LocationNotFoundException lnf) {
			throw new LocationNotFoundException("Address not found", lnf);
		} catch (Exception e) {
			throw new GISException("Error calling Gis Webservice", e);
		}

		return xyMap;

	}

    public static double getDistanceInMiles(Map<String, String> location1,
            Map<String, String> location2) throws InsuffcientLocationException {
        double rvDistance = 0;
        Double location1long = null;
        Double location1lat = null;
        Double location2long = null;
        Double location2lat = null;

        

        try {
            location1long = Double.parseDouble(location1.get("long"));
            location1lat = Double.parseDouble(location1.get("lat"));

            location2long = Double.parseDouble(location2.get("long"));
            location2lat = Double.parseDouble(location2.get("lat"));
        } catch (NumberFormatException e) {
            throw new InsuffcientLocationException(
                    "Invalid coordinates passed to getDistanceInMiles(location1, location2) method.");
        }

        //   using Haversine formula
        
        // Double R = (3949.901 + 3963.189 )/2; // earth is a sphere so split the r's for Radious of earth.
        
        Double  dLat = Math.toRadians(location2lat-location1lat);  //  need the delta in radians - not degrees
        Double  dLon = Math.toRadians(location2long-location1long); //  need the delta in radians - not degrees

        Double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(location1lat)) * Math.cos(Math.toRadians(location2lat)) * 
                Math.sin(dLon/2) * Math.sin(dLon/2); 
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
        
        // distance = Radians ( which factor in Pi)
        rvDistance = R * c;
        
     

        return (rvDistance);
    }

}
