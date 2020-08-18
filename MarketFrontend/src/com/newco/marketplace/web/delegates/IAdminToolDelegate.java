package com.newco.marketplace.web.delegates;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.auth.SecurityContext;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;

public interface IAdminToolDelegate {

	
	/**
	 * Description: Method used by admins to create a fullfillment_entry table
	 * record.
	 * 
	 * @param newAmount
	 * @param fulfillmentEntryIds
	 * @return <code>Map</code> of original id record was copied from and new
	 *         entry id.
	 * @throws Exception
	 */
	public Map<String, Long> createFullfillmentEntry(String fulfillmentEntryId, Double newAmount, String comments, String userName) throws Exception;

	//public void insertAdminToolLogging(FullfillmentAdminToolVO adminToolVO)throws Exception;

	/**
	 * Description: Method used by admins to lookup existing fullfillment_entry
	 * table record(s).
	 * @param groupId 
	 * 
	 * @param fulfillmentEntryIds
	 *            This can one or a space seperated list.
	 * @return <code>List</code> of VOs matching the request.
	 * @throws Exception
	 */
	public List<ValueLinkEntryVO> lookupFullfillmentEntry(String[] fulfillmentEntryId, Boolean groupId) throws Exception;

	/**
	 * Description: Method used by admins to make an off-setting entry to an
	 * already existing fullfillment_entry table record.
	 * 
	 * @param fulfillmentEntryIds
	 *            This can one or a space seperated list.
	 * @return <code>Map</code> of original ids and new off-setting entry ids.
	 * @throws Exception
	 */
	public Map<String, Long> processFullfillmentAdjustment(String[] fulfillmentEntryIds, String comments, String userName) throws Exception;
	
	
	/**
	 * Description: Method used by admins to resend a group of fulfillment records.
	 * @param split
	 * @return
	 */
	public List<ValueLinkEntryVO> processGroupResend(String[] split, String comments, String userName) throws Exception;

	/**
	 * @param glDate
	 * @throws SLBusinessServiceException
	 */
	public void runGLProcess(Date glDate) throws SLBusinessServiceException;
	
	/**
	 * @param nachaDate
	 * @throws SLBusinessServiceException
	 */
	public void runNachaProcess(Date nachaDate) throws SLBusinessServiceException;
	
	/**
	 * @param vendorId
	 * @throws SLBusinessServiceException
	 */
	public boolean sendBalanceInquiryMessage(Integer vendorId) throws Exception;

	public List<ServiceOrder> getPendingServiceOrders()
			throws SLBusinessServiceException;

	public void cancelPendingWalletTransaction(String soId, SecurityContext ctx)
			throws SLBusinessServiceException;
}
