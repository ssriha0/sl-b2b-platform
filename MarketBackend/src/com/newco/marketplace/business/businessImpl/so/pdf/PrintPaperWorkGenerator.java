package com.newco.marketplace.business.businessImpl.so.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.exception.core.DataNotFoundException;
import com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao;
import com.newco.marketplace.util.DocumentUtils;
import com.newco.marketplace.util.constants.SOPDFConstants;

// this class is developed for SL-15642 for print paper work action

public class PrintPaperWorkGenerator extends PdfPageEventHelper {
	private static final Logger logger = Logger.getLogger(PrintPaperWorkGenerator.class);

	public ByteArrayOutputStream printPaperwork(List<ServiceOrder> list, List<DocumentVO> logoDocumentVOs, List<String> checkedOptions, List<List<DocumentVO>> docLists) {
		logger.info("INSIDE PrintPaperWorkGenerator ::: While Creating Tables for PDF");
		ByteArrayOutputStream pdfStream = null;
		Document document = null;
		List<Integer> pageNo =new ArrayList<Integer>();
		List<Integer> custCopyEnds= new ArrayList<Integer>();
		List<Image> nonRiLogoImages = new ArrayList<Image>();
		Image logoImage = null;
		boolean omFlag = true;
		int size;
		int pageno;
		int index=0;
		DocumentVO logoDocumentVO = new DocumentVO();
		try {
			document = new Document(PageSize.LETTER, 20, 20, 20, 20);
			pdfStream = new ByteArrayOutputStream();
			PdfWriter pdfWriter = PdfWriter.getInstance(document, pdfStream);
			pdfWriter.setPageEvent(new PDFGenerator());
			document.open(); 

			for(ServiceOrder so: list){
				
				omFlag = true;
				logoImage = null;
				if(index<logoDocumentVOs.size()){
					logoDocumentVO = logoDocumentVOs.get(index);
				}
				if (logoDocumentVO != null && logoDocumentVO.getBlobBytes() != null) 
					{
					try {
					logoImage = Image.getInstance(logoDocumentVO.getBlobBytes());
					if (so.getBuyerId() != 1000) {
						if (DocumentUtils.IsResizable(
								logoDocumentVO.getBlobBytes(),
								SOPDFConstants.DEFAULT_LOGO_PICTURE_WIDTH,
								SOPDFConstants.DEFAULT_LOGO_PICTURE_HEIGHT)) {
							byte[] resizedlogoImage = DocumentUtils.resizeoImage(
									logoDocumentVO.getBlobBytes(),
									SOPDFConstants.DEFAULT_LOGO_PICTURE_WIDTH,
									SOPDFConstants.DEFAULT_LOGO_PICTURE_HEIGHT);
							logoImage = Image.getInstance(resizedlogoImage);
						}
					} else {
						logoImage = Image.getInstance(logoDocumentVO
								.getBlobBytes());
					}
					if (logoImage != null) {
						String processID = extractCustomRefValueByCustomRefName(so.getCustomRefs(), "ProcessID");
						scaleLogo(logoImage, processID);
					}
				} catch (Exception ioe) {
					logger.warn("Unable to parse logo image at " + logoDocumentVO.getDocPath() + ".  Using default.", ioe);
				}
			}
				if (logoImage==null) try {
					//logoImage = Image.getInstance(new ClassPathResource("resources/images/service_live_logo.gif").getURL());
					ClassPathResource classRes = new ClassPathResource("resources/images/service_live_logo.gif");
					File file = classRes.getFile();
					byte[] logoImageByte = FileUtils.readFileToByteArray(file);
					// Start--Made changes to solve JIRA SL-18178
					//				byte[] resizedlogoImage = DocumentUtils.scaleImage(
					//						logoImageByte,
					//						SOPDFConstants.DEFAULT_LOGO_PICTURE_WIDTH,
					//						SOPDFConstants.DEFAULT_LOGO_PICTURE_HEIGHT);
					logoImage = Image.getInstance(logoImageByte);
					// Start--End changes to solve JIRA SL-18178
				} catch (Exception ioe) {
					logger.error("Unable to parse default logo image.", ioe);
				}


				// Write PDFDocument to output byte stream
				//document = new Document(PageSize.LETTER);

				// Open the document/stream and start adding data tables to it

				//pdf for RI buyers
				// Customer Copy for RI orders

				SOAddonPDFTemplateImpl soAddonPDFTemplate=null;
				List<ServiceOrderAddonVO> addons = so.getUpsellInfo();

				for(String docType : checkedOptions){
					
					if(so.getBuyerId()==1000){
						soAddonPDFTemplate = new SOAddonPDFTemplateImpl();
						soAddonPDFTemplate.setLogoImage(logoImage);
						soAddonPDFTemplate.setSoId(so.getSoId());
						soAddonPDFTemplate.setSo(so);
						soAddonPDFTemplate.setSoAddons(addons);
						soAddonPDFTemplate.setProcessID(extractCustomRefValueByCustomRefName(so.getCustomRefs(), "ProcessID"));
						
						if(docType.equalsIgnoreCase(SOPDFConstants.CUSTOMER_COPY)) {
							RISODescriptionPDFTemplateImpl soDescriptionCustCopyRIPDFTemplate = new RISODescriptionPDFTemplateImpl();
							soDescriptionCustCopyRIPDFTemplate.setMainPageTitle(SOPDFConstants.CUSTOMER_COPY);
							soDescriptionCustCopyRIPDFTemplate.setSo(so);
							soDescriptionCustCopyRIPDFTemplate.setLogoImage(logoImage);
							soDescriptionCustCopyRIPDFTemplate.execute(pdfWriter, document,omFlag);

							if (addons != null && !addons.isEmpty()) {
								soAddonPDFTemplate.setMainPageTitle(SOPDFConstants.CUSTOMER_COPY);
								soAddonPDFTemplate.execute(pdfWriter, document);
							}
						}
						else{
							//Provider Copy for RI orders
							if(checkedOptions.size()!=1){
								omFlag=false;
							}
							RISODescriptionPDFTemplateImpl soDescriptionProCopyRIPDFTemplate = new RISODescriptionPDFTemplateImpl();
							soDescriptionProCopyRIPDFTemplate.setMainPageTitle(SOPDFConstants.PROVIDER_COPY);
							soDescriptionProCopyRIPDFTemplate.setSo(so);
							soDescriptionProCopyRIPDFTemplate.setLogoImage(logoImage);
							soDescriptionProCopyRIPDFTemplate.execute(pdfWriter, document,omFlag);

							if (addons != null && !addons.isEmpty()) {
								soAddonPDFTemplate.setMainPageTitle(SOPDFConstants.PROVIDER_COPY);
								soAddonPDFTemplate.execute(pdfWriter, document);
							}
							// Provider Instructions for RI orders
							RIServiceProInstrPDFTemplateImpl  serviceProInstRIPDFTemplate = new RIServiceProInstrPDFTemplateImpl();
							serviceProInstRIPDFTemplate.setLogoImage(logoImage);
							serviceProInstRIPDFTemplate.setSo(so);
							List<DocumentVO> docList = new ArrayList<DocumentVO>();
							docList = docLists.get(index);
							serviceProInstRIPDFTemplate.setDocList(docList);
							String buyerTermsCond = so.getBuyerTermsCond();
							//In case of simple buyer this value comes through as null and gives an exception in the log
							//Temporary fix to stop it from filling up the logs, is to initialise this as an empty string.
							if (buyerTermsCond == null) {
								buyerTermsCond = StringUtils.EMPTY;
							}
							serviceProInstRIPDFTemplate.setTermsCond(buyerTermsCond);
							serviceProInstRIPDFTemplate.execute(pdfWriter, document);
							
						}
						

					}

					//for other buyers
					else{

						soAddonPDFTemplate = new SOAddonPDFTemplateImpl();
						soAddonPDFTemplate.setLogoImage(logoImage);
						soAddonPDFTemplate.setSoId(so.getSoId());
						soAddonPDFTemplate.setSo(so);
						soAddonPDFTemplate.setSoAddons(addons);
						soAddonPDFTemplate.setProcessID(extractCustomRefValueByCustomRefName(so.getCustomRefs(), "ProcessID"));
						// Customer Copy for other orders
						if(docType.equalsIgnoreCase(SOPDFConstants.CUSTOMER_COPY)) {
							SODescriptionPDFTemplateImpl soDescriptionCustCopyPDFTemplate = new SODescriptionPDFTemplateImpl();
							soDescriptionCustCopyPDFTemplate.setMainPageTitle(SOPDFConstants.CUSTOMER_COPY);
							soDescriptionCustCopyPDFTemplate.setSo(so);
							soDescriptionCustCopyPDFTemplate.setLogoImage(logoImage);

							soDescriptionCustCopyPDFTemplate.execute(pdfWriter, document);
							
							
							
							if (addons != null && !addons.isEmpty()) {
								
								soAddonPDFTemplate.setMainPageTitle(SOPDFConstants.CUSTOMER_COPY);

								soAddonPDFTemplate.execute(pdfWriter, document);
							}
							size = custCopyEnds.size();
							pageno = pdfWriter.getCurrentPageNumber();
							custCopyEnds.add(pageno);
						}
						else{
							// Provider Copy for other orders
							if(checkedOptions.size()!=1){
								document.newPage();
							}
							SODescriptionPDFTemplateImpl soDescriptionProCopyPDFTemplate = new SODescriptionPDFTemplateImpl();
							soDescriptionProCopyPDFTemplate.setMainPageTitle(SOPDFConstants.PROVIDER_COPY);
							soDescriptionProCopyPDFTemplate.setSo(so);
							soDescriptionProCopyPDFTemplate.setLogoImage(logoImage);

							soDescriptionProCopyPDFTemplate.execute(pdfWriter, document);


							if (addons != null && !addons.isEmpty()) {
								soAddonPDFTemplate.setMainPageTitle(SOPDFConstants.PROVIDER_COPY);
								soAddonPDFTemplate.execute(pdfWriter, document);
							}
						}
					
						nonRiLogoImages.add(logoImage);
					}
					
					

				}
				size= pageNo.size();
				pageno=pdfWriter.getCurrentPageNumber();
				pageNo.add(pageno);
				index++;
				String soId1= so.getSoId();
				String soId2 = list.get(list.size()-1).getSoId();
				
				if(!(soId1.equals(soId2))){
					document.newPage();
					}

			} 

		}catch (Throwable th) {
			logger.error("Unexpected error while generating Service Order PDF", th);
		} finally {
			if (document != null && document.isOpen()) {
				try {
					document.close();
					byte[] byteArray = pdfStream.toByteArray();
					setHeaderAndFooter(byteArray, pdfStream,list,nonRiLogoImages,pageNo,custCopyEnds,checkedOptions);						
				} catch (Throwable th) {
					logger.error("Can't close the PDF document object.", th);
					// No business rule, do nothing
				}
			}
		}
		return pdfStream;
	}

	private String extractCustomRefValueByCustomRefName(List<ServiceOrderCustomRefVO> customRefs, String customRefName) {
		for (ServiceOrderCustomRefVO customRef : customRefs) {
			if (customRef.getRefType().equals(customRefName)) {
				return customRef.getRefValue();
			}
		}
		return null;
	}

	private void scaleLogo(Image logoImage, String processID) {
		try {
			IApplicationPropertiesDao applicationPropertiesDao = (IApplicationPropertiesDao)MPSpringLoaderPlugIn.getCtx().getBean("applicationPropertiesDao");
			String thirdPartyProcessIDs = applicationPropertiesDao.query("ThirdPartyProcessIDs").getAppValue();
			if (StringUtils.isNotBlank(thirdPartyProcessIDs)) {
				List<String> processIdList = Arrays.asList(thirdPartyProcessIDs.split(","));
				if (StringUtils.isNotBlank(processID) && processIdList.contains(processID)) {
					String thirdPartyLogoScalePct = applicationPropertiesDao.query("ThirdPartyLogoScalePct").getAppValue();
					List<String> logoScalePctList = Arrays.asList(thirdPartyLogoScalePct.split(","));
					String logoScalePct = logoScalePctList.get(processIdList.indexOf(processID));
					logoImage.scalePercent(Float.parseFloat(logoScalePct));
				}
			}
		}
		catch (DataNotFoundException e) { } // Unexpected, Ignore
		catch (DataAccessException e) { } // Unexpected, Ignore
	}


	/**
	 * @param byteArray
	 * @param fs
	 * @param list
	 * @param nonRiLogoImages
	 * @param pageNo
	 * @throws IOException
	 * @throws DocumentException
	 */
	private  void setHeaderAndFooter(byte[] byteArray,ByteArrayOutputStream fs,List<ServiceOrder> list,List<Image> nonRiLogoImages, List<Integer> pageNo,List<Integer> custCopyEnds,List<String> docTypes) throws IOException, DocumentException{
		PdfReader reader = new PdfReader(byteArray);
		PdfStamper stamper = new PdfStamper(reader, fs);
		PdfContentByte under = null;
		BaseFont bf1 = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252,false);
		BaseFont bf2= BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252,false);
		int pageno;
		int start;
		int end;
		int custCopyIndex=0;
		Image logoImage; 
		for(int i=0;i<list.size();i++){
			pageno=1;
			ServiceOrder so=list.get(i);
			switch (i) {
			case 0:start=1;
			break;
			default: start=pageNo.get(i-1)+1;
			break;
			}
			switch (i) {
			case 0:end=pageNo.get(i);
			break;
			default: end = pageNo.get(i)-pageNo.get(i-1);
			break;
			}
			if(so.getBuyerId()!=1000){
				for (int page = start; page <= pageNo.get(i); page++) {
					under = stamper.getUnderContent(page);
					String pageXofY = String.format(SOPDFConstants.FOOTER_PAGE+" %d of %d", pageno++,end);
					under = stamper.getOverContent(page);
					under.beginText();
					under.setFontAndSize(bf1, 10);
					under.setTextMatrix(540,10);
					under.showText(pageXofY);
					under.setFontAndSize(bf1,10);
					under.setTextMatrix(250,10);
					under.showText(SOPDFConstants.FOOTER_SO );
					under.setFontAndSize(bf2,10);
					under.setTextMatrix(317,10);
					under.showText(SOPDFConstants.FOOTER_HASH+ so.getSoId());
					if(nonRiLogoImages != null && nonRiLogoImages.get(custCopyIndex) != null){
						logoImage = nonRiLogoImages.get(custCopyIndex);
						logoImage.setAlignment(Element.ALIGN_TOP);
						logoImage.setAbsolutePosition(20,735);
						under.addImage(logoImage);
					}
					under.setFontAndSize(bf1,10);
					under.setTextMatrix(250,776);
					under.showText(SOPDFConstants.FOOTER_SO );
					under.setFontAndSize(bf2,10);
					under.setTextMatrix(317,776);
					under.showText(SOPDFConstants.FOOTER_HASH+ so.getSoId());
					under.setFontAndSize(bf1,10);
					if(docTypes.size()==1){
						under.setTextMatrix(533,776);
						under.showText(docTypes.get(0));
					}
					else{
						if(page<=custCopyEnds.get(custCopyIndex)){
							under.setTextMatrix(533,776);
							under.showText(SOPDFConstants.CUSTOMER_COPY );
						}
						else{
							under.setTextMatrix(540,776);
							under.showText(SOPDFConstants.PROVIDER_COPY);
						}
					}
					
					under.endText();

				}
				custCopyIndex= custCopyIndex+1;
			}
			else{
		 		for (int page = start; page <= pageNo.get(i); page++) {
		 			under = stamper.getUnderContent(page);
		 			String pageXofY = String.format(SOPDFConstants.FOOTER_PAGE+" %d of %d", pageno++,end);
		 			under = stamper.getOverContent(page);
		 			under.beginText();
		 			under.setFontAndSize(bf1,10);
					under.setTextMatrix(212,10);
					under.showText(SOPDFConstants.FOOTER_SO );
					under.setFontAndSize(bf1,10);
					under.setTextMatrix(277,10);
					under.showText(SOPDFConstants.FOOTER_HASH+ so.getSoId());
					under.setFontAndSize(bf1, 10);
					under.setTextMatrix(540,10);
					under.showText(pageXofY);
					under.endText();
		         }
			}
		}
		reader.close();
		stamper.close(); 

	}
	/* (non-Javadoc)
	 * @see com.lowagie.text.pdf.PdfPageEventHelper#onStartPage(com.lowagie.text.pdf.PdfWriter, com.lowagie.text.Document)
	 */


	public void onStartPage(PdfWriter writer, Document document) {
		PdfPTable table =new PdfPTable(4);
		PdfPCell cell = new PdfPCell();
		cell.setColspan(4);
		cell.setBorderWidth(0);
		com.lowagie.text.List list = new com.lowagie.text.List();
		list.setListSymbol(SOPDFConstants.SPACE);
		list.add(new ListItem(SOPDFConstants.BIG_SPACE));
		cell.addElement(list);
		table.addCell(cell);
		try {
			document.add(table);
			document.add(table);
		} 
		catch (DocumentException e) {
			logger.error("Can't add space on start.", e);
		}
	}


	/**
	 * @param pdfWriter
	 * @param document
	 */
	public void onEndCopy(PdfWriter pdfWriter, Document document) {
		// TODO Auto-generated method stub
		PdfPTable table =new PdfPTable(4);
		PdfPCell cell = new PdfPCell();
		cell.setColspan(4);
		cell.setBorderWidth(0);
		com.lowagie.text.List simpleList = new com.lowagie.text.List();
		simpleList.setListSymbol(SOPDFConstants.SPACE);
		simpleList.add(new ListItem(SOPDFConstants.BIG_SPACE));
		cell.addElement(simpleList);
		table.addCell(cell);
		if(pdfWriter.fitsPage(table)){
			document.newPage();
		}
	}

}
