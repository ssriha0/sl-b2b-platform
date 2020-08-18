package com.newco.marketplace.web.action.buyeradmin;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.newco.marketplace.constants.Constants;

import com.newco.marketplace.dto.vo.BuyerDocumentTypeVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.IDocumentDelegate;
import com.newco.marketplace.web.delegates.ILookupDelegate;
import com.newco.marketplace.web.dto.SLDocumentDTO;
import com.newco.marketplace.web.dto.SLDocumentsAndPhotosDTO;
import com.newco.marketplace.web.utils.ObjectMapper;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class BADocManagerAction extends SLBaseAction implements Preparable, OrderConstants, ModelDriven<SLDocumentsAndPhotosDTO>
{
	private static final long serialVersionUID = -866612647463785085L;

    private static final Logger logger = Logger.getLogger("BADocManagerAction");

	private SLDocumentsAndPhotosDTO documentsAndPhotosDTO = new SLDocumentsAndPhotosDTO();
	private IDocumentDelegate documentDelegate;
	private Integer documentId;
	private String soId;
	private String docTitle;

	private ILookupDelegate lookupDelegate; 
	/* 
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		if(_commonCriteria == null)
			createCommonServiceOrderCriteria();	
	}	

	 
	/* 
	 * This method is called only when Buyer Document link is clicked on Buyer Dashboard.
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() throws Exception {
		return "init";
	}
	

	/**
	 * Retrieves documents by buyer id and category id , to be listed in document manager
	 * @return String
	 * @throws Exception
	 */
	public String getDocuments()throws DelegateException{
		List<DocumentVO> existingDocs = null;
		List<SLDocumentDTO> documents = new ArrayList<SLDocumentDTO>();
		Integer buyerId = _commonCriteria.getCompanyId();	
		
		//Retrieving documents and logos of the buyer
		existingDocs = documentDelegate.retrieveBuyerDocumentsAndLogosByBuyerId(buyerId);	
		if(null != existingDocs){
			for (DocumentVO docVO : existingDocs){
				documents.add(ObjectMapper.convertBuyerDocumentVOToDTO(docVO));					
			}	
		}
		documentsAndPhotosDTO.setDocuments(documents);			
		clearfields();
		
		//documentUsage(soId);						
		this.logSession();
		
		if(getParameter("tab")!=null && getParameter("tab").length() > 0){
			String setTab = getParameter("tab");
			documentsAndPhotosDTO.setTab(setTab);
		}
		retriveDocTypes();
		return SUCCESS;
	}
	
	
	/**
	 * Retrieves document types and sets the value.
	 * @return void 
	 * @throws DelegateException
	  */
	public void  retriveDocTypes() throws DelegateException
	{
		List<BuyerDocumentTypeVO> buyerexistingTypes = null;
		Integer buyerId = _commonCriteria.getCompanyId();	
		Integer source=null;
		buyerexistingTypes =  documentDelegate.retrieveDocTypesByBuyerId(buyerId,source);	
		getRequest().setAttribute("buyerUploadTypes", buyerexistingTypes);
		retriveLookUpDocument();
	}
	
	/**
	 * Adds document types entered by the buyer and displays it in the document manager, 
	 * @return String
	 * @throws DelegateException
     */
	public String  addDocumentTypeDetail() throws DelegateException
	{
		Integer buyerId = _commonCriteria.getCompanyId();	
		String documentType =getParameter("documentType");
	    String mandatoryInd =getParameter("mandatoryInd");
		String docSource = getParameter("documentSource");
		BuyerDocumentTypeVO buyerDocumentTypeVO = new BuyerDocumentTypeVO();
		if(_commonCriteria.getSecurityContext()!= null)
		{
		buyerDocumentTypeVO.setModifiedBy(_commonCriteria.getSecurityContext().getUsername());
		}
		buyerDocumentTypeVO.setBuyerId(buyerId);
		if(null!=mandatoryInd && !StringUtils.isBlank(mandatoryInd)){
			buyerDocumentTypeVO.setMandatoryInd(Integer.parseInt(mandatoryInd));
		}
		if(null!=docSource && !StringUtils.isBlank(docSource)){
			buyerDocumentTypeVO.setSourceId(Integer.parseInt(docSource));
		}
		try {
			documentType = URLDecoder.decode(documentType , "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		documentType = documentType.replaceAll("-prcntg-", "%");
		buyerDocumentTypeVO.setDocumentTitle(documentType);
		documentDelegate.addDocumentTypeDetailByBuyerId(buyerDocumentTypeVO);	
		retriveDocTypes();
		return "done";
	}
	
	/**
	 * Deletes document types.
	 * @return String
	 * @throws DelegateException
     */
	public String deleteDocumentType() throws DelegateException
	{
		Integer buyerDocTypeId =Integer.parseInt(getParameter("docTypeId"));
		documentDelegate.deleteDocumentTypeDetailByBuyerId(buyerDocTypeId);
		retriveDocTypes();
		return "done";
	}
	/**
	 * Retrieves document types by buyer id to be listed in Look up drop down
	 * @return void
	 * @throws DelegateException
     */
	public void retriveLookUpDocument() throws DelegateException
	{
		List<LookupVO> buyerLookUpDocDetail = null;
		Integer buyerId = _commonCriteria.getCompanyId();
		Integer source=null;
		//Retrieving documents type to be uploaded by the provider
		buyerLookUpDocDetail =  lookupDelegate.retrieveLookUpDocumentByBuyerId(buyerId,source);	
		getRequest().setAttribute("buyerLookUpTypes", buyerLookUpDocDetail);
		
	}
	private void clearfields(){
		documentsAndPhotosDTO.setDescription("");
		documentsAndPhotosDTO.setDocument(null);
		documentsAndPhotosDTO.setLogoDocumentInd(false);
		documentsAndPhotosDTO.setTitle("");
		
	}
	
	/**
	 * @return
	 */
	public String removeDocument() {

		try {
			boolean isLogoPresentFlag = false;
			String documentLogoType = null;
			documentsAndPhotosDTO = getModel();
			DocumentVO documentVO = new DocumentVO();

			documentVO.setDocumentId(Integer.parseInt(documentsAndPhotosDTO
					.getDocumentSelection()));
			documentVO.setEntityId(_commonCriteria.getVendBuyerResId());
			documentVO.setRoleId(_commonCriteria.getRoleId());
			documentVO.setCompanyId(_commonCriteria.getCompanyId());
			documentVO.setTitle(docTitle);
			
			ProcessResponse pr = new ProcessResponse();
			//Changes for SL-17955-->START
			if (null != documentsAndPhotosDTO.getDocumetType()) {
				documentLogoType = documentsAndPhotosDTO.getDocumetType();
			}
			if (null != documentLogoType
					&& MPConstants.DOCUMENT_TYPE_LOGO.equalsIgnoreCase(documentLogoType)) {
				isLogoPresentFlag = documentDelegate.isLogoPresentInTemplate(documentVO);
			}
			if(isLogoPresentFlag){
				addActionError(MPConstants.ERROR_LOGO_PRESENT_IN_TEMPLATE);
			}else{
				pr = documentDelegate.deleteDocument(documentVO);
			}
			//Changes for SL-17955-->END
			
			if (pr != null) {
				setErrorMessage(pr);
			}
			
			getDocuments();
		}catch(Exception e){	
			logger.error("Exception thrown deleting buyer document - ", e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * @return
	 */
	public String viewDocument(){
		downloadDocument();
		return SUCCESS;
	}
	
	/**
	 * @return
	 */
	public String downloadDocumentWidget() {
		documentsAndPhotosDTO = new SLDocumentsAndPhotosDTO();
		documentsAndPhotosDTO.setDocumentSelection(this.documentId.toString());
		
		return downloadDocument();
	}
	
	/**
	 * @return
	 */
	public String downloadDocumentIFrame() {
		documentsAndPhotosDTO = getModel();
		
		return downloadDocument();
	}
	
	/**
	 * @return
	 */
	protected String downloadDocument(){
		Integer documentId = null;
		
		try {
			SLDocumentDTO document = new SLDocumentDTO();
			DocumentVO documentVO = new DocumentVO();
			
			if(documentsAndPhotosDTO.getDocumentSelection()!=null &&
					documentsAndPhotosDTO.getDocumentSelection().length()>0){
				documentId = Integer.parseInt(documentsAndPhotosDTO.getDocumentSelection());
			}
	    
	    	//1. Retrieve the Document
			if (documentId!=null){
				documentVO = documentDelegate.retrieveBuyerDocumentByDocumentId(documentId);
	
				getResponse().setContentType(document.getUploadContentType());
				
				String header = "attachment;filename=\""  + document.getName() + "\"";
				getResponse().setHeader("Content-Disposition", header);
				InputStream in = new ByteArrayInputStream(document.getBlobBytes());
				
				ServletOutputStream outs = getResponse().getOutputStream();
				
				int bit = 256;
				
				while ((bit) >= 0) {
					bit = in.read();
					outs.write(bit);
				}
				
				outs.flush();
				outs.close();
				in.close();
			}
			
			getDocuments();
	
		} catch (Exception e) {
		  logger.error("Exception thrown downloading buyer document - ", e);
		}
		return SUCCESS;
	}
	
	/**
	 * @return
	 */
	public String documentUpload(){
	
		try {
			DocumentVO documentVO = new DocumentVO();
			
			documentsAndPhotosDTO = getModel();
			List<String> actionErrors = (List<String>) this.getActionErrors();
			List<String> newActionErrors = new ArrayList <String>();
			
			boolean processUpload = true;
			boolean islogoPresent = false;
			
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
				documentVO.setDocument(documentsAndPhotosDTO.getDocument().getUpload());
				documentVO.setSoId(soId);
				documentVO.setFileName(documentsAndPhotosDTO.getDocument().getUploadFileName());
				documentVO.setDescription(documentsAndPhotosDTO.getDescription());
				documentVO.setTitle(documentsAndPhotosDTO.getTitle());
				documentVO.setFormat(documentsAndPhotosDTO.getDocument().getUploadContentType());
				documentVO.setDocSize(documentsAndPhotosDTO.getDocument().getUpload().length());
				documentVO.setEntityId(_commonCriteria.getVendBuyerResId());
				documentVO.setRoleId(_commonCriteria.getRoleId());
				documentVO.setCompanyId(_commonCriteria.getCompanyId());
				documentVO.setTitle(documentsAndPhotosDTO.getTitle());
				documentVO.setValidateOnBuyerId(true);
				if(documentsAndPhotosDTO.isLogoDocumentInd()){
					islogoPresent = true;
					documentVO.setDocCategoryId(Constants.BuyerAdmin.LOGO_DOC_CATEGORY_ID);
				}else{
					documentVO.setDocCategoryId(Constants.BuyerAdmin.DOC_CATEGORY_ID);
				}
				ProcessResponse pr = new ProcessResponse();
				
				pr = documentDelegate.insertDocument(documentVO);
				
				String errorMessage = "";				 
				if (pr!=null){
					setErrorMessage(pr);
					}
				}		
			getDocuments();
			//if error, then keep the values
			List<String>errorMessages =  (List<String>) this.getActionErrors();
			if(null!=errorMessages && errorMessages.size()>0){
				documentsAndPhotosDTO.setDescription(documentVO.getDescription());
				documentsAndPhotosDTO.setLogoDocumentInd(islogoPresent);
				documentsAndPhotosDTO.setTitle(documentVO.getTitle());
			}
		}catch(Exception e){
			logger.error("Exception thrown inserting buyer document - ", e);
		}
		return SUCCESS;
	}

	/**
	 * @param pr
	 */
	private void setErrorMessage(ProcessResponse pr){
		String errorMessage = "";
		
		if (pr.getCode().equalsIgnoreCase(DOC_PROCESSING_ERROR_RC) ||
				pr.getCode().equalsIgnoreCase(DOC_UPLOAD_ERROR_RC) ||
				pr.getCode().equalsIgnoreCase(DOC_DELETE_ERROR_RC) ||
				pr.getCode().equalsIgnoreCase(DOC_RETRIEVAL_ERROR_RC) ||
				pr.getCode().equalsIgnoreCase(Constants.BuyerAdmin.DOC_WITH_TITLE_EXISTS) ||
				pr.getCode().equalsIgnoreCase(Constants.BuyerAdmin.DOC_WITH_FILENAME_EXISTS) ||
				pr.getCode().equalsIgnoreCase(SO_DOC_INVALID_FORMAT)||
				pr.getCode().equalsIgnoreCase(LOGO_DOC_INVALID_FORMAT)){			
				ResourceBundle rb = ResourceBundle.getBundle("/resources/properties/servicelive_copy");
				errorMessage = rb.getObject("error.msg." + pr.getCode()).toString();
				addActionError(errorMessage);
		}
	}

	public SLDocumentsAndPhotosDTO getModel() {
		// TODO Auto-generated method stub
		return documentsAndPhotosDTO;
	}
	
	public void setModel(SLDocumentsAndPhotosDTO dto){
		
		this.documentsAndPhotosDTO = dto;
	}
	
	public Integer getDocumentId() {
		return documentId;
	}
	
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	
	public void setSelectedSO(String soId) {
		this.soId = soId;
	}
	
	public String getSoId() {
		return soId;
	}
	
	public void setSoId(String soId) {
		this.soId = soId;
	}

	public IDocumentDelegate getDocumentDelegate() {
		return documentDelegate;
	}

	public void setDocumentDelegate(IDocumentDelegate documentDelegate) {
		this.documentDelegate = documentDelegate;
	}

	public SLDocumentsAndPhotosDTO getDocumentsAndPhotosDTO() {
		return documentsAndPhotosDTO;
	}

	public void setDocumentsAndPhotosDTO(
			SLDocumentsAndPhotosDTO documentsAndPhotosDTO) {
		this.documentsAndPhotosDTO = documentsAndPhotosDTO;
	}
	
	
	public String getDocTitle() {
		return docTitle;
	}


	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}
	public ILookupDelegate getLookupDelegate() {
		return lookupDelegate;
	}
	public void setLookupDelegate(ILookupDelegate lookupDelegate) {
		this.lookupDelegate = lookupDelegate;
	}
}
/*
 * Maintenance History
 * $Log: BADocManagerAction.java,v $
 * Revision 1.7  2008/04/26 01:13:49  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.5.12.1  2008/04/23 11:41:39  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.6  2008/04/23 05:19:29  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.5  2008/02/14 23:44:52  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.2.2.2  2008/02/11 19:25:55  pbhinga
 * Minor changes to restructure exception handling.
 *
 * Revision 1.2.2.1  2008/02/11 04:08:05  pbhinga
 * Checked for Iteration 17 functionality - Document Manager. Reviewed by Gordon.
 *
 */
