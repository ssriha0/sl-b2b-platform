import junit.framework.TestCase;

import org.drools.RuleBase;

import com.newco.marketplace.translator.rules.RulesLoader;


public class TestRuleLoading extends TestCase {

	public TestRuleLoading(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetRuleBase() {
		try {
			RuleBase ruleBase = RulesLoader.getRuleBase();
			assertNotNull("Checking rule base not null", ruleBase);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
