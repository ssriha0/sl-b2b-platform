package com.servicelive.gl.vo;

/**
 * @author swamy patsa.
 */
import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class GlProcessLogVO.
 */
public class SoToBeCorrectedVO implements Serializable {

	/** The Constant serialVersionUID. */
	/**
	 * 
	 */
	private static final long serialVersionUID = -5376939705687658127L;

	/** The file name. */
	private String soId;

	/** The from date. */
	private String buyerId;

	private String wfState;
	
	private String fundingTypeId;
	
	private double availableBalanceOld;
	
	private double availableBalanceNew;

	private double soProjBal;

	/** The process date. */
	private double soCost;
	
	private double soAddonCost;
	
	private double soPartCost;
	
	private double soIncSpendLimit;
	
	private double soDecSpendLimit;

	/** The process succesfull. */
	private double soProjBalCorrected;

	/** The to date. */
	private double soCostCorrected;

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public String getWfState() {
		return wfState;
	}

	public void setWfState(String wfState) {
		this.wfState = wfState;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public double getSoProjBal() {
		return soProjBal;
	}

	public void setSoProjBal(double soProjBal) {
		this.soProjBal = soProjBal;
	}

	public double getSoCost() {
		return soCost;
	}

	public void setSoCost(double soCost) {
		this.soCost = soCost;
	}

	public double getSoProjBalCorrected() {
		return soProjBalCorrected;
	}

	public void setSoProjBalCorrected(double soProjBalCorrected) {
		this.soProjBalCorrected = soProjBalCorrected;
	}

	public double getSoCostCorrected() {
		return soCostCorrected;
	}

	public void setSoCostCorrected(double soCostCorrected) {
		this.soCostCorrected = soCostCorrected;
	}

	public String getFundingTypeId() {
		return fundingTypeId;
	}

	public void setFundingTypeId(String fundingTypeId) {
		this.fundingTypeId = fundingTypeId;
	}

	public double getSoAddonCost() {
		return soAddonCost;
	}

	public void setSoAddonCost(double soAddonCost) {
		this.soAddonCost = soAddonCost;
	}

	public double getSoIncSpendLimit() {
		return soIncSpendLimit;
	}

	public void setSoIncSpendLimit(double soIncSpendLimit) {
		this.soIncSpendLimit = soIncSpendLimit;
	}

	public double getSoDecSpendLimit() {
		return soDecSpendLimit;
	}

	public void setSoDecSpendLimit(double soDecSpendLimit) {
		this.soDecSpendLimit = soDecSpendLimit;
	}

	public double getSoPartCost() {
		return soPartCost;
	}

	public void setSoPartCost(double soPartCost) {
		this.soPartCost = soPartCost;
	}

	public double getAvailableBalanceOld() {
		return availableBalanceOld;
	}

	public void setAvailableBalanceOld(double availableBalanceOld) {
		this.availableBalanceOld = availableBalanceOld;
	}

	public double getAvailableBalanceNew() {
		return availableBalanceNew;
	}

	public void setAvailableBalanceNew(double availableBalanceNew) {
		this.availableBalanceNew = availableBalanceNew;
	}

}
