package com.newco.marketplace.persistence.daoImpl.provider;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IDocumentDAO;
import com.newco.marketplace.persistence.service.document.DocumentService;
import com.newco.marketplace.vo.provider.DocumentVO;

public class DocumentDAOImpl extends SqlMapClientDaoSupport implements IDocumentDAO {
	

	private static final Logger logger = Logger.getLogger(DocumentDAOImpl.class);
	
	private DocumentService documentService;
	
	public DocumentVO selectMetadatByDocId(DocumentVO inDocumentVO) throws DBException {
		com.newco.marketplace.dto.vo.DocumentVO newDocumentVO = new com.newco.marketplace.dto.vo.DocumentVO();
	     try {
	    	 mapProviderDocumentDetails(inDocumentVO,newDocumentVO);
	    	 newDocumentVO = documentService.getDocumentMetadata(newDocumentVO);
	    	 convertToProviderDocumentDetails(inDocumentVO, newDocumentVO);
	    	 inDocumentVO.setLastUpdateTimestamp(newDocumentVO.getModifiedDate());
		} catch (Exception ex) {		
			throw new DBException("General Exception @DocumentDAOImpl.selectMetadatByDocId() due to " + ex.getMessage());
		}
		return inDocumentVO;
	}
	
	public DocumentVO insert(DocumentVO myDocumentVO)
			throws DBException {
		Integer id = null;
		com.newco.marketplace.dto.vo.DocumentVO newDocumentVO = new com.newco.marketplace.dto.vo.DocumentVO();
		try {
			
			mapProviderDocumentDetails(myDocumentVO,newDocumentVO);
			newDocumentVO = documentService.createDocument(newDocumentVO);
			id = newDocumentVO.getDocumentId();
			myDocumentVO.setDocumentId(id.intValue());
		} catch (Exception ex) {
			throw new DBException("General Exception @DocumentDAOImpl.insert() due to "	+ ex.getMessage());
		}
		
		return myDocumentVO;
	}

	public DocumentVO selectByDocId(DocumentVO inDocumentVO)
			throws DBException {
		Integer docId = null;
		com.newco.marketplace.dto.vo.DocumentVO newDocumentVO = new com.newco.marketplace.dto.vo.DocumentVO();
		try {
			mapProviderDocumentDetails(inDocumentVO, newDocumentVO);
			docId = newDocumentVO.getDocumentId();
			newDocumentVO = documentService.retrieveDocumentByDocumentId(docId,"spn.document.query_by_document_id");
			convertToProviderDocumentDetails(inDocumentVO, newDocumentVO);
		} catch (Exception ex) {
			throw new DBException("General Exception @DocumentDAOImpl.selectByDocId() due to " + ex.getMessage());
		}
		return inDocumentVO;
	}

	public DocumentVO updateDocumentByDocumentId(DocumentVO inDocumentVO)
			throws DBException {

		com.newco.marketplace.dto.vo.DocumentVO newDocumentVO = new com.newco.marketplace.dto.vo.DocumentVO();
		try {
			mapProviderDocumentDetails(inDocumentVO, newDocumentVO);
			newDocumentVO = documentService.updateDocument(newDocumentVO);
			convertToProviderDocumentDetails(inDocumentVO, newDocumentVO);

		} catch (Exception ex) {
			throw new DBException("General Exception @DocumentDAOImpl.updateDocumentByDocumentId() due to "	+ ex.getMessage());
		}
		return inDocumentVO;
	}
	/**
	 * Maps provider DocumentVO
	 * @param DocumentVO
	 * @param com.newco.marketplace.dto.vo.DocumentVO
	 * @return void
	 * @throws DBException
	 */
	private void mapProviderDocumentDetails(DocumentVO documentVO, com.newco.marketplace.dto.vo.DocumentVO newDocumentVO){
		if(null!=documentVO){
			newDocumentVO.setBlobBytes(documentVO.getBlobBytes());
			newDocumentVO.setDocCategoryId(documentVO.getDocCategoryId());
			newDocumentVO.setFileName(documentVO.getFileName());
			newDocumentVO.setFormat(documentVO.getFormat());
			newDocumentVO.setTitle(documentVO.getTitle());
			newDocumentVO.setVendorId(documentVO.getVendorId());
			newDocumentVO.setDescription(documentVO.getDescription());
			newDocumentVO.setSource(documentVO.getSource());
			newDocumentVO.setDocumentId(documentVO.getDocumentId());
			newDocumentVO.setKeywords(documentVO.getKeywords());
			newDocumentVO.setExpireDate(documentVO.getExpireDate());
			newDocumentVO.setPurgeDate(documentVO.getPurgeDate());
			newDocumentVO.setLastUpdateTimestamp(documentVO.getLastUpdateTimestamp());
			newDocumentVO.setDocSize(documentVO.getDocFileSize());
			if(null != documentVO.getRoleId()){
				newDocumentVO.setRoleId(Integer.parseInt(documentVO.getRoleId()));	
			}
		}	
	}
	/**
	 * Maps provider DocumentVO
	 * @param DocumentVO
	 * @param com.newco.marketplace.dto.vo.DocumentVO
	 * @return void
	 * @throws Exception
	 */
	private void convertToProviderDocumentDetails(DocumentVO documentVO, com.newco.marketplace.dto.vo.DocumentVO newDocumentVO){
		if(null!=newDocumentVO){
			documentVO.setBlobBytes(newDocumentVO.getBlobBytes());
			documentVO.setDocCategoryId(newDocumentVO.getDocCategoryId());
			documentVO.setFileName(newDocumentVO.getFileName());
			documentVO.setFormat(newDocumentVO.getFormat());
			documentVO.setTitle(newDocumentVO.getTitle());
			documentVO.setVendorId(newDocumentVO.getVendorId());
			documentVO.setDescription(newDocumentVO.getDescription());
			documentVO.setSource(newDocumentVO.getSource());
			documentVO.setDocumentId(newDocumentVO.getDocumentId());
			documentVO.setKeywords(newDocumentVO.getKeywords());
			documentVO.setExpireDate(newDocumentVO.getExpireDate());
			documentVO.setPurgeDate(newDocumentVO.getPurgeDate());
			documentVO.setLastUpdateTimestamp(newDocumentVO.getLastUpdateTimestamp());
			if(null != newDocumentVO.getRoleId()){
				documentVO.setRoleId(newDocumentVO.getRoleId().toString());		
			}
		}
	}
	
	public DocumentService getDocumentService() {
		return documentService;
	}
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

}
