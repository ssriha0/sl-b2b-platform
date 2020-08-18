package com.servicelive.wallet.valuelink;

import java.util.List;
import java.util.Map;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkVO;

public interface IValueLinkBO {

	public boolean checkValueLinkReconciledIndicator(String soId) throws SLBusinessServiceException;
	
	public boolean isACHTransPending(String soId) throws SLBusinessServiceException ;

	
    public boolean hasPreviousAddOn(String serviceOrderId)throws SLBusinessServiceException;


	public ValueLinkEntryVO getValueLinkMessageDetail(Long fullfillmentEntryId) throws SLBusinessServiceException;

	public void sendValueLinkRequest(ValueLinkVO request) throws SLBusinessServiceException;
	
	public List<ValueLinkEntryVO> getValueLinkEntries(String[] valueLinkEntryId, Boolean groupId) throws SLBusinessServiceException;
	
	public List<ValueLinkEntryVO> processGroupResend(String[] groupIds, String comments, String userName) throws SLBusinessServiceException;

	public Map<String, Long> reverseValueLinkTransaction(String[] valueLinkIds, String comments, String userName) throws SLBusinessServiceException;

	public Map<String, Long> createValueLinkWithNewAmount(String fulfillmentEntryId, Double newAmount, String comments, String userName) throws SLBusinessServiceException;

	public double getCompletedAmount(long vendorId) throws SLBusinessServiceException;
	
}
