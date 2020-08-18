import junit.framework.TestCase;

import com.newco.marketplace.translator.business.impl.BuyerService;
import com.newco.marketplace.translator.dto.BuyerCredentials;
import com.newco.marketplace.translator.util.SpringUtil;


public class TestBuyerCredentials extends TestCase {

	@Override
	public void setUp() throws Exception {
		// intentionally blank
	}

	@Override
	public void tearDown() throws Exception {
		// intentionally blank
	}

	public void testGetBuyerCredentials() {
		BuyerService service = (BuyerService) SpringUtil.factory.getBean("BuyerService");
		BuyerCredentials creds = service.getBuyerCredentials("buyer0021");
		assertNotNull(creds); 
		assertEquals(creds.getBuyerID().intValue(), 22);
	}

}
