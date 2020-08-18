/**
 * 
 */
package com.servicelive.spn.dao.common.impl;

import org.dozer.DozerBeanMapper;

import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.persistence.service.document.DocumentService;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.common.DocumentDao;
import com.servicelive.domain.common.Document;

/**
 * @author hoza
 *
 */
public class DocumentDaoImpl extends AbstractBaseDao implements DocumentDao {
	
	private DocumentService documentService;
	private DozerBeanMapper beanMapper;
		
	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.common.DocumentDao#delete(com.servicelive.domain.common.Document)
	 */
	public void delete(Document entity) throws Exception {
		//super.delete(entity);
		DocumentVO documentVo = beanMapper.map(entity, DocumentVO.class);
		documentService.deleteDocument(documentVo);
		//FIXME note setting entity doesn't do anything.
		entity = beanMapper.map(documentVo, Document.class);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.common.DocumentDao#findById(java.lang.Integer)
	 */
	public Document findById(Integer id) throws Exception {
		//return (Document) super.findById(Document.class, id);
		DocumentVO documentVo = documentService.retrieveDocumentByDocumentId(id, "document.query_by_document_id");
		Document document = beanMapper.map(documentVo, Document.class);
		return document;
	}
	public Document findById_spn(Integer id) throws Exception {
		//return (Document) super.findById(Document.class, id);
		DocumentVO documentVo = documentService.retrieveDocumentByDocumentId_spn(id, "document.query_by_document_id");
		Document document = beanMapper.map(documentVo, Document.class);
		return document;
	}
	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.common.DocumentDao#save(com.servicelive.domain.common.Document)
	 */
	public void save(Document entity) throws Exception {
		//super.save(entity);
		DocumentVO documentVo = beanMapper.map(entity, DocumentVO.class);
		documentService.createDocument(documentVo);
		entity.setDocumentId(documentVo.getDocumentId());
		//refresh(entity);
		
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.common.DocumentDao#update(com.servicelive.domain.common.Document)
	 */
	public Document update(Document entity) throws Exception {
		DocumentVO documentVo = beanMapper.map(entity, DocumentVO.class);
		documentService.updateDocument(documentVo);
		return entity;
	}

	/**
	 * @param documentService
	 */
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}
	
	/**
	 * @param beanMapper the beanMapper to set
	 */
	public void setBeanMapper(DozerBeanMapper beanMapper) {
		this.beanMapper = beanMapper;
	}

}
