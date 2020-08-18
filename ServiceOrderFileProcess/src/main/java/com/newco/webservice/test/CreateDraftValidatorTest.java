package com.newco.webservice.test;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.webservices.response.WSErrorInfo;
import com.newco.marketplace.webservices.validation.so.draft.CreateDraftValidator;

/**
 * @author sali030
 *
 */
public class CreateDraftValidatorTest extends TestCase {

	/**
	 * @throws java.lang.Exception
	 */
	@Override
	protected void setUp() throws Exception {
		// intentionally blank
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Override
	protected void tearDown() throws Exception {
		// intentionally blank
	}

public void testValidatorTest(){
	CreateDraftValidator val = new CreateDraftValidator();
	//System.out.print("hhsdaf");
	ServiceOrder so = new ServiceOrder();
	//Contact co = new Contact();
	//co.setBusinessName("shoukath");
	
	so.setSoTermsCondContent("kjashfdhajksfdhasdf");
	//so.setServiceContact(co);
	
	Map<String, List<WSErrorInfo>> var = val.validateCreateDraft(so);
	List<WSErrorInfo> lis = var.get("error");
	
	System.out.println("error="+lis.size());
	for (int i = 0; i<lis.size();i++){
		System.out.println(lis.get(i).getCode());
		System.out.println(lis.get(i).getMessage());
	}
	

	/*while (it.hasNext()){
		List<WSErrorInfo> lis =new ArrayList();
		lis= (List)it.next();
		System.out.println(lis.size());
		for (int i = 0; i<lis.size();i++){
			System.out.println(lis.size());
		}*/
	//} 
List<WSErrorInfo> list = var.get("warning");
	
	System.out.println("warnings="+list.size());
	for (int i = 0; i<list.size();i++){
		System.out.println(list.get(i).getCode());
		System.out.println(list.get(i).getMessage());
	//assertNotNull("fsd", var);
	System.gc();
	
}
}
}
