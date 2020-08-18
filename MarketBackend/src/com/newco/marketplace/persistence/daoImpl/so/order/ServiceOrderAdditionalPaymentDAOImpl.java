package com.newco.marketplace.persistence.daoImpl.so.order;

import com.newco.marketplace.dto.vo.serviceorder.AdditionalPaymentVO;
import com.newco.marketplace.persistence.iDao.so.order.IServiceOrderAdditionalPaymentDAO;
import com.sears.os.dao.impl.ABaseImplDao;

public class ServiceOrderAdditionalPaymentDAOImpl extends ABaseImplDao
		implements IServiceOrderAdditionalPaymentDAO {
	
	/**
	 * method to insert the PaymentInfo
	 */
	
	public void insertPaymentInfobySo(AdditionalPaymentVO paymentVO){
		String soId = paymentVO.getSoId();
		deleteAdditionalPaymentInfo(soId);
		insert("additionalpayment.insert", paymentVO);
	}
	
	public void insertCheckPaymentInfobySo(AdditionalPaymentVO paymentVO){
		String soId = paymentVO.getSoId();
		deleteAdditionalPaymentInfo(soId);
		insert("additionalpayment.check.insert", paymentVO);
	}

	
	public void insertCreditCardPaymentInfobySo(AdditionalPaymentVO paymentVO){
		String soId = paymentVO.getSoId();
		deleteAdditionalPaymentInfo(soId);
		insert("additionalpayment.creditcard.insert", paymentVO);
	}

	public AdditionalPaymentVO getAdditionalPaymentInfo(String soID){
		return (AdditionalPaymentVO) queryForObject("soUpsell.soUpsoldPayment.query", soID);
	}

	public void deleteAdditionalPaymentInfo(String soID)
	{
		delete("additionalpayment.delete", soID);
	}
}
