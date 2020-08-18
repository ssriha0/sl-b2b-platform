package com.newco.marketplace.web.action.BuyerFileUpload;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.RunningHistoryVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadFileVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.buyer.IBuyerFileUploadDelegate;
import com.newco.marketplace.web.dto.SODocumentDTO;
import com.newco.marketplace.web.dto.buyerFileUpload.UploadAuditDTO;
import com.newco.marketplace.web.dto.buyerFileUpload.UploadAuditErrorDTO;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class BuyerFileUploadAction  extends SLDetailsBaseAction implements Preparable, ModelDriven<UploadAuditDTO> {

	private static final long serialVersionUID = -8394559024055757155L;
	
	private static final Logger logger = Logger.getLogger(BuyerFileUploadAction.class);
	
	private IBuyerFileUploadDelegate iBuyerFileUploadDelegate;
	private UploadAuditDTO uploadAuditDTO;
	private static final String REDIRECT_TO_LANDING_PAGE = "redirectLandingPage";
	private static String _helpLink = null;
	private static String _sampleFileLink = null;
	private Integer hidFileId;

	private String getHelpLink() {
		if( _helpLink== null) {
			_helpLink = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.SO_IMPORT_TOOL_HELP_URL);
		}
		return _helpLink;
	}

	private String getSampleFileLink() {
		if(_sampleFileLink == null) {
			_sampleFileLink = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.SO_IMPORT_TOOL_SAMPLE_FILE_URL);
		}
		return _sampleFileLink;
	}

	public BuyerFileUploadAction (IBuyerFileUploadDelegate iBuyerFileUploadDelegate, UploadAuditDTO uploadAuditDTO) {
		this.iBuyerFileUploadDelegate = iBuyerFileUploadDelegate;
		this.uploadAuditDTO = uploadAuditDTO;
	}

	public void prepare() throws Exception {
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute("SecurityContext");
		Integer buyerId = securityContext.getCompanyId();
		Integer buyerResourceId = securityContext.getVendBuyerResId();
		
		loadLogoInSession(buyerId);
		loadRunningHistoryInRequest(buyerResourceId);
		
		getRequest().setAttribute("helpLink", getHelpLink());
		getRequest().setAttribute("sampleFileLink", getSampleFileLink());
		
		String fileUploaded = getRequest().getParameter("fileUploaded");
		if (fileUploaded == null) { 
			getSession().removeAttribute("uploadStat");
			getSession().removeAttribute("uploadedFileName");
		}
	}
	
	private void loadLogoInSession(Integer buyerId) throws Exception {
		SODocumentDTO soDocDTO = new SODocumentDTO();
		UploadFileVO uploadFileVO  = iBuyerFileUploadDelegate.getUserLogo(buyerId);
		if (uploadFileVO != null) {
			byte [] logo =  uploadFileVO.getLogo();
			String logoFileName = uploadFileVO.getName_of_file();
			String urlFilePath = System.getProperty("java.io.tmpdir")+logoFileName;
			soDocDTO.setName(logoFileName);
			soDocDTO.setBlobBytes(logo);
			getSession().setAttribute("soDocDTO", soDocDTO);
			getSession().setAttribute("urlFilePath", urlFilePath);
			uploadAuditDTO.setUrlFilePath(urlFilePath);
		}
	}
	
	private void loadRunningHistoryInRequest(Integer buyerResourceId) throws Exception {
		List<RunningHistoryVO> runningHistoryList= iBuyerFileUploadDelegate.getRunningHistory(buyerResourceId);
		getRequest().setAttribute("runningHistoryList", runningHistoryList);
	}

	public String execute() throws Exception {		
		return SUCCESS;
	}
	
	public String submit() throws Exception {
		logger.info("inside submit method");
		try {
			
			if(null == uploadAuditDTO.getUploadFile()) {
				addFieldError("uploadAuditDTO.uploadFile","Please select a file and retry");
				getSession().setAttribute("uploadStat", "false");
				return INPUT;
			}
			
			//SL-11157:Commenting out content-type validation for now, coz in some cases excel file gives application/octet-stream mime type
			//String fileContentType = uploadAuditDTO.getUploadFileContentType().trim();
			//logger.info("File Content Type = ["+fileContentType+"]");
			if(!uploadAuditDTO.getUploadFileFileName().trim().toLowerCase().endsWith(".xls")) {
				addFieldError("uploadAuditDTO.uploadFile","Currently system supports only XLS format files. Please retry with XLS file.");
				getSession().setAttribute("uploadStat", "false");
				return INPUT;
			}
			
			//SL-21129
			FileInputStream fileIn = new FileInputStream(uploadAuditDTO.getUploadFile());
            HSSFWorkbook wb = new HSSFWorkbook(fileIn);
            HSSFSheet sheet = wb.getSheetAt(0);
            Iterator<HSSFRow> rows =  sheet.rowIterator();
            String str = null;
                              
                  while(rows.hasNext()){
                        HSSFRow row = (HSSFRow) rows.next();
                        HSSFCell cell= row.getCell((short) 47);
                        
                        if(cell.getCellNum() == 47){
                        str = cell.getStringCellValue();
                              
                        if(str.length() > 50){
                              addFieldError("uploadAuditDTO.uploadFile","Last name contains more than 50 characters in row number "+ (row.getRowNum()+1)+". Please correct the last name.");
                              getSession().setAttribute("uploadStat", "false");
                              return INPUT;
                        }
                        }
                  }

			
			SecurityContext securityContext = (SecurityContext) getSession().getAttribute("SecurityContext");
			Integer buyerId = securityContext.getCompanyId();
			Integer buyerResourceId = securityContext.getVendBuyerResId();
			Integer buyerContactId = securityContext.getRoles().getContactId(); 
			uploadAuditDTO.setBuyerId(buyerId);
			uploadAuditDTO.setBuyerResourceId(buyerResourceId);
			uploadAuditDTO.setBuyerResContactId(buyerContactId);
			uploadAuditDTO.setUploadFileName(uploadAuditDTO.getUploadFileFileName().substring(0,uploadAuditDTO.getUploadFileFileName().length()-4)+"_");
			uploadAuditDTO.setBlobFromFile(uploadAuditDTO.getUploadFile());
			iBuyerFileUploadDelegate.insertFiletoDb(uploadAuditDTO);
			getSession().setAttribute("uploadStat", "true");
			getSession().setAttribute("uploadedFileName", uploadAuditDTO.getUploadFileFileName());
		} catch(DelegateException ex) {
			logger.info("Exception Occured while processing the request due to"+ex.getMessage());
			addActionError("Exception Occured while processing the request due to"+ex.getMessage());
			getSession().setAttribute("uploadStat", "false");
			return ERROR;
		}
		
		return REDIRECT_TO_LANDING_PAGE;
	}
	
	/**
	 * get doc of the buyer of the so ID
	 * @return
	 * @throws Exception
	 */
	public String buyerLogoDisplay() throws Exception {
		
		
		SODocumentDTO sessionImage = (SODocumentDTO)getSession().getAttribute("soDocDTO");
		SODocumentDTO selectedImage = sessionImage; 
		int extStart = (sessionImage.getName()).lastIndexOf(".");
		String format = (sessionImage.getName()).substring(extStart+1);
		
		if(extStart <= 0){
			return SUCCESS;
		}else{
			format="image/"+format.trim();
		}
		getSession().removeAttribute("soDocDTO");
		selectedImage.setFormat(format);
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
		return NONE;
	}
	
	/**
	 * This method is used to fetch the list of error records in an uploaded file.
	 * @return String
	 */
	public String getErrorRecordList() throws Exception{		
		List<UploadAuditErrorDTO> errorRecordList = iBuyerFileUploadDelegate.getErrorRecordList(hidFileId);		
		getRequest().setAttribute("errorRecordList",errorRecordList);
		return "errorList";
	}
	
	public IBuyerFileUploadDelegate getIBuyerFileUploadDelegate() {
		return iBuyerFileUploadDelegate;
	}
	
	public void setIBuyerFileUploadDelegate(
			IBuyerFileUploadDelegate buyerFileUploadDelegate) {
		iBuyerFileUploadDelegate = buyerFileUploadDelegate;
	}

	public UploadAuditDTO getUploadAuditDTO() {
		return uploadAuditDTO;
	}

	public void setUploadAuditDTO(UploadAuditDTO uploadAuditDTO) {
		this.uploadAuditDTO = uploadAuditDTO;
	}

	public UploadAuditDTO getModel() {
		return null;
	}

	public Integer getHidFileId() {
		return hidFileId;
	}

	public void setHidFileId(Integer hidFileId) {
		this.hidFileId = hidFileId;
	}
}
