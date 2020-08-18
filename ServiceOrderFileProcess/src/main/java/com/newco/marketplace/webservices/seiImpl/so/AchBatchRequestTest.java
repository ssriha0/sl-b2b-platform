package com.newco.marketplace.webservices.seiImpl.so;


import com.newco.marketplace.dto.vo.ach.AchProcessQueueEntryVO;
import com.newco.marketplace.dto.vo.ledger.MarketPlaceTransactionVO;
import com.newco.marketplace.utils.RandomGUID;
import com.sears.os.context.ContextValue;

public class AchBatchRequestTest{
	//private int accountId = 10;
	//private int achProcessId = 20;
	//private int entityId = 2;
	//private int entityTypeId = 10;
	//private int processStatusId = 1;
	//private double transactionAmount= 200.00;
	//private int transactionId = 0;
	//private int transactionTypeId = 100;

	/*@Before
	public void init() throws Exception {
		AchProcessQueueEntryVO queueEntry = new AchProcessQueueEntryVO();

		queueEntry.setAccountId(accountId);
		queueEntry.setAchProcessId(achProcessId);
		queueEntry.setEntityId(entityId);
		queueEntry.setEntityTypeId(entityTypeId);
		queueEntry.setProcessStatusId(processStatusId);
		queueEntry.setTransactionAmount(transactionAmount);
		queueEntry.setTransactionId(transactionId);
		queueEntry.setTrasactionTypeId(transactionTypeId);
	}*/

	public static void main(String [] args){
		AchProcessQueueEntryVO queueEntry = new AchProcessQueueEntryVO();
		int accountId = 10;
		//int achProcessId = 20;
		int entityId = 2;
		int entityTypeId = 10;
		int processStatusId = 1;
		double transactionAmount= 1200.00;
		//withdrawl
		//int transactionTypeId = 800;
		//int businessTransactionId = 30;
		//deposit
		int transactionTypeId = 100;
		int businessTransactionId = 0;
		RandomGUID randomIdGenerator = new RandomGUID();
		queueEntry.setAccountId(accountId);
		try {
			Long testLng = randomIdGenerator.generateGUID();
			System.out.println("Random ID Long:"+testLng);
			String testStr = String.valueOf(testLng);
			System.out.println("Random ID String:"+testStr);
			queueEntry.setAchProcessId(testLng.longValue());

		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		queueEntry.setEntityId(entityId);
		queueEntry.setEntityTypeId(entityTypeId);
		queueEntry.setProcessStatusId(processStatusId);
		queueEntry.setTransactionAmount(transactionAmount);

		queueEntry.setTransactionTypeId(transactionTypeId);
		queueEntry.setAchBatchAssocId(1);

		/*IAchBatchRequestBO achBatchRequestBO =
					(AchBatchRequestBOImpl)MPSpringLoaderPlugIn.ctx.getBean("achRequestDao");*/

		ContextValue.setContextFile("resources/spring/applicationContext.xml");
		System.out.println("achtester-->Loaded applicationContext.xml");

		//ILedgerFacilityBO iLedgerFacilityBO = (ILedgerFacilityBO)BeanFactory.getBean("accountingTransactionManagementBO");
		try{
			MarketPlaceTransactionVO service =  new MarketPlaceTransactionVO();
			service.setBusinessTransId(Integer.valueOf(businessTransactionId));
			service.setBuyerID(Integer.valueOf(entityId));
			service.setCCInd(false);
			/*
			boolean flag = iLedgerFacilityBO.fundACHAccountAction(queueEntry,service);
			*/
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Problem creating queue entry");
		}
	}

}
