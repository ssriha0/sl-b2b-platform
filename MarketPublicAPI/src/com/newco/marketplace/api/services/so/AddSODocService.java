/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 24-Sep-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.addSODoc.AddSODocBean;
import com.newco.marketplace.api.beans.so.addSODoc.AddSODocRequest;
import com.newco.marketplace.api.beans.so.addSODoc.AddSODocResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.SOAddDocMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

/**
 * This class is a service class for inserting the Documents to a SO.
 * 
 * @author Infosys
 * @version 1.0
 */
public class AddSODocService extends BaseService {
	private Logger logger = Logger.getLogger(AddSODocService.class);
	
	private IDocumentBO documentBO;	
	private SOAddDocMapper soAddDocMapper;
	

	/**
	 * Constructor
	 */

	public AddSODocService() {
		super(PublicAPIConstant.DOCUMENT_XSD, PublicAPIConstant.SORESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,
				AddSODocRequest.class, AddSODocResponse.class);
	}

	/**
	 * This method is for adding SO Docs.
	 * 
	 * @param apiVO
	 *            APIRequestVO
	 * @return AddSODocResponse
	 */
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute() method of AddSODocService");
		List<String> invalidDocumentList = new ArrayList<String>();
		AddSODocResponse addSODocResponse = null;

		try {
			AddSODocRequest addSODocRequest = (AddSODocRequest) apiVO
					.getRequestFromPostPut();
			int buyerId = apiVO.getBuyerIdInteger();
			String soId = apiVO.getSOId();
			SecurityContext securityContext = super.
							getSecurityContextForBuyerAdmin(buyerId);
			
				List<AddSODocBean> docList = addSODocRequest
						.getServiceorderdoc();
				
				for (AddSODocBean doc : docList) {
					DocumentVO documentVO ;
					if(doc.isVideo()){
						documentVO = new DocumentVO();
						documentVO.setFileName(doc.getFileName());
						documentVO.setDocPath(doc.getFileName());
						documentVO.setDocCategoryId(Constants.DocumentTypes.CATEGORY.VIDEO);
						documentVO.setDescription(doc.getDescription());
						documentVO.setTitle(doc.getDescription());
						documentVO.setFormat("video");
						documentVO.setRoleId(securityContext.getRoleId());
						documentVO.setDelInd(0);
						documentVO.setDocSize(Long.parseLong("0"));
					}else{
					 documentVO = documentBO
							.retrieveDocumentByFileNameAndEntityID(
									Constants.DocumentTypes.BUYER, doc.getFileName(),
									securityContext.getCompanyId());
					}
					if (null != documentVO ) {
						if(Constants.DocumentTypes.CATEGORY.VIDEO != documentVO.getDocCategoryId().intValue()){
							documentVO = documentBO
							.retrieveDocumentByTitleAndEntityID(
									Constants.DocumentTypes.BUYER,
									documentVO.getTitle(), securityContext
											.getCompanyId());
						}
						
						
						if (null != documentVO) {
							documentVO.setDocumentId(null);
							documentVO.setSoId(soId);
							documentVO.setCompanyId(securityContext
									.getCompanyId());
							logger.info("Going to associate "
									+ "document with ServiceOrder");
							ProcessResponse insertDocResponse = documentBO
									.insertServiceOrderDocument(documentVO);
							if (null != insertDocResponse) {
								if (!insertDocResponse.isSuccess()) {
									if (null != doc.getFileName()) {
										logger.info("Adding the unAssociated"
												+ " Document to "
												+ "invalidDocumentList");
										invalidDocumentList.add(doc.getFileName());
									}
								}
							}
						} else {
							if (null != doc.getFileName()) {
								logger.info("Adding the unAssociated"
										+ " Document to invalidDocumentList");
								invalidDocumentList.add(doc.getFileName());
							}
						}
					} else {
						if (null != doc.getFileName())
							invalidDocumentList.add(doc.getFileName());
						logger.info("Adding the unAssociated Document"
								+ " to invalidDocumentList");
					}
				
				}
				addSODocResponse = soAddDocMapper
						.mapAddSODocResponse(invalidDocumentList);
			
		} catch (Exception ex) {
			logger.error("AddSODocService-->execute()--> Exception-->"
					+ ex.getMessage(), ex);
			Results results = Results.getError(ex.getMessage(),
					ResultsCode.GENERIC_ERROR.getCode());
			addSODocResponse = new AddSODocResponse();
			addSODocResponse.setResults(results);
		}
		addSODocResponse.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
		addSODocResponse
				.setSchemaLocation(PublicAPIConstant.SORESPONSE_SCHEMALOCATION);
		addSODocResponse.setNamespace(PublicAPIConstant.SORESPONSE_NAMESPACE);
		addSODocResponse.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		logger.info("Leaving execute() method of addSODocService");
		
		return addSODocResponse;

	}
	
	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}
	
	public void setSoAddDocMapper(SOAddDocMapper soAddDocMapper) {
		this.soAddDocMapper = soAddDocMapper;
	}	
}
