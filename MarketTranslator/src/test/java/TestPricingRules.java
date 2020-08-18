import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.newco.marketplace.translator.dao.BuyerMT;
import com.newco.marketplace.translator.dao.BuyerDAO;
import com.newco.marketplace.translator.dao.BuyerSku;
import com.newco.marketplace.translator.dao.BuyerSkuDAO;
import com.newco.marketplace.translator.rules.RulesEngine;
import com.newco.marketplace.translator.util.SpringUtil;


public class TestPricingRules extends TestCase {

	public void testFireRules() {
		List<Object> objects = new ArrayList<Object>();
		//buyer 22 for testing
		BuyerDAO buyerDAO = (BuyerDAO) SpringUtil.factory.getBean("BuyerDAO");
		BuyerMT buyer = buyerDAO.findById(Integer.valueOf(22));
		objects.add(buyer);
		BuyerSkuDAO buyerSkuDAO = (BuyerSkuDAO) SpringUtil.factory.getBean("BuyerSkuDAO");
		List<BuyerSku> buyerSkus = buyerSkuDAO.findBySku("TEST");
		for (BuyerSku buyerSku : buyerSkus) {
			objects.add(buyerSku);
		}
		RulesEngine.fireRules(objects);
		//sku margin should be adjusted to 0.99
		for (BuyerSku buyerSku : buyerSkus) {
			assertNotNull(buyerSku);
			assertEquals(0.99, buyerSku.getBidMargin().doubleValue(), 0);
		}
	}

}
