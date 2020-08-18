package com.newco.marketplace.persistence.iDao.so.order;

import com.newco.marketplace.dto.vo.serviceorder.AdditionalPaymentVO;

/**
 * Interface representing the Data Access Object for so_additional_payment
 * @author dpotlur
 *
 */
public interface IServiceOrderAdditionalPaymentDAO {
	
	public void insertPaymentInfobySo(AdditionalPaymentVO upSellPayment);
	public void insertCheckPaymentInfobySo(AdditionalPaymentVO upSellPayment);
	public void insertCreditCardPaymentInfobySo(AdditionalPaymentVO upSellPayment);
	public AdditionalPaymentVO getAdditionalPaymentInfo(String soID);
	public void deleteAdditionalPaymentInfo(String soID);

}
