package com.newco.marketplace.business.businessImpl.so.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
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
import com.newco.marketplace.api.mobile.beans.sodetails.AddonPayment;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Signature;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.exception.core.DataNotFoundException;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao;
import com.newco.marketplace.util.DocumentUtils;
import com.newco.marketplace.util.constants.SOPDFConstants;
import com.newco.marketplace.utils.DateUtils;

public class PDFGenerator extends PdfPageEventHelper {
	private static final Logger logger = Logger.getLogger(PDFGenerator.class);
	
	public ByteArrayOutputStream getPDF(ServiceOrder so, List<DocumentVO> docList, DocumentVO logoDocumentVO, List<Signature> signatureList,
			AddonPayment addOnPaymentDetails, boolean mobileIndicator, boolean customerCopyOnlyInd) {
		logger.info("INSIDE PDFGenerator ::: While Creating Tables for PDF");
		ByteArrayOutputStream pdfStream = null;
		Document document = null;
		int pageNo =0;
		Image logoImage = null;
		boolean omFlag = false;

		try {
			// change for mobile provider API ---------------- START
			
			/*
			 * Signature to be split from the list for Customer and provider 
			 */
			Signature customerSignature = null;
			Signature providerSignature = null;
			// String currentDate = DateUtils.getFormatedDate(new Date(), "yyyy-MM-dd");
			String currentDate = DateUtils.getFormatedDate(new Date(), "MM/dd/yyyy");
			Image customerSignatureImage = null;
			Image providerSignatureImage = null;
			
			// only if signatures are available
			if(null != signatureList && !signatureList.isEmpty()){
				for(Signature signature : signatureList){
					if(MPConstants.CUSTOMER.equals(signature.getResourceInd())){
						customerSignature = signature;
						customerSignature.setCurrentDate(currentDate);
					}
					else if(MPConstants.PROVIDER.equals(signature.getResourceInd())){
						providerSignature = signature;
						providerSignature.setCurrentDate(currentDate);
					}
				}
			}
			
			// scale the signature images
			if (customerSignature != null && !StringUtils.isBlank(customerSignature.getDocPath())) {
				try {
					//ClassPathResource classRes = new ClassPathResource(customerSignature.getDocPath());
					//File file = classRes.getFile();
					File file = new File(customerSignature.getDocPath());
					byte[] customerImageByte = FileUtils.readFileToByteArray(file);

					if (DocumentUtils.IsResizable(
							customerImageByte,
							SOPDFConstants.DEFAULT_SIGNATURE_WIDTH,
							SOPDFConstants.DEFAULT_SIGNATURE_HEIGHT)) 
					{
						byte[] resizedCustomerImage = DocumentUtils.scaleImage(
								customerImageByte,
								SOPDFConstants.DEFAULT_SIGNATURE_WIDTH,
								SOPDFConstants.DEFAULT_SIGNATURE_HEIGHT);
						customerSignatureImage = Image.getInstance(resizedCustomerImage);
					} else {
						customerSignatureImage = Image.getInstance(customerImageByte);
					}
				} catch (Exception ioe) {
					logger.error("Unable to parse customer signature image.", ioe);
				}
			}
			
			if (providerSignature != null && !StringUtils.isBlank(providerSignature.getDocPath())) {
				try {
					//ClassPathResource classRes = new ClassPathResource(providerSignature.getDocPath());
					//File file = classRes.getFile();
					File file = new File(providerSignature.getDocPath());
					byte[] providerImageByte = FileUtils.readFileToByteArray(file);

					if (DocumentUtils.IsResizable(
							providerImageByte,
							SOPDFConstants.DEFAULT_SIGNATURE_WIDTH,
							SOPDFConstants.DEFAULT_SIGNATURE_HEIGHT)) 
					{
						byte[] resizedProviderImage = DocumentUtils.scaleImage(
								providerImageByte,
								SOPDFConstants.DEFAULT_SIGNATURE_WIDTH,
								SOPDFConstants.DEFAULT_SIGNATURE_HEIGHT);
						providerSignatureImage = Image.getInstance(resizedProviderImage);
					} else {
						providerSignatureImage = Image.getInstance(providerImageByte);
					}
				} catch (Exception ioe) {
					logger.error("Unable to parse provider signature image.", ioe);
				}
			}
			
			// change for mobile provider API ---------------- END

			if (logoDocumentVO != null && logoDocumentVO.getBlobBytes() != null) try {
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
			document = new Document(PageSize.LETTER, 20, 20, 20, 20);
			pdfStream = new ByteArrayOutputStream();
			PdfWriter pdfWriter = PdfWriter.getInstance(document, pdfStream);
			// Open the document/stream and start adding data tables to it
			
			// SLM-117 : Use the existing generation of service order PDF
			if(!customerCopyOnlyInd){
				//pdf for RI buyers
				// Customer Copy for RI orders
				if(so.getBuyerId()==1000){
					document.open(); 
					// SPM-1340 : Resolution Comments to be appended in PDF
					// Calling the constructor to set mobileIndicator
					// This indicator is to restrict the addition of resolution comments in pdf only to MobileSoPdfGeneration batch
					RISODescriptionPDFTemplateImpl soDescriptionCustCopyRIPDFTemplate = new RISODescriptionPDFTemplateImpl(mobileIndicator);
					soDescriptionCustCopyRIPDFTemplate.setMainPageTitle(SOPDFConstants.CUSTOMER_COPY);
					soDescriptionCustCopyRIPDFTemplate.setSo(so);
					soDescriptionCustCopyRIPDFTemplate.setLogoImage(logoImage);
					
					soDescriptionCustCopyRIPDFTemplate.setCustomerSignature(customerSignature);
					soDescriptionCustCopyRIPDFTemplate.setCustomerSignatureImage(customerSignatureImage);
					soDescriptionCustCopyRIPDFTemplate.setProviderSignature(providerSignature);
					soDescriptionCustCopyRIPDFTemplate.setProviderSignatureImage(providerSignatureImage);
					
					soDescriptionCustCopyRIPDFTemplate.execute(pdfWriter, document,omFlag);
					
					SOAddonPDFTemplateImpl soAddonPDFTemplate = null;
					List<ServiceOrderAddonVO> addons = so.getUpsellInfo();
					if (addons != null && !addons.isEmpty()) {
						soAddonPDFTemplate = new SOAddonPDFTemplateImpl();
						soAddonPDFTemplate.setLogoImage(logoImage);
						soAddonPDFTemplate.setSoId(so.getSoId());
						soAddonPDFTemplate.setSo(so);
						soAddonPDFTemplate.setMainPageTitle(SOPDFConstants.CUSTOMER_COPY);
						soAddonPDFTemplate.setSoAddons(addons);
						soAddonPDFTemplate.setProcessID(extractCustomRefValueByCustomRefName(so.getCustomRefs(), "ProcessID"));
						if(null != addOnPaymentDetails){
							soAddonPDFTemplate.setAddOnPaymentDetails(addOnPaymentDetails);
							soAddonPDFTemplate.setCustomerSignatureImage(customerSignatureImage);
							soAddonPDFTemplate.setCurrentDate(currentDate);
						}
						soAddonPDFTemplate.execute(pdfWriter, document);
					}
					
					// Provider Copy for RI orders
					// SPM-1340 : Resolution Comments to be appended in PDF
					// Calling the constructor to set mobileIndicator
					
					// SLM-69 : Provider copy not needed for customer email from mobile so pdf generation batch. 
						RISODescriptionPDFTemplateImpl soDescriptionProCopyRIPDFTemplate = new RISODescriptionPDFTemplateImpl(mobileIndicator);
						soDescriptionProCopyRIPDFTemplate.setMainPageTitle(SOPDFConstants.PROVIDER_COPY);
						soDescriptionProCopyRIPDFTemplate.setSo(so);
						soDescriptionProCopyRIPDFTemplate.setLogoImage(logoImage);
						
						soDescriptionProCopyRIPDFTemplate.setCustomerSignature(customerSignature);
						soDescriptionProCopyRIPDFTemplate.setCustomerSignatureImage(customerSignatureImage);
						soDescriptionProCopyRIPDFTemplate.setProviderSignature(providerSignature);
						soDescriptionProCopyRIPDFTemplate.setProviderSignatureImage(providerSignatureImage);
						
						soDescriptionProCopyRIPDFTemplate.execute(pdfWriter, document,omFlag);
						
						if (addons != null && !addons.isEmpty()) {
							soAddonPDFTemplate.setMainPageTitle(SOPDFConstants.PROVIDER_COPY);
							soAddonPDFTemplate.execute(pdfWriter, document);
						}
						
						// Provider Instructions for RI orders
						RIServiceProInstrPDFTemplateImpl  serviceProInstRIPDFTemplate = new RIServiceProInstrPDFTemplateImpl();
						serviceProInstRIPDFTemplate.setLogoImage(logoImage);
						serviceProInstRIPDFTemplate.setSo(so);
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
				
				
				else{
					// Customer Copy for other orders
					
				pdfWriter.setPageEvent(new PDFGenerator());
				document.open(); 

				// SPM-1340 : Resolution Comments to be appended in PDF
				// Calling the constructor to set mobileIndicator
				SODescriptionPDFTemplateImpl soDescriptionCustCopyPDFTemplate = new SODescriptionPDFTemplateImpl(mobileIndicator);
				soDescriptionCustCopyPDFTemplate.setMainPageTitle(SOPDFConstants.CUSTOMER_COPY);
				soDescriptionCustCopyPDFTemplate.setSo(so);
				soDescriptionCustCopyPDFTemplate.setLogoImage(logoImage);
				
				soDescriptionCustCopyPDFTemplate.setCustomerSignature(customerSignature);
				soDescriptionCustCopyPDFTemplate.setCustomerSignatureImage(customerSignatureImage);
				soDescriptionCustCopyPDFTemplate.setProviderSignature(providerSignature);
				soDescriptionCustCopyPDFTemplate.setProviderSignatureImage(providerSignatureImage);
				
				soDescriptionCustCopyPDFTemplate.execute(pdfWriter, document);
				
				SOAddonPDFTemplateImpl soAddonPDFTemplate = null;
				List<ServiceOrderAddonVO> addons = so.getUpsellInfo();
				if (addons != null && !addons.isEmpty()) {
					soAddonPDFTemplate = new SOAddonPDFTemplateImpl();
					soAddonPDFTemplate.setLogoImage(logoImage);
					soAddonPDFTemplate.setSoId(so.getSoId());
					soAddonPDFTemplate.setSo(so);
					soAddonPDFTemplate.setMainPageTitle(SOPDFConstants.CUSTOMER_COPY);
					soAddonPDFTemplate.setSoAddons(addons);
					soAddonPDFTemplate.setProcessID(extractCustomRefValueByCustomRefName(so.getCustomRefs(), "ProcessID"));
					if(null != addOnPaymentDetails){
						soAddonPDFTemplate.setAddOnPaymentDetails(addOnPaymentDetails);
						soAddonPDFTemplate.setCustomerSignatureImage(customerSignatureImage);
						soAddonPDFTemplate.setCurrentDate(currentDate);
					}
					soAddonPDFTemplate.execute(pdfWriter, document);
				}
				
				 pageNo = pdfWriter.getCurrentPageNumber();
				
				// Provider Copy for other orders
				// SPM-1340 : Resolution Comments to be appended in PDF
				// Calling the constructor to set mobileIndicator
				 
					SODescriptionPDFTemplateImpl soDescriptionProCopyPDFTemplate = new SODescriptionPDFTemplateImpl(mobileIndicator);
					soDescriptionProCopyPDFTemplate.setMainPageTitle(SOPDFConstants.PROVIDER_COPY);
					soDescriptionProCopyPDFTemplate.setSo(so);
					soDescriptionProCopyPDFTemplate.setLogoImage(logoImage);
					
					soDescriptionProCopyPDFTemplate.setCustomerSignature(customerSignature);
					soDescriptionProCopyPDFTemplate.setCustomerSignatureImage(customerSignatureImage);
					soDescriptionProCopyPDFTemplate.setProviderSignature(providerSignature);
					soDescriptionProCopyPDFTemplate.setProviderSignatureImage(providerSignatureImage);
					
					document.newPage();

					soDescriptionProCopyPDFTemplate.execute(pdfWriter, document);
				
					
					if (addons != null && !addons.isEmpty()) {
						soAddonPDFTemplate.setMainPageTitle(SOPDFConstants.PROVIDER_COPY);
						soAddonPDFTemplate.setSo(so);
						soAddonPDFTemplate.execute(pdfWriter, document);
					}
					
					//this code has been removed as part of SL-17429
					// Provider Instructions
//					ServiceProInstrPDFTemplateImpl  serviceProInstPDFTemplate = new ServiceProInstrPDFTemplateImpl();
//					serviceProInstPDFTemplate.setLogoImage(logoImage);
//					serviceProInstPDFTemplate.setSo(so);
//					serviceProInstPDFTemplate.setDocList(docList);
//					String buyerTermsCond = so.getBuyerTermsCond();
//					//In case of simple buyer this value comes through as null and gives an exception in the log
//					//Temporary fix to stop it from filling up the logs, is to initialise this as an empty string.
//					if (buyerTermsCond == null) {
//						buyerTermsCond = StringUtils.EMPTY;
//					}
//					serviceProInstPDFTemplate.setTermsCond(buyerTermsCond);
//					serviceProInstPDFTemplate.execute(pdfWriter, document);
			}
			
				
			
			} 
			else{
				// Code for Digital reciept generation
				SODigitalRecieptPDFTemplateImpl digitalRecieptPDFTemplateImpl = new SODigitalRecieptPDFTemplateImpl(mobileIndicator);
				digitalRecieptPDFTemplateImpl.setSo(so);
				digitalRecieptPDFTemplateImpl.setLogoImage(logoImage);
				digitalRecieptPDFTemplateImpl.setCustomerSignature(customerSignature);
				digitalRecieptPDFTemplateImpl.setCustomerSignatureImage(customerSignatureImage);
				digitalRecieptPDFTemplateImpl.setProviderSignature(providerSignature);
				digitalRecieptPDFTemplateImpl.setProviderSignatureImage(providerSignatureImage);
				document.open(); 
				digitalRecieptPDFTemplateImpl.execute(pdfWriter, document);

			}
			
		}catch (Throwable th) {
			logger.error("Unexpected error while generating Service Order PDF", th);
		} finally {
			if (document != null && document.isOpen()) {
				try {
					document.close();
					if(!customerCopyOnlyInd){
						if(so.getBuyerId() != 1000){
							byte[] byteArray = pdfStream.toByteArray();
							setHeaderAndFooter(byteArray, pdfStream,so,logoImage,pageNo);
						}
						//Added to add page no in RI order as footer
						else{
							byte[] byteArray = pdfStream.toByteArray();
							setFooterForRI(byteArray, pdfStream,so);
						}
					}
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
	 * @param so
	 * @param logoImage
	 * @param pageNo
	 * @throws IOException
	 * @throws DocumentException
	 */
	private  void setHeaderAndFooter(byte[] byteArray,ByteArrayOutputStream fs,ServiceOrder so,Image logoImage, int pageNo) throws IOException, DocumentException{
		PdfReader reader = new PdfReader(byteArray);
		PdfStamper stamper = new PdfStamper(reader, fs);
		PdfContentByte under = null;
		int totalPages = reader.getNumberOfPages();
		BaseFont bf1 = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252,false);
		BaseFont bf2= BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252,false);

		for (int page = 1; page <= totalPages; page++) {
			under = stamper.getUnderContent(page);
			String pageXofY = String.format(SOPDFConstants.FOOTER_PAGE+" %d of %d", page,totalPages);
			under = stamper.getOverContent(page);
			logoImage.setAlignment(Element.ALIGN_TOP);
			logoImage.setAbsolutePosition(20,735);
			under.addImage(logoImage);
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
			under.setFontAndSize(bf1,10);
			under.setTextMatrix(250,776);
			under.showText(SOPDFConstants.FOOTER_SO );
			under.setFontAndSize(bf2,10);
			under.setTextMatrix(317,776);
			under.showText(SOPDFConstants.FOOTER_HASH+ so.getSoId());
			under.setFontAndSize(bf1,10);
			
			if(page<=pageNo){
				under.setTextMatrix(533,776);
				under.showText(SOPDFConstants.CUSTOMER_COPY );
			}
			else{
				under.setTextMatrix(540,776);
				under.showText(SOPDFConstants.PROVIDER_COPY);
			}
			under.endText();

		}
		reader.close();
		stamper.close(); 
	}
     private void setFooterForRI(byte[] byteArray,ByteArrayOutputStream pdfStream, ServiceOrder so) throws IOException, DocumentException {
    	 PdfReader reader = new PdfReader(byteArray);
 		 PdfStamper stamper = new PdfStamper(reader, pdfStream);
 		 PdfContentByte under = null;
// 		 int x=212;
// 		 int y=10;
// 		 int p=277;
// 		 int q=540;
 		 int totalPages = reader.getNumberOfPages();
 		 BaseFont bf1 = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252,false);
 		 BaseFont bf2= BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252,false);

 		for (int page = 1; page <= totalPages; page++) {
 			under = stamper.getUnderContent(page);
 			String pageXofY = String.format(SOPDFConstants.FOOTER_PAGE+" %d of %d", page,totalPages);
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


}
