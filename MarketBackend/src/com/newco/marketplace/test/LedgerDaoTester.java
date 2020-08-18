package com.newco.marketplace.test;

import java.util.ArrayList;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.ledger.TransactionEntryTypeVO;
import com.newco.marketplace.dto.vo.ledger.TransactionRuleVO;
import com.newco.marketplace.persistence.daoImpl.ledger.TransactionDaoImpl;
import com.newco.marketplace.persistence.iDao.ledger.TransactionDao;

public class LedgerDaoTester{
    public static final String BEAN_NAME_TRANSACTION_DAO = "transactionDao";
    public static final String BEAN_NAME_ACCOUNTING_TRANSACTION_MANAGEMENT = "accountingTransactionManagementBO";
	public static void main (String[] args) {
		
		System.out.println("loading applicationContext.xml");
		try {
			/*************************************
			 * Testing the queryTransactionRule
			 *************************************/
			TransactionDao dao = (TransactionDaoImpl)MPSpringLoaderPlugIn.getCtx().getBean(BEAN_NAME_TRANSACTION_DAO);
			ArrayList alTester = new ArrayList();
			TransactionRuleVO vo = new TransactionRuleVO();

			vo.setBusTransId(130);
			vo.setLedgerEntityTypeId(10);
			alTester = (ArrayList) dao.queryTransactionRule(vo);
			for (java.util.Iterator it = alTester.iterator(); it.hasNext();) {
				TransactionRuleVO itTR = (TransactionRuleVO) it.next();
				System.out.println("Transaction RULES" + itTR.getBusTransDescr());
			}
			
			/***************************************
			 * Testing the loadTransactionEntryType
			 ***************************************/
			ArrayList alTester2 = new ArrayList();
			alTester2 = (ArrayList) dao.loadTransactionEntryType();

			for (java.util.Iterator it2 = alTester2.iterator(); it2.hasNext();) {
				TransactionEntryTypeVO itTR2 = (TransactionEntryTypeVO) it2.next();
				System.out.println("Transaction ENTRY TYPE" + itTR2.getDescr());
			}
			
			/***********************************************
			 * Testing the ledgerBusinessTransaction INSERT
			 ***********************************************/
			IServiceOrderBO x = (IServiceOrderBO)MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderBOTarget");
			System.out.println("LedgerBusinessTransaction has been INSERTED");
		} catch (Throwable t) {
			t.printStackTrace();
			System.out.println(t.getMessage());
		}
	
	}
}
