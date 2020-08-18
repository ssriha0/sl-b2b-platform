package com.newco.marketplace.admintools.ach;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newco.marketplace.business.iBusiness.nachaadmin.INachaAdminTool;
import com.newco.marketplace.exception.BusinessServiceException;
/**
 * @author spatsa
 */
public class ResetAcknowledgementFiles{
	private static final Logger logger = Logger.getLogger(ResetAcknowledgementFiles.class
			.getName());
	/**
	 * @param args[0] is Nacha Process Log Id
	 * @param args[1] is Process Owner Name
	 * This is the entry point for Resetting the rejected files from the First Data.
	 * When Service Live Admin runs script with above two parameters. Script should call this main method.
	 */
	public static void resetAcknowledgementData(Integer processLogId, String processOwner)  throws Exception{
		/*if(args.length < 2){
			System.out.println("Insuffcient Arguments");
			System.exit(0);
		}*/
		
		
		try{
			if(processLogId == null || processOwner == null){
				System.out.println("Nacha process Log Id/Process Owner Should not be null.");
				throw new Exception("Nacha process Log Id/Process Owner Should not be null.");
			}
			/*Integer processLogId = new Integer(args[0]);
			String processOwner = args[1];*/
			ApplicationContext appContext = new ClassPathXmlApplicationContext("resources/spring/admintoolApplicationContext.xml");
			INachaAdminTool nachaAdminBo = (INachaAdminTool)appContext.getBean("nachaAdminToolBO");
			// nachaAdminBo.resetFDRejectedBatch(processLogId, processOwner);
			System.out.println("<<<<< Process Success >>>> ");
		}catch(BusinessServiceException bse){
			logger.error(bse.getMessage(), bse);
			throw bse;
			
		}catch(NumberFormatException nfe){
			logger.error("<<<<< Process Failed >>>> Nacha Process Log Id should be an Integer value.", nfe);
			throw nfe;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw e;
		}

	}

}
