/**
 * 
 */
package test.newco.test.marketplace.manager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newco.marketplace.dto.vo.serviceorder.ContactLocationVO;
import com.newco.marketplace.web.delegatesImpl.SOWizardFetchDelegateImpl;

/**
 * @author SALI030
 *
 */
public class SOWizardFetchDelegateImplTest {
	public static void main(String[] args) {
	ApplicationContext ctx;

	ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	SOWizardFetchDelegateImpl soBO = (SOWizardFetchDelegateImpl)ctx.getBean("SOWizardFetchBean");
	ContactLocationVO contLoc=soBO.getBuyerContLocDetails(110);
	System.out.println("list size contact:" + contLoc.getListContact().size());
	System.out.println("primary mobile no:" + contLoc.getBuyerPrimaryContact().getFirstName());
	//System.out.println("list size location:" + contLoc.getListLocation().size());
	System.out.println("business name:" + contLoc.getBuyerPrimaryContact().getBusinessName());
	System.out.println("phone ext:" + contLoc.getBuyerPrimaryContact().getPhoneNoExt());
	
	contLoc=soBO.getBuyerResContLocDetails(110, 11287);
	System.out.println("list size contact:" + contLoc.getListContact().size());
	System.out.println("primary mobile no:" + contLoc.getBuyerPrimaryContact().getFirstName());
	//System.out.println("list size location:" + contLoc.getListLocation().size());
	System.out.println("business name:" + contLoc.getBuyerPrimaryContact().getBusinessName());
	System.out.println("phone ext:" + contLoc.getBuyerPrimaryContact().getPhoneNoExt());

}
}