import junit.framework.TestCase;

import com.newco.marketplace.translator.dao.BuyerMT;
import com.newco.marketplace.translator.dao.BuyerDAO;
import com.newco.marketplace.translator.util.SpringUtil;

/**
 * @author gjackson
 *
 */
public class TestGeneralDao extends TestCase {

	private BuyerDAO buyerDao;
	
	/**
	 * @param name
	 */
	public TestGeneralDao(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		buyerDao = (BuyerDAO) SpringUtil.factory.getBean("BuyerDAO");
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link com.newco.marketplace.translator.dao.BuyerDAO#findById(java.lang.Integer)}.
	 */
	public void testFindById() {
		assertNotNull("Checking buyerDAO for null", buyerDao);
		BuyerMT buyer = buyerDao.findById(new Integer(36));
		assertNotNull("Checking buyer[id 36] for null", buyer);
	}

}
