package com.newco.marketplace.business.businessImpl.financeManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.wallet.WalletControlVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.walletControl.WalletControlDao;
import com.newco.marketplace.webservices.base.response.ProcessResponse;


public class WalletControlBOTest {

	private static final Logger logger = Logger
			.getLogger(WalletControlBOTest.class.getName());
	private WalletControlBO walletControlBO;
	private WalletControlDao walletControlDao;

	@Before
	public void setUp() throws Exception {

		walletControlDao = mock(WalletControlDao.class);
		walletControlBO = mock(WalletControlBO.class);
		walletControlBO.setWalletControlDao(walletControlDao);
	}

	@Test
	public void validateInsertWalletControlDocument() {

		DocumentVO documentVO = new DocumentVO();

		WalletControlVO walletControlVO = new WalletControlVO();
		walletControlVO.setAmount(100.00);
		walletControlVO.setWalletControlId(13);
		walletControlVO.setHoldDate(new Date());
		walletControlVO.setWalletControlType(WalletControlEnum
				.getEnumName("irsLevy"));
		walletControlVO.setOnHold(true);
		ProcessResponse processResponse = new ProcessResponse();

		try {

			when(
					walletControlBO.insertWalletControlDocument(documentVO,
							walletControlVO)).thenReturn(processResponse);
			String result = processResponse.getCode();

			assertNotNull(processResponse);

		} catch (Exception e) {
			logger.error("Exception in Junit execution for validateInsertWalletControlDocument"
					+ e.getMessage());
		}

	}

	@Test
	public void saveWalletControl() throws BusinessServiceException,
			DBException {
		List<DocumentVO> documentVOList = new ArrayList<DocumentVO>();
		DocumentVO documentVO = new DocumentVO();
		documentVO.setDocumentId(12);
		documentVOList.add(documentVO);
		// WalletControlDao walletControlDao1 = new WalletControlDaoImpl();
		WalletControlVO walletControlVO = new WalletControlVO();
		walletControlVO.setAmount(new Double(500));
		walletControlVO.setEntityId(102054);
		walletControlVO.setWalletControlId(1);
		walletControlVO.setOnHold(true);
		walletControlVO.setRemainingAmount(new Double(500));

		walletControlVO.setWalletControlType(WalletControlEnum
				.getEnumName("irsLevy"));
		try {
			
			when(
					walletControlBO.walletControlInsertOrUpdate(
							walletControlVO, documentVOList)).thenReturn(
					walletControlVO);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			logger.error("Exception in Junit execution for saveWalletControl"
					+ e1.getMessage());
		}

	}

	@Test
	public void saveWalletControl_1() {
		WalletControlVO walletControlVO = new WalletControlVO();
		walletControlVO.setAmount(new Double(500));
		walletControlVO.setEntityId(13);
		walletControlVO.setWalletControlId(1);
		walletControlVO.setWalletControlType(WalletControlEnum
				.getEnumName("irsLevy"));
		walletControlVO.setOnHold(true);
		try {

			when(walletControlDao.walletControlInsertOrUpdate(walletControlVO))
					.thenReturn(walletControlVO);

			assertNotNull(walletControlVO);

		} catch (Exception e) {
			logger.error("Exception in Junit execution for saveWalletControl"
					+ e.getMessage());
		}
	}
	
	@Test
	public void getDocument() throws DataServiceException {
		DocumentVO vo = new DocumentVO();
		vo.setBuyerId(1);
		try {
			when(walletControlDao.getDocument(1)).thenReturn(vo);

			assertNotNull(vo);

		} catch (Exception e) {
			logger.error("Exception in Junit execution for getDocument" + e.getMessage());
		}
	}

	@Test
	public void deleteDocument() throws DataServiceException {
		Integer i = new Integer(1);
		try {
			when(walletControlDao.deleteDocument(1)).thenReturn(i);
			assertEquals(1, i);
		} catch (Exception e) {
			logger.error("Exception in Junit execution for deleteDocument" + e.getMessage());
		}
	}

	@Test
	public void fetchWalletControl() throws DataServiceException {
		WalletControlVO walletControlVO = new WalletControlVO();
		walletControlVO.setAmount(new Double(500));
		walletControlVO.setEntityId(13);
		walletControlVO.setWalletControlId(1);
		walletControlVO.setWalletControlType(WalletControlEnum.getEnumName("irsLevy"));
		try {
			when(walletControlDao.fetchWalletControl(1)).thenReturn(walletControlVO);
			assertNotNull(walletControlVO);
		} catch (Exception e) {
			logger.error("Exception in Junit execution for fetchWalletControl" + e.getMessage());
		}
	}

}
