package com.newco.marketplace.test.persistence.service.impl;

import org.junit.Test;
import static org.mockito.Mockito.*;

import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.persistence.dao.document.DocumentDao;
import com.newco.marketplace.persistence.service.impl.document.DocumentServiceImpl;

public class DocumentServiceImplTest {

	private DocumentDao documentDao;
	private DocumentServiceImpl documentService;
	
	@Test
	public void testdeleteDoc(){
		documentDao = mock(DocumentDao.class);
		DocumentVO documentVO = new DocumentVO();
		documentVO.setDocumentId(12345);
		documentVO.setSoId("541-5430-5650-17");	
		documentService = new DocumentServiceImpl();
		documentService.setDocumentDao(documentDao);
		try {
			when(documentDao.delete("document.so_delete",documentVO)).thenReturn(1);
			documentService.deleteDoc(documentVO);
			
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
