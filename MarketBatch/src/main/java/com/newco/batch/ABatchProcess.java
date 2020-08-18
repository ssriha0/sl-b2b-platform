package com.newco.batch;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.exception.core.BusinessServiceException;
/* */
public abstract class ABatchProcess {

	private static final Logger logger = Logger.getLogger(ABatchProcess.class.getName());

	protected static ApplicationContext ctx = null;

	protected void setUp() throws Exception {
			springInit();
			oneTimeSetUp();
	}

	protected void tearDown() throws Exception {
			oneTimeTearDown();
	}

	/**
	 * Anything that should be ran only once (test start) can be put here
	 * @throws Exception
	 */
	protected void oneTimeSetUp() throws Exception {
		logger.info("oneTimeSetUp()");
	}

	/**
	 * Anything that should be ran only once (test end) can be put here
	 * @throws Exception
	 */
	protected void oneTimeTearDown() throws Exception {
		logger.info("oneTimeTearDown()");
	}

	/**
	 * Please add configLocations to property files here to make Spring work.
	 *
	 */
	public abstract void  setArgs(String args[]);

	private void springInit() throws Exception {
		logger.info("Configuring spring");
		try {
		    ctx = MPSpringLoaderPlugIn.getCtx();
		} catch(Exception e) {
			logger.fatal(e);
			throw e;
		}
	}

	public abstract int execute() throws BusinessServiceException;

	public void process(){
		try{
			this.setUp();
			execute();
			tearDown();
		}catch(Throwable t){
			logger.error(t);

		}
	}
}
