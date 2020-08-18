package com.newco.marketplace.dto.vo.ach;

import com.sears.os.vo.SerializableBaseVO;

/**  
* FieldDetailVO.java - This class represents a field information of the records in the NACHA file. 
* It holds information like field name, start position of the field, end position of the field,
* field size, field value, order of the field and the field type. 
* 
* @author  Siva
* @version 1.0  
*/
public class FieldDetailVO extends SerializableBaseVO{

/**
	 * 
	 */
	private static final long serialVersionUID = -5738081035683816134L;
private int fieldId;
private String fieldName;
private int startPosition;
private int endPosition;
private String fieldDescription;
private int fieldSize;
private String fieldValue;
private int fieldOrder;
private String fieldType; 
private String fieldCondition;
private int batchGroupId=0;
private String fieldCategory;

public String getFieldCategory() {
	return fieldCategory;
}
public void setFieldCategory(String fieldCategory) {
	this.fieldCategory = fieldCategory;
}
public String getFieldCondition() {
	return fieldCondition;
}
public void setFieldCondition(String fieldCondition) {
	this.fieldCondition = fieldCondition;
}
public String getFieldType() {
	return fieldType;
}
public void setFieldType(String fieldType) {
	this.fieldType = fieldType;
}
public int getFieldId() {
	return fieldId;
}
public void setFieldId(int fieldId) {
	this.fieldId = fieldId;
}
public String getFieldName() {
	return fieldName;
}
public void setFieldName(String fieldName) {
	this.fieldName = fieldName;
}
public int getStartPosition() {
	return startPosition;
}
public void setStartPosition(int startPosition) {
	this.startPosition = startPosition;
}
public int getEndPosition() {
	return endPosition;
}
public void setEndPosition(int endPosition) {
	this.endPosition = endPosition;
}

public int getFieldSize() {
	return fieldSize;
}
public void setFieldSize(int fieldSize) {
	this.fieldSize = fieldSize;
}
public String getFieldValue() {
	return fieldValue;
}
public void setFieldValue(String fieldValue) {
	this.fieldValue = fieldValue;
}
public int getFieldOrder() {
	return fieldOrder;
}
public void setFieldOrder(int fieldOrder) {
	this.fieldOrder = fieldOrder;
}
/*public FieldDetail clone(){
	FieldDetail fd = new FieldDetail();
	fd.setEndPosition(this.endPosition);
	fd.setField_description(this.field_description);
	fd.setFieldId(this.fieldId);
	fd.setFieldName(this.fieldName);
	fd.setFieldOrder(this.fieldOrder);
	fd.setFieldSize(this.fieldSize);
	fd.setFieldType(this.fieldType);
	fd.setFieldValue(this.fieldValue);
	
	return fd;
	
}*/

@Override
public String toString(){
	StringBuffer sb = new StringBuffer();
	sb.append("[ ");
	sb.append("fieldId: "+fieldId);
	sb.append(", fieldName: "+fieldName);
	sb.append(", startPosition: "+startPosition);
	sb.append(", endPosition: "+endPosition);
	sb.append(", fieldSize: "+fieldSize);
	sb.append(", fieldValue: "+fieldValue);
	sb.append(", fieldType: "+fieldType);
	sb.append(", fieldOrder: "+fieldOrder);
	sb.append(", fieldDescription: "+fieldDescription);
	sb.append(" ]");
	return sb.toString();
}
public void setFieldDescription(String fieldDescription) {
	this.fieldDescription = fieldDescription;
}
public String getFieldDescription() {
	return fieldDescription;
}
public int getBatch_group_Id() {
	return batchGroupId;
}
public void setBatch_group_Id(int batch_group_Id) {
	this.batchGroupId = batch_group_Id;
}
public int getBatchGroupId() {
	return batchGroupId;
}
public void setBatchGroupId(int batchGroupId) {
	this.batchGroupId = batchGroupId;
}
}
