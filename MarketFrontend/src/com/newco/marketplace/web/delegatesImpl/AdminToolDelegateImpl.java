package com.newco.marketplace.web.delegatesImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.web.delegates.IAdminToolDelegate;
import com.newco.marketplace.web.utils.OFUtils;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import com.servicelive.wallet.batch.ach.balanced.BalancedFileProcessor;
import com.servicelive.wallet.batch.gl.GLProcessor;
import com.servicelive.wallet.batch.vl.BalanceInquiryProcessor;
import com.servicelive.wallet.serviceinterface.IWalletBO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;

public class AdminToolDelegateImpl implements IAdminToolDelegate {

	private IWalletBO walletBO;
	private GLProcessor glProcessor;
	private BalancedFileProcessor balancedFileProcessor;
	private BalanceInquiryProcessor balanceInquiryProcessor;
	private OFHelper ofHelper;
	
	public BalanceInquiryProcessor getBalanceInquiryProcessor() {
		return balanceInquiryProcessor;
	}

	public void setBalanceInquiryProcessor(
			BalanceInquiryProcessor balanceInquiryProcessor) {
		this.balanceInquiryProcessor = balanceInquiryProcessor;
	}

	public Map<String, Long> createFullfillmentEntry(String fulfillmentEntryId, Double newAmount, String comments, String userName) throws Exception {
		return walletBO.createValueLinkWithNewAmount(fulfillmentEntryId, newAmount, comments, userName);
	}

	public List<ValueLinkEntryVO> lookupFullfillmentEntry(
			String[] fulfillmentEntryId, Boolean groupId) throws Exception {
		return walletBO.getValueLinkEntries(fulfillmentEntryId, groupId);
	}

	public Map<String, Long> processFullfillmentAdjustment(String[] fulfillmentEntryIds, String comments, String userName) throws Exception {
		return walletBO.reverseValueLinkTransaction(fulfillmentEntryIds, comments, userName);
	}

	public List<ValueLinkEntryVO> processGroupResend(String[] fulfillmentGroupIds, String comments, String userName)
			throws Exception {
		return walletBO.processGroupResend(fulfillmentGroupIds, comments, userName);
	}

	public boolean sendBalanceInquiryMessage(Integer vendorId) throws Exception
	{
		return balanceInquiryProcessor.sendBalanceInquiryMessage(vendorId);
	}
	
	public void setWalletBO(IWalletBO walletBO) {
		this.walletBO = walletBO;
	}

	public IWalletBO getWalletBO() {
		return walletBO;
	}

	public void runGLProcess(Date glDate) throws SLBusinessServiceException {
		glProcessor.process(glDate);
	}

	public void runNachaProcess(Date nachaDate) throws SLBusinessServiceException {
		balancedFileProcessor.process(nachaDate);
	}
	
	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}
	
	public void cancelPendingWalletTransaction( String soId, SecurityContext ctx ) throws SLBusinessServiceException {
		
		OrderFulfillmentRequest req = OFUtils.createRequestForCancelPendingTransaction(ctx);
		OrderFulfillmentResponse resp = ofHelper.runOrderFulfillmentProcess(soId, SignalType.ADMIN_CANCEL_PENDING_TRANSACTION, req);
		
		if( resp.getErrors().size() > 0 ) {
			throw new SLBusinessServiceException( resp.getErrors() );
		}
	}

	public List<ServiceOrder> getPendingServiceOrders() throws SLBusinessServiceException {
		List<ServiceOrder> orders = this.ofHelper.getPendingServiceOrders();
        //check if the list is null then give empty list
        return (null == orders)? new ArrayList<ServiceOrder>() : orders;
	}

	/**
	 * @param glProcessor the glProcessor to set
	 */
	public void setGlProcessor(GLProcessor glProcessor) {
		this.glProcessor = glProcessor;
	}

	/**
	 * @param balanceFileProcessor the balanceFileProcessor to set
	 */
	public void setBalancedFileProcessor(BalancedFileProcessor balancedFileProcessor) {
		this.balancedFileProcessor = balancedFileProcessor;
	}
}
