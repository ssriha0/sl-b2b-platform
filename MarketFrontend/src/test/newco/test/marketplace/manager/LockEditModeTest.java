/**
 * 
 */
package test.newco.test.marketplace.manager;

import com.newco.marketplace.web.delegatesImpl.SOWizardFetchDelegateImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author SALI030
 *
 */
public class LockEditModeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext ctx;
        String soId="131-8066-7549-65";
        Integer editIndex=1;
		  ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		   SOWizardFetchDelegateImpl soBO = (SOWizardFetchDelegateImpl)ctx.getBean("SOWizardFetchBean");
		   soBO.setLockEditMode(soId, editIndex, null);
		   System.out.println("End of the test");
	}

}
