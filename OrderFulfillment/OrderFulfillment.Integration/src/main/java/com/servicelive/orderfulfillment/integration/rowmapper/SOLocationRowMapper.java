package com.servicelive.orderfulfillment.integration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.LocationClassification;
import com.servicelive.orderfulfillment.domain.type.LocationType;

/**
 * User: Mustafa Motiwala
 * Date: Apr 12, 2010
 * Time: 5:44:33 PM
 */
public class SOLocationRowMapper implements RowMapper{
    private ServiceOrder serviceOrder;

    public SOLocationRowMapper(ServiceOrder so){
        serviceOrder = so;
    }

    public SOLocation mapRow(ResultSet resultSet, int i) throws SQLException {
        SOLocation returnVal = new SOLocation();
        returnVal.setServiceOrder(serviceOrder);
        returnVal.setLocationName(resultSet.getString("locationName"));
        returnVal.setLocationNote(resultSet.getString("locationNotes"));
        returnVal.setStreet1(resultSet.getString("addressLine1"));
        returnVal.setStreet2(resultSet.getString("addressLine2"));
        returnVal.setCity(resultSet.getString("city"));
        returnVal.setState(resultSet.getString("state"));
        returnVal.setZip(resultSet.getString("zipCode"));
        returnVal.setZip4(resultSet.getString("zip4"));
        String locationType = resultSet.getString("locationType");
        if(resultSet.wasNull() || StringUtils.isBlank(locationType)) returnVal.setSoLocationTypeId(LocationType.SERVICE);
        else returnVal.setSoLocationTypeId(LocationType.valueOf(locationType));
        
        String locationClass = resultSet.getString("locationClassification");
        if(!resultSet.wasNull() && !StringUtils.isBlank(locationClass)) { 
        	returnVal.setSoLocationClassId(LocationClassification.valueOf(locationClass));
        }
        return returnVal;
    }
}
