package com.newco.marketplace.web.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.utils.SecurityChecker;
import org.owasp.esapi.ESAPI;
/*
 * Maintenance History: See bottom of file
 */
public class BuyerDocumentViewServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8191558418254635701L;

	protected void processRequest(HttpServletRequest req,
			HttpServletResponse response) throws ServletException, IOException {

		IDocumentBO bo = (IDocumentBO) MPSpringLoaderPlugIn.getCtx()
				.getBean(Constants.ApplicationContextBeans.DOCUMENT_BO_BEAN);

		HttpSession session = req.getSession();

		String docId = req.getParameter("documentSelection");
		if(docId == null || docId.length() == 0)
		{
			OutputStream out = response.getOutputStream();
			out.flush();
			out.close();
			return;
		}
		//String soId = (String) session.getAttribute(OrderConstants.SO_ID);
		//if (StringUtils.isEmpty(soId)) {
		//	soId = req.getParameter("soId");
		//}

		SecurityContext securityContext = (SecurityContext) session
				.getAttribute(SOConstants.SECURITY_KEY);
		
		if (null != securityContext) {
			//LoginCredentialVO lvo = securityContext.getRoles();
	
			//if (null != lvo) {
			Integer roleId = securityContext.getRoleId();
			Integer buyerId = securityContext.getCompanyId();
			
				try {
					Integer userDocId = bo.isDocExistsForUser(Integer.decode(docId),buyerId);
					byte[] mybytes = null;
					OutputStream out = response.getOutputStream();
					
					if(null!=userDocId){
						DocumentVO docVO = bo.retrieveBuyerDocumentByDocumentId(Integer.decode(docId));
						
						if (null != docVO && null != docVO.getBlobBytes()) {
							mybytes = docVO.getBlobBytes();
			
							response.setContentLength(mybytes.length);
							
							SecurityChecker sc=new SecurityChecker();
							String checked1 = sc.securityCheck(docVO.getMimeType());
							
							response.setContentType(checked1);
							
							String checked2= sc.fileNameCheck(docVO.getFileName());
							String echoString = ESAPI.encoder().canonicalize(checked2);
							String checked2Var = ESAPI.encoder().encodeForHTML(echoString);

							response.setHeader("Content-disposition",
									"attachment; filename=\"" + checked2Var + "\"");
						}
					}else{
						String errorMsg = "Requested document cannot be found on your account";
						mybytes = errorMsg.getBytes();
					}					
					out.write(mybytes);
					out.flush();
					out.close();
					
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (BusinessServiceException e) {
					e.printStackTrace();
				}
			//}
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
 * $Log: BuyerDocumentViewServlet.java,v $
 * Revision 1.4  2008/05/02 21:23:24  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.3  2008/04/26 01:13:51  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.1.32.1  2008/05/01 21:20:59  cgarc03
 * processRequest() handle null documentId parameter.
 *
 * Revision 1.1.14.1  2008/04/23 11:41:41  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.2  2008/04/23 05:19:53  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.1  2008/02/08 23:37:27  pbhinga
 * Checked for Iteration 17 functionality - Document Manager. Reviewed by Gordon.
 *
 *
 */