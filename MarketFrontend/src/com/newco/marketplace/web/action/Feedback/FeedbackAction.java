package com.newco.marketplace.web.action.Feedback;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.dto.SOWError;
import com.sears.os.utils.StringUtils;
import com.servicelive.feedback.services.FeedbackService;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.feedback.FeedbackVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.opensymphony.xwork2.ModelDriven;

import flexjson.JSONSerializer;

import org.apache.log4j.Logger;

public class FeedbackAction extends SLBaseAction implements ModelDriven<Map<String,Object>> {
	
	private static final long serialVersionUID = -866612647463785085L;
	private static final Logger logger = Logger.getLogger(FeedbackAction.class);
	private FeedbackService feedbackService;
	public static final String S_JSON_RESULT = "jsonResult";
	public static final String HAS_ERROR = "hasError";
	private Map<String,Object> model= new HashMap<String,Object>();
	//private FeedbackVO feedbcakVO = new FeedbackVO();
	private static final String ACTIVE_TAB = "omDisplayTab";
	private static final String PAGE_NAME = "pageName";
	//private static final String CONTACT_IND = "contactIndicator";
	private static final String SOURCE_URL = "sourceURL";
	private static final String FEEDBACK_CAT_ID = "categoryId";
	private static final String FEEDBACK_COMMENTS = "feedbackComments";
	private static final String PNG = ".png";
	private static final String JPG = ".jpg";
	private static final String GIF = ".gif";
	private static final String DOC = ".doc";
	private static final String DOCX = ".docx";
	private static final String PROVIDER_FEEDBACK_DESC = "Provider feedback";
	//private List<SOWError> feddbackErrors = new ArrayList<SOWError>();
	private File uploadFeedback;//
	private String uploadFeedbackContentType; //The content type of the file
    private String uploadFeedbackFileName; //The uploaded file name



	public String loadfeedBackCategory() {
		try {
			List<FeedbackVO> categoryList = new ArrayList<FeedbackVO>();
			categoryList = feedbackService.loadFeedbackCategory();
			setAttribute("feedbackCategoryList", categoryList);

		} catch (BusinessServiceException bse) {
			logger.error("Exception in FeedbackAction --> loadfeedBackCategory() due to"
					+ bse.getMessage());
		}
		return SUCCESS;
	}

	public String saveFeedback() {
		FeedbackVO feedbcakVO = new FeedbackVO();
		if(null != feedbcakVO){
			/*Commented as per SL-18927*/
			//Boolean contactInd = Boolean.parseBoolean((getRequest().getParameter(CONTACT_IND)));
			//feedbcakVO.setContactInd(contactInd);
			
			setUserDetails(feedbcakVO);
			setPagedetails(feedbcakVO);
			mapFeedbackDetails(feedbcakVO);
			File file = null;
			File[] uploadedFile = (File[])model.get("uploadFeedback");	
			if(null!= uploadedFile && uploadedFile.length>0){
				file =  uploadedFile[0];	
			}
			if(null != file)
			uploadDocument(feedbcakVO);
			String hasError = (String)model.get(HAS_ERROR);
			if(StringUtils.isBlank(hasError)||hasError.equals("false")){
				
				if (null != feedbcakVO) {
					try {
						feedbackService.saveFeedback(feedbcakVO);
					} catch (BusinessServiceException bse) {
						logger.error("Exception in FeedbackAction --> saveFeedback() due to"
								+ bse.getMessage());
					}
				}
				String success="success";
				model.put(S_JSON_RESULT, getJSON(success));
			}
		}

		return SUCCESS;
		
	}
	
	public void uploadDocument(FeedbackVO feedbcakVO){
		if(null != feedbcakVO){
			validateDoc();
			String hasError = (String)model.get(HAS_ERROR);
			if(StringUtils.isBlank(hasError) || hasError.equals("false")){
				try {
				DocumentVO documentVO = new DocumentVO();
				//setUserDetails(feedbcakVO);
				//setPagedetails(feedbcakVO);
				mapDocdetails(documentVO,feedbcakVO);
				feedbackService.uploadFeedbackDoc(documentVO);
				feedbcakVO.setDocumentId(documentVO.getDocumentId());
				} catch (BusinessServiceException bse) {
					logger.error("Exception in FeedbackAction --> saveFeedback() due to"
							+ bse.getMessage());
				}
			}
		}
	}
	
	
	private void mapDocdetails(DocumentVO documentVO,FeedbackVO feedbcakVO){
		File file = null;
		File[] uploadedFile = (File[])model.get("uploadFeedback");	
		if(null!= uploadedFile && uploadedFile.length>0){
			file =  uploadedFile[0];	
		}
		
		String[] uploadedFileName = (String[])model.get("uploadFeedbackFileName");
		String fileName ="";
		if(null != uploadedFileName && uploadedFileName.length>0)
			fileName = uploadedFileName[0];
		
		String[] uploadedFileContent = (String[])model.get("uploadFeedbackContentType");
		String fileType ="";
		if(null != uploadedFileContent && uploadedFileContent.length>0)
			fileType = uploadedFileContent[0];
		
		documentVO.setDocument(file);
		documentVO.setDocCategoryId(OrderConstants.PROVIDER_FEEDBACK_DOC_CATEGORY);
		documentVO.setFileName(fileName);
		documentVO.setFormat(fileType);
		documentVO.setRoleId(feedbcakVO.getRoleId());
		documentVO.setDocSize(file.length());
		documentVO.setEntityId(feedbcakVO.getResourceId());
		documentVO.setCompanyId(feedbcakVO.getCompanyId());
		if(feedbcakVO.getRoleId().equals(1)){
		documentVO.setVendorId(feedbcakVO.getCompanyId());
		documentVO.setDescription(PROVIDER_FEEDBACK_DESC);
		}
		//Setting the source of photo as from Feedback
		documentVO.setDocSource(MPConstants.PROVIDER_FEEDBACK_INDICATOR);
	}
	
	private void validateDoc(){
		File file = null;
		File[] uploadedFile = (File[])model.get("uploadFeedback");	
		if(null!= uploadedFile && uploadedFile.length>0){
			file =  uploadedFile[0];	
		}
		String[] uploadedFileName = (String[])model.get("uploadFeedbackFileName");
		String fileName ="";
		if(null != uploadedFileName && uploadedFileName.length>0)
			fileName = uploadedFileName[0];
		if(null != file){
			//validate doc size
			Long docSize=file.length();
			Integer size = docSize.intValue()/OrderConstants.SIZE_KB;
			String errorMsg = "";
			model.put(HAS_ERROR, "false");
			if(size>FIVE_KB){
				errorMsg = "Invalid file size. Please attach a file no larger than 5MB.";
				model.put(HAS_ERROR, "true");
			}
			//validate file extension
			//String fileName =getUploadFeedbackFileName();
			String fileExtenstion = "";
			if(null!=fileName && !(OrderConstants.EMPTY_STR.equals(fileName.trim()))){
				Integer fileExtensionIndex=fileName.lastIndexOf(".");
				if(fileExtensionIndex==-1)
				{
					model.put(HAS_ERROR, "true");
					errorMsg = errorMsg + "Invalid File format. Please use one of the valid formats given below.";
				}
				else
				{
				fileExtenstion = fileName.substring(fileExtensionIndex,fileName.length());
				if(!(fileExtenstion.equalsIgnoreCase(PNG) || fileExtenstion.equalsIgnoreCase(JPG)|| fileExtenstion.equalsIgnoreCase(GIF)|| fileExtenstion.equalsIgnoreCase(DOC)|| fileExtenstion.equalsIgnoreCase(DOCX))){
					model.put(HAS_ERROR, "true");
					errorMsg = errorMsg + "Invalid File format. Please use one of the valid formats given below.";
				}
				}
			}			
			model.put(S_JSON_RESULT, getJSON(errorMsg));
		}
	}

	
	private void mapFeedbackDetails(FeedbackVO feedbcakVO){
		Integer feedbackCatId = Integer.parseInt(getRequest().getParameter(FEEDBACK_CAT_ID));
		String feedbackComments = getRequest().getParameter(FEEDBACK_COMMENTS);	
		feedbcakVO.setCategoryId(feedbackCatId);
		feedbcakVO.setFeedbackComments(feedbackComments);
	}
	private void setPagedetails(FeedbackVO feedbcakVO) {
		String tab = (String) getSession().getAttribute(ACTIVE_TAB);
		String requestURL = getRequest().getParameter(SOURCE_URL);
		String pageName = getRequest().getParameter(PAGE_NAME);
		feedbcakVO.setTabName(tab);
		feedbcakVO.setPageName(pageName);
		feedbcakVO.setSourceURL(requestURL);
	}

	private void setUserDetails(FeedbackVO feedbcakVO) {
		createCommonServiceOrderCriteria();
		if (null != this._commonCriteria
				&& null != this._commonCriteria.getSecurityContext()) {
			feedbcakVO.setRoleId(this._commonCriteria.getSecurityContext()
					.getRoleId());
			feedbcakVO.setCompanyId(this._commonCriteria.getSecurityContext()
					.getCompanyId());
			feedbcakVO.setResourceId(this._commonCriteria.getSecurityContext()
					.getVendBuyerResId());
			boolean adopted = this._commonCriteria.getSecurityContext().isAdopted();
			if(adopted){
				String adminUserName = this._commonCriteria.getSecurityContext().getSlAdminUName();
				logger.info("SL 18780 SL Admin user name is"+adminUserName);
				if(null!=adminUserName)
				{
					feedbcakVO.setModifiedBy(adminUserName);
				}
			}
			else
			{
				feedbcakVO.setModifiedBy(this._commonCriteria.getSecurityContext()
						.getUsername());	
			}
		}

	}
	
	public String getJSON(Object obj) {
		String rval= "null";
		JSONSerializer serializer = new JSONSerializer();

        if (obj != null) try {
			rval= serializer.exclude("*.class").serialize(obj); 
		} catch (Throwable e) {
			logger.error("Unable to generate JSON for object: " + obj.toString(), e);
		}				
		return rval;
	}

	public FeedbackService getFeedbackService() {
		return feedbackService;
	}

	public void setFeedbackService(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}

	

	public File getUploadFeedback() {
		return uploadFeedback;
	}

	public void setUploadFeedback(File uploadFeedback) {
		this.uploadFeedback = uploadFeedback;
	}

	public String getUploadFeedbackContentType() {
		return uploadFeedbackContentType;
	}

	public void setUploadFeedbackContentType(String uploadFeedbackContentType) {
		this.uploadFeedbackContentType = uploadFeedbackContentType;
	}

	public String getUploadFeedbackFileName() {
		return uploadFeedbackFileName;
	}

	public void setUploadFeedbackFileName(String uploadFeedbackFileName) {
		this.uploadFeedbackFileName = uploadFeedbackFileName;
	}
	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}


}
