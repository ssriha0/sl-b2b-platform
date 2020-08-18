package com.newco.marketplace.admintools.ach;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.nachaadmin.INachaAdminTool;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.util.BaseFileUtil;

public class ResetOrginationFileRecords {
	private static final Logger logger = Logger.getLogger(ResetOrginationFileRecords.class
			.getName());
	/**
	 * @param args
	 */
	public static void resetOriginationData(String controlFileName) throws Exception{
		BaseFileUtil fileUtil = new BaseFileUtil();
		/*if(args.length < 1){
			System.out.println("Insuffcient Arguments");
			System.exit(0);
		}*/
		try{
			if(controlFileName == null){
				System.out.println("Nacha process Log Id/Process Owner Should not be null.");
				throw new Exception("Nacha process Log Id/Process Owner Should not be null.");
			}
			INachaAdminTool nachaAdminBo = (INachaAdminTool)MPSpringLoaderPlugIn.getCtx().getBean("nachaAdminToolBO");
			Integer ledgerEntryId;
			File file = new File(controlFileName);
			String ledgerEntryIdsStr = fileUtil.readLine(file);
			List<Integer> ledgerEntryIdsList = fileUtil.splitRecordData(ledgerEntryIdsStr, ",");
			Iterator<Integer> itr = ledgerEntryIdsList.iterator();
            while(itr.hasNext()){
            	ledgerEntryId = itr.next();
            	// nachaAdminBo.resetOrginationFileRec(ledgerEntryId);
            }
            			
			
		}catch(BusinessServiceException bse){
			logger.error(bse.getMessage(), bse);
			throw bse;
		}catch(NumberFormatException nfe){
			logger.error("<<<<< Process Failed >>>> Ledger Entry Id should be an Integer value.", nfe);
			throw nfe;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw e;
		}

	}

}
