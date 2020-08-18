package com.newco.marketplace.dto.vo.ptd;

import com.sears.os.vo.SerializableBaseVO;

public class PTDRecordTypeVO extends SerializableBaseVO{

	private int ptdDetailId;
	private String ptdShortAlias;
	private String fieldDataType;
	private int fieldStartposition;
	private int fieldEndPosition;
	private int fieldLength;
	private String fieldValue;
	private String fieldName;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public int getPtdDetailId() {
		return ptdDetailId;
	}
	public void setPtdDetailId(int ptdDetailId) {
		this.ptdDetailId = ptdDetailId;
	}
	public String getPtdShortAlias() {
		return ptdShortAlias;
	}
	public void setPtdShortAlias(String ptdShortAlias) {
		this.ptdShortAlias = ptdShortAlias;
	}
	public String getFieldDataType() {
		return fieldDataType;
	}
	public void setFieldDataType(String fieldDataType) {
		this.fieldDataType = fieldDataType;
	}
	public int getFieldStartposition() {
		return fieldStartposition;
	}
	public void setFieldStartposition(int fieldStartposition) {
		this.fieldStartposition = fieldStartposition;
	}
	public int getFieldEndPosition() {
		return fieldEndPosition;
	}
	public void setFieldEndPosition(int fieldEndPosition) {
		this.fieldEndPosition = fieldEndPosition;
	}
	public int getFieldLength() {
		return fieldLength;
	}
	public void setFieldLength(int fieldLength) {
		this.fieldLength = fieldLength;
	}
	
	
}
