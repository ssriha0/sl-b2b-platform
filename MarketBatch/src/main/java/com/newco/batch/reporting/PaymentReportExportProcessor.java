package com.newco.batch.reporting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.financeManager.FinanceManagerBO;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.dto.vo.financemanger.AdminPaymentVO;
import com.newco.marketplace.dto.vo.financemanger.BuyerSOReportVO;
import com.newco.marketplace.dto.vo.financemanger.DateComparator;
import com.newco.marketplace.dto.vo.financemanger.FMReportVO;
import com.newco.marketplace.dto.vo.financemanger.FMW9ProfileVO;
import com.newco.marketplace.dto.vo.financemanger.NameComparator;
import com.newco.marketplace.dto.vo.financemanger.ProviderSOReportVO;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.BaseFileUtil;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.DateUtils;

import com.newco.marketplace.exception.BusinessServiceException;
import com.servicelive.common.properties.IApplicationProperties;

public class PaymentReportExportProcessor extends BaseFileUtil {

	private FinanceManagerBO financeManagerBO;
	private  Cryptography cryptography;
	private IApplicationProperties applicationProperties;
	private static final Logger LOGGER = Logger.getLogger(PaymentReportExportProcessor.class.getName());
	/**
	 * Method which handles the scheduled report generation
	 * 
	 * */
	//TODO : 
	public void execute() throws BusinessServiceException {
		List <FMReportVO> listReportVO = null;
		//Fetching the properties required for generating the reports
		//String fileDir = PropertiesUtils.getPropertyValue(MPConstants.PAYMENT_REPORTS_DIRECTORY);
		//String fileRemovalThreshold = PropertiesUtils.getPropertyValue(MPConstants.FILE_REMOVAL_THRESHOLD);
		//Integer numberOfReportsToBeProcessed = Integer.valueOf(PropertiesUtils.getPropertyValue(MPConstants.NUMBER_REPORTS_TO_BE_PROCESSED));
		String fileDir = "";
		int fileRemovalThresholdVal = MPConstants.FILE_REMOVAL_THRESHOLD_VALUE;
//		if(!StringUtils.isEmpty(fileRemovalThreshold) && StringUtils.isNumeric(fileRemovalThreshold)){
//			fileRemovalThresholdVal = Integer.parseInt(fileRemovalThreshold);
//		}
		Integer numberOfReportsToBeProcessed = new Integer(0);
		Integer maxTimeIntervalForBatchInSec = MPConstants.TIME_INTERVAL_FOR_BATCH_SEC; 
		//Dynamically fetching the properties required for generating the reports
		 try{
			 //File directory where the generated CSV file will be stored.
			 fileDir = applicationProperties.getPropertyFromDB(MPConstants.PAYMENT_REPORTS_DIRECTORY);
			 //Number of days to consider for deleting reports 
			 fileRemovalThresholdVal = Integer.valueOf(applicationProperties.getPropertyFromDB(MPConstants.FILE_REMOVAL_THRESHOLD));
			 //Number of reports to be generated in each batch thread.
			 numberOfReportsToBeProcessed = Integer.valueOf(applicationProperties.getPropertyFromDB(MPConstants.NUMBER_REPORTS_TO_BE_PROCESSED));
			 //Time in seconds, to be checked to make 'In Process' report to 'Pending' 
			 maxTimeIntervalForBatchInSec = Integer.valueOf(applicationProperties.getPropertyFromDB(MPConstants.MAX_TIME_INTERVAL_FOR_BATCH_SEC));
         }catch(Exception e){
             LOGGER.error("Failed to fetch value from app properties tables. ", e);
         }
		 LOGGER.info("PaymentReportExportProcessor --> Inside execute");
		
		/*
		 * Fetching reports with status as Completed and Failed to delete if they are older than one week.
		 */
		try{
			listReportVO = financeManagerBO.getCompletedAndFailedRecords();
		}catch(Exception e){
			LOGGER.error(this.getClass().getName()+" :- "+e.getMessage());
		}		
		if(null != listReportVO){
			List <Integer> reportIds = new ArrayList<Integer>();
			Iterator <FMReportVO> reportIt = listReportVO.iterator();	
			while(reportIt.hasNext()){
				FMReportVO report = reportIt.next();
				//updating status as deleted and delete the file from disc if report request is older then 7 days
				if((null != report.getReportRequestedDate()) && DateUtils.getExactDaysBetweenDates(report.getReportRequestedDate(), new Date())>fileRemovalThresholdVal){
					reportIds.add(report.getReportId());
					if(null != report.getFilePath()){
						try{
							deleteFile(report.getFilePath());
						}catch(Exception e){
							LOGGER.error("Error deleting file in path: "+report.getFilePath()+" - "+e.getMessage());
						}
					}
				}			
			}
			if(reportIds.size()>0){
				try{
					financeManagerBO.updateDeletedStatus(reportIds);
				}catch(Exception e){
					LOGGER.error(this.getClass().getName()+" :- "+e.getMessage());
				}				
			}			
		}
		/**End of deleting older reports**/
		
		/*
		 * Fetching reports with status as Queued to generate report
		 * 
		 */
		try{
			financeManagerBO.checkAndUpdateInProcessReport(maxTimeIntervalForBatchInSec);
			listReportVO = financeManagerBO.getQueuedRecords(numberOfReportsToBeProcessed);			
		}catch(Exception e){
			LOGGER.error(this.getClass().getName()+" :- "+e.getMessage());
		}	
		/*
		 * Update status as In Process and process_strat_time
		 */
		if(null != listReportVO){
			for(FMReportVO reportVO : listReportVO){
				reportVO.setReportStatus(OrderConstants.REPORT_PROCESSING);
				int numberOfAttempt = 0;
				if(null != reportVO.getNumberOfAttepmts()){
					numberOfAttempt = reportVO.getNumberOfAttepmts().intValue();
				}else{
					numberOfAttempt = 0;
				}	
				++numberOfAttempt;
				reportVO.setNumberOfAttepmts(numberOfAttempt);				
			}			
			try{
				financeManagerBO.updateAsInProcess(listReportVO);
			}catch(Exception e){
				LOGGER.error(this.getClass().getName()+" :- "+e.getMessage());
			}
			
			/* Generate report for each request one by one based on the criteria */
			Iterator <FMReportVO> reportIt = listReportVO.iterator();
			while(reportIt.hasNext()){
				String fileName = "";
				int totalRecordCount = 0;
				FMReportVO report = reportIt.next();				
				report.setExport(true);
				int role = report.getRole().intValue();
				String reportName = report.getReportName();
				//set reoleType as buyer if role is 3, and provider when 1 etc..
				report.setRoleType(getRoleTypeFromRoleId(role));
				if(!StringUtils.isEmpty(report.getBuyers())){
					report.setBuyerList(convertCommaSepStrToList(report.getBuyers()));
				}
				if(!StringUtils.isEmpty(report.getProviders())){
					report.setProviderList(convertCommaSepStrToList(report.getProviders()));
				}

				report.setReportForSpecificBuyers(!report.isReportForAllBuyers());
				report.setReportByCompletedDate(!report.isReportByPaymentDate());
				report.setReportForSpecificProviders(!report.isReportForAllProviders());
				
				int numberOfAttempt = 0;
				if(null != report.getNumberOfAttepmts()){
					numberOfAttempt = report.getNumberOfAttepmts().intValue();
				}else{
					numberOfAttempt = 1;
					report.setNumberOfAttepmts(numberOfAttempt); //To handle possible data errors
				}
				if(OrderConstants.PROVIDER_ROLEID == role || OrderConstants.NEWCO_ADMIN_ROLEID == role){
					if(OrderConstants.PROVIDER_SO_REPORT.equalsIgnoreCase(reportName)){
						List<ProviderSOReportVO> listProviderSOReport = null;
						try{
							listProviderSOReport = financeManagerBO.getProviderPaymentByServiceOrder(report);
							if(null != listProviderSOReport){
								totalRecordCount = listProviderSOReport.size();
							}	
							fileName = writeToCsvProviderSo(listProviderSOReport, report);
							report.setReportStatus(OrderConstants.REPORT_COMPLETED);
						}catch (Exception e) {
							LOGGER.error(e);
							report.setException(e.toString());
							report.setReportStatus(OrderConstants.REPORT_FAILED);
						}
					}
					else if (OrderConstants.PROVIDER_REV_REPORT.equalsIgnoreCase(reportName)) {
						List<ProviderSOReportVO> listProviderNetSummary = null;
						try{
							listProviderNetSummary = financeManagerBO.getProviderNetSummaryReport(report);
							if(null != listProviderNetSummary){
								totalRecordCount = listProviderNetSummary.size();
							}
							fileName = writeToCsvProviderNetSummary(listProviderNetSummary, report);
							report.setReportStatus(OrderConstants.REPORT_COMPLETED);
						}catch (Exception e) {
							LOGGER.error(e);
							report.setException(e.toString());
							report.setReportStatus(OrderConstants.REPORT_FAILED);
						}
					}
				}
				if(OrderConstants.BUYER_ROLEID == role || OrderConstants.NEWCO_ADMIN_ROLEID == role){
					if (OrderConstants.BUYER_SO_REPORT.equalsIgnoreCase(reportName)) {
						try{
							fileName = writeToCsvBuyerSoReport(null, report);
							totalRecordCount=report.getTotalRecords();
							report.setReportStatus(OrderConstants.REPORT_COMPLETED);
						}catch (Exception e) {
							LOGGER.error(e);
							report.setException(e.toString());
							report.setReportStatus(OrderConstants.REPORT_FAILED);
						}
					}
					else if (OrderConstants.BUYER_TAXID_REPORT.equalsIgnoreCase(reportName)) {
						List<BuyerSOReportVO> listBuyerSOReportVO = null;
						try{	
							fileName = writeToCsvBuyerTaxPayerID(listBuyerSOReportVO, report);
							totalRecordCount=report.getTotalRecords();
							report.setReportStatus(OrderConstants.REPORT_COMPLETED);
						}catch (Exception e) {
							LOGGER.error(e);
							report.setException(e.toString());
							report.setReportStatus(OrderConstants.REPORT_FAILED);
						}
					}
				}
				if(OrderConstants.NEWCO_ADMIN_ROLEID == role && OrderConstants.ADMIN_PAYMENT_REPORT.equalsIgnoreCase(reportName)){
					List<AdminPaymentVO> adminPaymentVOs = null;
					try{
						adminPaymentVOs = financeManagerBO.getAdminPaymentReport(report);
						if(null != adminPaymentVOs){							
							totalRecordCount = adminPaymentVOs.size();
						}
						fileName = writeToCsvAdminPayment(adminPaymentVOs, report);
						report.setReportStatus(OrderConstants.REPORT_COMPLETED);
						//report.setFilePath(fileDir+fileName);
					}catch (Exception e) {
						LOGGER.error(e);
						report.setException(e.toString());
						report.setReportStatus(OrderConstants.REPORT_FAILED);
					}										
				}	
				fileName = report.getFilePath();
				if(StringUtils.isEmpty(fileName) ){
					LOGGER.info("File name empty..");
					report.setReportStatus(OrderConstants.REPORT_FAILED);
				}else{
					//Set relative file path
					report.setFilePath(fileDir+fileName);
					report.setReportStatus(OrderConstants.REPORT_COMPLETED);
				}
				//Update status as pending when number of attempt is <= 2 else status will be failed
				if(OrderConstants.REPORT_FAILED.equalsIgnoreCase(report.getReportStatus())&& OrderConstants.MAX_ATTEMPT_BEFORE_FAIL.intValue() > numberOfAttempt){
					report.setReportStatus(OrderConstants.REPORT_QUEUED);
				}
				report.setReportGeneratedDate(new Date());
				report.setTotalRecords(totalRecordCount);
				
				/* Update report details such as report count, new status, file name....etc  */
				try{
					financeManagerBO.updateExportDetails(report);
				}catch (Exception e) {
					LOGGER.error(this.getClass().getName()+":- Failed to update Export details."+e.getMessage());
				}				
			}
			/*End of Report generation*/
		}		
	}

	/**
	 * @param qualifiedFileName : file name to be deleted
	 * @return true if the file deleted successfully.
	 * This method will check whether the file exists or not.
	 * */
	private boolean deleteFile(String qualifiedFileName) throws Exception{
		File fileToDelete = null;
		boolean success = false;
		try{
			fileToDelete = new File(qualifiedFileName);
			if(fileToDelete.exists()){
				success = fileToDelete.delete();
				if(success){
					LOGGER.info(qualifiedFileName+ "deleted.");
				}else{
					LOGGER.info(qualifiedFileName+ "not deleted.");
				}
			}else{
				LOGGER.info(qualifiedFileName+ "doesnt exists.");
			}
		}catch (Exception e) {
			LOGGER.error(this.getClass().getName()+" :- deleteFile() :- "+e.getMessage());
		}
		return success;
	}

	private String writeToCsvProviderNetSummary(
			List<ProviderSOReportVO> listProviderNetSummary, FMReportVO reportVO) throws Exception {
		File outputFile = null; 
		FileWriter writer = null;
		PrintWriter pWriter = null;
		NumberFormat formatter = new DecimalFormat(OrderConstants.PRICE_FORMAT_CSV);
		try{
			outputFile = createFile(reportVO.getReportNameForExport());		
			reportVO.setFilePath(null!=outputFile?outputFile.getName():"");
			writer = new FileWriter(outputFile);		
			pWriter = new PrintWriter(new BufferedWriter(writer,8192));		
			Iterator<ProviderSOReportVO> providerIterator = listProviderNetSummary.iterator();
			List<String> headerList = new ArrayList<String>();
			List<String> footerList = new ArrayList<String>();
			BigDecimal totalFinalPriceAll = BigDecimal.ZERO;
			BigDecimal totalServiceLiveFee = BigDecimal.ZERO;
			BigDecimal totalNetPayment = BigDecimal.ZERO;
			if(reportVO.getRole().intValue() == OrderConstants.NEWCO_ADMIN_ROLEID){
				headerList.addAll(convertCommaSepStrToList(OrderConstants.PROVIDER_REV_REPORT_HDR_ADMN));
				footerList.add("");
				footerList.add("");
			}else{
				headerList.addAll(convertCommaSepStrToList(OrderConstants.PROVIDER_REV_REPORT_HDR));
			}		
			for(String header : headerList){
				pWriter.write(header ==null?"": header.toString());
				pWriter.append(',');
			}
			pWriter.append('\n');
			if(null == listProviderNetSummary || listProviderNetSummary.size() ==0){
				LOGGER.info("No records found.");
				throw new Exception("No records found.");
			}
			long recordCount = 0L;
			while(providerIterator.hasNext()){				
				ProviderSOReportVO providerNetSummarVO =  providerIterator.next();				
				if(reportVO.getRole().intValue() == OrderConstants.NEWCO_ADMIN_ROLEID){
					if(providerNetSummarVO.getProviderFirmId()!=null){
						formatAndAppend(providerNetSummarVO.getProviderFirmId().toString() ,pWriter);
					}
					formatAndAppend(providerNetSummarVO.getProviderFirmName() ,pWriter);
				}				
				formatAndAppend(providerNetSummarVO.getBuyerId(),pWriter);
				formatAndAppend(providerNetSummarVO.getBuyerName(),pWriter);
				formatAndAppend(formatter.format(providerNetSummarVO.getTotalFinalPrice()),pWriter);
				formatAndAppend(formatter.format(providerNetSummarVO.getServiceLiveFee()),pWriter);
				formatAndAppend(formatter.format(providerNetSummarVO.getNetPayment()),pWriter);

				totalFinalPriceAll=totalFinalPriceAll.add(providerNetSummarVO.getTotalFinalPrice());
				totalServiceLiveFee=totalServiceLiveFee.add(providerNetSummarVO.getServiceLiveFee());
				totalNetPayment=totalNetPayment.add(providerNetSummarVO.getNetPayment());
				pWriter.append("\n");
				++recordCount;
				if(recordCount%100 == 0){
					pWriter.flush();
				}
			}
			footerList.add("");
			footerList.add("");
			footerList.add(formatter.format(totalFinalPriceAll));
			footerList.add(formatter.format(totalServiceLiveFee));
			footerList.add(formatter.format(totalNetPayment));
			StringBuilder strFooter = new StringBuilder(totalFinalPriceAll.toString()).append(",").append(totalServiceLiveFee.toString()).append(",");
			strFooter.append(totalNetPayment.toString());
			for(String footer : footerList){
				pWriter.write(footer ==null?"": footer.toString());
				pWriter.append(',');
			}
			pWriter.append("\n");
			pWriter.append(OrderConstants.REPORT_FOOTER_INFO);
			reportVO.setReportFooter(strFooter.toString());
			
		}catch(IOException ioe){
			reportVO.setException(ioe.toString());
			LOGGER.error(ioe);
			throw(ioe);
		}catch (Exception e) {
			reportVO.setException(e.toString());
			LOGGER.error(e);
			throw(e);
		}finally{
			try{
				if(null != pWriter){
					pWriter.flush();
					pWriter.close();
				}
				if(null != writer){
					writer.close();
				}
			}catch (Exception e) {
				LOGGER.error(e);
			}			
		}
		return outputFile==null?null:outputFile.getName();
	}

	private String writeToCsvBuyerSoReport(
			List<BuyerSOReportVO> listBuyerSO, FMReportVO reportVO) throws Exception {
		File outputFile = null; 
		FileWriter writer = null;
		PrintWriter pWriter = null;
		NumberFormat formatter = new DecimalFormat(OrderConstants.PRICE_FORMAT_CSV);
		boolean flagNoRecord = false;
		try{
			outputFile = createFile(reportVO.getReportNameForExport());	
			reportVO.setFilePath(null!=outputFile?outputFile.getName():"");
			writer = new FileWriter(outputFile);		
			pWriter = new PrintWriter(new BufferedWriter(writer,8192));

			int buyerSoReportCount=financeManagerBO.getBuyerSOReportCount(reportVO);
			int averageCount=(int) Math.ceil((float)buyerSoReportCount/OrderConstants.REPORT_BUYER_SO_COUNT);
			int totalRecordCount=0;

			BigDecimal totalGrossPayment = BigDecimal.ZERO;
			BigDecimal totalBuyerPostingFee = BigDecimal.ZERO;
			BigDecimal totalProviderFee = BigDecimal.ZERO;
			List<String> headerList = new ArrayList<String>();
			List<String> footerList = new ArrayList<String>();
			List<FMW9ProfileVO>  profileList =null;
			List<FMW9ProfileVO>  profileListAddress =null;

			if(reportVO.getRole().intValue() == OrderConstants.NEWCO_ADMIN_ROLEID){
				headerList.addAll(convertCommaSepStrToList(OrderConstants.BUYER_SO_REPORT_HDR_ADMN));
				footerList.add("");
				footerList.add("");
			}else{
				headerList.addAll(convertCommaSepStrToList(OrderConstants.BUYER_SO_REPORT_HDR));
			}
			for(String header : headerList){
				pWriter.write(header ==null?"": header.toString());
				pWriter.append(',');
			}
			pWriter.append('\n');
			if(averageCount==0)
			{
				flagNoRecord = true;
				LOGGER.info("No records found.");
				throw new Exception("No records found.");
			}
			profileList=financeManagerBO.getFMW9History(reportVO);
			profileListAddress=financeManagerBO.getFMW9HistoryForAddress(reportVO);
			Map<Integer,List<FMW9ProfileVO>> mapOfProfiles = financeManagerBO.convertProfileToMap(profileList);
			Map<Integer,List<FMW9ProfileVO>> mapOfProfilesAddress = financeManagerBO.convertProfileToMap(profileListAddress);

			for(int count=1;count<=averageCount;count++)
			{
				int startIndex=(count-1)*OrderConstants.REPORT_BUYER_SO_COUNT;
				int numberOfRecords=OrderConstants.REPORT_BUYER_SO_COUNT;
				List<BuyerSOReportVO> listBuyerSOReportVO = null;
				reportVO.setExport(false);
				reportVO.setStartIndex(startIndex);
				reportVO.setNumberOfRecords(numberOfRecords);
				listBuyerSOReportVO = financeManagerBO.getBuyerPaymentByServiceOrderReport(reportVO,mapOfProfiles,mapOfProfilesAddress);	
				if(null!=listBuyerSOReportVO)
				{
					totalRecordCount=totalRecordCount+listBuyerSOReportVO.size();
				}
				
				Iterator<BuyerSOReportVO> buyerSOIterator = listBuyerSOReportVO.iterator();
				long recordCount = 0L;
				while(buyerSOIterator.hasNext()){
					BuyerSOReportVO buyerSOReportVO =  buyerSOIterator.next();
					int taxPayerType=0;
					
					if (2 == buyerSOReportVO.getTaxPayerTypeId()) {
						buyerSOReportVO.setTinType("SSN");
					} else if (1 == buyerSOReportVO.getTaxPayerTypeId()) {
						buyerSOReportVO.setTinType("EIN");
					} else {//Providers do not have alternate taxpayer id
						buyerSOReportVO.setTinType(" - ");
					}
					if(reportVO.getRole().intValue() == OrderConstants.NEWCO_ADMIN_ROLEID){
						formatAndAppend(buyerSOReportVO.getBuyerId(), pWriter);
						formatAndAppend(buyerSOReportVO.getBuyerName(), pWriter);
					}
					formatAndAppend(String.valueOf(buyerSOReportVO.getProviderFirmId()),pWriter);
					formatAndAppend(buyerSOReportVO.getProviderFirmName(),pWriter);
					formatAndAppend(buyerSOReportVO.getTaxPayerType(),pWriter);
					formatAndAppend(buyerSOReportVO.getExempt(),pWriter);
					formatAndAppend(buyerSOReportVO.getTinType(),pWriter);
					String taxPayerId = getDecryptedValue(buyerSOReportVO.getEncrypedTaxPayerId(), taxPayerType);
					formatAndAppend(formatTaxPayerId(taxPayerId,buyerSOReportVO.getTinType()),pWriter);
					formatAndAppend(formatDate(buyerSOReportVO.getPaymentDate(),"MM/dd/yyyy"),pWriter);
					formatAndAppend(buyerSOReportVO.getServiceOrderId(),pWriter);		        
					if (buyerSOReportVO.getTotalGrossPayment() != null) {
						formatAndAppend(formatter.format(buyerSOReportVO.getTotalGrossPayment()),pWriter);
						totalGrossPayment = totalGrossPayment.add(buyerSOReportVO.getTotalGrossPayment());
					} else {
						formatAndAppend("$0.00",pWriter);
					}
					/*if (buyerSOReportVO.getBuyerPostingFee() != null) {
						formatAndAppend(formatter.format(buyerSOReportVO.getBuyerPostingFee()), pWriter);
						totalBuyerPostingFee = totalBuyerPostingFee.add(buyerSOReportVO.getBuyerPostingFee());
					} else {
						formatAndAppend("$0.00",pWriter);
					}*/
					// Buyer_payment_by_so report generation related changes
					if (buyerSOReportVO.getPostingFee() != null) {
						formatAndAppend(formatter.format(buyerSOReportVO.getPostingFee()), pWriter);
						totalBuyerPostingFee = totalBuyerPostingFee.add(BigDecimal.valueOf(buyerSOReportVO.getPostingFee()));
					} else {
						formatAndAppend("$0.00",pWriter);
					}
					
					if (buyerSOReportVO.getProviderServiceLiveFee() != null) {
						formatAndAppend(formatter.format(buyerSOReportVO.getProviderServiceLiveFee()),pWriter);
						totalProviderFee = totalProviderFee.add(buyerSOReportVO.getProviderServiceLiveFee());
					} else {
						formatAndAppend("$0.00",pWriter);
					}
					pWriter.append("\n");
					++recordCount;
					if(recordCount%100 == 0){
						pWriter.flush();
					}
				}
				if(count==averageCount)
				{
					footerList.add("");
					footerList.add("");
					footerList.add("");
					footerList.add("");
					footerList.add("");
					footerList.add("");
					footerList.add("");
					footerList.add("");
					footerList.add(formatter.format(totalGrossPayment));
					footerList.add(formatter.format(totalBuyerPostingFee));
					footerList.add(formatter.format(totalProviderFee));
					StringBuilder strFooter = new StringBuilder(totalGrossPayment.toString()).append(",").append(totalBuyerPostingFee.toString()).append(",");
					strFooter.append(totalProviderFee.toString());
					for(String footer : footerList){
						pWriter.write(footer ==null?"": footer.toString());
						pWriter.append(',');
					}
					pWriter.append("\n");
					pWriter.append(OrderConstants.REPORT_FOOTER_INFO);
					reportVO.setReportFooter(strFooter.toString());
				}
			}			
			reportVO.setTotalRecords(totalRecordCount);

		}catch(IOException ioe){
			reportVO.setException(ioe.toString());
			LOGGER.error(ioe);
			throw(ioe);
		}catch (Exception e) {
			reportVO.setException(e.toString());
			LOGGER.error(e);
			throw(e);
		}
		finally{
			try{
				if(null != pWriter){
					pWriter.flush();
					pWriter.close();
				}
				if(null != writer){
					writer.close();
				}
				if(!flagNoRecord && !StringUtils.isEmpty(reportVO.getException()) && !StringUtils.isEmpty(reportVO.getFilePath())){
					deleteFile(reportVO.getFilePath());
					reportVO.setFilePath("");				
				}
			}catch (Exception e) {
				LOGGER.error(e);
			}			
		}
		return outputFile==null?null:outputFile.getName();
	}

	private String writeToCsvBuyerTaxPayerID(
			List<BuyerSOReportVO> listBuyerSO, FMReportVO reportVO) throws Exception {
		File outputFile = null; 
		FileWriter writer = null;
		PrintWriter pWriter = null;
		NumberFormat formatter = new DecimalFormat(OrderConstants.PRICE_FORMAT_CSV);
		boolean flagNoRecord = false;
		try{		
			List<FMW9ProfileVO>  profileList =null;
			List<FMW9ProfileVO>  profileListWithAddress =null;

			int buyerSoReportCount=financeManagerBO.getBuyerSOReportCount(reportVO); 
			int averageCount=(int) Math.ceil((float)buyerSoReportCount/OrderConstants.REPORT_BUYER_SO_COUNT);			
			outputFile = createFile(reportVO.getReportNameForExport());	
			reportVO.setFilePath(null!=outputFile?outputFile.getName():"");
			writer = new FileWriter(outputFile);		
			pWriter = new PrintWriter(new BufferedWriter(writer,8192));		
			List<String> headerList = new ArrayList<String>();
			List<String> footerList = new ArrayList<String>();

			BigDecimal totalGrossLabor = BigDecimal.ZERO;
			if(reportVO.getRole().intValue() == OrderConstants.NEWCO_ADMIN_ROLEID){
				headerList.addAll(convertCommaSepStrToList(OrderConstants.BUYER_TAXID_REPORT_HDR_ADMN));
				footerList.add("");
				footerList.add("");
			}else{
				headerList.addAll(convertCommaSepStrToList(OrderConstants.BUYER_TAXID_REPORT_HDR));
			}			
			for(String header : headerList){
				pWriter.write(header ==null?"": header.toString());
				pWriter.append(',');
			}
			pWriter.append('\n');
			if(averageCount==0)
			{
				flagNoRecord = true;
				throw new Exception("No records found.");
			}	
			profileList=financeManagerBO.getFMW9History(reportVO);
			LOGGER.info("inside writeToCsvBuyerTaxPayerIDmethod");
			profileListWithAddress=financeManagerBO.getFMW9HistoryForAddress(reportVO);
			Map<Integer,List<FMW9ProfileVO>> mapOfProfilesAddress = financeManagerBO.convertProfileToMap(profileListWithAddress);
			Map<Integer,List<FMW9ProfileVO>> mapOfProfiles = financeManagerBO.convertProfileToMap(profileList);
			Map<String, BuyerSOReportVO> mapOfBuyerTaxPayerIds=new HashMap<String, BuyerSOReportVO>();
			for(int count=1;count<=averageCount;count++)
			{
				int startIndex=(count-1)*OrderConstants.REPORT_BUYER_SO_COUNT;
				int numberOfRecords=OrderConstants.REPORT_BUYER_SO_COUNT;
				
				List<BuyerSOReportVO> listBuyerSOReportVO = null;
				reportVO.setExport(false);
				reportVO.setStartIndex(startIndex);
				reportVO.setNumberOfRecords(numberOfRecords);
				LOGGER.info("before financeManagerBO.getBuyerPaymentByTaxPayerIdReport(r");

				mapOfBuyerTaxPayerIds = financeManagerBO.getBuyerPaymentByTaxPayerIdReport(reportVO,mapOfProfiles,mapOfProfilesAddress,mapOfBuyerTaxPayerIds);	
			}
			//Iterator<BuyerSOReportVO> buyerSOIterator = new ArrayList(mapOfBuyerTaxPayerIds.values()).listIterator();
			List<BuyerSOReportVO> listBuyerSOReportVO = new ArrayList<BuyerSOReportVO>(mapOfBuyerTaxPayerIds.values());
			ComparatorChain chain = new ComparatorChain();
			chain.addComparator(new NameComparator());
			chain.addComparator(new DateComparator());
			Collections.sort(listBuyerSOReportVO, chain);
			Iterator<BuyerSOReportVO> buyerSOIterator = listBuyerSOReportVO.listIterator();
			long recordCount = 0L;
			int recCount = mapOfBuyerTaxPayerIds.values().size();
			while(buyerSOIterator.hasNext()){
				BuyerSOReportVO buyerSOReportVO =  buyerSOIterator.next();
				int taxPayerType = 0;				
				if (2 == buyerSOReportVO.getTaxPayerTypeId()) {
					buyerSOReportVO.setTinType("SSN");
				} else if (1 == buyerSOReportVO.getTaxPayerTypeId()) {
					buyerSOReportVO.setTinType("EIN");
				} else {//Providers do not have alternate taxpayer id
					buyerSOReportVO.setTinType(" - ");
				}
				if(reportVO.getRole().intValue() == OrderConstants.NEWCO_ADMIN_ROLEID){
					if(StringUtils.isEmpty( buyerSOReportVO.getBuyerId()) || "0".equalsIgnoreCase(buyerSOReportVO.getBuyerId())){
						recCount--;
						continue;
					}
					formatAndAppend(buyerSOReportVO.getBuyerId(), pWriter);
					formatAndAppend(buyerSOReportVO.getBuyerName(), pWriter);
				}
				formatAndAppend(String.valueOf(buyerSOReportVO.getProviderFirmId()),pWriter);
				formatAndAppend(buyerSOReportVO.getProviderFirmName(),pWriter);
				formatAndAppend(getNotNullValue(buyerSOReportVO.getDbaName()),pWriter);		       
				formatAndAppend(buyerSOReportVO.getTaxPayerType(),pWriter);
				formatAndAppend(buyerSOReportVO.getExempt(),pWriter);
				formatAndAppend(buyerSOReportVO.getTinType(),pWriter);	
				String taxPayerId ="";
				if(null!=buyerSOReportVO.getEncrypedTaxPayerId()){
					taxPayerId=getDecryptedValue(buyerSOReportVO.getEncrypedTaxPayerId(), taxPayerType);	
				}
				formatAndAppend(formatTaxPayerId(taxPayerId, buyerSOReportVO.getTinType()),pWriter);
				//Changes for SL-20958--START
				formatAndAppend(buyerSOReportVO.getStreet1(),pWriter);	
				//SL-20997- Capture apt information in street 2
				String aptStreet2 = getAptNoandStreet2(buyerSOReportVO.getAptNo(),buyerSOReportVO.getStreet2());
				formatAndAppend(aptStreet2,pWriter);
				formatAndAppend(buyerSOReportVO.getCity(),pWriter);
				formatAndAppend(buyerSOReportVO.getState(),pWriter);
				formatAndAppend(formatZipString(buyerSOReportVO),pWriter);
				//Changes for SL-20958--END
				if(buyerSOReportVO.getTotalGrossPayment()!=null){
					formatAndAppend(formatter.format(buyerSOReportVO.getTotalGrossPayment()),pWriter);
					totalGrossLabor=totalGrossLabor.add(buyerSOReportVO.getTotalGrossPayment());
				}
				else{
					formatAndAppend("$0.00",pWriter);
				}
				pWriter.append("\n");
				++recordCount;
				if(recordCount%100 == 0){
					pWriter.flush();
				}
			}			
			footerList.add("");
			footerList.add("");
			footerList.add("");
			footerList.add("");
			footerList.add("");
			footerList.add("");
			footerList.add("");
			footerList.add("");
			//Changes for SL-20958--START
			footerList.add("");
			footerList.add("");
			footerList.add("");
			footerList.add("");
			//Changes for SL-20958--END
			footerList.add(formatter.format(totalGrossLabor));
			for(String footer : footerList){
				pWriter.write(footer ==null?"": footer.toString());
				pWriter.append(',');
			}
			pWriter.append("\n");
			pWriter.append(OrderConstants.REPORT_FOOTER_INFO);
			reportVO.setTotalRecords(recCount);
			reportVO.setReportFooter(totalGrossLabor.toString());			
		}catch(IOException ioe){
			reportVO.setException(ioe.toString());			
			LOGGER.error(ioe);
			throw(ioe);
		}catch (Exception e) {			
			reportVO.setException(e.toString());
			LOGGER.error(e);
			throw(e);
		}finally{
			try{
				if(null != pWriter){
					pWriter.flush();
					pWriter.close();
				}
				if(null != writer){
					writer.close();
				}
				if(!flagNoRecord && !StringUtils.isEmpty(reportVO.getException()) && !StringUtils.isEmpty(reportVO.getFilePath())){
					deleteFile(reportVO.getFilePath());
					reportVO.setFilePath("");				
				}
			}catch (Exception e) {
				LOGGER.error(e);
			}			
		}
		return outputFile==null?null:outputFile.getName();
	}

    private String writeToCsvProviderSo(
			List<ProviderSOReportVO> listProviderSOReport, FMReportVO reportVO) throws Exception {
		File outputFile = null; 
		FileWriter writer = null;
		PrintWriter pWriter = null;
		NumberFormat formatter = new DecimalFormat(OrderConstants.PRICE_FORMAT_CSV);	
		try{
			outputFile = createFile(reportVO.getReportNameForExport());
			reportVO.setFilePath(null!=outputFile?outputFile.getName():"");
			writer = new FileWriter(outputFile);		
			pWriter = new PrintWriter(new BufferedWriter(writer,8192));
			Iterator<ProviderSOReportVO> providerIterator = listProviderSOReport.iterator();
			List<String> headerList = new ArrayList<String>();
			List<String> footerList = new ArrayList<String>();
			BigDecimal totalGrossLabor = BigDecimal.ZERO;
			BigDecimal totalGrossOther = BigDecimal.ZERO;
			BigDecimal totalFinalPriceAll = BigDecimal.ZERO;
			BigDecimal totalServiceLiveFee = BigDecimal.ZERO;
			BigDecimal totalNetPayment = BigDecimal.ZERO;
			if(reportVO.getRole().intValue() == OrderConstants.NEWCO_ADMIN_ROLEID){
				headerList.addAll(convertCommaSepStrToList(OrderConstants.PROVIDER_SO_REPORT_HDR_ADMN));
				footerList.add("");
				footerList.add("");
			}else{
				headerList.addAll(convertCommaSepStrToList(OrderConstants.PROVIDER_SO_REPORT_HDR));
			}
			for(String header : headerList){
				pWriter.write(header ==null?"": header.toString());
				pWriter.append(',');
			}
			pWriter.append('\n');
			if(null == listProviderSOReport || listProviderSOReport.size()==0){
				throw new Exception("No records found.");
			}
			long recordCount = 0L;
			while(providerIterator.hasNext()){
				ProviderSOReportVO providerSOReportVO =  providerIterator.next();
				if(reportVO.getRole().intValue() == OrderConstants.NEWCO_ADMIN_ROLEID){
					if(providerSOReportVO.getProviderFirmId()!=null){
						formatAndAppend(providerSOReportVO.getProviderFirmId().toString() ,pWriter);
					}
					formatAndAppend(providerSOReportVO.getProviderFirmName() ,pWriter);
				}
				formatAndAppend(providerSOReportVO.getBuyerId(),pWriter);
				formatAndAppend(providerSOReportVO.getBuyerName(),pWriter);
				formatAndAppend((formatDate(providerSOReportVO.getCompletedDate(),"MM/dd/yyyy")),pWriter);
				formatAndAppend(providerSOReportVO.getSoId(),pWriter);
				formatAndAppend((formatDate(providerSOReportVO.getDatePaid(),"MM/dd/yyyy")),pWriter);
				formatAndAppend(formatter.format(providerSOReportVO.getGrossLabor()),pWriter);
				formatAndAppend(formatter.format(providerSOReportVO.getGrossOther()),pWriter);
				formatAndAppend(formatter.format(providerSOReportVO.getTotalFinalPrice()),pWriter);
				formatAndAppend(formatter.format(providerSOReportVO.getServiceLiveFee()),pWriter);
				formatAndAppend(formatter.format(providerSOReportVO.getNetPayment()),pWriter);
				totalGrossLabor = totalGrossLabor.add(providerSOReportVO.getGrossLabor());
				totalGrossOther = totalGrossOther.add(providerSOReportVO.getGrossOther());
				totalFinalPriceAll = totalFinalPriceAll.add(providerSOReportVO.getTotalFinalPrice());
				totalServiceLiveFee = totalServiceLiveFee.add(providerSOReportVO.getServiceLiveFee());
				totalNetPayment = totalNetPayment.add(providerSOReportVO.getNetPayment());
				pWriter.append("\n");
				++recordCount;
				if(recordCount%100 == 0){
					pWriter.flush();
				}
			}	
			footerList.add("");
			footerList.add("");
			footerList.add("");
			footerList.add("");
			footerList.add("");
			footerList.add(formatter.format(totalGrossLabor));
			footerList.add(formatter.format(totalGrossOther));
			footerList.add(formatter.format(totalFinalPriceAll));
			footerList.add(formatter.format(totalServiceLiveFee));
			footerList.add(formatter.format(totalNetPayment));
			StringBuilder strFooter = new StringBuilder(totalGrossLabor.toString()).append(",").append(totalGrossOther.toString()).append(",");
			strFooter.append(totalFinalPriceAll.toString()).append(",").append(totalServiceLiveFee.toString()).append(",").append(totalNetPayment.toString());
			for(String footer : footerList){
				pWriter.write(footer ==null?"": footer);
				pWriter.append(',');				
			}	
			pWriter.append("\n");
			pWriter.append(OrderConstants.REPORT_FOOTER_INFO);
			reportVO.setReportFooter(strFooter.toString());
		}catch(IOException ioe){
			reportVO.setException(ioe.toString());
			LOGGER.error(ioe);
			throw(ioe);
		}catch (Exception e) {
			reportVO.setException(e.toString());
			LOGGER.error(e);
			throw(e);
		}finally{
			try{
				if(null != pWriter){
					pWriter.flush();
					pWriter.close();
				}
				if(null != writer){
					writer.close();
				}
			}catch (Exception e) {
				LOGGER.error(e);
			}			
		}
		return outputFile==null?null:outputFile.getName();
	}

	private List<String> convertCommaSepStrToList(String inputStr) {
		String stringWithCommas = inputStr.trim();
		if (stringWithCommas != null) {
			List<String> listBuyerIds = new ArrayList<String>(
					Arrays.asList(stringWithCommas.split(",")));
			return listBuyerIds;
		} else {
			return null;
		}
	}

	public FinanceManagerBO getFinanceManagerBO() {
		return financeManagerBO;
	}

	public void setFinanceManagerBO(FinanceManagerBO financeManagerBO) {
		this.financeManagerBO = financeManagerBO;
	}

	public Cryptography getCryptography() {
		return cryptography;
	}

	public void setCryptography (Cryptography cryptography){
		this.cryptography = cryptography;
	}
	
	public IApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
	
	/* Accepts VO and creates a CSV file with the passed data.
	 * Returns the file (report) name */
	private String  writeToCsvAdminPayment(List<AdminPaymentVO> adminPaymentVO, FMReportVO reportVO) throws Exception{
		File outputFile = null;
		FileWriter writer = null;
		PrintWriter pWriter = null;
		NumberFormat formatter = new DecimalFormat(OrderConstants.PRICE_FORMAT_CSV);
		try{
			outputFile = createFile(reportVO.getReportNameForExport());
			reportVO.setFilePath(null!=outputFile?outputFile.getName():"");
			writer = new FileWriter(outputFile);		
			StringBuffer report = new StringBuffer();
			Iterator<AdminPaymentVO> adminIterator = adminPaymentVO.iterator();
			pWriter = new PrintWriter(new BufferedWriter(writer,8192));
			List<String> headerList = new ArrayList<String>();
			List<String> footerList = new ArrayList<String>();
			headerList.addAll(convertCommaSepStrToList(OrderConstants.ADMIN_PAYMENT_HDR));
			for(String header : headerList){
				pWriter.write(header ==null?"": header.toString());
				pWriter.append(',');
			}
			pWriter.append('\n');
			if(null == adminPaymentVO || adminPaymentVO.size() ==0){
				throw new Exception("No records found.");
			}	
			long recordCount = 0L;
			while(adminIterator.hasNext()){
				AdminPaymentVO paymentVO =  adminIterator.next();
				formatAndAppend(paymentVO.getVendorID(),pWriter);
				formatAndAppend(formatter.format(paymentVO.getGoodwillCredits()),pWriter);
				formatAndAppend(formatter.format(paymentVO.getGoodwillDebits()),pWriter);
				formatAndAppend(formatter.format(paymentVO.getNetGoodwillAmount()),pWriter);
				pWriter.append("\n");
				++recordCount;
				if(recordCount%100 == 0){
					pWriter.flush();
				}
			}	
			pWriter.append(OrderConstants.REPORT_FOOTER_INFO);
		}catch(IOException ioe){
			reportVO.setException(ioe.toString());
			LOGGER.error(ioe);
			throw(ioe);
		}catch (Exception e) {
			reportVO.setException(e.toString());
			LOGGER.error(e);
			throw(e);
		}finally{
			try{
				if(null != pWriter){
					pWriter.flush();
					pWriter.close();
				}
				if(null != writer){
					writer.close();
				}
			}catch (Exception e) {
				LOGGER.error(e);
			}			
		}
		return outputFile==null?null:outputFile.getName();
	}


	private void formatAndAppend(String value, PrintWriter pWriter){  
		if (StringUtils.isEmpty(value)){
			//If value to be written in csv is empty or null, insert '-' 
			value = "-";
		}
		String strValue = value;
		pWriter.append("\"");
		if(value.contains("\"")){
			//Replace all existing double quotes 
			strValue = value.replace( "\"", "\"\"");                   
		}
		pWriter.append(strValue);
		pWriter.append("\"");
		pWriter.append(",");
	} 
	
	private File createFile(String reportName) throws Exception{
		File outputFile = null;
		String fileName = reportName;
		String fileDir = PropertiesUtils.getPropertyValue(MPConstants.PAYMENT_REPORTS_DIRECTORY);		
		StringBuilder strBuilder = new StringBuilder(fileName);
		strBuilder.append(".csv");
		fileName = strBuilder.toString();
		try{			
			boolean success = new File(fileDir).mkdir();			
			outputFile = new File(fileDir + fileName);						
		} 
		catch(Exception e){
			LOGGER.error("PaymentReportExportProcessor IO Exception. Directory incorrect or could not write file", e);
			throw new Exception(e);
		}

		return outputFile;
	}

	private String formatDate(Date date, String format) {
		String formattedDate = null;
		if (date != null) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat(format);
				formattedDate = dateFormat.format(date);
			} catch (IllegalArgumentException e) {
				LOGGER.error("Illegar Format Exception" + e.getCause());
			}
			return formattedDate;
		} else {
			return "";
		}
	}

	private String formatAddressString(BuyerSOReportVO buyerSOReportVO) {
		StringBuilder formatedAddress =new StringBuilder();
		formatedAddress.append(buyerSOReportVO.getStreet1()==null?"":buyerSOReportVO.getStreet1());
		formatedAddress.append(" ");
		formatedAddress.append(buyerSOReportVO.getStreet2()==null?"":buyerSOReportVO.getStreet2());
		formatedAddress.append(" ");
		formatedAddress.append(buyerSOReportVO.getCity()==null?"":buyerSOReportVO.getCity());
		formatedAddress.append(", ");
		formatedAddress.append(buyerSOReportVO.getState() ==null?"":buyerSOReportVO.getState());
		formatedAddress.append(" ");
		formatedAddress.append(buyerSOReportVO.getZip()==null?"":buyerSOReportVO.getZip());
		formatedAddress.append(buyerSOReportVO.getZip4()==null?"":buyerSOReportVO.getZip4());		
		return formatedAddress.toString();
	}

	private String formatZipString(BuyerSOReportVO buyerSOReportVO) {
		StringBuilder formatedZip =new StringBuilder();
		formatedZip.append(buyerSOReportVO.getZip()==null?"":buyerSOReportVO.getZip());
		formatedZip.append(buyerSOReportVO.getZip4()==null?"":buyerSOReportVO.getZip4());		
		return formatedZip.toString();
	}
	private String getDecryptedValue(String encryptedEin, int taxPayerIdType) {
		LOGGER.info("getDecryptedValue");
		CryptographyVO cryptographyVO = new CryptographyVO();
		cryptographyVO.setInput(encryptedEin);
		cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
		cryptographyVO = cryptography.decryptKey(cryptographyVO);
		String einNo = cryptographyVO.getResponse();
		if (null == einNo || einNo.equalsIgnoreCase("")) {
			einNo = "";
		}
		/*
		 * else if (!einNo.startsWith("XXX-XX-") &&
		 * taxPayerIdType==ProviderConstants.COMPANY_SSN_IND) { einNo =
		 * ProviderConstants.COMPANY_SSN_ID_MASK+einNo.substring(5); } else
		 * if(taxPayerIdType==0) { einNo = "XXXXX"+einNo.substring(5); } else {
		 * einNo = ProviderConstants.COMPANY_EIN_ID_MASK+einNo.substring(5); }
		 */
		LOGGER.info("leaving getDecryptedValue");
		return einNo;
	}

	private String getRoleTypeFromRoleId(int role) {
		switch(role){
		case OrderConstants.PROVIDER_ROLEID:
			return OrderConstants.PROVIDER_RPT;	
		case OrderConstants.NEWCO_ADMIN_ROLEID:
			return OrderConstants.NEWCO_ADMIN;	
		case OrderConstants.BUYER_ROLEID:
			return OrderConstants.BUYER_RPT;	
		default:
			return "";
		}
	}
	private String getNotNullValue(String dbaName) {
		if (dbaName == null || dbaName.equalsIgnoreCase("")) {
			return OrderConstants.NOT_APPLICABLE_SIGN;
		}
		return dbaName;
	}
	/**
	 * @param taxPayerId : SSN or EIN number which is to be formatted.
	 * @param tinType : SSN/EIN or any other value when nothing is provided.
	 * 
	 * @return formatted taxpayer id . for  123-45-6789
	 * for EIN 12-3456789
	 * 
	 *  This method is duplicated in FMReportAction
	 * **/
	private String formatTaxPayerId(String taxPayerId, String tinType) {
		StringBuilder formatedId = new StringBuilder("-");
		if(!StringUtils.isEmpty(tinType) && !StringUtils.isEmpty(taxPayerId)){
			formatedId = new StringBuilder(taxPayerId);
			if("SSN".equalsIgnoreCase(tinType)){
				formatedId.insert(3, '-');
				formatedId.insert(6, '-');
			}else if("EIN".equalsIgnoreCase(tinType)){
				formatedId.insert(2, '-');
			}
		}else{
			return StringUtils.isEmpty(taxPayerId)?"-":taxPayerId;
		}
		
		return formatedId.toString();
	}
	/**@Description : This method will Prepend Apt with aptNo value in street2 field value
	 * @param aptNo
	 * @param street2
	 * @return
	 */
	private String getAptNoandStreet2(String aptNo, String street2) {
		String aptSteet2 ="";
		if(StringUtils.isNotBlank(aptNo) && StringUtils.isNotBlank(street2)){
			aptSteet2 = OrderConstants.APT_NUMBER + aptNo + OrderConstants.COMMA_DELIMITER_SPACE + street2;
		}else if(StringUtils.isNotBlank(aptNo) && StringUtils.isBlank(street2)){
			aptSteet2 = OrderConstants.APT_NUMBER + aptNo;
		}else if(StringUtils.isBlank(aptNo) && StringUtils.isNotBlank(street2)){
			aptSteet2 = street2;
		}
		return aptSteet2;
	}
}
