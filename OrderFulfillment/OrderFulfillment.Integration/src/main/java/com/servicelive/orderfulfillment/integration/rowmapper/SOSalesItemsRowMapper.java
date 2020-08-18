package com.servicelive.orderfulfillment.integration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.servicelive.orderfulfillment.domain.SOSalesCheckItems;

public class SOSalesItemsRowMapper implements RowMapper {
    
    public SOSalesCheckItems mapRow(ResultSet resultSet, int i) throws SQLException {
    	
    	SOSalesCheckItems returnVal = new SOSalesCheckItems();
        
        returnVal.setItemId(resultSet.getString("itemId"));
        returnVal.setServiceOrderId(resultSet.getString("serviceOrderId"));
        returnVal.setLineNumber(resultSet.getString("lineNumber"));
        returnVal.setDivision(resultSet.getString("division"));	
        returnVal.setItemNumber(resultSet.getString("itemNumber"));	
        returnVal.setSku(resultSet.getString("sku"));	
        returnVal.setPurchaseAmt(resultSet.getString("purchaseAmt"));	
        returnVal.setDescription(resultSet.getString("description"));	
        returnVal.setQuantity(resultSet.getString("quantity"));
        returnVal.setGiftFlag(resultSet.getString("giftFlag"));	
        returnVal.setGiftDate(resultSet.getString("giftDate"));
        
        return returnVal;
    }
}
