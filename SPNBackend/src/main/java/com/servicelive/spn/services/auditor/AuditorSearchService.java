package com.servicelive.spn.services.auditor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.lang.StringUtils;


import com.newco.marketplace.utils.DateUtils;
import com.servicelive.domain.spn.detached.SPNAuditorSearchCriteriaVO;
import com.servicelive.domain.spn.detached.SPNAuditorSearchResultVO;
import com.servicelive.spn.common.SPNBackendConstants;

import com.servicelive.spn.common.detached.BackgroundInformationVO;
import com.servicelive.spn.common.detached.SearchBackgroundInformationVO;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.common.util.PropertyManagerUtil;
import com.servicelive.spn.services.BaseServices;
import com.servicelive.spn.common.detached.BackgroundCheckHistoryVO;
/**
 * Auditor Search for provider firms 
 * @author sldev
 *
 */

public class AuditorSearchService extends BaseServices {

	private PropertyManagerUtil propertyManagerUtil;

	@SuppressWarnings("unused")
	@Override
	protected void handleDates(Object entity) {
		// do nothing
	}

	/**
	 * @param spnAuditorSearchCriteriaVO
	 * @return List<SPNAuditorSearchResultVO>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SPNAuditorSearchResultVO> getAuditorSearchForProviderFirms
	(SPNAuditorSearchCriteriaVO spnAuditorSearchCriteriaVO) throws Exception{
		SPNAuditorSearchResultVO result = null;
		String userName = null;
		
		Date time = CalendarUtil.getNow();
		CalendarUtil.addMinutes(time, -1*propertyManagerUtil.getSpnAuditorMonitorStickyQueueTimeoutMinutes());
		spnAuditorSearchCriteriaVO.setLockReferenceTime(time);

		List<SPNAuditorSearchResultVO> searchResults = getSqlMapClient().queryForList("network.auditorsearch.provider_firm_search_for_auditor", spnAuditorSearchCriteriaVO);
		for(SPNAuditorSearchResultVO searchResult : searchResults){
			if(null != searchResult){
				if(StringUtils.isNotBlank(searchResult.getLockedByName()) && null == searchResult.getLockedById() 
						&& StringUtils.isBlank(searchResult.getLockedByFirstName()) && StringUtils.isBlank(searchResult.getLockedByLastName())){
					
					if(null == result || (null != result && !(result.getLockedByName().equals(searchResult.getLockedByName())))){
						userName = searchResult.getLockedByName();
						result = (SPNAuditorSearchResultVO)getSqlMapClient().queryForObject("network.auditorsearch.get_admin_details", userName);
					}					
					if(null != result){
						searchResult.setLockedById(result.getLockedById());
						searchResult.setLockedByFirstName(result.getLockedByFirstName());
						searchResult.setLockedByLastName(result.getLockedByLastName());
					}
				}
			}
		}
		return searchResults;
	}
	
	
	/**
	 * @param searchBackgroundInformationVO
	 * @return Integer
	 * @throws SQLException
	 */
	//SL-19387
	//Fetching Background Check details count of resources from db
	@SuppressWarnings("unchecked")
	public Integer getBackgroundInformationCount(SearchBackgroundInformationVO searchBackgroundInformationVO) throws SQLException {
		Integer count =(Integer) getSqlMapClient().queryForObject("auditor.background.getBackgroundInformationCount",searchBackgroundInformationVO);
		return count;
		}

	
	/**
	 * @param searchBackgroundInformationVO
	 * @return List<BackgroundInformationVO>
	 * @throws SQLException
	 */
	//SL-19387
	//Fetching Background Check details of resources from db
	@SuppressWarnings("unchecked")
	public List<BackgroundInformationVO> getBackgroundInformation(SearchBackgroundInformationVO searchBackgroundInformationVO) throws SQLException {
		List<BackgroundInformationVO> backgroundInfoList = getSqlMapClient().queryForList("auditor.background.getBackgroundInformation",searchBackgroundInformationVO);
		return backgroundInfoList;
		}
	
	/**
	 * @param propertyManagerUtil the propertyManagerUtil to set
	 */
	public void setPropertyManagerUtil(PropertyManagerUtil propertyManagerUtil) {
		this.propertyManagerUtil = propertyManagerUtil;
	}
	/**
	 * @param ByteArrayOutputStream
	 * @param List<BackgroundInformationVO>
	 * @return ByteArrayOutputStream
	 * @throws IOException, WriteException
	 */
	//R11.0
	//Exporting data in excel format
	public ByteArrayOutputStream getExportToExcel(ByteArrayOutputStream outFinal, List<BackgroundInformationVO> bckgdInfoVO) throws IOException, WriteException {
		//Creating excel
		WritableWorkbook workbook = Workbook.createWorkbook(outFinal);
		WritableSheet sheet = workbook.createSheet("First Sheet", 0);
		sheet.setColumnView(0,40);
		sheet.setColumnView(1,40);
		sheet.setColumnView(2,30);
		sheet.setColumnView(3,30);
		sheet.setColumnView(4,30);
		sheet.setColumnView(5,30);
		sheet.setColumnView(6,85);
		

		// Create a cell format for Arial 10 point font 
		WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD); 
		WritableCellFormat arial10format = new WritableCellFormat (arial10font);	
		arial10format.setAlignment(Alignment.CENTRE);
		arial10format.setBackground(Colour.ICE_BLUE);
		arial10format.setBorder(Border.ALL,BorderLineStyle.THIN);
		WritableCellFormat cellformat = new WritableCellFormat ();
		cellformat.setAlignment(Alignment.LEFT);

		
		Label providerFirm = new Label(0, 0, "Provider Firm",arial10format);
		Label provider = new Label(1, 0, "Provider",arial10format);
		Label bcStatus = new Label(2, 0, "Background Check Status",arial10format);
		Label certDate = new Label(3, 0, "Last Certification Date",arial10format);
		Label recDueDate = new Label(4, 0, "Recertification Due Date",arial10format);
		Label recStatus = new Label(5, 0, "Recertification Status",arial10format);
		Label systemAction = new Label(6, 0, "System Action/Notice Sent On",arial10format);
		
		
		sheet.addCell(providerFirm);
		sheet.addCell(provider);
		sheet.addCell(bcStatus);
		sheet.addCell(certDate);
		sheet.addCell(recDueDate);
		sheet.addCell(recStatus);
		sheet.addCell(systemAction);

		for (int i=0; i < bckgdInfoVO.size(); i++){

			
			Label firmName = new Label(0, (1+i), bckgdInfoVO.get(i).getVendorBusinessName()+"\n"+"("+"ID #"+bckgdInfoVO.get(i).getVendorId()+")",cellformat);
			Label providerName = new Label(1, (1+i), bckgdInfoVO.get(i).getProviderFirstName()+" "+bckgdInfoVO.get(i).getProviderLastName()+"\n"+"("+"ID #"+bckgdInfoVO.get(i).getResourceId()+")",cellformat);
			Label backgroundStatus = new Label(2, (1+i), bckgdInfoVO.get(i).getBackgroundState(),cellformat);
			Label certificationDate = null;
			if(null != bckgdInfoVO.get(i).getVerificationDate())
			{
				certificationDate= new Label(3, (1+i), DateUtils.getFormatedDate(bckgdInfoVO.get(i).getVerificationDate(),"MM/dd/yyyy"),cellformat);
			}else
			{					
				certificationDate= new Label(3, (1+i), null, cellformat);
			}
			String rectDate = null;
			if (null != bckgdInfoVO.get(i).getReverificationDate() && null!=bckgdInfoVO.get(i).getCriteriaBg() && bckgdInfoVO.get(i).getCriteriaBg() > 0) {
				rectDate = ""
						+ DateUtils.getFormatedDate(bckgdInfoVO.get(i).getReverificationDate(),
								"MM/dd/yyyy");
			}
			
			Label recertificationDueDate = new Label(4, (1+i),rectDate,cellformat);
			
			String recertStat = null;
			if (null != bckgdInfoVO.get(i).getRecertificationStatus() && null!=bckgdInfoVO.get(i).getCriteriaBg() && bckgdInfoVO.get(i).getCriteriaBg() > 0) {
				if(bckgdInfoVO.get(i).getRecertificationStatus().equals("In Process")) {
					recertStat =bckgdInfoVO.get(i).getRecertificationStatus();
				} else if (Integer.parseInt(bckgdInfoVO.get(i).getRecertificationStatus()) == 0) {
					recertStat = "Due Today";
				} else if (Integer.parseInt(bckgdInfoVO.get(i).getRecertificationStatus()) < 0) {
					recertStat = "Past Due";
				} else if(Integer.parseInt(bckgdInfoVO.get(i).getRecertificationStatus()) > 0 && Integer.parseInt(bckgdInfoVO.get(i).getRecertificationStatus()) <= 30) {
					recertStat = "Due in "+ bckgdInfoVO.get(i).getRecertificationStatus() + " days";
				}
			}
			
			Label recertificationStatus = new Label(5, (1+i),recertStat,cellformat);
			
			StringBuilder notType = new StringBuilder();
			if (null !=  bckgdInfoVO.get(i).getNotificationType()&& (null != bckgdInfoVO.get(i).getNotificationDateThirty() ||
					null != bckgdInfoVO.get(i).getNotificationDateSeven() || null != bckgdInfoVO.get(i).getNotificationDateZero())
					&& null!=bckgdInfoVO.get(i).getCriteriaBg() && bckgdInfoVO.get(i).getCriteriaBg() > 0 && null != bckgdInfoVO.get(i).getRecertificationStatus()) {
				
				
				if(null != bckgdInfoVO.get(i).getNotificationDateThirty())
				{
					notType.append("30 days notice sent on").append(" ")
						.append(DateUtils.getFormatedDate(bckgdInfoVO.get(i).getNotificationDateThirty(),
								"MM/dd/yyyy")).append(" ");
				}
				
				if(null != bckgdInfoVO.get(i)
						.getNotificationDateSeven())
				{
					notType.append("7 days notice sent on").append(" ")
						.append(DateUtils.getFormatedDate(bckgdInfoVO.get(i).getNotificationDateSeven(),
								"MM/dd/yyyy")).append(" ");
				}
				
				if(null != bckgdInfoVO.get(i).getNotificationDateZero())
				{
					notType.append("0 days notice sent on").append(" ")
						.append(DateUtils.getFormatedDate(bckgdInfoVO.get(i).getNotificationDateZero(),
								"MM/dd/yyyy"));
				}

			}
			Label sytemAction = new Label(6, (1+i),notType.toString(),cellformat);

			
			sheet.addCell(firmName);
			sheet.addCell(providerName);
			sheet.addCell(backgroundStatus);
			sheet.addCell(certificationDate);
			sheet.addCell(recertificationDueDate);
			sheet.addCell(recertificationStatus);
			sheet.addCell(sytemAction);
		}


		workbook.write();
		workbook.close();
		outFinal.close();

		return outFinal;
	}

	/**
	 * @param List<BackgroundInformationVO>
	 * @return StringBuffer
	 */
	//R11.0
	//Exporting data in CSV Comma format
	public StringBuffer getExportToCSVComma(List<BackgroundInformationVO> bckgdInfoVO) {
		StringBuffer buffer = new StringBuffer();

		Iterator<BackgroundInformationVO> bkgdIterator = bckgdInfoVO.iterator();
		List<String> headerList = new ArrayList<String>();
		headerList.addAll(convertCommaSepStrToList(SPNBackendConstants.SPN_AUDITOR_HDR));
		for(String header : headerList){
			buffer.append(header ==null?"": header.toString());
			buffer.append(',');
		}
		buffer.append('\n');
		while(bkgdIterator.hasNext()){
			BackgroundInformationVO bkgVO =  bkgdIterator.next();
			if(StringUtils.isNotEmpty(bkgVO.getVendorBusinessName()) && null!=bkgVO.getVendorId()){
			formatAndAppend(bkgVO.getVendorBusinessName()+" "+"("+"ID #"+bkgVO.getVendorId()+")",buffer);
			}
			else{
				formatAndAppend(null,buffer);
			}
			if(StringUtils.isNotEmpty(bkgVO.getProviderFirstName()) && StringUtils.isNotEmpty(bkgVO.getProviderLastName()) && null!=bkgVO.getResourceId() && null!=bkgVO.getVendorId()){
			formatAndAppend(bkgVO.getProviderFirstName()+" "+bkgVO.getProviderLastName()+" "+"("+"ID #"+bkgVO.getResourceId()+")",buffer);
			}
			else{
				formatAndAppend(null,buffer);
			}
			if(StringUtils.isNotEmpty(bkgVO.getBackgroundState())){
			formatAndAppend(bkgVO.getBackgroundState(),buffer);
			}
			else{
				formatAndAppend(null,buffer);
			}
			if(null!= bkgVO.getVerificationDate()){
			formatAndAppend(DateUtils.getFormatedDate(bkgVO.getVerificationDate(),"MM/dd/yyyy"),buffer);
			}
			else{
				String verificationDate = null;
				formatAndAppend(verificationDate,buffer);
			}
			if (null != bkgVO.getReverificationDate() && null!=bkgVO.getCriteriaBg() && bkgVO.getCriteriaBg() > 0) {
			formatAndAppend(DateUtils.getFormatedDate(bkgVO.getReverificationDate(),"MM/dd/yyyy"),buffer);
			}else{
				String reVerificationDate = null;
				formatAndAppend(reVerificationDate,buffer);
			}
			setRecertificationStatus(bkgVO);
			formatAndAppend(bkgVO.getRecertificationStatus(),buffer);
			setNotificationType(bkgVO);
			formatAndAppend(bkgVO.getNotificationType(),buffer);
			buffer.append("\n");
		}
	
		return buffer;
	}
	/**
	 * @param List<BackgroundInformationVO>
	 * @return StringBuffer
	*/
	//R11.0
	//Exporting data in CSV Pipe format
	public StringBuffer getExportToCSVPipe(List<BackgroundInformationVO> bckgdInfoVO) {
		StringBuffer buffer = new StringBuffer();

		Iterator<BackgroundInformationVO> bkgdIterator = bckgdInfoVO.iterator();
		List<String> headerList = new ArrayList<String>();
		headerList.addAll(convertCommaSepStrToList(SPNBackendConstants.SPN_AUDITOR_HDR));
		for(String header : headerList){
			buffer.append(header ==null?"": header.toString());
			buffer.append('|');
		}
		buffer.append('\n');
		while(bkgdIterator.hasNext()){
			BackgroundInformationVO bkgVO =  bkgdIterator.next();
			if(StringUtils.isNotEmpty(bkgVO.getVendorBusinessName()) && null!=bkgVO.getVendorId()){
				formatAndAppendPipe(bkgVO.getVendorBusinessName()+" "+"("+"ID #"+bkgVO.getVendorId()+")",buffer);
			}
			else{
				formatAndAppendPipe(null,buffer);
			}
			if(StringUtils.isNotEmpty(bkgVO.getProviderFirstName()) && StringUtils.isNotEmpty(bkgVO.getProviderLastName()) && null!=bkgVO.getResourceId() && null!=bkgVO.getVendorId()){
				formatAndAppendPipe(bkgVO.getProviderFirstName()+" "+bkgVO.getProviderLastName()+" "+"("+"ID #"+bkgVO.getResourceId()+")",buffer);
			}
			else{
				formatAndAppendPipe(null,buffer);
			}
			if(StringUtils.isNotEmpty(bkgVO.getBackgroundState())){
				formatAndAppendPipe(bkgVO.getBackgroundState(),buffer);
			}
			else{
				formatAndAppendPipe(null,buffer);
			}
			if(null!= bkgVO.getVerificationDate()){
				formatAndAppendPipe(DateUtils.getFormatedDate(bkgVO.getVerificationDate(),"MM/dd/yyyy"),buffer);
			}
			else{
				String verificationDate = null;
				formatAndAppendPipe(verificationDate,buffer);
			}
			if (null != bkgVO.getReverificationDate() && null!=bkgVO.getCriteriaBg() && bkgVO.getCriteriaBg() > 0) {
				formatAndAppendPipe(DateUtils.getFormatedDate(bkgVO.getReverificationDate(),"MM/dd/yyyy"),buffer);
			}else{
				String reVerificationDate = null;
				formatAndAppendPipe(reVerificationDate,buffer);
			}
			setRecertificationStatus(bkgVO);
			formatAndAppendPipe(bkgVO.getRecertificationStatus(),buffer);
			setNotificationType(bkgVO);
			formatAndAppendPipe(bkgVO.getNotificationType(),buffer);
			buffer.append("\n");
		}
	
		return buffer;
	}
	/**
	 * @param BackgroundInformationVO
	 * To set the notification type based on the notification date
	*/
	private void setNotificationType(BackgroundInformationVO bkgVO) {
		StringBuilder notType = new StringBuilder();
		if (null !=  bkgVO.getNotificationType()&& (null != bkgVO.getNotificationDateThirty() ||
				null != bkgVO.getNotificationDateSeven() || null != bkgVO.getNotificationDateZero())
				&& null!=bkgVO.getCriteriaBg() && bkgVO.getCriteriaBg() > 0 && null != bkgVO.getRecertificationStatus()) {
			
			
			if(null != bkgVO.getNotificationDateThirty())
			{
				notType.append("30 days notice sent on").append(" ")
					.append(DateUtils.getFormatedDate(bkgVO.getNotificationDateThirty(),
							"MM/dd/yyyy")).append(" ");
			}
			
			if(null != bkgVO
					.getNotificationDateSeven())
			{
				notType.append("7 days notice sent on").append(" ")
					.append(DateUtils.getFormatedDate(bkgVO.getNotificationDateSeven(),
							"MM/dd/yyyy")).append(" ");
			}
			
			if(null != bkgVO.getNotificationDateZero())
			{
				notType.append("0 days notice sent on").append(" ")
					.append(DateUtils.getFormatedDate(bkgVO.getNotificationDateZero(),
							"MM/dd/yyyy"));
			}

		}
		bkgVO.setNotificationType(notType.toString());
	}
	/**
	 * @param BackgroundInformationVO
	 * To set the recertification status based on the recertification status value
	*/
	private void setRecertificationStatus(BackgroundInformationVO bkgVO) {
		String recertStat = null;
		if (null != bkgVO.getRecertificationStatus() && null!=bkgVO.getCriteriaBg() && bkgVO.getCriteriaBg() > 0) {
			if(bkgVO.getRecertificationStatus().equals("In Process")) {
				recertStat =bkgVO.getRecertificationStatus();
			} else if (Integer.parseInt(bkgVO.getRecertificationStatus()) == 0) {
				recertStat = "Due Today";
			} else if (Integer.parseInt(bkgVO.getRecertificationStatus()) < 0) {
				recertStat = "Past Due";
			} else if(Integer.parseInt(bkgVO.getRecertificationStatus()) > 0 && Integer.parseInt(bkgVO.getRecertificationStatus()) <= 30) {
				recertStat = "Due in "+ bkgVO.getRecertificationStatus() + " days";
			}
			
		}
		bkgVO.setRecertificationStatus(recertStat);
	}
	/**
	 * @param inputString
	 * @return List<String>
	 * To convert the Comma Separated string to a list 
	*/
	private List<String> convertCommaSepStrToList(String inputStr) {
		String stringWithCommas = inputStr.trim();
		if (stringWithCommas != null) {
			List<String> listHdr = new ArrayList<String>(
					Arrays.asList(stringWithCommas.split(",")));
			return listHdr;
		} else {
			return null;
		}
	}
	/**
	 * @param value
	 * @param buffer
	 * To format the value and append comma delimiter 
	*/
	private void formatAndAppend(String value, StringBuffer buffer){  
		if (StringUtils.isEmpty(value)){
			value = "";
		}
		buffer.append(value);
		buffer.append(",");
	}
	
	/**
	 * @param value
	 * @param buffer
	 * To format the value and append pipe delimiter 
	*/
	private void formatAndAppendPipe(String value,StringBuffer buffer) {
		if (StringUtils.isEmpty(value)){
			value = "";
		}
		buffer.append(value);
		buffer.append("|");
		
	}
	public List<BackgroundCheckHistoryVO> getBackgroundCheckHistoryDetails(BackgroundCheckHistoryVO bgHistVO) throws SQLException {
		List<BackgroundCheckHistoryVO> backgroundHistList = getSqlMapClient().queryForList("auditor.background.getBackgroundCheckHistoryDetails",bgHistVO);
		return backgroundHistList;
	}
	
	public String getProviderName(Integer resourceId) throws SQLException{
		String name =(String) getSqlMapClient().queryForObject("auditor.getProviderName",resourceId);
		return name;
	}
//SL-20367
	public Boolean getBuyerFeatureSet(Integer buyerId, String feature) throws SQLException {
		Boolean foundFeature = null;
		String strfeature =  null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("buyerId", String.valueOf(buyerId.intValue()));
		params.put("feature", feature);
		
		strfeature = (String) getSqlMapClient().queryForObject("auditor.getBuyerFeatureValue",params);
		
		if(strfeature != null) {
			foundFeature = new Boolean(true);
			}
		else{
			foundFeature = new Boolean(false);
			}
		return foundFeature;
	}
}
