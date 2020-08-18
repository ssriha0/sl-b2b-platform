package com.newco.marketplace.dto.vo.ptd;

import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

public class PTDFileVO extends SerializableBaseVO{
private static final long serialVersionUID = 1L;
	
private PTDFileHeaderRecordVO ptdFileHeaderRecordVO;
private PTDFileTrailerRecordVO ptdFileTrailerRecordVO;
private List<PTDTransactionRecordVO> ptdTransactionList ;
public PTDFileHeaderRecordVO getPtdFileHeaderRecordVO() {
	return ptdFileHeaderRecordVO;
}
public void setPtdFileHeaderRecordVO(PTDFileHeaderRecordVO ptdFileHeaderRecordVO) {
	this.ptdFileHeaderRecordVO = ptdFileHeaderRecordVO;
}
public PTDFileTrailerRecordVO getPtdFileTrailerRecordVO() {
	return ptdFileTrailerRecordVO;
}
public void setPtdFileTrailerRecordVO(
		PTDFileTrailerRecordVO ptdFileTrailerRecordVO) {
	this.ptdFileTrailerRecordVO = ptdFileTrailerRecordVO;
}
public List<PTDTransactionRecordVO> getPtdTransactionList() {
	return ptdTransactionList;
}
public void setPtdTransactionList(
		List<PTDTransactionRecordVO> ptdTransactionList) {
	this.ptdTransactionList = ptdTransactionList;
}

}
