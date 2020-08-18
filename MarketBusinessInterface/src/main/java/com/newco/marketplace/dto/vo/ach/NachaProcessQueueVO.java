package com.newco.marketplace.dto.vo.ach;

import java.util.Date;

import com.newco.marketplace.vo.MPBaseVO;

public class NachaProcessQueueVO extends MPBaseVO {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6608011980160871443L;
	private String accTypeDescr;
    private String entityTypeDescr;
    private String bankName;
    private int countryId;
    private int accountTypeId;
    private int accountStatusId;
    private String routingNo;
    private String accountNo;
    private String transTypeDescr;
    private int achProcessId;
    private int entityTypeId;
    private int entityId;
    private int accountId;
    private long ledgerEntryId;
    private long ledgerTransactionEntryId;
    private int transactionTypeId;
    private double transAmount;
    private Date entryDate;
    private int processStatusId;
    private int rejectReasonId;
    private long discretionaryData; 
    private double ledgerTransAmount;
    
    public int getRejectReasonId() {
		return rejectReasonId;
	}

	public void setRejectReasonId(int rejectReasonId) {
		this.rejectReasonId = rejectReasonId;
	}

	public int getAccountId() {

        return accountId;
    }

    public String getAccountNo() {

        return accountNo;
    }

    public int getAccountStatusId() {

        return accountStatusId;
    }

    public int getAccountTypeId() {

        return accountTypeId;
    }

    public String getAccTypeDescr() {

        return accTypeDescr;
    }

    public int getAchProcessId() {

        return achProcessId;
    }

    public String getBankName() {

        return bankName;
    }

    public int getCountryId() {

        return countryId;
    }

    public int getEntityId() {

        return entityId;
    }

    public String getEntityTypeDescr() {

        return entityTypeDescr;
    }

    public int getEntityTypeId() {

        return entityTypeId;
    }

    public Date getEntryDate() {

        return entryDate;
    }

    public long getLedgerEntryId() {

        return ledgerEntryId;
    }

    public int getProcessStatusId() {

        return processStatusId;
    }

    public String getRoutingNo() {

        return routingNo;
    }

    public int getTransactionTypeId() {

        return transactionTypeId;
    }

    public double getTransAmount() {

        return transAmount;
    }

    // private Date processStatusChgDate;
    public String getTransTypeDescr() {

        return transTypeDescr;
    }

    public void setAccountId(int accountId) {

        this.accountId = accountId;
    }

    public void setAccountNo(String accountNo) {

        this.accountNo = accountNo;
    }

    public void setAccountStatusId(int accountStatusId) {

        this.accountStatusId = accountStatusId;
    }

    public void setAccountTypeId(int accountTypeId) {

        this.accountTypeId = accountTypeId;
    }

    public void setAccTypeDescr(String accTypeDescr) {

        this.accTypeDescr = accTypeDescr;
    }

    public void setAchProcessId(int achProcessId) {

        this.achProcessId = achProcessId;
    }

    public void setBankName(String bankName) {

        this.bankName = bankName;
    }

    public void setCountryId(int countryId) {

        this.countryId = countryId;
    }

    public void setEntityId(int entityId) {

        this.entityId = entityId;
    }

    public void setEntityTypeDescr(String entityTypeDescr) {

        this.entityTypeDescr = entityTypeDescr;
    }

    public void setEntityTypeId(int entityTypeId) {

        this.entityTypeId = entityTypeId;
    }

    public void setEntryDate(Date entryDate) {

        this.entryDate = entryDate;
    }

    public void setLedgerEntryId(long ledgerEntryId) {

        this.ledgerEntryId = ledgerEntryId;
    }

    public void setProcessStatusId(int processStatusId) {

        this.processStatusId = processStatusId;
    }

    public void setRoutingNo(String routingNo) {

        this.routingNo = routingNo;
    }

    public void setTransactionTypeId(int transactionTypeId) {

        this.transactionTypeId = transactionTypeId;
    }

    public void setTransAmount(double transAmount) {

        this.transAmount = transAmount;
    }

    public void setTransTypeDescr(String transTypeDescr) {

        this.transTypeDescr = transTypeDescr;
    }

    @Override
	public String toString() {
      return ("<NachaProcessQueueVo>" + getDelimitedString(this, " | " ) + "</NachaProcessQueueVo>");
    }

    public String getDelimitedString(
            NachaProcessQueueVO nachaProcessQueueVO, String delimiter) {
        StringBuffer sb = new StringBuffer("");
        sb.append(nachaProcessQueueVO.getAccountId());
        sb.append(delimiter);
        sb.append(nachaProcessQueueVO.getAccountId());
        sb.append(delimiter);
        sb.append(nachaProcessQueueVO.getAccountNo());
        sb.append(delimiter);
        sb.append(nachaProcessQueueVO.getAccountStatusId());
        sb.append(nachaProcessQueueVO.getAccountTypeId());
        sb.append(delimiter);
        sb.append(nachaProcessQueueVO.getAccTypeDescr());
        sb.append(delimiter);
        sb.append(nachaProcessQueueVO.getAchProcessId());
        sb.append(delimiter);
        sb.append(nachaProcessQueueVO.getBankName());
        sb.append(delimiter);
        sb.append(nachaProcessQueueVO.getCountryId());
        sb.append(delimiter);
        sb.append(nachaProcessQueueVO.getEntityId());
        sb.append(delimiter);
        sb.append(nachaProcessQueueVO.getEntityTypeDescr());
        sb.append(nachaProcessQueueVO.getEntityTypeId());
        sb.append(delimiter);
        sb.append(nachaProcessQueueVO.getEntryDate());
        sb.append(delimiter);
        sb.append(nachaProcessQueueVO.getLedgerEntryId());
        sb.append(delimiter);
        sb.append(nachaProcessQueueVO.getProcessStatusId());
        sb.append(delimiter);
        sb.append(nachaProcessQueueVO.getRoutingNo());
        sb.append(delimiter);
        sb.append(nachaProcessQueueVO.getTransactionTypeId());
        sb.append(delimiter);
        sb.append(nachaProcessQueueVO.getTransAmount());
        sb.append(delimiter);
        sb.append(nachaProcessQueueVO.getTransTypeDescr());
        return (sb.toString());
    } // getDelimitedString

	public long getLedgerTransactionEntryId() {
		return ledgerTransactionEntryId;
	}

	public void setLedgerTransactionEntryId(long ledgerTransactionEntryId) {
		this.ledgerTransactionEntryId = ledgerTransactionEntryId;
	}

	public long getDiscretionaryData() {
		return discretionaryData;
	}

	public void setDiscretionaryData(long discretionaryData) {
		this.discretionaryData = discretionaryData;
	}

	public double getLedgerTransAmount() {
		return ledgerTransAmount;
	}

	public void setLedgerTransAmount(double ledgerTransAmount) {
		this.ledgerTransAmount = ledgerTransAmount;
	}

}
