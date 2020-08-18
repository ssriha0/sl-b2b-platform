package com.servicelive.orderfulfillment.integration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * User: Mustafa Motiwala
 * Date: Apr 12, 2010
 * Time: 3:25:58 PM
 */
public class SOTaskRowMapper implements RowMapper {
    private ServiceOrder serviceOrder;

    public SOTaskRowMapper(ServiceOrder so){
        serviceOrder=so;
    }

    public SOTask mapRow(ResultSet resultSet, int i) throws SQLException {
        SOTask returnVal = new SOTask();
        returnVal.setServiceOrder(serviceOrder);
        returnVal.setTaskComments(resultSet.getString("comments"));
        returnVal.setTaskName(resultSet.getString("title"));
        returnVal.setPrice(resultSet.getBigDecimal("amount"));
        returnVal.setExternalSku(resultSet.getString("externalSku"));
        returnVal.setSpecialtyCode(resultSet.getString("specialtyCode"));
        returnVal.setPrimaryTask(resultSet.getBoolean("default"));
        if(resultSet.wasNull())returnVal.setPrimaryTask(false);
        returnVal.setCategory(resultSet.getString("category"));
        returnVal.setSubCategory(resultSet.getString("subCategory"));
        returnVal.setServiceType(resultSet.getString("serviceType"));
        returnVal.setSequenceNumber(resultSet.getInt("sequence_number"));
        return returnVal;
    }
}
