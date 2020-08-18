/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.security.SecurityProcess;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.SOReadDocMapper;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.exception.BusinessServiceException;

/**
 * This class is a service class for Reading the Documents of an SO.
 * 
 * @author Infosys
 * @version 1.0
 */
public class ReadSODocService  extends BaseService{
	private Logger logger = Logger.getLogger(SOCreateService.class);
	private XStreamUtility conversionUtility;
	private IDocumentBO documentBO;
	private SecurityProcess securityProcess;
	private SOReadDocMapper soReadDocMapper;
	
	/**
	 * Constructor
	 */

	public ReadSODocService() {
		super(null, 
			  null,
			  PublicAPIConstant.SORESPONSE_NAMESPACE,
			  PublicAPIConstant.DOCUMENT_SCHEMAS,
			  PublicAPIConstant.SORESPONSE_SCHEMALOCATION, 
			  null,
			  null);
	}
	public IAPIResponse execute (APIRequestVO apiVO){
		return null;
	}
	/**
	 * This method is for reading SO Docs.
	 * APIRequestVO apiVO
	 * @throws Exception  Exception
	 * @return DocumentVO
	 */
	@SuppressWarnings("unchecked")
	public DocumentVO dispatchReadSODocRequest(APIRequestVO apiVO)
 throws Exception {
		logger.info("Entering ReadSODocService.dispatchReadSODocRequest()");
		DocumentVO docVO = new DocumentVO();

		int buyerId = apiVO.getBuyerIdInteger();
		String fileName = apiVO.getFileName();
		String soId = apiVO.getSOId();
		SecurityContext securityContext = getSecurityContextForBuyerAdmin(buyerId);

		DocumentVO documentVO = new DocumentVO();
		documentVO.setSoId(soId);
		documentVO.setFileName(fileName);
		documentVO = documentBO.retrieveDocumentByFileNameAndSoID(Constants.DocumentTypes.SERVICEORDER, documentVO);
		if (null != documentVO) {
			documentVO.setSoId(soId);
			logger.info(" Docid: " + documentVO.getDocumentId() + " soid: " + documentVO.getSoId() 
					+ " roleId: " + securityContext.getRoleId() + " company id : " + securityContext.getCompanyId());
			docVO = documentBO.retrieveServiceOrderDocumentByDocumentId(documentVO.getDocumentId(), documentVO.getSoId(),
					securityContext.getRoleId(), securityContext.getCompanyId(), securityContext.getRoleId());
			if (null == docVO || null == docVO.getBlobBytes()) {
				logger.error("docVO is null");
				throw new BusinessServiceException(CommonUtility.getMessage(PublicAPIConstant.DOCUMENT_NOTEXISTS_ERROR_CODE));
			}
		} else {
			logger.error("documentVO is null");
			throw new BusinessServiceException(CommonUtility.getMessage(PublicAPIConstant.DOCUMENT_NOTEXISTS_ERROR_CODE));
		}

		logger.info("Leaving ReadSODocService.dispatchReadSODocRequest()");
		return docVO;
	}
	
	
	public XStreamUtility getConversionUtility() {
		return conversionUtility;
	}
	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
	}
	public IDocumentBO getDocumentBO() {
		return documentBO;
	}
	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}
	public SecurityProcess getSecurityProcess() {
		return securityProcess;
	}
	public void setSecurityProcess(SecurityProcess securityProcess) {
		this.securityProcess = securityProcess;
	}
	public SOReadDocMapper getSoReadDocMapper() {
		return soReadDocMapper;
	}
	public void setSoReadDocMapper(SOReadDocMapper soReadDocMapper) {
		this.soReadDocMapper = soReadDocMapper;
	}
	
}

