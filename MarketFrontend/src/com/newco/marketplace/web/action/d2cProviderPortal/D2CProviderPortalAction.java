package com.newco.marketplace.web.action.d2cProviderPortal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DispatchLocationVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.VendorServiceOfferPriceVo;
import com.newco.marketplace.dto.vo.VendorServiceOfferingVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.Coverage;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.D2CProviderUpdateVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.RateCardRequestVO;
//import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.RateCardRequestVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.VendorServiceOfferedOnVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.ZipCodesData;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.ZipCodesWrapper;
import com.newco.marketplace.dto.vo.provider.VendorDocumentVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.ID2CProviderPortalDelegate;
import com.newco.marketplace.web.delegates.provider.IProviderProfilePagesDelegate;
import com.newco.marketplace.web.dto.FirmUploadLogo;
import com.newco.marketplace.web.dto.PrimaryIndustryDTO;
import com.newco.marketplace.web.dto.SkuDetail;
import com.newco.marketplace.web.dto.SkuDetailRequestDTO;
import com.newco.marketplace.web.dto.SkuPrices;
import com.newco.marketplace.web.utils.FileUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ActionContext;

import flexjson.JSONSerializer;

/**
 * 
 * This class is to handle page loading for D2C Provider Portal and to handle
 * ajax calls from D2C Provider Portal
 * 
 * @author rranja1
 * 
 */
public class D2CProviderPortalAction extends SLBaseAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(D2CProviderPortalAction.class);

	// Constants
	private static final String STRING_EMPTY = "";
	private static final String FILTER_CREDENTIALS = "credentials";
	private static final String FILTER_CONTACT = "contact";

	// Gson object
	private final Gson gson = new Gson();

	// delegate instance
	private final ID2CProviderPortalDelegate d2cProviderPortalDelegate;
	private IProviderProfilePagesDelegate providerProfilePagesDelegate;

	// Json response root element for ajax calls
	private String jsonString = STRING_EMPTY;

	// json serializer to convert Object into json
	// call toJson method
	private final JSONSerializer jsonSerializer = new JSONSerializer().exclude("*.class");

	// uploadfirmlogo
	private String photoDocFileSize;

	public D2CProviderPortalAction(ID2CProviderPortalDelegate d2cProviderPortalDelegate) {
		this.d2cProviderPortalDelegate = d2cProviderPortalDelegate;
	}

	/**
	 * This method is called on page init to check logged in user info
	 * 
	 * @return
	 */
	public String initPage() {
		logger.info("start initPage of D2CProviderPortalAction");

		String returnAction = SUCCESS;
		String vendorIdStr = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		logger.debug("vendor id: " + vendorIdStr);
		if (null != vendorIdStr && vendorIdStr.trim().length() > 0) {
			returnAction = SUCCESS; //d2cProviderPortalDelegate.updateVendorServiceOfferings(vendorIdStr) ? SUCCESS : ERROR;
		} else {
			returnAction = ERROR;
			logger.warn("vendor id is null. redirecting to error page from D2CProviderPortalAction.");
		}

		logger.debug("returnAction: " + returnAction);

		logger.info("end initPage of D2CProviderPortalAction");
		return returnAction;
	}

	/**
	 * This method is called to get provider profile data
	 * 
	 * @return
	 */
	public String getProviderProfileDetails() {

		logger.info("entering getProviderProfileDetails of D2CProviderPortalAction");
		String vendorIdStr = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		logger.debug("vendor id: " + vendorIdStr);

		if (null != vendorIdStr) {

			List<String> filters = new ArrayList<String>();
			filters.add(FILTER_CONTACT);
			filters.add(FILTER_CREDENTIALS);

			List<String> vendorIds = new ArrayList<String>();
			vendorIds.add(vendorIdStr);

			jsonString = toJSON(d2cProviderPortalDelegate.getProviderProfileDetails(vendorIds, filters));
		} else {
			logger.warn("Vendor id is not present in session.");
			jsonString = "Error!";
		}

		logger.info("exiting getProviderProfileDetails of D2CProviderPortalAction");
		return SUCCESS;
	}

	/**
	 * This method is called to get dispatch location
	 * 
	 * @return
	 */
	public String getDispatchLocation() {
		logger.info("entering getDispatchLocation of D2CProviderPortalAction");
		String vendorId = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		logger.debug("vendor id : " + vendorId);

		if (null != vendorId) {
			jsonString = toJSON(d2cProviderPortalDelegate.getDispatchLocation(vendorId));
		}

		logger.info("exiting getDispatchLocation of D2CProviderPortalAction");
		return SUCCESS;
	}
	
	/**
	 * This method is called to get list of skus not opted by vendor
	 * 
	 * @return
	 */
	public String getNotOptedSKUList() {
		logger.info("entering getNotOptedSKUList of D2CProviderPortalAction");
		String vendorId = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		logger.debug("vendor id : " + vendorId);

		if (null != vendorId) {
			jsonString = toJSON(d2cProviderPortalDelegate.getNotOptedSKUList(vendorId));
		}

		logger.info("exiting getNotOptedSKUList of D2CProviderPortalAction");
		return SUCCESS;
	}

	/**
	 * This method is called to add/update dispatch location
	 * 
	 * @return
	 */
	public String addUpdateDispatchLocation() {
		logger.info("entering addUpdateDispatchLocation of D2CProviderPortalAction");
		String vendorId = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		logger.debug("vendor id : " + vendorId);
		if (null != vendorId) {
			try {
				DispatchLocationVO disLocationVO = gson.fromJson(getRequest().getReader(), DispatchLocationVO.class);
				disLocationVO.setVendorId(vendorId);

				jsonString = toJSON(d2cProviderPortalDelegate.addUpdateDispatchLocation(disLocationVO));
			} catch (IOException e) {
				logger.error("error while addUpdateDispatchLocation of D2CProviderPortalAction", e);
				jsonString = "Error";
			}
		}
		logger.info("exiting addUpdateDispatchLocation of D2CProviderPortalAction");
		return SUCCESS;
	}

	/**
	 * Ping action class to check
	 * 
	 * @return
	 */
	public String pingAJAX() {
		jsonString = "{\"reply\" : \"ping\"}";
		return SUCCESS;
	}

	/**
	 * This method is called to get primary Industry data
	 * 
	 * @return
	 */
	public String getResponseToRetrievePrimaryIndustry() {
		logger.info("Start of getResponseToRetrievePrimaryIndustry");

		String vendorId = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		logger.debug("vendor id : " + vendorId);

		if (null != vendorId) {
			jsonString = toJSON(d2cProviderPortalDelegate.getPrimaryIndustryDeleImpl(Integer.parseInt(vendorId)));
		}

		logger.info("End of getResponseToRetrievePrimaryIndustry");
		return SUCCESS;
	}

	/**
	 * get sku details
	 * 
	 * @return
	 */
	public String getSkuDetails() {
		logger.info("entering getSkuDetailsForVendorAndPrimaryIndustries of D2CProviderPortalAction");
		String vendorIdStr = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		logger.debug("vendor id: " + vendorIdStr);
		String json = null;
		try {
			json = IOUtils.toString(getRequest().getReader());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		@SuppressWarnings("deprecation")
		JSONObject obj = JSONObject.fromString(json);
		String primaryIndustryId = obj.get("primaryIndustryId").toString();
		List<Integer> primaryIndustries = new ArrayList<Integer>();
		primaryIndustries.add(Integer.parseInt(primaryIndustryId));
		jsonString = toJSON(d2cProviderPortalDelegate.getSkuDetailsForVendorAndPrimaryIndustries(Integer.parseInt(vendorIdStr),
				primaryIndustries));

		logger.info("Sku data from the delegate layer");
		return SUCCESS;
	}

	/**
	 * get service offering details
	 * 
	 * @return
	 */
	public String getServiceOfferedDetail() {
		logger.info("Start of getServiceOfferedDetail in D2CProviderPortalAction");

		String vendorId = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		logger.debug("vendor id : " + vendorId);
		if (null != vendorId) {
			List<Integer> primaryIndustries = new ArrayList<Integer>();
			try {
				PrimaryIndustryDTO primaryIndustryDTO = gson.fromJson(getRequest().getReader(), PrimaryIndustryDTO.class);
				if (null != primaryIndustryDTO.getPrimaryIndustryIds()) {
					primaryIndustries.addAll(primaryIndustryDTO.getPrimaryIndustryIds());
				}
			} catch (IOException e) {
				jsonString = "Error";
				logger.error("Error occured while parsing request parameters in getServiceOfferedDetail D2CProviderPortalAction", e);
			}

			jsonString = toJSON(d2cProviderPortalDelegate.getServiceOfferedDetail(vendorId, primaryIndustries));
		} else {
			logger.warn("Vendor id is not present in session.");
			jsonString = "Error!";
		}

		logger.info("End of getServiceOfferedDetail in D2CProviderPortalAction");
		return SUCCESS;
	}

	/**
	 * SL-21464 To save dailyLimit and price of provider
	 **/
	public String updateDailyLimitAndPrice() {
		logger.info("entering updateDailyLimitAndPrice of D2CProviderPortalAction");
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute("SecurityContext");
		String vendorName = soContxt.getUsername();

		String vendorIdStri = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		Integer vendorIdStr = Integer.parseInt(vendorIdStri);
		logger.debug("vendor id: " + vendorIdStr);
		SkuDetailRequestDTO skuDetailRequestDTO = new SkuDetailRequestDTO();
		try {
			skuDetailRequestDTO = gson.fromJson(getRequest().getReader(), SkuDetailRequestDTO.class);
		} catch (IOException e) {
			e.printStackTrace();
			jsonString = "{\"error\":\"Error while updating SkuPriceAndDailyLimit\"}";
			return SUCCESS;
		}
		
		VendorServiceOfferingVO vendorServiceOffering = new VendorServiceOfferingVO();
		List<VendorServiceOfferPriceVo> vendorServiceOfferPriceVo = new ArrayList<VendorServiceOfferPriceVo>();

		if (null != skuDetailRequestDTO.getPrimaryIndustryId()) {
			vendorServiceOffering.setPrimaryIndustryId(skuDetailRequestDTO.getPrimaryIndustryId());
		}
		
		for (SkuDetail skuDetail : skuDetailRequestDTO.getSkuDetails()) {
			
			VendorServiceOfferPriceVo offerPriceVo = new VendorServiceOfferPriceVo();

			offerPriceVo.setSkuId(skuDetail.getSkuid());
			offerPriceVo.setSku(skuDetail.getSku());
			offerPriceVo.setVendorOfferingId(null != skuDetail.getOfferingId() ? Integer.parseInt(skuDetail.getOfferingId()) : null);

			for (SkuPrices skuPrices : skuDetail.getSkuPrices()) {
				if (null != skuPrices.getId()) {
					offerPriceVo.setId(Integer.parseInt(skuPrices.getId()));
				}
				if (null != skuPrices.getPrice()) {
					offerPriceVo.setPrice(Double.parseDouble(skuPrices.getPrice()));
				} else {
					offerPriceVo.setPrice(Double.parseDouble("0"));
				}
				if (null != skuPrices.getDailyLimit()) {
					offerPriceVo.setDailyLimit(Integer.parseInt(skuPrices.getDailyLimit()));
				} else {
					offerPriceVo.setDailyLimit(Integer.parseInt("1"));
				}
			}
			vendorServiceOfferPriceVo.add(offerPriceVo);
		}
		
		vendorServiceOffering.setVendorServiceOfferPriceVo(vendorServiceOfferPriceVo);

		try {
			jsonString = toJSON(d2cProviderPortalDelegate.updateDailyLimitAndPrice(vendorServiceOffering, vendorIdStr, vendorName));
		} catch (Exception e) {
			logger.error("Exception in updateDailyLimitAndPrice inside D2CProviderPortalAction", e);
			jsonString = "{\"error\":\"Error while updating SkuPriceAndDailyLimit\"}";
		}
		logger.info("Exiting updateDailyLimitAndPrice of D2CProviderPortalAction");
		return SUCCESS;
	}

	/**
	 * save zip radius
	 * 
	 * @return
	 * @throws IOException
	 */
	public String saveZipCoverageRadius() throws IOException {
		logger.info("entering saveZipCoverageRadius of D2CProviderPortalAction");
		String vendorIdStr = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		Gson gson = new Gson();
		String jsonStringReq = IOUtils.toString(getRequest().getReader());
		Coverage coverage = gson.fromJson(jsonStringReq, Coverage.class);
		coverage.setVendorId(vendorIdStr);
		if (null != coverage) {
			d2cProviderPortalDelegate.saveZipCoverageRadius(coverage);
		}
		logger.info("exiting saveZipCoverageRadius of D2CProviderPortalAction");
		return SUCCESS;

	}

	/**
	 * update coverage
	 * 
	 * @return
	 * @throws IOException
	 */
	public String updateCoverageZipCodes() throws IOException {
		logger.info("entering updateCoverageZipCodes of D2CProviderPortalAction");
		String vendorIdStr = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		Gson gson = new Gson();
		Coverage coverage = new Coverage();
		List<ZipCodesData> selectedZipCodesDataWithVendLocId = null;
		jsonString = IOUtils.toString(getRequest().getReader());
		ZipCodesWrapper zipCodesWrapper = gson.fromJson(jsonString, new TypeToken<ZipCodesWrapper>() {
		}.getType());
		coverage.setCoverageRadius(zipCodesWrapper.getCoverageRadius());
		coverage.setLocId(zipCodesWrapper.getVendorLocId());
		coverage.setVendorId(vendorIdStr);

		List<ZipCodesData> selectedZipCodesData = zipCodesWrapper.getZipCodesData();

		if (null != zipCodesWrapper.getVendorLocId() && null != zipCodesWrapper.getCoverageRadius() && null != selectedZipCodesData
				&& selectedZipCodesData.size() != 0) {

			selectedZipCodesDataWithVendLocId = addVendorLocId(selectedZipCodesData, zipCodesWrapper.getVendorLocId());

			d2cProviderPortalDelegate.updateCoverageZipCodes(selectedZipCodesDataWithVendLocId, coverage);

		}

		logger.info("exiting updateCoverageZipCodes of D2CProviderPortalAction");
		return SUCCESS;
	}

	public String findZipCodesInRadius() {
		logger.info("entering findZipCodesInRadius of D2CProviderPortalAction");
		String vendorIdStr = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		Coverage coverage = new Coverage();
		coverage.setVendorId(vendorIdStr);
		coverage.setZip(getRequest().getParameter("zip"));
		coverage.setLocId(getRequest().getParameter("locId"));
		coverage.setCoverageRadius(getRequest().getParameter("coverageRadius"));

		jsonString = d2cProviderPortalDelegate.findZipCodesInRadius(coverage);
		logger.info("exiting findZipCodesInRadius of D2CProviderPortalAction");
		return SUCCESS;
	}

	/**
	 * add vendor id
	 * 
	 * @param selectedZipCodesData
	 * @param vendorLocId
	 * @return
	 */
	public List<ZipCodesData> addVendorLocId(List<ZipCodesData> selectedZipCodesData, String vendorLocId) {
		List<ZipCodesData> selectedZipCodesDataWithVendLocId = new ArrayList<ZipCodesData>();

		for (ZipCodesData selectedZipcodeWithVendLocId : selectedZipCodesData) {
			selectedZipcodeWithVendLocId.setVendorLocId(vendorLocId);
			selectedZipcodeWithVendLocId.setZipCode(selectedZipcodeWithVendLocId.getCode());
			selectedZipcodeWithVendLocId.setDistance_miles(selectedZipcodeWithVendLocId.getDistance());
			selectedZipCodesDataWithVendLocId.add(selectedZipcodeWithVendLocId);

		}
		return selectedZipCodesDataWithVendLocId;

	}

	public String updateVendorData() throws IOException {
		logger.info("Inside D2CProviderPortalAction.updateVendorData");
		String vendorIdStri = (String) ActionContext.getContext().getSession().get(VENDOR_ID);

		String tempJsonString = IOUtils.toString(getRequest().getReader());
		D2CProviderUpdateVO d2CProviderUpdateVO = gson.fromJson(tempJsonString, D2CProviderUpdateVO.class);

		String data = d2CProviderUpdateVO.getValue();
		String flagVal = d2CProviderUpdateVO.getName();
		Integer vendorIdStr = Integer.parseInt(vendorIdStri);

		logger.debug("vendor id: " + vendorIdStr);
		logger.info("Flag value is:" + flagVal);
		logger.info("Data to Update:" + data);

		if (StringUtils.isNotBlank(flagVal) && StringUtils.isNotBlank(data)) {
			jsonString = toJSON(d2cProviderPortalDelegate.updateVendorData(data, vendorIdStr, flagVal));
			logger.info("Exiting D2CProviderPortalAction.updateVendorData");
		}

		return SUCCESS;
	}

	public String uploadFirmLogo() throws Exception {

		byte[] imageByte;
		boolean check = true;
		boolean processUpload = true;
		FirmUploadLogo firmUploadLogo = new FirmUploadLogo();
		try {
			firmUploadLogo = gson.fromJson(getRequest().getReader(), FirmUploadLogo.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String base64Image = firmUploadLogo.getFormData().get(0).toString();
			BASE64Decoder decoder = new BASE64Decoder();
			imageByte = decoder.decodeBuffer(base64Image.split(",")[1]);
			File outputfile = new File(firmUploadLogo.getFile().getName());
			OutputStream stream = new FileOutputStream(outputfile);
			stream.write(imageByte);
			stream.close();

			VendorDocumentVO vendorDoc = new VendorDocumentVO();
			DocumentVO documentVO = new DocumentVO();
			String vendorId = (String) ActionContext.getContext().getSession().get(VENDOR_ID);

			Long fileSize = outputfile.length();
			photoDocFileSize = fileSize.toString();

			FileUtils fileUtils = new FileUtils();
			check = fileUtils.checkAttachmentForImage(outputfile);
			if (check == false) {
				processUpload = false;
				jsonString = "{\"error\":\"Invalid file type. Please select valid file type.\"}";
				return SUCCESS;

			}else if(fileSize > 5000000 ||fileSize > 5242880 ){
				jsonString = "{\"error\":\"Too big a file. Resize the logo to less than 5MB file size and try again.\"}";
				return SUCCESS;
			}
			if (processUpload) {
				vendorDoc.setVendorId(Integer.parseInt(vendorId));
				vendorDoc.setPrimaryInd(Boolean.TRUE);
				vendorDoc.setCreatedDate(new Date());
				vendorDoc.setModifiedDate(new Date());
				documentVO.setDocument(outputfile);
				documentVO.setBlobBytes(imageByte);
				documentVO.setFileName(firmUploadLogo.getFile().getName());
				documentVO.setFormat(firmUploadLogo.getFile().getType());
				documentVO.setDocSize(outputfile.length());
				documentVO.setDocCategoryId(Constants.DocumentTypes.CATEGORY.LOGO);
				ProcessResponse pr = new ProcessResponse();
				if (((Boolean) getRequest().getSession().getAttribute(SOConstants.IS_LOGGED_IN)).booleanValue()) {
					// do upload only
					SecurityContext sctx = (SecurityContext) getRequest().getSession().getAttribute("SecurityContext");
					vendorDoc.setModifiedBy(sctx.getUsername());
					vendorDoc.setCreatedBy(sctx.getUsername());
					vendorDoc.setCategoryId(Constants.DocumentTypes.CATEGORY.LOGO);
					documentVO.setEntityId(Integer.parseInt(vendorId));
					documentVO.setVendorId(Integer.parseInt(vendorId));
					documentVO.setRoleId(sctx.getRoleId());
					vendorDoc.setDocDetails(documentVO);
					pr = providerProfilePagesDelegate.uploadFirmLogo(vendorDoc);
				}
				if (pr != null) {
					if (pr.getCode().equalsIgnoreCase(SO_DOC_SIZE_EXCEEDED_RC)) {
						ResourceBundle rb = ResourceBundle.getBundle("/resources/properties/servicelive_copy");
						logger.error(rb.getObject("error.msg." + pr.getCode()).toString());
						return SUCCESS;

					} else if (pr.getCode().equalsIgnoreCase(SO_DOC_INVALID_FORMAT)) {
						logger.error("Please upload a valid file type");
						jsonString = "{\"error\":\"Invalid file type. Please select valid file type.\"}";
						return SUCCESS;
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error occured while trying to save attachment", e);
			jsonString = "{\"error\":\"Invalid file type. Please select valid file type.\"}";
			return SUCCESS;

		}
		jsonString = "{\"success\":\"SUCCESS\"}";
		return SUCCESS;
	}

	public String getFirmLogo() {
		logger.info("entering getFrimLogo of D2CProviderPortalAction");
		String vendorId = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		jsonString = toJSON(d2cProviderPortalDelegate.getFirmLogo(Integer.parseInt(vendorId)));
		logger.info("exiting getFrimLogo of D2CProviderPortalAction");
		return SUCCESS;
	}
	
	public String deleteCoverageArea() throws IOException{
		logger.info("entering deleteCoverageArea of D2CProviderPortalAction");
		String vendorIdStr = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		Gson gson = new Gson();
		String response="";
		String jsonStringReq = IOUtils.toString(getRequest().getReader());
		Coverage coverage = gson.fromJson(jsonStringReq, Coverage.class);
		coverage.setVendorId(vendorIdStr);
		if (null != coverage) {
		response=d2cProviderPortalDelegate.deleteCoverageArea(coverage);

		}
		logger.info("exiting deleteCoverageArea of D2CProviderPortalAction");
		return SUCCESS;
	}
	
	public String getCoverageAreas() {
		logger.info("entering getCoverageAreas of D2CProviderPortalAction");
		String vendorIdStr = (String) ActionContext.getContext().getSession().get(VENDOR_ID);

		jsonString = toJSON (d2cProviderPortalDelegate.getCoverageAreas(vendorIdStr));
		logger.info("exiting getCoverageAreas of D2CProviderPortalAction");
		return SUCCESS;
	}

	public String getLookupServiceDays(){
		logger.info("start getLookupServiceDays of D2CProviderPortalAction");
		
		jsonString = toJSON (d2cProviderPortalDelegate.getLookupServiceDays());
		
		logger.info("end getLookupServiceDays of D2CProviderPortalAction");
		return SUCCESS;
	}

	public String getRateCardPriceDetails(){
		logger.info("start getLookupServiceDays of D2CProviderPortalAction");
		String vendorIdStr = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		String json = null;
		try {
			json = IOUtils.toString(getRequest().getReader());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		@SuppressWarnings("deprecation")
		JSONObject obj = JSONObject.fromString(json);
		String primaryIndustryId = obj.get("primaryIndustryId").toString();
		jsonString = toJSON (d2cProviderPortalDelegate.getRateCardPriceDetails(Integer.parseInt(primaryIndustryId), Integer.parseInt(vendorIdStr)));
		
		logger.info("end getLookupServiceDays of D2CProviderPortalAction");
		return SUCCESS;
	}
	
	// Generic Method to Save and delete offersOn data
	public String updateOrDeleteOffersOn(){
		logger.info("Inside D2CProviderPortalAction.updateOrDeleteOffersOn");
		VendorServiceOfferedOnVO vendorServiceOfferedOnVO = null;
		String vendorIdStr = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		try {
			vendorServiceOfferedOnVO = gson.fromJson(getRequest().getReader(), VendorServiceOfferedOnVO.class);
			vendorServiceOfferedOnVO.setVendorId(vendorIdStr);
		} catch (Exception e) {
			logger.error("Error in converting data from json ",e);
		}
		//LuOffersOnVO luOffersOnVO = new LuOffersOnVO();
		jsonString = toJSON(d2cProviderPortalDelegate.updateOrDeleteOffersOn(vendorServiceOfferedOnVO));
		logger.info("Exiting D2CProviderPortalAction.updateOrDeleteOffersOn");
		return SUCCESS;
	}
	
	/*saveRateCardPrice*/

	public String saveRateCardPrice(){
		String vendorIdStr = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		RateCardRequestVO rateCardRequestVO = new RateCardRequestVO();
		String json = null;
		try {
			json = IOUtils.toString(getRequest().getReader());
			rateCardRequestVO = gson.fromJson(json, RateCardRequestVO.class);
			jsonString = toJSON ( d2cProviderPortalDelegate.saveRateCardPrice(rateCardRequestVO, Integer.parseInt(vendorIdStr)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	

	/**
	 * METHOD TO VALIDATE DISPATCH LOCATION ADDRESS
	 */
	public String addressValidation() {
		logger.info("entering addressValidation() of D2CProviderPortalAction");
		String json = null;
		String vendorId = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		DispatchLocationVO dispatchLocationVO = new DispatchLocationVO();
		dispatchLocationVO.setVendorId(vendorId);
		try {
			json = IOUtils.toString(getRequest().getReader());
			dispatchLocationVO = gson.fromJson(json, DispatchLocationVO.class);
		} catch (Exception e) {
			logger.error("Error in converting data from json ",e);
		}
		jsonString = toJSON(d2cProviderPortalDelegate.addressValidation(dispatchLocationVO));
		logger.info("exiting addressValidation() of D2CProviderPortalAction");
		return SUCCESS;
	}
	/**
	 * This method will delete the provider firm logo.
	 * @return
	 */
	public String deleteFirmLogo(){
		logger.info("Inside D2CProviderPortalAction.deleteFirmLogo");
		String vendorID = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		Integer vendorId = Integer.parseInt(vendorID);
		try{
			jsonString = toJSON(d2cProviderPortalDelegate.deleteFirmLogo(vendorId));
		}catch(Exception e){
			logger.error("error in D2CProviderPortalAction.deleteFirmLogo", e);
		}
		logger.info("Exiting D2CProviderPortalAction.deleteFirmLogo");
		return SUCCESS;
	}
	
	/**
	 * Method to convert object into JSON string
	 * 
	 * @param obj
	 * @return
	 */
	private String toJSON(Object obj) {
		return jsonSerializer.deepSerialize(obj);
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public IProviderProfilePagesDelegate getProviderProfilePagesDelegate() {
		return providerProfilePagesDelegate;
	}

	public void setProviderProfilePagesDelegate(IProviderProfilePagesDelegate providerProfilePagesDelegate) {
		this.providerProfilePagesDelegate = providerProfilePagesDelegate;
	}

}