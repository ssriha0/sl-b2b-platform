package com.newco.marketplace.dto.vo.ledger;
/**
 * @author swamy patsa.
 */
import java.sql.Timestamp;
import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

public class GlProcessLogVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8554798475137419492L;
	private Date fromDate;
	private Date toDate;
	private Timestamp processDate;
	private String fileName;
	private int processSuccesfull;
	private int initiatedManually;
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public Timestamp getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Timestamp processDate) {
		this.processDate = processDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getProcessSuccesfull() {
		return processSuccesfull;
	}
	public void setProcessSuccesfull(int processSuccesfull) {
		this.processSuccesfull = processSuccesfull;
	}
	public int getInitiatedManually() {
		return initiatedManually;
	}
	public void setInitiatedManually(int initiatedManually) {
		this.initiatedManually = initiatedManually;
	}
	
	
}
