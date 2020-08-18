/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 12-Mar-2009	KMSTRSUP   Infosys				1.1
 * 
 * 
 */
package com.newco.marketplace.web.action.simple;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.DocumentUtils;
import com.newco.marketplace.web.action.base.SLSimpleBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.ICreateServiceOrderDelegate;
import com.newco.marketplace.web.delegates.ISOWizardPersistDelegate;
import com.newco.marketplace.web.dto.SODocumentDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderDocAndPhotosDTO;
import com.newco.marketplace.web.utils.SSoWSessionFacility;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * $Revision: 1.2 $ $Author: akashya $ $Date: 2008/05/21 23:32:57 $
 */
public class CreateServiceOrderDocAndPhotosAction extends SLSimpleBaseAction
                 implements Preparable,ModelDriven<CreateServiceOrderDocAndPhotosDTO>,OrderConstants{
	

	private static final long serialVersionUID = 2067218958157835878L;
	
	private static final Logger logger = Logger.getLogger("SOWDocumentsAndPhotosAction");	
	private CreateServiceOrderDocAndPhotosDTO soDocumentsAndPhotosDTO = new CreateServiceOrderDocAndPhotosDTO();
	private ICreateServiceOrderDelegate createServiceOrderDelegate;
	private Integer documentId;
	private String soId;
	private String buyerGuid;
	private String viewImageContentType;

	public CreateServiceOrderDocAndPhotosAction(ICreateServiceOrderDelegate createSODelegate){
		this.createServiceOrderDelegate = createSODelegate;
	}
	
	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		
		soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
		if(soId == null){
			buyerGuid = (String)getSession().getAttribute(Constants.SESSION.SIMPLE_BUYER_GUID);
			if (StringUtils.isBlank(buyerGuid))
			{
				buyerGuid = SSoWSessionFacility.getInstance().createSimpleBuyerGUIDId();
				getSession().setAttribute(Constants.SESSION.SIMPLE_BUYER_GUID,buyerGuid);

			}

		}
		else
		{
			ServiceOrderDTO dto= (ServiceOrderDTO) getSession().getAttribute(THE_SERVICE_ORDER);
			if(dto != null && soId.equals(dto.getSoId()))
			{
				soDocumentsAndPhotosDTO.setPriceModel(dto.getPriceModel());
			}
		}
	}
	/**
	 * This is landing method - comes here for display 
	 * get list of documents and photos  if any for a SO 
	 * @return
	 * @throws Exception
	 */
	public String getDocumentsAndPhotos() throws Exception {

			try{
				
				List<SODocumentDTO> docList = new ArrayList<SODocumentDTO>();
				List<SODocumentDTO> photoDocList = new ArrayList<SODocumentDTO>();
				if(com.newco.marketplace.web.utils.SLStringUtils.isNullOrEmpty(soId)){
					docList = createServiceOrderDelegate.getTemporaryDocumentListBySimpleBuyerId(buyerGuid,
							Constants.DocumentTypes.CATEGORY.REFERENCE);
					photoDocList = createServiceOrderDelegate.getTemporaryDocumentListBySimpleBuyerId(buyerGuid,
							Constants.DocumentTypes.CATEGORY.IMAGE);
				}else{
					docList = createServiceOrderDelegate.getDocumentListBySOId(soId,
                            Constants.DocumentTypes.CATEGORY.REFERENCE, _commonCriteria.getRoleId(), _commonCriteria.getVendBuyerResId());
					// photo doc list
					photoDocList = createServiceOrderDelegate.getDocumentListBySOId(soId,
	                        Constants.DocumentTypes.CATEGORY.IMAGE, _commonCriteria.getRoleId(), _commonCriteria.getVendBuyerResId());

				}
				
				List<SODocumentDTO> documents = new ArrayList<SODocumentDTO>();
				
				
				getSession().setAttribute("photoDocList", photoDocList);

				List<SODocumentDTO> resizedPhotoList = getResizedPhotoDocList(photoDocList);
				soDocumentsAndPhotosDTO.setPhotoDocuments(resizedPhotoList);
				
				
				getSession().setAttribute("resizedPhotoDocList", resizedPhotoList);
				getSession().setAttribute(OrderConstants.SO_ID, soId);
				
				soDocumentsAndPhotosDTO.setDocuments(docList);

			}catch(Exception e){
				e.printStackTrace();
			}
		
		this.logSession();
		// return to simple_document_and_photos.jsp
		return SUCCESS;
		
	}
	
	private void getDocumentsAndPhotosList() throws Exception{
		try{
			
			List<SODocumentDTO> docList = new ArrayList<SODocumentDTO>();
			List<SODocumentDTO> photoDocList = new ArrayList<SODocumentDTO>();
			if(com.newco.marketplace.web.utils.SLStringUtils.isNullOrEmpty(soId)){
				docList = createServiceOrderDelegate.getTemporaryDocumentListBySimpleBuyerId(buyerGuid,
						Constants.DocumentTypes.CATEGORY.REFERENCE);
				photoDocList = createServiceOrderDelegate.getTemporaryDocumentListBySimpleBuyerId(buyerGuid,
						Constants.DocumentTypes.CATEGORY.IMAGE);
			}else{
				docList = createServiceOrderDelegate.getDocumentListBySOId(soId,
                        Constants.DocumentTypes.CATEGORY.REFERENCE, _commonCriteria.getRoleId(), _commonCriteria.getVendBuyerResId());
				// photo doc list
				photoDocList = createServiceOrderDelegate.getDocumentListBySOId(soId,
                        Constants.DocumentTypes.CATEGORY.IMAGE, _commonCriteria.getRoleId(), _commonCriteria.getVendBuyerResId());

			}
			
			List<SODocumentDTO> documents = new ArrayList<SODocumentDTO>();
			
			
			getSession().setAttribute("photoDocList", photoDocList);
			/* List<SODocumentDTO> photoDocList = createServiceOrderDelegate.getDocumentListBySOId(soId,
                    Constants.DocumentTypes.CATEGORY.IMAGE, _commonCriteria.getRoleId(), _commonCriteria.getVendBuyerResId()); */
			// get resized image to display on page
			List<SODocumentDTO> resizedPhotoList = getResizedPhotoDocList(photoDocList);
			soDocumentsAndPhotosDTO.setPhotoDocuments(resizedPhotoList);
			
			
			getSession().setAttribute("resizedPhotoDocList", resizedPhotoList);
			getSession().setAttribute(OrderConstants.SO_ID, soId);
			
			//clearfields();
			//documentUsage(soId);
			
			//List<SODocumentDTO> documents = new ArrayList<SODocumentDTO>();
			soDocumentsAndPhotosDTO.setDocuments(docList);

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * get doc of the buyer of the so ID
	 * @return
	 * @throws Exception
	 */
	public String displayLogoDoc() throws Exception {
		
		String imageDocId = getRequest().getParameter("imageDocId");
		List<SODocumentDTO> imageList = (List<SODocumentDTO>)getSession().getAttribute("resizedPhotoDocList");
		SODocumentDTO selectedImage = null; 
		
		for(SODocumentDTO image : imageList){
			if(image.getDocumentId()!= null && image.getDocumentId().toString().equals(imageDocId)){
				selectedImage = image;
			}
		}
		if(selectedImage == null)
			return SUCCESS;
	    
		
		try{
			
			if ( StringUtils.equals(selectedImage.getFormat(),"image/bmp") ||
				StringUtils.equals(selectedImage.getFormat(),"image/gif") ||
				StringUtils.equals(selectedImage.getFormat(),"image/jpeg") ||
				StringUtils.equals(selectedImage.getFormat(),"image/jpg") ) {
				getResponse().setContentType(selectedImage.getFormat());
			} else {
				getResponse().setContentType("text/html");
			}
		
			String header = "attachment;filename=\""
					+ selectedImage.getName() + "\"";
			getResponse().setHeader("Content-Disposition", header);
			InputStream in = new ByteArrayInputStream(selectedImage.getBlobBytes());
			ServletOutputStream outs = getResponse().getOutputStream();
			int bit = 256;
			while ((bit) >= 0) {
				bit = in.read();
				outs.write(bit);
			}
			outs.flush();
			outs.close();
			in.close();
		} catch (Exception e) {
			logger.error("Error in DisplayBuyerDocumentAction --> displayBuyerDocument()");
		}
		
		// Need to clear 'logoDoc' out of session
		//getSession().removeAttribute("logoDoc");
		return SUCCESS;
	}
	
	
	
	private List<SODocumentDTO> getResizedPhotoDocList(List<SODocumentDTO> photoList) {
		List<SODocumentDTO> resizedPhotoList =  new ArrayList<SODocumentDTO>();
		SODocumentDTO modifiedDocDto = null;
		for(SODocumentDTO docDto : photoList){
			modifiedDocDto = new SODocumentDTO();
			modifiedDocDto.setDesc(docDto.getDesc());
			modifiedDocDto.setDocumentId(docDto.getDocumentId());
			modifiedDocDto.setName(docDto.getName());
			modifiedDocDto.setSize(docDto.getSize());
			
			if(docDto.getBlobBytes()!= null){
				modifiedDocDto.setBlobBytes(DocumentUtils.resizeoImage(docDto.getBlobBytes(), 100, 100));
			}
			
			resizedPhotoList.add(modifiedDocDto);
		}
		
		int imagesCnt = resizedPhotoList.size();
		// 5 is the no of images shown on page(describe & schedule)  
		int blankImageCnt = 5 - imagesCnt;
		// add empty photo doc so tht in front default blank image can be displayed when doc is empty.
		for(int i=0; i < blankImageCnt; i++){
			modifiedDocDto = new SODocumentDTO();
			resizedPhotoList.add(modifiedDocDto);
		}
		return resizedPhotoList;
	}

	public String documentUpload() throws Exception {

		try{
			DocumentVO documentVO = new DocumentVO();
			
			soDocumentsAndPhotosDTO = getModel();
			List<String> actionErrors = (List<String>) this.getActionErrors();
			List<String> newActionErrors = new ArrayList <String>();
			
			boolean processUpload = true;
			
			if (actionErrors.size() >0){
				for (String msg : actionErrors) {
					if (StringUtils.contains(msg, "the request was rejected because its size")) {
						newActionErrors.add("The file you attempted to upload exceeds the 5MB maximum allowed limit");
						setActionErrors(newActionErrors);
						processUpload = false;
					}
				}
			}

			if (processUpload){
				documentVO.setDocument(soDocumentsAndPhotosDTO.getDocument().getUpload());
				documentVO.setSoId(soId);
				documentVO.setFileName(soDocumentsAndPhotosDTO.getDocument().getUploadFileName());
				documentVO.setFormat(soDocumentsAndPhotosDTO.getDocument().getUploadContentType());
				documentVO.setDocSize(soDocumentsAndPhotosDTO.getDocument().getUpload().length());
				documentVO.setDocCategoryId(Constants.DocumentTypes.CATEGORY.REFERENCE);
				ProcessResponse pr = new ProcessResponse();
				if ( ((Boolean)getSession().getAttribute(SOConstants.IS_LOGGED_IN)).booleanValue() ) {
					documentVO.setEntityId(_commonCriteria.getVendBuyerResId());
					documentVO.setRoleId(_commonCriteria.getRoleId());
					documentVO.setCompanyId(_commonCriteria.getCompanyId());
				}
				
				// insert doc temporarily
				if(com.newco.marketplace.web.utils.SLStringUtils.isNullOrEmpty(soId)){
					documentVO.setSoId(buyerGuid);
					pr = createServiceOrderDelegate.insertTempSODocument(documentVO);
				}else{
					pr = createServiceOrderDelegate.insertSODocument(documentVO);
				}
				
				
				//String errorMessage = "";
				 
				if (pr!=null){
					setErrorMessage(pr);
				}
			
			}
			
			getDocumentsAndPhotosList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String photoDocUpload() throws Exception {
		try{
			DocumentVO documentVO = new DocumentVO();
			
			soDocumentsAndPhotosDTO = getModel();
			List<String> actionErrors = (List<String>) this.getActionErrors();
			List<String> newActionErrors = new ArrayList <String>();
			
			boolean processUpload = true;
			
			if (actionErrors.size() >0){
				for (String msg : actionErrors) {
					if (StringUtils.contains(msg, "the request was rejected because its size")) {
						newActionErrors.add("The file you attempted to upload exceeds the 5MB maximum allowed limit");
						setActionErrors(newActionErrors);
						processUpload = false;
					}
				}
			}

			if (processUpload){

				//Resize the image if the dimension is greater than IMAGE_COMPRESSION_CONSTANTS value
				/* byte[] imageBlobBytes= resizeIfRequired(
						 soDocumentsAndPhotosDTO.getPhotoDoc().getUpload());
				 FileUtils.writeByteArrayToFile(
						 soDocumentsAndPhotosDTO.getPhotoDoc().getUpload(), imageBlobBytes);
					*/
				documentVO.setDocument(soDocumentsAndPhotosDTO.getPhotoDoc().getUpload());
				documentVO.setSoId(soId);
				documentVO.setFileName(soDocumentsAndPhotosDTO.getPhotoDoc().getUploadFileName());
				documentVO.setFormat(soDocumentsAndPhotosDTO.getPhotoDoc().getUploadContentType());
				documentVO.setDocSize(soDocumentsAndPhotosDTO.getPhotoDoc().getUpload().length());
				documentVO.setDocCategoryId(Constants.DocumentTypes.CATEGORY.IMAGE);
				
				if ( ((Boolean)getSession().getAttribute(SOConstants.IS_LOGGED_IN)).booleanValue() ) {
					documentVO.setEntityId(_commonCriteria.getVendBuyerResId());
					documentVO.setRoleId(_commonCriteria.getRoleId());
					documentVO.setCompanyId(_commonCriteria.getCompanyId());
				}
				ProcessResponse pr = new ProcessResponse();
				// insert doc temporarily
				if(com.newco.marketplace.web.utils.SLStringUtils.isNullOrEmpty(soId)){
					documentVO.setSoId(buyerGuid);
					pr = createServiceOrderDelegate.insertTempSODocument(documentVO);
				} else {		
					pr = createServiceOrderDelegate.insertSODocument(documentVO);
				}
				
				if (pr!=null){
					setErrorMessage(pr);
				}
			}
			getDocumentsAndPhotos();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String viewImage(){
		String imageDocId = getRequest().getParameter("imageDocId");
		SODocumentDTO selectedImage = null; 
		InputStream imagestream = null;

		try {
			List<SODocumentDTO> photoDocList = (List<SODocumentDTO>)getSession().getAttribute("photoDocList");
			for(SODocumentDTO image : photoDocList){
					if(image.getDocumentId()!= null && image.getDocumentId().toString().equals(imageDocId)){
						selectedImage = image;
						imagestream =new ByteArrayInputStream( image.getBlobBytes());
				}
			}
			
			soDocumentsAndPhotosDTO.setImageStreamView(imagestream);
			
			if(selectedImage!= null){
				
				if ( StringUtils.equals(selectedImage.getFormat(),"image/bmp") ||
					StringUtils.equals(selectedImage.getFormat(),"image/gif") ||
					StringUtils.equals(selectedImage.getFormat(),"image/jpeg") ||
					StringUtils.equals(selectedImage.getFormat(),"image/jpg") ) {
					setViewImageContentType(selectedImage.getFormat());
				} else {
					setViewImageContentType("text/html");
				}				
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "viewImage";
	}
	
	
	public String removeDocument(){

		try{
			soDocumentsAndPhotosDTO = getModel();
			Integer docId = Integer.parseInt(soDocumentsAndPhotosDTO.getDocumentSelection());
			DocumentVO documentVO = new DocumentVO();
			documentVO.setDocumentId(docId);
		
			ProcessResponse pr = new ProcessResponse();
			
			if(com.newco.marketplace.web.utils.SLStringUtils.isNullOrEmpty(soId)){
				pr = createServiceOrderDelegate.deleteTempSODocument(buyerGuid, docId);
			}else{
				documentVO.setEntityId(_commonCriteria.getVendBuyerResId());
				documentVO.setRoleId(_commonCriteria.getRoleId());
				documentVO.setCompanyId(_commonCriteria.getCompanyId());
				pr = createServiceOrderDelegate.deleteSODocument(documentVO);
			}
						
			if (pr!=null){
				setErrorMessage(pr);
			}
			getDocumentsAndPhotos();
		}catch(Exception e){	
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	private void setErrorMessage(ProcessResponse pr){
		String errorMessage = "";
		
		if (pr.getCode().equalsIgnoreCase(DOC_PROCESSING_ERROR_RC) ||
				pr.getCode().equalsIgnoreCase(DOC_UPLOAD_ERROR_RC) ||
				pr.getCode().equalsIgnoreCase(DOC_DELETE_ERROR_RC) ||
				pr.getCode().equalsIgnoreCase(DOC_RETRIEVAL_ERROR_RC) ||
				pr.getCode().equalsIgnoreCase(DOC_USER_AUTH_ERROR_RC) ||
				pr.getCode().equalsIgnoreCase(SO_DOC_NOT_IN_ALLOWED_STATE_ERROR_RC) ||
				pr.getCode().equalsIgnoreCase(SO_DOC_SIZE_EXCEEDED_RC) ||
				pr.getCode().equalsIgnoreCase(SO_DOC_INVALID_FORMAT) ||
				pr.getCode().equalsIgnoreCase(SO_DOC_UPLOAD_EXSITS) ||
				pr.getCode().equalsIgnoreCase(SO_DOC_WFSTATE_CLOSED_DELETE)||
				pr.getCode().equalsIgnoreCase(SO_DOC_WFSTATE_CLOSED_INSERT)){
			
				ResourceBundle rb = ResourceBundle.getBundle("/resources/properties/servicelive_copy");
				errorMessage = rb.getObject("error.msg." + pr.getCode()).toString();
				addActionError(errorMessage);
		}

	}

	

	/**
	 * This method reduces the dimension of the image if it is
	 *  greater than the system specified standard
	 * @param File uploadedImage
	 * @return byte[]
	 * @throws Exception
	 */
	/*private byte[] resizeIfRequired(File uploadedImage) throws Exception {
		byte[] originalImageBytes = FileUtils.readFileToByteArray(uploadedImage);
		Image sourceImage = new ImageIcon(originalImageBytes).getImage();
		logger.debug("Checks if  Resize of image is Required");
		if (sourceImage.getWidth(null) > Constants.IMAGE_COMPRESSION_CONSTANTS.IMAGE_WIDTH
				&& sourceImage.getHeight(null) > Constants.IMAGE_COMPRESSION_CONSTANTS.IMAGE_HEIGHT) {
			logger.debug("Resize is Required");
			byte[] resizedImageBytes = DocumentUtils.resizeoImage(originalImageBytes,
					Constants.IMAGE_COMPRESSION_CONSTANTS.IMAGE_WIDTH,
					Constants.IMAGE_COMPRESSION_CONSTANTS.IMAGE_HEIGHT);
			return resizedImageBytes;
		} else {
			logger.debug("Resizing is not required");
			return originalImageBytes;
		}
	}*/
	
	public CreateServiceOrderDocAndPhotosDTO getSoDocumentsAndPhotosDTO() {
		return soDocumentsAndPhotosDTO;
	}


	public void setSoDocumentsAndPhotosDTO(
			CreateServiceOrderDocAndPhotosDTO soDocumentsAndPhotosDTO) {
		this.soDocumentsAndPhotosDTO = soDocumentsAndPhotosDTO;
	}


	public ISOWizardPersistDelegate getIsoWizardPersistDelegate() {
		return isoWizardPersistDelegate;
	}


	public void setIsoWizardPersistDelegate(
			ISOWizardPersistDelegate isoWizardPersistDelegate) {
		this.isoWizardPersistDelegate = isoWizardPersistDelegate;
	}
	
	
	
	public CreateServiceOrderDocAndPhotosDTO getModel(){
		return soDocumentsAndPhotosDTO;
	}
	
	public void setModel(CreateServiceOrderDocAndPhotosDTO model){
		this.soDocumentsAndPhotosDTO = model;
	}

	public ICreateServiceOrderDelegate getCreateServiceOrderDelegate() {
		return createServiceOrderDelegate;
	}

	public void setCreateServiceOrderDelegate(
			ICreateServiceOrderDelegate createServiceOrderDelegate) {
		this.createServiceOrderDelegate = createServiceOrderDelegate;
	}

	public String getViewImageContentType() {
		return viewImageContentType;
	}

	public void setViewImageContentType(String viewImageContentType) {
		this.viewImageContentType = viewImageContentType;
	}


}
