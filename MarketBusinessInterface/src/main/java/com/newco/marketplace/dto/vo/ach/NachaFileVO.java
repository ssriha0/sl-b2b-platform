package com.newco.marketplace.dto.vo.ach;

import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

public class NachaFileVO extends SerializableBaseVO{
	private FileHeaderRecordVO fileHeaderRecordVO;
	private FileControlRecordVO fileControlRecordVO;
	private List<BatchRecordVO> batchRecords;
	public FileHeaderRecordVO getFileHeaderRecordVO() {
		return fileHeaderRecordVO;
	}
	public void setFileHeaderRecordVO(FileHeaderRecordVO fileHeaderRecordVO) {
		this.fileHeaderRecordVO = fileHeaderRecordVO;
	}
	public FileControlRecordVO getFileControlRecordVO() {
		return fileControlRecordVO;
	}
	public void setFileControlRecordVO(FileControlRecordVO fileControlRecordVO) {
		this.fileControlRecordVO = fileControlRecordVO;
	}
	public List<BatchRecordVO> getBatchRecords() {
		return batchRecords;
	}
	public void setBatchRecords(List<BatchRecordVO> batchRecords) {
		this.batchRecords = batchRecords;
	}  

}
