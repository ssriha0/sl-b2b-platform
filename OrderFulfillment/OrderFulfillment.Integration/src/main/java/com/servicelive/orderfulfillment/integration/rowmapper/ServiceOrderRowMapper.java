package com.servicelive.orderfulfillment.integration.rowmapper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;

import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.PartSupplierType;
import com.servicelive.orderfulfillment.domain.type.PriceModelType;
import com.servicelive.orderfulfillment.domain.type.SOScheduleType;

/**
 * User: Mustafa Motiwala
 * Date: Apr 9, 2010
 * Time: 9:45:17 AM
 */
public class ServiceOrderRowMapper implements RowMapper{
    private static final Log log = LogFactory.getLog(ServiceOrderRowMapper.class);
    
    public ServiceOrder mapRow(ResultSet resultSet, int row) throws SQLException {
        ServiceOrder returnVal = new ServiceOrder();
        returnVal.setOrderPrepRequired(true);
        returnVal.setSoId(resultSet.getString("serviceOrderId"));
        returnVal.setSowTitle(resultSet.getString("title"));
        returnVal.setSowDs(resultSet.getString("description"));
        returnVal.setProviderInstructions(resultSet.getString("providerInstructions"));
        returnVal.setBuyerId(resultSet.getLong("platformBuyerId"));
        returnVal.setPriceModel(PriceModelType.NAME_PRICE);
        SOSchedule schedule = new SOSchedule();
        returnVal.setSchedule(schedule);
        
        String serviceDate =  resultSet.getString("serviceWindowStartDate");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");	
        
        if(serviceDate != null) {
        	try {
        		schedule.setServiceDate1(df.parse(serviceDate));
        	} catch(ParseException e) {
        		log.error("Exception when trying to parse service start date in ServiceOrderRowMapper" + serviceDate);
        	}
        }
        
        serviceDate =  resultSet.getString("serviceWindowEndDate");
        
        if(serviceDate != null) {
        	try {
        		schedule.setServiceDate2(df.parse(serviceDate));
        	} catch(ParseException e) {
        		log.error("Exception when trying to parse service end date in ServiceOrderRowMapper" + serviceDate);
        	}
        }
        
        schedule.setServiceTimeStart(resultSet.getString("serviceWindowStartTime"));
        schedule.setServiceTimeEnd(resultSet.getString("serviceWindowEndTime"));
        if(resultSet.getBoolean("serviceWindowTypeFixed"))
        	schedule.setServiceDateTypeId(SOScheduleType.SINGLEDAY);
        else
        	schedule.setServiceDateTypeId(SOScheduleType.DATERANGE);

        Blob blob = resultSet.getBlob("customRefs");
        if(null != blob){//assumption that if the service order needs the custom ref it will populated by who ever populates the database
            try{
                @SuppressWarnings("unchecked")//CustomRefs can be a TreeMap or HashMap
                Map<String, String> customRefs = null;
                if(new ObjectInputStream(blob.getBinaryStream()).readObject() instanceof TreeMap<?,?>) {
                	customRefs =(TreeMap)new ObjectInputStream(blob.getBinaryStream()).readObject();
                } else {
                	customRefs =(HashMap)new ObjectInputStream(blob.getBinaryStream()).readObject();
                }
                List<SOCustomReference> targetCustomRefs = new ArrayList<SOCustomReference>();
                for(Map.Entry<String,String> entry:customRefs.entrySet()){
                    if(null == entry.getValue()) continue;
                    SOCustomReference customReference = new SOCustomReference();
                    customReference.setBuyerRefTypeName(entry.getKey());
                    customReference.setBuyerRefValue(entry.getValue());
                    customReference.setServiceOrder(returnVal);
                    targetCustomRefs.add(customReference);
                }
                returnVal.setCustomReferences(targetCustomRefs);
            }catch(IOException e){
                log.info("Exception when trying to read Custom References for serviceOrder:" + returnVal.getSoId());
                log.info("Ignoring custom references."); //We should be able to safely ignore this exception.
            }catch(ClassCastException e){
                log.info("ClassCastException when trying to read Custom References for serviceOrder:" + returnVal.getSoId());
                log.info("Ignoring custom references."); //We should be able to safely ignore this exception.
        }
            catch(ClassNotFoundException e){} //CNFE should never be getting raised here.
        }
        //other fields
        returnVal.setExternalStatus(resultSet.getString("externalStatus"));
        returnVal.setTemplateId(resultSet.getInt("templateId"));
        if(resultSet.wasNull()) returnVal.setTemplateId(null);
        returnVal.setProviderServiceConfirmInd(resultSet.getInt("providerServiceConfirmInd"));
        String partSupplier = resultSet.getString("partsSuppliedBy");
        if(StringUtils.isNotBlank(partSupplier))
        	returnVal.setPartsSupplier(PartSupplierType.valueOf(partSupplier));
        returnVal.setBuyerResourceId(resultSet.getLong("buyerResourceId"));
        returnVal.setPrimarySkillCategory(resultSet.getString("mainServiceCategory"));
        returnVal.setBuyerTermsCond(resultSet.getString("buyerTermsAndConditions"));
        returnVal.setSpendLimitLabor(resultSet.getBigDecimal("laborSpendLimit"));
        returnVal.setSpendLimitParts(resultSet.getBigDecimal("partsSpendLimit"));
        return returnVal;
    }
}
