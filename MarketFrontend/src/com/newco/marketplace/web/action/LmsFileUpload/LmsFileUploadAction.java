package com.newco.marketplace.web.action.LmsFileUpload;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.utils.SimpleCache;
import com.newco.marketplace.utils.ValidationUtils;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.dto.ajax.AjaxResAsLabelValueDTO;
import com.newco.marketplace.web.utils.SecurityChecker;
import com.opensymphony.xwork2.ModelDriven;
import com.servicelive.domain.lms.LmsFileUploadDTO;
import com.servicelive.domain.view.AdminUserProfileContact;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.lmscredential.IlmsCredentialFileUploadService;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.exception.BusinessServiceException;



public class LmsFileUploadAction extends SLBaseAction implements ModelDriven<LmsFileUploadDTO>{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(LmsFileUploadAction.class);
	private IlmsCredentialFileUploadService lmsFileUploadServiceImpl;
	private IBuyerBO buyerBO;
	private LmsFileUploadDTO lmsFileUploadDetailDTO;
    //AdminUserProfileContact is used in dao layer to get history details
	private AdminUserProfileContact  adminUserProfileContact;
	private static final String REDIRECT_TO_LANDING_PAGE = "redirectLandingPage";
	private List<AjaxResAsLabelValueDTO> buyerSuggestionList = new ArrayList<AjaxResAsLabelValueDTO>();
	private String searchTerm;
	// SL-21142 
	//private String buyerTextField;

     public LmsFileUploadAction(){
	   }
	 public LmsFileUploadAction (IlmsCredentialFileUploadService lmsFileUploadServiceImpl,LmsFileUploadDTO lmsFileUploadDetailDTO,IBuyerBO buyerBO,AdminUserProfileContact  adminUserProfileContact) {
		this.lmsFileUploadServiceImpl = lmsFileUploadServiceImpl;
		this.lmsFileUploadDetailDTO=lmsFileUploadDetailDTO;
		this.buyerBO = buyerBO;
		this.adminUserProfileContact=adminUserProfileContact;
	}

    public String getLmsDetailHistory() {
		logger.info("inside getLmsDetailHistory");
		try{
			List <Object> runningHistoryList= lmsFileUploadServiceImpl.getLmsHistory();
			getSession().setAttribute("runningLmsList", runningHistoryList);
			getRequest().removeAttribute("runningErrorList");

			String fileUploaded = getRequest().getParameter("fileUploaded");
			if (fileUploaded == null) { 
				getSession().removeAttribute("uploadStat");
				getSession().removeAttribute("uploadedFileName");
			}
		}catch(Exception e){
			logger.error("While retreiving LMS history from db:" + e);
		    e.printStackTrace();
       }
		return SUCCESS;
	}

     public String getLmsErrorHistory(){
		logger.info("inside getLmsErrorHistory");
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute("SecurityContext");
		Integer resourceId = securityContext.getVendBuyerResId();
		String fileId = getRequest().getParameter("fileId");
		try{
			List<Object> runningErrorList=lmsFileUploadServiceImpl.getLmsErrorHistory(resourceId,Integer.valueOf(fileId));
			getRequest().setAttribute("runningErrorList", runningErrorList);
		}catch(NullPointerException ex){
			logger.error("While getting the LMS error history list", ex);
            ex.printStackTrace();		
        }catch(Exception e){
			logger.error("While getting the LMS error history list", e);
            e.printStackTrace();		
        }
		return SUCCESS;
	}

	public String insertFileToDb(){
		logger.info("Inside insertfileToDb");
		try {
			SecurityContext securityContext = (SecurityContext) getSession().getAttribute("SecurityContext");
			Integer uploadedId = securityContext.getVendBuyerResId();

			// SL-21142 - START
			/*if(null == buyerTextField || buyerTextField.split("~").length <= 1 || !ValidationUtils.isInteger(buyerTextField.split("~")[0].trim())) {
				addFieldError("uploadAuditDTO.uploadFile","Please enter Buyer id.");
				getRequest().setAttribute("uploadStat", "false");
				return INPUT;
			}else{
				String buyerID = buyerTextField.split("~")[0].trim();
				lmsFileUploadDetailDTO.setBuyerId(Integer.valueOf(buyerID));
			}*/
			// - END

			if(null == lmsFileUploadDetailDTO.getUploadFile()) {
				addFieldError("uploadAuditDTO.uploadFile","Please select a file and retry");
				getRequest().setAttribute("uploadStat", "false");
				return INPUT;
			}

			if(!lmsFileUploadDetailDTO.getUploadFileFileName().trim().toLowerCase()
					.endsWith(".csv")) {
				addFieldError("uploadAuditDTO.uploadFile","Currently system supports only CSV format files. Please retry with CSV file.");
				getRequest().setAttribute("uploadStat", "false");
				return INPUT;
			}



			lmsFileUploadDetailDTO.setResourceId(uploadedId);
			getSession().setAttribute("uploadedFileFileName", lmsFileUploadDetailDTO.getUploadFileFileName());
			lmsFileUploadServiceImpl.insert(lmsFileUploadDetailDTO);
			getSession().setAttribute("uploadStat", "true");
			getSession().setAttribute("uploadedFileName", lmsFileUploadDetailDTO.getFileName());

			getRequest().removeAttribute("runningErrorList");

		}catch(Exception exception){
			exception.printStackTrace();
		}
		return REDIRECT_TO_LANDING_PAGE;
	}

	public void downloadFileDocument() throws Exception {		
		String documentId = getRequest().getParameter("docId");	
		try{
			if (StringUtils.isNotBlank(documentId)){
				lmsFileUploadDetailDTO.setId(Integer.parseInt(documentId));
				lmsFileUploadDetailDTO = lmsFileUploadServiceImpl.downloadFile(lmsFileUploadDetailDTO);	
				String credentialDocumentFileName = lmsFileUploadDetailDTO.getUploadFileFileName();
				String fileNameWithoutDate= credentialDocumentFileName.substring(0,credentialDocumentFileName.length()-15);
				String fileName = "attachment; fileName='"+fileNameWithoutDate+"'.csv";
				byte[] fileData = (byte[]) lmsFileUploadDetailDTO.getFileContent();
				getResponse().setContentType("text/csv");
				getResponse().setContentLength(fileData.length);
				getResponse().setHeader("Content-Disposition", fileName.replace("'",""));
				getResponse().setHeader("Expires", "0");
				getResponse().setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				getResponse().setHeader("Pragma", "public");
				getResponse().getOutputStream().write(fileData);
				getResponse().getOutputStream().flush();
				
			}	
		}catch(DataServiceException dse){
			logger.error("File Download exception in getting file content from DB due to exception: "+dse);
			throw new com.newco.marketplace.exception.core.BusinessServiceException(dse);
		}catch(Exception exp){
			logger.error("File Download general exception for lookup: "+exp);
			throw new com.newco.marketplace.exception.core.BusinessServiceException(exp);
		}finally{
			getResponse().getOutputStream().close();
		}
		//return "docDownload";		
	}	
	
	public String ajaxGetBuyerIDSuggestion(){
		buyerSuggestionList.clear();
		try{
			List<Buyer> buyerList;
			String originalSearch = searchTerm;
			//if searchTerm is bigger than 3 characters use previous results from cache
			searchTerm = searchTerm.length() > 3 ? searchTerm.substring(0, 3) : searchTerm;
			String keyName = "lmsCredUploadBuyerNameWith" + searchTerm;
			buyerList = (List<Buyer>)SimpleCache.getInstance().get(keyName);
			if (buyerList == null) {				
				buyerList = buyerBO.getMatchedBuyerByNameOrId(searchTerm);
				SimpleCache.getInstance().put(keyName, buyerList, SimpleCache.TEN_MINUTES);
			}
			for(Buyer iBuyer: buyerList){
				if((iBuyer.getBusinessName()!=null  && iBuyer.getBusinessName().toLowerCase().contains(originalSearch.toLowerCase())) ||
						iBuyer.getBuyerId().toString().contains(originalSearch)){
					if(iBuyer.getBusinessName()!=null){
					buyerSuggestionList.add(new AjaxResAsLabelValueDTO(iBuyer.getBuyerId()+" ~ "+iBuyer.getBusinessName(),
							iBuyer.getBuyerId()+" ~ "+iBuyer.getBusinessName()));
					}else{
						buyerSuggestionList.add(new AjaxResAsLabelValueDTO(iBuyer.getBuyerId().toString(),
								iBuyer.getBuyerId().toString()));
					}
				}
			}
			if(buyerSuggestionList.size()==0){
				buyerSuggestionList.add(new AjaxResAsLabelValueDTO("No suggestion found", ""));
			}
		}
		catch(BusinessServiceException bse){
			logger.error("Exception in getting buyer autocomplete suggestion values: "+bse);
		}
		return SUCCESS;
	}



	public IlmsCredentialFileUploadService getlmsFileUploadServiceImpl() {
		return lmsFileUploadServiceImpl;
	}

	public void setlmsFileUploadServiceImpl(IlmsCredentialFileUploadService lmsFileUploadServiceImpl) {
		this.lmsFileUploadServiceImpl = lmsFileUploadServiceImpl;
	}

	public LmsFileUploadDTO getModel() {
		return lmsFileUploadDetailDTO;
	}

	public IBuyerBO getBuyerBO() {
		return buyerBO;
	}

	public void setBuyerBO(IBuyerBO buyerBO) {
		this.buyerBO = buyerBO;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	// SL-21142 
	/*public String getBuyerTextField() {
		return buyerTextField;
	}

	public void setBuyerTextField(String buyerTextField) {
		this.buyerTextField = buyerTextField;
	}*/

	public List<AjaxResAsLabelValueDTO> getBuyerSuggestionList() {
		return buyerSuggestionList;
	}

	public void setBuyerSuggestionList(
			List<AjaxResAsLabelValueDTO> buyerSuggestionList) {
		this.buyerSuggestionList = buyerSuggestionList;
	}
	
}