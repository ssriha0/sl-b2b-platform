package com.newco.marketplace.web.action.simple;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLSimpleBaseAction;
import com.newco.marketplace.web.delegates.ICreateServiceOrderDelegate;
import com.newco.marketplace.web.delegates.ISOWizardPersistDelegate;
import com.newco.marketplace.web.dto.SODocumentDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderDocAndPhotosDTO;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * $Revision: 1.2 $ $Author: akashya $ $Date: 2008/05/21 23:32:57 $
 */
public class CreateServiceOrderViewImageAction extends SLSimpleBaseAction
                 implements Preparable,ModelDriven<CreateServiceOrderDocAndPhotosDTO>,OrderConstants{
	
	private static final long serialVersionUID = 44444444L;
	
	private static final Logger logger = Logger.getLogger("SOWDocumentsAndPhotosAction");	
	private CreateServiceOrderDocAndPhotosDTO soDocumentsAndPhotosDTO = new CreateServiceOrderDocAndPhotosDTO();
	private ICreateServiceOrderDelegate createServiceOrderDelegate;
	private String viewImageContentType;

	public CreateServiceOrderViewImageAction(ICreateServiceOrderDelegate createSODelegate){
		this.createServiceOrderDelegate = createSODelegate;
	}

	public void prepare() throws Exception {
		// Nothing to prepare
	}
	
	public String viewImage(){
		logger.info("Inside CreateServiceOrderViewImageAction.viewImage() method");
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
