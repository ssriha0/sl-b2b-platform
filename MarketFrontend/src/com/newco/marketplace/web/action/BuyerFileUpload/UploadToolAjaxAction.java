package com.newco.marketplace.web.action.BuyerFileUpload;

import java.util.List;
import org.apache.log4j.Logger;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.dto.buyerFileUpload.UploadAuditDTO;

public class UploadToolAjaxAction extends SLBaseAction {

	private static final long serialVersionUID = 102030405060L;

	private static final Logger logger = Logger.getLogger(UploadToolAjaxAction.class);

	private List<UploadAuditDTO> uploadToolStatusList;
	
	public String execute() throws Exception {
		
		String buyerResourceId = getRequest().getParameter("rid");
		logger.info("Entering UploadToolAjaxAction.retrieveUploadToolStatus; Resource Id = ["+buyerResourceId+"]");

		//-- Uncomment line below to call Delegate/BO/DAO to get List<UploadAuditDTO> for given buyerResourceId
		//uploadToolStatusList = uploadToolDelegate.retrieveUploadStatus(buyerResourceId);

		/*
		//-- Remove this comment block once actual status list is coming from database
		uploadToolStatusList = new ArrayList<UploadAuditDTO>();
		for (int i = 0; i < 4; ++i) {
			UploadAuditDTO dto = new UploadAuditDTO();
			dto.setFileName("fileName"+i);
			dto.setSubmittedDateTime("submittedDateTime"+i);
			dto.setSubmittedBy("submittedBy"+i);
			dto.setSuccessCount(i * 10);
			dto.setErrorCount(i);
			uploadToolStatusList.add(dto);
		}
		*/

		logger.info("Exiting UploadToolAjaxAction.retrieveUploadToolStatus SUCCESS");
		return SUCCESS;
	}

	public List<UploadAuditDTO> getUploadToolStatusList() {
		return uploadToolStatusList;
	}

	public void setUploadToolStatusList(
			List<UploadAuditDTO> uploadToolStatusList) {
		this.uploadToolStatusList = uploadToolStatusList;
	}
}
