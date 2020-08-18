package com.mobile.test;

import java.io.File;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

public class UploadFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UploadFile fileUpload = new UploadFile () ;
        File file = new File("/home/cancel_pii.gif") ;
        //Upload the file
        try {
        	System.out.println("file name :::"+file.getName());
			fileUpload.executeMultiPartRequest("http://151.149.159.235:8080/mobile/v1.0/providers/serviceorder/uploadDocument",
			        file, file.getName(), "File Uploaded :: cancel_pii.gif") ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@SuppressWarnings("deprecation")
	public void executeMultiPartRequest(String urlString, File file, String fileName, String fileDescription) throws Exception
    {
        HttpClient client = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost (urlString) ;
        try
        {
            //Set various attributes
            MultipartEntity multiPartEntity = new MultipartEntity () ;
            multiPartEntity.addPart("fileDescription", new StringBody(fileDescription != null ? fileDescription : "")) ;
            multiPartEntity.addPart("fileName", new StringBody(fileName != null ? fileName : file.getName())) ;
  
            FileBody fileBody = new FileBody(file, "application/octect-stream") ;
            //Prepare payload
            multiPartEntity.addPart("attachment", fileBody) ;
  
            //Set to request body
            postRequest.setEntity(multiPartEntity) ;
             
            //Send request
            HttpResponse response = client.execute(postRequest) ;
             
            //Verify response if any
            if (response != null)
            {
                System.out.println(response.getStatusLine().getStatusCode());
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace() ;
        }
    }

}
