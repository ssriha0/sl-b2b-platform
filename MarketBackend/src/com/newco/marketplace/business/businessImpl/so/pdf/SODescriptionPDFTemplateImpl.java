package com.newco.marketplace.business.businessImpl.so.pdf;


import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ElementTags;
import com.lowagie.text.Image;
import com.lowagie.text.List;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Signature;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.ProviderInvoicePartsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.DocumentUtils;
import com.newco.marketplace.util.constants.SOPDFConstants;
import com.newco.marketplace.utils.ServiceLiveStringUtils;

/**
 * Class responsible for generating the Customer Copy/Service Pro Copy of PDF
 */

public class SODescriptionPDFTemplateImpl implements PDFTemplate {

	private Image logoImage;
	private ServiceOrder so;
	private String mainPageTitle;
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

		PdfPTable mainTable =createNewMainTable();
		addSoSummaryTables(mainTable);
		addAddInfo(mainTable);
		document.add(mainTable);

//		mainTable =createNewMainTable();
//		SOPDFUtils.drawMiddleLine(document, pdfWriter);
//		document.add(mainTable);
		
		PdfPTable overviewTable = createNewTable();
		mainTable =createNewMainTable();
		overviewTable = addOverview(overviewTable,mainTable,pdfWriter);
		document.add(overviewTable);
		//Adding Special Instruction & Service Location Notes on Provider copy- Jira#21049
		if(this.mainPageTitle.equalsIgnoreCase(SOPDFConstants.PROVIDER_COPY)){
			mainTable =createNewMainTable();
			PdfPTable spclInstruct = createNewTable();
			addSpecialInstructionTable(spclInstruct, mainTable, pdfWriter, document);
			mainTable =createNewMainTable();
			PdfPTable serviceLocNotes = createNewTable();
			addServiceLocNotesTable(serviceLocNotes, mainTable, pdfWriter, document);
		}
			
		mainTable =createNewMainTable();
		PdfPTable descTable = createNewTable();
		addTaskAndPartList(descTable, mainTable, pdfWriter, document);
		
		PdfPTable notesTable = createNewMainTable();
		addNotesTable(notesTable, document);

		PdfPTable waiversTable = createNewMainTable();
		addWaiverList(waiversTable,document);
		
	}
	
	public SODescriptionPDFTemplateImpl() {
		super();
	}
	
	public SODescriptionPDFTemplateImpl(boolean mobileIndicator) {
		super();
		this.mobileIndicator = mobileIndicator;
	}
	
	private void thankYouTable(PdfPTable thankyoutable, Document document) throws DocumentException{
		PdfPTable tableSignatures = new PdfPTable(1);
		PdfPCell cell = null;

		tableSignatures.setWidthPercentage(100);
		
		cell = new PdfPCell(new Phrase(SOPDFConstants.THANK_YOU_STRING_LOWERCASE, SOPDFConstants.FONT_LABEL_BIG));
		cell.setBorder(0);
		cell.setColspan(4);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableSignatures.addCell(cell);
		cell = new PdfPCell();
		cell.setColspan(4);
		cell.setBorder(0);
		cell.addElement(tableSignatures);
		thankyoutable.addCell(cell);
		thankyoutable.setKeepTogether(true);
		document.add(thankyoutable);
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
	 * to add soSummary(sl-17429)
	 */
	private void addSoSummaryTables(PdfPTable mainTable){
		addBuyerInfoTable(mainTable);

		if(this.mainPageTitle.equalsIgnoreCase(SOPDFConstants.PROVIDER_COPY)){
			addPickUpLocationTable(mainTable);
			addServiceDateTable(mainTable);
		}
		else{
			addServiceDateTable(mainTable);
			addProvider(mainTable);
		}
		addServiceLocationTable(mainTable);

		
	}
	
	
	/**
	 * Method to add alt buyer information to buyer information table to PDF
	 * @param so
	 * @param buyerInfoList
	 */
	private void addBuyerInfoTable(PdfPTable mainTable){
		PdfPTable tablebuyerInfo = new PdfPTable(1);
		List buyerInfoList = new List(false);
		Image image = null;
		try {
			if(this.mainPageTitle.equalsIgnoreCase(SOPDFConstants.PROVIDER_COPY)){
				image =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_USER_ICON_FILEPATH).getURL());
			}
			else{
				image =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_COMMENT_ICON_FILEPATH).getURL());

			}
			image.setAbsolutePosition(100, 100);
			buyerInfoList.setListSymbol(new Chunk(image, 0, 0));

			ListItem item =new ListItem();
			item.add(SOPDFConstants.SPACE);
			item.setFont(SOPDFConstants.FONT_LABEL_BIG);

			item.add(SOPDFConstants.BUYER_CONT);
			buyerInfoList.add(item);	
			buyerInfoList.setListSymbol(SOPDFConstants.BIG_SPACE);

		}
		catch (BadElementException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (MalformedURLException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (IOException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		}
		if(so.getBuyer()!= null && so.getBuyer().getBusinessName() != null && so.getBuyerId() != null ){
			buyerInfoList.add(new ListItem(so.getBuyer().getBusinessName()
					// removing as part of SLM-112
					/*+ SOPDFConstants.SPACE + SOPDFConstants.FOOTER_HASH + so.getBuyerId() */,
					SOPDFConstants.FONT_LABEL));
		if(this.mainPageTitle.equalsIgnoreCase(SOPDFConstants.PROVIDER_COPY)){
			if(	so.getAltBuyerResource()!=null && so.getAltBuyerResource().getBuyerResContact()!=null
					&& so.getAltBuyerResource().getBuyerResContact().getFirstName() != null
					&& so.getAltBuyerResource().getBuyerResContact().getLastName() != null){
				
				ListItem listItem = new ListItem();
				listItem.setLeading(12);
				
				listItem.add(new Phrase(so.getAltBuyerResource().getBuyerResContact().getLastName()
						+SOPDFConstants.COMMA+ SOPDFConstants.SPACE + so.getAltBuyerResource().getBuyerResContact().getFirstName()
						+ SOPDFConstants.SPACE ,SOPDFConstants.FONT_LABEL_BOLD));
				listItem.add(new Phrase(SOPDFConstants.OPENING_BRACE+ SOPDFConstants.FOOTER_HASH + so.getAltBuyerResource().getBuyerResContact().getResourceId()+SOPDFConstants.CLOSING_BRACE,
						SOPDFConstants.FONT_LABEL));
				buyerInfoList.add(listItem);
			}else{
				buyerInfoList
				.add(new ListItem(SOPDFConstants.BLANK_SPACE, SOPDFConstants.FONT_LABEL));
			}
		}
		}
		
		if(this.mainPageTitle.equalsIgnoreCase(SOPDFConstants.PROVIDER_COPY)){
			if (null != so.getAltBuyerResource() && null != so.getAltBuyerResource().getBuyerResPrimaryLocation()){
				if(so.getAltBuyerResource().getBuyerResPrimaryLocation().getStreet1() != null ){
					if(so.getAltBuyerResource().getBuyerResPrimaryLocation().getStreet2() != null){
						buyerInfoList
						.add(new ListItem(so.getAltBuyerResource().getBuyerResPrimaryLocation().getStreet1()
								+ SOPDFConstants.COMMA+SOPDFConstants.SPACE+ so.getAltBuyerResource().
								getBuyerResPrimaryLocation().getStreet2(),SOPDFConstants.FONT_LABEL));
					}else{
						buyerInfoList
						.add(new ListItem(so.getAltBuyerResource().getBuyerResPrimaryLocation().getStreet1(),SOPDFConstants.FONT_LABEL));
					}
				}
				if(so.getAltBuyerResource().getBuyerResPrimaryLocation().getCity() != null 
						&& so.getAltBuyerResource().getBuyerResPrimaryLocation().getState()!= null
						&& so.getAltBuyerResource().getBuyerResPrimaryLocation().getZip() != null){
					buyerInfoList.add(new ListItem(so.getAltBuyerResource().getBuyerResPrimaryLocation().getCity()
							+ SOPDFConstants.COMMA+SOPDFConstants.SPACE+so.getAltBuyerResource().getBuyerResPrimaryLocation().getState()
							+ SOPDFConstants.SPACE + so.getAltBuyerResource().getBuyerResPrimaryLocation()
							.getZip(),SOPDFConstants.FONT_LABEL));
				}
			}
		}
		ListItem item =new ListItem();
		Image imagePhone =null;
		Image imageEmail =null;
		try {
			try {
				imagePhone =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_PHONE_ICON_FILEPATH).getURL());
				imageEmail =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_EMAIL_ICON_FILEPATH).getURL());
			} catch (BadElementException e) {
				logger.error("Unexpected error while generating Service Order PDF", e);
			}
		} catch (MalformedURLException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);
			
		} catch (IOException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);
		}
		
		item =new ListItem();
		boolean phoneAdded =false;
		if(null != so.getAltBuyerResource() && 
				null != so.getAltBuyerResource().getBuyerResContact() && StringUtils.isNotBlank(so.getAltBuyerResource().getBuyerResContact().getPhoneNo())){
			
			//SL-20760 ->Phone number for buyer  513539 from contact table when buyer contact number is present
			if((this.mainPageTitle.equalsIgnoreCase(SOPDFConstants.PROVIDER_COPY) && SOPDFConstants.CONSTELLATION_HOME_BUYER_ID == so.getBuyerId())) {
				
				if(StringUtils.isNotBlank(so.getConstellationBuyerPhone())){
				buyerInfoList.setListSymbol(new Chunk(imagePhone,0, -2));
				
				//Data from application_constants table
				item.add(new Phrase(SOPDFConstants.SPACE+SOPDFConstants.SPACE+SOPDFUtils.formatPhoneNumber(so.getConstellationBuyerPhone()),
					SOPDFConstants.FONT_LABEL_BOLD));
				
				
				item.add(new Phrase(SOPDFConstants.WORK, SOPDFConstants.FONT_LABEL));
				buyerInfoList.add(item);
				phoneAdded = true;
				buyerInfoList.setListSymbol(SOPDFConstants.SMALL_SPACE);
			}else{
				buyerInfoList.add(new ListItem(SOPDFConstants.BLANK_SPACE, SOPDFConstants.FONT_LABEL));	
			}
		   }else
		   {
			   	buyerInfoList.setListSymbol(new Chunk(imagePhone,0, -2));
				item
				.add(new Phrase(SOPDFConstants.SPACE+SOPDFConstants.SPACE+SOPDFUtils.formatPhoneNumber(so.getAltBuyerResource().getBuyerResContact().getPhoneNo()),
					SOPDFConstants.FONT_LABEL_BOLD));
				item.add(new Phrase(SOPDFConstants.WORK, SOPDFConstants.FONT_LABEL));
				buyerInfoList.add(item);
				phoneAdded = true;
				buyerInfoList.setListSymbol(SOPDFConstants.SMALL_SPACE);
		   }
		}	
		else{
			
			//SL-20760 ->Phone number for buyer  513539 from contact table when buyer contact number is not present
			if((this.mainPageTitle.equalsIgnoreCase(SOPDFConstants.PROVIDER_COPY) && SOPDFConstants.CONSTELLATION_HOME_BUYER_ID == so.getBuyerId())) {
				
				if(StringUtils.isNotBlank(so.getConstellationBuyerPhone())){
				buyerInfoList.setListSymbol(new Chunk(imagePhone,0, -2));
				
				//Data from application_constants table
				item.add(new Phrase(SOPDFConstants.SPACE+SOPDFConstants.SPACE+SOPDFUtils.formatPhoneNumber(so.getConstellationBuyerPhone()),
					SOPDFConstants.FONT_LABEL_BOLD));
				
				
				item.add(new Phrase(SOPDFConstants.WORK, SOPDFConstants.FONT_LABEL));
				buyerInfoList.add(item);
				phoneAdded = true;
				buyerInfoList.setListSymbol(SOPDFConstants.SMALL_SPACE);
				}else{	
					buyerInfoList.add(new ListItem(SOPDFConstants.BLANK_SPACE, SOPDFConstants.FONT_LABEL));
					
				}
				
			}
			else{	
			buyerInfoList.add(new ListItem(SOPDFConstants.BLANK_SPACE, SOPDFConstants.FONT_LABEL));
			
			}
		} 
		if(null!=so.getAltBuyerResource()&& so.getAltBuyerResource().getBuyerResContact() != null && StringUtils.isNotBlank(so.getAltBuyerResource().getBuyerResContact().getCellNo())){
			if(phoneAdded){
				item =new ListItem();
				item.add(SOPDFConstants.SPACE);
				item.add(new Phrase(SOPDFConstants.SPACE+SOPDFUtils.formatPhoneNumber(so.getAltBuyerResource().getBuyerResContact().getCellNo()), SOPDFConstants.FONT_LABEL));
			}else{
				buyerInfoList.setListSymbol(new Chunk(imagePhone,0, -2));
				item.add(new Phrase(SOPDFConstants.SPACE+SOPDFConstants.SPACE+SOPDFUtils.formatPhoneNumber(so.getAltBuyerResource().getBuyerResContact().getCellNo()), SOPDFConstants.FONT_LABEL));
			}
			item.add(new Phrase(SOPDFConstants.MOBILE, SOPDFConstants.FONT_LABEL));
			buyerInfoList.add(item);

		}
		else{
			buyerInfoList.add(new ListItem(SOPDFConstants.BLANK_SPACE, SOPDFConstants.FONT_LABEL));
		}
		
		//SL-20760 ->Email Address for buyer other than 513539 from contact table (PROVIDER_COPY)
		if(this.mainPageTitle.equalsIgnoreCase(SOPDFConstants.PROVIDER_COPY) && SOPDFConstants.CONSTELLATION_HOME_BUYER_ID != so.getBuyerId()){
			if(null!=so.getAltBuyerResource()&& so.getAltBuyerResource().getBuyerResContact().getEmail() != null){
				item =new ListItem();
				buyerInfoList.setListSymbol(new Chunk(imageEmail,0, -2));
				item.add(SOPDFConstants.SPACE);
				String emailPart1=null;
				String emailPart2=null;
				if(so.getAltBuyerResource().getBuyerResContact().getEmail().length() > SOPDFConstants.STRING_LEN)
				{
					int len = so.getAltBuyerResource().getBuyerResContact().getEmail().length();
					emailPart1 = so.getAltBuyerResource().getBuyerResContact().getEmail().substring(SOPDFConstants.STRING_LEN_ZERO, SOPDFConstants.STRING_LEN_SEVENTEEN);
					emailPart2 = so.getAltBuyerResource().getBuyerResContact().getEmail().substring(SOPDFConstants.STRING_LEN_SEVENTEEN,len);
					
					item.add(new Phrase(SOPDFConstants.BLANK_SPACE
							+ emailPart1, SOPDFConstants.FONT_LABEL));
					item.add(SOPDFConstants.NEW_LINE_FEED);
					item.add(new Phrase(SOPDFConstants.BLANK_SPACE
							+ emailPart2, SOPDFConstants.FONT_LABEL));
					
				}
				else
				{
					emailPart1 =so.getAltBuyerResource().getBuyerResContact().getEmail();
					item.add(new Phrase(SOPDFConstants.BLANK_SPACE
							+ emailPart1, SOPDFConstants.FONT_LABEL));
				}

				buyerInfoList.add(item);
				buyerInfoList.setListSymbol(SOPDFConstants.SMALL_SPACE);

			}else{
				buyerInfoList.add(new ListItem(SOPDFConstants.BLANK_SPACE, SOPDFConstants.FONT_LABEL));
			}
		}
		
		//SL-20760 -> Email address for Constellation buyer 513539 from application constants table.(PROVIDER_COPY)
		if(this.mainPageTitle.equalsIgnoreCase(SOPDFConstants.PROVIDER_COPY) && SOPDFConstants.CONSTELLATION_HOME_BUYER_ID == so.getBuyerId()){
			if(StringUtils.isNotBlank(so.getConstellationBuyerEmail())){
				item =new ListItem();
				buyerInfoList.setListSymbol(new Chunk(imageEmail,0, -2));
				item.add(SOPDFConstants.SPACE);
				//Email address from application constants table.
				
				String emailPart1=null;
				String emailPart2=null;
				if(so.getConstellationBuyerEmail().length() > SOPDFConstants.STRING_LEN)
				{
					int len = so.getConstellationBuyerEmail().length();
					emailPart1 = so.getConstellationBuyerEmail().substring(SOPDFConstants.STRING_LEN_ZERO, SOPDFConstants.STRING_LEN_SEVENTEEN);
					emailPart2 = so.getConstellationBuyerEmail().substring(SOPDFConstants.STRING_LEN_SEVENTEEN,len);
					
					item.add(new Phrase(SOPDFConstants.BLANK_SPACE
							+ emailPart1, SOPDFConstants.FONT_LABEL));
					item.add(SOPDFConstants.NEW_LINE_FEED);
					item.add(new Phrase(SOPDFConstants.BLANK_SPACE
							+ emailPart2, SOPDFConstants.FONT_LABEL));
					
				}else
				{
					emailPart1 =so.getConstellationBuyerEmail();
					item.add(new Phrase(SOPDFConstants.BLANK_SPACE
							+ emailPart1, SOPDFConstants.FONT_LABEL));
				}			
				buyerInfoList.add(item);
				buyerInfoList.setListSymbol(SOPDFConstants.SMALL_SPACE);

			}else{
				buyerInfoList.add(new ListItem(SOPDFConstants.BLANK_SPACE, SOPDFConstants.FONT_LABEL));
			}
		}
		
		//SL-20760 -> Email address for Constellation buyer 513539 from contact table.(CUSTOMER_COPY)
		if(this.mainPageTitle.equalsIgnoreCase(SOPDFConstants.CUSTOMER_COPY) && SOPDFConstants.CONSTELLATION_HOME_BUYER_ID == so.getBuyerId()){
			if(null!=so.getAltBuyerResource()&& so.getAltBuyerResource().getBuyerResContact().getEmail() != null){
				item =new ListItem();
			
				buyerInfoList.setListSymbol(new Chunk(imageEmail,0, -2));
				item.add(SOPDFConstants.SPACE);
				
				String emailPart1=null;
				String emailPart2=null;
				if(so.getAltBuyerResource().getBuyerResContact().getEmail().length() > SOPDFConstants.STRING_LEN)
				{
					int len = so.getAltBuyerResource().getBuyerResContact().getEmail().length();
					emailPart1 = so.getAltBuyerResource().getBuyerResContact().getEmail().substring(SOPDFConstants.STRING_LEN_ZERO, SOPDFConstants.STRING_LEN_SEVENTEEN);
					emailPart2 = so.getAltBuyerResource().getBuyerResContact().getEmail().substring(SOPDFConstants.STRING_LEN_SEVENTEEN,len);
					
					item.add(new Phrase(SOPDFConstants.BLANK_SPACE
							+ emailPart1, SOPDFConstants.FONT_LABEL));
					item.add(SOPDFConstants.NEW_LINE_FEED);
					item.add(new Phrase(SOPDFConstants.BLANK_SPACE
							+ emailPart2, SOPDFConstants.FONT_LABEL));
					
				}
				else
				{
					emailPart1 =so.getAltBuyerResource().getBuyerResContact().getEmail();
					item.add(new Phrase(SOPDFConstants.BLANK_SPACE
							+ emailPart1, SOPDFConstants.FONT_LABEL));
				}

				buyerInfoList.add(item);
				buyerInfoList.setListSymbol(SOPDFConstants.SMALL_SPACE);

			}else{
				buyerInfoList.add(new ListItem(SOPDFConstants.BLANK_SPACE, SOPDFConstants.FONT_LABEL));
			}
		}
		

		SOPDFUtils.addCellToTable(buyerInfoList, 0, -1, 40, mainTable,tablebuyerInfo);		

	}

	/**
	 * Method to add pickUp location details to PDF (sl-17429)
	 * @param mainTable
	 */
	/**
	 * @param mainTable
	 */
	private void addPickUpLocationTable(PdfPTable mainTable){
		String checkers[] = new String[5];
		PdfPTable tablePickUpLocation = new PdfPTable(1);
		List pickUpLocationList = new List(false);
		pickUpLocationList.setListSymbol(SOPDFConstants.SPACE);
		

		Properties locProp = new Properties();
		locProp.put(ElementTags.FONT, SOPDFConstants.FONT_LABEL);
		SoLocation pickUpLoc = getPickupLocation(so);
		Contact pickupContact = getPickupContact(so);
		
		Image image =null;
		try {
			image =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_PICKUP_ICON_FILEPATH).getURL());
			ListItem item =new ListItem(new Chunk(image, 0, 0));
			item.add(SOPDFConstants.SPACE);
			item.setFont(SOPDFConstants.FONT_LABEL_BIG);
			item.add(SOPDFConstants.PICK_UP_LOCATION);
			pickUpLocationList.add(item);	
			pickUpLocationList.setListSymbol(SOPDFConstants.LARGE_SPACE);
		}
		catch (BadElementException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (MalformedURLException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (IOException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		}

		if (pickUpLoc != null) {
			if(pickUpLoc.getLocnClassDesc()!=null){
				pickUpLocationList.add(new ListItem(pickUpLoc.getLocnClassDesc(), SOPDFConstants.FONT_ITALIC));
			}
			
			if (pickupContact != null && StringUtils.isNotBlank(pickupContact.getFirstName()) && StringUtils.isNotBlank(pickupContact.getLastName())) {
				pickUpLocationList.add(new ListItem(pickupContact.getFirstName()+SOPDFConstants.SPACE+pickupContact.getLastName(), SOPDFConstants.FONT_LABEL));
			}
			if(StringUtils.isNotBlank(pickUpLoc.getLocnName())) {
				pickUpLocationList.add(new ListItem(pickUpLoc.getLocnName(), SOPDFConstants.FONT_LABEL));
			}
			if(pickUpLoc.getStreet1()!=null && pickUpLoc.getStreet2()!=null &&
					pickUpLoc.getAptNo()!=null){
				checkers[0] = new SOPDFUtils.Checker(new SOPDFUtils.Checker(pickUpLoc.getStreet1()).appendWithCommaForPickup(pickUpLoc.getStreet2())).
						appendWithCommaForPickup(pickUpLoc.getAptNo());
			}
			if(pickUpLoc.getCity()!=null && pickUpLoc.getState()!=null &&
					pickUpLoc.getZip()!=null){
				checkers[1] = new SOPDFUtils.Checker(pickUpLoc.getCity())
					.appendWithCommaForPickup(pickUpLoc.getState() +SOPDFConstants.SPACE
							+pickUpLoc.getZip());
			}
			//SL-15646.checking whether pick-up location is available
			if (StringUtils.isBlank(checkers[0])&& StringUtils.isBlank(checkers[1])){
				pickUpLocationList.add(new ListItem(SOPDFConstants.NOT_AVAILABLE, SOPDFConstants.FONT_LABEL));
			}else {
				if (null != checkers[0]){
					pickUpLocationList.add(new ListItem(checkers[0], SOPDFConstants.FONT_LABEL));
				}
				if (null != checkers[1]){
					pickUpLocationList.add(new ListItem(checkers[1], SOPDFConstants.FONT_LABEL));
				}
			}		
		} else {
			pickUpLocationList.add(new ListItem(SOPDFConstants.NOT_AVAILABLE, SOPDFConstants.FONT_LABEL));
		}
		
		if (pickupContact != null && StringUtils.isNotBlank(pickupContact.getPhoneNo())) {
			pickUpLocationList.add(new ListItem(new SOPDFUtils.Checker(SOPDFUtils.formatPhoneNumber(pickupContact.getPhoneNo()))
					.prependWith(SOPDFConstants.BLANK_SPACE,SOPDFConstants.BLANK_SPACE), SOPDFConstants.FONT_LABEL));
		}
		SOPDFUtils.addCellToTable(pickUpLocationList, 0, -1, 40, mainTable,tablePickUpLocation);		
	}
	
	/**
	 * @param so
	 * @return
	 */
	private SoLocation getPickupLocation(ServiceOrder so) {
		SoLocation pickupLoc = null;
		java.util.List<Part> parts = so.getParts();
		if (parts != null) {
			for (Part part : parts) {
				pickupLoc = part.getPickupLocation();
				if (pickupLoc != null) {
					break;
				}
			}
		}
		return pickupLoc;
	}
	
	/**
	 * @param so
	 * @return
	 */
	private Contact getPickupContact(ServiceOrder so) {
		Contact pickupContact = null;
		java.util.List<Part> parts = so.getParts();
		if (parts != null) {
			for (Part part : parts) {
				pickupContact = part.getPickupContact();
				if (pickupContact != null) {
					break;
				}
			}
		}
		return pickupContact;
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
	 * @param overviewTable
	 * @param mainTable
	 * @param pdfWriter
	 * @param document
	 * @return
	 * @throws DocumentException
	 * 
	 * Method responsible for adding overview to PDF
	 * table.
	 * 
	 */
	@SuppressWarnings("deprecation")
	private PdfPTable addOverview(PdfPTable overviewTable, PdfPTable mainTable, PdfWriter pdfWriter) throws DocumentException {
		
		logger.info("SODescriptionPDFTemplateImpl : addOverview");
		List serviceOverviewList = new List(false);
		List serviceOverviewList2 = new List(false);
		Image image=null;
		try {
			image =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_OVERVIEW_ICON_FILEPATH).getURL());
			
			image.setAbsolutePosition(100, 100);
			serviceOverviewList.setListSymbol(new Chunk(image,-4, 0));
			serviceOverviewList2.setListSymbol(new Chunk(image,-4, 0));
			
			ListItem item =new ListItem();
			item.setFont(SOPDFConstants.FONT_LABEL_BIG);

			item.add(SOPDFConstants.SO_OVERVIEW);
			serviceOverviewList.add(item);
			serviceOverviewList2.add(item);
			serviceOverviewList.setListSymbol(SOPDFConstants.ALLIGN_SPACE);
			serviceOverviewList2.setListSymbol(SOPDFConstants.ALLIGN_SPACE);

		} 
		catch (BadElementException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (MalformedURLException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (IOException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		}
		ListItem item2 = new ListItem();

		if (so.getSowTitle() != null) {
			item2.setFont(SOPDFConstants.FONT_LABEL_BOLD);
			item2.add(so.getSkill().getNodeName()+SOPDFConstants.SPACE+SOPDFConstants.ARROW+SOPDFConstants.SPACE+(so.getSowTitle()));
			item2.add(SOPDFConstants.NEW_LINE_FEED);
		}
		
		serviceOverviewList.add(item2);
		serviceOverviewList2.add(item2);
		item2 =new ListItem();
		item2.setFont(SOPDFConstants.FONT_LABEL);

		
		if(StringUtils.isNotEmpty(so.getSowDs())){
	
			String overview = so.getSowDs();
			//SL-20728
			List description = null;
			try{
				//replace html character entities
				overview = SOPDFUtils.getHtmlContent(overview);
				//Convert the html content to rich text	
				description = SOPDFUtils.getRichText(overview, SOPDFConstants.FONT_LABEL, null, SOPDFConstants.ALLIGN_SPACE);
			}catch(Exception e){
				logger.error("Error while converting rich text for " + SOPDFConstants.OVERVIEW + e);
				description = null;
			}
			if(null != description && !description.isEmpty()){
				serviceOverviewList.add(description);
			}
			else{
				overview = so.getSowDs();
				overview =overview.replaceFirst("<p>", "");
				overview = overview.replaceAll("</p><p>","\n");
				overview = overview.replaceAll("<p>","\n");
				overview = overview.replaceAll("</p>","\n");
				overview = overview.replaceAll("<BR />","\n");				
				overview = overview.replaceAll("&nbsp;"," ");
				
				//replace html character entities
				overview = ServiceLiveStringUtils.removeHTML(overview);

				item2.add(overview);
				serviceOverviewList.add(item2);
			}
		}
		serviceOverviewList2.add(SOPDFConstants.NEW_LINE);

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setColspan(4);
		cell.addElement(serviceOverviewList);
		overviewTable.addCell(cell);
		overviewTable.setWidthPercentage(100);
		
		PdfPCell cell2 = new PdfPCell();
		cell2.setBorder(0);
		cell2.setColspan(4);
		cell2.addElement(serviceOverviewList2);
		mainTable.addCell(cell2);
		
		if(!pdfWriter.fitsPage(mainTable)){
			overviewTable.setKeepTogether(true);
		}
		
		return overviewTable;
	
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
			if (so.getServiceLocation().getLocnClassDesc() != null) {
				serviceLocationList.add(new ListItem(so.getServiceLocation().getLocnClassDesc(), SOPDFConstants.FONT_ITALIC));
			}
			if (so.getServiceContact() != null && so.getServiceContact().getBusinessName() != null) {
				serviceLocationList.add(new ListItem(so.getServiceContact().getBusinessName(), SOPDFConstants.FONT_LABEL));
			}
			if (so.getServiceContact() != null && so.getServiceContact().getFirstName() != null && so.getServiceContact().getLastName() != null) {
				if(this.mainPageTitle.equalsIgnoreCase(SOPDFConstants.CUSTOMER_COPY)){
					serviceLocationList.add(new ListItem(so.getServiceContact().getLastName() + SOPDFConstants.COMMA + SOPDFConstants.SPACE + so.getServiceContact().getFirstName(), SOPDFConstants.FONT_LABEL_BOLD));
			
				}
				else{
					serviceLocationList.add(new ListItem(so.getServiceContact().getLastName() + SOPDFConstants.COMMA + SOPDFConstants.SPACE + so.getServiceContact().getFirstName(), SOPDFConstants.FONT_LABEL));
				}
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
				if(this.mainPageTitle.equalsIgnoreCase(SOPDFConstants.CUSTOMER_COPY)){
					
					item.add(new Phrase(SOPDFConstants.SPACE+SOPDFConstants.SPACE+SOPDFUtils.formatPhoneNumber(so.getServiceContact().getPhoneNo()), SOPDFConstants.FONT_LABEL_BOLD));
					addPhoneType(item,so.getServiceContact().getPhoneClassId());
					serviceLocationList.add(item);
					serviceLocationList.setListSymbol(SOPDFConstants.SMALL_SPACE);
					phoneAdded = true;
				}
				else{
					item.add(new Phrase(SOPDFConstants.SPACE+SOPDFConstants.SPACE+SOPDFUtils.formatPhoneNumber(so.getServiceContact().getPhoneNo()), SOPDFConstants.FONT_LABEL));
					addPhoneType(item,so.getServiceContact().getPhoneClassId());
					serviceLocationList.add(item);
					serviceLocationList.setListSymbol(SOPDFConstants.SMALL_SPACE);
					phoneAdded = true;
				}
			
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

				if(this.mainPageTitle.equalsIgnoreCase(SOPDFConstants.CUSTOMER_COPY)){
				item.add(SOPDFConstants.APPOINTMENT_DATE);
				}
				else{
					item.add(SOPDFConstants.APPOINTMENT_DATE);
				}
				serviceDateList.add(item);
		
		serviceDateList.setListSymbol(SOPDFConstants.BIG_SPACE);
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
			list.setLeading(12);
			list.add(new Phrase(SOPDFConstants.SPACE+so.getAcceptedResource().getResourceContact().getLastName() + SOPDFConstants.COMMA  + SOPDFConstants.SPACE + so.getAcceptedResource().getResourceContact().getFirstName() + SOPDFConstants.SPACE ,SOPDFConstants.FONT_LABEL_BOLD));
			list.add(new Phrase(SOPDFConstants.USER_ID + so.getAcceptedResourceId(), SOPDFConstants.FONT_LABEL));
			providerList.setListSymbol(SOPDFConstants.SMALL_SPACE+SOPDFConstants.SPACE);

			providerList.add(list);
		}
		if (so.getAcceptedVendorId() != null) {
			providerList.setListSymbol(SOPDFConstants.BIG_SPACE);
			providerList.add(new ListItem(SOPDFConstants.COMPANY_ID + so.getAcceptedVendorId(), SOPDFConstants.FONT_LABEL));
			providerList.add(new ListItem(SOPDFConstants.SPACE, SOPDFConstants.FONT_LABEL));
		}

		Image provImage = null;	
		
		if(so.getWfStateId() >= OrderConstants.ACCEPTED_STATUS){
			
			if(mainPageTitle.equalsIgnoreCase(SOPDFConstants.CUSTOMER_COPY) && SOPDFConstants.CONSTELLATION_HOME_BUYER_ID != so.getBuyerId()){
				try{
					if((null == so.getAssignmentType() || (null != so.getAssignmentType() && so.getAssignmentType().equals(SOPDFConstants.ASSIGNMENT_TYPE_PROVIDER)))&& so.getAcceptedProviderDocument()!= null && so.getAcceptedProviderDocument().getDocDetails()!=null && so.getAcceptedProviderDocument().getDocDetails().getBlobBytes()!=null && null != so.getAcceptedResourceId()){
						provImage = Image.getInstance(so.getAcceptedProviderDocument().getDocDetails().getBlobBytes());
					}
										
				}catch (BadElementException e) {
					logger.error("Unexpected error while generating Service Order PDF", e);

				} catch (MalformedURLException e) {
					logger.error("Unexpected error while generating Service Order PDF", e);

				} catch (IOException e) {
					logger.error("Unexpected error while generating Service Order PDF", e);

				}
			}
		}
		SOPDFUtils.addListAndImageToTable(providerList,provImage, 0, -1, 40, mainTable,providerTable);
		
	}

	
	/**
	 * @param detailsTable
	 * 
	 * Method responsible for adding custom references to the so Details table
	 * 
	 */
	private void addCustomRef(PdfPTable detailsTable) {
		PdfPTable customerRef = new PdfPTable(1);
		List buyerCustomerRefList = new List(false);
		buyerCustomerRefList.setListSymbol(SOPDFConstants.SPACE);
		if (so.getCustomRefs() != null) {
			java.util.List<ServiceOrderCustomRefVO> customReflist = so.getCustomRefs();
			if (!customReflist.isEmpty()) {
				buyerCustomerRefList.add(new ListItem(SOPDFConstants.BUYER_CUSTOM_REF, SOPDFConstants.FONT_HEADER));
				for (ServiceOrderCustomRefVO vo : customReflist) {
					String refValue = vo.getRefValue();
					if (StringUtils.isNotBlank(refValue) && vo.getPdfRefInd().equals(SOPDFConstants.ONE) && !vo.isPrivateInd()) {
						buyerCustomerRefList.add(new ListItem(SOPDFUtils.append(vo.getRefType(), refValue, SOPDFConstants.COLON + SOPDFConstants.SPACE), SOPDFConstants.FONT_LABEL));
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
	 * @return
	 * 
	 * Method responsible for adding custom references to the so Details table
	 */
	private List addCustomRef() {
		List buyerCustomerRefList = new List(false);
		ListItem item = new ListItem();
		Image image=null;
		try {
			if (!(so.getCustomRefs().isEmpty())) {
				image =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_INFO_ICON_FILEPATH).getURL());
				image.setAbsolutePosition(100, 100);
				buyerCustomerRefList.setListSymbol(new Chunk(image,-1, 0));

				item.add(SOPDFConstants.SPACE);
				item.setFont(SOPDFConstants.FONT_LABEL_BIG);
				item.add(SOPDFConstants.ADDITIONAL_INFO);
				item.add(SOPDFConstants.NEW_LINE_FEED);
				buyerCustomerRefList.add(item);
				buyerCustomerRefList.setListSymbol(SOPDFConstants.CENTER_SPACE);
			}
		} 
		catch (BadElementException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (MalformedURLException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (IOException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		}

		if (so.getCustomRefs() != null) {
			java.util.List<ServiceOrderCustomRefVO> customReflist = so.getCustomRefs();
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
					item.add(SOPDFUtils.append(vo.getRefType(), refValue, SOPDFConstants.COLON + SOPDFConstants.SPACE));
					if(count != size){
						item.add(SOPDFConstants.COMMA+SOPDFConstants.SPACE);
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
		
		logger.info("SODescriptionPDFTemplateImpl : addTaskAndPartList");
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

			item.add(SOPDFConstants.SERVICE_ORDER_DETAILS);
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
		int taskCount = 0;
		

		serviceTaskList2.add(SOPDFConstants.NEW_LINE);
		serviceTaskList2.add(SOPDFConstants.NEW_LINE);
		serviceTaskList2.add(SOPDFConstants.NEW_LINE);
		
		java.util.List<ServiceOrderTask> tasks = so.getTasks();		
		if (tasks != null && !tasks.isEmpty()) {
			for (ServiceOrderTask task : tasks) {
				++taskCount;
				ListItem item2 = new ListItem();
				item2.setFont(SOPDFConstants.FONT_LABEL);
				serviceTaskList.add(item2);
				item2.add(new Phrase(SOPDFConstants.TASK+taskCount,SOPDFConstants.FONT_LABEL_BOLD));
				item2.add(new Phrase(SOPDFConstants.COLON + SOPDFConstants.SPACE + (!StringUtils.isEmpty(task.getServiceType())? task.getServiceType():SOPDFConstants.BLANK)+SOPDFConstants.SPACE+SOPDFConstants.ARROW+SOPDFConstants.SPACE+(!StringUtils.isEmpty(task.getTaskName())? task.getTaskName():SOPDFConstants.BLANK)));
				if (!StringUtils.isBlank(task.getTaskComments())) { 
					//SL-20728
					List comments = null;
					String taskComment = task.getTaskComments();
					try{
						//replace html character entities
						taskComment = SOPDFUtils.getHtmlContent(taskComment);
						//Convert the html content to rich text	
						comments = SOPDFUtils.getRichText(taskComment, SOPDFConstants.FONT_LABEL, null, SOPDFConstants.ALLIGN_SPACE);
					}catch(Exception e){
						logger.error("Error while converting rich text for " + SOPDFConstants.SERVICE_ORDER_DETAILS + e);
						comments = null;
					}
					if(null != comments && !comments.isEmpty()){
						serviceTaskList.add(comments);
					}
					else{
						taskComment = task.getTaskComments();
						//Remove html tags
						taskComment = ServiceLiveStringUtils.removeHTML(taskComment);
						item2 = new ListItem();
						item2.setFont(SOPDFConstants.FONT_LABEL);
						item2.add(taskComment);
						serviceTaskList.add(item2);
					}
				}
				
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
		if(mainPageTitle.equalsIgnoreCase(SOPDFConstants.PROVIDER_COPY)){
			descTable = createNewTable();
			addPartsToTable(descTable,document, pdfWriter);
		}
		/*// to add invoicee Parts in customer copy
		if(mainPageTitle.equalsIgnoreCase(SOPDFConstants.CUSTOMER_COPY) && SOPDFConstants.BUYER_INHOME_ID == so.getBuyerId().intValue()){
			descTable = createNewTable();
			addCustomerInvoicePartsToTable(descTable,document, pdfWriter);
		}*/
		// to add invoicee Parts in provider copy
		if(mainPageTitle.equalsIgnoreCase(SOPDFConstants.PROVIDER_COPY) && SOPDFConstants.BUYER_INHOME_ID == so.getBuyerId().intValue()){
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
		
		if (so.getInvoiceParts()!=null && !so.getInvoiceParts().isEmpty()) {
			List servicePartList = new List(false);
			List serviceInvoiceList = new List(false);

			PdfPCell cell1 = new PdfPCell();
			cell1.setColspan(1);

			float[] colsWidth = {10f}; 
			PdfPTable partTable = new PdfPTable(colsWidth);

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
					if(StringUtils.isNotBlank(part.getPartStatus())){
						item2.add(new Phrase(part.getPartStatus()+SOPDFConstants.SMALL_SPACE,SOPDFConstants.FONT_LABEL_BOLD));

					}
					else{
						item2.add(new Phrase(SOPDFConstants.BIG_SPACE));
					}
					
					if(StringUtils.isNotBlank(part.getPartNo())){
						item2.add(new Phrase(part.getPartNo(),SOPDFConstants.FONT_LABEL_BOLD));

					}
					else{
						item2.add(SOPDFConstants.BIG_SPACE);
					}
					if(StringUtils.isNotBlank(part.getDescription())){
						item2.add(new Phrase(SOPDFConstants.SPACE+SOPDFConstants.HYPHEN+SOPDFConstants.SPACE+part.getDescription()+SOPDFConstants.SPACE+SOPDFConstants.HYPHEN+SOPDFConstants.SPACE,SOPDFConstants.FONT_LABEL));
					}
					else{
						item2.add(SOPDFConstants.BIG_SPACE);
					}

					item2.add(new Phrase(SOPDFConstants.PART_QTY+SOPDFConstants.SPACE+(new Integer(part.getQty())).toString(),SOPDFConstants.FONT_LABEL));
					
					
					count ++;
					servicePartList.add(item2);
				}
				if(count ==1){
					descTable.setKeepTogether(true);
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
	 * @param descTable
	 * @param document
	 * @param pdfWriter
	 * @throws DocumentException
	 * to add installed parts in customer copy
	 * this code is not used anywhere since invoice parts section need not be shown in customer copy
	 * 
	 */
	private void addCustomerInvoicePartsToTable(PdfPTable descTable, Document document,
			PdfWriter pdfWriter) throws DocumentException {
		
		if (so.getInvoiceParts()!=null && !so.getInvoiceParts().isEmpty()) {
			List servicePartList = new List(false);
			List serviceInvoiceList = new List(false);

			PdfPCell cell1 = new PdfPCell();
			cell1.setColspan(1);

			float[] colsWidth = {10f}; 
			PdfPTable partTable = new PdfPTable(colsWidth);

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

			
			partTable.getDefaultCell().setBackgroundColor(Color.GRAY);
			partTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			servicePartList.setListSymbol(SOPDFConstants.ALLIGN_SPACE);

			java.util.List<ProviderInvoicePartsVO> parts = so.getInvoiceParts();
			java.util.List<ProviderInvoicePartsVO> invocieParts = new ArrayList<ProviderInvoicePartsVO>();

			// to filter only installed parts
						for(ProviderInvoicePartsVO invoicePartsVO : parts){
							if(null!= invoicePartsVO && StringUtils.isNotBlank(invoicePartsVO.getPartStatus()) && (SOPDFConstants.PART_INSTALLED).equals(invoicePartsVO.getPartStatus())){
								invocieParts.add(invoicePartsVO);
							}
						}
			Collections.sort(invocieParts, new Comparator<ProviderInvoicePartsVO>(){
				public int compare(ProviderInvoicePartsVO p1, ProviderInvoicePartsVO p2) {
					String partName1 = p1.getDescription();
					String partName2 = p2.getDescription();
					String partNum1 = p1.getPartNo();
					String partNum2 = p2.getPartNo();
					if(null!=partNum1 && null!=partNum2 && partNum1.compareToIgnoreCase(partNum2)!=0){
		            	return partNum1.compareToIgnoreCase(partNum2);
		            }
		            else{
		            	return partName1.compareToIgnoreCase(partName2);

		            }
		        }
			});
			partTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			partTable.getDefaultCell().setBorderWidth(0);
			
			if (!invocieParts.isEmpty()) {
				int count =0;
				for (ProviderInvoicePartsVO part : invocieParts) {
					ListItem item2 = new ListItem();
					item2.setFont(SOPDFConstants.FONT_LABEL);					
					
					if(StringUtils.isNotBlank(part.getPartNo())){
						item2.add(new Phrase(part.getPartNo()+SOPDFConstants.SPACE+SOPDFConstants.HYPHEN+SOPDFConstants.SPACE,SOPDFConstants.FONT_LABEL));

					}
					else{
						item2.add(SOPDFConstants.BIG_SPACE);
					}
					if(StringUtils.isNotBlank(part.getDescription())){
						item2.add(new Phrase(part.getDescription()+SOPDFConstants.SPACE+SOPDFConstants.HYPHEN+SOPDFConstants.SPACE,SOPDFConstants.FONT_LABEL));
					}
					else{
						item2.add(SOPDFConstants.BIG_SPACE);
					}

					item2.add(new Phrase(SOPDFConstants.PART_QTY+SOPDFConstants.SPACE+(new Integer(part.getQty())).toString(),SOPDFConstants.FONT_LABEL));
					
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
	 * @param descTable
	 * @param mainTable
	 * @param document
	 * @param pdfWriter
	 * @throws DocumentException
	 */
	private void addPartsToTable(PdfPTable descTable,
			Document document, PdfWriter pdfWriter) throws DocumentException {
		
		if (so.getParts()!=null) {
			List servicePartList = new List(false);
			
			PdfPCell cell1 = new PdfPCell();
			cell1.setColspan(6);

			float[] colsWidth = {4f, 6f,3f,8f,4f,3f}; 
			PdfPTable partTable = new PdfPTable(colsWidth);


			ListItem item = new ListItem();
			Image image=null;
			try {
				image =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_PARTS_ICON_FILEPATH).getURL());
				servicePartList.setListSymbol(new Chunk(image, -7, 0));
				item.setFont(SOPDFConstants.FONT_LABEL_BIG);
				item.add(SOPDFConstants.PARTS_AND_MATERIALS);
				item.add(SOPDFConstants.NEW_LINE_FEED+SOPDFConstants.NEW_LINE_FEED);
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
			cell1.setColspan(6);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);

			
			partTable.getDefaultCell().setBackgroundColor(Color.GRAY);
			partTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			
			partTable.addCell(new Phrase(SOPDFConstants.PART_PC, SOPDFConstants.FONT_LABEL_BOLD));
			partTable.addCell(new Phrase(SOPDFConstants.MANUFACTURER_PC, SOPDFConstants.FONT_LABEL_BOLD));
			partTable.addCell(new Phrase(SOPDFConstants.MODEL_PC, SOPDFConstants.FONT_LABEL_BOLD));
			partTable.addCell(new Phrase(SOPDFConstants.DESCRIPTION_PC, SOPDFConstants.FONT_LABEL_BOLD));
			partTable.addCell(new Phrase(SOPDFConstants.PROVIDED_BY_PC, SOPDFConstants.FONT_LABEL_BOLD));
			partTable.addCell(new Phrase(SOPDFConstants.QUANTITY_PC, SOPDFConstants.FONT_LABEL_BOLD));
			
			java.util.List<Part> parts = so.getParts();
			Collections.sort(parts, new Comparator<Part>(){
				public int compare(Part p1, Part p2) {
					String manufacturer1 = p1.getManufacturer().trim();
					String modelNo1 = p1.getModelNumber().trim();
					String manufacturer2 = p2.getManufacturer().trim();
					String modelNo2 = p2.getModelNumber().trim();
					if(manufacturer1.compareToIgnoreCase(manufacturer2)!=0){
		            	return manufacturer1.compareToIgnoreCase(manufacturer2);
		            }
		            else{
		            	return modelNo1.compareToIgnoreCase(modelNo2);

		            }
		        }
			});
			if (!parts.isEmpty()) {
				int count =0;
				for (Part part : parts) {
					count++;
					if(count%2 ==0){
						partTable.getDefaultCell().setBackgroundColor(Color.WHITE);
					}
					else{
						partTable.getDefaultCell().setBackgroundColor(Color.LIGHT_GRAY);
					}
					partTable.addCell(new Phrase(Integer.toString(count),SOPDFConstants.FONT_LABEL));
					if(StringUtils.isNotBlank(part.getManufacturer())){
						partTable.addCell(new Phrase(part.getManufacturer(),SOPDFConstants.FONT_LABEL));

					}
					else{
						partTable.addCell(SOPDFConstants.BIG_SPACE);
					}
					if(StringUtils.isNotBlank(part.getModelNumber())){
						partTable.addCell(new Phrase(part.getModelNumber(),SOPDFConstants.FONT_LABEL));

					}
					else{
						partTable.addCell(SOPDFConstants.BIG_SPACE);
					}
					if(StringUtils.isNotBlank(part.getPartDs())){
						partTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
						partTable.addCell(new Phrase(part.getPartDs(),SOPDFConstants.FONT_LABEL));

					}
					else{
						partTable.addCell(SOPDFConstants.BIG_SPACE);
					}
					
					partTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

					partTable.addCell(new Phrase(SOPDFConstants.BUYER_PART,SOPDFConstants.FONT_LABEL));

					if(StringUtils.isNotBlank(part.getQuantity())){
						partTable.addCell(new Phrase(part.getQuantity(),SOPDFConstants.FONT_LABEL));

					}
					else{
						partTable.addCell(SOPDFConstants.BIG_SPACE);
					}
					
					if(count ==1){
						if(!pdfWriter.fitsPage(partTable)){
							descTable.setKeepTogether(true);
						}
					}
				}

				PdfPCell cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.addElement(servicePartList);
				cell.addElement(partTable);
				cell.setBorderWidth(0);
				cell.setColspan(4);

				descTable.addCell(cell);
				descTable.setWidthPercentage(100);


				
				document.add(descTable);

			}
		}
		if(SOPDFConstants.BUYER_INHOME_ID != so.getBuyerId().intValue()){
			descTable =createNewTable();
			PdfPTable mainTable = createNewMainTable();
			addBuyerTCToTable(descTable,mainTable,document,pdfWriter);	
		}
		
	}
			
	/**
	 * @param descTable
	 * @param mainTable
	 * @param document
	 * @param pdfWriter
	 * @throws DocumentException
	 */
	private void addBuyerTCToTable(PdfPTable descTable, PdfPTable mainTable,
			Document document, PdfWriter pdfWriter) throws DocumentException {
		
		logger.info("SODescriptionPDFTemplateImpl : addBuyerTCToTable");
		List buyerTCList = new List(false);

		List buyerTCList2 = new List(false);
		buyerTCList2.setListSymbol(SOPDFConstants.SPACE);
		
		PdfPCell cell2 = new PdfPCell();
		PdfPCell cell = new PdfPCell();
		
		ListItem item = new ListItem();
		ListItem txtItem = new ListItem();
		Image image=null;
		try {
				image =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_BUYER_TC_ICON_FILEPATH).getURL());
				image.setAbsolutePosition(100, 100);
				buyerTCList.setListSymbol(new Chunk(image, -7, 0));
				buyerTCList2.setListSymbol(new Chunk(image, -7, 0));
				
				item.setFont(SOPDFConstants.FONT_LABEL_BIG);
				item.add(SOPDFConstants.BUYER_T_C);
				
				buyerTCList.add(item);
				buyerTCList2.add(item);
				buyerTCList.setListSymbol(SOPDFConstants.SMALL_SPACE);
				buyerTCList2.setListSymbol(SOPDFConstants.SMALL_SPACE);


			} 
		catch (BadElementException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (MalformedURLException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (IOException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		}
		if(StringUtils.isNotEmpty(so.getBuyerTermsCond())){
			//SL-20728
			List buyerTerms = null;
			String terms = so.getBuyerTermsCond();
			try{
				//replace html character entities
				terms = SOPDFUtils.getHtmlContent(terms);
				//Convert the html content to rich text	
				buyerTerms = SOPDFUtils.getRichText(terms, SOPDFConstants.FONT_LABEL, null, SOPDFConstants.ALLIGN_SPACE);
			}catch(Exception e){
				logger.error("Error while converting rich text for " + SOPDFConstants.BUYER_T_C + e);
				buyerTerms = null;
			}
			if(null != buyerTerms && !buyerTerms.isEmpty()){
				buyerTCList.add(buyerTerms);
			}
			else{
				terms = so.getBuyerTermsCond();
				//Remove the html tags
				terms = ServiceLiveStringUtils.removeHTML(terms);
				txtItem.add(new Phrase(terms,SOPDFConstants.FONT_LABEL));
				buyerTCList.add(txtItem);
			}
		}
		buyerTCList.add(SOPDFConstants.NEW_LINE_FEED);
		cell.addElement(buyerTCList);
		cell.setBorder(0);
		cell.setColspan(4);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);

		item = new ListItem();
		item.add(SOPDFConstants.BIG_SPACE);
		buyerTCList2.add(item);
		
		descTable.addCell(cell);
		descTable.setWidthPercentage(100);		
		descTable.setHorizontalAlignment(Element.ALIGN_LEFT);		
		
		cell2.setBorder(0);
		cell2.setColspan(4);
		cell2.addElement(buyerTCList2);
		mainTable.addCell(cell2);
		
		if(!pdfWriter.fitsPage(mainTable)){
			descTable.setKeepTogether(true);
		}
		document.add(descTable);
		descTable =createNewTable();
		mainTable = createNewMainTable();
		addArrivalandDepartureInstr(descTable,mainTable,document,pdfWriter);	
	}


	/**
	 * @param descTable
	 * @param mainTable
	 * @param document
	 * @param pdfWriter
	 * @throws DocumentException
	 */
	private void addArrivalandDepartureInstr(PdfPTable descTable,
			PdfPTable mainTable, Document document, PdfWriter pdfWriter) throws DocumentException {

		PdfPCell headingCell1 = new PdfPCell();
		headingCell1.setBorder(0);
		headingCell1.setColspan(4);
		headingCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		descTable.getDefaultCell().setLeading(12, 0);

		PdfPCell headingCellNewLine = new PdfPCell();
		headingCellNewLine.setBorder(0);
		headingCellNewLine.setColspan(4);
		List headingCellNewLineList = new List(false);
		headingCellNewLineList.add(SOPDFConstants.BIG_SPACE);
		headingCellNewLineList.add(SOPDFConstants.BIG_SPACE);
		headingCellNewLine.addElement(headingCellNewLineList);

		Image image1 =null;

		List arrivalAndDepartInstr = new List(false);

		try {
			image1 =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_ARRIVAL_DEPARTURE_ICON_FILEPATH).getURL());
			image1.setAbsolutePosition(10,10);
			arrivalAndDepartInstr.setListSymbol(new Chunk(image1,-6, 0));

		}  
		catch (BadElementException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (MalformedURLException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (IOException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		}
		ListItem arrivalDeparture = new ListItem();
		arrivalDeparture.add(new Phrase(SOPDFConstants.ARRIVAL_DEPARTURE, SOPDFConstants.FONT_LABEL_BIG));
		if(null != so.getAssignmentType() && so.getAssignmentType().equals(SOPDFConstants.ASSIGNMENT_TYPE_FIRM)){
			arrivalDeparture.add(new Phrase(SOPDFConstants.PROVIDER_UNASSIGNED_WARNING_1, SOPDFConstants.FONT_RED));
		}
		arrivalAndDepartInstr.add(arrivalDeparture);

		headingCell1.addElement(arrivalAndDepartInstr);
		descTable.addCell(headingCell1);



		// Arrival Instructions
		PdfPCell arrivalListCell = new PdfPCell();
		arrivalListCell.setBorder(0);
		arrivalListCell.setColspan(2);
		arrivalListCell.setLeading(12, 0);
		arrivalListCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell departListCell = new PdfPCell();
		departListCell.setBorder(0);
		departListCell.setColspan(2);
		departListCell.setLeading(12, 0);

		arrivalListCell.addElement(new Phrase(SOPDFConstants.CENTER_SPACE+SOPDFConstants.UP_ON_ARRIVAL, SOPDFConstants.FONT_LABEL));

		List uponArrivalList = new List(false);
		uponArrivalList.setListSymbol(SOPDFConstants.SMALL_SPACE+SOPDFConstants.SPACE);
		List uponDepartureList = new List(false);
		uponDepartureList.setListSymbol(SOPDFConstants.SMALL_SPACE+SOPDFConstants.SPACE);

		ListItem item = new ListItem();
		item.add(new Phrase(SOPDFConstants.ARRIVAL_STEP_1_1, SOPDFConstants.FONT_LABEL));
		item.add(new Phrase(SOPDFConstants.ARRIVAL_STEP_1_2, SOPDFConstants.FONT_LABEL));
		uponArrivalList.add(item);
		uponDepartureList.add(item);

		ListItem list = new ListItem();
		list.add(new Phrase(SOPDFConstants.ARRIVAL_STEP_2, SOPDFConstants.FONT_LABEL));
		if(null != so.getAcceptedResourceId() && ((null != so.getAssignmentType() && so.getAssignmentType().equals(SOPDFConstants.ASSIGNMENT_TYPE_PROVIDER)))){
			list.add(new Phrase(Integer.toString(so.getAcceptedResourceId()), SOPDFConstants.FONT_LABEL));
		}else if ((null != so.getAssignmentType() && so.getAssignmentType().equals(SOPDFConstants.ASSIGNMENT_TYPE_FIRM))){
			list.add(new Phrase(SOPDFConstants.UNASSIGNED, SOPDFConstants.FONT_RED));
		}
		else{
			list.add(new Phrase(SOPDFConstants.UNASSIGNED, SOPDFConstants.FONT_LABEL));
		}
		uponArrivalList.add(list);
		uponDepartureList.add(list);
		
		list = new ListItem();
		list.add(new Phrase(SOPDFConstants.ARRIVAL_STEP_3, SOPDFConstants.FONT_LABEL));
		list.add(new Phrase(so.getSoId(), SOPDFConstants.FONT_LABEL));
		uponArrivalList.add(list);
		uponDepartureList.add(list);		
		
		item = new ListItem();
		item.add(new Phrase(SOPDFConstants.ARRIVAL_STEP_4, SOPDFConstants.FONT_LABEL));
		uponArrivalList.add(item);
		uponArrivalList.add(SOPDFConstants.NEW_LINE);

		arrivalListCell.addElement(uponArrivalList);

		// Departure Instructions
	
		departListCell.addElement(new Phrase(SOPDFConstants.ALLIGN_SPACE+SOPDFConstants.WHEN_DEPARTING, SOPDFConstants.FONT_LABEL));

		item = new ListItem();
		item.add(new Phrase(SOPDFConstants.DEPARTURE_STEP_1_1, SOPDFConstants.FONT_LABEL));
		item.add(new Phrase(SOPDFConstants.DEPARTURE_STEP_1_2, SOPDFConstants.FONT_LABEL_BOLD));
		item.add(new Phrase(SOPDFConstants.OR+SOPDFConstants.SPACE, SOPDFConstants.FONT_LABEL));
		item.add(new Phrase(SOPDFConstants.DEPARTURE_STEP_1_3, SOPDFConstants.FONT_LABEL_BOLD));
		uponDepartureList.add(item);
		uponDepartureList.add(SOPDFConstants.NEW_LINE);
		item = new ListItem();
		item.add(new Phrase(SOPDFConstants.DEPARTURE_STEP_2, SOPDFConstants.FONT_LABEL));
		uponDepartureList.add(item);

		departListCell.addElement(uponDepartureList);
		descTable.addCell(arrivalListCell);
		descTable.addCell(departListCell);
		
		PdfPCell newLineCell = new PdfPCell();
		newLineCell.setBorder(0);
		newLineCell.setColspan(4);
		List newLineList = new List(false);
		newLineList.add(SOPDFConstants.BIG_SPACE);
		newLineList.add(SOPDFConstants.BIG_SPACE);
		newLineCell.addElement(headingCellNewLineList);
		
		
		mainTable.addCell(headingCell1);
		mainTable.addCell(headingCellNewLine);
		
		if(!pdfWriter.fitsPage(mainTable)){
			descTable.setKeepTogether(true);
		}
		descTable.setWidthPercentage(100);		
		descTable.setHorizontalAlignment(Element.ALIGN_LEFT);	
		document.add(descTable);
		descTable= null;
		descTable = createNewTable();
		mainTable = createNewMainTable();
		addContactInfo(descTable,mainTable,document,pdfWriter);

	}

	
	
	/**
	 * @param descTable
	 * @param mainTable
	 * @param document
	 * @param pdfWriter
	 * @throws DocumentException
	 */
	private void addContactInfo(PdfPTable descTable,PdfPTable mainTable, Document document, PdfWriter pdfWriter) throws DocumentException  {
		//contact info
		PdfPCell headingCell2 = new PdfPCell();
		headingCell2.setBorder(0);
		headingCell2.setColspan(4);
		headingCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell headingCellNewLine = new PdfPCell();
		headingCellNewLine.setBorder(0);
		headingCellNewLine.setColspan(4);
		List headingCellNewLineList = new List(false);
		headingCellNewLineList.add(SOPDFConstants.BIG_SPACE);
		headingCellNewLineList.add(SOPDFConstants.BIG_SPACE);
		headingCellNewLine.addElement(headingCellNewLineList);
		List contactInfoList1 =new List(false);
		Image image2 =null;

		try {

			image2 =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_PHONE_ICON_FILEPATH).getURL());
			image2.setAbsolutePosition(10,10);
			contactInfoList1.setListSymbol(new Chunk(image2,-6, 0));

		}  
		catch (BadElementException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (MalformedURLException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (IOException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		}
		contactInfoList1.add(new ListItem(SOPDFConstants.SPACE+SOPDFConstants.SO_SUPPORT_NO, SOPDFConstants.FONT_LABEL_BIG));
		headingCell2.addElement(contactInfoList1);
		descTable.addCell(headingCell2);

		List contactInfoList2 =new List(false);
		List contactInfoList3 =new List(false);

		PdfPCell contactInfoCell1 = new PdfPCell();
		contactInfoCell1.setBorder(0);
		contactInfoCell1.setColspan(2);
		PdfPCell contactInfoCell2 = new PdfPCell();
		contactInfoCell2.setBorder(0);
		contactInfoCell2.setColspan(2);

		contactInfoList2.setListSymbol(SOPDFConstants.ALLIGN_SPACE);
		contactInfoList3.setListSymbol(SOPDFConstants.SMALL_SPACE+SOPDFConstants.SPACE);
		ListItem item = new ListItem();

		item.add(new Phrase(SOPDFConstants.BUYER_CONTACT,SOPDFConstants.FONT_LABEL));
		
		if(null != so.getAltBuyerResource() && 
				null != so.getAltBuyerResource().getBuyerResContact() &&
				StringUtils.isNotBlank(so.getAltBuyerResource().getBuyerResContact().getPhoneNo())){
			item
			.add(new Phrase(SOPDFConstants.LARGE_SPACE+SOPDFConstants.CENTER_SPACE+SOPDFUtils.formatPhoneNumber(so.getAltBuyerResource().getBuyerResContact().getPhoneNo()),
					SOPDFConstants.FONT_LABEL_BOLD));
			item.add(new Phrase(SOPDFConstants.WORK,SOPDFConstants.FONT_LABEL));

		}else{
			item
			.add(new Phrase(SOPDFConstants.LARGE_SPACE+SOPDFConstants.BIG_SPACE, SOPDFConstants.FONT_LABEL_BOLD));
		}		
		contactInfoList2.add(item);

		item =new ListItem();
		if(null!=so.getAltBuyerResource()&& so.getAltBuyerResource().getBuyerResContact() != null && StringUtils.isNotBlank(so.getAltBuyerResource().getBuyerResContact().getCellNo())){
			item.add(new Phrase(SOPDFConstants.LARGE_SPACE+SOPDFConstants.CENTER_SPACE +SOPDFConstants.LARGE_SPACE +SOPDFConstants.LARGE_SPACE +SOPDFConstants.LARGE_SPACE+SOPDFUtils.formatPhoneNumber(so.getAltBuyerResource().getBuyerResContact().getCellNo()), SOPDFConstants.FONT_LABEL_BOLD));
			item.add(new Phrase(SOPDFConstants.MOBILE, SOPDFConstants.FONT_LABEL));

		}else{
			item.add(new Phrase(SOPDFConstants.LARGE_SPACE +SOPDFConstants.BIG_SPACE +SOPDFConstants.LARGE_SPACE +SOPDFConstants.LARGE_SPACE +SOPDFConstants.LARGE_SPACE, SOPDFConstants.FONT_LABEL_BOLD));
		}
		contactInfoList2.add(item);


		item = new ListItem();
		item.add(new Phrase(SOPDFConstants.SL_IVR,SOPDFConstants.FONT_LABEL));
		item.add(new Phrase(SOPDFConstants.CENTER_SPACE+SOPDFConstants.BIG_SPACE+SOPDFConstants.SL_IVR_NO,SOPDFConstants.FONT_LABEL_BOLD));
		contactInfoList3.add(item);

		item = new ListItem();
		item.add(new Phrase(SOPDFConstants.SL_SUPPORT,SOPDFConstants.FONT_LABEL));
		item.add(new Phrase(SOPDFConstants.BIG_SPACE+SOPDFConstants.SL_SUPPORT_NO,SOPDFConstants.FONT_LABEL_BOLD));
		contactInfoList3.add(item);

		contactInfoCell1.addElement(contactInfoList2);
		contactInfoCell2.addElement(contactInfoList3);
		contactInfoCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
		contactInfoCell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		mainTable.addCell(headingCell2);
		mainTable.addCell(headingCellNewLine);

		if(!pdfWriter.fitsPage(mainTable)){
			descTable.setKeepTogether(true);
		}
		descTable.setWidthPercentage(100);		
		descTable.setHorizontalAlignment(Element.ALIGN_LEFT);	
		descTable.addCell(contactInfoCell1);
		descTable.addCell(contactInfoCell2);

		document.add(descTable);
	}
	/**
	 * @param notesTable
	 * 
	 * Method responsible for setting notes to the PDF
	 * @param document 
	 * @throws DocumentException 
	 */
	private void addNotesTable(PdfPTable notesTable, Document document) throws DocumentException {

		PdfPTable tableNotes1 = new PdfPTable(1);
		PdfPTable tableNotes2 = new PdfPTable(3);
		
		PdfPTable notesTable1 =createNewMainTable();
		
		PdfPTable tableConstellation = new PdfPTable(1);
		tableConstellation.getDefaultCell().setFixedHeight(20);
		tableConstellation.setTotalWidth(359);
		tableConstellation.setLockedWidth(true);
		

		List notesList = new List(false);
		notesList.setListSymbol(SOPDFConstants.BLANK);
		ListItem item1 = new ListItem();
		ListItem item2 = new ListItem();
		Image image=null;
		Image checkBox=null;
		try {
			image =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_COMMENT_ICON_FILEPATH).getURL());
			item1 =new ListItem(new Chunk(image,-1, 0));
			item1.add(SOPDFConstants.SPACE);
			item1.setFont(SOPDFConstants.FONT_LABEL_BIG);

			item1.add(SOPDFConstants.ADDN_WORK_PERFORMED);
			notesList.add(item1);
			notesList.add(SOPDFConstants.NEW_LINE);


		} 
		catch (BadElementException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (MalformedURLException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (IOException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		}
		
		PdfPCell headingCell = new PdfPCell();
		headingCell.setBorder(0);
		headingCell.addElement(notesList);
		tableNotes1.getDefaultCell().setBorder(0);
		tableNotes1.addCell(headingCell);
		
		// SPM-1340 : Resolution Comments to be appended in PDF
		// Including Resolution Comments in "ADDITIONAL WORK PERFORMED / SERVICE PROVIDER NOTES" in both Customer copy and Provider copy.
		PdfPCell boxCell = new PdfPCell();
		if(mobileIndicator){
			if (this.mainPageTitle.equalsIgnoreCase(SOPDFConstants.CUSTOMER_COPY) 
					&& SOPDFConstants.CONSTELLATION_HOME_BUYER_ID == so.getBuyerId()){
				tableConstellation.addCell(new Phrase(SOPDFConstants.CONSTELLATION_BUYER_SAVINGS,SOPDFConstants.FONT_LABEL_BIG_BOLD));
				boxCell.addElement(tableConstellation);
			}
			boxCell.addElement(new Phrase(so.getResolutionDs(),SOPDFConstants.FONT_LABEL));	
		}
		//SL-20856
		else if (this.mainPageTitle.equalsIgnoreCase(SOPDFConstants.CUSTOMER_COPY) && SOPDFConstants.CONSTELLATION_HOME_BUYER_ID == so.getBuyerId()) {
			tableConstellation.addCell(new Phrase(SOPDFConstants.CONSTELLATION_BUYER_SAVINGS,SOPDFConstants.FONT_LABEL_BIG_BOLD));
			boxCell.addElement(tableConstellation);
		}
		
		boxCell.setBorder(0);
		boxCell.setMinimumHeight(115);
		boxCell.setColspan(2);
		tableNotes2.addCell(boxCell);
		
		PdfPCell notesTableCell1 = new PdfPCell(tableNotes1);
		notesTableCell1.setColspan(4);
		notesTableCell1.setBorder(0);

		PdfPCell boxCel2 = new PdfPCell();
		boxCel2.setBorder(0);
		boxCel2.setMinimumHeight(115);
		boxCel2.setColspan(1);
		boxCel2.setBorder(0);
		boxCel2.setBorderWidthBottom(1);
		boxCel2.setBorderWidthTop(1);
		boxCel2.setBorderWidthLeft(1);
		boxCel2.setBorderWidthRight(1);
		List addNotesList = new List(false);
		try {
			image =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_TIME_ICON_FILEPATH).getURL());
			checkBox=DocumentUtils.getCheckBox();
			image.setAbsolutePosition(100,100);
			addNotesList.setListSymbol(SOPDFConstants.CENTER_SPACE+SOPDFConstants.LARGE_SPACE);
			item1 =new ListItem(new Chunk(image,0, 0));
			item1.add(SOPDFConstants.SPACE);

			item1.setFont(SOPDFConstants.FONT_LABEL_BIG);
			item1.add(SOPDFConstants.TIME_ON_SITE);
			item1.add(SOPDFConstants.NEW_LINE_FEED);
			item1.add(SOPDFConstants.NEW_LINE_FEED);
			checkBox.setAbsolutePosition(0,0);
			item1.add(new Chunk(checkBox,80,-3));
			item1.setFont(SOPDFConstants.FONT_LABEL);
			item1.add(SOPDFConstants.BIG_SPACE+
					  SOPDFConstants.BIG_SPACE+
					  SOPDFConstants.BIG_SPACE+
					  SOPDFConstants.BIG_SPACE+
					  SOPDFConstants.BIG_SPACE+
					  SOPDFConstants.SPACE+
					  SOPDFConstants.SPACE+
					  SOPDFConstants.AM);
            item1.add(SOPDFConstants.NEW_LINE_FEED);
			addNotesList.add(item1);
			addNotesList.setListSymbol(SOPDFConstants.BLANK);

			item2.setFont(SOPDFConstants.FONT_LABEL);
			item2.add(SOPDFConstants.SPACE+SOPDFConstants.SPACE+SOPDFConstants.SPACE+SOPDFConstants.SPACE+SOPDFConstants.SPACE+SOPDFConstants.LARGE_SPACE+SOPDFConstants.ARRIVAL);
			item2.add(new Chunk(checkBox,-3,-3));
			item2.add(SOPDFConstants.PM);
			item2.add(SOPDFConstants.NEW_LINE_FEED);
			item2.add(new Chunk(checkBox,111,-3));
	        item2.add(SOPDFConstants.BIG_SPACE+
					  SOPDFConstants.BIG_SPACE+
					  SOPDFConstants.BIG_SPACE+
					  SOPDFConstants.BIG_SPACE+
					  SOPDFConstants.BIG_SPACE+
					  SOPDFConstants.BIG_SPACE+
					  SOPDFConstants.BIG_SPACE+
					  SOPDFConstants.SPACE+
					  SOPDFConstants.SPACE+
	        		  SOPDFConstants.AM);
			item2.add(SOPDFConstants.NEW_LINE_FEED);
		    item2.add(SOPDFConstants.BIG_SPACE+SOPDFConstants.DEPARTURE);
		
			
			item2.add(new Chunk(checkBox,-2,-3));
		    item2.add(SOPDFConstants.PM);
			addNotesList.add(item2);


		} 
		catch (BadElementException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (MalformedURLException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (IOException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		}



		boxCel2.addElement(addNotesList);
		tableNotes2.addCell(boxCel2);
		
		PdfPCell notesTableCell2 = new PdfPCell(tableNotes2);
		notesTableCell2.setColspan(4);
		notesTableCell2.setBorder(0);
		notesTableCell2.setBorderWidthBottom(1);
		notesTableCell2.setBorderWidthTop(1);
		notesTableCell2.setBorderWidthLeft(1);
		notesTableCell2.setBorderWidthRight(1);
		
		notesTable1.addCell(notesTableCell1);
		
		float[] colsWidth = {5f, 7f};
		PdfPTable notesTable2 = new PdfPTable(colsWidth);
		
		notesTable2.setWidthPercentage(94);
		notesTable2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		notesTable2.addCell(notesTableCell2);


		
		PdfPCell notesCell = new PdfPCell();
		notesCell.setColspan(4);
		notesCell.setBorder(0);
		notesCell.addElement(notesTable1);
		notesCell.addElement(notesTable2);
		
		notesTable.addCell(notesCell);
		notesTable.setKeepTogether(true);

		document.add(notesTable);
		
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
			if(isMobileIndicator()){
				item.add(new Phrase(SOPDFConstants.WAIVER_CUST_SIGNED,SOPDFConstants.FONT_LABEL_BOLD));
			}else{
				item.add(new Phrase(SOPDFConstants.WAIVER_CUST,SOPDFConstants.FONT_LABEL_BOLD));
			}
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
		
		item.add(new Phrase(SOPDFConstants.WAIVER_TEXT, SOPDFConstants.FONT_LABEL));
		waiverList.add(item);
		
		waiverList.add(new ListItem(SOPDFConstants.NEW_LINE_FEED, SOPDFConstants.FONT_LABEL));

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
		waiverList.add(new ListItem(SOPDFConstants.NEW_LINE_FEED, SOPDFConstants.FONT_LABEL));
		
		/*Moved customer signature to WAIVER OF LIEN to keep it together*/
		item=new ListItem();
		item.add(new Phrase(SOPDFConstants.WORK_ACK_STR, SOPDFConstants.FONT_LABEL));
		waiverList.add(item);
		waiverList.add(new ListItem(SOPDFConstants.NEW_LINE_FEED, SOPDFConstants.FONT_LABEL));
		
		waiverTableCell = addPdfCellForWaiver(waiverList);
		waiverTableCell.setColspan(4);
		waiversTable.setKeepTogether(true);
		waiversTable.addCell(waiverTableCell);
		
		if(null != customerSignature && null != customerSignatureImage){
			waiverTableCell = addSignatures(customerSignature, customerSignatureImage);
			waiverTableCell.setColspan(4);
			waiversTable.addCell(waiverTableCell);
			waiversTable.setKeepTogether(true);
		}
		
		waiverList = new List(false);
		waiverList.setListSymbol(SOPDFConstants.ALLIGN_SPACE);
		item=new ListItem();
		item.add(new Phrase(SOPDFConstants.SIGNATURE_TEXT, SOPDFConstants.FONT_LABEL_BOLD));
		waiverList.add(item);
		waiverList.add(new ListItem(SOPDFConstants.NEW_LINE_FEED, SOPDFConstants.FONT_LABEL));
			
		// SL-21886 verbiage change for the customer's copy of the pdf changes start
		
		waiverTableCell = addPdfCellForWaiver(waiverList);
		if(this.mainPageTitle.equalsIgnoreCase(SOPDFConstants.CUSTOMER_COPY) && so.getBuyerId()== 1953){
			
			Paragraph p=new Paragraph(SOPDFConstants.SURVEY_MESSAGE4,SOPDFConstants.FONT_LABEL_BIG_BOLD_UNDERLINE);

			p.setAlignment(Element.ALIGN_CENTER); 
			waiverTableCell.setBorder(0);
			waiverTableCell.addElement(p);
		}
		
		waiverTableCell.setColspan(4);
		waiversTable.setKeepTogether(true);
		waiversTable.addCell(waiverTableCell);
		
		waiverList = new List(false);
		waiverList.setListSymbol(SOPDFConstants.ALLIGN_SPACE);
		// SL-21886 verbiage change for the customer's copy of the pdf changes ends
		
		//
		//Add sears Products and Services for In Home to WAIVER OF LIEN to keep it together
		//removing the text as part of SLM-112
		/*if(so.getBuyerId() != null && so.getBuyerId() == SOPDFConstants.BUYER_INHOME_ID){ 
			item=new ListItem();
			item.add(new Phrase(SOPDFConstants.LEARN_ABT_SEARS_PRODUCTS_AND_SERVICES_HEAD,SOPDFConstants.FONT_LABEL_BIG ));
			waiverList.add(item);
			waiverList.add(new ListItem(SOPDFConstants.NEW_LINE_FEED, SOPDFConstants.FONT_LABEL));
			
			item=new ListItem();
			item.add(new Phrase(SOPDFConstants.GET_INITIAL,SOPDFConstants.FONT_LABEL ));
			item.add(new Phrase(SOPDFConstants.GET_INITIAL_OR,SOPDFConstants.FONT_LABEL_BOLD));
			item.add(new Phrase(SOPDFConstants.GET_INITIAL_PART,SOPDFConstants.FONT_LABEL));
			waiverList.add(item);
			waiverList.add(new ListItem(SOPDFConstants.NEW_LINE_FEED, SOPDFConstants.FONT_LABEL));
			
			item=new ListItem();
			item.add(new Phrase(SOPDFConstants.SEARS_PRODUCTS_AND_SERVICES_1,SOPDFConstants.FONT_LABEL ));
			item.add(new Phrase(SOPDFConstants.SEARS_PRODUCTS_AND_SERVICES_1_SUB,SOPDFConstants.FONT_LABEL ));
			item.add(new Phrase(SOPDFConstants.SEARS_PRODUCTS_AND_SERVICES_2,SOPDFConstants.FONT_LABEL ));
			item.add(new Phrase(SOPDFConstants.SEARS_PRODUCTS_AND_SERVICES_3,SOPDFConstants.FONT_LABEL ));
			waiverList.add(item);
		}*/
		
		
		item=new ListItem();
		item.add(new Phrase(SOPDFConstants.THANK_YOU_STRING_CENTER_LOWERCASE, SOPDFConstants.FONT_LABEL_BIG));
		waiverList.add(item);
		waiverList.add(new ListItem(SOPDFConstants.NEW_LINE_FEED, SOPDFConstants.FONT_LABEL));
		
		//SL-20807
		if(this.mainPageTitle.equalsIgnoreCase(SOPDFConstants.CUSTOMER_COPY) && SOPDFConstants.BUYER_INHOME_ID == so.getBuyerId()){
			waiverList.add(new ListItem(SOPDFConstants.SURVEY_MESSAGE1,SOPDFConstants.FONT_LABEL));
			waiverList.add(new ListItem(SOPDFConstants.SURVEY_MESSAGE2,SOPDFConstants.FONT_LABEL));
			waiverList.add(new ListItem(SOPDFConstants.SURVEY_MESSAGE3,SOPDFConstants.FONT_LABEL));
		}
		
		/**/		
		waiverTableCell = addPdfCellForWaiver(waiverList);
		waiverTableCell.setColspan(4);
		waiversTable.addCell(waiverTableCell);
		waiversTable.setKeepTogether(true);
    	
		document.add(waiversTable);	
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

	/**
	 * Method to add Special Instruction on Provider copy- Jira#21049
	 * @param specialInstructList
	 * @param so
	 * @throws DocumentException 
	 */
	@SuppressWarnings("deprecation")
	private void addSpecialInstructionTable(PdfPTable spclInstruct, PdfPTable mainTable, PdfWriter pdfWriter, Document document) throws DocumentException{		
		
		logger.info("SODescriptionPDFTemplateImpl : addSpecialInstructionTable");
		List specialInstructList = new List(false);
		List specialInstructList2 = new List(false);
		Image image=null;
		try {
			image =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_ARRIVAL_DEPARTURE_ICON_FILEPATH).getURL());
			
			image.setAbsolutePosition(100,100);
			specialInstructList.setListSymbol(new Chunk(image,-6, 0));
			specialInstructList2.setListSymbol(new Chunk(image,-6, 0));
			
			ListItem item =new ListItem();
			item.setFont(SOPDFConstants.FONT_LABEL_BIG);

			item.add((SOPDFConstants.INST_FOR_PROVIDER).toUpperCase());
			specialInstructList.add(item);
			specialInstructList2.add(item);
			specialInstructList.setListSymbol(SOPDFConstants.ALLIGN_SPACE);
			specialInstructList2.setListSymbol(SOPDFConstants.ALLIGN_SPACE);

		} 
		catch (BadElementException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (MalformedURLException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (IOException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		}
		ListItem item2 = new ListItem();

		if (so.getProviderInstructions() != null) {
			//SL-20728
			List instructions = null;
			String provInstrctn = so.getProviderInstructions();
			try {
				//replace html character entities
				provInstrctn = SOPDFUtils.getHtmlContent(provInstrctn);
				//Convert the html content to rich text
				instructions = SOPDFUtils.getRichText(provInstrctn, SOPDFConstants.FONT_LABEL, null, SOPDFConstants.ALLIGN_SPACE);
			} catch (Exception e) {
				logger.error("Error while converting rich text for " + SOPDFConstants.INST_FOR_PROVIDER + e);
				instructions = null;
			}
			if(null != instructions && !instructions.isEmpty()){
				specialInstructList.add(instructions);
			}
			else{
				provInstrctn = so.getProviderInstructions();
				//Remove the html tags
				provInstrctn = ServiceLiveStringUtils.removeHTML(provInstrctn);
				item2.setFont(SOPDFConstants.FONT_LABEL);
				item2.add(provInstrctn);
				specialInstructList.add(item2);
			}
		}
		
		
				
		specialInstructList2.add(SOPDFConstants.NEW_LINE);

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setColspan(4);
		cell.addElement(specialInstructList);
		spclInstruct.addCell(cell);
		spclInstruct.setWidthPercentage(100);
		
		PdfPCell cell2 = new PdfPCell();
		cell2.setBorder(0);
		cell2.setColspan(4);
		cell2.addElement(specialInstructList2);
		mainTable.addCell(cell2);
		
		if(!pdfWriter.fitsPage(mainTable)){
			spclInstruct.setKeepTogether(true);
		}
		
		document.add(spclInstruct);
}
	
	/**
	 * Method to add Service Location Notes on Provider copy- Jira#21049
	 * @param serviceLocationList
	 * @param so
	 * @throws DocumentException 
	 */
	@SuppressWarnings("deprecation")
	private void addServiceLocNotesTable(PdfPTable serviceLocNotes, PdfPTable mainTable, PdfWriter pdfWriter, Document document) throws DocumentException{		
		List serviceLocationList = new List(false);
		List serviceLocationList2 = new List(false);
		Image image=null;
		try {
			ListItem item =new ListItem();
			image =Image.getInstance(new ClassPathResource(SOPDFConstants.DEFAULT_WAIVER_ICON_FILEPATH).getURL());
			
			image.setAbsolutePosition(100, 100);
			serviceLocationList.setListSymbol(new Chunk(image,-1, 0));
			serviceLocationList2.setListSymbol(new Chunk(image,-1, 0));
			item.setFont(SOPDFConstants.FONT_LABEL_BIG);

			item.add((SOPDFConstants.SERVICE_LOCATION_NOTES).toUpperCase());
			serviceLocationList.add(item);
			serviceLocationList2.add(item);
			serviceLocationList.setListSymbol(SOPDFConstants.ALLIGN_SPACE);
			serviceLocationList2.setListSymbol(SOPDFConstants.ALLIGN_SPACE);

		} 
		catch (BadElementException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (MalformedURLException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		} catch (IOException e) {
			logger.error("Unexpected error while generating Service Order PDF", e);

		}
		ListItem item2 = new ListItem();
		
		if(so.getServiceLocation()!=null && so.getServiceLocation().getLocnNotes()!=null ){
			item2.setFont(SOPDFConstants.FONT_LABEL);
			item2.add(so.getServiceLocation().getLocnNotes());
		}

		serviceLocationList.add(item2);
				
		serviceLocationList2.add(SOPDFConstants.NEW_LINE);

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setColspan(4);
		cell.addElement(serviceLocationList);
		serviceLocNotes.addCell(cell);
		serviceLocNotes.setWidthPercentage(100);
		
		PdfPCell cell2 = new PdfPCell();
		cell2.setBorder(0);
		cell2.setColspan(4);
		cell2.addElement(serviceLocationList2);
		mainTable.addCell(cell2);
		
		if(!pdfWriter.fitsPage(mainTable)){
			serviceLocNotes.setKeepTogether(true);
		}
		
		document.add(serviceLocNotes);
	
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
