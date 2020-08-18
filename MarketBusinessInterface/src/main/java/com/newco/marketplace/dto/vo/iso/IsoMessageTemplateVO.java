package com.newco.marketplace.dto.vo.iso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import com.newco.marketplace.exception.StringParseException;
import com.newco.marketplace.util.Bitmapper;
import com.sears.os.vo.SerializableBaseVO;

public class IsoMessageTemplateVO extends SerializableBaseVO implements Comparator{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<IsoMessageGenericRecord> isoMessageGenericRecordList;
	private String messageTypeIdentifier = "";
	public List<IsoMessageGenericRecord> getIsoMessageGenericRecordList() {
		return isoMessageGenericRecordList;
	}

	public void setIsoMessageGenericRecord(List<IsoMessageGenericRecord> isoMessageGenericRecordList) {
		this.isoMessageGenericRecordList = isoMessageGenericRecordList;
	}
	public void sort(){
		Collections.sort(isoMessageGenericRecordList, this);

	}
	
	public byte[] formatValue() throws StringParseException, IOException{
		removeEmptyFields();
		ByteArrayOutputStream baus = new ByteArrayOutputStream();
		 Collections.sort(isoMessageGenericRecordList, this);
		 
		   String formatString = "";
		   for(int r=0;r<isoMessageGenericRecordList.size();r++) {
			   IsoMessageGenericRecord imgr = isoMessageGenericRecordList.get(r);
			   if(	r<5 || r==isoMessageGenericRecordList.size()-1) 
			   {
				  // baus.write(imgr.getIsoByteValue()) ;
			   }
			   else {
			   String str = formatValue(imgr);
			   baus.write(str.getBytes());
			   formatString=formatString+str;
			   }

		   }
	   
		   return baus.toByteArray();
	}
	
	public String format() throws StringParseException{
		 removeEmptyFields();
		 Collections.sort(isoMessageGenericRecordList, this);
		 Iterator<IsoMessageGenericRecord> itr = isoMessageGenericRecordList.iterator();
		   String formatString = "";
		   while (itr.hasNext())
		   {
			   IsoMessageGenericRecord imgr = itr.next();
			   String str = formatValue(imgr);
			   formatString=formatString+str;
		   }
		   return formatString;
	}
	private void removeEmptyFields(){
		for (int i=0;i<isoMessageGenericRecordList.size();i++)
		{
			IsoMessageGenericRecord isoMessageGenericRecord = isoMessageGenericRecordList.get(i);
			String temp = isoMessageGenericRecord.getIsoMessageValue();
			if (!(isoMessageGenericRecord.getIsoDataElement().equals("BITMAP"))) {
				if (( temp==null)|| (temp.equals("")) || (temp.equals("00")) )
				{
					isoMessageGenericRecordList.remove(i);
					i--;
				}
			}
		}
	}

	public String formatValue(IsoMessageGenericRecord isoMessageGenericRecord) throws StringParseException{
		String str = isoMessageGenericRecord.getIsoMessageValue();
		if (str==null) str="";
		String numberStr = "";
		
		
		if (isoMessageGenericRecord.getIsoFormatTypeDescr().equals("FIXED"))
		{
			int definedLength = isoMessageGenericRecord.getDataLength();
			int stringLength = str.length();
			int toPadLength = 0;
			if (stringLength<=definedLength){	
				toPadLength =definedLength-stringLength;
			}
			else{
				throw new StringParseException("Length of the String "+ str+ " exceeds the defined length of "+definedLength);
			}
			if (( isoMessageGenericRecord.getMessageDataType().equals("A") )|| 
					(isoMessageGenericRecord.getMessageDataType().equals("ANS"))) {
				str = formatAlphaNumeric(str,toPadLength);
			}
			if (isoMessageGenericRecord.getMessageDataType().equals("N")) {
				str = formatNumeric(str,toPadLength);
			}
		}
		if ((isoMessageGenericRecord.getIsoFormatTypeDescr().equals("LLVAR")) || 
				(isoMessageGenericRecord.getIsoFormatTypeDescr().equals("LLLVAR")) ) {
		if (isoMessageGenericRecord.getIsoFormatTypeDescr().equals("LLVAR")) {
			numberStr = prefixZeroForCount(str, 2);
		}
		if (isoMessageGenericRecord.getIsoFormatTypeDescr().equals("LLLVAR")) {
			numberStr = prefixZeroForCount(str, 3);
		}
		str = numberStr+str;
		}	
		return str ;
	}

	private String prefixZeroForCount(String str, int totalLength){
		String stringLengthStr = str;
		if (str!=null) {
		int stringLength = str.length();
		stringLengthStr = new Integer(stringLength).toString();
		int stringLengthStrLength = stringLengthStr.length();
		if (stringLengthStr.equals("0")) {stringLengthStrLength=0;}
		if(stringLengthStrLength<totalLength+1){
			while (stringLengthStrLength<=totalLength-1){
				stringLengthStr="0"+stringLengthStr;
				stringLengthStrLength=stringLengthStrLength+1;
			}
			
		}
		}
		return stringLengthStr;
	}
	public String formatNumeric(String inputStr, int toPadLength){
		String paddedString="";
		for(int t=0;t<toPadLength;t++)
		{
			paddedString=paddedString+"0";
		}
		return paddedString+inputStr;
		
		
	}

	public String formatAlphaNumeric(String inputStr, int toPadLength){
		String paddedString="";
		//int remainingPadLength= toPadLength;
		for(int t=0;t<toPadLength;t++)
		{
			paddedString=paddedString+" ";
		}
		return (inputStr+paddedString).toUpperCase();
		
		
	}
    public HashMap<String, IsoMessageGenericRecord> getHash(){
   	 HashMap<String, IsoMessageGenericRecord> hashMap = new HashMap<String, IsoMessageGenericRecord>(); 
		 for(int r=0; r<isoMessageGenericRecordList.size();r++){
			 IsoMessageGenericRecord mgr = isoMessageGenericRecordList.get(r);
			hashMap.put(mgr.getIsoDataElement(), mgr);
		 }
	return hashMap;
	 }
    public long getHexaValue(Long lNumber){
    	long i=0;
    	if (lNumber != null) {
    		i= Long.parseLong(lNumber.toString(),16);
    	}
    	return i;
    }

    public String getHexaValue(String lNumber){
    	String i="";
    	if (lNumber != null) {
    		i= new BigInteger(lNumber,16).toString();
    	}
    	return i;
    }
    
    public String constructBitmap() {
    	ArrayList pCodes = getAllPCodes();
    	TreeMap<Integer, Integer> tm = Bitmapper.constructBitmapTemplateHash();
    	String bitmap ="";
    	
    	for(int j=0;j<pCodes.size();j++) {
    			String temp = (String)pCodes.get(j);
    			tm.put(new Integer(temp),new Integer(1));
    	}
    	for( int i=1;i<(tm.size()+1);i++) {
    		Integer myInteger = tm.get(i);
    		bitmap=bitmap+myInteger.toString();
    	}
    	return bitmap;
	
    }
    
    public ArrayList getAllPCodes(){
    	ArrayList al = new ArrayList();
		for (int j =0;j<isoMessageGenericRecordList.size();j++){
			IsoMessageGenericRecord isoMessageGenericRecord = isoMessageGenericRecordList.get(j);
			String temp= isoMessageGenericRecord.getIsoDataElement();
			String firstChar = temp.substring(0,1);
			if (firstChar.equals("P")) {
				String s = temp.substring(1).trim();
				al.add(s);
			}
		}
    	return al;
    }

    public int compare(Object o1, Object o2){
		 IsoMessageGenericRecord f1= (IsoMessageGenericRecord)o1;
		 IsoMessageGenericRecord f2= (IsoMessageGenericRecord)o2;
		 if (f1.getIsoMessageSortOrder()>f2.getIsoMessageSortOrder()) 
		 {
			 return 1; 
		 }
		 else
		 {
			 return -1;
		 }
		 
	 }

	public String getMessageTypeIdentifier() {
		return messageTypeIdentifier;
	}

	public void setMessageTypeIdentifier(String messageTypeIdentifier) {
		this.messageTypeIdentifier = messageTypeIdentifier;
	}
    
  /*  public String formatValue(IsoMessageGenericRecord isoMessageGenericRecord){
		String str = isoMessageGenericRecord.getIsoMessageValue();
		int definedLength = isoMessageGenericRecord.getDataLength();
		String definedLengthString = new Integer(definedLength).toString();
		int definedLenStrLen = definedLengthString.length();
		if(definedLengthString.equals("0")) {
			definedLenStrLen=0;
		}
		if (str==null) str="";
		int stringLength = str.length();
		if (isoMessageGenericRecord.getMessageDataType().equals("N")){
			if ( (stringLength<definedLength) )	 {
			// As it is numeric Left Pad with 0s ( Right justified)
				int toPadLength =0;
				if(stringLength==0) {
					toPadLength = definedLength;
				}
				else
				{
					toPadLength = definedLength - stringLength+definedLenStrLen;
				}  
				str = formatNumeric(str,toPadLength);
				if(( isoMessageGenericRecord.getIsoFormatTypeDescr().equals("LLVAR")) ||(isoMessageGenericRecord.getIsoFormatTypeDescr().equals("LLLVAR"))){
					str = str.substring(definedLenStrLen);
					str = definedLengthString+str;
				}			
			}
			else if ((stringLength > definedLength)){
			//we need to truncate ??
				
			}
		}
		else if ( (isoMessageGenericRecord.getMessageDataType().equals("A")) || (isoMessageGenericRecord.getMessageDataType().equals("ANS"))  ){
			// Handle for Alphanumeric
			if ( (stringLength<definedLength) )	 {
				// As it is numeric Left Pad with 0s ( Right justified)
					int toPadLength =definedLength-stringLength+definedLenStrLen;  
					str = formatAlphaNumeric(str,toPadLength);
					if(( isoMessageGenericRecord.getIsoFormatTypeDescr().equals("LLVAR")) ||(isoMessageGenericRecord.getIsoFormatTypeDescr().equals("LLLVAR"))){
						str = str.substring(definedLenStrLen);
						str = definedLengthString+str;
					}					
				}
			
				else if ((stringLength> definedLength)){
				//we need to truncate ??
				
				}
		}
		return str ;
	}*/
    
}
