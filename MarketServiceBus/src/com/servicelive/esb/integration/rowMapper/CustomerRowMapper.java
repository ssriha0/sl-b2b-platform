package com.servicelive.esb.integration.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.servicelive.esb.integration.domain.Customer;


public class CustomerRowMapper implements RowMapper{
    public Customer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    	
    	Customer returnVal = new Customer();
    	returnVal.setCustKey(resultSet.getString("custKey"));
		returnVal.setNamePrefix(resultSet.getString("namePrefix"));
		returnVal.setFirstName(resultSet.getString("firstName"));
		returnVal.setSecondName(resultSet.getString("secondName"));
		returnVal.setLastName(resultSet.getString("lastName"));
		returnVal.setNameSuffix(resultSet.getString("nameSuffix"));
		returnVal.setAddressLine1(resultSet.getString("addressLine1"));
		returnVal.setAddressLine2(resultSet.getString("addressLine2"));
		returnVal.setAptNum(resultSet.getString("aptNum"));
		returnVal.setCity(resultSet.getString("city"));
		returnVal.setState(resultSet.getString("state"));
		returnVal.setZipCode(resultSet.getString("zipCode"));
		returnVal.setZipCodeExtension(resultSet.getString("zipCodeExtension"));
		returnVal.setCustPhoneNum(resultSet.getString("custPhoneNum"));
		returnVal.setPreferredPrimaryCntFl(resultSet.getString("preferredPrimaryCntFl"));
		returnVal.setEmailAddress(resultSet.getString("emailAddress"));
        return returnVal;
    }

	
}
