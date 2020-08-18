package com.servicelive.orderfulfillment.integration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.ContactLocationType;
import com.servicelive.orderfulfillment.domain.type.ContactType;
import com.servicelive.orderfulfillment.domain.type.EntityType;

/**
 * User: Mustafa Motiwala
 * Date: Apr 12, 2010
 * Time: 5:23:05 PM
 */
public class SOContactRowMapper implements RowMapper {
    private ServiceOrder serviceOrder;
    public SOContactRowMapper(ServiceOrder so) {
        serviceOrder = so;
    }

    public SOContact mapRow(ResultSet resultSet, int i) throws SQLException {
        SOContact returnVal = new SOContact();
        returnVal.setSoContactId(resultSet.getInt("contactId"));
        returnVal.setServiceOrder(serviceOrder);
        returnVal.setBusinessName(resultSet.getString("businessName"));
        returnVal.setFirstName(resultSet.getString("firstName"));
        returnVal.setMi(resultSet.getString("middleInitial"));
        returnVal.setLastName(resultSet.getString("lastName"));
        returnVal.setHonorific(resultSet.getString("honorific"));
        returnVal.setSuffix(resultSet.getString("suffix"));
        returnVal.setEmail(resultSet.getString("email"));
        returnVal.addContactLocation(ContactLocationType.SERVICE);
        //TODO: Verify if the contact & entity type are correct defaults!
        returnVal.setEntityType(EntityType.BUYER);
        returnVal.setContactTypeId(ContactType.PRIMARY);
        return returnVal;
    }
}
