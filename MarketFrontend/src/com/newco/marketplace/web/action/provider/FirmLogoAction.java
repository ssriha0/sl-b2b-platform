/**
 * 
 */
package com.newco.marketplace.web.action.provider;

import static com.newco.marketplace.web.action.provider.ProviderProfilePageConstants.PROVIDER_PHOTO;

import java.awt.Image;
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
import javax.swing.ImageIcon;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.provider.ProviderDocumentVO;
import com.newco.marketplace.dto.vo.provider.VendorDocumentVO;
import com.newco.marketplace.util.DocumentUtils;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.action.login.LoginAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.provider.IProviderProfilePagesDelegate;
import com.newco.marketplace.web.utils.AttachmentException;
import com.newco.marketplace.web.utils.FileUtils;
import com.newco.marketplace.web.utils.SecurityChecker;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ActionContext;

/** 
 * @author hoza
 *
 */
public class FirmLogoAction extends SLBaseAction implements
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
    private static final Logger logger = Logger.getLogger(FirmLogoAction.class.getName());

	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;

	}

	public void setSession(Map arg0) {
		this.sSessionMap = arg0;		

	}

	// display the iframe for the logo
	public String display() throws Exception {
		String vendorId =  (String) ServletActionContext.getRequest().getParameter(VENDOR_ID);
		String fileName = null;
		Long fileSize = null;
		VendorDocumentVO documentVO = new VendorDocumentVO();
		// get the logo details for the vendor if any
		documentVO  = providerProfilePagesDelegate.getFirmLogo(Integer.parseInt(vendorId));
		if(null!=documentVO){
		DocumentVO selectedImage = documentVO.getDocDetails();
		if(selectedImage!=null){
			fileName = selectedImage.getFileName();
			fileSize = selectedImage.getDocSize();
		}
		}
		ServletActionContext.getRequest().setAttribute(VENDOR_ID, vendorId);
		ServletActionContext.getRequest().setAttribute(FILE_SIZE, fileSize);
		ServletActionContext.getRequest().setAttribute(FILE_NAME, fileName);
		return SUCCESS;
	}
	
   
	public String loadPhoto() throws Exception {

		try{
			VendorDocumentVO documentVO = new VendorDocumentVO();
			String vendorId =  (String) ServletActionContext.getRequest().getParameter(VENDOR_ID);
			Integer max_width = Integer.valueOf(ServletActionContext.getRequest().getParameter("max_width"));
			Integer max_height = Integer.valueOf(ServletActionContext.getRequest().getParameter("max_height"));
			String link = (String) ServletActionContext.getRequest().getParameter("link");
			String isPopup = (String) ServletActionContext.getRequest().getParameter("isPopup");
			String link_back = new String();
			
			if ("1".equals(link))
			{
				link_back = genLinkBack1(vendorId, isPopup); 
			}
			
			 if( vendorId != null) {
				 documentVO  = providerProfilePagesDelegate.getFirmLogo(Integer.parseInt(vendorId));
			 }
			request.setAttribute(FIRM_LOGO, documentVO);		
			request.setAttribute("max_width", max_width);		
			request.setAttribute("max_height", max_height);
			request.setAttribute("link_back", link_back);
			request.setAttribute(VENDOR_ID, vendorId);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "loadPhoto";
	}	
	private String genLinkBack1(String vendorId, String isPopup)
	{
		String tmpLinkBack = new String(request.getContextPath());
		tmpLinkBack = tmpLinkBack.concat("/firmLogoAction_loadPhoto.action?max_width=65&max_height=65&vendorId=");
		tmpLinkBack = tmpLinkBack.concat(vendorId);
		if("true".equals(isPopup))
		{
			tmpLinkBack= tmpLinkBack.concat("&popup=true");
		}
		
		return tmpLinkBack;
	} 

	
	public String displayPhoto() throws Exception {
		
		String vendorId =  (String) ServletActionContext.getRequest().getParameter(VENDOR_ID);
		VendorDocumentVO documentVO;
		Integer max_width = Integer.valueOf(ServletActionContext.getRequest().getParameter("max_width"));
		Integer max_height = Integer.valueOf(ServletActionContext.getRequest().getParameter("max_height"));
		
		if (vendorId != null)
		{
			documentVO = providerProfilePagesDelegate.getFirmLogo(Integer.parseInt(vendorId)); 
			if (documentVO != null)
			{
				DocumentVO selectedImage = documentVO.getDocDetails();
				if (selectedImage != null && selectedImage.getBlobBytes() != null)
				{
					Image sourceImage = new ImageIcon(selectedImage.getBlobBytes()).getImage();
					Integer actualWidth = sourceImage.getWidth(null);
					Integer actualHeight = sourceImage.getHeight(null);
					if(actualWidth > 0 && actualHeight > 0)
					{
						if (actualWidth < max_width && actualWidth > 0)
						{
							max_width = actualWidth;
						}
						if (actualHeight < max_height && actualHeight > 0)
						{
							max_height = actualHeight;
						}
						selectedImage.setBlobBytes(DocumentUtils.resizeoImage(selectedImage.getBlobBytes(), max_width, max_height));
						SecurityChecker sc = new SecurityChecker();
						if (StringUtils.equals(selectedImage.getFormat(), "image/bmp")
								|| StringUtils.equals(selectedImage.getFormat(), "image/gif")
								|| StringUtils.equals(selectedImage.getFormat(), "image/jpeg")
								|| StringUtils.equals(selectedImage.getFormat(), "image/jpg"))
						{
							
							String format = sc.securityCheck(selectedImage.getFormat());
							ServletActionContext.getResponse().setContentType(format);
						}
						else
						{
							ServletActionContext.getResponse().setContentType("text/html");
						}
						String title = sc.securityCheck(selectedImage.getTitle());
						String header = "attachment;filename=\"" + title + "\"";
						ServletActionContext.getResponse().setHeader("Content-Disposition", header);
						InputStream in = new ByteArrayInputStream(selectedImage.getBlobBytes());
						ServletOutputStream outs = ServletActionContext.getResponse().getOutputStream();
						int bit = 256;
						while ((bit) >= 0)
						{
							bit = in.read();
							outs.write(bit);
						}
						outs.flush();
						outs.close();
						in.close();
					}
					else
					{
						logger.info("displayPhoto() width or height is less than zero for vendorId=" + vendorId);
					}									
				}
				else
				{
					logger.info("displayPhoto() has failed for vendorId=" + vendorId);
				}				
			}
			else
			{
				logger.info("displayPhoto() has failed for vendorId=" + vendorId); 
			}
		}

		return NONE;
	}
	
	public String pictureUpload() throws Exception {
		boolean check=true; 
		try{
			
			VendorDocumentVO vendorDoc = new VendorDocumentVO();
			DocumentVO documentVO = new DocumentVO();
			String vendorId =  (String) ServletActionContext.getRequest().getParameter(VENDOR_ID);			
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
						this.setErrorMessage("The file you attempted to upload exceeds the 5MB maximum allowed limit");
						processUpload = false;
						return "error"; 
					}
				}
			}
			if (processUpload){
				vendorDoc.setVendorId(Integer.parseInt(vendorId));
				vendorDoc.setPrimaryInd(Boolean.TRUE);
				vendorDoc.setCreatedDate(new Date());
				vendorDoc.setModifiedDate(new Date());
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
				documentVO.setDocCategoryId(Constants.DocumentTypes.CATEGORY.LOGO);
				ProcessResponse pr = new ProcessResponse();
				if ( ((Boolean)request.getSession().getAttribute(SOConstants.IS_LOGGED_IN)).booleanValue() ) {
					//do upload only 
					SecurityContext sctx = (SecurityContext)request.getSession().getAttribute("SecurityContext");
					vendorDoc.setModifiedBy(sctx.getUsername());
					vendorDoc.setCreatedBy(sctx.getUsername());
					vendorDoc.setCategoryId(Constants.DocumentTypes.CATEGORY.LOGO);
					documentVO.setEntityId(Integer.parseInt(vendorId));
					documentVO.setVendorId(Integer.parseInt(vendorId));
					documentVO.setRoleId(sctx.getRoleId());
					vendorDoc.setDocDetails(documentVO);
					pr = providerProfilePagesDelegate.uploadFirmLogo(vendorDoc);
				}
				if (pr!=null){
					if (pr.getCode().equalsIgnoreCase(SO_DOC_SIZE_EXCEEDED_RC)){
						ResourceBundle rb = ResourceBundle.getBundle("/resources/properties/servicelive_copy");
						this.errorMessage = rb.getObject("error.msg." + pr.getCode()).toString();
						return "error";
				   }else if (pr.getCode().equalsIgnoreCase(SO_DOC_INVALID_FORMAT)){
					this.errorMessage = "Please upload a valid file type";
					return "error";
			     }		
				}
			}	 
		} 
			catch(AttachmentException e){
			logger.error("Error occured while trying to save attachment",e);
			this.errorMessage="Unexpected error occurred while trying to save attachment.";
			return "error"; 
		}
				catch(Exception e){
			logger.error("Error occured while trying to save attachment",e);
			this.errorMessage="Unexpected error occurred while trying to save attachment.";
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
