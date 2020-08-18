package com.servicelive.wallet.vo;

/**
 * @author swamy patsa.
 */
import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class GlProcessLogVO.
 */
public class SOWalletVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7894484908368883662L;
	private String soId;
	private String wfState;
	private Double balBeforeCorrection;
	private Double costBeforeCorrection;
	private Double balAfterCorrection;
	private Double costAfterCorrection;
	
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getWfState() {
		return wfState;
	}
	public void setWfState(String wfState) {
		this.wfState = wfState;
	}
	public Double getBalBeforeCorrection() {
		return balBeforeCorrection;
	}
	public void setBalBeforeCorrection(Double balBeforeCorrection) {
		this.balBeforeCorrection = balBeforeCorrection;
	}
	public Double getCostBeforeCorrection() {
		return costBeforeCorrection;
	}
	public void setCostBeforeCorrection(Double costBeforeCorrection) {
		this.costBeforeCorrection = costBeforeCorrection;
	}
	public Double getBalAfterCorrection() {
		return balAfterCorrection;
	}
	public void setBalAfterCorrection(Double balAfterCorrection) {
		this.balAfterCorrection = balAfterCorrection;
	}
	public Double getCostAfterCorrection() {
		return costAfterCorrection;
	}
	public void setCostAfterCorrection(Double costAfterCorrection) {
		this.costAfterCorrection = costAfterCorrection;
	}
}
