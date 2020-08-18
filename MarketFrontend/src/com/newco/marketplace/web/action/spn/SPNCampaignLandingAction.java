package com.newco.marketplace.web.action.spn;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ISPNBuyerDelegate;
import com.newco.marketplace.web.dto.SODocumentDTO;
import com.newco.marketplace.web.dto.SPNBuilderDocRowDTO;
import com.newco.marketplace.web.dto.SPNBuilderFormDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;


public class SPNCampaignLandingAction extends SLBaseAction implements Preparable, ModelDriven<SODocumentDTO>
{

	private static final long serialVersionUID = -2389925564397223643L;
	private ISPNBuyerDelegate buyerSPNDelegate;
	private List<SPNBuilderDocRowDTO> documentsDTOList;
	private List<SPNBuilderDocRowDTO> spnRelatedDocsList;
	private SPNBuilderFormDTO spnBuilderFormDTO = new SPNBuilderFormDTO();
	private SODocumentDTO spnDocument = new SODocumentDTO();
	private static final Logger logger = Logger.getLogger("SOWDocumentsAndPhotosAction");	
	
	
	public SPNCampaignLandingAction(ISPNBuyerDelegate buyerSPNDelegate){
		this.buyerSPNDelegate = buyerSPNDelegate;
		
	}
	
	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();	
		// get SPN details
		ServiceOrdersCriteria criteria = this.get_commonCriteria();		
		documentsDTOList = buyerSPNDelegate.getAllDocumentsList(criteria.getCompanyId(), Constants.DocumentTypes.RESOURCE, 
				                                           criteria.getRoleId(), criteria.getVendBuyerResId());
	    String spnId = getRequest().getParameter("spnID");
	    if(com.newco.marketplace.web.utils.SLStringUtils.isNullOrEmpty(spnId)){
	    	logger.debug("Select Provider network number not available");
	    	return;
	    }
	    
	    Integer spnIdInt = Integer.valueOf(spnId);
	    spnRelatedDocsList = buyerSPNDelegate.getSpnRelatedDocDTOList(spnIdInt);

	    spnBuilderFormDTO =  buyerSPNDelegate.getSPNDetails(spnIdInt, documentsDTOList);
	    
	    spnBuilderFormDTO.setDocumentsDetailsList(spnRelatedDocsList);

	    // Handle Company Logo
		DocumentVO doc = buyerSPNDelegate.getLogoDoc( spnBuilderFormDTO.getBuyerId(), Constants.DocumentTypes.BUYER, 
		        OrderConstants.BUYER_ROLEID, criteria.getVendBuyerResId());
		getSession().setAttribute("logoDoc", doc);
	    
		
		setAttribute("spnInfo", spnBuilderFormDTO);
		setAttribute("spnRelatedDoc", spnRelatedDocsList);
		
	
	}	

	public String displayPage() throws Exception
	{
	    String spnId = getRequest().getParameter("spnID");
	    if(com.newco.marketplace.web.utils.SLStringUtils.isNullOrEmpty(spnId)){
	    	logger.debug("Select Provider network number not available");
	    	return ERROR;
	    	
	    }
	    
	    //Forward inviteeName to spn landing page
	    String inviteeName = getRequest().getParameter("inviteeName");
	    setAttribute("inviteeName", inviteeName);	    
	    
		return SUCCESS;
	}
	
	/**
	 * get Logo doc of the buyer of the spn
	 * @return
	 * @throws Exception
	 */
	public String displayLogoDoc() throws Exception {
				
		DocumentVO doc = (DocumentVO)getSession().getAttribute("logoDoc");		
		if(doc == null)
			return SUCCESS;
	    
		
		try{
			
			StringTokenizer fileName = new StringTokenizer(doc.getFileName(), ".");
			String extension = "";
			while (fileName.hasMoreTokens()) {
				extension = fileName.nextToken();
			}
			if (extension.equals("gif")) {
				getResponse().setContentType("image/gif");
			}else if(extension.equals("jpeg")|| extension.equals("jpg")){
				getResponse().setContentType("image/jpeg");
			}else if(extension.equals("png")){
				getResponse().setContentType("image/png");
			}else {
				getResponse().setContentType("text/html");
			}
			String header = "attachment;filename=\""
					+ doc.getFileName() + "\"";
			getResponse().setHeader("Content-Disposition", header);
			InputStream in = new ByteArrayInputStream(doc.getBlobBytes());
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
		getSession().removeAttribute("logoDoc");
		return SUCCESS;
	}
	
	/**
	 * provider response as interested
	 * @return
	 * @throws Exception
	 */
	public String buttonInterested() throws Exception
	{
		
		setAttribute("interested", true);
		setAttribute("applicationSubmitted", "Your application has been submitted");
		
	    String spnId = getRequest().getParameter("spnID");
	    if(com.newco.marketplace.web.utils.SLStringUtils.isNullOrEmpty(spnId)){
	    	logger.debug("Select Provider network number not available");
	    	return ERROR;
	    	
	    }
	    
	    String spnNetworkID = getRequest().getParameter("spnNetworkID");
	    if(spnNetworkID == null || spnNetworkID.equals("")){
	    	logger.debug("Select Provider network number not available");
	    	return ERROR;
	    }
	    Integer spnNetworkIdInt = Integer.valueOf(spnNetworkID);
		setAttribute(Constants.SESSION.SOD_MSG,	"spn.invite.accepted");

		/* persist response as interested*/
		
		buyerSPNDelegate.updateStatusByProviderResponse(Constants.SPN.INTERESTED_STR, spnNetworkIdInt);
	
		return SUCCESS;
	}
	
	
	/**
	 * provider response as not Interested
	 * @return
	 * @throws Exception
	 */
	public String buttonNotInterested() throws Exception
	{
		
		setAttribute("interested", false);
		
	    
	    String spnNetworkID = getRequest().getParameter("spnNetworkID");
	    if(spnNetworkID == null || spnNetworkID.equals("")){
	    	logger.debug("Select Provider network number not available");
	    	return ERROR;
	    }
	    	
	    
	    Integer spnNetworkIdInt = Integer.valueOf(spnNetworkID);
	    
		setAttribute(Constants.SESSION.SOD_MSG,	"spn.invite.not_interested");

		/* persist response as not interested*/
		buyerSPNDelegate.updateStatusByProviderResponse(Constants.SPN.NOT_INTERESTED_STR, spnNetworkIdInt);
		
		
		return SUCCESS;
	}
	
	/**
	 * upload the document
	 * @return
	 */
	public String documentUpload(){
		
		String spnId = getRequest().getParameter("spnID");
	    if(com.newco.marketplace.web.utils.SLStringUtils.isNullOrEmpty(spnId)){
	    	spnId="1";
	    	
	    }		

	    String spnNetworkID = getRequest().getParameter("spnNetworkID");
	    if(spnNetworkID == null || spnNetworkID.equals("")){
	    	logger.debug("Select Provider network number not available");
	    	return ERROR;
	    }
	    Integer spnNetworkIdInt = Integer.valueOf(spnNetworkID);

		try{
			DocumentVO documentVO = new DocumentVO();
			
			spnDocument = getModel();
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
				documentVO.setDocument(spnDocument.getUpload());
				documentVO.setFileName(spnDocument.getUploadFileName());
				documentVO.setFormat(spnDocument.getUploadContentType());
				documentVO.setDocSize(spnDocument.getUpload().length());
				documentVO.setEntityId(_commonCriteria.getVendBuyerResId());
				documentVO.setRoleId(_commonCriteria.getRoleId());
				documentVO.setCompanyId(_commonCriteria.getCompanyId());
				documentVO.setDocCategoryId(2);// need to be clarified
				documentVO.setSpnNetworkId(spnNetworkIdInt);
		
				ProcessResponse pr = new ProcessResponse();
				
				pr = buyerSPNDelegate.insertSPNDocument(documentVO);

				 
				if (pr!=null){
					setErrorMessage(pr);
				}
			
			}

			setAttribute("interested", true);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * set error messages if any after file upload
	 * @param pr
	 */
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
	
	
	
	
	public SODocumentDTO getModel()	{
			return spnDocument;
	}
	
	public void setModel(SODocumentDTO theModel) {
		this.spnDocument = theModel;
	}

	


		
}
