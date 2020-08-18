package com.newco.marketplace.web.action.widgets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.owasp.esapi.ESAPI;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.web.delegates.ILookupDelegate;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;

public class AjaxCredentialCategoryActionHelper {

	private static final Logger logger = Logger.getLogger(AjaxCredentialCategoryActionHelper.class);

	public String credentialCategory(ServiceOrdersCriteria serviceOrdersCriteria, ILookupDelegate lookupDelegate, HttpServletRequest request, HttpServletResponse response) {

		Integer credentialType = Integer.parseInt(request.getParameter("credentialId"));
		request.getSession().setAttribute(ProviderConstants.SELECTED_CREDENTIAL_ID, credentialType);
		int roleId = serviceOrdersCriteria.getRoleId();
		Integer buyerId = serviceOrdersCriteria.getCompanyId();
		Map<String, Integer> hm = new HashMap<String, Integer>();
		hm.put("credentialType", credentialType);
		hm.put("buyerId", buyerId);

		ArrayList<LookupVO> credentialCategory = null;
		try {
			if (roleId == OrderConstants.NEWCO_ADMIN_ROLEID) {
				credentialCategory = lookupDelegate.getCredentialCategory(credentialType);
			} else {
				credentialCategory = lookupDelegate.getCredCategoryForBuyer(hm);
			}
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		request.getSession().setAttribute(ProviderConstants.CREDENTIAL_CATEGORY_LIST, credentialCategory);

		LookupVO lookupVo = null;
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		StringBuffer xmlString = new StringBuffer("<message_output>"); // ------------Start XML String
		Integer id = 0;
		String desc = "";
		String idVal = "";
		for (int i = 0; i < credentialCategory.size(); i++) {
			lookupVo = credentialCategory.get(i);
			id = lookupVo.getId();
			if(id!=null){
			idVal = ESAPI.encoder().encodeForXML(id.toString());
			}
			desc = ESAPI.encoder().encodeForXML(lookupVo.getDescr());
			xmlString.append("<credCategory>");
			xmlString.append("<categoryId>" + idVal + "</categoryId>");
			xmlString.append("<categoryName>" + desc + "</categoryName>");
			xmlString.append("</credCategory>");
		}
		xmlString.append("</message_output>");// ------------End XML String
		try {
			response.getWriter().write(xmlString.toString());
		} catch (IOException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return null;
	}
}
