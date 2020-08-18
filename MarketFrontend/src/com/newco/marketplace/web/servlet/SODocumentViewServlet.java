package com.newco.marketplace.web.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.utils.SecurityChecker;
import org.owasp.esapi.ESAPI;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.7 $ $Author: akashya $ $Date: 2008/05/21 23:33:05 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class SODocumentViewServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3720794756798043187L;

	protected void processRequest(HttpServletRequest req,
			HttpServletResponse response) throws ServletException, IOException {

		IDocumentBO bo = (IDocumentBO) MPSpringLoaderPlugIn.getCtx()
				.getBean(Constants.ApplicationContextBeans.DOCUMENT_BO_BEAN);

		HttpSession session = req.getSession();
		SecurityChecker sc = new SecurityChecker();
		String docId = req.getParameter("documentSelection");
		//Sl-19820
		//String soId = (String) session.getAttribute(OrderConstants.SO_ID);
		String soId =req.getParameter("serviceId") ;
		String buyerGuid =(String)session.getAttribute(Constants.SESSION.SIMPLE_BUYER_GUID);
		if (StringUtils.isEmpty(soId)) {
			soId = req.getParameter("soId");
		}

		SecurityContext securityContext = (SecurityContext) session
				.getAttribute(SOConstants.SECURITY_KEY);
		
		if (null != securityContext && !com.newco.marketplace.web.utils.SLStringUtils.isNullOrEmpty(soId) ) {
			LoginCredentialVO lvo = securityContext.getRoles();
	
			if (null != lvo) {
	
				try {
					DocumentVO docVO = bo.retrieveServiceOrderDocumentByDocumentId(
							Integer.decode(docId), soId, lvo.getRoleId(), securityContext
							.getCompanyId(),securityContext.getRoleId());
	
					if (null != docVO && null != docVO.getBlobBytes()) {
						byte[] mybytes = docVO.getBlobBytes();
		
						response.setContentLength(mybytes.length);
						
						String mime = sc.securityCheck(docVO.getMimeType());
						response.setContentType(mime);
						String docName = sc.fileNameCheck(docVO.getFileName());
						response.setHeader("Content-disposition",
								"attachment; filename=\"" + docName + "\"");
		
						OutputStream out = response.getOutputStream();
		
						out.write(mybytes);
						out.flush();
						out.close();
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (BusinessServiceException e) {
					e.printStackTrace();
				}
			}
		}else{  // temp Doc service Order not yet saved 
			
			try {
				DocumentVO docVO = bo.retrieveTemporarySimpleBuyerSODocumentByDocumentId(
						Integer.decode(docId), buyerGuid);

				if (null != docVO && null != docVO.getBlobBytes()) {
					byte[] mybytes = docVO.getBlobBytes();
	
					response.setContentLength(mybytes.length);
					String mime = sc.securityCheck(docVO.getMimeType());
					response.setContentType(mime);
					String docName = sc.fileNameCheck(docVO.getFileName());
					String echoString = ESAPI.encoder().canonicalize(docName);
					String docNameVal = ESAPI.encoder().encodeForHTML(echoString);

					String header = "attachment; filename=\"" + docNameVal + "\"";
					
					response.setHeader("Content-disposition",
							header);
	
					OutputStream out = response.getOutputStream();
	
					out.write(mybytes);
					out.flush();
					out.close();
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (BusinessServiceException e) {
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
/*
 * Maintenance History
 * $Log: SODocumentViewServlet.java,v $
 * Revision 1.7  2008/05/21 23:33:05  akashya
 * I21 Merged
 *
 * Revision 1.6.6.1  2008/05/17 16:19:27  rgurra0
 * fixed View Doc for temp buyerId
 *
 * Revision 1.6  2008/04/26 01:13:51  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.4.12.1  2008/04/23 11:41:41  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.5  2008/04/23 05:19:53  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.4  2008/02/14 23:44:55  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.3.8.1  2008/02/08 02:34:21  spate05
 * serializing for session replication or updating serialuid
 *
 * Revision 1.3  2008/01/04 19:45:43  mhaye05
 * added null check
 *
 * Revision 1.2  2008/01/04 16:07:04  mhaye05
 * fixed bug where we were not sending company id for buyer authorization check
 *
 * Revision 1.1  2008/01/03 20:33:23  mhaye05
 * Initial Check In
 *
 */