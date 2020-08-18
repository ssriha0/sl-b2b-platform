package com.servicelive.orderfulfillment.integration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import com.servicelive.orderfulfillment.domain.SOPart;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.CarrierType;

/**
 * User: Mustafa Motiwala
 * Date: Apr 13, 2010
 * Time: 12:03:51 PM
 */
public class SOPartsRowMapper implements RowMapper {
    private ServiceOrder serviceOrder;

    public SOPartsRowMapper(ServiceOrder so) {
        serviceOrder = so;
    }

    public SOPart mapRow(ResultSet resultSet, int i) throws SQLException {
        SOPart returnVal = new SOPart();
        returnVal.setServiceOrder(serviceOrder);
        returnVal.setShipTrackNo(resultSet.getString("inboundTrackingNumber"));
        String inboundCarrier = resultSet.getString("inboundCarrier");
        if(StringUtils.isNotBlank(inboundCarrier))
        	returnVal.setShipCarrierId(CarrierType.valueOf(inboundCarrier));
        returnVal.setReturnTrackNo(resultSet.getString("outboundTrackingNumber"));
        String outboundCarrier = resultSet.getString("outboundCarrier");
        if(StringUtils.isNotBlank(outboundCarrier))
        	returnVal.setReturnCarrierId(CarrierType.valueOf(outboundCarrier));
        returnVal.setShipDate(resultSet.getDate("shippingDate"));
        returnVal.setManufacturer(resultSet.getString("manufacturer"));
        returnVal.setModelNumber(resultSet.getString("modelNumber"));
        returnVal.setPartDs(resultSet.getString("description"));
        returnVal.setReferencePartId(resultSet.getString("vendorPartNumber"));
        
        // Needed by Assurant
        returnVal.setSerialNumber(resultSet.getString("serialNumber"));
        returnVal.setManufacturerPartNumber(resultSet.getString("partNumber"));
        returnVal.setVendorPartNumber(resultSet.getString("vendorPartNumber"));
        returnVal.setAdditionalPartInfo(resultSet.getString("classComments"));
        returnVal.setAlternatePartReference1(resultSet.getString("classCode"));
        
        return returnVal;
    }
}
