package com.servicelive.wallet.batch.ach.vo;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * FieldDetailVO.java - This class represents a field information of the records in the NACHA file.
 * It holds information like field name, start position of the field, end position of the field,
 * field size, field value, order of the field and the field type.
 * 
 * @author Siva
 * @version 1.0
 */
public class FieldDetailVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5738081035683816134L;

	/** The batch group id. */
	private int batchGroupId = 0;

	/** The end position. */
	private int endPosition;

	/** The field category. */
	private String fieldCategory;

	/** The field condition. */
	private String fieldCondition;

	/** The field description. */
	private String fieldDescription;

	/** The field id. */
	private int fieldId;

	/** The field name. */
	private String fieldName;

	/** The field order. */
	private int fieldOrder;

	/** The field size. */
	private int fieldSize;

	/** The field type. */
	private String fieldType;

	/** The field value. */
	private String fieldValue;

	/** The start position. */
	private int startPosition;

	/**
	 * Gets the batch group id.
	 * 
	 * @return the batch group id
	 */
	public int getBatchGroupId() {

		return batchGroupId;
	}

	/**
	 * Gets the end position.
	 * 
	 * @return the end position
	 */
	public int getEndPosition() {

		return endPosition;
	}

	/**
	 * Gets the field category.
	 * 
	 * @return the field category
	 */
	public String getFieldCategory() {

		return fieldCategory;
	}

	/**
	 * Gets the field condition.
	 * 
	 * @return the field condition
	 */
	public String getFieldCondition() {

		return fieldCondition;
	}

	/**
	 * Gets the field description.
	 * 
	 * @return the field description
	 */
	public String getFieldDescription() {

		return fieldDescription;
	}

	/**
	 * Gets the field id.
	 * 
	 * @return the field id
	 */
	public int getFieldId() {

		return fieldId;
	}

	/**
	 * Gets the field name.
	 * 
	 * @return the field name
	 */
	public String getFieldName() {

		return fieldName;
	}

	/**
	 * Gets the field order.
	 * 
	 * @return the field order
	 */
	public int getFieldOrder() {

		return fieldOrder;
	}

	/**
	 * Gets the field size.
	 * 
	 * @return the field size
	 */
	public int getFieldSize() {

		return fieldSize;
	}

	/**
	 * Gets the field type.
	 * 
	 * @return the field type
	 */
	public String getFieldType() {

		return fieldType;
	}

	/**
	 * Gets the field value.
	 * 
	 * @return the field value
	 */
	public String getFieldValue() {

		return fieldValue;
	}

	/**
	 * Gets the start position.
	 * 
	 * @return the start position
	 */
	public int getStartPosition() {

		return startPosition;
	}

	/**
	 * Sets the batch group id.
	 * 
	 * @param batchGroupId the new batch group id
	 */
	public void setBatchGroupId(int batchGroupId) {

		this.batchGroupId = batchGroupId;
	}

	/**
	 * Sets the end position.
	 * 
	 * @param endPosition the new end position
	 */
	public void setEndPosition(int endPosition) {

		this.endPosition = endPosition;
	}

	/**
	 * Sets the field category.
	 * 
	 * @param fieldCategory the new field category
	 */
	public void setFieldCategory(String fieldCategory) {

		this.fieldCategory = fieldCategory;
	}

	/**
	 * Sets the field condition.
	 * 
	 * @param fieldCondition the new field condition
	 */
	public void setFieldCondition(String fieldCondition) {

		this.fieldCondition = fieldCondition;
	}

	/**
	 * Sets the field description.
	 * 
	 * @param fieldDescription the new field description
	 */
	public void setFieldDescription(String fieldDescription) {

		this.fieldDescription = fieldDescription;
	}

	/**
	 * Sets the field id.
	 * 
	 * @param fieldId the new field id
	 */
	public void setFieldId(int fieldId) {

		this.fieldId = fieldId;
	}

	/**
	 * Sets the field name.
	 * 
	 * @param fieldName the new field name
	 */
	public void setFieldName(String fieldName) {

		this.fieldName = fieldName;
	}

	/**
	 * Sets the field order.
	 * 
	 * @param fieldOrder the new field order
	 */
	public void setFieldOrder(int fieldOrder) {

		this.fieldOrder = fieldOrder;
	}

	/**
	 * Sets the field size.
	 * 
	 * @param fieldSize the new field size
	 */
	public void setFieldSize(int fieldSize) {

		this.fieldSize = fieldSize;
	}

	/**
	 * Sets the field type.
	 * 
	 * @param fieldType the new field type
	 */
	public void setFieldType(String fieldType) {

		this.fieldType = fieldType;
	}

	/**
	 * Sets the field value.
	 * 
	 * @param fieldValue the new field value
	 */
	public void setFieldValue(String fieldValue) {

		this.fieldValue = fieldValue;
	}

	/**
	 * Sets the start position.
	 * 
	 * @param startPosition the new start position
	 */
	public void setStartPosition(int startPosition) {

		this.startPosition = startPosition;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		StringBuffer sb = new StringBuffer();
		sb.append("[ ");
		sb.append("fieldId: " + fieldId);
		sb.append(", fieldName: " + fieldName);
		sb.append(", startPosition: " + startPosition);
		sb.append(", endPosition: " + endPosition);
		sb.append(", fieldSize: " + fieldSize);
		sb.append(", fieldValue: " + fieldValue);
		sb.append(", fieldType: " + fieldType);
		sb.append(", fieldOrder: " + fieldOrder);
		sb.append(", fieldDescription: " + fieldDescription);
		sb.append(" ]");
		return sb.toString();
	}
}
