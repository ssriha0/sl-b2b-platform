package com.newco.marketplace.persistence.iDao.lookup;

import java.util.ArrayList;

import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface LookupDaoFinance {
	
	public ArrayList<LookupVO> getAccountTypeList() throws DataServiceException;
	public ArrayList<LookupVO> getCreditCardTypeList() throws DataServiceException;
	public ArrayList<LookupVO> getTransferReasonCodeList() throws DataServiceException;
	public int getPostedStatus(String soId);
}
