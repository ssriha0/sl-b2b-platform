package com.newco.marketplace.dto.vo.buyerUploadScheduler;

import java.util.Date;
import java.sql.Blob;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream ;
import java.io.File;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sears.os.vo.SerializableBaseVO;
public class UploadFileVO extends SerializableBaseVO{
	protected final Log logger = LogFactory.getLog(getClass());
	private int buyer_id;
	private String buyer_first_name;
	private String buyer_second_name;
	private String name_of_file;
	private int no_of_so;
	private Date created_date;
	//private InputStream file_content;
	private  byte[] file_content;
	private  byte[] logo;
	private int buyer_resource_contact_id;
	private int buyer_resource_id;
	private int file_id;
	private Integer documentId;
	private String creatorUserName;
	
	public String getCreatorUserName() {
		return creatorUserName;
	}

	public void setCreatorUserName(String creatorUserName) {
		this.creatorUserName = creatorUserName;
	}

	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	public void setBuyer_id(int buyerid){
	 this.buyer_id=buyerid;
	}
	
	public int getBuyer_id(){
		return this.buyer_id;
	}

	
	public  void setBuyer_first_name(String firstname){
		this.buyer_first_name=firstname;
	}
	
	public String getBuyer_first_name(){
		return this.buyer_first_name;
	}
	public  void setBuyer_second_name(String secondname){
		this.buyer_second_name=secondname;
	}
	
	public String getBuyer_second_name(){
		return this.buyer_second_name;
	}
	public void setNo_of_so(int noofso){
		this.no_of_so=noofso;
	}
	public int getNo_of_so(){
		return this.no_of_so;
	}
	public void setName_of_file(String filename){
		this.name_of_file=filename;
		
	}
	
	public String getName_of_file(){
		return this.name_of_file;
	}
public byte[] getfile_content(){
	try{
	//InputStream fin = new java.io.FileInputStream("D:/upload/myfile.csv");
	//this.file_content=fin;
	return this.file_content;
	}
	catch(Exception e){
	logger.info("exception in getfile_content ")	;
	e.printStackTrace();
	return null;
	}
	
}
public void setfile_content(byte[] uploadedfilename){
	try{
		String str = "";
		//byte[] bytes = new byte[]; 
	      
		
	//InputStream fin = new java.io.FileInputStream(uploadedfilename);
	this.file_content=uploadedfilename;
	}
	catch(Exception e){
		logger.info("exception in setfile_content ")	;
		e.printStackTrace();
	}
	//uploadedfile.
}

public void setBlobFromFile(File uploadedfile){
	try{
	 InputStream is = new FileInputStream(uploadedfile);
	 long length = uploadedfile.length();
	 if (length > Integer.MAX_VALUE) {
         // File is too large
     }
 
	//int tscfdsx=is.read(this.file_content);
     // Create the byte array to hold the data
     byte[] bytes = new byte[(int)length];
 
     // Read in the bytes
     int offset = 0;
     int numRead = 0;
     while (offset < bytes.length
            && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
         offset += numRead;
     }
     if (offset < bytes.length) {
         throw new IOException("Could not completely read file "+uploadedfile.getName());
     }
 
     // Close the input stream and return bytes
     is.close();

     this.file_content=bytes;
	}
	catch(Exception e){
		
	}
}
public void setCreated_date(Date curdate){
	this.created_date= curdate;
	
}

public Date getCreated_date(){
	return this.created_date;
}

public int getBuyer_resource_contact_id() {
	return buyer_resource_contact_id;
}

public void setBuyer_resource_contact_id(int buyer_resource_contact_id) {
	this.buyer_resource_contact_id = buyer_resource_contact_id;
}

public int getBuyer_resource_id() {
	return buyer_resource_id;
}

public void setBuyer_resource_id(int buyer_resource_id) {
	this.buyer_resource_id = buyer_resource_id;
}

public byte[] getLogo() {
	return logo;
}

public void setLogo(byte[] logo) {
	this.logo = logo;
}

public int getFile_id() {
	return file_id;
}

public void setFile_id(int file_id) {
	this.file_id = file_id;
}


}


