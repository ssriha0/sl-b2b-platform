package com.newco.marketplace.web.action.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;

import org.apache.log4j.Logger;

import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.dto.SOWBrandingInfoDTO;

public class DisplayBuyerDocumentAction extends SLBaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6971425389155656264L;
	
	private static final Logger logger = Logger.getLogger("DisplayBuyerDocumentAction");

	private ISOWizardFetchDelegate fetchDelegate;
	
	public DisplayBuyerDocumentAction(ISOWizardFetchDelegate fetchDelegate) {
		this.fetchDelegate = fetchDelegate;
		setFetchDelegate(fetchDelegate);
	}
	
	public String displayBuyerDocument() throws Exception {
		
		String documentId = getParameter("docId");
		logger.info("Request to display document id "+documentId+" of buyer");
		try{
			SOWBrandingInfoDTO brandingInfoDTO = fetchDelegate.retrieveBuyerDocumentByDocumentId(
					new Integer(documentId));
			StringTokenizer fileName = new StringTokenizer(brandingInfoDTO
					.getLogoId(), ".");
			String extension = "";
			while (fileName.hasMoreTokens()) {
				extension = fileName.nextToken();
			}
			if (extension.equals("gif")) {
				getResponse().setContentType("image/gif");
			}else if(extension.equals("jpeg")|| extension.equals("jpg")){
				getResponse().setContentType("image/jpeg");
			}else if(extension.equals("png")){
				getResponse().setContentType("image/png");
			}else {
				getResponse().setContentType("text/html");
			}
			String header = "attachment;filename=\""
					+ brandingInfoDTO.getLogoId() + "\"";
			getResponse().setHeader("Content-Disposition", header);
			InputStream in = new ByteArrayInputStream(brandingInfoDTO
					.getBlobBytes());
			ServletOutputStream outs = getResponse().getOutputStream();
			int bit = 256;
			while ((bit) >= 0) {
				bit = in.read();
				outs.write(bit);
			}
			outs.flush();
			outs.close();
			in.close();
		} catch (Exception e) {
			logger.error("Error in DisplayBuyerDocumentAction --> displayBuyerDocument()");
		}
		return SUCCESS;
	}

	public ISOWizardFetchDelegate getFetchDelegate() {
		return fetchDelegate;
	}

	public void setFetchDelegate(ISOWizardFetchDelegate fetchDelegate) {
		this.fetchDelegate = fetchDelegate;
	}
}
