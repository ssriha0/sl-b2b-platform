package com.newco.marketplace.web.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.newco.marketplace.web.dto.SODocumentDTO;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.2 $ $Author: akashya $ $Date: 2008/05/21 23:33:04 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class ImageViewServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3720794756798043187L;

	protected void processRequest(HttpServletRequest req,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = req.getSession();
		String imageDocId = req.getParameter("imageDocId");
		
		if(!com.newco.marketplace.web.utils.SLStringUtils.isNullOrEmpty(imageDocId) ){
		
			List<SODocumentDTO> imageList = (List<SODocumentDTO>)session.getAttribute("photoDocList");
			SODocumentDTO selectedImage = null; 
			
			for(SODocumentDTO image : imageList){
				if(image.getDocumentId()!= null && image.getDocumentId().toString().equals(imageDocId)){
					selectedImage = image;
				}
			}
		
			try{
				
/*				if ( StringUtils.equals(selectedImage.getFormat(),"image/bmp") ||
					StringUtils.equals(selectedImage.getFormat(),"image/gif") ||
					StringUtils.equals(selectedImage.getFormat(),"image/jpeg") ||
					StringUtils.equals(selectedImage.getFormat(),"image/jpg") ) {
					String contentType = selectedImage.getFormat();
					response.setContentType(contentType);
					//response.setContentType("text/html"+"/force-download");
				} else {
					response.setContentType("text/html");
				}*/
				
				
				response.setContentLength(selectedImage.getBlobBytes().length);
				//response.setContentType(docVO.getMimeType());
				response.setHeader("Content-disposition",
						"inline; filename=\"" + selectedImage.getName() + "\"");
/*				
				response.setContentType("image/jpg");
				 
				response.setHeader ("Pragma", "public");
				response.setHeader("Cache-control", "must-revalidate");*/

				OutputStream out = response.getOutputStream();

				out.write(selectedImage.getBlobBytes());
				out.flush();
				out.close();
				
						
/*				String header = "attachment;filename=\""
						+ selectedImage.getName() + "\"";
				String header = "filename=\""
					+ selectedImage.getName() + "\"";
				
				response.setHeader("Content-Disposition", header);
				InputStream in = new ByteArrayInputStream(selectedImage.getBlobBytes());
				ServletOutputStream outs = response.getOutputStream();
				outs.write(selectedImage.getBlobBytes());
				int bit = 256;
				while ((bit) >= 0) {
					bit = in.read();
					outs.write(bit);
				}
				outs.flush();
				outs.close();
				in.close();*/
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		
		}

	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}

}

