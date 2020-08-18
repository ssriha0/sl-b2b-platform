package com.newco.batch.serviceorder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.provider.EmailTemplateBOImpl;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.so.order.SLPartsOrderFileVO;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.so.ISLPartsOrderFileDao;
import com.newco.marketplace.util.BaseFileUtil;
import com.newco.marketplace.util.NachaUtil;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.StackTraceHelper;

public class SLPartsOrderFileProcessor extends BaseFileUtil{
	private ISLPartsOrderFileDao slPartsOrderFileDao;
	EmailTemplateBOImpl emailTemplateBOImpl; 
	private static final Logger logger = Logger.getLogger(SLPartsOrderFileProcessor.class.getName());
	private static final String SL_PARTS_ORDER_FILE_DIRECTORY =  PropertiesUtils.getPropertyValue(Constants.AppPropConstants.SL_PARTS_ORDER_FILE_DIRECTORY);
	private static final String SL_PARTS_ORDER_FILE_ARCHIVE_DIRECTORY =  PropertiesUtils.getPropertyValue(Constants.AppPropConstants.SL_PARTS_ORDER_FILE_ARCHIVE_DIRECTORY);
	private static final String ASSURANT_BUYER_ID =  PropertiesUtils.getPropertyValue(Constants.AppPropConstants.ASSURANT_BUYER_ID);
	private static final String ASSURANT_PARTS_INFO_EMAIL_ID = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.ASSURANT_PARTS_INFO_EMAIL_ID);
	private static final String fileNamePrefix ="SL-PartOrder-BRT-";
	private static final String MAIL_BODY = "Process Name: SL Parts Order File Processor\n" +
											"Date : "+ NachaUtil.getCurrentTimeStamp()+"\n"+
											"Exception Details: ";
	public void generateFile() {
		try
		{
			String fileName = getFileName(fileNamePrefix);
			String fileNameWithDir = SL_PARTS_ORDER_FILE_DIRECTORY+fileName;
	        List<SLPartsOrderFileVO> slPartsOrderList = slPartsOrderFileDao.getPartsOrderRecords(new Integer(ASSURANT_BUYER_ID));
	        if(slPartsOrderList!=null && slPartsOrderList.size()>0)
	        {
		        StringBuffer sb = createStringBuffer(slPartsOrderList);
				File outputFile = new File(fileNameWithDir);
				writeStringToFile(outputFile, sb.toString());
				Runtime.getRuntime().exec("/ftp/scripts/encryptSLParts.sh " + fileNameWithDir + " " + (SL_PARTS_ORDER_FILE_ARCHIVE_DIRECTORY + fileName));
				String mailSubject = "File " + fileName + " has " + slPartsOrderList.size() + " records";
				emailTemplateBOImpl.sendGenericEmailWithoutLogo(ASSURANT_PARTS_INFO_EMAIL_ID,AlertConstants.SERVICE_LIVE_MAILID, mailSubject, mailSubject);
	        }
		}
		catch(Exception e)
		{
			 logger.fatal("[SLPartsOrderFileProcessor.generateFile - Exception] " + StackTraceHelper.getStackTrace(e));
 		     emailTemplateBOImpl.sendNachaFailureEmail(MAIL_BODY +  StackTraceHelper.getStackTrace(e));
		}
	}
	
	private StringBuffer createStringBuffer(List<SLPartsOrderFileVO> slPartsOrderList)
	{
		StringBuffer sb = new StringBuffer();
		Iterator<SLPartsOrderFileVO> itr = slPartsOrderList.iterator();
		SLPartsOrderFileVO slPartsOrderFileVO = null;
		String classCode = "";
		String classComments = "";
		while(itr.hasNext()){
			classCode = "";
			classComments = "";
			slPartsOrderFileVO = itr.next();
			sb.append(slPartsOrderFileVO.getBuyerId()+ "|");
			sb.append(slPartsOrderFileVO.getSlPartOrderId().toString()+ "|");
			sb.append(slPartsOrderFileVO.getSoId()+ "|");
			sb.append(slPartsOrderFileVO.getIncidentId()+ "|");
			String incCommentsStrNewLine = slPartsOrderFileVO.getIncidentComments().replaceAll("\\n+","");
			String incCommentsStrReturn = incCommentsStrNewLine.replaceAll("\\r+","");
			sb.append(incCommentsStrReturn + "|");
			sb.append(slPartsOrderFileVO.getStatus()+ "|");
			sb.append(slPartsOrderFileVO.getFirstName()+ "|");
			sb.append(slPartsOrderFileVO.getLastName()+ "|");
			sb.append(slPartsOrderFileVO.getAddress1()+ "|");
			sb.append(slPartsOrderFileVO.getAddress2()+ "|");
			sb.append(slPartsOrderFileVO.getCity()+ "|");
			sb.append(slPartsOrderFileVO.getState()+ "|");
			sb.append(slPartsOrderFileVO.getZipCode()+ "|");
			sb.append(slPartsOrderFileVO.getZipCodeExt()+ "|");
			sb.append(slPartsOrderFileVO.getPhone()+ "|");
			sb.append(slPartsOrderFileVO.getPhoneExt()+ "|");
			sb.append(slPartsOrderFileVO.getAltPhone()+ "|");
			sb.append(slPartsOrderFileVO.getAltPhoneExt()+ "|");
			sb.append(slPartsOrderFileVO.getProductLine()+ "|");
			sb.append(slPartsOrderFileVO.getQuantity().toString()+ "|");
			String partDesc = slPartsOrderFileVO.getPartDesc();
			classCode = slPartsOrderFileVO.getClassCode();
			classComments = slPartsOrderFileVO.getClassComments();
			/*int classCodeIdx = slPartsOrderFileVO.getPartDesc().indexOf("ClassCode");
			int classCommentsIdx = slPartsOrderFileVO.getPartDesc().indexOf("ClassComments");
			int partDescLen = slPartsOrderFileVO.getPartDesc().length();
			if(classCodeIdx!=-1)
			{
				for(int i=classCodeIdx+10;i<partDescLen;i++)
				{
					if(partDesc.charAt(i)!='!')
						classCode = classCode + partDesc.charAt(i);
					else
						break;
				}
			}
			if(classCommentsIdx!=-1)
			{
				for(int j=classCommentsIdx+14;j<partDescLen;j++)
				{
					if(partDesc.charAt(j)!='!')
						classComments = classComments + partDesc.charAt(j);
					else
						break;
				}
			}*/
			
			sb.append(classCode+ "|");
			sb.append(classComments+ "|");
			sb.append(slPartsOrderFileVO.getPartNumber()+ "|");
			sb.append(slPartsOrderFileVO.getOemNumber()+ "|");
			/**  get the information of part which was saved along with part desc earlier, now they are saved individually*/
			populatePartDescription(partDesc,slPartsOrderFileVO);
			sb.append(partDesc+ "|");
			
			sb.append(slPartsOrderFileVO.getManufacturer()+ "|");
			sb.append(slPartsOrderFileVO.getModelNumber()+ "|");
			sb.append(slPartsOrderFileVO.getSerialNumber()+ "\n");
		}
		return sb;
		
	}
	
	/**  get the information of part which was saved along with part desc earlier, now they are saved seperatly*/
	private void populatePartDescription(String partDesc, SLPartsOrderFileVO slPartsOrderFileVO) {
		if(partDesc!= null){
			String oemNumber = slPartsOrderFileVO.getOemNumber();
			String classCode = slPartsOrderFileVO.getClassCode();
			String classComments = slPartsOrderFileVO.getClassComments();
			String partNumber = slPartsOrderFileVO.getPartNumber();
			String manufacturer = slPartsOrderFileVO.getManufacturer();
			String modelNumber = slPartsOrderFileVO.getModelNumber();
			String serialNumber = slPartsOrderFileVO.getSerialNumber();
			
			StringBuffer partDescSb = new StringBuffer();
			partDescSb.append(partDesc);
			if (StringUtils.isNotBlank(oemNumber) ){
				partDescSb.append(OrderConstants.OEM + ":");
				partDescSb.append(oemNumber);
				partDescSb.append(" ! ");
			}
			if (StringUtils.isNotBlank(classCode) ){
				partDescSb.append(OrderConstants.CLASSCODE + ":");
				partDescSb.append(classCode);
				partDescSb.append(" ! ");
			}
			if (StringUtils.isNotBlank(classComments) ){
				partDescSb.append(OrderConstants.CLASSCOMMENTS + ":");
				partDescSb.append(classComments);
				partDescSb.append(" ! ");
			}
			if (StringUtils.isNotBlank(partNumber) ){
				partDescSb.append(OrderConstants.PARTNUMBER + ":");
				partDescSb.append(partNumber);
				partDescSb.append(" ! ");
			}
			if (StringUtils.isNotBlank(manufacturer) ){
				partDescSb.append(OrderConstants.MANUFACTURER + ":");
				partDescSb.append(manufacturer);
				partDescSb.append(" ! ");
			}
			if (StringUtils.isNotBlank(modelNumber) ){
				partDescSb.append(OrderConstants.MODELNUMBER + ":");
				partDescSb.append(modelNumber);
				partDescSb.append(" ! ");
			}
			if (StringUtils.isNotBlank(serialNumber) ){
				partDescSb.append(OrderConstants.SERIALNUMBER + ":");
				partDescSb.append(serialNumber);
				partDescSb.append(" ! ");
			}
	   		partDesc =  partDescSb.toString();	
		}
	}
	
	public static String getFileName(String fileNamePrefix2){
        StringBuilder fileName = new StringBuilder();
        fileName.append(fileNamePrefix2);
        String nowYYYYMMDD = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        fileName.append(nowYYYYMMDD);
  	    return fileName.toString();
     }
	
	public ISLPartsOrderFileDao getSlPartsOrderFileDao() {
		return slPartsOrderFileDao;
	}
	public void setSlPartsOrderFileDao(ISLPartsOrderFileDao slPartsOrderFileDao) {
		this.slPartsOrderFileDao = slPartsOrderFileDao;
	}

	public EmailTemplateBOImpl getEmailTemplateBOImpl() {
		return emailTemplateBOImpl;
	}

	public void setEmailTemplateBOImpl(EmailTemplateBOImpl emailTemplateBOImpl) {
		this.emailTemplateBOImpl = emailTemplateBOImpl;
	}
}
