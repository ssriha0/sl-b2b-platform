package com.newco.marketplace.business.businessImpl.so.pdf;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.api.mobile.beans.sodetails.AddonPayment;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.exception.core.DataNotFoundException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao;
import com.newco.marketplace.util.constants.SOPDFConstants;

/**
 *  Class responsible for generating the Customer Copy/Service Pro Copy of  PDF
 *
 */
public class SOAddonPDFTemplateImpl implements PDFTemplate {	
	
	//private Logger logger = Logger.getLogger(SODescriptionPDFTemplateImpl.class);
	
	private Image logoImage;
	private String soId;
	private String mainPageTitle;
	private java.util.List<ServiceOrderAddonVO> soAddons;
	private String processID;
	private ServiceOrder so;
	
	private AddonPayment addOnPaymentDetails;
	private Image customerSignatureImage;
	private String currentDate;
	private double totalPrice;
	
	public ServiceOrder getSo() {
		return so;
	}

	public void setSo(ServiceOrder so) {
		this.so = so;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.util.PDFTemplate#execute(java.lang.Boolean, com.newco.marketplace.dto.vo.serviceorder.ServiceOrder, java.util.List, com.lowagie.text.Image)
	 */
	public void execute(PdfWriter pdfWriter, Document document) throws DocumentException {
		document.newPage();
		PdfPTable mainTable = createNewMainTable();
		if(1000 == so.getBuyerId().intValue()){
			addHeaderTable(mainTable);
		}		
		addAddonTable(mainTable);
		SOPDFUtils.drawLine(mainTable, 3);
		if(null != addOnPaymentDetails && null != customerSignatureImage && totalPrice != 0.00){
			if(1000 == so.getBuyerId().intValue()){
				addCreditAuthTableForMobileForSearsBuyer(mainTable);
			}else{
				addCreditAuthTableForMobile(mainTable);
			}
			SOPDFUtils.drawLine(mainTable, 3);
			document.add(mainTable);
			mainTable = createNewMainTable();
			addChangeOfSpecsTableForMobile(mainTable,document,pdfWriter);
		} else {
			if(1000 == so.getBuyerId().intValue()){
				addCreditAuthTableForSears(mainTable);
			}else{
				addCreditAuthTable(mainTable);
			}
			SOPDFUtils.drawLine(mainTable, 3);
			addChangeOfSpecsTable(mainTable);
			document.add(mainTable);
		}
	}
	
	private PdfPTable createNewMainTable() {
		float[] widths = { 180, 600 };
		PdfPTable mainTable = new PdfPTable(widths);
		mainTable.setSplitLate(false);
		if(1000 == so.getBuyerId().intValue()){
			mainTable.setHeaderRows(1);
		}		
		mainTable.getDefaultCell().setBorder(0);
		float pageWidth = PageSize.LETTER.getWidth();
		mainTable.setTotalWidth(pageWidth - 30);
		mainTable.setLockedWidth(true);
		return mainTable;
	}
	
	/**
	 * Method for adding the header table to the Service Order Description Table
	 * @param isCustomerCopy
	 * @param so
	 * @param docList
	 * @param logoImage
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
		PdfPCell soIdCell = new PdfPCell(new Paragraph(SOPDFConstants.SERVICE_ORDER_NUMBER + soId, SOPDFConstants.FONT_LABEL_BIG));
		soIdCell.setBorder(0);
		PdfPTable centerSoId = new PdfPTable(1);
		centerSoId.setHorizontalAlignment(Element.ALIGN_CENTER);
		centerSoId.addCell(soIdCell);
		headerTable.addCell(centerSoId);
		
		// Add Page Heading on right side
		PdfPCell titleCell = new PdfPCell(new Paragraph(mainPageTitle, SOPDFConstants.FONT_LABEL_BIG));
		titleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		titleCell.setBorder(0);
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
	 * Addon Table
	 * @param mainTable
	 */
	private void addAddonTable(PdfPTable mainTable) {
		// Create Addon Table
		float[] addonWidths = { 30, 30, 100, 200, 50 };
		PdfPTable addonsTable = new PdfPTable(addonWidths);
		addonsTable.setTotalWidth(PageSize.LETTER.getWidth() - 30);
		addonsTable.setLockedWidth(true);
		
		double totalAddOnPrice = 0.00;
		
		// Add Addon Table Heading Row
		PdfPCell addonTableHeadingCell = new PdfPCell(new Paragraph(SOPDFConstants.ADDON_TABLE_HEADING, SOPDFConstants.FONT_LABEL_BIG));
		addonTableHeadingCell.setColspan(5);
		addonsTable.addCell(addonTableHeadingCell);
		
		// Add Addon Columns Heading Row
		PdfPCell qtyHeadingCell = new PdfPCell(new Paragraph(SOPDFConstants.ADDON_TABLE_COL_HEADING_QTY, SOPDFConstants.FONT_HEADER));
		qtyHeadingCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		addonsTable.addCell(qtyHeadingCell);
		PdfPCell skuHeadingCell = new PdfPCell(new Paragraph(SOPDFConstants.ADDON_TABLE_COL_HEADING_SKU, SOPDFConstants.FONT_HEADER));
		addonsTable.addCell(skuHeadingCell);
		PdfPCell descHeadingCell = new PdfPCell(new Paragraph(SOPDFConstants.ADDON_TABLE_COL_HEADING_DESC, SOPDFConstants.FONT_HEADER));
		addonsTable.addCell(descHeadingCell);
		PdfPCell sowHeadingCell = new PdfPCell(new Paragraph(SOPDFConstants.ADDON_TABLE_COL_HEADING_SOW, SOPDFConstants.FONT_HEADER));
		addonsTable.addCell(sowHeadingCell);
		PdfPCell priceHeadingCell = new PdfPCell(new Paragraph(SOPDFConstants.ADDON_TABLE_COL_HEADING_PRICE, SOPDFConstants.FONT_HEADER));
		priceHeadingCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		addonsTable.addCell(priceHeadingCell);
		
		// Add addon SKU rows
		for (ServiceOrderAddonVO addonVO : soAddons) {
			Font addonTextFont = SOPDFConstants.FONT_LABEL;
			String qty = StringUtils.EMPTY;
			if (SOPDFConstants.COVERAGE_CC.equals(addonVO.getCoverage())) {
				qty = String.valueOf(addonVO.getQuantity());
				addonTextFont = SOPDFConstants.FONT_HEADER;
			}
			
			if(addonVO.getQuantity() > 0 && addonVO.getRetailPrice()!=0.00){
				qty = String.valueOf(addonVO.getQuantity());				
				totalAddOnPrice = totalAddOnPrice + (addonVO.getQuantity() * addonVO.getRetailPrice());
			}
			
			PdfPCell qtyCell = new PdfPCell(new Paragraph(qty, addonTextFont));
			qtyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			addonsTable.addCell(qtyCell);
			PdfPCell skuCell = new PdfPCell(new Paragraph(addonVO.getSku(), addonTextFont));
			addonsTable.addCell(skuCell);
			PdfPCell descCell = new PdfPCell(new Paragraph(addonVO.getDescription(), addonTextFont));
			addonsTable.addCell(descCell);
			PdfPCell sowCell = new PdfPCell(new Paragraph(addonVO.getScopeOfWork(), addonTextFont));
			addonsTable.addCell(sowCell);
			if(OrderConstants.TASK_LEVEL_PRICING.equals(so.getPriceType()) && (addonVO.getDescription().contains(SOPDFConstants.CHANGE_OF_SPECS)||addonVO.getDescription().contains(SOPDFConstants.PERMIT)) ){
				if(addonVO.getRetailPrice()==0.00){
				PdfPCell priceCell = new PdfPCell();
					addonsTable.addCell(priceCell);					
				}
				else
				{
					PdfPCell priceCell = new PdfPCell(new Paragraph(new java.text.DecimalFormat("$0.00").format(addonVO.getRetailPrice()), addonTextFont));
					priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				addonsTable.addCell(priceCell);
			}
				
			}
			else{
			PdfPCell priceCell = new PdfPCell(new Paragraph(new java.text.DecimalFormat("$0.00").format(addonVO.getRetailPrice()), addonTextFont));
			priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			addonsTable.addCell(priceCell);
			}

		}
		if(so.getBuyer().isPermitInd()){
		Integer	rowCount = 0;
			for(rowCount=0;rowCount<2;rowCount++){
				PdfPCell qtyCell = new PdfPCell();
				qtyCell.setMinimumHeight(20);
				addonsTable.addCell(qtyCell);
				PdfPCell skuCell = new PdfPCell();
				skuCell.setMinimumHeight(20);
				addonsTable.addCell(skuCell);
				PdfPCell descCell = new PdfPCell();
				descCell.setMinimumHeight(20);
				addonsTable.addCell(descCell);
				PdfPCell sowCell = new PdfPCell();
				sowCell.setMinimumHeight(20);
				addonsTable.addCell(sowCell);
				PdfPCell priceCell = new PdfPCell();
				priceCell.setMinimumHeight(20);
				addonsTable.addCell(priceCell);
			}
			
		}
		// Now, add entire addon table to main table
		PdfPCell addonTableCell = new PdfPCell();
		addonTableCell.setBorder(0);
		addonTableCell.setColspan(3);
		addonTableCell.addElement(addonsTable);
		mainTable.addCell(addonTableCell);
		
		// Set total price
		if(totalAddOnPrice != 0.00){
			totalPrice = totalAddOnPrice;
			PdfPCell totalPriceValCell = new PdfPCell(new Paragraph(new java.text.DecimalFormat("0.00").format(totalAddOnPrice), SOPDFConstants.FONT_LABEL));
			totalPriceValCell.setBorder(0);
			totalPriceValCell.setColspan(3);
			totalPriceValCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			totalPriceValCell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
			totalPriceValCell.setPaddingRight(10f);
			
			mainTable.addCell(totalPriceValCell);
		}
				
		// Add total cell
		PdfPCell totalPriceCell = new PdfPCell(new Paragraph(SOPDFConstants.TOTAL_ADDONS_PRICE, SOPDFConstants.FONT_LABEL));
		totalPriceCell.setBorder(0);
		totalPriceCell.setColspan(3);
		totalPriceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		mainTable.addCell(totalPriceCell);
	}
	
	/**
	 * Credit Authorization Payment Form
	 * @param mainTable
	 */
	private void addCreditAuthTable(PdfPTable mainTable) {
		PdfPCell creditAuthHeadingCell = new PdfPCell(new Paragraph(SOPDFConstants.ADDON_CREDIT_AUTH_HEADING, SOPDFConstants.FONT_HEADER));
		creditAuthHeadingCell.setBorder(0);
		creditAuthHeadingCell.setColspan(3);
		mainTable.addCell(creditAuthHeadingCell);
		
		PdfPCell creditAuthFormCell = new PdfPCell(new Paragraph(SOPDFConstants.ADDON_CREDIT_AUTH_FORM, SOPDFConstants.FONT_LABEL));
		creditAuthFormCell.setBorder(0);
		creditAuthFormCell.setColspan(3);
		mainTable.addCell(creditAuthFormCell);
		
		SOPDFUtils.drawLine(mainTable, 3);
		
		PdfPCell creditAuthAmtDetailsCell = new PdfPCell(new Paragraph(getAddonCheckPayableToString(), SOPDFConstants.FONT_LABEL));
		creditAuthAmtDetailsCell.setBorder(0);
		creditAuthAmtDetailsCell.setColspan(3);
		mainTable.addCell(creditAuthAmtDetailsCell);
	}
	
	//-- For SL-13580: Third Party PDF customizations
	private String getAddonCheckPayableToString() {
		String addonCheckPayableTo = SOPDFConstants.ADDON_CREDIT_AUTH_AMT_DETAILS;
		try {
			//PDF changes for Innovel start Sl-21288
			/*IApplicationPropertiesDao applicationPropertiesDao = (IApplicationPropertiesDao)MPSpringLoaderPlugIn.getCtx().getBean("applicationPropertiesDao");
			String thirdPartyProcessIDs = applicationPropertiesDao.query("ThirdPartyProcessIDs").getAppValue();
			if(StringUtils.isNotBlank(thirdPartyProcessIDs)) {
		    	List<String> processIdList = Arrays.asList(thirdPartyProcessIDs.split(","));
		    	 if (StringUtils.isNotBlank(processID) && processIdList.contains(processID)) {
		    		String thirdPartyChecksPayableTo = applicationPropertiesDao.query("ThirdPartyChecksPayableTo").getAppValue();
			    	List<String> checksPayableToList = Arrays.asList(thirdPartyChecksPayableTo.split(","));
		    		String thirdPartyAddonCheckPayableTo = checksPayableToList.get(processIdList.indexOf(processID));
		    		addonCheckPayableTo = addonCheckPayableTo.replaceFirst("Make checks payable to Sears", "Make checks payable to " + thirdPartyAddonCheckPayableTo);
		    	}
	    	}*/
			if(1000 == so.getBuyerId()) {
	    		String addInnovelSolutions = SOPDFConstants.ADDON_INNOVEL_SOLUTIONS;
	    		addonCheckPayableTo = addonCheckPayableTo.replaceFirst("Make checks payable to Sears", "Make checks payable to " + addInnovelSolutions);
			}
		}
		// catch (DataNotFoundException e) { } // Unexpected, Ignore
		//PDF changes for Innovel end  SL-21288
		catch (DataAccessException e) { } // Unexpected, Ignore
    	return addonCheckPayableTo;
	}
	
	/**
	 * Change of Specifications Form
	 * @param mainTable
	 */
	private void addChangeOfSpecsTable(PdfPTable mainTable) {
		PdfPCell chgSpecsHeadingCell = new PdfPCell(new Paragraph(SOPDFConstants.ADDON_CHG_SPECS_HEADING, SOPDFConstants.FONT_HEADER));
		chgSpecsHeadingCell.setBorder(0);
		chgSpecsHeadingCell.setColspan(3);
		mainTable.addCell(chgSpecsHeadingCell);
		//PDF changes for Innovel starts SL-21288
		PdfPCell chgSpecsFormCell = null;
		if(1000 == so.getBuyerId()) {
			chgSpecsFormCell = new PdfPCell(new Paragraph(SOPDFConstants.MOBILE_ADDON_CHG_SPECS_FORM_1 +SOPDFConstants.ADDON_CREDIT_CARD_TEXT_INNOVEL + SOPDFConstants.MOBILE_ADDON_CHG_SPECS_FORM_2, SOPDFConstants.FONT_LABEL));
		}else{
			chgSpecsFormCell = new PdfPCell(new Paragraph(SOPDFConstants.ADDON_CHG_SPECS_FORM , SOPDFConstants.FONT_LABEL));
		}
		 //PDF changes for Innovel end SL-21288
		chgSpecsFormCell.setBorder(0);
		chgSpecsFormCell.setColspan(3);
		mainTable.addCell(chgSpecsFormCell);
	}
	
	/**
	 * Credit Authorization Payment Form for Mobile
	 * @param mainTable
	 * @throws DocumentException 
	 */
	private void addCreditAuthTableForMobile(PdfPTable mainTable) throws DocumentException {
		
		PdfPCell creditAuthHeadingCell = new PdfPCell(new Paragraph(SOPDFConstants.ADDON_CREDIT_AUTH_HEADING, SOPDFConstants.FONT_HEADER));
		creditAuthHeadingCell.setBorder(0);
		creditAuthHeadingCell.setColspan(3);
		mainTable.addCell(creditAuthHeadingCell);
		
		if(!SOPDFConstants.MOBILE_CHECK_TYPE.equals(addOnPaymentDetails.getPaymentType())){
			PdfPCell creditAuthFormCell = new PdfPCell(new Paragraph(SOPDFConstants.MOBILE_ADDON_CREDIT_AUTH_FORM, SOPDFConstants.FONT_LABEL));
			creditAuthFormCell.setBorder(0);
			creditAuthFormCell.setColspan(3);
			
			mainTable.addCell(creditAuthFormCell);

			mainTable.addCell(createCCTable1());

			mainTable.addCell(createCCTable2());

			mainTable.addCell(createCCTable3());
			
			PdfPCell ccTableCell4 = new PdfPCell(new Paragraph(SOPDFConstants.MOBILE_ADDON_NEWLINE, SOPDFConstants.FONT_LABEL));
			ccTableCell4.setBorder(0);
			ccTableCell4.setColspan(3);
			mainTable.addCell(ccTableCell4);
		} else {		
			PdfPCell creditAuthFormCell = new PdfPCell(new Paragraph(SOPDFConstants.ADDON_CREDIT_AUTH_FORM, SOPDFConstants.FONT_LABEL));
			creditAuthFormCell.setBorder(0);
			creditAuthFormCell.setColspan(3);
			mainTable.addCell(creditAuthFormCell);
		}
		
		SOPDFUtils.drawLine(mainTable, 3);
		
		PdfPCell creditAuthAmtDetailsCell = new PdfPCell(getAddonCheckPayableToStringForMobile());
		creditAuthAmtDetailsCell.setBorder(0);
		creditAuthAmtDetailsCell.setColspan(3);
		mainTable.addCell(creditAuthAmtDetailsCell);
		
		PdfPCell newLineCell = new PdfPCell(new Paragraph(SOPDFConstants.MOBILE_ADDON_NEWLINE, SOPDFConstants.FONT_LABEL));
		newLineCell.setBorder(0);
		newLineCell.setColspan(3);
		mainTable.addCell(newLineCell);
	}
	
	private PdfPCell createCCTable1() throws DocumentException{
		float[] widths = { 15, 24, 8, 24, 5, 24 };
		
		PdfPTable ccTable1 = new PdfPTable(6);
		ccTable1.setWidthPercentage(90);
		ccTable1.setSpacingAfter(0f);	
		ccTable1.setHorizontalAlignment(Element.ALIGN_LEFT);
		ccTable1.setWidths(widths);
		
		StringBuilder expirationDate = new StringBuilder("").append(addOnPaymentDetails.getCardExpireMonth()).append("/").append(addOnPaymentDetails.getCardExpireYear());
		
		ccTable1.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(addOnPaymentDetails.getCcType(), SOPDFConstants.FONT_LABEL,true));
		ccTable1.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(addOnPaymentDetails.getCcNumber(), SOPDFConstants.FONT_LABEL,true));
		ccTable1.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(expirationDate.toString(), SOPDFConstants.FONT_LABEL,true));
		
		ccTable1.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_TEXT_1, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_PLACEHOLDERLINE, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_TEXT_2, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_PLACEHOLDERLINE, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_TEXT_3, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_PLACEHOLDERLINE, SOPDFConstants.FONT_LABEL,false));
	
		ccTable1.setKeepTogether(true);

		PdfPCell ccTableCell1 = new PdfPCell();
		ccTableCell1.setColspan(3);
		ccTableCell1.setBorder(0);
		ccTableCell1.addElement(ccTable1);
		
		return ccTableCell1;
	}
	
	private PdfPCell createCCTable2() throws DocumentException{
		float[] widths1 = { 20, 36, 8, 36};
		
		PdfPTable ccTable2 = new PdfPTable(4);
		ccTable2.setWidthPercentage(60);
		ccTable2.setSpacingAfter(0f);
		ccTable2.setHorizontalAlignment(Element.ALIGN_LEFT);
		ccTable2.setWidths(widths1);
		
		ccTable2.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
		ccTable2.addCell(addNewCell(addOnPaymentDetails.getPreAuthNumber(), SOPDFConstants.FONT_LABEL,true));
		ccTable2.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
		ccTable2.addCell(addNewCell(currentDate, SOPDFConstants.FONT_LABEL,true));
		
		ccTable2.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_TEXT_4, SOPDFConstants.FONT_LABEL,false));
		ccTable2.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_PLACEHOLDERLINE, SOPDFConstants.FONT_LABEL,false));
		ccTable2.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_TEXT_5, SOPDFConstants.FONT_LABEL,false));
		ccTable2.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_PLACEHOLDERLINE, SOPDFConstants.FONT_LABEL,false));
		
		ccTable2.setKeepTogether(true);

		PdfPCell ccTableCell2 = new PdfPCell();
		ccTableCell2.setColspan(3);
		ccTableCell2.setBorder(0);
		ccTableCell2.addElement(ccTable2);
		
		return ccTableCell2;
	}

	private PdfPCell createCCTable3() throws DocumentException{
		float[] widths2 = { 35, 65};
		
		PdfPTable ccTable3 = new PdfPTable(2);
		ccTable3.setWidthPercentage(40);
		ccTable3.setSpacingAfter(0f);
		ccTable3.setHorizontalAlignment(Element.ALIGN_LEFT);
		ccTable3.setWidths(widths2);
		
		PdfPCell imageCell1 = new PdfPCell(customerSignatureImage);
		imageCell1.setBorder(0);
		imageCell1.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
		imageCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
		imageCell1.setPaddingLeft(10f);
		
		
		ccTable3.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
		ccTable3.addCell(imageCell1);
		
		ccTable3.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_TEXT_6, SOPDFConstants.FONT_LABEL,false));
		ccTable3.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_PLACEHOLDERLINE, SOPDFConstants.FONT_LABEL,false));
		
		ccTable3.setKeepTogether(true);

		PdfPCell ccTableCell3 = new PdfPCell();
		ccTableCell3.setColspan(3);
		ccTableCell3.setBorder(0);
		ccTableCell3.addElement(ccTable3);
		
		return ccTableCell3;
	}
	
	private PdfPCell addNewCell(String value, Font font, boolean isPaddingRequired){
		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		if(isPaddingRequired){
			cell.setPaddingLeft(25f);
		}
		cell.addElement(new Paragraph(value, font));
		
		return cell;
	}
	
	private PdfPCell getAddonCheckPayableToStringForMobile() throws DocumentException {
		float[] widths = { 16, 13, 12, 13, 3, 13, 30 };
		
		PdfPTable ccTable1 = new PdfPTable(7);
		ccTable1.setWidthPercentage(95);
		ccTable1.setSpacingAfter(0f);	
		ccTable1.setHorizontalAlignment(Element.ALIGN_LEFT);
		ccTable1.setWidths(widths);
		
		if(SOPDFConstants.MOBILE_CHECK_TYPE.equals(addOnPaymentDetails.getPaymentType())){
			ccTable1.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
			ccTable1.addCell(addNewCell("", SOPDFConstants.FONT_LABEL,true));
			ccTable1.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
			ccTable1.addCell(addNewCell(new java.text.DecimalFormat("0.00").format(totalPrice), SOPDFConstants.FONT_LABEL,true));
			ccTable1.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
			ccTable1.addCell(addNewCell(addOnPaymentDetails.getCheckNumber(), SOPDFConstants.FONT_LABEL,true));
			ccTable1.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
		} else {
			ccTable1.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
			ccTable1.addCell(addNewCell(new java.text.DecimalFormat("0.00").format(totalPrice), SOPDFConstants.FONT_LABEL,true));
			ccTable1.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
			ccTable1.addCell(addNewCell("", SOPDFConstants.FONT_LABEL,true));
			ccTable1.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
			ccTable1.addCell(addNewCell("", SOPDFConstants.FONT_LABEL,true));
			ccTable1.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
		}
		
		ccTable1.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_TEXT_7, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_PLACEHOLDERLINE_SHORT, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_TEXT_8, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_PLACEHOLDERLINE_SHORT, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_TEXT_9, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_PLACEHOLDERLINE_SHORT, SOPDFConstants.FONT_LABEL,false));
			
		String addonCheckPayableTo = SOPDFConstants.MOBILE_ADDON_TEXT_10;
		try {
			//PDF changes for Innovel starts SL-21288
			/*IApplicationPropertiesDao applicationPropertiesDao = (IApplicationPropertiesDao)MPSpringLoaderPlugIn.getCtx().getBean("applicationPropertiesDao");
			String thirdPartyProcessIDs = applicationPropertiesDao.query("ThirdPartyProcessIDs").getAppValue();
			if(StringUtils.isNotBlank(thirdPartyProcessIDs)) {
		    	List<String> processIdList = Arrays.asList(thirdPartyProcessIDs.split(","));
		    		if (StringUtils.isNotBlank(processID) && processIdList.contains(processID)) {
			    		String thirdPartyChecksPayableTo = applicationPropertiesDao.query("ThirdPartyChecksPayableTo").getAppValue();
				    	List<String> checksPayableToList = Arrays.asList(thirdPartyChecksPayableTo.split(","));
				    	String thirdPartyAddonCheckPayableTo = checksPayableToList.get(processIdList.indexOf(processID));
				    	addonCheckPayableTo = addonCheckPayableTo.replaceFirst("Make checks payable to Sears", "Make checks payable to " + thirdPartyAddonCheckPayableTo);
			    	} 
	    	}*/
			if(1000 == so.getBuyerId()) {
	    		String addInnovelSolutions = SOPDFConstants.ADDON_INNOVEL_SOLUTIONS;
	    		addonCheckPayableTo = addonCheckPayableTo.replaceFirst("Make checks payable to Sears", "Make checks payable to " + addInnovelSolutions);
			}
		}
		// catch (DataNotFoundException e) { } // Unexpected, Ignore
		//PDF changes for Innovel end  SL-21288
		catch (DataAccessException e) { } // Unexpected, Ignore
		
		ccTable1.addCell(addNewCell(addonCheckPayableTo, SOPDFConstants.FONT_LABEL,false));
		
		ccTable1.setKeepTogether(true);

		PdfPCell ccTableCell1 = new PdfPCell();
		ccTableCell1.setColspan(3);
		ccTableCell1.setBorder(0);
		ccTableCell1.addElement(ccTable1);
		
    	return ccTableCell1;
	}
	
	/**
	 * Change of Specifications Form for mobile
	 * @param mainTable
	 * @param pdfWriter 
	 * @throws DocumentException 
	 */
	private void addChangeOfSpecsTableForMobile(PdfPTable mainTable, Document document, PdfWriter pdfWriter) throws DocumentException {
		PdfPTable testTable = createNewMainTable();

		PdfPCell chgSpecsHeadingCell = new PdfPCell(new Paragraph(SOPDFConstants.ADDON_CHG_SPECS_HEADING, SOPDFConstants.FONT_HEADER));
		chgSpecsHeadingCell.setBorder(0);
		chgSpecsHeadingCell.setColspan(3);
		// mainTable.addCell(chgSpecsHeadingCell);
		
		 //PDF changes for Innovel starts SL-21288
		PdfPCell chgSpecsFormCell1=null;
		if(1000 == so.getBuyerId()) {
			chgSpecsFormCell1 = new PdfPCell(new Paragraph(SOPDFConstants.MOBILE_ADDON_CHG_SPECS_FORM_1+SOPDFConstants.ADDON_CREDIT_CARD_TEXT_INNOVEL, SOPDFConstants.FONT_LABEL));
		}else{
			chgSpecsFormCell1 = new PdfPCell(new Paragraph(SOPDFConstants.MOBILE_ADDON_CHG_SPECS_FORM_1, SOPDFConstants.FONT_LABEL));
		}
		 //PDF changes for Innovel end SL-21288
		chgSpecsFormCell1.setBorder(0);
		chgSpecsFormCell1.setColspan(3);
	//	mainTable.addCell(chgSpecsFormCell1);
		//document.add(mainTable);

		/** Image */
		PdfPCell imageCell1 = new PdfPCell(customerSignatureImage);
		imageCell1.setBorder(0);
		imageCell1.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
		imageCell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		
		/** Date */
		PdfPCell imageCell2 = new PdfPCell(new Paragraph(currentDate, SOPDFConstants.FONT_LABEL));
		imageCell2.setBorder(0);
		imageCell2.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
		imageCell2.setHorizontalAlignment(Element.ALIGN_LEFT);

		float[] widths = { 50, 20, 30};
		PdfPTable signTable = new PdfPTable(3);
		signTable.setWidthPercentage(80);
		signTable.setSpacingAfter(0f);	
		signTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		signTable.setWidths(widths);
	
		testTable.addCell(chgSpecsHeadingCell);
		testTable.addCell(chgSpecsFormCell1);
		
		signTable.addCell(imageCell1);
		signTable.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
		signTable.addCell(imageCell2);
		
		signTable.setKeepTogether(true);

		PdfPCell signTableCell = new PdfPCell();
		signTableCell.setColspan(3);
		signTableCell.setBorder(0);
		signTableCell.addElement(signTable);
		testTable.addCell(signTableCell);


		
		PdfPCell chgSpecsFormCell2 = new PdfPCell(new Paragraph(SOPDFConstants.MOBILE_ADDON_CHG_SPECS_FORM_2, SOPDFConstants.FONT_LABEL));
		chgSpecsFormCell2.setBorder(0);
		chgSpecsFormCell2.setColspan(3);
		testTable.addCell(chgSpecsFormCell2);

		if(!pdfWriter.fitsPage(testTable)){
			mainTable.setKeepTogether(true);
			if(1000 == so.getBuyerId().intValue()){
				addHeaderTable(mainTable);
			}
		}
		mainTable.addCell(chgSpecsHeadingCell);
		mainTable.addCell(chgSpecsFormCell1);
		mainTable.addCell(signTableCell);
		mainTable.addCell(chgSpecsFormCell2);

		document.add(mainTable);

	}
	
	/**
	 * Credit Authorization Payment Form for Mobile
	 * @param mainTable
	 * @throws DocumentException 
	 */
	private void addCreditAuthTableForMobileForSearsBuyer(PdfPTable mainTable) throws DocumentException {
		
		PdfPCell creditAuthHeadingCell = new PdfPCell(new Paragraph(SOPDFConstants.ADDON_CREDIT_AUTH_HEADING, SOPDFConstants.FONT_HEADER));
		creditAuthHeadingCell.setBorder(0);
		creditAuthHeadingCell.setColspan(3);
		mainTable.addCell(creditAuthHeadingCell);
		
		if(!SOPDFConstants.MOBILE_CHECK_TYPE.equals(addOnPaymentDetails.getPaymentType())){
			PdfPCell creditAuthFormCell = new PdfPCell(new Paragraph(SOPDFConstants.MOBILE_ADDON_CREDIT_AUTH_FORM, SOPDFConstants.FONT_LABEL));
			creditAuthFormCell.setBorder(0);
			creditAuthFormCell.setColspan(3);
			
			mainTable.addCell(creditAuthFormCell);

			mainTable.addCell(createCCTable1ForSearsBuyer());

			mainTable.addCell(createCCTable2ForSearsBuyer());

			mainTable.addCell(createCCTable3ForSearsBuyer());
			
			PdfPCell ccTableCell4 = new PdfPCell(new Paragraph(SOPDFConstants.MOBILE_ADDON_NEWLINE, SOPDFConstants.FONT_LABEL));
			ccTableCell4.setBorder(0);
			ccTableCell4.setColspan(3);
			mainTable.addCell(ccTableCell4);
		} else {		
			PdfPCell creditAuthFormCell = new PdfPCell(new Paragraph(SOPDFConstants.ADDON_CREDIT_AUTH_FORM_SEARS, SOPDFConstants.FONT_LABEL));
			creditAuthFormCell.setBorder(0);
			creditAuthFormCell.setColspan(3);
			mainTable.addCell(creditAuthFormCell);
		}
		
		SOPDFUtils.drawLine(mainTable, 3);
		
		PdfPCell creditAuthAmtDetailsCell = new PdfPCell(getAddonCheckPayableToStringForMobile());
		creditAuthAmtDetailsCell.setBorder(0);
		creditAuthAmtDetailsCell.setColspan(3);
		mainTable.addCell(creditAuthAmtDetailsCell);
		
		PdfPCell newLineCell = new PdfPCell(new Paragraph(SOPDFConstants.MOBILE_ADDON_NEWLINE, SOPDFConstants.FONT_LABEL));
		newLineCell.setBorder(0);
		newLineCell.setColspan(3);
		mainTable.addCell(newLineCell);
	}
	
	private PdfPCell createCCTable1ForSearsBuyer() throws DocumentException{
		float[] widths = { 14, 25, 15, 13, 9, 41 };
		
		PdfPTable ccTable1 = new PdfPTable(6);
		ccTable1.setWidthPercentage(100);
		ccTable1.setSpacingAfter(0f);	
		ccTable1.setHorizontalAlignment(Element.ALIGN_LEFT);
		ccTable1.setWidths(widths);
		StringBuilder expirationDate = new StringBuilder("").append(addOnPaymentDetails.getCardExpireMonth()).append("/").append(addOnPaymentDetails.getCardExpireYear());
		
		ccTable1.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(addOnPaymentDetails.getCcType(), SOPDFConstants.FONT_LABEL,true));
		ccTable1.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(addOnPaymentDetails.getCcNumber(), SOPDFConstants.FONT_LABEL,true));
		ccTable1.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(expirationDate.toString(), SOPDFConstants.FONT_LABEL,true));
		
		ccTable1.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_TEXT_1, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_PLACEHOLDERLINE, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_TEXT_2_SEARS, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_PLACEHOLDERLINE_SHORT1, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_TEXT_3_SEARS, SOPDFConstants.FONT_LABEL,false));
		ccTable1.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_PLACEHOLDERLINE_SHORT, SOPDFConstants.FONT_LABEL,false));
	
		ccTable1.setKeepTogether(true);

		PdfPCell ccTableCell1 = new PdfPCell();
		ccTableCell1.setColspan(3);
		ccTableCell1.setBorder(0);
		ccTableCell1.addElement(ccTable1);
		
		return ccTableCell1;
	}
	
	private PdfPCell createCCTable2ForSearsBuyer() throws DocumentException{
		float[] widths1 = { 20, 30, 8, 42};
		
		PdfPTable ccTable2 = new PdfPTable(4);
		ccTable2.setWidthPercentage(60);
		ccTable2.setSpacingAfter(0f);
		ccTable2.setHorizontalAlignment(Element.ALIGN_LEFT);
		ccTable2.setWidths(widths1);
		
		ccTable2.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
		ccTable2.addCell(addNewCell(addOnPaymentDetails.getPreAuthNumber(), SOPDFConstants.FONT_LABEL,true));
		ccTable2.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
		ccTable2.addCell(addNewCell(currentDate, SOPDFConstants.FONT_LABEL,true));
		
		ccTable2.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_TEXT_4, SOPDFConstants.FONT_LABEL,false));
		ccTable2.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_PLACEHOLDERLINE_MEDIUM, SOPDFConstants.FONT_LABEL,false));
		ccTable2.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_TEXT_5, SOPDFConstants.FONT_LABEL,false));
		ccTable2.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_PLACEHOLDERLINE_MEDIUM1, SOPDFConstants.FONT_LABEL,false));
		
		ccTable2.setKeepTogether(true);

		PdfPCell ccTableCell2 = new PdfPCell();
		ccTableCell2.setColspan(3);
		ccTableCell2.setBorder(0);
		ccTableCell2.addElement(ccTable2);
		
		return ccTableCell2;
	}

	private PdfPCell createCCTable3ForSearsBuyer() throws DocumentException{
		float[] widths2 = {25, 75};
		
		PdfPTable ccTable3 = new PdfPTable(2);
		ccTable3.setWidthPercentage(60);
		ccTable3.setSpacingAfter(0f);
		ccTable3.setHorizontalAlignment(Element.ALIGN_LEFT);
		ccTable3.setWidths(widths2);
		
		PdfPCell imageCell1 = new PdfPCell(customerSignatureImage);
		imageCell1.setBorder(0);
		imageCell1.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
		imageCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
		imageCell1.setPaddingLeft(10f);
		
		
		ccTable3.addCell(addNewCell(StringUtils.EMPTY, SOPDFConstants.FONT_LABEL,false));
		ccTable3.addCell(imageCell1);
		
		ccTable3.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_TEXT_6, SOPDFConstants.FONT_LABEL,false));
		ccTable3.addCell(addNewCell(SOPDFConstants.MOBILE_ADDON_PLACEHOLDERLINE_LONG, SOPDFConstants.FONT_LABEL,false));
		
		ccTable3.setKeepTogether(true);

		PdfPCell ccTableCell3 = new PdfPCell();
		ccTableCell3.setColspan(3);
		ccTableCell3.setBorder(0);
		ccTableCell3.addElement(ccTable3);
		
		return ccTableCell3;
	}
	
	
	/**
	 * Credit Authorization Payment Form
	 * @param mainTable
	 */
	private void addCreditAuthTableForSears(PdfPTable mainTable) {
		PdfPCell creditAuthHeadingCell = new PdfPCell(new Paragraph(SOPDFConstants.ADDON_CREDIT_AUTH_HEADING, SOPDFConstants.FONT_HEADER));
		creditAuthHeadingCell.setBorder(0);
		creditAuthHeadingCell.setColspan(3);
		mainTable.addCell(creditAuthHeadingCell);
		
		PdfPCell creditAuthFormCell = new PdfPCell(new Paragraph(SOPDFConstants.ADDON_CREDIT_AUTH_FORM_SEARS, SOPDFConstants.FONT_LABEL));
		creditAuthFormCell.setBorder(0);
		creditAuthFormCell.setColspan(3);
		mainTable.addCell(creditAuthFormCell);
		
		SOPDFUtils.drawLine(mainTable, 3);
		
		PdfPCell creditAuthAmtDetailsCell = new PdfPCell(new Paragraph(getAddonCheckPayableToString(), SOPDFConstants.FONT_LABEL));
		creditAuthAmtDetailsCell.setBorder(0);
		creditAuthAmtDetailsCell.setColspan(3);
		mainTable.addCell(creditAuthAmtDetailsCell);
	}
	
	public Image getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(Image logoImage) {
		this.logoImage = logoImage;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getMainPageTitle() {
		return mainPageTitle;
	}

	public void setMainPageTitle(String mainPageTitle) {
		this.mainPageTitle = mainPageTitle;
	}

	public java.util.List<ServiceOrderAddonVO> getSoAddons() {
		return soAddons;
	}

	public void setSoAddons(java.util.List<ServiceOrderAddonVO> soAddons) {
		this.soAddons = soAddons;
	}
	
	public String getProcessID() {
		return processID;
}

	public void setProcessID(String processID) {
		this.processID = processID;
	}

	public AddonPayment getAddOnPaymentDetails() {
		return addOnPaymentDetails;
	}

	public void setAddOnPaymentDetails(AddonPayment addOnPaymentDetails) {
		this.addOnPaymentDetails = addOnPaymentDetails;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Image getCustomerSignatureImage() {
		return customerSignatureImage;
	}

	public void setCustomerSignatureImage(Image customerSignatureImage) {
		this.customerSignatureImage = customerSignatureImage;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	
}
