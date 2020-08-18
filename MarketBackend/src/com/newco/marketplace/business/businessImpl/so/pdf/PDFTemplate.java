package com.newco.marketplace.business.businessImpl.so.pdf;

import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;

/**
 * This interface defines execute() method for any PDF template that must provide implementation for.
 */
public interface PDFTemplate {

	/**
	 * <p>The method generates the list of PdfPTable objects; based on contents the PDFWriter can fit in on single PDF Page.
	 * <p>The client may write these PdfPTable object on individual different pages of PDF.
	 * 
	 * @param pdfWriter common object required by all implementation classes
	 * @param Document the pdf document object
	 * @return List<PdfPTable>
	 * @throws DocumentException
	 */
	public void execute(PdfWriter pdfWriter, Document document) throws DocumentException, IOException;
	
}
