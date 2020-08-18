import java.util.List;

import junit.framework.TestCase;

import com.newco.marketplace.translator.business.impl.BuyerSkuService;
import com.newco.marketplace.translator.dto.Task;
import com.newco.marketplace.translator.util.SpringUtil;


public class TestSKUTranslation extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testTranslateSKU() {
		BuyerSkuService service = (BuyerSkuService) SpringUtil.factory.getBean("BuyerSkuService");
		Task task = new Task();
		task.setSku("TEST|This is the title");
		List<Task> tasks = service.translateSKU(task);
		assertNotNull(tasks);
		assertEquals(task.getTaskNodeID().intValue(), 1);
	}

}
