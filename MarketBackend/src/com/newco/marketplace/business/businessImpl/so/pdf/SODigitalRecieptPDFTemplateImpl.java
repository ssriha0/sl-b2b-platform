package com.newco.marketplace.business.businessImpl.so.pdf;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import com.lowagie.text.BadElementException;
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
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Signature;
import com.newco.marketplace.dto.vo.serviceorder.ProviderInvoicePartsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.constants.SOPDFConstants;



/**
 * $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/03/19
 *  Class responsible for generating the Customer Digital Reciept of PDF
 */
public class SODigitalRecieptPDFTemplateImpl implements PDFTemplate {

	private Image logoImage;
	private ServiceOrder so;
	private static final Logger logger = Logger.getLogger(PDFGenerator.class);
	
	private Signature customerSignature;
	private Image customerSignatureImage;
	private Signature providerSignature;
	private Image providerSignatureImage;
	private boolean mobileIndicator = false;
  //private int x=-3;
  //private int y=-3;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.util.PDFTemplate#execute(java.lang.Boolean,
	 *      com.newco.marketplace.dto.vo.serviceorder.ServiceOrder,
	 *      java.util.List, com.lowagie.text.Image)
	 */
	public void execute(PdfWriter pdfWriter, Document document) throws DocumentException, IOException {

		PdfPTable headerWithBarcode = createNewHeaderTable();
		addHeaderTable(headerWithBarcode, pdfWriter,true);
		document.add(headerWithBarcode);
		
		PdfPTable mainTable =createNewMainTable();
		addSoSummaryTables(mainTable);
		addAddInfo(mainTable);
		document.add(mainTable);
		
		mainTable =createNewMainTable();
		PdfPTable descTable = createNewTable();
		addTaskAndPartList(descTable, mainTable, pdfWriter, document);
		
		PdfPTable notesTable = createNewMainTable();
		addNotesTable(notesTable, document);

		PdfPTable waiversTable = createNewMainTable();
		addWaiverList(waiversTable,document);
		
		PdfPTable customerAgreementTable = createNewMainTable();
		addCustomerAgreement(customerAgreementTable,document);
		
		
	}
	
	private PdfPTable createNewHeaderTable() {
		float[] widths = {10,60};
		PdfPTable mainTable = new PdfPTable(widths);
		mainTable.setSplitLate(false);
		mainTable.getDefaultCell().setBorder(0);
		float pageWidth = PageSize.LETTER.getWidth();
		mainTable.setTotalWidth(pageWidth - 30);
		mainTable.setLockedWidth(true);
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
		headerTable.setTotalWidth(pageWidth -30);
		headerTable.setLockedWidth(true);

		// Add logo on left corner
		if(null!=logoImage){
			SOPDFUtils.addLogoImage(headerTable, logoImage);
		}

		List serviceHeaderList = new List(false);
		serviceHeaderList.setListSymbol(SOPDFConstants.SPACE);
		ListItem item2 = new ListItem();
		item2.setFont(SOPDFConstants.FONT_LABEL);
		if(SOPDFConstants.BUYER_INHOME_ID == so.getBuyerId().intValue()){
			item2.add(new Paragraph(SOPDFConstants.SEARS_HOME_SERVICES, SOPDFConstants.FONT_LABEL_BOLD));
		}
		else{
			item2.add(new Paragraph(SOPDFConstants.BIG_SPACE, SOPDFConstants.FONT_LABEL_BOLD));

		}
		
		item2.add(new Paragraph(SOPDFConstants.BIG_SPACE+SOPDFConstants.SERVICE_ORDER_NUMBER + so.getSoId(), SOPDFConstants.FONT_LABEL));
		
		serviceHeaderList.add(item2);
		// Add soId in center
		PdfPCell soIdCell = new PdfPCell();
		soIdCell.setBorder(0);
		PdfPTable centerSoId = new PdfPTable(1);
		centerSoId.setHorizontalAlignment(Element.ALIGN_LEFT);
		soIdCell.addElement(serviceHeaderList);
		centerSoId.addCell(soIdCell);
		headerTable.addCell(centerSoId);

		PdfPCell titleCell = new PdfPCell(new Paragraph(SOPDFConstants.SPACE));
		titleCell.setBorder(0);
		titleCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		PdfPTable rightTitle = new PdfPTable(1);
		rightTitle.setHorizontalAlignment(Element.ALIGN_LEFT);
		rightTitle.addCell(titleCell);
		headerTable.addCell(rightTitle);
		// headerTable.addCell(rightTitle);

		// Now, add entire heading row to main table
		PdfPCell headingCell = new PdfPCell();
		headingCell.setBorder(0);
		headingCell.setColspan(4);
		headingCell.addElement(headerTable);
		mainTable.addCell(headingCell);
	}

	public SODigitalRecieptPDFTemplateImpl() {
		super();
	}
	
	public SODigitalRecieptPDFTemplateImpl(boolean mobileIndicator) {
		super();
		this.mobileIndicator = mobileIndicator;
	}
	
    
	private PdfPTable createNewMainTable() {
		float pageWidth = PageSize.LETTER.getWidth();
		float[] mainTableWidths = { 160, 160, 160, 160 };
		PdfPTable mainTable = new PdfPTable(mainTableWidths);
		mainTable.setSplitLate(false);
		mainTable.getDefaultCell().setBorder(0);
		mainTable.setHorizontalAlignment(Element.ALIGN_CENTER);
		mainTable.setTotalWidth(pageWidth - 30);
		mainTable.setLockedWidth(true);
		mainTable.getDefaultCell().setLeading(8, 0);
		return mainTable;
	}
	
	/**
	 * @return
	 */
	private PdfPTable createNewTable() {

		PdfPTable descTable = new PdfPTable(4);
		descTable.setSplitLate(false);
		descTable.getDefaultCell().setLeading(8, 0);
		return descTable;
	
	}
	
	/**
	 * @param mainTable
	 * 
	 * to add soSummary(SLM-118)
	 */
	private void addSoSummaryTables(PdfPTable mainTable){
		addServiceDateTable(mainTable);
		addProvider(mainTable);
		addServiceLocationTable(mainTable);
		addContactUsTable(mainTable);
	}
	
	
	/**
	 * Method to add alt buyer information to buyer information table to PDF
	 * @param so
	 * @param buyerInfoList
	 */
	private void addContactUsTable(PdfPTable mainTable){
		PdfPTable contactUsInfo = new PdfPTable(1);
		List  contactUsInfoList = new List(false);
		try{ 
			
				Image imagePhone =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_PHONE_ICON_FILEPATH).getURL());
				 
			ListItem item =new ListItem();
			item.setFont(SOPDFConstants.FONT_LABEL_BIG);
			item.setListSymbol(new Chunk(imagePhone,0, 0));
			item.add(SOPDFConstants.SPACE+SOPDFConstants.CONTACT_US);
			contactUsInfoList.add(item);	
			item = new ListItem();
			contactUsInfoList.setListSymbol(SOPDFConstants.MEDIUM_SPACE);
			if(SOPDFConstants.BUYER_INHOME_ID == so.getBuyerId().intValue()){
				item.add(new Phrase(SOPDFConstants.MYHOME_CONTACT_US,SOPDFConstants.FONT_LABEL));
			}
			else if(SOPDFConstants.SEARS_BUYER == so.getBuyerId().intValue()){
				item.add(new Phrase(SOPDFConstants.SEARS_CONTACT_US,SOPDFConstants.FONT_LABEL));
			}
			else{
				if(null != so.getBuyer() && 
						null != so.getBuyer().getBuyerContact() && StringUtils.isNotBlank(so.getBuyer().getBuyerContact().getPhoneNo())){
					item.add(new Phrase(SOPDFUtils.formatPhoneNumber(so.getBuyer().getBuyerContact().getPhoneNo()),SOPDFConstants.FONT_LABEL));
				}
			}
			contactUsInfoList.add(item);	
			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.addElement(contactUsInfoList);
			contactUsInfo.getDefaultCell().setBorder(0);
			contactUsInfo.addCell(cell);
			mainTable.addCell(contactUsInfo);
		}
		catch (BadElementException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);
		}
	catch (MalformedURLException e) {
		logger.error("Unexpected error while generating Service Order PDF", e);
		
	} catch (IOException e) {
		logger.error("Unexpected error while generating Service Order PDF", e);
	}
		catch (Exception e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} 
		

	}


	
	
	/**
	 * Method for adding buyer custom refs etc to the PDF
	 * 
	 * @param mainTable
	 */
	private void addAddInfo(PdfPTable mainTable) {

		PdfPCell addInfoCell = new PdfPCell();
		addInfoCell.setBorder(0);
		List addInfoList = new List(false);
		addInfoList = addCustomRef();
		addInfoCell.addElement(addInfoList);

		PdfPTable addInfoTable = new PdfPTable(1);
		addInfoTable.setWidthPercentage(100);
		addInfoTable.addCell(addInfoCell);

		PdfPCell addInfoTableCell = new PdfPCell();
		addInfoTableCell.setColspan(4);
		addInfoTableCell.setBorder(0);
		addInfoTableCell.addElement(addInfoTable);
		mainTable.addCell(addInfoTableCell);

	}
	
	
	

	/**
	 * @param detailsTable
	 * 
	 * Method responsible for adding location details to PDF

	 */
	private void addServiceLocationTable(PdfPTable detailsTable) {
		PdfPTable tableServiceLocation = new PdfPTable(1);
		List serviceLocationList = new List(false);
		Image image=null;
			try {
				image =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_HOME_ICON_FILEPATH).getURL());
				image.setAbsolutePosition(100, 100);
				serviceLocationList.setListSymbol(new Chunk(image,0, 0));

			} catch (BadElementException e) {
				logger.error("Unexpected error while generating Service Order PDF", e);

			} catch (MalformedURLException e) {
				logger.error("Unexpected error while generating Service Order PDF", e);

			} catch (IOException e) {
				logger.error("Unexpected error while generating Service Order PDF", e);

			}

			ListItem item =new ListItem();
			item.add(SOPDFConstants.SPACE);
			item.setFont(SOPDFConstants.FONT_LABEL_BIG);

			item.add(SOPDFConstants.SERVICE_LOCATION);
			serviceLocationList.add(item);
			serviceLocationList.setListSymbol(SOPDFConstants.BIG_SPACE);

		if (so.getServiceLocation() != null) {
			
			if (so.getServiceContact() != null && so.getServiceContact().getFirstName() != null && so.getServiceContact().getLastName() != null) {
					serviceLocationList.add(new ListItem(so.getServiceContact().getLastName() + SOPDFConstants.COMMA + SOPDFConstants.SPACE + so.getServiceContact().getFirstName(), SOPDFConstants.FONT_LABEL));
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
			serviceLocationList.add(new ListItem(checkers[0], SOPDFConstants.FONT_LABEL));
			serviceLocationList.add(new ListItem(cityStateZip, SOPDFConstants.FONT_LABEL));
			boolean phoneAdded = false;
			item =new ListItem();
			Image imagePhone =null;
			
			//serviceLocationList.setListSymbol(SOPDFConstants.BIG_SPACE);
			
			if (so.getServiceContact() != null && StringUtils.isNotBlank(so.getServiceContact().getPhoneNo()) && !so.getServiceContact().getPhoneNo().equals(SOPDFConstants.ZERO_PHONE)) {
				
				try {
					try {
						imagePhone =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_PHONE_ICON_FILEPATH).getURL());
					} catch (BadElementException e) {
						logger.error("Unexpected error while generating Service Order PDF", e);
					}
				} catch (MalformedURLException e) {
					logger.error("Unexpected error while generating Service Order PDF", e);
					
				} catch (IOException e) {
					logger.error("Unexpected error while generating Service Order PDF", e);
				}
				serviceLocationList.setListSymbol(new Chunk(imagePhone,0, -2));
				item = new ListItem();
					
					item.add(new Phrase(SOPDFConstants.SPACE+SOPDFConstants.SPACE+SOPDFUtils.formatPhoneNumber(so.getServiceContact().getPhoneNo()), SOPDFConstants.FONT_LABEL_BOLD));
					addPhoneType(item,so.getServiceContact().getPhoneClassId());
					serviceLocationList.add(item);
					serviceLocationList.setListSymbol(SOPDFConstants.SMALL_SPACE);
					phoneAdded = true;
				
			
			}
			if (so.getServiceContactAlt() != null && StringUtils.isNotBlank(so.getServiceContactAlt().getPhoneNo())  && !so.getServiceContactAlt().getPhoneNo().equals(SOPDFConstants.ZERO_PHONE)) {
				if(phoneAdded){
					item =new ListItem();
					item.add(SOPDFConstants.SPACE);
					item.add(new Phrase(SOPDFConstants.SPACE+SOPDFUtils.formatPhoneNumber(so.getServiceContactAlt().getPhoneNo()), SOPDFConstants.FONT_LABEL));
				}else{
					item.add(new Phrase(SOPDFConstants.SPACE+SOPDFConstants.SPACE+SOPDFUtils.formatPhoneNumber(so.getServiceContactAlt().getPhoneNo()), SOPDFConstants.FONT_LABEL));
				}
				
				addPhoneType(item,so.getServiceContactAlt().getPhoneClassId());
				serviceLocationList.add(item);
			}
		}
		serviceLocationList.setListSymbol("");
		serviceLocationList.add(new ListItem(SOPDFConstants.BLANK, SOPDFConstants.FONT_LABEL));
		SOPDFUtils.addCellToTable(serviceLocationList, 0, -1, 40, detailsTable,tableServiceLocation);
		
	}


	/**
	 * @param detailsTable
	 * 
	 * Method responsible for identifying type of phone 

	 */
	private void addPhoneType(ListItem  serviceLocationListItem, String phoneClassId)
	{
		if(phoneClassId!=null)
		{
			if(phoneClassId.equalsIgnoreCase("0"))
			{
				serviceLocationListItem.add(new Phrase(SOPDFConstants.HOME,SOPDFConstants.FONT_LABEL));
			
			}
			else
				if(phoneClassId.equalsIgnoreCase("1"))
				{
					serviceLocationListItem.add(new Phrase(SOPDFConstants.WORK, SOPDFConstants.FONT_LABEL));
				}
				else
					if(phoneClassId.equalsIgnoreCase("2"))
					{
						serviceLocationListItem.add(new Phrase(SOPDFConstants.MOBILE, SOPDFConstants.FONT_LABEL));
					}
					else
						if(phoneClassId.equalsIgnoreCase("4"))
						{
							serviceLocationListItem.add(new Phrase(SOPDFConstants.PAGER, SOPDFConstants.FONT_LABEL));
						}
						else
							if(phoneClassId.equalsIgnoreCase("5"))
							{
								serviceLocationListItem.add(new Phrase(SOPDFConstants.OTHER, SOPDFConstants.FONT_LABEL));
							}
							else
								if(phoneClassId.equalsIgnoreCase("6"))
								{
									serviceLocationListItem.add(new Phrase(SOPDFConstants.FAX, SOPDFConstants.FONT_LABEL));
								}
								
		}
	}
	
	/**
	 * @param detailsTable
	 * 
	 * Method responsible for adding service date details

	 */
	private void addServiceDateTable(PdfPTable detailsTable) {
		
		PdfPTable tableServiceDate = new PdfPTable(1);
		List serviceDateList = new List(false);
		Image image =null;

				try {
					image =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_DATE_ICON_FILEPATH).getURL());
					image.setAbsolutePosition(100, 100);
					serviceDateList.setListSymbol(new Chunk(image, 0, 0));

				} catch (BadElementException e) {
					logger.error("Unexpected error while generating Service Order PDF", e);

				} catch (MalformedURLException e) {
					logger.error("Unexpected error while generating Service Order PDF", e);

				} catch (IOException e) {
					logger.error("Unexpected error while generating Service Order PDF", e);

				}

				serviceDateList.add(image);
				ListItem item =new ListItem();
				item.add(SOPDFConstants.SPACE);
				item.setFont(SOPDFConstants.FONT_LABEL_BIG);

				item.add(SOPDFConstants.APPOINTMENT);
				
				serviceDateList.add(item);
		
		serviceDateList.setListSymbol(SOPDFConstants.MED_SPACE);
		serviceDateList.add(new ListItem(SOPDFUtils.appointmentDates(so), SOPDFConstants.FONT_LABEL)); 
		serviceDateList.add(new ListItem(SOPDFUtils.serviceWindow(so), SOPDFConstants.FONT_LABEL));		
		serviceDateList.add(SOPDFConstants.NEW_LINE);
		SOPDFUtils.addCellToTable(serviceDateList, 0, -1, 40, detailsTable,tableServiceDate);		
	}

	
	/**
	 * @param mainTable
	 * 
	 * Method responsible for adding provider details
	 */
	private void addProvider(PdfPTable mainTable) {
		PdfPTable providerTable = new PdfPTable(1);
		providerTable.getDefaultCell().setBorder(0);
		List providerList = new List(false);
		Image image=null;
		try {
			image =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_USER_ICON_FILEPATH).getURL());
			image.setAbsolutePosition(100, 100);
			providerList.setListSymbol(new Chunk(image, 0, 0));
			
			ListItem itemProvider =new ListItem();
			itemProvider.add(SOPDFConstants.SPACE);
			itemProvider.setFont(SOPDFConstants.FONT_LABEL_BIG);
			itemProvider.add(SOPDFConstants.SERVICE_PRO);
			providerList.add(itemProvider);
			
			providerList.setListSymbol(SOPDFConstants.BIG_SPACE);
			ListItem item =new ListItem(); 
			if(so.getWfStateId() < OrderConstants.ACCEPTED_STATUS){
				item.setFont(SOPDFConstants.FONT_LABEL_BOLD);
				item.add(SOPDFConstants.LARGE_SPACE+SOPDFConstants.SMALL_SPACE+SOPDFConstants.UNASSIGNED);
				providerList.add(item);
			}else if(null != so.getAssignmentType() && so.getAssignmentType().equals(SOPDFConstants.ASSIGNMENT_TYPE_FIRM)){
				item.setFont(SOPDFConstants.FONT_RED);
				item.add(SOPDFConstants.LARGE_SPACE+SOPDFConstants.SMALL_SPACE+SOPDFConstants.UNASSIGNED);
				providerList.add(item);
			}
			
		} 
		 catch (BadElementException e) {
				logger.error("Unexpected error while generating Service Order PDF", e);

			} catch (MalformedURLException e) {
				logger.error("Unexpected error while generating Service Order PDF", e);

			} catch (IOException e) {
				logger.error("Unexpected error while generating Service Order PDF", e);

			}

		if (null != so.getAssignmentType() && so.getAssignmentType().equals(SOPDFConstants.ASSIGNMENT_TYPE_PROVIDER) && so.getAcceptedResource() != null && so.getAcceptedResourceId() != null && so.getAcceptedResource().getResourceContact() != null && so.getAcceptedResource().getResourceContact().getFirstName() != null && so.getAcceptedResource().getResourceContact().getLastName() != null) {
			ListItem list = new ListItem();
			list.setLeading(10);
			providerList.setListSymbol(SOPDFConstants.SMALL_SPACE+SOPDFConstants.SPACE);
			list.add(new Phrase(so.getAcceptedResource().getResourceContact().getLastName() + SOPDFConstants.COMMA  + SOPDFConstants.SPACE + so.getAcceptedResource().getResourceContact().getFirstName() + SOPDFConstants.SPACE ,SOPDFConstants.FONT_LABEL));
			list.add(new Phrase(SOPDFConstants.NEW_LINE_FEED+SOPDFConstants.USER_ID + so.getAcceptedResourceId(), SOPDFConstants.FONT_LABEL));

			providerList.add(list);
		}

		PdfPCell cell = new PdfPCell();
		cell.setMinimumHeight(40);
		cell.setBorder(0);
		cell.addElement(providerList);
		providerTable.addCell(cell);		
		mainTable.addCell(providerTable);
				
	}

	
	
	

	/**
	 * @return
	 * 
	 * Method responsible for adding custom references to the so Details table
	 */
	private List addCustomRef() {
		List buyerCustomerRefList = new List(false);
		ListItem item = new ListItem();

		buyerCustomerRefList.setListSymbol(SOPDFConstants.CENTER_SPACE);

		if (so.getCustomRefs() != null && !so.getCustomRefs().isEmpty()) {
			java.util.List<ServiceOrderCustomRefVO> customReflist = new java.util.ArrayList<ServiceOrderCustomRefVO>();
			
			if(SOPDFConstants.BUYER_INHOME_ID == so.getBuyerId().intValue()){
				java.util.List<ServiceOrderCustomRefVO> toBeFilteredList = so.getCustomRefs();
				
				/* Filter the custom reference for buyer 3000 - Only below given should be 
				 * present in the receipt 
				 * 	Model --> Model Number
				 * 	SerialNumber --> Serial Number
				 * 	Brand --> Brand
				 * 	CoverageTypeParts --> Coverage Type Parts
				 * 	CoverageTypeLabor --> Coverage Type Labor
				 * 	CustomerNumber --> Customer Number
				 */
				for (ServiceOrderCustomRefVO custVO : toBeFilteredList){					
					if(StringUtils.equalsIgnoreCase(custVO.getRefType(), SOPDFConstants.REF_MODEL)){
						custVO.setRefType(SOPDFConstants.REF_MODEL_DISPLAY);
						customReflist.add(custVO);
					}else if(StringUtils.equalsIgnoreCase(custVO.getRefType(), SOPDFConstants.REF_SERIAL)){
						custVO.setRefType(SOPDFConstants.REF_SERIAL_DISPLAY);
						customReflist.add(custVO);
					}else if(StringUtils.equalsIgnoreCase(custVO.getRefType(), SOPDFConstants.REF_BRAND)){
						custVO.setRefType(SOPDFConstants.REF_BRAND);
						customReflist.add(custVO);
					}else if(StringUtils.equalsIgnoreCase(custVO.getRefType(), SOPDFConstants.REF_CUSTOMER_NUMBER)){
						custVO.setRefType(SOPDFConstants.REF_CUSTOMER_NUMBER_DISPLAY);
						customReflist.add(custVO);
					}else if(StringUtils.equalsIgnoreCase(custVO.getRefType(), SOPDFConstants.REF_COVERAGE_PARTS)){
						custVO.setRefType(SOPDFConstants.REF_COVERAGE_PARTS_DISPLAY);
						customReflist.add(custVO);
					}else if(StringUtils.equalsIgnoreCase(custVO.getRefType(), SOPDFConstants.REF_COVERAGE_LABOR)){
						custVO.setRefType(SOPDFConstants.REF_COVERAGE_LABOR_DISPLAY);
						customReflist.add(custVO);
					}
				}
			}else{
				customReflist = so.getCustomRefs();
			}
			
			Collections.sort(customReflist, new Comparator<ServiceOrderCustomRefVO>(){
				public int compare(ServiceOrderCustomRefVO s1, ServiceOrderCustomRefVO s2) {
					return s1.getRefType().compareToIgnoreCase(s2.getRefType());
				}
				
			});			
			if (!customReflist.isEmpty()) {
				item = new ListItem(); 
				item.setFont(SOPDFConstants.FONT_LABEL);
				int count = 0;
				customReflist = filterCustomRef(customReflist);
				int size = customReflist.size();

				for (ServiceOrderCustomRefVO vo : customReflist){
					count++;
					String refValue = vo.getRefValue();
					if(StringUtils.isNotBlank(refValue)){
						item.add(SOPDFUtils.append(vo.getRefType()+SOPDFConstants.SPACE, refValue, SOPDFConstants.COLON + SOPDFConstants.SPACE));
						if(count != size){
							item.add(SOPDFConstants.NEW_LINE_FEED);
						}
					}
				}

				buyerCustomerRefList.add(item);
				//buyerCustomerRefList.add(SOPDFConstants.NEW_LINE);
			}

		}

		
		return buyerCustomerRefList;
	}
	
	
	
	//  to filter Custom Reference	
	
	/**
	 * @param customReflist
	 * @return
	 */
	private java.util.List<ServiceOrderCustomRefVO> filterCustomRef(
			java.util.List<ServiceOrderCustomRefVO> customReflist) {
     
		java.util.List<ServiceOrderCustomRefVO> filteredCustomReflist = new ArrayList<ServiceOrderCustomRefVO>();
		
		for (ServiceOrderCustomRefVO vo : customReflist){
			String refValue = vo.getRefValue();
			if (StringUtils.isNotBlank(refValue) && vo.getPdfRefInd().equals(SOPDFConstants.ONE) && !vo.isPrivateInd()) {
				filteredCustomReflist.add(vo);
			}
		}
		return filteredCustomReflist;
	}

	/**
	 * @param descTable
	 * @param mainTable
	 * @param pdfWriter
	 * @param document
	 * @throws DocumentException
	 * 
	 * 
	 *  Method responsible for adding task and parts list to the PDF
	 */
	@SuppressWarnings("deprecation")
	private void addTaskAndPartList(PdfPTable descTable, PdfPTable mainTable, PdfWriter pdfWriter, Document document) throws DocumentException {
		List serviceTaskList = new List(false);
		List serviceTaskList2 = new List(false);
		PdfPCell cell = null;
		PdfPCell cell2 = null;
		
		ListItem item = new ListItem();
		Image image=null;
		try {
			image =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_DETAILS_ICON_FILEPATH).getURL());
			image.setAbsolutePosition(100, 100);
			serviceTaskList.setListSymbol(new Chunk(image,-4, 0));
			item.setFont(SOPDFConstants.FONT_LABEL_BIG);

			item.add(SOPDFConstants.SERVICE_REQUESTED);
			serviceTaskList.add(item);
			serviceTaskList2.add(item);
			serviceTaskList.setListSymbol(SOPDFConstants.ALLIGN_SPACE);
			serviceTaskList2.setListSymbol(SOPDFConstants.ALLIGN_SPACE);

		} 
		catch (BadElementException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (MalformedURLException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (IOException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		}
		

		serviceTaskList2.add(SOPDFConstants.NEW_LINE);
		serviceTaskList2.add(SOPDFConstants.NEW_LINE);
		serviceTaskList2.add(SOPDFConstants.NEW_LINE);
		
		java.util.List<ServiceOrderTask> tasks = so.getTasks();
		if (tasks != null && !tasks.isEmpty()) {
			for (ServiceOrderTask task : tasks) {
				ListItem item2 = new ListItem();
				item2.setFont(SOPDFConstants.FONT_LABEL);
				//serviceTaskList.add(item2);
				item2.add(new Phrase((!StringUtils.isEmpty(task.getServiceType())? task.getServiceType():SOPDFConstants.BLANK)+SOPDFConstants.SPACE+SOPDFConstants.ARROW+SOPDFConstants.SPACE+(!StringUtils.isEmpty(task.getTaskName())? task.getTaskName():SOPDFConstants.BLANK)));
				
				serviceTaskList.add(item2);
				//serviceTaskList.add(SOPDFConstants.NEW_LINE);
				
				cell2=null;
				cell2 = new PdfPCell();
				cell2.setBorder(0);
				cell2.setColspan(4);
				cell2.addElement(serviceTaskList2);
				mainTable.addCell(cell2);
				
				cell=null;
				cell = new PdfPCell();
				cell.setBorder(0);
				cell.setColspan(4);
				cell.addElement(serviceTaskList);
				descTable.addCell(cell);
				descTable.setWidthPercentage(100);
				if(!pdfWriter.fitsPage(mainTable)){
					descTable.setKeepTogether(true);
				}
				document.add(descTable);
				descTable= null;
				descTable = createNewTable();
				serviceTaskList = new List(false);
				serviceTaskList.setListSymbol(SOPDFConstants.ALLIGN_SPACE);
				serviceTaskList2 = new List(false);
				serviceTaskList2.setListSymbol(SOPDFConstants.ALLIGN_SPACE);

				
			}
		}
		
		/*// to add invoicee Parts in customer copy
		if(mainPageTitle.equalsIgnoreCase(SOPDFConstants.CUSTOMER_COPY) && SOPDFConstants.BUYER_INHOME_ID == so.getBuyerId().intValue()){
			descTable = createNewTable();
			addCustomerInvoicePartsToTable(descTable,document, pdfWriter);
		}*/
		// to add invoicee Parts in provider copy
		if(SOPDFConstants.BUYER_INHOME_ID == so.getBuyerId().intValue()){
			descTable = createNewTable();
			addProviderInvoicePartsToTable(descTable,document, pdfWriter);
		}
		
	}
	
	
	
	
	/**
	 * @param descTable
	 * @param document
	 * @param pdfWriter
	 * to add all parts to provider copy
	 */
	private void addProviderInvoicePartsToTable(PdfPTable descTable,
			Document document, PdfWriter pdfWriter) throws DocumentException {
		
		if (so.getInvoiceParts()!=null) {
			List servicePartList = new List(false);

			PdfPCell cell1 = new PdfPCell();
			cell1.setColspan(1);


			ListItem item = new ListItem();
			Image image=null;
			try {
				image =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_PARTS_ICON_FILEPATH).getURL());
				servicePartList.setListSymbol(new Chunk(image, -7, 0));
				item.setFont(SOPDFConstants.FONT_LABEL_BIG);
				item.add(SOPDFConstants.INVOICE_PARTS);
				servicePartList.add(item);

			} 
			catch (BadElementException e) {
				logger.error("Unexpected error while generating Service Order PDF", e);

			} catch (MalformedURLException e) {
				logger.error("Unexpected error while generating Service Order PDF", e);

			} catch (IOException e) {
				logger.error("Unexpected error while generating Service Order PDF", e);

			}

			cell1.addElement(servicePartList);
			cell1.setBorderWidth(0);
			cell1.setColspan(4);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);

			
			servicePartList.setListSymbol(SOPDFConstants.ALLIGN_SPACE);

			java.util.List<ProviderInvoicePartsVO> invocieParts = so.getInvoiceParts();;
	
			Collections.sort(invocieParts, new Comparator<ProviderInvoicePartsVO>(){
				public int compare(ProviderInvoicePartsVO p1, ProviderInvoicePartsVO p2) {
					String partStatus1 = p1.getPartStatus();
					String partStatus2 = p2.getPartStatus();
					String partName1 = p1.getDescription();
					String partName2 = p2.getDescription();
					String partNum1 = p1.getPartNo();
					String partNum2 = p2.getPartNo();;
					if(null!=partStatus1 && null!=partStatus2 && partStatus1.compareToIgnoreCase(partStatus2)!=0){
		            	return partStatus1.compareToIgnoreCase(partStatus2);
		            }
					else if(null!=partNum1 && null!=partNum2 && partNum1.compareToIgnoreCase(partNum2)!=0){
		            	return partNum1.compareToIgnoreCase(partNum2);
		            }
		            else{
		            	return partName1.compareToIgnoreCase(partName2);

		            }
		        }
			});
			
			if (!invocieParts.isEmpty()) {
				int count =0;
				for (ProviderInvoicePartsVO part : invocieParts) {
					ListItem item2 = new ListItem();
					item2.setFont(SOPDFConstants.FONT_LABEL);					
					if(StringUtils.isNotBlank(part.getPartNo())){
						item2.add(new Phrase(part.getPartNo(),SOPDFConstants.FONT_LABEL));

					}
					else{
						item2.add(SOPDFConstants.BIG_SPACE);
					}
					if(StringUtils.isNotBlank(part.getDescription())){
						item2.add(new Phrase(SOPDFConstants.SPACE+SOPDFConstants.HYPHEN+SOPDFConstants.SPACE+part.getDescription()+SOPDFConstants.SPACE,SOPDFConstants.FONT_LABEL));
					}
					else{
						item2.add(SOPDFConstants.BIG_SPACE);
					}

					item2.add(new Phrase(SOPDFConstants.OPENING_BRACE+(new Integer(part.getQty())).toString()+SOPDFConstants.CLOSING_BRACE,SOPDFConstants.FONT_LABEL));
					
					if(count ==1){
							descTable.setKeepTogether(true);
					}
					count ++;
					servicePartList.add(item2);
				}

				PdfPCell cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.addElement(servicePartList);
				cell.setBorderWidth(0);
				cell.setColspan(4);

				
				cell1.addElement(servicePartList);
				cell1.setBorderWidth(0);
				cell1.setColspan(4);
				
				
				descTable.addCell(cell);
				descTable.setWidthPercentage(100);
				
				document.add(descTable);

			}
		}
	}

	
	
	
	
	/**
	 * @param notesTable
	 * 
	 * Method responsible for setting notes to the PDF
	 * @param document 
	 * @throws DocumentException 
	 */
	private void addNotesTable(PdfPTable notesTable, Document document) throws DocumentException {

		if (StringUtils.isNotBlank(so.getResolutionDs())) {
			PdfPCell notesCell = new PdfPCell();
			notesCell.setBorder(0);
			notesCell.setColspan(4);
			List providernotesList = new List(false);
			List providernotesList2 = new List(false);
			ListItem item = new ListItem();
			Image image=null;
			try {
				image =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_COMMENT_ICON_FILEPATH).getURL());
				item =new ListItem();
				providernotesList.setListSymbol(new Chunk(image,-1, 0));
				item.add(SOPDFConstants.SPACE);
				item.setFont(SOPDFConstants.FONT_LABEL_BIG);
				item.add(SOPDFConstants.SERVICE_PROVIDER_NOTES);
				providernotesList.add(item);


			} 
			catch (BadElementException e) {
				logger.error("Unexpected error while generating Service Order PDF", e);

			} catch (MalformedURLException e) {
				logger.error("Unexpected error while generating Service Order PDF", e);

			} catch (IOException e) {
				logger.error("Unexpected error while generating Service Order PDF", e);

			}
			item = new ListItem();
			providernotesList2.setListSymbol(SOPDFConstants.ALLIGN_SPACE);
			item.add(new Phrase(so.getResolutionDs(),SOPDFConstants.FONT_LABEL));
			providernotesList2.add(item);
			notesCell.addElement(providernotesList);
			notesCell.addElement(providernotesList2);

			notesTable.setWidthPercentage(100);
			notesTable.addCell(notesCell);
			document.add(notesTable);
		}

	}

	
	/**
	 * @param waiversTable
	 * @return
	 * 
	 * Method responsible for setting the waiver list to PDF
	 */
	private void addWaiverList(PdfPTable waiversTable, Document document) throws DocumentException, IOException{

		List waiverList = new List(false);
		ListItem item = new ListItem();

		Image image=null;
		try {
			image =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_WAIVER_ICON_FILEPATH).getURL());
			
			image.setAbsolutePosition(100, 100);
			waiverList.setListSymbol(new Chunk(image,-1, 0));
			item.add(SOPDFConstants.SPACE);

			item.add(new Phrase(SOPDFConstants.WAIVER_HEAD,SOPDFConstants.FONT_LABEL_BIG ));
			item.add(new Phrase(SOPDFConstants.WAIVER_CUST_RECEIPT,SOPDFConstants.FONT_LABEL_BOLD));

			waiverList.add(item);
			//waiverList.add(SOPDFConstants.NEW_LINE);
			waiverList.setListSymbol(SOPDFConstants.ALLIGN_SPACE);

		} 
		catch (BadElementException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (MalformedURLException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (IOException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		}
		item = new ListItem();
		
		item.add(new Phrase(SOPDFConstants.WAIVER_TEXT, SOPDFConstants.FONT_LABEL_SMALL));
		waiverList.add(item);
		
		waiverList.add(new ListItem(SOPDFConstants.NEW_LINE_FEED, SOPDFConstants.FONT_LABEL_SMALL));

		PdfPCell waiverTableCell = addPdfCellForWaiver(waiverList);
		waiverTableCell.setColspan(4);
		waiversTable.setKeepTogether(true);
		waiversTable.addCell(waiverTableCell);

		if(null != providerSignature && null != providerSignatureImage){
			waiverTableCell = addSignatures(providerSignature, providerSignatureImage);
			waiverTableCell.setColspan(4);
			waiversTable.addCell(waiverTableCell);
			waiversTable.setKeepTogether(true);
		}
		
		waiverList = new List(false);
		waiverList.setListSymbol(SOPDFConstants.ALLIGN_SPACE);
		item=new ListItem();
		item.add(new Phrase(SOPDFConstants.WAIVER_SIGNATURE_CUSTOMER, SOPDFConstants.FONT_HEADER));
		waiverList.add(item);
		waiverList.add(new ListItem(SOPDFConstants.NEW_LINE_FEED, SOPDFConstants.FONT_LABEL_SMALL));
		
		waiverTableCell = addPdfCellForWaiver(waiverList);
		waiverTableCell.setColspan(4);
		waiversTable.setKeepTogether(true);
		waiversTable.addCell(waiverTableCell);
		
		document.add(waiversTable);	
	}
	/**
	 * Method responsible for setting the customerAgreement to PDF
	 * @param customerAgreementTable
	 * @param document
	 * @throws DocumentException
	 * @throws IOException
	 */
	private void addCustomerAgreement(PdfPTable customerAgreementTable, Document document) throws DocumentException, IOException{

		List customerAgreementList = new List(false);
		
		customerAgreementList.setListSymbol(SOPDFConstants.ALLIGN_SPACE);
		
		ListItem item = new ListItem();

		PdfPCell customerAgreementTableCell = addPdfCellForWaiver(customerAgreementList);
		
		/*Moved customer signature to WAIVER OF LIEN to keep it together*/
		item=new ListItem();
		item.add(new Phrase(SOPDFConstants.WORK_ACK_STR, SOPDFConstants.FONT_LABEL_SMALL));
		customerAgreementList.add(item);
		customerAgreementList.add(new ListItem(SOPDFConstants.NEW_LINE_FEED, SOPDFConstants.FONT_LABEL_SMALL));
		
		customerAgreementTableCell = addPdfCellForWaiver(customerAgreementList);
		customerAgreementTableCell.setColspan(4);
		customerAgreementTable.setKeepTogether(true);
		customerAgreementTable.addCell(customerAgreementTableCell);
		
		if(null != customerSignature && null != customerSignatureImage){
			customerAgreementTableCell = addSignatures(customerSignature, customerSignatureImage);
			customerAgreementTableCell.setColspan(4);
			customerAgreementTable.addCell(customerAgreementTableCell);
			customerAgreementTable.setKeepTogether(true);
		}
		
		customerAgreementList = new List(false);
		customerAgreementList.setListSymbol(SOPDFConstants.ALLIGN_SPACE);
		item=new ListItem();
		item.add(new Phrase(SOPDFConstants.SIGNATURE_TEXT, SOPDFConstants.FONT_LABEL_BOLD));
		customerAgreementList.add(item);
		customerAgreementList.add(new ListItem(SOPDFConstants.NEW_LINE_FEED, SOPDFConstants.FONT_LABEL_SMALL));
		
		item=new ListItem();
		item.add(new Phrase(SOPDFConstants.THANK_YOU_STRING_CENTER_LOWERCASE, SOPDFConstants.FONT_LABEL_BIG));
		customerAgreementList.add(item);
		customerAgreementList.add(new ListItem(SOPDFConstants.NEW_LINE_FEED, SOPDFConstants.FONT_LABEL_SMALL));
		
		//SL-20807
		if(SOPDFConstants.BUYER_INHOME_ID == so.getBuyerId()){
			customerAgreementList.add(new ListItem(SOPDFConstants.SURVEY_MESSAGE1,SOPDFConstants.FONT_LABEL));
			customerAgreementList.add(new ListItem(SOPDFConstants.SURVEY_MESSAGE2,SOPDFConstants.FONT_LABEL));
			customerAgreementList.add(new ListItem(SOPDFConstants.SURVEY_MESSAGE3,SOPDFConstants.FONT_LABEL));
		}
		
		
		customerAgreementTableCell = addPdfCellForWaiver(customerAgreementList);
		customerAgreementTableCell.setColspan(4);
		customerAgreementTable.addCell(customerAgreementTableCell);
		customerAgreementTable.setKeepTogether(true);
    	
		document.add(customerAgreementTable);	
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
		waiverTableCell.setColspan(4);
		waiverTableCell.setBorder(0);
		waiverTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		waiverTableCell.addElement(waiverTable);
		
		return waiverTableCell;
	}
	
	private PdfPCell addSignatures(Signature signature, Image signatureImage) throws IOException, DocumentException{
		/** Name */
		PdfPCell signImageCell1 = new PdfPCell();
		List waiverList2 = new List(false);
		waiverList2.setListSymbol(SOPDFConstants.BIG_SPACE
				+ SOPDFConstants.BIG_SPACE+SOPDFConstants.ALLIGN_SPACE+SOPDFConstants.ALLIGN_SPACE+SOPDFConstants.ALLIGN_SPACE+SOPDFConstants.ALLIGN_SPACE);
		waiverList2.add(new ListItem(signature.getName(), SOPDFConstants.FONT_LABEL));
		signImageCell1.addElement(waiverList2);
		signImageCell1.setBorder(0);
		signImageCell1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		signImageCell1.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
		signImageCell1.setPaddingLeft(30f);
		
		/** Image */
		PdfPCell signImageCell2 = new PdfPCell(signatureImage);
		signImageCell2.setBorder(0);
		signImageCell2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		signImageCell2.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
		
		/** Date */
		PdfPCell signImageCell3 = new PdfPCell();
		signImageCell3.setBorder(0);
		List waiverList3 = new List(false);
		waiverList3.setListSymbol(SOPDFConstants.SPACE);
		waiverList3.add(new ListItem(signature.getCurrentDate(), SOPDFConstants.FONT_LABEL));
		signImageCell3.addElement(waiverList3);
		signImageCell3.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		signImageCell3.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
		signImageCell3.setPaddingLeft(25f);

		float widths[] = {40, 30, 30};
		PdfPTable waiverTable = new PdfPTable(3);
		waiverTable.setWidths(widths);
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
