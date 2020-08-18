package com.newco.marketplace.business.businessImpl.so.pdf;

import org.apache.log4j.Logger;

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
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.util.constants.SOPDFConstants;
import com.newco.marketplace.utils.ServiceLiveStringUtils;

public class RIServiceProInstrContPDFTemplate {
	
	private Logger logger = Logger.getLogger(RIServiceProInstrContPDFTemplate.class);
	
	private ServiceOrder so;	
	private Image logoImage;
	
	public void execute(PdfWriter pdfWriter, Document document) throws DocumentException {
		float pageWidth = PageSize.LETTER.getWidth();
		float[] serviceProInstrContTableWidths = {540 };
		PdfPTable serviceProInstrContTable = new PdfPTable(serviceProInstrContTableWidths);	
		serviceProInstrContTable.getDefaultCell().setBorder(0);
		serviceProInstrContTable.setHorizontalAlignment(Element.ALIGN_CENTER);
		serviceProInstrContTable.setTotalWidth(pageWidth - 72);
		serviceProInstrContTable.setLockedWidth(true);
		//adding logo
		SOPDFUtils.addLogoImage(serviceProInstrContTable, logoImage);
		//adding page heading
		this.addPageHeading(serviceProInstrContTable);	
		// Empty Row
		SOPDFUtils.addEmptyRow(3, 0, 25, serviceProInstrContTable);		
		//adding location notes
		this.addSoLocationList(serviceProInstrContTable);
		//adding special instructions
		this.addSpecialInstProvList(serviceProInstrContTable);
	
		// Empty Rows
		SOPDFUtils.addEmptyRow(3, 0, -1, serviceProInstrContTable);	
		SOPDFUtils.addEmptyRow(3, 0, -1, serviceProInstrContTable);
		
		document.newPage();
		document.add(serviceProInstrContTable);
	}	
	
	/**
	 * @param so
	 * @param tableMain
	 */
	private void addSoLocationList(PdfPTable serviceProInstrContTable ){
		PdfPTable scopeTable = new PdfPTable(1);
		PdfPCell cell = new PdfPCell(new Phrase(SOPDFConstants.SO_SCOPE_OF_WORK_CONTD_STRING, SOPDFConstants.FONT_LABEL_BIG));
		cell.setMinimumHeight(10);
		scopeTable.addCell(cell);
		List soLocationNotesList = new List(false);
		soLocationNotesList.setListSymbol(SOPDFConstants.SPACE);
		soLocationNotesList.add(new ListItem(SOPDFConstants.SERVICE_LOCATION_NOTES_STRING, SOPDFConstants.FONT_LABEL));
		if(so.getServiceLocation()!=null && so.getServiceLocation().getLocnNotes()!=null ){
			soLocationNotesList.add(new ListItem(so.getServiceLocation().getLocnNotes(), SOPDFConstants.FONT_LABEL));
		}
		soLocationNotesList.add(new ListItem(SOPDFConstants.NEW_LINE));
		
		cell = new PdfPCell();
		cell.addElement(soLocationNotesList);		
		cell.setMinimumHeight(40);		
		
		scopeTable.addCell(cell);
		cell = new PdfPCell(scopeTable);
		cell.setColspan(3);
		cell.setMinimumHeight(100);
		serviceProInstrContTable.addCell(cell);
	}
	
	/**
	 * @param so
	 * @param tableMain
	 */
	private void addSpecialInstProvList(PdfPTable serviceProInstrContTable ){
		
		System.out.println("TermsConditionsPDFTemplateImpl : addSpecialInstProvList");
		List specialInstProvList = new List(false);
		specialInstProvList.setListSymbol(SOPDFConstants.SPACE);
		specialInstProvList.add(new ListItem(SOPDFConstants.SPL_INSTRUCTIONS_FOR_PROVIDER_STRING, SOPDFConstants.FONT_HEADER));
		if(so.getProviderInstructions() != null){
			//SL-20728
			List instructions = null;
			String provInstr = so.getProviderInstructions();
			try {
				//replace html character entities
				provInstr = SOPDFUtils.getHtmlContent(provInstr);
				//Convert the html content to rich text
				instructions = SOPDFUtils.getRichText(provInstr, SOPDFConstants.FONT_LABEL, null, SOPDFConstants.SPACE);
			} 
			catch (Exception e) {
				logger.error("Error while converting rich text for " + SOPDFConstants.INST_FOR_PROVIDER + e);
				instructions = null;
			}
			if(null != instructions && !instructions.isEmpty()){
				specialInstProvList.add(instructions);
			}
			else{
				provInstr = so.getProviderInstructions();
				//Remove html tags
				provInstr = ServiceLiveStringUtils.removeHTML(provInstr);
				specialInstProvList.add(new ListItem(provInstr, SOPDFConstants.FONT_LABEL));
			}
		}	
		specialInstProvList.add(SOPDFConstants.NEW_LINE);
		
		PdfPCell cell = new PdfPCell();
		cell.addElement(specialInstProvList);
		cell.setMinimumHeight(300);
		
		PdfPTable scopeTable2 = new PdfPTable(1);
		scopeTable2.addCell(cell);
		cell = new PdfPCell(scopeTable2);
		cell.setColspan(3);
		cell.setMinimumHeight(300);
		serviceProInstrContTable.addCell(cell);
	}
	/**
	 * @param so
	 * @param serviceProInstrContTable
	 */
	private void addPageHeading(PdfPTable serviceProInstrContTable ){
		String pageMiddleHeading1 = SOPDFConstants.SERVICE_ORDER_STRING + SOPDFConstants.HASH_STR + SOPDFConstants.SPACE +SOPDFConstants.SQUARE_BRACKET_OPEN  + so.getSoId() + SOPDFConstants.SQUARE_BRACKET_CLOSE;
		String pageMiddleHeading2 = "";
		if( so.getAcceptedResource()!=null && so.getAcceptedResource().getResourceContact()!=null
				&& so.getAcceptedResource().getResourceContact().getLastName()!=null &&
				so.getAcceptedResource().getResourceContact().getFirstName()!=null)
		{
			String lastname = so.getAcceptedResource().getResourceContact().getLastName();
			lastname = lastname.substring(0);
			pageMiddleHeading2 = SOPDFConstants.PROVIDER_STRING + so.getAcceptedResource().getResourceContact().getFirstName() + SOPDFConstants.SPACE + lastname + SOPDFConstants.SPACE + so.getAcceptedVendorId();
		}

		List centerHeaderList = new List(false);
		centerHeaderList.setListSymbol(" ");
		centerHeaderList.add(new ListItem(pageMiddleHeading1, SOPDFConstants.FONT_LABEL_BIG));
		centerHeaderList.add(new ListItem(pageMiddleHeading2, SOPDFConstants.FONT_LABEL_BIG));
		
		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.addElement( centerHeaderList);
		serviceProInstrContTable.addCell(cell);	
		
		String pageHeading = SOPDFConstants.PROVIDER_INSTRUCTIONS_STRING ;
		cell = new PdfPCell(new Paragraph(pageHeading, SOPDFConstants.FONT_LABEL_BIG));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setColspan(3);
		cell.setBorder(0);
		serviceProInstrContTable.addCell(cell);		
	}

	public ServiceOrder getSo() {
		return so;
	}

	public void setSo(ServiceOrder so) {
		this.so = so;
	}

	public Image getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(Image logoImage) {
		this.logoImage = logoImage;
	}
}
