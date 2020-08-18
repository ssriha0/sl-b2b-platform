package com.newco.marketplace.test;

import java.util.HashMap;
import java.util.List;

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

public class GisTest {

	/**
	 * 
	 * @param args
	 * @throws Exception
	 * 
	 */

	public static void main(String[] args) throws Exception {
		ProviderLocationVO locationVO = new ProviderLocationVO();

		locationVO.setStreet("3333 Beverly Rd");
		locationVO.setCity("Hoffman Estates");
		locationVO.setState("IL");
		locationVO.setZip("60179");		
		
		HashMap<String, String> serviceLoc = getXYfromAddress(locationVO);

		
		/*
		 * RecordSet results = new RecordSet();
		 * 
		 * ObjectFactory objFactory = new ObjectFactory();
		 * 
		 * NewCoAddLocClient client = new NewCoAddLocClient();
		 * 
		 * GeocodeServerPort port = client.getGeocodeServerPort();
		 * 
		 * PropertySet Address = new PropertySet();
		 * 
		 * PropertySet PropMods = new PropertySet();
		 * 
		 * PropertySetProperty street = new PropertySetProperty();
		 * 
		 * PropertySetProperty city = new PropertySetProperty();
		 * 
		 * PropertySetProperty state = new PropertySetProperty();
		 * 
		 * PropertySetProperty zip = new PropertySetProperty();
		 * 
		 * ArrayOfPropertySetProperty propertyArray = new
		 * ArrayOfPropertySetProperty();
		 * 
		 * street.setKey("Street");
		 * 
		 * 
		 * 
		 * street.setValue(objFactory
		 * .createPropertySetPropertyValue(locationVO.getStreet()));
		 * 
		 * propertyArray.getPropertySetProperty().add(street);
		 * 
		 * 
		 * 
		 * city.setKey("city");
		 * 
		 * city.setValue(objFactory
		 * .createPropertySetPropertyValue(locationVO.getCity()));
		 * 
		 * 
		 * propertyArray.getPropertySetProperty().add(city);
		 * 
		 * 
		 * 
		 * state.setKey("State Abbreviation");
		 * 
		 * state.setValue(objFactory.createPropertySetPropertyValue(locationVO.getState()));
		 * 
		 * 
		 * 
		 * propertyArray.getPropertySetProperty().add(state);
		 * 
		 * zip.setKey("zip");
		 * 
		 * zip.setValue(objFactory.createPropertySetPropertyValue(locationVO.getZip()));
		 * 
		 * 
		 * propertyArray.getPropertySetProperty().add(zip);
		 * 
		 * Address.setPropertyArray(propertyArray);
		 * 
		 * List aovList;
		 *  // int i = 1;
		 * 
		 * try {
		 * 
		 * results = port.findAddressCandidates(Address, PropMods);
		 * 
		 * ArrayOfRecord records = results.getRecords();
		 * 
		 * List recordList = records.getRecord();
		 * 
		 * Record rcd = null;
		 * 
		 * Iterator<Record> itr = recordList.iterator();
		 * 
		 * PointN pointN;
		 * 
		 * int size;
		 * 
		 * ArrayOfValue aov;
		 * 
		 * while (itr.hasNext()) {
		 * 
		 * rcd = (Record) itr.next();
		 * 
		 * aov = (ArrayOfValue) rcd.getValues();
		 * 
		 * aovList = (List) aov.getValue();
		 * 
		 * size = aovList.size();
		 * 
		 * pointN = (PointN) aovList.get(1);
		 * 
		 * System.out.print("X-Cordinates :" + pointN.getX() + ", ");
		 * 
		 * System.out.print("Y-Coordinates :" + pointN.getY());
		 * 
		 * 
		 * for(int i = 0; i < size; i++){
		 * 
		 * 
		 * 
		 * pointN = (PointN)aovList.get(1);
		 * 
		 * System.out.print("X-Cordinates :"+pointN.getX()+", ");
		 * 
		 * System.out.print("Y-Coordinates :"+pointN.getY());
		 *  }
		 * 
		 * 
		 * System.out.println("\n");
		 *  }
		 *  } catch (Exception e) {
		 * 
		 * e.printStackTrace();
		 *  }
		 */

	}

	private static HashMap<String, String> getXYfromAddress(
			ProviderLocationVO locationVO) throws Exception {
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
			throw new InsuffcientLocationException("Location data is not suffcient");
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
		try {
			results = port.findAddressCandidates(Address, PropMods);
			if (results == null) {
				throw new LocationNotFoundException("Address not found");
			}
			if (results != null) {
				ArrayOfRecord records = results.getRecords();

				List<Record> recordList = records.getRecord();

				Record rcd = null;

				

				PointN pointN;

				

				ArrayOfValue aov;
				//List aovList;
				if(recordList.size() < 1){
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

				 System.out.print("X-Cordinates :"+xValue+", ");

                 System.out.print("Y-Coordinates :"+yValue);

			}
		}catch(LocationNotFoundException lnf){
			throw new LocationNotFoundException("Address not found", lnf);
		} catch (Exception e) {
			throw new GISException("Error calling Gis Webservice", e);

		}
		return xyMap;
	}

}
