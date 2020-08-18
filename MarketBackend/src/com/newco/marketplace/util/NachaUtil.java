package com.newco.marketplace.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.newco.marketplace.dto.vo.ach.AddendaRecordVO;
import com.newco.marketplace.dto.vo.ach.EntryDetailRecordVO;
import com.newco.marketplace.dto.vo.ach.FieldDetailVO;
import com.newco.marketplace.dto.vo.ach.FileControlRecordVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.persistence.iDao.ach.NachaMetaDataDao;
import com.newco.marketplace.utils.AchConstants;

public class NachaUtil {
    public static String getFileName(String fileNamePrefix){
  	   
  	   Calendar calendar = new GregorianCalendar();
  	   int month = calendar.get(Calendar.MONTH)+1;
  		String fileName = fileNamePrefix+ month+"-"+calendar.get(Calendar.DAY_OF_MONTH)+"-"+calendar.get(Calendar.YEAR)+"_"+calendar.get(Calendar.HOUR)
  						+"-"+calendar.get(Calendar.MINUTE)+"-"+calendar.get(Calendar.SECOND)+"_"+getAM_PM(calendar) ;
  		return fileName;
  	   
     }
    
     public static String getAM_PM(Calendar calendar){
  	   if (calendar.get(Calendar.AM_PM)==1) {
  		   return "PM";
  	   }
  	   else return "AM";
  	   
     }
     
     public static Timestamp getCurrentTimeStamp(){
    	 return new Timestamp(new Date(System.currentTimeMillis()).getTime());
    	 
     }
     public static EntryDetailRecordVO getEntryDetailCopy(EntryDetailRecordVO edrOriginal){
 		EntryDetailRecordVO entryDetailCopy = new EntryDetailRecordVO();
 		ArrayList<FieldDetailVO> fieldDetailsCopy = new ArrayList<FieldDetailVO>();
 		ArrayList<FieldDetailVO> fieldDetailsOriginal =  edrOriginal.getFieldDetailVO();
 		for (int p=0;p<fieldDetailsOriginal.size();p++){
 			FieldDetailVO fieldDetailCopy = new FieldDetailVO();
 			FieldDetailVO fieldDetailOriginal = (FieldDetailVO)fieldDetailsOriginal.get(p);
 			
 			fieldDetailCopy.setEndPosition(fieldDetailOriginal.getEndPosition());
 			fieldDetailCopy.setFieldDescription(fieldDetailOriginal.getFieldDescription());
 			fieldDetailCopy.setFieldId(fieldDetailOriginal.getFieldId());
 			fieldDetailCopy.setFieldName(fieldDetailOriginal.getFieldName());
 			fieldDetailCopy.setFieldOrder(fieldDetailOriginal.getFieldOrder());
 			fieldDetailCopy.setFieldSize(fieldDetailOriginal.getFieldSize());
 			fieldDetailCopy.setFieldType(fieldDetailOriginal.getFieldType());
 			fieldDetailCopy.setFieldValue(fieldDetailOriginal.getFieldValue());
 			fieldDetailCopy.setStartPosition(fieldDetailOriginal.getStartPosition());
 			fieldDetailsCopy.add(fieldDetailCopy);
 		}
 		entryDetailCopy.setFieldDetailVO(fieldDetailsCopy);
 		entryDetailCopy.setAddendaAvailable(edrOriginal.isAddendaAvailable());
 		entryDetailCopy.setEntryDetailNumber(edrOriginal.getEntryDetailNumber());
 		entryDetailCopy.setRecord(edrOriginal.getRecord());
 		entryDetailCopy.setImmediateDestination(edrOriginal.getImmediateDestination());
 		entryDetailCopy.setTransactionType(edrOriginal.getTransactionType());
 		
 		return entryDetailCopy; 
 	}
     
     
     public static AddendaRecordVO getAddendaCopy(AddendaRecordVO addendaOriginal){
    	 AddendaRecordVO addendaCopy = new AddendaRecordVO();
  		ArrayList<FieldDetailVO> fieldDetailsCopy = new ArrayList<FieldDetailVO>();
  		ArrayList<FieldDetailVO> fieldDetailsOriginal =  addendaOriginal.getFieldDetailVO();
  		for (int p=0;p<fieldDetailsOriginal.size();p++){
  			FieldDetailVO fieldDetailCopy = new FieldDetailVO();
  			FieldDetailVO fieldDetailOriginal = (FieldDetailVO)fieldDetailsOriginal.get(p);
  			fieldDetailCopy.setEndPosition(fieldDetailOriginal.getEndPosition());
  			fieldDetailCopy.setFieldDescription(fieldDetailOriginal.getFieldDescription());
  			fieldDetailCopy.setFieldId(fieldDetailOriginal.getFieldId());
  			fieldDetailCopy.setFieldName(fieldDetailOriginal.getFieldName());
  			fieldDetailCopy.setFieldOrder(fieldDetailOriginal.getFieldOrder());
  			fieldDetailCopy.setFieldSize(fieldDetailOriginal.getFieldSize());
  			fieldDetailCopy.setFieldType(fieldDetailOriginal.getFieldType());
  			fieldDetailCopy.setFieldValue(fieldDetailOriginal.getFieldValue());
  			fieldDetailCopy.setStartPosition(fieldDetailOriginal.getStartPosition());
  			fieldDetailsCopy.add(fieldDetailCopy);
  		}
  		addendaCopy.setFieldDetailVO(fieldDetailsCopy);
  		addendaCopy.setRecord(addendaOriginal.getRecord());
  		
  		return addendaCopy; 
  	}
      

     public static FileControlRecordVO getControlCopy(FileControlRecordVO fileControlRecordVO){
    	 FileControlRecordVO controlCopy = new FileControlRecordVO();
  		ArrayList<FieldDetailVO> fieldDetailsCopy = new ArrayList<FieldDetailVO>();
  		ArrayList<FieldDetailVO> fieldDetailsOriginal =  fileControlRecordVO.getFieldDetailVO();
  		for (int p=0;p<fieldDetailsOriginal.size();p++){
  			FieldDetailVO fieldDetailCopy = new FieldDetailVO();
  			FieldDetailVO fieldDetailOriginal = (FieldDetailVO)fieldDetailsOriginal.get(p);
  			fieldDetailCopy.setEndPosition(fieldDetailOriginal.getEndPosition());
  			fieldDetailCopy.setFieldDescription(fieldDetailOriginal.getFieldDescription());
  			fieldDetailCopy.setFieldId(fieldDetailOriginal.getFieldId());
  			fieldDetailCopy.setFieldName(fieldDetailOriginal.getFieldName());
  			fieldDetailCopy.setFieldOrder(fieldDetailOriginal.getFieldOrder());
  			fieldDetailCopy.setFieldSize(fieldDetailOriginal.getFieldSize());
  			fieldDetailCopy.setFieldType(fieldDetailOriginal.getFieldType());
  			fieldDetailCopy.setFieldValue(fieldDetailOriginal.getFieldValue());
  			fieldDetailCopy.setStartPosition(fieldDetailOriginal.getStartPosition());
  			fieldDetailsCopy.add(fieldDetailCopy);
  		}
  		controlCopy.setFieldDetailVO(fieldDetailsCopy);
  		  		
  		return controlCopy; 
  	}
     
 	/**
 	 * This private method returns a template for each record object that conforms to
 	 * NACHA format. The template is a list of FieldDetailVO object and each FieldDetailVO
 	 * object holds information about the field size, their positions, their type (alpha/numeric)
 	 * etc
 	 * 
 	 * @param metdataIdentifier represents the record that the method should return
 	 * @param batchId used only for retrieving batch header template
 	 * @return ArrayList  returns a list of FieldDetailVO 
 	 */
 	public static ArrayList<FieldDetailVO> getBaseMetaData(NachaMetaDataDao nachaMetaDataDao, String metdataIdentifier,
 		int batchId) throws BusinessServiceException {
 		ArrayList<FieldDetailVO> fdList = new ArrayList<FieldDetailVO>();
 		FieldDetailVO fieldVO = new FieldDetailVO();
 		fieldVO.setFieldCategory(metdataIdentifier);
 		fieldVO.setBatch_group_Id(batchId);
 		try {
 			fdList = nachaMetaDataDao.retrieveFieldDetais(fieldVO);
 		} catch (Exception e) {
 			throw new BusinessServiceException(AchConstants.ACH_TEMPLATE_RETRIEVAL_ERROR+e.getStackTrace());
 		}
 		return fdList;
 	}

     public static String getNextFileIdModifier(Integer iProcessLogCount){
    	 String fileIdModifier = null;
    	 //if less than 26 then it will be from A-Z
    	 if(iProcessLogCount < 26){
	    	 iProcessLogCount = iProcessLogCount + 65;
	 		 char charCount = (char)(iProcessLogCount.intValue());
	 		 fileIdModifier = String.valueOf(charCount);
    	 }
    	 //if its between 26 to 36 then it will be 0-9
    	 else if (iProcessLogCount > 25 && iProcessLogCount < 36){
    		 fileIdModifier = String.valueOf(iProcessLogCount.intValue() - 26);
    	 }
    	 else{
    		 fileIdModifier = "A";
    	 }
    	 
    	 return fileIdModifier;
     }
public static long getConsolidatedLedgerId(){
	   Calendar calendar = new GregorianCalendar();
  	   int month = calendar.get(Calendar.MONTH)+1;
  	   String id = ""+month+calendar.get(Calendar.DAY_OF_MONTH)+calendar.get(Calendar.YEAR)+calendar.get(Calendar.HOUR)
  						+calendar.get(Calendar.MINUTE)+calendar.get(Calendar.SECOND) ;
  	 long longLedgerId = new Long(new Long(id).longValue());
  	   
  	  return longLedgerId;
}

public static String getArchiveFileName(String fileName){
	String temp = BaseFileUtil.getFileNameWithoutExtension(fileName)+"_";
	String archiveFile = NachaUtil.getFileName(temp)+".txt";
	return archiveFile;
}
public static void main(String s[]){
	
}
}
