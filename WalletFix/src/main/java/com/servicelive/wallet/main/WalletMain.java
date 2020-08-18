package com.servicelive.wallet.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.servicelive.gl.constants.FileConstants;
import com.servicelive.gl.constants.DBConstants;
import com.servicelive.gl.db.DBAccess;
import com.servicelive.gl.properties.ReadProperties;
import com.servicelive.gl.vo.SoToBeCorrectedVO;

public class WalletMain {

	
	private static final Logger logger = Logger.getLogger(WalletMain.class.getName());

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("Starting the correction process");
		long startTime = System.currentTimeMillis();
		
		logger.info("Start time: "+startTime);
		final ReadProperties readProps = new ReadProperties();
		try {
			logger.info("Loading properties");
			readProps.loadInputProperties();
		} catch (FileNotFoundException e) {
			logger.error("Error loading properties", e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Error loading properties", e);
			e.printStackTrace();
		}		

		//defaulting the spName to the reporting SP
		String spName = "sp_fix_available_wallet_rpt";
		
		//File name of the report
		String fileName = "Available_Bal-Fix_Report_"+spName+"_"+DBConstants.BUYER_ID+".xls";
		
		//getting args from command line
		if(args.length == 1){
			spName = args[0];
			fileName = "Available_Bal-Fix_Report_"+spName+"_"+DBConstants.BUYER_ID+".xls";
			
			logger.info("Name of the SP: "+spName);

			DBAccess dba = new DBAccess();
		
			//Fetch the list of SOs that need to be corrected
			List<SoToBeCorrectedVO> soToBeCorrectedList = null;
			try {
				soToBeCorrectedList = getSoToBeCorrectedList(dba, DBConstants.BUYER_ID);
			} catch (Exception e1) {
				logger.error("Error fetching SOs: ", e1);
				e1.printStackTrace();
			}
			int count = 0;
			//Print the SO project balance and the SO cost
			for(SoToBeCorrectedVO soToBeCorrectedVO: soToBeCorrectedList){
				count++;
				logger.info("SO Id: "+count+": "+soToBeCorrectedVO.getSoId());
				//Invoke the stored procedure to correct the data
				applyDataFixForWallet(soToBeCorrectedVO, dba, spName);
			}

			//Write the details per SO per state into a CSV
			long endTime = System.currentTimeMillis();
			logger.info("End time: "+endTime);
			long diff = (endTime - startTime);
			logger.info(""+String.valueOf(diff));
			if(soToBeCorrectedList.size() > 0){
				//getting the dir from the properties file
				File soDetailsReport = new File(FileConstants.REPORT_DIR+fileName);
				FileOutputStream outFinal = null;
				try {
					logger.info("File is created at the location mentioned in the properties file.");
					outFinal = new FileOutputStream(soDetailsReport);
				} catch (FileNotFoundException e1) {
					logger.error("Error in creating the file.",e1);
					e1.printStackTrace();
				}
				try {
					//Writing to excel the SO details after correction
					writeToExcel(soToBeCorrectedList, outFinal);
				} catch (WriteException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else{
			logger.info("Incorrect number of arguments ");
		}
	}

	private static void writeToExcel(
			List<SoToBeCorrectedVO> soToBeCorrectedList,
			FileOutputStream outFinal) throws IOException, WriteException {
		logger.info("Creating the workbook");
 	   WritableWorkbook workbook = Workbook.createWorkbook(outFinal);
       WritableSheet sheet = workbook.createSheet("Wallet Sheet", 0);
       sheet.setColumnView(0,30);
       sheet.setColumnView(1,30);
       sheet.setColumnView(2,30);
       sheet.setColumnView(3,30);
       sheet.setColumnView(4,30);
       sheet.setColumnView(5,30);
       sheet.setColumnView(6,30);
       sheet.setColumnView(7,30);
       sheet.setColumnView(8,30);
       sheet.setColumnView(9,30);
       sheet.setColumnView(10,30);

       
       // Create a cell format for Arial 10 point font 
       WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD); 
       WritableCellFormat arial10format = new WritableCellFormat (arial10font);	
       arial10format.setAlignment(Alignment.CENTRE);
       arial10format.setBackground(Colour.ICE_BLUE);
       arial10format.setBorder(Border.ALL,BorderLineStyle.THIN);
       WritableCellFormat cellformat = new WritableCellFormat ();
       cellformat.setAlignment(Alignment.RIGHT);
       
		Label soNo = new Label(0, 0, "Service Order #",arial10format);
		Label buyer = new Label(1, 0, "Buyer Id",arial10format);
		Label fundingType = new Label(2, 0, "Funding Type",arial10format);
		Label wfState = new Label(3, 0, "State",arial10format);
		Label availableBalOldL = new Label(4, 0, "Old Available Balance",arial10format);
		Label availableBalNewL = new Label(5, 0, "New Available Balance",arial10format);
		
		/*Label addonCost = new Label(5, 0, "Addon Cost",arial10format);
		Label partCost = new Label(6, 0, "Part Cost",arial10format);
		Label oldSOBal = new Label(7, 0, "Old SO Balance",arial10format);
		Label incSpendLimit = new Label(8, 0, "Inc Spend Limit",arial10format);
		Label decSpendLimit = new Label(9, 0, "Dec Spend Limit",arial10format);
		Label newSOBal = new Label(10, 0, "New SO Balance",arial10format);*/
		
		sheet.addCell(soNo);
		sheet.addCell(buyer);
		sheet.addCell(fundingType);
		sheet.addCell(wfState);
		sheet.addCell(availableBalOldL);//so cost
		sheet.addCell(availableBalNewL);
		/*sheet.addCell(partCost);
		sheet.addCell(oldSOBal);
		sheet.addCell(incSpendLimit);
		sheet.addCell(decSpendLimit);
		sheet.addCell(newSOBal);*/
		logger.info("Added the Column labels to the workbook");
		logger.info("Writing row level data to the workbook");
		for (int i=0; i < soToBeCorrectedList.size(); i++){
			Label soId = new Label(0, (1+i), soToBeCorrectedList.get(i).getSoId(),cellformat);
			Label buyerId = new Label(1, (1+i), soToBeCorrectedList.get(i).getBuyerId(),cellformat);
	    	Label state = new Label(3, (1+i), soToBeCorrectedList.get(i).getWfState(),cellformat);
	    	Label fundingTypeId = new Label(2, (1+i), soToBeCorrectedList.get(i).getFundingTypeId(),cellformat);
	    	Number availableBalOld = new Number(4, (1+i), soToBeCorrectedList.get(i).getAvailableBalanceOld(),cellformat);
	    	Number availableBalNew = new Number(5, (1+i), soToBeCorrectedList.get(i).getAvailableBalanceNew(),cellformat);
	    	
	    	/*Number soCost = new Number(4, (1+i), soToBeCorrectedList.get(i).getSoCost(),cellformat);
	    	Number soAddonCost = new Number(5, (1+i), soToBeCorrectedList.get(i).getSoAddonCost(),cellformat);
	    	Number soPartCost = new Number(6, (1+i), soToBeCorrectedList.get(i).getSoPartCost(),cellformat);
	    	Number projBal = new Number(7, (1+i), soToBeCorrectedList.get(i).getSoProjBal(),cellformat);
	    	Number soIncSpendLimit = new Number(8, (1+i), soToBeCorrectedList.get(i).getSoIncSpendLimit(),cellformat);
	    	Number soDecSpendLimit = new Number(9, (1+i), soToBeCorrectedList.get(i).getSoDecSpendLimit(),cellformat);
	    	Number projBalCorrected = new Number(10, (1+i), soToBeCorrectedList.get(i).getSoProjBalCorrected(),cellformat);*/
	    	
	    	sheet.addCell(soId);
	    	sheet.addCell(buyerId);
	    	sheet.addCell(fundingTypeId);
	    	sheet.addCell(state);
	    	sheet.addCell(availableBalOld);
	    	sheet.addCell(availableBalNew);
	    	/*sheet.addCell(soCost);
	    	sheet.addCell(soAddonCost);
	    	sheet.addCell(soPartCost);
	    	sheet.addCell(projBal);
	    	sheet.addCell(soIncSpendLimit);
	    	sheet.addCell(soDecSpendLimit);
	    	sheet.addCell(projBalCorrected);*/
		}		
		
		workbook.write();
		workbook.close();
		outFinal.close();
		logger.info("Closing the workbook");
	}

	private static void applyDataFixForWallet(
			SoToBeCorrectedVO soToBeCorrectedVO, DBAccess dba, String spName) {
		try {
			dba.applyDataFixForWallet(soToBeCorrectedVO, dba, spName);
		} catch (SQLException e) {
			logger.error("Error while executing SP for: "+soToBeCorrectedVO.getSoId());
			logger.error("Error while executing: ", e);
		} catch (Exception e) {
			logger.error("Error while executing SP for: "+soToBeCorrectedVO.getSoId());
			logger.error("Error while executing: ", e);
			e.printStackTrace();
		}
		
	}

/*	private static void fetchSOProjBalAndCost(
			SoToBeCorrectedVO soToBeCorrectedVO, boolean isCorrected, DBAccess dba) {
		//DBAccess dba = new DBAccess();
		try {
			dba.fetchSOProjBalAndCost(soToBeCorrectedVO, isCorrected);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/

	private static List<SoToBeCorrectedVO> getSoToBeCorrectedList(DBAccess dba, String buyerId) throws Exception {
		//DBAccess dba = new DBAccess();
		List<SoToBeCorrectedVO> soToBeCorrectedList = null;
		try {
			soToBeCorrectedList = dba.getSoToBeCorrectedList(buyerId);
			logger.info("soToBeCorrectedList size: "+soToBeCorrectedList.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return soToBeCorrectedList;
	}

}
