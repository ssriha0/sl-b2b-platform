package com.servicelive.wallet.batch.gl;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import static org.mockito.Mockito.*;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.batch.gl.dao.GLDao;
import com.servicelive.wallet.batch.gl.dao.IGLDao;
import com.servicelive.wallet.batch.gl.dao.ISHCOMSDao;
import com.servicelive.wallet.batch.gl.dao.SHCOMSDao;
import com.servicelive.wallet.batch.gl.vo.ShopifyDetailsVO;
import com.servicelive.wallet.batch.gl.vo.ShopifyGLRuleVO;

public class ShopifyGLProcessorTest {

	private ISHCOMSDao shcSupplierDao = null;
	private IGLDao glDao = null;
	private ShopifyGLProcessor shopifyGLProcessor = new ShopifyGLProcessor();
	
	
	List<ShopifyDetailsVO> shopifyCreatedDetails = new ArrayList<ShopifyDetailsVO>();
	List<ShopifyGLRuleVO> glRuleDetails = new ArrayList<ShopifyGLRuleVO>();
	
	@Before
	public void setUp() throws DataServiceException {
		Timestamp startDate = null;
		Timestamp endDate = null;

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		endDate = new java.sql.Timestamp(cal.getTime().getTime());
		cal.add(Calendar.DATE, -1);
		startDate = new java.sql.Timestamp(cal.getTime().getTime());
		
		shopifyGLProcessor = new ShopifyGLProcessor();
		shcSupplierDao = mock(SHCOMSDao.class);
		glDao = mock(GLDao.class);
		
		shopifyGLProcessor.setGlDao(glDao);
		shopifyGLProcessor.setShcSupplierDao(shcSupplierDao);
		
		ShopifyDetailsVO shopifyDetailsVO = new ShopifyDetailsVO();
		shopifyDetailsVO.setCategory("100");
		shopifyDetailsVO.setPrice1("11111_10.00_0.04_REAL,11111");
		shopifyDetailsVO.setPrice2("_5.00_0.04_Personal");
		shopifyDetailsVO.setShopifyOrderNumber("order2");
		shopifyDetailsVO.setSoId("525-3929-4595-19");
		shopifyDetailsVO.setStatus("CREATED");
		shopifyCreatedDetails.add(shopifyDetailsVO);
		
		// setting rule
		ShopifyGLRuleVO shopifyGLRuleVO = new ShopifyGLRuleVO();
		shopifyGLRuleVO.setReportingId("1");
		shopifyGLRuleVO.setGlDivision("0400");
		shopifyGLRuleVO.setGlUnit("09803");
		shopifyGLRuleVO.setGlAccountNo("11531");
		shopifyGLRuleVO.setGlCategory("0100");
		shopifyGLRuleVO.setDescr("Misc Deferred Revenue (Shopify)");
		shopifyGLRuleVO.setLedgerRule("DefRev");
		shopifyGLRuleVO.setMultiplier(1.00);
		shopifyGLRuleVO.setLedgerEntityId("9000");
		shopifyGLRuleVO.setTransactionType("Created");
		glRuleDetails.add(shopifyGLRuleVO);
		
		when(glDao.getRulesForGL()).thenReturn(glRuleDetails);
		when(shcSupplierDao.getShopifyOrderDetails(startDate,endDate, CommonConstants.CREATED)).thenReturn(shopifyCreatedDetails);
	}
	
	@Test
	public void testProcess() throws DataServiceException, SLBusinessServiceException{
		
			shopifyGLProcessor.process();
		}
}
