package com.newco.marketplace.business.businessImpl.so.pdf;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.List;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Signature;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.util.constants.SOPDFConstants;
import com.newco.marketplace.utils.ServiceLiveStringUtils;

/**
 * Class responsible for generating the Customer Copy/Service Pro Copy of PDF for RI
 */
public class RISODescriptionPDFTemplateImpl {
	
	private Image logoImage;
	private ServiceOrder so;
	private String mainPageTitle;
	private static final Logger logger = Logger.getLogger(PDFGenerator.class);
	
	private Signature customerSignature;
	private Image customerSignatureImage;
	private Signature providerSignature;
	private Image providerSignatureImage;
	private boolean mobileIndicator = false;
	/**
	 * @param pdfWriter
	 * @param document
	 * @param omFlag (included for SL-15642:Merge PDF generation)
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void execute(PdfWriter pdfWriter, Document document, boolean omFlag) throws DocumentException, IOException {
		if(!omFlag){
			document.newPage();
		}
		PdfPTable headerWithBarcode = createNewHeaderTable();
		addHeaderTable(headerWithBarcode, pdfWriter,true);
		document.add(headerWithBarcode);
		PdfPTable mainTable = createNewMainTable();
		addHeaderTable(mainTable, pdfWriter,false);
		addSoDetailsTables(mainTable);
		PdfPTable descTable = createDescTable();
		mainTable = addTaskAndPartList(descTable, mainTable, pdfWriter, document);
		addNotesTable(mainTable);
		document.add(mainTable);
		mainTable = createNewTable();
		addWaiverList(mainTable,pdfWriter, document);
		// Moved the customer signature code to WAIVER OF LIEN to keep it together
		/*
		mainTable = createNewTable();
		addSignaturesTable(mainTable,pdfWriter, document);
		mainTable = createNewTable();
		addQuestionsText(mainTable,pdfWriter, document);
		*/
		
		
	}
	
	public RISODescriptionPDFTemplateImpl() {
		super();
	}
	
	public RISODescriptionPDFTemplateImpl(boolean mobileIndicator) {
		super();
		this.mobileIndicator = mobileIndicator;
	}

	private PdfPTable createNewHeaderTable() {
		float[] widths = { 21, 60 };
		PdfPTable mainTable = new PdfPTable(widths);
		mainTable.setSplitLate(false);
		mainTable.getDefaultCell().setBorder(0);
		float pageWidth = PageSize.LETTER.getWidth();
		mainTable.setTotalWidth(pageWidth - 30);
		mainTable.setLockedWidth(true);
		return mainTable;
	}
	private PdfPTable createNewTable() {
		float pageWidth = PageSize.LETTER.getWidth();
		float[] widths = { 21, 60 };
		PdfPTable mainTable = new PdfPTable(widths);
		mainTable.setSplitLate(false);
		mainTable.getDefaultCell().setBorder(0);
		mainTable.setTotalWidth(pageWidth - 30);
		mainTable.setLockedWidth(true);
		return mainTable;
	}
	private PdfPTable createNewMainTable() {
		float[] widths = { 21, 60 };
		PdfPTable mainTable = new PdfPTable(widths);
		mainTable.setSplitLate(false);
		mainTable.setHeaderRows(1);
		mainTable.getDefaultCell().setBorder(0);
		float pageWidth = PageSize.LETTER.getWidth();
		mainTable.setTotalWidth(pageWidth - 30);
		mainTable.setLockedWidth(true);
		mainTable.setSkipFirstHeader(true);
		return mainTable;
	}

	/**
	 * Method for adding the header table to the Service Order Description Table
	 * 
	 * @param mainTable
	 */
	private void addHeaderTable(PdfPTable mainTable,PdfWriter pdfWriter,boolean showBarCode) {
		// Create header table
		PdfPTable headerTable = new PdfPTable(3);
		headerTable.getDefaultCell().setBorder(0);
		headerTable.setHorizontalAlignment(Element.ALIGN_CENTER);
		float pageWidth = PageSize.LETTER.getWidth();
		headerTable.setTotalWidth(pageWidth - 30);
		headerTable.setLockedWidth(true);

		// Add logo on left corner
		SOPDFUtils.addLogoImage(headerTable, logoImage);

		// Add soId in center
		PdfPCell soIdCell = new PdfPCell(new Paragraph(SOPDFConstants.SERVICE_ORDER_NUMBER + so.getSoId(), SOPDFConstants.FONT_LABEL_BIG));
		soIdCell.setBorder(0);
		PdfPTable centerSoId = new PdfPTable(1);
		centerSoId.setHorizontalAlignment(Element.ALIGN_CENTER);
		centerSoId.addCell(soIdCell);
		
		// Add bar code below soId
		//only for Provider copy
		if(mainPageTitle.equalsIgnoreCase(SOPDFConstants.PROVIDER_COPY) && showBarCode){
			PdfPCell blankCell = new PdfPCell();
	        blankCell.setBorder(0);
	        blankCell.setFixedHeight(10f);
	        
	        PdfContentByte cb = pdfWriter.getDirectContent();
	        Barcode128 barCode = new Barcode128();
	        barCode.setCode(so.getSoId());       
	        barCode.setFont(SOPDFConstants.FONT_ITALIC.getBaseFont());
	        barCode.setSize(6f);
	        barCode.setX(0.85f);
			Image barCodeImg = barCode.createImageWithBarcode(cb, null, null);
					
	        PdfPCell barCodeCell = new PdfPCell(barCodeImg);
	        barCodeCell.setBorder(0);
	        barCodeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        centerSoId.addCell(blankCell);
	        centerSoId.addCell(barCodeCell);
		}        
        
		headerTable.addCell(centerSoId);

		// Add Page Heading on right side
		PdfPCell titleCell = new PdfPCell(new Paragraph(mainPageTitle, SOPDFConstants.FONT_LABEL_BIG));
		titleCell.setBorder(0);
		titleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		PdfPTable rightTitle = new PdfPTable(1);
		rightTitle.setHorizontalAlignment(Element.ALIGN_RIGHT);
		rightTitle.addCell(titleCell);
		headerTable.addCell(rightTitle);

		// Now, add entire heading row to main table
		PdfPCell headingCell = new PdfPCell();
		headingCell.setBorder(0);
		headingCell.setColspan(3);
		headingCell.addElement(headerTable);
		mainTable.addCell(headingCell);
	}

	/**
	 * Method for adding so details sub tables like service location, service
	 * date, buyer custom refs etc to the Service Order Description table
	 * 
	 * @param mainTable
	 */
	private void addSoDetailsTables(PdfPTable mainTable) {

		// Create left side details table
		PdfPTable detailsTable = new PdfPTable(1);
		detailsTable.setKeepTogether(false);
		detailsTable.getDefaultCell().setBorder(0);
		detailsTable.getDefaultCell().setBorderWidthBottom(0);
		detailsTable.getDefaultCell().setBorderWidthTop(0);
		detailsTable.getDefaultCell().setBorderWidthLeft(0);
		detailsTable.getDefaultCell().setBorderWidthRight(1);
		addServiceLocationTable(detailsTable);
		addServiceDateTable(detailsTable);
		addProvider(detailsTable);
		addCustomRef(detailsTable);
		addProductInfo(detailsTable);

		// Add left side details table to main table
		mainTable.addCell(detailsTable);
	}

	/**
	 * Method responsible for adding location details to so details table.
	 * 
	 * @param so
	 * @param descTable
	 */
	private void addServiceLocationTable(PdfPTable detailsTable) {
		PdfPTable tableServiceLocation = new PdfPTable(1);
		List serviceLocationList = new List(false);
		serviceLocationList.setListSymbol(SOPDFConstants.BLANK);
		serviceLocationList.add(new ListItem(SOPDFConstants.SERVICE_LOCATION, SOPDFConstants.FONT_HEADER_MEDIUM));

		if (so.getServiceLocation() != null) {
			if (so.getServiceLocation().getLocnClassDesc() != null) {
				serviceLocationList.add(new ListItem(so.getServiceLocation().getLocnClassDesc(), SOPDFConstants.FONT_ITALIC_MEDIUM));
			}
			// Adding businessName to serviceLocationList
			if (so.getServiceContact() != null && so.getServiceContact().getBusinessName() != null) {
				serviceLocationList.add(new ListItem(so.getServiceContact().getBusinessName(), SOPDFConstants.FONT_LABEL_MEDIUM));
			}
			if (so.getServiceContact() != null && so.getServiceContact().getFirstName() != null && so.getServiceContact().getLastName() != null) {
				serviceLocationList.add(new ListItem(so.getServiceContact().getLastName() + SOPDFConstants.COMMA + SOPDFConstants.SPACE + so.getServiceContact().getFirstName(), SOPDFConstants.FONT_LABEL_MEDIUM));
			}
			String[] checkers = new String[10];
			if (so.getServiceLocation().getStreet1() != null && so.getServiceLocation().getStreet2() != null && so.getServiceLocation().getAptNo() != null && !so.getServiceLocation().getStreet1().trim().equals(SOPDFConstants.BLANK) && !so.getServiceLocation().getStreet2().trim().equals(SOPDFConstants.BLANK) && !so.getServiceLocation().getAptNo().trim().equals(SOPDFConstants.BLANK)) {
				checkers[0] = new SOPDFUtils.Checker(so.getServiceLocation().getStreet1() + SOPDFConstants.COMMA + so.getServiceLocation().getStreet2() + SOPDFConstants.COMMA + so.getServiceLocation().getAptNo() + SOPDFConstants.COMMA).prependWith(SOPDFConstants.BLANK_SPACE, SOPDFConstants.BLANK_SPACE);
			} else if (so.getServiceLocation().getStreet1() != null && so.getServiceLocation().getAptNo() != null && !so.getServiceLocation().getStreet1().trim().equals(SOPDFConstants.BLANK) && !so.getServiceLocation().getAptNo().trim().equals(SOPDFConstants.BLANK)) {
				checkers[0] = new SOPDFUtils.Checker(so.getServiceLocation().getStreet1() + SOPDFConstants.COMMA + so.getServiceLocation().getAptNo() + SOPDFConstants.COMMA).prependWith(SOPDFConstants.BLANK_SPACE, SOPDFConstants.BLANK_SPACE);
			} else if (null != so.getServiceLocation().getStreet1() && null != so.getServiceLocation().getStreet2() && !so.getServiceLocation().getStreet1().trim().equals(SOPDFConstants.BLANK) && !so.getServiceLocation().getStreet2().trim().equals(SOPDFConstants.BLANK)){
				checkers[0] = new SOPDFUtils.Checker(so.getServiceLocation().getStreet1() + SOPDFConstants.COMMA + so.getServiceLocation().getStreet2() + SOPDFConstants.COMMA).prependWith(SOPDFConstants.BLANK_SPACE, SOPDFConstants.BLANK_SPACE);
			} else {
				checkers[0] = new SOPDFUtils.Checker(so.getServiceLocation().getStreet1() + SOPDFConstants.COMMA).prependWith(SOPDFConstants.BLANK_SPACE, SOPDFConstants.BLANK_SPACE);
			}

			String cityStateZip = null;
			if (so.getServiceLocation().getCity() != null && so.getServiceLocation().getState() != null && so.getServiceLocation().getZip() != null && !so.getServiceLocation().getCity().equals(SOPDFConstants.BLANK) && !so.getServiceLocation().getZip().equals(SOPDFConstants.BLANK)) {
				cityStateZip = so.getServiceLocation().getCity() + SOPDFConstants.COMMA + SOPDFConstants.SPACE + so.getServiceLocation().getState() + SOPDFConstants.SPACE + so.getServiceLocation().getZip();
			} else {
				cityStateZip = so.getServiceLocation().getState() + SOPDFConstants.SPACE + so.getServiceLocation().getZip();
			}
			serviceLocationList.add(new ListItem(checkers[0], SOPDFConstants.FONT_LABEL_MEDIUM));
			serviceLocationList.add(new ListItem(cityStateZip, SOPDFConstants.FONT_LABEL_MEDIUM));

			if(so.getServiceContact()!=null && so.getServiceContact().getPhoneNo()!=null && !so.getServiceContact().getPhoneNo().equals(SOPDFConstants.ZERO_PHONE)){
				serviceLocationList.add(new ListItem(new SOPDFUtils.Checker(SOPDFUtils.formatPhoneNumber(so
					.getServiceContact().getPhoneNo())).prependWith(SOPDFConstants.PRIMARY_PHONE,SOPDFConstants.BLANK_SPACE)
					, SOPDFConstants.FONT_LABEL_MEDIUM));
			}
			if(so.getServiceContactAlt()!=null && so.getServiceContactAlt().getPhoneNo()!=null && !so.getServiceContactAlt().getPhoneNo().equals(SOPDFConstants.ZERO_PHONE)){
				serviceLocationList.add(new ListItem(new SOPDFUtils.Checker(SOPDFUtils.formatPhoneNumber(so
					.getServiceContactAlt().getPhoneNo())).prependWith(SOPDFConstants.ALTERNATE_PHONENUMBER,SOPDFConstants.BLANK_SPACE)
					, SOPDFConstants.FONT_LABEL_MEDIUM));
			}
		}

		serviceLocationList.add(new ListItem(SOPDFConstants.BLANK, SOPDFConstants.FONT_LABEL_MEDIUM));

		PdfPCell cell = null;
		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.addElement(serviceLocationList);
		cell.setBorder(0);
		tableServiceLocation.addCell(cell);

		detailsTable.addCell(tableServiceLocation);
	}

	/**
	 * Method responsible for adding service date details
	 * 
	 * @param so
	 * @param descTable
	 */
	private void addServiceDateTable(PdfPTable detailsTable) {
		PdfPTable tableServiceDate = new PdfPTable(1);
		List serviceDateList = new List(false);
		serviceDateList.setListSymbol(SOPDFConstants.BLANK);
		serviceDateList.add(new ListItem(SOPDFConstants.SERVICE_EVENT, SOPDFConstants.FONT_HEADER_MEDIUM));

		serviceDateList.add(new ListItem(SOPDFUtils.appointmentDates(so), SOPDFConstants.FONT_LABEL_MEDIUM));
		serviceDateList.add(new ListItem(SOPDFUtils.serviceWindow(so), SOPDFConstants.FONT_LABEL_MEDIUM));

		serviceDateList.add(SOPDFConstants.NEW_LINE);
		
		serviceDateList.add(new ListItem(SOPDFConstants.ARRIVAL_TIME, SOPDFConstants.FONT_LABEL_MEDIUM));
		serviceDateList.add(new ListItem(SOPDFConstants.DEPARTURE_TIME, SOPDFConstants.FONT_LABEL_MEDIUM));
		serviceDateList.add(new ListItem(SOPDFConstants.SPACE, SOPDFConstants.FONT_LABEL_MEDIUM));
		
		PdfPCell cell = new PdfPCell();
		cell.addElement(serviceDateList);
		cell.setBorder(0);
		tableServiceDate.addCell(cell);

		detailsTable.addCell(tableServiceDate);
	}

	/**
	 * Method responsible for adding provider details
	 * 
	 * @param so
	 * @param descTable
	 */
	private void addProvider(PdfPTable detailsTable) {
		PdfPTable providerTable = new PdfPTable(1);
		List providerList = new List(false);
		providerList.setListSymbol(SOPDFConstants.SPACE);
		providerList.add(new ListItem(SOPDFConstants.SERVICE_PRO, SOPDFConstants.FONT_HEADER_MEDIUM));
		if(null != so.getAssignmentType() && so.getAssignmentType().equals(SOPDFConstants.ASSIGNMENT_TYPE_FIRM)){
			providerList.add(new ListItem(SOPDFConstants.PROVIDER_UNASSIGNED, SOPDFConstants.FONT_RED));
		}
		else if (so.getAcceptedResource() != null && so.getAcceptedResourceId() != null && so.getAcceptedResource().getResourceContact() != null && so.getAcceptedResource().getResourceContact().getFirstName() != null && so.getAcceptedResource().getResourceContact().getLastName() != null) {
			providerList.add(new ListItem(so.getAcceptedResource().getResourceContact().getFirstName() + " " + so.getAcceptedResource().getResourceContact().getLastName() + SOPDFConstants.SPACE + SOPDFConstants.OPENING_BRACE + SOPDFConstants.USER_ID_FOR_RI + so.getAcceptedResourceId() + SOPDFConstants.CLOSING_BRACE, SOPDFConstants.FONT_LABEL_MEDIUM));
		}
		if (so.getAcceptedVendorId() != null) {
			providerList.add(new ListItem(SOPDFConstants.COMPANY_ID_FOR_RI + so.getAcceptedVendorId(), SOPDFConstants.FONT_LABEL_MEDIUM));
			providerList.add(new ListItem(SOPDFConstants.SPACE, SOPDFConstants.FONT_LABEL_MEDIUM));
		}

		PdfPCell cell = null;
		cell = new PdfPCell();
		cell.addElement(providerList);
		cell.setBorder(0);
		providerTable.addCell(cell);
				
		
		if(so.getWfStateId().equals(SOPDFConstants.ACTIVE_STATUS) && mainPageTitle.equalsIgnoreCase(SOPDFConstants.CUSTOMER_COPY)){
			Image provImage = null;
			try{
				if((null != so.getAssignmentType() && SOPDFConstants.ASSIGNMENT_TYPE_PROVIDER.equals(so.getAssignmentType()))&& so.getAcceptedProviderDocument().getDocDetails()!=null && so.getAcceptedProviderDocument().getDocDetails().getBlobBytes()!=null && null != so.getAcceptedResourceId()){
					provImage = Image.getInstance(so.getAcceptedProviderDocument().getDocDetails().getBlobBytes());
				}else{
					provImage = Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_PROV_PICTURE_FILEPATH).getURL());
				}
									
			}catch(Exception ex){
				logger.error("Error occurred at PDF generation : ",ex);
			}
			PdfPCell provImageCell = new PdfPCell(provImage);
			provImageCell.setBorder(0);
			providerTable.addCell(provImageCell);
		}
		
		List newProviderList = new List(false);
		newProviderList.setListSymbol(SOPDFConstants.SPACE);
		if(mainPageTitle.equalsIgnoreCase(SOPDFConstants.PROVIDER_COPY) && so.getWfStateId()!= SOPDFConstants.POSTED_STATUS){
			newProviderList.add(new ListItem(SOPDFConstants.CALL_IVR_MSG_ON_CONTRACT_HDR, SOPDFConstants.FONT_HEADER_MEDIUM));
			newProviderList.add(new ListItem(SOPDFConstants.CALL_IVR_MSG_ON_CONTRACT_STEP1, SOPDFConstants.FONT_LABEL_MEDIUM));
			
			if(null != so.getAcceptedResourceId() && SOPDFConstants.ASSIGNMENT_TYPE_PROVIDER.equals(so.getAssignmentType())){
				newProviderList.add(new ListItem(SOPDFConstants.CALL_IVR_MSG_ON_CONTRACT_STEP2 + so.getAcceptedResourceId(), SOPDFConstants.FONT_LABEL_MEDIUM));
			}else{			
				ListItem callIVR = new ListItem();
				callIVR.setLeading(12);
				callIVR.add(new Phrase(SOPDFConstants.CALL_IVR_MSG_ON_CONTRACT_STEP2 , SOPDFConstants.FONT_LABEL_MEDIUM));
				callIVR.add(new Phrase(SOPDFConstants.UNASSIGNED, SOPDFConstants.FONT_RED));				
				newProviderList.add(callIVR);
			}			
			newProviderList.add(new ListItem(SOPDFConstants.CALL_IVR_MSG_ON_CONTRACT_STEP3 + so.getSoId(), SOPDFConstants.FONT_LABEL_MEDIUM));
			newProviderList.add(new ListItem(SOPDFConstants.CALL_IVR_MSG_ON_CONTRACT_STEP4, SOPDFConstants.FONT_LABEL_MEDIUM));
		}
		PdfPCell newCell = null;
		newCell = new PdfPCell();
		newCell.addElement(newProviderList);
		newCell.setBorder(0);
		providerTable.addCell(newCell);
		
		detailsTable.addCell(providerTable);
	}

	/**
	 * Method responsible for adding custom references to the so Details table
	 * 
	 * @param so
	 * @param descTable
	 */
	private void addCustomRef(PdfPTable detailsTable) {
		PdfPTable customerRef = new PdfPTable(1);
		List buyerCustomerRefList = new List(false);
		buyerCustomerRefList.setListSymbol(SOPDFConstants.SPACE);
		if (so.getCustomRefs() != null) {
			java.util.List<ServiceOrderCustomRefVO> customReflist = so.getCustomRefs();
			if (!customReflist.isEmpty()) {
				buyerCustomerRefList.add(new ListItem(SOPDFConstants.BUYER_CUSTOM_REF, SOPDFConstants.FONT_HEADER_MEDIUM));
				for(String customRef:SOPDFConstants.CUSTOM_REF_LIST){
					for (ServiceOrderCustomRefVO vo : customReflist){
						String refValue = vo.getRefValue();
						if(StringUtils.isNotBlank(refValue) && vo.getRefType().equals(customRef) && vo.getPdfRefInd().equals(SOPDFConstants.ONE) && !vo.isPrivateInd()){
							buyerCustomerRefList.add(new ListItem(SOPDFUtils.append(vo.getRefType(), refValue, SOPDFConstants.COLON + SOPDFConstants.SPACE), SOPDFConstants.FONT_LABEL_MEDIUM));	
					}
						
					}
				}
				if(mainPageTitle.equalsIgnoreCase(SOPDFConstants.PROVIDER_COPY)){
					buyerCustomerRefList.add(SOPDFConstants.NEW_LINE);
					for(String customRef:SOPDFConstants.CUSTOM_REF_LIST_FOR_PROV){
						for (ServiceOrderCustomRefVO vo : customReflist){
							String refValue = vo.getRefValue();
							if(StringUtils.isNotBlank(refValue) && vo.getRefType().equals(customRef) && vo.getPdfRefInd().equals(SOPDFConstants.ONE) && !vo.isPrivateInd()){
								buyerCustomerRefList.add(new ListItem(SOPDFUtils.append(vo.getRefType(), refValue, SOPDFConstants.COLON + SOPDFConstants.SPACE), SOPDFConstants.FONT_LABEL_MEDIUM));	
						}
							
						}
					}
				}
			}
		}
		PdfPCell cell = null;
		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.addElement(buyerCustomerRefList);
		cell.setBorder(0);
		customerRef.addCell(cell);
		detailsTable.addCell(customerRef);
	}

	/**
	 * Method responsible for adding product information to the so Details table
	 * @param detailsTable
	 */
	private void addProductInfo(PdfPTable detailsTable) {
		PdfPTable productInfo = new PdfPTable(1);
		List productInfoList = new List(false);
		productInfoList.setListSymbol(SOPDFConstants.SPACE);
		if (so.getParts() != null) {
			java.util.List<Part> parts = so.getParts();
			if (!parts.isEmpty()) {
				productInfoList.add(new ListItem(SOPDFConstants.PRODUCT_INFORMATION, SOPDFConstants.FONT_HEADER_MEDIUM));
				for (Part part : parts) {
					if (!StringUtils.isEmpty(part.getVendorPartNumber())) {
						String vendorPartNumber = part.getVendorPartNumber();
						Integer vendorPartNumberLength = vendorPartNumber.length();
						String division = vendorPartNumber.substring(0, 3);
						String itemNumber = vendorPartNumber.substring(3, vendorPartNumberLength);
						productInfoList.add(new ListItem(SOPDFConstants.DIVISION + division , SOPDFConstants.FONT_LABEL_MEDIUM));	
						productInfoList.add(new ListItem(SOPDFConstants.ITEM_NUMBER+ itemNumber , SOPDFConstants.FONT_LABEL_MEDIUM));	
					}
					if (!StringUtils.isEmpty(part.getManufacturer())){
						productInfoList.add(new ListItem(SOPDFConstants.PRODUCT_DESCRIPTION + part.getManufacturer(), SOPDFConstants.FONT_LABEL_MEDIUM));	
					}
				}
			}
		}
		PdfPCell cell = null;
		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.addElement(productInfoList);
		cell.setBorder(0);
		productInfo.addCell(cell);
		detailsTable.addCell(productInfo);
	}
	
	
	
	/**
	 * Method Responsible for setting the SO Description
	 * 
	 * @param so
	 * @param mainTable
	 * @throws DocumentException
	 */
	private PdfPTable createDescTable() {

		// Create right side details table
		PdfPTable descTable = new PdfPTable(1);
		descTable.setSplitLate(false);
		PdfPCell cell = null;
		cell = new PdfPCell(new Phrase(SOPDFConstants.SO_DESC, SOPDFConstants.FONT_HEADER));
		cell.setBorder(0);
		descTable.addCell(cell);
		return descTable;
	}

	/**
	 * Method responsible for adding task and parts list to the description
	 * table.
	 * 
	 * @param so
	 * @param descTable
	 * @throws DocumentException
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	private PdfPTable addTaskAndPartList(PdfPTable descTable, PdfPTable mainTable, PdfWriter pdfWriter, Document document) throws DocumentException, IOException {
		
		logger.info("RISODescriptionPDFTemplateImpl : addTaskAndPartList");
		List serviceTaskList = new List(false);
		List servicePartList = new List(false);
		Image checkBox = null;
		serviceTaskList.setListSymbol(SOPDFConstants.SPACE);
		servicePartList.setListSymbol(SOPDFConstants.SPACE);

		if (so.getSkill() != null && so.getSkill().getNodeName() != null) {
			serviceTaskList.add(new ListItem(SOPDFConstants.SERVICE_CATEGORY + so.getSkill().getNodeName(), SOPDFConstants.FONT_LABEL));
		}
		if (so.getSowTitle() != null) {
			serviceTaskList.add(new ListItem(SOPDFConstants.TITLE + so.getSowTitle(), SOPDFConstants.FONT_LABEL));
		}
		serviceTaskList.add(SOPDFConstants.NEW_LINE);
		
		//SL-20728
		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setColspan(1);
		cell.addElement(serviceTaskList);

		java.util.List<ServiceOrderTask> tasks = so.getTasks();
		if (tasks != null && !tasks.isEmpty()) {
			int taskCount = 0;
			for (ServiceOrderTask task : tasks) {
				//SL-20728
				serviceTaskList = new List(false);
				serviceTaskList.setListSymbol(SOPDFConstants.SPACE);
				++taskCount;
				if (!StringUtils.isEmpty(task.getTaskName())) {
					if(task.getTaskType()==1){
						serviceTaskList.add(new ListItem(SOPDFConstants.TASK + taskCount + SOPDFConstants.COLON + SOPDFConstants.SPACE + task.getTaskName()+SOPDFConstants.BLANK_SPACE_LONG + SOPDFConstants.PERMIT_PRICE_TEXT+SOPDFConstants.SPACE+SOPDFConstants.DOLLAR+SOPDFConstants.SPACE+task.getSellingPrice(),SOPDFConstants.FONT_LABEL));
					}
					else{
						serviceTaskList.add(new ListItem(SOPDFConstants.TASK + taskCount + SOPDFConstants.COLON +SOPDFConstants.SPACE + task.getTaskName(),SOPDFConstants.FONT_LABEL));
					}
				}
				if (!StringUtils.isEmpty(task.getTaskComments())) {
					List taskCommentsList=new List(false);
					try{
						checkBox = Image.getInstance(new ClassPathResource(SOPDFConstants.CHECKBOX_PICTURE_FILEPATH).getURL());
						taskCommentsList.setListSymbol(new Chunk(checkBox,0,-2));
					}
					catch(Exception ex){
						logger.error("Error occurred while retrieving image : ",ex);
					}
					String taskComments = task.getTaskComments();
					Integer commentsLength = taskComments.length();
					String description = so.getSowDs();
					
					//SL-20728
					boolean plainText = true;
					//Check whether the taskComments is having any html tags
					if(SOPDFUtils.hasHTMLTags(taskComments)){
						
						List taskCommentOrg = null;
						try{
							//replace html character entities
							taskComments = SOPDFUtils.getHtmlContent(taskComments);	
							
							//If task comments is not having any li tags, split the string into different lines at '.'
							if(!taskComments.contains(SOPDFConstants.LI_END_TAG)){
								taskComments = SOPDFUtils.getTaskComments(taskComments);
							}
							//Convert the html content to rich text
							taskCommentOrg = SOPDFUtils.getRichText(taskComments, SOPDFConstants.FONT_LABEL, checkBox, SOPDFConstants.SPACE);
						}
						catch(Exception e){
							logger.error("Error while converting rich text for Task Comments " + e);
							taskCommentOrg = null;
						}
						
						if(null != taskCommentOrg && !taskCommentOrg.isEmpty()){
							plainText = false;
							taskCommentsList.add(taskCommentOrg);
							serviceTaskList.add(taskCommentsList);
							if (description.contains(SOPDFConstants.NOTICE_TO_CUSTOMER_TITLE) && taskCount == SOPDFConstants.TASK_COUNT_ONE){
								//SL-20728
								cell.addElement(serviceTaskList);
								serviceTaskList = new List(false);
								serviceTaskList.setListSymbol(SOPDFConstants.SPACE);
								serviceTaskList.add(SOPDFConstants.NEW_LINE);
								serviceTaskList.add(new ListItem(SOPDFConstants.NOTICE_TO_CUSTOMER, SOPDFConstants.FONT_LABEL));
							}
						}
						
					}
					//If it does not contain any html tags or if any exception occurs, use the existing code
					if(plainText){
						taskComments = task.getTaskComments();
						//Remove html tags
						taskComments = ServiceLiveStringUtils.removeHTML(taskComments);
						commentsLength = taskComments.length();
						if (taskComments.contains(SOPDFConstants.HYPHEN)){
							Integer start = taskComments.indexOf(SOPDFConstants.HYPHEN);
							String comments = taskComments.substring(start+1, commentsLength);
							String comment = SOPDFConstants.SPACE.concat(comments);
							String[] soComments =  comment.split(SOPDFConstants.PERIOD); 
							for(int i=0;i<soComments.length;i++){
								taskCommentsList.add(new ListItem(soComments[i].concat(SOPDFConstants.END_OF_LINE), SOPDFConstants.FONT_LABEL));
							}
							serviceTaskList.add(taskCommentsList);
							if (description.contains(SOPDFConstants.NOTICE_TO_CUSTOMER_TITLE) && taskCount == SOPDFConstants.TASK_COUNT_ONE){
								serviceTaskList.add(SOPDFConstants.NEW_LINE);
								serviceTaskList.add(new ListItem(SOPDFConstants.NOTICE_TO_CUSTOMER, SOPDFConstants.FONT_LABEL));
							}
						}
						else{
							String taskComment =  SOPDFConstants.SPACE.concat(taskComments);
							String[] soComments =  taskComment.split(SOPDFConstants.PERIOD); 
							for(int i=0;i<soComments.length;i++){
								taskCommentsList.add(new ListItem(soComments[i].concat(SOPDFConstants.END_OF_LINE), SOPDFConstants.FONT_LABEL));
							}
							serviceTaskList.add(taskCommentsList);
							if (description.contains(SOPDFConstants.NOTICE_TO_CUSTOMER_TITLE) && taskCount == 1){
								serviceTaskList.add(SOPDFConstants.NEW_LINE);
								serviceTaskList.add(new ListItem(SOPDFConstants.NOTICE_TO_CUSTOMER, SOPDFConstants.FONT_LABEL));
							}
						}
					}
				}
				//SL-20728
				cell.addElement(serviceTaskList);
				serviceTaskList = new List(false);
				serviceTaskList.setListSymbol(SOPDFConstants.SPACE);
				serviceTaskList.add(SOPDFConstants.NEW_LINE);
				cell.addElement(serviceTaskList);
			}
		}

		if (so.getParts() != null && (mainPageTitle.equalsIgnoreCase(SOPDFConstants.PROVIDER_COPY))) {
			java.util.List<Part> parts = so.getParts();
			String[] checkers = new String[10];
			if (!parts.isEmpty()) {
				servicePartList.add(new ListItem(SOPDFConstants.PARTS_AND_MATERIALS, SOPDFConstants.FONT_HEADER));
				for (Part part : parts) {
					if (!StringUtils.isEmpty(part.getQuantity())) {
						checkers[0] = new SOPDFUtils.Checker(part.getQuantity()).prependWith(SOPDFConstants.COMMA + SOPDFConstants.QUANTITY_STR, SOPDFConstants.COLON);
					}
					if (!StringUtils.isEmpty(part.getWeight())) {
						checkers[0] = checkers[0] + new SOPDFUtils.Checker(part.getWeight()).prependWith(SOPDFConstants.COMMA + SOPDFConstants.WEIGHT, SOPDFConstants.COLON);
					}
					if (!StringUtils.isEmpty(part.getModelNumber())) {
						servicePartList.add(new ListItem(part.getManufacturer() + SOPDFConstants.COMMA + part.getModelNumber() + SOPDFConstants.COMMA + part.getPartDs() + checkers[0], SOPDFConstants.FONT_LABEL));
					}
				}

				for (Part part : parts) {
					if (part.getShippingCarrier() != null || part.getReturnCarrier() != null) {
						servicePartList.add(SOPDFConstants.NEW_LINE);
						servicePartList.add(new ListItem(SOPDFConstants.ADDITIONAL_PARTS_INST, SOPDFConstants.FONT_LABEL));
						break;
					}
				}
				for (Part part : parts) {
					if (part.getShippingCarrier() != null && part.getReturnCarrier() != null) {
						servicePartList.add(new ListItem(part.getShippingCarrier().getCarrierName() + SOPDFConstants.COMMA + part.getShippingCarrier().getTrackingNumber() + SOPDFConstants.COMMA + part.getReturnCarrier().getCarrierName() + SOPDFConstants.COMMA + part.getReturnCarrier().getTrackingNumber(), SOPDFConstants.FONT_LABEL));
					}
				}
			}
		}
		
		cell.addElement(servicePartList);
		descTable.addCell(cell);
		mainTable.addCell(descTable);
		return mainTable;
	}

	/**
	 * Method responsible for setting notes to the description table
	 * 
	 * @param so
	 * @param mainTable
	 */
	private void addNotesTable(PdfPTable mainTable) {

		PdfPTable tableNotes = new PdfPTable(1);
		PdfPCell headingCell = new PdfPCell(new Phrase(SOPDFConstants.ADD_WORK_PERFORMED, SOPDFConstants.FONT_HEADER));
		headingCell.setBorder(0);
		tableNotes.getDefaultCell().setBorder(0);
		tableNotes.addCell(headingCell);
		
		// SPM-1340 : Resolution Comments to be appended in PDF
		// Including Resolution Comments in "ADDITIONAL WORK PERFORMED / SERVICE PROVIDER NOTES:" in both Customer copy and Provider copy.
		PdfPCell boxCell;
		if(mobileIndicator){
			boxCell = new PdfPCell(new Phrase(so.getResolutionDs(), SOPDFConstants.FONT_LABEL));
		}
		else{
			boxCell = new PdfPCell();
		}
		boxCell.setBorder(0);
		boxCell.setMinimumHeight(50);
		tableNotes.addCell(boxCell);

		PdfPCell notesTableCell = new PdfPCell(tableNotes);
		notesTableCell.setColspan(2);
		notesTableCell.setBorder(0);
		notesTableCell.setBorderWidthBottom(1);
		notesTableCell.setBorderWidthTop(1);
		notesTableCell.setBorderWidthLeft(1);
		notesTableCell.setBorderWidthRight(1);
		mainTable.addCell(notesTableCell);
	}

	/**
	 * Method responsible for setting the waiver list to the SO Description
	 * Table
	 * 
	 * @param so
	 * @param mainTable
	 */
	private void addWaiverList(PdfPTable mainTable, PdfWriter pdfWriter, Document document) throws DocumentException, IOException {

		List waiverList = new List(false);
		waiverList.setListSymbol(SOPDFConstants.SPACE);
		if(isMobileIndicator()){
			waiverList.add(new ListItem(SOPDFConstants.WAIVER_HEADER_SIGNED, SOPDFConstants.FONT_LABEL));
		}else{
			waiverList.add(new ListItem(SOPDFConstants.WAIVER_HEADER, SOPDFConstants.FONT_LABEL));
		}
		//waiverList.add(new ListItem(SOPDFConstants.WAIVER_TEXT, SOPDFConstants.FONT_LABEL_SMALL));
		//Increased the size of font for waiver Text
		waiverList.add(new ListItem(SOPDFConstants.WAIVER_TEXT, SOPDFConstants.FONT_LABEL));
		waiverList.add(new ListItem(SOPDFConstants.NEW_LINE_FEED, SOPDFConstants.FONT_LABEL));
		
		PdfPCell waiverTableCell1 = addPdfCellForWaiver(waiverList);
		
		PdfPTable testTable = new PdfPTable(1);

		PdfPCell waiverTableCell2 = null; 
		if(null != providerSignature && null != providerSignatureImage){
			waiverTableCell2 = addSignatures(providerSignature, providerSignatureImage);
		}
		
		waiverList = new List(false);
		waiverList.setListSymbol(SOPDFConstants.SPACE);
		waiverList.add(new ListItem(SOPDFConstants.WAIVER_SIGNATURE, SOPDFConstants.FONT_LABEL));
		/*Moved customer signature to WAIVER OF LIEN to keep it together*/
		waiverList.add(new ListItem(SOPDFConstants.NEW_LINE_FEED, SOPDFConstants.FONT_LABEL));
		waiverList.add(new ListItem(SOPDFConstants.RI_SIGNATURE_TEXT_ONE, SOPDFConstants.FONT_LABEL));
		waiverList.add(new ListItem(SOPDFConstants.RI_SIGNATURE_TEXT_TWO, SOPDFConstants.FONT_LABEL));
		
		PdfPCell waiverTableCell3 = addPdfCellForWaiver(waiverList);
		
		PdfPCell waiverTableCell4 = null;
		if(null != customerSignature && null != customerSignatureImage){
			waiverTableCell4 = addSignatures(customerSignature, customerSignatureImage);
		}
		
		waiverList = new List(false);
		waiverList.setListSymbol(SOPDFConstants.SPACE);
		waiverList.add(new ListItem(SOPDFConstants.SIGNATURE_TEXT_TWO, SOPDFConstants.FONT_LABEL));
		waiverList.add(new ListItem(SOPDFConstants.NEW_LINE_FEED, SOPDFConstants.FONT_LABEL));
		waiverList.add(new ListItem(SOPDFConstants.THANK_YOU_STRING_CENTER, SOPDFConstants.FONT_LABEL_BIG));
		waiverList.add(new ListItem(SOPDFConstants.NEW_LINE_FEED, SOPDFConstants.FONT_LABEL));
		
		//SL-20707: Commenting the below line for removing the phone number inorder to support RI - COSCO orders
		//SL-20716: UnCommenting one of the below lines and changing the verbiage as per the JIRA
		if(mainPageTitle.equalsIgnoreCase(SOPDFConstants.CUSTOMER_COPY)){
			//waiverList.add(new ListItem(SOPDFConstants.QUESTIONS_AND_CONCERNS_CENTER,SOPDFConstants.FONT_HEADER));
			waiverList.add(new ListItem(SOPDFConstants.QUESTIONS_AND_CONCERNS_CONTD_CENTER,SOPDFConstants.FONT_LABEL));
			waiverList.add(new ListItem(SOPDFConstants.QUESTIONS_AND_CONCERNS_CONTD_CENTER_NEXT,SOPDFConstants.FONT_LABEL));
			//SL-20807
			//waiverList.add(new ListItem(SOPDFConstants.SURVEY_MESSAGE1,SOPDFConstants.FONT_LABEL));
			//waiverList.add(new ListItem(SOPDFConstants.SURVEY_MESSAGE2,SOPDFConstants.FONT_LABEL));
			//waiverList.add(new ListItem(SOPDFConstants.SURVEY_MESSAGE3,SOPDFConstants.FONT_LABEL));
		}
		
		PdfPCell waiverTableCell5 = addPdfCellForWaiver(waiverList);
		
		testTable.addCell(waiverTableCell1);
		if(null != waiverTableCell2)
			testTable.addCell(waiverTableCell2);
		testTable.addCell(waiverTableCell3);
		if(null != waiverTableCell4)
			testTable.addCell(waiverTableCell4);
		testTable.addCell(waiverTableCell5);
		if(!pdfWriter.fitsPage(testTable)){
			addHeaderTable(mainTable, pdfWriter,false);
		}
		
		mainTable.addCell(waiverTableCell1);
		if(null != waiverTableCell2)
			mainTable.addCell(waiverTableCell2);
		mainTable.addCell(waiverTableCell3);
		if(null != waiverTableCell4)
			mainTable.addCell(waiverTableCell4);
		mainTable.addCell(waiverTableCell5);
		mainTable.setKeepTogether(true);
				
		document.add(mainTable);
	}
	
	private PdfPCell addPdfCellForWaiver(List waiverList){
		PdfPCell waiverListCell = new PdfPCell();
		waiverListCell.setBorder(0);
		waiverListCell.addElement(waiverList);

		PdfPTable waiverTable = new PdfPTable(1);
		waiverTable.setWidthPercentage(100);
		waiverTable.addCell(waiverListCell);
		waiverTable.setKeepTogether(true);

		PdfPCell waiverTableCell = new PdfPCell();
		waiverTableCell.setColspan(2);
		waiverTableCell.setBorder(0);
		waiverTableCell.addElement(waiverTable);
		
		return waiverTableCell;
	}
	
	private PdfPCell addSignatures(Signature signature, Image signatureImage) throws IOException{
		/** Name */
		PdfPCell signImageCell1 = new PdfPCell();
		List waiverList2 = new List(false);
		waiverList2.setListSymbol(SOPDFConstants.BIG_SPACE
				+ SOPDFConstants.BIG_SPACE);
		waiverList2.add(new ListItem(signature.getName(), SOPDFConstants.FONT_LABEL));
		signImageCell1.addElement(waiverList2);
		signImageCell1.setBorder(0);
		signImageCell1.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
		signImageCell1.setPaddingLeft(25f);
		
		/** Image */
		PdfPCell signImageCell2 = new PdfPCell(signatureImage);
		signImageCell2.setBorder(0);
		signImageCell2.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
		
		/** Date */
		PdfPCell signImageCell3 = new PdfPCell();
		signImageCell3.setBorder(0);
		List waiverList3 = new List(false);
		waiverList3.setListSymbol(SOPDFConstants.SPACE);
		waiverList3.add(new ListItem(signature.getCurrentDate(), SOPDFConstants.FONT_LABEL));
		signImageCell3.addElement(waiverList3);
		signImageCell3.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

		PdfPTable waiverTable = new PdfPTable(3);
		waiverTable.setWidthPercentage(98);
		waiverTable.setSpacingAfter(0f);
	
		waiverTable.addCell(signImageCell1);
		waiverTable.addCell(signImageCell2);
		waiverTable.addCell(signImageCell3);
		waiverTable.setKeepTogether(true);

		PdfPCell waiverTableCell = new PdfPCell();
		waiverTableCell.setColspan(2);
		waiverTableCell.setBorder(0);
		waiverTableCell.addElement(waiverTable);
		
		return waiverTableCell;
	}

	/**
	 * Method responsible for setting signatures to the SO Description table
	 * 
	 * @param so
	 * @param soDescTable
	 */
	/* Moved this code to WAIVER OF LIEN to keep it together
	private void addSignaturesTable(PdfPTable soDescTable, PdfWriter pdfWriter, Document document) throws DocumentException, IOException {
		PdfPTable tableSignatures = new PdfPTable(1);
		List signList = new List(false);
		signList.setListSymbol(SOPDFConstants.SPACE);
		tableSignatures.setWidthPercentage(100);
		signList.add(new ListItem(SOPDFConstants.RI_SIGNATURE_TEXT_ONE, SOPDFConstants.FONT_LABEL));
		signList.add(new ListItem(SOPDFConstants.RI_SIGNATURE_TEXT_TWO, SOPDFConstants.FONT_LABEL));
		signList.add(new ListItem(SOPDFConstants.SIGNATURE_TEXT_TWO, SOPDFConstants.FONT_LABEL));

		PdfPCell cell = null;
		cell = new PdfPCell();
		cell.addElement(signList);
		cell.setBorder(0);
		tableSignatures.addCell(cell);
		tableSignatures.setKeepTogether(true);
		
		cell = new PdfPCell();
		cell.setColspan(3);
		cell.setBorder(0);
		cell.addElement(tableSignatures);
		soDescTable.setKeepTogether(true);
		PdfPTable testTable = new PdfPTable(1);
		testTable.addCell(cell);
		if(!pdfWriter.fitsPage(testTable)){
			addHeaderTable(soDescTable, pdfWriter,false);
		}
		soDescTable.addCell(cell);
		document.add(soDescTable);

	}
   */
	/**
	 * Method responsible for setting signatures to the SO Description table
	 * 
	 * @param so
	 * @param soDescTable
	 */
	/* Moved this code to WAIVER OF LIEN to keep it together*/
	/*
	private void addQuestionsText(PdfPTable soDescTable, PdfWriter pdfWriter, Document document) throws DocumentException, IOException {
		PdfPTable tableSignatures = new PdfPTable(1);
		tableSignatures.setWidthPercentage(100);
		
		PdfPCell cell = new PdfPCell(new Phrase(SOPDFConstants.THANK_YOU_STRING, SOPDFConstants.FONT_LABEL_BIG));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(0);
		cell.setColspan(3);
		tableSignatures.addCell(cell);
		PdfPCell cell = null;
		if(mainPageTitle.equalsIgnoreCase(SOPDFConstants.CUSTOMER_COPY)){
			PdfPCell cell1 = null;
			cell1 =  new PdfPCell(new Phrase(SOPDFConstants.QUESTIONS_AND_CONCERNS,SOPDFConstants.FONT_HEADER));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setBorder(0);
			cell1.setColspan(3);
			tableSignatures.addCell(cell1);
			
			PdfPCell questionscontd = null;
			questionscontd =  new PdfPCell(new Phrase(SOPDFConstants.QUESTIONS_AND_CONCERNS_CONTD,SOPDFConstants.FONT_LABEL));
			questionscontd.setHorizontalAlignment(Element.ALIGN_CENTER);
			questionscontd.setBorder(0);
			questionscontd.setColspan(3);
			tableSignatures.addCell(questionscontd);	
		}
		tableSignatures.setKeepTogether(true);
		cell = new PdfPCell();
		cell.setColspan(3);
		cell.setBorder(0);
		cell.addElement(tableSignatures);
		PdfPTable testTable = new PdfPTable(1);
		testTable.addCell(cell);
		if(!pdfWriter.fitsPage(testTable)){
			addHeaderTable(soDescTable, pdfWriter,false);
		}
		soDescTable.setKeepTogether(true);
		soDescTable.addCell(cell);
		document.add(soDescTable);
	}
    */
	public Image getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(Image logoImage) {
		this.logoImage = logoImage;
	}

	public ServiceOrder getSo() {
		return so;
	}

	public void setSo(ServiceOrder so) {
		this.so = so;
	}

	public String getMainPageTitle() {
		return mainPageTitle;
	}

	public void setMainPageTitle(String mainPageTitle) {
		this.mainPageTitle = mainPageTitle;
	}

	public Signature getCustomerSignature() {
		return customerSignature;
	}

	public void setCustomerSignature(Signature customerSignature) {
		this.customerSignature = customerSignature;
	}

	public Image getCustomerSignatureImage() {
		return customerSignatureImage;
	}

	public void setCustomerSignatureImage(Image customerSignatureImage) {
		this.customerSignatureImage = customerSignatureImage;
	}

	public Signature getProviderSignature() {
		return providerSignature;
	}

	public void setProviderSignature(Signature providerSignature) {
		this.providerSignature = providerSignature;
	}

	public Image getProviderSignatureImage() {
		return providerSignatureImage;
	}

	public void setProviderSignatureImage(Image providerSignatureImage) {
		this.providerSignatureImage = providerSignatureImage;
	}

	public boolean isMobileIndicator() {
		return mobileIndicator;
	}

	public void setMobileIndicator(boolean mobileIndicator) {
		this.mobileIndicator = mobileIndicator;
	}
}
