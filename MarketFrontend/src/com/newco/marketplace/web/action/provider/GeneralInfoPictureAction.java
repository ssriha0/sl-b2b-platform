/**
 * 
 */
package com.newco.marketplace.web.action.provider;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.provider.ProviderDocumentVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.action.login.LoginAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.provider.IProviderProfilePagesDelegate;
import com.newco.marketplace.web.utils.AttachmentException;
import com.newco.marketplace.web.utils.FileUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ActionContext;

/** 
 * @author hoza
 *
 */
public class GeneralInfoPictureAction extends SLBaseAction implements
		ServletRequestAware, SessionAware {
	 
	
	private HttpServletRequest request;
	private Map sSessionMap;
	private HttpSession session;
	private IProviderProfilePagesDelegate providerProfilePagesDelegate;
	private File photoDoc;
	private String photoDocContentType; //The content type of the file
    private String photoDocFileName; 
    private String errorMessage;
    private String photoDocFileSize; 
    private static final Logger logger = Logger.getLogger(GeneralInfoPictureAction.class.getName());

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
	 */
	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;

	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
	 */
	public void setSession(Map arg0) {
		this.sSessionMap = arg0;		

	}
	
	
	
	
	
	public String execute() throws Exception {
		String resourceid =  (String) ServletActionContext.getRequest().getParameter(RESOURCE_ID);
		String fileName = null;
		Long fileSize = null;
		if(resourceid == null ) {
			//try to grab from session.. to me its a bad idea.. but whatever
			resourceid = (String) ActionContext.getContext().getSession().get("resourceId");
		}
		ProviderDocumentVO documentVO = new ProviderDocumentVO();
		documentVO  = providerProfilePagesDelegate.getPrimaryPicture(Integer.parseInt(resourceid));
		DocumentVO selectedImage = documentVO.getDocDetails();
		if(selectedImage!=null){
			fileName = selectedImage.getFileName();
			fileSize = selectedImage.getDocSize();
		}

		ServletActionContext.getRequest().setAttribute(RESOURCE_ID, resourceid);
		ServletActionContext.getRequest().setAttribute(FILE_SIZE, fileSize);
		ServletActionContext.getRequest().setAttribute(FILE_NAME, fileName);
		return SUCCESS;
	}
	


	public String pictureUpload() throws Exception {
		boolean check=true; 
		try{
			
			ProviderDocumentVO pdoc = new ProviderDocumentVO();
			DocumentVO documentVO = new DocumentVO();
			
			String resourceid =  (String) ServletActionContext.getRequest().getParameter(RESOURCE_ID);
//			String acceptTerms = (String) ServletActionContext.getRequest().getParameter("acceptPhoto");
			
			List<String> actionErrors = (List<String>) this.getActionErrors();
			List<String> newActionErrors = new ArrayList <String>();
			
			boolean processUpload = true;
			
			Long fileSize=photoDoc.length();
			this.photoDocFileSize =fileSize.toString();
			
			if(photoDoc == null) {
				processUpload = false;
			}
			if (actionErrors.size() >0){
				for (String msg : actionErrors) {
					if (StringUtils.contains(msg, "the request was rejected because its size")) {
						//newActionErrors.add
						this.setErrorMessage("The file you attempted to upload exceeds the 5MB maximum allowed limit");
						//setActionErrors(newActionErrors);
						processUpload = false;
						return "error";
					}
				}
			}
//			if (!"true".equals(acceptTerms)) 
//			{
//					newActionErrors.add("You must agree to the conditions.");
//					setActionErrors(newActionErrors);
//					processUpload = false;
//			} 

			if (processUpload){
				pdoc.setResourceId(Integer.parseInt(resourceid));
				pdoc.setPrimaryInd(Boolean.TRUE);
				pdoc.setCreatedDate(new Date());
				pdoc.setModifiedDate(new Date());
				pdoc.setReviewedInd(Boolean.FALSE); 
				documentVO.setDocument(photoDoc);
				FileUtils fileUtils=new FileUtils();
				check=fileUtils.checkAttachmentForImage(photoDoc);  
				if(check==false)  
				{
					throw new AttachmentException("Bad File Type"); 
				}
				documentVO.setBlobBytes(getBlob(photoDoc)); 
				    
				
				documentVO.setFileName(getPhotoDocFileName());
				documentVO.setFormat(getPhotoDocContentType());
				documentVO.setDocSize(photoDoc.length());
				documentVO.setDocCategoryId(Constants.DocumentTypes.CATEGORY.PROVIDER_PHOTO);
				ProcessResponse pr = new ProcessResponse();
				if ( ((Boolean)request.getSession().getAttribute(SOConstants.IS_LOGGED_IN)).booleanValue() ) {
					
					//do upload only 
					
					SecurityContext sctx = (SecurityContext)request.getSession().getAttribute("SecurityContext");
				
					
					pdoc.setModifiedBY(	sctx.getUsername());
					documentVO.setEntityId(Integer.parseInt(resourceid));
					documentVO.setRoleId(sctx.getRoleId());
					pdoc.setDocDetails(documentVO);
					pr = providerProfilePagesDelegate.uploadResourcePicture(pdoc);
				}
				if (pr!=null){
					if (pr.getCode().equalsIgnoreCase(SO_DOC_SIZE_EXCEEDED_RC) ||pr.getCode().equalsIgnoreCase(SO_DOC_INVALID_FORMAT)){
						ResourceBundle rb = ResourceBundle.getBundle("/resources/properties/servicelive_copy");
						this.errorMessage = rb.getObject("error.msg." + pr.getCode()).toString();
						return "error";
				}
				}
			}
			
			 
		} 
			catch(AttachmentException e){
			logger.error("Error occured while trying to save attachment",e);
			this.errorMessage="Unexpected error occurred while trying to save attachment";
			return "error"; 
		}
				catch(Exception e){
			logger.error("Error occured while trying to save attachment",e);
			this.errorMessage="Unexpected error occurred while trying to save attachment";
			return "error";
		}
		return "uploadsuccess";
	}
	
	private List<String> validatePicture() {
		List<String> validateErrors = new ArrayList <String>();
		if(photoDoc == null) {
			validateErrors.add("Please use browse button to select file to upload");
		}
		else {
			
			
			
		}
		
		return validateErrors;
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

	
	
	/*private void setErrorMessage(ProcessResponse pr){
		String errorMessage = "";
		
		if (pr.getCode().equalsIgnoreCase(SO_DOC_SIZE_EXCEEDED_RC) ||pr.getCode().equalsIgnoreCase(SO_DOC_INVALID_FORMAT)){
				ResourceBundle rb = ResourceBundle.getBundle("/resources/properties/servicelive_copy");
				errorMessage = rb.getObject("error.msg." + pr.getCode()).toString();
				addActionError(errorMessage);
		}

	}*/
	/**
	 * @return the providerProfilePagesDelegate
	 */
	public IProviderProfilePagesDelegate getProviderProfilePagesDelegate() {
		return providerProfilePagesDelegate;
	}

	/**
	 * @param providerProfilePagesDelegate the providerProfilePagesDelegate to set
	 */
	public void setProviderProfilePagesDelegate(
			IProviderProfilePagesDelegate providerProfilePagesDelegate) {
		this.providerProfilePagesDelegate = providerProfilePagesDelegate;
	}

	/**
	 * @return the photoDoc
	 */
	public File getPhotoDoc() {
		return photoDoc;
	}

	/**
	 * @param photoDoc the photoDoc to set
	 */
	public void setPhotoDoc(File photoDoc) {
		this.photoDoc = photoDoc;
	}

	/**
	 * @return the photoDocContentType
	 */
	public String getPhotoDocContentType() {
		return photoDocContentType;
	}

	/**
	 * @param photoDocContentType the photoDocContentType to set
	 */
	public void setPhotoDocContentType(String photoDocContentType) {
		this.photoDocContentType = photoDocContentType;
	}

	/**
	 * @return the photoDocFileName
	 */
	public String getPhotoDocFileName() {
		return photoDocFileName;
	}

	/**
	 * @param photoDocFileName the photoDocFileName to set
	 */
	public void setPhotoDocFileName(String photoDocFileName) {
		this.photoDocFileName = photoDocFileName;
	}


	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getPhotoDocFileSize() {
		return photoDocFileSize;
	}

	public void setPhotoDocFileSize(String photoDocFileSize) {
		this.photoDocFileSize = photoDocFileSize;
	}

}
