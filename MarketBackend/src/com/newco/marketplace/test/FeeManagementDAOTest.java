package com.newco.marketplace.test;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.dto.vo.fee.FeeInfoVO;
import com.newco.marketplace.dto.vo.ledger.MarketPlaceTransactionVO;
import com.newco.marketplace.persistence.daoImpl.feemanager.FeeManagerDaoImpl;

public class FeeManagementDAOTest {
	public static void main(String[] args) {
		try {
			FeeManagerDaoImpl dao = (FeeManagerDaoImpl)MPSpringLoaderPlugIn.getCtx().getBean("feeManagementDao");
			MarketPlaceTransactionVO vo = new MarketPlaceTransactionVO();
			vo.setLedgerEntryRuleId(60);
			vo.setUserTypeID(10);
			FeeInfoVO fee = dao.getLedgerFee(vo);
			System.out.println(fee);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

