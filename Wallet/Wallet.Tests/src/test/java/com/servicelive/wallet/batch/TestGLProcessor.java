package com.servicelive.wallet.batch;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.batch.gl.GLProcessor;
import com.servicelive.wallet.batch.gl.dao.IGLDao;
import com.servicelive.wallet.batch.mocks.MockGLWriter;

// TODO: Auto-generated Javadoc
/**
 * Class TestGLProcessor.
 */
public class TestGLProcessor {

	/** _context. */
	private static ApplicationContext _context = null;

	/**
	 * getAppContext.
	 * 
	 * @return ApplicationContext
	 * 
	 * @throws BeansException 
	 */
	private static ApplicationContext getAppContext() throws BeansException {

		if (_context == null) {
			_context = new ClassPathXmlApplicationContext("com/servicelive/wallet/batch/testWalletBatchApplicationContext.xml");
		}
		return _context;
	}

	/** glProcessor. */
	private GLProcessor glProcessor;
	
	/** glWriter. */
	private MockGLWriter glWriter;
	
	private IGLDao glDao;

	/**
	 * setup.
	 * 
	 * @return void
	 */
	@Before
	public void setup() {
		ApplicationContext context = getAppContext();
		glDao = (IGLDao) context.getBean("glDao");
		glWriter = (MockGLWriter) context.getBean("mockGLWriter");
		glProcessor = (GLProcessor) context.getBean("GRP_GL.GL_FEED");
//		MockCreditCardBO mockCreditCard = (MockCreditCardBO) context.getBean("mockCreditCard");
//		WalletBO wallet = (WalletBO) context.getBean("wallet");
//		wallet.setCreditCard(mockCreditCard);

//		glWriter = (MockGLWriter)context.getBean("mockGlWriter");
		
//		glProcessor = (GLProcessor) context.getBean("glProcessor");
//		IGLDao glDao = (IGLDao) context.getBean("mockGLDao");
//		glProcessor.setGlDao(glDao);
//		ISHCOMSDao shcomsDao = (ISHCOMSDao) context.getBean("mockSHCOMSDao");
//		glProcessor.setShcSupplierDao(shcomsDao);
//		glProcessor.setGlWriter(glWriter);
		
		//clear out old gl transactions and create new ones
		//BTW this does not clear it out
//		try{
//			glProcessor.process();
//		}catch(Exception e){
//			//do nothing
//		}
	}

	/**
	 * testGLFileProcessor.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testGLFileProcessor() throws SLBusinessServiceException {

		try {
			
			
//			walletClientBO.depositBuyerFundsWithCash("junit-service-order", "SYSTEM", 502484L, 1352057100L, "Depositing money", 300.00);
//			walletClientBO.depositBuyerFundsWithCreditCard("junit-service-order", "SYSTEM", 502485L, 1311175870L, "678", "Depositing money", 302.00);
	
			glProcessor.process();
			
			//Assert.assertEquals(14, glWriter.getGlFeeds().size());
			
		} catch (SLBusinessServiceException e) {

			throw e;
		}
	}

	@Test
	public void _testGetGLSummary(){
		Calendar calendar = Calendar.getInstance();
		boolean admintoolRun = true;
		glDao.getLedgerSummary(calendar.getTime());
	}
	
	
	public GLProcessor getGlProcessor() {
		return glProcessor;
	}

	public void setGlProcessor(GLProcessor glProcessor) {
		this.glProcessor = glProcessor;
	}

	public MockGLWriter getGlWriter() {
		return glWriter;
	}

	public void setGlWriter(MockGLWriter glWriter) {
		this.glWriter = glWriter;
	}

	public IGLDao getGlDao() {
		return glDao;
	}

	public void setGlDao(IGLDao glDao) {
		this.glDao = glDao;
	}

}
