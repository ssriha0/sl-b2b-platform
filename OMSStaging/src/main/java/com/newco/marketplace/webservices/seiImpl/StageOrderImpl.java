package com.newco.marketplace.webservices.seiImpl;

import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.webservices.dao.ShcErrorLogging;
import com.newco.marketplace.webservices.dao.ShcOrder;
import com.newco.marketplace.webservices.dao.ShcOrderAddOn;
import com.newco.marketplace.webservices.dao.SpecialtyAddOn;
import com.newco.marketplace.webservices.dto.StagingDetails;
import com.newco.marketplace.webservices.sei.StageOrderSEI;

/**
 */
@WebService(endpointInterface="com.newco.marketplace.webservices.sei.StageOrderSEI", serviceName="StageOrderSEI")
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public class StageOrderImpl implements StageOrderSEI {

	//private Logger logger = Logger.getLogger(StageOrderImpl.class);
	private StageOrderSEI stageDispatch;

	public int createNPSProcessLog(StagingDetails stagingDetails,
			String fileName) {
		return stageDispatch.createNPSProcessLog(stagingDetails, fileName);
	}

	public void persistErrors(String errorCode, String errorMessage,
			String orderNumber, String unitNumber) {
		stageDispatch.persistErrors(errorCode, errorMessage, orderNumber, unitNumber);
		
	}

	public List<ShcOrder> retrieveClosedOrders(int npsOrderToClose) {
		return stageDispatch.retrieveClosedOrders(npsOrderToClose);
	}

	public void stageDataAfterProcessing(ShcOrder shcOrder,
			List<ShcErrorLogging> shcErrorLoggingList) throws Exception {
		stageDispatch.stageDataAfterProcessing(shcOrder, shcErrorLoggingList);
		
	}

	
	public void stageDataAfterUnMarshalling(StagingDetails stagingDetails) throws Exception {
		stageDispatch.stageDataAfterUnMarshalling(stagingDetails);
	}
	
	public void stageShcOrderAfterTranslation(ShcOrder shcOrder) {
		stageDispatch.stageShcOrderAfterTranslation(shcOrder);		
	}
	
	public boolean stageUpsoldInfo(StagingDetails stagingDetails) {
		return stageDispatch.stageUpsoldInfo(stagingDetails);
		
	}
	public List<SpecialtyAddOn> getMiscAddOn(String specialtyCode, String divisionCode)
	{
		return stageDispatch.getMiscAddOn( specialtyCode, divisionCode );
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.webservices.sei.StageOrderSEI#updateFinalPrice(java.lang.String, java.lang.String, java.lang.Double, java.lang.Double)
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
	public void updateFinalPrice(String orderNo, String unitNo,
			Double laborPrice, Double permitPrice, Double serviceFee) throws Exception {
		stageDispatch.updateFinalPrice(orderNo, unitNo, laborPrice, permitPrice, serviceFee);		
	}
	
	
	public SpecialtyAddOn getCallCollectAddon(String specialtyCode, String stockNumber ) throws Exception {
		return stageDispatch.getCallCollectAddon( specialtyCode, stockNumber );
	}

	/**
	 * @return the stageDispatch
	 */
	public StageOrderSEI getStageDispatch() {
		return stageDispatch;
	}

	/**
	 * @param stageDispatch the stageDispatch to set
	 */
	public void setStageDispatch(StageOrderSEI stageDispatch) {
		this.stageDispatch = stageDispatch;
	}
	
	public List<ShcOrderAddOn> getShcOrderDetails(String orderNumber, String unitNumber){
		return stageDispatch.getShcOrderDetails(orderNumber,unitNumber);
	}
	
	public ShcOrder getStageOrder(String orderNo, String unitNo) throws Exception{
		return stageDispatch.getStageOrder(orderNo, unitNo);
	}

}

