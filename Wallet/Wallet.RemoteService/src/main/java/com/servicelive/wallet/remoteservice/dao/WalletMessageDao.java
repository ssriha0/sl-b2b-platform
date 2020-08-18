package com.servicelive.wallet.remoteservice.dao;

import com.servicelive.common.ABaseDao;
import com.servicelive.common.DataServiceException;
import com.servicelive.wallet.remoteservice.vo.MessageResultVO;

public class WalletMessageDao extends ABaseDao implements IWalletMessageDao {
	
	public  MessageResultVO getResult(String messageId) throws DataServiceException {
		try{
			return (MessageResultVO) this.queryForObject("walletmessage.read", messageId);
		}catch(Exception dae){
			throw new DataServiceException(dae.getMessage());
		}
	}

	public void insertMessage(MessageResultVO params) throws DataServiceException {
		try{
			insert("walletmessage.insert", params);
		}catch(Exception dae){
			throw new DataServiceException(dae.getMessage());
		}
	}

}
