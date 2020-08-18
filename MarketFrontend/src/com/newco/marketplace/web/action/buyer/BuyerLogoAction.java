package com.newco.marketplace.web.action.buyer;

import java.awt.Image;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.swing.ImageIcon;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.business.businessImpl.document.LogoDocumentBO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.util.DocumentUtils;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.security.NonSecurePage;
import com.opensymphony.xwork2.Preparable;

/**
 * 
 *
 */
@NonSecurePage
public class BuyerLogoAction extends SLBaseAction implements Preparable{

	private static final long serialVersionUID = 20090408L;
	private LogoDocumentBO logoDocumentBO;

	/**
	 * 
	 */
	public void prepare() throws Exception {
		// do nothing
	}
	
	/**
	 * 
	 */
	public BuyerLogoAction() {
		super();
	}

	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String getBuyerLogo() throws Exception {
		Integer buyerId =  parseInt(ServletActionContext.getRequest().getParameter("buyer_id"));
		Integer maxWidth = parseInt(ServletActionContext.getRequest().getParameter("max_width"));
		Integer maxHeight = parseInt(ServletActionContext.getRequest().getParameter("max_height"));

		if( buyerId == null) {
			buyerId = Integer.valueOf(-1);
		}

		DocumentVO selectedImage = logoDocumentBO.getLogoForBuyer(buyerId);
		selectedImage.setBlobBytes(resize(selectedImage.getBlobBytes(), maxWidth, maxHeight));

		handleStreams(selectedImage.getTitle(), selectedImage.getFormat(), selectedImage.getBlobBytes());
		return NONE;
	}

	private Integer parseInt(String value) {
		if(StringUtils.isEmpty(value)) {
			return null;
		} 
		return Integer.valueOf(value);
	}

	private void handleStreams(String fileName, String format, byte[] imageBytes) throws IOException {
		ServletActionContext.getResponse().setContentType(format);
		ServletActionContext.getResponse().setHeader("Content-Disposition", "attachment;filename=\""+ fileName + "\"");
		ServletOutputStream outs = ServletActionContext.getResponse().getOutputStream();
		outs.write(imageBytes);
		outs.flush();
		outs.close();
	}

	private byte[] resize(byte[] imageBytes, Integer pMaxWidth, Integer pMaxHeight) {
		if(pMaxWidth == null || pMaxHeight == null) {
			return imageBytes;
		}

		int maxWidth = pMaxWidth.intValue();
		int maxHeight = pMaxHeight.intValue();

		Image sourceImage = new ImageIcon(imageBytes).getImage();

		int actualWidth = sourceImage.getWidth(null);
		int actualHeight = sourceImage.getHeight(null);

		if (actualWidth < maxWidth && actualWidth > 0) {
			maxWidth = actualWidth;
		}

		if (actualHeight < maxHeight && actualHeight > 0) {
			maxHeight = actualHeight;
		}

		return DocumentUtils.resizeoImage(imageBytes, maxWidth, maxHeight);
	}

	public LogoDocumentBO getLogoDocumentBO() {
		return logoDocumentBO;
	}

	public void setLogoDocumentBO(LogoDocumentBO logoDocumentBO) {
		this.logoDocumentBO = logoDocumentBO;
	}



}
