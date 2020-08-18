import junit.framework.TestCase;

import com.newco.marketplace.translator.business.IApplicationPropertiesService;
import com.newco.marketplace.translator.util.SpringUtil;


public class TestGetSLEndpoint  extends TestCase {

	@Override
	public void setUp() throws Exception {
		// intentionally blank
	}

	@Override
	public void tearDown() throws Exception {
		// intentionally blank
	}

	public void testGetServiceLiveEndpoint() {
		IApplicationPropertiesService service = (IApplicationPropertiesService) SpringUtil.factory.getBean("ApplicationPropertiesService");
		String endpoint = service.getServiceLiveEndpoint();
		
		endpoint = service.getServiceLiveEndpoint();
		
		
		assertNotNull(endpoint);
	}

}
