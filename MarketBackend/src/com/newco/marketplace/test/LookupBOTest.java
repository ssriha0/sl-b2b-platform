package com.newco.marketplace.test;

import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.businessImpl.lookup.LookupBO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.exception.core.DataServiceException;
/**
 * 
 * 
 */
public class LookupBOTest extends TestCase {

	private static Logger logger = Logger.getLogger(LookupBOTest.class);

	private LookupBO lookupBO = null;

	/**
	 * 
	 */
	public void testGetTaxPayerIdNumberTypeList() {
		try {
			List<LookupVO> list = getLookupBO().getTaxPayerTypeIdList();
			assertTrue(list.size() > 0);
		} catch (DataServiceException e) {
			// e.printStackTrace();
			logger.error(e);
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetSkillTreeMainCategories() {
		try {
			List<SkillNodeVO> svo = getLookupBO().getSkillTreeMainCategories();
			for (SkillNodeVO vo : svo) {
				String name = vo.getNodeName(); 
				logger.debug(name);
				assertNotNull(name);
				
			}
		} catch (DataServiceException e) {
			// e.printStackTrace();
			logger.error(e);
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetSkillTreeCategoriesOrSubCategories() {
		try {
			List<SkillNodeVO> svo1 = getLookupBO().getSkillTreeCategoriesOrSubCategories(100);
			logger.debug("size-- " + svo1.size());
			assertTrue(svo1.size() > 0);
		} catch (DataServiceException e) {
			// e.printStackTrace();
			logger.error(e);
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetIfZipInServiceBlackout() {
		try {
			boolean hm = getLookupBO().getIfZipInServiceBlackout(10, "60543");
			logger.debug("#####" + hm);
			assertTrue(hm);
		} catch (DataServiceException e) {
			// e.printStackTrace();
			logger.error(e);
			fail();
		}
	}

	/**
	 * @return the lookupBO
	 */
	public LookupBO getLookupBO() {
		if (lookupBO == null) {
			//TODO This code should be removed once spring injection is working in the tests
			LookupBO lbo = (LookupBO) MPSpringLoaderPlugIn.getCtx().getBean("lookupBO");
			lookupBO = lbo;
		}
		return lookupBO;
	}

	/**
	 * @param lookupBO
	 *            the lookupBO to set
	 */
	public void setLookupBO(LookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}
}
