package com.servicelive.spn.buyer.network;

import static com.servicelive.spn.common.SPNBackendConstants.DOC_CATEGORY_ID_FOR_SPN;
import static com.servicelive.spn.constants.SPNActionConstants.DOC_MAXIMUM_UPLOAD_SIZE_SPN;
import static com.servicelive.spn.constants.SPNActionConstants.RESULT_UPLOAD_DOCUMENT;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import com.opensymphony.xwork2.ModelDriven; 
import com.opensymphony.xwork2.Preparable;
import com.servicelive.spn.core.SPNBaseAction;
import com.servicelive.domain.common.Document;
import com.servicelive.spn.services.DocumentServices;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collection;
import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;
import eu.medsea.mimeutil.TextMimeDetector;
import eu.medsea.mimeutil.TextMimeType;
import eu.medsea.util.EncodingGuesser; 
   
 
 
/**
 * 
 *  
 *
 */
public class SPNCreateNetworkUploadDocumentAction extends SPNBaseAction implements Preparable, ModelDriven<SPNCreateNetworkModel>
{
	private static final long serialVersionUID = -2373625036264742599L;
	private SPNCreateNetworkModel model = new SPNCreateNetworkModel();
	
	private DocumentServices documentServices;
	private File photoDoc;
	private String photoDocContentType; //The content type of the file
    private String photoDocFileName;
    private List<String> allowedTypes = new ArrayList<String> (); 
    private boolean handlerRegistered; 

	public SPNCreateNetworkModel getModel()
	{
		return model;
	}
	
	public void prepare() throws Exception
	{
		 System.out.println(this.getActionErrors());
	}
	
	/**
	 * 
	 * @return String
	 */
	public String deleteSPNDocumentAjax()
	{
		model.getUploadDocData().setUploadSuccess(false);
		if (model.getUploadDocData().getDeleteSPNDocId().intValue() > 0)
		{
			if (documentServices.deleteSPNDocument(model.getUploadDocData().getDeleteSPNDocId()))
			{
				model.getUploadDocData().setUploadSuccess(true);
			}
		}
		else if (model.getUploadDocData().getDeleteDocId().intValue() > 0)
		{
			if (documentServices.deleteDocument(model.getUploadDocData().getDeleteDocId()))
			{
				model.getUploadDocData().setUploadSuccess(true);
			}
		}
		
		return "deleteDoc";
	}
	/**
	 *  
	 * @return String
	 */
	
	 
	public String uploadDocumentAjax() {
		boolean check = true;
		model = getModel();
		Document uploadedDoc = new Document();
		SPNCreateNetworkDocumentsVO uploadDocData = new SPNCreateNetworkDocumentsVO();

		try {

			byte[] blob = getBlob(photoDoc);
			Integer size = Integer.valueOf(blob.length);

			check = checkAttachment(photoDoc);
			if (check == false) {
				uploadDocData.setUploadSuccess(false);
			}
			if (size.intValue() > DOC_MAXIMUM_UPLOAD_SIZE_SPN.intValue()) {
				uploadDocData.setUploadSuccess(false);
				uploadDocData
						.setUploadDocErrorStr("File is larger than the maximum file size!");
			} else {
				uploadedDoc.setDocSize(size);
				uploadedDoc.setBlobBytes(blob);
				uploadedDoc.setDocumentFileName(photoDocFileName);
				uploadedDoc.setDocumentContentType(photoDocContentType);
				uploadedDoc.setTitle(model.getUploadDocData().getUploadDocTitle());
				uploadedDoc.setDescr(model.getUploadDocData().getUploadDocDesc());
				uploadedDoc.setDocCategoryId(DOC_CATEGORY_ID_FOR_SPN);
				
				documentServices.saveDocument(uploadedDoc);
				Integer docId = uploadedDoc.getDocumentId();
				
				
				uploadDocData.setUploadDocId(docId);
				uploadDocData.setUploadDocType(model.getUploadDocData().getUploadDocType());
				uploadDocData.setUploadSuccess(true);
			}
		} 
		catch (Exception e) {
			uploadDocData.setUploadSuccess(false);
		}
		
		/* 
		if (!model.getUploadDocData().getUploadDocType().equals(DOC_TYPE_ELECTRONIC_AGREEMENT))
		{
			try {
				byte[] blob = getBlob(photoDoc);
				Integer size = Integer.valueOf(blob.length);
				
				if (size.intValue() > DOC_MAXIMUM_UPLOAD_SIZE_SPN.intValue())
				{
					uploadDocData.setUploadSuccess(false);
					uploadDocData.setUploadDocErrorStr("File is larger than the maximum file size!");
				}
				else
				{
					uploadedDoc.setDocSize(size);
					uploadedDoc.setDocument(blob);
					uploadedDoc.setDocumentFileName(photoDocFileName);
					uploadedDoc.setDocumentContentType(photoDocContentType);
					uploadedDoc.setTitle(model.getUploadDocData().getUploadDocTitle());
					uploadedDoc.setDescr(model.getUploadDocData().getUploadDocDesc());
					uploadedDoc.setDocCategoryId(DOC_CATEGORY_ID_FOR_SPN);
					
					documentServices.saveDocument(uploadedDoc);
					Integer docId = uploadedDoc.getDocumentId();
					
					
					uploadDocData.setUploadDocId(docId);
					uploadDocData.setUploadDocType(model.getUploadDocData().getUploadDocType());
					uploadDocData.setUploadSuccess(true);
				}
			} catch (Exception e) {
				uploadDocData.setUploadSuccess(false);
			}
		}
		else
		{
			try {
				
				byte[] blob = model.getUploadDocData().getUploadDocES().getBytes();
				Integer size = Integer.valueOf(blob.length);
				
				uploadedDoc.setDocSize(size);
				uploadedDoc.setDocument(blob);
				uploadedDoc.setDocumentFileName("ElectronicSigniture");
				uploadedDoc.setDocumentContentType("text/html");
				uploadedDoc.setTitle(model.getUploadDocData().getUploadDocTitle());
				uploadedDoc.setDescr(model.getUploadDocData().getUploadDocDesc());
				uploadedDoc.setDocCategoryId(DOC_CATEGORY_ID_FOR_SPN);
					
				documentServices.saveDocument(uploadedDoc);
				Integer docId = uploadedDoc.getDocumentId();
					
					
				uploadDocData.setUploadDocId(docId);
				uploadDocData.setUploadDocType(model.getUploadDocData().getUploadDocType());
				uploadDocData.setUploadSuccess(true);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		*/ 
		
		model.setUploadDocData(uploadDocData);
		return RESULT_UPLOAD_DOCUMENT;
	}
	
	private byte[] getBlob(File file) throws Exception{
		
		InputStream is = new FileInputStream(file);
		
		// Get the size of the file
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
        	throw new Exception("File too big "); // File is too large
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
        is.read(bytes);
       
        // Close the input stream and return bytes
        is.close();
        return bytes;
		
	}
	/**
	 * 
	 * @return File
	 */
	public File getPhotoDoc() {
		return photoDoc;
	}
	/**
	 * 
	 * @param photoDoc
	 */
	public void setPhotoDoc(File photoDoc) {
		this.photoDoc = photoDoc;
	}
	/**
	 * 
	 * @return String
	 */
	public String getPhotoDocContentType() {
		return photoDocContentType;
	}
	/**
	 * 
	 * @param photoDocContentType
	 */
	public void setPhotoDocContentType(String photoDocContentType) {
		this.photoDocContentType = photoDocContentType;
	}
	/**
	 * 
	 * @return String
	 */
	public String getPhotoDocFileName() {
		return photoDocFileName;
	}
	/**
	 * 
	 * @param photoDocFileName
	 */
	public void setPhotoDocFileName(String photoDocFileName) {
		this.photoDocFileName = photoDocFileName;
	}
	/**
	 * 
	 * @return DocumentServices
	 */
	@Override
	public DocumentServices getDocumentServices() {
		return documentServices;
	}
	/**
	 * 
	 * @param documentServices
	 */
	@Override
	public void setDocumentServices(DocumentServices documentServices) {
		this.documentServices = documentServices;
	}
	public boolean checkAttachment(File file){

		if (!isValidMimeType(file)) {
			return false; 
	
			
		}
		return true;
	}
		private boolean isValidMimeType(File attachment) {
			boolean returnVal = false;  
	        if(!handlerRegistered){
	            String[] encodings = {"ASCII", "ISO-8859-15", "ISO-8859-1", "UTF-8"};
	            EncodingGuesser.setSupportedEncodings(Arrays.asList(encodings));
	            TextMimeDetector.setPreferredEncodings(encodings);
	            MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
	            handlerRegistered = true;  
	         
	        }  
			@SuppressWarnings("unchecked")
			Collection<MimeType> mimeTypes = MimeUtil.getMimeTypes(attachment);
			  for (MimeType type : mimeTypes) {
				  
				if (type instanceof TextMimeType) {
					returnVal = true;
					break; 
				}
				getAllowedMimetypes(); 
				if (allowedTypes.contains(type.toString())) {
					returnVal = true;
					break;
				}  			
			} 
			return returnVal; 
		}   
		public void getAllowedMimetypes() {

			allowedTypes.add("image/jpeg");
			allowedTypes.add("application/msword");
			allowedTypes.add("text/plain");
			allowedTypes.add("application/pdf");
			allowedTypes.add("image/bmp");
			allowedTypes.add("image/gif");
			allowedTypes.add("text/xml");
			allowedTypes.add("text/a");
			allowedTypes.add("image/jpg");
			allowedTypes.add("image/pjpeg"); 
			allowedTypes.add("image/png");
			allowedTypes.add("image/tiff");
			allowedTypes.add("image/x-ms-bmp");  
			allowedTypes.add("application/octet-stream");   
		}
		public List<String> getAllowedTypes() {
			return allowedTypes;
		}

		public void setAllowedTypes(List<String> allowedTypes) {
			this.allowedTypes = allowedTypes;
		}      
}