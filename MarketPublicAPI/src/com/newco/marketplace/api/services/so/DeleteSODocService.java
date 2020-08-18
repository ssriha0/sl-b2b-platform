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
import com.newco.marketplace.api.beans.so.addSODoc.AddSODocResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.SODeleteDocMapper;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

/**
 * This class is a service class for deleting the Documents to a SO.
 * 
 * @author Infosys
 * @version 1.0
 */
public class DeleteSODocService extends BaseService {
	private Logger logger = Logger.getLogger(DeleteSODocService.class);
	
	private IDocumentBO documentBO;	
	private SODeleteDocMapper soDeleteDocMapper;

	/**
	 * Constructor
	 */

	public DeleteSODocService() {
		super(null, PublicAPIConstant.SORESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION, null,
				AddSODocResponse.class);
	}

	/**
	 * This method is for deleting SO Documents.
	 * 
	 * @param apiVO
	 *            APIRequestVO
	 * @return AddSODocResponse
	 */
	@SuppressWarnings("unchecked")
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute() method of DeleteSODocService");
		List<String> invalidDocumentList = new ArrayList<String>();
		AddSODocResponse deleteSODocResponse = null;	
				
		String fileName = apiVO.getFileName();
		String soId = apiVO.getSOId();
		DocumentVO documentVO = new DocumentVO();
		documentVO.setSoId(soId);
		documentVO.setFileName(fileName);
		try {
			documentVO = documentBO.retrieveDocumentByFileNameAndSoID(
					Constants.DocumentTypes.SERVICEORDER, documentVO);
			if (null != documentVO) {
				documentVO.setSoId(soId);
				documentVO.setRoleId(OrderConstants.SIMPLE_BUYER_ROLEID);
				logger.info("Going to delete the "
						+ "association of document with ServiceOrder");
				ProcessResponse deleteDocResponse = documentBO
						.deleteServiceOrderDocument(documentVO.getDocumentId(),
								documentVO.getRoleId(), "PublicAPI",null);
				if (null != deleteDocResponse) {
					if (!deleteDocResponse.isSuccess()) {
						logger.info("Adding the unAssociated Document to "
								+ "invalidDocumentList");
						if (null != fileName) {
							invalidDocumentList.add(fileName);
						}
					}
				}
			} else {
				if (null != fileName) {
					invalidDocumentList.add(fileName);
				}
				logger.info("Adding the unAssociated Document to "
						+ "invalidDocumentList");
			}
			deleteSODocResponse = soDeleteDocMapper
					.mapDeleteSODocResponse(invalidDocumentList);
		} catch (Exception ex) {
			logger.error("DeleteSODocService-->execute()--> Exception-->"
					+ ex.getMessage(), ex);
			Results results = Results.getError(ex.getMessage(),
					ResultsCode.GENERIC_ERROR.getCode());
			deleteSODocResponse = new AddSODocResponse();
			deleteSODocResponse.setResults(results);
		}
		logger.info("Leaving execute() method of DeleteSODocService");
		deleteSODocResponse.setVersion(
				PublicAPIConstant.SORESPONSE_VERSION);
		deleteSODocResponse.setSchemaLocation(
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION);
		deleteSODocResponse.setNamespace(
				PublicAPIConstant.SORESPONSE_NAMESPACE);
		deleteSODocResponse.setSchemaInstance(
				PublicAPIConstant.SCHEMA_INSTANCE);
		logger.info("Leaving execute() method of DeleteSODocService");
		return deleteSODocResponse;
	}

	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}

	public void setSoDeleteDocMapper(SODeleteDocMapper soDeleteDocMapper) {
		this.soDeleteDocMapper = soDeleteDocMapper;
	}

}
