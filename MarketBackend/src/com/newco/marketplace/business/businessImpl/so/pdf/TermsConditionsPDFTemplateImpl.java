package com.newco.marketplace.business.businessImpl.so.pdf;

import java.io.StringReader;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.List;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.newco.marketplace.util.constants.SOPDFConstants;
import com.newco.marketplace.utils.ServiceLiveStringUtils;

public class TermsConditionsPDFTemplateImpl implements PDFTemplate{
	
	private Logger logger = Logger.getLogger(TermsConditionsPDFTemplateImpl.class);
	
	private Image logoImage;
	private String termsCond;
	
	public void execute(PdfWriter pdfWriter, Document document) throws DocumentException {
		float pageWidth = PageSize.LETTER.getWidth();
		float[] termsConditionsTableWidths = { 180, 180, 180 };
		PdfPTable termsConditionsTable = new PdfPTable(termsConditionsTableWidths);
		termsConditionsTable.setSplitLate(false);
		PdfPCell cell = null;
		termsConditionsTable.getDefaultCell().setBorder(0);
		termsConditionsTable.setHorizontalAlignment(Element.ALIGN_CENTER);
		termsConditionsTable.setTotalWidth(pageWidth - 72);
		termsConditionsTable.setLockedWidth(true);
		//adding terms and conditions
		PdfPTable tableTermsCond = this.addTermsAndConditionsTable();
		cell = new PdfPCell(tableTermsCond);
		cell.setColspan(3);
		cell.setMinimumHeight(300);
		termsConditionsTable.addCell(cell);

		// Empty Rows
		SOPDFUtils.addEmptyRow(3, 0, -1, termsConditionsTable);	
		SOPDFUtils.addEmptyRow(3, 0, -1, termsConditionsTable);
		
		document.newPage();
		document.add(termsConditionsTable);
	}
	
	/**
	 * Method for adding terms and conditions to table
	 * @param so
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private PdfPTable addTermsAndConditionsTable() {
		float[] tableTermsCondWidth = { 550 };
		PdfPTable tableTermsCond = new PdfPTable(tableTermsCondWidth);
		tableTermsCond.getDefaultCell().setBorder(1);
		tableTermsCond.setHorizontalAlignment(Element.ALIGN_LEFT);
		PdfPCell cellTermsCond = null;
		StringReader strTermsCond = new StringReader(termsCond);
		StyleSheet style = new StyleSheet();
				
		cellTermsCond = new PdfPCell(new Phrase(SOPDFConstants.BUYER_TERMS_COND, SOPDFConstants.FONT_HEADER));
		cellTermsCond.setBorder(0);
		cellTermsCond.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableTermsCond.addCell(cellTermsCond);
		
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
			tableTermsCond.addCell(cellTermsCond);
		}
		else{
			terms = termsCond;
			//Remove the html tags
			terms = ServiceLiveStringUtils.removeHTML(terms);
			cellTermsCond = new PdfPCell(new Phrase(terms, SOPDFConstants.FONT_LABEL_SMALL));
			cellTermsCond.setBorder(0);
			tableTermsCond.addCell(cellTermsCond);
			/*java.util.List<Paragraph> parasList = null;
			try {
				parasList = (java.util.List<Paragraph>)HTMLWorker.parseToList(strTermsCond, style);
			} catch (IOException ioEx) {
				logger.error("Unexpected IO Error while converting terms conditions HTML into list of iText Paragraphs", ioEx);
			}
			
			if (parasList != null) {
				for(Paragraph para : parasList) {								
					cellTermsCond = new PdfPCell(new Phrase(para.getContent(), SOPDFConstants.FONT_LABEL_SMALL));
					cellTermsCond.setBorder(0);
					tableTermsCond.addCell(cellTermsCond);
				}
			}*/
		}
		
		cellTermsCond = new PdfPCell(new Phrase(" "));
		cellTermsCond.setBorder(0);
		tableTermsCond.addCell(cellTermsCond);
		return tableTermsCond;
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
