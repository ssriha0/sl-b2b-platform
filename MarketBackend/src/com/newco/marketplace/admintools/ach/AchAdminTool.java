package com.newco.marketplace.admintools.ach;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newco.marketplace.business.iBusiness.ledger.ILedgerFacilityBO;

public class AchAdminTool {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		try {
			if (args[0].equals("acknowledgement") && args.length == 3){

				Integer processLogId = new Integer(args[1]);
				String processOwner = args[2];
				ResetAcknowledgementFiles.resetAcknowledgementData(processLogId, processOwner);

			}else if (args[0].equals("origination") && args.length == 2){
				String controlFileName = args[1];
				ResetOrginationFileRecords.resetOriginationData(controlFileName);
			}else if(args[0].equals("origination") && args.length == 3){
				ApplicationContext appContext = new ClassPathXmlApplicationContext("resources/spring/admintoolApplicationContext.xml");
				ILedgerFacilityBO iLedgerFacilityBO = (ILedgerFacilityBO)appContext.getBean("accountingTransactionManagementBO");
				Date date1 = null;
				Date date2 = null;
				if(args[1] != null && args[2] != null){
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy 'at' HH:mm:ss");
					date1 = sdf.parse(args[1]);
					date2 = sdf.parse(args[2]);
				}
				//iLedgerFacilityBO.writeGLFeed(date1, date2);
			}else{
				System.out.println("Insuffcient Arguments for specified action");
				System.exit(0);
			}
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
}
