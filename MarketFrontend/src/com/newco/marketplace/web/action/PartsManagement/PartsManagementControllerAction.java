package com.newco.marketplace.web.action.PartsManagement;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletOutputStream;



import com.newco.marketplace.api.mobile.beans.sodetails.Document;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.ProviderInvoicePartsVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.util.TimestampUtils;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.vo.mobile.InvoiceDetailsVO;
import com.newco.marketplace.vo.mobile.InvoicePartsVO;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.delegates.ISOWizardPersistDelegate;
import com.newco.marketplace.web.dto.PartsManagementTabDTO;
import com.newco.marketplace.web.dto.SODocumentDTO;
import com.newco.marketplace.web.dto.SOWError;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.utils.SecurityChecker;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.partsManagement.services.IPartsManagementService;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;
import flexjson.JSONSerializer;

/**
 * 
 * $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/01/15
 * This class is created for order management Parts Management
 * 
 */
public class PartsManagementControllerAction extends SLDetailsBaseAction implements
Preparable, ModelDriven<PartsManagementTabDTO> {

	private static final Logger LOGGER = Logger
			.getLogger(PartsManagementControllerAction.class);
	private static final long serialVersionUID = 1L;

	private PartsManagementTabDTO partsManagementTabDTO = new PartsManagementTabDTO();
	private IPartsManagementService partsManagementService;
	protected ISODetailsDelegate detailsDelegate;
	private static final String JSON = "json";
	private ISOWizardPersistDelegate isoWizardPersistDelegate;
	private ISOWizardFetchDelegate fetchDelegate;
	private List<Integer> documentIds = new ArrayList<Integer>();
	private static final String PNG = ".png";
	private static final String JPG = ".jpg";
	private static final String GIF = ".gif";
	private static final String DOC = ".doc";
	private static final String DOCX = ".docx";
	private static final String PDF = ".pdf";
	//private static final String TXT = ".txt";
	//private static final String XLS = ".xls";
	//private static final String XLSX = ".xlsx";
	//private static final String ZIP = ".zip";
	private static final String JPEG = ".jpeg";
	private static final String PJPEG = ".pjpeg";
	private static final String TIFF = ".tiff";
	//private static final String BMP = ".bmp";
	//private static final String XML = ".xml";
	public static final String DATE_FORMAT = "MMM d, yyyy hh:mm a zzz";
	private static final String TIME_STAMP_FORMAT_IN_DB_TWENTY_FOUR = "yyyy-MM-dd HH:mm:ss";
	
	//SL-21811
	private static final Double RETAIL_MAX_PRICE = Double.parseDouble(PropertiesUtils.getPropertyValue("RETAIL_MAX_PRICE"));

	public List<Integer> getDocumentIds() {
		return documentIds;
	}

	public void setDocumentIds(List<Integer> documentIds) {
		this.documentIds = documentIds;
	}


	private List<SOWError> pmErrors = new ArrayList<SOWError>();



	public List<SOWError> getPmErrors() {
		return pmErrors;
	}

	public PartsManagementTabDTO getModel() {
		return partsManagementTabDTO;
	}

	public void setModel(PartsManagementTabDTO partsManagementTabDTO) {
		this.partsManagementTabDTO = partsManagementTabDTO;
	}

	public IPartsManagementService getPartsManagementService() {
		return partsManagementService;
	}


	public void setPartsManagementService(
			IPartsManagementService partsManagementService) {
		this.partsManagementService = partsManagementService;
	}
	/**
	 * Description of the Method
	 * 
	 * @exception Exception
	 *                Description of the Exception
	 */
	public void prepare() throws Exception {
		this.clearFieldErrors();
		this.clearActionErrors();
		createCommonServiceOrderCriteria();
		//set the serviceOrderDTO in request
		ServiceOrderDTO soDTO = null;
		String soId = null;
		soId =(String)getRequest().getParameter("soId");
		if(StringUtils.isNotBlank(soId)){
			soDTO = getDetailsDelegate().getServiceOrder(soId, get_commonCriteria().getRoleId(), null);
			getModel().setSoId(soId);
		}
		setAttribute(SO_ID, soId);
		setAttribute(THE_SERVICE_ORDER, soDTO);
		partsManagementTabDTO.setResponseMessage(null);
		getSession().removeAttribute("documentUploadError");
	}

	/**
	 * action to save part details
	 * @return
	 */
	public String savePartDetails(){

		PartsManagementTabDTO partsManagementTabDTO= getModel();

		InvoicePartsVO partVO = null;
		Double retailPriceSum = null;
		Double totalRetailPrice = null;
		Double currentRetailPrice = null;
		Double retailPrice=null;
		Double commercialPrice=null;
		Boolean autoAdjudicationInd = null;
		Double adjudicationCommPricePercentage=null;
		//ServiceOrderDTO soDTO=null;
		String invoicePricingModel=null;

		if(null!=partsManagementTabDTO){
			partVO= partsManagementTabDTO.getEditPartVO();		
			String manualSoId = (String)getRequest().getParameter("soId");
			partVO.setSoId(manualSoId);
			setAttribute(SO_ID, partVO.getSoId());
		}
		if(null!=partVO){
			autoAdjudicationInd=partsManagementService.getAutoAdjudicationInd();
			//R12_1:
			//If a new part is created for a service order and if the parts_invoice_pricing model column has no value
			//and if the adjudication is on , then  update the so_work_flowcontrols  parts_invoice_pricing_type column  as 'cost plus model'.
			invoicePricingModel=partsManagementService.getInvoicePartsPricingModel(partVO.getSoId());

			if(null==invoicePricingModel){
				if(true==autoAdjudicationInd){
					partsManagementService.saveInvoicePartPricingModel(partVO.getSoId(),MPConstants.INVOICE_PARTS_COSTPLUS_PRICING_MODEL);
					invoicePricingModel=MPConstants.INVOICE_PARTS_COSTPLUS_PRICING_MODEL;
				}
				else{
					partsManagementService.saveInvoicePartPricingModel(partVO.getSoId(),MPConstants.INVOICE_PARTS_REIMBURSEMENT_PRICING_MODEL);
					invoicePricingModel=MPConstants.INVOICE_PARTS_REIMBURSEMENT_PRICING_MODEL;

				}
			}
			partVO.setPartNoSource(MPConstants.PART_SOURCE_MANUAL);
			partVO.setPartStatus(MPConstants.ADDED);
			//R12_1: adding entry to column commercial price
			if(MPConstants.INVOICE_PARTS_COSTPLUS_PRICING_MODEL.equals(invoicePricingModel)){
				if(null != partVO.getRetailPrice()){
					retailPrice=Double.parseDouble(partVO.getRetailPrice());
					//Fetching the percentage value from DB
					try{
						adjudicationCommPricePercentage=Double.parseDouble((partsManagementService.getConstantValueFromDB(MPConstants.ADJUDICATION_PRICE_PERC_KEY)));
						commercialPrice=MoneyUtil.getRoundedMoney(retailPrice - (retailPrice*(adjudicationCommPricePercentage/100)));
						partVO.setCommercialPrice(commercialPrice.toString());	
					}
					catch (Exception e) {
						// TODO: handle exception
						return ERROR;
					}
				}	
			}
			else{
				if(null != partVO.getRetailPrice()){
					partVO.setCommercialPrice(partVO.getRetailPrice());	
				}
			}
			retailPriceSum = partsManagementService.getRetailPriceTotal(partVO.getSoId(),null); 
			if(retailPriceSum == null){
				retailPriceSum =0.0;
			}
			currentRetailPrice = (Double.parseDouble(partVO.getRetailPrice())) * partVO.getQty();

			totalRetailPrice = retailPriceSum + currentRetailPrice;
			//SL-21811
			if(totalRetailPrice > RETAIL_MAX_PRICE){
				setAttribute(RETAIL_PRICE_ERROR, RETAIL_PRICE_MAX_VALIDATION.replace("$", "$"+String.valueOf(RETAIL_MAX_PRICE)));
			}
			else{
				partsManagementService.savePartsDetails(partVO);

			}				
		}
		else{
			return ERROR;
		}
		return SUCCESS;

	}

	/**
	 * @return
	 * action for save on edit part
	 */
	public String editPart(){

		partsManagementTabDTO = getModel();
		if(null!= getSession().getAttribute("partsManagementTabDTO")){
			PartsManagementTabDTO partsManagementTabDTOSession = (PartsManagementTabDTO)getSession().getAttribute("partsManagementTabDTO");
			partsManagementTabDTO.setDocumentIds(partsManagementTabDTOSession.getDocumentIds());
		}
		InvoicePartsVO editPartVO = null;

		Double retailPriceSum = null;
		Double totalRetailPrice = null;
		Double currentRetailPrice = null;

		String soId =null;
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		String userName = soContxt.getUsername();
		if(null!= partsManagementTabDTO){
			editPartVO = partsManagementTabDTO.getEditPartVO();
			editPartVO.setDocumentIdList(partsManagementTabDTO.getDocumentIds());
			soId = partsManagementTabDTO.getSoId();
		}
		if(null!=editPartVO && StringUtils.isNotEmpty(soId)){ 
			//If validations passes, update the parts status		
			retailPriceSum = partsManagementService.getRetailPriceTotal(soId,editPartVO.getPartInvoiceId()); 
			if(retailPriceSum == null){
				retailPriceSum =0.0;
			}
			currentRetailPrice = (Double.parseDouble(editPartVO.getRetailPrice())) * editPartVO.getQty();

			totalRetailPrice = retailPriceSum + currentRetailPrice;
			//SL-21811
			if(totalRetailPrice > RETAIL_MAX_PRICE){
				partsManagementTabDTO.setResponseMessage(RETAIL_PRICE_MAX_VALIDATION.replace("$", "$"+String.valueOf(RETAIL_MAX_PRICE)));
				String returnvalue = getJSON(partsManagementTabDTO);
				getRequest().setAttribute("returnvalue", returnvalue);
				return SUCCESS;
			}
			else{
				partsManagementService.editPartDetails(editPartVO,soId,userName);
				if(null != partsManagementTabDTO.getDocumentIds()){
					partsManagementTabDTO.getDocumentIds().clear();
					documentIds.clear();
				}
			}
		}
		else{
			return ERROR;
		}
		String returnvalue = getJSON(partsManagementTabDTO);
		getRequest().setAttribute("returnvalue", returnvalue);
		return SUCCESS;

	}


	/**
	 * @return
	 * 
	 * action for load of edit part
	 */
	public String loadEditPart(){

		getSession().removeAttribute("partsManagementTabDTO");
		partsManagementTabDTO = getModel();
		InvoicePartsVO editPartVO = null;
		String invoiceId =null;
		Integer invoicePartId =null;
		InvoicePartsVO invoicePartsVO =null;
		invoiceId = (String)getRequest().getParameter("invoiceId");
		String result = null;
		String partSummaryMode =null;
		partSummaryMode = getParameter("partSummaryMode");
		if(null!=invoiceId){
			invoicePartId = Integer.parseInt(invoiceId);
			invoicePartsVO = partsManagementService.loadPartDetails(invoicePartId);
		}
		if(null!=invoicePartsVO){
			if(null != partsManagementTabDTO){
				if(null != partsManagementTabDTO.getDocumentIds()){
					partsManagementTabDTO.getDocumentIds().clear();
					documentIds.clear();
				}else{
					partsManagementTabDTO.setDocumentIds(new ArrayList<Integer>());
					documentIds = new ArrayList<Integer>();
				}
			}
			invoicePartsVO.getPartStatusTypes().remove(0);
			invoicePartsVO = formatUploadedDateTimeForDocument(invoicePartsVO);
			partsManagementTabDTO.setEditPartVO(invoicePartsVO);
			setModel(partsManagementTabDTO);
			getRequest().setAttribute("editPartDetails", invoicePartsVO);
			getSession().setAttribute("partsManagementTabDTO", partsManagementTabDTO);

			// This will decide mode of view of part details(viewable/editable)
			if(StringUtils.isNotBlank(partSummaryMode)){
				if(partSummaryMode.equals("view")){
					result ="view";
				}else if(partSummaryMode.equals("edit")){
					result= "edit";
				}else{
					return ERROR;
				}
			}
			return result;
		}
		return ERROR;

	}

	/**
	 * @param invoicePartsVO
	 * @return
	 * 
	 * to format the uploaded date
	 * 
	 */
	private InvoicePartsVO  formatUploadedDateTimeForDocument(InvoicePartsVO invoicePartsVO) {
		// TODO Auto-generated method stub
		if(null!=invoicePartsVO.getInvoiceDocumentList() && !invoicePartsVO.getInvoiceDocumentList().isEmpty()){
			for(Document document : invoicePartsVO.getInvoiceDocumentList()){
				if(null != document && null!=document.getUploadDateTime()){
					Timestamp modifiedTimeStamp = TimestampUtils.
							getTimestampFromString(document.getUploadDateTime(), 
									TIME_STAMP_FORMAT_IN_DB_TWENTY_FOUR);
					DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
					String modifiedDate = dateFormat.format(modifiedTimeStamp);
					document.setUploadDateTime(modifiedDate);
				}
			}
		}
		return invoicePartsVO;
	}

	/**
	 * @return
	 * action for load on Add Invoice Button
	 */
	public String loadInvoiceOnAddInvoice() {

		getSession().removeAttribute("partsManagementTabDTO");
		partsManagementTabDTO = getModel();
		InvoicePartsVO editPartVO = null;
		String checkedInvoiceIds = null;
		List<Integer> invoicePartIds = new ArrayList<Integer>();
		InvoiceDetailsVO invoiceDetailsVO = null;
		String[] invoiceIds = null;
		String[] docOptions = null;
		HashMap<String, Object> invoiceDetails = new HashMap<String, Object>();

		if (null != getParameter(CHECKED_INVOICE_IDS)) {
			checkedInvoiceIds = getParameter(CHECKED_INVOICE_IDS);
		} else {
			checkedInvoiceIds = (String) getSession().getAttribute(
					CHECKED_INVOICE_IDS);
		}
		invoiceIds = StringUtils.split(checkedInvoiceIds, COMMA_DELIMITER);
		for (String invoiceId : invoiceIds) {
			if (StringUtils.isNotEmpty(invoiceId)) {
				invoicePartIds.add((Integer.parseInt(invoiceId)));
			}
		}
		if(!invoicePartIds.isEmpty()){
			invoiceDetailsVO = partsManagementService
					.loadInvoiceDetails(invoicePartIds);
		}
		invoiceDetailsVO = mapInvoiceDetailsVO(invoiceDetailsVO);
		if (null != invoiceDetailsVO && null!=invoiceDetailsVO.getInvoicePartsVOs() && !invoiceDetailsVO.getInvoicePartsVOs().isEmpty()) {
			getRequest().setAttribute("invoicePartDetails", invoiceDetailsVO);
			partsManagementTabDTO.setInvoiceDetailsVO(invoiceDetailsVO);
			getSession().setAttribute("partsManagementTabDTO", partsManagementTabDTO);
			return SUCCESS;
		}
		getSession().setAttribute("partsManagementTabDTO", partsManagementTabDTO);
		return ERROR;

	}


	/**
	 * @param invoiceDetailsVO
	 * @return
	 * 
	 * to map common invoice details
	 * 
	 */
	private InvoiceDetailsVO mapInvoiceDetailsVO(
			InvoiceDetailsVO invoiceDetailsVO) {

		if (null != invoiceDetailsVO && null!=invoiceDetailsVO.getInvoicePartsVOs() && !invoiceDetailsVO.getInvoicePartsVOs().isEmpty()) {
			List<InvoicePartsVO> invoicePartsVOs = invoiceDetailsVO.getInvoicePartsVOs();
			InvoicePartsVO partsVO = invoicePartsVOs.get(0);
			String source = partsVO.getPartSource();
			String nonSearsSource = partsVO.getNonSearsSource();
			String coverage = partsVO.getPartCoverage();
			String invoiceNumber = partsVO.getInvoiceNumber();
			invoiceDetailsVO.setNonSearsSource(nonSearsSource);
			invoiceDetailsVO.setPartSource(source);
			invoiceDetailsVO.setPartCoverage(coverage);
			invoiceDetailsVO.setInvoiceNumber(invoiceNumber);
			invoiceDetailsVO.getPartStatusTypes().remove(0);
			List<Document> documents = new ArrayList<Document>();
			for(InvoicePartsVO invoicePartsVO : invoicePartsVOs){
				if(null != invoicePartsVO){
					invoicePartsVO = formatUploadedDateTimeForDocument(invoicePartsVO);
					if(invoicePartsVO.getInvoiceDocumentList()!=null && !invoicePartsVO.getInvoiceDocumentList().isEmpty()){
						documents.addAll(invoicePartsVO.getInvoiceDocumentList());
						invoicePartsVO.setDocumentCount(invoicePartsVO.getInvoiceDocumentList().size());
					}else{
						invoicePartsVO.setDocumentCount(0);
					}
				}
			}
			if(null != documents && !documents.isEmpty()){
				removeDuplicatesDocumets(documents);
			}
			invoiceDetailsVO.setInvoiceDocuments(documents);
			invoiceDetailsVO.setInvoicePartsVOs(invoicePartsVOs);
			partsManagementTabDTO.setInvoiceDetailsVO(invoiceDetailsVO);
		}
		return invoiceDetailsVO;
	}

	private void removeDuplicatesDocumets(List<Document> documents) {
		List<Document> tempDocuments = new ArrayList<Document>(documents);
		for(Document document : tempDocuments){
			int count = 0;
			for(Document document1 : tempDocuments){
				if(document.getDocumentId().equals(document1.getDocumentId())){
					count++;
					if(count > 1){
						documents.remove(document1);
					}
				}
			}
		}
	}

	/**
	 * @return
	 * load invoice on click of invoice number
	 * 
	 */
	public String loadInvoiceOnInvoiceNumber() {

		getSession().removeAttribute("partsManagementTabDTO");
		partsManagementTabDTO = getModel();
		InvoicePartsVO editPartVO = null;
		String checkedInvoiceIds = null;
		InvoiceDetailsVO invoiceDetailsVO = null;
		String invoiceNum = (String)getRequest().getParameter("invoiceNum");
		String soId = (String)getRequest().getParameter("soId");
		String result = null;
		String partSummaryMode =null;
		partSummaryMode = getParameter("partSummaryMode");



		if (StringUtils.isNotEmpty(invoiceNum) && StringUtils.isNotEmpty(soId)) {
			invoiceDetailsVO = partsManagementService
					.loadInvoiceDetailsForInvoiceNum(invoiceNum,soId);
		}

		List<SOWError> pmErrorMsgs = checkForInvoiceDuplicates(invoiceDetailsVO,soId);
		invoiceDetailsVO = mapInvoiceDetailsVO(invoiceDetailsVO);
		if (null != invoiceDetailsVO && null!=invoiceDetailsVO.getInvoicePartsVOs() && !invoiceDetailsVO.getInvoicePartsVOs().isEmpty() && StringUtils.isNotEmpty(soId)) {
			if(null!=pmErrorMsgs && !pmErrorMsgs.isEmpty()){
				partsManagementTabDTO.setPmErrors(pmErrorMsgs);
				getRequest().setAttribute("sourceErrorExists", true);
			}
			getRequest().setAttribute("invoicePartDetails", invoiceDetailsVO);
			partsManagementTabDTO.setInvoiceDetailsVO(invoiceDetailsVO);
			getSession().setAttribute("partsManagementTabDTO", partsManagementTabDTO);
		}
		if(StringUtils.isNotBlank(partSummaryMode)){
			if(partSummaryMode.equals("view")){
				result ="view";
			}else if(partSummaryMode.equals("edit")){
				result= "edit";
			}else{
				result = ERROR;
			}
		}
		return result;

	}

	/**
	 * @param invoiceDetailsVO
	 * @return
	 * 
	 * to check for dupliacte invoice records
	 * 
	 */
	private List<SOWError> checkForInvoiceDuplicates(
			InvoiceDetailsVO invoiceDetailsVO,String soId) {
		List<SOWError> pmErrorMsgs = new ArrayList<SOWError>();
		if (null != invoiceDetailsVO && null!=invoiceDetailsVO.getInvoicePartsVOs() && !invoiceDetailsVO.getInvoicePartsVOs().isEmpty() && StringUtils.isNotEmpty(soId)) {

			List<InvoicePartsVO> invoicePartsVOs = invoiceDetailsVO.getInvoicePartsVOs();
			InvoicePartsVO partsVO = invoicePartsVOs.get(0);
			String source = partsVO.getPartSource();
			String nonSearsSource = partsVO.getNonSearsSource();
			String coverage = partsVO.getPartCoverage();
			String invoiceNumber = partsVO.getInvoiceNumber();
			boolean sourceMismatchInd =false;
			boolean coverageMismatchInd =false;
			for(InvoicePartsVO invoicePartsVO : invoicePartsVOs){
				if(null != invoicePartsVO){
					if(null!=source && !source.equals(invoicePartsVO.getPartSource())){
						sourceMismatchInd =true;
					}
					else if(null!=source && MPConstants.NON_SEARS.equals(invoicePartsVO.getPartSource()) && null!=nonSearsSource && !nonSearsSource.equals(invoicePartsVO.getNonSearsSource())){
						sourceMismatchInd = true;
					}
					if(null!=coverage && !coverage.equals(invoicePartsVO.getPartCoverage())){
						coverageMismatchInd = true;
					}

					if(sourceMismatchInd || coverageMismatchInd){
						break;
					}
				}
			}
			if(sourceMismatchInd){
				SOWError error = new SOWError(MPConstants.SOURCE, MPConstants.MISMATCH_ERROR, EMPTY_STR);
				pmErrorMsgs.add(error);
			}
			if(coverageMismatchInd){
				SOWError error = new SOWError(MPConstants.COVERAGE, MPConstants.MISMATCH_ERROR, EMPTY_STR);
				pmErrorMsgs.add(error);

			}
			invoiceDetailsVO.setPartSource(source);
			invoiceDetailsVO.setPartCoverage(coverage);
			invoiceDetailsVO.setInvoiceNumber(invoiceNumber);				
		}
		return pmErrorMsgs;
	}

	/**
	 * @return
	 * 
	 * save invoice details
	 * 
	 */
	public String saveInvoiceDetails(){

		List<Integer> docIds = new ArrayList<Integer>();
		ServiceOrderDTO soDTO = null;
		partsManagementTabDTO = getModel();
		//R12_1: Adjudication Phase II
		soDTO=(ServiceOrderDTO)getAttribute(THE_SERVICE_ORDER);
		PartsManagementTabDTO partsManagementTabDTOSession = null;
		if(null!= getSession().getAttribute("partsManagementTabDTO")){
			partsManagementTabDTOSession = (PartsManagementTabDTO)getSession().getAttribute("partsManagementTabDTO");
			partsManagementTabDTO.setDocumentIds(partsManagementTabDTOSession.getDocumentIds());
			if(null!=partsManagementTabDTOSession.getInvoiceDetailsVO()){
				InvoiceDetailsVO detailsVO = partsManagementTabDTOSession.getInvoiceDetailsVO();
				if(null != detailsVO.getInvoiceDocuments() && !(detailsVO.getInvoiceDocuments().isEmpty())){
					for( Document document:detailsVO.getInvoiceDocuments()){
						if(null !=document && null!= document.getDocumentId()){
							docIds.add(document.getDocumentId());
						}
					}
				}
			}
		}
		InvoiceDetailsVO invoiceDetailsVO = null;
		String soId =null;
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		String userName = soContxt.getUsername();
		Map<Integer , List<Integer>> dBInvoiceDocMap =new HashMap<Integer , List<Integer>>();
		Map<Integer , List<Integer>> newDocumentMap = new HashMap<Integer , List<Integer>>();	
		if(null!= partsManagementTabDTO){
			invoiceDetailsVO = partsManagementTabDTO.getInvoiceDetailsVO();
			if(null != partsManagementTabDTO.getDocumentIds()){
				invoiceDetailsVO.setInvoiceDocumentIds(partsManagementTabDTO.getDocumentIds());
			}
			soId = partsManagementTabDTO.getSoId();
		}
		if(null!=invoiceDetailsVO && invoiceDetailsVO.getInvoicePartsVOs()!=null && !invoiceDetailsVO.getInvoicePartsVOs().isEmpty() && StringUtils.isNotEmpty(soId)){ 
			// validating the count of documents associated to the invoice
			dBInvoiceDocMap = partsManagementService.getDBDocumentList(invoiceDetailsVO,soId,userName);
			newDocumentMap = checkForNewDocumentList(docIds,dBInvoiceDocMap);

			if(null != dBInvoiceDocMap || null != newDocumentMap){
				boolean errorInCountOfInvoiceDoc = validateCountOfInvoiceDocuments(dBInvoiceDocMap, newDocumentMap, docIds);
				if(errorInCountOfInvoiceDoc)
				{
					partsManagementService.saveInvoiceDetails(invoiceDetailsVO,soId,userName,newDocumentMap);
				}
				else{
					partsManagementTabDTO.setResponseMessage("Documents associated for this invoice exceedes the limit(5).");
					String returnvalue = getJSON(partsManagementTabDTO);
					getRequest().setAttribute("returnvalue", returnvalue);
					return SUCCESS;
				}
			}
			if(null != partsManagementTabDTO.getDocumentIds()){
				partsManagementTabDTO.getDocumentIds().clear();
				documentIds.clear();
			}					
		}
		else{
			return ERROR;
		}
		String returnvalue = getJSON(partsManagementTabDTO);
		getRequest().setAttribute("returnvalue", returnvalue);

		return SUCCESS;


	}

	/**
	 * @return
	 * 
	 * action for deletePart
	 */
	public String deletePart(){
		String partInvoiceIdString =null;
		Integer partInvoiceId =null;
		String soId = null;
		partInvoiceIdString = (String)getRequest().getParameter("partInvoiceId");
		soId = (String)getRequest().getParameter("soId");

		//commenting this as now this scenario of un-associated part doc will not occur
		/*		if(null!= getSession().getAttribute("partsManagementTabDTO")){
			PartsManagementTabDTO partsManagementTabDTOSession = (PartsManagementTabDTO)getSession().getAttribute("partsManagementTabDTO");
			if(null != partsManagementTabDTOSession){
				List<Integer>documentIdList = partsManagementTabDTOSession.getDocumentIds();
				if(null!=documentIdList && !documentIdList.isEmpty()){
					partsManagementService.documentHardDelete(documentIdList, soId);
				}
			}
		}*/
		if(null!=partInvoiceIdString && null!=soId){
			partInvoiceId = Integer.parseInt(partInvoiceIdString);
			partsManagementService.deletePartDetails(partInvoiceId,soId);
			return SUCCESS;
		}

		return ERROR;

	}
	/**
	 * @return
	 * load part details in summary page
	 * 
	 */
	public String loadPartDetails(){

		String soId = null;
		soId = (String)getRequest().getParameter("soId");
		if(null!=soId && StringUtils.isNotEmpty(soId)){			
			ServiceOrderDTO soDTO = getCurrentServiceOrderFromRequest();
			List<ProviderInvoicePartsVO> invoicePartList = new ArrayList<ProviderInvoicePartsVO>();
			invoicePartList = soDTO.getInvoiceParts();
			if(null!= invoicePartList && !(invoicePartList.isEmpty())){
				setAttribute("partExistInd","partExist");
			}
			else{
				setAttribute("partExistInd","partNotExist");			
			}
			String partExistInd=partsManagementService.getInvoicePartInd(soId);
			if(null!=partExistInd && "NO_PARTS_REQUIRED".equals(partExistInd)){
				setAttribute("partExistInd",partExistInd);	
			}
			/*This method will do the following 
			 * 1) Set Count of parts
			 * 2) Set count of unique invoice based on invoice no
			 * 3) Sort parts not having invoice based on partName
			 * 4) Sort parts having invoice based on invoice no,secondary sort is based on part name.
			 * 5) Calculate total sum of est provider payment
			 * 6) Set Est Provider Payment to NA if part status is not installed.
			 * */

			soDTO = modifySummaryDTOForInvoiceParts(soDTO);
			setAttribute("summaryDTO", soDTO);
			return SUCCESS;
		}
		return ERROR;

	}

	public ISODetailsDelegate getDetailsDelegate() {
		return detailsDelegate;
	}

	public void setDetailsDelegate(ISODetailsDelegate detailsDelegate) {
		this.detailsDelegate = detailsDelegate;
	}

	public String documentUpload(){
		PartsManagementTabDTO partsManagementTabDTOForm = getModel();
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		String fileType= null;
		String fileName = getRequest().getParameter("fileName");
		String partInvoiceId = getRequest().getParameter("partInvoiceId");

		try{
			if(null!= getSession().getAttribute("partsManagementTabDTO")){
				partsManagementTabDTO = (PartsManagementTabDTO)getSession().getAttribute("partsManagementTabDTO");
			}else{
				LOGGER.error("PartsManagementTabDTO Model returned null");
				return ERROR;
			}

			if(null != partsManagementTabDTO.getEditPartVO() && null != partsManagementTabDTO.getEditPartVO().getInvoiceDocumentList() && !partsManagementTabDTO.getEditPartVO().getInvoiceDocumentList().isEmpty()){
				if(partsManagementTabDTO.getEditPartVO().getInvoiceDocumentList().size() >= 5){
					partsManagementTabDTO.setResponseMessage("No. of documents uploaded exceeded the limit(5) for this part.");
					String returnvalue = getJSON(partsManagementTabDTO);
					getRequest().setAttribute("returnvalue", returnvalue);
					setModel(partsManagementTabDTO);

					getSession().setAttribute("partsManagementTabDTO", partsManagementTabDTO);
					return SUCCESS;
				}
			}

			fileName = URLDecoder.decode( fileName , "UTF-8");
			fileName = fileName.replaceAll("-prcntg-", "%");

			String fileExtenstion = "";
			Integer fileExtensionIndex=fileName.lastIndexOf(".");
			fileExtenstion = fileName.substring(fileExtensionIndex,fileName.length());
			if(null!=fileName && !(OrderConstants.EMPTY_STR.equals(fileName.trim()))){
				if(fileExtensionIndex == -1){
					partsManagementTabDTO.setResponseMessage("Invalid File format. Please use a valid format ");
					String returnvalue = getJSON(partsManagementTabDTO);
					getRequest().setAttribute("returnvalue", returnvalue);
					setModel(partsManagementTabDTO);

					getSession().setAttribute("partsManagementTabDTO", partsManagementTabDTO);
					return SUCCESS;
				}
				else{
					if(!(fileExtenstion.equalsIgnoreCase(PNG) || fileExtenstion.equalsIgnoreCase(JPG)||
							fileExtenstion.equalsIgnoreCase(GIF)|| fileExtenstion.equalsIgnoreCase(DOC)||
							fileExtenstion.equalsIgnoreCase(DOCX)|| fileExtenstion.equalsIgnoreCase(PDF)||  
							fileExtenstion.equalsIgnoreCase(TIFF) || fileExtenstion.equalsIgnoreCase(JPEG) ||  
							fileExtenstion.equalsIgnoreCase(PJPEG))){
						partsManagementTabDTO.setResponseMessage("Invalid File format. Please use a valid format ");
						String returnvalue = getJSON(partsManagementTabDTO);
						getRequest().setAttribute("returnvalue", returnvalue);
						setModel(partsManagementTabDTO);

						getSession().setAttribute("partsManagementTabDTO", partsManagementTabDTO);
						return SUCCESS;
					}
				}
			}

			if(null!=partsManagementTabDTOForm){
				partsManagementTabDTO.setFileSelect(partsManagementTabDTOForm.getFileSelect());
				soId = getRequest().getParameter("soId"); 
			}
			File file = null;
			file = (File)partsManagementTabDTO.getFileSelect();   
			partsManagementTabDTO.setResponseMessage("");
			List<DocumentVO> documentVOList = new ArrayList<DocumentVO>();
			if(null != file){
				DocumentVO documentVO = new DocumentVO();
				Long docSize = file.length();
				Integer size = docSize.intValue()/OrderConstants.SIZE_KB;

				if(size>FIVE_KB){
					partsManagementTabDTO.setResponseMessage("Please attach a file no larger than 5MB.");
					String returnvalue = getJSON(partsManagementTabDTO);
					getRequest().setAttribute("returnvalue", returnvalue);
					setModel(partsManagementTabDTO);

					getSession().setAttribute("partsManagementTabDTO", partsManagementTabDTO);
					return SUCCESS;
				}
				FileNameMap fileNameMap = URLConnection.getFileNameMap();
				fileType = fileNameMap.getContentTypeFor(fileName);

				documentVO.setDocument(file);
				documentVO.setDocSize(file.length());
				documentVO.setSoId(soId);
				documentVO.setFileName(fileName);
				documentVO.setDescription("");
				documentVO.setTitle("Parts Invoice");
				documentVO.setFormat(fileType);
				documentVO.setDocSize(file.length());
				String createdBy="";
				if (null != soContxt) {
					if (soContxt.isAdopted()) {

						createdBy = soContxt.getSlAdminFName() + " "
								+ soContxt.getSlAdminLName();

					} else {
						createdBy = this._commonCriteria.getFName() + " "
								+ this._commonCriteria.getLName();
					}
				}
				documentVO.setCreatedBy(createdBy);
				if(null != _commonCriteria){
					documentVO.setVendorId(_commonCriteria.getCompanyId());
					documentVO.setRoleId(_commonCriteria.getRoleId());
					documentVO.setEntityId(_commonCriteria.getVendBuyerResId());
					documentVO.setCompanyId(_commonCriteria.getCompanyId());
				}

				documentVO.setDocCategoryId(null);

				documentVO.setDocSource(null);

				ProcessResponse pr = new ProcessResponse();

				pr = isoWizardPersistDelegate.insertSODocument(documentVO);


				if (pr!=null){

					partsManagementTabDTO.setResponseMessage(setErrorMessage(pr));

					if(pr.getCode().equals(OrderConstants.RESPONSE_CODE)){
						Integer documentId = (Integer) pr.getObj();  

						//SL-20465: Changes to associate doc to part in case of upload
						List<InvoicePartsVO> invoicePartVOlist=new ArrayList<InvoicePartsVO>();
						InvoicePartsVO invoicePart=new InvoicePartsVO();
						List<Integer> docIds=new ArrayList<Integer>();
						invoicePart.setInvoiceId(partInvoiceId);
						//Adding document id from response
						docIds.add(documentId);
						//setting to documentList in InvoicePartsVO
						invoicePart.setDocumentIdList(docIds);
						//adding InvoicePartsVO to List of InvoicePartsVOs
						invoicePartVOlist.add(invoicePart);

						//save the document attached to the part in invoice parts table(reusing the existing code)
						partsManagementService.saveInvoicePartDocument(invoicePartVOlist);

						documentIds.add(documentId);
						if(null != partsManagementTabDTO.getDocumentIds()){
							partsManagementTabDTO.getDocumentIds().add(documentId);
						}
						else{
							partsManagementTabDTO.setDocumentIds(new ArrayList<Integer>());
							partsManagementTabDTO.getDocumentIds().add(documentId);
						}
						Document document = new Document();
						document.setDocumentId(documentId);
						document.setFileName(fileName);
						document.setUploadDateTime(new SimpleDateFormat(DATE_FORMAT).format(new Date()));
						Contact result= (Contact) detailsDelegate.getVisitResourceName(documentVO.getEntityId());
						document.setUploadedbyName(result.getFirstName() +" "+ result.getLastName());

						if(null != partsManagementTabDTO.getEditPartVO().getInvoiceDocumentList()){
							partsManagementTabDTO.getEditPartVO().getInvoiceDocumentList().add(0, document);
						}else{
							partsManagementTabDTO.getEditPartVO().setInvoiceDocumentList(new ArrayList<Document>());
							partsManagementTabDTO.getEditPartVO().getInvoiceDocumentList().add(0, document);
						}

						//this.populateDocsInCurrentVisit(documentId); 
					}
				} 						
			}
		}catch(Exception e){
			LOGGER.error("Exception in documentUpload"+e);
			partsManagementTabDTO.setResponseMessage("Exception in uploading document"+e.getMessage());
			String returnvalue = getJSON(partsManagementTabDTO);
			getRequest().setAttribute("returnvalue", returnvalue);
			setModel(partsManagementTabDTO);

			getSession().setAttribute("partsManagementTabDTO", partsManagementTabDTO);
			return SUCCESS;
		}
		String returnvalue = getJSON(partsManagementTabDTO);
		getRequest().setAttribute("returnvalue", returnvalue);
		setModel(partsManagementTabDTO);
		getSession().setAttribute("partsManagementTabDTO", partsManagementTabDTO);
		return SUCCESS;
	}


	public String documentUploadInvoice(){
		PartsManagementTabDTO partsManagementTabDTOForm = getModel();
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		String fileType= null;
		String fileName = getRequest().getParameter("fileName");
		String invoicePartIds = null;
		String[] invoiceIds=null;
		List<Integer> partInvoiceIdList = new ArrayList<Integer>();
		try{
			if(null!= getSession().getAttribute("partsManagementTabDTO")){
				partsManagementTabDTO = (PartsManagementTabDTO)getSession().getAttribute("partsManagementTabDTO");
			}else{
				LOGGER.error("PartsManagementTabDTO Model returned null");
				return ERROR;
			}

			if (null != getParameter("invoicePartIds")) {
				invoicePartIds = getParameter("invoicePartIds");
			} 
			invoiceIds = StringUtils.split(invoicePartIds, COMMA_DELIMITER);
			if(null != invoiceIds){
				for (String invoiceId : invoiceIds) {
					if (StringUtils.isNotEmpty(invoiceId)) {
						partInvoiceIdList.add(Integer.parseInt(invoiceId));
					}
				}
			}

			if(null != partInvoiceIdList && !partInvoiceIdList.isEmpty() && null != partsManagementTabDTO.getInvoiceDetailsVO() && null != partsManagementTabDTO.getInvoiceDetailsVO().getInvoicePartsVOs()){
				String documentCountErrMsg = "No. of documents uploaded exceeded the limit(5) for the parts - ";
				String countExceededParts = "";
				for(InvoicePartsVO invoiceParts : partsManagementTabDTO.getInvoiceDetailsVO().getInvoicePartsVOs()){
					for(Integer invoicePartid : partInvoiceIdList){
						if(invoicePartid.equals(invoiceParts.getPartInvoiceId()) && invoiceParts.getDocumentCount() >= 5){
							countExceededParts = countExceededParts + invoiceParts.getPartNumber() + ", ";
						}
					}
				}
				if(!countExceededParts.equals("")){
					partsManagementTabDTO.setResponseMessage(documentCountErrMsg + countExceededParts.substring(0, (countExceededParts.length()-2)));
					String returnvalue = getJSON(partsManagementTabDTO);
					getRequest().setAttribute("returnvalue", returnvalue);
					setModel(partsManagementTabDTO);

					getSession().setAttribute("partsManagementTabDTO", partsManagementTabDTO);
					return SUCCESS;
				}
			}

			String fileExtenstion = "";
			Integer fileExtensionIndex=fileName.lastIndexOf(".");
			fileExtenstion = fileName.substring(fileExtensionIndex,fileName.length());
			if(null!=fileName && !(OrderConstants.EMPTY_STR.equals(fileName.trim()))){
				if(fileExtensionIndex == -1){
					partsManagementTabDTO.setResponseMessage("Invalid File format. Please use a valid format ");
					String returnvalue = getJSON(partsManagementTabDTO);
					getRequest().setAttribute("returnvalue", returnvalue);
					setModel(partsManagementTabDTO);

					getSession().setAttribute("partsManagementTabDTO", partsManagementTabDTO);
					return SUCCESS;
				}
				else{
					if(!(fileExtenstion.equalsIgnoreCase(PNG) || fileExtenstion.equalsIgnoreCase(JPG)||
							fileExtenstion.equalsIgnoreCase(GIF)|| fileExtenstion.equalsIgnoreCase(DOC)||
							fileExtenstion.equalsIgnoreCase(DOCX)|| fileExtenstion.equalsIgnoreCase(PDF)||  
							fileExtenstion.equalsIgnoreCase(TIFF) || fileExtenstion.equalsIgnoreCase(JPEG) ||  
							fileExtenstion.equalsIgnoreCase(PJPEG))){
						partsManagementTabDTO.setResponseMessage("Invalid File format. Please use a valid format ");
						String returnvalue = getJSON(partsManagementTabDTO);
						getRequest().setAttribute("returnvalue", returnvalue);
						setModel(partsManagementTabDTO);

						getSession().setAttribute("partsManagementTabDTO", partsManagementTabDTO);
						return SUCCESS;
					}
				}
			}


			fileName = URLDecoder.decode( fileName , "UTF-8");
			fileName = fileName.replaceAll("-prcntg-", "%");
			if(null!=partsManagementTabDTOForm){
				partsManagementTabDTO.setFileSelect(partsManagementTabDTOForm.getFileSelect());
				soId = getRequest().getParameter("soId"); 
			}
			File file = null;
			file = (File)partsManagementTabDTO.getFileSelect();   
			partsManagementTabDTO.setResponseMessage("");
			List<DocumentVO> documentVOList = new ArrayList<DocumentVO>();
			if(null != file){
				DocumentVO documentVO = new DocumentVO();
				Long docSize = file.length();
				Integer size = docSize.intValue()/OrderConstants.SIZE_KB;

				if(size>FIVE_KB){
					partsManagementTabDTO.setResponseMessage("Please attach a file no larger than 5MB.");
					String returnvalue = getJSON(partsManagementTabDTO);
					getRequest().setAttribute("returnvalue", returnvalue);
					setModel(partsManagementTabDTO);

					getSession().setAttribute("partsManagementTabDTO", partsManagementTabDTO);
					return SUCCESS;
				}
				FileNameMap fileNameMap = URLConnection.getFileNameMap();
				fileType = fileNameMap.getContentTypeFor(fileName);

				documentVO.setDocument(file);
				documentVO.setDocSize(file.length());
				documentVO.setSoId(soId);
				documentVO.setFileName(fileName);
				documentVO.setDescription("");
				documentVO.setTitle("Parts Invoice");
				documentVO.setFormat(fileType);
				documentVO.setDocSize(file.length());
				String createdBy="";
				if (null != soContxt) {
					if (soContxt.isAdopted()) {

						createdBy = soContxt.getSlAdminFName() + " "
								+ soContxt.getSlAdminLName();

					} else {
						createdBy = this._commonCriteria.getFName() + " "
								+ this._commonCriteria.getLName();
					}
				}
				documentVO.setCreatedBy(createdBy);
				if(null != _commonCriteria){
					documentVO.setVendorId(_commonCriteria.getCompanyId());
					documentVO.setRoleId(_commonCriteria.getRoleId());
					documentVO.setEntityId(_commonCriteria.getVendBuyerResId());
					documentVO.setCompanyId(_commonCriteria.getCompanyId());
				}

				documentVO.setDocCategoryId(null);

				documentVO.setDocSource(null);

				ProcessResponse pr = new ProcessResponse();

				pr = isoWizardPersistDelegate.insertSODocument(documentVO); 
				if (pr!=null){
					partsManagementTabDTO.setResponseMessage(setErrorMessage(pr));

					if(pr.getCode().equals(OrderConstants.RESPONSE_CODE)){
						Integer documentId = (Integer) pr.getObj();  

						//SL-20465: Changes to associate doc to part in case of upload
						List<InvoicePartsVO> invoicePartVOlist=new ArrayList<InvoicePartsVO>();
						for (String invoiceId : invoiceIds) {
							InvoicePartsVO invoicePart = new InvoicePartsVO();
							invoicePart.setInvoiceId(invoiceId);
							List<Integer> docIds = new ArrayList<Integer>();
							docIds.add(documentId);
							invoicePart.setDocumentIdList(docIds);
							invoicePartVOlist.add(invoicePart);
						}
						//save the document attached to the part in invoice parts table(reusing the existing code)
						partsManagementService.saveInvoicePartDocument(invoicePartVOlist);


						documentIds.add(documentId);
						if(null != partsManagementTabDTO.getDocumentIds()){
							partsManagementTabDTO.getDocumentIds().add(documentId);
						}
						else{
							partsManagementTabDTO.setDocumentIds(new ArrayList<Integer>());
							partsManagementTabDTO.getDocumentIds().add(documentId);
						}
						Document document = new Document();
						document.setDocumentId(documentId);
						document.setFileName(fileName);
						document.setUploadDateTime(new SimpleDateFormat(DATE_FORMAT).format(new Date()));
						Contact result= (Contact) detailsDelegate.getVisitResourceName(documentVO.getEntityId());
						document.setUploadedbyName(result.getFirstName() +" "+ result.getLastName());

						if(null != partsManagementTabDTO.getInvoiceDetailsVO().getInvoiceDocuments()){
							partsManagementTabDTO.getInvoiceDetailsVO().getInvoiceDocuments().add(0, document);
						}else{
							partsManagementTabDTO.getInvoiceDetailsVO().setInvoiceDocuments(new ArrayList<Document>());
							partsManagementTabDTO.getInvoiceDetailsVO().getInvoiceDocuments().add(0, document);
						}
						for(InvoicePartsVO invoiceParts : partsManagementTabDTO.getInvoiceDetailsVO().getInvoicePartsVOs()){
							invoiceParts.setDocumentCount(invoiceParts.getDocumentCount()+1);
						}

						//this.populateDocsInCurrentVisit(documentId); 
					}
				} 						
			}
		}catch(Exception e){
			LOGGER.error("Exception in documentUpload"+e);
			partsManagementTabDTO.setResponseMessage("Exception in uploading document"+e.getMessage());
			String returnvalue = getJSON(partsManagementTabDTO);
			getRequest().setAttribute("returnvalue", returnvalue);
			setModel(partsManagementTabDTO);

			getSession().setAttribute("partsManagementTabDTO", partsManagementTabDTO);
			return SUCCESS;
		}
		String returnvalue = getJSON(partsManagementTabDTO);
		getRequest().setAttribute("returnvalue", returnvalue);
		setModel(partsManagementTabDTO);
		getSession().setAttribute("partsManagementTabDTO", partsManagementTabDTO);
		return SUCCESS;
	}
	public String loadEditPartUploadedFiles(){
		if(null!= getSession().getAttribute("partsManagementTabDTO")){
			partsManagementTabDTO = (PartsManagementTabDTO)getSession().getAttribute("partsManagementTabDTO");
		}
		if(null != partsManagementTabDTO){
			getRequest().setAttribute("editPartDetails", partsManagementTabDTO.getEditPartVO());
		}
		return SUCCESS;
	}

	public String loadEditPartUploadedFilesInvoice(){
		if(null!= getSession().getAttribute("partsManagementTabDTO")){
			partsManagementTabDTO = (PartsManagementTabDTO)getSession().getAttribute("partsManagementTabDTO");
		}
		if(null != partsManagementTabDTO){
			getRequest().setAttribute("invoicePartDetails", partsManagementTabDTO.getInvoiceDetailsVO());
		}
		return SUCCESS;
	}

	public String viewDocument(){
		PartsManagementTabDTO partsManagementTabDTO = getModel();
		Integer documentId = null;
		try {
			SODocumentDTO document = new SODocumentDTO();
			if(null != getRequest().getParameter("editDocId")){
				documentId = Integer.parseInt(getRequest().getParameter("editDocId")); 
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String removeDocument(){
		if(null!= getSession().getAttribute("partsManagementTabDTO")){
			partsManagementTabDTO = (PartsManagementTabDTO)getSession().getAttribute("partsManagementTabDTO");
		}
		Integer documentId = null;
		Integer invoicePartId = null;
		String source = null;
		DocumentVO documentVO = null;
		ProcessResponse pr = null;
		boolean documentDeleted = false;
		Integer documentCount = 0;
		List<Integer> partInvoiceIdList = new ArrayList<Integer>();
		String checkedInvoiceIds=null;
		String[] invoiceIds=null;
		if(null!= partsManagementTabDTO){
			soId = getRequest().getParameter("soId"); 
			try{
				if(null != getRequest().getParameter("documentId")){
					documentId = Integer.parseInt(getRequest().getParameter("documentId"));
				}
				if(null != getRequest().getParameter("invoiceId")){
					invoicePartId = Integer.parseInt(getRequest().getParameter("invoiceId"));
				}
				source = (String)getRequest().getParameter("source");


				if (null != getParameter(CHECKED_INVOICE_IDS)) {
					checkedInvoiceIds = getParameter(CHECKED_INVOICE_IDS);
				} else {
					checkedInvoiceIds = (String) getSession().getAttribute(
							CHECKED_INVOICE_IDS);
				}
				invoiceIds = StringUtils.split(checkedInvoiceIds, COMMA_DELIMITER);
				if(null != invoiceIds){
					for (String invoiceId : invoiceIds) {
						if (StringUtils.isNotEmpty(invoiceId)) {
							partInvoiceIdList.add((Integer.parseInt(invoiceId)));
						}
					}
				}

				// Fetch count of records from so_provider_invoice_doc for the documentId.
				// if(count = 0) delete the document. Nothing more
				// if source is edit_part and count > 1 do not delete document from document table.
				// 		Just delete the record from so_provider_invoice_doc for the invoicePartId
				// if source is add_invoice delete document from document table.
				//		delete all the records in the so_provider_invoice_doc for the documentId
				List<Integer> documentIdList = new ArrayList<Integer>();
				if(null!=invoicePartId){
					partInvoiceIdList.add(invoicePartId);
				}
				if(("cancel_edit_part").equals(source) || ("cancel_add_invoice").equals(source)){
					documentIdList = partsManagementTabDTO.getDocumentIds();
					for(Integer docId:documentIdList){
						if(null != partInvoiceIdList && !partInvoiceIdList.isEmpty()){
							partsManagementService.deleteSelectedPartDocument(partInvoiceIdList,docId);
						}	
					}
				}else{
					documentIdList.add(documentId);
					documentCount = partsManagementService.getInvoiceIdCountForDocId(documentIdList);
				}
				if(0 == documentCount && null != documentIdList && !documentIdList.isEmpty()){
					partsManagementService.documentHardDelete(documentIdList, soId);
					documentDeleted = true;
				}else if(("edit_part").equals(source)){
					if(documentCount > 1){
						if(null != partInvoiceIdList && !partInvoiceIdList.isEmpty()){
							partsManagementService.deleteSelectedPartDocument(partInvoiceIdList,documentId);
						}						
					}else if(documentCount == 1){
						if(null != partInvoiceIdList && !partInvoiceIdList.isEmpty()){
							partsManagementService.deleteSelectedPartDocument(partInvoiceIdList,documentId);
						}
						partsManagementService.documentHardDelete(documentIdList, soId);
					}
					documentDeleted = true;
				}else if(("add_invoice").equals(source)){
					if(null != partInvoiceIdList && !partInvoiceIdList.isEmpty()){
						partsManagementService.deleteSelectedPartDocument(partInvoiceIdList,documentId);
					}
					documentCount = partsManagementService.getInvoiceIdCountForDocId(documentIdList);
					if(documentCount == 0){
						partsManagementService.documentHardDelete(documentIdList, soId);
					}
					documentDeleted = true;
				}
				if(("cancel_edit_part").equals(source)){
					if(null != partsManagementTabDTO.getEditPartVO() && null !=partsManagementTabDTO.getEditPartVO().getInvoiceDocumentList()){
						partsManagementTabDTO.getEditPartVO().getInvoiceDocumentList().clear();
					}
					if(null != partsManagementTabDTO.getDocumentIds()){
						partsManagementTabDTO.getDocumentIds().clear();
					}
				}
				else if(("cancel_add_invoice").equals(source)){
					if(null != partsManagementTabDTO.getInvoiceDetailsVO() && null !=partsManagementTabDTO.getInvoiceDetailsVO().getInvoiceDocuments()){
						partsManagementTabDTO.getInvoiceDetailsVO().getInvoiceDocuments().clear();
					}
					if(null != partsManagementTabDTO.getDocumentIds()){
						partsManagementTabDTO.getDocumentIds().clear();
					}
				}
				else if(("edit_part").equals(source)){
					List<Document> tempDocumentList = new ArrayList<Document>(partsManagementTabDTO.getEditPartVO().getInvoiceDocumentList());
					if(documentDeleted && null != partsManagementTabDTO.getEditPartVO()){
						for(Document doc : partsManagementTabDTO.getEditPartVO().getInvoiceDocumentList()){
							if(documentId.equals(doc.getDocumentId())){
								tempDocumentList.remove(doc);
								break;
							}
						}
						if(null != partsManagementTabDTO.getDocumentIds()){
							partsManagementTabDTO.getDocumentIds().remove(documentId);
						}
						partsManagementTabDTO.getEditPartVO().setInvoiceDocumentList(tempDocumentList);
						setModel(partsManagementTabDTO);
					}
				}
				else if(("add_invoice").equals(source)){
					if(documentDeleted && null != partsManagementTabDTO.getInvoiceDetailsVO() && null!= partsManagementTabDTO.getInvoiceDetailsVO().getInvoiceDocuments()){
						List<Document> tempDocumentList = new ArrayList<Document>(partsManagementTabDTO.getInvoiceDetailsVO().getInvoiceDocuments());
						for(Document doc : partsManagementTabDTO.getInvoiceDetailsVO().getInvoiceDocuments()){
							if(documentId.equals(doc.getDocumentId())){
								tempDocumentList.remove(doc);
								break;
							}
						}
						if(null != partsManagementTabDTO.getDocumentIds()){
							partsManagementTabDTO.getDocumentIds().remove(documentId);
						}
						for(InvoicePartsVO invoiceParts : partsManagementTabDTO.getInvoiceDetailsVO().getInvoicePartsVOs()){
							for(Document doc : invoiceParts.getInvoiceDocumentList()){
								if(documentId.equals(doc.getDocumentId())){
									invoiceParts.setDocumentCount(invoiceParts.getDocumentCount().equals(0) ? 0 : (invoiceParts.getDocumentCount() - 1));
								}
							}
						}
						partsManagementTabDTO.getInvoiceDetailsVO().setInvoiceDocuments(tempDocumentList);
						setModel(partsManagementTabDTO);
					}
				}
			}catch(Exception e){	
				LOGGER.error("Exception in removeDocument()"+e);
			}
		}else{
			LOGGER.info("PartsManagementTabDTO Model returned null");
			getSession().setAttribute("partsManagementTabDTO", partsManagementTabDTO);
			return ERROR;
		}
		getSession().setAttribute("partsManagementTabDTO", partsManagementTabDTO);
		return SUCCESS;
	}

	private String setErrorMessage(ProcessResponse pr){
		String errorMessage = "-1";

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
		}
		return errorMessage;
	}

	public String getJSON(Object obj) {
		String rval= "null";
		JSONSerializer serializer = new JSONSerializer();

		if (obj != null) try {
			rval= serializer.exclude("*.class").serialize(obj);
		} catch (Throwable e) {
			LOGGER.error("Unable to generate JSON for object: " + obj.toString(), e);
		}               
		return rval;
	}

	public ISOWizardPersistDelegate getIsoWizardPersistDelegate() {
		return isoWizardPersistDelegate;
	}

	public void setIsoWizardPersistDelegate(
			ISOWizardPersistDelegate isoWizardPersistDelegate) {
		this.isoWizardPersistDelegate = isoWizardPersistDelegate;
	}

	public ISOWizardFetchDelegate getFetchDelegate() {
		return fetchDelegate;
	}

	public void setFetchDelegate(ISOWizardFetchDelegate fetchDelegate) {
		this.fetchDelegate = fetchDelegate;
	}


	/**
	 * @return
	 * 
	 * action for load of update part status
	 */
	public String loadUpdatePartDetails(){
		//TODO:
		PartsManagementTabDTO partsManagementTabDTO = getModel();
		String checkedInvoiceIds=null;
		String[] invoiceIds=null;
		List<Integer> invoicePartIds = new ArrayList<Integer>();
		InvoiceDetailsVO invoiceDetailsVO = null;
		String soId = (String)getRequest().getParameter("soId");
		if (null != getParameter(CHECKED_INVOICE_IDS)) {
			checkedInvoiceIds = getParameter(CHECKED_INVOICE_IDS);
		} else {
			checkedInvoiceIds = (String) getSession().getAttribute(
					CHECKED_INVOICE_IDS);
		}
		invoiceIds = StringUtils.split(checkedInvoiceIds, COMMA_DELIMITER);
		for (String invoiceId : invoiceIds) {
			if (StringUtils.isNotEmpty(invoiceId)) {
				invoicePartIds.add((Integer.parseInt(invoiceId)));
			}
		}
		if(!invoicePartIds.isEmpty()){
			invoiceDetailsVO = partsManagementService
					.loadInvoiceDetails(invoicePartIds);
		}
		invoiceDetailsVO.getPartStatusTypes().remove(0);
		if (null != invoiceDetailsVO && null!=invoiceDetailsVO.getInvoicePartsVOs() && !invoiceDetailsVO.getInvoicePartsVOs().isEmpty()) {
			getRequest().setAttribute("updatePartDetailsInfo", invoiceDetailsVO);
			return SUCCESS;
		}
		return ERROR;

	}



	/**
	 * @return
	 * 
	 * action for saving the updated part status
	 */
	public String updatePartStatus(){
		//TODO:
		PartsManagementTabDTO partsManagementTabDTO = getModel();
		List<InvoicePartsVO> parts=new ArrayList<InvoicePartsVO>();
		List<InvoicePartsVO> editedParts=new ArrayList<InvoicePartsVO>();
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(
				SECURITY_CONTEXT);
		String userName = soContxt.getUsername();
		String[] soIds = null;
		String soId = null;
		if(null!= partsManagementTabDTO){

			//Validations for partStatus to check if it is 'Part Added'
			parts=partsManagementTabDTO.getAddInvoiceDTO();
			if(null != parts){
				//Removing unedited parts
				for(InvoicePartsVO part: parts){
					if(null != part){
						if(null != part.getPartInvoiceId()){
							editedParts.add(part);
						}
					}

				}

				soIds = StringUtils.split(partsManagementTabDTO.getSoId(), COMMA_DELIMITER);
				soId = soIds[0];

				//If validations passes, update the parts status
				partsManagementService.updatePartStatus(editedParts,userName,soId);

				return SUCCESS;
			}
		}

		return ERROR;
	}

	public  List<String> removeDuplicatesInvoice(List<String> list) {
		// Store unique items in result.
		List<String> result = new ArrayList<String>();
		// Record encountered Strings in HashSet.
		HashSet<String> set = new HashSet<String>();
		// Loop over argument list.
		for (String item : list) {
			// If String is not in set, add it to the list and the set.
			if (!set.contains(item)) {
				result.add(item);
				set.add(item);
			}
		}
		return result;
	}
	/**
	 * 
	 * @param dBInvoiceDocMap
	 * @param newDocuments
	 * @param documentIds
	 * @return
	 * method to validate the count of documents  associated to an invoice
	 */
	public boolean validateCountOfInvoiceDocuments (Map<Integer , List<Integer>> dBInvoiceDocMap ,Map<Integer , List<Integer>> newDocumentMap,List<Integer> documentIds){
		boolean errorInCountOfInvoiceDoc = true;
		int totalCount ;
		List<Integer> newDocIdList= null;		
		List<Integer> docIdListDB = null;	
		try{
			if(null != dBInvoiceDocMap && !(dBInvoiceDocMap.isEmpty())){
				Set<Integer> invoiceIds = dBInvoiceDocMap.keySet();
				for(Integer invoiceId : invoiceIds){
					if(!(documentIds.isEmpty())){						
						newDocIdList= newDocumentMap.get(invoiceId);  
						docIdListDB = dBInvoiceDocMap.get(invoiceId);
						//iterating through the existing documentIds
						totalCount = newDocIdList.size() + docIdListDB.size();
						if(totalCount > 5){
							errorInCountOfInvoiceDoc = false;
						}										
					}
				}
			}
			else{
				if(documentIds.size() > 5){
					errorInCountOfInvoiceDoc = false;
				}
			}
		}
		catch (Exception e) {
			LOGGER.error("Exception in validateCountOfInvoiceDocuments()"+e);
		}
		return errorInCountOfInvoiceDoc;
	}	
	/**
	 * 
	 * @param documentIds
	 * @param dBInvoiceDocMap
	 * @return
	 * Method to get the new documents associated to each parts for an invoice
	 */
	public Map<Integer , List<Integer>> checkForNewDocumentList(List<Integer> documentIds ,Map<Integer ,List<Integer>>dBInvoiceDocMap){

		List<Integer> newDocIdList= null;		
		List<Integer> docIdListDB = null;
		Map<Integer , List<Integer>> newDocumentMap = new HashMap<Integer , List<Integer>>();								
		List<Integer> docIdList =null;
		try{
			if(null != dBInvoiceDocMap && !(dBInvoiceDocMap.isEmpty())){
				Set<Integer> invoiceIds = dBInvoiceDocMap.keySet();
				for(Integer invoiceId : invoiceIds){
					if(!(documentIds.isEmpty())){
						newDocIdList= new ArrayList<Integer>();  
						docIdListDB = dBInvoiceDocMap.get(invoiceId);
						//iterating through the existing documentIds
						if(null!=docIdListDB && !(docIdListDB.isEmpty())){
							for(Integer docId :documentIds){
								if(!(docIdListDB.contains(docId))){
									newDocIdList.add(docId);
								}
							} 
							newDocumentMap.put(invoiceId, newDocIdList);
						}
						else{
							newDocIdList = documentIds;
							newDocumentMap.put(invoiceId, newDocIdList);
						}

					}
				}
			}
		}
		catch (Exception e) {
			LOGGER.error("Exception in checkForNewDocumentList()"+e);
		}
		return newDocumentMap;
	}


	/**
	 * Sort the invoicePartList as below logic
	 * 1. If invoice number is null or not present, sort those records based on part status sort ascending
	 * 2. If invoice number is present, sort records by primarily invoice number and secondary part status	
	 * @param invoicePartList
	 */
	private void sortInvoicePartList(List<ProviderInvoicePartsVO> invoicePartList){
		List<ProviderInvoicePartsVO> invoicePartListWithoutInvoiceNo = new ArrayList<ProviderInvoicePartsVO>();
		List<ProviderInvoicePartsVO> invoicePartListWithInvoiceNo = new ArrayList<ProviderInvoicePartsVO>();
		if(null != invoicePartList && invoicePartList.size()>0){
			//separate into two lists one containing parts w/o invoice number and other having invoice number
			for(ProviderInvoicePartsVO invoicePart: invoicePartList){
				if(null != invoicePart){
					if(null != invoicePart.getInvoiceNo() && invoicePart.getInvoiceNo().length()>0){
						invoicePartListWithInvoiceNo.add(invoicePart);
					}else{
						invoicePartListWithoutInvoiceNo.add(invoicePart);
					}
				}
			}

			//sort invoicePartListWithoutInvoiceNo based on Part Status
			Collections.sort(invoicePartListWithoutInvoiceNo, new Comparator<ProviderInvoicePartsVO>() {
				public int compare(ProviderInvoicePartsVO o1,
						ProviderInvoicePartsVO o2) {
					int compareValue = 0;
					if(StringUtils.isNotEmpty(o1.getPartStatus()) &&  StringUtils.isNotEmpty(o2.getPartStatus())){
						compareValue = o1.getPartStatus().compareTo(o2.getPartStatus());
					}
					return compareValue;
				}
			});
			//sort invoicePartListWithInvoiceNo based on invoiceNo, partStatus
			Collections.sort(invoicePartListWithInvoiceNo, new Comparator<ProviderInvoicePartsVO>() {

				public int compare(ProviderInvoicePartsVO o1,
						ProviderInvoicePartsVO o2) {
					int compareValue = o1.getInvoiceNo().compareTo(o2.getInvoiceNo());
					if(compareValue == 0){
						if(StringUtils.isNotEmpty(o1.getPartStatus()) &&  StringUtils.isNotEmpty(o2.getPartStatus())){
							compareValue = o1.getPartStatus().compareTo(o2.getPartStatus());
						}
					}
					return compareValue;
				}
			});
			//clear the original invoicePartList
			invoicePartList.clear();
			//add the sorted lists to invoicePartList
			invoicePartList.addAll(invoicePartListWithoutInvoiceNo);
			invoicePartList.addAll(invoicePartListWithInvoiceNo);
		}
	}
	/**
	 * Method to fetch the part details when user search for a part number
	 * @return
	 */
	public String searchParts() {
		partsManagementTabDTO = getModel();
		List<InvoicePartsVO> addPartVOs = null;
		String result = null;	
		try {
			String partNum = (String)getRequest().getParameter("partNum");
			String searchSoId = (String)getRequest().getParameter("partsSoId");
			//method to search parts in LIS service for the entered part number
			addPartVOs = partsManagementService.searchParts(partNum,searchSoId);

			if(!addPartVOs.isEmpty() && null !=addPartVOs.get(0) && null!=addPartVOs.get(0).getErrorMessage() && StringUtils.isNotBlank(addPartVOs.get(0).getErrorMessage())){
				setAttribute(LIS_ERROR,addPartVOs.get(0).getErrorMessage());
				result ="success";
			}

			else if(null !=addPartVOs && !addPartVOs.isEmpty()){
				getRequest().setAttribute("searchPartDetails", addPartVOs);
				partsManagementTabDTO.setAddInvoiceDTO(addPartVOs);
				getSession().setAttribute("partsManagementTabDTO", partsManagementTabDTO);	
				result ="success";
			}
			else{
				partsManagementTabDTO.setSoId(searchSoId);
				getSession().setAttribute("partsManagementTabDTO", partsManagementTabDTO);	
				result ="success";
			}
		} 
		catch (Exception e) {
			LOGGER.error("Exception in searchParts()"+e.getMessage());
			e.printStackTrace();
		}

		return result;

	}
	/**
	 * Method to save the LIS part details to the db
	 * @return
	 */
	public String saveLisParts(){

		partsManagementTabDTO= getModel();				
		String checkedLisParts = null;
		List<InvoicePartsVO> partVOs = new ArrayList<InvoicePartsVO>();
		List<Integer>lisPartCount = new ArrayList<Integer>();
		String[] lisParts = null;
		List<InvoicePartsVO> lisPartVOs = new ArrayList<InvoicePartsVO>();

		if(null!=partsManagementTabDTO){
			partVOs= partsManagementTabDTO.getAddInvoiceDTO();
			if (null != partsManagementTabDTO.getPartNumPosition()) {
				checkedLisParts = partsManagementTabDTO.getPartNumPosition();
			} else {
				checkedLisParts = (String) getSession().getAttribute(partsManagementTabDTO.getPartNumPosition());
			}
			lisParts = StringUtils.split(checkedLisParts, COMMA_DELIMITER);
			for (String lisPart : lisParts) {
				if (StringUtils.isNotEmpty(lisPart) && StringUtils.isNotBlank(lisPart)) {
					lisPartCount.add((Integer.parseInt(lisPart)));
				}
			}
			for(Integer i : lisPartCount){
				for(InvoicePartsVO part : partVOs){
					if(null!=part && null !=part.getPartIdentifier() 
							&& StringUtils.isNotEmpty(part.getPartIdentifier().toString()) && StringUtils.isNotBlank(part.getPartIdentifier().toString())){
						if(part.getPartIdentifier().intValue()==i.intValue()){
							lisPartVOs.add(part);
						}
					}
				}

			}
		}
		Double retailPriceSum = null;
		Double totalRetailPrice = null;
		Double currentRetailPrice = null;
		Double retailPrice=null;
		Double commercialPrice=null;
		Boolean autoAdjudicationInd = null;
		Double adjudicationCommPricePercentage=null;
		//	ServiceOrderDTO soDTO=null;
		String invoicePricingModel=null;


		if(null!=lisPartVOs && !lisPartVOs.isEmpty()){
			Double totalPrice=0.0;
			for(InvoicePartsVO part :lisPartVOs){
				currentRetailPrice = (Double.parseDouble(part.getRetailPrice())) * part.getQty();
				totalPrice = totalPrice+currentRetailPrice;
			}
			//SL-21811
			if(totalPrice > RETAIL_MAX_PRICE){
				partsManagementTabDTO.setResponseMessage(RETAIL_PRICE_MAX_VALIDATION.replace("$", "$"+String.valueOf(RETAIL_MAX_PRICE)));
				String returnvalue = getJSON(partsManagementTabDTO);
				getRequest().setAttribute("returnvalue", returnvalue);
				return SUCCESS;
			}
			else{

				for(InvoicePartsVO lispart :lisPartVOs){	


					autoAdjudicationInd=partsManagementService.getAutoAdjudicationInd();
					//R12_1:
					//If a new part is created for a service order and if the parts_invoice_pricing model column has no value
					//and if the adjudication is on , then  update the so_work_flowcontrols  parts_invoice_pricing_type column  as 'cost plus model'.
					invoicePricingModel=partsManagementService.getInvoicePartsPricingModel(lispart.getSoId());

					if(null==invoicePricingModel){
						if(true==autoAdjudicationInd){
							partsManagementService.saveInvoicePartPricingModel(lispart.getSoId(),MPConstants.INVOICE_PARTS_COSTPLUS_PRICING_MODEL);
							invoicePricingModel=MPConstants.INVOICE_PARTS_COSTPLUS_PRICING_MODEL;
						}
						else{
							partsManagementService.saveInvoicePartPricingModel(lispart.getSoId(),MPConstants.INVOICE_PARTS_REIMBURSEMENT_PRICING_MODEL);
							invoicePricingModel=MPConstants.INVOICE_PARTS_REIMBURSEMENT_PRICING_MODEL;

						}
					}
					lispart.setPartNoSource(MPConstants.PART_SOURCE_LIS);
					lispart.setPartStatus(MPConstants.ADDED);
					//R12_1: adding entry to column commercial price
					if(MPConstants.INVOICE_PARTS_COSTPLUS_PRICING_MODEL.equals(invoicePricingModel)){
						if(null != lispart.getRetailPrice()){
							retailPrice=Double.parseDouble(lispart.getRetailPrice());
							//Fetching the percentage value from DB
							try{
								adjudicationCommPricePercentage=Double.parseDouble((partsManagementService.getConstantValueFromDB(MPConstants.ADJUDICATION_PRICE_PERC_KEY)));
								commercialPrice=MoneyUtil.getRoundedMoney(retailPrice - (retailPrice*(adjudicationCommPricePercentage/100)));
								lispart.setCommercialPrice(commercialPrice.toString());	
							}
							catch (Exception e) {
								// TODO: handle exception
								return ERROR;
							}
						}	
					}
					else{
						if(null != lispart.getRetailPrice()){
							lispart.setCommercialPrice(lispart.getRetailPrice());	
						}
					}
					retailPriceSum = partsManagementService.getRetailPriceTotal(lispart.getSoId(),null); 
					if(retailPriceSum == null){
						retailPriceSum =0.0;
					}
					currentRetailPrice = (Double.parseDouble(lispart.getRetailPrice())) * lispart.getQty();

					totalRetailPrice = retailPriceSum + currentRetailPrice;
					//SL-21811
					if(totalRetailPrice > RETAIL_MAX_PRICE){
						setAttribute(RETAIL_PRICE_ERROR, RETAIL_PRICE_MAX_VALIDATION.replace("$", "$"+String.valueOf(RETAIL_MAX_PRICE)));
					}
					else{
						partsManagementService.savePartsDetails(lispart);

					}				
				}

			}
		}

		else{
			return ERROR;
		}
		String returnvalue = getJSON(partsManagementTabDTO);
		getRequest().setAttribute("returnvalue", returnvalue);
		return SUCCESS;


	}

}
