package com.servicelive.orderfulfillment.integration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * User: Mustafa Motiwala
 * Date: Apr 12, 2010
 * Time: 3:44:55 PM
 */
public class SONotesRowMapper implements RowMapper {
    private ServiceOrder serviceOrder;
    public SONotesRowMapper(ServiceOrder so){
        serviceOrder = so;
    }

    public SONote mapRow(ResultSet resultSet, int i) throws SQLException {
        SONote returnVal = new SONote();
        returnVal.setServiceOrder(serviceOrder);
        returnVal.setNote(resultSet.getString("text"));
        returnVal.setPrivate(false); //TODO: Verify what the scope for notes should be.
        return returnVal;
    }
}
