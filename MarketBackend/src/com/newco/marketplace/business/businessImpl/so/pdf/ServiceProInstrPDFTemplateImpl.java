package com.newco.marketplace.business.businessImpl.so.pdf;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ElementTags;
import com.lowagie.text.Image;
import com.lowagie.text.List;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.util.constants.SOPDFConstants;
import com.newco.marketplace.utils.ServiceLiveStringUtils;

public class ServiceProInstrPDFTemplateImpl implements PDFTemplate {

	private Logger logger = Logger.getLogger(ServiceProInstrPDFTemplateImpl.class);
	
	private ServiceOrder so;
	private java.util.List<DocumentVO> docList;
	private Image logoImage;
	private String termsCond;
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.util.PDFTemplate#execute(boolean, com.newco.marketplace.dto.vo.serviceorder.ServiceOrder, java.util.List, com.lowagie.text.Image)
	 */
	public void execute(PdfWriter pdfWriter, Document document) throws DocumentException {
		float pageWidth = PageSize.LETTER.getWidth();
		float[] mainTableWidths = { 160, 160, 160, 160 };
		PdfPTable mainTable = new PdfPTable(mainTableWidths);
		mainTable.setSplitLate(false);
		mainTable.setHeaderRows(1);
		mainTable.getDefaultCell().setBorder(0);
		mainTable.setHorizontalAlignment(Element.ALIGN_CENTER);
		mainTable.setTotalWidth(pageWidth - 30);
		mainTable.setLockedWidth(true);
		this.addHeaderTable(mainTable);
		//SOPDFUtils.addEmptyRow(4,0,25,mainTable);
		this.addServiceDateTable( mainTable);
		this.addPickUpLocationTable(mainTable);
		this.addServiceLocationTable(mainTable);
		this.addBuyerInfoTable( mainTable);
		SOPDFUtils.drawLine(mainTable, 4);
		this.addProviderInfoTable(mainTable);
		SOPDFUtils.drawLine(mainTable, 4);
		this.addScopeOfWorkTable(mainTable);
		SOPDFUtils.drawLine(mainTable, 4);
		this.addAttachmentsTable(mainTable);
		SOPDFUtils.drawLine(mainTable, 4);
		addTermsAndConditionsTable(mainTable);
		SOPDFUtils.drawLine(mainTable, 4);
		addSupportInfo(mainTable);

		document.newPage();
		document.add(mainTable);
	}

	/**
	 * Method for adding the header table to the Service Order Description Table
	 * @param mainTable
	 */
	private void addHeaderTable(PdfPTable mainTable){
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
		String pageMiddleHeading = SOPDFConstants.SERVICE_ORDER_NUMBER + so.getSoId();
		String pageMiddleHeading2 = null;
		if(so.getAcceptedResource() != null && so.getAcceptedResource().getResourceContact() != null &&
				so.getAcceptedResource().getResourceContact().getFirstName() != null &&
				so.getAcceptedResource().getResourceContact().getLastName() != null &&
				so.getAcceptedResourceId() != null) {
			String lastname = so.getAcceptedResource().getResourceContact().getLastName();
			pageMiddleHeading2 = SOPDFConstants.PROVIDER + SOPDFConstants.COLON + SOPDFConstants.SPACE + 
				so.getAcceptedResource().getResourceContact().getFirstName() + SOPDFConstants.SPACE + lastname +
				SOPDFConstants.SPACE + SOPDFConstants.HASH_STR + so.getAcceptedResourceId();
			pageMiddleHeading += "\n" + pageMiddleHeading2;
		}

		PdfPCell soIdCell = new PdfPCell(new Paragraph(pageMiddleHeading, SOPDFConstants.FONT_LABEL_BIG));
		soIdCell.setBorder(1);
		soIdCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPTable centerSoId = new PdfPTable(1);
		centerSoId.addCell(soIdCell);
		headerTable.addCell(centerSoId);
		
		// Add Page Heading on right side
		PdfPCell titleCell = new PdfPCell(new Paragraph(SOPDFConstants.PROVIDER_INSTR, SOPDFConstants.FONT_LABEL_BIG));
		titleCell.setBorder(0);
		titleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		PdfPTable rightTitle = new PdfPTable(1);
		//rightTitle.setHorizontalAlignment(Element.ALIGN_RIGHT);
		rightTitle.addCell(titleCell);
		headerTable.addCell(rightTitle);
		
		// Now, add entire heading row to main table
		PdfPCell headingCell = new PdfPCell();
		headingCell.setBorder(0);
		headingCell.setColspan(4);
		headingCell.addElement(headerTable);
		mainTable.addCell(headingCell);
	}
	
	/**
	 * Method to add service date details in provider instructions Main table
	 * @param so
	 * @param mainTable
	 * @param cell
	 */
	private void addServiceDateTable(PdfPTable mainTable){
		PdfPTable tableServiceDate = new PdfPTable(1);
		List serviceDateList = new List(false);
		serviceDateList.setListSymbol(SOPDFConstants.SPACE);
		serviceDateList.add(new ListItem(SOPDFConstants.SERVICE_EVENT_DATE+SOPDFConstants.COLON, SOPDFConstants.FONT_HEADER));
		serviceDateList.add(SOPDFConstants.NEW_LINE);
		serviceDateList.add(new ListItem(SOPDFUtils.appointmentDates(so), SOPDFConstants.FONT_LABEL)); 
		serviceDateList.add(SOPDFConstants.NEW_LINE);
		serviceDateList.add(new ListItem(SOPDFConstants.SERVICE_WINDOW+
				SOPDFUtils.serviceWindow(so), SOPDFConstants.FONT_LABEL));		
		//check for call to confirm
		if (so.getProviderServiceConfirmInd()!= null && so.getProviderServiceConfirmInd().intValue() == 1) {
			serviceDateList.add(new ListItem(SOPDFConstants.SERVICE_LOCATION_CONTACT_MSG, SOPDFConstants.FONT_LABEL));
		}		
		serviceDateList.add(SOPDFConstants.NEW_LINE);
		SOPDFUtils.addCellToTable(serviceDateList, 0, -1, 40, mainTable,tableServiceDate);		
	}
	
	/**
	 * Method to add pickUp location details in provider instructions Main table
	 * @param mainTable
	 */
	private void addPickUpLocationTable(PdfPTable mainTable){
		String checkers[] = new String[5];
		PdfPTable tablePickUpLocation = new PdfPTable(1);
		List pickUpLocationList = new List(false);
		pickUpLocationList.setListSymbol(SOPDFConstants.SPACE);
		pickUpLocationList.add(new ListItem(SOPDFConstants.PICKUP_LOCATION+SOPDFConstants.COLON, 
				SOPDFConstants.FONT_HEADER));
		pickUpLocationList.add(SOPDFConstants.NEW_LINE);
		Properties locProp = new Properties();
		locProp.put(ElementTags.FONT, SOPDFConstants.FONT_LABEL);
		SoLocation pickUpLoc = getPickupLocation(so);
		Contact pickupContact = getPickupContact(so);
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
			if(pickUpLoc.getStreet1()!= null && pickUpLoc.getStreet2()!=null &&
					pickUpLoc.getAptNo()!=null){
				checkers[0] = new SOPDFUtils.Checker(new SOPDFUtils.Checker(pickUpLoc.getStreet1()).appendWithCommaForPickup(pickUpLoc.getStreet2())).
							appendWithCommaForPickup(pickUpLoc.getAptNo());
				//checkers[0] = new SOPDFUtils.Checker(new SOPDFUtils.Checker(pickUpLoc.getStreet1())+", "+(pickUpLoc.getStreet2()))+", "+ (pickUpLoc.getAptNo());
			}
			if(pickUpLoc.getCity()!=null && pickUpLoc.getState()!=null &&
					pickUpLoc.getZip()!=null){
				checkers[1] = new SOPDFUtils.Checker(pickUpLoc.getCity()).appendWithCommaForPickup(pickUpLoc.getState() +SOPDFConstants.SPACE
							+pickUpLoc.getZip());
				//checkers[1] = new SOPDFUtils.Checker(pickUpLoc.getCity())+", "+(pickUpLoc.getState() +SOPDFConstants.SPACE+pickUpLoc.getZip());
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
			
			pickUpLocationList.add(SOPDFConstants.NEW_LINE);			
		} else {
			pickUpLocationList.add(new ListItem(SOPDFConstants.NOT_AVAILABLE, SOPDFConstants.FONT_LABEL));
		}
		
		if (pickupContact != null && StringUtils.isNotBlank(pickupContact.getPhoneNo())) {
			pickUpLocationList.add(new ListItem(new SOPDFUtils.Checker(SOPDFUtils.formatPhoneNumber(pickupContact.getPhoneNo()))
					.prependWith(SOPDFConstants.BLANK_SPACE,SOPDFConstants.BLANK_SPACE), SOPDFConstants.FONT_LABEL));
		}
		SOPDFUtils.addCellToTable(pickUpLocationList, 0, -1, 40, mainTable,tablePickUpLocation);		
	}
	
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
	 * Method to add service location details in provider instructions Main table
	 * @param so
	 * @param mainTable
	 * @param cell
	 */
	private void addServiceLocationTable(PdfPTable mainTable){
		String checkers[] = new String[5];
		PdfPTable tableServiceLocation = new PdfPTable(1);
		List serviceLocationList = new List(false);
		serviceLocationList.setListSymbol(SOPDFConstants.SPACE);
		serviceLocationList.add(new ListItem(SOPDFConstants.SERVICE_LOCATION+SOPDFConstants.COLON, 
				SOPDFConstants.FONT_HEADER));
		serviceLocationList.add(SOPDFConstants.NEW_LINE);
		Properties locProp = new Properties();
		locProp.put(ElementTags.FONT, SOPDFConstants.FONT_LABEL);	
		if (so.getServiceLocation() != null) {
			if(so.getServiceLocation().getLocnClassDesc()!=null){
				serviceLocationList.add(new ListItem(so.getServiceLocation().getLocnClassDesc(), SOPDFConstants.FONT_ITALIC));
			}
			if(so.getServiceContact()!=null && so.getServiceContact().getFirstName()!=null &&
					 so.getServiceContact().getLastName()!=null)
				serviceLocationList.add(new ListItem(so.getServiceContact().getLastName()+ SOPDFConstants.COMMA+SOPDFConstants.SPACE+so
					.getServiceContact().getFirstName(), SOPDFConstants.FONT_LABEL));
			if(so.getServiceLocation().getStreet1()!=null && so.getServiceLocation().getStreet2()!=null &&
					so.getServiceLocation().getAptNo()!=null){
				checkers[0] = new SOPDFUtils.Checker(
						new SOPDFUtils.Checker(so.getServiceLocation().getStreet1()).appendWithComma(so.getServiceLocation().getStreet2())).
							appendWithComma(so.getServiceLocation().getAptNo());
			}
			
			String cityStateZip = null;
			if(so.getServiceLocation().getCity()!=null && so.getServiceLocation().getState()!=null &&
					so.getServiceLocation().getZip()!=null){
				cityStateZip = so.getServiceLocation().getCity()+SOPDFConstants.COMMA+so.getServiceLocation().getState()+SOPDFConstants.SPACE
							+ so.getServiceLocation().getZip();
			} else {
				cityStateZip = so.getServiceLocation().getState() + SOPDFConstants.SPACE + so.getServiceLocation().getZip();
			}
			if (checkers[0]!=null){
				serviceLocationList.add(new ListItem(checkers[0], SOPDFConstants.FONT_LABEL));
			}
			serviceLocationList.add(new ListItem(cityStateZip, SOPDFConstants.FONT_LABEL));

			serviceLocationList.add(SOPDFConstants.NEW_LINE);
			
			if(so.getServiceContact()!=null && so.getServiceContact().getPhoneNo()!=null && !so.getServiceContact().getPhoneNo().equals(SOPDFConstants.ZERO_PHONE)){
				serviceLocationList.add(new ListItem(new SOPDFUtils.Checker(SOPDFUtils.formatPhoneNumber(so
					.getServiceContact().getPhoneNo())).prependWith(SOPDFConstants.BLANK_SPACE,SOPDFConstants.BLANK_SPACE)
					, SOPDFConstants.FONT_LABEL));
			}
			if(so.getServiceContactAlt()!=null && so.getServiceContactAlt().getPhoneNo()!=null && !so.getServiceContactAlt().getPhoneNo().equals(SOPDFConstants.ZERO_PHONE)){
				serviceLocationList.add(new ListItem(new SOPDFUtils.Checker(SOPDFUtils.formatPhoneNumber(so
					.getServiceContactAlt().getPhoneNo())).prependWith(SOPDFConstants.BLANK_SPACE,SOPDFConstants.BLANK_SPACE)
					, SOPDFConstants.FONT_LABEL));
			}
			if(so.getServiceContact()!=null && so.getServiceContact().getEmail()!=null){
				serviceLocationList.add(new ListItem(new SOPDFUtils.Checker(so
						.getServiceContact().getEmail()).prependWith(SOPDFConstants.BLANK_SPACE,SOPDFConstants.BLANK_SPACE)
						, SOPDFConstants.FONT_LABEL));
				}
		}
		SOPDFUtils.addCellToTable(serviceLocationList, 0, -1, 40, mainTable,tableServiceLocation);		
	}
	
	/**
	 * Method to add buyer information in provider instructions Main table
	 * @param so
	 * @param mainTable
	 * @param cell
	 */
	private void addBuyerInfoTable(PdfPTable mainTable){
		PdfPTable tablebuyerInfo = new PdfPTable(1);
		List buyerInfoList = new List(false);
		buyerInfoList.setListSymbol(SOPDFConstants.SPACE);
		buyerInfoList.add(new ListItem(SOPDFConstants.CONTACT_INFORMATION+SOPDFConstants.COLON+SOPDFConstants.SPACE,
					SOPDFConstants.FONT_HEADER));
		buyerInfoList.add(SOPDFConstants.NEW_LINE);
		if(so.getBuyer()!=null && so.getBuyer().getBusinessName() != null && so.getBuyer().getBuyerId() != null){
			buyerInfoList.add(new ListItem(SOPDFConstants.BUYER+so.getBuyer().getBusinessName()+SOPDFConstants.SPACE
					+SOPDFConstants.HASH_STR+so.getBuyer().getBuyerId(), SOPDFConstants.FONT_LABEL));			
		}
		buyerInfoList.add(SOPDFConstants.NEW_LINE);
		this.addAltBuyerInfoToBuyerTable(so,buyerInfoList);
		SOPDFUtils.addCellToTable(buyerInfoList, 0, -1, 40, mainTable,tablebuyerInfo);		
	}
	
	/**
	 * Method to add alt buyer information to buyer information table
	 * @param so
	 * @param buyerInfoList
	 */
	private void addAltBuyerInfoToBuyerTable(ServiceOrder so,List buyerInfoList ){
		if(	so.getAltBuyerResource()!=null && so.getAltBuyerResource().getBuyerResContact()!=null
				&& so.getAltBuyerResource().getBuyerResContact().getFirstName() != null
				&& so.getAltBuyerResource().getBuyerResContact().getLastName() != null){
			buyerInfoList.add(new ListItem(so.getAltBuyerResource().getBuyerResContact().getFirstName()
					+ SOPDFConstants.SPACE + so.getAltBuyerResource().getBuyerResContact().getLastName()
					+ SOPDFConstants.SPACE + SOPDFConstants.OPENING_BRACE + SOPDFConstants.ID_NUM + so.getAltBuyerResource().getBuyerResContact().getResourceId()
					+ SOPDFConstants.CLOSING_BRACE,
					SOPDFConstants.FONT_LABEL));
		}else{
			buyerInfoList
			.add(new ListItem(SOPDFConstants.BLANK_SPACE, SOPDFConstants.FONT_LABEL));
		}

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
		buyerInfoList.add(SOPDFConstants.NEW_LINE);		
		if(null != so.getAltBuyerResource() && 
				null != so.getAltBuyerResource().getBuyerResContact() &&
				so.getAltBuyerResource().getBuyerResContact().getPhoneNo()!= null){
			buyerInfoList
			.add(new ListItem(SOPDFUtils.formatPhoneNumber(so.getAltBuyerResource().getBuyerResContact().getPhoneNo()),
					SOPDFConstants.FONT_LABEL));
		}else{
			buyerInfoList
			.add(new ListItem(SOPDFConstants.BLANK_SPACE, SOPDFConstants.FONT_LABEL));
		}		
		if(null!=so.getAltBuyerResource()&& so.getAltBuyerResource().getBuyerResContact() != null && so.getAltBuyerResource().getBuyerResContact().getCellNo() != null){
			buyerInfoList.add(new ListItem(SOPDFConstants.BLANK_SPACE +so.getAltBuyerResource().getBuyerResContact().getCellNo(), SOPDFConstants.FONT_LABEL));
		}else{
			buyerInfoList.add(new ListItem(SOPDFConstants.BLANK_SPACE, SOPDFConstants.FONT_LABEL));
		}		
		if(null!=so.getAltBuyerResource()&& so.getAltBuyerResource().getBuyerResContact().getEmail() != null){
			buyerInfoList.add(new ListItem(SOPDFConstants.BLANK_SPACE
					+ so.getAltBuyerResource().getBuyerResContact().getEmail(), SOPDFConstants.FONT_LABEL));
		}else{
			buyerInfoList.add(new ListItem(SOPDFConstants.BLANK_SPACE, SOPDFConstants.FONT_LABEL));
		}
	}
	
	/**
	 * Method to add details to be displayed in Scope of Work table
	 * @param so
	 * @param mainTable
	 * @param cell
	 */
	private void addScopeOfWorkTable(PdfPTable mainTable){	
		PdfPTable scopeTable = new PdfPTable(1);
		scopeTable.setSplitLate(false);
		
		PdfPCell sowHeadingCell = new PdfPCell(new Phrase(SOPDFConstants.SCOPE_OF_WORK_MSG, SOPDFConstants.FONT_HEADER));
		sowHeadingCell.setBorder(0);
		scopeTable.addCell(sowHeadingCell);
		List serviceTaskList = new List(false);
		serviceTaskList.setListSymbol(SOPDFConstants.SPACE);
		List servicePartList = new List(false);
		servicePartList.setListSymbol(SOPDFConstants.SPACE);
		this.addTasksInfoToScopeTable(serviceTaskList);
		this.addPartsInfoToScopeTable(serviceTaskList,servicePartList);
		PdfPCell tasksPartsCell = new PdfPCell();
		tasksPartsCell.setBorder(0);
		tasksPartsCell.addElement(serviceTaskList);
		tasksPartsCell.addElement(servicePartList);
		//tasksPartsCell.setMinimumHeight(400);
		scopeTable.addCell(tasksPartsCell);
		
		// Add SOW to main table
		PdfPCell sowCell = new PdfPCell(scopeTable);
		sowCell.setColspan(4);
		sowCell.setBorder(0);
		//sowCell.setMinimumHeight(300);
		mainTable.addCell(sowCell);
	}
	
	/**
	 * Method to add provider information in Scope of Work table
	 * @param so
	 * @param cell
	 * @param mainTable
	 */
	private void addProviderInfoTable(PdfPTable mainTable){
		PdfPCell headingCell = new PdfPCell();
		headingCell.setBorder(0);
		headingCell.setColspan(4);
		headingCell.addElement(new Phrase(SOPDFConstants.IMPORTANT_ARRIVAL_DEPARTURE_MSG, SOPDFConstants.FONT_HEADER));
		mainTable.addCell(headingCell);
		
		// Arrival Instructions
		PdfPCell uponArrivalListCell = new PdfPCell();
		uponArrivalListCell.setBorder(0);
		uponArrivalListCell.setColspan(2);
		uponArrivalListCell.addElement(new Phrase(SOPDFConstants.UPON_ARRIVAL, SOPDFConstants.FONT_HEADER));
			List uponArrivalList = new List(false);
			uponArrivalList.setListSymbol(new Chunk(new String(new char[] { (char) 0xb7 }),SOPDFConstants.FONT_HEADER));
			uponArrivalList.add(new ListItem(SOPDFConstants.UPON_ARRIVAL_STEP_1, SOPDFConstants.FONT_LABEL));
			uponArrivalList.add(new ListItem(SOPDFConstants.UPON_ARRIVAL_STEP_2, SOPDFConstants.FONT_LABEL));
			uponArrivalList.add(new ListItem(SOPDFConstants.UPON_ARRIVAL_STEP_3, SOPDFConstants.FONT_LABEL));
		uponArrivalListCell.addElement(uponArrivalList);
		mainTable.addCell(uponArrivalListCell);
		
		// Departure Instructions
		PdfPCell uponDepartureListCell = new PdfPCell();
		uponDepartureListCell.setBorder(0);
		uponDepartureListCell.setColspan(2);
		uponDepartureListCell.addElement(new Phrase(SOPDFConstants.UPON_DEPARTURE, SOPDFConstants.FONT_HEADER));
			List uponDepartureList = new List(false);
			uponDepartureList.setListSymbol(new Chunk(new String(new char[] { (char) 0xb7 }),SOPDFConstants.FONT_HEADER));
			uponDepartureList.add(new ListItem(SOPDFConstants.UPON_DEPARTURE_STEP_1, SOPDFConstants.FONT_LABEL));
			uponDepartureList.add(new ListItem(SOPDFConstants.UPON_DEPARTURE_STEP_2, SOPDFConstants.FONT_LABEL));
			uponDepartureList.add(new ListItem(SOPDFConstants.UPON_DEPARTURE_STEP_3, SOPDFConstants.FONT_LABEL));
		uponDepartureListCell.addElement(uponDepartureList);
		mainTable.addCell(uponDepartureListCell);
	}
	
	/**
	 * Method to add task details in scope of work table
	 * @param serviceTaskList
	 * @param so
	 */
	private void addTasksInfoToScopeTable(List serviceTaskList){	
		
		logger.info("ServiceProInstrPDFTemplateImpl : addTasksInfoToScopeTable");
		if(null!=so.getSkill()&& null!=so.getSkill().getNodeName()){
			serviceTaskList.add(new ListItem(SOPDFConstants.SERVICE_CATEGORY
					+ so.getSkill().getNodeName() ,SOPDFConstants.FONT_LABEL));
		}
		if(null!=so.getSowTitle()){
			serviceTaskList.add(new ListItem(SOPDFConstants.TITLE
					+ so.getSowTitle(), SOPDFConstants.FONT_LABEL));
		}
		if(null!=so.getSowDs()){
			//SL-20728
			List description = null;
			String sowDs = so.getSowDs();
			try{
				//replace html character entities
				sowDs = SOPDFUtils.getHtmlContent(sowDs);
				//Convert the html content to rich text	
				description = SOPDFUtils.getRichText(sowDs, SOPDFConstants.FONT_LABEL, null, SOPDFConstants.SPACE);
			}catch(Exception e){
				logger.error("Error while converting rich text for " + SOPDFConstants.OVERVIEW + e);
				description = null;
			}
			if(null != description && !description.isEmpty()){
				serviceTaskList.add(new ListItem(SOPDFConstants.OVERVIEW, SOPDFConstants.FONT_LABEL));
				serviceTaskList.add(description);
			}
			else{
				sowDs = so.getSowDs();
				//Remove html tags
				sowDs = ServiceLiveStringUtils.removeHTML(sowDs);
				serviceTaskList.add(new ListItem(SOPDFConstants.OVERVIEW 
						+ sowDs, SOPDFConstants.FONT_LABEL));
			}
		}
		serviceTaskList.add(SOPDFConstants.NEW_LINE);
		serviceTaskList.add(new ListItem(SOPDFConstants.INST_FOR_PROVIDER, SOPDFConstants.FONT_LABEL));
		
		//SL-20728
		List instructions = null;
		String provInstrctn = so.getProviderInstructions();
		try{
			//replace html character entities
			provInstrctn = SOPDFUtils.getHtmlContent(provInstrctn);
			//Convert the html content to rich text
			instructions = SOPDFUtils.getRichText(provInstrctn, SOPDFConstants.FONT_LABEL, null, SOPDFConstants.SPACE);
		}catch(Exception e){
			logger.error("Error while converting rich text for " + SOPDFConstants.INST_FOR_PROVIDER + e);
			instructions = null;
		}
		if(null != instructions && !instructions.isEmpty()){
			serviceTaskList.add(instructions);
		}
		else{
			provInstrctn = so.getProviderInstructions();
			//Remove html tags
			provInstrctn = ServiceLiveStringUtils.removeHTML(provInstrctn);
			serviceTaskList.add(new ListItem(provInstrctn, SOPDFConstants.FONT_LABEL));
		}
		
		serviceTaskList.add(SOPDFConstants.NEW_LINE);						
		serviceTaskList.add(new ListItem(SOPDFConstants.SERVICE_LOCATION_NOTES, SOPDFConstants.FONT_LABEL));
		if(so.getServiceLocation()!=null && so.getServiceLocation().getLocnNotes()!=null ){
			serviceTaskList.add(new ListItem(so.getServiceLocation().getLocnNotes(), SOPDFConstants.FONT_LABEL));
		}
		serviceTaskList.add(new ListItem(SOPDFConstants.NEW_LINE));

		if(so.getTasks()!=null){
			java.util.List<ServiceOrderTask> task = so.getTasks();	
			for (int i = 0; i < task.size(); i++) {
				int j = i + 1;
				serviceTaskList.add(new ListItem(SOPDFConstants.TASK+ j + SOPDFConstants.COLON
						+ SOPDFConstants.SPACE + task.get(i).getTaskName(), SOPDFConstants.FONT_LABEL));
				serviceTaskList.add(new ListItem(SOPDFConstants.TASK_CATEGORY
						+ task.get(i).getCategoryName(), SOPDFConstants.FONT_LABEL));
				serviceTaskList.add(new ListItem(SOPDFConstants.SUB_CATEGORY
						+ new SOPDFUtils.Checker(task.get(i).getSubCategoryName())
				.returnValueAsEmpty(), SOPDFConstants.FONT_LABEL));
				serviceTaskList.add(new ListItem(SOPDFConstants.SKILL
						+ task.get(i).getServiceType(), SOPDFConstants.FONT_LABEL));
				
				//SL-20728
				List taskComment = null;
				String comment = task.get(i).getTaskComments();
				try{
					//replace html character entities
					comment = SOPDFUtils.getHtmlContent(comment);
					//Convert the html content to rich text	
					taskComment = SOPDFUtils.getRichText(comment, SOPDFConstants.FONT_LABEL, null, SOPDFConstants.SPACE);
				}catch(Exception e){
					logger.error("Error while converting rich text for task comments" + e);
					taskComment = null;
				}
				if(null != taskComment && !taskComment.isEmpty()){
					serviceTaskList.add(new ListItem(SOPDFConstants.SERVICE_ORDER_COMMENTS,SOPDFConstants.FONT_LABEL));
					serviceTaskList.add(taskComment);
				}
				else{
					comment = task.get(i).getTaskComments();
					//Remove html tags
					comment = ServiceLiveStringUtils.removeHTML(comment);
					serviceTaskList.add(new ListItem(SOPDFUtils.append(SOPDFConstants.SERVICE_ORDER_COMMENTS,
							comment, SOPDFConstants.BLANK_SPACE), SOPDFConstants.FONT_LABEL));
				}
				serviceTaskList.add(SOPDFConstants.NEW_LINE);
			}
		}
	}
	
	/**
	 * Method to add parts details in scope of work table
	 * @param serviceTaskList
	 * @param servicePartList
	 * @param so
	 */
	private void addPartsInfoToScopeTable(List serviceTaskList,List servicePartList){
		if(so.getParts()!=null){
			java.util.List<Part> parts = so.getParts();
			servicePartList.add(new ListItem(SOPDFConstants.PARTS_AND_MATERIALS, SOPDFConstants.FONT_HEADER));
			for (int j = 0; j < parts.size(); j++) {
				servicePartList.add(new ListItem(SOPDFConstants.PART+(j+1), SOPDFConstants.FONT_HEADER));
				servicePartList.add(new ListItem(SOPDFConstants.MANUFACTURER+parts.get(j).getManufacturer(), SOPDFConstants.FONT_LABEL));
				servicePartList.add(new ListItem(SOPDFConstants.MODEL+parts.get(j).getModelNumber(), SOPDFConstants.FONT_LABEL));
				servicePartList.add(new ListItem(SOPDFConstants.DESCRIPTION+parts.get(j).getPartDs(), SOPDFConstants.FONT_LABEL));

				if (parts.get(j).getQuantity() != null) {
					servicePartList.add(new ListItem(SOPDFConstants.QUANTITY+parts.get(j).getQuantity(), SOPDFConstants.FONT_LABEL));
				}
				servicePartList.add(new ListItem(SOPDFConstants.NEW_LINE));				

				if (parts.get(j).getShippingCarrier() != null || parts.get(j).getReturnCarrier() != null) { 
					servicePartList.add(new ListItem(SOPDFConstants.ADDITIONAL_PARTS_INST, SOPDFConstants.FONT_HEADER));
				}
				if (parts.get(j).getShippingCarrier() != null) {
					if (parts.get(j).getShippingCarrier().getCarrierName() != null) {
						servicePartList.add(new ListItem(SOPDFConstants.SHIPPING_CARRIER+parts.get(j)
								.getShippingCarrier().getCarrierName(), SOPDFConstants.FONT_LABEL));
					}
					if (parts.get(j).getShippingCarrier().getTrackingNumber() != null) {
						servicePartList.add(new ListItem(SOPDFConstants.SHIPPING_TRACKING_NO+parts.get(j)
								.getShippingCarrier().getTrackingNumber(), SOPDFConstants.FONT_LABEL));
					}
				}
				if (parts.get(j).getReturnCarrier() != null) {
					if (parts.get(j).getReturnCarrier().getCarrierName() != null) {
						servicePartList.add(new ListItem(SOPDFConstants.RETURN_CARRIER+parts.get(j)
								.getReturnCarrier().getCarrierName(), SOPDFConstants.FONT_LABEL));

					}
					if (parts.get(j).getReturnCarrier().getTrackingNumber() != null) {
						servicePartList.add(new ListItem(SOPDFConstants.RETURN_TRACKING_NO+parts.get(j)
								.getReturnCarrier().getTrackingNumber(), SOPDFConstants.FONT_LABEL));
					}
				}
				servicePartList.add(new ListItem(SOPDFConstants.NEW_LINE));
			}
		}
	}
	
	/**
	 * Method to add attachment details in provider instructions Main table
	 * @param so
	 * @param mainTable
	 * @param cell
	 * @param docList
	 */
	private void addAttachmentsTable(PdfPTable mainTable){
		PdfPTable attachmentTable = new PdfPTable(1);
		attachmentTable.setWidthPercentage(100);
		List attachmentsList = new List(false);
		attachmentsList.setListSymbol(SOPDFConstants.SPACE);
		attachmentsList.add(new ListItem(SOPDFConstants.ATTACHMENTS,SOPDFConstants.FONT_HEADER));
		attachmentsList.setListSymbol(new Chunk(new String(new char[] { (char) 0xb7 }),SOPDFConstants.FONT_HEADER));
		attachmentsList.setSymbolIndent(5);
		attachmentsList.setAutoindent(false);
		attachmentsList.setFirst(0);
		if (docList != null) {
			for (DocumentVO docVO : docList) {
				attachmentsList.add(new ListItem(docVO.getFileName(),SOPDFConstants.FONT_LABEL));
			}
		}
		//SOPDFUtils.addCellToTable(attachmentsList, 0, 4, 40, mainTable,attachmentTable);
		PdfPCell cell = new PdfPCell();
		cell.addElement(attachmentsList);		
		//cell.setMinimumHeight(40);
		cell.setBorder(0);
		attachmentTable.addCell(cell);
		this.addCustomReferenceTable(so,attachmentTable);
		
		cell = new PdfPCell();
		cell.addElement(attachmentTable);
		//cell.setMinimumHeight(40);
		cell.setBorder(0);
		cell.setColspan(4);
		mainTable.addCell(cell);
	}
	
	/**
	 * Method to add custom reference details to the attachment table
	 * @param so
	 * @param attachmentTable
	 */
	private void addCustomReferenceTable(ServiceOrder so,PdfPTable attachmentTable){
		List custRefList = new List(false);
		custRefList.setListSymbol(SOPDFConstants.SPACE);
		custRefList.add(new ListItem(SOPDFConstants.CUSTOM_REFERENCE,SOPDFConstants.FONT_HEADER));
		if( so.getCustomRefs()!=null){
			java.util.List<ServiceOrderCustomRefVO> list = so.getCustomRefs();
			for (ServiceOrderCustomRefVO vo : list) {
				String refValue = vo.getRefValue();
				if (StringUtils.isNotBlank(refValue)&& vo.getPdfRefInd().equals(SOPDFConstants.ONE)) {
					custRefList.add(new ListItem(SOPDFUtils.append(vo.getRefType(), refValue, SOPDFConstants.COLON +SOPDFConstants.SPACE), SOPDFConstants.FONT_LABEL));
				}
			}
		}
		
		PdfPCell cell = new PdfPCell();
		cell.addElement(custRefList);
		cell.setBorder(0);
		cell.setColspan(4);
		attachmentTable.addCell(cell);
	}
	
	/**
	 * Method for adding terms and conditions to table
	 * @param mainTable
	 */
	@SuppressWarnings("unchecked")
	private void addTermsAndConditionsTable(PdfPTable mainTable) {
		PdfPTable termsCondTable = new PdfPTable(1);
		termsCondTable.setWidthPercentage(100);
		termsCondTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		PdfPCell cellTermsCond = null;
		StringReader strTermsCond = new StringReader(termsCond);
		StyleSheet style = new StyleSheet();
		logger.info("ServiceProInstrPDFTemplateImpl : addTermsAndConditionsTable");
				
		cellTermsCond = new PdfPCell(new Phrase(SOPDFConstants.BUYER_TERMS_COND, SOPDFConstants.FONT_HEADER));
		cellTermsCond.setBorder(0);
		cellTermsCond.setHorizontalAlignment(Element.ALIGN_LEFT);
		termsCondTable.addCell(cellTermsCond);
		
		//SL-20728
		List termsAndConditions = null;
		String terms = termsCond;
		try {
			//replace html character entities
			terms = SOPDFUtils.getHtmlContent(terms);
			//Convert the html content to rich text		
			termsAndConditions = SOPDFUtils.getRichText(terms, SOPDFConstants.FONT_LABEL_SMALL, null, SOPDFConstants.SPACE);
		} catch (Exception e) {
			logger.error("Error while converting rich text for " + SOPDFConstants.BUYER_TERMS_COND + e);
			termsAndConditions = null;
		}
		if(null != termsAndConditions && !termsAndConditions.isEmpty()){
			cellTermsCond = new PdfPCell();
			cellTermsCond.addElement(termsAndConditions);
			cellTermsCond.setBorder(0);
			termsCondTable.addCell(cellTermsCond);
		}
		else{
			terms = termsCond;
			//Remove html tags
			terms = ServiceLiveStringUtils.removeHTML(terms);
			cellTermsCond = new PdfPCell(new Phrase(terms, SOPDFConstants.FONT_LABEL_SMALL));
			cellTermsCond.setBorder(0);
			termsCondTable.addCell(cellTermsCond);
			/*java.util.List<Paragraph> parasList = null;
			try {
				parasList = (java.util.List<Paragraph>)HTMLWorker.parseToList(strTermsCond, style);
			} catch (IOException ioEx) {
				logger.error("Unexpected IO Error while converting terms conditions HTML into list of iText Paragraphs", ioEx);
				return;
			}
			if (parasList != null) {
				for(Paragraph para : parasList) {								
					cellTermsCond = new PdfPCell(new Phrase(para.getContent(), SOPDFConstants.FONT_LABEL_SMALL));
					cellTermsCond.setBorder(0);
					termsCondTable.addCell(cellTermsCond);
				}
			}*/
		}
		cellTermsCond = new PdfPCell(new Phrase(" "));
		cellTermsCond.setBorder(0);
		termsCondTable.addCell(cellTermsCond);
		
		PdfPCell termsCondCell = new PdfPCell();
		termsCondCell.addElement(termsCondTable);
		termsCondCell.setBorder(0);
		termsCondCell.setColspan(4);
		mainTable.addCell(termsCondCell);
	}
	
	private void addSupportInfo(PdfPTable mainTable) {
		PdfPCell supportInfoCell=null;
		if(null != so.getAltBuyerResource() && 
			null != so.getAltBuyerResource().getBuyerResContact() &&
			null != so.getAltBuyerResource().getBuyerResContact().getPhoneNo()){
			supportInfoCell = new PdfPCell(new Phrase(SOPDFConstants.SUPPORT_CONTACT_LABEL + SOPDFUtils.formatPhoneNumber(so.getAltBuyerResource().getBuyerResContact().getPhoneNo()), SOPDFConstants.FONT_HEADER));
		}else if(null != so.getBuyer() && null != so.getBuyer().getBuyerContact() &&
			null != so.getBuyer().getBuyerContact().getPhoneNo()){
			supportInfoCell = new PdfPCell(new Phrase(SOPDFConstants.SUPPORT_CONTACT_LABEL + SOPDFUtils.formatPhoneNumber(so.getBuyer().getBuyerContact().getPhoneNo()), SOPDFConstants.FONT_HEADER));
		}
		if(null != supportInfoCell){
			supportInfoCell.setBorder(0);
			supportInfoCell.setColspan(4);
			mainTable.addCell(supportInfoCell);
		}
	}
	
	public ServiceOrder getSo() {
		return so;
	}

	public void setSo(ServiceOrder so) {
		this.so = so;
	}

	public java.util.List<DocumentVO> getDocList() {
		return docList;
	}

	public void setDocList(java.util.List<DocumentVO> docList) {
		this.docList = docList;
	}

	public Image getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(Image logoImage) {
		this.logoImage = logoImage;
	}

	public String getTermsCond() {
		return termsCond;
	}

	public void setTermsCond(String termsCond) {
		this.termsCond = termsCond;
	}

}
