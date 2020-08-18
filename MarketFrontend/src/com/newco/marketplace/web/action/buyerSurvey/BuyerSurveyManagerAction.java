package com.newco.marketplace.web.action.buyerSurvey;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.buyersurvey.BuyerLogo;
import com.newco.marketplace.dto.vo.buyersurvey.BuyerSurveyDTO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegate.buyersurvey.BuyerSurveyManagerDelegate;
import com.newco.marketplace.web.dto.FirmUploadLogo;
import com.newco.marketplace.web.utils.FileUtils;
import com.opensymphony.xwork2.ModelDriven;

import sun.misc.BASE64Decoder;

public class BuyerSurveyManagerAction extends SLBaseAction implements ModelDriven<BuyerSurveyDTO> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("BuyerSurveyManagerAction");
	
	private BuyerSurveyDTO buyerSurveyDTO = new BuyerSurveyDTO();
	
	private String jsonString;
	
	/**
	 * @return the buyerSurveyDTO
	 */
	public BuyerSurveyDTO getBuyerSurveyDTO() {
		return buyerSurveyDTO;
	}

	/**
	 * @param buyerSurveyDTO the buyerSurveyDTO to set
	 */
	public void setBuyerSurveyDTO(BuyerSurveyDTO buyerSurveyDTO) {
		this.buyerSurveyDTO = buyerSurveyDTO;
	}

	private BuyerSurveyManagerDelegate buyerSurveyDelegate;

	public BuyerSurveyManagerDelegate getBuyerSurveyDelegate() {
		return buyerSurveyDelegate;
	}

	public void setBuyerSurveyDelegate(BuyerSurveyManagerDelegate buyerSurveyDelegate) {
		this.buyerSurveyDelegate = buyerSurveyDelegate;
	}

	/**
	 * Method to display buyer survey manager page
	 * @return
	 */
	
	public String displayPage() 
	{
		logger.info("Inside BuyerSurveyManagerAction::displayPage() metjod");
		SecurityContext soContxt=(SecurityContext) getSession().getAttribute("SecurityContext");
		int buyerId = soContxt.getCompanyId();
		logger.info("Buyer id is "+buyerId);
		buyerSurveyDTO=buyerSurveyDelegate.getEmailConfiguration(buyerId);
		buyerSurveyDTO.setBuyerId(buyerId);
		getSession().setAttribute("surveyTypeMap", buyerSurveyDTO.getSurveyTypeMap());
		getSession().setAttribute("buyerSurveyModel", buyerSurveyDTO);
		
		if(null != getSession().getAttribute("message")) {
			getRequest().setAttribute("isSaved", getSession().getAttribute("isSaved"));
			getRequest().setAttribute("message", getSession().getAttribute("message"));
			getSession().removeAttribute("message");
		}
		return INPUT;
	}

	public String saveBuyerConfiguration() 
	{
		logger.info("Inside BuyerSurveyManagerAction::displayPage() metjod");
		SecurityContext soContxt=(SecurityContext) getSession().getAttribute("SecurityContext");
		int buyerId = soContxt.getCompanyId();
		getModel().setBuyerId(buyerId);
		Map<Integer,String> surveyTypeMap=(Map<Integer, String>) getSession().getAttribute("surveyTypeMap");
		getModel().setSurveyTypeMap(surveyTypeMap);
		getModel().setUserName(soContxt.getUsername());
		boolean isSaved = true;
		try {
			buyerSurveyDelegate.saveBuyerConfiguration(getModel());
		} catch (BusinessServiceException bse) {
			logger.error("Could not save email Configuration in Database ", bse);
			isSaved =false;
		}
		
		getSession().setAttribute("isSaved", isSaved);
		if(isSaved){
			getSession().setAttribute("message", "Update Successful. Your changes have been saved.");
		}else{
			getSession().setAttribute("message", "System Error. Unable to save your changes. Please update and submit again.");
		}
		
		return SUCCESS;
	}
	
	public String updateBuyerConfiguration() 
	{
	
		return SUCCESS;
	}
	
	public String getPreviewTemplate() {
		String eventName =getRequest().getParameter("eventName");
		String templateResponse= buyerSurveyDelegate.getEmailTemplate(buyerSurveyDTO,eventName);
		setJsonString(templateResponse);
		setBuyerSurveyDTO((BuyerSurveyDTO) getSession().getAttribute("buyerSurveyModel"));
		getBuyerSurveyDTO().setTemplateResponse(templateResponse);
		return "ajaxResponse";
	}
	
	@Override
	public BuyerSurveyDTO getModel() {
		return buyerSurveyDTO;
	}

	/**
	 * This method will get invoked when buyer upload the logo images.
	 * 
	 * @return String
	 */
	public String uploadBuyerLogo() {

		SecurityContext soContxt = (SecurityContext) getSession().getAttribute("SecurityContext");
		int buyerId = soContxt.getCompanyId();
		Gson gson = new Gson();
		boolean isValid = false;
		LogoUploadResponse uploadResponse = new LogoUploadResponse();
		FirmUploadLogo firmUploadLogo = new FirmUploadLogo();
		try {
			firmUploadLogo = gson.fromJson(getRequest().getReader(), FirmUploadLogo.class);
		} catch (IOException e) {
			logger.error("Error occured while accessing the image", e);
		}
		try {
			String base64Image = firmUploadLogo.getFormData().get(0).toString();
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] imageByte;
				imageByte = decoder.decodeBuffer(base64Image.split(",")[1]);
			File outputfile = new File(firmUploadLogo.getFile().getName());
			OutputStream stream = new FileOutputStream(outputfile);
			
			stream.write(imageByte);
			stream.close();

			BuyerLogo buyerLogoProbs = buyerSurveyDelegate.getBuyerLogoProperties();

			isValid = validateLogoFile(outputfile, buyerLogoProbs, uploadResponse);

			if (isValid) {
				String fileName = new StringBuilder().append(buyerId).append("_email_logo.")
						.append(FilenameUtils.getExtension(outputfile.getName())).toString();
				File file = new File(buyerLogoProbs.getUploadPath() + fileName);
				org.apache.commons.io.FileUtils.copyFile(outputfile, file);
				uploadResponse.setBuyerLogoName(fileName);
				uploadResponse.setStatus("success");
				uploadResponse.setMessage("Logo image successfully saved");
			}
		} catch (IOException e) {
			logger.error("Error occured while trying to save attachment", e);
			uploadResponse.setStatus("error");
			uploadResponse.setMessage("Error while uploading logo. Please try again.");
		}
		setJsonString(gson.toJson(uploadResponse).replace("\\", ""));
		return "json";
	}

	private boolean validateLogoFile(File outputfile, BuyerLogo buyerLogo, LogoUploadResponse uploadResponse) throws IOException {
		boolean isValid;
		Long fileSize = outputfile.length();
		
		FileUtils fileUtils = new FileUtils();
		isValid = fileUtils.checkAttachmentForImage(outputfile);
		if (!isValid) {
			uploadResponse.setStatus("error");
			uploadResponse.setMessage("Invalid file type. Please select valid file type.");
		} else if (fileSize > buyerLogo.getMaxSize()) {
			uploadResponse.setStatus("error");
			uploadResponse.setMessage("Too big a file. Resize the logo to less than 5MB file size and try again.");
		}
		
		return isValid;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

}
