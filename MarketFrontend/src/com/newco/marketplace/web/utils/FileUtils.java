package com.newco.marketplace.web.utils;
 
import java.io.File;
import java.io.FileInputStream;
import org.apache.log4j.Logger;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collection;
import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;
import eu.medsea.mimeutil.TextMimeDetector;
import eu.medsea.mimeutil.TextMimeType;
import eu.medsea.util.EncodingGuesser;
 
public class FileUtils {
 
	private static Logger logger = Logger.getLogger(FileUtils.class);
	private List<String> allowedTypes = new ArrayList<String> (); 
 
 
	private boolean handlerRegistered;

	static public FileUpload updateDetails(
			MultiPartRequestWrapper multiPartRequestWrapper, String fieldName) {
		FileUpload fileUpload = null;
		byte[] bytes = null;
		try {
			fileUpload = new FileUpload();
			String fileName = getFileName(multiPartRequestWrapper, fieldName);
			fileUpload.setCredentialDocumentExtention(getContentType(fileName));
			fileUpload.setCredentialDocumentFileName(fileName);
			File file = getFile(multiPartRequestWrapper, fieldName);
 			
 			if (file != null) {
				bytes = getBytes(file);
				fileUpload.setCredentialDocumentBytes(bytes); 
			}

			if (bytes != null) {
				fileUpload.setCredentialDocumentFileSize(bytes.length);
			}
		} 
		
			catch (Exception ex) {
		
			logger.error(ex.getMessage(), ex);
			return null;
		} 
		
		return fileUpload;
	}
 
	static public FileUpload updateDetailsforFile(
			MultiPartRequestWrapper multiPartRequestWrapper, String fieldName) {
		FileUpload fileUpload = null;
		byte[] bytes = null;
		boolean check = true; 
		try {
			fileUpload = new FileUpload();
			String fileName = getFileName(multiPartRequestWrapper, fieldName);
			fileUpload.setCredentialDocumentExtention(getContentType(fileName));
			fileUpload.setCredentialDocumentFileName(fileName);
			File file = getFile(multiPartRequestWrapper, fieldName);
 			FileUtils fileUtils = new FileUtils();
 			check=fileUtils.checkAttachment(file);
 			if(check==false)
 			{
 				fileUpload.setCredentialDocumentExtention(null);	
 			}
 		
 			 
 			
 			if (file != null) {
				bytes = getBytes(file); 
				fileUpload.setCredentialDocumentBytes(bytes); 
			}

			if (bytes != null) {
				fileUpload.setCredentialDocumentFileSize(bytes.length);
			}
		} 
		
			catch (Exception ex) {
		
			logger.error(ex.getMessage(), ex);
			return null;
		}
		
		return fileUpload;
	}

	static public byte[] getBytes(File file) {
		byte[] bytes;
		try {
			if (file == null || !file.isFile()) {
				return null;
			}

			long fileLength = file.length();
			if (fileLength > 0) {
				bytes = new byte[(int) fileLength];
				FileInputStream fileInputStream = new FileInputStream(file);
				fileInputStream.read(bytes);
				return bytes;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return null;
	}

	static public String getFileName(MultiPartRequestWrapper multiPartRequestWrapper, String fieldName) {
		String fileName = null;
		String[] fileNames = null;
		try {
			fileNames = multiPartRequestWrapper.getFileNames(fieldName);
			if (fileNames == null || fileNames.length <= 0) {
				return null;
			}
			fileName = fileNames[0];
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			return null;
		}
		return fileName;
	}

	static public File getFile(MultiPartRequestWrapper multiPartRequestWrapper, String fieldName) {
		File file = null;
		File[] files = null;
		try {
			files = multiPartRequestWrapper.getFiles(fieldName);
			if (files == null || files.length <= 0) {
				return null;
			}
			file = files[0];
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			return null;
		}
		return file;
	}

	static public String getContentType(String fileName) {
		String contentType = null;
		try {
			if (fileName != null && fileName.trim().length() > 0) {
				int position = fileName.lastIndexOf(".");
				if (position > 0) {
					String format = fileName.substring(position + 1).trim();
					if(format.equalsIgnoreCase("a")) // check the output file// type
						return "text/a";
					else if(format.equalsIgnoreCase("jpg"))
						return "image/jpeg";
					else if(format.equalsIgnoreCase("doc"))
						return "application/msword";
					else if(format.equalsIgnoreCase("txt"))
						return "text/plain";
					else if(format.equalsIgnoreCase("pdf"))
						return "application/pdf";
					else if(format.equalsIgnoreCase("bmp"))
						return "image/bmp";
					else if(format.equalsIgnoreCase("gif"))
						return "image/gif";
					else if(format.equalsIgnoreCase("image_gif"))
						return "image/gif";
					else if(format.equalsIgnoreCase("image_jpeg"))
						return "image/jpeg";
					else if(format.equalsIgnoreCase("image_bmp"))
						return "image/bmp";
					else if(format.equalsIgnoreCase("xml"))
						return "text/xml";
					else
						return null;
				}
			}
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			return null;
		}
		return contentType;
	}

	static public String getFileExtension(String fileName) {
		String extension = null;
		try {
			if (fileName != null && fileName.trim().length() > 0) {
				int position = fileName.lastIndexOf(".");
				if (position > 0) {
					extension = fileName.substring(position + 1);
				}
			}
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			return null;
		}
		return extension;
	}

	static public boolean isValidExtn(String strVal) {
		boolean boolValid = true;
		if (strVal != null && strVal.trim().length() > 0) {
			if (!(strVal.trim().equalsIgnoreCase("txt")
					|| strVal.trim().equalsIgnoreCase("gif")
					|| strVal.trim().equalsIgnoreCase("jpg")
					|| strVal.trim().equalsIgnoreCase("jpeg")
					|| strVal.trim().equalsIgnoreCase("doc")
					|| strVal.trim().equalsIgnoreCase("bmp") || strVal.trim()
					.equalsIgnoreCase("pdf"))) {
				boolValid = false;
			}
		}
		return boolValid;
	} 
	
	
	public boolean checkAttachment(File file){

		if (!isValidMimeType(file)) {
			return false; 
	
			
		}
		return true;
		
		
	}
	public boolean checkAttachmentForImage(File file){ 
		if (!isValidMimeTypeForImage(file)) {
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
	private boolean isValidMimeTypeForImage(File attachment) {
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
			getAllowedMimetypesForImage(); 
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
		
	}     
	public void getAllowedMimetypesForImage() {
 
		allowedTypes.add("image/jpeg");
		allowedTypes.add("image/jpg");
		allowedTypes.add("image/pjpeg"); 
		allowedTypes.add("image/png");
		allowedTypes.add("image/bmp"); 
		allowedTypes.add("image/gif");
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
