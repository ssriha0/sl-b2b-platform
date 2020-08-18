package com.newco.marketplace.dto.vo.ptd;

import java.util.HashMap;
import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

public class PTDRecordVO extends SerializableBaseVO{

	private static final long serialVersionUID = 1L;
	String recordIdentifier;
	private List<PTDRecordTypeVO> ptdRecordTypeList;

public String getRecordIdentifier() {
	return recordIdentifier;
}

public void setRecordIdentifier(String recordIdentifier) {
	this.recordIdentifier = recordIdentifier;
}

public List<PTDRecordTypeVO> getPtdRecordTypeList() {
	return ptdRecordTypeList;
}

public void setPtdRecordTypeList(List<PTDRecordTypeVO> ptdRecordTypeList) {
	this.ptdRecordTypeList = ptdRecordTypeList;
}
public HashMap<String, PTDRecordTypeVO> getHash(List<PTDRecordTypeVO> fieldDetails)
{
	HashMap<String, PTDRecordTypeVO> hashMap = new HashMap<String, PTDRecordTypeVO>(); 
	for(int r=0; r<fieldDetails.size();r++){
		PTDRecordTypeVO fd = fieldDetails.get(r);
		hashMap.put(fd.getFieldName(), fd);
	}
	return hashMap;
}
}
