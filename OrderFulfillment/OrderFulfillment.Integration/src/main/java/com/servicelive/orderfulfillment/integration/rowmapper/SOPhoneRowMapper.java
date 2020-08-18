package com.servicelive.orderfulfillment.integration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOPhone;
import com.servicelive.orderfulfillment.domain.type.PhoneClassification;
import com.servicelive.orderfulfillment.domain.type.PhoneType;

/**
 * User: Mustafa Motiwala
 * Date: Apr 12, 2010
 * Time: 5:36:01 PM
 */
public class SOPhoneRowMapper implements RowMapper {
    private SOContact contact;

    public SOPhoneRowMapper(SOContact c) {
        contact = c;
    }

    public SOPhone mapRow(ResultSet resultSet, int i) throws SQLException {
        SOPhone returnVal = new SOPhone();
        returnVal.setContact(contact);
        if (!StringUtils.isEmpty(resultSet.getString("phoneType"))) {
        returnVal.setPhoneClass(PhoneClassification.valueOf(resultSet.getString("phoneType")));
        }
        returnVal.setPhoneNo(resultSet.getString("phoneNumber"));
        returnVal.setPhoneExt(resultSet.getString("phoneExtension"));
        if(resultSet.getBoolean("primary")) returnVal.setPhoneType(PhoneType.PRIMARY);
        else returnVal.setPhoneType(PhoneType.ALTERNATE);
        return returnVal;
    }
}
