package com.servicelive.wallet.remoteservice.dao;

import com.servicelive.common.DataServiceException;
import com.servicelive.wallet.remoteservice.vo.MessageResultVO;

public interface IWalletMessageDao {
	
	public void insertMessage(MessageResultVO message) throws DataServiceException;
	
	public MessageResultVO getResult(String messageId) throws DataServiceException;
}
