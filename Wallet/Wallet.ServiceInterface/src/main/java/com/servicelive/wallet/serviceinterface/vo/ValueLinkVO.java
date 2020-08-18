package com.servicelive.wallet.serviceinterface.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.servicelive.common.vo.ABaseVO;


// TODO: Auto-generated Javadoc
/**
 * Class ValueLinkVO.
 */
@XmlRootElement()
public class ValueLinkVO extends ABaseVO {

	/** serialVersionUID. */
	private static final long serialVersionUID = 3566246008118364117L;

	/** buyerState. */
	private String buyerState;

	/** buyerV1AccountNumber. */
	private Long buyerV1AccountNumber;

	/** buyerV1CreditAmount. */
	private Double buyerV1CreditAmount;

	/** buyerV1DebitAmount. */
	private Double buyerV1DebitAmount;

	/** buyerV2AccountNumber. */
	private Long buyerV2AccountNumber;

	/** buyerV2CreditAmount. */
	private Double buyerV2CreditAmount;

	/** buyerV2DebitAmount. */
	private Double buyerV2DebitAmount;

	/** providerState. */
	private String providerState;

	/** providerV1AccountNumber. */
	private Long providerV1AccountNumber;

	/** providerV1CreditAmount. */
	private Double providerV1CreditAmount;

	/** providerV1DebitAmount. */
	private Double providerV1DebitAmount;

	/** serviceLiveSL1AccountNumber. */
	private Long serviceLiveSL1AccountNumber;

	/** serviceLiveSL1CreditAmount. */
	private Double serviceLiveSL1CreditAmount;

	/** serviceLiveSL1DebitAmount. */
	private Double serviceLiveSL1DebitAmount;

	/** serviceLiveSL3AccountNumber. */
	private Long serviceLiveSL3AccountNumber;

	/** serviceLiveSL3CreditAmount. */
	private Double serviceLiveSL3CreditAmount;

	/** serviceLiveSL3DebitAmount. */
	private Double serviceLiveSL3DebitAmount;

	/** serviceLiveSL3DebitAmount. */
	private boolean isAdminCredit = false;
	
	/**
	 * ValueLinkVO.
	 */
	public ValueLinkVO() {

	}

	/**
	 * ValueLinkVO.
	 * 
	 * @param request 
	 */
	public ValueLinkVO(ABaseVO request) {

		super(request);
	}

	/**
	 * ValueLinkVO.
	 * 
	 * @param request 
	 */
	public ValueLinkVO(ValueLinkVO request) {

		super(request);
		this.buyerState = request.getBuyerState();
		this.providerState = request.getProviderState();
		this.buyerV1AccountNumber = request.getBuyerV1AccountNumber();
		this.buyerV2AccountNumber = request.getBuyerV2AccountNumber();
		this.providerV1AccountNumber = request.getProviderV1AccountNumber();
		this.serviceLiveSL1AccountNumber = request.getServiceLiveSL1AccountNumber();
		this.serviceLiveSL3AccountNumber = request.getServiceLiveSL3AccountNumber();
		this.buyerV1CreditAmount = request.getBuyerV1CreditAmount();
		this.buyerV1DebitAmount = request.getBuyerV1DebitAmount();
		this.buyerV2CreditAmount = request.getBuyerV2CreditAmount();
		this.buyerV2DebitAmount = request.getBuyerV2DebitAmount();
		this.providerV1CreditAmount = request.getProviderV1CreditAmount();
		this.providerV1DebitAmount = request.getProviderV1DebitAmount();
		this.serviceLiveSL1CreditAmount = request.getServiceLiveSL1CreditAmount();
		this.serviceLiveSL1DebitAmount = request.getServiceLiveSL1DebitAmount();
		this.serviceLiveSL3CreditAmount = request.getServiceLiveSL3CreditAmount();
		this.serviceLiveSL3DebitAmount = request.getServiceLiveSL3DebitAmount();
	}

	/**
	 * getBuyerState.
	 * 
	 * @return String
	 */
	public String getBuyerState() {

		return buyerState;
	}

	/**
	 * getBuyerV1AccountNumber.
	 * 
	 * @return Long
	 */
	public Long getBuyerV1AccountNumber() {

		return buyerV1AccountNumber;
	}

	/**
	 * getBuyerV1CreditAmount.
	 * 
	 * @return Double
	 */
	public Double getBuyerV1CreditAmount() {

		return buyerV1CreditAmount;
	}

	/**
	 * getBuyerV1DebitAmount.
	 * 
	 * @return Double
	 */
	public Double getBuyerV1DebitAmount() {

		return buyerV1DebitAmount;
	}

	/**
	 * getBuyerV2AccountNumber.
	 * 
	 * @return Long
	 */
	public Long getBuyerV2AccountNumber() {

		return buyerV2AccountNumber;
	}

	/**
	 * getBuyerV2CreditAmount.
	 * 
	 * @return Double
	 */
	public Double getBuyerV2CreditAmount() {

		return buyerV2CreditAmount;
	}

	/**
	 * getBuyerV2DebitAmount.
	 * 
	 * @return Double
	 */
	public Double getBuyerV2DebitAmount() {

		return buyerV2DebitAmount;
	}

	/**
	 * getProviderState.
	 * 
	 * @return String
	 */
	public String getProviderState() {

		return providerState;
	}

	/**
	 * getProviderV1AccountNumber.
	 * 
	 * @return Long
	 */
	public Long getProviderV1AccountNumber() {

		return providerV1AccountNumber;
	}

	/**
	 * getProviderV1CreditAmount.
	 * 
	 * @return Double
	 */
	public Double getProviderV1CreditAmount() {

		return providerV1CreditAmount;
	}

	/**
	 * getProviderV1DebitAmount.
	 * 
	 * @return Double
	 */
	public Double getProviderV1DebitAmount() {

		return providerV1DebitAmount;
	}

	/**
	 * getServiceLiveSL1AccountNumber.
	 * 
	 * @return Long
	 */
	public Long getServiceLiveSL1AccountNumber() {

		return serviceLiveSL1AccountNumber;
	}

	/**
	 * getServiceLiveSL1CreditAmount.
	 * 
	 * @return Double
	 */
	public Double getServiceLiveSL1CreditAmount() {

		return serviceLiveSL1CreditAmount;
	}

	/**
	 * getServiceLiveSL1DebitAmount.
	 * 
	 * @return Double
	 */
	public Double getServiceLiveSL1DebitAmount() {

		return serviceLiveSL1DebitAmount;
	}

	/**
	 * getServiceLiveSL3AccountNumber.
	 * 
	 * @return Long
	 */
	public Long getServiceLiveSL3AccountNumber() {

		return serviceLiveSL3AccountNumber;
	}

	/**
	 * getServiceLiveSL3CreditAmount.
	 * 
	 * @return Double
	 */
	public Double getServiceLiveSL3CreditAmount() {

		return serviceLiveSL3CreditAmount;
	}

	/**
	 * getServiceLiveSL3DebitAmount.
	 * 
	 * @return Double
	 */
	public Double getServiceLiveSL3DebitAmount() {

		return serviceLiveSL3DebitAmount;
	}

	/**
	 * setBuyerState.
	 * 
	 * @param buyerState 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setBuyerState(String buyerState) {

		this.buyerState = buyerState;
	}

	/**
	 * setBuyerV1AccountNumber.
	 * 
	 * @param buyerV1AccountNumber 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setBuyerV1AccountNumber(Long buyerV1AccountNumber) {

		this.buyerV1AccountNumber = buyerV1AccountNumber;
	}

	/**
	 * setBuyerV1CreditAmount.
	 * 
	 * @param buyerV1CreditAmount 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setBuyerV1CreditAmount(Double buyerV1CreditAmount) {

		this.buyerV1CreditAmount = buyerV1CreditAmount;
	}

	/**
	 * setBuyerV1DebitAmount.
	 * 
	 * @param buyerV1DebitAmount 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setBuyerV1DebitAmount(Double buyerV1DebitAmount) {

		this.buyerV1DebitAmount = buyerV1DebitAmount;
	}

	/**
	 * setBuyerV2AccountNumber.
	 * 
	 * @param buyerV2AccountNumber 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setBuyerV2AccountNumber(Long buyerV2AccountNumber) {

		this.buyerV2AccountNumber = buyerV2AccountNumber;
	}

	/**
	 * setBuyerV2CreditAmount.
	 * 
	 * @param buyerV2CreditAmount 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setBuyerV2CreditAmount(Double buyerV2CreditAmount) {

		this.buyerV2CreditAmount = buyerV2CreditAmount;
	}

	/**
	 * setBuyerV2DebitAmount.
	 * 
	 * @param buyerV2DebitAmount 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setBuyerV2DebitAmount(Double buyerV2DebitAmount) {

		this.buyerV2DebitAmount = buyerV2DebitAmount;
	}

	/**
	 * setProviderState.
	 * 
	 * @param providerState 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setProviderState(String providerState) {

		this.providerState = providerState;
	}

	/**
	 * setProviderV1AccountNumber.
	 * 
	 * @param providerV1AccountNumber 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setProviderV1AccountNumber(Long providerV1AccountNumber) {

		this.providerV1AccountNumber = providerV1AccountNumber;
	}

	/**
	 * setProviderV1CreditAmount.
	 * 
	 * @param providerV1CreditAmount 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setProviderV1CreditAmount(Double providerV1CreditAmount) {

		this.providerV1CreditAmount = providerV1CreditAmount;
	}

	/**
	 * setProviderV1DebitAmount.
	 * 
	 * @param providerV1DebitAmount 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setProviderV1DebitAmount(Double providerV1DebitAmount) {

		this.providerV1DebitAmount = providerV1DebitAmount;
	}

	/**
	 * setServiceLiveSL1AccountNumber.
	 * 
	 * @param serviceLiveSL1AccountNumber 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setServiceLiveSL1AccountNumber(Long serviceLiveSL1AccountNumber) {

		this.serviceLiveSL1AccountNumber = serviceLiveSL1AccountNumber;
	}

	/**
	 * setServiceLiveSL1CreditAmount.
	 * 
	 * @param serviceLiveSL1CreditAmount 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setServiceLiveSL1CreditAmount(Double serviceLiveSL1CreditAmount) {

		this.serviceLiveSL1CreditAmount = serviceLiveSL1CreditAmount;
	}

	/**
	 * setServiceLiveSL1DebitAmount.
	 * 
	 * @param serviceLiveSL1DebitAmount 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setServiceLiveSL1DebitAmount(Double serviceLiveSL1DebitAmount) {

		this.serviceLiveSL1DebitAmount = serviceLiveSL1DebitAmount;
	}

	/**
	 * setServiceLiveSL3AccountNumber.
	 * 
	 * @param serviceLiveSL3AccountNumber 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setServiceLiveSL3AccountNumber(Long serviceLiveSL3AccountNumber) {

		this.serviceLiveSL3AccountNumber = serviceLiveSL3AccountNumber;
	}

	/**
	 * setServiceLiveSL3CreditAmount.
	 * 
	 * @param serviceLiveSL3CreditAmount 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setServiceLiveSL3CreditAmount(Double serviceLiveSL3CreditAmount) {

		this.serviceLiveSL3CreditAmount = serviceLiveSL3CreditAmount;
	}

	/**
	 * setServiceLiveSL3DebitAmount.
	 * 
	 * @param serviceLiveSL3DebitAmount 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setServiceLiveSL3DebitAmount(Double serviceLiveSL3DebitAmount) {

		this.serviceLiveSL3DebitAmount = serviceLiveSL3DebitAmount;
	}

	public void setAdminCredit(boolean isAdminCredit) {
		this.isAdminCredit = isAdminCredit;
	}

	public boolean isAdminCredit() {
		return isAdminCredit;
	}

}
