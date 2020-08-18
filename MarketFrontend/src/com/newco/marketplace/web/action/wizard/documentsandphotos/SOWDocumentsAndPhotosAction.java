package com.newco.marketplace.web.action.wizard.documentsandphotos;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.BuyerDocumentTypeVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.serviceorder.SoDocumentVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.BuyerConstants;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLWizardBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.delegates.ISOWizardPersistDelegate;
import com.newco.marketplace.web.dto.SLDocumentDTO;
import com.newco.marketplace.web.dto.SODocumentDTO;
import com.newco.marketplace.web.dto.SOWDocumentsAndPhotosDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.security.SecuredAction;
import com.newco.marketplace.web.utils.ObjectMapper;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.newco.marketplace.web.utils.SecurityChecker;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;


/**
 * $Revision: 1.30 $ $Author: glacy $ $Date: 2008/04/26 01:13:55 $
 */


public class SOWDocumentsAndPhotosAction extends SLWizardBaseAction implements Preparable,ModelDriven<SOWDocumentsAndPhotosDTO>,OrderConstants
{
	
    
	private static final long serialVersionUID = 6277348083049354292L;
	private static final Logger logger = Logger.getLogger("SOWDocumentsAndPhotosAction");	
	private SOWDocumentsAndPhotosDTO sowDocumentsAndPhotosDTO = new SOWDocumentsAndPhotosDTO();
	private ISOWizardPersistDelegate isoWizardPersistDelegate;
	private Integer documentId;
	private String soId;
	private boolean viewProviderDocPermission=true;
	private boolean viewDocCommentInd=false;
	private IBuyerFeatureSetBO buyerFeatureSetBO;
	protected ISODetailsDelegate detailsDelegate;
	
	public ISODetailsDelegate getDetailsDelegate() {
		return detailsDelegate;
	}

	public void setDetailsDelegate(ISODetailsDelegate detailsDelegate) {
		this.detailsDelegate = detailsDelegate;
	}

	public boolean getViewDocCommentInd() {
		return viewDocCommentInd;
	}

	public void setViewDocCommentInd(boolean viewDocCommentInd) {
		this.viewDocCommentInd = viewDocCommentInd;
	}

	public boolean getViewProviderDocPermission() {
		return viewProviderDocPermission;
	}

	public void setViewProviderDocPermission(boolean viewProviderDocPermission) {
		this.viewProviderDocPermission = viewProviderDocPermission;
	}



	public SOWDocumentsAndPhotosDTO getSowDocumentsAndPhotosDTO() {
		return sowDocumentsAndPhotosDTO;
	}



	public void setSowDocumentsAndPhotosDTO(
			SOWDocumentsAndPhotosDTO sowDocumentsAndPhotosDTO) {
		this.sowDocumentsAndPhotosDTO = sowDocumentsAndPhotosDTO;
	}



	public ISOWizardPersistDelegate getIsoWizardPersistDelegate() {
		return isoWizardPersistDelegate;
	}



	public void setIsoWizardPersistDelegate(
			ISOWizardPersistDelegate isoWizardPersistDelegate) {
		this.isoWizardPersistDelegate = isoWizardPersistDelegate;
	}



	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();
		// Set priceModel on Model
		//Sl-19820
		//String soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
		String soId = getParameter("soId");
		if(SLStringUtils.isNullOrEmpty(soId)){
			soId=getParameter("serviceId");
		}
		this.soId=soId;
		setAttribute(OrderConstants.SO_ID, soId);		
		String status = getParameter(SOConstants.SERVICE_ORDER_STATUS);
		setAttribute(THE_SERVICE_ORDER_STATUS_CODE, status);
		//Sl-19820
		//fetching the SO
		//ServiceOrderDTO dto= (ServiceOrderDTO) getSession().getAttribute(THE_SERVICE_ORDER);
		ServiceOrderDTO dto = null;
		
		//SL-19820 Added a check for status to prevent barebone SO from entering
		if(StringUtils.isNotBlank(soId) && StringUtils.isNotBlank(status)){
			dto = getDetailsDelegate().getServiceOrder(soId, get_commonCriteria().getRoleId(), null);
		}
		setAttribute(THE_SERVICE_ORDER, dto);
		boolean isautocloseOn = false;
		if(dto != null && soId != null && soId.equals(dto.getId())){
			sowDocumentsAndPhotosDTO.setPriceModel(dto.getPriceModel());
			isautocloseOn=buyerFeatureSetBO.validateFeature(Integer.parseInt(dto.getBuyerID()), BuyerFeatureConstants.AUTO_CLOSE);
		}
		else if(get_commonCriteria().getStatusId()==null&&get_commonCriteria().getRoleId().intValue()==BUYER_ROLEID ){
			isautocloseOn=buyerFeatureSetBO.validateFeature(get_commonCriteria().getCompanyId(), BuyerFeatureConstants.AUTO_CLOSE);
		}
		//check to see if autoclose on for this buyer
		//SL-19820
		//getSession().setAttribute("isAutocloseOn", isautocloseOn);
		setAttribute("isAutocloseOn", isautocloseOn);
		sowDocumentsAndPhotosDTO.setAutocloseOn(isautocloseOn);
		//end of autoclose check;
	}


	
	public SOWDocumentsAndPhotosAction(ISOWizardFetchDelegate fetchDelegate ) {
		this.fetchDelegate = fetchDelegate;
	}
		
	@SuppressWarnings("unchecked")
	public String getDocuments()throws Exception{
		
		//SL-19820
		//String soId = (String)getSession().getAttribute(OrderConstants.SO_ID);

		String soId = (String)getAttribute(OrderConstants.SO_ID);
		if(SLStringUtils.isNullOrEmpty(soId)){
			soId=getParameter("serviceId");
		}
		setAttribute(OrderConstants.SO_ID, soId);
		this.soId=soId;
		String buyerId = null;
		//SL-19820
		//ServiceOrderDTO soDto= (ServiceOrderDTO) getSession().getAttribute(THE_SERVICE_ORDER);
		ServiceOrderDTO soDto = (ServiceOrderDTO) getAttribute(THE_SERVICE_ORDER);
		if(null!=soDto){
			buyerId = soDto.getBuyerID();
		}
		if (StringUtils.isEmpty(soId)){
			logger.debug("Service Order number not available");
		}else{
			DocumentVO x = new DocumentVO();
			x.setSoId(soId);
			x.setRoleId(_commonCriteria.getRoleId());
			
			Integer userId = _commonCriteria.getVendBuyerResId();
			try{
				String docSource = (String)getRequest().getParameter("docSource");
				String skuIndicator=(String)getRequest().getParameter("skuIndicator");
				if(StringUtils.isEmpty(docSource)){
					docSource = null;
				}
				//SL:17973:to fetch the documents irrespective of docSource		
				List<DocumentVO> y = fetchDelegate.getDocumentsMetaDataBySOID(x, userId,null);
				List<SODocumentDTO> documents = new ArrayList<SODocumentDTO>();
				List<SODocumentDTO> skuDocuments = new ArrayList<SODocumentDTO>();
				List<DocumentVO> docListOfSKu = new ArrayList<DocumentVO>();
				for (DocumentVO docVO : y){
					documents.add(ObjectMapper.convertDocumentVOToDTO(docVO));
					getDocumentsInCurrentVisit(docVO,documents);
				}	
				String skuInd ="0";
			
				if(null!=getSession().getAttribute("skuAttached_"+soId))
				{
					skuInd=(String) getSession().getAttribute("skuAttached_"+soId);	
				}
				
			
				skuDocuments = (List<SODocumentDTO>) getSession().getAttribute("attachmentsList_"+soId);
		
				if(null!=skuDocuments && !skuDocuments.isEmpty() && null!=skuInd && skuInd.equals("0"))
				{

					getSession().setAttribute("skuAttached_"+soId,"1");
					
				//Sl-19820					
				
				//Sl-19820 Commenting docListOfSKu as it is not used any where.
				//docListOfSKu =(List<DocumentVO>) getSession().getAttribute("documentVOList_"+soId);
			
				documents.addAll(skuDocuments);
				//documents.get(documents.size()-1).setDocInCurrentVisit(true); 
				for(SODocumentDTO soDocumentDto:skuDocuments)
				{
					//sowDocumentsAndPhotosDTO.setDocument(soDocumentDto);
					//Document persist for the so created using sku
					try{
						SoDocumentVO documentVO = new SoDocumentVO();			
						//docSource = "Sku";
						documentVO.setDocumentId(soDocumentDto.getDocumentId());
						documentVO.setDocSource(null);
						documentVO.setSoId(soId);
						isoWizardPersistDelegate.insertSODocuments(documentVO); 
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					//end of document persist using  sku
					
				}
				
				}
				sowDocumentsAndPhotosDTO.setDocuments(documents);
				//check whether SL-Admin has adopted the buyer
				boolean isAdoptedBuyer = this._commonCriteria.getSecurityContext().isAdopted();
				Integer roleId= 0;
				if (isAdoptedBuyer){
					roleId= this._commonCriteria.getSecurityContext().getAdminRoleId();
				}else{
					roleId= this._commonCriteria.getSecurityContext().getRoleId();
				}
				//SL:17972:check whether buyer/Admin has permissions to view provider documents.
				if(NEWCO_ADMIN_ROLEID == roleId.intValue()){
					Map <String, UserActivityVO> roleActivityList = this._commonCriteria.getSecurityContext().getRoleActivityIdList();
					//to check whether buyer has permission to view prov docs
					if(BUYER_ROLEID == this._commonCriteria.getSecurityContext().getRoleId().intValue()){
						String userName = this._commonCriteria.getSecurityContext().getUsername();					
						boolean viewDocPermissionForBuyer = fetchDelegate.getViewDocPermission(userName);
						setViewProviderDocPermission(roleActivityList.containsKey(VIEW_PROVIDER_DOC_PERMISSION_ADOPTED_BUYER)&& viewDocPermissionForBuyer);
					}else{
						setViewProviderDocPermission(roleActivityList.containsKey(VIEW_PROVIDER_DOC_PERMISSION_ADOPTED_BUYER));
					}
					
					for (SODocumentDTO doc : documents ){
						if(doc.getRole()==ROLE_PROVIDER && !getViewProviderDocPermission()){
							setViewDocCommentInd(true);
						}
					}
				}
				//check whether buyer has permissions to view provider documents.	
				if(BUYER_ROLEID == roleId.intValue()){
					Map <String, UserActivityVO> roleActivityList = this._commonCriteria.getSecurityContext().getRoleActivityIdList();
					setViewProviderDocPermission(roleActivityList.containsKey(VIEW_PROVIDER_DOC_PERMISSION));
					
					for (SODocumentDTO doc : documents ){
						if(doc.getRole()==ROLE_PROVIDER && !getViewProviderDocPermission()){
							setViewDocCommentInd(true);
						}
					}
				}
				//SL-17601: retrieving the reference docs associated to an SO to be displayed in Create SO
				if(NEWCO_ADMIN_ROLEID == roleId.intValue() || BUYER_ROLEID == roleId.intValue() || SIMPLE_BUYER_ROLEID == roleId.intValue()){
					SecurityContext soContxt=(SecurityContext) getSession().getAttribute("SecurityContext");
					int buyerID = soContxt.getCompanyId();
					List<DocumentVO> existingDocs = null;
					List<SLDocumentDTO> refDocuments = new ArrayList<SLDocumentDTO>();
					existingDocs = fetchDelegate.retrieveRefBuyerDocumentsByBuyerId(buyerID, soId);
					if(null != existingDocs){
						for (DocumentVO docVO : existingDocs){
							refDocuments.add(ObjectMapper.convertBuyerDocumentVOToDTO(docVO));					
						}	
					}					
					if(null == refDocuments || 0 == refDocuments.size()){
						sowDocumentsAndPhotosDTO.setDocSrc("");
					}	
					//SL-19820
					getSession().setAttribute("refDocuments"+"_"+soId,refDocuments);
					setAttribute("refDocuments",refDocuments);
				}
				clearfields();
				getValidDisplayExtensions();
				if(null!=buyerId && !(buyerId.equals(""))){
					Integer buyerID = Integer.parseInt(buyerId);
					getDocumentTypes(buyerID,OrderConstants.COMPLETION_SOURCE);
				}
				//SL-17963:to solve issue with doc type display
				List <BuyerDocumentTypeVO> documentTypeList = (List <BuyerDocumentTypeVO>)getRequest().getAttribute("documentTypes");
				if(null != documents && null != documentTypeList){
					for(SODocumentDTO doc : documents){
						int flag = 0;
						for (BuyerDocumentTypeVO type : documentTypeList){
							if(null!= doc.getDocumentTitle() &&( doc.getDocumentTitle().equals(type.getDocumentTitle()))){
								flag = 1;
								break;
							}
							else{
								continue;
							}
						}
						if(0 == flag){
							doc.setDocumentTitle("");
						}
					}		
				}		
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		//LogSession iterates through the session and log only in case of debug, hence added a debugger condition.
		if(logger.isDebugEnabled()){
			this.logSession();
		}
		
		if(getParameter("tab")!=null && getParameter("tab").length() > 0){
			String setTab = getParameter("tab");
			sowDocumentsAndPhotosDTO.setTab(setTab);
		}

		return SUCCESS;
	}
	/**
	 * This method retrieves document types of a buyer
	 */
	private void getDocumentTypes(Integer buyerId , Integer source) { 
	try{
		List<BuyerDocumentTypeVO> documentTypeList= fetchDelegate.retrieveDocumentByBuyerIdAndSource(buyerId, source);
		getRequest().setAttribute("documentTypes", documentTypeList);
	}catch(Exception e){	
		e.printStackTrace();
	}
		
	}
	/**
	 * This method provides the valid extensions from the DB and set to the DTO
	 */
	private void getValidDisplayExtensions() { 
	try{
		String validDisplayExtension = fetchDelegate.getValidDisplayExtensions();
		sowDocumentsAndPhotosDTO.setValidDisplayExtensions(validDisplayExtension);
	}catch(Exception e){	
		e.printStackTrace();
	}
		
	}
	
	private void clearfields(){
		sowDocumentsAndPhotosDTO.setDescription("");
		sowDocumentsAndPhotosDTO.setDocument(null);
	}
	


	
	public String removeDocument(){

		try{
			sowDocumentsAndPhotosDTO = getModel();
	        //Sl-19820
			//String soId = (String)getSession().getAttribute(OrderConstants.SO_ID);		
			String soId =getParameter("serviceId");	
			DocumentVO documentVO = new DocumentVO();
			
			documentVO.setDocumentId(Integer.parseInt(sowDocumentsAndPhotosDTO.getDocumentSelection()));
			
			documentVO.setEntityId(_commonCriteria.getVendBuyerResId());
			documentVO.setRoleId(_commonCriteria.getRoleId());
			documentVO.setCompanyId(_commonCriteria.getCompanyId());
			documentVO.setSoId(soId);
			
			ProcessResponse pr = new ProcessResponse();
			
			pr = isoWizardPersistDelegate.deleteSODocument(documentVO);
			
			if (pr!=null){
				setErrorMessage(pr);
			}
			
			getDocuments();
		}catch(Exception e){	
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String viewDocument(){
		downloadDocument();
		
		return SUCCESS;
	}
	

	
	public String downloadDocumentWidget() {
		sowDocumentsAndPhotosDTO = new SOWDocumentsAndPhotosDTO();
		sowDocumentsAndPhotosDTO.setDocumentSelection(this.documentId.toString());
		
		return downloadDocument();
	}
	
	public String downloadDocumentIFrame() {
		sowDocumentsAndPhotosDTO = getModel();
		
		return downloadDocument();
	}
	protected String downloadDocument(){
		Integer documentId = null;
		
		try {
	
			SODocumentDTO document = new SODocumentDTO();
			
			if(sowDocumentsAndPhotosDTO.getDocumentSelection()!=null &&
					sowDocumentsAndPhotosDTO.getDocumentSelection().length()>0){
				documentId = Integer.parseInt(sowDocumentsAndPhotosDTO.getDocumentSelection());
			}
        
        	//1. Retrieve the Document
			if (documentId!=null){
	        	document = fetchDelegate.retrieveServiceOrderDocumentByDocumentId(documentId);
	
	        	   SecurityChecker sc = new SecurityChecker();
	        	   String uploadType = sc.securityCheck(document.getUploadContentType());
	        	  getResponse().setContentType(uploadType);
	        	
	        		String docName = sc.fileNameCheck(document.getName());
	              String header = "attachment;filename=\""
	                          + docName + "\"";
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
    	  e.printStackTrace();
      }
		return SUCCESS;
	}
	
	@SecuredAction(securityTokenEnabled = true)
	public String documentUpload(){
		//Sl-19820
		//String soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
		String soId=getParameter("serviceId");	
		this.soId=soId;
		getRequest().setAttribute(OrderConstants.SO_ID, soId);
		getRequest().setAttribute(OrderConstants.FILE_UPLOAD_STATUS, false);
		if (StringUtils.isEmpty(soId)){
			logger.debug("Service Order number not available");
			return ERROR;
		}


		try{
			DocumentVO documentVO = new DocumentVO();			
			sowDocumentsAndPhotosDTO = getModel();
			List<String> actionErrors = (List<String>) this.getActionErrors();
			List<String> newActionErrors = new ArrayList <String>();			
			boolean processUpload = true;
			documentVO.setDocSize(sowDocumentsAndPhotosDTO.getDocument().getUpload().length());
			Integer size=documentVO.getDocSize().intValue()/OrderConstants.SIZE_KB;
			if(sowDocumentsAndPhotosDTO.getPermitWarningStatus()!=null){
			sowDocumentsAndPhotosDTO.setPermitWarningStatus(sowDocumentsAndPhotosDTO.getPermitWarningStatus());
			}
			
			if(size>FIVE_KB)
			{
				newActionErrors.add("Please attach a file no larger than 5mb.");
				setActionErrors(newActionErrors);
				processUpload = false;
			}
			
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
				//SL-21011
				/*documentVO.setDocument(sowDocumentsAndPhotosDTO.getDocument().getUpload());
				documentVO.setSoId(soId);
				documentVO.setFileName(sowDocumentsAndPhotosDTO.getDocument().getUploadFileName());*/
				String uploadFileName = sowDocumentsAndPhotosDTO.getDocument().getUploadFileName();
				uploadFileName = uploadFileName.replaceAll("[-+#$%&*()~\\[\\]<}>{!@\"=/?';|^:,]","");
				documentVO.setDocument(sowDocumentsAndPhotosDTO.getDocument().getUpload());
				documentVO.setSoId(soId);
				documentVO.setFileName(uploadFileName);
				documentVO.setDescription(sowDocumentsAndPhotosDTO.getDescription());
				documentVO.setTitle(sowDocumentsAndPhotosDTO.getDocumentTitle());
				documentVO.setFormat(sowDocumentsAndPhotosDTO.getDocument().getUploadContentType());
				documentVO.setDocSize(sowDocumentsAndPhotosDTO.getDocument().getUpload().length());
				documentVO.setEntityId(_commonCriteria.getVendBuyerResId());
				documentVO.setRoleId(_commonCriteria.getRoleId());
				documentVO.setCompanyId(_commonCriteria.getCompanyId());
				//for buyer 1000
				documentVO.setAutocloseOn(sowDocumentsAndPhotosDTO.isAutocloseOn());
				if(sowDocumentsAndPhotosDTO.isAutocloseOn() && sowDocumentsAndPhotosDTO.getDocCategoryId()!=null )
				{
					documentVO.setDocCategoryId(sowDocumentsAndPhotosDTO.getDocCategoryId());
					
				}
				String docSource = (String)getRequest().getParameter("docSource");
				if(StringUtils.isEmpty(docSource)){
				        docSource = null;
				}
				documentVO.setDocSource(docSource);
				ProcessResponse pr = new ProcessResponse();
				
				pr = isoWizardPersistDelegate.insertSODocument(documentVO); 
				if (pr!=null){
					setErrorMessage(pr);
					if(pr.getCode().equals(OrderConstants.RESPONSE_CODE)){
						getRequest().setAttribute(OrderConstants.FILE_UPLOAD_STATUS, true);						
					}			 
					 Integer documentId = (Integer) pr.getObj();  
					 this.populateDocsInCurrentVisit(documentId); 
				} 						
			}			
			getDocuments();
			
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
				pr.getCode().equalsIgnoreCase(SO_DOC_WFSTATE_CLOSED_INSERT) ||
				pr.getCode().equalsIgnoreCase(SO_DOC_INVALID_FORMAT_SEARS_BUYER)){
			
				ResourceBundle rb = ResourceBundle.getBundle("/resources/properties/servicelive_copy");
				errorMessage = rb.getObject("error.msg." + pr.getCode()).toString();
				addActionError(errorMessage);
		}

	}
	
	
	public String getDocumentsWidget() {
		
		DocumentVO documentVO = new DocumentVO();
		documentVO.setSoId(soId);
		documentVO.setRoleId(_commonCriteria.getRoleId());
		
		StringBuffer sb = new StringBuffer();
		sb.append("<message_result>");
		sb.append("<documents>");
		SODocumentDTO dto = null;
		
		try {
			List<DocumentVO> listVO = fetchDelegate.getDocumentsMetaDataBySOID
				(documentVO, _commonCriteria.getVendBuyerResId(),null);
			
			if(listVO != null){
				for (DocumentVO vo : listVO) {
					dto = new SODocumentDTO();
					dto.setDocumentId(vo.getDocumentId());
					dto.setName(vo.getFileName());
					dto.setSize(vo.getDocSize().intValue());
					dto.setDesc(vo.getDescription());
					dto.setCategory(vo.getDocCategoryId());
					dto.setDocPath(vo.getDocPath());
					sb.append(dto.toXml());
				}
			}
		} catch (DataServiceException e) {
			// unable to get documents return an empty collection
		}
		
		sb.append("</documents>");
		sb.append("<soId>").append(soId).append("</soId>");
		sb.append("</message_result>");

		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(sb.toString());
		} catch (IOException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return NONE;
	}
	 /** 
	  * Method to set the documents uploaded in current visit in session. 
	  * @param documentId 
	  */ 
	@SuppressWarnings("unchecked") 	
	private void populateDocsInCurrentVisit(Integer documentId){ 
		if(null != documentId){ 
			//Checks whether called from SOW or SOD 
			String soStatus = getParameter(SOConstants.SERVICE_ORDER_STATUS); 
			//Sl-19820
			String soId=this.soId;
			if(null != soStatus){ 
				if (!soStatus.equals("")
						&& !soStatus.equalsIgnoreCase(SOConstants.DRAFT_STATUS)
						&& !soStatus.equalsIgnoreCase(SOConstants.EXPIRED_STATUS)
						&& !soStatus.equalsIgnoreCase(SOConstants.POSTED_STATUS)) { 
					// Called from SOD - get list of documents in current visit from Session 
					List<Integer> docsInCurrentVisit = (List<Integer>) getSession()
							.getAttribute(Constants.SESSION.DOCS_IN_CURRENTVISIT_LIST+"_"+soId); 
					if(null == docsInCurrentVisit || docsInCurrentVisit.isEmpty()){ 
						docsInCurrentVisit = new ArrayList<Integer>(); 
					}  
					//Puts the currently uploaded document in Session 
					docsInCurrentVisit.add(documentId); 
					getSession().setAttribute(
							Constants.SESSION.DOCS_IN_CURRENTVISIT_LIST+"_"+soId,docsInCurrentVisit); 
				} 
			} 
		}   
	} 
	/** 
	 * Method to get documents uploaded in current visit from session 
	 * @param docVO 
	 * @param documents 
	 */ 
	@SuppressWarnings("unchecked") 
	private void getDocumentsInCurrentVisit(DocumentVO docVO,List<SODocumentDTO> documents){ 
		//sl-19820
		String soId=this.soId;
		List<Integer> docsInCurrentVisit = new ArrayList<Integer>(); 
		String soStatus = getParameter(SOConstants.SERVICE_ORDER_STATUS); 		
		if(null != soStatus){ 
			if (soStatus.equals("")
					|| soStatus.equalsIgnoreCase(SOConstants.DRAFT_STATUS)
					|| soStatus.equalsIgnoreCase(SOConstants.EXPIRED_STATUS) 
					|| soStatus.equalsIgnoreCase(SOConstants.POSTED_STATUS)) { 
				//Sets docINCurrentVisit to truw for all documents
				documents.get(documents.size()-1).setDocInCurrentVisit(true); 
			}else{ 
				// Called from SOD for SO status above accepted - get list of documents in current visit from Session 
				docsInCurrentVisit = (List<Integer>) getSession().getAttribute(
						Constants.SESSION.DOCS_IN_CURRENTVISIT_LIST+"_"+soId);
				//If uploaded in current visit, set the docInCurrentVisit value to true 
				if(null != docsInCurrentVisit && !docsInCurrentVisit.isEmpty()){ 
					if(docsInCurrentVisit.contains(docVO.getDocumentId())){ 
						documents.get(documents.size()-1).setDocInCurrentVisit(true); 
					} 
				} 
			} 
		}  		
	} 
	
	
	//SL-17601 to insert an already uploaded doc while SO creation
	@SecuredAction(securityTokenEnabled = true)
	public String uploadDocs(){
		//Sl-19820
		//String soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
		String soId =getParameter("serviceId");
		this.soId=soId;
		if (StringUtils.isEmpty(soId)){
			logger.debug("Service Order number not available");
			return ERROR;
		}


		try{
					
			sowDocumentsAndPhotosDTO = getModel();
			int count = 0;
			List <SLDocumentDTO> refDocuments = (List <SLDocumentDTO>) getSession().getAttribute("refDocuments"+"_"+soId);
			if(null != sowDocumentsAndPhotosDTO.getDocumentIds()){
				count = sowDocumentsAndPhotosDTO.getDocumentIds().size();
			}
			if(null != sowDocumentsAndPhotosDTO.getDocumentIds()){
				for(int i=1; i<count; i++){
					Integer documentId = sowDocumentsAndPhotosDTO.getDocumentIds().get(i);
					if(null != documentId){
						SoDocumentVO soDocVO = new SoDocumentVO();				
						soDocVO.setDocumentId(documentId);
						soDocVO.setSoId(soId);	
						soDocVO.setDocSource(null);						
						isoWizardPersistDelegate.insertSODocuments(soDocVO); 	
						getRequest().setAttribute(OrderConstants.FILE_UPLOAD_STATUS, true);		
						this.populateDocsInCurrentVisit(documentId);					
					}					
				}
			}			
			getDocuments();			
		}catch(Exception e){
			e.printStackTrace();
		}
	
		return SUCCESS;
	}
	
	public SOWDocumentsAndPhotosDTO getModel() {
		return sowDocumentsAndPhotosDTO;
	}
	
	public void setModel(SOWDocumentsAndPhotosDTO dto){
		
		this.sowDocumentsAndPhotosDTO = dto;
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

	public IBuyerFeatureSetBO getBuyerFeatureSetBO() {
		return buyerFeatureSetBO;
	}

	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}

}
